/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gerag
 *
 */
@JsonIgnoreProperties({"amsConnections","trafficPorts","protectedLinks"})
public class NetNode {

	public static final String ITEMS_DELIMITER = ":";
	public static final String LIST_ITEMS_DELIMITER = "::";

	static Logger log = LoggerFactory.getLogger(NetNode.class);	

	public enum SDNNodeMode {
		sdnenablednative, sdnenabledhybrid
	}

	public enum Status {
		INVALID,
		ACTIVE,
		REMOVED
	}

	/* NetNode types to conform with ODL Node types. Omitting PE=PCEP and PR=Production */
	public static final String OF = "OF";
	public static final String PK = "PK";

	/* NetNode Repo common columns */
	public static final String ID = "id";
	public static final String LABEL = "label";
	public static final String TYPE = "type";
	public static final String MGMT_IP_ADDR_STRING = "mgmt_ip_addr_string";
	public static final String MGMT_PORT = "mgmt_port";
	public static final String OF_MODE = "of_mode";
	public static final String HEALTH_CHECK_FREQUENCY = "health_check_frequency";
	public static final String STATUS = "status";
	public static final String AMS_CONNECTION_PREFIX = "ams_connection_";
	public static final String PORT_PREFIX = "port_";
	public static final String PORTS_LINK_PREFIX = "ports_link_";
	public static final String PROPS = "props";

	public static final int DEFAULT_HEALTH_CHECK_FREQUENCY = 10;

	protected static ArrayList<RepoCD> netNodeRepoCDs = null;

	public String label;	// Record key.
	public String id;		// For OpenFlow NetNodes this is the DPID
	public String type;		// Covers ODL Node types and beyond 
	public String mgmtAddr;
	public int 	  mgmtPort;
	public SDNNodeMode sdnNodeMode;
	public int 	  healthCheckFrequency; // When in-path in secs. When out of path - decrease frequency by X 10	
	public Status status;
	public Properties props;
	public Hashtable<String,AMSConnection> amsConnections;	public String amsConnectionsStr; 	
	public Hashtable<String,TrafficPort> trafficPorts; 		public String trafficPortsStr; 	
	public Hashtable<String,ProtectedLink> protectedLinks;	public String protectedLinksStr; 

	public String getAmsConnectionsStr() {return amsConnectionsStr;}

	public void setAmsConnectionsStr(String amsConnectionsStr) {
		this.amsConnectionsStr = amsConnectionsStr;
		this.amsConnections = fromAmsConnectionsStr(amsConnectionsStr); 		
	}

	public String getTrafficPortsStr() {return trafficPortsStr;}

	public void setTrafficPortsStr(String trafficPortsStr) {
		this.trafficPortsStr = trafficPortsStr;
		this.trafficPorts = fromTrafficPortsStr(trafficPortsStr); 
	}

	public String getProtectedLinksStr() {return protectedLinksStr;}

	public void setProtectedLinksStr(String protectedLinksStr) {
		this.protectedLinksStr = protectedLinksStr;
		this.protectedLinks = fromProtectedLinksStr(protectedLinksStr);
	}

	public static String generateAMSConnColName(String amsConnLabel) {return AMS_CONNECTION_PREFIX + amsConnLabel; }

	/* ### Description ###
	 * @param param_name 
	 */
	public NetNode() {

		label = null; id = null; type = null; mgmtAddr = null; mgmtPort = 0; sdnNodeMode = SDNNodeMode.sdnenablednative;
		healthCheckFrequency = DEFAULT_HEALTH_CHECK_FREQUENCY; status = Status.INVALID;
		amsConnectionsStr = null; amsConnections = new Hashtable<String,AMSConnection>();
		trafficPortsStr = null; trafficPorts = new Hashtable<String,TrafficPort>(); 
		protectedLinksStr = null; protectedLinks = new Hashtable<String,ProtectedLink>();
		props = new Properties();
	}

