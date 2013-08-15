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
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.mina.filter.firewall.Subnet;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort.DFProtocol;


public class Traffic {
	
	public enum TrafficMatch {
		MATCH,
		CONTAIN,
		NO
	}
	
	public InetAddress dstAddr;
	public int dstAddrPrefixLen;
	
	/* Entry key = DPProtocol.name(). value = list of ports.
	 * Empty hashtable means all protocols. Empty port list means all ports (for that protocol). */
	public Hashtable<String,ArrayList<Integer>> protoPorts;

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
	
	public TrafficMatch match(InetAddress dstAddr, DFProtocol dfProtocol, int dstPort) {

		if(this.dstAddr == null || dstAddr == null) return TrafficMatch.NO;	

		Subnet subnet = null;
		subnet = new Subnet(this.dstAddr, dstAddrPrefixLen);
		
		/* Resolve destination address match */
		if(! subnet.inSubnet(dstAddr)) return TrafficMatch.NO;
		TrafficMatch dstAddrMatch = this.dstAddr.equals(dstAddr.getHostName()) ? TrafficMatch.MATCH : TrafficMatch.CONTAIN;
		
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