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

public class FlowStatistics {
	
	public Node node;
	public List<FlowStat> flowStat;

    public FlowStatistics() {node = null; flowStat = null;}

    public Node getNode() {return node;}
    public void setNode(Node node) {this.node = node;}

    public List<FlowStat> getFlowStats() {return flowStat;}
    public void setFlowStats(List<FlowStat> flowStats) {this.flowStat = flowStats;}

    @Override
    public String toString() {
    	return "FlowStatistics [" + node.toString() + ":" + flowStat.toString() + "]";
    }
}
