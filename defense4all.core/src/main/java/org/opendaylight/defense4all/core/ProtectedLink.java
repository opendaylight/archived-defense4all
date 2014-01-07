package org.opendaylight.defense4all.core;

public class ProtectedLink {

	public String label;
	public short northPort;
	public short southPort;
	public String macOfConnectedToNorthPort;

	public ProtectedLink() {this.label = null; this.northPort = 0; this.southPort = 0; macOfConnectedToNorthPort = "";}

	public ProtectedLink(String label, short northPort, short southPort, String macOfConnectedToNorthPort) {
		this.label = label; this.northPort = northPort; this.southPort = southPort;
		this.macOfConnectedToNorthPort = (macOfConnectedToNorthPort != null) ? macOfConnectedToNorthPort : "";
	}

	public ProtectedLink(String s) throws IllegalArgumentException {

		String[] split = s.split(NetNode.ITEMS_DELIMITER);
		if(split == null || split.length < 3) {
			NetNode.log.error("Invalid string parameter " + s);
			throw new IllegalArgumentException("Invalid string parameter " + s);
		}
		label = split[0];
		try {
			northPort = Short.valueOf(split[1]);
			southPort = Short.valueOf(split[2]);
			macOfConnectedToNorthPort = split.length >= 4 ? split[3]:""; // Empty serialized Mac will not produce split[3] 
		} catch (NumberFormatException e) {
			NetNode.log.error("Invalid string parameter " + s);
			throw new IllegalArgumentException("Invalid string parameter " + s, e);
		}
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(label); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(northPort); 
		sb.append(NetNode.ITEMS_DELIMITER); sb.append(southPort); 
		sb.append(NetNode.ITEMS_DELIMITER);	sb.append(macOfConnectedToNorthPort);
		return sb.toString();
	}

	public void validate() throws Exception {
		if(northPort == 0 || southPort == 0 || northPort == southPort)
			throw new Exception("Invalid ports - cannot be 0 and north cannot be equal to south.");
	}
}