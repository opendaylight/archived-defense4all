/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.controlapps.defense4all.mitigationdriver.local;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.Attack;
import org.opendaylight.controlapps.defense4all.core.DFAppModule;
import org.opendaylight.controlapps.defense4all.core.MitigationDriver;
import org.opendaylight.controlapps.defense4all.core.Mitigation;
import org.opendaylight.controlapps.defense4all.core.PN;
import org.opendaylight.controlapps.defense4all.core.TrafficTuple;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;

public class MitigationDriverLocal extends DFAppModule implements MitigationDriver {
	
	protected class MitigationResponse {
		String mitigationKey; boolean mitigating;
		MitigationResponse(String mitigationKey, boolean mitigating) {
			this.mitigationKey = mitigationKey; this.mitigating = mitigating;
		}
	}
	
	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_DIVERT_AGAINST_OTHER_MODULES = 1;
	protected static final int ACTION_END_DIVERSION_AGAINST_OTHER_MODULES = 2;
	protected static final int ACTION_RESPOND_MITIGATION = 3;

	Logger log = Logger.getLogger(this.getClass());
	String label = null;
	
	/* Constructor for Spring */
	public MitigationDriverLocal() {
		super();
	}
	
	/* Setters for Spring */
	public void setLabel(String label) {this.label = label;}

	@Override
	public String getLabel() {return label;}
		
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {		
		super.init();
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
	 * @throws exception_type circumstances description 
	 */
	public void topologyChanged() {}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected String allocateAMSForDiversion(String mitigationKey) {
		
		/* First get all AMSs and make sure there are any */
		List<String> amsKeys = dfAppRoot.amsRepo.getKeys();
		if(amsKeys == null) return null;

		String attackKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.ATTACK_KEY); 
		String pnKey = (String) dfAppRoot.attacksRepo.getCellValue(attackKey, Attack.PNKEY); 
		
		Properties diversionProps;		
		for(String amsKey : amsKeys) {
			
			diversionProps = dfAppRoot.getDvsnRep().getDvsnProps(pnKey, amsKey);
			if (diversionProps == null) continue;
			
			/* Found an AMS to which diversion is possible */
			dfAppRoot.mitigationsRepo.setCell(mitigationKey,Mitigation.MITIGATION_AMS_LABEL, amsKey);
			dfAppRoot.mitigationsRepo.setCell(mitigationKey,Mitigation.MITIGATION_CONFIG_PROPS, diversionProps);
			return amsKey; 
		}

		return null;
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

	@Override
	public void mitigate(String mitigationKey) {

		/* Select a AMS for diversion and update the diversion record */		
		String amsLabel = allocateAMSForDiversion(mitigationKey);
		if (amsLabel == null) { // Notify Mitigation manager - not mitigating
			MitigationResponse mitigationResponse = new MitigationResponse(mitigationKey, false);
			invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse); 
			return;
		}

		/* Do the actual traffic diversion and start collecting stats from the diversion AMS */
		invokeDecoupledSerially(ACTION_DIVERT_AGAINST_OTHER_MODULES, mitigationKey);	
	}
	
	protected void decoupledDivertAgainstOtherModules(String mitigationKey) {
		
		MitigationResponse mitigationResponse = new MitigationResponse(mitigationKey, false);

		/* Configure the AMS with latest baselines */
		String amsLabel = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.MITIGATION_AMS_LABEL);
		String pnKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
		String baselineStr = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.BASELINES);
		TrafficTuple baselines = new TrafficTuple(baselineStr);
		dfAppRoot.getAMSRep().setAMSBaselines(amsLabel, baselines);
		
		/* Divert traffic */
		List<String> trafficFloorKeys = dfAppRoot.getDvsnRep().divert(mitigationKey);
		if(trafficFloorKeys == null || trafficFloorKeys.isEmpty()) { 
			invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse); // No diversion done - notify not mitigating
			return;
		}
		
		for(String trafficFloorKey : trafficFloorKeys)
			dfAppRoot.pNsRepo.setCell(pnKey, PN.TRAFFIC_FLOOR_KEY_PREFIX + trafficFloorKey, trafficFloorKey);		
		
		dfAppRoot.mitigationsRepo.setCell(mitigationKey, Mitigation.STATUS, Mitigation.Status.ACTIVE.name());
		
		/* Notify amsRep about the performed diversion. amsRep can optionally trigger monitoring 
		 * of the attack by the mitigation device */
		dfAppRoot.getAMSRep().addMitigation(mitigationKey);
	
		/* Diversion succeeded - notify "mitigating" */
		mitigationResponse.mitigating = true; 
		invokeDecoupledSerially(ACTION_RESPOND_MITIGATION, mitigationResponse); 
	}

	protected void decoupledRespondMitigation(MitigationResponse mitigationResponse) {
		dfAppRoot.getMitigationMgr().handleMitigationResponse(mitigationResponse.mitigationKey, mitigationResponse.mitigating);		
	}

	@Override
	public void endMitigation(String mitigationKey) {		
		invokeDecoupledSerially(ACTION_END_DIVERSION_AGAINST_OTHER_MODULES, mitigationKey);
	}

	protected void decoupledEndDiversionAgainstOtherModules(String mitigationKey) {

		String pnKey = (String) dfAppRoot.mitigationsRepo.getCellValue(mitigationKey, Mitigation.PNKEY);
		dfAppRoot.getAMSRep().removeMitigation(mitigationKey); // Notify amsRep about ending diversion.		
		List<String> trafficFloorKeys = dfAppRoot.getDvsnRep().endDvsn(mitigationKey); // End the diversion itself

		if(trafficFloorKeys != null && !trafficFloorKeys.isEmpty()) { 
			for(String trafficFloorKey : trafficFloorKeys)
				dfAppRoot.pNsRepo.deleteCell(pnKey, PN.TRAFFIC_FLOOR_KEY_PREFIX + trafficFloorKey);			
		}
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
			case ACTION_RESERVED:
				break;
			case ACTION_DIVERT_AGAINST_OTHER_MODULES:
				decoupledDivertAgainstOtherModules((String) param);
				break;
			case ACTION_END_DIVERSION_AGAINST_OTHER_MODULES:
				decoupledEndDiversionAgainstOtherModules((String) param);
				break;
			case ACTION_RESPOND_MITIGATION:
				decoupledRespondMitigation((MitigationResponse) param);
				break;
			default:
				break;
		}
	}
}
