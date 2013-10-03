package org.opendaylight.defense4all.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.opendaylight.defense4all.framework.core.PropertiesSerializer;

public class NetNodeDvsnInfo {
	
	public String netNodeLabel;
	public List<String> amsLabels;
	public Properties dvsnProps;
	
	public NetNodeDvsnInfo(String netNodeLabel, List<String> amsLabels, Properties dvsnProps) {
		this.netNodeLabel = netNodeLabel; this.amsLabels = amsLabels; this.dvsnProps = dvsnProps;
	}
	
	public NetNodeDvsnInfo(NetNodeDvsnInfo other) {
		this.netNodeLabel = other.netNodeLabel; this.amsLabels = other.amsLabels; this.dvsnProps = other.dvsnProps;
	}
	
	public NetNodeDvsnInfo(String dvsnPerNetNodePairStr) {
		
		String[] split = dvsnPerNetNodePairStr.split("::");
		netNodeLabel = split[0]; 
		String[] split2 = split[1].split(",");
		this.amsLabels  = new ArrayList<String>(); 
		for(String amsLabel : split2)
			amsLabels.add(amsLabel);
		dvsnProps = PropertiesSerializer.get().fromString(split[2]);
	}
	
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(netNodeLabel); sb.append("::");
		for(String amsLabel : amsLabels)
			sb.append(amsLabel); sb.append(",");
		sb.append("::");
		sb.append(PropertiesSerializer.get().toString(dvsnProps));
		
		return sb.toString();
	}
}