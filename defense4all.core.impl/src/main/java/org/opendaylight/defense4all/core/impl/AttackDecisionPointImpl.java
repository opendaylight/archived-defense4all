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

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.AttackDecisionPoint;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.Detection;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.core.Traffic;
import org.opendaylight.defense4all.core.Attack.Status;
import org.opendaylight.defense4all.core.PN.OperationalStatus;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.Traffic.TrafficMatch;
import org.opendaylight.defense4all.core.interactionstructures.EndDetectionNotification;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;


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

	protected boolean initialized = false;

	public AttackDecisionPointImpl() {
		super();
	}

	/* Setters for Spring */
	public void setProcessAttacksPeriod(int period) { this.processAttacksPeriod = period;}

	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {

		super.init();
		log.info( "AttackDecisionPoint is starting.");
		addPeriodicExecution(ACTION_PROCESS_ATTACKS, null, processAttacksPeriod);
		initialized = true;
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		log.info( "AttackDecisionPoint is stopping.");
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public synchronized void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		log.info( "AttackDecisionPoint is resetting to level " + resetLevel);
		super.reset(resetLevel);
		Traffic.NameHash.reset();
		try {
			dfAppRootFullImpl.detectionsRepo.truncate();
			dfAppRootFullImpl.attacksRepo.truncate();
		} catch (Throwable e) {
			log.error("Failed to reset detectionsRepo and/or attacksRepo", e );
		}
	}

	/** Test 
	 * @throws ExceptionControlApp */
	public void test() throws ExceptionControlApp {
		test2();
	}

	protected void test2() throws ExceptionControlApp {

		Attack attack = new Attack("pn1_TCP:0", "pn1", new ProtocolPort(DFProtocol.TCP, 0), Status.DECLARED, new Properties());
		attack.detectionKeys.setProperty("df_detectorpn1.TCP:0", "df_detectorpn1.TCP:0");
		dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());

		Attack check;

		long start = System.currentTimeMillis() / 1000;
		for(int i=0;i<10000;i++) {
			int j = ((int) Math.random()) % 4;
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
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public synchronized void addDetection(Detection detection) {

		/* Record the detection in detections repo */	
		if (detection== null || detection.key == null ) {
			log.error("Trying to add null detection");
			return;
		}

		/* Check if the PN is failed or canceled */
		try {
			Object obj = dfAppRootFullImpl.pNsRepo.getCellValue(detection.pnKey, PN.OPERATIONAL_STATUS);
			OperationalStatus operationalStatus = OperationalStatus.INVALID;
			if(obj != null)	operationalStatus = OperationalStatus.valueOf((String) obj);
			if(operationalStatus == OperationalStatus.CANCELED || operationalStatus == OperationalStatus.FAILED) return;
		} catch (Throwable e) {
			log.error("Failed to retrieve operational status value for " + detection.pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		log.info("DF is processing detection "+detection.toString());

		try {
			dfAppRootFullImpl.detectionsRepo.setRow(detection.key, detection.toRow());
		} catch (ExceptionControlApp e) {
			log.error("Failed to add detetection to detectionsRepo. Detection key: "+detection.key, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE,"DefenseFlow failed to process detection "+Detection.getPrintableDetectionTarget(detection.key));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Record the detection also in attacks repo. Create new attack if no matching attack exists yet. */
		Attack attack = findMatchingAttack(detection);
		String newAttackKey = Attack.generateKey(detection.pnKey, detection.protocolPort);
		if(attack == null) {
			attack = new Attack(newAttackKey, detection.pnKey, detection.protocolPort, Status.SUSPECTED, null);
			fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow declaring a new attack on " +attack.getPrintableAttackTarget());
		}
		switch(attack.status) {
		case DECLARED:
			log.info( "DF is adding detection " + detection.key 
					+ " to existing attack " + attack.key);
			break;
		case SUSPECTED:
			break;
		case ENDED:
			try {
				dfAppRootFullImpl.attacksRepo.deleteRow(attack.key); // Attack can be deleted - cleanup completed
			} catch (Throwable e) {
				log.error("Failed to delete attack from attacksRepo. " +attack.key, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			attack = new Attack(newAttackKey, detection.pnKey, detection.protocolPort, Status.SUSPECTED, null);
			fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow declaring a new attack on " +Detection.getPrintableDetectionTarget( newAttackKey));
			log.info("DF is declaring a new attack " + newAttackKey);
			break;
		case ENDING:
			log.info(detection.key + " is of an \"ending attack\". "
					+ "DF is ignoring new detections until the attack is fully ended.");
			return;    // Ignore new detections until ended attack is deleted
		default:
			return;
		}
		attack.detectionKeys.setProperty(detection.key, detection.key);	
		try {
			dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
		} catch (Throwable e) {
			log.error("Failed to create attack in the attacksRepo. " +attack.key, e);
			fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow  fail to process attack on " +Detection.getPrintableDetectionTarget( attack.key));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Invoke the processAttacks now. (Never mind if fails - it is invoked periodically anyway). */
		try {
			invokeDecoupledSerially(ACTION_PROCESS_ATTACKS, null);	// Invoke to check status of all attacks (process all detections)
		} catch (Throwable e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_PROCESS_ATTACKS , e);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected Attack findMatchingAttack(Detection detection) {

		Attack attack; PN pn;
		try {
			pn = new PN(dfAppRootFullImpl.pNsRepo.getRow(detection.pnKey));
		} catch (Throwable e1) {
			log.error("Failed to inflate PN from repo for detection: " + detection.key + "PN key:" + detection.pnKey, e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return null;
		}

		Traffic comparedAttackTraffic = new Traffic(0, pn.dstAddr, pn.dstAddrPrefixLen); //vlan=0 - not important. same PN compared
		Hashtable<String,Hashtable<String,Object>> attackTable = dfAppRootFullImpl.attacksRepo.getTable();		
		if(attackTable == null) {
			log.error("Retrieved null attacksTable.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			return null;
		}

		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = attackTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; 
		Hashtable<String,Object> attackRow; TrafficMatch trafficMatch;

		while(iter.hasNext()) {

			entry = iter.next();
			try {
				attackRow = entry.getValue();
				attack = new Attack(attackRow);
			} catch (Throwable e) {
				log.error("Excepted trying to inflate attack from row. " , e);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}

			if(!attack.pnKey.equals(detection.pnKey)) continue;

			/* Matched pnKey means matched dstAddr and dstAddrPrefixLen, so match proto-port */
			comparedAttackTraffic.setProtocolPort(attack.protocolPort.protocol,attack.protocolPort.port);
			try {
				trafficMatch = comparedAttackTraffic.match(0, pn.dstAddr, detection.protocolPort.protocol, 
						detection.protocolPort.port); // vlan = 0 is not important here, because comparison is for the same PN
			} catch (Throwable e) {
				log.error("Excepted in match operation for attack traffic: "+ pn.dstAddr.getHostAddress()+":"+
						detection.protocolPort.protocol+"."+ detection.protocolPort.port, e);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
			if(trafficMatch == TrafficMatch.NO) continue;

			return attack; // Found matching attack
		}

		return null;	// Did not find matching attack
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public synchronized void removeDetection(String detectionKey) {

		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow removing detection for  " + Detection.getPrintableDetectionTarget(detectionKey));
		log.info("DF is removing detection " + detectionKey);

		Hashtable<String, Object> detectionRow;
		try {
			detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to get detetectionRow from detectionsRepo. Detection key: "+detectionKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove detection for " +Detection.getPrintableDetectionTarget(detectionKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		if(detectionRow == null) return;
		Detection detection = new Detection(detectionRow);

		/* Remove the detection also in attacks repo - not very efficient, but clear */
		try {
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
		} catch (Throwable e) {
			log.error("Failed to delete detection from the attack. Detection key: "+detection.key, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Remove the detection from detections repo, but hold on to it for removal from attacks repo */		
		try {
			dfAppRootFullImpl.detectionsRepo.deleteRow(detectionKey);
		} catch (ExceptionControlApp e1) {
			log.error("Failed to delete detetectionRow from detectionsRepo. Detection key: "+detectionKey, e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		// Piggyback processing. For possible name changes
		Traffic.NameHash.reset();
		try {
			invokeDecoupledSerially(ACTION_PROCESS_ATTACKS, null);	// To process all detections.
		} catch (Throwable e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_PROCESS_ATTACKS, e);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) throws ExceptionControlApp {

		log.info( "DF is processing PN removal with respect to ongoing attacks "
				+ pnKey);
		try {
			invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);	
		} catch (ExceptionControlApp e) {
			String msg = "Excepted trying to invokeDecoupledSerialiy " + ACTION_REMOVE_PN + " " + pnKey;
			log.error(msg, e);
			throw new ExceptionControlApp(msg, e);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected synchronized void decoupledRemovePN(String pnKey)  {

		Hashtable<String,Hashtable<String,Object>> attackTable = dfAppRootFullImpl.attacksRepo.getTable();
		if (attackTable  == null ) return;
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = attackTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; Hashtable<String,Object> attackRow;
		String attackPnkey;

		/* Check if there are any attacks for this PN, and end them */
		while(iter.hasNext()) {
			try {
				entry = iter.next();
				attackRow = entry.getValue();
				attackPnkey = (String) attackRow.get(Attack.PNKEY);
				if(attackPnkey.equals(pnKey)) {
					String msg = "DefenseFlow ending attack for removed PO  " + PN.getPrintableKey(pnKey);
					fr.logRecord(DFAppRoot.FR_DF_SECURITY, msg);
					endAttack(new Attack(attackRow));
				}
			} catch (ExceptionControlApp e) {
				log.error("Failed to process endAttack in decoupledRemovePN ", e);
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
	protected synchronized void periodicProcessAttacks() {

		if(!fMain.isOpenForBusiness()) return; // Operate only after everything is initialized and is not terminating

		List<String> attackKeys = null;
		try {
			attackKeys = dfAppRootFullImpl.attacksRepo.getKeys();
		} catch (ExceptionControlApp e) {
			log.error("Failed to get keys from attacksRepo", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
		if ( attackKeys == null ) return;
		for(String attackKey : attackKeys) {
			try {
				processAttack(attackKey);
			} catch (ExceptionControlApp e) {
				log.error("Failed in decoupledProcessAttacks. Attack key: "+attackKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}
	}

	protected void processAttack(String attackKey) throws ExceptionControlApp {

		Hashtable<String, Object> attackRow;
		try {
			attackRow = dfAppRootFullImpl.attacksRepo.getRow(attackKey);
		} catch (Throwable e) {
			log.error("Failed to get attackRow from attacksRepo. Attack key: "+attackKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp ("Failed to get attackRow from attacksRepo. Attack key: "+attackKey, e);

		}
		if(attackRow == null) return;

		Attack attack = new Attack(attackRow);
		if(attack.status == Status.ENDING) 
			return;
		if(attack.status == Status.ENDED) {
			try {
				dfAppRootFullImpl.attacksRepo.deleteRow(attackKey); // Attack can be deleted - cleanup completed
			} catch (Exception e) {
				log.error("Failed to delete attackRow from attacksRepo. Attack key: "+attackKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp ("Failed to delete attackRow from attacksRepo. Attack key: "+attackKey, e);
			}
			return;
		}

		Detection detection; long detectionExpiration; Hashtable<String,Object> detectionRow;
		long currentTime = System.currentTimeMillis() / 1000;
		long latestDetectionExpiration = currentTime;
		Set<Entry<Object, Object>> attackSet = attack.detectionKeys.entrySet();
		if ( attackSet == null ) return;
		Iterator<Map.Entry<Object,Object>> iter	= attackSet.iterator();
		String detectionKey;

		while(iter.hasNext()) {

			detectionKey = (String) iter.next().getKey();
			try {
				detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey);
			} catch (Throwable e) {
				log.error("Failed to get detectionRow from detectionsRepo. Detection key: "+detectionKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}

			if(detectionRow == null) continue;

			detection = new Detection(detectionRow);
			detectionExpiration = detection.setTime + detection.duration;
			if(detectionExpiration > latestDetectionExpiration) {
				latestDetectionExpiration = detectionExpiration;
			}
		}

		log.debug("Attack: "+attackKey+" Expiration: "+ latestDetectionExpiration+ " Current time: "+currentTime);
		if(latestDetectionExpiration <= currentTime && attack.status != Status.ENDING && attack.status != Status.ENDED) {
			log.info("processAttack: ending attack: " + attack.toString());
			endAttack(attack); // The attack should be ended
		} else if(attack.status != Status.DECLARED) { // New attack
			declareAttack(attackKey);
		}

		log.debug("attack detection keys: " + attack.detectionKeys.toString());
		try {
			log.debug("attack repo detection keys: " + ((Properties) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.DETECTION_KEYS)).toString());
		} catch (Exception e) {} // ignore

		// Else ongoing attack - nothing to be done
	}

	protected void declareAttack(String attackKey) throws ExceptionControlApp {

		// same message twice
		//fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow declaring new attack on " + Attack.getPrintableAttackTarget(attackKey));
		
		try {
			// clean-up counters from previous attacks 	
			dfAppRootFullImpl.detectorMgrImpl.cleanup();
		} catch ( Throwable e) {
			log.error("Failed to initiate detector manager cleanup", e);
			// ignore
		}; 

		try {
			dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.STATUS, Status.DECLARED.name());
		} catch (ExceptionControlApp e1) {
			log.error("Failed to update attack status in attacksRepo "+attackKey, e1);
			fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow failed to process attack on " + Attack.getPrintableAttackTarget(attackKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp ("Failed to update attack status in attacksRepo "+attackKey, e1);
		}

		dfAppRootFullImpl.mitigationMgrImpl.mitigate(attackKey);

		// Update attack row 
	}

	protected void endAttack(Attack attack) throws ExceptionControlApp {

		if(attack.status != Status.DECLARED) return;
		
		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow ending the attack on " + Attack.getPrintableAttackTarget(attack.key));

		attack.status = Status.ENDING;
		List<String> mitigationTFKeys = dfAppRootFullImpl.mitigationMgrImpl.getMitigationTrafficFloorKeys(attack.key);		
		Set<Entry<Object, Object>> detectionSet = attack.detectionKeys.entrySet();

		if ( mitigationTFKeys != null && detectionSet != null ) {

			EndDetectionNotification endDetectionNotification = new EndDetectionNotification();
			endDetectionNotification.trafficFloorKeys = mitigationTFKeys;

			Iterator<Map.Entry<Object,Object>> iter	= detectionSet.iterator();
			Map.Entry<Object,Object> entry; 
			String detectionKey; Hashtable<String,Object> detectionRow; Detection detection;
			while(iter.hasNext()) {

				entry = iter.next();
				detectionKey = (String) entry.getKey();
				try {
					detectionRow = dfAppRootFullImpl.detectionsRepo.getRow(detectionKey);
					detection = new Detection(detectionRow);
					endDetectionNotification.detection = detection;
					dfAppRootFullImpl.detectorMgrImpl.notifyEndDetection(endDetectionNotification);
					dfAppRootFullImpl.detectionsRepo.deleteRow(detectionKey);
				} catch (Throwable e) {
					log.error("Failed to end detection for attack. "+detectionKey);
					//				// don't process next - it will be retry on next circle
					//				throw new ExceptionControlApp("Failed to end detection for attack. "+detectionKey); 
				}
				iter.remove();
			}	
		}

		try {
			dfAppRootFullImpl.attacksRepo.setRow(attack.key, attack.toRow());
		} catch (Exception e) {
			log.error("Failed to update attack in attacksRepo. "+attack.key);
			fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow failed to end attack on " + Attack.getPrintableAttackTarget(attack.key));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			// don't process next - it will be retry on next circle
			throw new ExceptionControlApp("Failed to update attack in attacksRepo. "+attack.key);
		}

		try {
			dfAppRootFullImpl.mitigationMgrImpl.endMitigation(attack.key);
		} catch (Exception e) {
			//			log.error("Excepted trying to request endMitigation from mitigation manager. "+attack.key ); 
			//			throw new ExceptionControlApp("Excepted trying to request endMitigation from mitigation manager. "+attack.key );
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_PROCESS_ATTACKS:
			periodicProcessAttacks();
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		default:
		}
	}
}
