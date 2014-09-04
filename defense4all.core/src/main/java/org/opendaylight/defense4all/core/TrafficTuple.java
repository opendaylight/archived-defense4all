/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.core;

import java.lang.IllegalArgumentException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.opendaylight.defense4all.core.Traffic.TrafficDirection;
import org.opendaylight.defense4all.core.interactionstructures.Bandwidth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TrafficTuple {

	protected static final String TUPLE_SERIALIZATION_DELIMITER = "::";
	protected static final String TRAFFIC_DATA_SERIALIZATION_DELIMITER = ":";

	public static Logger log = LoggerFactory.getLogger(TrafficTuple.class);

	public class TrafficData {

		public int protocol;		// 6-tcp, 17-udp, 1-icmp, 0- other
		public int port;			// Relevant only for tcp and udp
		public float bytes;
		public float packets;
		public boolean forTrafficLearning;
		public TrafficDirection direction; 

		public TrafficData() {
			this.protocol = 0; this.port = 0; this.bytes = 0; this.packets = 0;	this.forTrafficLearning = false;
			direction = TrafficDirection.INVALID; 
		}

		public TrafficData(TrafficData other) {
			this.protocol = other.protocol;
			this.port = other.port;
			this.bytes = other.bytes;
			this.packets = other.packets;
			this.forTrafficLearning = other.forTrafficLearning;
			this.direction = other.direction;
		}
		
		public String toPrintableString () {
			return ( ProtocolPort.DFProtocol.getProtocol(protocol).name() + " bytes: "+bytes+ " packets: "+packets); 
		}
	}

	protected Hashtable<Integer,TrafficData> tuple;

	public Hashtable<Integer,TrafficData> getTuple() {
		return tuple;
	}

	public static int generateTrafficDataKey(int protocol, int port) {
		return (protocol<<16) + port;
	}

	public TrafficData getTrafficData(int protocol, int port) {
		int key = generateTrafficDataKey(protocol, port);
		return tuple.get(key);
	}

	public TrafficData getTrafficData(int key) {
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

	public boolean isForTrafficLearning () {
		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		while(iter.hasNext()) {
			if ( iter.next().getValue().forTrafficLearning ) 
				return true;
		}
		return false;
	}

	public void setTrafficData(int protocol, int port, float bytes, float packets, boolean forTrafficLearning, 
			TrafficDirection direction) {
		TrafficData trafficData = new TrafficData();
		trafficData.protocol = protocol;
		trafficData.port = port;
		trafficData.bytes = bytes > 0 ? bytes : 0;
		trafficData.packets = packets > 0 ? packets : 0;
		trafficData.forTrafficLearning = forTrafficLearning;
		trafficData.direction = direction;

		tuple.put(generateTrafficDataKey(protocol, port), trafficData);
	}

	public void setTrafficData(TrafficData trafficData ) { 
		tuple.put(generateTrafficDataKey(trafficData.protocol, trafficData.port), new TrafficData(trafficData));
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
			if(trafficData.bytes > 0 || trafficData.packets > 0) return false;
		}
		return true;
	}

	/* Inflate from concatinated thresholds string. Use the structure from Spring: 
	 * tcpsynportbytes:tcpsynportpackets:tcpportbytes:tcpportpackets:tcpbytes:tcppackets:
	 * udpportbytes:udpportpackets:udpbytes:udppackets:icmpbytes:icmppackets:otherbytes:otherpackets
	 */
	public TrafficTuple(String trafficTupleStr) throws IllegalArgumentException {

		this();

		if(trafficTupleStr == null || trafficTupleStr.length() == 0) return;

		String[] split1 = trafficTupleStr.split(TUPLE_SERIALIZATION_DELIMITER);
		String[] split2; TrafficData trafficData;

		for(String trafficDataStr : split1) {

			if(trafficDataStr.length() == 0) continue;
			split2 = trafficDataStr.split(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			if(split2.length < 6) continue;

			trafficData = new TrafficData();
			try {
				trafficData.protocol = Short.valueOf(split2[0]);
				trafficData.port = Short.valueOf(split2[1]);
				trafficData.bytes = Float.valueOf(split2[2]);
				trafficData.packets = Float.valueOf(split2[3]);
				trafficData.forTrafficLearning = Boolean.valueOf(split2[4]);
				trafficData.direction = TrafficDirection.valueOf(split2[5]);
			} catch (NumberFormatException e) {
				log.error("Failed to construct TrafficTuple from string." + e.getLocalizedMessage());
				throw new IllegalArgumentException("Could not parse trafficTupleStr. + " + e.getLocalizedMessage());
			}

			tuple.put(generateTrafficDataKey(trafficData.protocol, trafficData.port), trafficData);
		}		
	}

	@Override
	public TrafficTuple clone() {

		TrafficTuple tt = new TrafficTuple();

		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData;
		while(iter.hasNext()) {
			trafficData = new TrafficData(iter.next().getValue());
			tt.tuple.put(generateTrafficDataKey(trafficData.protocol, trafficData.port), trafficData);
		}

		return tt;
	}
	

	@Override
	public String toString() {
		return serialize();
	}
	
	public String toPrintableString() {
		if ( tuple == null || tuple.isEmpty()) return "";

		StringBuilder sb = new StringBuilder(); TrafficData trafficData;

		int[] traceOrder = {6,17,1,0};
		for ( int tr:traceOrder) {
			trafficData = getTrafficData( tr, 0);
			if (trafficData == null ) continue;
			sb.append( trafficData.toPrintableString());
			sb.append(" ");	
		}
	
		return sb.toString();
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
			sb.append(trafficData.forTrafficLearning);  sb.append(TRAFFIC_DATA_SERIALIZATION_DELIMITER);
			sb.append(trafficData.direction);  
			sb.append(TUPLE_SERIALIZATION_DELIMITER);
		}

		sb.setLength(sb.length() - TUPLE_SERIALIZATION_DELIMITER.length());
		return sb.toString();
	}

	public Object toString(int protocol) {

		if ( tuple == null || tuple.isEmpty()) return "";		
		float trafficBytes = getTrafficBytes(protocol, 0);
		return Float.toString(trafficBytes);
	}

	public TrafficTuple delta(TrafficTuple lower, float timePeriod) {

		if(lower == null) return this;

		TrafficTuple tt = new TrafficTuple();
		if(timePeriod == 0) return tt ;

		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData; TrafficData trafficDelta;

		while(iter.hasNext()) {
			trafficData = iter.next().getValue();
			if(trafficData == null) continue;
			TrafficData lowerTrafficData = lower.getTrafficData(trafficData.protocol, trafficData.port);		

			if ( lowerTrafficData == null )
				lowerTrafficData = new TrafficData();

			/* port, protocol and flag are same as source. bytes and packets should be calculated in terms of rates */
			trafficDelta = new TrafficData(trafficData);
			trafficDelta.bytes = ( trafficData.bytes - lowerTrafficData.bytes ) / timePeriod;
			trafficDelta.packets = ( trafficData.packets - lowerTrafficData.packets ) / timePeriod;

			tt.tuple.put(generateTrafficDataKey(trafficDelta.protocol, trafficDelta.port), trafficDelta);
		}

		return tt;
	}

	public void add(TrafficTuple other) {

		if(other == null) return;

		// Update traffic data for received protocol/port data
		Iterator<Map.Entry<Integer,TrafficData>> iter = other.tuple.entrySet().iterator();
		TrafficData otherTrafficData;

		while(iter.hasNext()) {
			otherTrafficData = iter.next().getValue();
			int key = generateTrafficDataKey(otherTrafficData.protocol, otherTrafficData.port);
			if ( ! tuple.containsKey(key)) {
				tuple.put(key, new TrafficData (otherTrafficData));
			} else if ( otherTrafficData.direction == tuple.get(key).direction ) { // Add for same direction only 
				tuple.get(key).bytes = tuple.get(key).bytes + otherTrafficData.bytes ;
				tuple.get(key).packets = tuple.get(key).packets + otherTrafficData.packets ;
			}		
		}
	}

	public void setNonNegative(TrafficTuple other) {

		if(other == null) return;

		// Update traffic data for received protocol/port data
		Iterator<Map.Entry<Integer,TrafficData>> iter = other.tuple.entrySet().iterator();
		TrafficData otherTrafficData;

		while(iter.hasNext()) {
			otherTrafficData = iter.next().getValue();
			// Copy only positive values. In case of negative keep current
			if ( otherTrafficData.bytes < 0 ||otherTrafficData.packets < 0 ) continue;

			int key = generateTrafficDataKey(otherTrafficData.protocol, otherTrafficData.port);
			if ( ! tuple.containsKey(key)) {
				tuple.put(key, new TrafficData (otherTrafficData));
			} else if ( otherTrafficData.direction == tuple.get(key).direction ) { // Add for same direction only 
				tuple.get(key).bytes =   otherTrafficData.bytes ;
				tuple.get(key).packets = otherTrafficData.packets ;
			}		
		}
	}

	/* Return string containing bytes and packets. */
	public String getBandwidth(TrafficDirection direction) {

		long bytes = 0; long packets = 0;
		Iterator<Map.Entry<Integer,TrafficData>> iter = tuple.entrySet().iterator();
		TrafficData trafficData; 
		while(iter.hasNext()) {
			trafficData = iter.next().getValue();
			if(trafficData.direction == direction) {
				bytes += trafficData.bytes;
				packets += trafficData.packets;
			}
		}
		Bandwidth bandwidth = new Bandwidth(bytes, packets);
		return bandwidth.toString();		
	}

}
