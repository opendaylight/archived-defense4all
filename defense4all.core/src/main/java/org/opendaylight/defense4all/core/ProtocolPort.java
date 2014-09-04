/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

/**
 * 
 */
package org.opendaylight.defense4all.core;

public class ProtocolPort {
	
	public enum DFProtocol {
		INVALID,
		TCP,
		UDP,
		ICMP,
		IP;
		
		final static int invalidCounter = -1;
		final static int ipCounter = 0;
		final static int tcpCounter = 6;
		final static int udpCounter = 17;
		final static int icmpCounter = 1;
		
		public static DFProtocol getProtocol(int protocol) {
			switch ( protocol ) {
				case invalidCounter: return DFProtocol.INVALID;
				case tcpCounter:     return DFProtocol.TCP;
				case udpCounter:     return DFProtocol.UDP;
				case icmpCounter:    return DFProtocol.ICMP;
				default:             return DFProtocol.IP;
			}
		}
		
		public short getProtocolNumber() {
			if(this == DFProtocol.TCP) return tcpCounter;
			else if(this == DFProtocol.UDP) return udpCounter;
			else if(this == DFProtocol.ICMP) return icmpCounter;
			else if(this == DFProtocol.IP) return ipCounter;
			else return invalidCounter;
		}
		
		public static short getProtocolNumber(DFProtocol protocol) {
			if(protocol == DFProtocol.TCP) return tcpCounter;
			else if(protocol == DFProtocol.UDP) return udpCounter;
			else if(protocol == DFProtocol.ICMP) return icmpCounter;
			else if(protocol == DFProtocol.IP) return ipCounter;
			else return invalidCounter;
		}
		
	}
	
	public DFProtocol protocol;
	public int port;
	
	public ProtocolPort() {protocol = DFProtocol.INVALID; port = 0;}	
	public ProtocolPort(DFProtocol protocol, int port) {this.protocol = protocol; this.port = port;}
	
	@Override
	public boolean equals(Object other) { 
		DFProtocol otherProtocol = ((ProtocolPort)other).protocol;
		int otherPort = ((ProtocolPort)other).port;
		return protocol == otherProtocol && port==otherPort;  
	}
	
	public ProtocolPort(String serialized) {
		String[] split = serialized.split(":");
		protocol = DFProtocol.valueOf(split[0]);
		port = Integer.valueOf(split[1]);
	}
	
	@Override
	public String toString() {return protocol.name() + ":" + port;}

	public ProtocolPort clone() {return new ProtocolPort(protocol, port);}
	
	public String toPrintableString() {
		if ( port != 0 )
			return protocol.name() + ":" + port;
		else
			return protocol.name();
	}
}
