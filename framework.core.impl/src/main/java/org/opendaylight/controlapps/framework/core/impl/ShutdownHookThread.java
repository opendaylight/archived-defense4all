/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.framework.core.impl;

import org.opendaylight.controlapps.framework.core.AppRoot;


public class ShutdownHookThread extends Thread {
	
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
			e.printStackTrace();
		}
	}
}
