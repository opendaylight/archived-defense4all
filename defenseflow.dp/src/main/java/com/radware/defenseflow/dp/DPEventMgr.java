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

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.Traffic;
import org.opendaylight.defense4all.core.DFAppRoot.RepoMajor;
import org.opendaylight.defense4all.core.Traffic.TrafficMatch;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoFactory;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

import com.radware.defenseflow.dp.DPRep.RepoMinor;

public class DPEventMgr extends DFAppModule {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_PROCESS_SYSLOGS = 1;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public DPRep amsRep;

	/* Rep and cache of all monitored traffics. */
	public Repo<String> monitoredTrafficRepo = null;
	protected ConcurrentHashMap<String,MonitoredTraffic> monitoredTraffics = null;

	String rsyslogDFPipe;

	protected Hashtable<String,String> dpNames;

	/* Constructor for Spring */
	public DPEventMgr() {
		super();
		monitoredTraffics = new ConcurrentHashMap<String,MonitoredTraffic>();
		dpNames = new Hashtable<String,String>();
	}

	/* Setters for Spring */
	public void setRsyslogDFPipe(String rsyslogDFPipe) {this.rsyslogDFPipe = rsyslogDFPipe;}
	public void setAmsRep(DPRep amsRep) {this.amsRep = amsRep;}

