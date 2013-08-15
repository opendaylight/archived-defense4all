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

import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.DFAppModule;
import org.opendaylight.controlapps.defense4all.core.DFHolder;
import org.opendaylight.controlapps.defense4all.core.Detection;
import org.opendaylight.controlapps.defense4all.core.Detector;
import org.opendaylight.controlapps.defense4all.core.DetectorInfo;
import org.opendaylight.controlapps.defense4all.core.Mitigation;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort;
import org.opendaylight.controlapps.defense4all.core.Detection.DetectionConfidence;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


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

	Logger log = Logger.getLogger(this.getClass());
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
		detectorInfo = new DetectorInfo(detectorInfoStr);
		detectorInfo.ofBasedDetector = false;
		detectorInfo.externalDetector = false;
	}
	public void setLabel ( String label ) {
		detectorInfo.setLabel(label);
	}
	public void setDurationOfDetection(int duration) {this.durationOfDetection = duration;}
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {	
		super.init();
		
		DFHolder.get().getDetectorMgr().addDetector(this); // Register to support PNs with DP-based detection  
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws UnknownHostException 
	 */
	public void addMonitoredAttack(String mitigationKey) throws UnknownHostException {
		
		/* Register initial DP-based "detection" until DP indications start flowing */
		long currentTime = System.currentTimeMillis() / 1000;
		Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
		Mitigation mitigation = null;
		try {
			mitigation = new Mitigation(mitigationRow);
		} catch (Exception e1) {
			log.error("Failed both to register initial DP detection and set attack monitoring in DP", e1);
			return;
		}
		String detectionKey = Detection.generateDetectionKey(detectorInfo.label, mitigation.pnKey, mitigation.protocolPort);
		Detection detection = new Detection(detectionKey, detectorInfo.label, DetectionConfidence.VERY_HIGH, 
				currentTime, durationOfDetection, mitigation.pnKey, mitigation.protocolPort);
		
		invokeDecoupledSerially(ACTION_ADD_DETECTION, detection);
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
			System.out.println("DP attack termination syslog" + attackReport.toString());
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
		invokeDecoupledSerially(ACTION_ADD_DETECTION, detection);
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
		} catch (Exception e) {return;}
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
	
	public void fromRow(Hashtable<String, Object> row)
	{
		this.detectorInfo.fromRow(row); 
		
		if ( row.get(DPBasedDetector.DURATION_OF_DETECTION) != null )
			durationOfDetection = (Integer) row.get(DURATION_OF_DETECTION);
	
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
