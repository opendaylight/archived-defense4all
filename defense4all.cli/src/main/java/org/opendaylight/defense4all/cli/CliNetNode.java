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
import org.opendaylight.defense4all.core.AMSConnection;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.core.ProtectedLink;
import org.opendaylight.defense4all.core.TrafficPort;
import org.opendaylight.defense4all.core.TrafficPort.PortLocation;
import org.opendaylight.defense4all.framework.core.Utils;

public class CliNetNode {

	public static final String netnodeExplanation = "Netnode is an abstraction of a network location on which traffic "
			+ "counters can be placed, and from which traffic is diverted (and in case of local diversion, returned) "
			+ "to mitigation devices. Depending on the level of network abstraction a netnode can be a single switch, "
			+ "an entry point to a logical network, or the complete logical network (including all entry points).";

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetNetnodes() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getnetnodes\n");
		sb.append("   Description - returns the netnodes known to DF.\n");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetNetnodesCount() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getnetnodescount\n");
		sb.append("   Description - returns the number of netnodes known to DF.\n");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageGetNetnode() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps getnetnode netnode_label\n");
		sb.append("   Description - returns the netnode corresponding to specified netnode_label.\n");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageAddNetnode() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps addnetnode param1 param2 ... \n");
		sb.append("   Description - adds the netnode described through the params.\n");
		sb.append("   A params is formed -- field_name=field_value.\n");
		sb.append("   A composite field is formed -- composite_field::sub_composite_field=sub_composite_field_value.\n");
		sb.append("   I field in composite list element -- list_name::list_element_label::composite_field::sub_composite_field=value.\n");
		sb.append("   The params are\n");
		sb.append("       label - [mandatory] user provided unique textual label (starts with a letter) to this netnode.\n");
		sb.append("       id - For OpenFlow NetNodes this is the DPID. For other netnodes left empty.\n");
		sb.append("       type - NetNode type, currently only \"OF\" (for openflow), which is also the default if none provided.\n");
		sb.append("       mgmtaddr - management address of the netnode. Currently unused.\n");
		sb.append("       mgmtport - management port of the netnode. Currently unused.\n");
		sb.append("       sdnnodemode - can be sdnenablednative [default] or sdnenabledhybrid.\n");
		sb.append("       props - other custom properties. Each in the form:\n");
		sb.append("           props::prop_name or props::prop_name=prop_value.\n");
		sb.append("       ams connections - netnode connections to amss. Can be zero or more. Each connection is described through its fields, as follows:\n");
		sb.append("           amsconnection::amslabel=amslabel_value - [mandatory] label of the connected ams.\n");
		sb.append("           amsconnection::amslabel::netnodenorthport=port_value - [mandatory] number of the north (closer to client) port in the netnode connected to the ams.\n");
		sb.append("           amsconnection::amslabel::netnodesouthport=port_value - [mandatory] number of the south (closer to server) port in the netnode connected to the ams.\n");
		sb.append("           amsconnection::amslabel::amsnorthport=port_value - [mandatory] number of the north port in the ams connected to the north port in the netnode.\n");
		sb.append("           amsconnection::amslabel::amssouthport=port_value - [mandatory] number of the south port in the ams connected to the south port in the netnode.\n");
		sb.append("       traffic ports - ports through which traffic flows north-south and south-north. Must have at least one north-south port. Each traffic port is described through its fields, as follows:\n");
		sb.append("           trafficport::label=label_value - [mandatory] label of this traffic port.\n");
		sb.append("           trafficport::label::number=number_value - [mandatory] number of the traffic port.\n");
		sb.append("           trafficport::label::vlan=vlan_value - [mandatory] vlan to consider in this port.\n");
		sb.append("           trafficport::label::location=location_value - [mandatory] Can be \"north\" (closer to client) or \"south\" (closer to server).\n");
		sb.append("       protected links - links that should be considered for traffic protection. Must have at least one in sdnenablednative netnodes. Each protected link is described through its fields, as follows:\n");
		sb.append("           protectedlink::label=label_value - [mandatory] label of this protected link.\n");
		sb.append("           protectedlink::label::northport=port_value - [mandatory] number of the north traffic port.\n");
		sb.append("           protectedlink::label::southport=port_value - [mandatory] number of the south traffic port.\n");
		sb.append("Example: controlapps addnetnode label=ovs id=00:00:00:50:56:a3:1b:80 type=OF \n");
		sb.append("           mgmtaddr=10.10.10.10 mgmtport=0 sdnnodemode=sdnenablednative \n");
		sb.append("           props::example_prop=example_prop_value amsconnection::amslabel=ams1 \n");
		sb.append("           amsconnection::ams1::netnodenorthport=2 amsconnection::ams1::netnodesouthport=3 \n");
		sb.append("           amsconnection::ams1::amsnorthport=5 amsconnection::ams1::amssouthport=6 \n");
		sb.append("           trafficport::label=tp1 trafficport::tp1::number=1 trafficport::tp1::vlan=0 trafficport::tp1::location=north \n");
		sb.append("           trafficport::label=tp4 trafficport::tp4::number=4 trafficport::tp4::vlan=0 trafficport::tp4::location=south \n");		
		sb.append("           protectedlink::label=pl1 \n");
		sb.append("           protectedlink::pl1::northport=1 protectedlink::pl1::southport=4");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void displayUsageRemoveNetnode() {

		StringBuilder sb = new StringBuilder();
		sb.append("Usage:  controlapps removenetnode netnode_label\n");
		sb.append("   Description - removes the netnode corresponding to the netnode_label.");
		System.out.println(sb.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetNetNodes() {

		List<NetNode> netNodes;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<List<NetNode>>(){};
			netNodes = connector.getFromControlApps("netnodes", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get netnodes because " + e.getMessage());
			return;
		}
		if(netNodes == null || netNodes.isEmpty()) {
			System.out.println("DF has no netnodes configured.");
			return;
		}

		System.out.println("netnodes:\n");
		for(NetNode netnode : netNodes) {
			System.out.println(netnode.toString());
			System.out.println("=================================");
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetNetNodesCount() {

		Integer count = 0;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<Integer>(){};
			count = connector.getFromControlApps("netnodes/count", typeRef);
		} catch (Exception e) {
			System.out.println("Could not get netnodes count because " + e.getMessage());
			return;
		}

		System.out.println("There are " + count + " netNodes known to DF.\n");
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleGetNetNode(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageGetNetnode();
			return;
		}
		String netNodeLabel = params.get(0);
		if(netNodeLabel == null || netNodeLabel.isEmpty()) {
			displayUsageGetNetnode();
			return;
		}

		NetNode netNode;
		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			TypeReference<?> typeRef = new TypeReference<NetNode>(){};
			netNode = connector.getFromControlApps("netnodes/" + netNodeLabel, typeRef);
			String printOut = (netNode == null) ? "No netNode " + netNodeLabel + " is known to DF.\n" : netNode.toString();
			System.out.println(printOut);
		} catch (Exception e) {
			System.out.println("Could not get netnode " + netNodeLabel + " because " + e.getMessage());
			return;
		}		
		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleAddNetNode(ArrayList<String> params) {

		if(params == null) {
			displayUsageRemoveNetnode();
			return;
		}

		NetNode netNode = new NetNode();
		netNode.props = new Properties();

		try {
			for(String param : params) {

				if(param.startsWith("label"))
					addLabel(netNode, param);
				else if(param.startsWith("id"))
					addId(netNode, param);
				else if(param.startsWith("type"))
					addType(netNode, param);
				else if(param.startsWith("mgmtaddr"))
					addMgmtAddr(netNode, param);
				else if(param.startsWith("mgmtport"))
					addMgmtPort(netNode, param);
				else if(param.startsWith("sdnnodemode"))
					addSDNNodeMode(netNode, param);
				else if(param.startsWith("props"))
					addProp(netNode, param);
				else if(param.startsWith("amsconnection"))
					addAmsConnectionField(netNode, param);
				else if(param.startsWith("trafficport"))
					addTrafficPortField(netNode, param);
				else if(param.startsWith("protectedlink"))
					addProtectedLinkField(netNode, param);
			}
			netNode.toJacksonFriendly(); // Jackson does not handle lists with complex items. Let it inflate strings 
										 // of those serialized lists, and inflate lists in "set" methods.
			netNode.validate();

		} catch (Throwable e1) {
			System.out.println(e1.getMessage());
			displayUsageAddNetnode();
			return;
		}

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);			
			connector.postToControlApps("netnodes", netNode);
		} catch (Exception e) {
			System.out.println("Could not add netnode because " + e.getMessage());
		}
		System.out.println("Adding netnode " + netNode.label);
	}

	protected static void addLabel(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		netNode.label = split[1]; // Split "label=l1"
	}

	protected static boolean addId(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		netNode.id = split[1]; // Split "id=00:00:00:50:56:a3:1b:80"
		return true;
	}

	protected static void addType(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		netNode.type = split[1]; // Split "type=OF"
	}

	protected static void addMgmtAddr(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		netNode.mgmtAddr = split[1]; // Split "mgmtaddr=10.10.10.10"
	}

	protected static void addMgmtPort(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		netNode.mgmtPort = Integer.valueOf(split[1]); // Split "mgmtport=0"
	}

	protected static void addSDNNodeMode(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "=");
		netNode.sdnNodeMode = SDNNodeMode.valueOf(split[1]); // Split "sdnnodemode=sdnenablednative"
	}

