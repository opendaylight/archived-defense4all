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

import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.MitigationMgr;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PN.MitigationScope;
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

	/* Constructor for Spring */
	public MitigationMgrImpl() {
		super();
	}

	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {
		super.init();
		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "MitigationMgr is starting.");
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "MitigationMgr is stopping.");

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.finit();
		}
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "MitigationMgr is resetting to level " + resetLevel);
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
	protected void decoupledAddPN(String pnKey) {

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {

			try {
				mitigationDriver.addPN(pnKey);
			} catch (Throwable e) {
				log.error("Excepted in adding PN to mitigationDriver :"+mitigationDriver.getLabel()+" PN "+pnKey, e );
				try {
					mitigationDriver.removePN(pnKey);
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
	protected void decoupledRemovePN(String pnKey) {
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			try {
				mitigationDriver.removePN(pnKey);
			} catch (Throwable e) {
				log.error("Excepted in removing PN from mitigationDriver :"+mitigationDriver.getLabel()+" PN "+pnKey, e );
				continue;
			}
		}
	}

	public void addNetNode(String netNodeKey) {	

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
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void topologyChanged()  {

		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			try {
				mitigationDriver.topologyChanged();
			} catch (Throwable e) {
				log.error("Excepted in topologyChange processing in mitigationDriver :"+mitigationDriver.getLabel(), e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
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
	public void mitigate(String attackKey) {

		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DF is starting mitigation for attack " + attackKey);
		Hashtable<String, Object> attackRow;
		try {
			attackRow = dfAppRootFullImpl.attacksRepo.getRow(attackKey);
		} catch (Throwable e) {
			log.error("Failed to get attack row from attacksRepo : "+attackKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation for attack " + attackKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		if(attackRow == null) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation for attack " + attackKey);
			return;
		}

		String pnKey = (String) attackRow.get(Attack.PNKEY);
		ProtocolPort attackedProtocolPort;
		try {
			attackedProtocolPort = new ProtocolPort((String) attackRow.get(Attack.PROTOCOL_PORT));
		} catch (Throwable e1) {
			log.error("Failed to construct ProtocolPort from attackRow cell "+(String) attackRow.get(Attack.PROTOCOL_PORT),e1);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation for attack " + attackKey);
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
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation for attack " + attackKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		/* Determine the traffic to divert according to PN configuration - all or only the attacked protocol-port */
		ProtocolPort protocolPort;
		try {
			protocolPort = determineTrafficToDivert(pnKey, attackedProtocolPort);
		} catch (ExceptionControlApp e1) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation for attack " + attackKey);
			return;
		}

		boolean collectStats = true; String mitigationDriverLabel = "";	boolean setMitigation = false;
		Mitigation mitigation = new Mitigation(mitigationKey, attackKey, pnKey, null, dstAddr, dstAddrPrefixLen, 
				protocolPort, Mitigation.Status.NO_RESOURCES, collectStats, mitigationDriverLabel, null);
		try {
			dfAppRootFullImpl.mitigationsRepo.setRow(mitigationKey, mitigation.toRow());
			setMitigation = true;
			dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.MITIGATION_KEY, mitigationKey);
		} catch (Throwable e4) {
			log.error("Failed to update mitigationsRepo and/or attacksRepo "+mitigationKey + " attack key "+attackKey, e4);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation for attack " + attackKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			if ( setMitigation == true ) {
				try {
					dfAppRootFullImpl.mitigationsRepo.deleteRow(mitigationKey);
				} catch ( Throwable e ) {};
			}
			return;
		}

		/* Start requesting mitigation drivers to handle this mitigation. */
		try {
			if(dfAppRootFullImpl.mitigationDrivers == null || dfAppRootFullImpl.mitigationDrivers.size() == 0) {
				fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DF cannot mitigate attack " + attackKey 
						+ " because there are no mitigation drivers installed.");
				return; // No mitigation drivers installed
			}
			MitigationDriver mitigationDriver = dfAppRootFullImpl.mitigationDrivers.get(0);
			tryMitigationByDriver(mitigationKey, mitigationDriver);
		} catch (ExceptionControlApp e) {/* Ignore */}
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

		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DF is trying to mitigate " + mitigationKey 
				+ " using mitigation driver " + mitigationDriver.getLabel());
		try {
			dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_DRIVER, mitigationDriver.getLabel());
		} catch (Throwable e) {
			log.error("Failed to update mitigationsRepo "+mitigationKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process mitigation " + mitigationKey);
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

	protected void decoupledMitigate(MitigationInfo mitigationInfo)  {
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
	public void handleMitigationResponse (String mitigationKey, boolean mitigating) {

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
			fr.logRecord(DFAppRoot.FR_DF_SECURITY,currentMitigationDriverLabel+" is driving mitigation of "+mitigationKey);
			return;
		}

		/* The current in line mitigation driver is not mitigating this attack. Find and try the next one. */

		fr.logRecord(DFAppRoot.FR_DF_SECURITY,currentMitigationDriverLabel+" is not driving mitigation of "+mitigationKey);
		
		int numofMitigationDrivers = dfAppRootFullImpl.mitigationDrivers.size();
		String mitigationDriverLabel; int i;

		for(i=1;i<numofMitigationDrivers;i++) {
			mitigationDriverLabel = dfAppRootFullImpl.mitigationDrivers.get(i).getLabel();
			if(mitigationDriverLabel.equals(currentMitigationDriverLabel)) break;
		}

		if(i >= numofMitigationDrivers-1) {
			fr.logRecord(DFAppRoot.FR_DF_SECURITY,"No more mitigation drivers to try driving mitigation of "+mitigationKey);
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
	public void endMitigation(String attackKey) {

		// take care about some clean-up in case first running fails
		String mitigationKey = null;
		String mitigationStatusStr;
		try {
			mitigationKey = (String) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.MITIGATION_KEY);
			mitigationStatusStr = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.STATUS);
		} catch (Throwable e) {
			log.error("Failed to get mitigation status from mitigationsRepo and/or attacksRepo : "+mitigationKey, e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly end mitigation of attack " + attackKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}
		if(mitigationStatusStr.equals(Mitigation.Status.ENDED.name())) return;
		
		fr.logRecord(DFAppRoot.FR_DF_SECURITY, "DF is ending mitigation of attack " + attackKey);

		try {
			invokeDecoupledSerially(ACTION_END_MITIGATION, mitigationKey);
		} catch (ExceptionControlApp e1) {
			log.error("Excepted trying to invokeDecoupledSerialiy " + ACTION_END_MITIGATION  +" " + mitigationKey, e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	protected void decoupledEndMitigation(String mitigationKey) {

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
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly end mitigation " + mitigationKey +
						"against mitigation driver " + mitigationDriver.getLabel());
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
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly end mitigation " + mitigationKey);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
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
		default:
			break;
		}
	}
}
