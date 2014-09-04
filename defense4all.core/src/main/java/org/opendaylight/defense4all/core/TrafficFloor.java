/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.ShortSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.RepoCD;

public class TrafficFloor {

	/* TrafficFloor Repo common columns */	
	public static final String KEY = "key";
	public static final String PNKEY = "pnKey";
	public static final String NODE_LABEL = "node_label";
	public static final String NODE_ID = "node_id";
	public static final String FLOOR_BASE = "floor_base";
	public static final String FLOOR_CURRENT_HEIGHT = "floor_current_height";	
	public static final String FLOW_CONFIG_INFO_KEY_PREFIX = "flow_config_info_key_";
	public static final String STATUS = "status";

	public final static short FLOOR_INVALID = -1;
	public final static short FLOOR_STEP = 20;
	public final static short FLOOR_COMMON_START = 10;
	public final static short FLOOR_PEACETIME_START = FLOOR_COMMON_START + FLOOR_STEP;
	public final static short FLOOR_OTHER_ATTACK_START = FLOOR_PEACETIME_START + FLOOR_STEP;
	public final static short FLOOR_ATTACK_START = FLOOR_OTHER_ATTACK_START + FLOOR_STEP;
	public final static short FLOOR_ATTACK_END = 32000;
	/* "common" floor - 10-29;  peace-time floor - 30-49
	 * "other" attack floor - 50-69; tcp/udp/icmp attack1 floor - 70-89; tcp/udp/icmp attack2 floor - 90-109...
	 *
	 * For "other" attacks we allocate a dedicated floor with priority lower than any other attack floor, but higher than
	 * peace time. This is because the way to divert "other" traffic only is to put higher priority flows to send TCP, UDP
	 * ICMP traffic straight and another lower priority flow to divert all (remaining) traffic. Had this floor been higher
	 * than other attack floors, the "straight" flow of this entry would cancel diversion of such a flow in a lower floor.*/
	
	public final static short FLOOR_TCP_PRIORITY = 4;
	public final static short FLOOR_UDP_PRIORITY = 3;
	public final static short FLOOR_ICMP_PRIORITY = 2;
	public final static short FLOOR_OTHER_PRIORITY = 1;
	
	protected static ArrayList<RepoCD> trafficFloorRCDs = null;
	
	public String key;
	public String pnKey;
	public String nodeLabel;
	public String nodeId;
	public short  floorBase;
	public short  floorCurrentHeight;
	public Status status;
	public HashMap<String,Object> flowConfigInfoKeys;	
	
	public enum Status {
		ACTIVE,
		REMOVED
	}

	
	public String generateAndSetKey() {		
		StringBuilder sb = new StringBuilder();
		sb.append(nodeLabel); sb.append(":");
		sb.append(pnKey); sb.append(":");
		sb.append(floorBase);
		key = sb.toString();
		return key;
	}
	
	public static String generateAndSetKey(String node, String pnKey, short floor) {		
		StringBuilder sb = new StringBuilder();
		sb.append(node); sb.append(":");
		sb.append(pnKey); sb.append(":");
		sb.append(floor);	
		return sb.toString();
	}
	
	public static String generatePeacetimeFloorKey(String node, String pnKey) {		
		StringBuilder sb = new StringBuilder();
		sb.append(node); sb.append(":");
		sb.append(pnKey); sb.append(":");
		sb.append(FLOOR_PEACETIME_START);	
		return sb.toString();
	}

	/* ### Description ###
	 * @param param_name 
	 */
	public TrafficFloor() {		
		key = null; pnKey = null; nodeLabel = null; nodeId = null; floorBase = FLOOR_INVALID; floorCurrentHeight = floorBase;
		flowConfigInfoKeys = new HashMap<String,Object>(); status = Status.ACTIVE;
	}
	
	public TrafficFloor(Hashtable<String, Object> row) {
		
		this();
		
		key = (String) row.get(KEY);
		pnKey = (String) row.get(PNKEY);
		nodeLabel = (String) row.get(NODE_LABEL);
		nodeId = (String) row.get(NODE_ID);
		floorBase = (Short) row.get(FLOOR_BASE);
		floorCurrentHeight= (Short) row.get(FLOOR_CURRENT_HEIGHT);
		status = Status.valueOf((String) row.get(STATUS));
		
		Iterator<Map.Entry<String,Object>> iter = row.entrySet().iterator();
		Map.Entry<String,Object> entry;
		while(iter.hasNext()) {
			entry = iter.next();
			if(entry.getKey().startsWith(FLOW_CONFIG_INFO_KEY_PREFIX))
				flowConfigInfoKeys.put((String) entry.getValue(), null);
		}
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(pnKey == null) pnKey = "";
		if(nodeLabel == null ) nodeLabel = "";
		if(nodeId == null ) nodeId = "";
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(KEY, key);
		row.put(PNKEY, pnKey);
		row.put(NODE_LABEL, nodeLabel);
		row.put(NODE_ID, nodeId);
		row.put(FLOOR_BASE, floorBase);
		row.put(FLOOR_CURRENT_HEIGHT, floorCurrentHeight);
		row.put(STATUS, status.name());
		Iterator<Map.Entry<String,Object>> iter = flowConfigInfoKeys.entrySet().iterator();
		String flowConfigInfoKey;
		while(iter.hasNext()) {
			flowConfigInfoKey = iter.next().getKey();
			row.put(FLOW_CONFIG_INFO_KEY_PREFIX + flowConfigInfoKey, flowConfigInfoKey);
		}
		return row;
	}
	
	public static List<RepoCD> getRCDs() {

		if(trafficFloorRCDs == null) {
			RepoCD rcd;
			trafficFloorRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);			trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);			trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(NODE_LABEL, StringSerializer.get(), null);		trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(NODE_ID, StringSerializer.get(), null);		trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(FLOOR_BASE, ShortSerializer.get(), null);		trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(FLOOR_CURRENT_HEIGHT, ShortSerializer.get(), null); trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(STATUS, StringSerializer.get(), null);	 trafficFloorRCDs.add(rcd);
		}		
		return trafficFloorRCDs;
	}
}
