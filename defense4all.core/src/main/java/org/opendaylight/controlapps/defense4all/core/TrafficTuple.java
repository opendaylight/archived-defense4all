/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opendaylight.controlapps.defense4all.core.ProtocolPort.DFProtocol;

public class TrafficTuple {
	
	protected static final String TUPLE_SERIALIZATION_DELIMITER = "::";
	protected static final String TRAFFIC_DATA_SERIALIZATION_DELIMITER = ":";
	
	public class TrafficData {
		
		public int protocol;		// 6-tcp, 17-udp, 1-icmp, 0- other
		public int port;			// Relevant only for tcp and udp
		public float bytes;
		public float packets;
		public boolean forTrafficLearning;
		
		public TrafficData() {
			this.protocol = 0; this.port = 0; this.bytes = 0; this.packets = 0;	this.forTrafficLearning = false;
		}
		
		public TrafficData(TrafficData other) {
			this.protocol = other.protocol;
			this.port = other.port;
			this.bytes = other.bytes;
			this.packets = other.packets;
			this.forTrafficLearning = other.forTrafficLearning;
		}
	}
	
	protected Hashtable<Integer,TrafficData> tuple;
	
	protected static int generateTrafficDataKey(int protocol, int port) {
		return (protocol<<16) + port;
	}
	
	public TrafficData getTrafficData(int protocol, int port) {
		int key = generateTrafficDataKey(protocol, port);
		return tuple.get(key);
	}
	
	public float getTrafficBytes(int protocol, int port) {
		int key = generateTrafficDataKey(protocol, port);
		return tuple.get(key).bytes;
	}
	
	public float getTrafficPackets(int protocol, int port) {
		int key = generateTrafficDataKey(protocol, port);
		return tuple.get(key).packets;
	}
	
	public void setTrafficData(int protocol, int port, float bytes, float packets, boolean forTrafficLearning) {
		
		TrafficData trafficData = new TrafficData();
		trafficData.protocol = protocol;
		trafficData.port = port;
		trafficData.bytes = bytes;
		trafficData.packets = packets;
		trafficData.forTrafficLearning = forTrafficLearning;
		
		tuple.put(generateTrafficDataKey(protocol, port), trafficData);
	}
	
	public TrafficTuple() {
		tuple = new Hashtable<Integer,TrafficData>();
	}

	public void zero() {
		
		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData;
		while(iter.hasNext()) {
			trafficData = iter.next().getValue();
			trafficData.bytes = trafficData.packets = 0;
		}
	}
	
	public boolean isZero() {
		
		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData;
		while(iter.hasNext()) {
			trafficData = iter.next().getValue();
			if(trafficData.bytes != 0 || trafficData.packets != 0) return false;
		}
		return true;
	}

