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

import javax.transaction.NotSupportedException;

import org.opendaylight.defense4all.core.PN.OperationalStatus;
import org.opendaylight.defense4all.core.interactionstructures.StatReport;
import org.opendaylight.defense4all.core.interactionstructures.StatsCountersPlacement;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.ExternalComponentException;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;


public abstract class StatsCollectionRep extends DFAppModule {

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
		setStatsCollectionInterval( ofcKey);
		initConnectionToOFC(ofcKey);
	}

	public void removeOFC(String ofcKey) {
		// TODO Auto-generated method stub
		// For now we do nothing
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
	public abstract void removeNetNode(String netNodeLabel) throws ExceptionControlApp, NotSupportedException;

	/**
	 * Offer all possible placements of stats counters that monitor the traffic to protected object denoted by the passed in pNKey param. 
	 * @param param_name param description
	 * @return return An array of all possible placements of stats counters that monitor the subject traffic. Each placement contains a set of
	 * stats counter locations in the OF network and a common QoS specification in such a placement. QoS degrading factors can be aggregating counters
	 * of multiple traffics into a single one monitoring only the sum of all traffics, time sharing counter placement, Qos degradation to other 
	 * traffics monitoring (if location is preempted by these counters).
	 * @throws ExceptionControlApp 
	 * @throws ExternalComponentException 
	 * @throws exception_type circumstances description 
	 */
	public List<StatsCountersPlacement> offerCounterPlacements(String pNKey) 
			throws ExceptionControlApp, ExternalComponentException {
		// Initial implementation can ignore traffic monitoring merges and preemption.
		return null;
	}

	protected void notifyTopologyChanged() {
		dfAppRoot.getStatsCollector().statsCollectionTopologyChanged();
	}

	// TODO: need also a method to replace OFC?	

	/**
	 * Set counter in OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public String addPeacetimeCounterTrafficFloorSetPNStatus(String pnKey, String newTrafficFloorLoc) throws ExceptionControlApp {

		try {
			String trafficFloor = addPeacetimeCounterTrafficFloor(pnKey, newTrafficFloorLoc);
			return trafficFloor;
		} catch (Throwable e) {
			DFHolder.get().pNsRepo.setCell(pnKey, PN.OPERATIONAL_STATUS, OperationalStatus.FAILED.name());
			return null;
		}
	}

	protected abstract String addPeacetimeCounterTrafficFloor(String pnKey, String newTrafficFloorLoc) throws ExceptionControlApp;

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeTrafficFloor(String trafficFloorKey) throws ExceptionControlApp, ExternalComponentException;

	/**
	 * #### method description ####
	 * @param pnRow 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public StatReport getStatsReport(String pnKey, String trafficFloorKey) throws ExceptionControlApp {

		TrafficFloor.Status status;
		try {
			String statusStr = 
					(String) dfAppRoot.trafficFloorsRepo.getCellValue(trafficFloorKey, TrafficFloor.STATUS);
			status = TrafficFloor.Status.valueOf(statusStr);
		} catch ( Throwable e ) {
			return null;
		}

		if ( status != TrafficFloor.Status.ACTIVE ) return null;

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
	public abstract TrafficTuple getStats(String trafficFloorKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @throws ExceptionControlApp 
	 */
	public abstract String getTrafficFloorLocation(String trafficFloorKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 */
	public abstract int getStatsCollectionInterval();
	/**
	 * #### method description ####
	 * @throws ExceptionControlApp 
	 */
	public abstract void setStatsCollectionInterval(String ofcKey) throws ExceptionControlApp;

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
	}
}
