/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.core.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.MitigationMgr;
import org.opendaylight.defense4all.core.MitigationDriver;
import org.opendaylight.defense4all.core.ProtocolPort.DFProtocol;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PN.MitigationScope;
import org.opendaylight.defense4all.core.ProtocolPort;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
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

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	/* Constructor for Spring */
	public MitigationMgrImpl() {
		super();
	}
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {
		
		super.init();	
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		
		super.finit();
		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.finit();
		}
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {
		
		super.reset(resetLevel);
		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.reset(resetLevel);
		}
		
		dfAppRootFullImpl.mitigationsRepo.truncate();
	}

	public ArrayList<MitigationDriver> getMitigationDrivers() {return dfAppRootFullImpl.mitigationDrivers;}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) {
		invokeDecoupledSerially(ACTION_ADD_PN, pnKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void decoupledAddPN(String pnKey) {
		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.addPN(pnKey);
		}		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) {
		invokeDecoupledSerially(ACTION_REMOVE_PN, pnKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void decoupledRemovePN(String pnKey) {		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.removePN(pnKey);
		}
	}

	public void addNetNode(String netNodeKey) {		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.addNetNode(netNodeKey);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void topologyChanged() {
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers)
			mitigationDriver.topologyChanged();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void mitigate(String attackKey) {
		
		Hashtable<String,Object> attackRow = dfAppRootFullImpl.attacksRepo.getRow(attackKey);
		if(attackRow == null) return;		
		String pnKey = (String) attackRow.get(Attack.PNKEY);
		ProtocolPort attackedProtocolPort = new ProtocolPort((String) attackRow.get(Attack.PROTOCOL_PORT));
		
		frameworkMain.getMyLogger().logRow("Defense4All has detected attack on " + pnKey + " " + 
				attackedProtocolPort.toString() + "!");
		
		/* Create diversion object and record it in diversions repo */
		
		String mitigationKey = Mitigation.generateKey(attackKey);
		InetAddress dstAddr;
		try {
			dstAddr = InetAddress.getByName((String) dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.DST_ADDR));
		} catch (UnknownHostException e) {return;}
		int dstAddrPrefixLen = (Integer) dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.DST_ADDR_PREFIX_LEN);
		
		/* Determine the traffic to divert according to PN configuration - all or only the attacked protocol-port */
		String mitigationScopeStr = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.MITIGATION_SCOPE);
		MitigationScope mitigationScope = MitigationScope.valueOf(mitigationScopeStr);
		ProtocolPort protocolPort = mitigationScope == MitigationScope.ATTACKED ? attackedProtocolPort : new ProtocolPort(DFProtocol.IP, 0);
		
		boolean collectStats = true; String mitigationDriverLabel = "";
		Mitigation mitigation = new Mitigation(mitigationKey, attackKey, pnKey, null, dstAddr, dstAddrPrefixLen, 
				protocolPort, Mitigation.Status.NO_RESOURCES, collectStats, mitigationDriverLabel, null);
		dfAppRootFullImpl.mitigationsRepo.setRow(mitigationKey, mitigation.toRow());
		dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.MITIGATION_KEY, mitigationKey);

		/* Try the first MitigationDriver plugin to mitigate the attack. It will respond with a callback about
		 * whether it handled the mitigation (or whether the next mitigation plugin should be attempted). */
		
		if(dfAppRootFullImpl.mitigationDrivers == null || dfAppRootFullImpl.mitigationDrivers.size() == 0) 
			return; // No mitigation drivers installed
		
		MitigationDriver mitigationDriver = dfAppRootFullImpl.mitigationDrivers.get(0);
		dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_DRIVER, mitigationDriver.getLabel());
		MitigationInfo mitigationInfo = new MitigationInfo(mitigationKey, mitigationDriver);
		invokeDecoupledSerially(ACTION_MITIGATE, mitigationInfo);
	}
	
	protected void decoupledMitigate(MitigationInfo mitigationInfo) {
		mitigationInfo.driver.mitigate(mitigationInfo.mitigationKey);		
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void handleMitigationResponse(String mitigationKey, boolean mitigating) {

		if(mitigating) {
			dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ACTIVE.name());
			return;
		}
		
		/* The current in line mitigation driver is not mitigating this attack. Find and try the next one. */
		
		String currentMitigationDriverLabel = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.MITIGATION_DRIVER);
		if(currentMitigationDriverLabel == null) return;
		
		int numofMitigationDrivers = dfAppRootFullImpl.mitigationDrivers.size();
		String mitigationDriverLabel; int i;
		
		for(i=1;i<numofMitigationDrivers;i++) {
			mitigationDriverLabel = dfAppRootFullImpl.mitigationDrivers.get(i).getLabel();
			if(mitigationDriverLabel.equals(currentMitigationDriverLabel)) break;
		}
		
		if(i >= numofMitigationDrivers-1) return; // Either not found current mitigating driver
						// (could have been removed in this app lifecycle) or this was the last one installed

		/* Try the next mitigation driver */
		MitigationDriver mitigationDriver = dfAppRootFullImpl.mitigationDrivers.get(i+1);
		dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.MITIGATION_DRIVER, mitigationDriver.getLabel());
		MitigationInfo mitigationInfo = new MitigationInfo(mitigationKey, mitigationDriver);
		invokeDecoupledSerially(ACTION_MITIGATE, mitigationInfo);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void endMitigation(String attackKey) {
		
		String mitigationKey = (String) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.MITIGATION_KEY);
		String mitigationStatusStr = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.STATUS);
		if(mitigationStatusStr.equals(Mitigation.Status.ENDED.name())) return;

		invokeDecoupledSerially(ACTION_END_MITIGATION, mitigationKey);
	}

	protected void decoupledEndMitigation(String mitigationKey) {
		
		/* Notify all mitigationDrivers to end mitigating. There can be more than one driver mitigating
		 * as some drivers may prefer not to consume the mitigation (respond with not "mitigating"). */		
		for(MitigationDriver mitigationDriver : dfAppRootFullImpl.mitigationDrivers) {
			mitigationDriver.endMitigation(mitigationKey);
		}
		
		dfAppRootFullImpl.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ENDED.name());
		String attackKey = (String) dfAppRootFullImpl.mitigationsRepo.getCellValue(mitigationKey, Mitigation.ATTACK_KEY);
		dfAppRootFullImpl.attacksRepo.setCell(attackKey, Attack.STATUS, Attack.Status.ENDED.name());
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
