/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */
package org.opendaylight.defense4all.mitigationdriver.local;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.CounterStat;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DvsnRep;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.DvsnInfo;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.NetNode.AMSConnection;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.Traffic.TrafficDirection;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.core.DvsnInfo.AMSDvsnInfo;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;

public class MitigationDriverLocal extends DFAppModule implements MitigationDriver {

	protected class MitigationResponse {
		String mitigationKey; boolean mitigating;
		MitigationResponse(String mitigationKey, boolean mitigating) {
			this.mitigationKey = mitigationKey; this.mitigating = mitigating;
		}
	}

	protected class MitigationParam {
		String mitigationKey; List<DvsnInfo> netNodeDvsnInfo;
		MitigationParam(String mitigationKey, List<DvsnInfo> netNodeDvsnInfo) {
			this.mitigationKey = mitigationKey; this.netNodeDvsnInfo = netNodeDvsnInfo;
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
	protected static final int ACTION_MITIGATE = 1;
	protected static final int ACTION_END_MITIGATION = 2;
	protected static final int ACTION_RESPOND_MITIGATION = 3;
	protected static final int ACTION_NOTIFY_TOPOLOGY_CHANGED = 4;
	protected static final int ACTION_ADD_NETNODE = 5;
	private static 	 final int ACTION_ADD_PN = 6;
	private static 	 final int ACTION_REMOVE_PN = 7;

	private List<NetNodeAmsConnectivitySet> netNodeSets = null;
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

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		super.reset(resetLevel);
		netNodeSets.clear();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void topologyChanged() throws ExceptionControlApp {
		try {
			invokeDecoupledSerially(ACTION_NOTIFY_TOPOLOGY_CHANGED, null);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_NOTIFY_TOPOLOGY_CHANGED, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to invokeDecoupledSerially. "+ACTION_NOTIFY_TOPOLOGY_CHANGED, e);
		}
	}

	protected void decoupledTopologyChanged() {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp {
		try {
			invokeDecoupledSerially(ACTION_ADD_PN, pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_ADD_PN+" "+pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to invokeDecoupledSerially. "+ACTION_ADD_PN+" "+pnKey, e);
		}
	}

	protected void decoupledAddPN(String pnKey) {
		try {
			dfAppRoot.getAMSRep().addPN(pnKey);
		} catch (Throwable e) {
			log.error("Excepted trying to add PN to AMSRep "+pnKey);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) throws ExceptionControlApp {
		try {
			invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_REMOVE_PN+" "+pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to invokeDecoupledSerially. "+ACTION_REMOVE_PN+" "+pnKey, e);
		}
	}

	protected void decoupledRemovePN(String pnKey) {
		try {
			dfAppRoot.getAMSRep().removePN(pnKey);
		} catch (Throwable e) {
			log.error("Excepted trying to remove PN from AMSRep "+pnKey);
		}
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(String netNodeKey) {
		try {
			invokeDecoupledSerially(ACTION_ADD_NETNODE, netNodeKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerially." + ACTION_ADD_NETNODE, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	/* Check if netnode sets should be updated */
	protected void decoupledAddNetNode(String netNodeKey) {

		Hashtable<String, Object> netNodeRow;
		try {
			netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeKey);
		} catch (Throwable e) {
			log.error("Failed to get NetNode row from netNodesRepo : "+netNodeKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		NetNode netNode;
		try {
			netNode = new NetNode(netNodeRow);
		} catch (Throwable e) {
			log.error("Failed to inflate NetNode row from netNodesRepo : "+netNodeKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Construct a list of all amsLabels this netNode is connected to. */
		Set<String> amsLabels = new HashSet<String>(); String amsLabel;
		Set<Entry<String, AMSConnection>> amsConnectionsSet = netNode.amsConnections.entrySet();
		if (amsConnectionsSet == null ) return;
		Iterator<Map.Entry<String,NetNode.AMSConnection>>  iter = amsConnectionsSet.iterator();
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
	protected List<DvsnInfo> allocateAMSForDiversion(String mitigationKey) throws ExceptionControlApp {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, 
				"Looking for candidate AMSs to divert traffic through per mitigation " + mitigationKey);

		/* First get all AMSs and make sure there are any */
		List<String> amsKeys;
		try {
			amsKeys = dfAppRoot.amsRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to obtain amsKeys from amsRepo",e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to obtain amsKeys from amsRepo.", e);
		}
		if(amsKeys == null) {
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "No AMSs are installed in DF");
			return null;
		}

		List<DvsnInfo> dvsnInfos = new ArrayList<DvsnInfo>();
		String pnKey;
		Hashtable<String, Object> pnRow;
		try {
			String attackKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.ATTACK_KEY); 
			pnKey = (String) dfAppRoot.attacksRepo.getCellValue(attackKey, Attack.PNKEY); 
			pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		} catch (Throwable e) {
			log.error("Failed to obtain attack and/or pnRow relevant for mitigation "+mitigationKey, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to obtain attack and/or pnRow relevant for mitigation "+mitigationKey, e);
		}
		PN pn;
		try {
			pn = new PN(pnRow);
		} catch (Throwable e) {
			log.error("Excepted trying to inflate PN from row. "+ pnKey, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate PN from row. "+ pnKey, e);
		}
		DvsnInfo netNodeDvsnInfo;

		/* For each netNode assign ams device(s) to divert traffic to, such that all netNodes in the same 
		 * netNodeDPConnectivitySet are assigned the same ams device(s). 
		 * Try/catch for extra safety in list processing functions */
		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "For each NetNode looking to assign AMS(s) to divert traffic to");
		StringBuilder sb = new StringBuilder();
		try {
			for (String netNodeLabel : pn.netNodeLabels) {

				netNodeDvsnInfo = getAssignedAmsForNetNodeSet(dvsnInfos, netNodeLabel);
				if(netNodeDvsnInfo == null) // AMS(s) have not yet been assigned for this netNode set
					netNodeDvsnInfo = allocateDvsnAMSForNetNode(mitigationKey, pnKey, netNodeLabel);

				sb.setLength(0);				
				if(netNodeDvsnInfo != null) {					
					sb.append("Allocated for NetNode "); sb.append(netNodeLabel); sb.append(", amsLabels: ");
					for(AMSDvsnInfo amsDvsnInfo : netNodeDvsnInfo.amsDvsnInfos) {
						sb.append(amsDvsnInfo.label); sb.append(", ");
					}
					sb.setLength(sb.length() - 2); 	// Remove last ", "					
					dvsnInfos.add(netNodeDvsnInfo);
				} else
					sb.append("No AMSs were allocated for diversion from " + netNodeLabel);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());
			}
		} catch (Exception e) {
			log.error("Excepted in processing DvsnInfo for PN "+pnKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to assign AMSs in mitigation " + mitigationKey);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted in processing DvsnInfo for PN "+pnKey, e);
		}

		if(dvsnInfos.isEmpty())	return null;
		return dvsnInfos;
	}

	/* Return the assigned AMS(s) for netNodes in the set containing the netNodeLabel. If no AMS(s) was/were
	 * assigned yet return null. */
	protected DvsnInfo getAssignedAmsForNetNodeSet(List<DvsnInfo> dvsnInfos, String netNodeLabel) {

		for(DvsnInfo netNodeDvsnInfo : dvsnInfos) {
			if(inSameNetNodeSet(netNodeDvsnInfo.netNodeLabel, netNodeLabel)) {
				DvsnInfo result = new DvsnInfo(netNodeDvsnInfo);
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
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected DvsnInfo allocateDvsnAMSForNetNode(String mitigationKey, String pnKey, String netNodeLabel) throws ExceptionControlApp {

		List<String> amsKeys;
		try {
			amsKeys = dfAppRoot.amsRepo.getKeys();
		} catch (Throwable e) {			
			log.error("Failed to obtain AmsKeys from amsRepo",e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to obtain AmsKeys from amsRepo.", e);
		}
		if(amsKeys == null) return null;

		Properties dvsnProps = null; AMSDvsnInfo possibleAmsDvsnInfo;
		List<AMSDvsnInfo> possibleAmsDvsnInfos = new ArrayList<AMSDvsnInfo>();

		for(String amsKey : amsKeys) {
			dvsnProps = dfAppRoot.getDvsnRep().getDvsnProps(pnKey, netNodeLabel, amsKey);				
			if (dvsnProps != null) {	// Diversion to this ams (amsKey) is possible	
				possibleAmsDvsnInfo = new AMSDvsnInfo();
				possibleAmsDvsnInfo.label = amsKey;
				possibleAmsDvsnInfo.amsDvsnProps = dvsnProps;
				possibleAmsDvsnInfos.add(possibleAmsDvsnInfo);
				break;
			}
		}
		if(dvsnProps == null) return null;

		/* Select AMSs to divert traffic to. Currently assume a single AMS for NodeNode diversion. */
		List<AMSDvsnInfo> selectedAmsDvsnInfos = selectDvsnAMS(possibleAmsDvsnInfos);
		DvsnInfo dvsnInfo = new DvsnInfo(netNodeLabel, mitigationKey, selectedAmsDvsnInfos, null);
		return dvsnInfo;
	}

	/* Select AMSs for diversion. Right now we select only one - the first one in the list. */
	protected List<AMSDvsnInfo> selectDvsnAMS(List<AMSDvsnInfo> possibleAmsDvsnInfos) {

		if(possibleAmsDvsnInfos == null || possibleAmsDvsnInfos.isEmpty()) return null;

		List<AMSDvsnInfo> selectedDvsnAMS = new ArrayList<AMSDvsnInfo>();
		selectedDvsnAMS.add(possibleAmsDvsnInfos.get(0));
		return selectedDvsnAMS;
	}

	@Override
	public void mitigate(String mitigationKey) throws ExceptionControlApp {
		try {
			invokeDecoupledSerially(ACTION_MITIGATE, mitigationKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_MITIGATE+" "+mitigationKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to invokeDecoupledSerially. "+ACTION_MITIGATE+" "+mitigationKey, e);		
		}	
	}

	protected void decoupledMitigate(String mitigationKey) {

		MitigationResponse mitigationResponse = new MitigationResponse(mitigationKey, false);

		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "MitigationDriverLocal attempts to mitigate " + mitigationKey);

		/* Select a AMS for diversion and update the mitigation record */		
		List<DvsnInfo> dvsnInfos;
		try {
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"MitigationDriverLocal is checking diversion options for "
					+ mitigationKey);
			dvsnInfos = allocateAMSForDiversion(mitigationKey);
		} catch (Throwable e) {
			log.error("Failed to allocate AMS for mitigation " + mitigationKey );
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "MitigationDriverLocal failed to mitigate "+mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			dvsnInfos = null;
		}
		if (dvsnInfos == null) { // Notify Mitigation manager - not mitigating
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "MitigationDriverLocal will not mitigate "+mitigationKey);
			try {
				invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse);
			} catch (ExceptionControlApp e) {
				log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_RESPOND_MITIGATION+" "+mitigationKey, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to mitigate "+mitigationKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			} 
			return;
		}

		List<String> tFloorKeys = new ArrayList<String>();
		String tFloorKey = null;
		String pnkey;
		try {
			pnkey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
		} catch (Throwable e1) {
			log.error("Failed to obtain PN for mitigation. "+mitigationKey, e1);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to mitigate "+mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			try {
				invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse);
			} catch (ExceptionControlApp e) {
				log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_RESPOND_MITIGATION+" "+mitigationKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			} 
			return;
		}

		// Store parameters for possible clean-up after failure
		//	List<> 

		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"MitigationDriverLocal has found diversion options for "
				+ mitigationKey + ", and is attempting to divert traffic from all NetNodes through which the " +
				"subject traffic flows.");
		StringBuilder sb = new StringBuilder();
		StringBuilder sbNetNodeAMSs = new StringBuilder();
		
		for(DvsnInfo dvsnInfo : dvsnInfos) {

			/* Set the traffic bandwidth of the peacetime floor in this netNode location. */
			String bandwidth = retrieveBandwidth(dvsnInfo.netNodeLabel, pnkey);
			dvsnInfo.configProps.setProperty(DvsnInfo.INBOUND_BANDWIDTH, bandwidth);

			/* Record netNodeDvsnInfo in its repo and record the record key in relevant mitigations repo. */
			try {
				dfAppRoot.dvsnInfosRepo.setRow(dvsnInfo.key, dvsnInfo.toRow());
				dfAppRoot.mitigationsRepo.setCell(dvsnInfo.mitigationKey, Mitigation.DVSN_INFO_KEY_PREFIX + dvsnInfo.key, dvsnInfo.key);
			} catch (ExceptionControlApp e) {
				log.error("Failed to persist dvsnInfo in dvsnInfosRepo and/or mitigationsRepo "+dvsnInfo.key, e );
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to divert traffic from "
						+ dvsnInfo.netNodeLabel + ". Continuing to try to divert from other NetNodes if any.");
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				failedDiversionCleanup(dvsnInfo, true, false);
				// don't perform actual mitigation in this case - it will not be deleted
				continue;
			}                                                                  

			/* Configure AMSs that will mitigate this attack with relevant bandwidth.
			 * Divert traffic to mitigating AMSs. */
			try {
				
				sb.setLength(0);
				sbNetNodeAMSs.setLength(0);
				sb.append("MitigationDriverLocal is adding security configuration to AMSs to receive traffic from NetNode."); 
				sbNetNodeAMSs.append("NetNode="); sbNetNodeAMSs.append(dvsnInfo.netNodeLabel);
				sbNetNodeAMSs.append(". AMS(s) is/are: ");
				for(AMSDvsnInfo amsDvsnInfo : dvsnInfo.amsDvsnInfos) {
					sbNetNodeAMSs.append(amsDvsnInfo.label); sb.append(", "); 
				}
				sbNetNodeAMSs.setLength(sb.length() - 2); // Remove the last ", "
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,sb.toString() + sbNetNodeAMSs.toString());
				
				dfAppRoot.getAMSRep().addSecurityConfiguration(dvsnInfo.key);
				
			} catch (ExceptionControlApp e) {
				log.error("Failed to set AMS Security configuration for diversion "+dvsnInfo.key, e );
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to add security configuration for diversion from "
						+ dvsnInfo.netNodeLabel);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				failedDiversionCleanup(dvsnInfo, true, true);
				continue;
			} 

			int retry = 3; boolean isError = false;
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"MitigationDriverLocal is diverting traffic from " 
					+ sbNetNodeAMSs.toString());
			
			while ( retry-- > 0 ) {
				try {
					tFloorKey = dfAppRoot.getDvsnRep().divert(mitigationKey, dvsnInfo.key);
				} catch (Throwable e) {
					if ( ! isError ) {
						log.error("Failed to start diverson "+dvsnInfo.key, e);
						fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					}
					isError = true;
					continue;
				}
				isError = false;
				break;
			}

			/* If succeeded creating the traffic floor - record it in PNs record and in mitigation record. */
			if(tFloorKey != null) {
				
				isError = false;
				try {
					dfAppRoot.pNsRepo.setCell(pnkey, PN.TRAFFIC_FLOOR_KEY_PREFIX + tFloorKey, tFloorKey);
					dfAppRoot.mitigationsRepo.setCell(mitigationKey,Mitigation.TRAFFIC_FLOOR_KEY_PREFIX+tFloorKey,tFloorKey);
				} catch (ExceptionControlApp e) {
					log.error("Failed to persist traffic floor in pNsRepo and/or mitigationsRepo "+dvsnInfo.key, e);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"MitigationDriverLocal failed to update internal "
							+ "records. Canceling diversion for " + sbNetNodeAMSs.toString());					
					failedTrafficFloorCleanup( pnkey, mitigationKey, tFloorKey); // delete floor in this case
					isError = true;
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
				if ( ! isError ) tFloorKeys.add(tFloorKey);
			}
		}

		Boolean isMitigationRepoError = false; Boolean isMonitoringError = false;
		if(!tFloorKeys.isEmpty()) { // Diversion succeeded at least on some netNodes " */			
			try {
				dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ACTIVE.name());
			} catch (ExceptionControlApp e) {
				log.error("Failed to update mitigation status in mitigationsRepo "+mitigationKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				isMitigationRepoError = true;
			}
			if ( ! isMitigationRepoError ) {
				try {
					
					fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"MitigationDriverLocal is instructing AMSRep to start " 
							+ "monitoring from AMS(s) for diverted traffic according to mitigation " + mitigationKey);

					dfAppRoot.getAMSRep().startMonitoring(mitigationKey); //Notify amsRep -can trigger attack monitoring by AMS
				} catch (ExceptionControlApp e) {
					log.error("Excepted in startMonitoring invocation", e);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to instruct AMSRep to "
							+ "start monitoring from AMS(s) for diverted traffic according to mitigation "+mitigationKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					isMonitoringError = true;
				}
			}
			if ( isMitigationRepoError || isMonitoringError ) {
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to mitigate "+mitigationKey);
				failedMitigationCleanup(pnkey, mitigationKey, dvsnInfos, tFloorKeys );
			} else {
				mitigationResponse.mitigating = true; 
			}
		}

		decoupledRespondMitigation(mitigationResponse); 
	}

	protected void failedMitigationCleanup(String pnkey, String mitigationKey, List<DvsnInfo> dvsnInfos, List<String> tFloorKeys) {
		// delete all traffic floors from failed mitigation
		if ( tFloorKeys != null ) {
			for ( String tFloorKey:tFloorKeys ) 
				failedTrafficFloorCleanup(pnkey,  mitigationKey,  tFloorKey);	
		}

		// delete all diversions from failed mitigation
		if ( dvsnInfos != null ) {
			for ( DvsnInfo dvsnInfo:dvsnInfos )
				failedDiversionCleanup(dvsnInfo, true, true );
		}

		// revert operations from mitigation
		try {
			dfAppRoot.getAMSRep().stopMonitoring(mitigationKey);
		} catch (Throwable e) {};

		try {
			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.INVALID.name());
		} catch(Throwable e) {};
	}

	protected void failedDiversionCleanup(DvsnInfo dvsnInfo, Boolean cleanDvsnRepo, Boolean cleanSecConf) {		
		if (cleanSecConf ) {
			try {
				dfAppRoot.getAMSRep().removeSecurityConfiguration(dvsnInfo.key);
			} catch (Throwable e) {};
		}

		if ( cleanDvsnRepo ) {
			try {
				dfAppRoot.dvsnInfosRepo.deleteRow(dvsnInfo.key);   
				dfAppRoot.mitigationsRepo.deleteCell(dvsnInfo.mitigationKey, Mitigation.DVSN_INFO_KEY_PREFIX + dvsnInfo.key);
			} catch (Throwable e) {};
		}

		try {
			dfAppRoot.mitigationsRepo.setCell(dvsnInfo.mitigationKey, Mitigation.STATUS, Mitigation.Status.INVALID.name());
		} catch (Throwable e) {};
	}

	protected void failedTrafficFloorCleanup(String pnkey, String mitigationKey, String tFloorKey) {
		int retry = 3; 
		while ( retry-- > 0 ) {
			try {
				dfAppRoot.getDvsnRep().endDvsn(mitigationKey, tFloorKey);
			} catch (Throwable e) {};
			break;
		}
		try {
			dfAppRoot.pNsRepo.deleteCell(pnkey, PN.TRAFFIC_FLOOR_KEY_PREFIX + tFloorKey);
			dfAppRoot.mitigationsRepo.deleteCell(mitigationKey, Mitigation.TRAFFIC_FLOOR_KEY_PREFIX + tFloorKey);
		} catch (ExceptionControlApp e) {}
	}

	protected String retrieveBandwidth(String netNodeLabel, String pnkey) {

		String peaceFloorKey = TrafficFloor.generatePeacetimeFloorKey(netNodeLabel, pnkey);
		String counterStatKey = CounterStat.generateKey(peaceFloorKey);
		TrafficTuple trafficTuple = null;
		try {
			String trafficTupleStr = (String) dfAppRoot.countersStatsRepo.getCellValue(counterStatKey, CounterStat.MOVING_AVERAGE);
			trafficTuple = new TrafficTuple(trafficTupleStr);
		} catch (Throwable e) {
			log.error("Failed to obtain bandwidth for NetNode "+netNodeLabel+" PN "+pnkey, e );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			trafficTuple = new TrafficTuple();
		}

		String bandwidth = trafficTuple.getBandwidth(TrafficDirection.INBOUND);
		return bandwidth;
	}

	protected void decoupledRespondMitigation(MitigationResponse mitigationResponse) {
		try {
			dfAppRoot.getMitigationMgr().handleMitigationResponse(mitigationResponse.mitigationKey, mitigationResponse.mitigating);
		} catch (Throwable e) {
			log.error("Excepted trying to process mitigation response "+mitigationResponse.mitigationKey+" mitigating : "+mitigationResponse.mitigating );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}		
	}

	@Override
	public void endMitigation(String mitigationKey) throws ExceptionControlApp {		
		try {
			invokeDecoupledSerially(ACTION_END_MITIGATION, mitigationKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_END_MITIGATION + " " + mitigationKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to invokeDecoupledSerially. "+ ACTION_END_MITIGATION + " " + mitigationKey, e);
		}
	}

	protected void decoupledEndMitigation(String mitigationKey) {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "MitigationDriverLocal is ending mitigation " + mitigationKey);
		/* Notify amsRep to stop monitoring for mitigation of this attack. */
		try {
			String msg = "MitigationDriverLocal instructs AMSRep to stop monitoring for traffic per " + mitigationKey;
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, msg);
			dfAppRoot.getAMSRep().stopMonitoring(mitigationKey);
		} catch (Throwable e) {
			log.error("Excepted in stopMonitoring invocation", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
		} 

		/* Inflate mitigation object. */
		Hashtable<String, Object> mitigationRow;
		try {
			mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
		} catch (Throwable e) {
			log.error("Failed to get row from mitigationsRepo for "+mitigationKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to end mitigation "+mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		Mitigation mitigation;
		try {
			mitigation = new Mitigation(mitigationRow);
		} catch (Throwable e) {
			log.error("Failed to inflate Mitigation from row ", e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"MitigationDriverLocal failed to end mitigation "+mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* End the diversion itself per each trafficFloor in this mitigation, and remove the trafficFloor from pn's repo */
		DvsnRep dvsnRep = dfAppRoot.getDvsnRep();
		String pnKey = mitigation.pnKey; 
		String msgTrying = "MitigationDriverLocal is ending diversion for traffic floor ";
		String msgSucceeded = "MitigationDriverLocal succeeded ending diversion for traffic floor ";
		String msgFailed = "MitigationDriverLocal failed ending diversion for traffic floor ";
		if(mitigation.trafficFloorKeys != null && !mitigation.trafficFloorKeys.isEmpty()) { 
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"MitigationDriverLocal is ending diversions for "+mitigationKey);
			for(String trafficFloorKey : mitigation.trafficFloorKeys) {
				try {
					fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,msgTrying + trafficFloorKey);
					dvsnRep.endDvsn(mitigationKey, trafficFloorKey);
				} catch (Throwable e) {
					log.error("Failed to end diverson and/or update pNsRepo row "+pnKey, e);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,msgFailed + trafficFloorKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
				try {
					dfAppRoot.pNsRepo.deleteCell(pnKey, PN.TRAFFIC_FLOOR_KEY_PREFIX + trafficFloorKey);
				} catch (Throwable e) {
					log.error("Failed to delete traffic floor cell from PN row "+pnKey+" "+trafficFloorKey, e);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
			}
		}

		/* Remove the security configuration from all mitigating AMSs. */
		msgTrying = "MitigationDriverLocal is removing security configuration for diversion ";
		msgSucceeded = "MitigationDriverLocal succeeded removing security configuration for diversion ";
		msgFailed = "MitigationDriverLocal failed removing security configuration for diversion ";
		for(String dvsnInfoKey : mitigation.dvsnInfoKeys) {
			try {
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,msgTrying + dvsnInfoKey);
				dfAppRoot.getAMSRep().removeSecurityConfiguration(dvsnInfoKey);
			} catch (ExceptionControlApp e) {
				log.error("Failed to AMS removeSecurityConfiguration for "+dvsnInfoKey, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,msgFailed + dvsnInfoKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}

		/* Remove the mitigation record from mitigations repo, and remove dvsnInfos records from dvsnInfos repo. */
		for(String dvsnInfoKey : mitigation.dvsnInfoKeys) {
			try {
				dfAppRoot.dvsnInfosRepo.deleteRow(dvsnInfoKey);
			} catch (Throwable e) {
				log.error("Failed to delete dvsnInfosRepo row "+dvsnInfoKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_MITIGATE:
			decoupledMitigate((String) param);
			break;
		case ACTION_END_MITIGATION:
			decoupledEndMitigation((String) param);
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
		case ACTION_ADD_PN:
			decoupledAddPN((String) param);
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		default:
			break;
		}
	}
}
