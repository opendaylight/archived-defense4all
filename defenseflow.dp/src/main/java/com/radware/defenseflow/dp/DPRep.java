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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.AMSRep;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.Utils;

public class DPRep extends DFAppModule implements AMSRep {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	private static final int   ACTION_ADD_AMS = 1;
	private static final int   ACTION_REMOVE_AMS = 2;
	private static final int   ACTION_ADD_PN = 3;
	private static final int   ACTION_REMOVE_PN = 4;
	private static final int   ACTION_START_MONITORING = 7;
	private static final int   ACTION_STOP_MONITORING = 8;

	/**
	 * Radware DefensePro brand to be put in AMS.brand
	 */
	public static final String DP = "DefensePro";

	/** Names of application networks start with this prefix. */
	private static final String NETWORK_APPLICATION_PREFIX = "NAP";

	/**
	 * Name space allocation of DP Detector Repo minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
		MONITORED_TRAFFIC,
		CONFIGURED_NETWORKS,
		CONFIGURED_VLANS,
		SECURITY_CONFIGURATIONS
	}

	protected Logger log = LoggerFactory.getLogger(this.getClass());
	protected DPEventMgr  dpEventMgr;
	protected DPConfigMgr dpConfigMgr;
	protected DPHealthMgr dpHealthMgr;
	protected DPBasedDetector dpBasedDetector;

	/* Constructor for Spring */
	public DPRep() {
		super();
	}

	/* Setters for Spring */
	public void setDpEventMgr(DPEventMgr dpEventMgr) {this.dpEventMgr = dpEventMgr;}
	public void setDpConfigMgr(DPConfigMgr dpConfigMgr) {this.dpConfigMgr = dpConfigMgr;}
	public void setDpHealthMgr(DPHealthMgr dpHealthMgr) {this.dpHealthMgr = dpHealthMgr;}
	public void setDpBasedDetector(DPBasedDetector detector) {this.dpBasedDetector = detector;}

	/** Post-constructor initialization	 
	 * @throws ExceptionControlApp */
	@Override
	public void init() throws ExceptionControlApp {

		super.init();
		
		dpEventMgr.init();
		dpConfigMgr.init();
		dpHealthMgr.init();		
		dpBasedDetector.init(); // Register detector in the repo
	}

	/** Pre-shutdown cleanup */
	@Override
	public void finit() {

		dpEventMgr.finit();
		dpConfigMgr.finit();
		dpHealthMgr.finit();
		
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	@Override
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		
		super.reset(resetLevel);

		dpEventMgr.reset(resetLevel);
		dpConfigMgr.reset(resetLevel);
		dpHealthMgr.reset(resetLevel);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addAMS(String amsKey) throws ExceptionControlApp {
		try {	
			invokeDecoupledSerially(ACTION_ADD_AMS, amsKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_ADD_AMS + " " + amsKey, e);
			throw e;
		}
	}

	protected void decoupledAddAMS (String amsKey) {
		log.info( "DPRep is adding DP " + amsKey);
		
		try {
			AMS.Status status = AMS.Status.valueOf((String)dfAppRoot.amsRepo.getCellValue(amsKey, AMS.STATUS));
			if ( status == AMS.Status.REMOVED ) return;
			dpConfigMgr.addAMS(amsKey);
			dpHealthMgr.addAMS(amsKey);
			dpEventMgr.addAMS(amsKey);
		} catch (Throwable e) {
			log.error("Excepted adding AMS to either dpConfigMgr or dpHealthMgr" + e.getMessage());
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to add AMS " + amsKey);
			return;
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeAMS(String amsKey) throws ExceptionControlApp {

		try {
			invokeDecoupledSerially(ACTION_REMOVE_AMS, amsKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_REMOVE_AMS + " " + amsKey, e);
			throw e;
		}
	}

	protected void decoupledRemoveAMS (String amsKey) {
		try {
			log.info("DPRep is removing DP " + amsKey);
			dpConfigMgr.removeAMS(amsKey);
			dpHealthMgr.removeAMS(amsKey);	
		} catch (ExceptionControlApp e) {
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to Remove AMS " + amsKey);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addPN(String pnKey) throws ExceptionControlApp {

		try {
			invokeDecoupledSerially(ACTION_ADD_PN, pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_ADD_PN + " " + pnKey, e);
			throw e;
		}
	}

	protected void decoupledAddPN (String pnKey) {
		try {
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_CONFIG, "Adding PO " + PN.getPrintableKey(pnKey));
			dpConfigMgr.addPN(pnKey);
		} catch (ExceptionControlApp e) {
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to add PO " + PN.getPrintableKey(pnKey));
			return;
		}
		dpEventMgr.addPN(pnKey);
	}

	protected static String generateNetworkName(String s) {
		String res = (s.length() <= 7) ? s : NETWORK_APPLICATION_PREFIX + Utils.shortHash(s);
		return res;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removePN(String pnKey) throws ExceptionControlApp {

		try {
			invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_REMOVE_PN + " " + pnKey, e);
			throw e;
		}
	}

	protected void decoupledRemovePN (String pnKey) {
		dpConfigMgr.removePN(pnKey);
		dpEventMgr.removePN(pnKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp {

		try {
			dpConfigMgr.addSecurityConfiguration(dvsnInfoKey);
		} catch (ExceptionControlApp e) {}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp {
		dpConfigMgr.removeSecurityConfiguration(dvsnInfoKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void startMonitoring(String mitigationKey) throws ExceptionControlApp {
		
		try {
			invokeDecoupledSerially(ACTION_START_MONITORING, mitigationKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy "+ACTION_START_MONITORING+" "+mitigationKey,e);
			throw e;
		}
	}

	/* Notify DPBasedDetector to assume a DP based detector role for the mitigated traffic. DPBasedDetector
	 * will translate dpEventMgr notifications into DP originated "attack detections". Notify DPEventMgr to
	 * start monitoring for security events related to this mitigation and notify dpBasedDetector. */
	protected void decoupledStartMonitoring(String mitigationKey) {
		log.info("Starting to monitor through DPs traffic for mitigation "+ mitigationKey);
		dpBasedDetector.addMonitoredAttack(mitigationKey);
		dpEventMgr.startMonitoring(mitigationKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void stopMonitoring(String mitigationKey) throws ExceptionControlApp {
		
		try {
			invokeDecoupledSerially(ACTION_STOP_MONITORING, mitigationKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy "+ACTION_STOP_MONITORING+" "+mitigationKey,e);
			throw e;
		}
	}

	/* Notify DPEventMgr to stop monitoring for security events related to this mitigation, dpBasedDetector
	 * to stop acting as DP based detector for this mitigated traffic. */
	protected void decoupledStopMonitoring(String mitigationKey) {
		log.info( "Stopping to monitor through DPs traffic for mitigation " + mitigationKey);
		dpEventMgr.stopMonitoring(mitigationKey);
		dpBasedDetector.removeMonitoredAttack(mitigationKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public boolean check() {
		return true;
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_ADD_AMS:
			decoupledAddAMS((String) param); 
			break;
		case ACTION_REMOVE_AMS:
			decoupledRemoveAMS((String) param); 
			break;
		case ACTION_ADD_PN:
			decoupledAddPN((String) param); 
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param); 
			break;
		case ACTION_START_MONITORING:
			decoupledStartMonitoring((String) param); 
			break;
		case ACTION_STOP_MONITORING:
			decoupledStopMonitoring((String) param); 
			break;
		default:
		}
	}

	public void test() {}
}
