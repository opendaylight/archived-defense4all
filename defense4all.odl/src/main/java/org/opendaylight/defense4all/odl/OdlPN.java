/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.ProtectionSLA;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.TrafficTuple;


public class OdlPN extends PN {

	/** ### Description ###
	 * @param param_name 
	 * @throws UnknownHostException 
	 */
	public OdlPN(String label, String dstAddr, int dstAddrPrefixLen, ProtocolPort protocolPort,
			Properties amsConfigProps, ProtectionSLA protectionSLA, boolean dvsnConfirmation,
			String detectorLabel, TrafficTuple thresholds, String thresholdStr, MitigationScope scope,
			List<String> trafficFloorKeys, List<String> netNodeLabels, Properties props) 
					throws UnknownHostException {		
		super(label, dstAddr, dstAddrPrefixLen, protocolPort, amsConfigProps, protectionSLA, dvsnConfirmation, 
			  detectorLabel, thresholdStr, scope, true, trafficFloorKeys, netNodeLabels, props);		
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws UnknownHostException 
	 */
	public OdlPN(Hashtable<String,Object> pnRow) throws UnknownHostException {		
		super(pnRow);
	}
	
	/** ### Description ###
	 * @param param_name 
	 */
	public OdlPN(PN other) {		
		super(other);
	}
}
