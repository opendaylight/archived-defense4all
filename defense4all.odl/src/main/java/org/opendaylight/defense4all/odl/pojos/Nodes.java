/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl.pojos;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.opendaylight.defense4all.odl.controller.Connector.JsonPreprocessor;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Nodes {

	public List<NodeProperties> nodeProperties;

    public Nodes() {nodeProperties = null;}    

	public List<NodeProperties> getNodeProperties() {return nodeProperties;}	
	public void setNodeProperties(List<NodeProperties> nodeProperties) {this.nodeProperties = nodeProperties;}

	@Override
    public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("NodeProperties [ ");
		for(NodeProperties nodeProperty : nodeProperties) {
			sb.append(nodeProperty.toString());	sb.append(", ");
		}
		sb.append(" ]");
    	return sb.toString();
    }

	public static JsonPreprocessor getJsonPreprocessor() {
		
		return new JsonPreprocessor() {			
			@Override
			public String preProcess(String jsonStr) {
				
				String preBracket = "{\"nodeProperties\":";	 int preBracketLength = preBracket.length();
				if(jsonStr == null) return null;
				if(jsonStr.charAt(preBracketLength) == '[') return jsonStr;	// We already have an array
				
				StringBuilder result = new StringBuilder(jsonStr);				
				result.insert(preBracketLength, '[');
				result.insert(result.length() - 1, ']');
				
				return result.toString();
			}
		};
	}
}
