/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl;

import java.util.Hashtable;
import java.util.Properties;

import org.opendaylight.controlapps.defense4all.core.OFC;

/**
 * @author gerag
 *
 */
public class OdlOFC extends OFC {

	public static final String OFC_NAME = "ofc_name";
	
	public String ofcName = null;
	
	public OdlOFC() {
		super();
		ofcName = Odl.DEFAULT_CONTROLLER_NAME;
	}

	public OdlOFC(String hostname, String ipAddrString, int port, String username, String password, 
			boolean forStatsCollection,	boolean forDiversion, Properties props, String ofcName) {
		super(hostname, ipAddrString, port, username, password,	forStatsCollection, forDiversion, props);
		this.ofcName = ofcName;
	}

	/* (non-Javadoc)
	 * @see org.opendaylight.controlapps.defense4all.core.OFC#toRow()
	 */
	public OdlOFC(Hashtable<String, Object> ofcRow) {
		super(ofcRow);
		ofcName = (String) ofcRow.get(OFC_NAME);
	}

	/* (non-Javadoc)
	 * @see org.opendaylight.controlapps.defense4all.core.OFC#toRow()
	 */
	@Override
	public Hashtable<String, Object> toRow() {
		
		Hashtable<String, Object> row = super.toRow();
		if(ofcName == null) ofcName = "";
		row.put(OFC_NAME, ofcName);		
		return row;
	}	
}
