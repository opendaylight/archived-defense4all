/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.core;

import java.util.Collection;

import javax.transaction.NotSupportedException;

import org.opendaylight.defense4all.core.interactionstructures.PNStatReport;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;

public interface DFMgmtPoint {

	/**
	 * Set the OFC address. If this overrides the previously set OFC address, then it is considered a different OFC. 
	 * In that case the application will attempt to contact the previously set OFC in order to remove all monitors  
	 * set through it, prior to setting them through the new OFC.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addOFC(OFC ofc) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeOFC(String ofcLabel) throws ExceptionControlApp;

	/**
	 * Set the OFC address. If this overrides the previously set OFC address, then it is considered a different OFC. 
	 * In that case the application will attempt to contact the previously set OFC in order to remove all monitors  
	 * set through it, prior to setting them through the new OFC.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws NotSupportedException 
	 * @throws IllegalArgumentException 
	 * @throws exception_type circumstances description 
	 */
	public void addNetNode(NetNode netNode) throws ExceptionControlApp, IllegalArgumentException, NotSupportedException;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws NotSupportedException 
	 * @throws exception_type circumstances description 
	 */
	public void removeNetNode(String netNodeLabel) throws ExceptionControlApp, NotSupportedException;

	/**
	 * Set the OFC address. If this overrides the previously set OFC address, then it is considered a different OFC. 
	 * In that case the application will attempt to contact the previously set OFC in order to remove all monitors  
	 * set through it, prior to setting them through the new OFC.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addAMS(AMS ams) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public boolean removeAMS(String dpLabel) throws ExceptionControlApp;

	/**
	 * Add an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addDetector(DetectorInfo detector) throws ExceptionControlApp;

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
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public boolean removePN(String pnLabel) throws ExceptionControlApp;

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public PNStatReport getLatestPNStatReport(String pnKey) throws ExceptionControlApp, IllegalArgumentException;

	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
	 * @throws ExceptionControlApp 
		 * @throws exception_type circumstances description
	 */
	public Collection<PNStatReport> getLatestPNStatReports() throws ExceptionControlApp;
}
