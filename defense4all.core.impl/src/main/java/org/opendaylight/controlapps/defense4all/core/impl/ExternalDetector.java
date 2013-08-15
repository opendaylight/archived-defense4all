/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

/**
 * 
 */
package org.opendaylight.controlapps.defense4all.core.impl;

import java.util.Hashtable;

import org.opendaylight.controlapps.defense4all.core.DFHolder;
import org.opendaylight.controlapps.defense4all.core.Detector;
import org.opendaylight.controlapps.defense4all.core.DetectorInfo;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;

/**
 * @author gerag
 *
 */
public class ExternalDetector implements Detector {
	
	protected DetectorInfo detectorInfo = null;
	
	public ExternalDetector() {
		detectorInfo = new DetectorInfo();
		detectorInfo.ofBasedDetector = false;
		detectorInfo.externalDetector = true;
	}
	public ExternalDetector(DetectorInfo detectorInfo) {this.detectorInfo = detectorInfo;}
	
	public void init() {
		DFHolder.get().getDetectorMgr().addDetector(this); // Register to support PNs with external detection
	}
	
	public void setLabel ( String label ) {
		detectorInfo.setLabel(label);
	}
	
	public void finit() {}; 
	public void reset(ResetLevel level){}; 

	public void addPN(String pnKey) throws Exception {/* No op */}
	public void removePN(String pnKey) {/* No op */}
	public DetectorInfo getDetectorInfo() {return detectorInfo;}
	
	public Hashtable<String, Object> toRow() {
		return detectorInfo.toRow();
	}
	
	public void fromRow(Hashtable<String, Object> row)
	{
		this.detectorInfo.fromRow(row); 
	}
}
