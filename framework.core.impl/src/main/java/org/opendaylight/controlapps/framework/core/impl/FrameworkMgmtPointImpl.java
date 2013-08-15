/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.framework.core.impl;

/** 
 *	TODO: description - org.opendaylight.ctlapps.framework focal point. orchestrates start, stop. 
 */

import java.util.Properties;

import org.opendaylight.controlapps.framework.core.ClusterInfo;
import org.opendaylight.controlapps.framework.core.FrameworkMgmtPoint;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


public class FrameworkMgmtPointImpl extends FrameworkModule implements FrameworkMgmtPoint {

	/**
	 * Request application to perform factory reset - i.e., return to factory settings
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void requestFactoryReset(ResetLevel resetLevel) {
		frameworkMainImpl.reset(resetLevel);
	}

	/**
	 * Set the cluster info. If this overrides the previously set cluster info, then this is considered a connection to a different cluster
	 * (e.g., sandbox). All setups remain valid (unless overriden by the new cluster global settings). 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void setClusterInfo(ClusterInfo clusterInfo) {
		frameworkMainImpl.clusterMgrImpl.setClusterInfo(clusterInfo);
	}

	/**
	 * Get cluster info. 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public ClusterInfo getClusterInfo() {
		return null;
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		// TODO: check if decoupled execution is needed		
	}
	
	// TODO: cleanup at the end. This is just for trying REST...
	
	DirectionsImpl directionsImpl = DirectionsImpl.getInstance();
	
	public Properties getDirections() {
		return directionsImpl.getDirections();
	}
}
