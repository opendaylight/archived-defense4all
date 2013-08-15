/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Gera Goft
 * @version 0.1
 */

package com.radware.controlapps.dp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.AMSRep;
import org.opendaylight.controlapps.defense4all.core.DFAppModule;
import org.opendaylight.controlapps.defense4all.core.DFHolder;
import org.opendaylight.controlapps.defense4all.core.Mitigation;
import org.opendaylight.controlapps.defense4all.core.PN;
import org.opendaylight.controlapps.defense4all.core.Traffic;
import org.opendaylight.controlapps.defense4all.core.TrafficTuple;
import org.opendaylight.controlapps.defense4all.core.DFAppRoot.RepoMajor;
import org.opendaylight.controlapps.defense4all.core.Traffic.TrafficMatch;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.ExceptionEntityExists;
import org.opendaylight.controlapps.framework.core.ExceptionRepoFactoryInternalError;
import org.opendaylight.controlapps.framework.core.Repo;
import org.opendaylight.controlapps.framework.core.RepoFactory;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


public class DPRep extends DFAppModule implements AMSRep {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_PROCESS_SYSLOGS = 1;
	
	/**
	 * Radware DefensePro brand to be put in AMS.brand
	 */
	public static final String DP = "DefensePro";
	
	/**
	 * Name space allocation of DP Detector Repo minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
		MONITORED_TRAFFIC
	}

	protected Logger log = Logger.getLogger(this.getClass());
	protected boolean initialized = false;

	/* Rep and cache of all monitored traffics. */
	public Repo<String> monitoredTrafficRepo = null;
	protected ConcurrentHashMap<String,MonitoredTraffic> monitoredTraffics = null;

	String rsyslogDFPipe;
	File f;
	protected DPBasedDetector dpBasedDetector = null;

	/* Constructor for Spring */
	public DPRep() {
		super();
		monitoredTraffics = new ConcurrentHashMap<String,MonitoredTraffic>();
	}

	/* Setters for Spring */
	public void setDpBasedDetector(DPBasedDetector detector) {this.dpBasedDetector = detector;}
	public void setRsyslogDFPipe(String rsyslogDFPipe) {this.rsyslogDFPipe = rsyslogDFPipe;}

