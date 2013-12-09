/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.HealthTracker;

public class HealthTrackerImpl implements HealthTracker {

	private static final int PERCENTAGE_OF_HEALTH_IMROVEMENT_PER_MINUTE = 10;
	private static final int MINIMAL_TIME_IN_MINUTES_BETWEEN_FR_HEALTH_LOGS = 1;

	/* request shutdown is dedicated thread */
	class ShutdownThread implements Runnable { 
		boolean graceful;
		public ShutdownThread(boolean graceful) {this.graceful = graceful;}
		public void run() { 
			while ( true ) { 
				fMain.requestShutdown(graceful); 
			} 
		} 
	} 

	protected FrameworkMain fMain;
	protected int healthLevel; // Maximum is maxHealthLevel(100%). Shutdown is requested below threshold.
	protected long lastReportTime;
	protected int minimalHealthThreshold;
	protected boolean shutdownRequested;
	protected int maxHealthLevel = 100; // Maximum is 100% if not permanent issue has reported

	public HealthTrackerImpl() {healthLevel = 100; lastReportTime = System.currentTimeMillis(); shutdownRequested = false;}

	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {this.fMain = frameworkMain;}
	public void setMinimalHealthThreshold(int threshold) {this.minimalHealthThreshold = threshold;}


	/** ####
	 * @param param_name param description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void reportHealthIssue(int issueLevel) {

		long currentTime = System.currentTimeMillis();
		long timeFromLastReportInMins = (currentTime - lastReportTime) / 60000;

		/* Check if graceful termination was requested more than 1 minute ago - so immediate termination is needed. */
		if(shutdownRequested && timeFromLastReportInMins >= 1) {
			fMain.requestShutdown(false); // Request immediate shutdown
			return;
		}

		/* Update health level according to "healing" time passed. */
		healthLevel += ((int) timeFromLastReportInMins) * PERCENTAGE_OF_HEALTH_IMROVEMENT_PER_MINUTE;
		if(healthLevel > maxHealthLevel) healthLevel = maxHealthLevel;
		if(!shutdownRequested)
			lastReportTime = currentTime;

		/* Add the new "illness". */
		healthLevel -= issueLevel;
		
		/* Log health in flight recorder */
		if(timeFromLastReportInMins > MINIMAL_TIME_IN_MINUTES_BETWEEN_FR_HEALTH_LOGS)
			fMain.getFR().logRecord(FrameworkMain.FR_FRAMEWORK_OPERATIONAL, "Health level is " + healthLevel + "%");

		if(healthLevel >= minimalHealthThreshold) return;

		/* Health is below threshold level, so request graceful termination. 
		 * Launch shutdown request in dedicated thread to prevent possible deadlocks on finit operations */
		if(!shutdownRequested) {
			shutdownRequested = true;
			fMain.getFR().logRecord(FrameworkMain.FR_FRAMEWORK_OPERATIONAL, ". Initiating shutdown because health level " 
					+ healthLevel +	"% is below threshold level of " + minimalHealthThreshold);
			ShutdownThread shutdownThread = new ShutdownThread(true);
			shutdownThread.run();

			fMain.requestShutdown(true);			
		}		
	}
	
	@Override
	public void reportHealthIssue(int issueLevel, boolean permanent) {
		if ( permanent == false) {
			// just report health issue
			reportHealthIssue (issueLevel );
		} else {
			// permanent health issue - decrease max
			maxHealthLevel -= issueLevel;
			if ( maxHealthLevel < 0) maxHealthLevel = 0;
			
			// report 0 health issue to check current value against new maximum
			reportHealthIssue ( 0 );
		}
	}
}
