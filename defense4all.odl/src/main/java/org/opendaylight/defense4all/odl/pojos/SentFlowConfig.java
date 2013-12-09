/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl.pojos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.odl.OdlFlowConfigInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SentFlowConfig extends FlowConfig {
	
	public List<String> actions;	// According to ActionType

    public SentFlowConfig() {}
    
    public SentFlowConfig(OdlFlowConfigInfo flowInfo) throws ExceptionControlApp {    	
    	super(flowInfo);
    	actions = flowInfo.actions;
    }

	public List<String> getActions() {return actions;}
	public void setActions(List<String> actions) {this.actions = actions;}

	@Override
	protected String getDerivedString() {return ", actions=" + actions;}
}
