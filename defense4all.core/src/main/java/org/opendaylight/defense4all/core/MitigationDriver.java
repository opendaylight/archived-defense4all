/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.core;

import java.util.List;

import org.opendaylight.defense4all.core.DFAppRoot.HealthStatus;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public interface MitigationDriver {

	/** Post-constructor initialization
	 * @throws ExceptionControlApp 
	 */
	public void init() throws ExceptionControlApp;

	/** Pre-shutdown cleanup
	 */
	public void finit();

	/** Factory reset
	 */
	public void reset(ResetLevel level) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void mitigate(String mitigationKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void endMitigation(String mitigationKey) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public String getLabel();

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addNetNode(String netNodeKey);

	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public void netNodeStatusDowned(String logicalNetNodeLabel, HealthStatus healthStatus);

	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public void handleFailedAMSs(List<String> failedAMSs);
}
