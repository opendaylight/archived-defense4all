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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.access.InvalidInvocationException;
import com.fasterxml.jackson.core.type.TypeReference;

public class FlowEntryMgr {

	// TODO: Temporary before integration with controller
	protected class NodeIDType{ public static final String OPENFLOW = "OF";}	

	Logger log = LoggerFactory.getLogger(this.getClass());

	static protected String openFlowEntryType = NodeIDType.OPENFLOW;

	public Connector connector = null;
	public Odl odl;

	public FlowEntryMgr() {}

	/* Setters for Spring */
	public void setOdl(Odl odl) {this.odl = odl;}

	protected void assertConnectorNotNull() throws InvalidInvocationException {

		if(connector == null) {
			log.error("No OFC has been set yet");
			throw new InvalidInvocationException("No OFC has been set yet");
		}
	}

	public ReceivedFlowConfig getOpenFlowEntry(String nodeId, String flowEntryLabel) throws Exception {

		try {
			assertConnectorNotNull();
			StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
			TypeReference<?> typeRef = new TypeReference<ReceivedFlowConfig>(){};
			JsonPreprocessor preprocessor = ReceivedFlowConfig.getJsonPreprocessor();
			ReceivedFlowConfig flowConfig = connector.getFromController(urlPrefix.toString(), typeRef, preprocessor);
			return flowConfig;
		} catch (Throwable e) {
			String msg = "Excepted trying to getOpenFlowEntry for " + nodeId + " " + flowEntryLabel;
			log.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public void addOpenFlowEntry(String nodeId, String flowEntryLabel, Object flowEntry) throws Exception {

		try {
			assertConnectorNotNull();
			StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
			connector.putToController(urlPrefix.toString(), flowEntry);
		} catch (Throwable e) {
			String msg = "Excepted trying to addOpenFlowEntry for " + nodeId + " " + flowEntryLabel + " " + flowEntry;
			log.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public void toggleOpenFlowEntry(String nodeId, String flowEntryLabel) throws Exception {

		try {
			assertConnectorNotNull();
			StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
			connector.putToController(urlPrefix.toString());
		} catch (Throwable e) {
			String msg = "Excepted trying to toggleOpenFlowEntry for " + nodeId + " " + flowEntryLabel;
			log.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public void deleteOpenFlowEntry(String nodeId, String flowEntryLabel) throws Exception {

		try {
			assertConnectorNotNull();
			StringBuilder urlPrefix = constructFlowUrlPrefix(nodeId, flowEntryLabel);
			connector.delFromController(urlPrefix.toString());
		} catch (Throwable e) {
			String msg = "Excepted trying to deleteOpenFlowEntry for " + nodeId + " " + flowEntryLabel;
			log.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public FlowStatistics getOpenFlowStats(String nodeId) throws Exception {

		try {
			assertConnectorNotNull();
			StringBuilder urlPrefix = constructFlowStatsUrlPrefix(nodeId);
			TypeReference<?> typeRef = new TypeReference<FlowStatistics>(){};
			JsonPreprocessor preprocessor = FlowStatistics.getJsonPreprocessor();
			FlowStatistics flowStatistics = connector.getFromController(urlPrefix.toString(), typeRef, preprocessor);
			return flowStatistics;
		} catch (Throwable e) {
			String msg = "Excepted trying to getOpenFlowStats for " + nodeId;
			log.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	public Nodes getNodes() throws Exception {

		try {
			assertConnectorNotNull();
			StringBuilder urlPrefix = constructFlowSwitchUrlPrefix();
			TypeReference<?> typeRef = new TypeReference<Nodes>(){};
			JsonPreprocessor preprocessor = Nodes.getJsonPreprocessor();
			Nodes nodeProperties = connector.getFromController(urlPrefix.toString(), typeRef, preprocessor);
			return nodeProperties;
		} catch (Throwable e) {
			String msg = "Excepted trying to getNodes.";
			log.error(msg, e);
			throw new Exception(msg, e);
		}
	}

	/* Append: {constUrlPrefix}/node/{containerName}/{nodeType}/{nodeId}/staticFlow/{name}/ */
	protected StringBuilder constructFlowUrlPrefix(String nodeId, String flowEntryLabel) {

		assertConnectorNotNull();

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

		assertConnectorNotNull();

		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append(odl.constFlowUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		return urlPrefix;
	}

	/* Append: {containerName}/flow/node/{nodeType}/{nodeId} */
	protected StringBuilder constructFlowStatsUrlPrefix(String nodeId) {

		assertConnectorNotNull();

		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append(odl.constStatsUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		urlPrefix.append("/flow/node");
		urlPrefix.append("/"); urlPrefix.append(openFlowEntryType); 
		urlPrefix.append("/"); urlPrefix.append(nodeId); 		
		return urlPrefix;
	}

	/* Append: {containerName}/flowstats/{nodeType}/{nodeId} */
	protected StringBuilder constructFlowSwitchUrlPrefix() {

		assertConnectorNotNull();

		StringBuilder urlPrefix = new StringBuilder();		
		urlPrefix.append(odl.constSwitchUrlPrefix);
		urlPrefix.append("/"); urlPrefix.append(connector.odlOFC.restpathControllerName);
		urlPrefix.append("/nodes");		
		return urlPrefix;
	}

	public void reset() {
	}
}

