/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.defense4all.core.impl;

import java.util.ArrayList;
import java.util.Properties;

import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.AMSRep;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.CounterStat;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.DFMgmtPoint;
import org.opendaylight.defense4all.core.Detection;
import org.opendaylight.defense4all.core.DetectorMgr;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.FlowConfigInfo;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.DvsnRep;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.MitigationMgr;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.DvsnInfo;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.StatsCollectionRep;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoFactory;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class DFAppRootFullImpl extends DFAppRoot {

	static Logger log = LoggerFactory.getLogger(DFAppRootFullImpl.class);

	protected AttackDecisionPointImpl attackDecisionPointImpl;
	protected StatsCollectorImpl statsCollectorImpl;
	protected MitigationMgrImpl mitigationMgrImpl;
	protected DFMgmtPointImpl mgmtPointImpl;
	protected DetectorMgrImpl detectorMgrImpl;
	protected StatsCollectionRep statsCollectionRep;
	protected DvsnRep dvsnRep;
	protected AMSRep amsRep;
	protected int controllerStatsCollectionIntervalInSecs = CONTROLLER_STATS_COLLECTION_INTERVAL; // controller interval to collect statistics 


	/* Mitigation drivers will be invoked for mitigation - in the order they appear here - 0 first. */
	protected MitigationDriver mitigationDriver0 = null;
	protected MitigationDriver mitigationDriver1 = null;
	protected MitigationDriver mitigationDriver2 = null;
	protected MitigationDriver mitigationDriver3 = null;
	protected MitigationDriver mitigationDriver4 = null;
	protected MitigationDriver mitigationDriver5 = null;
	protected ArrayList<MitigationDriver> mitigationDrivers = null;

	/**
	 * defense4all core entity manager id
	 */
	public static final String DF_CORE_EM_ID = "df.core";

	protected String stateClassPaths = null;

	/**
	 * Constructor for Spring to set context and mFrameworkMain. Sets all column descriptions for all the global repos.
	 * @param param_name param description
	 * @throws exception_type circumstances description 
	 */
	public DFAppRootFullImpl() throws ExceptionControlApp {		
		super();
		DFHolder.set(this);
	}	

	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {super.setFrameworkMain(frameworkMain);}
	public void setAttackDecisionPoint(AttackDecisionPointImpl impl) {this.attackDecisionPointImpl = impl;}
	public void setStatsCollectorImpl(StatsCollectorImpl impl) {this.statsCollectorImpl = impl;}
	public void setMitigationMgrImpl(MitigationMgrImpl impl) {this.mitigationMgrImpl = impl;}
	public void setDetectorMgrImpl(DetectorMgrImpl impl) {this.detectorMgrImpl = impl;}
	public void setMgmtPointImpl(DFMgmtPointImpl impl) {this.mgmtPointImpl = impl;}
	public void setStatsCollectionRep(StatsCollectionRep statsCollectionRep) {this.statsCollectionRep = statsCollectionRep;}
	public void setDvsnRep(DvsnRep dvsnRep) {this.dvsnRep = dvsnRep;}
	public void setAmsRep(AMSRep amsRep) {this.amsRep = amsRep;}
	public void setClassPaths(String stateClassPaths) {this.stateClassPaths = stateClassPaths;}
	public void setAttackDecisionPointImpl(AttackDecisionPointImpl impl) {this.attackDecisionPointImpl = impl;}
	public void setMitigationDriver0(MitigationDriver driver0) {this.mitigationDriver0 = driver0;}
	public void setMitigationDriver1(MitigationDriver driver1) {this.mitigationDriver1 = driver1;}
	public void setMitigationDriver2(MitigationDriver driver2) {this.mitigationDriver2 = driver2;}
	public void setMitigationDriver3(MitigationDriver driver3) {this.mitigationDriver3 = driver3;}
	public void setMitigationDriver4(MitigationDriver driver4) {this.mitigationDriver4 = driver4;}
	public void setMitigationDriver5(MitigationDriver driver5) {this.mitigationDriver5 = driver5;}
	public void setControllerStatsCollectionIntervalInSecs(int interval) {this.controllerStatsCollectionIntervalInSecs=interval;}
	public void setBaselineRecordingIntervalInSecs(long interval) {this.baselineRecordingIntervalInSecs = interval;}

	@Override
	public StatsCollectionRep getStatsCollectionRep() {return statsCollectionRep;}	
	@Override
	public DvsnRep getDvsnRep() {return dvsnRep;}
	@Override
	public AMSRep getAMSRep() {return amsRep;}
	@Override
	public DFMgmtPoint getMgmtPoint() {return mgmtPointImpl;}
	@Override
	public MitigationMgr getMitigationMgr() {return mitigationMgrImpl;}
	@Override
	public DetectorMgr getDetectorMgr() {return detectorMgrImpl;}
	@Override
	public StatsCollectorImpl getStatsCollector() {return statsCollectorImpl;}
	@Override
	public AttackDecisionPointImpl getAttackDecisionPoint() {return attackDecisionPointImpl;}
	@Override
	public ArrayList<MitigationDriver> getMitigationDrivers() {return mitigationDrivers;}

	/**
	 * Initializes all modules after construction - bottom-up.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */

	public void init() throws ExceptionControlApp {

		try {
			fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DF is (re)starting.");

			super.init();

			name = DF_APP;

			/* This class initialization */
			RepoFactory rf = fMain.getRepoFactory();
			dfEM = rf.getOrCreateEM(DF_CORE_EM_ID, stateClassPaths);

			/* Set all non-null mitigation drivers into their array list */
			mitigationDrivers = new ArrayList<MitigationDriver>();
			if(mitigationDriver0 != null) {
				mitigationDriver0.init();
				mitigationDrivers.add(mitigationDriver0);
			}
			if(mitigationDriver1 != null) {
				mitigationDriver1.init();
				mitigationDrivers.add(mitigationDriver1);
			}
			if(mitigationDriver2 != null) {
				mitigationDriver2.init();
				mitigationDrivers.add(mitigationDriver2);
			}
			if(mitigationDriver3 != null) {
				mitigationDriver3.init();
				mitigationDrivers.add(mitigationDriver3);
			}
			if(mitigationDriver4 != null) {
				mitigationDriver4.init();
				mitigationDrivers.add(mitigationDriver4);
			}
			if(mitigationDriver5 != null) {
				mitigationDriver5.init();
				mitigationDrivers.add(mitigationDriver5);
			}


			// All DF global repos
			String repoGlobal = RepoMajor.DF_GLOBAL.name();
			try { 
				oFCsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.OFCS.name(), sSer, true, OFC.getOFCRCDs());
				amsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.AMS.name(), sSer, true, AMS.getAMSRCDs());
				netNodesRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.NETNODES.name(), sSer, true, NetNode.getNetNodeRCDs());
				pNsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.PNS.name(), sSer, true, PN.getPNRCDs());
				attacksRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.ATTACKS.name(), sSer, true, Attack.getAttackRCDs());
				detectorsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.DETECTORS.name(), sSer, true, DetectorInfo.getDetectorRCDs());
				detectionsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.DETECTIONS.name(), sSer, true, Detection.getDetectionRCDs());
				profilesRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.PROFILES.name(), sSer, true, null);
				policiesRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.POLICIES.name(), sSer, true, null);
				mitigationsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.MITIGATIONS.name(), sSer, true, Mitigation.getMitigationRCDs());
				dvsnInfosRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.DVSN_INFOS.name(), sSer, true, DvsnInfo.getRCDs());
				flowConfigInfosRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.FLOW_CONFIG_INFOS.name(), sSer, true, FlowConfigInfo.getRCDs());
				trafficFloorsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.TRAFFIC_FLOORS.name(), sSer, true, TrafficFloor.getRCDs());
				countersStatsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.COUNTERS_STATS.name(), sSer, true, CounterStat.getCounterStatsRCDs());
			} catch (Throwable e) {
				String msg = "Excepted trying to retrieve/construct all global repos";
				log.error(msg, e);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to start.");
				throw new ExceptionControlApp(msg, e);
			}

			; // Set DF and AMS based detectors in detectionSourcesRepo		

			/* Other modules initialization */

			statsCollectionRep.init();
			dvsnRep.init();
			amsRep.init();

			statsCollectorImpl.init();		
			detectorMgrImpl.init();

			attackDecisionPointImpl.init();
			mitigationMgrImpl.init();		

			mgmtPointImpl.init();

		} catch (Throwable e) {
			log.error("DF Failed to start. " + e.getMessage());
			throw new ExceptionControlApp("DF Failed to start. " + e.getMessage());
		}
	}

	/**
	 * Cleans up all modules before shutdown - top-down .
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DF is stopping.");

		// TODO: this class cleanup

		try {
			mgmtPointImpl.finit();
		} catch (Exception e) {log.error("mgmtPointImpl failed to finit " + e.getMessage());}

		try {
			mitigationMgrImpl.finit();
		} catch (Exception e) {log.error("mitigationMgrImpl failed to finit " + e.getMessage());}
		try {
			attackDecisionPointImpl.finit();
		} catch (Exception e) {log.error("attackDecisionPointImpl failed to finit " + e.getMessage());}

		try {
			detectorMgrImpl.finit();
		} catch (Exception e) {log.error("detectorMgrImpl failed to finit " + e.getMessage());}
		try {
			statsCollectorImpl.finit();
		} catch (Exception e) {log.error("statsCollectorImpl failed to finit " + e.getMessage());}

		try {
			amsRep.finit();
		} catch (Exception e) {log.error("amsRep failed to finit " + e.getMessage());}
		try {
			statsCollectionRep.finit();
		} catch (Exception e) {log.error("statsCollectionRep failed to finit " + e.getMessage());}
		try {
			dvsnRep.finit();
		} catch (Exception e) {log.error("dvsnRep failed to finit " + e.getMessage());}

		try {
			super.finit();
		} catch (Exception e) {log.error("dfAppRoot super failed to finit " + e.getMessage());}
	}

	/**
	 * Performs factory reset on all modules.
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DF is resetting to level " + resetLevel + ".");

		super.reset(resetLevel);

		mgmtPointImpl.reset(resetLevel);
		mitigationMgrImpl.reset(resetLevel);
		attackDecisionPointImpl.reset(resetLevel);

		detectorMgrImpl.reset(resetLevel); 
		statsCollectorImpl.reset(resetLevel);

		amsRep.reset(resetLevel);
		statsCollectionRep.reset(resetLevel);
		dvsnRep.reset(resetLevel);

		/* Clear repos */
		flowConfigInfosRepo.truncate();
		trafficFloorsRepo.truncate();
		countersStatsRepo.truncate();
	}

	@Override
	public void test(Properties props) {

		//		annotationsTest1();
		//		repoTest1();
		//		mStatsCollectionRep.test(props);
		//		mDiversionRep.test(props);
		//		mMgmtPointImpl.test();
		//		attackDecisionPointImpl.test();
	}
}
