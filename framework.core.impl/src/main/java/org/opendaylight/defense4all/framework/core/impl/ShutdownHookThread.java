/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import org.opendaylight.defense4all.framework.core.AppRoot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHookThread extends Thread {

	Logger log = LoggerFactory.getLogger(this.getClass());
	
	FrameworkMainImpl frameworkMainImpl;
	AppRoot appRoot;	
	
	/** Empty constructor for Spring
	 */
	public ShutdownHookThread() {
		super();
	}

	/** ### Description ###
	 * @param param_name 
	 */
	public ShutdownHookThread(FrameworkMainImpl frameworkMainImpl, AppRoot appRoot) {
		
		this.frameworkMainImpl = frameworkMainImpl;
		this.appRoot = appRoot;
	}

	public void run() {
		finit();
	}

	/**
	 * Subclasses are expected to override this method to include their cleanup at shutdown.
	 */
	public void finit() {
		
		try {
			this.frameworkMainImpl.finit();
		} catch (Throwable e) {
			log.error("Failed to finit frameworkMainImpl." + e.getLocalizedMessage());
		}
	}
}
