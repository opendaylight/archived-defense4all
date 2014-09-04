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

import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.AMSConnection;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.CounterStat;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFAppRoot.HealthStatus;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.DvsnInfo;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.Mitigation;
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
	protected static final int ACTION_ADD_NETNODE = 4;
	private static 	 final int ACTION_ADD_PN = 5;
	private static 	 final int ACTION_REMOVE_PN = 6;
	private static final int   ACTION_NETNODE_DOWNED = 7;

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
		Iterator<Map.Entry<String,AMSConnection>>  iter = amsConnectionsSet.iterator();
		while(iter.hasNext()) {
			amsLabel = iter.next().getValue().amsLabel;
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

		log.info("Looking for candidate AMSs to divert traffic through per mitigation " + mitigationKey);

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
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "No AMS installed on DefenseFlow.");
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
				} else {
					sb.append("No AMSs were allocated for diversion from " + netNodeLabel);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "No AMS allocated for diversion from PO "+PN.getPrintableKey(pn.label));
				}
				log.info(sb.toString());
			}
		} catch (Exception e) {
			log.error("Excepted in processing DvsnInfo for PN "+pnKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "No AMS allocated for diversion from PO "+PN.getPrintableKey(pn.label));
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
	protected DvsnInfo allocateDvsnAMSForNetNode(String mitigationKey, String pnKey, String netNodeLabel) 
			throws ExceptionControlApp {

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
		boolean up;

		for(String amsKey : amsKeys) {
			try {
				up = (Boolean) dfAppRoot.amsRepo.getCellValue(amsKey, AMS.HEALTH_STATUS);
				if(!up) continue;	// This AMS is dead / not healthy. Choose from others.
			} catch (Throwable e) {			
				String msg = "Failed to read from AMS Repo " + amsKey;
				log.error(msg,e);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
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
		if(selectedAmsDvsnInfos == null || selectedAmsDvsnInfos.isEmpty()) return null;
		DvsnInfo dvsnInfo = new DvsnInfo(netNodeLabel, null, mitigationKey, selectedAmsDvsnInfos, null);
		return dvsnInfo;
	}

	/* Select AMSs for diversion. Right now we select only one - the first one in the list. */
	protected List<AMSDvsnInfo> selectDvsnAMS(List<AMSDvsnInfo> possibleAmsDvsnInfos) {

		if(possibleAmsDvsnInfos == null || possibleAmsDvsnInfos.isEmpty()) return null;

		List<AMSDvsnInfo> selectedDvsnAMS = new ArrayList<AMSDvsnInfo>();
		for(AMSDvsnInfo amsDvsnInfo : possibleAmsDvsnInfos) {
			if(AMS.isUp(amsDvsnInfo.label)) {
				selectedDvsnAMS.add(amsDvsnInfo);
				break;	// Currently select only one. Can select multiple for large attacks (elastic scale) in the future.
			}
		}
		return selectedDvsnAMS;
	}

	@Override
	public synchronized void mitigate(String mitigationKey) throws ExceptionControlApp {
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

		log.info("MitigationDriverLocal attempts to mitigate " + mitigationKey);

		/* Select a AMS for diversion and update the mitigation record */		
		List<DvsnInfo> dvsnInfos;
		try {
			log.info("MitigationDriverLocal is checking diversion options for "+ mitigationKey);
			dvsnInfos = allocateAMSForDiversion(mitigationKey);
		} catch (Throwable e) {
			log.error("Failed to allocate AMS for mitigation " + mitigationKey );
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to mitigate "+Mitigation.getPrintableMitigationTarget(mitigationKey));                      
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			dvsnInfos = null;
		}
		if (dvsnInfos == null) { // Notify Mitigation manager - not mitigating
			log.info( "MitigationDriverLocal will not mitigate "+mitigationKey);
			try {
				invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse);
			} catch (ExceptionControlApp e) {
				log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_RESPOND_MITIGATION+" "+mitigationKey, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to mitigate "+Mitigation.getPrintableMitigationTarget(mitigationKey));                      	
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			} 
			return;
		}

		List<String> tFloorKeys = new ArrayList<String>();
		String pnkey;
		try {
			pnkey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
		} catch (Throwable e1) {
			log.error("Failed to obtain PN for mitigation. "+mitigationKey, e1);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to mitigate "+Mitigation.getPrintableMitigationTarget(mitigationKey));                      	
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
			try {
				invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse);
			} catch (ExceptionControlApp e) {
				log.error("Excepted trying to invokeDecoupledSerially. "+ACTION_RESPOND_MITIGATION+" "+mitigationKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			} 
			return;
		}




		log.info("MitigationDriverLocal has found diversion options for "
				+ mitigationKey + ", and is attempting to divert traffic from all NetNodes through which the " +
				"subject traffic flows.");

		for(DvsnInfo dvsnInfo : dvsnInfos) {
			mitigatePerDvsnInfo(dvsnInfo, pnkey, mitigationKey, tFloorKeys);
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

					log.info("MitigationDriverLocal is instructing AMSRep to start " 
							+ "monitoring from AMS(s) for diverted traffic according to mitigation " + mitigationKey);

					dfAppRoot.getAMSRep().startMonitoring(mitigationKey); //Notify amsRep -can trigger attack monitoring by AMS
				} catch (ExceptionControlApp e) {
					log.error("Excepted in startMonitoring invocation", e);
					log.info("MitigationDriverLocal failed to instruct AMSRep to "
							+ "start monitoring from AMS(s) for diverted traffic according to mitigation "+mitigationKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					isMonitoringError = true;
				}
			}
			if ( isMitigationRepoError || isMonitoringError ) {
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to mitigate "+Mitigation.getPrintableMitigationTarget(mitigationKey));                      	
				failedMitigationCleanup(pnkey, mitigationKey, dvsnInfos, tFloorKeys );
			} else {
				mitigationResponse.mitigating = true; 
			}
		}

		decoupledRespondMitigation(mitigationResponse); 
	}

	protected void mitigatePerDvsnInfo(DvsnInfo dvsnInfo, String pnkey, String mitigationKey, List<String> tFloorKeys) {

		/* Set the traffic bandwidth of the peacetime floor in this netNode location. */
		String bandwidth = retrieveBandwidth(dvsnInfo.netNodeLabel, pnkey);
		dvsnInfo.configProps.setProperty(DvsnInfo.INBOUND_BANDWIDTH, bandwidth);

		boolean setDvsnInfoRow = false;
		StringBuilder sb = new StringBuilder();
		StringBuilder sbNetNodeAMSs = new StringBuilder();
		String tFloorKey = null;

		/* Record netNodeDvsnInfo in its repo and record the record key in relevant mitigations repo. */
		try {
			dfAppRoot.dvsnInfosRepo.setRow(dvsnInfo.key, dvsnInfo.toRow());
			setDvsnInfoRow = true;
			dfAppRoot.mitigationsRepo.setCell(dvsnInfo.mitigationKey, Mitigation.DVSN_INFO_KEY_PREFIX + dvsnInfo.key, dvsnInfo.key);
		} catch (ExceptionControlApp e) {
			log.error("Failed to persist dvsnInfo in dvsnInfosRepo and/or mitigationsRepo "+dvsnInfo.key, e );
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to divert "
					+ Mitigation.getPrintableMitigationTarget(dvsnInfo.mitigationKey ));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			try {
				if(setDvsnInfoRow) dfAppRoot.dvsnInfosRepo.deleteRow(dvsnInfo.key);
			} catch (Throwable e1) {/* Ignore */}
			return;
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
			log.info(sb.toString() + sbNetNodeAMSs.toString());

			dfAppRoot.getAMSRep().addSecurityConfiguration(dvsnInfo.key);

		} catch (ExceptionControlApp e) {
			log.error("Failed to set AMS Security configuration for diversion "+dvsnInfo.key, e );
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to add security configuration for diversion "
					+Mitigation.getPrintableMitigationTarget(mitigationKey));  
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		} 

		tFloorKey = reigniteDvsn(dvsnInfo, pnkey);
		if (tFloorKey != null )
			tFloorKeys.add(tFloorKey);
	}

	protected String reigniteDvsn(DvsnInfo dvsnInfo, String pnkey) {

		String tFloorKey = null;
		int retry = 3; boolean printed = false;
		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Reigniting traffic diversion for PO "+
				PN.getPrintableKey(pnkey));


		while ( retry-- > 0 ) {
			try { 
//				dvsnInfo = dfAppRoot.getDvsnRep().prepareForDvsn(dvsnInfo.mitigationKey, dvsnInfo.key);
				dfAppRoot.getDvsnRep().prepareForDvsn(dvsnInfo.mitigationKey, dvsnInfo.key);
				if( dvsnInfo != null ) {
					tFloorKey = dfAppRoot.getDvsnRep().divert(dvsnInfo.mitigationKey, dvsnInfo.key);
					dvsnInfo.tFloorKey = tFloorKey;
				}
				break;
			} catch (Throwable e) {
				if ( ! printed ) {
					log.error("Failed to reignite diverson "+dvsnInfo.key, e);
					printed = true;
				}
				continue;
			}
		}

		/* If succeeded creating the traffic floor - record it in PNs record and in mitigation record. */
		if(tFloorKey != null) {
			try {
				dfAppRoot.pNsRepo.setCell(pnkey, PN.TRAFFIC_FLOOR_KEY_PREFIX + tFloorKey, tFloorKey);
				dfAppRoot.mitigationsRepo.setCell(dvsnInfo.mitigationKey,Mitigation.TRAFFIC_FLOOR_KEY_PREFIX + 
						tFloorKey,tFloorKey);
				dvsnInfo.trafficDiverted = true;
				dfAppRoot.dvsnInfosRepo.setRow(dvsnInfo.key, dvsnInfo.toRow()); // Record trafficDiverted and tFloor.
			} catch (ExceptionControlApp e) {
				log.error("Failed to persist traffic floor in pNsRepo and/or mitigationsRepo "+dvsnInfo.key, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"Internal memory error. Canceling TCP traffic diversion for PO "
						+ PN.getPrintableKey(pnkey));			
				endDvsnPerDvsnInfo(dvsnInfo);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}
		return tFloorKey;
	}

	protected void failedMitigationCleanup(String pnkey, String mitigationKey, List<DvsnInfo> dvsnInfos, 
			List<String> tFloorKeys) {

		// delete all diversions from failed mitigation
		if ( dvsnInfos != null ) {
			for ( DvsnInfo dvsnInfo:dvsnInfos ) {
				try {
					endDvsnPerDvsnInfo(dvsnInfo);
				} catch (Throwable e) {};
				try {
					dfAppRoot.getAMSRep().removeSecurityConfiguration(dvsnInfo.key);
				} catch (Throwable e) {};
				try {
					dfAppRoot.dvsnInfosRepo.deleteRow(dvsnInfo.key);   
					dfAppRoot.mitigationsRepo.deleteCell(dvsnInfo.mitigationKey, Mitigation.DVSN_INFO_KEY_PREFIX + dvsnInfo.key);
				} catch (Throwable e) {};
				try {
					dfAppRoot.mitigationsRepo.setCell(dvsnInfo.mitigationKey, Mitigation.STATUS, Mitigation.Status.INVALID.name());
				} catch (Throwable e) {};
			}
		}

		// revert operations from mitigation
		try {
			dfAppRoot.getAMSRep().stopMonitoring(mitigationKey);
		} catch (Throwable e) {};

		try {
			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.INVALID.name());
		} catch(Throwable e) {};
	}

	//	protected void failedMitigationCleanup(String pnkey, String mitigationKey, List<DvsnInfo> dvsnInfos, 
	//			List<String> tFloorKeys) {
	//
	//		// delete all traffic floors from failed mitigation
	//		if ( tFloorKeys != null ) {
	//			for ( String tFloorKey:tFloorKeys ) 
	//				failedTrafficFloorCleanup(pnkey,  mitigationKey,  tFloorKey);	
	//		}
	//
	//		// delete all diversions from failed mitigation
	//		if ( dvsnInfos != null ) {
	//			for ( DvsnInfo dvsnInfo:dvsnInfos ) {
	//				try {
	//					dfAppRoot.getAMSRep().removeSecurityConfiguration(dvsnInfo.key);
	//				} catch (Throwable e) {};
	//				try {
	//					dfAppRoot.dvsnInfosRepo.deleteRow(dvsnInfo.key);   
	//					dfAppRoot.mitigationsRepo.deleteCell(dvsnInfo.mitigationKey, Mitigation.DVSN_INFO_KEY_PREFIX + dvsnInfo.key);
	//				} catch (Throwable e) {};
	//				try {
	//					dfAppRoot.mitigationsRepo.setCell(dvsnInfo.mitigationKey, Mitigation.STATUS, Mitigation.Status.INVALID.name());
	//				} catch (Throwable e) {};
	//			}
	//		}
	//
	//		// revert operations from mitigation
	//		try {
	//			dfAppRoot.getAMSRep().stopMonitoring(mitigationKey);
	//		} catch (Throwable e) {};
	//
	//		try {
	//			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.INVALID.name());
	//		} catch(Throwable e) {};
	//	}

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

	protected synchronized void decoupledEndMitigation(String mitigationKey) {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Ending mitigation of " + Mitigation.getPrintableMitigationTarget(mitigationKey));
		/* Notify amsRep to stop monitoring for mitigation of this attack. */
		try {
			String msg = "MitigationDriverLocal instructs AMSRep to stop monitoring for traffic per " + mitigationKey;
			log.info(msg); 
			dfAppRoot.getAMSRep().stopMonitoring(mitigationKey);
		} catch (Throwable e) {
			log.error("Excepted in stopMonitoring invocation", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
		} 
		Mitigation mitigation;
		try {
			mitigation = Mitigation.getMitigation(mitigationKey);
		} catch (Throwable e) {
			log.error("Failed to inflate Mitigation from row ", e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to end mitigation of "+Mitigation.getPrintableMitigationTarget(mitigationKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* End the diversion itself per each dvsnInfo in this mitigation, and remove the trafficFloor from pn's repo */
		if(mitigation.dvsnInfoKeys == null || mitigation.dvsnInfoKeys.isEmpty()) return;
		//fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"Ending diversions for "+Mitigation.getPrintableMitigationTarget(mitigationKey));
		DvsnInfo dvsnInfo;
		for(String dvsnInfoKey : mitigation.dvsnInfoKeys) {

			try {
				dvsnInfo = DvsnInfo.getDvsnInfo(dvsnInfoKey);
			} catch (Throwable e) {
				log.error("Failed to inflate dvsnInfo record " + dvsnInfoKey, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to end mitigation of " + Mitigation.getPrintableMitigationTarget(mitigationKey));
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}

			if(dvsnInfo != null) {
//				endDvsnPerDvsnInfo(dvsnInfo);//I moved it below to a new finally.

				/* Remove the security configuration from all mitigating AMSs. */
				try {
					dfAppRoot.getAMSRep().removeSecurityConfiguration(dvsnInfoKey);
				} catch (Throwable e) {
					log.error("Failed to removeSecurityConfiguration " + dvsnInfoKey, e);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to end mitigation of " + Mitigation.getPrintableMitigationTarget(mitigationKey));
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
				finally
				{
					endDvsnPerDvsnInfo(dvsnInfo);
				}
			}

			/* Remove the mitigation record from mitigations repo, and remove dvsnInfos records from dvsnInfos repo. */
			try {
				dfAppRoot.dvsnInfosRepo.deleteRow(dvsnInfoKey);
			} catch (Throwable e) {
				log.error("Failed to delete dvsnInfosRepo row "+dvsnInfoKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}
	}

	@Override
	public void netNodeStatusDowned(String netNodeLabel, HealthStatus healthStatus) {

		try {
			invokeDecoupledSerially(ACTION_NETNODE_DOWNED, netNodeLabel);
		} catch (Throwable e) {
			log.error("Failed to invoke handling of status change of netNode " + netNodeLabel, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	protected void decoupledNetNodeDowned(String netNodeLabel) {

		Hashtable<String,Object> dvsnInfoRow;
		DvsnInfo dvsnInfo = null;
		try {
			Hashtable<String,Hashtable<String,Object>> dvsnInfoTable = dfAppRoot.dvsnInfosRepo.getTable();
			Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = dvsnInfoTable.entrySet().iterator();
			String currentNetNodeLabel; boolean hasActiveDvsnsForMitigation;

			while(iter.hasNext()) {
				dvsnInfoRow = iter.next().getValue();
				currentNetNodeLabel = (String) dvsnInfoRow.get(DvsnInfo.NETNODE_LABEL);
				if(! currentNetNodeLabel.equals(netNodeLabel)) continue; // Some other netNode

				/* End this particular diversion from the netNode. */
				dvsnInfo = new DvsnInfo(dvsnInfoRow);
				endDvsnPerDvsnInfo(dvsnInfo);

				/* Check if there are any active diversions left for this mitigation. If not notify not mitigating. */
				hasActiveDvsnsForMitigation = Mitigation.hasActiveDvsns(dvsnInfo.mitigationKey);
				if(!hasActiveDvsnsForMitigation) {
					decoupledEndMitigation(dvsnInfo.mitigationKey); // Remove all configurations related to this mitigation
					MitigationResponse mitigationResponse = new MitigationResponse(dvsnInfo.mitigationKey, false);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Ending mitigation of "+ Mitigation.getPrintableMitigationTarget(dvsnInfo.mitigationKey) 
							+ " - no live netNodes left to divert traffic from.");
					invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse);
				}
			}
		}  catch (Throwable e) {
			String msg = "Failed to properly handle NetNodeDowned";
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	protected void endDvsnPerDvsnInfo(DvsnInfo dvsnInfo) {

		/* End the diversion itself and remove the trafficFloor from pn's repo */
		if(dvsnInfo.tFloorKey != null) { 
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"Ending diversion of "+Mitigation.getPrintableMitigationTarget(dvsnInfo.mitigationKey));
			try {
				dfAppRoot.getDvsnRep().endDvsn(dvsnInfo.mitigationKey, dvsnInfo.tFloorKey);			
			} catch (Throwable e) {
				String msg = "Failed to end diversion for ";
				log.error("Failed to end diverson from " + dvsnInfo.tFloorKey + " for " + dvsnInfo.mitigationKey, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,msg + Mitigation.getPrintableMitigationTarget(dvsnInfo.mitigationKey));
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			String pnKey = null;
			try {
				pnKey = (String) dfAppRoot.mitigationsRepo.getCellValue(dvsnInfo.mitigationKey, Mitigation.PNKEY);
				dfAppRoot.pNsRepo.deleteCell(pnKey, PN.TRAFFIC_FLOOR_KEY_PREFIX + dvsnInfo.tFloorKey);
				dfAppRoot.mitigationsRepo.deleteCell(dvsnInfo.mitigationKey, Mitigation.TRAFFIC_FLOOR_KEY_PREFIX + 
						dvsnInfo.tFloorKey);
				dvsnInfo.tFloorKey = null;
			} catch (Throwable e) {
				log.error("Failed to delete traffic floor cell from PN row "+pnKey+" "+dvsnInfo.tFloorKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}

		/* Remove topological preparations for this diversion. */
		try {
			String msgTrying = "MitigationDriverLocal is removing topological preparation as well as security configuration for diversion ";
			log.info(msgTrying + dvsnInfo.key);
			dvsnInfo = dfAppRoot.getDvsnRep().unprepareForDvsn(dvsnInfo.mitigationKey, dvsnInfo.key);
		} catch (Throwable e) {
			String msgFailed = "MitigationDriverLocal failed removing topological preparation as well as security configuration for diversion ";
			log.error(msgFailed + dvsnInfo.key, e);
			//fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,msgFailed + dvsnInfo.key);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}

		if ( dvsnInfo == null ) {
			String msgFailed = "MitigationDriverLocal failed removing topological preparation as well as security configuration for diversion ";
			log.error(msgFailed);
			//fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,msgFailed);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Mark trafficDiverted as false. */
		dvsnInfo.trafficDiverted = false;
		try {
			dfAppRoot.dvsnInfosRepo.setRow(dvsnInfo.key, dvsnInfo.toRow());
		} catch (Throwable e) {
			log.error("Failed to record in DvsnInfo that traffic is no longer diverted " + dvsnInfo.key, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to end diversion of " + Mitigation.getPrintableMitigationTarget(dvsnInfo.mitigationKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	@Override
	public void handleFailedAMSs(List<String> failedAMSs) {

		Hashtable<String,Object> dvsnInfoRow;
		DvsnInfo dvsnInfo = null;
		try {
			Hashtable<String,Hashtable<String,Object>> dvsnInfoTable = dfAppRoot.dvsnInfosRepo.getTable();
			Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = dvsnInfoTable.entrySet().iterator();
			boolean dvsnInfoAffected; boolean hasActiveDvsnsForMitigation;

			while(iter.hasNext()) {

				dvsnInfoRow = iter.next().getValue();
				dvsnInfo = new DvsnInfo(dvsnInfoRow);
				dvsnInfoAffected = dvsnInfo.containsDvsnToAMSs(failedAMSs);
				if(!dvsnInfoAffected) continue;

				endDvsnPerDvsnInfo(dvsnInfo);

				/* Check if there are any active diversions left for this mitigation. If not notify not mitigating. */
				hasActiveDvsnsForMitigation = Mitigation.hasActiveDvsns(dvsnInfo.mitigationKey);
				if(!hasActiveDvsnsForMitigation) {
					decoupledEndMitigation(dvsnInfo.mitigationKey); // Remove all configurations related to this mitigation
					MitigationResponse mitigationResponse = new MitigationResponse(dvsnInfo.mitigationKey, false);
					String message = "Ending mitigation of " + Mitigation.getPrintableMitigationTarget(dvsnInfo.mitigationKey)
							+ " - no live netNodes left to divert traffic from.";
					fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, message);
					log.debug(message);
					invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse);
				}
			}
		}  catch (Throwable e) {
			String msg = "Failed to properly handle handleFailedAMSs";
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
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
		case ACTION_ADD_NETNODE:
			decoupledAddNetNode((String) param);
			break;
		case ACTION_ADD_PN:
			decoupledAddPN((String) param);
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		case ACTION_NETNODE_DOWNED:
			decoupledNetNodeDowned((String) param);
			break;
		default:
			break;
		}
	}
}
