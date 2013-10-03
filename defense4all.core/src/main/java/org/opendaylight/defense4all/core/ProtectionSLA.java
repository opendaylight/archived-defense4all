/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

public class ProtectionSLA {
	
	protected String sla;

	/* ### Description ###
	 * @param param_name 
	 */
	public ProtectionSLA() {
		sla = null;
	}
	
	/**
	 * 
	 */
	public ProtectionSLA(String protectionSLAString) {
		sla = protectionSLAString;
	}
	
	public String toString() {
		return sla;
	}
}