	/** Post-constructor initialization	 
	 * @throws ExceptionControlApp */
	public void init() throws ExceptionControlApp {

		super.init(); 
		
		// Register detector in the repo
		dpBasedDetector.init();

		/* DP Repos */
		RepoFactory rf = frameworkMain.getRepoFactory();
		String rMajor = RepoMajor.DF_AMS_REP.name();
		try {
			monitoredTrafficRepo = (Repo<String>) rf.getOrCreateRepo(rMajor, RepoMinor.MONITORED_TRAFFIC.name(), 
					StringSerializer.get(), true, MonitoredTraffic.getMonitoredTrafficRCDs());
		} catch (ExceptionRepoFactoryInternalError e) {
			throw new ExceptionControlApp("Internal framework error. ", e);
		} catch (IllegalArgumentException e) {
			throw new ExceptionControlApp("Internal framework error. ", e);
		} catch (ExceptionEntityExists e) {
			throw new ExceptionControlApp("Internal framework error. ", e);
		}

		/* Load monitored traffics */
		Hashtable<String,Hashtable<String,Object>> monitoredTrafficTable = monitoredTrafficRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = monitoredTrafficTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; MonitoredTraffic monitoredTraffic;
		while(iter.hasNext()) {
			entry = iter.next();
			try {
				monitoredTraffic = new MonitoredTraffic(entry.getValue());
			} catch (UnknownHostException e) {continue;}
			monitoredTraffics.put(monitoredTraffic.key, monitoredTraffic);
		}

		/* Initialize Linux pipe file from which to draw DP syslogs deposited by rsyslogd. */
		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.exec("mkfifo " + rsyslogDFPipe);
			runtime.exec("sudo service rsyslog restart"); // Make sure rsyslogd picks up the latest creation of DF syslog pipe
			f = new File(rsyslogDFPipe);
			addBackgroundTask(ACTION_PROCESS_SYSLOGS, null);
		} catch (Exception e) {
			log.error("Failed to open file input string " + rsyslogDFPipe, e);
			f = null;
		}

		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {

		super.finit();

		Iterator<Map.Entry<String,MonitoredTraffic>> iter = monitoredTraffics.entrySet().iterator();
		Map.Entry<String,MonitoredTraffic> entry;
		while(iter.hasNext()) {
			entry = iter.next();
			monitoredTrafficRepo.setRow(entry.getKey(), entry.getValue().toRow());
		}
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {

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

		; // Establish connection with the DP? what else?
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeAMS(String amsKey) {
		; // terminate connection with DP? what else?		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addMitigation(String mitigationKey) {
		
		Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
		Mitigation mitigation = null;
		try {
			mitigation = new Mitigation(mitigationRow);
		} catch (Exception e1) {
			log.error("Failed both to register initial DP detection and set attack monitoring in DP", e1);
			return;
		}

		/* Construct monitoredTraffic and register it in monitoredTraffics and monitoredTrafficsRepo */
		int dstAddrPrefixLen = (Integer) DFHolder.get().pNsRepo.getCellValue(mitigation.pnKey, PN.DST_ADDR_PREFIX_LEN);
		Traffic traffic = new Traffic(mitigation.dstAddr, dstAddrPrefixLen);
		traffic.addProtocolPort(mitigation.protocolPort.protocol, mitigation.protocolPort.port);
		String key = MonitoredTraffic.generateKey(mitigation.dstAddr.getHostName());	
		MonitoredTraffic monitoredTraffic = new MonitoredTraffic(key, mitigation.pnKey, mitigationKey, traffic);
		monitoredTraffics.put(key, monitoredTraffic);
		monitoredTrafficRepo.setRow(key, monitoredTraffic.toRow());

		/* Remember the key of the monitoredTraffic (to be used in removal of this monitoredTraffic) */
		mitigation.monitoredTrafficKey = key;
		dfAppRoot.mitigationsRepo.setRow(mitigationKey, mitigation.toRow());
		
		/* Now notify DPBasedDetector (which will add preliminary attackDetection, until getting syslog
		 * Triggered attack notifications and triggering attackDetection refresh. */		
		try {
			dpBasedDetector.addMonitoredAttack(mitigationKey);
		} catch (UnknownHostException e) {/* TODO: handle */}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeMitigation(String mitigationKey) {
		
		String key = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey,Mitigation.MONITORED_TRAFFIC_KEY);		
		monitoredTraffics.remove(key);
		monitoredTrafficRepo.deleteRow(key);
		
		dpBasedDetector.removeMonitoredAttack(mitigationKey);	
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

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void configureAMS(String amsKey, Properties configProps) {		
		// TODO Auto-generated method stub
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void setAMSBaselines(String amsKey, TrafficTuple baselines) {		
		// TODO Auto-generated method stub
	}

	/* #### method description ####
	 * 
	 * @param s
	 * @return
	 */
	protected void backgroundProcessSyslogs() {

		BufferedReader bufferedReader = null;
		String syslogMsg;

		while(true) {

			try {
				bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(rsyslogDFPipe)));
			} catch (FileNotFoundException e) {
				log.error("Failed to read from rsyslog pipe.", e);
				return;
			}
			try {
				syslogMsg = bufferedReader.readLine();
			} catch (IOException e) {continue;}
			if (syslogMsg == null || syslogMsg.length() == 0) continue;
			DPEvent dpEvent = DPEvent.fromString(syslogMsg);
			if (dpEvent == null) continue; // Not a DP message

			System.out.println("DP event - " + dpEvent.toString());

			/* Check if it is a security event */
			DPSecEvent dpSecurityEvent = DPSecEvent.fromString(dpEvent.msg);
			if (dpSecurityEvent != null) { // Process security event
				processSecEvent(dpSecurityEvent); 
			}

			/* Check if it is a liveness event */
			; // TODO
		}
	}

	protected void processSecEvent(DPSecEvent secEvent) {

		System.out.println("DP security event - " + secEvent.toString());

		Iterator<Map.Entry<String,MonitoredTraffic>> iter = monitoredTraffics.entrySet().iterator();
		TrafficMatch trafficMatch; MonitoredTraffic monitoredTraffic;

		while(iter.hasNext()) {

			monitoredTraffic = iter.next().getValue();

			/* Check if this is a monitored security event - dst_addr, protocol, dst_port */
			trafficMatch = monitoredTraffic.traffic.match(secEvent.dstAddress, secEvent.dpProtocol.toDFProtocol(), secEvent.dstPort);
			if(trafficMatch == TrafficMatch.NO) continue;

			/* Construct AttackReport and deliver to the DPBasedDetector. */
			/* TODO: Refine attack reporting when TrafficMatch.CONTAIN, so as to allow diversion refinements accordingly */
			long currentTime = System.currentTimeMillis() / 1000;
			AttackReport attackReport = new AttackReport(currentTime, monitoredTraffic, secEvent);
			dpBasedDetector.handleAttackReport(attackReport);
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
