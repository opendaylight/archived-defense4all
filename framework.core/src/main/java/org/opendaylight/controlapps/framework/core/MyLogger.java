/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.core;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class MyLogger {

	private BufferedWriter bw = null;

	public MyLogger(String fileName) {
		try {
			File file = new File(fileName);
			if(!file.exists()) 
				file.createNewFile(); 	// if file doesnt exists, create it
			File absPath = file.getAbsoluteFile();
			FileWriter fwriter = new FileWriter(absPath);
			bw = new BufferedWriter(fwriter);
		} catch (IOException e) {e.printStackTrace();}
	}

	public void log(String msg) {
		try {
			bw.write(msg);
			bw.flush();
		} catch (IOException e) {e.printStackTrace();}
	}

	public void logRow(String msg) {
		log(msg + "\n");
	}
	
	public void finit() {
		try {
			bw.close();
		} catch (IOException e) {e.printStackTrace();}
	}
}
