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
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFDetector;
import org.opendaylight.defense4all.core.Detection;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.StatReport;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.core.DFAppRoot.RepoMajor;
import org.opendaylight.defense4all.core.Detection.DetectionConfidence;
import org.opendaylight.defense4all.core.DetectorInfo.DetectorConfidence;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.ExceptionEntityExists;
import org.opendaylight.defense4all.framework.core.ExceptionRepoFactoryInternalError;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoFactory;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.core.impl.CounterStat.Status;

import me.prettyprint.cassandra.serializers.StringSerializer;


public class RateBasedDetectorImpl extends DFAppCoreModule implements DFDetector {

	/**
	 * Name space allocation of DF Detector Repo minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
		COUNTERS_STATS
	}

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_PROCESS_STATS = 1;
	protected static final int ACTION_REMOVE_PN = 3; // Asynchronously remove PN against other modules
	private static final int ACTION_PROCESS_BASELINES = 5; // Periodically drop average to PN repo

	DetectorInfo detectorInfo;

	private Repo<String> countersStatsRepo = null;
	protected ArrayBlockingQueue<StatReport> statsQueue;
	protected int statsQueueCapacity;

	private ConcurrentHashMap<String,CounterStat> counterStats = null; // Cache to hold all counters and flash them periodically
	protected Hashtable<String,PNStatSum> pnStatSums;

	/* Use the values set below if not set anywhere else */
	protected int movingAveragePeriod = 1000; 
	protected int gracePeriod = 100; 
	protected int warmupPeriod = 15; 
	protected int upperDetectionDeviationPercentage = 100;  // Per counter deviatiocheckAggrForAttacksn threshold in percents - 
	// triggers immediate check at PN level (all counters).
	protected int lowerDetectionDeviationPercentage = 50;
	protected int durationOfDetection = 300;  
	protected long baselinesProcessingInterval = 0;
	protected int pnSuspicionsThreshold = 3;

	/* Detectors Repo column names */
	public static final String MOVING_AVERAGE_PERIOD = "moving_average_period";
	public static final String GRACE_PERIOD = "grace_period";
	public static final String WARMUP_PERIOD = "warmup_period";
	public static final String UPPER_DETECTION_DEVIATION_PERCENTAGE = "upper_detection_deviation_percentage";
	public static final String LOWER_DETECTION_DEVIATION_PERCENTAGE = "lower_detection_deviation_percentage";
	public static final String DURATION_OF_DETECTION = "duration_of_detection";
	public static final String PN_PROCESSING_INTERVAL = "pn_processing_interval";
	public static final String BASELINES_PROCESSING_INTERVAL = "baselines_processing_interval";
	public static final String COUNTER_SUSPICIONS_THRESHOLD = "counter_suspicions_threshold";
	public static final String PN_SUSPICIONS_THRESHOLD = "pn_suspicions_threshold";

	Logger log = LoggerFactory.getLogger(this.getClass());
	protected boolean initialized = false;

	/* Constructor for Spring */
	public RateBasedDetectorImpl(int statsQueueCapacity) {
		super();
		boolean ofBasedDetector = true; boolean externalDetector = false;
		detectorInfo = new DetectorInfo(DFAppRoot.OF_RATE_BASED_DETECTOR_LABEL, DetectorConfidence.VERY_HIGH, 
				ofBasedDetector, externalDetector);
		this.statsQueueCapacity = statsQueueCapacity; // spring injection
		statsQueue = new ArrayBlockingQueue<StatReport>(statsQueueCapacity);
		counterStats = new ConcurrentHashMap<String,CounterStat>();
		pnStatSums = new Hashtable<String,PNStatSum>();
	}