	/* Inflate from concatinated thresholds string. Use the structure from Spring: 
	 * tcpsynportbytes:tcpsynportpackets:tcpportbytes:tcpportpackets:tcpbytes:tcppackets:
	 * udpportbytes:udpportpackets:udpbytes:udppackets:icmpbytes:icmppackets:otherbytes:otherpackets
	 */
	public TrafficTuple(String trafficTupleStr) {
		
		this();
		
		if(trafficTupleStr == null || trafficTupleStr.length() == 0) return;
		
		String[] split1 = trafficTupleStr.split(TUPLE_SERIALIZATION_DELIMITER);
		String[] split2; TrafficData trafficData;
		
		for(String trafficDataStr : split1) {
			
			if(trafficDataStr.length() == 0) continue;
			split2 = trafficDataStr.split(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			if(split2.length < 5) continue;

			trafficData = new TrafficData();
			trafficData.protocol = Short.valueOf(split2[0]);
			trafficData.port = Short.valueOf(split2[1]);
			trafficData.bytes = Float.valueOf(split2[2]);
			trafficData.packets = Float.valueOf(split2[3]);
			trafficData.forTrafficLearning = Boolean.valueOf(split2[4]);
			
			tuple.put(generateTrafficDataKey(trafficData.protocol, trafficData.port), trafficData);
		}		
	}

	private TrafficTuple nonZeroClone() {
		
		TrafficTuple tt = new TrafficTuple();

		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData;
		while(iter.hasNext()) {
			trafficData = new TrafficData(iter.next().getValue());
			if(trafficData.bytes == 0) trafficData.bytes = 1;
			if(trafficData.packets == 0) trafficData.packets = 1;
			tt.tuple.put(generateTrafficDataKey(trafficData.protocol, trafficData.port), trafficData);
		}
		
		return tt;
	}

	/**
	 * ####
	 * @return
	 */
	public String serialize() {
		
		if ( tuple == null || tuple.isEmpty()) return "";

		StringBuilder sb = new StringBuilder(); TrafficData trafficData;
		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		
		while(iter.hasNext()) {
			trafficData = iter.next().getValue();
			sb.append(trafficData.protocol); sb.append(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			sb.append(trafficData.port); sb.append(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			sb.append(trafficData.bytes); sb.append(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			sb.append(trafficData.packets); sb.append(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			sb.append(trafficData.forTrafficLearning); sb.append(TUPLE_SERIALIZATION_DELIMITER);
		}
		
		sb.setLength(sb.length() - TUPLE_SERIALIZATION_DELIMITER.length());
		return sb.toString();
	}

	public TrafficTuple delta(TrafficTuple lower, float timePeriod) {

		if(lower == null) return this;

		TrafficTuple tt = new TrafficTuple();
		if(timePeriod == 0) return tt ;

		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData; TrafficData trafficDelta;

		while(iter.hasNext()) {
			trafficData = iter.next().getValue();
			TrafficData lowerTrafficData = lower.getTrafficData(trafficData.protocol, trafficData.port);
			if ( lowerTrafficData == null ) {
				lowerTrafficData = new TrafficData();
			}

			// port, protocol and flag are same as source
			// bytes and packets should be calculated in terms of rates
			trafficDelta = new TrafficData(trafficData);
			trafficDelta.bytes = ( trafficData.bytes - lowerTrafficData.bytes ) / timePeriod;
			trafficDelta.packets = ( trafficData.packets - lowerTrafficData.packets ) / timePeriod;

			tt.tuple.put(generateTrafficDataKey(trafficDelta.protocol, trafficDelta.port), trafficDelta);
		}

		return tt;
	}

	/* Use the following formula to update the averages:
	 * newAverage = (currentAverage * periodTime + latest * latestTime) / (periodTime + latestTime); 
	 * */
	public void updateAverage(TrafficTuple latest, float movingAveragePeriod, float latestPeriod) {
		
		if(latest == null) return;
		float sumTime = movingAveragePeriod + latestPeriod;
		if(sumTime == 0) sumTime = 1;
		
		// Update movingAverage for received protocol/port data
		Iterator<Map.Entry<Integer,TrafficData>> iter = latest.tuple.entrySet().iterator();
		TrafficData latestTrafficData;

		while(iter.hasNext()) {
			latestTrafficData = iter.next().getValue();
			if ( !latestTrafficData.forTrafficLearning ) continue;
			
			int key = generateTrafficDataKey(latestTrafficData.protocol, latestTrafficData.port);
			if ( ! tuple.containsKey(key))
				tuple.put(key, new TrafficData (latestTrafficData));
			else {
				tuple.get(key).bytes = ( tuple.get(key).bytes * movingAveragePeriod + latestTrafficData.bytes * latestPeriod) / sumTime;
				tuple.get(key).packets = ( tuple.get(key).packets * movingAveragePeriod + latestTrafficData.packets * latestPeriod) / sumTime;
			}		
		}
	}

	public void add(TrafficTuple other) {

		if(other == null) return;
		
		// Update movingAverage for received protocol/port data
		Iterator<Map.Entry<Integer,TrafficData>> iter = other.tuple.entrySet().iterator();
		TrafficData otherTrafficData;

		while(iter.hasNext()) {
			otherTrafficData = iter.next().getValue();
			int key = generateTrafficDataKey(otherTrafficData.protocol, otherTrafficData.port);
			if ( ! tuple.containsKey(key))
				tuple.put(key, new TrafficData (otherTrafficData));
			else {
				tuple.get(key).bytes = tuple.get(key).bytes + otherTrafficData.bytes ;
				tuple.get(key).packets = tuple.get(key).packets + otherTrafficData.packets ;
			}		
		}
	}
		
	public List<ProtocolPort> deviationExceeds(TrafficTuple from, int deviationPercentage) {
		
		if(from == null) return null;		
		float deviationFraction = ((float) deviationPercentage) / 100;
		ArrayList<ProtocolPort> exceededDeviations = new ArrayList<ProtocolPort>();
		TrafficTuple nonZeroFrom = from.nonZeroClone();
		
		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData; int trafficKey; ProtocolPort protocolPort;
		
		while(iter.hasNext()) {
			trafficKey  = iter.next().getKey();
			trafficData = tuple.get(trafficKey);
			float fromBytes = nonZeroFrom.tuple.get(trafficKey).bytes;
			float fromPackets = nonZeroFrom.tuple.get(trafficKey).packets;
		
			if ((trafficData.bytes  - fromBytes) /  fromBytes > deviationFraction ||
					(trafficData.packets - fromPackets) / fromPackets > deviationFraction) {
				protocolPort = new ProtocolPort( DFProtocol.getProtocol(trafficData.protocol), trafficData.port);
				exceededDeviations.add(protocolPort);
			}
		}
		
		return exceededDeviations.isEmpty() ? null : exceededDeviations;
	}
}
