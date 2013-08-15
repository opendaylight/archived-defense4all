/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.StatsCollectionRep;
import org.opendaylight.controlapps.defense4all.core.StatsCountersPlacement;
import org.opendaylight.controlapps.defense4all.core.TrafficFloor;
import org.opendaylight.controlapps.defense4all.core.TrafficTuple;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


public class OdlStatsCollectionRep extends StatsCollectionRep {

	public Odl odl = null;
	Logger log = Logger.getLogger("OdlStatsCollectionRep");
	
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

		ArrayList<String> statsCounterLocs = new ArrayList<String>();
		
		// TODO: loop through all the netNodes for this PN, and retrieve node name
		String netNodeLabel = "place_holder_switch_name";		
		statsCounterLocs.add(netNodeLabel);
		
		StatsCountersPlacement statsCountersPlacement = new StatsCountersPlacement(statsCounterLocs, null);
		
		ArrayList<StatsCountersPlacement> statsCounterPlacements = new ArrayList<StatsCountersPlacement>();
		statsCounterPlacements.add(statsCountersPlacement);
		
		return statsCounterPlacements;
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
	public String addPeacetimeCounterTrafficFloor(String location, String pnKey) throws Exception {	

		/* Create the trafficFloor object and set it in odlTrafficFloorRepo */
		TrafficFloor trafficFloor = new TrafficFloor();
		trafficFloor.pnKey = pnKey;
		trafficFloor.node = location;
		trafficFloor.floor = TrafficFloor.FLOOR_PEACETIME_START;
		String trafficFloorKey = trafficFloor.generateAndSetKey();
		odl.odlTrafficFloorRepo.setRow(trafficFloorKey, trafficFloor.toRow());

		; // TODO: Add counter to its location	
		
		return trafficFloorKey;
	}

	/**
	 * Remove counter from OFSs.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeTrafficFloor(String trafficFloorKey) {
		
		; // TODO: Remove counter from the specified location
		
		odl.odlTrafficFloorRepo.deleteRow(trafficFloorKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public TrafficTuple getStats(String trafficFloorKey) {
		
		TrafficTuple stats = new TrafficTuple();
		stats.setTrafficData(6, 0, 30000, 3000, true);
		stats.setTrafficData(17, 0, 20000, 2000, true);
		stats.setTrafficData(1, 0, 10000, 1000, true);
		stats.setTrafficData(0, 0, 5000, 500, true);
		
		return stats;
	}

	@Override
	public String getTrafficFloorLocation(String trafficFloorKey) {
		String node = (String) odl.odlTrafficFloorRepo.getCellValue(trafficFloorKey, TrafficFloor.NODE);
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
