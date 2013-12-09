/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Gera Goft
 * @version 0.1
 */

package com.radware.defenseflow.dp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.Traffic;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.StringSerializer;

public class MonitoredTraffic {
	
	private static Logger log = LoggerFactory.getLogger(MonitoredTraffic.class);
	
	/* DiversionRepo column names */
	public static final String KEY = "key";
	public static final String PNKEY = "pnkey";
	public static final String MITIGATION_KEY = "mitigation_key";
	public static final String DST_ADDR = "dst_addr";
	public static final String PROTO_PORT_PREFIX = "proto_port_";
	
	protected static ArrayList<RepoCD> mMonitoredTrafficRepoCDs = null;
	
	public String key;
	public String pnKey;
	public String mitigationKey;
	public Traffic traffic;
	
	public static String generateKey(String dstAddr) {
		return "mt_" + dstAddr;
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public MonitoredTraffic() {key = pnKey = mitigationKey = null; traffic = new Traffic();}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws UnknownHostException 
	 */
	public MonitoredTraffic(String key, String pnKey, String mitigationKey, Traffic traffic) {
		this.key = key; this.pnKey = pnKey; this.mitigationKey = mitigationKey; this.traffic = traffic; 
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public MonitoredTraffic(MonitoredTraffic other) {
		this.key = other.key; this.pnKey = other.pnKey; this.mitigationKey = other.mitigationKey; this.traffic = other.traffic;
	}

	public MonitoredTraffic(Hashtable<String, Object> monitoredTrafficRow) throws ExceptionControlApp {
		
		this();
		try {
			key = (String) monitoredTrafficRow.get(KEY);
			pnKey = (String) monitoredTrafficRow.get(PNKEY);
			mitigationKey = (String) monitoredTrafficRow.get(MITIGATION_KEY);
			String dstAddrStr = (String) monitoredTrafficRow.get(DST_ADDR);
			traffic.dstAddr = dstAddrStr.isEmpty() ? null : InetAddress.getByName((String) monitoredTrafficRow.get(DST_ADDR));
			
			Iterator<Map.Entry<String,Object>> iter = monitoredTrafficRow.entrySet().iterator();
			Map.Entry<String,Object> entry; String key; ProtocolPort protocolPort; ArrayList<Integer> ports;
			
			while(iter.hasNext()) {
				
				entry = iter.next();
				key = entry.getKey();
				if(! key.startsWith(PROTO_PORT_PREFIX)) continue;
				
				protocolPort = new ProtocolPort((String)entry.getValue());
				ports = traffic.protoPorts.get(protocolPort.protocol.name());
				if(ports == null) {
					ports = new ArrayList<Integer>();
					traffic.protoPorts.put(protocolPort.protocol.name(), ports);
				}
				if(protocolPort.port != 0)
					ports.add(protocolPort.port);
			}
		} catch (Throwable e) {
			log.error("Excepted trying to inflate MonitoredTraffic from row.", e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate MonitoredTraffic from row.", e);
		}
	}

	public Hashtable<String, Object> toRow() {
		
		if(key == null) return null;
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(pnKey == null) pnKey = "";
		if(mitigationKey == null) mitigationKey = "";
		String dstAddrStr = traffic.dstAddr == null ? "" : traffic.dstAddr.getHostName();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(KEY, key);
		row.put(PNKEY, pnKey);
		row.put(MITIGATION_KEY, mitigationKey);
		row.put(DST_ADDR, dstAddrStr);
		
		Iterator<Map.Entry<String, ArrayList<Integer>>> iter = traffic.protoPorts.entrySet().iterator();
		Map.Entry<String, ArrayList<Integer>> entry; DFProtocol dfProtocol; ArrayList<Integer> ports; ProtocolPort protocolPort;
		String protoPortStr;
		
		while(iter.hasNext()) {
			
			entry = iter.next();
			dfProtocol = DFProtocol.valueOf(entry.getKey());
			ports = entry.getValue();
			if(ports == null || ports.isEmpty()) {
				protocolPort = new ProtocolPort(dfProtocol, 0);
				protoPortStr = protocolPort.toString();
				row.put(PROTO_PORT_PREFIX + "_" + protoPortStr, protoPortStr);
				continue;
			}
			for(int port : ports) {
				protocolPort = new ProtocolPort(dfProtocol, port);
				protoPortStr = protocolPort.toString();
				row.put(PROTO_PORT_PREFIX + "_" + protoPortStr, protoPortStr);
			}
		}
		
		return row;
	}
	
	@Override
	public String toString() {
		if(key == null) key = "";
		if(pnKey == null) pnKey = "";
		if(mitigationKey == null) mitigationKey = "";
		String dstAddrStr = traffic.dstAddr == null ? "" : traffic.dstAddr.getHostName();
		String s = "MonitoredTraffic [key:" + key + ", pnkey:" + pnKey + ", mitigationKey:" + mitigationKey
				 + ", dstAddr:" + dstAddrStr + ", protoPorts:" + traffic.protoPorts.toString() + "]";
		return s;
	}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	
	public String getPnKey() {return pnKey;}
	public void setPnKey(String pnKey) {this.pnKey = pnKey;}
	
	public String getMitigationKey() {return mitigationKey;}
	public void setMitigationKey(String mitigationKey) {this.mitigationKey = mitigationKey;}

	public InetAddress getDstAddr() {return traffic.dstAddr;}
	public void setDstAddr(InetAddress dstAddr) {this.traffic.dstAddr = dstAddr;}	

	public Hashtable<String,ArrayList<Integer>>  getProtoPorts() {return traffic.protoPorts;}
	public void setProtoPorts(Hashtable<String,ArrayList<Integer>> protoPorts) {this.traffic.protoPorts = protoPorts;}

	public static List<RepoCD> getRCDs() {

		if(mMonitoredTrafficRepoCDs == null) {
			RepoCD rcd;
			mMonitoredTrafficRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY,      StringSerializer.get(), null);		mMonitoredTrafficRepoCDs.add(rcd);
			rcd = new RepoCD(PNKEY,    StringSerializer.get(), null);		mMonitoredTrafficRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_KEY, StringSerializer.get(), null);	mMonitoredTrafficRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR, StringSerializer.get(), null);		mMonitoredTrafficRepoCDs.add(rcd);
			//	             PROTO_PORT_PREFIX - StringSerializer
		}		
		return mMonitoredTrafficRepoCDs;
	}
}