	protected static void addProp(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::"); // Split "props::example_prop=example_prop_value"
		split = split[1].split("="); // Can be [example_prop] or [example_prop, example_prop_value]
		if(split[0].isEmpty())
			throw new Exception("Invalid property format - " + param);
		String value = (split.length < 2) ? "" : split[1];
		netNode.props.setProperty(split[0], value);
	}

	protected static void addAmsConnectionField(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::");
		if(split[1].startsWith("amslabel"))
			addAmsConnectionAmsLabel(netNode, split[1]);
		else if(split.length < 3 || split[1] == null || split[1].isEmpty())			
			throw new Exception("Invalid param " + param); // Other fields: amsconnection::label::field_name=field_value
		else if(split[2].startsWith("netnodenorthport"))
			addAmsConnectionNetnodeNorthPort(netNode, split[1], split[2]);
		else if(split[2].startsWith("netnodesouthport"))
			addAmsConnectionNetnodeSouthPort(netNode, split[1], split[2]);
		else if(split[2].startsWith("amsnorthport"))
			addAmsConnectionAmsNorthPort(netNode, split[1], split[2]);
		else if(split[2].startsWith("amssouthport"))
			addAmsConnectionAmsSouthPort(netNode, split[1], split[2]);
	}

	private static void addAmsConnectionAmsLabel(NetNode netNode, String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		getCreateAmsConnection(netNode, split[1]);	// Create if non existent
	}

