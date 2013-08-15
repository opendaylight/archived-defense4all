/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.opendaylight.controlapps.framework.core.RepoCD;

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
	public DetectorInfo(String label, DetectorConfidence detectorConfidence, boolean ofBasedDetector,
			boolean externalDetector) {	
		this.label = label;	this.detectorConfidence = detectorConfidence; 
		this.ofBasedDetector = ofBasedDetector; this.externalDetector = externalDetector;
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public DetectorInfo(String combined) {
		ofBasedDetector = false; externalDetector = true;
		String[] split = combined.split(":");
		label = split[0]; detectorConfidence = DetectorConfidence.valueOf(split[1]);
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public String toString() {
		return label+":"+detectorConfidence.toString();
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
		label = (String) pnRow.get(DetectorInfo.LABEL);
		detectorConfidence = DetectorConfidence.valueOf((String)pnRow.get(DetectorInfo.DETECTOR_CONFIDENCE));	
		ofBasedDetector = (Boolean) pnRow.get(DetectorInfo.OF_BASED_DETECTOR);	
		externalDetector = (Boolean) pnRow.get(DetectorInfo.EXTERNAL_DETECTOR);
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
		if ( pnRow.get(DetectorInfo.LABEL) != null )
			label = (String) pnRow.get(DetectorInfo.LABEL);
		if ( pnRow.get(DetectorInfo.DETECTOR_CONFIDENCE) != null )
			detectorConfidence = DetectorConfidence.valueOf((String)pnRow.get(DetectorInfo.DETECTOR_CONFIDENCE));	
		if ( pnRow.get(DetectorInfo.OF_BASED_DETECTOR) != null )
			ofBasedDetector = Boolean.valueOf ( pnRow.get(DetectorInfo.OF_BASED_DETECTOR).toString() );	
		if ( pnRow.get(DetectorInfo.EXTERNAL_DETECTOR) != null )
			externalDetector = Boolean.valueOf( pnRow.get(DetectorInfo.EXTERNAL_DETECTOR).toString());
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
