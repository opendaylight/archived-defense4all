/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	static Logger log = LoggerFactory.getLogger(Utils.class);

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
			log.debug("row " + key + ": " + row.toString());
		}
	}

	public static ArrayList<String> paramsFromArgs(String[] args, int firstParamPosition) {

		ArrayList<String> params = new ArrayList<String>();
		for(int i=firstParamPosition; i<args.length; i++)
			params.add(args[i]);
		return params;
	}

	public static boolean confirmYesByUser(String s) {

		String input;
		Console console = System.console();
		System.out.println(s);
		if(console != null)
			input = console.readLine();
		else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		    try {
				input = reader.readLine();
			} catch (IOException e) {return false;}
		}
		if(input.startsWith("yes") || input.startsWith("Yes") || input.equals("y") || input.equals("Y"))
			return true;
		return false;
	}
}
