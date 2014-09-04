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

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFAppRoot.HealthStatus;
import org.opendaylight.defense4all.core.MitigationMgr;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.Mitigation.Status;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PN.MitigationScope;
import org.opendaylight.defense4all.core.PN.OperationalStatus;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class MitigationMgrImpl extends DFAppCoreModule implements MitigationMgr {

	protected class MitigationInfo {
		String mitigationKey;
		MitigationDriver driver;
		MitigationInfo(String mitigationKey, MitigationDriver driver) {
			this.mitigationKey = mitigationKey; this.driver = driver;
		}
	}

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_ADD_PN = 1;
	protected static final int ACTION_REMOVE_PN = 2;
	protected static final int ACTION_MITIGATE = 3;
	protected static final int ACTION_END_MITIGATION = 4;
	protected static final int ACTION_RETRY_MITIGATION = 5;


	protected long retryIntervalInSecs = 60; // Period to check all Vexs and vbrs health, if not set anywhere else
	/* Constructor for Spring */
	public MitigationMgrImpl() {
		super();
	}

	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {
		super.init();
		log.info( "MitigationMgr is starting.");

		addPeriodicExecution(ACTION_RETRY_MITIGATION, null, retryIntervalInSecs);	
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		log.info( "MitigationMgr is stopping.");

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.finit();
		}
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public synchronized void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		log.info( "MitigationMgr is resetting to level " + resetLevel);
		super.reset(resetLevel);

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			try {
				mitigationDriver.reset(resetLevel);
			} catch (Throwable e) {
				log.error("Excepted in reset mitigationDriver :"+mitigationDriver.getLabel(), e );
				continue;
			}
		}

		try {
			dfAppRootFullImpl.mitigationsRepo.truncate();
		}  catch (Throwable e) {
			log.error("Failed to reset mitigationsRepo", e );
		}
	}

	public ArrayList<MitigationDriver> getMitigationDrivers() {return dfAppRootFullImpl.mitigationDrivers;}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp  {
		try {
			invokeDecoupledSerially(ACTION_ADD_PN, pnKey);
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_ADD_PN + " " + pnKey, e);
			throw e;
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected synchronized void decoupledAddPN(String pnKey) {

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {

			try {
				mitigationDriver.addPN(pnKey);
			} catch (Throwable e) {
				log.error("Excepted in adding PN to mitigationDriver :"+mitigationDriver.getLabel()+" PN "+pnKey, e );
				try {
					dfAppRootFullImpl.pNsRepo.setCell(pnKey, PN.OPERATIONAL_STATUS, PN.OperationalStatus.FAILED.name());
				} catch (Throwable e1) {}; // ignore
				continue;
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
	public void removePN(String pnKey) throws ExceptionControlApp {
		try {
			invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);	
		} catch (ExceptionControlApp e) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_REMOVE_PN + " " + pnKey, e);
			throw e;
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected synchronized void decoupledRemovePN(String pnKey) {
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			try {
				mitigationDriver.removePN(pnKey);
			} catch (Throwable e) {
				log.error("Excepted in removing PN from mitigationDriver :"+mitigationDriver.getLabel()+" PN "+pnKey, e );
				continue;
			}
		}
	}

	public synchronized void addNetNode(String netNodeKey) {	

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			try {
				mitigationDriver.addNetNode(netNodeKey);
			} catch (Throwable e) {
				log.error("Excepted in addNetNode processing in mitigationDriver :"+mitigationDriver.getLabel(), e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws Throwable 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public synchronized void mitigate(String attackKey) {

		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow starting mitigation for attack " + Attack.getPrintableAttackTarget(attackKey));
		Hashtable<String, Object> attackRow;
		try {
			attackRow = dfAppRootFullImpl.attacksRepo.getRow(attackKey);
		} catch (Throwable e) {
			log.error("Failed to get attack row from attacksRepo : "+attackKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation for attack on " + Attack.getPrintableAttackTarget(attackKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		if(attackRow == null) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation for attack on " + Attack.getPrintableAttackTarget(attackKey));
			return;
		}

		String pnKey = (String) attackRow.get(Attack.PNKEY);
		ProtocolPort attackedProtocolPort;
		try {
			attackedProtocolPort = new ProtocolPort((String) attackRow.get(Attack.PROTOCOL_PORT));
		} catch (Throwable e1) {
			log.error("Failed to construct ProtocolPort from attackRow cell "+(String) attackRow.get(Attack.PROTOCOL_PORT),e1);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation for attack on " + Attack.getPrintableAttackTarget(attackKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Check if the PN is failed or canceled */
		try {
			Object obj = dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.OPERATIONAL_STATUS);
			OperationalStatus operationalStatus = OperationalStatus.INVALID;
			if(obj != null)	operationalStatus = OperationalStatus.valueOf((String) obj);
			if(operationalStatus == OperationalStatus.CANCELED || operationalStatus == OperationalStatus.FAILED) return;
		} catch (Throwable e) {
			log.error("Failed to retrieve operational status value for " + pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Create diversion object and record it in diversions repo */

		String mitigationKey = Mitigation.generateKey(attackKey);
		InetAddress dstAddr; int dstAddrPrefixLen;
		try {
			dstAddr = InetAddress.getByName((String) dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.DST_ADDR));
			dstAddrPrefixLen = (Integer) dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.DST_ADDR_PREFIX_LEN);
		} catch (Throwable e2) {
			log.error("Failed to construct dstAddr and/or dstAddrPrefixLen from pNsRepo cell "+pnKey, e2);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation for attack on " + Attack.getPrintableAttackTarget(attackKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		/* Determine the traffic to divert according to PN configuration - all or only the attacked protocol-port */
		ProtocolPort protocolPort;
		try {
			protocolPort = determineTrafficToDivert(pnKey, attackedProtocolPort);
		} catch (ExceptionControlApp e1) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation for attack on " + Attack.getPrintableAttackTarget(attackKey));
			return;
		}

		boolean collectStats = true; String mitigationDriverLabel = "";	boolean setMitigation = false;
		Mitigation mitigation = new Mitigation(mitigationKey, attackKey, pnKey, null, dstAddr, dstAddrPrefixLen, 
				protocolPort, Mitigation.Status.NO_RESOURCES, collectStats, mitigationDriverLabel, null);
		try {
			Hashtable<String,Object> existingMitigation = dfAppRootFullImpl.mitigationsRepo.getRow(mitigationKey);
			if(existingMitigation != null) return; // We already have a mitigation for this key. Called twice for same attack?
			dfAppRootFullImpl.mitigationsRepo.setRow(mitigationKey, mitigation.toRow());
			setMitigation = true;
			dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.MITIGATION_KEY, mitigationKey);
		} catch (Throwable e4) {
			log.error("Failed to update mitigationsRepo and/or attacksRepo "+mitigationKey + " attack key "+attackKey, e4);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation for attack on " + Attack.getPrintableAttackTarget(attackKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			if ( setMitigation == true ) {
				try {
					dfAppRootFullImpl.mitigationsRepo.deleteRow(mitigationKey);
				} catch ( Throwable e ) {};
			}
			return;
		}

		/* Start requesting mitigation drivers to handle this mitigation. */
		startMitigationByDrivers(mitigationKey);
	}

	protected synchronized void startMitigationByDrivers(String mitigationKey) {
		try {
			if(dfAppRootFullImpl.mitigationDrivers == null || dfAppRootFullImpl.mitigationDrivers.size() == 0) {
				fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow cannot start mitigation for " +Mitigation.getPrintableMitigationTarget( mitigationKey )
						+ " because there are no mitigation drivers installed.");
				return; // No mitigation drivers installed
			}
			MitigationDriver mitigationDriver = dfAppRootFullImpl.mitigationDrivers.get(0);
			tryMitigationByDriver(mitigationKey, mitigationDriver);
		} catch (ExceptionControlApp e) {/* Ignore */}
	}

	public List<String> getMitigationTrafficFloorKeys(String attackKey) throws ExceptionControlApp {

		List<String> mitigationTrafficFloorKeys = new ArrayList<String>();		
		try {			
			Hashtable<String,Hashtable<String,Object>> mitigationsTable = dfAppRootFullImpl.mitigationsRepo.getTable();
			Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = mitigationsTable.entrySet().iterator();
			Hashtable<String,Object> mitigationRow; Mitigation mitigation;			
			while(iter.hasNext()) {
				mitigationRow = iter.next().getValue();
				mitigation = new Mitigation(mitigationRow);
				if(mitigation.attackKey.equals(attackKey)) {
					mitigationTrafficFloorKeys.addAll(mitigation.trafficFloorKeys);
					return mitigationTrafficFloorKeys;
				}
			}			
		} catch (Throwable e) {
			String msg = "Failed to getMitigationTrafficFloorKeys " + " attack key "+attackKey;
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg + e.getMessage());
		}
		return null;
	}

	protected ProtocolPort determineTrafficToDivert(String pnKey, ProtocolPort attackedProtocolPort) 
			throws ExceptionControlApp {		

		try {
			String mitigationScopeStr = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.MITIGATION_SCOPE);
			MitigationScope mitigationScope = MitigationScope.valueOf(mitigationScopeStr);
			ProtocolPort protocolPort;
			if(mitigationScope == MitigationScope.ATTACKED)
				protocolPort = attackedProtocolPort;
			else
				protocolPort = new ProtocolPort(DFProtocol.IP, 0);
			return protocolPort;
		} catch (Throwable e) {
			String msg = "Failed to construct protocolPort from pNsRepo cell "+pnKey;
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg + ". " + e.getMessage());
		}
	}

	/* Try the first MitigationDriver plugin to mitigate the attack. It will respond with a callback about
	 * whether it handled the mitigation (or whether the next mitigation plugin should be attempted). */
	protected void tryMitigationByDriver(String mitigationKey,MitigationDriver mitigationDriver) throws ExceptionControlApp {

		log.info( "DF is trying to mitigate " + mitigationKey 
				+ " using mitigation driver " + mitigationDriver.getLabel());
		try {
			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_DRIVER, mitigationDriver.getLabel());
		} catch (Throwable e) {
			log.error("Failed to update mitigationsRepo "+mitigationKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to process mitigation off " + Mitigation.getPrintableMitigationTarget(mitigationKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to update mitigationsRepo "+mitigationKey, e);
		}
		MitigationInfo mitigationInfo = new MitigationInfo(mitigationKey, mitigationDriver);
		try {
			invokeDecoupledSerially(ACTION_MITIGATE, mitigationInfo);
		} catch (ExceptionControlApp e1) {
			try {
				dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_DRIVER, ""); //Unset mitigation driver
			} catch (Throwable e) {/* Ignore */}
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_MITIGATE + mitigationKey, e1);
			throw e1;
		}
	}

	protected synchronized void decoupledMitigate(MitigationInfo mitigationInfo)  {
		/* mitigate is decoupled in the mitigation driver. 
		 * Retry and recovery should be handled by mitigation driver.
		 *  Mitigation manager can handle status of invocation only */
		try {
			mitigationInfo.driver.mitigate(mitigationInfo.mitigationKey);
		} catch (Throwable e) {
			log.error("Excepted trying to invoke mitigate of mitigation driver  " + mitigationInfo.driver.getLabel(), e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public synchronized void handleMitigationResponse (String mitigationKey, boolean mitigating) {

		String currentMitigationDriverLabel = "";
		try {
			currentMitigationDriverLabel = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey,
					Mitigation.MITIGATION_DRIVER);
		} catch (ExceptionControlApp e) {
			log.error("Failed to get MITIGATION_DRIVER from mitigationsRepo "+mitigationKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
		if(currentMitigationDriverLabel == null || currentMitigationDriverLabel.isEmpty()) return;

		if(mitigating) {
			try {
				dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ACTIVE.name());
			} catch (Throwable e) {
				log.error("Failed to update mitigationsRepo with active mitigation status "+mitigationKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			log.info(currentMitigationDriverLabel+" is driving mitigation of "+mitigationKey);
			return;
		}

		/* The current in line mitigation driver is not mitigating this attack. Find and try the next one. */
		try {
			dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_DRIVER, "");
		} catch (Throwable e) { };
		log.info(currentMitigationDriverLabel+" is not driving mitigation of "+mitigationKey);
		int numofMitigationDrivers = dfAppRootFullImpl.mitigationDrivers.size();
		String mitigationDriverLabel; int i;

		for(i=1;i<numofMitigationDrivers;i++) {
			mitigationDriverLabel = dfAppRootFullImpl.mitigationDrivers.get(i).getLabel();
			if(mitigationDriverLabel.equals(currentMitigationDriverLabel)) break;
		}

		if(i >= numofMitigationDrivers-1) {
			try {
				dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.NO_RESOURCES.name());
			} catch (Throwable e) {
				log.error("Failed to update mitigationsRepo with active mitigation status "+mitigationKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			log.info("No more mitigation drivers to try driving mitigation of "+mitigationKey);
			return; // Either not found current mitigating driver (could have been removed in this app lifecycle) or 
			// this was the last one installed
		}

		/* Try the next mitigation driver */
		try {
			MitigationDriver mitigationDriver = dfAppRootFullImpl.mitigationDrivers.get(i+1);
			tryMitigationByDriver(mitigationKey, mitigationDriver);
		} catch (ExceptionControlApp e) {/* Ignore */}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public synchronized void endMitigation(String attackKey) {

		// take care about some clean-up in case first running fails
		String mitigationKey = null;
		String mitigationStatusStr;
		try {
			mitigationKey = (String) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.MITIGATION_KEY);
			mitigationStatusStr = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.STATUS);
		} catch (Throwable e) {
			log.error("Failed to get mitigation status from mitigationsRepo and/or attacksRepo : "+mitigationKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to end mitigation of attack on " + Attack.getPrintableAttackTarget(attackKey));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		if(mitigationStatusStr.equals(Mitigation.Status.ENDED.name())) return;

		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DefenseFlow ending mitigation of attack on " + Attack.getPrintableAttackTarget(attackKey));

		try {
			invokeDecoupledSerially(ACTION_END_MITIGATION, mitigationKey);
		} catch (ExceptionControlApp e1) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_END_MITIGATION  +" " + mitigationKey, e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	public synchronized void decoupledRetryMitigations() {
		if(!fMain.isOpenForBusiness()) return; 

		// Go over all mitigations and look on NO_RESOURCES
		try {
			Hashtable<String,Hashtable<String,Object>> mitigationsTable = dfAppRootFullImpl.mitigationsRepo.getTable();	
			Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = mitigationsTable.entrySet().iterator();
			Hashtable<String,Object> mitigationRow; Mitigation mitigation;	Attack.Status attackStatus;	String attackStatusStr;	
			while(iter.hasNext()) {
				mitigationRow = iter.next().getValue();
				mitigation = new Mitigation(mitigationRow);
				if ( mitigation.status != Mitigation.Status.NO_RESOURCES || ! mitigation.mitigationDriverLabel.isEmpty() ) continue;
				// check status of attack
				attackStatusStr = (String ) dfAppRootFullImpl.attacksRepo.getCellValue(mitigation.attackKey, Attack.STATUS);
				if(attackStatusStr == null || attackStatusStr.isEmpty()) continue; // TODO: log error
				attackStatus = Attack.Status.valueOf( (String ) attackStatusStr);
				if ( attackStatus != Attack.Status.DECLARED ) continue;

				// Start to look available driver again
				startMitigationByDrivers(mitigation.key);
			}

		} catch (Throwable e) {
			log.error("Failed to retryMitigations ", e);
			//fMain.getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL, "Failed to retryMitigations" );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	protected synchronized void decoupledEndMitigation(String mitigationKey) {

		boolean isError = false;
		/* Notify all mitigationDrivers to end mitigating. There can be more than one driver mitigating
		 * as some drivers may prefer not to consume the mitigation (respond with not "mitigating"). */		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			/* End mitigation is decoupled in the mitigation driver. 
			 * Retry and recovery should be handled by mitigation driver.
			 *  Mitigation manager can handle status of invocation only */
			try {
				mitigationDriver.endMitigation(mitigationKey);
			} catch (Throwable e) {
				log.error("Excepted in invocation of endMitigation for mitigationDriver :"+mitigationDriver.getLabel(), e);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to end mitigation of "
						+ Mitigation.getPrintableMitigationTarget(mitigationKey));	
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				isError = true;
				continue;
			}
		}

		// Don't update status of attack if one of mitigationDrivers has an issue 
		if ( isError == true ) return;

		try {
			dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ENDED.name());
			String attackKey = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.ATTACK_KEY);
			dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.STATUS, Attack.Status.ENDED.name());
		} catch (ExceptionControlApp e) {
			log.error("Failed to update mitigationsRepo and/or attacksRepo about end mitigation :"+mitigationKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to end mitigation of "
					+ Mitigation.getPrintableMitigationTarget(mitigationKey));	
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly end mitigation " + mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}

		// check if any mitigations waiting for resources
		decoupledRetryMitigations();
	}

	@Override
	public List<Mitigation> getAllPNMitigations(String pnkey) {
		List<Mitigation> mitigations = new ArrayList<Mitigation>();
		String currentPNKey; Hashtable<String, Object> mitigationRow; Mitigation mitigation;
		try {
			List<String> mitigationKeys = dfAppRootFullImpl.mitigationsRepo.getKeys();
			for ( String mitigationKey : mitigationKeys) {
				try {
					currentPNKey = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
					if ( currentPNKey.equals(pnkey)) {
						mitigationRow = dfAppRootFullImpl.mitigationsRepo.getRow(mitigationKey);
						mitigation = new Mitigation(mitigationRow);
						mitigations.add(mitigation);
					}
				} catch (Throwable e) {
					log.error("Failed to get mitigation from mitigationsRepo :"+mitigationKey, e);
					fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF Failed to get mitigation from mitigationsRepo : " + mitigationKey);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				}
			}
		} catch( Throwable e1) {
			log.error("Failed to get mitigations for PN :"+pnkey, e1);
			//fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF Failed to get mitigations for PN: " + pnkey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
		return mitigations;
	}

	public void netNodeStatusChanged(String logicalNetNodeLabel, HealthStatus healthStatus) {

		if(healthStatus == HealthStatus.UP)	 {
			try {
				invokeDecoupledSerially(ACTION_RETRY_MITIGATION, null );
			} catch (Throwable e) {
				log.error("Failed to invoke handling of status change UP of netNode " + logicalNetNodeLabel, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		} else {
			for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
				mitigationDriver.netNodeStatusDowned(logicalNetNodeLabel, healthStatus);
			} 
		}
	}

	@Override
	public void notifyFailedAMSs(List<String> failedAMSs) {
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.handleFailedAMSs(failedAMSs);
		}
	}

	public boolean removeAMS(String amsLabel) {

		// Check if there are any active mitigations
		try {
			List<String> mitigationKeys = dfAppRootFullImpl.mitigationsRepo.getKeys();
			Mitigation.Status mitigationStatus;	String mitigationStatusStr;	
			for(String mitigationKey : mitigationKeys) {
				mitigationStatusStr = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.STATUS);
				mitigationStatus = Status.valueOf((String) mitigationStatusStr);
				if(mitigationStatus == Mitigation.Status.ACTIVE) return false; // TODO: check if the mitigation involves this AMS
			}
			return true;
		} catch (Throwable e) {
			log.error("Failed to check if there are active mitigations", e);
			//fMain.getFR().logRecord(DFAppRoot.FR_DF_FAILURE, "Failed to check if there are active mitigations" );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return false;
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_ADD_PN:
			decoupledAddPN((String) param);
			break;
		case ACTION_REMOVE_PN:
			decoupledRemovePN((String) param);
			break;
		case ACTION_MITIGATE:
			decoupledMitigate((MitigationInfo) param);
			break;
		case ACTION_END_MITIGATION:
			decoupledEndMitigation((String) param);
			break;
		case ACTION_RETRY_MITIGATION:
			decoupledRetryMitigations();
		default:
			break;
		}
	}
}
