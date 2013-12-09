/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

public interface HealthTracker {
	
	public static final int SIGNIFICANT_HEALTH_ISSUE = 5;
	public static final int MODERATE_HEALTH_ISSUE = 3;
	public static final int MINOR_HEALTH_ISSUE = 1;

	/** Report health event
	 * @param param_name param description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reportHealthIssue(int issueLevel);
	public void reportHealthIssue(int issueLevel, boolean permanent);
}
