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
	public static final String FLOOR = "floor";
	public static final String FLOW_CONFIG_INFO_KEY_PREFIX = "flow_config_info_key_";

	public final static short FLOOR_INVALID = -1;
	public final static short FLOOR_STEP = 10;
	public final static short FLOOR_COMMON_START = 0;
	public final static short FLOOR_PEACETIME_START = FLOOR_COMMON_START + FLOOR_STEP;
	public final static short FLOOR_OTHER_ATTACK_START = FLOOR_PEACETIME_START + FLOOR_STEP;
	public final static short FLOOR_ATTACK_START = FLOOR_OTHER_ATTACK_START + FLOOR_STEP;
	public final static short FLOOR_ATTACK_END = 32000;
	// Each attack floor takes interval of next 10 priorities: 20-29, 30-39...
	
	public final static short FLOOR_TCP_PRIORITY = 5;
	public final static short FLOOR_UDP_PRIORITY = 5;
	public final static short FLOOR_ICMP_PRIORITY = 5;
	public final static short FLOOR_OTHER_PRIORITY = 3;
	
	protected static ArrayList<RepoCD> trafficFloorRCDs = null;
	
	public String key;
	public String pnKey;
	public String nodeLabel;
	public String nodeId;
	public short  floor;
	public HashMap<String,Object> flowConfigInfoKeys;	
	
	public String generateAndSetKey() {		
		StringBuilder sb = new StringBuilder();
		sb.append(nodeLabel); sb.append(":");
		sb.append(pnKey); sb.append(":");
		sb.append(floor);
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

	/* ### Description ###
	 * @param param_name 
	 */
	public TrafficFloor() {		
		key = null; pnKey = null; nodeLabel = null; nodeId = null; floor = FLOOR_INVALID; 
		flowConfigInfoKeys = new HashMap<String,Object>();
	}
	
	public TrafficFloor(Hashtable<String, Object> row) {
		
		this();
		
		key = (String) row.get(KEY);
		pnKey = (String) row.get(PNKEY);
		nodeLabel = (String) row.get(NODE_LABEL);
		nodeId = (String) row.get(NODE_ID);
		floor = (Short) row.get(FLOOR);
		
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
		row.put(FLOOR, floor);
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
			rcd = new RepoCD(FLOOR, ShortSerializer.get(), null);			trafficFloorRCDs.add(rcd);
		}		
		return trafficFloorRCDs;
	}
}
