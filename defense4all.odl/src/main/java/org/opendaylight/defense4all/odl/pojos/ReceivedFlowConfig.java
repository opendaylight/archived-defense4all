/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl.pojos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReceivedFlowConfig extends FlowConfig {
	
	public Object actions;

    public ReceivedFlowConfig() {
    	super();
    }    

	public Object getActions() {return actions;}
	public void setActions(Object actions) {this.actions = actions;}

	@Override
	protected String getDerivedString() {return ", actions=" + actions;}
}
