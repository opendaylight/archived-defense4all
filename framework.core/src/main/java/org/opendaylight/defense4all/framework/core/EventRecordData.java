/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;

public class EventRecordData {
	public Date	   			eventTime;
	public String        	eventType;
	public String        	eventData;
	
    public String toString() {
    	StringBuilder sb = new StringBuilder();
		sb.append(eventTime.toString()); sb.append(" ");
		sb.append(eventType);sb.append(" ");	
		sb.append(eventData);
		return sb.toString();
    }
    
	public void dump(BufferedWriter bw) throws IOException {			
		bw.write(eventTime.toString()); bw.write(":");
		bw.write(eventType);bw.write(":");	
		bw.write(eventData);
	}
}
