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
import java.util.List;

import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFAppRoot.HealthStatus;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class DPHealthMgr extends DFAppModule {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_CHECK_DP_HEALTH = 1;

	protected long dpHealthCheckIntervalInSecs = 60; // Period to check all DPs health, if not set anywhere else
	public DPRep amsRep;
	List<SnmpConnector> snmpConnectors;

	public DPHealthMgr() {
		super();
		snmpConnectors = new ArrayList<SnmpConnector>();
	}

	/* Setters for Spring */
	public void setAmsRep(DPRep amsRep) {this.amsRep = amsRep;}
	public void setDpHealthCheckIntervalInSecs(long intervalInSecs) {this.dpHealthCheckIntervalInSecs = intervalInSecs;}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {
		super.init();

		if(!fMain.isDemo()) {
			try {
				addPeriodicExecution(ACTION_CHECK_DP_HEALTH, null, dpHealthCheckIntervalInSecs);
			} catch (Exception e) {
				log.error("Failed to instantiate periodic task to check DP's health", e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {

		super.finit();

		for(SnmpConnector snmpConnector : snmpConnectors) {
			try {
				snmpConnector.finit();
			} catch (Throwable e) {/* Ignore */}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		super.reset(resetLevel);

		for(SnmpConnector snmpConnector : snmpConnectors) {
			try {
				snmpConnector.reset(resetLevel);
			} catch (Throwable e) {/* Ignore */}
		}
		snmpConnectors.clear();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(String amsKey) throws ExceptionControlApp {

		String dpAddr = null;
		try {
			dpAddr = (String) dfAppRoot.amsRepo.getCellValue(amsKey, AMS.MGMT_IP_ADDR_STRING);
			if(dpAddr == null || dpAddr.isEmpty()) throw new ExceptionControlApp("Null or empty DP mgmt address in repo");
		} catch (ExceptionControlApp e) {
			String msg = "Failed to getCellValue for added AMS.";
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);
		}
		SnmpConnector snmpConnector = new SnmpConnector(amsKey, dpAddr);
		snmpConnector.init();
		snmpConnectors.add(snmpConnector);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removeAMS(String amsKey) throws ExceptionControlApp {

		for (SnmpConnector snmpConnector : snmpConnectors) {
			if(snmpConnector.amsKey.equals(amsKey)) {
				snmpConnectors.remove(snmpConnector);
				snmpConnector.finit();
				return;
			}
		}
	}

	protected void checkAllDPsHealth() {

		if(!fMain.isOpenForBusiness()) return; // Operate only after everything is initialized and is not terminating

		HealthStatus newDPHealthStatus; HealthStatus oldDPHealthStatus; 
		for(SnmpConnector snmpConnector : snmpConnectors) {
			try {
				oldDPHealthStatus = HealthStatus.fromBoolean ( (Boolean) dfAppRoot.amsRepo.getCellValue(snmpConnector.amsKey, AMS.HEALTH_STATUS));
				newDPHealthStatus = snmpConnector.getStatus();
				if(newDPHealthStatus == oldDPHealthStatus) continue; // No DP status change

				// Need to record the DP liveness change
				fMain.getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL, ""+snmpConnector.amsKey+" now "+newDPHealthStatus);
				dfAppRoot.notifyAMSStatusChange(snmpConnector.amsKey, newDPHealthStatus);
				dfAppRoot.amsRepo.setCell(snmpConnector.amsKey, AMS.HEALTH_STATUS, HealthStatus.toBoolean(newDPHealthStatus));
			} catch (Throwable e) {/* Ignore */}
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_CHECK_DP_HEALTH:
			checkAllDPsHealth();
			break;
		default:
		}
	}
}
