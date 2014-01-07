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

import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class DPHealthMgr extends DFAppModule {
	
	public DPRep amsRep;
	
	public DPHealthMgr() {
		super();
	}

	/* Setters for Spring */
	public void setAmsRep(DPRep amsRep) {this.amsRep = amsRep;}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {
		super.init();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {
		super.finit();
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		super.reset(resetLevel);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(String amsKey) {
		 
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeAMS(String amsKey) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
	}
}
