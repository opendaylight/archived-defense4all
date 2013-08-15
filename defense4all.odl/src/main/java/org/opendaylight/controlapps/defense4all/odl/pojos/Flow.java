/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

public class Flow {
	
	public Match match;
	public FlowActions actions;
	public short priority;
	public short idleTimeout;
	public short hardTimeout;
	public long id; // cookie

    public Flow() {match = null; actions = null; priority = 0; idleTimeout = hardTimeout = 0; id = 0;}
    
    public Match getMatch() {return match;}
	public void setMatch(Match match) {this.match = match;}

	public FlowActions getActions() {return actions;}
	public void setActions(FlowActions actions) {this.actions = actions;}

	public short getPriority() {return priority;}
	public void setPriority(short priority) {this.priority = priority;}

	public short getIdleTimeout() {return idleTimeout;}
	public void setIdleTimeout(short idleTimeout) {this.idleTimeout = idleTimeout;}

	public short getHardTimeout() {return hardTimeout;}
	public void setHardTimeout(short hardTimeout) {this.hardTimeout = hardTimeout;}

	public long getId() {return id;}
	public void setId(long id) {this.id = id;}

	@Override
    public String toString() {
        return "Flow [ " +  match.toString() + ", priority = " + priority 
                + ", idleTimeout = " + idleTimeout + ", hardTimeout = " + hardTimeout + ", id = " + id + "]";
    }
}