/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

public class MatchField {
	
	/* Content of matchType string */ 
	public enum MatchType {
	    IN_PORT,
	    DL_SRC,
	    DL_DST,
	    DL_VLAN,
	    DL_VLAN_PR, 
	    DL_OUTER_VLAN,
	    DL_OUTER_VLAN_PR,
	    DL_TYPE,
	    NW_TOS,
	    NW_PROTO,
	    NW_SRC,
	    NW_DST,
	    TP_SRC,
	    TP_DST		
	}
	
	public Object mask;
	public String matchType; 
	public Object value;

	public MatchField() {mask = null; matchType = null; value = null;}

    public Object getMask() {return mask;}
	public void setMask(Object mask) {this.mask = mask;}

	public String getType() {return matchType;}
	public void setType(String matchType) {this.matchType = matchType;}

	public Object getValue() {return value;}
	public void setValue(Object value) {this.value = value;}

	@Override
    public String toString() {
        return "MatchField [ " + "mask=" + mask.toString() + ", matchType=" + matchType.toString() 
        		+ ", value=" + value.toString() + "]";
    }
}
