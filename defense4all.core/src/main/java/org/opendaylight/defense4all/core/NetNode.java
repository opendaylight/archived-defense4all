/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.core;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.RepoCD;

/**
 * @author gerag
 *
 */
public class NetNode {
	
	public class AMSConnection {
		
		public String amsLabel;
		public int toAMSInboundPort; 	// port in the node - closer to client
		public int toAMSOutboundPort;	// port in the node - closer to server
		public int inboundAMSPort; 		// port in the AMS device - closer to client
		public int outboundAMSPort;		// port in the AMS device - closer to server
		
		public AMSConnection() {
			this.amsLabel = null; this.toAMSInboundPort = 0; this.toAMSOutboundPort = 0;
			this.inboundAMSPort = 0; this.outboundAMSPort = 0;
		}
		
		public AMSConnection(String amsLabel, int toAMSPort, int fromAMSPort, int inboundAMSPort, int outboundAMSPort) {
			this.amsLabel = amsLabel; this.toAMSInboundPort = toAMSPort; this.toAMSOutboundPort = fromAMSPort;
			this.inboundAMSPort = inboundAMSPort; this.outboundAMSPort = outboundAMSPort;
		}
		
		public AMSConnection(String s) throws ExceptionControlApp {
			
			String[] split = s.split(":");
			if(split.length < 3) throw new ExceptionControlApp("Improper serialized AMS connection");
			amsLabel = split[0];
			try {
				toAMSInboundPort = Integer.valueOf(split[1]);
				toAMSOutboundPort = Integer.valueOf(split[2]);
				inboundAMSPort = Integer.valueOf(split[3]);
				outboundAMSPort = Integer.valueOf(split[4]);
			} catch (NumberFormatException e) {
				throw new ExceptionControlApp("Improper serialized AMS connection", e);
			}
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(amsLabel);  
			sb.append(":"); sb.append(toAMSInboundPort); sb.append(":"); sb.append(toAMSOutboundPort);
			sb.append(":"); sb.append(inboundAMSPort); sb.append(":"); sb.append(outboundAMSPort);
			return sb.toString();
		}
	}
	
	public enum PortDirection {
		INBOUND, // Closer to client
		OUTBOUND // Closer to server
	}
	
	public enum SDNNodeMode {
		SDN_ENABLED_NATIVE,	SDN_ENABLED_HYBRID
	}
	
	public class TrafficPort {	
		
		public String label;
		public short number;
		public int vlan;
		public PortDirection direction;
		
		public TrafficPort() {this.label = null; this.number = 0; this.vlan = 0; direction = PortDirection.INBOUND;}
		
		public TrafficPort(String label, short number, int vlan, PortDirection direction) {
			this.label = label; this.number = number; this.vlan = vlan; this.direction = direction;
		}
		
		public TrafficPort(String s) throws ExceptionControlApp {
			
			String[] split = s.split(":");
			if(split.length < 4) throw new ExceptionControlApp("Improper serialized Port");
			label = split[0];
			try {
				number = Short.valueOf(split[1]);
				vlan = Integer.valueOf(split[2]);
				direction = PortDirection.valueOf(split[3]);
			} catch (Exception e) {throw new ExceptionControlApp("Improper serialized Port", e);}
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(label); sb.append(":"); sb.append(number); sb.append(":"); 
			sb.append(vlan); sb.append(":"); sb.append(direction); 
			return sb.toString();
		}
	}
	
	public class ProtectedLink {
		
		public String label;
		public short inboundPort;
		public int outboundPort;
		
		public ProtectedLink() {this.label = null; this.inboundPort = 0; this.outboundPort = 0;}
		
		public ProtectedLink(String label, short inboundPort, int fromAMSPort) {
			this.label = label; this.inboundPort = inboundPort; this.outboundPort = fromAMSPort;
		}
		
		public ProtectedLink(String s) throws ExceptionControlApp {
			
			String[] split = s.split(":");
			if(split.length < 3) throw new ExceptionControlApp("Improper serialized PortsLink");
			label = split[0];
			try {
				inboundPort = Short.valueOf(split[1]);
				outboundPort = Integer.valueOf(split[2]);
			} catch (NumberFormatException e) {
				throw new ExceptionControlApp("Improper serialized PortsLink", e);
			}
		}
		
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(label); sb.append(":"); sb.append(inboundPort); sb.append(":"); sb.append(outboundPort);
			return sb.toString();
		}
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
	public Hashtable<String,AMSConnection> amsConnections;
	public Hashtable<String,TrafficPort> trafficPorts;
	public Hashtable<String,ProtectedLink> protectedLinks;
	public Properties props;
	
	/* ### Description ###
	 * @param param_name 
	 */
	public NetNode() {
		
		label = null; id = null; type = null; mgmtAddr = null; mgmtPort = 0; sdnNodeMode = SDNNodeMode.SDN_ENABLED_HYBRID;
		healthCheckFrequency = DEFAULT_HEALTH_CHECK_FREQUENCY; status = Status.INVALID;
		amsConnections = new Hashtable<String,AMSConnection>();
		trafficPorts = new Hashtable<String,TrafficPort>(); protectedLinks = new Hashtable<String,ProtectedLink>();
		props = new Properties();
	}
	
	/* ### Description ###
	 * @param param_name 
	 */
	public NetNode(String label, String id, String type, String mgmtAddr, int port, SDNNodeMode sdnNodeMode,
			int healthCheckFrequency) throws UnknownHostException {
		this();
		this.label = (label == null || label.isEmpty()) ? "label_" + id : label; 
		this.id = id; this.type = type; this.mgmtAddr = mgmtAddr; this.mgmtPort = port;	
		this.sdnNodeMode = sdnNodeMode; this.healthCheckFrequency = healthCheckFrequency; 
	}
	
	public NetNode(Hashtable<String, Object> netNodeRow) throws UnknownHostException {
		
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
					amsConnections.put(amsConnection.amsLabel, amsConnection);
				} catch (ExceptionControlApp e) { /* TODO: log and ignore */}
			} else if(columnName.startsWith(PORT_PREFIX)) {
				try {
					port = new TrafficPort((String) entry.getValue());
					trafficPorts.put(port.label, port);
				} catch (ExceptionControlApp e) { /* TODO: log and ignore */}				
			} else if(columnName.startsWith(PORTS_LINK_PREFIX)) {
				try {
					portsLink = new ProtectedLink((String) entry.getValue());
					protectedLinks.put(portsLink.label, portsLink);
				} catch (ExceptionControlApp e) { /* TODO: log and ignore */}				
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
			columnName = AMS_CONNECTION_PREFIX + (String) amsConnectionEntry.getKey();
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
}
