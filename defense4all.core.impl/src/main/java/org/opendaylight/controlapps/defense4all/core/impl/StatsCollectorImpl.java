/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core.impl;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.PN;
import org.opendaylight.controlapps.defense4all.core.StatReport;
import org.opendaylight.controlapps.defense4all.core.StatsCollectionRep;
import org.opendaylight.controlapps.defense4all.core.StatsCollector;
import org.opendaylight.controlapps.defense4all.core.StatsCountersPlacement;
import org.opendaylight.controlapps.defense4all.core.PN.StatsCollectionStatus;
import org.opendaylight.controlapps.framework.core.Asserter;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


public class StatsCollectorImpl extends DFAppCoreModule implements StatsCollector {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_TOPOLOGY_CHANGED = 1;
	protected static final int ACTION_COLLECT_STATS = 2;
	protected static final int ACTION_REMOVE_PN = 3;
	

	protected long mCollectStatsIntervalInSecs = 60; // Period to collect traffic statistics - if not set anywhere else
	
	Logger log = Logger.getLogger(this.getClass());
	protected boolean initialized = false;
	
	/* Constructor for Spring */
	public StatsCollectorImpl(int collectStatsIntervalInSecs) {
		super();
		mCollectStatsIntervalInSecs = collectStatsIntervalInSecs;
	}
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {
		
		super.init();
		
		// start periodically collecting stats for all protected objects. 
		// Later can use the param to specify which stats to collect in each cycle - based on SLA.
		addPeriodicExecution(ACTION_COLLECT_STATS, null, mCollectStatsIntervalInSecs);
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void decoupledTopologyChanged() {
		
		if(!initialized) return; // Can happen if Rep completes init and topo retrieval before this module is initialized
		
		// TODO: check in topo repo if protected objects were added/removed/migrated, and update stats monitors settings accordingly.
		// To check in the repo need to define another column - OFCStatsColelctorInspection, and in every entry of a server that is either
		// a protected object or is in the protected network - check if the timestamps of any of the connectivity columns is newer than
		// that of the last inspection. Update the inspection timestamp. If the timestamp is newer check if the server was added, removed or
		// migrated. 
		// If added - check if there is a need to add a stats counter (in the first implementation the answer is always yes).
		// If removed - remove the stats counter if one was put solely for this server (in the first implementation the answer is always yes).
		// If migrated - perform the remove and add.
		
		
		String pnKey = ""; // just to compile
//		
//		try {
//			addStatsCounters(pnKey);
//		} catch (ExceptionControlApp e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} // here is how to add
//
//		removeStatsCounters(pnKey); // here is how to remove
	}

	/**
	 * Adding stats counters per protected object: check options against topology. select. 
	 * add new and remove old counter locations - against stats collection rep.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected boolean selectAndSetStatsCounters(String pnKey) throws ExceptionControlApp {		
		
		List<StatsCountersPlacement> placements = dfAppRootFullImpl.statsCollectionRep.offerCounterPlacements(pnKey);
		List<String> newLocations = selectPlacement(placements); // Select a set of counter locations
		if(newLocations == null) return false;
		boolean succeeded = setStatsCounters(pnKey, newLocations);		
		return succeeded;
	}

	/**
	 * Remove no longer needed locations and add the new ones. Add counters to counter repo.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public boolean setStatsCounters(String pnKey, List<String> newTrafficFloorLocs) throws ExceptionControlApp {

		Asserter.assertNonEmptyStringParam(pnKey, "pnKey");
		Asserter.assertNonNullObjectParam(newTrafficFloorLocs, "counter locations");		
		StatsCollectionRep statsCollectionRep = dfAppRootFullImpl.getStatsCollectionRep();
		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String oldTrafficFloorKey; String newTrafficFloorKey = null;
		String oldTrafficFloorLoc; int numofLocs = 0;

		while(iter.hasNext()) {
			entry = iter.next();
			if(! entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) continue;
			oldTrafficFloorKey = (String) (entry.getValue());
			oldTrafficFloorLoc = (String) statsCollectionRep.getTrafficFloorLocation(oldTrafficFloorKey);
			if(newTrafficFloorLocs.contains(oldTrafficFloorLoc)) { // Don't add this new location in OFC - already set
				newTrafficFloorLocs.remove(oldTrafficFloorLoc);
				numofLocs++;
			} else {
				try {
					statsCollectionRep.removeTrafficFloor(oldTrafficFloorKey);
				} catch (Exception e) {/* Ignore */}
				iter.remove();
			}
		}

		for (String newTrafficFloorLoc : newTrafficFloorLocs) {
			
			try {
				newTrafficFloorKey = statsCollectionRep.addPeacetimeCounterTrafficFloor(pnKey, newTrafficFloorLoc);
				pnRow.put(PN.TRAFFIC_FLOOR_KEY_PREFIX + newTrafficFloorKey, newTrafficFloorKey);
				numofLocs++;
			} catch (Exception e) {/* Ignore */}
		}
		
		dfAppRoot.pNsRepo.setRow(pnKey, pnRow);		
		return (numofLocs > 0);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	private List<String> selectPlacement(List<StatsCountersPlacement> statsCountersPlacements) {
		if (statsCountersPlacements == null || statsCountersPlacements.size() == 0)
			return null;		
		return statsCountersPlacements.get(0).counterLocations; // TODO: Incorporate QoS-aware placement selection algorithm.
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void removeStatsCounters(String pnKey) {
		
		StatsCollectionRep statsCollectionRep = dfAppRootFullImpl.getStatsCollectionRep();
		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		
		/* Retrieve all qualifiedCounterNames from OFC and PNs repo */		
		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String trafficFloorKey;
		while(iter.hasNext()) {
			entry = iter.next();
			if(entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) {
				trafficFloorKey = (String) (entry.getValue());
				try {
					statsCollectionRep.removeTrafficFloor(trafficFloorKey);
				} catch (Exception e) {/* Ignore */}	
				iter.remove();	
			}
		}
		dfAppRoot.pNsRepo.setRow(pnKey, pnRow);			
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp {
		
		boolean succeeded = selectAndSetStatsCounters(pnKey);		
		if(succeeded)	
			dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.ACTIVE.name());
		else
			dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.NONE.name());
	}

	/**
	 * #### method descripBytestion ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) {

		invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);		
		dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.INVALID.name());				
	}

	/**
	 * #### method descripBytestion ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void decoupledRemovePN(String pnKey) {
		removeStatsCounters(pnKey);		
		dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.INVALID.name());				
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void collectStats() {
		
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = dfAppRoot.pNsRepo.getTable().entrySet().iterator();
		String pnKey; Hashtable<String,Object> pnRow; Map.Entry<String,Hashtable<String,Object>> entry;

		/* Loop through all protected networks. */
		while(iter.hasNext()) {			
			entry = iter.next();
			pnKey = entry.getKey();
			pnRow = entry.getValue();
			collectStatsForPN(pnKey, pnRow);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void collectStatsForPN(String pnKey, Hashtable<String,Object> pnRow) {
		
		/* Ignore PNs with detectors not based on open flow stats collection */
		boolean ofBasedDetection = (Boolean) pnRow.get(PN.OF_BASED_DETECTION);
		if(!ofBasedDetection) return;
		
		/* Ignore protected networks not having OFC stats collection or collection state not active. */
		String s = (String) pnRow.get(PN.STATS_COLLECTION_STATUS);
		if(s == null) return;
		if(StatsCollectionStatus.valueOf(s) != StatsCollectionStatus.ACTIVE) return;

		StatReport statReport; String trafficFloorKey; Map.Entry<String,Object> cell;
		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		StatsCollectionRep statsCollectionRep = dfAppRoot.getStatsCollectionRep();

		while(iter.hasNext()) {

			cell = iter.next();			
			if(! cell.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) continue; // Not a location column

			trafficFloorKey = (String) cell.getValue();
			statReport = statsCollectionRep.getStatsReport(pnKey, trafficFloorKey);
			if(statReport == null || statReport.stats == null) continue; // Occasionally may fail to obtain stats

//			System.out.println("StatsCollector: delivering " + statReport.stats.tcpbytes + ", " + 
//							statReport.qualifiedCounterName);
			dfAppRootFullImpl.detectorMgrImpl.handleStatReport(pnRow, statReport);
		}
	}

	@Override
	public void statsCollectionTopologyChanged() {
		invokeDecoupledSerially(ACTION_TOPOLOGY_CHANGED, null);		
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_TOPOLOGY_CHANGED:
			decoupledTopologyChanged();
			break;
		case ACTION_COLLECT_STATS:
			collectStats(); // collect stats for specified objects
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		default:
		}
	}
}
