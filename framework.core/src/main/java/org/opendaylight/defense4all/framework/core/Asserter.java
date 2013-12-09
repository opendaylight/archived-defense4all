/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import org.slf4j.Logger;

public abstract class Asserter {
	
	/**
		 * #### method description ####
		 * @param param to be checked
		 * @param paramName name of the param to be checked (recorded in log in case of error)
		 * @param log in which error message is to be logged
		 * @throws IllegalArgumentException
	 */
	public static void assertNonEmptyStringParam(String param, String paramName, Logger log) {
		if(param == null || param.length() == 0) {
			log.error(paramName + " is null or empty. ");
			throw new IllegalArgumentException("Invalid param - " + paramName);
		}
	}

	/**
		 * #### method description ####
		 * @param param to be checked
		 * @param paramName name of the param to be checked (recorded in log in case of error)
		 * @param log in which error message is to be logged
		 * @throws IllegalArgumentException
	 */
	static public void assertNonNullObjectParam(Object param, String paramName, Logger log) {
		if(param == null) {
			log.error(paramName + " is null. ");
			throw new IllegalArgumentException("Invalid param - " + paramName);
		}
	}
}
