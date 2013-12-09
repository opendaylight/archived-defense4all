/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import java.util.ArrayList;
import java.util.Properties;

import org.opendaylight.defense4all.framework.core.AppRoot;
import org.opendaylight.defense4all.framework.core.EM;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public abstract class DFAppRoot extends AppRoot {
	
	/**
	 * Name space allocation of DF REPO major IDs
	 */

	public enum RepoMajor {	
		DF_INVALID,
		DF_GLOBAL,
		DF_ATTACK_DECISION_POINT,
		DF_MGMT_POINT,
		DF_DIVERSION_CONFIRMATION_POINT,
		DF_STATS_COLLECTION_REP,
		DF_DVSN_REP,
		DF_AMS_REP,
		DF_DP_BASED_DETECTOR,
		DF_DETECTOR,
		DF_STATS_COLLECTOR,
		DF_TRAFIC_DIVERSION_MODE,
		DF_MITIGATION_MGR
	}
	
	/**
	 * Name space allocation of DF REPO global minor IDs
	 */

	public enum RepoMinor {	
		INVALID,
		OFCS,
		AMS,
		NETNODES,
		PNS,
		ATTACKS,
		DETECTORS,
		DETECTIONS,
		PROFILES,
		POLICIES,
		MITIGATIONS,
		DVSN_INFOS,
		FLOW_CONFIG_INFOS,
		TRAFFIC_FLOORS,
		COUNTERS_STATS,
	}
	
	public static final String DF_APP = "Defense4All";

	/* Defense4All flight recorder event types */
	public static final String FR_DF_CONFIG = "DF_config";
	public static final String FR_DF_OPERATIONAL = "DF_operational";
	public static final String FR_DF_FAILURE = "DF_failure";
	public static final String FR_DF_SECURITY = "DF_security";
	public static final String FR_AMS_OPERATIONAL = "AMS_operational";
	public static final String FR_AMS_FAILURE = "AMS_failure";
	public static final String FR_AMS_SECURITY = "AMS_security";
	public static final String FR_OFC_FAILURE = "OFC_failure";
	public static final String FR_OFC_OPERATIONAL = "OFC_operational";
	
	
	public static final int  CONTROLLER_STATS_COLLECTION_INTERVAL = 15; 
	public long baselineRecordingIntervalInSecs;
	
	public static final String OF_RATE_BASED_DETECTOR_LABEL = "of_rate_based_detector";

	
	public EM dfEM = null;
	
	public Repo<String> oFCsRepo = null;	
	public Repo<String> amsRepo = null;		
	public Repo<String> netNodesRepo = null;		
	public Repo<String> pNsRepo = null;		
	public Repo<String> attacksRepo = null;	
	public Repo<String> detectorsRepo = null;	
	public Repo<String> detectionsRepo = null;	
	public Repo<String> mitigationsRepo = null;	
	public Repo<String> dvsnInfosRepo = null;	
	public Repo<String> profilesRepo = null;	
	public Repo<String> policiesRepo = null;
	public Repo<String> flowConfigInfosRepo = null;
	public Repo<String> trafficFloorsRepo = null;
	public Repo<String> countersStatsRepo = null;
	
	/**
	 * Initializes all modules after construction - bottom-up.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {	
		super.init();
	}

	/**
	 * Cleans up all modules before shutdown - top-down .
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {
		super.finit();
	}

	/**
	 * Performs reset on all modules.
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		super.reset(resetLevel);
	}

	/**
	 * Performs factory reset on all modules.
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void test(Properties props) {
	}

	public abstract AMSRep getAMSRep();
	public abstract StatsCollectionRep getStatsCollectionRep();
	public abstract DvsnRep getDvsnRep();
	public abstract StatsCollector getStatsCollector();
	public abstract DFMgmtPoint getMgmtPoint();
	public abstract MitigationMgr getMitigationMgr();
	public abstract DetectorMgr getDetectorMgr();
	public abstract AttackDecisionPoint getAttackDecisionPoint();
	public abstract ArrayList<MitigationDriver> getMitigationDrivers();
}
