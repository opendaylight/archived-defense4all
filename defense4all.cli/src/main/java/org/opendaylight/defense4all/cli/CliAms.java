package org.opendaylight.defense4all.cli;
/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.type.TypeReference;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.framework.core.Utils;

public class CliAms {

	//TODO complete....
	public static final String explanation = "Attack Mitigation System, "
			+ "is a hardware or software physical or virtual device that detects, "
			+ "mitigates and reports DDoS and other cyber-attacks. "
			+ "AMS can be placed “in-line” or “out-of-path” relative to protected traffic flow. "
			+ "In the former the traffic flows through the AMS AT ALL TIMES. "
			+ "In the latter ONLY ATTACKED TRAFFIC is diverted to flow through the AMS, "
			+ "which then cleanses it returning only legitimate traffic to its original destination.";


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetAmsCount() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getamscount\n");
		sb.append("   Description - returns the number of AMSs known to DF.\n");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetAms() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getams ams_label\n");
		sb.append("   Description - returns the AMS corresponding to specified ams_label.\n");
		System.out.println(sb.toString());
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetAmss() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getamss\n");
		sb.append("   Description - returns the AMSs known to DF.\n");
		System.out.println(sb.toString());
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageAddAms() {

		String displayUsage = "Usage:  controlapps addofc param1 param2 ... \n"
				+"   Description - adds the ams described through the params.\n"
				+"   A params is formed -- field_name=field_value.\n"
				+"   A composite field is formed -- composite_field::sub_composite_field=sub_composite_field_value.\n"
				+"   I field in composite list element -- list_name::list_element_label::composite_field::sub_composite_field=value.\n"
				+"   The params are\n"
				+"       label -              [mandatory] user provided unique textual label (starts with a letter) to this ams.\n"
				+"       brand -              ams brand.\n"
				+"       version -            ams version.\n"
				+"       mgmtAddr -           [mandatory] Management address.\n"
				+"       mgmtPort -           [mandatory] Management port.\n"
				+"       mgmtUsername -       [mandatory] Management username.\n"
				+"       mgmtPassword -       [mandatory] Management password.\n"
				+"       forStatsCollection - is forStats collection (true/false default=false).\n"
				+"       forDiversion -       is for diversion (true/false default=false).\n"
				+"       healthCheckFrequency - health check frequency.\n"
				+"       props - 	{}.\n"	
				+"Example: controlapps addams label=dp1 mgmtAddr=10.206.167.51 mgmtPort=0 mgmtUsername=radware mgmtPassword=radware\n"
				+"forStatsCollection=false forDiversion=true\n";
		System.out.println(displayUsage);
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetAms() {

		List<AMS> amss;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<AMS>>(){};
			amss = connector.getFromControlApps("amss", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get amss because " + e.getMessage());
			return;
		}
		if(amss == null || amss.isEmpty()) {
			System.out.println("DF has no amss configured.");
			return;
		}

		System.out.println("amss:\n");
		for(AMS ams : amss) {
			System.out.println(ams.toString());
			System.out.println("=================================");
		}
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetAms(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageGetAms();
			return;
		}
		String label = params.get(0);
		if(label == null || label.isEmpty()) {
			displayUsageGetAms();
			return;
		}

		AMS ams;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<AMS>(){};
			ams = connector.getFromControlApps("amss/" + label, typeRef);
			String printOut = (ams == null) ? "No ams " + label + " is known to DF.\n" : ams.toString();
			System.out.println(printOut);
		} catch (Exception e) {
			System.out.println("Could not get ams " + label + " because " + e.getMessage());
			return;
		}		
		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleAddAms(ArrayList<String> params) {

		if(params == null) {
			displayUsageAddAms();
			return;
		}

		AMS ams = new AMS();
		ams.props = new Properties();

		try {
			for(String param : params) {

				if(param.startsWith("label"))
					addLabel(ams, param);
				else if(param.startsWith("brand"))
					addIpBrand(ams, param);
				else if(param.startsWith("version"))
					addVersion(ams, param);
				else if(param.startsWith("mgmtAddr"))
					addMgmtAddr(ams, param);
				else if(param.startsWith("mgmtPort"))
					addMgmtPort(ams, param);
				else if(param.startsWith("mgmtUsername"))
					addUsername(ams, param);
				else if(param.startsWith("mgmtPassword"))
					addPasswd(ams, param);			
				else if(param.startsWith("forStatsCollection"))
					addForStatsCollection(ams, param);
				else if(param.startsWith("forDiversion"))
					addForDiversion(ams, param);
				else if(param.startsWith("healthCheckFrequency"))
					addHealthCheckFrequency(ams, param);
				else if(param.startsWith("props"))
					addProp(ams, param);
			}
			ams.toJacksonFriendly(); // Jackson does not handle lists with complex items. Let it inflate strings 
										 // of those serialized lists, and inflate lists in "set" methods.
			ams.validate();

		} catch (Throwable e1) {
			System.out.println(e1.getMessage());
			displayUsageAddAms();
			return;
		}

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);	
			connector.postToControlApps("amss", ams);
		} catch (Exception e) {
			System.out.println("Could not add ams because " + e.getMessage());
		}
		System.out.println("Adding ams " + ams.label);
	}

	protected static void addLabel(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.label = split[1]; // Split "label=l1"
	}

	protected static boolean addIpBrand(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.brand = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
		return true;
	}

	protected static void addVersion(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.version = split[1]; // Split "type=OF"
	}



	protected static void addMgmtAddr(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.mgmtAddr = InetAddress.getByName(split[1]);  
	}
	protected static void addMgmtPort(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.mgmtPort = new Integer(split[1]);  
	}
	protected static void addUsername(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.username = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
	}
	protected static void addPasswd(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.password = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
	}
	protected static void addHealthCheckFrequency(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.healthCheckFrequency = new Integer(split[1]);  
	}
	protected static void addForStatsCollection(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.forStatsCollection = new Boolean(split[1]);  
	}
	
	protected static void addForDiversion(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ams.forDiversion = new Boolean(split[1]);  
	}
	
	protected static void addProp(AMS ams, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::"); // Split "props::example_prop=example_prop_value"
		split = split[1].split("="); // Can be [example_prop] or [example_prop, example_prop_value]
		if(split[0].isEmpty())
			throw new Exception("Invalid property format - " + param);
		String value = (split.length < 2) ? "" : split[1];
		ams.props.setProperty(split[0], value);
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleRemoveAms(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageRemoveAms();;
			return;
		}
		String label = params.get(0);
		if(label == null || label.isEmpty()) {
			displayUsageRemoveAms();
			return;
		}

		String msg = "Are you sure you want to remove AMS " + label + "? Please confirm with [yes, Yes or Y].";
		boolean confirmed = Utils.confirmYesByUser(msg);
		if(!confirmed) return;

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			connector.delFromControlApps("amss/" + label);
		} catch (Exception e) {
			System.out.println("Could not remove ams " + label + " because " + e.getMessage());
		}
		System.out.println("Initiating removal of ams " + label);
	}

	public static void handleGetAmsCount() {
		List<AMS> amss;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<AMS>>(){};
			amss = connector.getFromControlApps("amss", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get amss because " + e.getMessage());
			return;
		}
		if(amss == null || amss.isEmpty()) {
			System.out.println("0");
			return;
		}

		System.out.println("Ams counts:" + amss.size());
		System.out.println("=================================");
		
	}

	public static void displayUsageRemoveAms() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps removeams ams_label\n");
		sb.append("   Description - removes the AMS corresponding to the ams_label.");
		System.out.println(sb.toString());
		
	}

}