	/* ### Description ###
	 * @param param_name 
	 */
	public NetNode(String label, String id, String type, String mgmtAddr, int port, SDNNodeMode sdnNodeMode,
			int healthCheckFrequency) {
		this();
		this.label = (label == null || label.isEmpty()) ? "label_" + id : label; 
		this.id = id; this.type = type; this.mgmtAddr = mgmtAddr; this.mgmtPort = port;	
		this.sdnNodeMode = sdnNodeMode; this.healthCheckFrequency = healthCheckFrequency; 
	}

	/* ### Description ###
	 * @param param_name 
	 */
	public NetNode(String label, String id, String type, String mgmtAddr, int port, SDNNodeMode sdnNodeMode,
			int healthCheckFrequency, String amsConnectionsStr, String trafficPortsStr, String protectedLinksStr) {

		this(label, id, type, mgmtAddr, port, sdnNodeMode, healthCheckFrequency);
		if ( amsConnections != null && ! amsConnections.isEmpty())
			this.amsConnections = fromAmsConnectionsStr(amsConnectionsStr); 
		if ( trafficPorts != null && ! trafficPorts.isEmpty())
			this.trafficPorts = fromTrafficPortsStr(trafficPortsStr);
		if ( protectedLinks != null && ! protectedLinks.isEmpty())
			this.protectedLinks = fromProtectedLinksStr(protectedLinksStr);
	}

	public void toJacksonFriendly() {		
		setAmsConnectionsStr();
		setTrafficPortsStr();
		setProtectedLinksStr();
	}

	protected Hashtable<String, AMSConnection> fromAmsConnectionsStr(String amsConnectionsStr) {

		String[] split = amsConnectionsStr.split(LIST_ITEMS_DELIMITER);
		if(split == null) return null;

		Hashtable<String, AMSConnection> inflatedAmsConnections = new Hashtable<String, AMSConnection>();
		AMSConnection amsConnection;
		for(int i=0;i<split.length;i++) {
			try {
				amsConnection = new AMSConnection(split[i]);
				inflatedAmsConnections.put(amsConnection.label, amsConnection);
			} catch ( Throwable e) {continue;}
		}

		return inflatedAmsConnections;
	}

	protected void setAmsConnectionsStr() {

		if(amsConnections == null || amsConnections.isEmpty()) {
			amsConnectionsStr = "";
			return;
		}

		Iterator<Map.Entry<String,AMSConnection>> iter = amsConnections.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		AMSConnection amsConnection; String amsConnectionStr;

		while(iter.hasNext()) {
			amsConnection = iter.next().getValue();
			amsConnectionStr = amsConnection.toString();
			sb.append(amsConnectionStr); sb.append(LIST_ITEMS_DELIMITER);
		}
		sb.setLength(sb.length() - LIST_ITEMS_DELIMITER.length()); // Remove the last LIST_ITEMS_DELIMITER

		amsConnectionsStr = sb.toString();
	}

	protected Hashtable<String, TrafficPort> fromTrafficPortsStr(String trafficPortsStr) {

		String[] split = trafficPortsStr.split(LIST_ITEMS_DELIMITER);
		if(split == null) return null;

		Hashtable<String, TrafficPort> InflatedTrafficPorts = new Hashtable<String, TrafficPort>();
		TrafficPort trafficPort;
		for(int i=0;i<split.length;i++) {
			try {
				trafficPort = new TrafficPort(split[i]);
				InflatedTrafficPorts.put(trafficPort.label, trafficPort);
			} catch ( Throwable e) {continue;}
		}

		return InflatedTrafficPorts;
	}

	protected void setTrafficPortsStr() {

		if(trafficPorts == null || trafficPorts.isEmpty()) {
			trafficPortsStr = "";
			return;
		}

		Iterator<Map.Entry<String,TrafficPort>> iter = trafficPorts.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		TrafficPort trafficPort; String trafficPortStr;

		while(iter.hasNext()) {
			trafficPort = iter.next().getValue();
			trafficPortStr = trafficPort.toString();
			sb.append(trafficPortStr); sb.append(LIST_ITEMS_DELIMITER);
		}
		sb.setLength(sb.length() - LIST_ITEMS_DELIMITER.length()); // Remove the last LIST_ITEMS_DELIMITER

		trafficPortsStr = sb.toString();
	}

