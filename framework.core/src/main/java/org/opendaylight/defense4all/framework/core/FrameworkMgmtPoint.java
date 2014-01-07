/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public interface FrameworkMgmtPoint {

	public ClusterInfo getClusterInfo();
	public void setHostAddr(String hostAddress) throws ExceptionControlApp;
	void requestTerminate(String scope) throws ExceptionControlApp;
	void requestReset(ResetLevel resetLevel) throws ExceptionControlApp;
}
