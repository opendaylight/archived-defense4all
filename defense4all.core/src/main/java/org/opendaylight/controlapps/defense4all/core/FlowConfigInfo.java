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
import java.util.List;

import me.prettyprint.cassandra.serializers.ShortSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.controlapps.framework.core.RepoCD;
import org.opendaylight.controlapps.framework.core.Utils;

/**
 * @author gerag
 *
 */
public class FlowConfigInfo {

	/* FlowConfigInfo Repo columns */
	
	public static final String NAME = "name";
	public static final String NODE = "node";
	public static final String FLOOR = "floor";
	public static final String DIRECTION = "direction";
	public static final String TRAFFIC_FLOOR_KEY = "traffic_floor_key";

	public static final String ETHER_TYPE = "ether_type";
	public static final String PROTOCOL = "protocol";
	public static final String DL_SRC = "dl_src";
	public static final String DL_DST = "dl_dst";
	public static final String NW_SRC = "nw_src";
	public static final String NW_DST = "nw_dst";
	public static final String TP_SRC = "tp_src";
	public static final String TP_DST = "tp_dst";

	public static final String ACTIONS = "actions";
	
	public static final String FOR_RATES = "for_rates";
	public static final String FOR_TRAFFIC_LEARNING = "for_traffic_learning";
	
	public enum Direction {
		INVALID,
		DOWN,
		UP,
		TO_SIDE,
		FROM_SIDE
	}

	protected static ArrayList<RepoCD> flowConfigInfoRCDs = null;
	
	/* Flow "coordinates". */
	public String key;
	public String node;
	public int  floor;
	public Direction direction; 
	public String trafficFloorKey;
	
	/* Flow match fields. Protocols: 6=tcp, 17=udp, 1=icmp (0 = unspecified for rest of traffic) */
	public int  etherType;		public int  protocol;
	public String dlSrc;		public String dlDst;
	public String nwSrc;		public String nwDst;
	public String tpSrc;		public String tpDst;
	
	/* Flow actions */
	public List<String> actions;
	
	/* Flow metadata */
	public boolean forRates;	public boolean forTrafficLearning;

	protected static final String DEFENSE4ALL_FLOW_CONFIG_INFO_NAME_PREFIX = "d4a_fci_";
	
	public String generateAndSetKey() {
		
		if(trafficFloorKey == null) return null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(trafficFloorKey); sb.append(":");
		sb.append(direction); sb.append(":");
		sb.append(etherType); sb.append(":");
		sb.append(protocol); sb.append(":");
		if(dlSrc != null) {sb.append(dlSrc); sb.append(":");}
		if(dlSrc != null) {sb.append(dlDst); sb.append(":");}
		if(dlSrc != null) {sb.append(nwSrc); sb.append(":");}
		if(dlSrc != null) {sb.append(nwDst); sb.append(":");}
		if(dlSrc != null) {sb.append(tpSrc); sb.append(":");}
		if(dlSrc != null) sb.append(tpDst);
		key = DEFENSE4ALL_FLOW_CONFIG_INFO_NAME_PREFIX + Utils.shortHash(sb.toString());
		
		return key;
	}

	/* ### Description ###
	 * @param param_name 
	 */
	public FlowConfigInfo() {		
		key = null; node = null; floor = -1; direction = Direction.INVALID; trafficFloorKey = null;
		etherType = 0; protocol = 0; 
		dlSrc = null; dlDst = null;	nwSrc = null; nwDst = null; tpSrc = null; tpDst = null; 
		actions = new ArrayList<String>(); forRates = false; forTrafficLearning = false;
	}
	
	public FlowConfigInfo(Hashtable<String, Object> row) {
		
		this();
		
		key = (String) row.get(NAME);
		node = (String) row.get(NODE);
		floor = (Short) row.get(FLOOR);
		direction = Direction.valueOf((String) row.get(DIRECTION));
		trafficFloorKey = (String) row.get(TRAFFIC_FLOOR_KEY);
		
		etherType = (Short) row.get(ETHER_TYPE);
		protocol = (Short) row.get(PROTOCOL);
		dlSrc = (String) row.get(DL_SRC);
		dlDst = (String) row.get(DL_DST);
		nwSrc = (String) row.get(NW_SRC);
		nwDst = (String) row.get(NW_DST);
		tpSrc = (String) row.get(TP_SRC);
		tpDst = (String) row.get(TP_DST);
		
		String actionsListStr = (String) row.get(ACTIONS);
		String[] split = actionsListStr.split("::");
		for(String action : split)
			actions.add(action);

		forRates = (Boolean) row.get(FOR_RATES);
		forTrafficLearning = (Boolean) row.get(FOR_TRAFFIC_LEARNING);
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(node == null ) node = "";
		if(trafficFloorKey == null) trafficFloorKey = "";
		if(dlSrc == null) dlSrc = "";
		if(dlDst == null) dlDst = "";
		if(nwSrc == null) nwSrc = "";
		if(nwDst == null) nwDst = "";
		if(tpSrc == null) tpSrc = "";
		if(tpDst == null) tpDst = "";		
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(NAME, key);
		row.put(NODE, node);
		row.put(FLOOR, floor);
		row.put(DIRECTION, direction);
		row.put(TRAFFIC_FLOOR_KEY, trafficFloorKey);
		
		row.put(ETHER_TYPE, etherType);
		row.put(PROTOCOL, protocol);
		row.put(DL_SRC, dlSrc);
		row.put(DL_DST, dlDst);
		row.put(NW_SRC, nwSrc);
		row.put(NW_DST, nwDst);
		row.put(TP_SRC, tpSrc);
		row.put(TP_DST, tpDst);
		
		StringBuilder sb = new StringBuilder();
		for(String action : actions)
			sb.append(action);
		row.put(ACTIONS, sb.toString());

		row.put(FOR_RATES, forRates);
		row.put(FOR_TRAFFIC_LEARNING, forTrafficLearning);
		
		return row;
	}
	
	public static List<RepoCD> getRCDs() {

		if(flowConfigInfoRCDs == null) {
			RepoCD rcd;
			flowConfigInfoRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(NAME, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(NODE, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FLOOR, ShortSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(DIRECTION, StringSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(TRAFFIC_FLOOR_KEY, StringSerializer.get(), null); flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(ETHER_TYPE, ShortSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(PROTOCOL, ShortSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(DL_SRC, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(DL_DST, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(NW_SRC, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(NW_DST, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(TP_SRC, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(TP_DST, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(ACTIONS, StringSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FOR_RATES, ShortSerializer.get(),null); 		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FOR_TRAFFIC_LEARNING, ShortSerializer.get(), null); flowConfigInfoRCDs.add(rcd);
		}		
		return flowConfigInfoRCDs;
	}
}
