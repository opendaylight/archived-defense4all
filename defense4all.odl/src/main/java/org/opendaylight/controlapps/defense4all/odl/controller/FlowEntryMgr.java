/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.controlapps.defense4all.odl.controller;

import org.opendaylight.controlapps.defense4all.odl.Odl;
import org.springframework.jmx.access.InvalidInvocationException;
import org.codehaus.jackson.type.TypeReference;

/**
 * @author gerag
 *
 */
public class FlowEntryMgr {
	
	// TODO: Temporary before integration with controller
	protected class FlowConfig {}
	protected class FlowConfigs {}
	protected class FlowStatistics {}
	protected class NodeIDType{ public static final String OPENFLOW = "OF";}
	
	

	static protected String openFlowEntryType = NodeIDType.OPENFLOW;
	
	public Connector connector = null;
	protected Odl odl;

	public FlowEntryMgr() {}

	public FlowConfig getOpenFlowEntry(long nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		FlowConfig flowConfig = connector.getFromController(urlPrefix.toString(), new TypeReference<FlowConfig>(){});
		return flowConfig;
	}

	public void addOpenFlowEntry(long nodeId, String flowEntryLabel, FlowConfig flowEntry) throws Exception {
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		connector.addToController(urlPrefix.toString(), flowEntry);
	}

	public void toggleOpenFlowEntry(long nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		connector.putToController(urlPrefix.toString());
	}

	public void deleteOpenFlowEntry(long nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		connector.delFromController(urlPrefix.toString());
	}

	public FlowStatistics getOpenFlowStats(long nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		FlowStatistics flowStatistics = connector.getFromController(urlPrefix.toString(), new TypeReference<FlowConfig>(){});
		return flowStatistics;
	}

	/* Append: {constUrlPrefix}/{containerName}/{nodeType}/{nodeId}/{name}/ */
	protected StringBuilder constructFlowUrlPrefix(long nodeId, String flowEntryLabel) {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append("/"); urlPrefix.append(odl.constFlowUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.ofcName); 
		urlPrefix.append("/"); urlPrefix.append(openFlowEntryType); 
		urlPrefix.append("/"); urlPrefix.append(nodeId); 
		urlPrefix.append("/"); urlPrefix.append(flowEntryLabel); 		
		return urlPrefix;
	}

	/* Append: {constUrlPrefix}/{containerName}/ */
	protected StringBuilder constructAllFlowsUrlPrefix() {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append("/"); urlPrefix.append(odl.constFlowUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.ofcName); 
		return urlPrefix;
	}

	/* Append: {containerName}/flowstats/{nodeType}/{nodeId} */
	protected StringBuilder constructFlowStatsUrlPrefix(long nodeId) {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append("/"); urlPrefix.append(odl.constStatsUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.ofcName); 
		urlPrefix.append("/flowstats");
		urlPrefix.append("/"); urlPrefix.append(openFlowEntryType); 
		urlPrefix.append("/"); urlPrefix.append(nodeId); 		
		return urlPrefix;
	}

	public void reset() {
	}
}

