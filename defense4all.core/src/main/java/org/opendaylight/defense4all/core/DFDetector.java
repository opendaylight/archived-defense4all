/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */
package org.opendaylight.defense4all.core;

import java.util.Collection;

import org.opendaylight.defense4all.core.interactionstructures.EndDetectionNotification;
import org.opendaylight.defense4all.core.interactionstructures.PNStatReport;
import org.opendaylight.defense4all.core.interactionstructures.StatReport;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;

public interface DFDetector extends Detector {
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void handleStatReport(StatReport statsReport);
	
	/**
	 * Notify DF detector that the attack corresponding to the detection denoted by the passed in detectionKey
	 * is over. RateBasedDFDetectorImpl updates the state of the stat collection counters to resume calculating moving
	 * average.
	 * @param detectionKey
	 * @throws ExceptionControlApp 
	 */
	public void notifyEndDetection(EndDetectionNotification endDetectionNotification) ; 
	
	/**
	 * Retrieve PN stats - latest rates and averages.
	 * @param detectionKey
	 * @throws ExceptionControlApp 
	 */
	public PNStatReport getLatestPNStatReport(String pnKey);
	
	/**
	 * Trigger clean-up dynamic data 
	 * @throws ExceptionControlApp 
	 */
	public void cleanup() ;

	/**
	 * 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public Collection<PNStatReport> getLatestPNStatReports();
}
