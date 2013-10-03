/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.mitigationdriver.local;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNodeDvsnInfo;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class MitigationDriverLocal extends DFAppModule implements MitigationDriver {
	
	protected class MitigationResponse {
		String mitigationKey; boolean mitigating;
		MitigationResponse(String mitigationKey, boolean mitigating) {
			this.mitigationKey = mitigationKey; this.mitigating = mitigating;
		}
	}
	
	protected class MitigationParam {
		String mitigationKey; List<NetNodeDvsnInfo> dvsnAmsInfo;
		MitigationParam(String mitigationKey, List<NetNodeDvsnInfo> dvsnAmsInfo) {
			this.mitigationKey = mitigationKey; this.dvsnAmsInfo = dvsnAmsInfo;
		}
	}
	
	protected class NetNodeAmsConnectivitySet {
		
		Set<String> netNodeLabels;
		Set<String> amsLabels;
		
		NetNodeAmsConnectivitySet() {
			netNodeLabels = new HashSet<String>();
			amsLabels = new HashSet<String>();
		}
	}
	
	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_DIVERT_AGAINST_OTHER_MODULES = 1;
	protected static final int ACTION_END_DIVERSION_AGAINST_OTHER_MODULES = 2;
	protected static final int ACTION_RESPOND_MITIGATION = 3;
	protected static final int ACTION_NOTIFY_TOPOLOGY_CHANGED = 4;
	protected static final int ACTION_ADD_NETNODE = 5;

	private List<NetNodeAmsConnectivitySet> netNodeSets = null;
	Logger log = LoggerFactory.getLogger(this.getClass());
	String label = null;
	
	/* Constructor for Spring */
	public MitigationDriverLocal() {
		super();
	}
	
	/* Setters for Spring */
	public void setLabel(String label) {this.label = label;}

	@Override
	public String getLabel() {return label;}
		
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {		
		super.init();
		netNodeSets = new ArrayList<NetNodeAmsConnectivitySet>();
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
		netNodeSets.clear();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void topologyChanged() {
		invokeDecoupledSerially(ACTION_NOTIFY_TOPOLOGY_CHANGED, null);
	}
	
	protected void decoupledTopologyChanged() {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(String netNodeKey) {
		invokeDecoupledSerially(ACTION_ADD_NETNODE, netNodeKey);
	}
	
	/* Check if netnode sets should be updated */
	protected void decoupledAddNetNode(String netNodeKey) {
		
		Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeKey);
		NetNode netNode;
		try {
			netNode = new NetNode(netNodeRow);
		} catch (UnknownHostException e) {
			// TODO: log
			return;
		}
		
		/* Construct a list of all amsLabels this netNode is connected to. */
		Set<String> amsLabels = new HashSet<String>(); String amsLabel;
		Iterator<Map.Entry<String,NetNode.AMSConnection>> iter = netNode.amsConnections.entrySet().iterator();
		while(iter.hasNext()) {
			amsLabel = iter.next().getKey();
			amsLabels.add(amsLabel);
		}

		/* If the set of AMSs connected to this netNode is identical to the set of AMSs connected to netNodes
		 * in some set, then add this netNode to that set. */
		for(NetNodeAmsConnectivitySet netNodeSet : netNodeSets) {
			if(netNodeSet.amsLabels.equals(amsLabels)) {
				netNodeSet.netNodeLabels.add(netNode.label);
				return;
			}
		}
		
		/* Not found any existing matching set, so create a new one and add to the sets of netNodes. */
		NetNodeAmsConnectivitySet netNodeSet = new NetNodeAmsConnectivitySet();
		netNodeSet.netNodeLabels.add(netNode.label);
		netNodeSet.amsLabels.addAll(amsLabels);
		netNodeSets.add(netNodeSet);
	}

	/* Allocate AMS(s) for diversion for each of the netNodes through which attacked PN traffic flows.
	 * Ensure that netNodes in a set are assigned the same AMS(s). */
	protected List<NetNodeDvsnInfo> allocateAMSForDiversion(String mitigationKey) throws UnknownHostException {
		
		/* First get all AMSs and make sure there are any */
		List<String> amsKeys = dfAppRoot.amsRepo.getKeys();
		if(amsKeys == null) return null;
		
		List<NetNodeDvsnInfo> dvsnInfos = new ArrayList<NetNodeDvsnInfo>();
		String attackKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.ATTACK_KEY); 
		String pnKey = (String) dfAppRoot.attacksRepo.getCellValue(attackKey, Attack.PNKEY); 
		Hashtable<String, Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		PN pn = new PN(pnRow);
		NetNodeDvsnInfo netNodeDvsnInfo;

		/* For each netNode assign ams device(s) to divert traffic to, such that all netNodes in the same 
		 * netNodeDPConnectivitySet are assigned the same ams device(s). */
		for (String netNodeLabel : pn.netNodeLabels) {
			netNodeDvsnInfo = getAssignedAmsForNetNodeSet(dvsnInfos, netNodeLabel);
			if(netNodeDvsnInfo == null) // AMS(s) have not yet been assigned for this netNode set
				netNodeDvsnInfo = allocateDvsnAMSForNetNode(mitigationKey, pnKey, netNodeLabel);
			if(netNodeDvsnInfo != null) 
				dvsnInfos.add(netNodeDvsnInfo);
		}
		
		if(dvsnInfos.isEmpty())	return null;
		return dvsnInfos;
	}
	
	/* Return the assigned AMS(s) for netNodes in the set containing the netNodeLabel. If no AMS(s) was/were
	 * assigned yet return null. */
	protected NetNodeDvsnInfo getAssignedAmsForNetNodeSet(List<NetNodeDvsnInfo> dvsnInfos, String netNodeLabel) {
		
		for(NetNodeDvsnInfo netNodeDvsnInfo : dvsnInfos) {
			if(inSameNetNodeSet(netNodeDvsnInfo.netNodeLabel, netNodeLabel)) {
				NetNodeDvsnInfo result = new NetNodeDvsnInfo(netNodeDvsnInfo);
				result.netNodeLabel = netNodeLabel;
				return result;
			}
		}
		return null;
	}
	
	/* Check if two netNodes are in the same netNodeSet. */
	protected boolean inSameNetNodeSet(String netNodeLabel1, String netNodeLabel2) {
		
		/* Loop through all netNodeSets to check if netNodeLabel1 is in one of them. In case a set, containing
		 * netNodeLabel1 is found, and that set also contains netNodeLabel2 - return true. Otherwise false */
		for(NetNodeAmsConnectivitySet netNodeSet : netNodeSets) {
			if(netNodeSet.netNodeLabels.contains(netNodeLabel1)) {
				if(netNodeSet.netNodeLabels.contains(netNodeLabel2)) 
					return true;
				else 
					return false;
			}
		}
		return false;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected NetNodeDvsnInfo allocateDvsnAMSForNetNode(String mitigationKey, String pnKey,	String netNodeLabel) {

		List<String> amsKeys = dfAppRoot.amsRepo.getKeys();
		Properties dvsnProps = null;
		List<String> amsLabels = new ArrayList<String>();
		
		for(String amsKey : amsKeys) {
			dvsnProps = dfAppRoot.getDvsnRep().getDvsnProps(pnKey, netNodeLabel, amsKey);				
			if (dvsnProps != null) {	// Diversion to this ams (amsKey) is possible		
				amsLabels.add(amsKey);
				break;
			}
		}
		if(dvsnProps == null) return null;

		/* Found an AMS to which diversion is possible from the current netNode */	
		NetNodeDvsnInfo dvsnInfo = new NetNodeDvsnInfo(netNodeLabel, amsLabels, dvsnProps);
		String dvsnInfoStr = dvsnInfo.toString();
		String dvsnInfoColumnName = Mitigation.generateDvsnInfoColumnName(dvsnInfoStr);
		dfAppRoot.mitigationsRepo.setCell(mitigationKey,dvsnInfoColumnName, dvsnInfoStr);

		return dvsnInfo;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) {}

	@Override
	public void mitigate(String mitigationKey) {

		/* Select a AMS for diversion and update the diversion record */		
		List<NetNodeDvsnInfo> netNodeDvsnInfos;
		try {
			netNodeDvsnInfos = allocateAMSForDiversion(mitigationKey);
		} catch (UnknownHostException e) {
			; // TODO log internal error - hostname should be correct
			netNodeDvsnInfos = null;
		}
		if (netNodeDvsnInfos == null) { // Notify Mitigation manager - not mitigating
			MitigationResponse mitigationResponse = new MitigationResponse(mitigationKey, false);
			invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse); 
			return;
		}

		/* Do the actual traffic diversion and start collecting stats from the diversion AMS */
		MitigationParam mitigationParam = new MitigationParam(mitigationKey, netNodeDvsnInfos);
		invokeDecoupledSerially(ACTION_DIVERT_AGAINST_OTHER_MODULES, mitigationParam);	
	}
	
	protected void decoupledDivertAgainstOtherModules(MitigationParam mitigationParam) {

		String mitigationKey = mitigationParam.mitigationKey;
		MitigationResponse mitigationResponse = new MitigationResponse(mitigationKey, false);
		List<NetNodeDvsnInfo> netNodeDvsnInfos = mitigationParam.dvsnAmsInfo;
		String pnKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
		String trafficFloorKey; 
		List<String> trafficFloorKeys = new ArrayList<String>();

		for(NetNodeDvsnInfo netNodeDvsnInfo : netNodeDvsnInfos) {
			trafficFloorKey = divertAgainstOtherModules(mitigationKey, pnKey, netNodeDvsnInfo);
			if(trafficFloorKey != null)
				trafficFloorKeys.add(trafficFloorKey);
		}

		if(trafficFloorKeys.isEmpty()) { // Diversion failed on all netNodes - notify "not mitigating"
			mitigationResponse.mitigating = false; 
		} else { 						 // Diversion succeeded at least on some netNodes " */
			for(String trafficFloorKey2 : trafficFloorKeys)
				dfAppRoot.pNsRepo.setCell(pnKey, PN.TRAFFIC_FLOOR_KEY_PREFIX + trafficFloorKey2, trafficFloorKey2);
			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ACTIVE.name());
			dfAppRoot.getAMSRep().addMitigation(mitigationKey); //Notify amsRep -can trigger attack monitoring by AMS			
			mitigationResponse.mitigating = true; 
		}
		
		decoupledRespondMitigation(mitigationResponse); 
	}
	
	protected String divertAgainstOtherModules(String mitigationKey, String pnKey, NetNodeDvsnInfo dvsnInfo) {

		/* Configure AMS with latest baselines */
		String baselineStr = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.BASELINES); // TODO: change to counter baselines
		TrafficTuple baselines = new TrafficTuple(baselineStr);
		String amsLabel = dvsnInfo.amsLabels.get(0); // Assume a single AMS for NetNode diversion
		dfAppRoot.getAMSRep().setAMSBaselines(amsLabel, baselines);
		
		/* Divert traffic */
		String trafficFloorKey = dfAppRoot.getDvsnRep().divert(mitigationKey, dvsnInfo);
		return trafficFloorKey;
	}

	protected void decoupledRespondMitigation(MitigationResponse mitigationResponse) {
		dfAppRoot.getMitigationMgr().handleMitigationResponse(mitigationResponse.mitigationKey, mitigationResponse.mitigating);		
	}

	@Override
	public void endMitigation(String mitigationKey) {		
		invokeDecoupledSerially(ACTION_END_DIVERSION_AGAINST_OTHER_MODULES, mitigationKey);
	}

	protected void decoupledEndDiversionAgainstOtherModules(String mitigationKey) {

		String pnKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
		dfAppRoot.getAMSRep().removeMitigation(mitigationKey); // Notify amsRep about ending diversion.		
		List<String> trafficFloorKeys = dfAppRoot.getDvsnRep().endDvsn(mitigationKey); // End the diversion itself

		if(trafficFloorKeys != null && !trafficFloorKeys.isEmpty()) { 
			for(String trafficFloorKey : trafficFloorKeys)
				dfAppRoot.pNsRepo.deleteCell(pnKey, PN.TRAFFIC_FLOOR_KEY_PREFIX + trafficFloorKey);			
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
			case ACTION_RESERVED:
				break;
			case ACTION_DIVERT_AGAINST_OTHER_MODULES:
				decoupledDivertAgainstOtherModules((MitigationParam) param);
				break;
			case ACTION_END_DIVERSION_AGAINST_OTHER_MODULES:
				decoupledEndDiversionAgainstOtherModules((String) param);
				break;
			case ACTION_RESPOND_MITIGATION:
				decoupledRespondMitigation((MitigationResponse) param);
				break;
			case ACTION_NOTIFY_TOPOLOGY_CHANGED:
				decoupledTopologyChanged();
				break;
			case ACTION_ADD_NETNODE:
				decoupledAddNetNode((String) param);
				break;
			default:
				break;
		}
	}
}
