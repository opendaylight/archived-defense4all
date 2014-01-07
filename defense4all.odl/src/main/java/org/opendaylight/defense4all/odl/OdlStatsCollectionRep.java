package org.opendaylight.defense4all.odl;
/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.core.NetNode.Status;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.ProtectedLink;
import org.opendaylight.defense4all.core.StatsCollectionRep;
import org.opendaylight.defense4all.core.StatsCountersPlacement;
import org.opendaylight.defense4all.core.Traffic.TrafficDirection;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.core.TrafficPort;
import org.opendaylight.defense4all.core.TrafficPort.PortLocation;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.odl.pojos.FlowConfigNoProtocol.ActionType;
import org.opendaylight.defense4all.odl.pojos.FlowStat;
import org.opendaylight.defense4all.odl.pojos.FlowStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OdlStatsCollectionRep extends StatsCollectionRep {

	public Odl odl = null;
	Logger log = LoggerFactory.getLogger(this.getClass());

	/* Constructor for Spring */
	public OdlStatsCollectionRep() {
		super();
	}

	/* Setters for Spring */
	public void setOdl(Odl odl) {this.odl = odl;}

	/** Post-constructor initialization	 */
	@Override
	public void init() throws ExceptionControlApp {	
		super.init();
		odl.init();
	}

	/** Pre-shutdown cleanup */
	@Override
	public void finit() {
		super.finit();
		odl.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	@Override
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		
		super.reset(resetLevel);
		
		odl.reset(resetLevel);
	}

	/**
	 * Offer all possible placements of stats counters that monitor the traffic to protected network denoted
	 * by the passed in pnKey param.
	 * 
	 * @param param_name param description
	 * @return return An array of all possible placements of stats counters that monitor the subject traffic. 
	 * Each placement contains a set of stats counter locations in the OF network and a common QoS specification 
	 * in such a placement. QoS degrading factors can be aggregating counters of multiple traffics into a single 
	 * one monitoring only the sum of all traffics, time sharing counter placement, Qos degradation to other 
	 * traffics monitoring (if location is preempted by these counters).
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public List<StatsCountersPlacement> offerCounterPlacements(String pnKey) throws ExceptionControlApp {

		pnOdlSpecific(pnKey);

		ArrayList<String> statsCounterLocs = new ArrayList<String>();
		PN pn = null;
		try {
			Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
			pn = new PN(pnRow);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to inflate PN " + pnKey, e);
			throw e;
		}

		for(String netNodeLabel : pn.netNodeLabels) {
			Hashtable<String, Object> netNodeRow;
			try {
				netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeLabel);
			} catch (ExceptionControlApp e) {
				log.error("Failed to retrieve netNode row for " + netNodeLabel);
				continue;
			}
			if(netNodeRow == null) continue; // NetNode has not been defined to the application
			Status netNodeStatus = Status.valueOf((String) netNodeRow.get(NetNode.STATUS));
			if(netNodeStatus != NetNode.Status.ACTIVE) continue; // NetNode is not ready yet, or is being removed
			statsCounterLocs.add(netNodeLabel);
		}		

		StatsCountersPlacement statsCountersPlacement = new StatsCountersPlacement(statsCounterLocs, null);		
		ArrayList<StatsCountersPlacement> statsCounterPlacements = new ArrayList<StatsCountersPlacement>();
		statsCounterPlacements.add(statsCountersPlacement);

		StringBuilder sb = new StringBuilder();
		sb.append("OdlStatsCollectionRep offers to place stats counters for "); sb.append(pnKey); sb.append(" at - ");
		for(StatsCountersPlacement placement : statsCounterPlacements) {
			sb.append(placement.toString()); sb.append(", ");
		}
		sb.setLength(sb.length() - 2); // Remove the last ", "
		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, sb.toString());
		return statsCounterPlacements;
	}

	protected void pnOdlSpecific(String pnKey) throws ExceptionControlApp {		

		Properties props;
		try {
			
			props = (Properties) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.PROPS);
		} catch (Throwable e) {
			log.error("Excepted trying to retrieve props from which to construct netNodes for " + pnKey, e);
			return;
		}		
		Iterator<Map.Entry<Object,Object>> iter = props.entrySet().iterator();
		Map.Entry<Object,Object> entry; String key; String value; 
		Hashtable<String,Object> cells = new Hashtable<String,Object>();

		while(iter.hasNext()) {
			entry = iter.next();
			key = (String) entry.getKey();
			if(key.startsWith(PN.NETNODE_PREFIX)) {
				value = (String) entry.getValue();
				if(value != null) cells.put(PN.NETNODE_PREFIX + value, value);
			}
		}
		dfAppRoot.pNsRepo.setRow(pnKey, cells); // Persist the pnRow added cells
	}

	/**
	 * Initialize connectivity to the specified OFC.
	 * @throws ExceptionControlApp 
	 */
	@Override
	protected void initConnectionToOFC(String ofcKey) throws ExceptionControlApp {
		odl.initConnectionToOFC(ofcKey);
		odl.retrieveTopology(ofcKey);
		notifyTopologyChanged();
	}

	/**
	 * ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(String netNodeKey) throws ExceptionControlApp {
		odl.addNetNode(netNodeKey);
		notifyTopologyChanged();
	}

	/**
	 * ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeNetNode(String netNodeKey) throws ExceptionControlApp {
		odl.removeNetNode(netNodeKey);
		notifyTopologyChanged();
	}

	/**
	 * Set counter in OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public String addPeacetimeCounterTrafficFloor(String pnKey, String location) throws ExceptionControlApp {

		TrafficFloor trafficFloor; NetNode netNode; OdlFlowConfigInfo configInfoCommon; 
		OdlFlowConfigInfo configInfoClone; boolean success = true; String trafficFloorKey;
		Hashtable<String,Object> netNodeRow;

		try {
			netNodeRow = dfAppRoot.netNodesRepo.getRow(location);
			if(netNodeRow == null || NetNode.isRemoved(location)) {
				log.warn("Location (NetNode) " + location + " specified for PN " + pnKey + " is not known to Defense4All");
				return null;
			}
		} catch (ExceptionControlApp e1) {
			String msg = "Excepted most likely trying to retrieve elements from repos for " + pnKey + " " + location;
			log.error(msg, e1);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to add peacetime counter traffic floor for " + pnKey
					+ " at " + location);
			throw new ExceptionControlApp(msg, e1);
		}		
		
		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "Adding peacetime counter traffic floor for " + pnKey
				+ " at " + location);

		try {
			/* Create the trafficFloor object */
			trafficFloor = new TrafficFloor();
			trafficFloor.pnKey = pnKey; trafficFloor.nodeLabel = location;
			trafficFloor.nodeId = (String) netNodeRow.get(NetNode.ID);
			trafficFloor.floorBase = TrafficFloor.FLOOR_PEACETIME_START;
			trafficFloorKey = trafficFloor.generateAndSetKey();

			/* Check if we already added this counter. Can be if the application is restarted and PN is reintroduced.
			 * In such a case the counter is already installed in the location. */
			Hashtable<String,Object> trafficFloorRow;
			trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
			if(trafficFloorRow != null) return trafficFloorKey;

			/* Add counter to its location (location == netNode label) */

			netNode = new NetNode(netNodeRow);

			configInfoCommon = new OdlFlowConfigInfo();
			configInfoCommon.nodeLabel = location; configInfoCommon.etherType = 2048; 
			configInfoCommon.nwDst = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DST_ADDR);
			configInfoCommon.actions = new ArrayList<String>();
			configInfoCommon.forRates = true; configInfoCommon.forTrafficLearning = true;	// Stats collection flow entry
			configInfoCommon.direction = TrafficDirection.INBOUND;

		} catch (ExceptionControlApp e1) {
			String msg = "Excepted most likely trying to retrieve elements from repos for " + pnKey + " " + location;
			log.error(msg, e1);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to add peacetime counter traffic floor for " + pnKey
					+ " at " + location);
			throw new ExceptionControlApp(msg, e1);
		}

		if(netNode.sdnNodeMode == SDNNodeMode.sdnenabledhybrid) { // Loop over all netnode traffic ports

			configInfoCommon.actions.add(ActionType.HW_PATH.name());	// "Send to normal" (non-OF routing)
			if(netNode.trafficPorts == null) {
				String msg = "Got null trafficPorts in netNode " + location;
				log.error(msg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp(msg);
			}
			Iterator<Map.Entry<String,TrafficPort>> iter = netNode.trafficPorts.entrySet().iterator();
			TrafficPort trafficPort; 	

			while(iter.hasNext()) {

				trafficPort = iter.next().getValue();
				if(trafficPort.location!= PortLocation.north) continue;

				configInfoClone = new OdlFlowConfigInfo(configInfoCommon);
				configInfoClone.ingressPort = trafficPort.number;

				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Setting four flow entries for " + pnKey
					+ " at SDN hybrid NetNode " + location + ", traffic port " + trafficPort.label);
				success &= setFourFlowEntries(configInfoClone, trafficFloor, trafficFloor.floorBase);
				
				String msg = "setting flow entries for "+pnKey+" at NetNode "+location+", traffic port "+trafficPort.label;
				if(!success)
					fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + msg);					
			}
		} else { // SDNNodeMode.sdnenablednative - Loop over all netnode protected links

			Iterator<Map.Entry<String,ProtectedLink>> iter = netNode.protectedLinks.entrySet().iterator();
			ProtectedLink protectedLink; String action;

			while(iter.hasNext()) {

				protectedLink = iter.next().getValue();

				configInfoClone = new OdlFlowConfigInfo(configInfoCommon);
				configInfoClone.id = odl.getUniqueCookie();
				configInfoClone.ingressPort = protectedLink.northPort;
				action = ActionType.OUTPUT.name() + "=" + protectedLink.southPort; // Send to matching out port
				configInfoClone.actions.add(action);

				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Setting four flow entries for " + pnKey
					+ " at SDN native NetNode " + location + ", north port of protected link " + protectedLink.label);
				success &= setFourFlowEntries(configInfoClone, trafficFloor, trafficFloor.floorBase);
				String msg="setting flow entries for "+pnKey+" at NetNode "+location+", north port of "+protectedLink.label;
				if(!success)
					fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + msg);
			}
		}		

		dfAppRoot.trafficFloorsRepo.setRow(trafficFloorKey, trafficFloor.toRow());
		if(!success) {
			odl.removeTrafficFloor(trafficFloor);
			return null;
		}
		return trafficFloorKey;
	}

	/* Generate key, install the 4 flow entries in the netnode, record them in repo and in their traffic floor */	
	protected boolean setFourFlowEntries(OdlFlowConfigInfo configInfo, TrafficFloor trafficFloor, short floor) {

		boolean success; int tcpId; String tcpKey;	int udpId; String udpKey;	int icmpId; String icmpKey;
		success = setFlowEntry(configInfo, trafficFloor, (short) 6, (short) (floor + TrafficFloor.FLOOR_TCP_PRIORITY));
		if(!success) {
			return false;
		}
		tcpId = configInfo.id; tcpKey = configInfo.key;
		success &= setFlowEntry(configInfo, trafficFloor, (short) 17,(short) (floor + TrafficFloor.FLOOR_UDP_PRIORITY));
		if(!success) {
			try {
				odl.deleteOpenFlowEntry(Integer.toString(tcpId), tcpKey);
			} catch (ExceptionControlApp e) {
				String msg = "Failed to delete flow entries during rollback for " + configInfo.nodeLabel + " " + floor;
				log.error(msg, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, msg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			return false;
		}
		udpId = configInfo.id; udpKey = configInfo.key;
		success &= setFlowEntry(configInfo, trafficFloor, (short) 1, (short) (floor + TrafficFloor.FLOOR_ICMP_PRIORITY));
		if(!success) {
			try {
				odl.deleteOpenFlowEntry(Integer.toString(tcpId), tcpKey);
				odl.deleteOpenFlowEntry(Integer.toString(udpId), udpKey);
			} catch (ExceptionControlApp e) {
				String msg = "Failed to delete flow entries during rollback for " + configInfo.nodeLabel + " " + floor;
				log.error(msg, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, msg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			return false;
		}
		icmpId = configInfo.id; icmpKey = configInfo.key;
		success &= setFlowEntry(configInfo, trafficFloor, (short) 0, (short) (floor + TrafficFloor.FLOOR_OTHER_PRIORITY));
		if(!success) {
			try {
				odl.deleteOpenFlowEntry(Integer.toString(tcpId), tcpKey);
				odl.deleteOpenFlowEntry(Integer.toString(udpId), udpKey);
				odl.deleteOpenFlowEntry(Integer.toString(icmpId), icmpKey);
			} catch (ExceptionControlApp e) {
				String msg = "Failed to delete flow entries during rollback for " + configInfo.nodeLabel + " " + floor;
				log.error(msg, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, msg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			return false;
		}
		return true;
	}

	/* set protocol and key, install the flow entry in the netnode, record in repo and in its traffic floor */	
	protected boolean setFlowEntry(OdlFlowConfigInfo configInfo, TrafficFloor trafficFloor, short protocol, short priority) {

		configInfo.protocol = protocol; configInfo.floor = priority;
		configInfo.id = odl.getUniqueCookie();
		configInfo.generateAndSetKey();

		boolean success = odl.setFlowEntry(configInfo, trafficFloor);
		return success;
	}

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeTrafficFloor(String trafficFloorKey) throws ExceptionControlApp {
		odl.removeTrafficFloor(trafficFloorKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public TrafficTuple getStats(String trafficFloorKey) throws ExceptionControlApp {

		FlowStatistics flowStatistics = null;
		Hashtable<String, Object> trafficFloorRow; TrafficFloor trafficFloor;
		try {
			trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
			trafficFloor = new TrafficFloor(trafficFloorRow);
		} catch (ExceptionControlApp e1) {
			String msg = "Excepted trying to retrieve from repo traffic floor for " + trafficFloorKey;
			log.error(msg, e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e1);
		}
		try {
			flowStatistics = odl.flowEntryMgr.getOpenFlowStats(trafficFloor.nodeId);
			if(flowStatistics == null) return null;
		} catch (Exception e) { return null;}

		String flowEntryId; OdlFlowConfigInfo fi; int port;
		TrafficTuple stats = new TrafficTuple();		

		for(FlowStat flowStat: flowStatistics.flowStatistic) {

			try {
				flowEntryId = String.valueOf(flowStat.flow.id);
				if(!trafficFloor.flowConfigInfoKeys.containsKey(flowEntryId)) continue; // flowEntry not of this floor

				fi = odl.odlFlowConfigs.get(String.valueOf(flowEntryId));
				if(fi == null) return null;	// Internal inconsistency

				if(!fi.forRates) continue; // The flow entry is not for stats. (Can be for diversion).

				try {
					port = Integer.valueOf(fi.tpDst);
				} catch (Exception e) {port = 0;}
				stats.setTrafficData(fi.protocol, port, flowStat.byteCount, flowStat.packetCount, fi.forTrafficLearning, 
						fi.direction);
			} catch (Throwable e1) {
				log.error("Excepted in loop for flowStat " + flowStat.flow); // Ignore and continue
			}
		}

		return stats;
	}

	@Override
	public String getTrafficFloorLocation(String trafficFloorKey) throws ExceptionControlApp {
		String node = (String) dfAppRoot.trafficFloorsRepo.getCellValue(trafficFloorKey, TrafficFloor.NODE_LABEL);
		if(node == null) {
			log.error("Received null node from trafficFllorsRepo for key " + trafficFloorKey);
			throw new ExceptionControlApp("Received null node from trafficFllorsRepo for key " + trafficFloorKey);
		}
		return node;
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		super.actionSwitcher(actionCode, param);
	}

	public void test(Properties props) {
		odl.test(props);
	}
}
