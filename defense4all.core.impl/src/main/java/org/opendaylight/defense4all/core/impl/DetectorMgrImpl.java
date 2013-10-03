/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev 
 * @version 0.1
 */

package org.opendaylight.defense4all.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.core.DFDetector;
import org.opendaylight.defense4all.core.Detection;
import org.opendaylight.defense4all.core.DetectorMgr;
import org.opendaylight.defense4all.core.Detector;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.StatReport;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;



public class DetectorMgrImpl extends DFAppCoreModule  implements DetectorMgr {
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	protected Detector detector0 = null;
	protected Detector detector1 = null;
	protected Detector detector2 = null;
	protected Detector detector3 = null;
	protected Detector detector4 = null;
	protected Detector detector5 = null;
	public Hashtable<String, DFDetector> detectors = null;
	
	public void setdetector0(Detector detector0) {this.detector0 = detector0;}
	public void setdetector1(Detector detector1) {this.detector1 = detector1;}
	public void setdetector2(Detector detector2) {this.detector2 = detector2;}
	public void setdetector3(Detector detector3) {this.detector3 = detector3;}
	public void setdetector4(Detector detector4) {this.detector4 = detector4;}
	public void setdetector5(Detector detector5) {this.detector5 = detector5;}
	
	static private Properties defaultProperties = null; 

	
	public DetectorMgrImpl() {
		super();
		detectors = new Hashtable<String,DFDetector>();
	}
	
	/** Post-constructor initialization	 */
	public void init() throws ExceptionControlApp {
		super.init();	
		
		// Create and registry all detectors from repo
		Hashtable<String,Hashtable<String,Object>> detectorTable = dfAppRoot.detectorsRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = detectorTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; DetectorInfo detectorInfo; Detector detector;		
		while(iter.hasNext()) {			
			entry = iter.next();
			detectorInfo = new DetectorInfo(entry.getValue());
			if(  detectorInfo.getOfBasedDetector() ) { 					
				detector = instrumentDetector(detectorInfo.getLabel() );
				if ( detector != null &&  detector instanceof DFDetector  ) {
					detector.fromRow(entry.getValue());	
					detector.init();
					addDetector ( detector );
				}
			}
		}
		
		// First time repo initialization
		// Add Detector if one set through properties file. Next time Detector will be in repo.
		if(detectors.size() == 0 ) { 
			// read properties file
			for (Detector dt:instrumentDetectors()  ) {
				if ( dt != null &&  dt instanceof DFDetector  ) {
					// read properties file
					Hashtable<String, Object> detectorRow = getProperties ( dt);
					dt.fromRow(detectorRow);		
					dt.init();
					addDetector ( dt );
				}
			}
					
		}
	}
		
	/**
	 * Instrument an Detector constructed from spring context 
	 * by the label parameters
	 * 
	 */
	protected Detector instrumentDetector (String label) {
		for (Detector dt:instrumentDetectors()  ) {
			if ( dt != null) {
				if ( dt.getDetectorInfo().getLabel().equals(label) )
					return dt;
			}
		}
		return null;
	}
	protected Detector[] instrumentDetectors() {
		Detector allDetectors[] = {detector0, detector1, detector2, detector3, detector4, detector5 };
		return allDetectors;
	}
	
	
	/**
	 * Read properties file for pre-created detector
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	
	private  Hashtable<String, Object>  getProperties(Detector detector) {

		// Read properties file with default attributes
		if ( defaultProperties == null ) { 
			defaultProperties=new Properties();
			InputStream is=DFMgmtPointImpl.class.getClassLoader().getResourceAsStream("detectors.properties");
			try {
				defaultProperties.load(is);
			} catch(IOException ioExc) {
				log.error("Cannot load config properties file");
				log.error(ioExc.getLocalizedMessage());
			} 
		}


		// Get default map of attributes
		Hashtable<String, Object> detectorRow = detector.toRow();
		// get list of attributes and replace values from properties file
		for ( String key : detectorRow.keySet() ) {
			String propertyName = "Detector."+detector.getDetectorInfo().getLabel()+"."+key;
			if ( defaultProperties.containsKey(propertyName) && defaultProperties.get(propertyName) != null) {
				detectorRow.put( key , defaultProperties.get(propertyName));
			}
		}
		return detectorRow;

	}

	/**
	 * Add an Detector constructed from parameters on init
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addDetector(Detector detector) {
		String detectorLabel = detector.getDetectorInfo().getLabel();
		
		// insert or update repository row
		dfAppRootFullImpl.detectorsRepo.setRow(detectorLabel,detector.toRow());
				
		if (detector.getDetectorInfo().getOfBasedDetector() && detector instanceof DFDetector )
			detectors.put(detector.getDetectorInfo().getLabel(), (DFDetector)detector);
	}
	
	/**
	 * Return an initialized Detector from hash 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public DFDetector getDetector(String label) {
		return detectors.get(label);
	}
	
	/**
	 * Delete an initialized Detector from hash 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeDetector(String label) {
		Detector dt = detectors.get(label);
		if ( dt != null ) {
			dt.finit();
			detectors.remove(dt);
		}
		// remove from repository also
		dfAppRootFullImpl.detectorsRepo.deleteRow(label);
	}
	
	/**
	 * Loop over registered in PN detectors and pass stat report
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void handleStatReport(Hashtable<String,Object> pnRow, StatReport statReport) {
		String label = (String) pnRow.get(PN.DETECTOR_LABEL);
		if ( detectors.containsKey(label)) {
			if ( detectors.get(label).getDetectorInfo().getOfBasedDetector()) {
				detectors.get(label).handleStatReport( statReport );
				
			}
		}
	}
	
	/**
	 * Notify all relevant detectors 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void notifyEndDetection(String detectionKey) {
		String detectorLabel = (String) dfAppRootFullImpl.detectionsRepo.getCellValue(detectionKey, Detection.DETECTOR);
		if(detectorLabel == null) 
			return;	// Just in case detections repo is out of sync with attack detections

		DFDetector dt = getDetector(detectorLabel);
		if ( dt != null && dt.getDetectorInfo().getOfBasedDetector() )
			dt.notifyEndDetection(detectionKey);
	}

	public void cleanup() {	
		for (DFDetector detector : detectors.values() )
			detector.cleanup();
	}

	/** Pre-shutdown cleanup */
	public void finit() {
		super.finit();

		// loop over all detectors in detectors repo 
		for ( Detector dt : detectors.values() ) {
			dt.finit();
		}
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {
	}
	
	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		switch(actionCode) {
		default:
			break;
		}              
	}
}
