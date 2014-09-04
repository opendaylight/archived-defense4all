/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core.impl;

/** 
 *	TODO: description - org.opendaylight.ctlapps.framework focal point. orchestrates start, stop. 
 */
import org.opendaylight.defense4all.framework.core.ClusterInfo;
import org.opendaylight.defense4all.framework.core.CoreState;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.FrameworkMgmtPoint;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public class FrameworkMgmtPointImpl extends FrameworkModule implements FrameworkMgmtPoint {

	/**
	 * Decoupled actions for ActionSwitcher
	 */
	protected static final int ACTION_INVALID = -1;	// Already defined in Module. Brought here for brevity
	protected static final int ACTION_RESERVED = 0; // Already defined in Module. Brought here for brevity
	protected static final int ACTION_REQUEST_SHUTDOWN = 1;
	protected static final int ACTION_REQUEST_RESET = 2;
	
	/**
	 * Request application to perform factory reset - i.e., return to factory settings
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void requestTerminate(String scope) throws ExceptionControlApp {
		fMainImpl.frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_OPERATIONAL,"User requested termination of framework.");
		invokeDecoupledSerially(ACTION_REQUEST_SHUTDOWN, scope);
	}
	
	protected void decoupledRequestTerminate(String scope) {
		fMainImpl.requestShutdown(true);	// For now we ignore of scope until we implement clustering
	}
	
	/**
	 * Request application to perform factory reset - i.e., return to factory settings
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void requestReset(ResetLevel resetLevel) throws ExceptionControlApp {
		fMainImpl.frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_CONFIG, "User requested reset to " + resetLevel + " settings.");
		invokeDecoupledSerially(ACTION_REQUEST_RESET, resetLevel);
	}
	
	protected void decoupledRequestReset(ResetLevel resetLevel) {
		try {
			fMainImpl.requestReset(resetLevel);
		} catch (Throwable e) {
			log.error("Failed to reset to level " + resetLevel.toString());
		}
	}

	/**
	 * Set the cluster info. If this overrides the previously set cluster info, then this is considered a connection to a different cluster
	 * (e.g., sandbox). All setups remain valid (unless overriden by the new cluster global settings). 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void setClusterInfo(ClusterInfo clusterInfo) {
		fMain.getFR().logRecord(FrameworkMain.FR_FRAMEWORK_OPERATIONAL, "User request to set cluster information");
		fMainImpl.clusterMgrImpl.setClusterInfo(clusterInfo);
	}

	/**
	 * Get cluster info. 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public ClusterInfo getClusterInfo() {return null;}	

	@Override
	public void setHostAddr(String hostAddr) throws ExceptionControlApp {
		
		fr.logRecord(FrameworkMain.FR_FRAMEWORK_CONFIG, "Framework is setting Control Network address to " + hostAddr);		
		try {
			fMainImpl.coreStateRepo.setCell(CoreState.FWORK_CORE_STATE_ROW_KEY, CoreState.HOST_ADDRESS, hostAddr);
		} catch (Exception e) {
			log.error("Framework failed to set hostaddress " + hostAddr, e );
			fr.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Framework failed to set Control Network address " + hostAddr);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Framework failed to set hostaddress " + hostAddr + ", " + e.getMessage());
		}
		fMainImpl.hostAddr = hostAddr;
		fMainImpl.appRoot.setHostAddr(hostAddr); // Notify the application. 
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {

		switch(actionCode) {
		case ACTION_RESERVED:
			break;
		case ACTION_REQUEST_SHUTDOWN:
			decoupledRequestTerminate((String) param);
			break;
		case ACTION_REQUEST_RESET:
			decoupledRequestReset((ResetLevel) param);
			break;
		default:
		}
	}
}