	protected Hashtable<String, ProtectedLink> fromProtectedLinksStr(String protectedLinksStr) {

		String[] split = protectedLinksStr.split(LIST_ITEMS_DELIMITER);
		if(split == null) return null;

		Hashtable<String, ProtectedLink> InflatedProtectedLinks = new Hashtable<String, ProtectedLink>();
		ProtectedLink protectedLink;
		for(int i=0;i<split.length;i++) {
			protectedLink = new ProtectedLink(split[i]);
			InflatedProtectedLinks.put(protectedLink.label, protectedLink);
		}

		return InflatedProtectedLinks;
	}

	protected void setProtectedLinksStr() {

		if(protectedLinks == null || protectedLinks.isEmpty()) {
			protectedLinksStr = "";
			return;
		}

		Iterator<Map.Entry<String,ProtectedLink>> iter = protectedLinks.entrySet().iterator();
		StringBuilder sb = new StringBuilder();
		ProtectedLink protectedLink; String protectedLinkStr;

		while(iter.hasNext()) {
			protectedLink = iter.next().getValue();
			protectedLinkStr = protectedLink.toString();
			sb.append(protectedLinkStr); sb.append(LIST_ITEMS_DELIMITER);
		}
		sb.setLength(sb.length() - LIST_ITEMS_DELIMITER.length()); // Remove the last LIST_ITEMS_DELIMITER

		protectedLinksStr = sb.toString();
	}

	public NetNode(Hashtable<String, Object> netNodeRow) throws ExceptionControlApp {

		this();
		label = (String) netNodeRow.get(LABEL);
		id = (String) netNodeRow.get(ID);
		type = (String) netNodeRow.get(TYPE);
		mgmtAddr = (String) netNodeRow.get(MGMT_IP_ADDR_STRING);
		mgmtPort = (Integer) netNodeRow.get(MGMT_PORT);
		sdnNodeMode = SDNNodeMode.valueOf((String) netNodeRow.get(OF_MODE));
		healthCheckFrequency = (Integer) netNodeRow.get(HEALTH_CHECK_FREQUENCY);
		status = Status.valueOf((String) netNodeRow.get(STATUS));
		props = (Properties) netNodeRow.get(PROPS);


		Iterator<Map.Entry<String,Object>> iter = netNodeRow.entrySet().iterator();
		Map.Entry<String,Object> entry; String columnName;
		AMSConnection amsConnection; TrafficPort port; ProtectedLink portsLink;
		while(iter.hasNext()) {
			entry = iter.next();
			columnName = (String) entry.getKey();
			if(columnName.startsWith(AMS_CONNECTION_PREFIX)) {
				try {
					amsConnection = new AMSConnection((String) entry.getValue());
					amsConnections.put(amsConnection.label, amsConnection);
				} catch (Throwable e) { 
					log.error("Failed to instantiate AMSConnection using " + entry.getValue().toString());
					throw new ExceptionControlApp("Failed to instantiate AMSConnection using "+entry.getValue().toString(),e);					
				}
			} else if(columnName.startsWith(PORT_PREFIX)) {
				try {
					port = new TrafficPort((String) entry.getValue());
					trafficPorts.put(port.label, port);
				} catch (Throwable e) {
					log.error("Failed to instantiate TrafficPort using " + entry.getValue().toString());
					throw new ExceptionControlApp("Failed to instantiate TrafficPort using "+entry.getValue().toString(),e);
				}				
			} else if(columnName.startsWith(PORTS_LINK_PREFIX)) {
				try {
					portsLink = new ProtectedLink((String) entry.getValue());
					protectedLinks.put(portsLink.label, portsLink);
				} catch (Throwable e) { 
					log.error("Failed to instantiate ProtectedLink using " + entry.getValue().toString());
					throw new ExceptionControlApp("Failed to instantiate ProtectedLink using "+entry.getValue().toString(),e);
				}				
			}
		}		
	}

