/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.util.Properties;

public interface FrameworkMain {
	
	public enum ResetLevel {
		
		soft, // Reset easily reconstructible dynamic state (e.g., latest traffic statistics, network topology)
		dynamic, // Reset all dynamic state portions, including the not easily reconstructible (e.g., traffic baselines)
		factory;  // Full reset of both dynamic state as well as user configurations
		
		public static ResetLevel valueOf(String s, ResetLevel defaultLevel) {
			
			ResetLevel returnValue;
			try {
				returnValue = ResetLevel.valueOf(s);
			} catch (Exception e) {
				returnValue = defaultLevel;
			}
			return returnValue;
		}
	}
	
	/**
	 * Name space allocation of Framework REPO Major IDs
	 */

	public enum RepoMajor {	
		FWORK_INVALID,
		FWORK_GLOBAL,
		FWORK_REPO_FACTORY,
		FWORK_COMMUNICATOR,
		FWORK_CLUSTER_MGR,
		FWORK_MGMT_POINT,
		FWORK_FLIGHT_RECORDER
	}

	/**
	 * Name space allocation of Framework REPO global minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
		CORE_STATE
	}

	/* Framework flight recorder event types */
	public static final String FR_FRAMEWORK_CONFIG = "Framework_config";
	public static final String FR_FRAMEWORK_OPERATIONAL = "Framework_operational";
	public static final String FR_FRAMEWORK_FAILURE = "Framework_failure";
	
	public Properties getConfigProperties();
	public RepoFactory getRepoFactory();
	public ClusterMgr getClusterMgr();
	public PeerCommunicator getPeerCommunicator();
	public FrameworkMgmtPoint getFrameworkMgmtPoint();
	public AppRoot getAppRoot();
	public FR getFR();
	public HealthTracker getHealthTracker();
	public void requestShutdown(boolean graceful);
	public boolean isOpenForBusiness();
	public boolean isDebugRun();
	public String getHostAddr();
}
