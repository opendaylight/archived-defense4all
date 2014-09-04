/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.mina.filter.firewall.Subnet;
import org.opendaylight.defense4all.core.AMSConnection;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.DvsnRep;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PN.MitigationScope;
import org.opendaylight.defense4all.core.ProtectedLink;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.Traffic.TrafficDirection;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.core.DvsnInfo;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.TrafficPort;
import org.opendaylight.defense4all.core.TrafficPort.PortLocation;
import org.opendaylight.defense4all.core.interactionstructures.NetNodeUppedDownedAMSConns;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.odl.pojos.FlowConfigNoProtocol.ActionType;

public class OdlDvsnRep extends DvsnRep {

	Odl odl = null;
	Logger log = LoggerFactory.getLogger(this.getClass());

	/* Constructor for Spring */
	public OdlDvsnRep() {
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

	@Override
	protected void initConnectionToOFC(String ofcKey) throws ExceptionControlApp {
		odl.initConnectionToOFC(ofcKey);
		odl.retrieveTopology(ofcKey);
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
		// TODO: add checks and returns similar to NEC
		odl.removeNetNode(netNodeKey);
	}

	public void test(Properties props) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeOFC(String ofcKey) {
		// Reuse/extend super class implementation
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return Properties object containing diversion properties. If the traffic to the passed in protected object can not be diverted 
	 * to the passed in DPis (in terms of network capacity, addressing and other constraints) then null is returned.
	 * @throws exception_type circumstances description 
	 */
	@Override
	public Properties getDvsnProps(String pnKey, String netNodeLabel, String amsConnKey) {

		// Can check diversion links capacity and current load with respect to the diverted traffic size, 
		// constraints with respect to other diverted traffics.

		NetNode netNode;
		try {
			Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeLabel);
			if(netNodeRow == null || NetNode.isRemoved(netNodeLabel)) return null;
			netNode = new NetNode(netNodeRow);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to inflate netNode " + netNodeLabel, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to get diversion properties for " + pnKey
					+ " from NetNode " + netNodeLabel + " to AMS " + amsConnKey);
			return null;
		}

		/* If the ams is directly connected to the netNode, then local diversion is possible. Otherwise not. */
		if(netNode.amsConnections == null || !netNode.amsConnections.containsKey(amsConnKey)) 
			return null;
		return new Properties();
	}

