/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl.pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeProperties {
	
	public ReceivedNode node;
	@JsonIgnore
	public Object properties;

    public NodeProperties() {node = null; properties = null;}

    public ReceivedNode getNode() {return node;}
	public void setNode(ReceivedNode node) {this.node = node;}

	public Object getProperties() {return properties;}
	public void setProperties(Object properties) {this.properties = properties;}

	@Override
    public String toString() {
    	return "node :" + node.toString();
    }
}
