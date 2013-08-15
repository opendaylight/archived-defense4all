/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core.impl;

import org.opendaylight.controlapps.defense4all.core.DFAppModule;
import org.opendaylight.controlapps.defense4all.core.DFAppRoot;

public abstract class DFAppCoreModule extends DFAppModule {
	
	protected DFAppRootFullImpl dfAppRootFullImpl;
	
	/* Constructor for Spring */
	public DFAppCoreModule() {
		super();
	}

	/* Setters for Spring */
	public void setDfAppRoot(DFAppRoot dfAppRoot) {
		super.setDfAppRoot(dfAppRoot);
		dfAppRootFullImpl = (DFAppRootFullImpl) dfAppRoot;
	}
}
