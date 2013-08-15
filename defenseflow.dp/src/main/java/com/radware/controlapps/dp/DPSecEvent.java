/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * Security eventy queue message is used to haul messages which were originated in DefensePro and trapped by syslog listener.
 *
 * @author Kobi Samoray
 * @author Gera Goft
 * @version 0.1
 */
package com.radware.controlapps.dp;

import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opendaylight.controlapps.defense4all.core.ProtocolPort.DFProtocol;


/** 
 * Security eventy queue message is used to haul messages which were originated in DefensePro and trapped by syslog listener.
 *
 * @author kobis
 * @author gerag
 */
public class DPSecEvent extends DPEvent {
	
	public enum DPProtocol {
		TCP,
		UDP,
		ICMP,
		IP;
		
		public static DPProtocol fromDFProtocol(DFProtocol dfProtocol) {
			return valueOf(dfProtocol.name());	// The enum values are as of now identical.
		}
		
		public DFProtocol toDFProtocol() {
			return DFProtocol.valueOf(this.name()); // The enum values are as of now identical.
		}
	}

	/* syslog DP security message examples:
	 * 01-04-2012 19:49:25 WARNING 300000 Intrusions "BO-WINXP" TCP 192.168.8.105 1607 192.168.8.100 80 3 Regular "DMZ-Policy" occur 1 0 N/A 0 N/A low drop FFFFFFFF-FFFF-FFFF-0001-00004F7B1BE5
	 * 17-06-2013 11:39:40 WARNING 73 Behavioral-DoS "network flood IPv4 TCP-SYN" TCP 0.0.0.0 0 0.0.0.0 0 0 Regular "Server1" start 0 0 N/A 0 N/A high drop FFFFFFFF-FFFF-FFFF-0057-000051A606B5
	 * "(\\d+-\\d+-\\d+)\\s+(\\d+:\\d+:\\d+)\\s+(\\w+)\\s+(\\d+)\\s+(.+?)\\s+\"(.+?)\"\\s+(\\w+)\\s+(.+?)\\s+(\\d+?)\\s+(.+?)\\s+(\\d+)\\s+(\\d+)\\s+(\\w+)\\s+\"(.+?)\"\\s+(\\w+)\\s+(\\d+)\\s+(\\d+)\\s+(.+?)\\s+(\\d+)\\s+(.+?)\\s+(\\w+)\\s+(\\w+)";
	 */
	public static String DATE = "(\\d+-\\d+-\\d+)"; 	// 01-04-2012 		- regex group 1
	public static String TIME = "(\\d+:\\d+:\\d+)"; 	// 19:49:25   		- regex group 2
	public static String SEVERITY = "(\\w+)";			// WARNING    		- regex group 3
	public static String RDWR_EVT_TYPE_ID = "(\\d+)"; 	// 300000`			- regex group 4
	public static String CATEGORY = "(.+?)";			// Intrusions 		- regex group 5
	public static String ATTACK_NAME = "\"(.+?)\"";		// "BO-WINXP" 		- regex group 6
	public static String PROTOCOL = "(\\w+)";			// TCP 		  		- regex group 7
	public static String SRC_ADDR = "(.+?)";			// 192.168.8.105	- regex group 8
	public static String SRC_PORT = "(\\d+?)";			// 1607				- regex group 9
	public static String DST_ADDR = "(.+?)";			// 192.168.8.100	- regex group 10
	public static String DST_PORT = "(\\d+?)";			// 80				- regex group 11
	public static String DP_PORT = "(\\d+)";			// 3				- regex group 12
	public static String CONTEXT = "(\\w+)";			// Regular			- regex group 13
	public static String POLICY_NAME = "\"(.+?)\"";		// "DMZ-Policy"		- regex group 14
	public static String ATTACK_STATUS = "(\\w+)";  	// occur			- regex group 15
	public static String PACKET_COUNT = "(\\d+)";   	// 1				- regex group 16
	public static String BANDWIDTH = "(\\d+)"; 	    	// 0				- regex group 17
	public static String VLAN = "(.+?)";	    		// N/A				- regex group 18
	public static String MPLS_RD = "(\\d+)";	   		// 0				- regex group 19
	public static String MPLS_TAG = "(.+?)";	   		// N/A				- regex group 20
	public static String RISK = "(\\w+)";   			// low				- regex group 21
	public static String ACTION = "(\\w+)";   			// drop				- regex group 22
	public static String EVENT_GUID = "(\\.*+)"; 		// FFFFFFFF-FFFF-FFFF-0001-00004F7B1BE5	- regex group 23
	
	public static String DP_SECEVT_REGEX = DATE + "\\s+" + TIME + "\\s+" + SEVERITY + "\\s+" + RDWR_EVT_TYPE_ID + "\\s+"
		+ CATEGORY + "\\s+" + ATTACK_NAME + "\\s+" + PROTOCOL + "\\s+" + SRC_ADDR + "\\s+" + SRC_PORT + "\\s+"
		+ DST_ADDR + "\\s+" + DST_PORT + "\\s+" + DP_PORT + "\\s+" + CONTEXT + "\\s+" + POLICY_NAME + "\\s+"
		+ ATTACK_STATUS + "\\s+" + PACKET_COUNT + "\\s+" + BANDWIDTH + "\\s+" + VLAN + "\\s+" + MPLS_RD + "\\s+"
		+ MPLS_TAG + "\\s+" + RISK + "\\s+"	+ ACTION + "\\s+" + EVENT_GUID;

	private static Pattern dpSecEventRegex = null;
	
