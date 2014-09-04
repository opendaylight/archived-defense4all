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
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.type.TypeReference;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.framework.core.Utils;
/**
 * 
 * 
 * @author snirc
 *
 */
public class CliOfc {

	//TODO complete....
	public static final String explanation = "OFC – Open Flow Controller, "
			+ "is a SDN (Software Defined Network) Controller that communicates with SDN enabled "
			+ "network switches and routers via the Open Flow protocol. "
			+ "This software component presents a “north-bound” API allowing network applications, "
			+ "like DF, to program the underlying network equipment to monitor, divert, block or alter "
			+ "selected traffic packets.";


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetOfc() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getofc ofc_label\n");
		sb.append("   Description - returns the OFC corresponding to specified ofc_label.\n");
		System.out.println(sb.toString());
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetOfcs() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getofcs\n");
		sb.append("   Description - returns the OFCs known to DF.\n");
		System.out.println(sb.toString());
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageAddOfc() {

		String displayUsage = "Usage:  controlapps addofc param1 param2 ... \n"
				+"   Description - adds the ofc described through the params.\n"
				+"   A params is formed -- field_name=field_value.\n"
				+"   A composite field is formed -- composite_field::sub_composite_field=sub_composite_field_value.\n"
				+"   I field in composite list element -- list_name::list_element_label::composite_field::sub_composite_field=value.\n"
				+"   The params are\n"
				+"       hostname -           [mandatory] user provided unique textual host name (starts with a letter) to this ofc.\n"
				+"       mgmtAddr -           [mandatory if hostname can't be resolved] Management IP Address.\n"
				+"       mgmtPort -           [mandatory] Management port.\n"
				+"       mgmtUsername -       [mandatory] Management username.\n"
				+"       mgmtPassword -       [mandatory] Management password.\n"
				+"       forStatsCollection - is for stats sollection (true/false default=false).\n"
				+"       forDiversion -       is for diversion (true/false default=false).\n"
				+"       statsCollectionInterval - [optinal] StatsCollection interval in seconds.\n"
				+"       props - 	          {}.\n"	
				+"Example: controlapps addofc hostname=odl_controller mgmtPort=8080 mgmtUsername=admin mgmtPassword=admin forStatsCollection=true forDiversion=true statsCollectionInterval=120\n";
		System.out.println(displayUsage);
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetOfcs() {

		List<OFC> ofcs;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<OFC>>(){};
			ofcs = connector.getFromControlApps("ofcs", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get ofcs because " + e.getMessage());
			return;
		}
		if(ofcs == null || ofcs.isEmpty()) {
			System.out.println("DF has no ofcs configured.");
			return;
		}

		System.out.println("ofcs:\n");
		for(OFC ofc : ofcs) {
			System.out.println(ofc.toString());
			System.out.println("=================================");
		}
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetOfc(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageGetOfc();
			return;
		}
		String ofcHostName = params.get(0);
		if(ofcHostName == null || ofcHostName.isEmpty()) {
			displayUsageGetOfc();
			return;
		}

		OFC ofc;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<OFC>(){};
			ofc = connector.getFromControlApps("ofcs/" + ofcHostName, typeRef);
			String printOut = (ofc == null) ? "No ofc " + ofcHostName + " is known to DF.\n" : ofc.toString();
			System.out.println(printOut);
		} catch (Exception e) {
			System.out.println("Could not get ofc " + ofcHostName + " because " + e.getMessage());
			return;
		}		
		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleAddOfc(ArrayList<String> params) {

		if(params == null) {
			displayUsageAddOfc();
			return;
		}

		OFC ofc = new OFC();
		ofc.props = new Properties();

		try {
			for(String param : params) {

				if(param.startsWith("hostname"))
					addHostname(ofc, param);
				else if(param.startsWith("mgmtAddr"))
					addIpAddrString(ofc, param);
				else if(param.startsWith("mgmtPort"))
					addPort(ofc, param);
				else if(param.startsWith("mgmtUsername"))
					addUsername(ofc, param);
				else if(param.startsWith("mgmtPassword"))
					addPasswd(ofc, param);			
				else if(param.startsWith("forStatsCollection"))
					addForStatsCollection(ofc, param);	
				else if(param.startsWith("forDiversion"))
					addForDiversion(ofc, param);
				else if(param.startsWith("statsCollectionInterval"))
					addStatsCollectionInterval(ofc, param);	
				else if(param.startsWith("props"))
					addProp(ofc, param);
			}
			ofc.toJacksonFriendly(); // Jackson does not handle lists with complex items. Let it inflate strings 
										 // of those serialized lists, and inflate lists in "set" methods.
			ofc.validate();

		} catch (Throwable e1) {
			System.out.println(e1.getMessage());
			displayUsageAddOfc();
			return;
		}

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);	
			System.out.println("Adding ofc " + ofc.hostname);
			connector.postToControlApps("ofcs", ofc);
		} catch (Exception e) {
			System.out.println("Could not add ofc because " + e.getMessage());
		}
	}

	protected static void addHostname(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.hostname = split[1]; // Split "label=l1"
	}

	protected static void addIpAddrString(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.ipAddrString = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
	}

	protected static void addPort(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.port = Integer.valueOf(split[1]); // Split "type=OF"
	}

	protected static void addUsername(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.username = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
	}

	protected static void addPasswd(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.password = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
	}

	protected static void addForStatsCollection(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.forStatsCollection = Boolean.valueOf(split[1]);
	}

	protected static void addProp(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::"); // Split "props::example_prop=example_prop_value"
		split = split[1].split("="); // Can be [example_prop] or [example_prop, example_prop_value]
		if(split[0].isEmpty())
			throw new Exception("Invalid property format - " + param);
		String value = (split.length < 2) ? "" : split[1];
		ofc.props.setProperty(split[0], value);
	}

	protected static void addForDiversion(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.forDiversion = Boolean.valueOf(split[1]);  
	}
	
	protected static void addStatsCollectionInterval(OFC ofc, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		ofc.ofcStatsCollectionInterval = Integer.valueOf(split[1]);  
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleRemoveOfc(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageRemoveOfc();;
			return;
		}
		String ofcHostName = params.get(0);
		if(ofcHostName == null || ofcHostName.isEmpty()) {
			displayUsageRemoveOfc();
			return;
		}

		String msg = "Are you sure you want to remove Ofc " + ofcHostName + "? Please confirm with [yes, Yes or Y].";
		boolean confirmed = Utils.confirmYesByUser(msg);
		if(!confirmed) return;

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			connector.delFromControlApps("ofcs/" + ofcHostName);
		} catch (Exception e) {
			System.out.println("Could not remove Ofc " + ofcHostName + " because " + e.getMessage());
		}
		System.out.println("Initiating removal of ofc " + ofcHostName);
	}

	public static void displayUsageRemoveOfc() {
		StringBuilder sb = new StringBuilder();
		sb.append("Usage: controlapps removeofc ofc_hostname\n");
		sb.append("   Description - removes the ofc corresponding to the ofc_hostname.");
		System.out.println(sb.toString());
		
	}
}
