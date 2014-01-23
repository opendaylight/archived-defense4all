package org.opendaylight.defense4all.core;

public class AMSConnection {

	public String amsLabel;
	public String netNodeNorthPort; // port in the node - closer to client
	public String netNodeSouthPort; // port in the node - closer to server
	public int amsNorthPort; 	 // port in the AMS device - connected to netNodeNorthPort
	public int amsSouthPort;	 // port in the AMS device - connected to netNodeSouthPort

	public int getAmsNorthPort() {return amsNorthPort;}
	public void setAmsNorthPort(int amsNorthPort) {this.amsNorthPort = amsNorthPort;}

	public String getAmsLabel() {return amsLabel;}
	public void setAmsLabel(String amsLabel) {this.amsLabel = amsLabel;}

	public String getNetNodeNorthPort() {return netNodeNorthPort;}
	public void setNetNodeNorthPort(String netNodeNorthPort) {this.netNodeNorthPort = netNodeNorthPort;}

	public int getAmsSouthPort() {return amsSouthPort;}
	public void setAmsSouthPort(int amsSouthPort) {this.amsSouthPort = amsSouthPort;}

	public String getNetNodeSouthPort() {return netNodeSouthPort;}
	public void setNetNodeSouthPort(String netNodeSouthPort) {this.netNodeSouthPort = netNodeSouthPort;}

	public AMSConnection() {
		amsLabel = null; netNodeNorthPort = "0"; netNodeSouthPort = "0"; amsNorthPort = 0; amsSouthPort = 0;
	}

	public AMSConnection(String amsLabel,String netNodeNorthPort,String netNodeSouthPort,int amsNorthPort,int amsSouthPort) {
		this.amsLabel = amsLabel; this.netNodeNorthPort = netNodeNorthPort; this.netNodeSouthPort = netNodeSouthPort;
		this.amsNorthPort = amsNorthPort; this.amsSouthPort = amsSouthPort;
	}

	public AMSConnection(AMSConnection other) {
		this.amsLabel = other.amsLabel; this.netNodeNorthPort = other.netNodeNorthPort; this.netNodeSouthPort = other.netNodeSouthPort;
		this.amsNorthPort = other.amsNorthPort; this.amsSouthPort = other.amsSouthPort;
	}

	public AMSConnection(String s) throws IllegalArgumentException {

		String[] split = s.split(NetNode.ITEMS_DELIMITER);
		if(split == null || split.length < 3) {
			NetNode.log.error("Invalid param s " + s + ".");
			throw new IllegalArgumentException("Invalid param s " + s + ".");
		}
		amsLabel = split[0];
		try {
			netNodeNorthPort = split[1];
			netNodeSouthPort = split[2];
			amsNorthPort = Integer.valueOf(split[3]);
			amsSouthPort = Integer.valueOf(split[4]);
		} catch (NumberFormatException e) {
			NetNode.log.error("Invalid param s " + s + "." + e.getLocalizedMessage());
			throw new IllegalArgumentException("Invalid param s " + s + "." + e.getLocalizedMessage());
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(amsLabel);  
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(netNodeNorthPort); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(netNodeSouthPort);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsNorthPort); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsSouthPort);
		return sb.toString();
	}

	public void validate() throws Exception {
		if(netNodeNorthPort.equals("0") || netNodeSouthPort.equals("0") || netNodeNorthPort == netNodeSouthPort )
			throw new Exception("Invalid netnode north/south ports - cannot be 0 and north cannot be equal to south.");
		if(amsNorthPort == 0 || amsSouthPort == 0 || amsNorthPort == amsSouthPort)
			throw new Exception("Invalid ams north/south ports - cannot be 0 and north cannot be equal to south.");
	}
}