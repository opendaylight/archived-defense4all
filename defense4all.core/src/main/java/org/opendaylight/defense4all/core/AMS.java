/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
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

import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String FOR_STATS_COLLECTION = "for_stats_collection";
	public static final String FOR_DIVERSION = "for_diversion";
	public static final String STATUS = "status";
	public static final String HEALTH_CHECK_FREQUENCY = "health_check_frequency";
	public static final String HEALTH_STATUS = "up";
	public static final String PROPS = "props";
	public static final String SECURITY_CONFIG_PREFIX = "security_config_";
	
	public static final int DEFAULT_HEALTH_CHECK_FREQUENCY = 10;
	
	public enum Status {
		ACTIVE,
        UNKNOWN,
		REMOVED
	}

	
	static Logger log = LoggerFactory.getLogger(AMS.class);

	protected static ArrayList<RepoCD> amsRepoCDs = null;
	
	public String label;
	public String brand;
	public String version;
	public InetAddress mgmtAddr;
	public int mgmtPort;
	public String username;
	public String password;
	public boolean forStatsCollection;
	public boolean forDiversion;
	public int healthCheckFrequency; // When in-path in secs. When out of path - decrease frequency by X 10	
	public boolean up;
	public Properties props;
	public List<String> securityConfigKeys;
	public Status status;
	
	/* ### Description ###
	 * @param param_name 
	 */
	public AMS() {
		
		label = null; brand = null; version = null; mgmtAddr = null; mgmtPort = 0; username = null; password = null;
		forStatsCollection = forDiversion = false; healthCheckFrequency = DEFAULT_HEALTH_CHECK_FREQUENCY; up = true;
		props = new Properties(); status = Status.ACTIVE;
		securityConfigKeys = new ArrayList<String>();
	}
	
	/* ### Description ###
	 * @param param_name 
	 */
	public AMS(String label, String brand, String version, InetAddress mgmtAddr, int port, boolean forStatsCollection, 
			boolean forDiversion, int healthCheckFrequency, String username, String password, List<String> securityConfigKeys, 
			Properties props) throws UnknownHostException {
		
		this.label = label;	this.brand = brand; this.version = version; this.mgmtAddr = mgmtAddr;	
		this.mgmtPort = port; this.username = username; this.password = password; this.forStatsCollection = forStatsCollection;	
		this.forDiversion = forDiversion; this.healthCheckFrequency = healthCheckFrequency; up = true;
		this.props = props == null ? new Properties() : props;
		this.securityConfigKeys = securityConfigKeys;
		if(label == null || label.isEmpty()) label = "ams_" + mgmtAddr.getHostName();
		status = Status.ACTIVE;
	}
	
	public AMS(Hashtable<String, Object> amsRow) {
		
		label = (String) amsRow.get(LABEL);
		brand = (String) amsRow.get(BRAND);
		version = (String) amsRow.get(VERSION);
		try {
            String mgmtAddrStr = (String) amsRow.get(MGMT_IP_ADDR_STRING);
            if(!"".equals(mgmtAddrStr)) {//some AMS may not have mgmt ip
                mgmtAddr = InetAddress.getByName(mgmtAddrStr);
            }
		} catch (UnknownHostException e) {/* Ignore - some AMSs may not be manageable via Defense4All */}
		mgmtPort = (Integer) amsRow.get(MGMT_PORT);
		username = (String) amsRow.get(USERNAME);
		password = (String) amsRow.get(PASSWORD);
		forStatsCollection = (Boolean) amsRow.get(FOR_STATS_COLLECTION);
		forDiversion = (Boolean) amsRow.get(FOR_DIVERSION);
		healthCheckFrequency = (Integer) amsRow.get(HEALTH_CHECK_FREQUENCY);
		up = (Boolean) amsRow.get(HEALTH_STATUS);
		props = (Properties) amsRow.get(PROPS);
		status = Status.valueOf((String) amsRow.get(STATUS));
		

		/* Retrieve all securityConfigKeys */
		Iterator<Map.Entry<Object,Object>> iter = props.entrySet().iterator();
		Map.Entry<Object,Object> entry; String key; String securityconfigStr;
		securityConfigKeys = new ArrayList<String>();
		while(iter.hasNext()) {
			entry = iter.next();
			key = (String) entry.getKey();
			if(key.startsWith(SECURITY_CONFIG_PREFIX)) {
				securityconfigStr = (String) (entry.getValue());
				if(securityconfigStr == null) {
					log.error("Securityconfig cell " + key + " in AMS " + label + " has null value.");
					FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;
				}
				securityConfigKeys.add(securityconfigStr);
			}
		}
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(label == null) label = "";
		if(brand == null) brand = "";
		if(version == null ) version = "";
		if(username == null ) username = "";
		if(password == null ) password = "";
		if(props == null) props = new Properties();
		String mgmtAddrStr = mgmtAddr == null ? "" : mgmtAddr.getHostAddress();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(LABEL, label);
		row.put(BRAND, brand);
		row.put(VERSION, version);
		row.put(MGMT_IP_ADDR_STRING, mgmtAddrStr);
		row.put(MGMT_PORT, mgmtPort);
		row.put(USERNAME, username);
		row.put(PASSWORD, password);
		row.put(FOR_STATS_COLLECTION, forStatsCollection);
		row.put(FOR_DIVERSION, forDiversion);
		row.put(STATUS, status.name());
		row.put(HEALTH_CHECK_FREQUENCY, healthCheckFrequency);
		row.put(HEALTH_STATUS, up);
		row.put(PROPS, props);
		for(String securityConfigKey : securityConfigKeys)
			row.put(SECURITY_CONFIG_PREFIX + securityConfigKey, securityConfigKey);
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
	
	public boolean getUp() {return up;}
	public void setUp(boolean up) {this.up = up;}
	
	public Properties getProps() {return props;}
	public void setProps(Properties props) {this.props = props;}
	
	public String getUsername() {return username;}
	public void setUsername(String username) {this.username = username;}

	public String getPassword() {return password;}
	public void setPassword(String password) {this.password = password;}
	
	public List<String> getSecurityConfigKeys() {return securityConfigKeys;}
	public void setSecurityConfigKeys(List<String> securityConfigKeys) {this.securityConfigKeys = securityConfigKeys;}
	
	public Status getStatus() {return status;}
	public void setStatus(Status status) {this.status = status;}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("AMS[label="); sb.append(label); sb.append(", ");
		sb.append("brand="); sb.append(brand); sb.append(", ");
		sb.append("version="); sb.append(version); sb.append(", ");
		sb.append("mgmtAddr="); sb.append(mgmtAddr); sb.append(", ");
		sb.append("mgmtPort="); sb.append(mgmtPort); sb.append(", ");
		sb.append("username="); sb.append(username); sb.append(", ");
		sb.append("password="); sb.append(password); sb.append(", ");
		sb.append("forStatsCollection="); sb.append(forStatsCollection); sb.append(", ");
		sb.append("forDiversion="); sb.append(forDiversion); sb.append(", ");
		sb.append("healthCheckFrequency="); sb.append(healthCheckFrequency); sb.append(", ");
		sb.append("up="); sb.append(up); sb.append(", ");
		for(String securityConfigKey : securityConfigKeys) {
			sb.append("securityConfigKey="); sb.append(securityConfigKey); sb.append(", ");
		}
		sb.append("props="); sb.append(props.toString());
		if ( status == Status.REMOVED ) {
			sb.append("status="); sb.append(status); sb.append(", ");
		}
		
		sb.append("]");
		return sb.toString();
	}
	
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
			rcd = new RepoCD(STATUS, StringSerializer.get(), null);	 amsRepoCDs.add(rcd);
			rcd = new RepoCD(HEALTH_CHECK_FREQUENCY, IntegerSerializer.get(), null); amsRepoCDs.add(rcd);
			rcd = new RepoCD(HEALTH_STATUS, StringSerializer.get(), null); amsRepoCDs.add(rcd);
			rcd = new RepoCD(PROPS, PropertiesSerializer.get(), null); amsRepoCDs.add(rcd);
			
		}		
		return amsRepoCDs;
	}
	
	public void toJacksonFriendly() {
		// TODO Auto-generated method stub	
	}

	public void validate() throws Exception {
		if(label == null || label.isEmpty()) throw new Exception("Invalid ams label.");
        if(!"other".equalsIgnoreCase(brand)) {
            if (mgmtAddr == null) throw new Exception("Invalid ams address.");
            if (mgmtPort < 0) throw new Exception("Invalid ams port.");
            if (username == null || username.isEmpty()) throw new Exception("Invalid ams username.");
            if (password == null || password.isEmpty()) throw new Exception("Invalid ams password.");
        }
	}
	

	/* Check if AMS is alive */
	public static boolean isUp(String amsLabel) {
		
		try {
			boolean amsUp = (Boolean) DFHolder.get().amsRepo.getCellValue(amsLabel, HEALTH_STATUS);
			return amsUp;
		} catch (Throwable e1) {
			String msg = "Failed to retrieve AMS liveness from repo. ";
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			log.error(msg + e1.getLocalizedMessage());
			return false;	
		}
	}
	
	public static boolean isRemoved(String netNodeLabel) {

		try {
			String amsStatusStr = (String) DFHolder.get().amsRepo.getCellValue(netNodeLabel, AMS.STATUS);
			Status amsStatus = Status.valueOf(amsStatusStr);
			if( amsStatus == Status.REMOVED) return true;
			return false;
		} catch (Throwable e) {return false;}
	}

	public static boolean isRemoved(Hashtable<String,Object> amsRow) {

		try {
			String amsStatusStr = (String) amsRow.get(STATUS);
			Status amsStatus = Status.valueOf(amsStatusStr);
			if(amsStatus == Status.REMOVED) return true;
			return false;
		} catch (Throwable e) {return false;}
	}
}
