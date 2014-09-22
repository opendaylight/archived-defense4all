/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.AMSConnection;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.FlowConfigInfo;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.ProtectedLink;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.odl.controller.Connector;
import org.opendaylight.defense4all.odl.controller.FlowEntryMgr;
import org.opendaylight.defense4all.odl.pojos.FlowConfigNoProtocol.ActionType;
import org.opendaylight.defense4all.odl.pojos.ReceivedFlowConfig;
import org.opendaylight.defense4all.odl.pojos.SentFlowConfig;
import org.opendaylight.defense4all.odl.pojos.SentFlowConfigNoProtocol;

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

	Logger log = LoggerFactory.getLogger(this.getClass());

	protected boolean initialized = false;
	protected boolean finited = false;
	public FrameworkMain fMain = null;
	public DFAppRoot dfAppRoot = null;
	public String constFlowUrlPrefix;
	public String constStatsUrlPrefix;
	public String constSwitchUrlPrefix;	
	protected static Hashtable<String,Connector> connectors = new Hashtable<String,Connector>();
	protected FlowEntryMgr flowEntryMgr;	
	public Hashtable<String,OdlFlowConfigInfo> odlFlowConfigs;	
	protected HashMap<Integer,Object> flowCookies;

	protected Odl() {
		odlFlowConfigs = new Hashtable<String,OdlFlowConfigInfo>();
		flowCookies = new HashMap<Integer,Object>();
	}

	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {fMain = frameworkMain;}
	public void setDfAppRoot(DFAppRoot dfAppRoot) {this.dfAppRoot = dfAppRoot;}
	public void setFlowEntryMgr(FlowEntryMgr flowEntryMgr) {this.flowEntryMgr = flowEntryMgr;}
	public void setConstFlowUrlPrefix(String constFlowUrlPrefix) {this.constFlowUrlPrefix = constFlowUrlPrefix;}
	public void setConstStatsUrlPrefix(String constStatsUrlPrefix) {this.constStatsUrlPrefix = constStatsUrlPrefix;}
	public void setConstSwitchUrlPrefix(String constSwitchUrlPrefix) {this.constSwitchUrlPrefix=constSwitchUrlPrefix;}

	public synchronized void init() throws ExceptionControlApp {

		if(initialized) return;

		/* Loop through the flowConfigInfos to record all used cookies and to inflate flowConfigInfos hashmap */
		Hashtable<String,Hashtable<String,Object>> flowConfigsTable = dfAppRoot.flowConfigInfosRepo.getTable();
		if(flowConfigsTable == null) {
			log.error("Failed to initialize ODL because got null flowConfigsTable from repo.");
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to initialize ODL because got null flowConfigsTable from repo.");
		}
		OdlFlowConfigInfo flowConfigInfo;
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = flowConfigsTable.entrySet().iterator();
		while(iter.hasNext()) {
			try {
				flowConfigInfo = new OdlFlowConfigInfo(iter.next().getValue());
			} catch (Exception e) {continue; /* Ignore this flow entry. */}
			flowCookies.put(flowConfigInfo.id, null);
			odlFlowConfigs.put(flowConfigInfo.key, flowConfigInfo);
		}

		initialized = true;
	}

	public synchronized void finit() {
		if(finited) return;
		finited = true;
		// Clean up
	}

	//	log.error("Excepted trying to inflate OdlFlowConfigInfo from row. ");
	//	FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
	//	throw new ExceptionControlApp("Excepted trying to inflate OdlFlowConfigInfo from row. ");

	public synchronized void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		/* This method must be idempotent since it can be invoked by both OdlStatsCollectionRep and
		 * OdlDvsnRep. It should be noted that in contrast to init or finit reset may be invoked
		 * multiple times in a Defense4All lifecycle, so a boolean indicated whether or not it has been
		 * already reset is irrelevant. */

		/* Remove all traffic floors. */
		List<String> trafficFloorKeys;
		try {
			trafficFloorKeys = dfAppRoot.trafficFloorsRepo.getKeys();
		} catch (ExceptionControlApp e) {
			log.error("Failed resetting because excepted retrieving traffic floor keys", e);
			throw e;
		}

		if(!trafficFloorKeys.isEmpty()) // Can be invoked more than once (by dvsnRep and statsCollectionRep)
			fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Resetting - removing all traffic floors");

		for(String trafficFloorKey : trafficFloorKeys) {
			try {
				removeTrafficFloor(trafficFloorKey);
			} catch (Exception e) {continue;}
		}

		/* Clear all caches */
		odlFlowConfigs.clear();
		flowCookies.clear();

		flowEntryMgr.reset();
	}

	/**
	 * 
	 * @param ofcKey
	 * @throws ExceptionControlApp 
	 */
	public synchronized void initConnectionToOFC(String ofcKey) throws ExceptionControlApp {

		OdlOFC odlOfc; String hostname; Connector connector; int i;

		fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Initializing connectivity to OFC " + ofcKey);

		/* Retrieve and construct OdlOFC, update its record with the extra cells. */
		try {
			DFAppRoot dfAppRoot = DFHolder.get();
			Hashtable<String, Object> ofcRow = dfAppRoot.oFCsRepo.getRow(ofcKey);
			odlOfc = new OdlOFC(ofcRow);
			dfAppRoot.oFCsRepo.setRow(ofcKey, odlOfc.toRow()); // Record the extra fields OdlOFC adds to row.
		} catch (Throwable e) {
			log.error("Failed to initConnectionToOFC " + ofcKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to init connectivity to OFC " + ofcKey);
			throw new ExceptionControlApp("Failed to initConnectionToOFC " + ofcKey, e);
		}

		hostname = odlOfc.hostname;
		if (hostname == null || hostname.isEmpty())
			hostname = odlOfc.ipAddrString;

		connector = connectors.get(hostname);
        connector = null;//TODO: this is since we support only one controller now.
        // using the cached connectors will cause a problem on removing (need to remove from the connectors map.

		if (connector == null) {

			for (i=0;i<3;i++) {
				try { 
					connector = new Connector(odlOfc);
					connector.init();
					break;
				} catch (Throwable e) {
					log.error("Failed to initConnectionToOFC " + ofcKey, e);
				}
			}

			if(i == 3) {
				try {
					dfAppRoot.oFCsRepo.deleteCells(ofcKey, odlOfc.getExtraCells());
				} catch (ExceptionControlApp e2) {/* Ignore. */}
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed to init connectivity to OFC " + ofcKey);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
				throw new ExceptionControlApp("Exhausted retrying to initConnectionToOFC  " + ofcKey);
			}

			connectors.put(hostname, connector);
		}

		if (flowEntryMgr.connector == null) {
			log.debug("Set odl OFC connector");
			flowEntryMgr.connector = connector; // TODO: in the future need to maintain flowEntryMgr per connector/OFC
		}

		fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Connected to OFC " + ofcKey);
	}

	/**
	 * Add netNode to known topology domain. 
	 * If it is a native OFS install lowest priority flow entries to connect ports of protected links, 
	 * so that unprotected traffic will flow seamlessly from input to output port.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addNetNode(String netNodeKey) throws ExceptionControlApp {
		// TODO: add checks and returns similar to NEC
		NetNode netNode; TrafficFloor trafficFloor;	String trafficFloorKey;

		try {
			/* Inflate the netNode object */
			Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeKey);
			netNode = new NetNode(netNodeRow);

			/* Check if this netNode is SDN native or hybrid. Need to install common floor only for native OFS (SDN native mode)*/
			if(netNode.sdnNodeMode != SDNNodeMode.sdnenablednative) return;

			if(netNode.protectedLinks == null) {
				throw new ExceptionControlApp("Null protectedLinks in netNode record for " + netNodeKey);
			}

			/* Construct the trafficFloor object */
			trafficFloor = new TrafficFloor();
			trafficFloor.pnKey = ""; trafficFloor.nodeLabel = netNode.label; 
			trafficFloor.nodeId = (String) dfAppRoot.netNodesRepo.getCellValue(netNode.label, NetNode.ID);
			trafficFloor.floorBase = TrafficFloor.FLOOR_COMMON_START; 
			trafficFloor.floorCurrentHeight = trafficFloor.floorBase;
			trafficFloorKey = trafficFloor.generateAndSetKey();
		} catch (Throwable e1) {
			log.error("Failed to addNetNode " + netNodeKey, e1);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to addNetNode " + netNodeKey, e1);
		}

		/* Check if we already added this netNode. Can be if the application is restarted and netNodes are
		 * reintroduced to it, and if both statsCollectionRep and dvsnRep are ODL, so both invoke this method. */
		Hashtable<String,Object> trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
		if(trafficFloorRow != null) return;

		/* Create and set common fields in configInfo template object */
		setProtectedLinks(netNode, trafficFloor);

		/* Block ARPs from AMS connectivity ports */
		setARPBlockingFromAMSs(netNode, trafficFloor);
	}

	/**
	 * Set flows to pass all non-monitored traffic through the protected links.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void setProtectedLinks(NetNode netNode, TrafficFloor trafficFloor) throws ExceptionControlApp {

		/* Create and set common fields in configInfo template object */
		OdlFlowConfigInfo configInfoTemplate = new OdlFlowConfigInfo();
		configInfoTemplate.nodeLabel = netNode.label; configInfoTemplate.etherType = 2048;
		configInfoTemplate.idleTimeout = 0;//TODO: was 300, probably to avoid leaving flows on the controller on d4a crush (in this case, the other flows' timeout should be set as well.); // TODO: validate that idleTimeout is indeed in seconds.
		configInfoTemplate.actions = new ArrayList<String>();

		Iterator<Map.Entry<String,ProtectedLink>> iter = netNode.protectedLinks.entrySet().iterator();
		ProtectedLink protectedLink; OdlFlowConfigInfo configInfo; String action; 
		List<OdlFlowConfigInfo> setFlowEntries = new ArrayList<OdlFlowConfigInfo>();

		fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL,"Adding general connectivity flow entries to "+netNode.label);

		try {

			while(iter.hasNext()) {

				protectedLink = iter.next().getValue();

				/* Set connectivity from north port to south port */
				configInfo = new OdlFlowConfigInfo(configInfoTemplate);
				configInfo.id = getUniqueCookie();
				configInfo.ingressPort = protectedLink.northPort; 
				configInfo.floor = trafficFloor.floorCurrentHeight++;
				action = ActionType.OUTPUT.name() + "=" + protectedLink.southPort; // Send to matching out port
				configInfo.actions.add(action);
				configInfo.generateAndSetKey();

				String msg = "setting flow entry in "+netNode.label+" for protected link north-to-south connectivity, id="
						+configInfo.id+", ingress port="+configInfo.ingressPort+"out port="+protectedLink.southPort;
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, msg);

				setFlowEntry(configInfo, trafficFloor);

				setFlowEntries.add(configInfo);

				/* Set connectivity from south port to north port */
				configInfo = new OdlFlowConfigInfo(configInfoTemplate);
				configInfo.id = getUniqueCookie();
				configInfo.ingressPort = protectedLink.southPort; 
				configInfo.floor = trafficFloor.floorCurrentHeight++;
				action = ActionType.OUTPUT.name() + "=" + protectedLink.northPort; // Send to matching out port
				configInfo.actions.add(action);
				configInfo.generateAndSetKey();

				msg = "setting flow entry in "+netNode.label+" for protected link south-to-north connectivity, id="
						+configInfo.id+", ingress port="+configInfo.ingressPort+"out port="+protectedLink.northPort;
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, msg);

				setFlowEntry(configInfo, trafficFloor);

				setFlowEntries.add(configInfo);
			}

			dfAppRoot.trafficFloorsRepo.setRow(trafficFloor.key, trafficFloor.toRow());
		} catch (Exception e) {
			try {
				for(OdlFlowConfigInfo flowEntry : setFlowEntries) {
					flowEntryMgr.deleteOpenFlowEntry(trafficFloor.nodeId, flowEntry.key);
				}
			} catch (Throwable e2) {
				log.error("Failed to cleanup (rollback) partially set base trafic floor flow entries for " + netNode.label, e2);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			String msg = "Failed to addNetNode - could not install some of base traffic floor flow entries" + netNode.label;
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, msg);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);
		}
	}

	/**
	 * Block ARPs from AMS connections to avoid ARP storms.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void setARPBlockingFromAMSs(NetNode netNode, TrafficFloor trafficFloor) throws ExceptionControlApp {

		/* Create and set common fields in configInfo template object */
		OdlFlowConfigInfo configInfoTemplate = new OdlFlowConfigInfo();
		configInfoTemplate.nodeLabel = netNode.label; configInfoTemplate.etherType = 2054;
		configInfoTemplate.protocol = -1;	// To indicate that the protocol field should not be serialized at all.
		configInfoTemplate.actions = new ArrayList<String>();
		String action = ActionType.DROP.name(); // Drop the packet
		configInfoTemplate.actions.add(action);

		Iterator<Map.Entry<String,AMSConnection>> iter = netNode.amsConnections.entrySet().iterator();
		AMSConnection amsConnection; OdlFlowConfigInfo configInfo;
		List<OdlFlowConfigInfo> setFlowEntries = new ArrayList<OdlFlowConfigInfo>();

		fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL,"Adding flow entries to block ARPs out of AMS ports in "
				+ netNode.label);		
		try {

			while(iter.hasNext()) {

				amsConnection = iter.next().getValue();

				/* Block ARPs from north AMS connectivity port */
				configInfo = new OdlFlowConfigInfo(configInfoTemplate);
				configInfo.id = getUniqueCookie();
				configInfo.floor = trafficFloor.floorCurrentHeight++;
				configInfo.ingressPort = Short.valueOf(amsConnection.netNodeNorthPort); 
				configInfo.generateAndSetKey();

				String msg = "setting flow entry in "+netNode.label+" to block ARPs from north AMS connectivity port, id="
						+ configInfo.id + ", ingress port=" + configInfo.ingressPort;
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, msg);

				setFlowEntry(configInfo, trafficFloor);

				setFlowEntries.add(configInfo);

				/* Block ARPs from south AMS connectivity port */
				configInfo = new OdlFlowConfigInfo(configInfoTemplate);
				configInfo.id = getUniqueCookie();
				configInfo.floor = trafficFloor.floorCurrentHeight++;
				configInfo.ingressPort = Short.valueOf(amsConnection.netNodeSouthPort);
				configInfo.generateAndSetKey();

				msg = "setting flow entry in "+netNode.label+" to block ARPs from south AMS connectivity port, id="
						+ configInfo.id + ", ingress port=" + configInfo.ingressPort;
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, msg);

				setFlowEntry(configInfo, trafficFloor);

				setFlowEntries.add(configInfo);
			}

			dfAppRoot.trafficFloorsRepo.setRow(trafficFloor.key, trafficFloor.toRow());
		} catch (Exception e) {
			try {
				for(OdlFlowConfigInfo flowEntry : setFlowEntries) {
					flowEntryMgr.deleteOpenFlowEntry(trafficFloor.nodeId, flowEntry.key);
				}
			} catch (Throwable e2) {
				log.error("Failed to cleanup (rollback) partially set base trafic floor flow entries for "+netNode.label,e2);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			} finally {
                String msg = "Failed to addNetNode - could not install some of base traffic floor flow entries"+netNode.label;
                log.error(msg, e);
                fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, msg);
                FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
                throw new ExceptionControlApp(msg, e);
            }
		}
	}

	/**
	 * Remove netNode from known topology domain. 
	 * If it is a native OFS install lowest priority flow entries to connect ports of protected links, 
	 * so that unprotected traffic will flow seamlessly from input to output port.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removeNetNode(String netNodeKey) throws ExceptionControlApp {
		// TODO: add checks and returns similar to NEC
		try {
			/* Remove the common traffic floor */
			String netNodeLabel = (String) dfAppRoot.netNodesRepo.getCellValue(netNodeKey, NetNode.LABEL);
			String trafficFloorKey = TrafficFloor.generateAndSetKey(netNodeLabel, "", TrafficFloor.FLOOR_COMMON_START);
			fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "removing base traffic floor for " + netNodeKey);
			removeTrafficFloor(trafficFloorKey);
		} catch (Throwable e1) {
			log.error("Failed to removeNetNode " + netNodeKey, e1);
			fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed removing base traffic floor for "+netNodeKey);			
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to removeNetNode " + netNodeKey, e1);
		}
	}

	/**
	 * Retrieve the topology from the controller and populate it into the working objects (WOs).
	 * @param ofcKey
	 */
	public synchronized void retrieveTopology(String ofcKey) {}

	protected int getUniqueCookie() {

		int min = OdlFlowConfigInfo.ODL_COOKIE_MIN;
		int max = OdlFlowConfigInfo.ODL_COOKIE_MAX;
		int seqnum = min;
		for(int i=0;i<10000 /* some big number */;i++) {
			seqnum = (int) ((min + (Math.random() * (max-min)) + 1));
			if(!flowCookies.containsKey(seqnum)) break;
		}		
		flowCookies.put(seqnum, null);		
		return seqnum;
	}

	/**
	 * Install the flow entry in the netnode, record in repo and in its traffic floor 
	 * If it is a native OFS install lowest priority flow entries to connect ports of protected links, 
	 * so that unprotected traffic will flow seamlessly from input to output port.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public boolean setFlowEntry(OdlFlowConfigInfo configInfo, TrafficFloor trafficFloor) {

		int i=0; String exceptionMsg;
		SentFlowConfig fe = null; SentFlowConfigNoProtocol feNoProtocol = null;
		String nodeId = null; String feName = null;
		for(;i<3;i++) {
			try {
				if(configInfo.protocol != -1) { // IP traffic filter flow entry (TCP, UDP, ICMP, other IP)
					fe = new SentFlowConfig(configInfo);
					nodeId = (String) fe.node.id; feName = fe.name;
					flowEntryMgr.addOpenFlowEntry(nodeId, feName, fe);
				} else {	// Non IP traffic filter flow entry (e.g., ARP)
					feNoProtocol = new SentFlowConfigNoProtocol(configInfo);
					nodeId = (String) feNoProtocol.node.id; feName = feNoProtocol.name;
					flowEntryMgr.addOpenFlowEntry(nodeId, feName, feNoProtocol);					
				}
				break;
			} catch (Throwable e) {
				/* Check if the entry by any chance exists in the netNode already. Otherwise log error and retry*/
				try {
					ReceivedFlowConfig flowConfig = flowEntryMgr.getOpenFlowEntry(nodeId, feName);
					if(flowConfig != null) break; // The entry already exists in the netNode
				} catch (Exception e2) {/* Ignore */}				
				exceptionMsg = "Failed to either construct flow entry object or install the flow entry in netNode " + 
						configInfo.key + " " + trafficFloor.key;
				log.error(exceptionMsg, e);
			}
		}
		if(i == 3) { // Exhausted retry attempts
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return false;			
		}

		boolean setInFlowConfigInfosRepo = false; boolean setInOdlFlowConfigs = false;
		try {
			dfAppRoot.flowConfigInfosRepo.setRow(configInfo.key, configInfo.toRow());
			setInFlowConfigInfosRepo = true;
			OdlFlowConfigInfo flowConfigInfo = new OdlFlowConfigInfo(configInfo);
			odlFlowConfigs.put(configInfo.key, flowConfigInfo);
			setInOdlFlowConfigs = true;
			trafficFloor.flowConfigInfoKeys.put(configInfo.key, null);

		} catch (Exception e) {

			try {	// Try to delete the flow entry in NetNode
				flowEntryMgr.deleteOpenFlowEntry(nodeId, feName);
			} catch (Exception e2) {/* Ignore */}
			try {
				if(setInFlowConfigInfosRepo) dfAppRoot.flowConfigInfosRepo.deleteRow(configInfo.key);
				if(setInOdlFlowConfigs) odlFlowConfigs.remove(configInfo.key);
			} catch (Exception e3) {/* Ignore */}

			log.error("Failed to record set flow entry " + configInfo.key + " " + trafficFloor.key);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return false;			
		}

		return true;
	}

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removeTrafficFloor(String trafficFloorKey) throws ExceptionControlApp {

		TrafficFloor trafficFloor;
		try {
			Hashtable<String,Object> trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
			// remove traffic floor may be called twice from StatsCollector and DiversionRep
			if (trafficFloorRow == null )
				return;
			trafficFloor = new TrafficFloor(trafficFloorRow);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve traffic floor row " + trafficFloorKey, e);
			throw e;
		}
		removeTrafficFloor(trafficFloor);
	}

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removeTrafficFloor(TrafficFloor trafficFloor) throws ExceptionControlApp {

		String flowConfigInfoKey; boolean firstToDelete;
		fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "removing traffic floor " + trafficFloor.key);

		if(trafficFloor.flowConfigInfoKeys == null) {
			log.error("Received trafficFloor contains null flowConfigInfoKeys " + trafficFloor.key);
			throw new ExceptionControlApp("Received trafficFloor contains null flowConfigInfoKeys " + trafficFloor.key);
		}
		Iterator<Map.Entry<String,Object>> iter = trafficFloor.flowConfigInfoKeys.entrySet().iterator();

		/* To avoid packets loss delete first only "first-to-delete" flows (typically flows into AMS).
		 * Then in another loop delete all remaining flow entries. */
		while(iter.hasNext()) {

			flowConfigInfoKey = iter.next().getKey();
			try {
				firstToDelete = (Boolean) dfAppRoot.flowConfigInfosRepo.getCellValue(flowConfigInfoKey, FlowConfigInfo.FIRST_TO_DELETE);
			} catch (ExceptionControlApp e1) {
				log.error("Failed to retrieve flowConfigInfo record from repo for flowConfigInfoKey", e1);
				continue;
			}
			if(!firstToDelete) continue;
			try {
				deleteOpenFlowEntry(trafficFloor.nodeId, flowConfigInfoKey);
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Deleted flow entry " + flowConfigInfoKey);
				iter.remove();  // Avoid retrying to remove this removed flow entry in the second round below.
			} catch (Throwable e) {
				String msg = "Failed to delete first round flow entry " + flowConfigInfoKey + " on NetNode " +
						trafficFloor.nodeId + ", so aborting traffic floor removal! " + e.getMessage();
				log.error(msg);
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, msg);
				throw new ExceptionControlApp(msg);
			}
		}

		iter = trafficFloor.flowConfigInfoKeys.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		while(iter.hasNext()) {
			flowConfigInfoKey = iter.next().getKey();
			try {
				deleteOpenFlowEntry(trafficFloor.nodeId, flowConfigInfoKey);
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL, "Deleted flow entry " + flowConfigInfoKey);
			} catch (Throwable e) { // Log and continue deleting remaining flow entries
				sb.append("NetNode "); sb.append(trafficFloor.nodeId); sb.append("::");
				sb.append("flowConfigInfoKey "); sb.append(flowConfigInfoKey); sb.append(", ");
			}
		}

		if(sb.length() == 0) { // No errors. Can delete traffic floor.
			try {
				dfAppRoot.trafficFloorsRepo.deleteRow(trafficFloor.key);
			} catch (Exception e) {
				log.error("Failed to delete traffic floor from repo", e);
				throw new ExceptionControlApp("Failed to delete traffic floor from repo", e);
			}
		} else { // Failed to delete some flow entries - so keep this floor to avoid inconsistencies and allow later cleanup
			String msg = "Failed to fully remove traffic floor. Failed flow entries are: " + sb.toString();
			log.error(msg);
			fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, msg);
		}
	}

	public void deleteOpenFlowEntry(String nodeId, String flowConfigInfoKey) throws ExceptionControlApp {

		try {
			flowEntryMgr.deleteOpenFlowEntry(nodeId, flowConfigInfoKey);
		} catch (Throwable e) { 
			log.error("Excepted deletting flowEntry " + nodeId + " " + flowConfigInfoKey, e);
			throw new ExceptionControlApp("Excepted deleting flowEntry "+nodeId+" "+flowConfigInfoKey,e);
		}
		try {
			dfAppRoot.flowConfigInfosRepo.deleteRow(flowConfigInfoKey);
			odlFlowConfigs.remove(flowConfigInfoKey);
		} catch (Throwable e) {
			log.error("Excepted deletting flowEntry from flowConfigInfosRepo " + nodeId + " " + 
					flowConfigInfoKey, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted deleting flowEntry "+nodeId+" "+flowConfigInfoKey,e);
		}		
	}

	public void test(Properties props) {}
}
