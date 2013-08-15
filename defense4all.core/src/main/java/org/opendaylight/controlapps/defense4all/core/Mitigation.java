/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.opendaylight.controlapps.defense4all.core.ProtocolPort;
import org.opendaylight.controlapps.framework.core.PropertiesSerializer;
import org.opendaylight.controlapps.framework.core.RepoCD;

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
	public static final String MITIGATION_AMS_LABEL = "diversion_dp";
	public static final String COLLECT_STATS = "collect_stats";
	public static final String MITIGATION_DRIVER = "mitigation_driver";
	public static final String MITIGATION_CONFIG_PROPS = "mitigation_config_props";
	public static final String MITIGATION_EXECUTION_PROPS = "mitigation_execution_props";

	public String key;
	public String attackKey;
	public String pnKey;
	public String monitoredTrafficKey;
	public InetAddress dstAddr;
	public int dstAddrPrefixLen;
	public ProtocolPort protocolPort;
	public Status status;
	public String mitigationAmsLabel;
	public Boolean collectStats;
	public String mitigationDriverLabel;
	public Properties mitigationConfigProps;
	public Properties mitigationExecProps;
	
	public static String generateKey(String s) {return s + "_" + System.currentTimeMillis() / 1000;}
	
	protected static ArrayList<RepoCD> mitigationRepoCDs = null;

	/** ### Description ###
	 * @param param_name 
	 */
	public Mitigation() {
		key = attackKey = pnKey = monitoredTrafficKey = null; dstAddr = null; dstAddrPrefixLen = 0; 
		status = Status.INVALID; mitigationAmsLabel = null; collectStats = false; mitigationDriverLabel = "";
		protocolPort = new ProtocolPort(); mitigationConfigProps = new Properties(); mitigationExecProps = new Properties();		
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public Mitigation(String key, String attackKey, String pnKey, String monitoredTrafficKey, InetAddress dstAddr, 
			int dstAddrPrefixLen, ProtocolPort protocolPort, Status status, Boolean collectStats, 
			String mitigationDriverLabel, Properties mitigationProperties) {	
		this.key = key;	this.attackKey = attackKey;	this.pnKey = pnKey;	this.monitoredTrafficKey = monitoredTrafficKey;
		this.dstAddr = dstAddr; this.dstAddrPrefixLen = dstAddrPrefixLen; this.protocolPort = protocolPort; 
		this.status = status; this.collectStats= collectStats; this.mitigationDriverLabel = mitigationDriverLabel;
		this.mitigationConfigProps = mitigationProperties == null ? new Properties() : mitigationProperties;
		mitigationExecProps = new Properties();	
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
		this.mitigationConfigProps = other.mitigationConfigProps; this.mitigationExecProps = other.mitigationExecProps;
	}

	public Mitigation(Hashtable<String, Object> mitigationRow) throws UnknownHostException {
		this();
		key = (String) mitigationRow.get(KEY);
		attackKey = (String) mitigationRow.get(ATTACK_KEY);
		pnKey = (String) mitigationRow.get(PNKEY);
		monitoredTrafficKey = (String) mitigationRow.get(MONITORED_TRAFFIC_KEY);
		dstAddr = InetAddress.getByName((String) mitigationRow.get(DST_ADDR));
		dstAddrPrefixLen = (Integer) mitigationRow.get(DST_ADDR_PREFIX_LEN);
		protocolPort = new ProtocolPort((String) mitigationRow.get(PROTOCOL_PORT));
		status = Status.valueOf((String) mitigationRow.get(STATUS));
		collectStats= (Boolean) mitigationRow.get(COLLECT_STATS);
		mitigationDriverLabel = (String) mitigationRow.get(MITIGATION_DRIVER);
		mitigationConfigProps = (Properties) mitigationRow.get(MITIGATION_CONFIG_PROPS);
		mitigationExecProps = (Properties) mitigationRow.get(MITIGATION_EXECUTION_PROPS);		
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
		row.put(MITIGATION_CONFIG_PROPS, mitigationConfigProps);
		row.put(MITIGATION_EXECUTION_PROPS, mitigationExecProps);
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

	public String getMitigationAmsLabel() {return mitigationAmsLabel;}
	public void setMitigationAmsLabel(String mitigationAmsLabel) {this.mitigationAmsLabel = mitigationAmsLabel;}
	
	public Boolean getCollectStats() {return collectStats;}
	public void setCollectStats(Boolean collectStats) {this.collectStats = collectStats;}
	
	public String getMitigationDriverLabel() {return mitigationDriverLabel;}
	public void setMitigationDriverLabel(String label) {this.mitigationDriverLabel = label;}

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
			rcd = new RepoCD(MITIGATION_CONFIG_PROPS, PropertiesSerializer.get(), null); mitigationRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_EXECUTION_PROPS, PropertiesSerializer.get(), null); mitigationRepoCDs.add(rcd);
		}		
		return mitigationRepoCDs;
	}
}
