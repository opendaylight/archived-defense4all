/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public interface AMSRep {
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp;

	/** Pre-shutdown cleanup */
	public void finit();

	/** Reset */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public abstract void addAMS(String amsKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeAMS(String amsKey) throws ExceptionControlApp;

	/**
	 * #### Method description
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void addSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp;

	/**
	 * #### Method description
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void startMonitoring(String mitigationKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void stopMonitoring(String mitigationKey) throws ExceptionControlApp;

	/**
	 * Check AMS health.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract boolean check();

	/**
	 * ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp;

	/**
	 * ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	void removePN(String pnKey) throws ExceptionControlApp;
}
