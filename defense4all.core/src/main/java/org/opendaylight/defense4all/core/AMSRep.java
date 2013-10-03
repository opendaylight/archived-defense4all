/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import java.util.Properties;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public interface AMSRep {
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp;

	/** Pre-shutdown cleanup */
	public void finit();

	/** Reset */
	public void reset(ResetLevel resetLevel);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void addAMS(String amsKey);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeAMS(String amsKey);

	/**
	 * #### Method description
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void configureAMS(String amsKey, Properties configProps);

	/**
	 * #### Method description
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void setAMSBaselines(String amsKey, TrafficTuple baselines);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void addMitigation(String mitigationKey);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract void removeMitigation(String mitigationKey);

	/**
	 * Check AMS health.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public abstract boolean check();
}
