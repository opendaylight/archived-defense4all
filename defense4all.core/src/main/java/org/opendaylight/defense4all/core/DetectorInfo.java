/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.core;

import java.lang.IllegalArgumentException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class DetectorInfo {
	
	/* DetectionRepo column names */
	public static final String LABEL = "label";
	public static final String DETECTOR_CONFIDENCE = "detector_confidence";
	public static final String OF_BASED_DETECTOR = "of_based_detector";
	public static final String EXTERNAL_DETECTOR = "external_detector";
	
	public enum DetectorConfidence {
		INVALID,
		VERY_HIGH,
		HIGH,
		MEDIUM
	}
	
	static Logger log = LoggerFactory.getLogger(DetectorInfo.class);
	
	protected static ArrayList<RepoCD> mDetectorsRepoCDs = null;

	public String label;
	public DetectorConfidence detectorConfidence; // Detector confidence in this attack detection
	public boolean ofBasedDetector;
	public boolean externalDetector;

	/** ### Description ###
	 * @param param_name 
	 */
	public DetectorInfo() {
		label = null; detectorConfidence = DetectorConfidence.INVALID; ofBasedDetector = false;
		externalDetector = true;
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws  
	 */
	public DetectorInfo(String label,DetectorConfidence confidence, boolean ofBasedDetector, boolean externalDetector) {	
		this.label = label;	this.detectorConfidence = confidence; 
		this.ofBasedDetector = ofBasedDetector; this.externalDetector = externalDetector;
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public DetectorInfo(String combined) throws IllegalArgumentException {
		ofBasedDetector = false; externalDetector = true;
		String[] split = combined.split(":");
		if(split == null || split.length < 2) {
			log.error("Invalid combined detectorInfo string " + combined + ".");
			throw new IllegalArgumentException("Invalid combined detectorInfo string  " + combined + ".");
		}
		label = split[0]; detectorConfidence = DetectorConfidence.valueOf(split[1]);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("DetectorInfo[label="); sb.append(label); sb.append(", ");
		sb.append("detectorConfidence="); sb.append(detectorConfidence); sb.append(", ");
		sb.append("ofBasedDetector="); sb.append(ofBasedDetector); sb.append(", ");
		sb.append("externalDetector="); sb.append(externalDetector); sb.append(", ");
		sb.append("]");
		return sb.toString();
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public DetectorInfo(DetectorInfo other) {
		this.label = other.label; this.detectorConfidence = other.detectorConfidence; 
		this.ofBasedDetector = other.ofBasedDetector; this.externalDetector = other.externalDetector;
	}

	public String getLabel() {return label;}
	public void setLabel(String label) {this.label = label;}

	public DetectorConfidence getDetectorConfidence() {return detectorConfidence;}
	public void setDetectorConfidence(DetectorConfidence confidence) {this.detectorConfidence = confidence;}
	
	public boolean getOfBasedDetector() {return ofBasedDetector;}
	public void setOfBasedDetector(boolean ofBasedDetector) {this.ofBasedDetector = ofBasedDetector;}
	
	public boolean getExternalDetector() {return externalDetector;}
	public void setExternalDetector(boolean externalDetector) {this.externalDetector = externalDetector;}

	public DetectorInfo(Hashtable<String, Object> pnRow) {
		
		Object obj;
		label = (String) pnRow.get(DetectorInfo.LABEL);
		obj = pnRow.get(DetectorInfo.DETECTOR_CONFIDENCE);
		if (obj != null) detectorConfidence = DetectorConfidence.valueOf((String)obj);	
		obj = pnRow.get(DetectorInfo.OF_BASED_DETECTOR);		
		if (obj != null) ofBasedDetector = (Boolean) obj;	
		obj = pnRow.get(DetectorInfo.EXTERNAL_DETECTOR);		
		if (obj != null) externalDetector = (Boolean) obj;
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(label == null) label = "";
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(DetectorInfo.LABEL, label);
		row.put(DetectorInfo.DETECTOR_CONFIDENCE, detectorConfidence.name());
		row.put(DetectorInfo.OF_BASED_DETECTOR, ofBasedDetector);
		row.put(DetectorInfo.EXTERNAL_DETECTOR, externalDetector);
		return row;
	}
	
	public void fromRow(Hashtable<String, Object> pnRow) {
		
		Object obj;
		label = (String) pnRow.get(LABEL);
		obj = pnRow.get(DetectorInfo.DETECTOR_CONFIDENCE);
		if (obj != null) detectorConfidence = DetectorConfidence.valueOf((String)obj);	
		obj = pnRow.get(DetectorInfo.OF_BASED_DETECTOR);		
		if (obj != null) ofBasedDetector = Boolean.valueOf(obj.toString());	
		obj = pnRow.get(DetectorInfo.EXTERNAL_DETECTOR);		
		if (obj != null) externalDetector = Boolean.valueOf(obj.toString());
	}
	
	public static List<RepoCD> getDetectorRCDs() {

		if(mDetectorsRepoCDs == null) {
			RepoCD rcd;
			mDetectorsRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(DetectorInfo.LABEL, StringSerializer.get(), null);	mDetectorsRepoCDs.add(rcd);
			rcd = new RepoCD(DetectorInfo.DETECTOR_CONFIDENCE, StringSerializer.get(), null); mDetectorsRepoCDs.add(rcd);
			rcd = new RepoCD(DetectorInfo.OF_BASED_DETECTOR, BooleanSerializer.get(), null); mDetectorsRepoCDs.add(rcd);
			rcd = new RepoCD(DetectorInfo.EXTERNAL_DETECTOR, BooleanSerializer.get(), null); mDetectorsRepoCDs.add(rcd);
		}		
		return mDetectorsRepoCDs;
	}
}
