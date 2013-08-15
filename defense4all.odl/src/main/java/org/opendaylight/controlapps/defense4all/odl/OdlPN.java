/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl;

import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.opendaylight.controlapps.defense4all.core.PN;
import org.opendaylight.controlapps.defense4all.core.ProtectionSLA;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort;
import org.opendaylight.controlapps.defense4all.core.TrafficTuple;


public class OdlPN extends PN {

	/* pnRepo added column names */
	public static final String VTN_NAME = "vtn_name";
	public static final String NORTHBOUND_VEXTERNAL_PREFIX = "nb_vex_";
	
	public String vtnName = null;
	public List<String> nbVexNames = null; // If empty, all previously set north bound vexternals are assumed.

	/** ### Description ###
	 * @param param_name 
	 * @throws UnknownHostException 
	 */
	public OdlPN(String label, String dstAddr, int dstAddrPrefixLen, ProtocolPort protocolPort,
			Properties amsConfigProps, ProtectionSLA protectionSLA, boolean diversionConfirmation,
			String detectorLabel, TrafficTuple thresholds, String thresholdStr, MitigationScope scope,
			List<String> trafficFloorKeys, Properties props)	throws UnknownHostException {		
		super(label, dstAddr, dstAddrPrefixLen, protocolPort, amsConfigProps, protectionSLA, 
				diversionConfirmation, detectorLabel, thresholdStr, scope, true, trafficFloorKeys, props);	
	}
	
	/** ### Description ###
	 * @param param_name 
	 */
	public OdlPN(PN other) {
		
		super(other);
		
		/* Instantiate Vtn name - must be present. */
		vtnName = props.getProperty(VTN_NAME);
		if(vtnName == null) 
			throw new InvalidParameterException("Vtn name was not specified for protected network " + label);
		
		/* Retrieve all vexternal name properties (key is formatted as nb_vex_X, where X is some suffix) */
		Iterator<Map.Entry<Object,Object>> iter = props.entrySet().iterator();
		Map.Entry<Object,Object> entry; String key;	nbVexNames = new ArrayList<String>();
		while(iter.hasNext()) {
			entry = iter.next();
			key = (String) entry.getKey();
			if(key.startsWith(NORTHBOUND_VEXTERNAL_PREFIX))
				nbVexNames.add((String) (entry.getValue()));
		}		
	}
}
