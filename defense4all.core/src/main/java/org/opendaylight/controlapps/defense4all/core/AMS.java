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

import org.opendaylight.controlapps.framework.core.PropertiesSerializer;
import org.opendaylight.controlapps.framework.core.RepoCD;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;


public class AMS {

	/* AMS Repo common columns */
	public static final String LABEL = "label";
	public static final String BRAND = "brand";
	public static final String VERSION = "version";
	public static final String MGMT_IP_ADDR_STRING = "mgmt_ip_addr_string";
	public static final String MGMT_PORT = "mgmt_port";
	public static final String FOR_STATS_COLLECTION = "for_stats_collection";
	public static final String FOR_DIVERSION = "for_diversion";
	public static final String HEALTH_CHECK_FREQUENCY = "health_check_frequency";
	public static final String PROPS = "props";
	
	public static final int DEFAULT_HEALTH_CHECK_FREQUENCY = 10;

	protected static ArrayList<RepoCD> amsRepoCDs = null;
	
	public String label;
	public String brand;
	public String version;
	public InetAddress mgmtAddr;
	public int mgmtPort;
	public boolean forStatsCollection;
	public boolean forDiversion;
	public int healthCheckFrequency; // When in-path in secs. When out of path - decrease frequency by X 10	
	public Properties props;
	
	/* ### Description ###
	 * @param param_name 
	 */
	public AMS() {
		
		label = null; brand = null; version = null; mgmtAddr = null; mgmtPort = 0; 
		forStatsCollection = forDiversion = false; healthCheckFrequency = DEFAULT_HEALTH_CHECK_FREQUENCY;
		props = new Properties();
	}
	
	/* ### Description ###
	 * @param param_name 
	 */
	public AMS(String label, String brand, String version, InetAddress mgmtAddr, int port, 
			boolean forStatsCollection, boolean forDiversion, int healthCheckFrequency, Properties props) 
					throws UnknownHostException {
		
		this.label = label;	this.brand = brand; this.version = version; this.mgmtAddr = mgmtAddr;	
		this.mgmtPort = port; this.forStatsCollection = forStatsCollection;	this.forDiversion = forDiversion; 
		this.healthCheckFrequency = healthCheckFrequency; 
		this.props = props == null ? new Properties() : props;
		if(label == null || label.isEmpty()) label = "ams_" + mgmtAddr.getHostName();
	}
	
	public AMS(Hashtable<String, Object> amsRow) {
		
		label = (String) amsRow.get(LABEL);
		brand = (String) amsRow.get(BRAND);
		version = (String) amsRow.get(VERSION);
		try {
			mgmtAddr = InetAddress.getByName((String) amsRow.get(MGMT_IP_ADDR_STRING));
		} catch (UnknownHostException e) {/* Ignore - some AMSs may not be manageable via Defense4All */}
		mgmtPort = (Integer) amsRow.get(MGMT_PORT);
		forStatsCollection = (Boolean) amsRow.get(FOR_STATS_COLLECTION);
		forDiversion = (Boolean) amsRow.get(FOR_DIVERSION);
		healthCheckFrequency = (Integer) amsRow.get(HEALTH_CHECK_FREQUENCY);
		props = (Properties) amsRow.get(PROPS);
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(label == null) label = "";
		if(brand == null) brand = "";
		if(version == null ) version = "";
		if(props == null) props = new Properties();
		String mgmtAddrStr = mgmtAddr == null ? "" : mgmtAddr.getHostAddress();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(LABEL, label);
		row.put(BRAND, brand);
		row.put(VERSION, version);
		row.put(MGMT_IP_ADDR_STRING, mgmtAddrStr);
		row.put(MGMT_PORT, mgmtPort);
		row.put(FOR_STATS_COLLECTION, forStatsCollection);
		row.put(FOR_DIVERSION, forDiversion);
		row.put(HEALTH_CHECK_FREQUENCY, healthCheckFrequency);
		row.put(PROPS, props);
		return row;
	}

	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}
	
	public String getBrand() {return brand;}
	public void setBrand(String brand) {this.brand = brand;}

	public String getVersion() {return version;}
	public void setVersion(String version) {this.version = version;}

	public InetAddress getMgmtAddress() {return mgmtAddr;}
	public void setMgmtAddress(InetAddress mgmtAddress) {this.mgmtAddr = mgmtAddress;}
	
	public int getMgmtPort() {return mgmtPort;}
	public void setMgmtPort(int mgmtPort) {this.mgmtPort = mgmtPort;}
	
	public boolean getForStatsCollection() {return forStatsCollection;}
	public void setForStatsCollection(boolean forStatsCollection) {this.forStatsCollection = forStatsCollection;}
	
	public boolean getForDiversion() {return forDiversion;}
	public void setForDiversion(boolean forDiversion) {this.forDiversion = forDiversion;}
	
	public int getHealthCheckFrequency() {return healthCheckFrequency;}
	public void setHealthCheckFrequency(int healthCheckFrequency) {this.healthCheckFrequency = healthCheckFrequency;}
	
	public Properties getProps() {return props;}
	public void setProps(Properties props) {this.props = props;}	
	
	public static List<RepoCD> getAMSRCDs() {

		if(amsRepoCDs == null) {
			RepoCD rcd;
			amsRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(LABEL, StringSerializer.get(), null);	 amsRepoCDs.add(rcd);
			rcd = new RepoCD(BRAND, StringSerializer.get(), null);	 amsRepoCDs.add(rcd);
			rcd = new RepoCD(VERSION, StringSerializer.get(), null); amsRepoCDs.add(rcd);
			rcd = new RepoCD(MGMT_IP_ADDR_STRING, StringSerializer.get(), null);	amsRepoCDs.add(rcd);
			rcd = new RepoCD(MGMT_PORT, IntegerSerializer.get(), null);	 amsRepoCDs.add(rcd);
			rcd = new RepoCD(FOR_STATS_COLLECTION, BooleanSerializer.get(), null); amsRepoCDs.add(rcd);
			rcd = new RepoCD(FOR_DIVERSION, BooleanSerializer.get(), null);	amsRepoCDs.add(rcd);
			rcd = new RepoCD(HEALTH_CHECK_FREQUENCY, BooleanSerializer.get(), null); amsRepoCDs.add(rcd);
			rcd = new RepoCD(PROPS, PropertiesSerializer.get(), null); amsRepoCDs.add(rcd);
		}		
		return amsRepoCDs;
	}
}
