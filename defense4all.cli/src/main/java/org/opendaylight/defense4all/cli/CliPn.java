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
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PO.IpVersion;
import org.opendaylight.defense4all.core.PN.MitigationScope;
import org.opendaylight.defense4all.core.PN.OperationalStatus;
import org.opendaylight.defense4all.core.ProtectionSLA;
import org.opendaylight.defense4all.framework.core.Utils;

public class CliPn {

	//TODO complete....
	public static final String explanation = "PN – Protected Network, "
			+ "is a specified network traffic that is protected against DDoS attacks.  "
			+ "Protected traffic is defined by a range of target IP addresses, "
			+ "specified by either CIDR or subnet-mask notations. "
			+ "Attacks against TCP, UDP, ICMP protocols or attacks against "
			+ "all of remaining traffic are mitigated separately. "
			+ "This can mean that only traffic of an attacked protocol of a PN is diverted to AMS "
			+ "(Attack Mitigation Systems). "
			+ "This can also mean that attacked traffic of different protocols, "
			+ "even of the same PN may be diverted to different AMSs. "
			+ "Each PN can have its unique protection properties.";



	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetPn() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage: controlapps getpn pn_label\n");
		sb.append("   Description - returns the pn corresponding to specified pn_label.\n");
		System.out.println(sb.toString());
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetPns() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage: controlapps getpns\n");
		sb.append("   Description - returns the PNs known to DF.\n");
		System.out.println(sb.toString());
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetPnsCount() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getpnscount\n");
		sb.append("   Description - returns the number of PNs known to DF.\n");
		System.out.println(sb.toString());
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageRemovePn() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps removepn pn_label\n");
		sb.append("   Description - removes the PN corresponding to the pn_label.");
		System.out.println(sb.toString());
	}



	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageAddPn() {

	
		String displayUsage = "Usage:  controlapps addpn param1 param2 ... \n"
				+"   Description - adds the pn described through the params.\n"
				+"   A params is formed -- field_name=field_value.\n"
				+"   A composite field is formed -- composite_field::sub_composite_field=sub_composite_field_value.\n"
				+"   A field in composite list element -- list_name::list_element_label::composite_field::sub_composite_field=value.\n"
				+"   The params are\n"
				+"       label -                      [mandatory] user provided unique textual name (starts with a letter) to this pn.\n"
				+"       ipVersion -                  [mandatory] ip version (IPV4, IPV6).\n"
				+"       dstAddr - 	                  [mandatory] traffic destination adderess.\n"
				+"       dstAddrPrefixLen -           traffic destination adderess prefix len.(default=0)\n"
				+"       protectionSLA -              protection SLA.\n"
				+"       mitigationConfirmation -     is mitigation confirmation(true/false default=false).\n"
				+"       ofBasedDetection -           is rate based detection (true/false default=false).\n"
				+"       anomalyThresholdPercentage - anomaly threshold percentage.\n"
				+"       mitigationScope - 	          [mandatory] mitigation scop. (ATTACKED, ALL)\n"
				+"       detectorLabel -              [mandatory] label of relevant detector\n"
				+"       symmetricDvsn -              is attack diversion symmetric. (true/false default=true)\n"
				+"       amsConfigProps -             {}.\n"
				+"       netNodeLabel -               [list] of relevant NetNodes\n"
				+"       virtualNetid -               virtual network Id\n"
				+"       props - 	                  {}.\n"	
				+"Example: controlapps addpn label=pn1 ipVersion=IPV4 dstAddr=100.1.1.10 dstAddrPrefixLen=32 "
				+ "mitigationScope=ATTACKED detectorLabel=of_rate_based_detector mitigationConfirmation=false ofBasedDetection=true "
				+ "anomalyThresholdPercentage=80 virtualNetid=1 symmetricDvsn=true netNodeLabel=ovs\n";
		System.out.println(displayUsage);
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetPns() {

		List<PN> pns;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<PN>>(){};
			pns = connector.getFromControlApps("pns", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get pns because " + e.getMessage());
			return;
		}
		if(pns == null || pns.isEmpty()) {
			System.out.println("DF has no pns configured.");
			return;
		}

		System.out.println("pns:\n");
		for(PN pn : pns) {
			if ( pn.operationalStatus == OperationalStatus.CANCELED )
				continue;
			System.out.println(pn.toString());
			System.out.println("=================================");
		}
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetPns(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageGetPn();
			return;
		}
		String label = params.get(0);
		if(label == null || label.isEmpty()) {
			displayUsageGetPn();
			return;
		}

		PN pn;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<PN>(){};
			pn = connector.getFromControlApps("pns/" + label, typeRef);
			String printOut = (pn == null) ? "No pn " + label + " is known to DF.\n" : pn.toString();
			System.out.println(printOut);
		} catch (Exception e) {
			System.out.println("Could not get pn " + label + " because " + e.getMessage());
			return;
		}		
		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleAddPn(ArrayList<String> params) {

		if(params == null) {
			displayUsageAddPn();
			return;
		}

		PN pn = new PN();
		pn.props = new Properties();
		pn.amsConfigProps = new Properties();
		pn.protectionSLA = new ProtectionSLA("");

		try {
			for(String param : params) {

				if(param.startsWith("label"))
					addLabel(pn, param);
				else if(param.startsWith("ipVersion"))
					addIpVersion(pn, param);
				else if(param.startsWith("dstAddr") && ! param.startsWith("dstAddrPrefixLen") )
					addDstAddr(pn, param);
				else if(param.startsWith("dstAddrPrefixLen"))
					addDstAddrPrefixLen(pn, param);
				else if(param.startsWith("protectionSLA"))
					addProtectionSLA(pn, param);
				else if(param.startsWith("mitigationConfirmation"))
					addMitigationConfirmation(pn, param);
				else if(param.startsWith("ofBasedDetection"))
					addOfBasedDetection(pn, param);
				else if(param.startsWith("anomalyThresholdPercentage"))
					addAnomalyThresholdPercentage(pn, param);
				else if(param.startsWith("mitigationScope"))
					addMitigationScope(pn, param);
				else if(param.startsWith("detectorLabel"))
					addDetectorLabel(pn, param);
				else if(param.startsWith("virtualNetid"))
					addVirtualNetid(pn, param);
				else if(param.startsWith("symmetricDvsn"))
					addSymmetricDvsn(pn, param);
				else if(param.startsWith("amsConfigProps"))
					addAmsConfigProps(pn, param);
				else if(param.startsWith("netNodeLabel"))
					addNetNodeLabels(pn, param);
				else if(param.startsWith("props"))
					addProp(pn, param);			
			}
			pn.toJacksonFriendly(); // Jackson does not handle lists with complex items. Let it inflate strings 
										 // of those serialized lists, and inflate lists in "set" methods.
			pn.validate();

		} catch (Throwable e1) {
			System.out.println(e1.getMessage());
			displayUsageAddPn();
			return;
		}

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);	
			System.out.println("Adding pn " + pn.label);
			connector.postToControlApps("pns", pn);
		} catch (Exception e) {
			System.out.println("Could not add pn because " + e.getMessage());
		}
	}

	protected static void addLabel(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.label = split[1]; // Split "label=l1"
	}

	protected static boolean addIpVersion(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.ipVersion = IpVersion.valueOf(split[1]); // Split "id=00:00:00:50:56:a3:1b:80"
		return true;
	}

	protected static void addDstAddr(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.dstAddr = InetAddress.getByName(split[1]);  
	}
	protected static void addDstAddrPrefixLen(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.dstAddrPrefixLen = new Integer(split[1]);  
	}
	protected static void addAnomalyThresholdPercentage(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.anomalyThresholdPercentage = new Integer(split[1]);  
	}

	protected static void addProtectionSLA(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.protectionSLA = new ProtectionSLA(split[1]);  
	}
	
	protected static void addMitigationConfirmation(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.mitigationConfirmation = new Boolean(split[1]);  
	}
	
	protected static void addMitigationScope(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.mitigationScope = MitigationScope.valueOf(split[1]);
	}
	
	protected static void addOfBasedDetection(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.ofBasedDetection = new Boolean(split[1]);  
	}
	
	protected static void addProp(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::"); // Split "props::example_prop=example_prop_value"
		split = split[1].split("="); // Can be [example_prop] or [example_prop, example_prop_value]
		if(split[0].isEmpty())
			throw new Exception("Invalid property format - " + param);
		String value = (split.length < 2) ? "" : split[1];
		pn.props.setProperty(split[0], value);
	}
	
	protected static void addAmsConfigProps(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::"); // Split "props::example_prop=example_prop_value"
		split = split[1].split("="); // Can be [example_prop] or [example_prop, example_prop_value]
		if(split[0].isEmpty())
			throw new Exception("Invalid property format - " + param);
		String value = (split.length < 2) ? "" : split[1];
		pn.amsConfigProps.setProperty(split[0], value);
	}
	
	protected static void addDetectorLabel(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.detectorLabel = split[1]; // Split "label=l1"
	}
		
	protected static void 	addVirtualNetid(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.virtualNetid = split[1]; // Split "label=l1"
	}
	
	protected static void 	addSymmetricDvsn(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		pn.symmetricDvsn = new Boolean(split[1]);  
	}

	protected static void 	addNetNodeLabels(PN pn, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");	
		pn.netNodeLabels.add(split[1]);
	}


	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleRemovePn(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageRemovePn();
			return;
		}
		String label = params.get(0);
		if(label == null || label.isEmpty()) {
			displayUsageRemovePn();
			return;
		}

		String msg = "Are you sure you want to remove pn " + label + "? Please confirm with [yes, Yes or Y].";
		boolean confirmed = Utils.confirmYesByUser(msg);
		if(!confirmed) return;

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			connector.delFromControlApps("pns/" + label);
		} catch (Exception e) {
			System.out.println("Could not remove pn " + label + " because " + e.getMessage());
		}
		System.out.println("Initiating removal of pn " + label);
	}

	public static void handleGetPnsCount() {
		Integer count = 0;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);			
			TypeReference<?> typeRef = new TypeReference<Integer>(){};
			count = connector.getFromControlApps("pns/count", typeRef);
			
		} catch (Exception e) {
			System.out.println("Could not get pns because " + e.getMessage());
			return;
		}
		
		System.out.println("There are " + count + " PNs known to DF.\n");
	}

}
