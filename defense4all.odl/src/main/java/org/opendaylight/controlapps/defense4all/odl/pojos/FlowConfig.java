/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

import java.util.List;

public class FlowConfig {
	
	public static final Short IP_ETHER_TYPE = 0x800;

	public String name;			
	public String cookie; // Long - number
	public Node   node;			
	public short  ingressPort;		
	public short  vlanId;
	public short  priority; 
	public short  etherType;	
	public short  protocol; // 6=tcp, 17=udp, 1=icmp (0 = unspecified for rest of traffic)
	public String dlSrc;		public String dlDst;
	public String nwSrc;		public String nwDst;
	public String tpSrc;		public String tpDst;
	public short  idleTimeout;	public short  hardTimeout;
	public List<String> actions; // Serialized by FlowConfigActionType

    public FlowConfig() {}    

    public String getName() {return name;}
	public void setName(String name) {this.name = name;}

	public String getCookie() {return cookie;}
	public void setCookie(String cookie) {this.cookie = cookie;}

	public Node getNode() {return node;}
	public void setNode(Node node) {this.node = node;}

	public short getIngressPort() {return ingressPort;}
	public void setIngressPort(short ingressPort) {this.ingressPort = ingressPort;}

	public short getVlanId() {return vlanId;}
	public void setVlanId(short vlanId) {this.vlanId = vlanId;}

	public short getPriority() {return priority;}
	public void setPriority(short priority) {this.priority = priority;}

	public short getEtherType() {return etherType;}
	public void setEtherType(short etherType) {this.etherType = etherType;}

	public short getProtocol() {return protocol;}
	public void setProtocol(short protocol) {this.protocol = protocol;}

	public String getDlSrc() {return dlSrc;}
	public void setDlSrc(String dlSrc) {this.dlSrc = dlSrc;}

	public String getDlDst() {return dlDst;}
	public void setDlDst(String dlDst) {this.dlDst = dlDst;}

	public String getNwSrc() {return nwSrc;}
	public void setNwSrc(String nwSrc) {this.nwSrc = nwSrc;}

	public String getNwDst() {return nwDst;}
	public void setNwDst(String nwDst) {this.nwDst = nwDst;}

	public String getTpSrc() {return tpSrc;}
	public void setTpSrc(String tpSrc) {this.tpSrc = tpSrc;}

	public String getTpDst() {return tpDst;}
	public void setTpDst(String tpDst) {this.tpDst = tpDst;}

	public short getIdleTimeout() {return idleTimeout;}
	public void setIdleTimeout(short idleTimeout) {this.idleTimeout = idleTimeout;}

	public short getHardTimeout() {return hardTimeout;}
	public void setHardTimeout(short hardTimeout) {this.hardTimeout = hardTimeout;}

	public List<String> getActions() {return actions;}
	public void setActions(List<String> actions) {this.actions = actions;}

	@Override
    public String toString() {
        return "FlowConfig [name=" + name + ", cookie=" + cookie + ", node=" + node
        		+ ", ingressPort=" + ingressPort + ", vlanId=" + vlanId + ", priority=" + priority
        		+ ", etherType=" + etherType + ", protocol=" + protocol
        		+ ", dlSrc=" + dlSrc + ", dlDst=" + dlDst + ", nwSrc=" + nwSrc + ", nwDst=" + nwDst
        		+ ", tpSrc=" + tpSrc + ", tpDst=" + tpDst 
        		+ ", idleTimeout=" + idleTimeout + ", hardTimeout=" + hardTimeout + ", actions="
                + actions + "]";
    }
}
