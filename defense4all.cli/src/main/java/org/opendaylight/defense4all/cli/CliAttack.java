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
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.PN;

public class CliAttack {

	//TODO complete....
	public static final String explanation = "A record containing information about the mitigation measures being taken to mitigate an attack. "
			+ "Mitigation can take different forms, starting from mere reporting about the attack, "
			+ "through diversion of attacked traffic (target address range, protocol and port) through AMS (Attack Mitigation Systems) for cleansing, "
			+ "to blockage of traffic from attacking sources in network switches/routers. "
			+ "A Mitigation record contains the type of mitigation, mitigator id, "
			+ "as well as mitigation status and information (possibly collected from AMSs).";

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetAttack() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getnetattack\n");
		sb.append("   Description - returns the attack known to DF.\n");
		System.out.println(sb.toString());
	}



	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetAttack() {

		List<Attack> attacks;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<PN>>(){};
			attacks = connector.getFromControlApps("pns", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get pns because " + e.getMessage());
			return;
		}
		if(attacks == null || attacks.isEmpty()) {
			System.out.println("DF has no pns configured.");
			return;
		}

		System.out.println("pns:\n");
		for(Attack attack : attacks) {
			System.out.println(attack.toString());
			System.out.println("=================================");
		}
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetAttack(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageGetAttack();
			return;
		}
		String label = params.get(0);
		if(label == null || label.isEmpty()) {
			displayUsageGetAttack();
			return;
		}

		Attack attack;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<PN>(){};
			attack = connector.getFromControlApps("attacks/" + label, typeRef);
			String printOut = (attack == null) ? "No attack " + label + " is known to DF.\n" : attack.toString();
			System.out.println(printOut);
		} catch (Exception e) {
			System.out.println("Could not get attack " + label + " because " + e.getMessage());
			return;
		}		
		
	}


}
