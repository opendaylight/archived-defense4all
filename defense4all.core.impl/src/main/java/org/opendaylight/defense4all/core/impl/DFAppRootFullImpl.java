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

import java.util.*;

import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.AMSConnection;
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
import org.opendaylight.defense4all.core.PO;
import org.opendaylight.defense4all.core.StatsCollectionRep;
import org.opendaylight.defense4all.core.TrafficFloor;
import org.opendaylight.defense4all.core.interactionstructures.NetNodeUppedDownedAMSConns;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.HealthTracker;
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
    private Map<String, AMSRep> amsRepMap;

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
		name = DF_APP;
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
    public void setAmsRepMap(Map<String, AMSRep> amsRepMap) {this.amsRepMap = amsRepMap;}
    public void setClassPaths(String stateClassPaths) {this.stateClassPaths = stateClassPaths;}
	public void setAttackDecisionPointImpl(AttackDecisionPointImpl impl) {this.attackDecisionPointImpl = impl;}
	public void setMitigationDriver0(MitigationDriver driver0) {this.mitigationDriver0 = driver0;}
	public void setMitigationDriver1(MitigationDriver driver1) {this.mitigationDriver1 = driver1;}
	public void setMitigationDriver2(MitigationDriver driver2) {this.mitigationDriver2 = driver2;}
	public void setMitigationDriver3(MitigationDriver driver3) {this.mitigationDriver3 = driver3;}
	public void setMitigationDriver4(MitigationDriver driver4) {this.mitigationDriver4 = driver4;}
	public void setMitigationDriver5(MitigationDriver driver5) {this.mitigationDriver5 = driver5;}
	public void setControllerStatsCollectionIntervalInSecs(long interval) {this.controllerStatsCollectionIntervalInSecs = interval;}
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

	public void init(boolean bestEffort) throws ExceptionControlApp {

		try {
			fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, name + " is (re)starting.");
			super.init(bestEffort);
		} catch (Throwable e) {	
			if ( ! bestEffort ) throw new ExceptionControlApp(e);
		}



		/* This class initialization */
		RepoFactory rf= null;
		try {
			rf = fMain.getRepoFactory();
			dfEM = rf.getOrCreateEM(DF_CORE_EM_ID, stateClassPaths);
		} catch (Throwable e) {
			String msg = "Excepted trying to init RepoFactory "+ name;
			log.error(msg, e);
			if ( ! bestEffort ) throw new ExceptionControlApp(msg);	
		}

		/* Set all non-null mitigation drivers into their array list */
		try {
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
		} catch (Throwable e) {
			String msg = "Excepted trying to init Mitigation Drivers "+ name;
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort ) throw new ExceptionControlApp(msg);
		}



		// All DF global repos
		String repoGlobal = RepoMajor.DF_GLOBAL.name();
		try { 
			oFCsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.OFCS.name(), sSer, true, OFC.getOFCRCDs());
			amsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.AMS.name(), sSer, true, AMS.getAMSRCDs());
			netNodesRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.NETNODES.name(), sSer, true, NetNode.getNetNodeRCDs());
			pNsRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.PNS.name(), sSer, true, PN.getPNRCDs());
			posRepo = (Repo<String>) rf.getOrCreateRepo(repoGlobal, RepoMinor.POS.name(), sSer, true, PO.getPORCDs());
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
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( !bestEffort) throw new ExceptionControlApp(msg, e);
		}

		; // Set DF and AMS based detectors in detectionSourcesRepo		

		/* Other modules initialization */

		try {
			statsCollectionRep.init();
		} catch (Throwable e) {
			String msg = "Excepted trying to init statsCollectionRep";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}
		try {
			dvsnRep.init();
		} catch (Throwable e) {
			String msg = "Excepted trying to init dvsnRep";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}
		try {
            //load ams from DB in case it exists, and decide if using DPRep or DefaultAmsRep
            AMS ams = retrieveActiveAms();
            if (ams != null) {
                if (ams.getBrand().contains("DefensePro")) {
                    setAmsRepByType("DP");//constant from spring map at defense4all_context
                } else {
                    setAmsRepByType(ams.getBrand());
                }
            }

            amsRep.init();
            for (AMSRep amsRepItem : amsRepMap.values()) {
                amsRepItem.init();
            }
		} catch (Throwable e) {
			String msg = "Excepted trying to init amsRep";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}

		try {
			statsCollectorImpl.init();
		} catch (Throwable e) {
			String msg = "Excepted trying to init statsCollectorImpl";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}
		try {
			detectorMgrImpl.init();
		} catch (Throwable e) {
			String msg = "Excepted trying to init detectorMgrImpl";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}

		try {
			attackDecisionPointImpl.init();
		} catch (Throwable e) {
			String msg = "Excepted trying to init attackDecisionPointImpl";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}
		try {
			mitigationMgrImpl.init();		
		} catch (Throwable e) {
			String msg = "Excepted trying to init mitigationMgrImpl";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start."); 
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}
		try {
			mgmtPointImpl.init();
		} catch (Throwable e) {
			String msg = "Excepted trying to init mgmtPointImpl";
			log.error(msg, e);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, name + " failed to start.");
			if ( ! bestEffort) throw new ExceptionControlApp(msg);
		}

	}

    private AMS retrieveActiveAms() throws ExceptionControlApp {
        AMS activeAms = null;
        boolean foundActiveAms = false;
        List<String> AmsKeys = amsRepo.getKeys();
        for (String amsKey : AmsKeys) {
            AMS ams = new AMS(amsRepo.getRow(amsKey));
            if (AMS.Status.ACTIVE.equals(ams.getStatus())) {
                if (foundActiveAms) {
                    log.error("found more than one active AMS, supports 1 only!!! the other is " + ams);
                } else {
                    foundActiveAms = true;
                    log.debug("found active AMS in DB: " +ams);
                    activeAms = ams;
                }
            }
        }
        return activeAms;
    }

    /**
	 * Cleans up all modules before shutdown - top-down .
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {

		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, name + " stopping.");

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
            for (AMSRep amsRepItem : amsRepMap.values()) {
                amsRepItem.finit();
            }
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

		Throwable eToLog = null;

		fMain.getFR().logRecord(DFAppRoot.FR_DF_OPERATIONAL, name + " resetting to " + resetLevel + " settings.");

		try {
			super.reset(resetLevel);
		} catch ( Throwable e ) { eToLog = e;  }

		try {
			mgmtPointImpl.reset(resetLevel);
		} catch ( Throwable e ) { eToLog = e;}
		try {
			mitigationMgrImpl.reset(resetLevel);
		} catch ( Throwable e ) { eToLog = e; }
		try {
			attackDecisionPointImpl.reset(resetLevel);
		} catch ( Throwable e ) { eToLog = e; }

		try {
			detectorMgrImpl.reset(resetLevel); 
		} catch ( Throwable e ) {  eToLog = e; }
		try {
			statsCollectorImpl.reset(resetLevel);
		} catch ( Throwable e ) {  eToLog = e; }

		try {
			amsRep.reset(resetLevel);
            for (AMSRep amsRepItem : amsRepMap.values()) {
                amsRepItem.reset(resetLevel);
            }
		} catch ( Throwable e ) {  eToLog = e; }
		try {
			statsCollectionRep.reset(resetLevel);
		} catch ( Throwable e ) {  eToLog = e; }
		try {
			dvsnRep.reset(resetLevel);
		} catch ( Throwable e ) {  eToLog = e; }

		try {
			/* Clear repos */
			flowConfigInfosRepo.truncate();
			trafficFloorsRepo.truncate();
			countersStatsRepo.truncate();
		} catch ( Throwable e ) {  eToLog = e; }

		if ( eToLog != null ) {
			log.error("Excepting trying to reset application", eToLog);
			throw new ExceptionControlApp ( eToLog );
		}
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

	@Override
	public synchronized void notifyAMSStatusChange(String amsLabel, HealthStatus healthStatus) {

		if(!fMain.isOpenForBusiness()) return; // Operate only after everything is initialized and is not terminating

		/* Update all relevant AMSConnections, and build the list of AMSConnection status changes in all netnodes. */
		Hashtable<String,Hashtable<String,Object>> table = netNodesRepo.getTable();
		if(table == null) {
			log.error("Received null netnodes table");
			return;
		}
		Hashtable<String,Object> row; NetNode netNode; NetNodeUppedDownedAMSConns netNodeDownedAMSConns;	
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = table.entrySet().iterator();
		List<NetNodeUppedDownedAMSConns> downedAmsConns = new ArrayList<NetNodeUppedDownedAMSConns>();
		while(iter.hasNext()) {		
			row = iter.next().getValue();
			try {
				netNode = new NetNode(row);
				netNodeDownedAMSConns = updateNetNodeAMSConnsStatus(netNode, amsLabel, healthStatus);
				if(netNodeDownedAMSConns != null)
					downedAmsConns.add(netNodeDownedAMSConns);
			} catch (Throwable e1) {
				String msg = "Failed to netNodeUppedDownedAMSConns for netnode " + row.get(NetNode.LABEL);
				log.error(msg);
				fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to update AMS connection status");
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);				
			}
		}

		/* Notify AMSConnections status change. Do it in a second pass because some netNodes can be logical,
		 * depend on other netNodes, and their AMSConnections may be created dynamically. To avoid handling 
		 * complex cross-netNode dependencies we update all AMSConnections status in all netNodes in the
		 * first pass, and then notify to handle upon those changes in the second pass - ensuring that in
		 * any case all dependent upon netNodes have all their AMSConnection status updated already. */
		for(NetNodeUppedDownedAMSConns netNodedownedAMSConns2 : downedAmsConns) {
			dvsnRep.notifyNetNodeAMSConnStatusChanged(netNodedownedAMSConns2);
		}

		/* Mitigations with no resources or failed AMSs may look for other AMSs to retry mitigation. */
		mitigationMgrImpl.decoupledRetryMitigations();
	}

	protected NetNodeUppedDownedAMSConns updateNetNodeAMSConnsStatus(NetNode netNode,String amsLabel,HealthStatus amsStatus) {

		NetNodeUppedDownedAMSConns netNodeDownedAMSConns = new NetNodeUppedDownedAMSConns(netNode.label);
		Iterator<Map.Entry<String,AMSConnection>> iter = netNode.amsConnections.entrySet().iterator();
		AMSConnection amsConnection; AMSConnection clonedAMSConnection; 
		boolean netNodeChanged = false; boolean amsConnStatusChanged;

		while(iter.hasNext()) {

			amsConnection = iter.next().getValue();
			if(! amsConnection.amsLabel.equals(amsLabel)) continue;

			amsConnStatusChanged = amsConnection.setAmsStatus(amsStatus);
			if(!amsConnStatusChanged) continue;

			/* AMSConnection status changed, so record it in FR, add to upped/downed. mark netNode changed. */
			clonedAMSConnection = new AMSConnection(amsConnection);
			fMain.getFR().logRecord(DFAppRoot.FR_OFC_OPERATIONAL,"AMS "+amsConnection.amsLabel+" ports "+amsConnection.amsNorthPort+" "+amsConnection.amsSouthPort+" now "+amsConnection.getHealthStatus());
			if(amsConnection.getHealthStatus() == HealthStatus.DOWN)
				netNodeDownedAMSConns.downedAmsConns.add(clonedAMSConnection);
			netNodeChanged = true;
		}
		if(!netNodeChanged) return null;

		/* Persist in netnodes repo netnode changes. */
		try {
			netNodesRepo.setRow(netNode.label, netNode.toRow());
		} catch (Throwable e1) {
			log.error("Failed to persist changes to repo for netNode " + netNode.label, e1);
			fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to update AMS connection to "+netNode.id+" NetNode status");
			return null;
		}	

		return netNodeDownedAMSConns;
	}

	@Override
	public void netNodeStatusChanged(String logicalNetNodeLabel, HealthStatus healthStatus) {
		mitigationMgrImpl.netNodeStatusChanged(logicalNetNodeLabel, healthStatus);
	}

    public void setAmsRepByType(String amsTypeOrBrand) {
        this.amsRep = amsRepMap.get(amsTypeOrBrand);
        if (this.amsRep==null) {
            log.warn("did not found AMS type {} in spring configuration -> using DefaultAms", amsTypeOrBrand );
            this.amsRep = amsRepMap.get("DefaultAms");//default from the Spring xml
        }
    }

}
