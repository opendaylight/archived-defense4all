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
import java.util.Iterator;
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

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class PN {  

	/**
	 * ### Description ### 
	 */
	public enum IpVersion {		
		INVALID,
		IPV4,
		IPV6
	}

	/* Additional pnRepo column names */
	public enum StatsCollectionStatus {
		INVALID,
		NONE, 		// No stats collection locations are found yet. Keep retrying when topology changes.
		STOPPED, 	// Stats collection has been stopped. Can be because of attack mitigation, resource preemption, etc.
		ACTIVE		// Stats are collected in the PFSs and should be periodically harvested.
	}

	public enum MitigationScope {
		INVALID,
		ATTACKED, // Divert only attacked traffic
		ALL		  // Divert all traffic
	}

	static Logger log = LoggerFactory.getLogger(PN.class);

	/* pnRepo column names */
	/* first group is configurable column names */
	public static final String LABEL = "label";
	public static final String IP_VERSION = "ip_version";
	public static final String DST_ADDR = "dest_addr";
	public static final String DST_ADDR_PREFIX_LEN = "dest_addr_prefix_len";
	public static final String PROTOCOL_PORT = "protocol_port";
	public static final String AMS_CONFIG_PROPS = "ams_config_props";
	public static final String PROTECTION_SLA = "protection_SLA";
	public static final String MITIGATION_CONFIRMATION = "mitigation_confirmation";
	public static final String DETECTOR_LABEL = "detector_label";
	public static final String THRESHOLDS = "thresholds";	
	public static final String MITIGATION_SCOPE = "mitigation_scope";
	public static final String PROPS = "props";
	public static final String OF_BASED_DETECTION = "of_based_detection";
	public static final String SYMMETRIC_DVSN = "symmetric_dvsn";
	public static final String VIRTUAL_NETWORK_ID = "virtual_network_id";
	public static final String ANOMALY_RATE_THRESHOLD_PERCENTAGE = "anomaly_rate_threshold_percentage";
	public static final String NETNODE_PREFIX = "netnode_";

	/* second group is dynamic column names */
	public static final String AVERAGES = "averages";	
	public static final String LATEST_RATES = "latest_rates";
	public static final String BASELINES = "baselines";
	public static final String LATEST_RATES_TIME = "latest_rates_time";
	public static final String BASELINES_TIME = "baselines_time";
	public static final String STATS_COLLECTION_STATUS = "stats_collection_status";	
	public static final String PN_CANCELED = "pn_canceled";
	public static final String ATTACK_SUSPICIONS = "attack_suspicions";
	public static final String TRAFFIC_FLOOR_KEY_PREFIX = "traffic_floor_key_";

	/* Other constants */
	public static final long DEFAULT_ANOMALY_THERSHOLD_PERCENTAGE = 100; // Suspect attack if the rate doubles the normal
	public static final String AMS_PROPERTY_PREFIX = "ams_property_";		// Loaded from properties file.

	public String 	label;

	public IpVersion ipVersion;
	public InetAddress dstAddr;
	public int 		dstAddrPrefixLen;
	public String   virtualNetid;
	public ProtocolPort protocolPort;

	public Properties amsConfigProps;	
	public ProtectionSLA protectionSLA;  
	public boolean 	mitigationConfirmation;
	public String 	detectorLabel;

	public String 	thresholdStr;
	public String	averageStr;
	public String 	latestRateStr;
	public String 	baselineStr;
	public long 	latestRateTime;
	public long 	baselinesTime;
	public boolean  ofBasedDetection;
	public boolean  symmetricDvsn;
	public long 	anomalyThresholdPercentage;
	public boolean 	pnCanceled;
	public String 	attackSuspicions;
	public MitigationScope mitigationScope;
	public List<String> trafficFloorKeys;
	public List<String> netNodeLabels = null; // If empty, all previously set netNodes are assumed.

	/* Subclass specific stuff - insulates DFMgmtPoint from knowing about ProtectedNetwork sub-classes 
	 * when putting the object in protected networks repo. */	
	public Properties props;

	/* Dynamic part of the protected network. */
	public StatsCollectionStatus statsCollectionStatus;

	protected static ArrayList<RepoCD> mPNsRepoCDs = null;

	protected static int assertAddrPrefixLen(int prefixLen, IpVersion ipVersion) throws IllegalArgumentException {
		if(prefixLen < 0) throw new IllegalArgumentException("Prefix len can not be negative");
		if(prefixLen <=32) return prefixLen;
		if(ipVersion == IpVersion.IPV6 && prefixLen <=128) return prefixLen;

		log.error("Invalid prefix len parameter - " + prefixLen + " for " + ipVersion);
		throw new IllegalArgumentException("Invalid prefix len parameter - " + prefixLen + " for " + ipVersion);
	}

	protected static int assertPort(int port) throws IllegalArgumentException{

		if(port < 0 || port > 65535) {
			log.error("Invalid port number parameter - " + port);
			throw new IllegalArgumentException("Invalid port number parameter - " + port);
		}
		return port;
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public PN() {

		label = null; dstAddr = null; dstAddrPrefixLen = 0;
		this.ipVersion = Inet6Address.class.isInstance(dstAddr) ? IpVersion.IPV6 : IpVersion.IPV4;
		protocolPort = new ProtocolPort();
		protectionSLA = null; mitigationConfirmation = false;
		detectorLabel = ""; props = new Properties(); amsConfigProps = new Properties();
		thresholdStr = averageStr = latestRateStr = baselineStr = ""; 
		latestRateTime = baselinesTime = 0; statsCollectionStatus = StatsCollectionStatus.INVALID;
		ofBasedDetection = false; pnCanceled = false;
		mitigationScope = MitigationScope.INVALID; trafficFloorKeys = new ArrayList<String>();
		netNodeLabels = new ArrayList<String>();
		symmetricDvsn = true; 
		virtualNetid = "";
		attackSuspicions = "";
		anomalyThresholdPercentage = DEFAULT_ANOMALY_THERSHOLD_PERCENTAGE;
	}

	/** ### Description ###
	 * @param param_name 
	 * @throws UnknownHostException
	 * @throws IllegalArgumentException 
	 * @throws ExceptionControlApp 
	 */
	public PN(String label, String dstAddrStr, int dstAddrPrefixLen, ProtocolPort protocolPort,
			Properties amsConfigProps, ProtectionSLA protectionSLA, boolean mitigationConfirmation,
			String detectorLabel, String thresholdStr, MitigationScope mitigationScope, boolean ofBasedDetection, 
			boolean symmetricDvsn, String virtualNetid, long anomalyThresholdPercentage,
			List<String> trafficFloorKeys, List<String> netNodeLabels, 
			Properties props) throws UnknownHostException, IllegalArgumentException, ExceptionControlApp {

		this();

		this.label = label;	
		this.ipVersion = Inet6Address.class.isInstance(dstAddr) ? IpVersion.IPV6 : IpVersion.IPV4;
		this.dstAddr = InetAddress.getByName(dstAddrStr); // Throws exception if address is not valid.
		this.dstAddrPrefixLen = assertAddrPrefixLen(dstAddrPrefixLen, this.ipVersion);
		this.protocolPort = protocolPort;
		this.amsConfigProps = amsConfigProps; // Null means default configProps are to be used.
		this.protectionSLA = protectionSLA;	this.mitigationConfirmation = mitigationConfirmation;
		this.detectorLabel = detectorLabel;
		this.thresholdStr = thresholdStr; 
		this.mitigationScope = mitigationScope;
		this.ofBasedDetection = ofBasedDetection;
		this.symmetricDvsn = symmetricDvsn; 
		this.virtualNetid = virtualNetid != null ? virtualNetid : "";
		this.anomalyThresholdPercentage = anomalyThresholdPercentage;
		this.amsConfigProps = (amsConfigProps == null ? new Properties() : amsConfigProps);
		if(trafficFloorKeys != null) this.trafficFloorKeys = trafficFloorKeys;
		this.props = (props == null ? new Properties() : props);
		this.netNodeLabels = netNodeLabels;
		if(netNodeLabels == null || netNodeLabels.isEmpty()) {
			try {
				this.netNodeLabels = createFromNetNodesInProps();
			} catch (IllegalArgumentException e) {
				log.error("Failed to create PN. " + e.getLocalizedMessage());
				throw new IllegalArgumentException("Failed to create PN. " + e.getLocalizedMessage());
			}
		}
		if(netNodeLabels == null || netNodeLabels.isEmpty()) {
			try {
				this.netNodeLabels = createFromAllNetNodes();
			} catch (ExceptionControlApp e) {
				log.error("Failed to create PN. " + e.getLocalizedMessage());
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp("Failed to create PN. " + e.getLocalizedMessage());
			}
		}
	}

	/** ### Description ###
	 * @param param_name 
	 * @throws ExceptionControlApp 
	 */
	public PN(PN other) throws ExceptionControlApp {

		this.label = other.label; this.dstAddr = other.dstAddr; this.dstAddrPrefixLen = other.dstAddrPrefixLen;
		this.protocolPort = other.protocolPort.clone();
		this.amsConfigProps = (Properties) other.amsConfigProps.clone(); 
		this.statsCollectionStatus = other.statsCollectionStatus; this.protectionSLA = other.protectionSLA; 
		this.mitigationConfirmation = other.mitigationConfirmation; this.detectorLabel = other.detectorLabel; 
		this.thresholdStr = other.thresholdStr; this.averageStr = other.averageStr; 
		this.baselineStr = other.baselineStr; this.latestRateStr = other.latestRateStr;
		this.latestRateTime = other.latestRateTime; this.baselinesTime = other.baselinesTime;
		this.props = (Properties) other.props.clone(); this.statsCollectionStatus = other.statsCollectionStatus;
		this.mitigationScope = other.mitigationScope;
		this.ofBasedDetection = other.ofBasedDetection;
		this.trafficFloorKeys = other.trafficFloorKeys;
		this.symmetricDvsn = other.symmetricDvsn; 
		this.virtualNetid = other.virtualNetid;
		this.anomalyThresholdPercentage = other.anomalyThresholdPercentage;
		this.netNodeLabels = other.netNodeLabels;
		if(netNodeLabels == null || netNodeLabels.isEmpty()) {
			try {
				this.netNodeLabels = createFromAllNetNodes();
			} catch (ExceptionControlApp e) {
				log.error("Failed to create PN. " + e.getLocalizedMessage());
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp("Failed to create PN. " + e.getLocalizedMessage());
			}
		}
	}

	@SuppressWarnings("unchecked")
	public PN(Hashtable<String, Object> pnRow) throws ExceptionControlApp {

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
			obj = pnRow.get(PROTOCOL_PORT);
			if(obj != null)	protocolPort = new ProtocolPort((String) obj);
			obj = pnRow.get(AMS_CONFIG_PROPS);
			if(obj != null && Map.class.isInstance(obj)) amsConfigProps.putAll(( Map<String,Object> )obj);
			obj = pnRow.get(PROTECTION_SLA);
			if(obj != null)	protectionSLA = new ProtectionSLA((String) obj);
			obj = pnRow.get(MITIGATION_CONFIRMATION);
			if(obj != null)	mitigationConfirmation =  (Boolean) obj;
			detectorLabel = (String) pnRow.get(DETECTOR_LABEL);
			thresholdStr = (String) pnRow.get(THRESHOLDS);
			averageStr = (String) pnRow.get(AVERAGES);
			latestRateStr = (String) pnRow.get(LATEST_RATES);
			baselineStr = (String) pnRow.get(BASELINES);
			obj = pnRow.get(MITIGATION_SCOPE);
			if(obj != null) mitigationScope = MitigationScope.valueOf((String) obj);
			obj = pnRow.get(OF_BASED_DETECTION);
			if(obj != null) ofBasedDetection = (Boolean) obj;
			obj = pnRow.get(SYMMETRIC_DVSN);
			if(obj != null) symmetricDvsn = (Boolean) obj;
			obj = pnRow.get(VIRTUAL_NETWORK_ID);
			if(obj != null) virtualNetid = (String) obj;
			obj = pnRow.get(ANOMALY_RATE_THRESHOLD_PERCENTAGE);
			if(obj != null) anomalyThresholdPercentage = (Long) obj;
			obj = pnRow.get(PROPS);
			if(obj != null && Map.class.isInstance(obj)) props.putAll( ( Map<String,Object> )obj);
			// REST API will not send non-configuration parameters keep it default in this case.
			obj = pnRow.get(LATEST_RATES_TIME); 
			if(obj != null)	latestRateTime = (Long) obj;
			obj = pnRow.get(BASELINES_TIME); 
			if(obj != null)	baselinesTime = (Long) obj;
			obj = pnRow.get(STATS_COLLECTION_STATUS);
			if(obj != null)	statsCollectionStatus = StatsCollectionStatus.valueOf((String) obj);
			obj = pnRow.get(PN_CANCELED);
			if(obj != null)	pnCanceled = (Boolean) obj;
			attackSuspicions = (String) pnRow.get(ATTACK_SUSPICIONS);

			Iterator<Map.Entry<String,Object>> iter = pnRow.entrySet().iterator();
			Map.Entry<String,Object> entry; String value;
			while(iter.hasNext()) {
				entry = iter.next();
				if(entry.getKey().startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX)) {
					value = (String)entry.getValue();
					if(value == null) {
						log.error("Traffic floor cell " + entry.getKey() + " in PN " + label + " has null value.");
						FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
						continue;					
					}
					trafficFloorKeys.add(value);
				}
			}

			/* Retrieve all netNode label pairs (key is formatted as netnode_X, where X is some suffix) */
			Iterator<Map.Entry<Object,Object>> iter2 = props.entrySet().iterator();
			Map.Entry<Object,Object> entry2; String key; String netNodeStr;
			netNodeLabels = new ArrayList<String>();
			while(iter2.hasNext()) {
				entry2 = iter2.next();
				key = (String) entry2.getKey();
				if(key.startsWith(NETNODE_PREFIX)) {
					netNodeStr = (String) (entry2.getValue());
					if(netNodeStr == null) {
						log.error("Netnode cell " + key + " in PN " + label + " has null value.");
						FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
						continue;
					}
					netNodeLabels.add(netNodeStr);
				}
			}
			if(netNodeLabels.isEmpty())
				this.netNodeLabels = createFromNetNodesInProps();
			if(netNodeLabels.isEmpty())
				this.netNodeLabels = createFromAllNetNodes();
		} catch (Throwable e) {
			log.error("Excepted trying to inflate PN from row. " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate PN from row. ", e);
		}
	}

	public PN(Properties propsFromFile, Properties props, Properties pnAmsProps, List<String> netNodeLabels) 
			throws UnknownHostException, ExceptionControlApp {

		this();


		try {
			String strobj;
			label = (String) propsFromFile.getProperty(LABEL);
			dstAddr = InetAddress.getByName((String) propsFromFile.getProperty(DST_ADDR));
			strobj = propsFromFile.getProperty(IP_VERSION) ;
			if ( strobj != null )
				ipVersion = IpVersion.valueOf(strobj);
			else
				ipVersion = Inet6Address.class.isInstance(dstAddr) ? IpVersion.IPV6 : IpVersion.IPV4;		
			strobj = propsFromFile.getProperty(DST_ADDR_PREFIX_LEN); 
			if ( strobj != null )	dstAddrPrefixLen =  Integer.valueOf (strobj);
			strobj = propsFromFile.getProperty(PROTOCOL_PORT); 
			if ( strobj != null) protocolPort = new ProtocolPort(strobj);
			if (pnAmsProps != null)
				this.amsConfigProps.putAll(pnAmsProps);
			strobj = propsFromFile.getProperty(PROTECTION_SLA) ; if ( strobj != null )
				protectionSLA = new ProtectionSLA((String) propsFromFile.getProperty(PROTECTION_SLA));
			strobj= propsFromFile.getProperty(MITIGATION_CONFIRMATION); if ( strobj != null )
				mitigationConfirmation =  Boolean.valueOf(propsFromFile.getProperty(MITIGATION_CONFIRMATION).toString());
			detectorLabel = (String) propsFromFile.getProperty(DETECTOR_LABEL);
			thresholdStr = (String) propsFromFile.getProperty(THRESHOLDS);
			averageStr = (String) propsFromFile.getProperty(AVERAGES);
			latestRateStr = (String) propsFromFile.getProperty(LATEST_RATES);
			baselineStr = (String) propsFromFile.getProperty(BASELINES);
			strobj = propsFromFile.getProperty(MITIGATION_SCOPE) ; 
			if ( strobj != null ) mitigationScope = MitigationScope.valueOf(strobj);
			strobj = propsFromFile.getProperty(OF_BASED_DETECTION) ; 
			if ( strobj != null ) ofBasedDetection = Boolean.valueOf(strobj);
			strobj = propsFromFile.getProperty(SYMMETRIC_DVSN) ; 
			if ( strobj != null ) symmetricDvsn = Boolean.valueOf(strobj);
			virtualNetid = propsFromFile.getProperty(VIRTUAL_NETWORK_ID);
			strobj = propsFromFile.getProperty(ANOMALY_RATE_THRESHOLD_PERCENTAGE) ; 
			if ( strobj != null ) anomalyThresholdPercentage = Long.valueOf(strobj);
			if ( props != null) this.props.putAll(props);
			// REST API will not send non-configuration parameters
			// keep it default in this case
			strobj = propsFromFile.getProperty(LATEST_RATES_TIME) ;
			if ( strobj != null ) latestRateTime = Long.valueOf(strobj);
			strobj = propsFromFile.getProperty(BASELINES_TIME) ; 
			if ( strobj != null ) baselinesTime = Long.valueOf(strobj);
			strobj = propsFromFile.getProperty(STATS_COLLECTION_STATUS) ; 
			if ( strobj != null ) statsCollectionStatus = StatsCollectionStatus.valueOf(strobj);
			strobj = propsFromFile.getProperty(PN_CANCELED); 
			if ( strobj != null ) pnCanceled = Boolean.valueOf(strobj);

			/* Set all netNodes */
			this.netNodeLabels = netNodeLabels;
			if(netNodeLabels == null || netNodeLabels.isEmpty()) {
				this.netNodeLabels = createFromAllNetNodes();		
			}

			Iterator<Map.Entry<Object,Object>> iter = propsFromFile.entrySet().iterator();
			Map.Entry<Object,Object> entry;
			while(iter.hasNext()) {
				entry = iter.next();
				if(((String)(entry.getKey())).startsWith(PN.TRAFFIC_FLOOR_KEY_PREFIX))
					trafficFloorKeys.add((String)entry.getValue());
			}
		} catch (Throwable e) {
			log.error("Excepted trying to inflate PN from properties file. " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate PN from properties file. ", e);
		}
	}

	public Hashtable<String, Object> toRow() throws IllegalArgumentException {

		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(label == null) label = "";
		if(protectionSLA == null) protectionSLA = new ProtectionSLA("");
		if(thresholdStr == null)  thresholdStr = "";
		if(averageStr == null)    averageStr = "";
		if(latestRateStr == null) latestRateStr = "";
		if(baselineStr == null)   baselineStr = "";
		if(detectorLabel == null) detectorLabel = "";
		if(virtualNetid == null)  virtualNetid = "";
		if(attackSuspicions == null)  attackSuspicions = "";
		if(amsConfigProps == null) amsConfigProps = new Properties();
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
		row.put(PROTOCOL_PORT, protocolPort.toString());
		row.put(AMS_CONFIG_PROPS, amsConfigProps);
		row.put(PROTECTION_SLA, protectionSLA.toString());
		row.put(MITIGATION_CONFIRMATION, mitigationConfirmation);
		row.put(DETECTOR_LABEL, detectorLabel);
		row.put(THRESHOLDS, thresholdStr);
		row.put(AVERAGES, averageStr);
		row.put(LATEST_RATES, latestRateStr);
		row.put(BASELINES, baselineStr);
		row.put(LATEST_RATES_TIME, latestRateTime);
		row.put(BASELINES_TIME, baselinesTime);
		row.put(PROPS, props);
		row.put(STATS_COLLECTION_STATUS, statsCollectionStatus.name());
		row.put(PN_CANCELED, pnCanceled);
		row.put(MITIGATION_SCOPE, mitigationScope.name());
		row.put(ATTACK_SUSPICIONS, attackSuspicions);
		row.put(OF_BASED_DETECTION, ofBasedDetection);
		row.put(SYMMETRIC_DVSN, symmetricDvsn);
		row.put(VIRTUAL_NETWORK_ID, virtualNetid);
		row.put(ANOMALY_RATE_THRESHOLD_PERCENTAGE, anomalyThresholdPercentage);
		for(String trafficFloorKey : trafficFloorKeys)
			row.put(PN.TRAFFIC_FLOOR_KEY_PREFIX + trafficFloorKey, trafficFloorKey);
		for(String netNodeLabel : netNodeLabels)
			row.put(PN.NETNODE_PREFIX + netNodeLabel, netNodeLabel);

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
		sb.append("protocolPort="); sb.append(protocolPort.toString()); sb.append(", ");
		sb.append("amsConfigProps="); sb.append(amsConfigProps.toString());
		sb.append("protectionSLA="); sb.append(protectionSLA.toString()); sb.append(", ");
		sb.append("mitigationConfirmation="); sb.append(mitigationConfirmation); sb.append(", ");
		sb.append("detectorLabel="); sb.append(detectorLabel); sb.append(", ");
		sb.append("ofBasedDetection="); sb.append(ofBasedDetection); sb.append(", ");
		sb.append("symmetricDvsn="); sb.append(symmetricDvsn); sb.append(", ");
		sb.append("anomalyThresholdPercentage="); sb.append(anomalyThresholdPercentage); sb.append(", ");
		sb.append("mitigationScope="); sb.append(mitigationScope); sb.append(", ");
		sb.append("ofBasedDetection="); sb.append(ofBasedDetection); sb.append(", ");
		for(String netNodeLabel : netNodeLabels) {
			sb.append("netNodeLabel="); sb.append(netNodeLabel); sb.append(", ");
		}
		sb.append("props="); sb.append(props.toString());
		sb.append("]");
		return sb.toString();
	}

	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}

	public IpVersion getIpVersion() {return ipVersion;}
	public void setIpVersion(IpVersion ipVersion) {this.ipVersion = ipVersion;}

	public String getDstAddr() {return dstAddr.toString();}
	public void setDstAddr(InetAddress dstAddr) {this.dstAddr = dstAddr;}

	public int getDstAddrPrefixLen() {return dstAddrPrefixLen;}
	public void setDstAddrPrefixLen(int dstAddrPrefixLen) {this.dstAddrPrefixLen = dstAddrPrefixLen;}

	public ProtocolPort getProtocolPort() {return protocolPort;}
	public void setProtocol(ProtocolPort protocolPort) {this.protocolPort = protocolPort;}

	public String getProtectionSLA() {return protectionSLA.toString();}
	public void setProtectionSLA(ProtectionSLA protectionSLA) {this.protectionSLA = protectionSLA;}

	public boolean getMitigationConfirmation() {return mitigationConfirmation;}
	public void setMitigationConfirmation(boolean confirmation) {this.mitigationConfirmation = confirmation;}

	public String getDetectorLabel() {return detectorLabel;}	
	public void setDetectorLabel(String detectorLabel) {this.detectorLabel = detectorLabel;}

	public String getThresholdStr() {return thresholdStr;}	
	public void setThresholdStr(String thresholdStr) {this.thresholdStr = thresholdStr;}

	public String getAverageStr() {return averageStr;}	
	public void setAverageStr(String averageStr) {this.averageStr = averageStr;}

	public Properties getAmsConfigProps() {return amsConfigProps;}
	public void setAmsConfigProps(Properties amsConfigProps) {this.amsConfigProps = amsConfigProps;}

	public String getBaselineStr() {return baselineStr;}	
	public void setBaselieStr(String baselineStr) {this.baselineStr = baselineStr;}

	public String getLatestRateStr() {return latestRateStr;}
	public void setLatestRateStr(String latestRateStr) {this.latestRateStr = latestRateStr;}

	public long getLatestRateTime() {return latestRateTime;}
	public void setLatestRateTime(long latestRateTime) {this.latestRateTime = latestRateTime;}

	public long getBaselinesTime() {return baselinesTime;}
	public void setBaselinesTime(long baselinesTime) {this.baselinesTime = baselinesTime;}

	public Properties getProps() {return props;}
	public void setProps(Properties props) {this.props = props;}

	public StatsCollectionStatus getStatsCollectionStatus() {return statsCollectionStatus;}	
	public void setStatsCollectionStatus(StatsCollectionStatus scStatus) {this.statsCollectionStatus = scStatus;}

	public void setCanceled() {pnCanceled = true;}
	public boolean isCanceled() {return pnCanceled;}

	public boolean isPnCanceled() {return pnCanceled;}
	public void setPnCanceled(boolean pnCanceled) {this.pnCanceled = pnCanceled;}

	public MitigationScope getMitigationScope() {return mitigationScope;}
	public void setMitigationScope(MitigationScope scope) {this.mitigationScope = scope;}

	public boolean isOfBasedDetection() {return ofBasedDetection;}
	public void setOfBasedDetection(boolean ofBasedDetection) {this.ofBasedDetection = ofBasedDetection;}

	public List<String> getNetNodeLabels() {return netNodeLabels;}
	public void setNetNodePairs(List<String> netNodeLabels) {this.netNodeLabels = netNodeLabels;}

	public boolean isSymmetricDvsn() {return symmetricDvsn;}
	public void setSymmetricDvsn(boolean symmetricDvsn) {this.symmetricDvsn = symmetricDvsn;}

	public String getVirtualNetid() {return virtualNetid;}
	public void setVirtualNetid(String virtualNetid) {this.virtualNetid = virtualNetid;}

	public long getAnomalyThresholdPercentage() {return anomalyThresholdPercentage;}
	public void setAnomalyThresholdPercentage(long percentage) {this.anomalyThresholdPercentage = percentage;}

	protected List<String> createFromNetNodesInProps() throws IllegalArgumentException {

		List<String> netNodeLabels = new ArrayList<String>();
		Iterator<Map.Entry<Object,Object>> iter = props.entrySet().iterator();
		String netNodeLabel; Map.Entry<Object,Object> entry; String key;
		while(iter.hasNext()) {
			entry = iter.next();
			key = (String) entry.getKey();
			if(key.startsWith(PN.NETNODE_PREFIX)) {
				netNodeLabel = (String) entry.getValue();
				if(netNodeLabel == null) {
					log.error("NetNodeLabel is null " + " for " + key);
					throw new IllegalArgumentException("Failed to create from NetNodes in Props. " + " for " + key);
				}
				netNodeLabels.add(netNodeLabel);
			}
		}		
		return netNodeLabels;
	}

	protected List<String> createFromAllNetNodes() throws ExceptionControlApp {	

		List<String> netNodeLabels;
		try {
			netNodeLabels = DFHolder.get().netNodesRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to obtain NetNodeLabels from netNodesRepo",e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to obtain NetNodeLabels from netNodesRepo.", e);
		}
		return new ArrayList<String>(netNodeLabels);
	}

	public static List<RepoCD> getPNRCDs() {

		if(mPNsRepoCDs == null) {
			RepoCD rcd;
			mPNsRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(LABEL, StringSerializer.get(), null);	mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR, StringSerializer.get(), null);	mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(DST_ADDR_PREFIX_LEN, IntegerSerializer.get(), null);	mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(PROTOCOL_PORT, StringSerializer.get(), null);	mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(AMS_CONFIG_PROPS, PropertiesSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(PROTECTION_SLA, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_CONFIRMATION, BooleanSerializer.get(), null);	mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(DETECTOR_LABEL, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(THRESHOLDS, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(AVERAGES, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(LATEST_RATES, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(LATEST_RATES_TIME, LongSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(BASELINES, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(PROPS, PropertiesSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(STATS_COLLECTION_STATUS, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(OF_BASED_DETECTION, BooleanSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(SYMMETRIC_DVSN, BooleanSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(VIRTUAL_NETWORK_ID, BooleanSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(ANOMALY_RATE_THRESHOLD_PERCENTAGE, BooleanSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(PN_CANCELED, BooleanSerializer.get(), null); mPNsRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_SCOPE, StringSerializer.get(), null); mPNsRepoCDs.add(rcd);
		}		
		return mPNsRepoCDs;
	}
}
