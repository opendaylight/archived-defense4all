/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Pozdeev Konstantin
 * @version 0.1
 */

package org.opendaylight.defense4all.core;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DvsnInfo {

	static Logger log = LoggerFactory.getLogger(DvsnInfo.class);

	/* AMS Repo common columns */
	public static final String KEY = "key";
	public static final String NETNODE_LABEL = "netnode_label";
	public static final String TRAFFIC_FLOOR_KEY = "traffic_floor_key";
	public static final String MITIGATION_KEY = "mitigation_key";
	public static final String CONFIG_PROPS = "config_props";
	public static final String AMS_DIVERSION_INFO_PREFIX = "ams_diversion_info_";
	public static final String TRAFFIC_DIVERTED = "traffic_diverted";

	/* Config props common fields. */
	public static final String INBOUND_BANDWIDTH = "inbound_bandwidth";

	public static class AMSDvsnInfo {

		public String label;
		public Properties amsDvsnProps; 	// Information related to the act of traffic diversion to this AMS

		public AMSDvsnInfo() {label = null; amsDvsnProps = null;}

		public AMSDvsnInfo(String amsLabel, Properties configProps, Properties dvsnProps) {

			this.label = amsLabel;
			if(dvsnProps == null)
				this.amsDvsnProps = null;
			else {
				this.amsDvsnProps = new Properties();
				this.amsDvsnProps.putAll(dvsnProps);
			}
		}

		public AMSDvsnInfo(String amsDvsnInfoStr) throws IllegalArgumentException {

			if(amsDvsnInfoStr == null) throw new IllegalArgumentException("Null amsDvsnInfoStr param.");
			String[] split = null;
			try {
				split = amsDvsnInfoStr.split(":::");
				if(split.length < 2) throw new IllegalArgumentException("missing spit parts.");
				label = split[0];
				amsDvsnProps = split[1] == null ? null : PropertiesSerializer.get().fromString(split[1]);
			} catch (Throwable e) {
				log.error("Invalid format of amsDvsnInfoStr", e);
				throw new IllegalArgumentException("Invalid format of amsDvsnInfoStr", e);
			}
		}

		public AMSDvsnInfo(AMSDvsnInfo other) {
			this.label = other.label; 
			this.amsDvsnProps = new Properties(); this.amsDvsnProps.putAll(other.amsDvsnProps);
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();

			String s;
			sb.append(label); sb.append(":::");
			s = PropertiesSerializer.get().toString(amsDvsnProps);
			sb.append(s);

			return sb.toString();
		}
	}	

	public String key;
	public String netNodeLabel;		// netNode from which to divert traffic to the selected set of AMSs
	public String tFloorKey;		// Traffic floor containing the details of this diversion.
	public String mitigationKey;	// Relation to the mitigation for which diversion is triggered
	public Properties configProps;  // NetNode level configuration params. Different from dvsnProps per AMS
	public boolean trafficDiverted;	// Indicator whether the traffic has been diverted
	public List<AMSDvsnInfo> amsDvsnInfos; // Diversion and configuration information of each AMS used for this diversion 

	protected static ArrayList<RepoCD> repoCDs = null;

	public static String generateDvsnInfoKey(String netNodeLabel, String mitigationKey) {
		StringBuilder sb = new StringBuilder();
		sb.append(netNodeLabel); sb.append("_"); sb.append(mitigationKey);
		return sb.toString();
	}
	
	public DvsnInfo(String key ) {
		// placeholder for return incomplete object
		this.key = key;
	}
	

	public DvsnInfo(String netNodeLabel, String tFloorKey, String mitigationKey, List<AMSDvsnInfo> amsDvsnInfos, 
			Properties configProps) {
		this.key = generateDvsnInfoKey(netNodeLabel, mitigationKey);
		this.netNodeLabel = netNodeLabel; this.tFloorKey = tFloorKey; this.mitigationKey = mitigationKey;
		this.amsDvsnInfos = amsDvsnInfos; 
		this.configProps = new Properties();
		if(configProps != null)
			this.configProps.putAll(configProps);
		trafficDiverted = false;
	}

	public DvsnInfo(DvsnInfo other) {
		this.key = other.key; this.netNodeLabel = other.netNodeLabel; this.tFloorKey = other.tFloorKey; 
		this.mitigationKey = other.mitigationKey;
		this.amsDvsnInfos = other.amsDvsnInfos;
		this.trafficDiverted = other.trafficDiverted;
		if(other.configProps == null)
			this.configProps = null;
		else {
			this.configProps = new Properties();
			this.configProps.putAll(other.configProps);
		}
	}

	public DvsnInfo(Hashtable<String, Object> row) throws IllegalArgumentException {

		try {

			key = (String) row.get(KEY);
			netNodeLabel = (String) row.get(NETNODE_LABEL);
			tFloorKey = (String) row.get(TRAFFIC_FLOOR_KEY);
			mitigationKey = (String) row.get(MITIGATION_KEY);
			amsDvsnInfos = new ArrayList<AMSDvsnInfo>();
			configProps = (Properties) row.get(CONFIG_PROPS);
			trafficDiverted = (Boolean) row.get(TRAFFIC_DIVERTED);

			Iterator<Map.Entry<String,Object>> iter = row.entrySet().iterator();
			Map.Entry<String,Object> entry; String amsDvsnInfoStr; AMSDvsnInfo amsDvsnInfo;
			while(iter.hasNext()) {
				entry = iter.next();
				if(!entry.getKey().startsWith(DvsnInfo.AMS_DIVERSION_INFO_PREFIX)) continue;
				amsDvsnInfoStr = (String)entry.getValue();
				if(amsDvsnInfoStr == null) {
					log.error("AMS diversion info cell " + entry.getKey() + " in NetNodeDiversionInfo " + amsDvsnInfoStr + " has null value.");
					FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;					
				}
				amsDvsnInfo = new AMSDvsnInfo(amsDvsnInfoStr);
				amsDvsnInfos.add(amsDvsnInfo);
			}
			if(amsDvsnInfos.isEmpty()) amsDvsnInfos = null;
		} catch (Throwable e) {
			log.error("Failed to inflate NetNodeDvsnInfo from row " + key + ".", e);
			throw new IllegalArgumentException("Failed to inflate NetNodeDvsnInfo from row " + key + ".", e);
		}
	}

	public Hashtable<String, Object> toRow() {

		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(netNodeLabel == null) netNodeLabel = "";
		if(tFloorKey == null) tFloorKey = "";
		if(mitigationKey == null ) mitigationKey = "";
				
		if(configProps == null ) configProps = new Properties();		

		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(KEY, key);
		row.put(NETNODE_LABEL, netNodeLabel);
		row.put(TRAFFIC_FLOOR_KEY, tFloorKey);
		row.put(MITIGATION_KEY, mitigationKey);
		row.put(CONFIG_PROPS, configProps);
		row.put(TRAFFIC_DIVERTED, trafficDiverted);
		if(amsDvsnInfos != null) {
			for(AMSDvsnInfo amsDvsnInfo : amsDvsnInfos) {
				String amsDvsnInfoStr = amsDvsnInfo.toString();
				//row.put(AMS_DIVERSION_INFO_PREFIX+amsDvsnInfo.label, amsDvsnInfo.toString());
				row.put(AMS_DIVERSION_INFO_PREFIX+amsDvsnInfo.label, amsDvsnInfoStr);
			}			
		}
		return row;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Diversion Info [netNodeLabel="); sb.append(netNodeLabel);
		sb.append(", mitigationKey="); sb.append(mitigationKey);
		sb.append(", configProperties="); sb.append(configProps.toString());
		for(AMSDvsnInfo amsDvsnInfo : amsDvsnInfos) {
			sb.append(", ams diversion info ["); sb.append(amsDvsnInfo.toString()); sb.append("]");
		}
		return sb.toString();
	}

	public static List<RepoCD> getRCDs() {

		if(repoCDs == null) {
			RepoCD rcd;
			repoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null); repoCDs.add(rcd);
			rcd = new RepoCD(NETNODE_LABEL, StringSerializer.get(), null);	 repoCDs.add(rcd);
			rcd = new RepoCD(TRAFFIC_FLOOR_KEY, StringSerializer.get(), null);	 repoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_KEY, StringSerializer.get(), null); repoCDs.add(rcd);
			rcd = new RepoCD(CONFIG_PROPS, PropertiesSerializer.get(), null); repoCDs.add(rcd);
			rcd = new RepoCD(TRAFFIC_DIVERTED, BooleanSerializer.get(), null); repoCDs.add(rcd);
		}		
		return repoCDs;
	}

	public static DvsnInfo getDvsnInfo(String dvsnInfoKey) {

		try {
			Hashtable<String, Object> dvsnInfoRow = DFHolder.get().dvsnInfosRepo.getRow(dvsnInfoKey); 
			if(dvsnInfoRow == null) { 
				log.error("Got null dvsnInfoRow for key " + dvsnInfoKey);
				return null;
			}
			DvsnInfo dvsnInfo = new DvsnInfo(dvsnInfoRow); 
			return dvsnInfo;
		} catch (Throwable e) {
			log.error("Failed to get DvsInfo : "+dvsnInfoKey, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return null;
		}
	}

	public static DvsnInfo getDvsnInfo(String netNodeLabel, String amsLabel) {

		try {
			Hashtable<String,Hashtable<String, Object>> dvsnInfoTable = DFHolder.get().dvsnInfosRepo.getTable();
			Iterator<Map.Entry<String, Hashtable<String, Object>>> dvsnInfoIter = dvsnInfoTable.entrySet().iterator();
			Hashtable<String, Object> dvsnInfoRow; DvsnInfo dvsnInfo = null; String currentNetNodeLabel;

			while(dvsnInfoIter.hasNext()) {
				dvsnInfoRow = dvsnInfoIter.next().getValue();
				if(dvsnInfoRow == null) continue;
				currentNetNodeLabel = (String) dvsnInfoRow.get(DvsnInfo.NETNODE_LABEL);
				if(currentNetNodeLabel.equals(netNodeLabel)) {
					dvsnInfo = new DvsnInfo(dvsnInfoRow);
					break;
				}				
			}			
			return dvsnInfo;			
		} catch (Throwable e) {
			String msg = "Failed to retrieve AMS diversion information " + amsLabel;
			log.error(msg, e);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, msg);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return null;
		}		
	}

	public boolean containsDvsnToAMSs(List<String> failedAMSs) {
		
		for(AMSDvsnInfo amsDvsnInfo : amsDvsnInfos) {
			if(failedAMSs.contains(amsDvsnInfo.label))
				return true;
		}
		return false;
	}
}