	private static AMSConnection getCreateAmsConnection(NetNode netNode, String label) throws Exception {		
		AMSConnection amsConnection = netNode.amsConnections.get(label);
		if(amsConnection == null) { // First field in this amsConnection
			amsConnection = new AMSConnection();
			amsConnection.amsLabel = label;
			netNode.amsConnections.put(label, amsConnection);
		}
		return amsConnection;
	}

	private static void addAmsConnectionNetnodeNorthPort(NetNode netNode,String label,String nameValStr) throws Exception {

		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		int port = Integer.valueOf(split[1]);
		if(port == 0) throw new Exception("Invalid netnodenorthport (zero) for amsconnection " + label);
		AMSConnection amsConnection = getCreateAmsConnection(netNode, label);
		amsConnection.netNodeNorthPort = port;
	}

	private static void addAmsConnectionNetnodeSouthPort(NetNode netNode,String label,String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		int port = Integer.valueOf(split[1]);
		if(port == 0) throw new Exception("Invalid netnodesouthport (zero) for amsconnection " + label);
		AMSConnection amsConnection = getCreateAmsConnection(netNode, label);
		amsConnection.netNodeSouthPort = port;
	}

	private static void addAmsConnectionAmsNorthPort(NetNode netNode, String label, String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		int port = Integer.valueOf(split[1]);
		if(port == 0) throw new Exception("Invalid amsnorthport (zero) for amsconnection " + label);
		AMSConnection amsConnection = getCreateAmsConnection(netNode, label);
		amsConnection.amsNorthPort = port;
	}

	private static void addAmsConnectionAmsSouthPort(NetNode netNode, String label, String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		int port = Integer.valueOf(split[1]);
		if(port == 0)
			throw new Exception("Invalid amssouthport (zero) for amsconnection " + label);
		AMSConnection amsConnection = getCreateAmsConnection(netNode, label);
		amsConnection.amsSouthPort = port;
	}

	protected static void addTrafficPortField(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::");
		if(split[1].startsWith("label"))
			addTrafficPortLabel(netNode, split[1]);
		else if(split.length < 3 || split[1] == null || split[1].isEmpty())			
			throw new Exception("Invalid param " + param); // Other fields: trafficport::label::field_name=field_value
		else if(split[2].startsWith("number"))
			addTrafficPortNumber(netNode, split[1], split[2]);
		else if(split[2].startsWith("vlan"))
			addTrafficPortVlan(netNode, split[1], split[2]);
		else if(split[2].startsWith("location"))
			addTrafficPortLocation(netNode, split[1], split[2]);
	}