	/* Diversion execution properties. Correspond to field description below */
	public static final String SEVERITY_PROPERTY = "severity";
	public static final String RADWARE_EVENT_TYPE_ID_PROPERTY = "radware_event_type_id";
	public static final String CATEGORY_PROPERTY = "category";
	public static final String ATTACK_NAME_PROPERTY = "attack_name";
	public static final String CONTEXT_PROPERTY = "context";
	public static final String POLICY_NAME_PROPERTY = "policy_name";
	public static final String ATTACK_STATUS_PROPERTY = "attack_status";
	public static final String PACKET_COUNT_PROPERTY = "packet_count";
	public static final String ATTACK_BANDWIDTH_PROPERTY = "attack_bandwidth";
	public static final String RISK_PROPERTY = "risk";
	public static final String ACTION_PROPERTY = "action";
	public static final String EVENT_GUID_PROPERTY = "event_guid";
	
	public Date dateTime;
	public long timeInSecs;
	public String severity;		// Assigned by DP: DEBUG, INFO, WARNING, ERROR, FATAL
	public int rdwrEventTypeId;	// Radware ID number, identifying the type of the security event
	public String category;		// Anomalies, Anti Scanning, HTTP Flood, SYN Flood, Behavioral DoS, Server Cracking, Intrusions, ACL, DoS Shield
	public String attackName;	// Radware defined attack name
	public String proto;		// Protocol - TCP/UDP/ICMP/IP
	public DPProtocol dpProtocol;
	public InetAddress srcAddress;	
	public int srcPort;				
	public InetAddress dstAddress;	
	public int dstPort;				
	public int attackDPPort;	// On which attack has been detected
	public String context;		// Nature of the attack that matched: Regular or SSL
	public String policyName;	// Name of the internal policy that matched the attack
	public String attackStatus;	// ‘per packet’ or continuous security (SYN or HTTP Protection...) events
								// ‘per packet’ - occur (e.g. IPS signatures, Black list, Packet anomalies)
								// continuous security - start/term, ongoing (sent every 15 secs, in between start-term),
								// sampled (provide layer 4 details of a specific packet detected during the event).
	public long packetCount;	// Number of packets detected per reported event
	public long attackBandwidth;// Total bandwidth used by the attack – in kbps, rounded down
	public int vlan;			// 0 indicates that the VLAN Tag information is not available
	public int mplsRd;			// can be N/A
	public String mplsTag;		// 
	public String risk;			// Info, Low, Medium, High
	public String action;		// forward – packet was forwarded to its destination; drop – discarded packet.
								// source-reset – sent TCP Reset to source.
								// dest-reset – sent TCP Reset to destination.
								// source-dest-reset – sent TCP Reset to both source and destination.
								// proxy – forwarded packet using transparent proxy mechanism.
								// challenge – responded with SYN Safe-Reset or Web-Authentication challenge.
								// quarantine – added source IP to quarantine table.
								// drop-and-quarantine – dropped packet and replied with the defined quarantine action.
	public String eventGuid;	//
	
	public DPSecEvent(Matcher matcher) throws Exception {

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
		dateTime = formatter.parse(matcher.group(1) + " " + matcher.group(2));
		severity  = matcher.group(3);
		timeInSecs = dateTime.getTime()/1000;
		rdwrEventTypeId = Integer.parseInt(matcher.group(4));
		category = matcher.group(5);
		attackName = matcher.group(6);
		proto = matcher.group(7);
		dpProtocol = DPProtocol.valueOf(proto);
		srcAddress = InetAddress.getByName(matcher.group(8));
		srcPort = Integer.parseInt(matcher.group(9));
		dstAddress = InetAddress.getByName(matcher.group(10));
		dstPort = Integer.parseInt(matcher.group(11));
		attackDPPort = Integer.parseInt(matcher.group(12));
		context = matcher.group(13);
		policyName = matcher.group(14);
		attackStatus = matcher.group(15);
		packetCount = Long.parseLong(matcher.group(16));
		attackBandwidth = Long.parseLong(matcher.group(17));
		if(matcher.group(18).equals("N/A"))
			vlan = 0;
		else 
			vlan = Integer.parseInt(matcher.group(18));
		mplsRd = Integer.parseInt(matcher.group(19));
		mplsTag = matcher.group(20);
		risk = matcher.group(21);
		action = matcher.group(22);
		eventGuid = matcher.group(23);
	}
	
	@Override
	public String toString() {
		String s = "DPSecurityEvent [datetime=" + dateTime + ", severity=" + severity + ", rdwrEventTypeId=" + rdwrEventTypeId
				 + ", category=" + category	+ ", attackName=" + attackName + ", proto=" + proto + ", srcAddress=" + srcAddress
				 + ", srcPort=" + srcPort + ", dstAddress=" + dstAddress + ", dstPort=" + dstPort
				 + ", attackDPPort=" + attackDPPort + ", context=" + context + ", policyName=" + policyName
				 + ", attackStatus=" + attackStatus + ", packetCount=" + packetCount + ", attackBandwidth=" + attackBandwidth
				 + ", vlan=" + vlan + ", mplsRd=" + mplsRd + ", mplsTag=" + mplsTag + ", risk=" + risk + ", action=" + action
				 + ", eventGuid=" + eventGuid + "]";
		return s;
	}

	public static DPSecEvent fromString(String s) {

		if(dpSecEventRegex == null) // First time usage
			dpSecEventRegex = Pattern.compile(DP_SECEVT_REGEX);

		Matcher matcher = dpSecEventRegex.matcher(s);
		if(!matcher.find()) return null;

		try {
			DPSecEvent event = new DPSecEvent(matcher);
			return event;
		} catch (Exception e) {return null;}
	}
}