	/**
	 * Set diversion in the OF network 
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public String divert(String mitigationKey, String dvsnInfoKey) throws ExceptionControlApp {

		Mitigation mitigation;	DvsnInfo dvsnInfo;	NetNode netNode; PN pn; TrafficFloor tFloor;
		boolean success; DFProtocol protoToDivert; AMSConnection amsConn; String amsConnLabel;

		try {

			Hashtable<String, Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
			mitigation = new Mitigation(mitigationRow);

			Hashtable<String,Object> dvsnInfoRow = dfAppRoot.dvsnInfosRepo.getRow(dvsnInfoKey);
			dvsnInfo = new DvsnInfo(dvsnInfoRow);

			Hashtable<String, Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(dvsnInfo.netNodeLabel);
			if(netNodeRow == null || NetNode.isRemoved(dvsnInfo.netNodeLabel)) {
				log.warn("NetNode " + dvsnInfo.netNodeLabel + " specified for PN " + mitigation.pnKey + " is not known to Defense4All");
				return null; 
			}
			netNode = new NetNode(netNodeRow);	// NetNode in which to install diversion flow entries

			Hashtable<String, Object> pnRow = dfAppRoot.pNsRepo.getRow(mitigation.pnKey);
			pn = new PN(pnRow);

			tFloor = new TrafficFloor();
			tFloor.pnKey = mitigation.pnKey;
			tFloor.nodeLabel = netNode.label;
			tFloor.nodeId = netNode.id;

			amsConnLabel = dvsnInfo.amsDvsnInfos.get(0).label;
			amsConn = netNode.amsConnections.get(amsConnLabel);	// Connection to diversion AMS
			if(amsConn == null) {
				String msg = "Internal DF inconsistency - NetNode AMSConnection does not contain the AMSConnection " + 
						" specified in DvsnInfo. DF Reset is advised. " + dvsnInfoKey;
				log.error(msg);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "OdlDvsnRep failed to divert traffic for mitigation "
						+ mitigationKey	+ " , and according to diversion information " + dvsnInfoKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				return null;
			}

			tFloor.floorBase = getAvailableDvsnFloor(tFloor.nodeLabel, tFloor.pnKey, mitigation.protocolPort.protocol);
			tFloor.floorCurrentHeight = tFloor.floorBase;
			
			String trafficFloorKey = tFloor.generateAndSetKey();

			/* Check if we already added this diversion traffic floor. Can be if the application is restarted 
			 * and attacked traffic diversion is re-requested. */
			Hashtable<String,Object> trafficFloorRow = dfAppRoot.trafficFloorsRepo.getRow(trafficFloorKey);
			if(trafficFloorRow != null) {
				String msg =  "Suitable diversion traffic floor exists for " + pn.label + ", mitigation " + mitigationKey
						+ ", diversion information " + dvsnInfoKey + ". Most likely DF was restarted during an active attack.";
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, msg);
				return trafficFloorKey;
			}

		} catch (Throwable e) {
			String msg = "Excepted retrieving/reconstructing relevant info from repos " + mitigationKey + " " + dvsnInfoKey;
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "OdlDvsnRep failed to divert traffic for mitigation "
					+ mitigationKey	+ " , and according to diversion information " + dvsnInfoKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);
		}

		/* Prepare configInfo common template to be duplicated and adjusted in called methods.
		 * Per mitigation scope - divert either all traffic of attacked PN or only traffic of attacked protocol */
		OdlFlowConfigInfo ciTemplate = new OdlFlowConfigInfo();
		ciTemplate.nodeLabel = netNode.label; ciTemplate.etherType = 2048; 
		ciTemplate.actions = new ArrayList<String>();
		ciTemplate.forTrafficLearning = false; // None of the flow entries in this floor is for traffic learning

		/* Set protocol if only attacked protocol traffic should be diverted (rather than all PN traffic), and
		 * the protocol is not IP (rest of the traffic). */
		if(pn.mitigationScope == MitigationScope.ATTACKED) {
			protoToDivert = mitigation.protocolPort.protocol;
			if(mitigation.protocolPort.protocol != DFProtocol.IP)
				ciTemplate.protocol = mitigation.protocolPort.protocol.getProtocolNumber();
		} else
			protoToDivert = DFProtocol.INVALID;

		try {

			StringBuilder sb = new StringBuilder();
			sb.append("Diverting traffic for pn="); sb.append(pn.label); 
			if(pn.mitigationScope == MitigationScope.ATTACKED) {
				sb.append(", protocol="); sb.append(mitigation.protocolPort.protocol);
				if(mitigation.protocolPort.port != 0) {
					sb.append(", protocol="); sb.append(mitigation.protocolPort.protocol);
				}
			} else {
				sb.append(" - all traffic");
			}			
			sb.append(", according to mitigationKey="); sb.append(mitigationKey);
			sb.append(", and diversionInfoKey="); sb.append(dvsnInfoKey);
			sb.append(". diversionInfoKey="); sb.append(dvsnInfoKey);
			sb.append(". Diverting from NetNode="); sb.append(netNode.label);
			sb.append(" through AMSConnection="); sb.append(amsConnLabel);
			sb.append(". The diversion is "); sb.append(pn.symmetricDvsn ? "symmetric" : "asymmetric");
			sb.append(". Diversion traffic floor key="); sb.append(tFloor.key);
			sb.append(", floor number="); sb.append(tFloor.floorBase);	
			sb.append(", floor current height="); sb.append(tFloor.floorCurrentHeight);	
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());

			/* Set diversion flow entries for traffic to protected object to flow through mitigating DP */
			success = addReturnPath(tFloor, netNode, amsConn, TrafficDirection.INBOUND, ciTemplate);
			success &= addDvsnPath(pn, tFloor, netNode, amsConn, TrafficDirection.INBOUND, ciTemplate, protoToDivert);

			/* In case of symmetric diversion divert also the returning traffic from protected object through same DP */
			if(pn.symmetricDvsn) {
				success &= addReturnPath(tFloor, netNode, amsConn, TrafficDirection.OUTBOUND,ciTemplate);
				success &= addDvsnPath(pn,tFloor,netNode, amsConn, TrafficDirection.OUTBOUND,ciTemplate, protoToDivert);
			}
			if(! success) {	// If any of the diversion parts failed cancel all other parts of this diversion
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed to divert traffic for mitigation="
						+ mitigationKey + ". Removing any installed flow entries in this traffic floor.");
				odl.removeTrafficFloor(tFloor);
				return null;
			}

			/* record the complemented diversion information in diversions repo */
			dfAppRoot.mitigationsRepo.setRow(mitigationKey, mitigation.toRow());
			dfAppRoot.trafficFloorsRepo.setRow(tFloor.key, tFloor.toRow());

			return tFloor.key;

		} catch (Throwable e) {
			try {
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed to divert traffic for mitigation="
						+ mitigationKey + ". Removing any installed flow entries in this traffic floor.");
				odl.removeTrafficFloor(tFloor);
			} catch (ExceptionControlApp e1) {
				String msg = "Excepted cleaning up traffic floor after failure to install " + tFloor.key;
				log.error(msg);
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, msg);
			}
			String msg = "Failed to create diversion for " + tFloor.toString();
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);
		}
	}

	/* Add returning flow entries down. */
	protected boolean addReturnPath(TrafficFloor tFloor, NetNode netNode, AMSConnection amsConn, TrafficDirection direction, 
			OdlFlowConfigInfo configInfoTemplate) {

		OdlFlowConfigInfo configInfoTemplateClone = new OdlFlowConfigInfo(configInfoTemplate);
		configInfoTemplateClone.forRates = false;	// Don't monitor oubound traffic - only inbound (to servers)
		configInfoTemplateClone.direction = direction;
		boolean inBoundTraffic = (direction == TrafficDirection.INBOUND);
		boolean success = false;

		/* For inbound traffic set return path flow from southToAms port. For outbound traffic - from northToAms port */
		String portStr = (inBoundTraffic ? amsConn.netNodeSouthPort : amsConn.netNodeNorthPort);
		configInfoTemplateClone.ingressPort = Short.valueOf(portStr);			

		if(netNode.sdnNodeMode == SDNNodeMode.sdnenabledhybrid) { // hybrid switch - action is "send to normal"

			StringBuilder sb = new StringBuilder();
			sb.append("adding diversion return path for traffic floor key="); sb.append(tFloor.key);
			sb.append(" in direction "); sb.append(direction);
			sb.append(" from SDN hybrid NetNode="); sb.append(netNode.label);
			sb.append(" through AMS "); sb.append(amsConn.toString());
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());
			
			OdlFlowConfigInfo configInfo = new OdlFlowConfigInfo(configInfoTemplateClone);
			configInfo.actions.add(ActionType.HW_PATH.name());	// "Send to normal" (non-OF routing)
			configInfo.floor = tFloor.floorCurrentHeight++;
			configInfo.id = odl.getUniqueCookie();
			success = odl.setFlowEntry(configInfo, tFloor);
			if(!success) 
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + sb.toString());				
			return success;

		} else { // native switch - send to output traffic port with matching destination mac 

			Iterator<Map.Entry<String,ProtectedLink>> iter = netNode.protectedLinks.entrySet().iterator();
			if(netNode.protectedLinks == null) {
				log.error("Got null protectedLinks in netNode " + netNode);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				return false;
			}
			ProtectedLink protectedLink; OdlFlowConfigInfo configInfo; String action; short outPort = 0; 
			boolean anyMacs = false; Map.Entry<String,ProtectedLink> entry;
			StringBuilder sb = new StringBuilder();

			while(iter.hasNext()) {

				entry = iter.next();
				protectedLink = entry.getValue();
				if(protectedLink == null) {
					log.error("Got null protectedLink value for in netNode " + entry.getKey());
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					return false;
				}		
				configInfo = new OdlFlowConfigInfo(configInfoTemplateClone);
				configInfo.id = odl.getUniqueCookie();
				configInfo.generateAndSetKey();

				/* Determine to which port to route the traffic returning from AMS. The output traffic port has to
				 * match the input traffic port (protectedLink) otherwise MAC learning will be screwed. Because the
				 * output traffic port is lost in packets returning from AMS device we must use another mechanism 
				 * to determine the input port. We can only identify the input port by the MAC of the device 
				 * connected to north port - which still appears in the packet. We assume a topology in which there 
				 * is a single router connected to north port, and learn it. Then, for inbound traffic we match the 
				 * source mac, and for outbound traffic we match the destination mac. */
				if(inBoundTraffic) {
					configInfo.dlSrc = protectedLink.macOfConnectedToNorthPort;
					outPort = protectedLink.southPort;
				} else {
					configInfo.dlDst = protectedLink.macOfConnectedToNorthPort;
					outPort = protectedLink.northPort;					
				}
				if(protectedLink.macOfConnectedToNorthPort == null || protectedLink.macOfConnectedToNorthPort.isEmpty())
					continue;  // Will set a single flow entry if no MACs are set. Usable for single north port topology.
				anyMacs = true;

				sb.setLength(0);
				sb.append("adding diversion return path for traffic floor key="); sb.append(tFloor.key);
				sb.append(" in direction "); sb.append(direction);
				sb.append(" from SDN native NetNode="); sb.append(netNode.label);
				sb.append(" through AMS "); sb.append(amsConn.toString());
				sb.append("Setting "); sb.append(inBoundTraffic ? "source" : "destination");
				sb.append(" MAC filter of north connected Network element to "); 
				sb.append(protectedLink.macOfConnectedToNorthPort);
				sb.append(". Outputting to port "); sb.append(outPort);
				sb.append(". Generated cookie (id)="); sb.append(configInfo.id);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());
				action = ActionType.OUTPUT.name() + "=" + outPort; // Send to matching out port
				if(configInfo.actions == null) 
					configInfo.actions = new ArrayList<String>(); // Just in case it was not created
				configInfo.actions.add(action);
				configInfo.floor = tFloor.floorCurrentHeight++;
				
				success |= odl.setFlowEntry(configInfo, tFloor);
				
				if(!success)
					fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + sb.toString());
			}

			if(!anyMacs && outPort != 0) {	// Support topology with single NB port with no MACs learned of connected devices
				if(netNode.protectedLinks.size() > 1) return false;	// Cannot return traffic to the wrong output port
				configInfo = new OdlFlowConfigInfo(configInfoTemplateClone);
				configInfo.id = odl.getUniqueCookie();
				configInfo.generateAndSetKey();
				action = ActionType.OUTPUT.name() + "=" + outPort; // Send to sole out port
				if(configInfo.actions == null) 
					configInfo.actions = new ArrayList<String>(); // Just in case it was not created
				configInfo.actions.add(action);				
				configInfo.floor = tFloor.floorCurrentHeight++;	
				sb.setLength(0);
				
				sb.append("adding diversion return path for traffic floor key="); sb.append(tFloor.key);
				sb.append(" in direction "); sb.append(direction);
				sb.append(" from SDN native NetNode="); sb.append(netNode.label);
				sb.append(" through AMS "); sb.append(amsConn.toString());
				sb.append("Setting "); sb.append(inBoundTraffic ? "source" : "destination");
				sb.append(". Outputting to port "); sb.append(outPort);
				sb.append(". Generated cookie (id)="); sb.append(configInfo.id);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());	
				
				success |= odl.setFlowEntry(configInfo, tFloor);
				
				if(!success)
					fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + sb.toString());
			}

			return success;
		}		
	}

	/* Add flow entries to divert traffic into mitigating DP. */
	protected boolean addDvsnPath(PN pn, TrafficFloor tFloor, NetNode netNode, AMSConnection amsConn, 
			TrafficDirection direction, OdlFlowConfigInfo configInfoTemplate, DFProtocol protocolToDivert) {

		boolean success = false; 
		OdlFlowConfigInfo configInfoTemplateClone = new OdlFlowConfigInfo(configInfoTemplate);
		configInfoTemplateClone.firstToDelete = true; // Diversion flow entries should be deleted first to avoid packet loss
		boolean inBoundTraffic = (direction == TrafficDirection.INBOUND);
		configInfoTemplateClone.direction = direction;
		configInfoTemplateClone.forRates = (inBoundTraffic == true);
		StringBuilder sb = new StringBuilder();

		/* Set the pnAddr filter - either as destination match or as source match - depending on traffic direction */
		try {
			String dstAddrStr = (String) dfAppRoot.pNsRepo.getCellValue(pn.label, PN.DST_ADDR);
			int dstAddrLen = (Integer) dfAppRoot.pNsRepo.getCellValue(pn.label, PN.DST_ADDR_PREFIX_LEN);
			Subnet pnAddrSubnet = new Subnet(InetAddress.getByName(dstAddrStr), dstAddrLen);
			if(inBoundTraffic)
				configInfoTemplateClone.nwDst = pnAddrSubnet.toString();
			else
				configInfoTemplateClone.nwSrc = pnAddrSubnet.toString();
		} catch (Throwable e) { 
			log.error("Excepted trying to get PN destination address from repo for " + pn.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return false;
		}
		
		

		String portStr = (inBoundTraffic ? amsConn.netNodeNorthPort : amsConn.netNodeSouthPort);
		int portToAms = Short.valueOf(portStr);
		String divertAction = ActionType.OUTPUT.name() + "=" + portToAms; // E.g., "OUTPUT=2"
		PortLocation relevantPortLoc = inBoundTraffic ? PortLocation.north : PortLocation.south;

		/* 1) "Rest of traffic" diversion - send {TCP,UDP,ICMP} traffic to matching output port. "Rest of traffic" - to AMS. 
		 * 2) Any other protocol diversion - divert traffic of that to AMS. 
		 * 3) All traffic diversion - divert all traffic to AMS. */
		if(protocolToDivert == DFProtocol.IP) { // Case 1 above
			
			sb.append("adding diversion path for traffic floor key="); sb.append(tFloor.key);
			sb.append(" in direction "); sb.append(direction);
			sb.append(" of \"other\" (non TCP, UDP or ICMP)  traffic "); sb.append(protocolToDivert);
			sb.append(" from NetNode="); sb.append(netNode.label);
			sb.append(" through AMS "); sb.append(amsConn.toString());
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());

			if(netNode.protectedLinks == null) {
				log.error("Got null protectedLinks in netNode " + netNode);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				return false;
			}
			Iterator<Map.Entry<String,ProtectedLink>> iter = netNode.protectedLinks.entrySet().iterator();
			ProtectedLink protectedLink; String normalFlowAction; short ingressPort; short dvsnPort;

			while(iter.hasNext()) {		

				protectedLink = iter.next().getValue();

				if(inBoundTraffic) {
					ingressPort = protectedLink.northPort;
					dvsnPort = protectedLink.southPort; // E.g., "OUTPUT=3"
				} else {
					ingressPort = protectedLink.southPort;
					dvsnPort = protectedLink.northPort; // E.g., "OUTPUT=1"
				}
				normalFlowAction = ActionType.OUTPUT.name() + "=" + dvsnPort;

				sb.setLength(0);
				sb.append("adding diversion path for ingress port="); sb.append(ingressPort);
				sb.append(". TCP, UDP, ICMP traffic is send normally to "); sb.append(dvsnPort);
				sb.append(", \"other\" traffic is diverted to AMS via port="); sb.append(portToAms);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());
				
				/* Add OTHER flow entry - divert to AMS - This flow entry needs to have the lowest priority. As such
				 * it is added first, every set bumps up the priority */
				success |= setFlowEntry(configInfoTemplateClone, tFloor, ingressPort, DFProtocol.IP.getProtocolNumber(),
						divertAction);

				/* Now add flows with no diversion - for all other protocols */
				success |= setFlowEntry(configInfoTemplateClone, tFloor, ingressPort, DFProtocol.UDP.getProtocolNumber(), 
						normalFlowAction); // Add UDP flow entry - normal flow to output
				success |= setFlowEntry(configInfoTemplateClone, tFloor, ingressPort, DFProtocol.ICMP.getProtocolNumber(),
						normalFlowAction); // Add ICMP flow entry - normal flow to output
				success |= setFlowEntry(configInfoTemplateClone, tFloor, ingressPort, DFProtocol.TCP.getProtocolNumber(), 
						normalFlowAction); // Add TCP flow entry - normal flow to output
				
				if(!success)
					fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + sb.toString());
			}

		} else { // cases 2 and 3 above

			if(netNode.trafficPorts == null) {
				String msg = "Got null trafficPorts in netNode " + netNode;
				log.error(msg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				return false;
			}
			Iterator<Map.Entry<String,TrafficPort>> iter = netNode.trafficPorts.entrySet().iterator();
			TrafficPort trafficPort; Map.Entry<String,TrafficPort> entry;

			sb.append("adding diversion path for traffic floor key="); sb.append(tFloor.key);
			sb.append(" in direction "); sb.append(direction);
			sb.append(" of protocol="); sb.append(protocolToDivert);
			sb.append(" from NetNode="); sb.append(netNode.label);
			sb.append(" through AMS "); sb.append(amsConn.toString());
			sb.append(", via port "); sb.append(portToAms);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());

			while(iter.hasNext()) {
				entry = iter.next();	
				trafficPort = entry.getValue();	
				if(trafficPort == null) {
					log.error("Got null trafficPort value for in netNode " + entry.getKey());
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					return false;
				}			
				if(trafficPort.location != relevantPortLoc) continue;

				sb.setLength(0);
				sb.append("adding diversion path from traffic port="); sb.append(trafficPort.toString());
				sb.append(" to AMS via port="); sb.append(portToAms);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, sb.toString());
				
				success |= setFlowEntry(configInfoTemplateClone, tFloor, trafficPort.number, 
						protocolToDivert.getProtocolNumber(), divertAction);
				
				if(!success)
					fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed " + sb.toString());
			}
		}

		return success;
	}

	protected boolean setFlowEntry(OdlFlowConfigInfo clone, TrafficFloor tFloor, short ingressPort, short protocol, 
			String action) {

		OdlFlowConfigInfo configInfo = new OdlFlowConfigInfo(clone);
		configInfo.ingressPort = ingressPort;
		configInfo.actions.add(action);
		configInfo.id = odl.getUniqueCookie();
		configInfo.generateAndSetKey();
		configInfo.protocol = protocol;
		configInfo.floor = (short) (tFloor.floorCurrentHeight++);
		boolean success = odl.setFlowEntry(configInfo, tFloor);
		return success;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void endDvsn(String mitigationKey, String tFloorKey) {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Ending diversion for dvsnKey=" + mitigationKey 
				+ ", trafficFloorKey=" + tFloorKey);
		try {
			Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
			String pnKey = (String) mitigationRow.get(Mitigation.PNKEY);
			ProtocolPort protocolPort = new ProtocolPort((String) mitigationRow.get(Mitigation.PROTOCOL_PORT));
			log.info("defense4all is canceling traffic diversion for " + pnKey + " " + protocolPort.toString() + "!");
		} catch (Throwable e1) {/* Ignore */}
		try {
			odl.removeTrafficFloor(tFloorKey);
		} catch (Throwable e) {
			log.error("Excepted trying to remove diversion traffic floor for " + tFloorKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY,"Failed to properly end diversion for trafficFloorKey="+tFloorKey);
		}
	}

	@Override
	public DvsnInfo prepareForDvsn(String mitigationKey, String dvsnInfoKey) {return new DvsnInfo(dvsnInfoKey);}

	@Override
	public DvsnInfo unprepareForDvsn(String mitigationKey, String dvsnInfoKey) {return new DvsnInfo(dvsnInfoKey);}
	
	@Override
	public void notifyNetNodeAMSConnStatusChanged(NetNodeUppedDownedAMSConns netNodeUppedDownedAMSConn2) {}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {}

}