	public Hashtable<String, Object> toRow() {

		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(label == null) label = "";
		if(id == null) id = "";
		if(props == null) props = new Properties();
		String mgmtAddrStr = mgmtAddr == null ? "" : mgmtAddr;

		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(LABEL, label);
		row.put(ID, id);
		row.put(TYPE, type);
		row.put(MGMT_IP_ADDR_STRING, mgmtAddrStr);
		row.put(MGMT_PORT, mgmtPort);
		row.put(OF_MODE, sdnNodeMode.name());
		row.put(HEALTH_CHECK_FREQUENCY, healthCheckFrequency);
		row.put(STATUS, status.name());
		row.put(PROPS, props);

		String columnName; String cellValue;

		Iterator<Map.Entry<String,AMSConnection>> amdConnectionIter = amsConnections.entrySet().iterator();
		Map.Entry<String,AMSConnection> amsConnectionEntry; 
		while(amdConnectionIter.hasNext()) {
			amsConnectionEntry = amdConnectionIter.next();
			columnName = generateAMSConnColName( amsConnectionEntry.getKey());
			cellValue = amsConnectionEntry.getValue().toString();
			row.put(columnName, cellValue);
		}

		Iterator<Map.Entry<String,TrafficPort>> portIter = trafficPorts.entrySet().iterator();
		Map.Entry<String,TrafficPort> portEntry;
		while(portIter.hasNext()) {
			portEntry = portIter.next();
			columnName = PORT_PREFIX + (String) portEntry.getKey();
			cellValue = portEntry.getValue().toString();
			row.put(columnName, cellValue);
		}

		Iterator<Map.Entry<String,ProtectedLink>> portsLinkIter = protectedLinks.entrySet().iterator();
		Map.Entry<String,ProtectedLink> portsLinkEntry;
		while(portsLinkIter.hasNext()) {
			portsLinkEntry = portsLinkIter.next();
			columnName = PORTS_LINK_PREFIX + (String) portsLinkEntry.getKey();
			cellValue = portsLinkEntry.getValue().toString();
			row.put(columnName, cellValue);
		}		

		return row;
	}

	public String getId() {return id;}
	public void setId(String id) {this.id = id;}

	public String getType() {return type;}
	public void setType(String type) {this.type = type;}

	public String getMgmtAddr() {return mgmtAddr;}
	public void setMgmtAddr(String mgmtAddr) {this.mgmtAddr = mgmtAddr;}

	public SDNNodeMode getOfType() {return sdnNodeMode;}
	public void setOfType(SDNNodeMode ofMode) {this.sdnNodeMode = ofMode;}

	public Hashtable<String, AMSConnection> getAmsConnections() {return amsConnections;}
	public void setAmsConnections(Hashtable<String, AMSConnection> connections) {this.amsConnections = connections;}

	public Hashtable<String, TrafficPort> getPorts() {return trafficPorts;}
	public void setPorts(Hashtable<String, TrafficPort> ports) {this.trafficPorts = ports;}

