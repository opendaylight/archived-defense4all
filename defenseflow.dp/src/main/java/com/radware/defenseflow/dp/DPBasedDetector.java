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

import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.Detection;
import org.opendaylight.defense4all.core.Detector;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.Detection.DetectionConfidence;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.HealthTracker;

public class DPBasedDetector extends DFAppModule implements Detector {

	/**
	 * DPBasedDetectionEngine Repo types
	 */
	public static final int TYPE_INVALID = -1;
	public static final int TYPE_RESERVED = 0;

	/**
	 * Minimal time to keep the diversion going so the attack is not mistakingly ended in case DP is out for some time
	 */
	public static final int MINIMAL_ATTACK_DURATION = 600;

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_ADD_DETECTION = 1;

	Logger log = LoggerFactory.getLogger(this.getClass());
	protected boolean initialized = false;
	DetectorInfo detectorInfo; 
	protected DPRep dpRep = null;
	protected int durationOfDetection = 300;  

	/* Detectors Repo column names */
	public static final String DURATION_OF_DETECTION = "duration_of_detection";

	public DPBasedDetector() {
		super();
	}

	/* Setters for Spring */
	public void setDpRep(DPRep dpRep) {this.dpRep = dpRep;}
	public void setDetectorInfoStr(String detectorInfoStr) {
		try {
			detectorInfo = new DetectorInfo(detectorInfoStr);
		} catch (IllegalArgumentException e) {
			log.error("Incorrect detectorInfoStr " + detectorInfoStr);
		}
		detectorInfo.ofBasedDetector = false;
		detectorInfo.externalDetector = false;
	}
	public void setLabel ( String label ) {
		detectorInfo.setLabel(label);
	}
	public void setDurationOfDetection(int duration) {this.durationOfDetection = duration;}

