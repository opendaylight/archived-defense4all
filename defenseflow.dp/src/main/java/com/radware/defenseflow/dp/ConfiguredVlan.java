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

import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfiguredVlan {

	private static Logger log = LoggerFactory.getLogger(ConfiguredVlan.class);

	/* ConfiguredNetwork Repo columns */	
	public static final String NAME = "name";
	public static final String VLAN = "vlan";

	protected static ArrayList<RepoCD> configVlanRCDs = null;
	
	public String name;
	public long   vlan;	

	/* ### Description ###
	 * @param param_name 
	 */
	public ConfiguredVlan() {name = null; vlan = 0;}
	
	public ConfiguredVlan(Hashtable<String, Object> row) throws ExceptionControlApp {
		
		this();
		
		try {
			name = (String) row.get(NAME);
			vlan = (Long) row.get(VLAN);
		} catch (Throwable e) {
			log.error("Excepted trying to inflate ConfiguredVlan from row.", e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate ConfiguredVlan from row.", e);
		}
	}

	public ConfiguredVlan(ConfiguredVlan other) {this.name = other.name; this.vlan = other.vlan;}

	public Hashtable<String, Object> toRow() {
		
		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(name == null) name = "";
		
		Hashtable<String, Object> row = new Hashtable<String, Object>();		

		row.put(NAME, name);
		row.put(VLAN, vlan);
		
		return row;
	}
	
	@Override
	public String toString() {
		String s = "ConfiguredNetwork [" + "name=" + name + ", vlan=" + vlan + "]"; 
		return s;
	}
	
	public static List<RepoCD> getRCDs() {

		if(configVlanRCDs == null) {
			RepoCD rcd;
			configVlanRCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(NAME, StringSerializer.get(), null);	configVlanRCDs.add(rcd);
			rcd = new RepoCD(VLAN, LongSerializer.get(), null);	configVlanRCDs.add(rcd);
		}		
		return configVlanRCDs;
	}
}