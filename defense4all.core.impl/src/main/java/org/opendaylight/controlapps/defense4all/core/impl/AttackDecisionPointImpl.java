/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core.impl;

import java.net.UnknownHostException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.Attack;
import org.opendaylight.controlapps.defense4all.core.AttackDecisionPoint;
import org.opendaylight.controlapps.defense4all.core.Detection;
import org.opendaylight.controlapps.defense4all.core.Detector;
import org.opendaylight.controlapps.defense4all.core.PN;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort;
import org.opendaylight.controlapps.defense4all.core.Traffic;
import org.opendaylight.controlapps.defense4all.core.Attack.Status;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.controlapps.defense4all.core.Traffic.TrafficMatch;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


public class AttackDecisionPointImpl extends DFAppCoreModule implements AttackDecisionPoint {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_PROCESS_ATTACKS = 1;
	protected static final int ACTION_REMOVE_PN = 2;
	
	/* Use the values set below if not set anywhere else */
	protected long processAttacksPeriod = 1000;

	Logger log = Logger.getLogger(this.getClass());
	protected boolean initialized = false;

	public AttackDecisionPointImpl() {
		super();
	}
	
	/* Setters for Spring */
	public void setProcessAttacksPeriod(int period) { this.processAttacksPeriod = period;}
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {
		
		super.init();
		addPeriodicExecution(ACTION_PROCESS_ATTACKS, null, processAttacksPeriod);
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {		
		super.reset(resetLevel);		
		dfAppRootFullImpl.detectionsRepo.truncate();
		dfAppRootFullImpl.attacksRepo.truncate();
	}

	/** Test */
	public void test() {
		test2();
	}
	
	protected void test2() {

		Attack attack = new Attack("pn1_TCP:0", "pn1", new ProtocolPort(DFProtocol.TCP, 0), Status.DECLARED, new Properties());
		attack.detectionKeys.setProperty("df_detectorpn1.TCP:0", "df_detectorpn1.TCP:0");
		dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
		
		Attack check;

		long start = System.currentTimeMillis() / 1000;
		for(int i=0;i<10000;i++) {
			int j = ((int) Math.random()) % 4;
//			System.out.println(i);
			switch(j) {
				case 0:
					attack = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					attack.detectionKeys.setProperty("df_detectorpn1.TCP:0", "df_detectorpn1.TCP:0");
					dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
					check = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					if(attack.detectionKeys.size() != check.detectionKeys.size())
						System.out.println(attack.detectionKeys.toString() + "; " + check.detectionKeys.toString());
					break;
				case 1:
					attack = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					attack.detectionKeys.setProperty("dp_based_detectorpn1.TCP:0", "dp_based_detectorpn1.TCP:0");
					dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
					check = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					if(attack.detectionKeys.size() != check.detectionKeys.size())
						System.out.println(attack.detectionKeys.toString() + "; " + check.detectionKeys.toString());
					break;
				case 2:
					attack = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					attack.detectionKeys.setProperty("dp_based_detectorpn1.TCP:80", "dp_based_detectorpn1.TCP:80");
					dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
					check = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					if(attack.detectionKeys.size() != check.detectionKeys.size())
						System.out.println(attack.detectionKeys.toString() + "; " + check.detectionKeys.toString());
					break;
				case 3:
					attack = new Attack(dfAppRootFullImpl.attacksRepo.getRow("pn1_TCP:0"));
					attack.detectionKeys.clear();
					dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
					System.out.println("Cleared detectionKeys");
					break;
				default:
						break;
			}
		}
		long end = System.currentTimeMillis() / 1000;
		System.out.println("time = " + (end-start));
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public synchronized void addDetection(Detection detection) {

		/* Record the detection in detections repo */		
		dfAppRootFullImpl.detectionsRepo.setRow(detection.key, detection.toRow());

		/* Record the detection also in attacks repo. First look if there is already an attack containing the detection. */

		PN pn;
		try {
			pn = new PN(dfAppRootFullImpl.pNsRepo.getRow(detection.pnKey));
		} catch (UnknownHostException e) {return;}
		
		Traffic traffic = new Traffic(pn.dstAddr, pn.dstAddrPrefixLen);
		
		Hashtable<String,Hashtable<String,Object>> attackTable = dfAppRootFullImpl.attacksRepo.getTable();		
		Hashtable<String,Object> attackRow; boolean foundMatchingAttack = false; Attack attack = null; TrafficMatch trafficMatch;
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = attackTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; String attackKey = null;

		while(iter.hasNext()) {
			
			entry = iter.next();
			attackRow = entry.getValue();
			attack = new Attack(attackRow);
			if(!attack.pnKey.equals(detection.pnKey)) 
				continue;
			
			/* Matched pnKey means matched dstAddr and dstAddrPrefixLen, so match proto-port */
			traffic.setProtocolPort(attack.protocolPort.protocol,attack.protocolPort.port);
			trafficMatch = traffic.match(pn.dstAddr, detection.protocolPort.protocol, detection.protocolPort.port);
			if(trafficMatch == TrafficMatch.NO) 
				continue;
			
			/* This attack either contains or matches the subject detection */
			foundMatchingAttack = true;
			attackKey = entry.getKey();
			
			if(attack!= null && attackKey!= null) {
				Properties dKeys = ((Properties) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.DETECTION_KEYS));
				if(attack.detectionKeys.size() != dKeys.size()) {
					System.out.println("attack detection keys 2: " + attack.detectionKeys.toString());
					System.out.println("attack repo detection keys 2: " + dKeys.toString());
					System.out.println("DIFFERENT SIZE!");
				}
			}
			
			break;
		}
		

		if(foundMatchingAttack) {
			switch(attack.status) {
				case DECLARED:
				case SUSPECTED:
					break;
				case ENDED:
					dfAppRootFullImpl.attacksRepo.deleteRow(attackKey); // Attack can be deleted - cleanup completed
					attackKey = Attack.generateKey(detection.pnKey, detection.protocolPort);
					attack = new Attack(attackKey, detection.pnKey, detection.protocolPort, Status.SUSPECTED, null);
					break;
				case ENDING: 
					return;    // Ignore new detections until ended attack is deleted
				default:
					return;
			}
		} else {
			attackKey = Attack.generateKey(detection.pnKey, detection.protocolPort);
			attack = new Attack(attackKey, detection.pnKey, detection.protocolPort, Status.SUSPECTED, null);
		}

		attack.detectionKeys.setProperty(detection.key, detection.key);	
		dfAppRootFullImpl.attacksRepo.setRow(attackKey, attack.toRow());
		System.out.println("Adding " + detection.detector + " detection at " + System.currentTimeMillis() / 1000
				+ ", attack detection keys: " + attack.detectionKeys.toString());

		invokeDecoupledSerially(ACTION_PROCESS_ATTACKS, null); // Invoke to check status of all attacks (process all detections)
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public synchronized void removeDetection(String detectionKey) {
		
		/* Remove the detection from detections repo, but hold on to it for removal from attacks repo */
		Hashtable<String,Object> detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey);
		if(detectionRow == null) return;
		Detection detection = new Detection(detectionRow);
		dfAppRootFullImpl.detectionsRepo.deleteRow(detectionKey);

		/* Remove the detection also in attacks repo - not very efficient, but clear */
		Hashtable<String,Hashtable<String,Object>> attackTable = dfAppRootFullImpl.attacksRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = attackTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; Properties detectionKeys;		
		while(iter.hasNext()) {
			entry = iter.next();
			detectionKeys = (Properties) entry.getValue().get(Attack.DETECTION_KEYS);
			if(detectionKeys == null) continue; 
			detectionKeys.remove(detection.key);
			dfAppRootFullImpl.attacksRepo.setCell(entry.getKey(), Attack.DETECTION_KEYS, detectionKeys);
		}

		invokeDecoupledSerially(ACTION_PROCESS_ATTACKS, null); // To process all detections.
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public synchronized void removePN(String pnKey) {
		invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void decoupledRemovePN(String pnKey) {

		Hashtable<String,Hashtable<String,Object>> attackTable = dfAppRootFullImpl.attacksRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = attackTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; Hashtable<String,Object> attackRow;
		String attackPnkey;
		
		/* Check if there are any attacks for this PN, and end them */
		while(iter.hasNext()) {
			
			entry = iter.next();
			attackRow = entry.getValue();
			attackPnkey = (String) attackRow.get(Attack.PNKEY);
			if(attackPnkey.equals(pnKey))			
				endAttack(new Attack(attackRow));
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void decoupledProcessAttacks() {
		
		List<String> attackKeys = dfAppRootFullImpl.attacksRepo.getKeys();
		for(String attackKey : attackKeys)
			processAttack(attackKey);
	}

	protected void processAttack(String attackKey) {
		
		Hashtable<String,Object> attackRow = dfAppRootFullImpl.attacksRepo.getRow(attackKey);
		if(attackRow == null) return;
		
		Attack attack = new Attack(attackRow);
		if(attack.status == Status.ENDING) 
			return;
		if(attack.status == Status.ENDED) {
			dfAppRootFullImpl.attacksRepo.deleteRow(attackKey); // Attack can be deleted - cleanup completed
			return;
		}
		
		Detection detection; long detectionExpiration; Hashtable<String,Object> detectionRow;
		long currentTime = System.currentTimeMillis() / 1000;
		long latestDetectionExpiration = currentTime;
		
		System.out.println("processAttack " + attack.key + ": currentTime = " + currentTime + 
				", detection keys = " + attack.detectionKeys.toString());
		
		Iterator<Map.Entry<Object,Object>> iter = attack.detectionKeys.entrySet().iterator();
		String detectionKey;
		while(iter.hasNext()) {
			
			detectionKey = (String) iter.next().getKey();
			detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey);

			if(detectionRow == null) 
				continue;
			
			detection = new Detection(detectionRow);
			
			detectionExpiration = detection.setTime + detection.duration;
			
			System.out.println("processAttack: detector = " + detection.detector + " detection set time = " + detection.setTime
					+ ", detection duration = " + detection.duration + ", detectionExpiration = " + detectionExpiration);
			
			if(detectionExpiration > latestDetectionExpiration) {
				latestDetectionExpiration = detectionExpiration;
			}
		}
		
		if(latestDetectionExpiration <= currentTime) { // The attack should be ended
			System.out.println("processAttack: ending attack: " + attack.toString());
			endAttack(attack);
		} else if(attack.status != Status.DECLARED) { // New attack
			declareAttack(attackKey);
		}
		
		System.out.println("attack detection keys: " + attack.detectionKeys.toString());
		System.out.println("attack repo detection keys: " + ((Properties) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.DETECTION_KEYS)).toString());
		// Else ongoing attack - nothing to be done
	}
	
	protected void declareAttack(String attackKey) {

		System.out.println("processAttack: declaring attack on " + attackKey);
		
		dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.STATUS, Status.DECLARED.name());
		dfAppRootFullImpl.mitigationMgrImpl.mitigate(attackKey);
	}
	
	protected synchronized void endAttack(Attack attack) {
		
		if(attack.status != Status.DECLARED) return;		
		
		Iterator<Map.Entry<Object,Object>> iter = attack.detectionKeys.entrySet().iterator();
		Map.Entry<Object,Object> entry; String detectionKey; String detectorLabel;
		
		while(iter.hasNext()) {
			
			entry = iter.next();
			detectionKey = (String) entry.getKey();
			dfAppRootFullImpl.detectorMgrImpl.notifyEndDetection(detectionKey);
			
			dfAppRootFullImpl.detectionsRepo.deleteRow(detectionKey);
			iter.remove();
		}
		
		attack.status = Status.ENDING;
		dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
		dfAppRootFullImpl.mitigationMgrImpl.endMitigation(attack.key);
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
			case ACTION_RESERVED:
				break;
			case ACTION_PROCESS_ATTACKS:
				decoupledProcessAttacks();
				break;
			case ACTION_REMOVE_PN:
				decoupledRemovePN((String) param);
				break;
			default:
		}
	}
}
