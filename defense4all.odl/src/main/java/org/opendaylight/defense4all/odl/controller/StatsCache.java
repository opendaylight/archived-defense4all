/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.odl.controller;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gerag
 *
 */
public class StatsCache {
	
	public class CachedStat {
		
		public long flowId; // Cookie
	    public long packetCount;
	    public long byteCount;
	    public long readingTime; // in seconds
	}
	
	public ConcurrentHashMap<String,ConcurrentHashMap<String,CachedStat>> statsCache;

	/**
	 * 
	 */
	public StatsCache() {
		statsCache = new ConcurrentHashMap<String,ConcurrentHashMap<String,CachedStat>>();
	}

}
