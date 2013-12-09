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
	 * Request application to perform factory reset - i.e., return to factory settings
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void requestReset(ResetLevel resetLevel) throws ExceptionControlApp {
		fMainImpl.frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_CONFIG, "User requested reset " + resetLevel);
		fMainImpl.reset(resetLevel);
	}

	/**
	 * Set the cluster info. If this overrides the previously set cluster info, then this is considered a connection to a different cluster
	 * (e.g., sandbox). All setups remain valid (unless overriden by the new cluster global settings). 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void setClusterInfo(ClusterInfo clusterInfo) {
		fMain.getFR().logRecord(FrameworkMain.FR_FRAMEWORK_OPERATIONAL, "User requested to set cluster info" + clusterInfo.toString());
		fMainImpl.clusterMgrImpl.setClusterInfo(clusterInfo);
	}

	/**
	 * Get cluster info. 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public ClusterInfo getClusterInfo() {return null;}	
	
	public void setHostAddr(String hostAddr) throws ExceptionControlApp {
		
		fr.logRecord(FrameworkMain.FR_FRAMEWORK_CONFIG, "Framework is setting hostaddress to " + hostAddr);		
		try {
			fMainImpl.coreStateRepo.setCell(CoreState.FWORK_CORE_STATE_ROW_KEY, CoreState.HOST_ADDRESS, hostAddr);
		} catch (Exception e) {
			log.error("Framework failed to set hostaddress " + hostAddr, e );
			fr.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Framework failed to set hostaddress " + hostAddr);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Framework failed to set hostaddress " + hostAddr + ", " + e.getMessage());
		}
		fMainImpl.hostAddr = hostAddr;
		fMainImpl.appRoot.setHostAddr(hostAddr); // Notify the application. 
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {}
}