	/** Post-constructor initialization	 
	 * @throws ExceptionControlApp */
	@Override
	public void init() throws ExceptionControlApp {

		super.init();		

		/* DPEventMgr Repos */
		RepoFactory rf = fMain.getRepoFactory();
		String rMajor = RepoMajor.DF_AMS_REP.name();
		try {
			monitoredTrafficRepo = (Repo<String>) rf.getOrCreateRepo(rMajor, RepoMinor.MONITORED_TRAFFIC.name(), 
					StringSerializer.get(), true, MonitoredTraffic.getRCDs());
		} catch (Throwable e) {
			log.error("Failed to getOrCreateRepo for monitored traffic.", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to getOrCreateRepo for monitored traffic.", e);
		}

		/* Load monitored traffics */
		Hashtable<String,Hashtable<String,Object>> monitoredTrafficTable = monitoredTrafficRepo.getTable();
		if(monitoredTrafficTable == null) {
			log.error("Failed to init - received null monitoredTraffic table");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to init - received null monitoredTraffic table");
		}
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = monitoredTrafficTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; MonitoredTraffic monitoredTraffic;
		while(iter.hasNext()) {
			entry = iter.next();
			try {
				monitoredTraffic = new MonitoredTraffic(entry.getValue());
			} catch (ExceptionControlApp e) {continue;}
			monitoredTraffics.put(monitoredTraffic.key, monitoredTraffic);
		}

		/* Initialize Linux pipe file from which to draw DP syslogs deposited by rsyslogd. */
		if(fMain.isDebugRun()) {
			try {
				Runtime runtime = Runtime.getRuntime();
//				runtime.exec("dd if=" + rsyslogDFPipe + " iflag=nonblock of=/dev/null > /dev/null");
				runtime.exec("mkfifo " + rsyslogDFPipe);
				runtime.exec("sudo service rsyslog restart"); // Make sure rsyslogd picks up the latest creation of DF syslog pipe
			} catch (Throwable e1) {}
		}
		try {
			addBackgroundTask(ACTION_PROCESS_SYSLOGS, null);
		} catch (Exception e) {
			log.error("Failed to instantiate periodic task to read syslogs from DPs", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
		}
	}

	/** Pre-shutdown cleanup */
	@Override
	public void finit() {

		super.finit();

		Iterator<Map.Entry<String,MonitoredTraffic>> iter = monitoredTraffics.entrySet().iterator();
		Map.Entry<String,MonitoredTraffic> entry; MonitoredTraffic monitoredTraffic; Hashtable<String,Object> row;
		String trafficKey = null;
		while(iter.hasNext()) {
			entry = iter.next();
			try {
				monitoredTraffic = entry.getValue();
				trafficKey = monitoredTraffic.key;
				row = monitoredTraffic.toRow();
				monitoredTrafficRepo.setRow(entry.getKey(), row);
			} catch (ExceptionControlApp e) {
				log.error("Failed to record monitored traffic in repo - " + trafficKey, e);
			}
		}
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	@Override
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		super.reset(resetLevel);

		monitoredTrafficRepo.truncate();
		monitoredTraffics.clear();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(String amsKey) {

		try {
			String dpAddrStr = (String) dfAppRoot.amsRepo.getCellValue(amsKey, AMS.MGMT_IP_ADDR_STRING);
			String dpLabel = (String) dfAppRoot.amsRepo.getCellValue(amsKey, AMS.LABEL);
			if(dpAddrStr != null && dpLabel != null)
				dpNames.put(dpAddrStr, dpLabel);
		} catch (Throwable e) {
			log.error("Failed to retrieve from amsRepo dpAddr or dpLabel for " + amsKey);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "DPRep failed to add DP " + amsKey);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeAMS(String amsKey) {

		try {
			String dpAddrStr = (String) dfAppRoot.amsRepo.getCellValue(amsKey, AMS.MGMT_IP_ADDR_STRING);
			if(dpAddrStr != null )
				dpNames.remove(dpAddrStr);
		} catch (Throwable e) {
			log.error("Failed to retrieve from amsRepo dpAddr for " + amsKey);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "DPRep failed to properly remove DP " + amsKey);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void startMonitoring(String mitigationKey) {

		Mitigation mitigation; String key = null;
		boolean monitoredTrafficsSet = false; boolean monitoredTrafficRowSet = false;

		try {

			Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
			mitigation = new Mitigation(mitigationRow);

			/* Construct monitoredTraffic and register it in monitoredTraffics and monitoredTrafficsRepo */
			int dstAddrPrefixLen = (Integer) DFHolder.get().pNsRepo.getCellValue(mitigation.pnKey, PN.DST_ADDR_PREFIX_LEN);
			Traffic traffic = new Traffic(mitigation.dstAddr, dstAddrPrefixLen);
			traffic.addProtocolPort(mitigation.protocolPort.protocol, mitigation.protocolPort.port);
			key = MonitoredTraffic.generateKey(mitigation.dstAddr.getHostName());	
			MonitoredTraffic monitoredTraffic = new MonitoredTraffic(key, mitigation.pnKey, mitigationKey, traffic);
			monitoredTraffics.put(key, monitoredTraffic);
			monitoredTrafficsSet = true;
			monitoredTrafficRepo.setRow(key, monitoredTraffic.toRow());
			monitoredTrafficRowSet = true;

			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Starting to monitor security events from DefensePros " +
					"for traffic " + monitoredTraffic.toString());

			/* Remember the key of the monitoredTraffic (to be used in removal of this monitoredTraffic) */
			mitigation.monitoredTrafficKey = key;
			dfAppRoot.mitigationsRepo.setRow(mitigationKey, mitigation.toRow());

		} catch (Throwable e) {
			log.error("Excepted starting monitoring for " + mitigationKey, e);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "DPRep failed to start monitoring through DPs for "
					+ "mitigated traffic " + mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			try {
				if(key != null && monitoredTrafficsSet) monitoredTraffics.remove(key);
				if(key != null && monitoredTrafficRowSet) monitoredTrafficRepo.deleteRow(key);
			} catch (Throwable e2) { 
				log.error("Failed to clean up after excepting trying to start monitoring " + mitigationKey, e2);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "DPRep failed to properly stop monitoring "
						+ "through DPs for mitigated traffic " + mitigationKey);
			}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void stopMonitoring(String mitigationKey) {

		try {
			String key = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey,Mitigation.MONITORED_TRAFFIC_KEY);		
			MonitoredTraffic monitoredTraffic = monitoredTraffics.remove(key);
			if(monitoredTraffic != null)
				fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Stopping to monitor security events from DefensePros " +
						"for traffic " + monitoredTraffic.toString());

			monitoredTrafficRepo.deleteRow(key);
		} catch (Throwable e) {
			log.error("Failed to stopMonitoring.", e);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "DPRep failed to properly stop monitoring through "
					+ "DPs for mitigated traffic " + mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}			
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public boolean check() {
		return true;
	}

	/* #### method description ####
	 * 
	 * @param s
	 * @return
	 */
	protected void backgroundProcessSyslogs() {

		BufferedReader bufferedReader;	String syslogMsg;

		while(true) {

			try {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(rsyslogDFPipe)));
			} catch (Throwable e) {
				log.error("Failed to open rsyslog pipe.", e);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to read DP syslogs from pipe "+rsyslogDFPipe);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				try {
					Thread.sleep(10000);
				} catch (Throwable e1) {/* Ignore */}
				continue;
			}

			try {
				syslogMsg = bufferedReader.readLine();
				log.info("DP SYSLOG msg: " + syslogMsg);
				bufferedReader.close();
			} catch (IOException e) {
				log.error("Failed to read from rsyslog pipe.", e);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to read DP syslogs from pipe "+rsyslogDFPipe);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				try {
					Thread.sleep(1000);
				} catch (Throwable e1) {/* Ignore */}
				continue;
			}
			if (syslogMsg == null || syslogMsg.length() == 0) continue;
			DPEvent dpEvent;
			try {
				dpEvent = DPEvent.fromString(syslogMsg);
				log.info("DP SYSLOG Event: " + (dpEvent == null ? "null dp event" : dpEvent.toString()));
			} catch (ExceptionControlApp e) {
				log.error("Failed to parse syslogMsg into dpEvent.", e);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to parse DP syslog msg " + syslogMsg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				return;
			}
			if (dpEvent == null) continue; // Not a DP message

			/* Strive to associate dp names to returned addresses */
			String dpName = dpNames.get(dpEvent.dpAddrStr);
			if(dpName != null) dpEvent.dpName = dpName;

			/* Check if it is a security event */
			DPSecEvent dpSecurityEvent = null;
			try {
				dpSecurityEvent = DPSecEvent.fromString(dpEvent.msg);
				if(dpSecurityEvent != null)
					log.info("DP SYSLOG Event: " + dpSecurityEvent.toString());
			} catch (ExceptionControlApp e) {
				log.error("Failed to process syslogMsg into dpEvent.", e);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE,"Failed to parse DP syslog security msg "+syslogMsg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				return;
			}
			if (dpSecurityEvent == null) {
				fMain.getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL, dpEvent.toString()); // Record as operational event
			} else { // Process security event
				processSecEvent(dpSecurityEvent); 
			}
		}
	}

	protected void processSecEvent(DPSecEvent secEvent) {

		Iterator<Map.Entry<String,MonitoredTraffic>> iter = monitoredTraffics.entrySet().iterator();
		TrafficMatch trafficMatch; MonitoredTraffic monitoredTraffic; Map.Entry<String,MonitoredTraffic> entry;

		while(iter.hasNext()) {

			entry = iter.next();
			monitoredTraffic = entry.getValue();
			if(monitoredTraffic == null) {
				log.error("Null monitoredTraffic for key " + entry.getKey());
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}

			/* Check if this is a monitored security event - dst_addr, protocol, dst_port */
			try {
				trafficMatch = monitoredTraffic.traffic.match(secEvent.dstAddress,secEvent.dpProtocol.toDFProtocol(),secEvent.dstPort);
			} catch (ExceptionControlApp e) {
				String msg = "Failed to match DP security syslog message to monitored traffics - " + secEvent.dstAddress
						+ secEvent.dpProtocol.toDFProtocol() + secEvent.dstPort;
				log.error(msg,e);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, msg);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
			if(trafficMatch == TrafficMatch.NO) continue;

			/* Record this security event. */
			fMain.getFR().logRecord(DFAppRoot.FR_AMS_SECURITY,secEvent.toString());

			/* Construct AttackReport and deliver to the DPBasedDetector. */
			/* TODO: Refine attack reporting when TrafficMatch.CONTAIN, so as to allow diversion refinements accordingly */
			long currentTime = System.currentTimeMillis() / 1000;
			AttackReport attackReport = new AttackReport(currentTime, monitoredTraffic, secEvent);
			amsRep.dpBasedDetector.handleAttackReport(attackReport);
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_PROCESS_SYSLOGS:
			backgroundProcessSyslogs();
			break;
		default:
		}
	}

	public void test() {}
}
