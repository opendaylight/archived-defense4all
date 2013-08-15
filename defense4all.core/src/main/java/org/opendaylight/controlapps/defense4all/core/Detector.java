/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.core;

import java.util.Hashtable;

import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;

public interface Detector {
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public void addPN(String pnKey) throws Exception;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnKey);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public DetectorInfo getDetectorInfo();
	
	/** Post-constructor initialization
	 * @throws ExceptionControlApp 
	 */
	public void init() throws ExceptionControlApp ;

	/** Pre-shutdown cleanup
	 */
	public void finit(); 

	/** Factory reset
	 */
	public void reset(ResetLevel level); 
	
	/** Store detector attributes in the repository
	 */
	public Hashtable<String, Object> toRow();
	
	/** Init detector attributes from the repository
	 */
	public void fromRow ( Hashtable<String, Object> row);

}
