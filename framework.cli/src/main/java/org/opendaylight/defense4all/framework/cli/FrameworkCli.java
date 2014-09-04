/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.cli;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.codehaus.jackson.type.TypeReference;
import org.opendaylight.defense4all.framework.core.EventRecordData;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.Utils;

public class FrameworkCli {

	public enum FrameworkCmd {
		help,
		sethostaddr,
		gethostaddr,
		terminate,
		reset,
		frevents,
		frlatest,
		frdump,
		frcleanup,
		cmdlist
	}

	public enum FrSubArgs {
		maxNum,
		fromDate,
		toDate,
		toFile,
		olderDays,
		filter
	}

	public static final String user = "user";
	public static final String password = "password";

	public static void main(String[] args) {

		if(args.length < 1)	{ // Illegal command structure. Should be: controlapps FrameworkCmd_name param1 param2 ...
			displayUsage();
			System.exit(0);
		}
		FrameworkCmd fCmd = null;
		try {
			fCmd = FrameworkCmd.valueOf(args[0]);
		} catch (Throwable e) { // Illegal FrameworkCmd_name
			displayUsage();
			System.exit(0);
		}

		ArrayList<String> params = Utils.paramsFromArgs(args, 1);		
		switch (fCmd) {
		case gethostaddr:
			handleGethostaddr(params);
			System.exit(0);
		case sethostaddr:
			handleSethostaddr(params);
			System.exit(0);
		case terminate:
			handleTerminate(params);
			System.exit(0);
		case reset:
			handleReset(params);
			System.exit(0);
		case help:
			displayUsage(params); // Could be help for a FrameworkCmd or a general help
			System.exit(0);
		case frevents:
			handleFREvents(params);
			System.exit(0);
		case frlatest:
			handleFRLatest(params);
			System.exit(0);
		case frdump:
			handleFRDump(params);
			System.exit(0);
		case frcleanup:
			handleFRCleanup(params);
			System.exit(0);
		case cmdlist:
			handleCmdList();
			System.exit(0);
		}		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsage() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps cmd [params]\n");
		sb.append("   Where cmd is one of: "); 
		FrameworkCmd[] frameworkCmds = FrameworkCmd.values();
		for(FrameworkCmd frameworkCmd : frameworkCmds) {
			sb.append(frameworkCmd.name()); sb.append(", ");
		}
		sb.setLength(sb.length() - 2); /* Remove the last ", " */ sb.append("\n");
		sb.append("   [params] - zero or more cmd specific params, separated by spaces (e.g., param1 param2 ...)\n");
		sb.append("To get help about specific cmd type: controlapps cmd help\n");

		System.out.println(sb.toString());
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleCmdList() {
		FrameworkCmd[] frameworkCmds = FrameworkCmd.values();
		for(FrameworkCmd frameworkCmd : frameworkCmds) {
			if ( frameworkCmd == FrameworkCmd.cmdlist || frameworkCmd == FrameworkCmd.help  )
				continue;
			System.out.print(" "+frameworkCmd.name());
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsage(ArrayList<String> params) {

		if(params.isEmpty()) {
			displayUsage();
			return;
		}

		FrameworkCmd fCmd = null;
		try {
			fCmd = FrameworkCmd.valueOf(params.get(0));
		} catch (Throwable e) { // Illegal FrameworkCmd_name
			displayUsage();
			return;
		}

		switch (fCmd) {
		case gethostaddr:
			displayUsageGethostaddr();
			return;
		case sethostaddr:
			displayUsageSethostaddr();
			return;
		case terminate:
			displayUsageTerminate();
			return;
		case reset:
			displayUsageReset();
			return;
		case frevents:
			displayUsageFREvents();
			return;
		case frlatest:
			displayUsageFRLatest();
			return;
		case frdump:
			displayUsageFRDump();
			return;
		case frcleanup:
			displayUsageFRCleanup();
			return;
		case help:
			displayUsage(params); // Display general usage if no params. Display fcmd specific usage if param is a fcmd.
			return;
		case cmdlist:
			return;
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGethostaddr() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps gethostaddr\n");
		sb.append("   Description - returns the IP address of this (virtual) machine hosting the controlapps instance. ");

		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageSethostaddr() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps sethostaddr hostaddress\n");
		sb.append("   Description - sets in local controlapps instance the IP address of the (virtual) machine hosting it.");

		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageTerminate() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps terminate terminate_scope\n");
		sb.append("    Where terminate_scope is \"this\", or \"cluster\". If none provided \"this\" is assumed.");
		sb.append("   Description - terminates local (this) controlapps instance or the entire controlapps cluster. ");
		sb.append("                 In case of a single instance cluster the two options are identical. ");

		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageReset() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps reset reset_type\n");
		sb.append("    Possible reset types are:\n");
		for(ResetLevel resetLevel : ResetLevel.values()) {
			sb.append("           "); sb.append(resetLevel.name()); sb.append(" - "); 
			sb.append(resetLevel.getExplanationMsg()); sb.append("\n");
		}
		sb.append("    Description - Resets controlapps (all instances in a cluster) according to the specified reset type.");
		sb.append("    Warning! User should be very cautious conducting the more severe resets, especially \"factory\".");

		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageFRLatest() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps frlatest \n");
		sb.append("        [filter=list of event categories ',' delimited]\n");
		sb.append("        [maxNum=<number of events>]\n");
		sb.append("   Description - displays latest recorded events in format: Date_time event_category event_description");
		
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageFREvents() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps frevents \n");
		sb.append("        [filter=list of event categories ',' delimited]\n");
		sb.append("        [fromDate=MM/dd/yyyy_hh:mm:ss toDate=MM/dd/yyyy_hh:mm:ss maxNum=<number of events>]\n");
		sb.append("   Description - displays a range of recorded events in format: Date_time event_category event_description");
		
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageFRDump() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps frdump toFile=<file name> \n");
		sb.append("        [filter=list of event categories ',' delimited]\n");
		sb.append("        [fromDate=MM/dd/yyyy_hh:mm:ss toDate=MM/dd/yyyy_hh:mm:ss maxNum=<number of events>]");
				
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageFRCleanup() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps frcleanup olderDays=<number of days>\n");
		sb.append("    Description - cleanup event older then specified parameter ");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleSethostaddr(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageSethostaddr();
			return;
		}

		String hostaddr = params.get(0);
		if(hostaddr == null || hostaddr.isEmpty()) {
			displayUsageSethostaddr();
			return;			
		}

		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			connector.postToControlApps("hostaddress", hostaddr);
		} catch (Exception e) {
			System.out.println("Could not set hostaddr because " + e.getMessage());
		}
		System.out.println("Setting hostaddr = " + hostaddr);		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGethostaddr(ArrayList<String> params) {

		String hostaddr = null;
		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			hostaddr = connector.getStringFromControlApps("hostaddress");
		} catch (Exception e) {
			System.out.println("Could not get hostaddr because " + e.getMessage());
		}
		System.out.println("hostaddr = " + hostaddr);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleTerminate(ArrayList<String> params) {

		String param = (params != null && !params.isEmpty()) ? params.get(0) : null;
		String scope = (param != null && !param.isEmpty()) ? param : "this";

		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			connector.postToControlApps("terminate", scope);
		} catch (Exception e) {
			System.out.println("Could not initiate terminate because " + e.getMessage());
		}
		System.out.println("Initiating terminate " + scope);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleReset(ArrayList<String> params) {

		String param = (params != null && !params.isEmpty()) ? params.get(0) : null;
		ResetLevel resetLevel = (param!=null && !param.isEmpty()) ? ResetLevel.valueOf(param) : ResetLevel.soft;

		if(resetLevel == ResetLevel.factory) {
			String msg = "Are you sure you want to perform factory reset? Please respond with [yes, Yes or Y].";
			boolean confirmed = Utils.confirmYesByUser(msg);
			if(!confirmed) return;
		}

		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			connector.postToControlApps("reset", resetLevel.toString());
		} catch (Exception e) {
			System.out.println("Could not initiate reset because " + e.getMessage());
		}
		System.out.println("Initiating reset " + resetLevel.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleFREvents(ArrayList<String> params) {
		
		TypeReference<?> typeEventRecord = new TypeReference<List<EventRecordData>>(){};
		List<EventRecordData> listEvents = null;
		
		String params_url = formatFRargumentsURL(params);
		if ( params_url == null ) {
			displayUsageFREvents();
			return;
		}
		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			listEvents = connector.getFromControlApps("fr/events"+params_url, typeEventRecord);
		} catch (Exception e) {
			System.out.println("Could not get events because " + e.getMessage());
			return;
		}
		
		if(listEvents == null)
			System.out.println("No events");
		else {
			for (EventRecordData ev:listEvents ) 
				System.out.println(ev.toString());	
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleFRLatest(ArrayList<String> params) {
		
		TypeReference<?> typeEventRecord = new TypeReference<List<EventRecordData>>(){};
		List<EventRecordData> listEvents = null;
		
		String params_url = formatFRargumentsURL(params);
		if ( params_url == null ) {
			displayUsageFRLatest();;
			return;
		}
		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			listEvents = connector.getFromControlApps("fr/latest"+params_url, typeEventRecord);
		} catch (Exception e) {
			System.out.println("Could not get latest events because " + e.getMessage());
			return;
		}

		if(listEvents == null)
			System.out.println("No events");
		else {
			for (EventRecordData ev:listEvents ) 
				System.out.println(ev.toString());	
		}		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleFRDump(ArrayList<String> params) {

		Map<String,String> rest_input = formatFRarguments(params);

		if (rest_input == null || rest_input.get("toFile") == null ) {
			displayUsageFRDump();
			return;
		}

		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			connector.postToControlApps("fr/dump", rest_input);
		} catch (Exception e) {
			System.out.println("Could not initiate dump events because " + e.getMessage());
		}
		System.out.println("Initiating dump to " + rest_input.get("toFile"));
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleFRCleanup(ArrayList<String> params) {
		
		Map<String,String> rest_input = formatFRarguments(params);

		if (rest_input == null || rest_input.get("olderDays") == null ) {
			displayUsageFRCleanup();
			return;
		}

		try {
			FrameworkConnector connector = new FrameworkConnector(user, password);
			connector.postToControlApps("fr/cleanup", rest_input);
		} catch (Exception e) {
			System.out.println("Could not initiate cleanup events because " + e.getMessage());
		}
		System.out.println("Initiating cleanup");

	}

	private static Map<String,String> formatFRarguments(ArrayList<String> params)
	{
		Map<String,String> frArgs = new  HashMap<String, String>();
		FrSubArgs argname = null;
		String argvalue = null;

		for ( String subarg:params) {
			try {
				String[] split = subarg.split("=");
				argname = FrSubArgs.valueOf(split[0]);
				argvalue = split[1];
				if ( argname == null || argvalue == null ) {
					System.out.println("Invalid argument : " + subarg );
					return null;
				}
			}
			catch (Exception e) {
				System.out.println("Invalid argument : " + subarg );
				return null;
			}
			frArgs.put(argname.name() , argvalue);
		}
		String fromDateStr= frArgs.get("fromDate");
		String toDateStr= frArgs.get("toDate");
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy_hh:mm:ss");  
		try {   
			if (fromDateStr != null )
				sdf.parse(fromDateStr);  
			if ( toDateStr != null )
				sdf.parse( toDateStr );
		} catch ( Throwable pe) {  
			System.out.println("Invalid argument : "+pe.getMessage());
			return null;
		}   
		return frArgs;
	}
	
	private static String formatFRargumentsURL(ArrayList<String> params) {
		
		Map<String,String> frArgs = formatFRarguments(params);
		if ( frArgs== null)
			return null;
		
		StringBuilder sb = new StringBuilder();
		if ( frArgs.size() != 0 ) {
			sb.append("?");
			for ( String arg:frArgs.keySet()) {
				sb.append(arg).append("=");
				sb.append(frArgs.get(arg));
				sb.append("&");
			}
			sb.setLength(sb.length()-1);
		}
		
		return sb.toString();
	}
}
