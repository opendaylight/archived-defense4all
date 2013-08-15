/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

import java.util.List;

public class FlowConfigs {

	public List<FlowConfig> flowConfig;

	public FlowConfigs() { }

	public List<FlowConfig> getFlowConfig() {return flowConfig;}
	public void setFlowConfig(List<FlowConfig> flowConfig) {this.flowConfig = flowConfig;}

    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append("FlowConfigs [ ");
    	for(FlowConfig fc : flowConfig)
    		sb.append(fc.toString());
    	sb.append(" ]");
        return sb.toString();
    }
}
