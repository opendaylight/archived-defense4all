/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.odl.controller.Connector;
import org.opendaylight.defense4all.odl.controller.FlowEntryMgr;

public class Odl {

	/* Default ofc_hostname and restpath_controller_name values */
	public static final String DEFAULT_RESTPATH_CONTROLLER_NAME = "default";
	
	/**
	 * Name space allocation of DF NEC Repo minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
		FLOW_CONFIG_INFO,
		TRAFFIC_FLOOR,
	}
	
	protected boolean initialized = false;
	protected boolean cleanedUp = false;
	public FrameworkMain fm = null;
	public DFAppRoot dfAppRoot = null;
	public String constFlowUrlPrefix;
	public String constStatsUrlPrefix;
	public String constSwitchUrlPrefix;
	protected Logger log = LoggerFactory.getLogger("odl");	
	protected static Hashtable<String,Connector> connectors = new Hashtable<String,Connector>();
	protected FlowEntryMgr flowEntryMgr;	
	public Hashtable<String,OdlFlowConfigInfo> odlFlowConfigs;	
	protected HashMap<Short,Object> flowCookies;
	
	protected Odl() {
		odlFlowConfigs = new Hashtable<String,OdlFlowConfigInfo>();
		flowCookies = new HashMap<Short,Object>();
	}
	
	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {fm = frameworkMain;}
	public void setDfAppRoot(DFAppRoot dfAppRoot) {this.dfAppRoot = dfAppRoot;}
	public void setFlowEntryMgr(FlowEntryMgr flowEntryMgr) {this.flowEntryMgr = flowEntryMgr;}
	public void setConstFlowUrlPrefix(String constFlowUrlPrefix) {this.constFlowUrlPrefix = constFlowUrlPrefix;}
	public void setConstStatsUrlPrefix(String constStatsUrlPrefix) {this.constStatsUrlPrefix = constStatsUrlPrefix;}
	public void setConstSwitchUrlPrefix(String constSwitchUrlPrefix) {this.constSwitchUrlPrefix=constSwitchUrlPrefix;}

	public synchronized void init() throws ExceptionControlApp {
		
		if(initialized) return;
		
		/* Loop through the flowConfigInfos to record all used cookies and to inflate flowConfigInfos hashmap */
		Hashtable<String,Hashtable<String,Object>> flowConfigsTable = dfAppRoot.flowConfigInfosRepo.getTable();
		OdlFlowConfigInfo flowConfigInfo;
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = flowConfigsTable.entrySet().iterator();
		while(iter.hasNext()) {
			flowConfigInfo = new OdlFlowConfigInfo(iter.next().getValue());
			flowCookies.put((Short) flowConfigInfo.id, null);
			odlFlowConfigs.put(flowConfigInfo.key, flowConfigInfo);
		}
		
		initialized = true;
	}

	public synchronized void finit() {
		if(cleanedUp) return;
		cleanedUp = true;
		// Clean up
	}

	public synchronized void reset(ResetLevel resetLevel) {

		/* Clear all caches */
		odlFlowConfigs.clear();
		flowCookies.clear();
		
		flowEntryMgr.reset();
	}

	/**
	 * 
	 * @param ofcKey
	 */
	public synchronized void initConnectionToOFC(String ofcKey) {
		
		DFAppRoot dfAppRoot = DFHolder.get();
		Hashtable<String,Object> ofcRow = dfAppRoot.oFCsRepo.getRow(ofcKey);
		OdlOFC odlOfc = new OdlOFC(ofcRow);
		dfAppRoot.oFCsRepo.setRow(ofcKey, odlOfc.toRow());		// Record the extra fields OdlOFC adds to row.
		
		String hostname = odlOfc.hostname;
		if(hostname == null || hostname.isEmpty()) hostname = odlOfc.ipAddrString;
		
		Connector connector = connectors.get(hostname);
		if(connector == null) {
			connector = new Connector(odlOfc);
			connector.init();
			connectors.put(hostname, connector);
			if(flowEntryMgr.connector == null) 
				flowEntryMgr.connector = connector; // TODO: in the future need to maintain flowEntryMgr per connector/OFC
		}
	}

	/**
	 * Retrieve the topology from the controller and populate it into the working objects (WOs).
	 * @param ofcKey
	 */
	public synchronized void retrieveTopology(String ofcKey) {
	}
	
	protected short getUniqueCookie() {
		short min = OdlFlowConfigInfo.ODL_COOKIE_MIN;
		short max = OdlFlowConfigInfo.ODL_COOKIE_MAX;
		short seqnum = min;
		for(int i=0;i<10000 /* some big number */;i++) {
			seqnum = (short) ((min + (short) ((Math.random() * (max-min)) + 1)));
			if(!flowCookies.containsKey(seqnum)) break;
		}
		flowCookies.put(seqnum, null);
		return seqnum;
	}

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeTrafficFloor(String trafficFloorKey) {

		Hashtable<String,Object> trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
		TrafficFloor trafficFloor = new TrafficFloor(trafficFloorRow);

		Iterator<Map.Entry<String,Object>> iter = trafficFloor.flowConfigInfoKeys.entrySet().iterator();
		String flowConfigInfoKey;
		while(iter.hasNext()) {
			flowConfigInfoKey = iter.next().getKey();		
			try {
				flowEntryMgr.deleteOpenFlowEntry(trafficFloor.nodeLabel, flowConfigInfoKey);
			} catch (Exception e) { /* Ignore */}			
			dfAppRoot.flowConfigInfosRepo.deleteRow(flowConfigInfoKey);
			odlFlowConfigs.remove(flowConfigInfoKey);
		}

		dfAppRoot.trafficFloorsRepo.deleteRow(trafficFloorKey);
	}

	public void test(Properties props) {
	}
}
