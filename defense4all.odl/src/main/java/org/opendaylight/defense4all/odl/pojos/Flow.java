/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl.pojos;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Flow {

	public long id; // cookie

    public Flow() {id = 0;}
    
	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	@Override
    public String toString() {
        return "Flow [ " +  "id = " + id + "]";
    }
}