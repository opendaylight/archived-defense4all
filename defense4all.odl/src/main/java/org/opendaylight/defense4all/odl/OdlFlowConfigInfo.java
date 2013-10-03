/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.odl;

import java.util.Hashtable;

import org.opendaylight.defense4all.core.FlowConfigInfo;

/**
 * @author gerag
 *
 */
public class OdlFlowConfigInfo extends FlowConfigInfo {

	/* OdlFlowConfigInfo Repo additional columns */	
	public static final String ID = "id";	// Cookie
	public static final String VLAN_ID = "vlan_id";	
	public static final String IDLE_TIMEOUT = "idle_timeout";
	public static final String HARD_TIMEOUT = "hard_timeout";
	
	/* ODL cookies range */
	public static final short ODL_COOKIE_MIN = 1;
	public static final short ODL_COOKIE_MAX = (short) 65000; // A bit less than 65535
	
	/* ODL Floors: 0-10: flows common to all PNs, 10-20: peace time flows, >20: attack flows */
	
	public short  id;
	public short  vlanId;
	public short  idleTimeout;
	public short  hardTimeout;	

	/* ### Description ###
	 * @param param_name 
	 */
	public OdlFlowConfigInfo() {
		super();
		id = 0; vlanId = 0; idleTimeout = 0; hardTimeout = 0;
	}
	
	public OdlFlowConfigInfo(Hashtable<String, Object> row) {
		
		super(row);
		
		id = (Short) row.get(ID);
		vlanId = (Short) row.get(VLAN_ID);
		idleTimeout = (Short) row.get(IDLE_TIMEOUT);
		hardTimeout = (Short) row.get(HARD_TIMEOUT);
	}

	public OdlFlowConfigInfo(OdlFlowConfigInfo other) {		
		super(other);
		this.id = other.id;	this.vlanId = other.vlanId;
		this.idleTimeout = other.idleTimeout;	this.hardTimeout = other.hardTimeout;
	}

	public Hashtable<String, Object> toRow() {
		
		Hashtable<String, Object> row = super.toRow();		

		row.put(ID, id);
		row.put(VLAN_ID, vlanId);
		row.put(IDLE_TIMEOUT, idleTimeout);
		row.put(HARD_TIMEOUT, hardTimeout);
		
		return row;
	}

	@Override
	public String generateAndSetKey() {
		if(id != 0)	{ // Cookie has been set (stats flow entry)
			key = String.valueOf(id); // Fastest to lookup - ODL statistics correspond to flowConfig cookie (id)
			return key;
		}
		return super.generateAndSetKey(); // Cookie has not been set so use the super key generator
	}
}
