/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import java.util.List;
import java.util.Properties;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;


public abstract class StatsCollectionRep extends DFAppModule {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_CHECK_TOPO = 1;
	
	/* Constructor for Spring */
	public StatsCollectionRep() {
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

	public void removeOFC(String ofcKey) {
		// TODO Auto-generated method stub
		// For now we do nothing
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
	public abstract void addNetNode(String netNodeKey);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeNetNode(String netNodeLabel);

	/**
	 * Offer all possible placements of stats counters that monitor the traffic to protected object denoted by the passed in pNKey param. 
	 * @param param_name param description
	 * @return return An array of all possible placements of stats counters that monitor the subject traffic. Each placement contains a set of
	 * stats counter locations in the OF network and a common QoS specification in such a placement. QoS degrading factors can be aggregating counters
	 * of multiple traffics into a single one monitoring only the sum of all traffics, time sharing counter placement, Qos degradation to other 
	 * traffics monitoring (if location is preempted by these counters).
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public List<StatsCountersPlacement> offerCounterPlacements(String pNKey) throws ExceptionControlApp {
		// Initial implementation can ignore traffic monitoring merges and preemption.
		return null;
	}
	
	protected void notifyTopologyChanged() {
		dfAppRoot.getStatsCollector().statsCollectionTopologyChanged();
	}

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
	 * Set counter in OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public abstract String addPeacetimeCounterTrafficFloor(String pnKey, String newTrafficFloorLoc) throws Exception;

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeTrafficFloor(String trafficFloorKey);

	/**
	 * #### method description ####
	 * @param pnRow 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public StatReport getStatsReport(String pnKey, String trafficFloorKey) {
		
		TrafficTuple stats = getStats(trafficFloorKey); // Controller specific implementation obtains stats
		if(stats == null) stats = new TrafficTuple();
		
		StatReport statReport = new StatReport();
		statReport.stats = stats;
		statReport.pnKey = pnKey;
		statReport.trafficFloorKey = trafficFloorKey;
		statReport.readingTime = System.currentTimeMillis() / 1000; // Keep time in seconds
		
		return statReport;
	}

	/**
	 * #### method description ####
	 * @param pnRow 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract TrafficTuple getStats(String trafficFloorKey);

	/**
	 * #### method description ####
	 */
	public abstract String getTrafficFloorLocation(String trafficFloorKey);

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_CHECK_TOPO:
			retrieveTopologyChanges((String) param);
			dfAppRoot.getAMSRep().check();
			; // Scan through the topo repo for changes and health degradations. found changes invoke:
			dfAppRoot.getStatsCollector().statsCollectionTopologyChanged();
			break;
		default:
		}
	}
}
