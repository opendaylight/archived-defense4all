/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core.interactionstructures;

import java.util.List;

import org.opendaylight.defense4all.core.PlacementQoS;

public class StatsCountersPlacement {
	public List<String> counterLocations;
	public PlacementQoS placementQoS;
	
	/** ### Description ###
	 * @param param_name 
	 */
	public StatsCountersPlacement(List<String> counterLocations, PlacementQoS placementQoS) {
		this.counterLocations = counterLocations;
		this.placementQoS = placementQoS;
	}
	
	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("StatsCountersPlacement [placementQoS="); sb.append(placementQoS == null ? "Not set" : placementQoS);
		sb.append(", counterLocations: ");
		for(String counterLocation : counterLocations) {
			sb.append(counterLocation); sb.append(", "); 
		}
		sb.setLength(sb.length() - 2); // Remove last ", "
		sb.append("]");
		
		return sb.toString();
	}
}
