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
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.DFAppRoot;
import org.opendaylight.controlapps.defense4all.core.DFDetector;
import org.opendaylight.controlapps.defense4all.core.Detection;
import org.opendaylight.controlapps.defense4all.core.DetectorInfo;
import org.opendaylight.controlapps.defense4all.core.PN;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort;
import org.opendaylight.controlapps.defense4all.core.StatReport;
import org.opendaylight.controlapps.defense4all.core.TrafficTuple;
import org.opendaylight.controlapps.defense4all.core.DFAppRoot.RepoMajor;
import org.opendaylight.controlapps.defense4all.core.Detection.DetectionConfidence;
import org.opendaylight.controlapps.defense4all.core.DetectorInfo.DetectorConfidence;
import org.opendaylight.controlapps.defense4all.core.impl.CounterStat.Status;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.ExceptionEntityExists;
import org.opendaylight.controlapps.framework.core.ExceptionRepoFactoryInternalError;
import org.opendaylight.controlapps.framework.core.Repo;
import org.opendaylight.controlapps.framework.core.RepoFactory;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;

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
	protected static final int ACTION_PROCESS_PN_STATS = 2; // Periodically aggregate stats at PN level
	protected static final int ACTION_REMOVE_PN = 3; // Asynchronously remove PN against other modules

	DetectorInfo detectorInfo;

	public Repo<String> countersStatsRepo = null;
	protected ArrayBlockingQueue<StatReport> statsQueue;
	protected int statsQueueCapacity;
	
	protected ConcurrentHashMap<String,CounterStat> counterStats = null; // Cache to hold all counters and flash them periodically
	
	/* Use the values set below if not set anywhere else */
	protected int movingAveragePeriod = 1000; 
	protected int gracePeriod = 100; 
	protected int warmupPeriod = 15; 
	protected int upperDetectionDeviationPercentage = 100;  // Per counter deviation threshold in percents - 
											   			// triggers immediate check at PN level (all counters).
	protected int lowerDetectionDeviationPercentage = 50;
	protected int durationOfDetection = 300;  
	protected long pnProcessingInterval = 300; 
	protected int counterSuspicionsThreshold = 2;
	protected int pnSuspicionsThreshold = 3;
	
	/* Detectors Repo column names */
	public static final String MOVING_AVERAGE_PERIOD = "moving_average_period";
	public static final String GRACE_PERIOD = "grace_period";
	public static final String WARMUP_PERIOD = "warmup_period";
	public static final String UPPER_DETECTION_DEVIATION_PERCENTAGE = "upper_detection_deviation_percentage";
	public static final String LOWER_DETECTION_DEVIATION_PERCENTAGE = "lower_detection_deviation_percentage";
	public static final String DURATION_OF_DETECTION = "duration_of_detection";
	public static final String PN_PROCESSING_INTERVAL = "pn_processing_interval";
	public static final String COUNTER_SUSPICIONS_THRESHOLD = "counter_suspicions_threshold";
	public static final String PN_SUSPICIONS_THRESHOLD = "pn_suspicions_threshold";

	Logger log = Logger.getLogger(this.getClass());
	protected boolean initialized = false;
	
	Hashtable<String,PNStatSum> pnStatSums;

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
	public void setPnProcessingInterval(long interval) {this.pnProcessingInterval = interval;}
	public void setDetectorInfoStr(String detectorInfoStr) {
		detectorInfo = new DetectorInfo(detectorInfoStr);
		detectorInfo.ofBasedDetector = true;
		detectorInfo.externalDetector = false;
	}
	public void setCounterSuspicionsThreshold(int threshold) {this.counterSuspicionsThreshold = threshold;}
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
		addPeriodicExecution(ACTION_PROCESS_PN_STATS, null, pnProcessingInterval);
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
			} catch (Exception e) {/* Ignore */}
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
			
			if(zeroStat) return; // No need to create new counterStats for stat omissions.
			
			// TODO: check if the counter is in repo first before creating. Can be the case in a distributed DF
			cStat = new CounterStat();  // Konsta
//			cStat = new CounterStat(statReport.trafficFloorKey, statReport.pnKey, statReport.dvsnStat);
			cStat.status = Status.INVALID;	// Konsta
