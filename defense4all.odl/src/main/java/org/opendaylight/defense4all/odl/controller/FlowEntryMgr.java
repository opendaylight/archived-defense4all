/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.odl.controller;

import org.opendaylight.defense4all.odl.Odl;
import org.opendaylight.defense4all.odl.controller.Connector.JsonPreprocessor;
import org.opendaylight.defense4all.odl.pojos.FlowStatistics;
import org.opendaylight.defense4all.odl.pojos.Nodes;
import org.opendaylight.defense4all.odl.pojos.ReceivedFlowConfig;
import org.opendaylight.defense4all.odl.pojos.SentFlowConfig;
import org.springframework.jmx.access.InvalidInvocationException;
import org.codehaus.jackson.type.TypeReference;

public class FlowEntryMgr {
	
	// TODO: Temporary before integration with controller
	protected class NodeIDType{ public static final String OPENFLOW = "OF";}	

	static protected String openFlowEntryType = NodeIDType.OPENFLOW;
	
	public Connector connector = null;
	public Odl odl;
	
	public FlowEntryMgr() {}
	
	/* Setters for Spring */
	public void setOdl(Odl odl) {this.odl = odl;}

	public ReceivedFlowConfig getOpenFlowEntry(String nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		TypeReference<?> typeRef = new TypeReference<ReceivedFlowConfig>(){};
		JsonPreprocessor preprocessor = ReceivedFlowConfig.getJsonPreprocessor();
		ReceivedFlowConfig flowConfig = connector.getFromController(urlPrefix.toString(), typeRef, preprocessor);
		return flowConfig;
	}

	public void addOpenFlowEntry(String nodeId, String flowEntryLabel, SentFlowConfig flowEntry) throws Exception {
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		connector.putToController(urlPrefix.toString(), flowEntry);
	}

	public void toggleOpenFlowEntry(String nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		connector.putToController(urlPrefix.toString());
	}

	public void deleteOpenFlowEntry(String nodeId, String flowEntryLabel) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
		connector.delFromController(urlPrefix.toString());
	}

	public FlowStatistics getOpenFlowStats(String nodeId) throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowStatsUrlPrefix(nodeId);
		TypeReference<?> typeRef = new TypeReference<FlowStatistics>(){};
		JsonPreprocessor preprocessor = FlowStatistics.getJsonPreprocessor();
		FlowStatistics flowStatistics = connector.getFromController(urlPrefix.toString(), typeRef, preprocessor);
		return flowStatistics;
	}

	public Nodes getNodes() throws Exception {	
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		StringBuilder urlPrefix = constructFlowSwitchUrlPrefix();
		TypeReference<?> typeRef = new TypeReference<Nodes>(){};
		JsonPreprocessor preprocessor = Nodes.getJsonPreprocessor();
		Nodes nodeProperties = connector.getFromController(urlPrefix.toString(), typeRef, preprocessor);
		return nodeProperties;
	}

	/* Append: {constUrlPrefix}/node/{containerName}/{nodeType}/{nodeId}/staticFlow/{name}/ */
	protected StringBuilder constructFlowUrlPrefix(String nodeId, String flowEntryLabel) {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append(odl.constFlowUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		urlPrefix.append("/node");
		urlPrefix.append("/"); urlPrefix.append(openFlowEntryType); 
		urlPrefix.append("/"); urlPrefix.append(nodeId); 
		urlPrefix.append("/staticFlow");
		urlPrefix.append("/"); urlPrefix.append(flowEntryLabel); 		
		return urlPrefix;
	}

	/* Append: {constUrlPrefix}/{containerName}/ */
	protected StringBuilder constructAllFlowsUrlPrefix() {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append("/"); urlPrefix.append(odl.constFlowUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		return urlPrefix;
	}

	/* Append: {containerName}/flow/node/{nodeType}/{nodeId} */
	protected StringBuilder constructFlowStatsUrlPrefix(String nodeId) {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append("/"); urlPrefix.append(odl.constStatsUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		urlPrefix.append("/flow/node");
		urlPrefix.append("/"); urlPrefix.append(openFlowEntryType); 
		urlPrefix.append("/"); urlPrefix.append(nodeId); 		
		return urlPrefix;
	}

	/* Append: {containerName}/flowstats/{nodeType}/{nodeId} */
	protected StringBuilder constructFlowSwitchUrlPrefix() {
		
		if(connector == null) throw new InvalidInvocationException("No OFC has been set yet");
		
		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append("/"); urlPrefix.append(odl.constSwitchUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		urlPrefix.append("/nodes");		
		return urlPrefix;
	}

	public void reset() {
	}
}

