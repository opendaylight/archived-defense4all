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
import java.util.ArrayList;
import java.util.Hashtable;

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
	
	public InetAddress dstAddr;
	public int dstAddrPrefixLen;
	
	/* Entry key = DPProtocol.name(). value = list of ports.
	 * Empty hashtable means all protocols. Empty port list means all ports (for that protocol). */
	public Hashtable<String,ArrayList<Integer>> protoPorts;
	
	/* Local hashtable for hostname lookups */
	public static class NameHash {
		
		private static Hashtable<InetAddress,String> nameHash = null;
		
		public static String getHostName(InetAddress addr) {
			if ( nameHash == null ) {
				nameHash = new Hashtable<InetAddress,String>();
			}
			if ( nameHash.containsKey(addr)) {
				return   (String)nameHash.get(addr) ; 
			} else {
				String name = addr.getHostName();
				nameHash.put(addr, name);
				return name;
			}
		}
		
		public static void reset() {
			if (nameHash != null )
				nameHash.clear();
		}
	}
	
	//protected static Hashtable<dstAddr,String> nameHash;

	public Traffic() {dstAddr = null; dstAddrPrefixLen = 0; protoPorts = new Hashtable<String,ArrayList<Integer>>();}
	
	public Traffic(InetAddress dstAddr, int dstAddrPrefixLen) {
		this();
		this.dstAddr = dstAddr; this.dstAddrPrefixLen = dstAddrPrefixLen;
	}
	
	public Traffic(Traffic other) {
		this();
		this.dstAddr = other.dstAddr; this.dstAddrPrefixLen = other.dstAddrPrefixLen; 
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
	
	public TrafficMatch match(InetAddress dstAddr, DFProtocol dfProtocol, int dstPort) throws ExceptionControlApp {

		if(this.dstAddr == null || dstAddr == null) return TrafficMatch.NO;	

		Subnet subnet = null;
		try {
			subnet = new Subnet(this.dstAddr, dstAddrPrefixLen);
		} catch (Throwable e) {
			log.error("Failed to construct Subnet. " + this.dstAddr + ". " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to construct Subnet. + ", e);
		}
		
		/* Resolve destination address match */
		if(! subnet.inSubnet(dstAddr)) return TrafficMatch.NO;
		//TrafficMatch dstAddrMatch = this.dstAddr.equals(dstAddr.getHostName()) ? TrafficMatch.MATCH : TrafficMatch.CONTAIN;
		TrafficMatch dstAddrMatch = this.dstAddr.equals(NameHash.getHostName(dstAddr)) ? TrafficMatch.MATCH : TrafficMatch.CONTAIN;
		
		/* Empty protocols hashtable means all protocols. Empty port list means all ports (for that protocol) */
		if(protoPorts==null || protoPorts.isEmpty()) return TrafficMatch.CONTAIN;// Regardless whether dstAddr contains or matches
		ArrayList<Integer> ports = protoPorts.get(dfProtocol.name());
		if(ports == null) return TrafficMatch.NO;	// No match with any of the monitored protocols
		if(dfProtocol != DFProtocol.TCP && dfProtocol != DFProtocol.UDP) return TrafficMatch.MATCH; // Ports only in L4 protocols
		if(ports.isEmpty() || ports.contains(0)) return TrafficMatch.CONTAIN; // All ports are monitored		
		if(!ports.contains(dstPort)) return TrafficMatch.NO; // No match with any monitored port in the matched protocol
		
		return dstAddrMatch;
	}	
}