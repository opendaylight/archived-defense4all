package org.opendaylight.defense4all.cli;
/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.type.TypeReference;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;

public class CliMitigations {

	//TODO complete....
	public static final String explanation = "A record containing information about the mitigation measures being taken to mitigate an attack. "
			+ "Mitigation can take different forms, starting from mere reporting about the attack, "
			+ "through diversion of attacked traffic (target address range, protocol and port) through AMS (Attack Mitigation Systems) "
			+ "for cleansing, to blockage of traffic from attacking sources in network switches/routers. "
			+ "A Mitigation record contains the type of mitigation, mitigator id, "
			+ "as well as mitigation status and information (possibly collected from AMSs).";
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetMitigation() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getnetmitigation\n");
		sb.append("   Description - returns the Mitigation known to DF.\n");
		System.out.println(sb.toString());
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetMitigation() {

		List<Mitigation> mitigations;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<PN>>(){};
			mitigations = connector.getFromControlApps("mitigations", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get mitigations because " + e.getMessage());
			return;
		}
		if(mitigations == null || mitigations.isEmpty()) {
			System.out.println("DF has no pns configured.");
			return;
		}

		System.out.println("mitigations:\n");
		for(Mitigation mitigation : mitigations) {
			System.out.println(mitigation.toString());
			System.out.println("=================================");
		}
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetMitigation(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageGetMitigation();
			return;
		}
		String label = params.get(0);
		if(label == null || label.isEmpty()) {
			displayUsageGetMitigation();
			return;
		}

		Mitigation mitigation;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<PN>(){};
			mitigation = connector.getFromControlApps("attacks/" + label, typeRef);
			String printOut = (mitigation == null) ? "No mitigation " + label + " is known to DF.\n" : mitigation.toString();
			System.out.println(printOut);
		} catch (Exception e) {
			System.out.println("Could not get attack " + label + " because " + e.getMessage());
			return;
		}		
		
	}


}
