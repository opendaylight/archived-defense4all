/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.core.interactionstructures;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bandwidth {
	
	public long bytes;
	public long packets;

	public static Logger log = LoggerFactory.getLogger(Bandwidth.class);
	public Bandwidth(long bytes, long packets) {this.bytes = bytes; this.packets = packets;}
	
	public Bandwidth(String bandwidth) throws IllegalArgumentException {
		
		try {
			String[] split = bandwidth.split(":");
			bytes = Long.valueOf(split[0]); packets = Long.valueOf(split[1]);
		} catch (Throwable e) {
			log.error("Excepted inflating from " + bandwidth, e);
			throw new IllegalArgumentException("Excepted inflating from " + bandwidth, e);
		}
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(Long.toString(bytes)); sb.append(":");
		sb.append(Long.toString(packets));
		return sb.toString();
	}
}