package org.opendaylight.defense4all.core;


public class TrafficPort {
	
	public enum PortLocation { 
		invalid, 
		north, 	 // closer to client
		south 	 // closer to server
	}

	public String label;
	public short number;
	public int vlan;
	public PortLocation location;
	public boolean up;

	public TrafficPort() {
		this.label = null; this.number = 0; this.vlan = 0; location = PortLocation.invalid; up = true;
	}

	public TrafficPort(String label, short number, int vlan, PortLocation location) {
		this.label = label; this.number = number; this.vlan = vlan; this.location = location; up = true;
	}

	public TrafficPort(String s) throws IllegalArgumentException {

		String[] split = s.split(NetNode.ITEMS_DELIMITER);
		if(split == null || split.length < 4) {
			NetNode.log.error("Invalid string parameter " + s);
			throw new IllegalArgumentException("Invalid string parameter " + s);
		}
		label = split[0];
		try {
			number = Short.valueOf(split[1]);
			vlan = Integer.valueOf(split[2]);
			location = PortLocation.valueOf(split[3]);
		} catch (NumberFormatException e) {
			NetNode.log.error("Invalid string parameter " + s);
			throw new IllegalArgumentException("Invalid string parameter " + s, e);
		}
		up = true;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(label); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(number); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(vlan); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(location);
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(up);
		return sb.toString();
	}

	public void validate() throws Exception {
		if(number == 0)
			throw new Exception("Invalid ports - cannot be 0 and north cannot be equal to south.");
	}
}