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
import java.util.Properties;

import org.opendaylight.controlapps.framework.core.PropertiesSerializer;
import org.opendaylight.controlapps.framework.core.RepoCD;

import me.prettyprint.cassandra.serializers.StringSerializer;


public class Attack {
	
	public enum Status {
		INVALID,
		SUSPECTED,
		DECLARED,
		ENDING,
		ENDED
	}
	
	public final static String DF_DETECTOR = "df_detector";
	
	/* AttackRepo column names */
	public static final String KEY 	= "key";
	public static final String PNKEY = "pnkey";
	public static final String MITIGATION_KEY	= "mitigation_key";
	public static final String PROTOCOL_PORT = "protocol_port";
	public static final String STATUS = "status";
	public static final String DETECTION_KEYS = "detection_keys";

	public String key;
	public String pnKey;
	public String mitigationKey;
	public ProtocolPort protocolPort;
	public Status status;
	public Properties detectionKeys;
	
	public static String generateKey(String pnKey, ProtocolPort protocolPort) {return pnKey + "_" + protocolPort.toString();}
	
	protected static ArrayList<RepoCD> mAttacksRepoCDs = null;

	/** ### Description ###
	 * @param param_name 
	 */
	public Attack() {
		key = pnKey = mitigationKey = null; protocolPort = new ProtocolPort(); status = Status.INVALID; 
		detectionKeys = new Properties();
	}
	
	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public Attack(String key, String pnKey, ProtocolPort protocolPort, Status attackstatus, Properties detectionKeys) {
		this();
		this.key = key;	this.pnKey = pnKey;	this.protocolPort = protocolPort; this.status = attackstatus;
		this.detectionKeys = detectionKeys == null ? new Properties() : detectionKeys;
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public Attack(Attack other) {
		this.key = other.key; this.pnKey = other.pnKey;	this.mitigationKey = other.mitigationKey; 
		this.protocolPort = other.protocolPort;	this.status = other.status;	this.detectionKeys = other.detectionKeys;
	}

	public Attack(Hashtable<String, Object> attackRow) {
		this();
		key = (String) attackRow.get(KEY);
		pnKey = (String) attackRow.get(PNKEY);
		mitigationKey = (String) attackRow.get(MITIGATION_KEY);
		protocolPort = new ProtocolPort((String) attackRow.get(PROTOCOL_PORT));
		status = Status.valueOf((String) attackRow.get(STATUS));
		detectionKeys = (Properties) attackRow.get(DETECTION_KEYS);
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(pnKey == null) pnKey = "";
		if(mitigationKey == null) mitigationKey = "";
		if(protocolPort == null) protocolPort = new ProtocolPort();
		if(detectionKeys == null) detectionKeys = new Properties();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(KEY, key);
		row.put(PNKEY, pnKey);
		row.put(MITIGATION_KEY, mitigationKey);
		row.put(PROTOCOL_PORT, protocolPort.toString());
		row.put(STATUS, status.name());
		row.put(DETECTION_KEYS, detectionKeys);
		return row;
	}
	
	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}
	
	public String getPnkey() {return pnKey;}
	public void setPnkey(String pnKey) {this.pnKey = pnKey;}
	
	public String getMitigationkey() {return mitigationKey;}
	public void setMitigationkey(String mitigationKey) {this.mitigationKey = mitigationKey;}

	public ProtocolPort getProtocolPort() {return protocolPort;}
	public void setProtocolPort(ProtocolPort protocolPort) {this.protocolPort = protocolPort;}

	public Status getStatus() {return status;}
	public void setStatus(Status attackstatus) {this.status = attackstatus;}

	public Properties getDetectionKeys() {return detectionKeys;}
	public void setDetectionKeys(Properties detectionKeys) {this.detectionKeys = detectionKeys;}

	public static List<RepoCD> getAttackRCDs() {

		if(mAttacksRepoCDs == null) {
			RepoCD rcd;
			mAttacksRepoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);	mAttacksRepoCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);	mAttacksRepoCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_KEY, StringSerializer.get(), null); mAttacksRepoCDs.add(rcd);
			rcd = new RepoCD(PROTOCOL_PORT, StringSerializer.get(), null);	mAttacksRepoCDs.add(rcd);
			rcd = new RepoCD(STATUS, StringSerializer.get(), null);	mAttacksRepoCDs.add(rcd);
			rcd = new RepoCD(DETECTION_KEYS, PropertiesSerializer.get(), null);	mAttacksRepoCDs.add(rcd);
		}		
		return mAttacksRepoCDs;
	}
}
