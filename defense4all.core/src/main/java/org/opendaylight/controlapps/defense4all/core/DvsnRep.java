/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core;

import java.util.List;
import java.util.Properties;

import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


public abstract class DvsnRep extends DFAppModule {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_CHECK_TOPO = 1;
	
	/**
	 * ### Description ### 
	 */
	public enum TrafficDirection {
		INVALID(-1),
		ANY(0),
		IN(1),
		OUT(2);
		
		int mValue;
		private TrafficDirection(int value) {mValue = value;}
	}
	
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

	/** Reset */
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
	}
	
	public void test(Properties props) {
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addOFC(String ofcKey) {		
		initConnectionToOFC(ofcKey);
	}

	/**
	 * Establish connection with the OFC
	 * @param ofcKey
	 */
	protected abstract void initConnectionToOFC(String ofcKey);
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(String amsKey) {

		; // set any additional information in topo repo for this DP
				
		registerDP(amsKey);
		
		; // Set in repo that DP is ready (otherwise Mitigation driver may attempt to divert to it before OFC knows about it).
			
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeOFC(String ofcKey) {			
		// TODO:
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeDP(String amsKey) {

		dfAppRoot.getAMSRep().removeAMS(amsKey);			
		// TODO here	
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return Properties object containing diversion properties. If the traffic to the passed in protected object can not be diverted 
	 * to the passed in DPis (in terms of network capacity, addressing and other constraints) then null is returned.
	 * @throws exception_type circumstances description 
	 */
	public Properties getDvsnProps(String pnKey, String amsKey) {
		// Can check diversion links capacity and current load with respect to the diverted traffic size, constraints with respect to other diverted
		// traffics (e.g., for NEC VLAN-physical port allocation can be set).
		return new Properties();
	}
	
	protected void notifyTopologyChanged() {}

	/**
	 * Retrieve topology changes and set in repo. if retrieving changes is not supported retrieve entire topology. 
	 * This operation is synchronous. If topology has changed, child class implementation should invoke
	 * notifyTopologyChanged(), unless more refined topology changes are identified, in which case StatsCollector
	 * and Mitigation driver should be invoked individually.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected abstract void retrieveTopologyChanges(String ofcKey); 
	
	// TODO: need also a method to replace OFC?	

	/**
	 * Set diversion in the OF network for the passed in protected object
	 * @param param_name param description
	 * @return return true if succeeded to divert traffic
	 * @throws exception_type circumstances description 
	 */
	public abstract List<String> divert(String dvsnKey);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract List<String> endDvsn(String dvsnKey);

	/**
	 * Register DP physical connectivity in OFC
	 * (for NEC - 2 vExt points for DP (VTN, VLAN) in properties)
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected abstract void registerDP(String amsKey);

	/**
	 * Unregister DP physical connectivity in OFC
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void unregisterDP(String amsKey);

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_CHECK_TOPO:
			retrieveTopologyChanges((String) param);
			dfAppRoot.getAMSRep().check();
			; // Scan through the topo repo for changes and health degradations. found changes invoke:
			break;
		default:
		}
	}
}
