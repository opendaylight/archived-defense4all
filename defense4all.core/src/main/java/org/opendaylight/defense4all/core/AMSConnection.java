package org.opendaylight.defense4all.core;

import org.opendaylight.defense4all.core.DFAppRoot.HealthStatus;

public class AMSConnection {

	public String label;
	public String amsLabel;
	private HealthStatus amsStatus;
	public String netNodeNorthPort; // port in the node - closer to client
	private HealthStatus netNodeNorthPortStatus;
	public String netNodeSouthPort; // port in the node - closer to server
	private HealthStatus netNodeSouthPortStatus;
	public int amsNorthPort; 	 // port in the AMS device - connected to netNodeNorthPort
	private HealthStatus amsNorthPortStatus;
	public int amsSouthPort;	 // port in the AMS device - connected to netNodeSouthPort
	private HealthStatus amsSouthPortStatus;
	private HealthStatus healthStatus;

	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}

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

	public HealthStatus getHealthStatus() {return healthStatus;}

	/* In all set status methods return true if the overall status changes as a result of the set. */
	public boolean setAmsStatus(HealthStatus amsStatus) {
		if(this.amsStatus == amsStatus) return false;
		this.amsStatus = amsStatus;
		return recalcHealthStatus();
	}
	public boolean setNetNodeNorthPortStatus(HealthStatus netNodeNorthPortStatus) {
		if(this.netNodeNorthPortStatus == netNodeNorthPortStatus) return false;
		this.netNodeNorthPortStatus = netNodeNorthPortStatus;
		return recalcHealthStatus();
	}
	public HealthStatus getNetNodeNorthPortStatus() { return netNodeNorthPortStatus; }

	public boolean setNetNodeSouthPortStatus(HealthStatus netNodeSouthPortStatus) {
		if(this.netNodeSouthPortStatus == netNodeSouthPortStatus) return false;
		this.netNodeSouthPortStatus = netNodeSouthPortStatus;
		return recalcHealthStatus();
	}
	public HealthStatus getNetNodeSouthPortStatus()  { return netNodeSouthPortStatus; }

	public boolean setAmsNorthPortStatus(HealthStatus amsNorthPortStatus) {
		if(this.amsNorthPortStatus == amsNorthPortStatus) return false;
		this.amsNorthPortStatus = amsNorthPortStatus;
		return recalcHealthStatus();
	}
	public boolean setAmsSouthPortStatus(HealthStatus amsSouthPortStatus) {
		if(this.amsSouthPortStatus == amsSouthPortStatus) return false;
		this.amsSouthPortStatus = amsSouthPortStatus;
		return recalcHealthStatus();
	}

	public AMSConnection() {
		label = null; amsLabel = null; netNodeNorthPort = "0"; netNodeSouthPort = "0"; amsNorthPort = 0; amsSouthPort = 0; 
		healthStatus = amsStatus = netNodeNorthPortStatus = netNodeSouthPortStatus = 
				amsNorthPortStatus = amsSouthPortStatus = HealthStatus.UP;
	}

	public AMSConnection(String label, String amsLabel, String netNodeNorthPort, String netNodeSouthPort,
			int amsNorthPort,int amsSouthPort) {
		this.label = label; this.amsLabel = amsLabel; 
		this.netNodeNorthPort = netNodeNorthPort; this.netNodeSouthPort = netNodeSouthPort;
		this.amsNorthPort = amsNorthPort; this.amsSouthPort = amsSouthPort;
		healthStatus = amsStatus = netNodeNorthPortStatus = netNodeSouthPortStatus = 
				amsNorthPortStatus = amsSouthPortStatus = HealthStatus.UP;
	}

	public AMSConnection(AMSConnection other) {
		this.label = other.label; this.amsLabel = other.amsLabel; 
		this.netNodeNorthPort = other.netNodeNorthPort; this.netNodeSouthPort = other.netNodeSouthPort;
		this.amsNorthPort = other.amsNorthPort; this.amsSouthPort = other.amsSouthPort; 
		healthStatus = other.healthStatus;
		amsStatus = other.amsStatus;
		netNodeNorthPortStatus = other.netNodeNorthPortStatus;
		netNodeSouthPortStatus = other.netNodeSouthPortStatus;
		amsNorthPortStatus = other.amsNorthPortStatus;
		amsSouthPortStatus = other.amsSouthPortStatus;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(label);  
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsLabel); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(netNodeNorthPort); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(netNodeSouthPort);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsNorthPort); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsSouthPort);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(healthStatus);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsStatus);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(netNodeNorthPortStatus);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(netNodeSouthPortStatus);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsNorthPortStatus);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(amsSouthPortStatus);
		return sb.toString();
	}

	public AMSConnection(String s) throws IllegalArgumentException {

		String[] split = s.split(NetNode.ITEMS_DELIMITER);
		if(split == null || split.length < 6 ) {
			NetNode.log.error("Invalid param s " + s + ".");
			throw new IllegalArgumentException("Invalid param s " + s + ".");
		}
		label = split[0];
		amsLabel = split[1];
		try {
			netNodeNorthPort = split[2];
			netNodeSouthPort = split[3];
			amsNorthPort = Integer.valueOf(split[4]);
			amsSouthPort = Integer.valueOf(split[5]);
			if (  split.length > 6 ) { // String includes statuses from repo
				healthStatus = HealthStatus.valueOf(split[6]);
				amsStatus = HealthStatus.valueOf(split[7]);
				netNodeNorthPortStatus = HealthStatus.valueOf(split[8]);
				netNodeSouthPortStatus = HealthStatus.valueOf(split[9]);
				amsNorthPortStatus = HealthStatus.valueOf(split[10]);
				amsSouthPortStatus = HealthStatus.valueOf(split[11]);
			} else {
				healthStatus = amsStatus = netNodeNorthPortStatus = netNodeSouthPortStatus = 
						amsNorthPortStatus = amsSouthPortStatus = HealthStatus.UP;
			}
		} catch (NumberFormatException e) {
			NetNode.log.error("Invalid param s " + s + "." + e.getLocalizedMessage());
			throw new IllegalArgumentException("Invalid param s " + s + "." + e.getLocalizedMessage());
		}
	}

	protected boolean recalcHealthStatus() {

		HealthStatus oldHealthStatus = healthStatus;
		if(netNodeNorthPortStatus == HealthStatus.UP && netNodeSouthPortStatus == HealthStatus.UP && 
				amsNorthPortStatus == HealthStatus.UP && amsSouthPortStatus == HealthStatus.UP && amsStatus == HealthStatus.UP)
			healthStatus = HealthStatus.UP;
		else
			healthStatus = HealthStatus.DOWN;
		return(healthStatus != oldHealthStatus); // Return true if status changed.
	}

	public void validate() throws Exception {
		if(netNodeNorthPort.isEmpty() || netNodeSouthPort.isEmpty() || netNodeNorthPort.equals(netNodeSouthPort))
			throw new Exception("Invalid netnode north/south ports - cannot be empty and north cannot be equal to south.");
		if(amsNorthPort == 0 || amsSouthPort == 0 || amsNorthPort == amsSouthPort)
			throw new Exception("Invalid ams north/south ports - cannot be 0 and north cannot be equal to south.");
	}
}
