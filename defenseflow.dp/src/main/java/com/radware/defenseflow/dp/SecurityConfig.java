/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Gera Goft
 * @version 0.1
 */
package com.radware.defenseflow.dp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityConfig {

	private static Logger log = LoggerFactory.getLogger(SecurityConfig.class);

	/* SecurityConfiguration Repo columns */	
	public static final String KEY = "key";
	public static final String MITIGATION_KEY = "mitigation_key";
	public static final String PNKEY = "pnkey";
	public static final String NETWORK_NAME = "configured_network_name";
	public static final String BDOS_PROFILE_NAME = "bdos_profile_name";
	public static final String OUT_OF_STATE_PROFILE_NAME = "out_of_state_profile_name";
	public static final String DNS_PROFILE_NAME = "dns_profile_name";
	public static final String SECURITY_POLICY_NAME = "security_policy_name";
	public static final String DP_NAME_PREFIX = "dp_";

	protected static ArrayList<RepoCD> securityConfigurationRCDs = null;
	
	public String key;
	public String mitigationKey;
	public String pnkey;
	public String configuredNetworkName;
	public String bdosProfileName;
	public String oosProfileName;
	public String dnsProfileName;
	public String securityPolicyName;
	public List<String> configuredDPs;
	
	public String generateAndSetKey() {
		key = generateKey(configuredNetworkName, mitigationKey);
		return key;
	}
	
	public static String generateKey(String networkName, String mitigationKey) {
		StringBuilder sb = new StringBuilder();
		sb.append(networkName); sb.append("_"); sb.append(mitigationKey);
		return sb.toString();
	}

	/* ### Description ###
	 * @param param_name 
	 */
	public SecurityConfig() {
		key = null; mitigationKey = null; pnkey = null; configuredNetworkName = null; bdosProfileName = null;
		oosProfileName = null; dnsProfileName = null; securityPolicyName = null; configuredDPs = null;
	}

	public SecurityConfig(SecurityConfig other) {
		this.key = other.key; this.mitigationKey = other.mitigationKey; this.pnkey = other.pnkey; 
		this.configuredNetworkName = other.configuredNetworkName; this.bdosProfileName = other.bdosProfileName;
		this.oosProfileName = other.oosProfileName; this.dnsProfileName = other.dnsProfileName; 
		this.securityPolicyName = other.securityPolicyName;
		this.configuredDPs = new ArrayList<String>(); this.configuredDPs.addAll(other.configuredDPs);
	}
	
	public SecurityConfig(Hashtable<String, Object> row) throws ExceptionControlApp {
		
		this();
		
		try {
			key = (String) row.get(KEY);
			mitigationKey = (String) row.get(MITIGATION_KEY);	
			pnkey = (String) row.get(PNKEY);
			configuredNetworkName = (String) row.get(NETWORK_NAME);
			bdosProfileName = (String) row.get(BDOS_PROFILE_NAME);
			oosProfileName = (String) row.get(OUT_OF_STATE_PROFILE_NAME);
			dnsProfileName = (String) row.get(DNS_PROFILE_NAME);
			securityPolicyName = (String) row.get(SECURITY_POLICY_NAME);
			
			/* Retrieve all dpNames */
			Iterator<Map.Entry<String,Object>> iter = row.entrySet().iterator();
			Map.Entry<String,Object> entry; String key;	
			configuredDPs = new ArrayList<String>();
			while(iter.hasNext()) {
				entry = iter.next();
				key = (String) entry.getKey();
				if(key.startsWith(DP_NAME_PREFIX))
					configuredDPs.add((String) (entry.getValue()));
			}
		} catch (Exception e) {
			log.error("Excepted trying to inflate SecurityConfig from row.", e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate SecurityConfig from row.", e);
		}		
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(key == null) key = "";
		if(mitigationKey == null ) mitigationKey = "";
		if(pnkey == null ) pnkey = "";
		if(configuredNetworkName == null) configuredNetworkName = "";
		if(bdosProfileName == null) bdosProfileName = "";
		if(oosProfileName == null) oosProfileName = "";
		if(dnsProfileName == null) dnsProfileName = "";
		if(securityPolicyName == null) securityPolicyName = "";		
		if(configuredDPs == null) configuredDPs = new ArrayList<String>();
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(KEY, key);
		row.put(MITIGATION_KEY, mitigationKey);
		row.put(PNKEY, pnkey);
		row.put(NETWORK_NAME, configuredNetworkName);
		row.put(BDOS_PROFILE_NAME, bdosProfileName);
		row.put(OUT_OF_STATE_PROFILE_NAME, oosProfileName);
		row.put(DNS_PROFILE_NAME, dnsProfileName);
		row.put(SECURITY_POLICY_NAME, securityPolicyName);
		for(String dpName : configuredDPs)
			row.put(DP_NAME_PREFIX + dpName, dpName);
		
		return row;
	}
	
	public static List<RepoCD> getRCDs() {

		if(securityConfigurationRCDs == null) {
			RepoCD rcd;
			securityConfigurationRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);				securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(MITIGATION_KEY, StringSerializer.get(), null);		securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);				securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(NETWORK_NAME, StringSerializer.get(), null); securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(BDOS_PROFILE_NAME, StringSerializer.get(), null);	securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(OUT_OF_STATE_PROFILE_NAME, StringSerializer.get(), null);	securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(DNS_PROFILE_NAME, StringSerializer.get(), null);	securityConfigurationRCDs.add(rcd);
			rcd = new RepoCD(SECURITY_POLICY_NAME, StringSerializer.get(), null); securityConfigurationRCDs.add(rcd);
		}		
		return securityConfigurationRCDs;
	}
}