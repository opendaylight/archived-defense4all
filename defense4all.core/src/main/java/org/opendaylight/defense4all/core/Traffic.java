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
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.mina.filter.firewall.Subnet;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Traffic {

	static Logger log = LoggerFactory.getLogger(Traffic.class);

	public enum TrafficMatch {
		MATCH,
		CONTAIN,
		NO
	}

	public enum TrafficDirection {
		INVALID, 
		INBOUND, // Client to server
		OUTBOUND // Server to client
	}

	public int vlan;
	public InetAddress dstAddr;
	public int dstAddrPrefixLen;

	/* Entry key = DPProtocol.name(). value = list of ports.
	 * Empty hashtable means all protocols. Empty port list means all ports (for that protocol). */
	public Hashtable<String,ArrayList<Integer>> protoPorts;

	/* Local hashtable for hostname lookups */
	public static class NameHash {

		private static Hashtable<InetAddress,String> nameHash = null;
		private static Hashtable<String,InetAddress> addrHash = null;
		
		private static void initIfNeeded() {
			if ( nameHash == null ) {
				nameHash = new Hashtable<InetAddress,String>();
			}
			if ( addrHash == null ) {
				addrHash = new Hashtable<String,InetAddress>();
			}
		}

		public static String getHostName(InetAddress addr) {
			initIfNeeded();
			
			if ( nameHash.containsKey(addr)) {
				return  nameHash.get(addr) ; 
			} else {
				String name = addr.getHostName();
				nameHash.put(addr, name);
				addrHash.put(name, addr);
				return name;
			}
		}
		
		public static InetAddress getHostAddr(String name) {
			initIfNeeded();
			
			if ( addrHash.containsKey(name)) {
				return  addrHash.get(name) ; 
			} else {
				InetAddress addr=null;
				try {
					addr = InetAddress.getByName(name);
					if (addr == null) 
						addr = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {};
				
				nameHash.put(addr, name);
				addrHash.put(name, addr);
				return addr;
			}
		}
		

		public static void reset() {
			if (nameHash != null )
				nameHash.clear();
			if (addrHash != null )
				addrHash.clear();
		}
	}

	//protected static Hashtable<dstAddr,String> nameHash;

	public Traffic() {vlan = 0; dstAddr = null; dstAddrPrefixLen = 0; protoPorts = new Hashtable<String,ArrayList<Integer>>();}

	public Traffic(int vlan, InetAddress dstAddr, int dstAddrPrefixLen) {
		this();
		this.vlan = vlan; this.dstAddr = dstAddr; this.dstAddrPrefixLen = dstAddrPrefixLen;
	}

	public Traffic(Traffic other) {
		this();
		this.vlan = other.vlan; this.dstAddr = other.dstAddr; this.dstAddrPrefixLen = other.dstAddrPrefixLen; 
		this.protoPorts = other.protoPorts;
	}

	public void addProtocolPort(DFProtocol dfProtocol, int port) {

		ArrayList<Integer> ports = protoPorts.get(dfProtocol.name());
		if(ports == null) { // No such protocol
			ports = new ArrayList<Integer>();
			protoPorts.put(dfProtocol.name(), ports);
		}
		if(port != 0) ports.add(port);
	}	

	public void setProtocolPort(DFProtocol dfProtocol, int port) {

		protoPorts.clear();
		ArrayList<Integer> ports = new ArrayList<Integer>();
		protoPorts.put(dfProtocol.name(), ports);
		if(port != 0) ports.add(port);
	}

	public TrafficMatch match(int vlan, InetAddress dstAddr, DFProtocol dfProtocol, int dstPort) throws ExceptionControlApp {

		if(this.vlan != vlan) return TrafficMatch.NO;
		if(this.dstAddr == null || dstAddr == null) return TrafficMatch.NO;	

		TrafficMatch dstAddrMatch;
		TrafficMatch protoMatch;
		TrafficMatch portMatch;

		/* Check dstAddr match or containment. If neither return immediately with TrafficMatch.NO */
		if(this.dstAddr.equals(dstAddr)) {
			dstAddrMatch = TrafficMatch.MATCH;
		} else {
			Subnet subnet = null;
			try {
				subnet = new Subnet(this.dstAddr, dstAddrPrefixLen);
			} catch (Throwable e) {
				log.error("Failed to construct Subnet. " + this.dstAddr + ". " + e.getLocalizedMessage());
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp("Failed to construct Subnet. + ", e);
			}

			// Resolve destination address match 
			if(! subnet.inSubnet(dstAddr)) 
				return TrafficMatch.NO;
			dstAddrMatch = TrafficMatch.CONTAIN;
		}

		/* Empty protocols hashtable means all protocols. Empty port list means all ports (for that protocol) */
		if(protoPorts==null || protoPorts.isEmpty()) return TrafficMatch.CONTAIN;// Regardless whether dstAddr contains or matches

		/* Check protocol match/containment */
		List<Integer> ports = protoPorts.get(dfProtocol.name());
		if(ports == null) return TrafficMatch.NO;	// Did not find the matched protocol in the existing ones.
		protoMatch = (protoPorts.size() > 1) ? TrafficMatch.CONTAIN : TrafficMatch.MATCH;		
		if(dfProtocol != DFProtocol.TCP && dfProtocol != DFProtocol.UDP) { // Ports only in L4 protocols
			if(dstAddrMatch == TrafficMatch.MATCH && protoMatch == TrafficMatch.MATCH)
				return TrafficMatch.MATCH; 
			else
				return TrafficMatch.CONTAIN;
		}

		/* Compare ports (of the tcp or udp protocol) */
		if(ports.isEmpty() || (ports.size() == 1 && ports.get(0) == 0 ))
			portMatch = (dstPort == 0) ? TrafficMatch.MATCH : TrafficMatch.CONTAIN;			
		else {
			if(!ports.contains(dstPort)) return TrafficMatch.NO; // No match with any monitored port in the matched protocol
			portMatch = (ports.size() > 1) ? TrafficMatch.CONTAIN : TrafficMatch.MATCH;
		}

		/* Finally, check dstAddr, proto and port match or containment */
		if(dstAddrMatch == TrafficMatch.MATCH && protoMatch == TrafficMatch.MATCH && portMatch == TrafficMatch.MATCH)
			return TrafficMatch.MATCH; 
		else
			return TrafficMatch.CONTAIN;
	}	
}