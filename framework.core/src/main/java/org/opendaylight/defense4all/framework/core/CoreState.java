/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import org.opendaylight.defense4all.framework.core.RepoCD;

import me.prettyprint.cassandra.serializers.StringSerializer;

public class CoreState {

	/* AttackRepo column names */
	public static final String KEY 	= "key";
	public static final String HOST_ADDRESS = "host_address";
	public static final String CLUSTER_NAME	= "cluster_name";

	/* Framework core state row key. */
	public static final String FWORK_CORE_STATE_ROW_KEY = "framework_core_state";
	public static final String FWORK_DEFAULT_CLUSTER_NAME = "framework_default_cluster"; //Different from Cassandra cluster	

	public String key;
	public String hostAddr;
	public String clusterName;

	protected static ArrayList<RepoCD> repoCDs = null;

	/** ### Description ###
	 * @param param_name 
	 */
	public CoreState() {
		key = FWORK_CORE_STATE_ROW_KEY; hostAddr = null; clusterName = FWORK_DEFAULT_CLUSTER_NAME;
	}

	/** ### Description ###
	 * @param param_name 
	 * @throws 
	 */
	public CoreState(String hostAddr, String clusterName) {
		key = FWORK_CORE_STATE_ROW_KEY; this.hostAddr = hostAddr; this.clusterName = clusterName;
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public CoreState(CoreState other) {
		this.key = other.key; this.hostAddr = other.hostAddr; this.clusterName = other.clusterName;
	}

	public CoreState(Hashtable<String, Object> row) {
		key = (String) row.get(KEY);
		hostAddr = (String) row.get(HOST_ADDRESS);
		clusterName = (String) row.get(CLUSTER_NAME);
	}

	public Hashtable<String, Object> toRow() {

		/* Change any null value to empty, otherwise Hashtable.put() will throw an exception */
		if(hostAddr == null) hostAddr = "";

		Hashtable<String, Object> row = new Hashtable<String, Object>();
		row.put(KEY, key);
		row.put(HOST_ADDRESS, hostAddr);
		row.put(CLUSTER_NAME, clusterName);
		return row;
	}

	public String getKey() {return key;}
	public void setKey(String key) {this.key = key;}

	public String getHostAddr() {return hostAddr;}
	public void setHostAddr(String hostAddr) {this.hostAddr = hostAddr;}

	public String getClusterName() {return clusterName;}
	public void setClusterName(String clusterName) {this.clusterName = clusterName;}

	public static List<RepoCD> getRCDs() {

		if(repoCDs == null) {
			RepoCD rcd;
			repoCDs = new ArrayList<RepoCD>();			
			rcd = new RepoCD(KEY, StringSerializer.get(), null);			repoCDs.add(rcd);
			rcd = new RepoCD(HOST_ADDRESS, StringSerializer.get(), null);	repoCDs.add(rcd);
			rcd = new RepoCD(CLUSTER_NAME, StringSerializer.get(), null); 	repoCDs.add(rcd);
		}		
		return repoCDs;
	}
}
