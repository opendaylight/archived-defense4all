package org.opendaylight.defense4all.restservice;

public class AMSConnection {
	private String amsLabel;
	private int netNodeNorthPort;
	private int netNodeSouthPort;
	private int amsNorthPort;
	private int amsSouthPort;
	

	public AMSConnection() {
		
	}

	public AMSConnection(String amsLabel) {
		
	}

	public int getAmsNorthPort() {
		return amsNorthPort;
	}


	public void setAmsNorthPort(int amsNorthPort) {
		this.amsNorthPort = amsNorthPort;
	}


	public String getAmsLabel() {
		return amsLabel;
	}


	public void setAmsLabel(String amsLabel) {
		this.amsLabel = amsLabel;
	}


	public int getNetNodeNorthPort() {
		return netNodeNorthPort;
	}


	public void setNetNodeNorthPort(int netNodeNorthPort) {
		this.netNodeNorthPort = netNodeNorthPort;
	}


	public int getAmsSouthPort() {
		return amsSouthPort;
	}


	public void setAmsSouthPort(int amsSouthPort) {
		this.amsSouthPort = amsSouthPort;
	}


	public int getNetNodeSouthPort() {
		return netNodeSouthPort;
	}


	public void setNetNodeSouthPort(int netNodeSouthPort) {
		this.netNodeSouthPort = netNodeSouthPort;
	}

}
