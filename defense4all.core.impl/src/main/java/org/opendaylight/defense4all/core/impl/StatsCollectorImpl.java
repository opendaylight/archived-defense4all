/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.core.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.StatReport;
import org.opendaylight.defense4all.core.StatsCollectionRep;
import org.opendaylight.defense4all.core.StatsCollector;
import org.opendaylight.defense4all.core.StatsCountersPlacement;
import org.opendaylight.defense4all.core.PN.StatsCollectionStatus;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.framework.core.Asserter;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.HealthTracker;

public class StatsCollectorImpl extends DFAppCoreModule implements StatsCollector {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_TOPOLOGY_CHANGED = 1;
	protected static final int ACTION_COLLECT_STATS = 2;
	protected static final int ACTION_ADD_PN = 3;
	protected static final int ACTION_REMOVE_PN = 4;

	protected int mCollectStatsIntervalInSecs = 60; // Period to collect traffic statistics - if not set anywhere else

	protected boolean initialized = false;

	/* Constructor for Spring */
	public StatsCollectorImpl(int collectStatsIntervalInSecs) {
		super();
		mCollectStatsIntervalInSecs = collectStatsIntervalInSecs;
	}

	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "StatsCollector is starting.");
		
		super.init();

		// start periodically collecting stats for all protected objects. 
		// Later can use the param to specify which stats to collect in each cycle - based on SLA.
		int interval = ( mCollectStatsIntervalInSecs > dfAppRootFullImpl.controllerStatsCollectionIntervalInSecs) ?
				mCollectStatsIntervalInSecs : dfAppRootFullImpl.controllerStatsCollectionIntervalInSecs;
		addPeriodicExecution(ACTION_COLLECT_STATS, null, (long) interval);
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "StatsCollector is stopping.");
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "StatsCollector is resetting to level " + resetLevel);
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


		//		String pnKey = ""; // just to compile
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

		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "Checking possible counter placement locations for " + pnKey);
		
		List<StatsCountersPlacement> placements = dfAppRootFullImpl.statsCollectionRep.offerCounterPlacements(pnKey);
		List<String> newLocations = selectPlacement(placements); // Select a set of counter locations
		if(newLocations == null) {
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "No possible counter placement locations for " + pnKey);
			return false;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("adding stats counters in selected locations for "); sb.append(pnKey); sb.append(". Locations: ");
		for(String newLocation : newLocations) {
			sb.append(newLocation); sb.append(", ");
		}
		sb.setLength(sb.length() - 2); // Remove last ", "
		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, sb.toString());
		
		boolean succeeded = setStatsCounters(pnKey, newLocations);
		
		if(!succeeded)
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed " + sb.toString());
			
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

		Asserter.assertNonEmptyStringParam(pnKey, "pnKey", log);
		Asserter.assertNonNullObjectParam(newTrafficFloorLocs, "counter locations", log);

		StatsCollectionRep statsCollectionRep = dfAppRootFullImpl.getStatsCollectionRep();
		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String oldTrafficFloorKey; String newTrafficFloorKey = null;
		String oldTrafficFloorLoc; int numofLocs = 0; short oldTrafficFloor;
		//		List<String> processedLocations = new ArrayList<String>();

		while(iter.hasNext()) {

			entry = iter.next();
			if(! entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) continue;

			oldTrafficFloorKey = (String) (entry.getValue());
			if(oldTrafficFloorKey == null) continue;

			oldTrafficFloor = (Short) dfAppRoot.trafficFloorsRepo.getCellValue(oldTrafficFloorKey, TrafficFloor.FLOOR_BASE);
			if(oldTrafficFloor != TrafficFloor.FLOOR_PEACETIME_START) continue; // Account only peace time floor in each loc

			try {
				oldTrafficFloorLoc = (String) statsCollectionRep.getTrafficFloorLocation(oldTrafficFloorKey);
			} catch (ExceptionControlApp e1) {
				continue; // Ignore this old location in calculating where to put new counters. At best this leads to
				// inefficiency. At worst this can conflict with existing counter, so the new counter will not work
			}
			//			// Don't process same location twice in case we have several traffic floors
			//			if ( processedLocations.contains(oldTrafficFloorLoc )) continue;
			//			processedLocations.add(oldTrafficFloorLoc);
			if(newTrafficFloorLocs.contains(oldTrafficFloorLoc)) { // Don't add this new location in OFC - already set
				newTrafficFloorLocs.remove(oldTrafficFloorLoc);
				numofLocs++;
			} else {
				try {
					fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "Removing old traffic floor " + oldTrafficFloorKey);
					statsCollectionRep.removeTrafficFloor(oldTrafficFloorKey);
				} catch (ExceptionControlApp e) {
					log.error("Failed to remove old traffic floor " + oldTrafficFloorKey, e);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed removing traffic floor "+oldTrafficFloorKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
				iter.remove();
			}
		}

		List<String> newTrafficFloorKeys = new ArrayList<String>();
		for (String newTrafficFloorLoc : newTrafficFloorLocs) {

			try {
				newTrafficFloorKey = statsCollectionRep.addPeacetimeCounterTrafficFloor(pnKey, newTrafficFloorLoc);
				if(newTrafficFloorKey == null) continue;  // Failed to add peacetime traffic floor at this location
				pnRow.put(PN.TRAFFIC_FLOOR_KEY_PREFIX + newTrafficFloorKey, newTrafficFloorKey);
				newTrafficFloorKeys.add(newTrafficFloorKey);
				numofLocs++;
			} catch (ExceptionControlApp e) {
				log.error("Failed to add peacetime counter traffic floor for "+pnKey+" at location "+newTrafficFloorLoc,e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed adding peacetime counter traffic floor at "
						+ newTrafficFloorLoc + " for PN " + pnKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);			
			}
		}

		try {
			dfAppRoot.pNsRepo.setRow(pnKey, pnRow);
		} catch (Exception e) {
			log.error("Failed to record in repo PN updates with peacetime floor for " + pnKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed adding peacetime counter traffic floors for PN "
					+ pnKey + ", removing partially installed counters");
			for(String key : newTrafficFloorKeys) {
				try {
					statsCollectionRep.removeTrafficFloor(key);
				} catch (ExceptionControlApp e1) {
					log.error("Excepted trying to remove new traffic floor during cleanup after failure to update PN " + 
							"repo with new floors - for " + pnKey, e);
					fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed removing partially installed counters for "+pnKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
					continue;
				}
			}
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
		}		
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
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void removeStatsCounters(String pnKey) {

		StatsCollectionRep statsCollectionRep = dfAppRootFullImpl.getStatsCollectionRep();
		Hashtable<String, Object> pnRow;
		try {
			pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to get pnRow from repo for " + pnKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed removing peacetime counter traffic floors for PN "+pnKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Retrieve all qualifiedCounterNames from OFC and PNs repo */		
		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String trafficFloorKey;
		while(iter.hasNext()) {
			entry = iter.next();
			if(! entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) continue;
			trafficFloorKey = (String) (entry.getValue());
			short floor = 0;
			try {
				floor = (Short) dfAppRootFullImpl.trafficFloorsRepo.getCellValue(trafficFloorKey, TrafficFloor.FLOOR_BASE);
			} catch (ExceptionControlApp e1) {
				log.error("Failed to get floor from trafficFloorRepo for " + trafficFloorKey, e1);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed removing peacetime counter traffic floor " 
						+ trafficFloorKey + " for PN " + pnKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
			if(floor != TrafficFloor.FLOOR_PEACETIME_START) continue;
			try {
				statsCollectionRep.removeTrafficFloor(trafficFloorKey);
			} catch (ExceptionControlApp e) {
				log.error("Excepted trying to remove traffic floor for " + trafficFloorKey, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed removing peacetime counter traffic floor " 
						+ trafficFloorKey + " for PN " + pnKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			}
			iter.remove();	
		}
		try {
			dfAppRoot.pNsRepo.setRow(pnKey, pnRow);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to set pnRow for " + pnKey, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to properly remove peacetime counter traffic floors "
					+ "for PN " + pnKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
		}			
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp {

		try {
			invokeDecoupledSerially(ACTION_ADD_PN, pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_ADD_PN + " " + pnKey, e);
			throw e;
		}
	}

	protected void decoupledAddPN(String pnKey) {

		boolean succeeded;

		for (int i=0;i<3;i++) {
			try {
				succeeded = selectAndSetStatsCounters(pnKey);
				if (succeeded) {
					dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.ACTIVE.name());
					return;
				}
			} catch (Exception e) {
				log.error("Excepted trying to addPN " + pnKey, e);
				try {
					dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.INVALID.name());
				} catch (ExceptionControlApp e1) {
					log.error("Excepted in marking statsCollectionstatus in pn " + pnKey +
							" as invalid following exception in setting stats counters ", e);
				}
				return;
			}
		}

		try {
			dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.NONE.name());
		} catch (ExceptionControlApp e) {
			log.error("Excepted in marking statsCollectionstatus in pn " + pnKey + " as none. ", e);
		}
	}

	/**
	 * #### method descripBytestion ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) throws ExceptionControlApp {

		try {
			invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);	
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_REMOVE_PN + " " + pnKey, e);
			throw e;
		}

		try {
			dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.STOPPED.name());	
		} catch (ExceptionControlApp e) {
			log.error("Excepted in marking statsCollectionstatus in pn " + pnKey + " as stopped. ", e);
			throw e;
		}		
	}

	/**
	 * #### method descripBytestion ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void decoupledRemovePN(String pnKey) {

		removeStatsCounters(pnKey);

		try {
			dfAppRoot.pNsRepo.setCell(pnKey, PN.STATS_COLLECTION_STATUS, StatsCollectionStatus.INVALID.name());	
		} catch (ExceptionControlApp e) {
			log.error("Excepted in marking statsCollectionstatus in pn " + pnKey + " as invalid. ", e);
		}			
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void periodicCollectStats() {

		if(!fMain.isOpenForBusiness()) return; // Operate only after everything is initialized and is not terminating
		
		Hashtable<String,Hashtable<String,Object>> table = dfAppRoot.pNsRepo.getTable();
		if(table == null) {
			log.error("Received null pns table");
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to collect statistics for all PNs");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			return;
		}

		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = table.entrySet().iterator();
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

		if(pnKey == null || pnKey.isEmpty() || pnRow == null || pnRow.isEmpty()) {
			String pnRowMsg = (pnRow == null) ? "null pnRow" : pnRow.toString();
			log.error("Received bad params for collectstatsForPN: pnKey = " + pnKey + ", pnRow = " + pnRowMsg);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to collect statistics for PN " + pnKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;			
		}

		/* Ignore PNs with detectors not based on open flow stats collection */
		boolean ofBasedDetection = (Boolean) pnRow.get(PN.OF_BASED_DETECTION);
		if(!ofBasedDetection) return;

		/* Ignore protected networks not having OFC stats collection or collection state not active. */
		String s = (String) pnRow.get(PN.STATS_COLLECTION_STATUS);
		if(s == null) return;
		if(StatsCollectionStatus.valueOf(s) != StatsCollectionStatus.ACTIVE) return;

		StatReport statReport; String trafficFloorKey; Map.Entry<String,Object> pncell;
		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		StatsCollectionRep statsCollectionRep = dfAppRoot.getStatsCollectionRep();

		while(iter.hasNext()) {

			pncell = iter.next();			
			if(! pncell.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) continue; // Not a location column

			trafficFloorKey = (String) pncell.getValue();
			if(trafficFloorKey == null) {
				log.error("Null traffic floor key for key " + pncell.getKey());
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				return;
			}

			statReport = null;
			for(int i=0;i<3;i++) {
				try {
					statReport = statsCollectionRep.getStatsReport(pnKey, trafficFloorKey);
					break;
				} catch (ExceptionControlApp e) {
					log.error("Excepted trying to get stats report for pnKey = " + pnKey + ", trafficFloorKey = " + trafficFloorKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
			}			
			if(statReport == null || statReport.stats == null) {
				fMain.getFR().logRecord(DFAppRoot.FR_OFC_FAILURE, "Failed to collect statistics for PN " + pnKey + " at "
						+ trafficFloorKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue; // Occasionally may fail to obtain stats
			}

			//			System.out.println("StatsCollector: delivering " + statReport.stats.tcpbytes + ", " + 
			//							statReport.qualifiedCounterName);
			try {
				dfAppRootFullImpl.detectorMgrImpl.handleStatReport(pnRow, statReport);
			} catch (Throwable e) {/* Ignore */}
		}
	}

	@Override
	public void statsCollectionTopologyChanged() {

		try {
			invokeDecoupledSerially(ACTION_TOPOLOGY_CHANGED, null);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerially " + ACTION_TOPOLOGY_CHANGED, e);
		}		
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
			periodicCollectStats(); // collect stats for specified objects
			break;
		case ACTION_ADD_PN:
			decoupledAddPN((String) param);
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		default:
		}
	}
}
