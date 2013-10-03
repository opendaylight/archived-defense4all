/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core.impl;

import org.opendaylight.defense4all.framework.core.ClusterInfo;
import org.opendaylight.defense4all.framework.core.ClusterMgr;

public class ClusterMgrImpl extends FrameworkModule implements ClusterMgr {

	/**
	 * Set the cluster info. If this overrides the previously set cluster info, then this is considered a connection to a different cluster
	 * (e.g., sandbox). All setups remain valid (unless overriden by the new cluster global settings). 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void setClusterInfo(ClusterInfo clusterInfo) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		// TODO: check if decoupled execution is needed		
	}
}
