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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.opendaylight.controlapps.defense4all.core.StatReport;
import org.opendaylight.controlapps.defense4all.core.TrafficTuple;
import org.opendaylight.controlapps.framework.core.RepoCD;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class CounterStat {
	
	/* CounterStat statuses */
	public enum Status {
		INVALID,
		WARMUP_PERIOD, 			// Warm-up time - averages are not updated, neither attacks are suspected
		GRACE_PERIOD, 			// Averages are updated, but attacks are not suspected
		ACTIVE					// Attacks may be suspected
	}
	
	/* CounterStat column names */
	public static final String LOCATION = "location";
	public static final String PNKEY = "pnkey";
	public static final String LAST_READING = "last_reading";
	public static final String LATEST_RATE = "latest_rate";
	public static final String LAST_READING_TIME = "last_reading_time";
	public static final String MOVING_AVERAGE = "moving_average";
	public static final String NUMOF_ATTACK_SUSPICIONS = "numof_attack_suspicions";
	public static final String STATUS = "status";
	public static final String DVSN_STAT = "dvsn_stat";

	public String key;		// key in repo - location + "_" + pnKey
	public String location;	// serialized Counter location (CounterLocation.serialize())
	public String pnKey;
	public String lastReadingStr;		public TrafficTuple lastReading;	// bytes/packets counter reading
	public String latestRateStr;		public TrafficTuple latestRate;		// bytes/packets per second
	public String movingAverageStr; 	public TrafficTuple movingAverage;	// bytes/packets per second
	public long   lastReadTime;	// Time of last reading	
	public long   firstReadTime;	// Time of first reading - used to determine the end of grace period	
	public Status status;
	public int 	  numofAttackSuspicions;
	public boolean attacked; 		// Flag indicating whether at the PN level attack has been detected
//	public boolean dvsnStat;  - Konsta 
	
	protected static ArrayList<RepoCD> mCounterStatRepoCDs = null;
	
	public static String generateKey(String location, String pnKey) {return location + "_" + pnKey;}
	
	public void printTCP() {
		
		float latestTcpbytes = 0; float latestTcppackets = 0; float averageTcpbytes = 0; float averageTcppackets = 0;
		if(latestRate != null) {
			latestTcpbytes = latestRate.getTrafficBytes(6, 0); 
			latestTcppackets = latestRate.getTrafficPackets(6, 0);
		}
		if(movingAverage != null) {
			averageTcpbytes = movingAverage.getTrafficBytes(6, 0); 
			averageTcppackets = movingAverage.getTrafficPackets(6, 0);
		}

		System.out.println("Counter    for " + location + ":\tlatest=" + (int)Math.ceil(latestTcpbytes) + "/" + 
			(int)Math.ceil(latestTcppackets) + ",\t" + "averages=" + (int)Math.ceil(averageTcpbytes) + "/" + 
			(int)Math.ceil(averageTcppackets) + ";\t"  + "suspicions=" + numofAttackSuspicions + ";\t" + status /* Konsta +
			 ";\tdiversion stat=" + dvsnStat */);
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public CounterStat() {
		key = null; location = null; pnKey = null; movingAverageStr = null; movingAverage = null; 
		lastReadingStr = null; lastReading = null; latestRateStr = null; latestRate = null; 
		lastReadTime = 0; firstReadTime = 0; status = Status.INVALID; 
		numofAttackSuspicions = 0; attacked = false; /* Konsta dvsnStat = false */;
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws
	 */
	public CounterStat(String location, String pnKey/* Konsta , boolean dvsnStat*/) {
		this();
		this.location = location;
		this.pnKey = pnKey;
		/* Konsta this.dvsnStat = dvsnStat;*/
		key = generateKey(location, pnKey);
	}

	public CounterStat(Hashtable<String, Object> counterStatRow) {
		this();
		location = (String) counterStatRow.get(LOCATION);
		pnKey = (String) counterStatRow.get(PNKEY);
		key = location + "_" + pnKey;
		movingAverageStr = (String) counterStatRow.get(MOVING_AVERAGE); movingAverage = new TrafficTuple(movingAverageStr);
		lastReadingStr = (String) counterStatRow.get(LAST_READING); lastReading = new TrafficTuple(lastReadingStr);
		latestRateStr = (String) counterStatRow.get(LATEST_RATE); latestRate = new TrafficTuple(latestRateStr);
		lastReadTime = (Long) counterStatRow.get(LAST_READING_TIME);
		numofAttackSuspicions = (Integer) counterStatRow.get(NUMOF_ATTACK_SUSPICIONS);
		status = Status.valueOf((String) counterStatRow.get(STATUS));
		/*Konsta dvsnStat = (Boolean) counterStatRow.get(DVSN_STAT);*/
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(location == null) location = "";
		if(pnKey == null) pnKey = "";
		if(movingAverage == null) movingAverage = new TrafficTuple(); movingAverageStr = movingAverage.serialize();
		if(lastReading == null) lastReading = new TrafficTuple(); lastReadingStr = lastReading.serialize();
		if(latestRate == null) latestRate = new TrafficTuple(); latestRateStr = latestRate.serialize();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(LOCATION, location);
		row.put(PNKEY, pnKey);
		row.put(MOVING_AVERAGE, movingAverageStr);
		row.put(LAST_READING, lastReadingStr);
		row.put(LATEST_RATE, latestRateStr);
		row.put(LAST_READING_TIME, lastReadTime);
		row.put(STATUS, status.name());
		row.put(NUMOF_ATTACK_SUSPICIONS, numofAttackSuspicions);
		/* Konsta row.put(DVSN_STAT, dvsnStat);*/
		return row;
	}
	
	public static List<RepoCD> getCounterStatsRCDs() {

		if(mCounterStatRepoCDs == null) {
			RepoCD rcd;
			mCounterStatRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(LOCATION, StringSerializer.get(), null);	mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);	mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(MOVING_AVERAGE, StringSerializer.get(), null);	mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(LAST_READING, StringSerializer.get(), null);	mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(LATEST_RATE, StringSerializer.get(), null);	mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(LAST_READING_TIME, LongSerializer.get(), null);	mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(STATUS, StringSerializer.get(), null); mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(NUMOF_ATTACK_SUSPICIONS, IntegerSerializer.get(), null); mCounterStatRepoCDs.add(rcd);
			rcd = new RepoCD(DVSN_STAT, BooleanSerializer.get(), null); mCounterStatRepoCDs.add(rcd);
		}		
		return mCounterStatRepoCDs;
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void updateStats(StatReport statsReport, float averagePeriod, boolean updateAverage) {
		
		/* Check if first reading */
		if(firstReadTime == 0) {
			firstReadTime = lastReadTime = statsReport.readingTime;
			lastReading = statsReport.stats;
			return;
		}
		
		long thisPeriod = statsReport.readingTime - lastReadTime; // Calculate this period
		lastReadTime = statsReport.readingTime; 				  // Update the time of last reading
		latestRate = statsReport.stats.delta(lastReading, thisPeriod); // Calculate latest rates per new stats reading
		lastReading = statsReport.stats; 						  // Set last reading to this one
		if(updateAverage) 			// Update moving average. In 2nd reading moving average is set for the first time
			movingAverage.updateAverage(latestRate, averagePeriod, thisPeriod);
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void updateStatsWithZero() {
		latestRate.zero();
	}
}
