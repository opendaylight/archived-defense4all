/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

public class Utils {

	/** 
	 * Returns 4 bytes string that is with very high probability unique mapping of the inputed string. 
	 * In other words if res1 = shortHash(s1), res2 = shortHash(s2), s1 != s2, then the probability that
	 * res1 == res2 is extremely small.
	 * @param s Input string
	 * @return return resulted 4 bytes string that is uniquely mapped from s, or null if s is null
	 */
	public static String shortHash(String s) {
		short h = 0;

		if(s == null) return null;
		for (int i=0;i<s.length();i++) {
			h = (short) (31*h + s.charAt(i));
		}
		h = (short) Math.abs(h);
		return String.valueOf(h);
	}
	
	public static Long valueOf(String s) {
		try {
			return(Long.valueOf(s));
		} catch (Exception e) {
			return 0L;
		}
	}
	
	/** 
	 * Prints the content of the input table
	 * @param table input table to print
	 */
	public static void printTable(Hashtable<String,Hashtable<String,Object>> table) {

		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = table.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; Hashtable<String,Object> row; String key = null;

		while(iter.hasNext()) {
			
			entry = iter.next();
			key = entry.getKey();
			row = entry.getValue();
			System.out.println("row " + key + ": " + row.toString());
		}
	}	
}