	/* Setters for Spring */
	public void setMovingAveragePeriod(int period) {this.movingAveragePeriod = period;}
	public void setGracePeriod(int period) {this.gracePeriod = period;}
	public void setWarmupPeriod(int period) {this.warmupPeriod = period;}
	public void setUpperDetectionDeviationPercentage(int percentage) {this.upperDetectionDeviationPercentage = percentage;}
	public void setLowerDetectionDeviationPercentage(int percentage) {this.lowerDetectionDeviationPercentage = percentage;}
	public void setDurationOfDetection(int duration) {this.durationOfDetection = duration;}
	public void setBaselinesInterval(long interval) {this.baselinesProcessingInterval = interval;}
	public void setDetectorInfoStr(String detectorInfoStr) {
		detectorInfo = new DetectorInfo(detectorInfoStr);
		detectorInfo.ofBasedDetector = true;
		detectorInfo.externalDetector = false;
	}
	public void setPnSuspicionsThreshold(int threshold) {this.pnSuspicionsThreshold = threshold;}
	public void setLabel ( String label ) {
		detectorInfo.setLabel(label);
	}

	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {	
		super.init();

		// All DF Detector repos
		RepoFactory rf = frameworkMain.getRepoFactory();
		String rMajor = RepoMajor.DF_DETECTOR.name();
		try {
			countersStatsRepo = (Repo<String>) rf.getOrCreateRepo(rMajor, RepoMinor.COUNTERS_STATS.name(), 
					StringSerializer.get(), true, CounterStat.getCounterStatsRCDs());
		} catch (ExceptionRepoFactoryInternalError e) {
			throw new ExceptionControlApp("Internal framework error. ", e);
		} catch (IllegalArgumentException e) {
			throw new ExceptionControlApp("Internal framework error. ", e);
		} catch (ExceptionEntityExists e) {
			throw new ExceptionControlApp("Internal framework error. ", e);
		}		

		loadCounters();
		addBackgroundTask(ACTION_PROCESS_STATS, null);
		if ( baselinesProcessingInterval != 0)
			addPeriodicExecution(ACTION_PROCESS_BASELINES, null, baselinesProcessingInterval);
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
		try {
			// in case finit is after reset ignore exception
			persistCounters();
		} catch ( Exception e ) {
			// in case finit is after reset ignore exception
		}

	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {

		super.reset(resetLevel);

		pnStatSums.clear();		
		statsQueue.clear();
		counterStats.clear();
		countersStatsRepo.truncate();
	}

	/* Load all counters from repo into counters cache */
	protected void loadCounters() {
		Hashtable<String,Hashtable<String,Object>> counterTable = countersStatsRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = counterTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; CounterStat counterStat;
		while(iter.hasNext()) {
			entry = iter.next();
			counterStat = new CounterStat(entry.getValue());
			counterStats.put(entry.getKey(), counterStat);
		}
	}

	/* Persist counters to repo, including dynamic information. */
	protected void persistCounters() {

		Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();
		Map.Entry<String,CounterStat> entry; CounterStat counterStat;
		while(iter.hasNext()) {
			entry = iter.next();
			counterStat = entry.getValue();
			countersStatsRepo.setRow(entry.getKey(), counterStat.toRow());
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws Exception {

		; // add information in the protected objects repo?
		try {
			dfAppRootFullImpl.statsCollectorImpl.addPN(pnKey);
		} catch (ExceptionControlApp e) {
			throw new Exception("Failed to add protected network " + pnKey, e );
		}		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) {
		invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);			
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void decoupledRemovePN(String pnKey) {

		dfAppRootFullImpl.attackDecisionPointImpl.removeDetection(pnKey); // if previously notified attack detection for this object - cancel it.
		dfAppRootFullImpl.statsCollectorImpl.removePN(pnKey);			
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void handleStatReport(StatReport statsReport) {
		try {
			statsQueue.put(statsReport);
		} catch (InterruptedException e) {
			; // Ignore
		}		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void processStatReports() {

		StatReport statReport;		
		while(true) {			
			try {
				statReport = statsQueue.take();
				processStatReport(statReport);
			} catch (Exception e) {/* Ignore */
				System.out.println("Exception in processStatReports ");
			}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void processStatReport(StatReport statReport) {

		boolean zeroStat = statReport.stats.isZero();
		
		/* Get the counter from cache. create and persist if non-existent */
		String counterStatKey = CounterStat.generateKey(statReport.trafficFloorKey, statReport.pnKey);
		CounterStat cStat = counterStats.get(counterStatKey);
		if(cStat == null) { // New counter. Need to create it, add to cache and to repo
			if(zeroStat) return;

			// TODO: check if the counter is in repo first before creating. Can be the case in a distributed DF
			cStat = new CounterStat(statReport.trafficFloorKey, statReport.pnKey ); 

			if ( statReport.stats.isForTrafficLearning()) {
				cStat.status = Status.WARMUP_PERIOD;
			} else {
				cStat.status = Status.ACTIVE; 
			}

			counterStats.put(counterStatKey, cStat);		
		}

		try {

			// lock counter for modification
			cStat.lock();

			/* Zero stat is only used to clear latest rate of EXISTING counterStats. This can happen for instance,
			 * when a diversion ends and the location is deleted, so the counter will not be refreshed anymore 
			 * (deleted eventually), but is taken into account when calculating PN aggregated stats. */
			if(zeroStat) {
				cStat.updateStatsWithZero();
				return;
			}		


			/* If warmup - check for end of warmup - into grace */
			if(cStat.status == Status.WARMUP_PERIOD && cStat.lastReadTime - cStat.firstReadTime > warmupPeriod) {
				cStat.status = Status.GRACE_PERIOD;
				return;
			}

			/* Update the latest reading, latest reading time, and if not under attack - moving average. 
			 * Return the latest rate or null if first time reading. */
			boolean updateAverages = cStat.status == Status.ACTIVE || cStat.status == Status.GRACE_PERIOD; 
			float averagePeriod = cStat.status == Status.ACTIVE ? movingAveragePeriod : gracePeriod;
			cStat.updateStats(statReport, averagePeriod, updateAverages);

			/* If grace - check for end of grace */
			if(cStat.status == Status.GRACE_PERIOD && cStat.lastReadTime - cStat.firstReadTime > gracePeriod) {
				cStat.status = Status.ACTIVE;
				return;
			}

			if(cStat.status != Status.ACTIVE) return;

			// Aggregate and process PN level counter
		    PNStatSum pnStatSum = aggregatePNStat(statReport.pnKey); // averages, latest rates from all counters	
			processPNcheckAttack(statReport.pnKey, pnStatSum);
		} finally {	// Persist to repo. TODO: optimize to do it periodically - according to per-PN calculations.
			try {
				// cStat.printTCP();
				countersStatsRepo.setRow(cStat.getKey(), cStat.toRow());
			} finally { 
				cStat.unlock();
			}
		}
	}

	protected PNStatSum aggregatePNStat(String pnKey) {		

		PNStatSum pnStatSum = new PNStatSum(pnKey);

		Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();
		CounterStat counterStat;  		
		while(iter.hasNext()) {
			counterStat = iter.next().getValue();
			if(counterStat.pnKey.equals(pnKey))
				pnStatSum.add(counterStat);
		}	
		pnStatSums.put(pnKey, pnStatSum);
		return pnStatSum;	
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void processPNcheckAttack(String pnKey, PNStatSum pnStatSum) {

		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		if(pnRow == null) return; // TODO: log this		
		long currentTime = System.currentTimeMillis() / 1000;

		try {
			/* Update moving average, latest rate, latestRate time */
			TrafficTuple average;
			if (pnStatSum.status == Status.WARMUP_PERIOD)
				average = new TrafficTuple(); // Zeros
			else if(pnStatSum.movingAverage.isZero())
				average = pnStatSum.latestRate; // 1st aggregation, all counters active - initial average set to latest
			else
				average = pnStatSum.movingAverage ;

			pnRow.put(PN.AVERAGES, average.serialize());
			pnRow.put(PN.LATEST_RATES, pnStatSum.latestRate.serialize());
			pnRow.put(PN.LATEST_RATES_TIME, currentTime);

			if(pnStatSum.status != Status.ACTIVE ) return ;

			/* Load current attack suspicions */
			String statusOfPNSuspicions = ( String ) pnRow.get(PN.ATTACK_SUSPICIONS);
			pnStatSum.loadStatusData (statusOfPNSuspicions );

			/* Check for attacks */	
			List<ProtocolPort> attackedProtocolPorts = checkAggrForAttacks(pnStatSum);
			if(attackedProtocolPorts == null) {
				return;

			} 
					
			/* Store updated status to PN repo */
			pnRow.put(PN.ATTACK_SUSPICIONS, pnStatSum.serializeStatusData());

			/* Set attack in counters, and notify AttackDecisionPoint about generated/updated attack detections */
			setAttackInCounters(pnKey, attackedProtocolPorts, true);
			addAllAttackDetections(pnKey, attackedProtocolPorts, currentTime);
		} 
		finally {
			// String s; TrafficTuple latest, averages;
			// s = (String) pnRow.get(PN.LATEST_RATES); latest = new TrafficTuple(s);
			// s = (String) pnRow.get(PN.AVERAGES); averages = new TrafficTuple(s);
			//			frameworkMain.getMyLogger().logRow(pnKey + " tcp values:" + 
			//					"   latest bps=" + (int)Math.ceil(latest.tcpbytes) + 
			//					",  average bps=" + (int)Math.ceil(averages.tcpbytes) + 
			//					",  latest pps=" + (int)Math.ceil(latest.tcppackets) + 
			//					",  average pps=" + (int)Math.ceil(averages.tcppackets));

			dfAppRoot.pNsRepo.setRow(pnKey, pnRow);
		}
	}

	protected void setAttackInCounters(String pnKey, List<ProtocolPort> attackedProtocolPorts,  boolean attacked) {

		CounterStat counterStat; Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();

		while(iter.hasNext()) {
			counterStat = iter.next().getValue();
			try {
				counterStat.lock();	
				if(counterStat.pnKey.equals(pnKey) ) {
					for (ProtocolPort protocolPort:attackedProtocolPorts ) {
						counterStat.setAttacked(protocolPort.protocol.getProtocolNumber(), protocolPort.port, attacked );  
					}
				}
			}finally {
				counterStat.unlock();
			}
		}
	}

	protected List<ProtocolPort> checkAggrForAttacks(PNStatSum pnStatSum ) {

		ArrayList<ProtocolPort> attackedProtocolPorts = new ArrayList<ProtocolPort>();
		List<ProtocolPort> suspictProtocolPorts = pnStatSum.deviationExceeds(pnStatSum.movingAverage, 
				lowerDetectionDeviationPercentage, upperDetectionDeviationPercentage );	
		/* Can add as many attack detection mechanism here, adding to detected attackedProtocolPorts list */

		// add attack suspicion for each detected deviation */
		// decrease attack suspicion for other protocol / ports
		pnStatSum.resetAddAttackSuspicions(suspictProtocolPorts);

		if (suspictProtocolPorts == null )
			return suspictProtocolPorts;

		/* When are attack detections reporting to attack decision point:
		 * 1. If suspicions counter = 0 -> do not report;
		 * 2. If suspicions counter exceeded threshold -> report
		 * 3. If 0 < suspicions counter <= threshold -> if attack then report, if peace - not */
		for ( ProtocolPort protocolPort : suspictProtocolPorts) {
			int nSuspict = pnStatSum.getAttackSuspicions(protocolPort);
			if ( nSuspict > pnSuspicionsThreshold 
					|| nSuspict <= pnSuspicionsThreshold && pnStatSum.isAttacked(protocolPort) ) {
				attackedProtocolPorts.add(protocolPort);
			} 
		}	

		return attackedProtocolPorts;
	}

	/* Add all detected attacks*/
	protected void addAllAttackDetections(String pnKey, List<ProtocolPort> attackedProtocolPorts, long currentTime) {

		Detection detection; String detectionKey;
		for(ProtocolPort protocolPort : attackedProtocolPorts) {
			detectionKey = Detection.generateDetectionKey(detectorInfo.label, pnKey, protocolPort);
			detection = new Detection(detectionKey, detectorInfo.label, DetectionConfidence.VERY_HIGH, currentTime, 
					durationOfDetection, pnKey, protocolPort);

			dfAppRootFullImpl.attackDecisionPointImpl.addDetection(detection);
		}
	}

	/**
	 * Notify DF detector that the attack corresponding to the detection denoted by the passed in detectionKey
	 * is over. RateBasedDetectorImpl updates the state of the stat collection counters to resume calculating moving
	 * average.
	 * @param detectionKey
	 */
	@Override
	public void notifyEndDetection(String detectionKey) {

		Hashtable<String, Object> detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey); 
		String pnKey = (String) detectionRow.get(Detection.PNKEY); 
		ProtocolPort protocolPort = new ProtocolPort((String) detectionRow.get( Detection.PROTOCOL_PORT) );

		// invalidate counters for case zero stat was not send
		// find traffic floor from detection via attack and mitigation
		Hashtable<String,Hashtable<String,Object>> attackTable = dfAppRootFullImpl.attacksRepo.getTable();		
		Hashtable<String,Object> attackRow;  Attack attack = null; 
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iterAttack = attackTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry;
		String mitigationKey = null;

		while(iterAttack.hasNext()) {		
			entry = iterAttack.next();
			attackRow = entry.getValue();
			attack = new Attack(attackRow);
			if(!attack.pnKey.equals(pnKey)) 
				continue;

			Iterator<Map.Entry<Object,Object>> iterDetectionsOfAttack = attack.detectionKeys.entrySet().iterator();
			while(iterDetectionsOfAttack.hasNext()) {
				// look-up over detection_keys properties of attack
				if ( detectionKey.equals( (String) iterDetectionsOfAttack.next().getKey()) ){
					// found relevant attack, take the mitigation
					mitigationKey = attack.mitigationKey;
					break;
				}
			}

			if ( mitigationKey != null )
				break;
		}

		// if found mitigation - take traffic floor if any
		String mitigationTrafficFloor = null;
		if ( mitigationKey != null ) {
			mitigationTrafficFloor = 
					(String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.TRAFFIC_FLOOR_KEY) ;  
		}

		// find counters used for mitigated traffic 
		// by mitigationKey and pnKey
		if ( mitigationTrafficFloor != null ) {
			String mitigationCounterStatKey = CounterStat.generateKey(mitigationTrafficFloor, pnKey);
			CounterStat cStat = counterStats.get(mitigationCounterStatKey);
			try {
				cStat.lock();
				cStat.status = Status.INVALID;
				cStat.updateStatsWithZero();
				countersStatsRepo.setRow(cStat.getKey(), cStat.toRow());
			}finally { 
				cStat.unlock();
			}
		}	

		List<ProtocolPort> protoPorts = new ArrayList<ProtocolPort>();
		protoPorts.add(protocolPort);
		setAttackInCounters(pnKey, protoPorts, false);

		String statusOfPNSuspicions = (String)dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.ATTACK_SUSPICIONS );
		CounterStat status = new PNStatSum(pnKey);
		status.loadStatusData (statusOfPNSuspicions );
		status.setAttacked( protocolPort.protocol.getProtocolNumber(), protocolPort.port, false);
		status.setAttackSuspicions(protocolPort.protocol.getProtocolNumber(), protocolPort.port, 0);
		dfAppRootFullImpl.pNsRepo.setCell(pnKey, PN.ATTACK_SUSPICIONS, status.serializeStatusData());
	}


	/**
	 * Clean-up all relevant detectors 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void cleanup(){
		// Move over invalidated counters
		// if location has been deleted from PN - delete detector also
		for ( String key : counterStats.keySet() ) {
			CounterStat cStat = counterStats.get(key);
			if ( cStat.status != Status.INVALID) 
				continue;

			boolean found = false;
			Hashtable<String,Object> pnRow = dfAppRootFullImpl.pNsRepo.getRow(cStat.pnKey);
			if(pnRow != null) {
				Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
				Map.Entry<String,Object> entry;
				while(iter.hasNext()) {
					entry = iter.next();
					if(entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) {
						CounterStat cPNStat = new CounterStat ( (String) entry.getValue(), cStat.pnKey);
						if (cPNStat.getKey().equals(key)) {
							// This traffic floor still exists in the PN - do nothing
							found = true;
							continue;
						}
					}
				}

				// Traffic floor doesn't exist in the PN - clean counter
				if ( found == false ) {
					counterStats.remove(key);
					countersStatsRepo.deleteRow(key);
				}
			}
		}
	}
	
	/**
	 * Periodically drop statistics from detector average to PN repo 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void periodicProcessBaselines () {
		/* Set sums into PN repo, and check for attacks */
		synchronized ( pnStatSums ) {
			Map.Entry<String,PNStatSum> entry;
			Iterator<Entry<String, PNStatSum>> pnStatSumIter = pnStatSums.entrySet().iterator();

			while(pnStatSumIter.hasNext()) {
				entry = pnStatSumIter.next();
				String pnKey = entry.getKey();
				TrafficTuple average = entry.getValue().movingAverage;

				if(average.isZero()) continue;	

				Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
				if(pnRow == null) continue;
				long currentTime = System.currentTimeMillis() / 1000;
				pnRow.put(PN.BASELINES, average.serialize());
				pnRow.put(PN.BASELINES_TIME, currentTime);
				dfAppRoot.pNsRepo.setRow(pnKey, pnRow);
			}
		}
	}

	/* 
	 * (non-Javadoc)
	 * @see org.opendaylight.controlapps.defense4all.core.Detector#getDetectorInfo()
	 * For spring requirements get should return string
	 * getter to string is for spring req
	 */
	public DetectorInfo getDetectorInfo() {return detectorInfo;}
	public String getDetectorInfoStr() { return detectorInfo.toString();}

	/**
	 * Compose detector repository row
	 */
	public Hashtable<String, Object> toRow() {
		// First columns are detector info
		Hashtable<String, Object> row = detectorInfo.toRow();

		row.put(RateBasedDetectorImpl.MOVING_AVERAGE_PERIOD, movingAveragePeriod);
		row.put(RateBasedDetectorImpl.GRACE_PERIOD ,gracePeriod);
		row.put(RateBasedDetectorImpl.WARMUP_PERIOD ,warmupPeriod);
		row.put(RateBasedDetectorImpl.UPPER_DETECTION_DEVIATION_PERCENTAGE ,upperDetectionDeviationPercentage);
		row.put(RateBasedDetectorImpl.LOWER_DETECTION_DEVIATION_PERCENTAGE ,lowerDetectionDeviationPercentage);
		row.put(RateBasedDetectorImpl.DURATION_OF_DETECTION ,durationOfDetection);
		row.put(RateBasedDetectorImpl.BASELINES_PROCESSING_INTERVAL ,baselinesProcessingInterval);
		row.put(RateBasedDetectorImpl.PN_SUSPICIONS_THRESHOLD ,pnSuspicionsThreshold);

		return row;
	}

	public void fromRow(Hashtable<String, Object> row)
	{
		this.detectorInfo.fromRow(row); 

		if ( row.get(RateBasedDetectorImpl.MOVING_AVERAGE_PERIOD) != null )
			movingAveragePeriod = Integer.valueOf(row.get(MOVING_AVERAGE_PERIOD).toString());
		if ( row.get(RateBasedDetectorImpl.GRACE_PERIOD) != null )
			gracePeriod = Integer.valueOf( row.get(GRACE_PERIOD).toString());
		if ( row.get(RateBasedDetectorImpl.WARMUP_PERIOD) != null )
			warmupPeriod = Integer.valueOf( row.get(WARMUP_PERIOD).toString());
		if ( row.get(RateBasedDetectorImpl.UPPER_DETECTION_DEVIATION_PERCENTAGE) != null )
			upperDetectionDeviationPercentage = Integer.valueOf( row.get(UPPER_DETECTION_DEVIATION_PERCENTAGE).toString());
		if ( row.get(RateBasedDetectorImpl.LOWER_DETECTION_DEVIATION_PERCENTAGE) != null )
			lowerDetectionDeviationPercentage = Integer.valueOf( row.get(LOWER_DETECTION_DEVIATION_PERCENTAGE).toString());
		if ( row.get(RateBasedDetectorImpl.DURATION_OF_DETECTION) != null )
			durationOfDetection = Integer.valueOf( row.get(DURATION_OF_DETECTION).toString());
		if ( row.get(RateBasedDetectorImpl.BASELINES_PROCESSING_INTERVAL) != null )
			baselinesProcessingInterval = Long.valueOf(row.get(BASELINES_PROCESSING_INTERVAL).toString());
		if ( row.get(RateBasedDetectorImpl.PN_SUSPICIONS_THRESHOLD) != null )
			pnSuspicionsThreshold = Integer.valueOf( row.get(PN_SUSPICIONS_THRESHOLD).toString());

	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_PROCESS_STATS:
			processStatReports();
			break;
		case ACTION_PROCESS_BASELINES:
			periodicProcessBaselines();
			break;	
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		default:
		}
	}
}
