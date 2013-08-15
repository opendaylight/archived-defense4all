/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.core;

import org.opendaylight.controlapps.framework.core.ExceptionControlApp;

public class DFHolder {
	
	protected static DFAppRoot dfAppRoot = null;
	
	public static DFAppRoot get() {
		return dfAppRoot;
	}
	
	public static void set(DFAppRoot df) throws ExceptionControlApp {
		if(dfAppRoot != null)
			throw new ExceptionControlApp("Multiple DFAppRoots");
		dfAppRoot = df;
	}
}
