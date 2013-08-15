/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core;

import java.util.Properties;

public interface DFMgmtPoint {

	/**
	 * Set the OFC address. If this overrides the previously set OFC address, then it is considered a different OFC. 
	 * In that case the application will attempt to contact the previously set OFC in order to remove all monitors  
	 * set through it, prior to setting them through the new OFC.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addOFC(OFC ofc);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeOFC(String ofcLabel);

	/**
	 * Set the OFC address. If this overrides the previously set OFC address, then it is considered a different OFC. 
	 * In that case the application will attempt to contact the previously set OFC in order to remove all monitors  
	 * set through it, prior to setting them through the new OFC.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(AMS ams);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeAMS(String dpLabel);

	/**
	 * Add an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addDetector(DetectorInfo detector);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void configureAMS(String amsLabel, Properties configProperties);

	/**
	 * Add protected object and notify the specified detector to start monitoring this PN. 
	 * @param param_name param description
	 * @return return description
	 * @throws IllegalArgumentException If the configuration information does not conform with AMS requirements
	 * @throws Exception 
	 */
	public void addPN(PN pn) throws IllegalArgumentException, Exception;

	/**
	 * Change protected object. No op at this time.
	 * @param param_name param description
	 */
	public void changePN(PN pn);

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnLabel);
}
