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
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.CounterStat;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFDetector;
import org.opendaylight.defense4all.core.Detection;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.StatReport;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.core.CounterStat.Status;
import org.opendaylight.defense4all.core.Detection.DetectionConfidence;
import org.opendaylight.defense4all.core.DetectorInfo.DetectorConfidence;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class RateBasedDetectorImpl extends DFAppCoreModule implements DFDetector {

	/**
	 * Name space allocation of DF Detector Repo minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
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

	protected ArrayBlockingQueue<StatReport> statsQueue;
	protected int statsQueueCapacity;

	protected ConcurrentHashMap<String,CounterStat> counterStats = null; // Cache to hold and flash periodically all counters
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

		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL,"RateBased Detector is starting");

		try {
			loadCounters();
		} catch (Throwable e) {
			log.error("Failed to load data from countersRepo in RateBasedDetector. ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE,"RateBased Detector failed to properly start");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);			
			throw new ExceptionControlApp("Failed to load data from countersRepo in RateBasedDetector. ", e);
		}

		addBackgroundTask(ACTION_PROCESS_STATS, null);
		if ( baselinesProcessingInterval != 0)
			addPeriodicExecution(ACTION_PROCESS_BASELINES, null, baselinesProcessingInterval);
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL,"RateBased Detector is stopping");
		try {
			persistCounters();
		} catch ( Throwable e ) {
			// log and ignore
			log.error("Failed to persist data into countersRepo in RateBasedDetector. ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE,"RateBased Detector failed to properly stop");
		}
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL,"RateBased Detector is resetting to level " + resetLevel);
		super.reset(resetLevel);

		pnStatSums.clear();
		statsQueue.clear();
		counterStats.clear();
	}

	/* Load all counters from repo into counters cache */
	protected void loadCounters() throws ExceptionControlApp {
		Hashtable<String,Hashtable<String,Object>> counterTable = dfAppRoot.countersStatsRepo.getTable();
		if ( counterTable == null ) return;
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = counterTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; CounterStat counterStat;
		while(iter.hasNext()) {
			entry = iter.next();
			counterStat = new CounterStat(entry.getValue());
			counterStats.put(entry.getKey(), counterStat);
		}
	}

	/* Persist counters to repo, including dynamic information. */
	protected void persistCounters() throws ExceptionControlApp {

		if (counterStats == null ) return;
		Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();
		Map.Entry<String,CounterStat> entry; CounterStat counterStat;
		while(iter.hasNext()) {
			entry = iter.next();
			counterStat = entry.getValue();
			try {
				dfAppRoot.countersStatsRepo.setRow(entry.getKey(), counterStat.toRow());
			} catch (Throwable e) {	
				log.error("Failed to persist counters for detector "+detectorInfo.label, e );
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp {
		try {
			dfAppRootFullImpl.statsCollectorImpl.addPN(pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to add protected network to statistics counters. " + pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp ("Failed to add protected network to statistics counters.  " + pnKey, e );
		}		
	}

	/**
	 * #### method description ####
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
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void decoupledRemovePN(String pnKey) {

		try {
			dfAppRootFullImpl.attackDecisionPointImpl.removeDetection(pnKey); // if previously notified attack detection for this object - cancel it.
			dfAppRootFullImpl.statsCollectorImpl.removePN(pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted in removing PN from detector " +detectorInfo.label+ " PN key "+ pnKey , e);			
		}			
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
		} 
		catch (InterruptedException e1) { // ignore - termination request
		} 
		catch ( Throwable e ){
			log.error("Failed to handleStatReport. PN key: " + statsReport.pnKey+" Reading time: "+statsReport.readingTime, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
		} 
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void processStatReports() {

		StatReport statReport = null;		
		while(true) {			
			try {
				statReport = statsQueue.take();
				processStatReport(statReport);
			}
			catch (InterruptedException e1) { // termination request
				break;
			}  
			catch (Throwable e) {
				String msg = "Failed to process stat report: PN key: " + ( statReport  != null ?statReport.pnKey:"");
				log.error(msg, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void processStatReport(StatReport statReport) throws ExceptionControlApp {

		if ( statReport == null || statReport.stats == null ) {
			log.error("Unexpected zero statReport recived." );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		boolean zeroStat = statReport.stats.isZero();

		/* Get the counter from cache. create and persist if non-existent */
		String counterStatKey = CounterStat.generateKey(statReport.trafficFloorKey);
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

			cStat.lock(); // lock counter for modification

			/* Zero stat is only used to clear latest rate of EXISTING counterStats. This can happen for instance,
			 * when a diversion ends and the location is deleted, so the counter will not be refreshed anymore 
			 * (deleted eventually), but is taken into account when calculating PN aggregated stats. */
			if(zeroStat) {
				cStat.updateStatsWithZero();
				return;
			}

			/* If warmup - check for end of warmup - into grace */
			if(cStat.status == Status.WARMUP_PERIOD && cStat.lastReadTime - cStat.firstReadTime > warmupPeriod) {
				cStat.status = Status.LEARNING_PERIOD;
				fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL,"Counter "+cStat.trafficFloorKey+" is in learning period");
				return;
			}

			/* Update the latest reading, latest reading time, and if not under attack - moving average. 
			 * Return the latest rate or null if first time reading. */
			boolean updateAverages = cStat.status == Status.ACTIVE || cStat.status == Status.LEARNING_PERIOD; 
			float averagePeriod = cStat.status == Status.ACTIVE ? movingAveragePeriod : gracePeriod;
			cStat.updateStats(statReport, averagePeriod, updateAverages);

			/* If grace - check for end of grace */
			if(cStat.status == Status.LEARNING_PERIOD && cStat.lastReadTime - cStat.firstReadTime > gracePeriod) {
				cStat.status = Status.ACTIVE;
				fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL,"Counter " + cStat.trafficFloorKey + " is active");
				// setup initial baselines
				aggregatePNStat(statReport.pnKey);
				periodicProcessBaselines();
				return;
			}

			if(cStat.status != Status.ACTIVE) return;

			/* Periodically record in flight recorder the counter moving averages */
			cStat.periodicallyRecordAverages(fr, dfAppRoot.baselineRecordingIntervalInSecs);

			// Aggregate and process PN level counter
			PNStatSum pnStatSum = aggregatePNStat(statReport.pnKey); // averages, latest rates from all counters	
			processPNcheckAttack(statReport.pnKey, pnStatSum);	
		}  finally {	// Persist to repo. TODO: optimize to do it periodically - according to per-PN calculations.
			try {
				log.debug("Update counter : "+cStat.toString());
				dfAppRoot.countersStatsRepo.setRow(cStat.getKey(), cStat.toRow());
			} catch ( Throwable e) {
				log.error("Failed to persist counter status for counter "+cStat.getKey());
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
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
		synchronized (pnStatSums) {
			pnStatSums.put(pnKey, pnStatSum);
		}

		return pnStatSum;	
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void processPNcheckAttack(String pnKey, PNStatSum pnStatSum) throws ExceptionControlApp {

		Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		Hashtable<String,Object> pnUpdateCells = new Hashtable<String,Object>();
		try {
			pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to get pnRow from repo for " + pnKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to process statistics for PN " + pnKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw e;
		}
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

			String averageStr = average.serialize();
			pnUpdateCells.put(PN.AVERAGES, averageStr);
			pnUpdateCells.put(PN.LATEST_RATES, pnStatSum.latestRate.serialize());
			pnUpdateCells.put(PN.LATEST_RATES_TIME, currentTime);

			if(pnStatSum.status != Status.ACTIVE ) return ;

			/* Load current attack suspicions */
			String statusOfPNSuspicions = ( String ) pnRow.get(PN.ATTACK_SUSPICIONS);
			pnStatSum.loadStatusData (statusOfPNSuspicions );

			/* Check for attacks */	
			List<ProtocolPort> attackedProtocolPorts = checkAggrForAttacks(pnStatSum);
			/* Store updated status to PN repo */
			pnUpdateCells.put(PN.ATTACK_SUSPICIONS, pnStatSum.serializeStatusData());

			if(attackedProtocolPorts == null) return; 

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

			try {
				dfAppRoot.pNsRepo.setRow(pnKey, pnUpdateCells);
			}  catch (ExceptionControlApp e) {
				log.error("Failed to update attack status in pnRow for " + pnKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw e;
			}
		}
	}

	protected void setAttackInCounters(String pnKey, List<ProtocolPort> attackedProtocolPorts,  boolean attacked) {

		CounterStat counterStat = null; 
		Iterator<Map.Entry<String,CounterStat>> iter = counterStats.entrySet().iterator();

		while(iter.hasNext()) {
			try {
				counterStat = iter.next().getValue();
				if ( counterStat == null ) {
					log.error("Unexpected null counter in detector repo" );
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;
				}

				counterStat.lock();	
				if(counterStat.pnKey.equals(pnKey) ) {
					for (ProtocolPort protocolPort:attackedProtocolPorts ) {
						counterStat.setAttacked(protocolPort.protocol.getProtocolNumber(), protocolPort.port, attacked );  
					}
				}
			}finally {
				if ( counterStat != null)
					counterStat.unlock();
			}
		}
	}

	protected List<ProtocolPort> checkAggrForAttacks(PNStatSum pnStatSum ) throws ExceptionControlApp {

		ArrayList<ProtocolPort> attackedProtocolPorts = new ArrayList<ProtocolPort>();
		List<ProtocolPort> suspectedProtocolPorts = pnStatSum.deviationExceeds(pnStatSum.movingAverage, 
				lowerDetectionDeviationPercentage, upperDetectionDeviationPercentage );	
		/* Can add as many attack detection mechanism here, adding to detected attackedProtocolPorts list */
		log.debug("Detector :"+detectorInfo.label+" Suspicions "+((suspectedProtocolPorts!=null)?suspectedProtocolPorts.size():null));

		// add attack suspicion for each detected deviation */
		// decrease attack suspicion for other protocol / ports
		pnStatSum.resetAddAttackSuspicions(suspectedProtocolPorts);

		if (suspectedProtocolPorts == null) return null;

		/* When are attack detections reporting to attack decision point:
		 * 1. If suspicions counter = 0 -> do not report;
		 * 2. If suspicions counter exceeded threshold -> report
		 * 3. If 0 < suspicions counter <= threshold -> if attack then report, if peace - not */
		for ( ProtocolPort protocolPort : suspectedProtocolPorts) {
			int nSuspict = pnStatSum.getAttackSuspicions(protocolPort);
			if (nSuspict > pnSuspicionsThreshold || (nSuspict <= pnSuspicionsThreshold && pnStatSum.isAttacked(protocolPort)))
				attackedProtocolPorts.add(protocolPort);
		}	
		log.debug("Detector :"+detectorInfo.label+" Attacked "+((attackedProtocolPorts!=null)?attackedProtocolPorts.size():null));

		return attackedProtocolPorts;
	}

	/* Add all detected attacks*/
	protected void addAllAttackDetections(String pnKey, List<ProtocolPort> attackedProtocolPorts, long currentTime) {

		Detection detection; String detectionKey;		
		for(ProtocolPort protocolPort : attackedProtocolPorts) {
			detectionKey = Detection.generateDetectionKey(detectorInfo.label, pnKey, protocolPort);
			detection = new Detection(detectionKey, detectorInfo.label, DetectionConfidence.VERY_HIGH, currentTime, 
					durationOfDetection, pnKey, protocolPort);
			fr.logRecord(DFAppRoot.FR_DF_SECURITY, "OF RateBasedDetector is adding attack detection on PN "
					+ pnKey + ", protocolPort=" + protocolPort.toString());
			dfAppRootFullImpl.attackDecisionPointImpl.addDetection(detection);
		}
	}

	/**
	 * Notify DF detector that the attack corresponding to the detection denoted by the passed in detectionKey
	 * is over. RateBasedDetectorImpl updates the state of the stat collection counters to resume calculating moving
	 * average.
	 * @param detectionKey
	 * @throws ExceptionControlApp 
	 */
	@Override
	public void notifyEndDetection(String detectionKey) {

		Hashtable<String, Object> detectionRow;
		try {
			detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to get detectionRow from repo for " + detectionKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		String pnKey = (String) detectionRow.get(Detection.PNKEY); 
		ProtocolPort protocolPort = new ProtocolPort((String) detectionRow.get( Detection.PROTOCOL_PORT) );

		// invalidate counters for case zero stat was not send
		// find traffic floor from detection via attack and mitigation
		Hashtable<String, Hashtable<String, Object>> attackTable;
		try {
			attackTable = dfAppRootFullImpl.attacksRepo.getTable();
		} catch (Throwable e1) {
			log.error("Failed to get attacksRepo table " , e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			return;
		}		
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

			Set<Entry<Object, Object>> detectionsOfAttack = attack.detectionKeys.entrySet() ;
			if ( detectionsOfAttack == null) return;
			Iterator<Map.Entry<Object,Object>> iterDetectionsOfAttack = detectionsOfAttack.iterator();
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
			try {
				Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
				Mitigation mitigation = new Mitigation(mitigationRow);
				if(mitigation.trafficFloorKeys != null && !mitigation.trafficFloorKeys.isEmpty())
					mitigationTrafficFloor = mitigation.trafficFloorKeys.get(0); 
			} catch (Throwable e) {
				log.error("Failed to lookup mitigationTrafficFloor over mitigationsRepo for mitigationKey "+mitigationKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				return;
			} 
		}

		/* Find counters used for mitigated traffic by mitigationKey and pnKey */
		if ( mitigationTrafficFloor != null ) {
			CounterStat cStat = null;
			try {
				String mitigationCounterStatKey = CounterStat.generateKey(mitigationTrafficFloor);
				cStat = counterStats.get(mitigationCounterStatKey);
				if(cStat != null) {
					cStat.lock();
					cStat.status = Status.INVALID;
					cStat.updateStatsWithZero();
					dfAppRoot.countersStatsRepo.setRow(cStat.getKey(), cStat.toRow());
				}
			} catch (Throwable e) {
				String cStatInfo = (cStat == null) ? "" : cStat.getKey();
				log.error("Failed to set invalid state to PN counter " + cStatInfo);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			} finally {
				if(cStat != null) cStat.unlock();
			}
		}	

		List<ProtocolPort> protoPorts = new ArrayList<ProtocolPort>();
		protoPorts.add(protocolPort);
		setAttackInCounters(pnKey, protoPorts, false);

		try {
			String statusOfPNSuspicions = (String)dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.ATTACK_SUSPICIONS );
			CounterStat status = new PNStatSum(pnKey);
			status.loadStatusData (statusOfPNSuspicions );
			status.setAttacked( protocolPort.protocol.getProtocolNumber(), protocolPort.port, false);
			status.setAttackSuspicions(protocolPort.protocol.getProtocolNumber(), protocolPort.port, 0);
			dfAppRootFullImpl.pNsRepo.setCell(pnKey, PN.ATTACK_SUSPICIONS, status.serializeStatusData());
		} catch (Throwable e) {
			log.error("Failed to update PN attack status for prtocolPort " + protocolPort.toString());
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	/**
	 * #### 
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void cleanup() {
		// Move over invalidated counters. If location has been deleted from PN - delete detector also
		for ( String key : counterStats.keySet() ) {
			try {
				cleanupCounterStat(key);
			} catch (Throwable e) {/* Ignore */} 
		}
	}

	/**
	 * #### 
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void cleanupCounterStat(String key) {

		CounterStat cStat = counterStats.get(key);
		if ( cStat == null || cStat.status != Status.INVALID) return;

		boolean found = false;
		Hashtable<String, Object> pnRow = null;
		try {
			pnRow = dfAppRootFullImpl.pNsRepo.getRow(cStat.pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to get pnRow from repo for " + cStat.pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		if(pnRow == null) return;

		Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String trafficFloorKey;

		while(iter.hasNext()) {

			entry = iter.next();
			if(!entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) continue;

			trafficFloorKey = (String) entry.getValue();
			if(trafficFloorKey == null) continue;

			CounterStat cPNStat = new CounterStat (trafficFloorKey, cStat.pnKey);
			if (cPNStat.getKey().equals(key)) {				
				found = true; // This traffic floor still exists in the PN - do nothing
				break;
			}
		}

		// Traffic floor doesn't exist in the PN - clean counter
		if ( ! found) {
			try {
				dfAppRoot.countersStatsRepo.deleteRow(key);
				counterStats.remove(key);
			} catch (Throwable e) {
				log.error("Failed to delete counter. " + cStat.pnKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);	
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

		if(!fMain.isOpenForBusiness()) return; // Operate only after everything is initialized and is not terminating

		/* Set sums into PN repo, and check for attacks */
		synchronized ( pnStatSums ) {
			Map.Entry<String,PNStatSum> entry;
			Iterator<Entry<String, PNStatSum>> pnStatSumIter = pnStatSums.entrySet().iterator();

			while(pnStatSumIter.hasNext()) {
				entry = pnStatSumIter.next();
				String pnKey = entry.getKey();
				TrafficTuple average = entry.getValue().movingAverage;

				if(average.isZero()) continue;	

				try {					
					Hashtable<String,Object> pnRow = dfAppRoot.pNsRepo.getRow(pnKey);
					if(pnRow == null) continue;
					Hashtable<String,Object> pnUpdateCells = new Hashtable<String,Object>();
					long currentTime = System.currentTimeMillis() / 1000;
					String averageStr = average.serialize();
					pnUpdateCells.put(PN.BASELINES, averageStr);
					pnUpdateCells.put(PN.BASELINES_TIME, currentTime);
					dfAppRoot.pNsRepo.setRow(pnKey, pnUpdateCells);					
					fr.logRecord(DFAppRoot.FR_DF_SECURITY,"Baselines for PN " + pnKey + ": " + averageStr);
				} catch (ExceptionControlApp e) {
					log.error("Excepted trying to update baselines for pnRow. " + pnKey, e);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				}	
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

		Object obj = null;
		obj = row.get(MOVING_AVERAGE_PERIOD); 
		if ( obj != null ) movingAveragePeriod = Integer.valueOf(obj.toString());
		obj = row.get(GRACE_PERIOD); 
		if ( obj != null ) gracePeriod = Integer.valueOf(obj.toString());
		obj = row.get(WARMUP_PERIOD); 
		if ( obj != null ) warmupPeriod = Integer.valueOf(obj.toString());
		obj = row.get(UPPER_DETECTION_DEVIATION_PERCENTAGE); 
		if ( obj != null ) upperDetectionDeviationPercentage = Integer.valueOf(obj.toString());
		obj = row.get(LOWER_DETECTION_DEVIATION_PERCENTAGE); 
		if ( obj != null ) lowerDetectionDeviationPercentage = Integer.valueOf(obj.toString());
		obj = row.get(DURATION_OF_DETECTION); 
		if ( obj != null ) durationOfDetection = Integer.valueOf(obj.toString());
		obj = row.get(BASELINES_PROCESSING_INTERVAL); 
		if ( obj != null ) baselinesProcessingInterval = Long.valueOf(obj.toString());
		obj = row.get(PN_SUSPICIONS_THRESHOLD); 
		if ( obj != null ) pnSuspicionsThreshold = Integer.valueOf(obj.toString());
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