	/** Post-constructor initialization	 
	 * @throws Exception */
	public void init() throws ExceptionControlApp {	
		super.init();

		try {
			DFHolder.get().getDetectorMgr().addDetector(this); // Register to support PNs with DP-based detection  
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to register the dpBasedDetector at detectors manager.", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw e;
		}
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		super.reset(resetLevel);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws UnknownHostException 
	 */
	public void addMonitoredAttack(String mitigationKey) {

		/* Register initial DP-based "detection" until DP indications start flowing */
		long currentTime = System.currentTimeMillis() / 1000;
		Hashtable<String, Object> mitigationRow; Mitigation mitigation;
		try {
			mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
			mitigation = new Mitigation(mitigationRow);
		} catch (Throwable e) {
			log.error("Excepted trying to retrieve mitigationRow or inflate mitigation " + mitigationKey, e);
			//FMHolder.get().getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to add monitored through DPs traffic "
			//		+ "for mitigation "	+ mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			return;
		}
		String detectionKey = Detection.generateDetectionKey(detectorInfo.label, mitigation.pnKey, mitigation.protocolPort);
		Detection detection = new Detection(detectionKey, detectorInfo.label, DetectionConfidence.VERY_HIGH, 
				currentTime, durationOfDetection, mitigation.pnKey, mitigation.protocolPort);

		try {
			invokeDecoupledSerially(ACTION_ADD_DETECTION, detection);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_ADD_DETECTION + " " + detectionKey, e);
			return;
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeMonitoredAttack(String mitigationKey) {	
		; // No op
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) {	
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) {		
	}

	protected void decoupledAddDetection(Detection detection) {
		dfAppRoot.getAttackDecisionPoint().addDetection(detection);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void handleAttackReport(AttackReport attackReport) {

		/* Check attack status. Can be occur, start, ongoing, sampled, term. Anything except "term" will trigger
		 * attack detection update. */
		if(attackReport.dpSecEvent.attackStatus.equals("term")) {
			log.info("DP attack termination syslog" + attackReport.toString());
			fMain.getFR().logRecord(DFAppRoot.FR_DF_SECURITY, "Received \"attack terminated\" indication from DP.");
			return; // Fade out attack detection
		}

		/* If there is already a diversion for this attack then update its runtime properties */
		updateDvsnExecutionProps(attackReport.monitoredTraffic.mitigationKey, attackReport.dpSecEvent);

		/* Tell AttackDecisionPoint that the attack is still on */
		String pnKey = attackReport.monitoredTraffic.pnKey;
		DPSecEvent dpSecEvent = attackReport.dpSecEvent;
		ProtocolPort protocolPort = new ProtocolPort(dpSecEvent.dpProtocol.toDFProtocol(), dpSecEvent.dstPort);
		String detectionKey = Detection.generateDetectionKey(detectorInfo.label, pnKey, protocolPort);
		Detection detection = new Detection(detectionKey, detectorInfo.label, DetectionConfidence.VERY_HIGH,
				attackReport.reportTime, durationOfDetection, pnKey, protocolPort);
		try {
			invokeDecoupledSerially(ACTION_ADD_DETECTION, detection);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_ADD_DETECTION + " " + detectionKey, e);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void updateDvsnExecutionProps(String mitigationKey, DPSecEvent dpSecEvent) {

		/* All the premature returns are because there is apparently no diversion (yet) for this attack)*/
		if(mitigationKey == null) return;
		Properties dvsnExecProps = null;
		
		try {
			
			dvsnExecProps = (Properties) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.MITIGATION_EXECUTION_PROPS);
			if(dvsnExecProps == null) return;

			/* Set the latest diversion execution properties */
			dvsnExecProps.setProperty(DPSecEvent.SEVERITY_PROPERTY, dpSecEvent.severity);
			dvsnExecProps.setProperty(DPSecEvent.RADWARE_EVENT_TYPE_ID_PROPERTY, String.valueOf(dpSecEvent.rdwrEventTypeId));
			dvsnExecProps.setProperty(DPSecEvent.CATEGORY_PROPERTY, dpSecEvent.category);
			dvsnExecProps.setProperty(DPSecEvent.ATTACK_NAME_PROPERTY, dpSecEvent.attackName);
			dvsnExecProps.setProperty(DPSecEvent.CONTEXT_PROPERTY, dpSecEvent.context);
			dvsnExecProps.setProperty(DPSecEvent.POLICY_NAME_PROPERTY, dpSecEvent.policyName);
			dvsnExecProps.setProperty(DPSecEvent.ATTACK_STATUS_PROPERTY, dpSecEvent.attackStatus);
			dvsnExecProps.setProperty(DPSecEvent.PACKET_COUNT_PROPERTY, String.valueOf(dpSecEvent.packetCount));
			dvsnExecProps.setProperty(DPSecEvent.ATTACK_BANDWIDTH_PROPERTY, String.valueOf(dpSecEvent.attackBandwidth));
			dvsnExecProps.setProperty(DPSecEvent.RISK_PROPERTY, dpSecEvent.risk);
			dvsnExecProps.setProperty(DPSecEvent.ACTION_PROPERTY, dpSecEvent.action);
			dvsnExecProps.setProperty(DPSecEvent.EVENT_GUID_PROPERTY, dpSecEvent.eventGuid);

			/* Set the updated diversion execution properties back to repo */
			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_EXECUTION_PROPS, dvsnExecProps);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to updateDvsnExecutionProps for " + mitigationKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
	}

	public DetectorInfo getDetectorInfo() {return detectorInfo;}
	public String getDetectorInfoStr() {return detectorInfo.toString();}

	/**
	 * Compose detector repository row
	 */
	public Hashtable<String, Object> toRow() {
		Hashtable<String, Object> row = detectorInfo.toRow();
		row.put(DPBasedDetector.DURATION_OF_DETECTION ,durationOfDetection);
		return row;
	}

	public void fromRow(Hashtable<String, Object> row) {
		this.detectorInfo.fromRow(row);
		Object durationOfDetectionObj = row.get(DPBasedDetector.DURATION_OF_DETECTION);
		if(durationOfDetectionObj != null) durationOfDetection = (Integer) durationOfDetectionObj;
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_ADD_DETECTION:
			decoupledAddDetection((Detection) param);
			break;
		default:
			break;
		}		
	}
}
