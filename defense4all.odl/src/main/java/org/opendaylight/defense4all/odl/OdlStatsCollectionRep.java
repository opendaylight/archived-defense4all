/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNode.PortDirection;
import org.opendaylight.defense4all.core.NetNode.ProtectedLink;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.core.NetNode.Status;
import org.opendaylight.defense4all.core.NetNode.TrafficPort;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.StatsCollectionRep;
import org.opendaylight.defense4all.core.StatsCountersPlacement;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.odl.pojos.FlowStat;
import org.opendaylight.defense4all.odl.pojos.FlowStatistics;
import org.opendaylight.defense4all.odl.pojos.SentFlowConfig;
import org.opendaylight.defense4all.odl.pojos.FlowConfig.ActionType;


public class OdlStatsCollectionRep extends StatsCollectionRep {
	
	public Odl odl = null;
	Logger log = LoggerFactory.getLogger("OdlStatsCollectionRep");
	
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

	/** Reset */
	@Override
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
		odl.reset(resetLevel);
	}

	/**
	 * Offer all possible placements of stats counters that monitor the traffic to protected network
	 * denoted by the passed in pnKey param.
	 * 
	 * @param param_name param description
	 * @return return An array of all possible placements of stats counters that monitor the subject traffic. Each placement contains a set of
	 * stats counter locations in the OF network and a common QoS specification in such a placement. QoS degrading factors can be aggregating counters
	 * of multiple traffics into a single one monitoring only the sum of all traffics, time sharing counter placement, Qos degradation to other 
	 * traffics monitoring (if location is preempted by these counters).
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public List<StatsCountersPlacement> offerCounterPlacements(String pnKey) throws ExceptionControlApp {

		pnOdlSpecific(pnKey);
		
		ArrayList<String> statsCounterLocs = new ArrayList<String>();
		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		OdlPN odlPn = null;
		try {
			odlPn = new OdlPN(pnRow);
		} catch (UnknownHostException e) {return null;}
		
		for(String netNodeLabel : odlPn.netNodeLabels) {
			Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeLabel);
			if(netNodeRow == null) continue; // NetNode has not been defined to the application
			Status netNodeStatus = Status.valueOf((String)  netNodeRow.get(NetNode.STATUS));
			if(netNodeStatus != NetNode.Status.ACTIVE) continue; // NetNode is not ready yet, or is being removed
			statsCounterLocs.add(netNodeLabel);
		}
		
		
		StatsCountersPlacement statsCountersPlacement = new StatsCountersPlacement(statsCounterLocs, null);		
		ArrayList<StatsCountersPlacement> statsCounterPlacements = new ArrayList<StatsCountersPlacement>();
		statsCounterPlacements.add(statsCountersPlacement);
		
		return statsCounterPlacements;
	}
	
	/* Retrieve vtn_name and north-bound vexternals and add to protected network row in repo. */
	protected void pnOdlSpecific(String pnKey) throws ExceptionControlApp {		
		
		Properties props = (Properties) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.PROPS);		
		Iterator<Map.Entry<Object,Object>> iter = props.entrySet().iterator();
		Map.Entry<Object,Object> entry; String key; String value; 
		Hashtable<String,Object> cells = new Hashtable<String,Object>();
		
		while(iter.hasNext()) {
			entry = iter.next();
			key = (String) entry.getKey();
			if(key.startsWith(OdlPN.NETNODE_PREFIX)) {
				value = (String) entry.getValue();
				cells.put(OdlPN.NETNODE_PREFIX + value, value);
			}
		}
		dfAppRoot.pNsRepo.setRow(pnKey, cells); // Persist the pnRow added cells
	}

	/**
	 * Initialize connectivity to the specified OFC.
	 */
	@Override
	protected void initConnectionToOFC(String ofcKey) {
		odl.initConnectionToOFC(ofcKey);
		odl.retrieveTopology(ofcKey);
		notifyTopologyChanged();
	}

	/**
	 * ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(String netNodeKey) {
		notifyTopologyChanged();
	}

	/**
	 * ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeNetNode(String netNodeLabel) {
		notifyTopologyChanged();
	}

	/**
	 * Just retrieve the entire topology.
 	 * This operation is synchronous.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	protected void retrieveTopologyChanges(String ofcKey) {
	}

	/**
	 * Set counter in OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public String addPeacetimeCounterTrafficFloor(String pnKey, String location) throws Exception {

		/* Create the trafficFloor object and set it in odlTrafficFloorRepo */
		TrafficFloor trafficFloor = new TrafficFloor();
		trafficFloor.pnKey = pnKey; trafficFloor.nodeLabel = location;
		trafficFloor.nodeId = (String) dfAppRoot.netNodesRepo.getCellValue(location, NetNode.ID);
		trafficFloor.floor = TrafficFloor.FLOOR_PEACETIME_START;
		String trafficFloorKey = trafficFloor.generateAndSetKey();

		/* Add counter to its location (location == netNode label) */
		
		Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(location);
		NetNode netNode = new NetNode(netNodeRow);
		
		try {
			
			OdlFlowConfigInfo configInfo = new OdlFlowConfigInfo();
			configInfo.nodeLabel = location; configInfo.etherType = 2400; 
			configInfo.nwDst = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DST_ADDR);
			configInfo.actions = new ArrayList<String>();

			if(netNode.sdnNodeMode == SDNNodeMode.SDN_ENABLED_HYBRID) { // Loop over all netnode traffic ports
				
				configInfo.actions.add(ActionType.HW_PATH.name());	// "Send to normal" (non-OF routing)				
				Iterator<Map.Entry<String,TrafficPort>> iter = netNode.trafficPorts.entrySet().iterator();
				TrafficPort trafficPort; OdlFlowConfigInfo configInfoClone;		
				
				while(iter.hasNext()) {		
					
					trafficPort = iter.next().getValue();
					if(trafficPort.direction != PortDirection.INBOUND) continue;
					configInfoClone = new OdlFlowConfigInfo(configInfo);
					configInfoClone.ingressPort = trafficPort.number;
					setFourFlowEntries(configInfoClone, trafficFloor, trafficFloor.floor);
				}
			} else { // SDNNodeMode.SDN_ENABLED_NATIVE - Loop over all netnode protected links

				Iterator<Map.Entry<String,ProtectedLink>> iter = netNode.protectedLinks.entrySet().iterator();
				ProtectedLink protectedLink; OdlFlowConfigInfo configInfoClone; String action;
				
				while(iter.hasNext()) {
					
					protectedLink = iter.next().getValue();
					configInfoClone = new OdlFlowConfigInfo(configInfo);
					configInfoClone.id = odl.getUniqueCookie();
					configInfoClone.ingressPort = protectedLink.inboundPort;
					action = ActionType.OUTPUT.name() + "=" + protectedLink.outboundPort; // Send to matching out port
					configInfoClone.actions.add(action);
					setFourFlowEntries(configInfoClone, trafficFloor, trafficFloor.floor);
				}
			}			
		} catch (Exception e) {
			System.out.println("exception adding flowEntry: " + e.getLocalizedMessage());
		}

		dfAppRoot.trafficFloorsRepo.setRow(trafficFloorKey, trafficFloor.toRow());
		return trafficFloorKey;
	}

	/* Generate key, install the 4 flow entries in the netnode, record them in repo and in their traffic floor */	
	protected void setFourFlowEntries(OdlFlowConfigInfo configInfo, TrafficFloor trafficFloor, short floor) 
			throws Exception {
		setFlowEntry(configInfo, trafficFloor, (short) 6, (short) (floor + TrafficFloor.FLOOR_TCP_PRIORITY));
		setFlowEntry(configInfo, trafficFloor, (short) 17,(short) (floor + TrafficFloor.FLOOR_UDP_PRIORITY));
		setFlowEntry(configInfo, trafficFloor, (short) 1, (short) (floor + TrafficFloor.FLOOR_ICMP_PRIORITY));
		setFlowEntry(configInfo, trafficFloor, (short) 0, (short) (floor + TrafficFloor.FLOOR_OTHER_PRIORITY));
	}

	/* set protocol and key, install the flow entry in the netnode, record in repo and in its traffic floor */	
	protected void setFlowEntry(OdlFlowConfigInfo configInfo, TrafficFloor floor, short protocol, short priority) 
			throws Exception {

		configInfo.protocol = protocol;
		configInfo.forRates = true;	configInfo.forTrafficLearning = true;	// Stats collection flow entry
		configInfo.generateAndSetKey();
		configInfo.floor = priority;
		SentFlowConfig fe = new SentFlowConfig(configInfo);
		odl.flowEntryMgr.addOpenFlowEntry((String) fe.node.id, fe.name, fe);
		Hashtable<String,Object> configInfoRow = configInfo.toRow();
		dfAppRoot.flowConfigInfosRepo.setRow(configInfo.key, configInfoRow);
		OdlFlowConfigInfo flowConfigInfo = new OdlFlowConfigInfo(configInfoRow);
		odl.odlFlowConfigs.put(configInfo.key, flowConfigInfo);
		floor.flowConfigInfoKeys.put(configInfo.key, null);
	}

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeTrafficFloor(String trafficFloorKey) {
		odl.removeTrafficFloor(trafficFloorKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public TrafficTuple getStats(String trafficFloorKey) {
		
		FlowStatistics flowStatistics = null;
		Hashtable<String,Object> trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
		TrafficFloor trafficFloor = new TrafficFloor(trafficFloorRow);
		try {
			flowStatistics = odl.flowEntryMgr.getOpenFlowStats(trafficFloor.nodeId);
			if(flowStatistics == null) return null;
		} catch (Exception e) { return null;}
		
		String flowEntryId; OdlFlowConfigInfo fi; 
		TrafficTuple stats = new TrafficTuple();
		
		for(FlowStat flowStat: flowStatistics.flowStatistic) {
			
			flowEntryId = String.valueOf(flowStat.flow.id);
			if(!trafficFloor.flowConfigInfoKeys.containsKey(flowEntryId)) continue; // flowEntry not of this floor
			
			fi = odl.odlFlowConfigs.get(String.valueOf(flowEntryId));
			if(fi == null) return null;	// Internal inconsistency
			
			if(!fi.forRates) continue; // The flow entry is not for stats. (Can be for diversion).
			
			stats.setTrafficData(fi.protocol, Integer.valueOf(fi.tpDst), flowStat.byteCount, 
					flowStat.packetCount, fi.forTrafficLearning, fi.direction);
		}
		
		return stats;
	}

	@Override
	public String getTrafficFloorLocation(String trafficFloorKey) {
		String node = (String) dfAppRoot.trafficFloorsRepo.getCellValue(trafficFloorKey, TrafficFloor.NODE_LABEL);
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
