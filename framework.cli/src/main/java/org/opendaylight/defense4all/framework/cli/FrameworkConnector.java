/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Kobi Samoray 
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.framework.cli;

public class FrameworkConnector extends ControlappsConnector {
	
	/* Example post request: http://localhost:8086/rest/general/hostaddress -d '10.206.167.31' */	
	public static final String FRAMEWORK_REST_SUBPATH = "/rest/general/";

	public FrameworkConnector(String username, String password) throws Exception {		
		super(username, password, FRAMEWORK_REST_SUBPATH);
	}
}