//			cStat.status = statReport.dvsnStat ? Status.ACTIVE : Status.WARMUP_PERIOD;
			counterStats.put(counterStatKey, cStat);
		}
		
		/* Zero stat is only used to clear latest rate of EXISTING counterStats. This can happen for instance,
		 * when a diversion ends and the location is deleted, so the counter will not be refreshed anymore 
		 * (deleted eventually), but is taken into account when calculating PN aggregated stats. */
		if(zeroStat) {
			cStat.updateStatsWithZero();
			return;
		}		

		try {

			/* If warmup - check for end of warmup - into grace */
			if(cStat.status == Status.WARMUP_PERIOD && cStat.lastReadTime - cStat.firstReadTime > warmupPeriod) {
				cStat.status = Status.GRACE_PERIOD;
				return;
			}
			
			/* Update the latest reading, latest reading time, and if not under attack - moving average. 
			 * Return the latest rate or null if first time reading. */
			boolean updateAverages = !cStat.attacked;
			float averagePeriod = cStat.status == Status.ACTIVE ? movingAveragePeriod : gracePeriod;
			cStat.updateStats(statReport, averagePeriod, updateAverages);

//			if(statReport.dvsnStat) return;
			// Konsta
			
			/* If grace - check for end of grace */
			if(cStat.status == Status.GRACE_PERIOD && cStat.lastReadTime - cStat.firstReadTime > gracePeriod) {
				cStat.status = Status.ACTIVE;
				return;
			}

			if(cStat.status != Status.ACTIVE) return;

			/* ACTIVE operation - check for deviations */
			int percentage = cStat.attacked ? lowerDetectionDeviationPercentage : upperDetectionDeviationPercentage;
			List<ProtocolPort> d = cStat.latestRate.deviationExceeds(cStat.movingAverage,percentage);
			if(d == null) {
				cStat.numofAttackSuspicions = 0;
				return;
			}

			/* Deviations found. If the number of times deviations were found exceeds threshold - suspect an attack */
			cStat.numofAttackSuspicions++;
			if(cStat.numofAttackSuspicions <= counterSuspicionsThreshold) return;
			
			PNStatSum pnStatSum = aggregatePNStat(statReport.pnKey); // averages, latest rates from all counters
			processPNAggregatedStat(statReport.pnKey, pnStatSum);

		} finally {	// Persist to repo. TODO: optimize to do it periodically - according to per-PN calculations.
//			cStat.printTCP();
			countersStatsRepo.setRow(cStat.key, cStat.toRow());
		}
	}

	protected void periodicProcessPNStats () {
		
		Iterator<Map.Entry<String,PNStatSum>> pnStatSumIter = pnStatSums.entrySet().iterator();
		while(pnStatSumIter.hasNext()) pnStatSumIter.next().getValue().reset(); // Reset sums prior to next round
		
		/* Add averages and latest rates from all counters - across corresponding PNStatSum objects. */
		
		String pnKey; CounterStat counterStat; PNStatSum pnStatSum;
		Iterator<Map.Entry<String,CounterStat>> counterStatIter = counterStats.entrySet().iterator();
		while(counterStatIter.hasNext()) {
			
			counterStat = counterStatIter.next().getValue();
			pnKey = counterStat.pnKey;
			pnStatSum = pnStatSums.get(pnKey);
			if(pnStatSum == null) {
				pnStatSum = new PNStatSum();
				pnStatSums.put(pnKey, pnStatSum);
			}
			pnStatSum.add(counterStat);
//			System.out.println("periodicProcessPNStats: counter stat " + counterStat.key + ", " + 
//					counterStat.latestRate.tcpbytes);
		}
		
		/* Set sums into PN repo, and check for attacks */
		Map.Entry<String,PNStatSum> entry;
		pnStatSumIter = pnStatSums.entrySet().iterator();
		while(pnStatSumIter.hasNext()) {
			entry = pnStatSumIter.next();
			processPNAggregatedStat(entry.getKey(), entry.getValue());
		}
	}

	protected PNStatSum aggregatePNStat(String pnKey) {		

		Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();
		CounterStat counterStat;  PNStatSum pnStatSum = new PNStatSum();		
		while(iter.hasNext()) {
			counterStat = iter.next().getValue();
			if(counterStat.pnKey.equals(pnKey))
				pnStatSum.add(counterStat);
//			System.out.println("aggregatePNStat: counter stat " + counterStat.key + ", " + 
//					counterStat.latestRate.tcpbytes);
		}		
		return pnStatSum;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void processPNAggregatedStat(String pnKey, PNStatSum pnStatSum) {
		
		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		if(pnRow == null) return; // TODO: log this		
		long currentTime = System.currentTimeMillis() / 1000;
		
		try {
			
			/* Update moving average, latest rate, latestRate time */
			TrafficTuple average;
			if(pnStatSum.warmupPeriod)
				average = new TrafficTuple(); // Zeros
			else if(pnStatSum.average.isZero())
				average = pnStatSum.latestRate; // 1st aggregation, all counters active - initial average set to latest
			else
				average = pnStatSum.average;
			pnRow.put(PN.AVERAGES, average.serialize());
			pnRow.put(PN.LATEST_RATES, pnStatSum.latestRate.serialize());
			pnRow.put(PN.LATEST_RATES_TIME, currentTime);
			if(!pnStatSum.activePeriod) return;

			/* Check for attacks */
			int numofPNSuspicions = (Integer) pnRow.get(PN.NUMOF_ATTACK_SUSPICIONS);
			List<ProtocolPort> attackedProtocolPorts = checkForAttacks(pnStatSum);
			if(attackedProtocolPorts == null) {
				numofPNSuspicions--;
				if(numofPNSuspicions < 0) numofPNSuspicions = 0;
			} else
				numofPNSuspicions++;				
			pnRow.put(PN.NUMOF_ATTACK_SUSPICIONS, numofPNSuspicions);

			/* When are attack detections reporting to attack decision point:
			 * 1. If suspicions counter = 0 -> do not report;
			 * 2. If suspicions counter exceeded threshold -> report
			 * 3. If 0 < suspicions counter <= threshold -> if attack then report, if peace - not */
			if(numofPNSuspicions == 0 ||numofPNSuspicions <= pnSuspicionsThreshold && !pnStatSum.attacked) 
				return;

			/* Set attack in counters, and notify AttackDecisionPoint about generated/updated attack detections */
			setAttackInCounters(pnKey, true);
			addAllAttackDetections(pnKey, attackedProtocolPorts, currentTime);
			
		} finally {
			
			String s; TrafficTuple latest, averages;
			s = (String) pnRow.get(PN.LATEST_RATES); latest = new TrafficTuple(s);
			s = (String) pnRow.get(PN.AVERAGES); averages = new TrafficTuple(s);
//			frameworkMain.getMyLogger().logRow(pnKey + " tcp values:" + 
//					"   latest bps=" + (int)Math.ceil(latest.tcpbytes) + 
//					",  average bps=" + (int)Math.ceil(averages.tcpbytes) + 
//					",  latest pps=" + (int)Math.ceil(latest.tcppackets) + 
//					",  average pps=" + (int)Math.ceil(averages.tcppackets));
			// Konsta
			
			dfAppRoot.pNsRepo.setRow(pnKey, pnRow);
		}
	}
	
	protected void setAttackInCounters(String pnKey, boolean attacked) {

		CounterStat counterStat; Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();
		while(iter.hasNext()) {
			counterStat = iter.next().getValue();
			if(counterStat.pnKey.equals(pnKey) && !counterStat.dvsnStat)	
				counterStat.attacked = attacked;
		}
	}
	
	protected List<ProtocolPort> checkForAttacks(PNStatSum pnStatSum) {

		int percentage = pnStatSum.attacked ? lowerDetectionDeviationPercentage : upperDetectionDeviationPercentage;
		List<ProtocolPort> attackedProtocolPorts = pnStatSum.latestRate.deviationExceeds(pnStatSum.average, percentage);
		
		/* Can add as many attack detection mechanism here, adding to detected attackedProtocolPorts list */
		
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
		String pnKey = (String) dfAppRootFullImpl.detectionsRepo.getCellValue(detectionKey, Detection.PNKEY);
		setAttackInCounters(pnKey, false);
		dfAppRootFullImpl.pNsRepo.setCell(pnKey, PN.NUMOF_ATTACK_SUSPICIONS, 0);
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
		row.put(RateBasedDetectorImpl.PN_PROCESSING_INTERVAL ,pnProcessingInterval);
		row.put(RateBasedDetectorImpl.COUNTER_SUSPICIONS_THRESHOLD ,counterSuspicionsThreshold);
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
		if ( row.get(RateBasedDetectorImpl.PN_PROCESSING_INTERVAL) != null )
			pnProcessingInterval = Long.valueOf(row.get(PN_PROCESSING_INTERVAL).toString());
		if ( row.get(RateBasedDetectorImpl.COUNTER_SUSPICIONS_THRESHOLD) != null )
			counterSuspicionsThreshold = Integer.valueOf( row.get(COUNTER_SUSPICIONS_THRESHOLD).toString());
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
			case ACTION_PROCESS_PN_STATS:
				periodicProcessPNStats();
				break;
			case ACTION_REMOVE_PN:
				decoupledRemovePN((String) param);
				break;
			default:
		}
	}
}
