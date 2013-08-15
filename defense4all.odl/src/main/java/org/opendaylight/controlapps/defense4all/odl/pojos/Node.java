/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Node {

	@JsonProperty("@id")
    public Object id;
	
	@JsonProperty("@type")
    public String type; // "OF" (openflow), "PE" (pcep), "PK" (onepk), "PR" (production)

    public Node() {this.id = null; this.type = null;}
    public Node(Object id, String nodeType) {this.id = id; this.type = nodeType;}

    public String getType() {return this.type;}
    public void setType(String type) {this.type = type;}

    public Object getNodeID() {return this.id;}
    public void setNodeID(Object nodeID) {this.id = nodeID;}

    @Override
    public String toString() {
    	return "Node [" + id.toString() + ":" + type.toString() + "]";
    }
}
