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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.ShortSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.core.Traffic.TrafficDirection;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.opendaylight.defense4all.framework.core.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlowConfigInfo {

	static Logger log = LoggerFactory.getLogger(FlowConfigInfo.class);

	/* FlowConfigInfo Repo columns */	
	public static final String NAME = "name";
	public static final String NODE_LABEL = "node";
	public static final String FLOOR = "floor";
	public static final String DIRECTION = "direction";
	public static final String TRAFFIC_FLOOR_KEY = "traffic_floor_key";
	public static final String ETHER_TYPE = "ether_type";
	public static final String PROTOCOL = "protocol";
	public static final String INGRESS_PORT = "ingress_port";
	public static final String DL_SRC = "dl_src";
	public static final String DL_DST = "dl_dst";
	public static final String NW_SRC = "nw_src";
	public static final String NW_DST = "nw_dst";
	public static final String TP_SRC = "tp_src";
	public static final String TP_DST = "tp_dst";
	public static final String ACTIONS = "actions";	
	public static final String FOR_RATES = "for_rates";
	public static final String FOR_TRAFFIC_LEARNING = "for_traffic_learning";
	public static final String FIRST_TO_DELETE = "first_to_delete";

	protected static ArrayList<RepoCD> flowConfigInfoRCDs = null;
	
	/* Flow "coordinates". */
	public String key;
	public String nodeLabel;
	public short  floor;
	public TrafficDirection direction; 
	public String trafficFloorKey;
	
	/* Flow match fields. Protocols: 6=tcp, 17=udp, 1=icmp (0 = unspecified for rest of traffic) */
	public int etherType;	public short  protocol;   
	public short  ingressPort;
	public String dlSrc;		public String dlDst;
	public String nwSrc;		public String nwDst;
	public String tpSrc;		public String tpDst;
	
	/* Flow actions */
	public List<String> actions;
	
	/* Flow metadata */
	public boolean forRates;	public boolean forTrafficLearning;		public boolean firstToDelete;

	protected static final String DEFENSE4ALL_FLOW_CONFIG_INFO_NAME_PREFIX = "d4a_fci_";
	
	public String generateAndSetKey() {
		
		if(trafficFloorKey == null) return null;
		
		StringBuilder sb = new StringBuilder();
		sb.append(trafficFloorKey);
		sb.append(direction);
		sb.append(etherType);
		sb.append(protocol);
		sb.append(ingressPort);
		if(dlSrc != null) sb.append(dlSrc);
		if(dlSrc != null) sb.append(dlDst);
		if(dlSrc != null) sb.append(nwSrc);
		if(dlSrc != null) sb.append(nwDst);
		if(dlSrc != null) sb.append(tpSrc);
		if(dlSrc != null) sb.append(tpDst);
		key = DEFENSE4ALL_FLOW_CONFIG_INFO_NAME_PREFIX + Utils.shortHash(sb.toString());
		
		return key;
	}

	/* ### Description ###
	 * @param param_name 
	 */
	public FlowConfigInfo() {		
		key = null; nodeLabel = null; floor = -1; direction = TrafficDirection.INVALID; trafficFloorKey = null;
		etherType = 0; protocol = 0; ingressPort = 0;
		dlSrc = null; dlDst = null;	nwSrc = null; nwDst = null; tpSrc = null; tpDst = null; 
		actions = new ArrayList<String>(); forRates = false; forTrafficLearning = false; firstToDelete = false;
	}
	
	public FlowConfigInfo(Hashtable<String, Object> row) throws ExceptionControlApp {
		
		this();
		
		try {
			key = (String) row.get(NAME);
			nodeLabel = (String) row.get(NODE_LABEL);	
			floor = (Short) row.get(FLOOR);
			direction = TrafficDirection.valueOf((String)row.get(DIRECTION));
			trafficFloorKey = (String) row.get(TRAFFIC_FLOOR_KEY);

			etherType = (Integer) row.get(ETHER_TYPE);
			protocol = (Short) row.get(PROTOCOL);
			ingressPort = (Short) row.get(INGRESS_PORT);
			dlSrc = (String) row.get(DL_SRC);
			dlDst = (String) row.get(DL_DST);
			nwSrc = (String) row.get(NW_SRC);
			nwDst = (String) row.get(NW_DST);
			tpSrc = (String) row.get(TP_SRC);
			tpDst = (String) row.get(TP_DST);
			
			String actionsListStr = (String) row.get(ACTIONS);
			String[] split = actionsListStr.split("::");
			if(split != null) {
				for(String action : split)
					actions.add(action);
			}

			forRates = (Boolean) row.get(FOR_RATES);
			forTrafficLearning = (Boolean) row.get(FOR_TRAFFIC_LEARNING);
			firstToDelete = (Boolean) row.get(FIRST_TO_DELETE);
		} catch (Throwable e) {
			log.error("Excepted trying to inflate FlowConfigInfo from row. " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate FlowConfigInfo from row. ", e);
		}
	}

	public FlowConfigInfo(FlowConfigInfo other) {
		this.key = other.key; this.nodeLabel = other.nodeLabel; this.floor = other.floor; 
		this.direction = other.direction; this.trafficFloorKey = other.trafficFloorKey; 
		this.etherType = other.etherType; this.protocol = other.protocol; this.ingressPort = other.ingressPort;
		this.dlSrc = other.dlSrc; this.dlDst = other.dlDst; this.nwSrc = other.nwSrc; this.nwDst = other.nwDst; 
		this.tpSrc = other.tpSrc; this.tpDst = other.tpDst; this.actions = new ArrayList<String>(other.actions); 
		this.forRates = other.forRates; this.forTrafficLearning = other.forTrafficLearning; 
		this. firstToDelete = other.firstToDelete;
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(nodeLabel == null ) nodeLabel = "";
		if(trafficFloorKey == null) trafficFloorKey = "";
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(NAME, key);
		row.put(NODE_LABEL, nodeLabel);
		row.put(FLOOR, floor);
		row.put(DIRECTION, direction.name());
		row.put(TRAFFIC_FLOOR_KEY, trafficFloorKey);		
		row.put(ETHER_TYPE, etherType);
		row.put(PROTOCOL, protocol);
		row.put(INGRESS_PORT, ingressPort);
		if(dlSrc != null) row.put(DL_SRC, dlSrc);
		if(dlDst != null) row.put(DL_DST, dlDst);
		if(nwSrc != null) row.put(NW_SRC, nwSrc);
		if(nwDst != null) row.put(NW_DST, nwDst);
		if(tpSrc != null) row.put(TP_SRC, tpSrc);
		if(tpDst != null) row.put(TP_DST, tpDst);
		
		StringBuilder sb = new StringBuilder();
		for(String action : actions)
			sb.append(action);
		row.put(ACTIONS, sb.toString());
		row.put(FOR_RATES, forRates);
		row.put(FOR_TRAFFIC_LEARNING, forTrafficLearning);
		row.put(FIRST_TO_DELETE, firstToDelete);
		
		return row;
	}
	
	public static List<RepoCD> getRCDs() {

		if(flowConfigInfoRCDs == null) {
			RepoCD rcd;
			flowConfigInfoRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(NAME, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(NODE_LABEL, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FLOOR, IntegerSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(DIRECTION, StringSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(TRAFFIC_FLOOR_KEY, StringSerializer.get(), null); flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(ETHER_TYPE, IntegerSerializer.get(), null);	flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(PROTOCOL, IntegerSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(INGRESS_PORT, IntegerSerializer.get(), null);  flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(DL_SRC, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(DL_DST, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(NW_SRC, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(NW_DST, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(TP_SRC, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(TP_DST, StringSerializer.get(), null);			flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(ACTIONS, StringSerializer.get(), null);		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FOR_RATES, ShortSerializer.get(),null); 		flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FOR_TRAFFIC_LEARNING, ShortSerializer.get(), null); flowConfigInfoRCDs.add(rcd);
			rcd = new RepoCD(FIRST_TO_DELETE, ShortSerializer.get(), null); flowConfigInfoRCDs.add(rcd);
		}		
		return flowConfigInfoRCDs;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNodeLabel() {
		return nodeLabel;
	}

	public void setNodeLabel(String nodeLabel) {
		this.nodeLabel = nodeLabel;
	}

	public short getFloor() {
		return floor;
	}

	public void setFloor(short floor) {
		this.floor = floor;
	}

	public TrafficDirection getDirection() {
		return direction;
	}

	public void setDirection(TrafficDirection direction) {
		this.direction = direction;
	}

	public String getTrafficFloorKey() {
		return trafficFloorKey;
	}

	public void setTrafficFloorKey(String trafficFloorKey) {
		this.trafficFloorKey = trafficFloorKey;
	}

	public int getEtherType() {
		return etherType;
	}

	public void setEtherType(int etherType) {
		this.etherType = etherType;
	}

	public short getProtocol() {
		return protocol;
	}

	public void setProtocol(short protocol) {
		this.protocol = protocol;
	}

	public short getIngressPort() {
		return ingressPort;
	}

	public void setIngressPort(short ingressPort) {
		this.ingressPort = ingressPort;
	}

	public String getDlSrc() {
		return dlSrc;
	}

	public void setDlSrc(String dlSrc) {
		this.dlSrc = dlSrc;
	}

	public String getDlDst() {
		return dlDst;
	}

	public void setDlDst(String dlDst) {
		this.dlDst = dlDst;
	}

	public String getNwSrc() {
		return nwSrc;
	}

	public void setNwSrc(String nwSrc) {
		this.nwSrc = nwSrc;
	}

	public String getNwDst() {
		return nwDst;
	}

	public void setNwDst(String nwDst) {
		this.nwDst = nwDst;
	}

	public String getTpSrc() {
		return tpSrc;
	}

	public void setTpSrc(String tpSrc) {
		this.tpSrc = tpSrc;
	}

	public String getTpDst() {
		return tpDst;
	}

	public void setTpDst(String tpDst) {
		this.tpDst = tpDst;
	}

	public List<String> getActions() {
		return actions;
	}

	public void setActions(List<String> actions) {
		this.actions = actions;
	}

	public boolean isForRates() {
		return forRates;
	}

	public void setForRates(boolean forRates) {
		this.forRates = forRates;
	}

	public boolean isForTrafficLearning() {
		return forTrafficLearning;
	}

	public void setForTrafficLearning(boolean forTrafficLearning) {
		this.forTrafficLearning = forTrafficLearning;
	}

	public boolean isFirstToDelete() {
		return firstToDelete;
	}

	public void setFirstToDelete(boolean firstToDelete) {
		this.firstToDelete = firstToDelete;
	}
	
	
}
