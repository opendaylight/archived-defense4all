/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.cli;

import java.util.ArrayList;

import org.opendaylight.defense4all.framework.core.Utils;

public class Cli {

	public enum Defense4AllCmd {
		help, getpns, getpnscount, getpn, addpn, removepn, getamss, getamsscount, getams, addams, removeams, getofcs, getofc, addofc, removeofc, getnetnodes, getnetnodescount, getnetnode, addnetnode, removenetnode, getmitigations, getmitigation, getattacks, getattack, cmdlist
	}

	public static final String user = "admin";
	public static final String password = "admin";

	public static void main(String[] args) {

		if(args.length < 1)	{ // Illegal command structure. Should be: controlapps Defense4AllCmd_name param1 param2 ...
			displayUsage();
			System.exit(0);
		}
		Defense4AllCmd dfCmd = Defense4AllCmd.cmdlist;
		try {
			dfCmd = Defense4AllCmd.valueOf(args[0]);
		} catch (Throwable e) { // Illegal Defense4AllCmd_name
			displayUsage();
			System.exit(0);
		}

		ArrayList<String> params = Utils.paramsFromArgs(args, 1);		
		switch (dfCmd) {
		case help:
			displayUsage(params); // Could be help for a Defense4AllCmd or a general help
			System.exit(0);
		/*****NetNodes**********/
		case getnetnodes:
			CliNetNode.handleGetNetNodes();
			System.exit(0);
		case getnetnodescount:
			CliNetNode.handleGetNetNodesCount();
			System.exit(0);
		case getnetnode:
			CliNetNode.handleGetNetNode(params);
			System.exit(0);
		case addnetnode:
			CliNetNode.handleAddNetNode(params);
			System.exit(0);
		case removenetnode:
			CliNetNode.handleRemoveNetNode(params);
			System.exit(0);
		/*****OFCs**********/			
		case getofcs:
			CliOfc.handleGetOfcs();
			System.exit(0);
		case getofc:
			CliOfc.handleGetOfc(params);
			System.exit(0);
		case addofc:
			CliOfc.handleAddOfc(params);
			System.exit(0);
		case removeofc:
			CliOfc.handleRemoveOfc(params);
			System.exit(0);
		/*****AMSs**********/
		case getamss:
			CliAms.handleGetAms();
			System.exit(0);
		case getamsscount:
			CliAms.handleGetAmsCount();
			System.exit(0);
		case getams:
			CliAms.handleGetAms(params);
			System.exit(0);
		case addams:
			CliAms.handleAddAms(params);
			System.exit(0); 	
		case removeams:
			CliAms.handleRemoveAms(params);
			System.exit(0);
		/*****PNS**********/			
		case getpns:
			CliPn.handleGetPns();
			System.exit(0);
		case getpnscount:
			CliPn.handleGetPnsCount();
			System.exit(0);
		case getpn:
			CliPn.handleGetPns(params);
			System.exit(0);
		case addpn:
			CliPn.handleAddPn(params);
			System.exit(0);
		case removepn:
			CliPn.handleRemovePn(params);
			System.exit(0);
		/*****Attacks**********/		
		case getattacks:
			CliAttack.handleGetAttacks();
			System.exit(0);
		case getattack:
			CliAttack.handleGetAttack(params);
			System.exit(0);
		/*****Mitigations**********/
		case getmitigations:
			CliMitigation.handleGetMitigations();
			break;
		case getmitigation:
			CliMitigation.handleGetMitigation(params);
			System.exit(0);
		/*****Other**********/
		case cmdlist:
			handleCmdList();
			System.exit(0);
		default:
			displayUsage();
			System.exit(0);

		}

	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws exception_type
	 *             circumstances description
	 */
	protected static void displayUsage() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps cmd [params]\n");
		sb.append("   Where cmd is one of: ");
		Defense4AllCmd[] defense4AllCmds = Defense4AllCmd.values();
		for (Defense4AllCmd frameworkCmd : defense4AllCmds) {
			sb.append(frameworkCmd.name());
			sb.append(", ");
		}
		sb.setLength(sb.length() - 2); /* Remove the last ", " */
		sb.append("\n");
		sb.append("   [params] - zero or more cmd specific params, separated by spaces (e.g., param1 param2 ...)\n");
		sb.append("To get help about specific cmd type: controlapps cmd help\n");

		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws exception_type
	 *             circumstances description
	 */
	protected static void displayUsage(ArrayList<String> params) {

		if (params.isEmpty()) {
			displayUsage();
			return;
		}

		Defense4AllCmd dfCmd = Defense4AllCmd.help;
		try {
			dfCmd = Defense4AllCmd.valueOf(params.get(0));
		} catch (Throwable e) { // Illegal FrameworkCmd_name
			displayUsage();
			return;
		}

		switch (dfCmd) {
		case getnetnodes:
			CliNetNode.displayUsageGetNetnodes();
			break;
		case getnetnodescount:
			CliNetNode.displayUsageGetNetnodesCount();
			break;
		case getnetnode:
			CliNetNode.displayUsageGetNetnode();
			break;
		case addnetnode:
			CliNetNode.displayUsageAddNetnode();
			break;
		case removenetnode:
			CliNetNode.displayUsageRemoveNetnode();
			break;

			/*****OFC**********/
		case getofcs:
			CliOfc.displayUsageGetOfcs();
			break;	
		case getofc:
			CliOfc.displayUsageGetOfc();;
			break;
		case addofc:
			CliOfc.displayUsageAddOfc();
			break;
		case removeofc:
			CliOfc.displayUsageRemoveOfc();
			break;		

		case getamss:
			CliAms.displayUsageGetAmss();
			break;
		case getamsscount:
			CliAms.displayUsageGetAmsCount();
			break;
		case getams:
			CliAms.displayUsageGetAms();
			break;
		case addams:
			CliAms.displayUsageAddAms();
			break; 	
		case removeams:
			CliAms.displayUsageRemoveAms();
			break;	

		case getpns:
			CliPn.displayUsageGetPns();
			System.exit(0);
		case getpnscount:
			CliPn.displayUsageGetPnsCount();
			System.exit(0);
		case getpn:
			CliPn.displayUsageGetPn();
			System.exit(0);
		case addpn:
			CliPn.displayUsageAddPn(); 
			System.exit(0);
		case removepn:
			CliPn.displayUsageRemovePn();
			System.exit(0);

		case getattacks:
			CliAttack.displayUsageGetAttacks();
			break;
		case getattack:
			CliAttack.displayUsageGetAttack();
			break;
		case getmitigations:
			CliMitigation.displayUsageGetMitigations();
			break;
		case getmitigation:
			CliMitigation.displayUsageGetMitigation();
			break;

		case help: // Display general usage if no params. Display dfcmd specific
			// usage if param is a dfcmd.
			displayUsage(params);
			break;
		default:
			break;
		}
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws exception_type
	 *             circumstances description
	 */
	protected static void handleCmdList() {
		Defense4AllCmd[] dfCmds = Defense4AllCmd.values();
		for (Defense4AllCmd dfCmd : dfCmds) {
			if (dfCmd == Defense4AllCmd.cmdlist || dfCmd == Defense4AllCmd.help)
				continue;
			System.out.print(" " + dfCmd.name());
		}
	}

	protected static String[] splitAndAssertSize(String param, String delimiter)
			throws Exception {
		String[] split = param.split(delimiter);
		if (split.length < 2)
			throw new Exception("Invalid param format in " + param);
		return split;
	}
}
