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
import java.util.concurrent.locks.ReentrantLock;

import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.StatReport;
import org.opendaylight.defense4all.core.TrafficTuple;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.TrafficTuple.TrafficData;
import org.opendaylight.defense4all.framework.core.RepoCD;

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
	
	protected static final String COUNTER_STATUS_SERIALIZATION_DELIMITER = "::";
	protected static final String COUNTER_STATUS_DATA_SERIALIZATION_DELIMITER = ":";
	
	/* CounterStat column names */
	public static final String LOCATION = "location";
	public static final String PNKEY = "pnkey";
	public static final String LAST_READING = "last_reading";
	public static final String LATEST_RATE = "latest_rate";
	public static final String LAST_READING_TIME = "last_reading_time";
	public static final String MOVING_AVERAGE = "moving_average";
	public static final String NUMOF_ATTACK_SUSPICIONS = "numof_attack_suspicions";
	public static final String STATUS = "status";
	public static final String COUNTERS_STATUS = "counters_status";

	private final String key;		// key in repo - location + "_" + pnKey
	public String location;			// serialized Counter location (CounterLocation.serialize())
	public String pnKey;
	public String lastReadingStr;		public TrafficTuple lastReading;	// bytes/packets counter reading
	public String latestRateStr;		public TrafficTuple latestRate;		// bytes/packets per second
	public String movingAverageStr; 	public TrafficTuple movingAverage;	// bytes/packets per second
	public long   lastReadTime;		// Time of last reading	
	public long   firstReadTime;	// Time of first reading - used to determine the end of grace period	
	public Status status;
	
	// to allow locking of the Counter object from Detectors methods
	private final ReentrantLock lock = new ReentrantLock();
	public void lock() { this.lock.lock(); }
	public void unlock() { this.lock.unlock(); }
	
	public class CounterStatusData {
		
		public int protocol;		// 6-tcp, 17-udp, 1-icmp, 0- other
		public int port;			// Relevant only for tcp and udp

		private boolean 	attacked;
		private int 	  	numofAttackSuspicions;
		
		public CounterStatusData() {
			this.attacked = false; this.numofAttackSuspicions = 0;
		}

		public CounterStatusData(int protocol, int port) {
			this.protocol = protocol; this.port = port;
			this.attacked = false; this.numofAttackSuspicions = 0;
		}

		public CounterStatusData(CounterStatusData other) {
			this.protocol = other.protocol; 
			this.port = other.port;
			this.attacked = other.attacked;
			this.numofAttackSuspicions = other.numofAttackSuspicions;
		}
	}
	
	// Map to store metadata about counters in the TrafficTuples
	// The key should be equal with the TrafficTuple key
	private Hashtable<Integer,CounterStatusData> countersStatus;
	
	protected static ArrayList<RepoCD> mCounterStatRepoCDs = null;

	/** ### Description ###
	 * @param param_name 
	 * @throws
	 */
	public CounterStat(String location, String pnKey) {
		this.location = location;
		this.pnKey = pnKey;
		key = generateKey(location, pnKey);
		countersStatus = new  Hashtable<Integer,CounterStatusData>() ;
	}
	
	public int getAttackSuspicions ( ProtocolPort protocolPort ) {	
		return  getAttackSuspicions ( protocolPort.protocol.getProtocolNumber(), protocolPort.port);  
	}
	
	public int getAttackSuspicions ( int protocol, int port ) {
		int key = TrafficTuple.generateTrafficDataKey(protocol, port);
		if ( ! countersStatus.containsKey(key)) 
			// this means statistic is processed comes for port / protocol first time
			countersStatus.put(key, new CounterStatusData(protocol, port));
		return countersStatus.get(key).numofAttackSuspicions;		
	}
	
	public void setAttackSuspicions ( int protocol, int port, int counter ) {
		int key = TrafficTuple.generateTrafficDataKey(protocol, port);
		if ( ! countersStatus.containsKey(key)) 
			// this means statistic is processed comes for port / protocol first time
			countersStatus.put(key, new CounterStatusData(protocol, port));

		countersStatus.get(key).numofAttackSuspicions = counter;
	}
	
	public boolean isAttacked ( int protocol, int port ) {
		int key = TrafficTuple.generateTrafficDataKey(protocol, port);
		if ( ! countersStatus.containsKey(key)) 
			// this means statistic is processed comes for port / protocol first time
			countersStatus.put(key, new CounterStatusData(protocol, port));
		return countersStatus.get(key).attacked;
	}
	
	public boolean isAttacked ( ProtocolPort protocolPort ) {
		return  isAttacked ( protocolPort.protocol.getProtocolNumber(), protocolPort.port);  
	}
	
	public void setAttacked ( int protocol, int port, boolean attacked ) {
		int key = TrafficTuple.generateTrafficDataKey(protocol, port);
		if ( ! countersStatus.containsKey(key)) 
			// this means statistic is processed comes for port / protocol first time
			countersStatus.put(key, new CounterStatusData(protocol, port));

		countersStatus.get(key).attacked = attacked;
	}
	
	// copy only attacked flag for use in aggregated statistics
	public void copyAttacked (CounterStat other) {
		Iterator<Map.Entry<Integer,CounterStatusData>> iter = other.countersStatus.entrySet().iterator();
		while(iter.hasNext()) {
			CounterStatusData counterStatusData = iter.next().getValue();
			if ( counterStatusData.attacked)
				setAttacked (counterStatusData.protocol, counterStatusData.port, true);	
		}
	}
	
	public boolean validateAttackSuspicions ( int threshold ) {
		Iterator<Map.Entry<Integer,CounterStatusData>> iter = countersStatus.entrySet().iterator();
		while(iter.hasNext()) {
			if ( iter.next().getValue().numofAttackSuspicions >  threshold)
				return true;
		}
		return false;
	}

	// zero attack suspicions for Protocol/port not in the attacked list
	public void resetAddAttackSuspicions(List<ProtocolPort> suspicList) {
		Iterator<Map.Entry<Integer,CounterStatusData>> iter = countersStatus.entrySet().iterator();
		while(iter.hasNext()) {
			CounterStatusData entryData = iter.next().getValue();
			DFProtocol protocol = DFProtocol.getProtocol(entryData.protocol);
			if (suspicList == null ||  ! suspicList.contains(new ProtocolPort(protocol, entryData.port))) {
				entryData.numofAttackSuspicions --;
				if(entryData.numofAttackSuspicions < 0) entryData.numofAttackSuspicions = 0;
			} else {
				entryData.numofAttackSuspicions ++;
			}
		}
		
	}
		
	public static String generateKey(String location, String pnKey) {return location + "_" + pnKey;}
	
	public String getKey() { return key; }
	
	public void printTCP() {
		
		float latestTcpbytes = 0; float latestTcppackets = 0; float averageTcpbytes = 0; float averageTcppackets = 0;
		int numofAttackSuspicions=0; boolean attacked=false;
		if(latestRate != null) {
			latestTcpbytes = latestRate.getTrafficBytes(6, 0); 
			latestTcppackets = latestRate.getTrafficPackets(6, 0);
		}
		if(movingAverage != null) {
			averageTcpbytes = movingAverage.getTrafficBytes(6, 0); 
			averageTcppackets = movingAverage.getTrafficPackets(6, 0);
		}
		if ( countersStatus != null) {
			numofAttackSuspicions = getAttackSuspicions (6,0);
			attacked = isAttacked(6,0);
		}

		System.out.println("Counter    for " + location + ":\tlatest=" + (int)Math.ceil(latestTcpbytes) + "/" + 
			(int)Math.ceil(latestTcppackets) + ",\t" + "averages=" + (int)Math.ceil(averageTcpbytes) + "/" + 
			(int)Math.ceil(averageTcppackets) + ";\t"+"suspicions=" + numofAttackSuspicions + ";\t" + status  +
			 ";\tattacked stat=" + attacked );
	}

	
	 public Hashtable<Integer,CounterStatusData> loadStatusData (String counterStatusStr) {
		
		countersStatus = new  Hashtable<Integer,CounterStatusData>() ;
		
		if(counterStatusStr == null || counterStatusStr.length() == 0) return countersStatus;
		
		String[] split1 = counterStatusStr.split(COUNTER_STATUS_SERIALIZATION_DELIMITER);
		String[] split2; CounterStatusData counterStatusData;
		
		for(String trafficDataStr : split1) {
			
			if(trafficDataStr.length() == 0) continue;
			split2 = trafficDataStr.split(COUNTER_STATUS_DATA_SERIALIZATION_DELIMITER);
			if(split2.length < 4) continue;
			counterStatusData = new CounterStatusData();
			counterStatusData.protocol = Short.valueOf(split2[0]);
			counterStatusData.port = Short.valueOf(split2[1]);
			counterStatusData.attacked = Boolean.valueOf(split2[2]);
			counterStatusData.numofAttackSuspicions = Integer.valueOf(split2[3]);
			
			countersStatus.put(TrafficTuple.generateTrafficDataKey(counterStatusData.protocol, counterStatusData.port), counterStatusData);
		}
		return countersStatus;
	}
	
	public String serializeStatusData() {

		if ( countersStatus == null || countersStatus.isEmpty()) return "";

		StringBuilder sb = new StringBuilder(); CounterStatusData counterStatusData;
		Iterator<Map.Entry<Integer,CounterStatusData>> iter = countersStatus.entrySet().iterator();

		while(iter.hasNext()) {
			counterStatusData = iter.next().getValue();
			sb.append(counterStatusData.protocol); sb.append(COUNTER_STATUS_DATA_SERIALIZATION_DELIMITER);
			sb.append(counterStatusData.port); sb.append(COUNTER_STATUS_DATA_SERIALIZATION_DELIMITER);
			sb.append(counterStatusData.attacked); sb.append(COUNTER_STATUS_DATA_SERIALIZATION_DELIMITER);
			sb.append(counterStatusData.numofAttackSuspicions); 
			sb.append(COUNTER_STATUS_SERIALIZATION_DELIMITER);
		}

		sb.setLength(sb.length() - COUNTER_STATUS_SERIALIZATION_DELIMITER.length());
		return sb.toString();
	}	

	public CounterStat(Hashtable<String, Object> counterStatRow) {
		location = (String) counterStatRow.get(LOCATION);
		pnKey = (String) counterStatRow.get(PNKEY);
		key = location + "_" + pnKey;
		movingAverageStr = (String) counterStatRow.get(MOVING_AVERAGE); movingAverage = new TrafficTuple(movingAverageStr);
		lastReadingStr = (String) counterStatRow.get(LAST_READING); lastReading = new TrafficTuple(lastReadingStr);
		latestRateStr = (String) counterStatRow.get(LATEST_RATE); latestRate = new TrafficTuple(latestRateStr);
		lastReadTime = (Long) counterStatRow.get(LAST_READING_TIME);
		status = Status.valueOf((String) counterStatRow.get(STATUS));
		countersStatus = loadStatusData ((String) counterStatRow.get((COUNTERS_STATUS)));
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
		row.put(COUNTERS_STATUS,serializeStatusData());
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
			rcd = new RepoCD(COUNTERS_STATUS, StringSerializer.get(), null); mCounterStatRepoCDs.add(rcd);
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
		
		if  ( status == Status.INVALID )
			return;
		
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
			updateAverage(latestRate, averagePeriod, thisPeriod);
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
	

	/* Use the following formula to update the averages:
	 * newAverage = (currentAverage * periodTime + latest * latestTime) / (periodTime + latestTime); 
	 * */
	public void updateAverage(TrafficTuple latestRate, float movingAveragePeriod, float latestPeriod) {

		if(latestRate == null) return;
		float sumTime = movingAveragePeriod + latestPeriod;
		if(sumTime == 0) sumTime = 1;

		// Update movingAverage for received protocol/port data
		Iterator<Map.Entry<Integer,TrafficData>> iter = latestRate.getTuple().entrySet().iterator();
		TrafficData latestTrafficData;

		while(iter.hasNext()) {
			latestTrafficData = iter.next().getValue();

			if ( !latestTrafficData.forTrafficLearning ) continue;

			int key = TrafficTuple.generateTrafficDataKey(latestTrafficData.protocol, latestTrafficData.port);
			if ( ! movingAverage.getTuple().containsKey(key))
				movingAverage.setTrafficData(latestTrafficData);
			else {
				// Average for same direction only
				// It will take out attacked flow 
				if ( isAttacked( latestTrafficData.protocol, latestTrafficData.port)   ) continue;
				if ( latestTrafficData.direction != movingAverage.getTrafficData(key).direction ) continue;
				movingAverage.getTrafficData(key).bytes = ( movingAverage.getTrafficData(key).bytes * movingAveragePeriod + latestTrafficData.bytes * latestPeriod) / sumTime;
				movingAverage.getTrafficData(key).packets = ( movingAverage.getTrafficData(key).packets * movingAveragePeriod + latestTrafficData.packets * latestPeriod) / sumTime;
			}		
		}
	}

		public List<ProtocolPort> deviationExceeds(TrafficTuple average, int lowerDeviationPercentage , int upperDeviationPercentage) {
			
			if(average == null) return null;	
			
			ArrayList<ProtocolPort> exceededDeviations = new ArrayList<ProtocolPort>();
			TrafficTuple nonZeroAverage = average.nonZeroClone();
			
			Iterator<Map.Entry<Integer,TrafficData>> iter = latestRate.getTuple().entrySet().iterator();
			TrafficData trafficData; int trafficKey; ProtocolPort protocolPort;
			
			while(iter.hasNext()) {		
				trafficKey  = iter.next().getKey();
				trafficData = latestRate.getTrafficData(trafficKey);
				
				if ( !nonZeroAverage.getTuple().containsKey(trafficKey )) 
			 			continue;
				
				float averageBytes = nonZeroAverage.getTuple().get(trafficKey).bytes;
				float averagePackets = nonZeroAverage.getTuple().get(trafficKey).packets;
				boolean attacked = isAttacked( trafficData.protocol, trafficData.port) ;
				int deviationPercentage = attacked ? lowerDeviationPercentage : upperDeviationPercentage;
				float deviationFraction = ((float) deviationPercentage) / 100;

				if ( trafficData.bytes == 0 || trafficData.packets == 0)
					continue;
				
				if ((trafficData.bytes  - averageBytes) /  averageBytes > deviationFraction ||
						(trafficData.packets - averagePackets) / averagePackets > deviationFraction) {
					protocolPort = new ProtocolPort( DFProtocol.getProtocol(trafficData.protocol), trafficData.port);
													
					exceededDeviations.add(protocolPort);
				} 
			}
			return exceededDeviations.isEmpty() ? null : exceededDeviations;
		}
}
