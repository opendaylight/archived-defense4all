/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core.impl;

import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.Module;

public abstract class FrameworkModule extends Module {
	
	FrameworkMainImpl fMainImpl;
	
	/* Constructor for Spring */
	public FrameworkModule() {
		super();
	}

	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain fMain) {
		super.setFrameworkMain(fMain);
		this.fMainImpl = (FrameworkMainImpl) fMain;
	}
}
