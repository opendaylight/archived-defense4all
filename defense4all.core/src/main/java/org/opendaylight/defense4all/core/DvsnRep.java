/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import java.net.UnknownHostException;
import java.util.Properties;

import javax.transaction.NotSupportedException;

import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.interactionstructures.NetNodeUppedDownedAMSConns;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public abstract class DvsnRep extends DFAppModule {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity

	/* Constructor for Spring */
	public DvsnRep() {
		super();
	}

	/** Post-constructor initialization	 
	 * @throws ExceptionControlApp */
	public void init() throws ExceptionControlApp {	
		super.init();
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		super.reset(resetLevel);
	}

	public void test(Properties props) {
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addOFC(String ofcKey) throws ExceptionControlApp {
		initConnectionToOFC(ofcKey);
	}

	/**
	 * Establish connection with the OFC
	 * @param ofcKey
	 * @throws ExceptionControlApp 
	 */
	protected abstract void initConnectionToOFC(String ofcKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(String amsKey) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeOFC(String ofcKey) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeAMS(String amsKey) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws NotSupportedException 
	 * @throws exception_type circumstances description 
	 */
	public abstract void addNetNode(String netNodeKey) throws ExceptionControlApp, NotSupportedException;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws NotSupportedException 
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeNetNode(String netNodeLabel) throws NotSupportedException, ExceptionControlApp, IllegalStateException;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return Properties object containing diversion properties. If the traffic to the passed in protected object can not be diverted 
	 * to the passed in DPis (in terms of network capacity, addressing and other constraints) then null is returned.
	 * @throws exception_type circumstances description 
	 */
	public Properties getDvsnProps(String pnKey, String netNodeLabel, String amsKey) {
		// Can check diversion links capacity and current load with respect to the diverted traffic size, constraints with respect to other diverted
		// traffics.
		return new Properties();
	}

	/**
	 * Set diversion in the OF network for the passed in protected object
	 * @param dvsnInfo 
	 * @param param_name param description
	 * @return return true if succeeded to divert traffic
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public abstract String divert(String mitigationKey, String dvsnInfokey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param trafficFloorKey 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void endDvsn(String dvsnKey, String trafficFloorKey);

	/**
	 * ####
	 * @param param_name param description
	 * @return return description 
	 * @throws ExceptionControlApp 
	 * @throws UnknownHostException 
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	protected short getAvailableDvsnFloor(String node, String pnKey, DFProtocol dfProtocol) throws ExceptionControlApp {

		/* For "other" attacks we allocate a dedicated floor with priority lower than any other attack floor, but higher 
		 * than peace time. This is because the way to divert "other" traffic only is to put higher priority flows to send
		 * TCP, UDP ICMP traffic straight and another lower priority flow to divert all (remaining) traffic. Had this floor
		 * been higher than other attack floors, the "straight" flow of this entry would cancel diversion of such a flow in
		 * a lower floor. */
		if(dfProtocol == DFProtocol.IP)
			return TrafficFloor.FLOOR_OTHER_ATTACK_START;

		short floor = TrafficFloor.FLOOR_ATTACK_START;
		while ( true ) {
			String key = TrafficFloor.generateAndSetKey( node,  pnKey,  floor);
			String existing = null;
			try {
				existing = (String) dfAppRoot.trafficFloorsRepo.getCellValue(key, TrafficFloor.KEY);
			} catch (ExceptionControlApp e) {
				log.error("Could not get available diversion floor because excepted checking floor existence in repo.");
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				throw new ExceptionControlApp("Could not get available diversion floor.", e);
			}
			if (existing == null ) 
				return floor;
			else 
				floor+=TrafficFloor.FLOOR_STEP;
		}
	}

	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public abstract DvsnInfo prepareForDvsn(String mitigationKey, String dvsnInfoKey);
	
	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public abstract DvsnInfo unprepareForDvsn(String mitigationKey, String dvsnInfoKey);

	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public abstract void notifyNetNodeAMSConnStatusChanged(NetNodeUppedDownedAMSConns netNodeUppedDownedAMSConns);

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		default:
		}
	}
}
