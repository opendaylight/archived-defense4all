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
import java.net.Inet6Address;
import java.net.UnknownHostException;
import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class PO {  

	/**
	 * ### Description ### 
	 */
	public enum IpVersion {		
		INVALID,
		IPV4,
		IPV6
	}

	static Logger log = LoggerFactory.getLogger(PO.class);

	/* pnRepo column names */
	/* first group is configurable column names */
	public static final String LABEL = "label";
	public static final String IP_VERSION = "ip_version";
	public static final String DST_ADDR = "dest_addr";
	public static final String DST_ADDR_PREFIX_LEN = "dest_addr_prefix_len";
	public static final String PROPS = "props";
	public static final String VIRTUAL_NETWORK_ID = "virtual_network_id";
	public static final String PROTECTION_SLA = "protection_SLA";

	public String 	label;
	public IpVersion ipVersion;
	public InetAddress dstAddr;
	public int 		dstAddrPrefixLen;
	public String   virtualNetid;
	public ProtectionSLA protectionSLA;
	public Properties props;

	protected static ArrayList<RepoCD> posRepoCDs = null;

	/** ### Description ###
	 * @param param_name 
	 */
	public PO() {

		label = null; dstAddr = null; dstAddrPrefixLen = 0;
		this.ipVersion = Inet6Address.class.isInstance(dstAddr) ? IpVersion.IPV6 : IpVersion.IPV4;
		protectionSLA = null; props = new Properties(); virtualNetid = "";
	}

	/** ### Description ###
	 * @param param_name 
	 * @throws UnknownHostException
	 * @throws IllegalArgumentException 
	 * @throws ExceptionControlApp 
	 */
	public PO(String label, String dstAddrStr, int dstAddrPrefixLen, ProtectionSLA protectionSLA, String virtualNetid,  
			Properties props) throws UnknownHostException {

		this();

		this.label = label;	
		this.ipVersion = Inet6Address.class.isInstance(dstAddr) ? IpVersion.IPV6 : IpVersion.IPV4;
		this.dstAddr = InetAddress.getByName(dstAddrStr); // Throws exception if address is not valid.
		this.dstAddrPrefixLen = dstAddrPrefixLen;
		this.protectionSLA = protectionSLA;
		this.virtualNetid = virtualNetid != null ? virtualNetid : "";
		this.props = (props == null ? new Properties() : props);
	}

	@SuppressWarnings("unchecked")
	public PO(Hashtable<String, Object> pnRow) throws ExceptionControlApp {

		this();

		try {
			Object obj;
			label = (String) pnRow.get(LABEL);
			dstAddr = InetAddress.getByName((String) pnRow.get(DST_ADDR));
			obj = pnRow.get(IP_VERSION);
			if(obj != null)	
				ipVersion = IpVersion.valueOf((String) obj);
			else
				ipVersion = Inet6Address.class.isInstance(dstAddr) ? IpVersion.IPV6 : IpVersion.IPV4;		
			obj = pnRow.get(DST_ADDR_PREFIX_LEN);
			if(obj != null)	dstAddrPrefixLen = (Integer) obj;
			obj = pnRow.get(PROTECTION_SLA);
			if(obj != null)	protectionSLA = new ProtectionSLA((String) obj);
			obj = pnRow.get(VIRTUAL_NETWORK_ID);
			if(obj != null) virtualNetid = (String) obj;
			obj = pnRow.get(PROPS);
			if(obj != null && Map.class.isInstance(obj)) props.putAll( ( Map<String,Object> )obj);
		} catch (Throwable e) {
			log.error("Excepted trying to inflate PO from row. " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate PO from row. ", e);
		}
	}

	public Hashtable<String, Object> toRow() throws IllegalArgumentException {

		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(label == null) label = "";
		if(protectionSLA == null) protectionSLA = new ProtectionSLA("");
		if(virtualNetid == null)  virtualNetid = "";
		if(props == null) props = new Properties();
		if(dstAddr == null) {
			try {
				dstAddr = InetAddress.getLocalHost();
			} catch (UnknownHostException e) {
				log.error("Failed to obtain local host. " + e.getLocalizedMessage());
				throw new IllegalArgumentException("Failed to obtain local host. " + e.getLocalizedMessage());
			}
		}

		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(LABEL, label);
		row.put(IP_VERSION, ipVersion.name());
		row.put(DST_ADDR, dstAddr.getHostAddress());
		row.put(DST_ADDR_PREFIX_LEN, dstAddrPrefixLen);
		row.put(PROTECTION_SLA, protectionSLA.toString());
		row.put(PROPS, props);
		row.put(VIRTUAL_NETWORK_ID, virtualNetid);
		
		return row;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("PN[label="); sb.append(label); sb.append(", ");
		sb.append("ipVersion="); sb.append(ipVersion); sb.append(", ");
		sb.append("dstAddr="); sb.append(dstAddr); sb.append(", ");
		sb.append("dstAddrPrefixLen="); sb.append(dstAddrPrefixLen); sb.append(", ");
		sb.append("virtualNetid="); sb.append(virtualNetid); sb.append(", ");
		sb.append("protectionSLA="); sb.append(protectionSLA.toString()); sb.append(", ");
		sb.append("props="); sb.append(props.toString());
		sb.append("]");
		return sb.toString();
	}

	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}

	public IpVersion getIpVersion() {return ipVersion;}
	public void setIpVersion(IpVersion ipVersion) {this.ipVersion = ipVersion;}

	public String getDstAddr() {return dstAddr.toString();}
	public void setDstAddr(String dstAddr) throws UnknownHostException {
		String addrStr = dstAddr.replace("/", ""); // Serialized InetAddress adds "/" at the beginning 
		this.dstAddr = InetAddress.getByName(addrStr);
	}

	public int getDstAddrPrefixLen() {return dstAddrPrefixLen;}
	public void setDstAddrPrefixLen(int dstAddrPrefixLen) {this.dstAddrPrefixLen = dstAddrPrefixLen;}

	public String getProtectionSLA() {return protectionSLA.toString();}
	public void setProtectionSLA(ProtectionSLA protectionSLA) {this.protectionSLA = protectionSLA;}

	public Properties getProps() {return props;}
	public void setProps(Properties props) {this.props = props;}

	public String getVirtualNetid() {return virtualNetid;}
	public void setVirtualNetid(String virtualNetid) {this.virtualNetid = virtualNetid;}

	public static List<RepoCD> getPORCDs() {

		if(posRepoCDs == null) {
			RepoCD rcd;
			posRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(LABEL, StringSerializer.get(), null);			posRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR, StringSerializer.get(), null);		posRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR_PREFIX_LEN, IntegerSerializer.get(), null);	posRepoCDs.add(rcd);
			rcd = new RepoCD(IP_VERSION, StringSerializer.get(), null);		posRepoCDs.add(rcd);
			rcd = new RepoCD(PROTECTION_SLA, StringSerializer.get(), null); posRepoCDs.add(rcd);
			rcd = new RepoCD(PROPS, PropertiesSerializer.get(), null); 		posRepoCDs.add(rcd);
			rcd = new RepoCD(VIRTUAL_NETWORK_ID, StringSerializer.get(), null); posRepoCDs.add(rcd);
		}		
		return posRepoCDs;
	}
}