	public Hashtable<String, ProtectedLink> getPortsLinks() {return protectedLinks;}
	public void setPortsLinks(Hashtable<String, ProtectedLink> portsLinks) {this.protectedLinks = portsLinks;}

	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}

	public int getMgmtPort() {return mgmtPort;}
	public void setMgmtPort(int mgmtPort) {this.mgmtPort = mgmtPort;}

	public int getHealthCheckFrequency() {return healthCheckFrequency;}
	public void setHealthCheckFrequency(int healthCheckFrequency) {this.healthCheckFrequency = healthCheckFrequency;}

	public Status getStatus() {return status;}
	public void setStatus(Status status) {this.status = status;}

	public Properties getProps() {return props;}
	public void setProps(Properties props) {this.props = props;}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("NetNode [label="); sb.append(label); sb.append(", ");
		sb.append("id="); sb.append(id); sb.append(", ");
		sb.append("type="); sb.append(type); sb.append(", ");
		sb.append("mgmtAddr="); sb.append(mgmtAddr); sb.append(", ");
		sb.append("mgmtPort="); sb.append(mgmtPort); sb.append(", ");
		sb.append("sdnNodeMode="); sb.append(sdnNodeMode); sb.append(", ");
		sb.append("healthCheckFrequency="); sb.append(healthCheckFrequency); sb.append(", ");
		Iterator<Map.Entry<String,AMSConnection>> iter1 = amsConnections.entrySet().iterator();
		while(iter1.hasNext()) {
			sb.append("amsConnection="); sb.append(iter1.next().getValue().toString()); sb.append(", ");
		}
		Iterator<Map.Entry<String,TrafficPort>> iter2 = trafficPorts.entrySet().iterator();
		while(iter2.hasNext()) {
			sb.append("trafficPort="); sb.append(iter2.next().getValue().toString()); sb.append(", ");
		}
		Iterator<Map.Entry<String,ProtectedLink>> iter3 = protectedLinks.entrySet().iterator();
		while(iter3.hasNext()) {
			sb.append("protectedLink="); sb.append(iter3.next().getValue().toString()); sb.append(", ");
		}
		if ( status == Status.REMOVED ) {
			sb.append("status="); sb.append(status); sb.append(", ");
		}
		sb.append("props="); sb.append(props.toString());
		sb.append("]");
		return sb.toString();
	}

	public static List<RepoCD> getNetNodeRCDs() {

		if(netNodeRepoCDs == null) {
			RepoCD rcd;
			netNodeRepoCDs = new ArrayList<RepoCD>();
			rcd = new RepoCD(LABEL, StringSerializer.get(), null);	 netNodeRepoCDs.add(rcd);	
			rcd = new RepoCD(ID, StringSerializer.get(), null);	 netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(TYPE, StringSerializer.get(), null);	 netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(MGMT_IP_ADDR_STRING, StringSerializer.get(), null);	netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(MGMT_PORT, IntegerSerializer.get(), null);	netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(OF_MODE, StringSerializer.get(), null);	 netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(STATUS, StringSerializer.get(), null);	 netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(HEALTH_CHECK_FREQUENCY, IntegerSerializer.get(), null); netNodeRepoCDs.add(rcd);
			rcd = new RepoCD(PROPS, PropertiesSerializer.get(), null); netNodeRepoCDs.add(rcd);
		}		
		return netNodeRepoCDs;
	}

	public void validate() throws Exception {

		if(label == null || label.isEmpty()) throw new Exception("Invalid netnode label.");

		Iterator<Map.Entry<String,AMSConnection>> iter1 = amsConnections.entrySet().iterator();
		while(iter1.hasNext()) {
			iter1.next().getValue().validate();
		}

		Iterator<Map.Entry<String,TrafficPort>> iter2 = trafficPorts.entrySet().iterator();
		while(iter2.hasNext()) {
			iter2.next().getValue().validate();
		}

		Iterator<Map.Entry<String,ProtectedLink>> iter3 = protectedLinks.entrySet().iterator();
		while(iter3.hasNext()) {
			iter3.next().getValue().validate();
		}
	}

	public static boolean isRemoved(String netNodeLabel) {

		try {
			String netNodeStatusStr = (String) DFHolder.get().netNodesRepo.getCellValue(netNodeLabel, NetNode.STATUS);
			Status netNodeStatus = Status.valueOf(netNodeStatusStr);
			if(netNodeStatus == Status.REMOVED) return true;
			return false;
		} catch (Throwable e) {return false;}
	}

	public static boolean isRemoved(Hashtable<String,Object> netNodeRow) {

		try {
			String netNodeStatusStr = (String) netNodeRow.get(STATUS);
			Status netNodeStatus = Status.valueOf(netNodeStatusStr);
			if(netNodeStatus == Status.REMOVED) return true;
			return false;
		} catch (Throwable e) {return false;}
	}
	
	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public static NetNode getNetNode(String netNodeKey) {
		
		try {
			Hashtable<String, Object> netNodeRow = DFHolder.get().netNodesRepo.getRow(netNodeKey); 
			if(netNodeRow == null) { 
				log.error("Got null netNodeRow for key " + netNodeKey);
				return null;
			}
			NetNode netNode = new NetNode(netNodeRow); 
			return netNode;
		} catch (Throwable e) {
			log.error("Failed to getNetNode : "+netNodeKey, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return null;
		}
	}
}
