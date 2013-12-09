/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.opendaylight.defense4all.framework.core.RepoCD;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

public class Detection {
	
	public final static String DF_DETECTOR = "df_detector";
	
	/* DetectionRepo column names */
	public static final String KEY 	= "key";
	public static final String DETECTOR = "detector";
	public static final String DETECTION_CONFIDENCE = "detection_confidence";
	public static final String SET_TIME = "set_time";
	public static final String DURATION = "duration";
	public static final String PNKEY = "pnkey";
	public static final String PROTOCOL_PORT = "protocol_port";
	
	public enum DetectionConfidence {
		INVALID,
		VERY_HIGH,
		HIGH,
		MEDIUM
	}	

	public String key;
	public String detector;
	public DetectionConfidence detectionConfidence; // Detector confidence in this attack detection
	public long setTime;
	public long duration;
	public String pnKey;
	public ProtocolPort protocolPort;
	
	protected static ArrayList<RepoCD> mDetectionsRepoCDs = null;
	
	public static String generateDetectionKey(String detectorLabel, String pnKey, ProtocolPort protocolPort) {
		return detectorLabel + pnKey + "." + protocolPort.toString();
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public Detection() {
		key = detector = null; setTime = duration = 0; pnKey = null; protocolPort = new ProtocolPort(); 
		detectionConfidence = DetectionConfidence.INVALID;
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws
	 */
	public Detection(String key, String detector, DetectionConfidence detectionConfidence, long setTime, 
					 long duration, String pnKey, ProtocolPort protocolPort) {	
		this.key = key;	this.detector = detector; this.detectionConfidence = detectionConfidence;
		this.setTime = setTime;	this.duration = duration; this.pnKey = pnKey; this.protocolPort = protocolPort;
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public Detection(Detection other) {		
		this.key = other.key; this.detector = other.detector; this.detectionConfidence = other.detectionConfidence;
		this.setTime = other.setTime; this.duration = other.duration; this.pnKey = other.pnKey;
		this.protocolPort = other.protocolPort;
	}

	public Detection(Hashtable<String, Object> detectionRow) {
		this();
		key = (String) detectionRow.get(KEY);
		detector = (String) detectionRow.get(DETECTOR);
		detectionConfidence = DetectionConfidence.valueOf((String)detectionRow.get(DETECTION_CONFIDENCE));
		setTime = (Long) detectionRow.get(SET_TIME);
		duration = (Long) detectionRow.get(DURATION);
		pnKey = (String) detectionRow.get(PNKEY);
		protocolPort = new ProtocolPort((String) detectionRow.get(PROTOCOL_PORT));
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(detector == null) detector = "";
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(KEY, key);
		row.put(DETECTOR, detector);
		row.put(DETECTION_CONFIDENCE, detectionConfidence.name());
		row.put(SET_TIME, setTime);
		row.put(DURATION, duration);
		row.put(PNKEY, pnKey);
		row.put(PROTOCOL_PORT, protocolPort.toString());
		return row;
	}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	
	public String getDetector() {return detector;}
	public void setDetector(String detector) {this.detector = detector;}
	
	public DetectionConfidence getDetectionConfidence() {return detectionConfidence;}
	public void setDetectionConfidence(DetectionConfidence confidence) {this.detectionConfidence = confidence;}

	public long getSetTime() {return setTime;}
	public void setSetTime(long setTime) {this.setTime = setTime;}

	public long getDuration() {return duration;}
	public void setDuration(long duration) {this.duration = duration;}
	
	public String getPnkey() {return pnKey;}
	public void setPnkey(String pnKey) {this.pnKey = pnKey;}

	public ProtocolPort getProtocolPort() {return protocolPort;}
	public void setProtocolPort(ProtocolPort protocolPort) {this.protocolPort = protocolPort;}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Detection [key="); sb.append(key);
		sb.append(", detector="); sb.append(detector);
		sb.append(", detectorConfidence="); sb.append(detectionConfidence);
		sb.append(", setTime="); sb.append(setTime);
		sb.append(", duration="); sb.append(duration);
		sb.append(", pn="); sb.append(pnKey);
		sb.append(", protocol-port="); sb.append(protocolPort.toString());
		sb.append("]");
		return sb.toString();
	}

	public static List<RepoCD> getDetectionRCDs() {

		if(mDetectionsRepoCDs == null) {
			RepoCD rcd;
			mDetectionsRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);	mDetectionsRepoCDs.add(rcd);
			rcd = new RepoCD(DETECTOR, StringSerializer.get(), null);	mDetectionsRepoCDs.add(rcd);
			rcd = new RepoCD(DETECTION_CONFIDENCE, StringSerializer.get(), null);  mDetectionsRepoCDs.add(rcd);
			rcd = new RepoCD(SET_TIME, IntegerSerializer.get(), null);	mDetectionsRepoCDs.add(rcd);
			rcd = new RepoCD(DURATION, IntegerSerializer.get(), null);	mDetectionsRepoCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);	mDetectionsRepoCDs.add(rcd);
			rcd = new RepoCD(PROTOCOL_PORT, StringSerializer.get(), null);	mDetectionsRepoCDs.add(rcd);
		}		
		return mDetectionsRepoCDs;
	}
}
