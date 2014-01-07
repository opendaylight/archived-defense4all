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
public class FlowStatistics {
	
//	@JsonIgnore
//	public Node node;
	public List<FlowStat> flowStatistic;

    public FlowStatistics() {flowStatistic = null;}

    public List<FlowStat> getFlowStats() {return flowStatistic;}
    public void setFlowStats(List<FlowStat> flowStats) {this.flowStatistic = flowStats;}

    @Override
    public String toString() {
    	return "FlowStatistics [" + flowStatistic.toString() + "]";
    }

	public static JsonPreprocessor getJsonPreprocessor() {
		
		return new JsonPreprocessor() {			
			@Override
			public String preProcess(String jsonStr) {
//				
//				if (jsonStr == null) return null;
//				
//				String actionsOccurence = "\"actions\":";
//				int actionsOccurenceLength = actionsOccurence.length();
//				StringBuilder result = new StringBuilder(jsonStr);
//				int index = 0; char nextChar; char nextChar2; int bracketsCount;
//				
//				while(index != -1 && index < result.length() - actionsOccurenceLength -2) {
//					
//					index = result.indexOf(actionsOccurence, index);
//					if(index == -1 || index > result.length() - actionsOccurenceLength -2) break;
//					
//					index = index + actionsOccurenceLength;
//					nextChar = result.charAt(index);
//					if(nextChar == '[') continue;	// This actions instance is already a json Array;					
//					
//					/* Need to insert '[', find matching '"' or paranthesis, and insert ']' afterwards */
//					result.insert(index, '[');
//					index += 2;		// Advance index beyond inserted '[' and opening '"' or '{' 
//					if(nextChar == '"') {
//						for(;index<result.length();index++) {
//							if(result.charAt(index) == '"') break;
//						}
//						index++; // To the position following the closing '"', in which ']' is to be inserted
//					} else if(nextChar == '{') {
//						bracketsCount = 1;
//						for(;index<result.length();index++) {
//							nextChar2 = result.charAt(index);
//							if(nextChar2 == '{' || nextChar2 == '[') bracketsCount++; 
//							else if(nextChar2 == '}' || nextChar2 == ']') bracketsCount--;
//							if(bracketsCount == 0) break;
//						}
//						index++; // To the position following the closing '}', in which ']' is to be inserted
//					}
//					result.insert(index, ']');
//				}
//				
//				return result.toString();
				return jsonStr;
			}
		};
	}
}
