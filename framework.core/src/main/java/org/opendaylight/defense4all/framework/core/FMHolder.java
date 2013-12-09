/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core;

public class FMHolder {
	
	protected static FrameworkMain frameworkMain = null;
	
	public static FrameworkMain get() {
		return frameworkMain;
	}
	
	public static void set(FrameworkMain fm) throws ExceptionControlApp {
		if(frameworkMain != null) {
			throw new ExceptionControlApp("Multiple sets of FrameworkMain singleton in FMFactory.");
		}
		frameworkMain = fm;
	}
}
