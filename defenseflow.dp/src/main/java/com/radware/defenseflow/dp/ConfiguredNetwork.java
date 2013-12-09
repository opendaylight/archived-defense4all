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
import java.util.List;

import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfiguredNetwork {

	private static Logger log = LoggerFactory.getLogger(ConfiguredNetwork.class);

	/* ConfiguredNetwork Repo columns */	
	public static final String NAME = "name";
	public static final String INDEX = "index";
	public static final String PNKEY = "pnkey";
	public static final String ADDRESS = "address";
	public static final String ADDRESS_PREFIX_LEN = "address_prefix_len";

	protected static ArrayList<RepoCD> configNetworkRCDs = null;
	
	public String name;
	public long   index;
	public String pnkey;
	public String address;
	public int 	  addressPrefixLen;	

	/* ### Description ###
	 * @param param_name 
	 */
	public ConfiguredNetwork() {name = null; index = 0; pnkey = null; address = null; addressPrefixLen = 0;}
	
	public ConfiguredNetwork(Hashtable<String, Object> row) throws ExceptionControlApp {
		
		this();
		
		try {
			name = (String) row.get(NAME);
			index = (Long) row.get(INDEX);	
			pnkey = (String) row.get(PNKEY);
			address = (String) row.get(ADDRESS);
			addressPrefixLen = (Integer) row.get(ADDRESS_PREFIX_LEN);
		} catch (Throwable e) {
			log.error("Excepted trying to inflate ConfiguredNetwork from row.", e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate ConfiguredNetwork from row.", e);
		}
	}

	public ConfiguredNetwork(ConfiguredNetwork other) {
		this.name = other.name; this.index = other.index; this.pnkey = other.pnkey; 
		this.address = other.address; this.addressPrefixLen = other.addressPrefixLen;
	}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(name == null) name = "";
		if(pnkey == null ) pnkey = "";
		if(address == null) address = "";
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(NAME, name);
		row.put(INDEX, index);
		row.put(PNKEY, pnkey);
		row.put(ADDRESS, address);
		row.put(ADDRESS_PREFIX_LEN, addressPrefixLen);
		
		return row;
	}
	
	@Override
	public String toString() {
		String s = "ConfiguredNetwork [" + "name=" + name + ", address=" + address + ", addressPrefixLen=" 
					+ addressPrefixLen + "]"; 
		return s;
	}
	
	public static List<RepoCD> getRCDs() {

		if(configNetworkRCDs == null) {
			RepoCD rcd;
			configNetworkRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(NAME, StringSerializer.get(), null);	configNetworkRCDs.add(rcd);
			rcd = new RepoCD(INDEX, LongSerializer.get(), null);	configNetworkRCDs.add(rcd);
			rcd = new RepoCD(PNKEY, StringSerializer.get(), null);	configNetworkRCDs.add(rcd);
			rcd = new RepoCD(ADDRESS, StringSerializer.get(), null);configNetworkRCDs.add(rcd);
			rcd = new RepoCD(ADDRESS_PREFIX_LEN, IntegerSerializer.get(), null);	configNetworkRCDs.add(rcd);
		}		
		return configNetworkRCDs;
	}
}