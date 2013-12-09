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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class Mitigation {
	
	public enum Status {
		INVALID,
		ACTIVE,
		NO_RESOURCES,
		ENDED
	}
	
	/* MitigationRepo column names */
	public static final String KEY 	= "key";
	public static final String ATTACK_KEY = "attack_key";
	public static final String PNKEY = "pnkey";
	public static final String MONITORED_TRAFFIC_KEY = "monitored_traffic_key";
	public static final String DST_ADDR = "dest_addr";
	public static final String DST_ADDR_PREFIX_LEN = "dest_addr_prefix_len";
	public static final String PROTOCOL_PORT = "protocol_port";
	public static final String STATUS = "status";
	public static final String COLLECT_STATS = "collect_stats";
	public static final String MITIGATION_DRIVER = "mitigation_driver";
	public static final String TRAFFIC_FLOOR_KEY_PREFIX = "traffic_floor_key_";
	public static final String DVSN_INFO_KEY_PREFIX = "dvsn_info_key_";
	public static final String MITIGATION_EXECUTION_PROPS = "mitigation_execution_props";

	static Logger log = LoggerFactory.getLogger(Mitigation.class);

	public String key;
	public String attackKey;
	public String pnKey;
	public String monitoredTrafficKey;
	public InetAddress dstAddr;
	public int 	  dstAddrPrefixLen;
	public ProtocolPort protocolPort;
	public Status status;
	public Boolean collectStats;
	public String mitigationDriverLabel;
	public List<String> trafficFloorKeys; // used to indicate traffic floors created for mitigation
	public List<String> dvsnInfoKeys;
	public Properties mitigationExecutionProps;
	
	public static String generateKey(String s) {return s + "_" + System.currentTimeMillis() / 1000;}
	
	protected static ArrayList<RepoCD> mitigationRepoCDs = null;

	/** ### Description ###
	 * @param param_name 
	 */
	public Mitigation() {
		key = attackKey = pnKey = monitoredTrafficKey = null; dstAddr = null; dstAddrPrefixLen = 0; 
		status = Status.INVALID; collectStats = false; mitigationDriverLabel = "";
		trafficFloorKeys = new ArrayList<String>();
		protocolPort = new ProtocolPort(); 
		dvsnInfoKeys = new ArrayList<String>(); 
		mitigationExecutionProps = new Properties();
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public Mitigation(String key, String attackKey, String pnKey, String monitoredTrafficKey, InetAddress dstAddr, 
			int dstAddrPrefixLen, ProtocolPort protocolPort, Status status, Boolean collectStats, 
			String mitigationDriverLabel, List<String> dvsnInfoKeys) {	
		this.key = key;	this.attackKey = attackKey;	this.pnKey = pnKey;	this.monitoredTrafficKey = monitoredTrafficKey;
		this.dstAddr = dstAddr; this.dstAddrPrefixLen = dstAddrPrefixLen; this.protocolPort = protocolPort; 
		this.status = status; this.collectStats= collectStats; this.mitigationDriverLabel = mitigationDriverLabel;
		this.trafficFloorKeys = new ArrayList<String>();
		this.dvsnInfoKeys = dvsnInfoKeys == null ? new ArrayList<String>() : dvsnInfoKeys;
		this.mitigationExecutionProps = new Properties();
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public Mitigation(Mitigation other) {
		this.key = other.key; this.attackKey = other.attackKey; this.pnKey = other.pnKey; 
		this.monitoredTrafficKey = other.monitoredTrafficKey; this.dstAddr = other.dstAddr; 
		this.dstAddrPrefixLen = other.dstAddrPrefixLen; this.protocolPort = other.protocolPort; 
		this.status = other.status; this.collectStats= other.collectStats; 
		this.mitigationDriverLabel = other.mitigationDriverLabel;
		this.trafficFloorKeys = other.trafficFloorKeys;
		this.dvsnInfoKeys = other.dvsnInfoKeys;
		this.mitigationExecutionProps = other.mitigationExecutionProps;
	}

	public Mitigation(Hashtable<String, Object> mitigationRow) throws UnknownHostException, ExceptionControlApp {
		this();
		key = (String) mitigationRow.get(KEY);
		attackKey = (String) mitigationRow.get(ATTACK_KEY);
		pnKey = (String) mitigationRow.get(PNKEY);
		monitoredTrafficKey = (String) mitigationRow.get(MONITORED_TRAFFIC_KEY);
		try {
			dstAddr = InetAddress.getByName((String) mitigationRow.get(DST_ADDR));
		} catch (Exception e) {
			log.error("Failed to construct Mitigation - bad destination address " + dstAddr + ". " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to construct Mitigation. + ", e);
		}
		dstAddrPrefixLen = (Integer) mitigationRow.get(DST_ADDR_PREFIX_LEN);
		protocolPort = new ProtocolPort((String) mitigationRow.get(PROTOCOL_PORT));
		status = Status.valueOf((String) mitigationRow.get(STATUS));
		collectStats= (Boolean) mitigationRow.get(COLLECT_STATS);
		mitigationDriverLabel = (String) mitigationRow.get(MITIGATION_DRIVER);
		mitigationExecutionProps = (Properties) mitigationRow.get(MITIGATION_EXECUTION_PROPS);
		
		Iterator<Map.Entry<String,Object>> iter = mitigationRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String key;
		while(iter.hasNext()) {
			entry = iter.next();
			key = entry.getKey();
			if(key.startsWith(DVSN_INFO_KEY_PREFIX))
				dvsnInfoKeys.add((String) entry.getValue());
			else if(key.startsWith(TRAFFIC_FLOOR_KEY_PREFIX))
				trafficFloorKeys.add((String) entry.getValue());
		}	
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(attackKey == null) attackKey = "";
		if(pnKey == null) pnKey = "";
		if(monitoredTrafficKey == null) monitoredTrafficKey = "";
		if(mitigationDriverLabel == null) mitigationDriverLabel = "";
		if(protocolPort == null) protocolPort = new ProtocolPort();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(KEY, key);
		row.put(ATTACK_KEY, attackKey);
		row.put(PNKEY, pnKey);
		row.put(MONITORED_TRAFFIC_KEY, monitoredTrafficKey);
		row.put(DST_ADDR, dstAddr.getHostAddress());
		row.put(DST_ADDR_PREFIX_LEN, dstAddrPrefixLen);
		row.put(PROTOCOL_PORT, protocolPort.toString());
		row.put(STATUS, status.name());
		row.put(COLLECT_STATS, collectStats);
		row.put(MITIGATION_DRIVER, mitigationDriverLabel);
		row.put(MITIGATION_EXECUTION_PROPS, mitigationExecutionProps);
		for(String dvsnInfoKey : dvsnInfoKeys)
			row.put(DVSN_INFO_KEY_PREFIX + dvsnInfoKey, dvsnInfoKey);
		for(String trafficFloorKey : trafficFloorKeys)
			row.put(trafficFloorKey, trafficFloorKey);
		return row;
	}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	
	public String getAttackKey() {return attackKey;}
	public void setAttackKey(String attackKey) {this.attackKey = attackKey;}
	
	public String getMonitoredTraffickey() {return monitoredTrafficKey;}
	public void setMonitoredTraffickey(String monitoredTrafficKey) {this.monitoredTrafficKey = monitoredTrafficKey;}
	
	public String getPnkey() {return pnKey;}
	public void setPnkey(String pnKey) {this.pnKey = pnKey;}

	public InetAddress getDstAddr() {return dstAddr;}
	public void setDstAddr(InetAddress dstAddr) {this.dstAddr = dstAddr;}

	public int getDstAddrPrefixLen() {return dstAddrPrefixLen;}
	public void setDstAddrPrefixLen(int dstAddrPrefixLen) {this.dstAddrPrefixLen = dstAddrPrefixLen;}

	public ProtocolPort getProtocolPort() {return protocolPort;}
	public void setProtocolPort(ProtocolPort protocolPort) {this.protocolPort = protocolPort;}

	public Status getStatus() {return status;}
	public void setStatus(Status status) {this.status = status;}
	
	public Boolean getCollectStats() {return collectStats;}
	public void setCollectStats(Boolean collectStats) {this.collectStats = collectStats;}
	
	public String getMitigationDriverLabel() {return mitigationDriverLabel;}
	public void setMitigationDriverLabel(String label) {this.mitigationDriverLabel = label;}
	
	public List<String> getTrafficFlowKeys() {return trafficFloorKeys;}
	public void setTrafficFlowKeys(List<String> trafficFloorKeys) {this.trafficFloorKeys = trafficFloorKeys;}
	
	public List<String> getDvsnInfoKeys() {return dvsnInfoKeys;}
	public void setDvsnInfoKeys(List<String> dvsnInfoKeys) {this.dvsnInfoKeys = dvsnInfoKeys;}	

	public Properties getMitigationExecutionProps() {return mitigationExecutionProps;}
	public void setMitigationExecutionProps(Properties props) {this.mitigationExecutionProps = props;}

	public static List<RepoCD> getMitigationRCDs() {

		if(mitigationRepoCDs == null) {
			RepoCD rcd;
			mitigationRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(ATTACK_KEY, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(MONITORED_TRAFFIC_KEY, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR_PREFIX_LEN, IntegerSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(PROTOCOL_PORT, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(STATUS, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(COLLECT_STATS, BooleanSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_DRIVER, StringSerializer.get(), null);	mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_EXECUTION_PROPS, PropertiesSerializer.get(), null);	mitigationRepoCDs.add(rcd);
		}		
		return mitigationRepoCDs;
	}
}
