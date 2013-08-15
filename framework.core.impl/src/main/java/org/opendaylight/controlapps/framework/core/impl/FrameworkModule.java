/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.framework.core.impl;

import org.opendaylight.controlapps.framework.core.FrameworkMain;
import org.opendaylight.controlapps.framework.core.Module;

public abstract class FrameworkModule extends Module {
	
	FrameworkMainImpl frameworkMainImpl;
	
	/* Constructor for Spring */
	public FrameworkModule() {
	}

	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {
		super.setFrameworkMain(frameworkMain);
		this.frameworkMainImpl = (FrameworkMainImpl) frameworkMain;
	}
}