	private static void addTrafficPortLabel(NetNode netNode, String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		getCreateTrafficPort(netNode, split[1]);	// Create if non existent
	}

	private static TrafficPort getCreateTrafficPort(NetNode netNode, String label) throws Exception {		
		TrafficPort trafficPort = netNode.trafficPorts.get(label);
		if(trafficPort == null) { // First field in this trafficPort
			trafficPort = new TrafficPort();
			trafficPort.label = label;
			netNode.trafficPorts.put(label, trafficPort);
		}
		return trafficPort;
	}

	private static void addTrafficPortNumber(NetNode netNode, String label, String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		short number = Short.valueOf(split[1]);
		if(number == 0) throw new Exception("Invalid number (zero) for trafficport" + label);
		TrafficPort trafficPort = getCreateTrafficPort(netNode, label);
		trafficPort.number = number;
	}

	private static void addTrafficPortVlan(NetNode netNode, String label, String nameValStr) throws Exception {		
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		short vlan = Short.valueOf(split[1]);
		TrafficPort trafficPort = getCreateTrafficPort(netNode, label);
		trafficPort.vlan = vlan;
	}

	private static void addTrafficPortLocation(NetNode netNode, String label, String nameValStr) throws Exception {		
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		PortLocation location = PortLocation.valueOf(split[1]);
		TrafficPort trafficPort = getCreateTrafficPort(netNode, label);
		trafficPort.location = location;
	}

	protected static void addProtectedLinkField(NetNode netNode, String param) throws Exception {
		String[] split = Cli.splitAndAssertSize(param, "::");
		if(split[1].startsWith("label"))
			addProtectedLinkLabel(netNode, split[1]);
		else if(split.length < 3 || split[1] == null || split[1].isEmpty())			
			throw new Exception("Invalid param " + param); // Other fields: protectedlink::label::field_name=field_value
		else if(split[2].startsWith("northport"))
			addProtectedLinkNorthPort(netNode, split[1], split[2]);
		else if(split[2].startsWith("southport"))
			addProtectedLinkSouthPort(netNode, split[1], split[2]);
	}

	private static void addProtectedLinkLabel(NetNode netNode, String nameValStr) throws Exception {
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		getCreateProtectedLink(netNode, split[1]);	// Create if non existent
	}

	private static ProtectedLink getCreateProtectedLink(NetNode netNode, String label) throws Exception {		
		ProtectedLink protectedLink = netNode.protectedLinks.get(label);
		if(protectedLink == null) { // First field in this protectedLink
			protectedLink = new ProtectedLink();
			protectedLink.label = label;
			netNode.protectedLinks.put(label, protectedLink);
		}
		return protectedLink;
	}

	private static void addProtectedLinkNorthPort(NetNode netNode, String label, String nameValStr) throws Exception {		
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		short port = Short.valueOf(split[1]);
		if(port == 0) throw new Exception("Invalid northport (zero) for protectedlink" + label);
		ProtectedLink protectedLink = getCreateProtectedLink(netNode, label);
		protectedLink.northPort = port;
	}

	private static void addProtectedLinkSouthPort(NetNode netNode, String label, String nameValStr) throws Exception {		
		String[] split = Cli.splitAndAssertSize(nameValStr, "=");
		short port = Short.valueOf(split[1]);
		if(port == 0) throw new Exception("Invalid southport (zero) for protectedlink" + label);
		ProtectedLink protectedLink = getCreateProtectedLink(netNode, label);
		protectedLink.southPort = port;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected static void handleRemoveNetNode(ArrayList<String> params) {

		if(params == null || params.isEmpty()) {
			displayUsageRemoveNetnode();
			return;
		}
		String netNodeLabel = params.get(0);
		if(netNodeLabel == null || netNodeLabel.isEmpty()) {
			displayUsageRemoveNetnode();
			return;
		}

		String msg = "Are you sure you want to remove netnode " + netNodeLabel + "? Please confirm with [yes, Yes or Y].";
		boolean confirmed = Utils.confirmYesByUser(msg);
		if(!confirmed) return;

		try {
			Defense4allConnector connector = new Defense4allConnector(Cli.user, Cli.password);
			connector.delFromControlApps("netnodes/" + netNodeLabel);
		} catch (Exception e) {
			System.out.println("Could not remove netnode " + netNodeLabel + " because " + e.getMessage());
		}
		System.out.println("Initiating removal of netnode " + netNodeLabel);
	}
}
