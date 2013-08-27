/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.controlapps.defense4all.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.ShortSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.controlapps.framework.core.RepoCD;

public class TrafficFloor {

	/* TrafficFloor Repo common columns */	
	public static final String KEY = "key";
	public static final String PNKEY = "pnKey";
	public static final String NODE = "node";
	public static final String FLOOR = "floor";
	public static final String FLOW_CONFIG_INFO_KEY_PREFIX = "flow_config_info_key_";

	public final static short FLOOR_INVALID = -1;
	public final static short FLOOR_COMMON_START = 0;
	public final static short FLOOR_COMMON_END = 9;
	public final static short FLOOR_PEACETIME_START = FLOOR_COMMON_END + 1;
	public final static short FLOOR_PEACETIME_END = 19;
	public final static short FLOOR_ATTACK_START = FLOOR_PEACETIME_END + 1;
	// Each attack floor takes interval of next 10 priorities: 20-29, 30-39...
	
	public final static short FLOOR_TCP_PRIORITY = 5;
	public final static short FLOOR_UDP_PRIORITY = 5;
	public final static short FLOOR_ICMP_PRIORITY = 5;
	public final static short FLOOR_OTHER_PRIORITY = 3;
	
	protected static ArrayList<RepoCD> trafficFloorRCDs = null;
	
	public String key;
	public String pnKey;
	public String node;
	public short  floor;
	public List<String> flowConfigInfoKeys;	
	
	public String generateAndSetKey() {		
		StringBuilder sb = new StringBuilder();
		sb.append(node); sb.append(":");
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
		key = null; pnKey = null; node = null; floor = FLOOR_INVALID; flowConfigInfoKeys = new ArrayList<String>();
	}
	
	public TrafficFloor(Hashtable<String, Object> row) {
		
		this();
		
		key = (String) row.get(KEY);
		pnKey = (String) row.get(PNKEY);
		node = (String) row.get(NODE);
		floor = (Short) row.get(FLOOR);
		
		Iterator<Map.Entry<String,Object>> iter = row.entrySet().iterator();
		Map.Entry<String,Object> entry;
		while(iter.hasNext()) {
			entry = iter.next();
			if(entry.getKey().startsWith(FLOW_CONFIG_INFO_KEY_PREFIX))
				flowConfigInfoKeys.add((String) entry.getValue());
		}
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(pnKey == null) pnKey = "";
		if(node == null ) node = "";	
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(KEY, key);
		row.put(PNKEY, pnKey);
		row.put(NODE, node);
		row.put(FLOOR, floor);		
		for(String flowConfigInfoKey : flowConfigInfoKeys)
			row.put(FLOW_CONFIG_INFO_KEY_PREFIX + flowConfigInfoKey, flowConfigInfoKey);
		
		return row;
	}
	
	public static List<RepoCD> getRCDs() {

		if(trafficFloorRCDs == null) {
			RepoCD rcd;
			trafficFloorRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);			trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);			trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(NODE, StringSerializer.get(), null);			trafficFloorRCDs.add(rcd);
			rcd = new RepoCD(FLOOR, ShortSerializer.get(), null);			trafficFloorRCDs.add(rcd);
		}		
		return trafficFloorRCDs;
	}
}
