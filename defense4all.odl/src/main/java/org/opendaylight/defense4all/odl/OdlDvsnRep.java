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
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.DvsnRep;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNode.AMSConnection;
import org.opendaylight.defense4all.core.NetNodeDvsnInfo;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class OdlDvsnRep extends DvsnRep {
	
	Odl odl = null;
	Logger log = LoggerFactory.getLogger("OdlDvsnRep");
	
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

	/** Reset */
	@Override
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
		odl.reset(resetLevel);
	}

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
	
	public void test(Properties props) {
	}
	
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
	public Properties getDvsnProps(String pnKey, String netNodeLabel, String amsKey) {
		
		// Can check diversion links capacity and current load with respect to the diverted traffic size, 
		// constraints with respect to other diverted traffics.
		
		Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(netNodeLabel);
		NetNode netNode;
		try {
			netNode = new NetNode(netNodeRow);
		} catch (UnknownHostException e) {
			// TODO log
			return null;
		}
		
		/* If the ams is directly connected to the netNode, then local diversion is possible. Otherwise not. */
		if(netNode.amsConnections.containsKey(amsKey))
			return new Properties();
		return null;
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
	 * Set diversion in the OF network 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public String divert(String mitigationKey, NetNodeDvsnInfo dvsnInfo) {

		Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
		Hashtable<String,Object> netNodeRow = dfAppRoot.netNodesRepo.getRow(dvsnInfo.netNodeLabel);
		String trafficFloorKey = null;

		try {

			Mitigation mitigation = new Mitigation(mitigationRow);
			String amsLabel = dvsnInfo.amsLabels.get(0);
			NetNode netNode = new NetNode(netNodeRow);	// NetNode in which to install diversion flow entries
			AMSConnection amsConnection = netNode.amsConnections.get(amsLabel);	// Connection to diversion AMS
			
			frameworkMain.getMyLogger().logRow("defense4all is diverting traffic for " + mitigation.pnKey + " " 
			+ mitigation.protocolPort.toString() + "!");
			
			; /* TODO: Create diversion back flow */
			; /* TODO: Create diversion flow */
		} catch (Exception e) {
			log.debug("Failed to create diversion" + e.getMessage());
			return null;
		}
		
		/* record the complemented diversion information in diversions repo */
		dfAppRoot.mitigationsRepo.setRow(mitigationKey, mitigationRow);
		return trafficFloorKey;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public List<String> endDvsn(String dvsnKey) {

		Hashtable<String,Object> dvsnRow = dfAppRoot.mitigationsRepo.getRow(dvsnKey);
		List<String> trafficFloorKeys = new ArrayList<String>();

		try {
			
			String pnKey = (String) dvsnRow.get(Mitigation.PNKEY);

			ProtocolPort protocolPort = new ProtocolPort((String) dvsnRow.get(Mitigation.PROTOCOL_PORT));
			frameworkMain.getMyLogger().logRow("defense4all is canceling traffic diversion for " + pnKey + " " + protocolPort.toString() + "!");

			; // TODO: Remove diversion path
			; // TODO: Remove diversion back path

		} catch (Exception e) {
			log.debug("Failed to end diversion" + e.getMessage());
			return trafficFloorKeys;
		}
		
		/* record the complemented diversion information in diversions repo */
		dfAppRoot.mitigationsRepo.setRow(dvsnKey, dvsnRow);
		return trafficFloorKeys;
	}
	
	@Override
	public void registerDP(String amsKey) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void unregisterDP(String amsKey) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		// Reuse/extend super class implementation
	}
}
