/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.defense4all.core.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.DFMgmtPoint;
import org.opendaylight.defense4all.core.Detector;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.ExceptionProtectionProfileNotFound;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNode.AMSConnection;
import org.opendaylight.defense4all.core.NetNode.ProtectedLink;
import org.opendaylight.defense4all.core.NetNode.TrafficPort;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.NetNode.PortDirection;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.core.PN.StatsCollectionStatus;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;


/** 
 *	TODO: description - app focal point. orchestrates start, stop. 
 */

public class DFMgmtPointImpl extends DFAppCoreModule implements DFMgmtPoint {

	/**
	 * DFMgmtPoint Repo types
	 */
	public static final int TYPE_INVALID = -1;
	public static final int TYPE_RESERVED = 0;

	/* OFC from Spring */
	protected String ofcHostname = null;
	protected String ofcIpAddrString = null;
	protected int 	 ofcPort = -1;
	protected String ofcUsername = null;
	protected String ofcPassword = null;
	protected boolean ofcForStatsCollection = false;
	protected boolean ofcForDvsn = false;
	protected boolean ofcSetInSpring = false;

	/* AMS from Spring */
	protected String amsLabel = null;
	protected String amsBrand = null;
	protected String amsVersion = null;
	protected boolean amsForStatsCollection = false;
	protected boolean amsForDvsn = false;
	protected int amsHealthCheckFrequency;
	protected boolean amsSetInSpring = false;
	
	/* OFS from Spring */
	protected String ofsLabel;	// Record key.
	protected String ofsId;		// For OpenFlow NetNodes this is the DPID
	protected String ofsType;	// Covers ODL Node types and beyond 
	protected String ofsMgmtAddr;
	protected int 	 ofsMgmtPort;
	protected SDNNodeMode ofsSdnNodeMode;
	protected int 	 ofsHealthFrequency; // When in-path in secs. When out of path - decrease frequency by X 10	
	protected String ofsAmsConnAmsLabel;
	protected int 	 ofsAmsConnToAmsInPort; 	// port in the node - closer to client
	protected int 	 ofsAmsConnToAmsOutPort;	// port in the node - closer to server
	protected int 	 ofsAmsConnInAmsPort; 	// port in the AMS device - closer to client
	protected int 	 ofsAmsConnOutAmsPort;	// port in the AMS device - closer to server
	protected String ofsTrafficPortLabel;
	protected int 	 ofsTrafficPort;
	protected int 	 ofsTrafficPortVlan;
	protected PortDirection ofsTrafficPortDirection;
	protected String ofsProtectedLinkLabel;
	protected int 	 ofsProtectedLinkInPort;
	protected int 	 ofsProtectedLinkOutPort;
	protected Properties ofsProps;
	protected boolean ofsSetInSpring = false;
	
	private static Log log = LogFactory.getLog(DFMgmtPointImpl.class);
	
	/* Setters for Spring */
	
	public void setOfcHostname(String ofcHostname) {this.ofcHostname = ofcHostname;}
	public void setOfcIpAddrString(String ofcIpAddrString) {this.ofcIpAddrString = ofcIpAddrString;}
	public void setOfcPort(int ofcPort) {this.ofcPort = ofcPort;}
	public void setOfcUsername(String ofcUsername) {this.ofcUsername = ofcUsername;}
	public void setOfcPassword(String ofcPassword) {this.ofcPassword = ofcPassword;}
	public void setOfcForStatsCollection(boolean forStatsCollection) {this.ofcForStatsCollection = forStatsCollection;}
	public void setOfcForDvsn(boolean forDvsn) {this.ofcForDvsn = forDvsn;}
	public void setOfcSetInSpring(boolean ofcSetInSpring) {this.ofcSetInSpring = ofcSetInSpring;}
	
	public void setOfsLabel(String ofsLabel) {this.ofsLabel = ofsLabel;}
	public void setOfsId(String ofsId) {this.ofsId = ofsId;}
	public void setOfsType(String ofsType) {this.ofsType = ofsType;}
	public void setOfsMgmtAddr(String ofsMgmtAddr) {this.ofsMgmtAddr = ofsMgmtAddr;}
	public void setOfsMgmtPort(int ofsMgmtPort) {this.ofsMgmtPort = ofsMgmtPort;}
	public void setOfsSdnNodeMode(SDNNodeMode ofsSdnNodeMode) {this.ofsSdnNodeMode = ofsSdnNodeMode;}
	public void setOfsHealthCheckFrequency(int frequency) {this.ofsHealthFrequency = frequency;}
	public void setOfsAmsConnAmsLabel(String label) {this.ofsAmsConnAmsLabel = label;}
	public void setOfsAmsConnToAmsInboundPort(int port) {this.ofsAmsConnToAmsInPort = port;}
	public void setOfsAmsConnToAmsOutboundPort(int port) {this.ofsAmsConnToAmsOutPort = port;}
	public void setOfsAmsConnInAmsPort(int port) {this.ofsAmsConnInAmsPort = port;}
	public void setOfsAmsConnOutAmsPort(int port) {this.ofsAmsConnOutAmsPort = port;}
	public void setOfsTrafficPortLabel(String label) {this.ofsTrafficPortLabel = label;}
	public void setOfsTrafficPort(int ofsTrafficPort) {this.ofsTrafficPort = ofsTrafficPort;}
	public void setOfsTrafficPortVlan(int vlan) {this.ofsTrafficPortVlan = vlan;}
	public void setOfsTrafficPortDirection(PortDirection direction) {this.ofsTrafficPortDirection = direction;}
	public void setOfsProtectedLinkLabel(String label) {this.ofsProtectedLinkLabel = label;}
	public void setOfsProtectedLinkInPort(int port) {this.ofsProtectedLinkInPort = port;}
	public void setOfsProtectedLinkOutPort(int port) {this.ofsProtectedLinkOutPort = port;}	
	public void setOfsHealthFrequency(int ofsHealthFrequency) {this.ofsHealthFrequency = ofsHealthFrequency;}	
	public void setOfsAmsConnToAmsInPort(int port) {this.ofsAmsConnToAmsInPort = port;}
	public void setOfsAmsConnToAmsOutPort(int port) {this.ofsAmsConnToAmsOutPort = port;}	
	public void setOfsProps(Properties ofsProps) {this.ofsProps = ofsProps;}
	public void setOfsSetInSpring(boolean ofsSetInSpring) {this.ofsSetInSpring = ofsSetInSpring;}
	
	public void setAmsLabel(String amsLabel) {this.amsLabel = amsLabel;}
	public void setAmsBrand(String amsBrand) {this.amsBrand = amsBrand;}
	public void setAmsVersion(String amsVersion) {this.amsVersion = amsVersion;}
	public void setAmsForStatsCollection(boolean forStatsCollection) {this.amsForStatsCollection = forStatsCollection;}
	public void setAmsForDvsn(boolean forDvsn) {this.amsForDvsn = forDvsn;}
	public void setAmsHealthCheckFrequency(int frequency) {this.amsHealthCheckFrequency = frequency;}
	public void setAmsSetInSpring(boolean amsSetInSpring) {this.amsSetInSpring = amsSetInSpring;}
	
	public DFMgmtPointImpl() {
		super();
	}

	/**
	 * Initialize after construction. Look for an OFC to add - first check in repo if one has already been set in 
	 * previous life cycle. If not, check if one has been set through Spring.
	 * @throws exception_type circumstances description 
	 */
	public void init() {

		/* OFC related initializations. */
		if (ofcHostname == null) {
			if(ofcIpAddrString == null)
				ofcSetInSpring = false; // At least on of hostname or ip must be not null, otherwise consider them unset by Spring.
			else
				ofcHostname = ofcIpAddrString;
		}		
		List<String> ofcKeys = dfAppRoot.oFCsRepo.getKeys();
		
		if(ofcKeys.size() > 0) {
			addOFC(ofcKeys.get(0));
		} else if(ofcSetInSpring) { // Add OFC if one set through Spring. After first invocation ofc will be in repo.
			OFC ofc = new OFC(ofcHostname, ofcIpAddrString, ofcPort, ofcUsername, ofcPassword, 
					ofcForStatsCollection, ofcForDvsn, null);			
			addOFC(ofc);
		}

		/* AMS related initializations. */
		List<String> amsKeys = dfAppRoot.amsRepo.getKeys();
		for(int i=0; i<amsKeys.size();i++) // Re-register all AMSs if not there yet
			addAMS(amsKeys.get(i));
		if(amsKeys.size() == 0 && amsSetInSpring) { // Add AMS if one set through Spring. Next time AMS will be in repo.
			try {
				AMS ams = new AMS(amsLabel, amsBrand, amsVersion, null, 0, amsForStatsCollection, amsForDvsn, 
						amsHealthCheckFrequency, null);
				addAMS(ams);
			} catch (UnknownHostException e) { 
				log.error(e);
			}	
		}

		/* NetNode related initializations. */
		List<String> netNodeKeys = dfAppRoot.netNodesRepo.getKeys();
		for(int i=0; i<netNodeKeys.size();i++) // Re-register all NetNodes if not there yet
			addNetNode(netNodeKeys.get(i));
		if(netNodeKeys.size() == 0 && ofsSetInSpring) { // Add OFS if one set through Spring. Next time OFS will be in repo.
			try {
				NetNode ofs = new NetNode(ofsLabel,ofsId,ofsType,ofsMgmtAddr,ofsMgmtPort,ofsSdnNodeMode,ofsHealthFrequency);				
				AMSConnection amsConnection = ofs.new AMSConnection(ofsAmsConnAmsLabel, ofsAmsConnToAmsInPort,
					ofsAmsConnToAmsOutPort, ofsAmsConnInAmsPort, ofsAmsConnOutAmsPort);
				ofs.amsConnections.put(ofsAmsConnAmsLabel, amsConnection);				
				TrafficPort trafficPort = ofs.new TrafficPort(ofsTrafficPortLabel, (short) ofsTrafficPort,
						ofsTrafficPortVlan,ofsTrafficPortDirection);
				ofs.trafficPorts.put(ofsTrafficPortLabel, trafficPort);				
				ProtectedLink protectedLink = ofs.new ProtectedLink(ofsProtectedLinkLabel,
						(short) ofsProtectedLinkInPort,ofsProtectedLinkOutPort);
				ofs.protectedLinks.put(ofsProtectedLinkLabel, protectedLink);				
				addNetNode(ofs);
			} catch (UnknownHostException e) { 
				log.error(e);
			}	
		}				

		/* PN related initializations. */	
		List<String> pnKeys = dfAppRoot.pNsRepo.getKeys();
		try {			
			for(int i=0; i<pnKeys.size();i++) // Re-register all protected networks in controller if not there yet
				addPN(pnKeys.get(i));
			if(pnKeys.size() == 0) { // Add PN if one set through properties file. Next time PN will be in repo.
				loadAndSetPNsFromPropsFile();
			}
		} catch (Exception e) {
			log.error(e); // TODO handle. Can be because it already exists in the system (from prior runs).
		}	
	}

	protected void loadAndSetPNsFromPropsFile() throws Exception {

		// Read properties file with default PNs attributes
		Properties propsFromFile = new Properties();
		InputStream is=DFMgmtPointImpl.class.getClassLoader().getResourceAsStream("pns.properties");
		try {
			propsFromFile.load(is);
		} catch(IOException ioExc) {
			log.error(ioExc);
			throw new Exception("Failed to load config properties file", ioExc);
		}

		String pnLabels = propsFromFile.getProperty("PN.pns");
		PN defaultPN = new PN(); Set<String> pnKeySet = defaultPN.toRow().keySet();
		Hashtable<String, Object> pnRow; Properties pnProps; Set<Object> propsFromFileKeySet;
		String propFromFileKey; String pnAttributeKey; String[] split;

		/* Set each property for relevant pn in the props file - either into its attribute or
		 * into the props attribute (can be in case properties of PN sub-classes are set) */
		for( String pnLabel : pnLabels.split(",") ) {

			pnRow = defaultPN.toRow(); // Get default map of attributes	            	
			pnProps = new Properties();
			propsFromFileKeySet = propsFromFile.keySet();
			for ( Object keyObject : propsFromFileKeySet ) {
				propFromFileKey = (String) keyObject;
				split = propFromFileKey.split("\\.");
				if(split.length < 3) continue;
				if(!split[1].equals(pnLabel)) continue;
				pnAttributeKey = split[2];
				if (pnKeySet.contains(pnAttributeKey)) {
					pnRow.put(pnAttributeKey, propsFromFile.get(propFromFileKey));
				} else {
					pnProps.setProperty(pnAttributeKey, propsFromFile.getProperty(propFromFileKey));
				}
			}
			pnRow.put(PN.PROPS, pnProps);

			// construct new PN from hash of attributes
			PN pn = new PN(pnRow);
			// Set type of detection from registered detector
			Detector pnDetector = dfAppRootFullImpl.detectorMgrImpl.getDetector(pn.getDetectorLabel());
			if ( pnDetector  != null ) {
				pn.setOfBasedDetection ( pnDetector.getDetectorInfo().getOfBasedDetector() );   
			}
			addPN(pn);
		}	
	}
	
	/**
	 * Cleanup before shutdown
	 * @throws exception_type circumstances description 
	 */
	public void finit() {		
		// TODO all pre-termination cleanups go here
	}

	/** Reset */
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
		resetPns(resetLevel);
	}
	
	protected void resetPns(ResetLevel resetLevel) {
		
		Hashtable<String,Hashtable<String,Object>> pnTable = dfAppRootFullImpl.pNsRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = pnTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; String pnKey;	PN pn;
		
		while(iter.hasNext()) {
			
			entry = iter.next();
			pnKey = entry.getKey();  
			try {
				pn = new PN(entry.getValue());
			} catch (UnknownHostException e) {continue; /* Ignore */}
			
			pn.averageStr = ""; pn.latestRateStr = ""; pn.latestRateTime = 0; pn.attackSuspicions = "";
			pn.statsCollectionStatus = StatsCollectionStatus.INVALID;
			if(resetLevel == ResetLevel.dynamic) {
				pn.baselineStr = ""; pn.baselinesTime = 0;
			}
			
			dfAppRootFullImpl.pNsRepo.setRow(pnKey, pn.toRow());
		}
	}
	
	protected void removeCanceledPns() {
		
		Hashtable<String,Hashtable<String,Object>> pnTable = dfAppRootFullImpl.pNsRepo.getTable();
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = pnTable.entrySet().iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; Hashtable<String,Object> pnRow; boolean canceled;
		
		while(iter.hasNext()) {
			
			entry = iter.next();
			pnRow = entry.getValue();
			canceled = (Boolean) pnRow.get(PN.PN_CANCELED);
			if(canceled) dfAppRootFullImpl.pNsRepo.deleteRow(entry.getKey());
		}
	}

	/**
	 * Add OFC according to its purpose - collection or dvsn (the same OFC can be set for both). Ignore if
	 * already set (currently we accept only one).
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addOFC(OFC ofc) {		
		
		List<String> keys = dfAppRootFullImpl.oFCsRepo.getKeys();
		if(keys.size() != 0) // Already have a set OFC. Currently there can be only one OFC in the system.
			return;		
		dfAppRootFullImpl.oFCsRepo.setRow(ofc.hostname, ofc.toRow()); // Record ofc in ofcs repo.		
		addOFC(ofc.hostname); // Notify relevant DF modules. 
	}

	/**
	 * Add OFC that has already been recorded in ofcs repo. Add according to its purpose - collection or dvsn 
	 * (the same OFC can be set for both). Ignore if already set (currently we accept only one).
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addOFC(String ofcKey) {
		
		if((Boolean) dfAppRootFullImpl.oFCsRepo.getCellValue(ofcKey, OFC.FOR_STATS_COLLECTION)) {
			dfAppRootFullImpl.getStatsCollectionRep().addOFC(ofcKey);
		}
		
		if((Boolean) dfAppRootFullImpl.oFCsRepo.getCellValue(ofcKey, OFC.FOR_DIVERSION)) {
			dfAppRootFullImpl.getDvsnRep().addOFC(ofcKey);
		}
	}

	/**
	 * Remove OFC. The application will attempt to contact the previously set OFC in order to remove all monitors set through it.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeOFC(String ofcLabel) {
		String ofcKey=""; // get from repo
		boolean forStatsCollection = false; // retrieve from repo
		boolean forDvsn = false; // retrieve from repo
		
		if(forStatsCollection) {
			// TODO: for now we do not remove OFC network element
			dfAppRootFullImpl.getStatsCollectionRep().removeOFC(ofcKey);
		}
			
		if(forDvsn) {
			// TODO: Set the passed in network element in DvsnNetworkElements repo.
			dfAppRootFullImpl.getDvsnRep().removeOFC(ofcKey);	
		}
	}

	/**
	 * Add netNode. Notify statsCollectionRep and DvsnRep.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(NetNode netNode) {		
		dfAppRootFullImpl.netNodesRepo.setRow(netNode.label, netNode.toRow()); // Record netNode in netNodes repo.		
		addNetNode(netNode.label); // Notify relevant DF modules. 
	}

	/**
	 * Add netNode that has already been recorded in netNodes repo. Notify statsCollectionRep and DvsnRep.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addNetNode(String netNodeKey) {		
		dfAppRootFullImpl.getStatsCollectionRep().addNetNode(netNodeKey);		
		dfAppRootFullImpl.getDvsnRep().addNetNode(netNodeKey);
		dfAppRoot.netNodesRepo.setCell(netNodeKey, NetNode.STATUS, NetNode.Status.ACTIVE.name());
		dfAppRootFullImpl.mitigationMgrImpl.addNetNode(netNodeKey);
	}

	/**
	 * Remove netNode. The application will attempt to remove all traffic counting/diversion elements set in it.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeNetNode(String netNodeLabel) {
		dfAppRoot.netNodesRepo.setCell(netNodeLabel, NetNode.STATUS, NetNode.Status.REMOVED);	
		dfAppRootFullImpl.getDvsnRep().removeNetNode(netNodeLabel);
		dfAppRootFullImpl.getStatsCollectionRep().removeNetNode(netNodeLabel);	
	}

	/**
	 * Add DP. If it is for stats collection need to notify the DP-based detector. If it is for dvsn,
	 * notify both the dvsn rep and the DP rep.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addAMS(AMS ams) {

		Hashtable<String,Object> dpRow = dfAppRootFullImpl.amsRepo.getRow(ams.label);
		if(dpRow != null) {
			String msg = "DP " + ams.label + " is already defined. Need to delete it first";
			throw new InvalidParameterException(msg);
		}
		dfAppRootFullImpl.amsRepo.setRow(ams.label, ams.toRow());			
		addAMS(ams.label);
	}

	/**
	 * #### description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addAMS(String amsKey) {
		dfAppRootFullImpl.amsRep.addAMS(amsKey);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void configureAMS(String amsLabel, Properties configProps) {		
		dfAppRootFullImpl.amsRep.configureAMS(amsLabel, configProps);
	}

	/**
	 * #### description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeAMS(String amsLabel) {
		dfAppRootFullImpl.amsRep.removeAMS(amsLabel);
	}

	/**
	 * Add an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */	
	public void addDetector(DetectorInfo detectorInfo) {
		if ( detectorInfo.getExternalDetector() == true) {
			ExternalDetector externalDetector = new ExternalDetector(detectorInfo);
			dfAppRootFullImpl.detectorMgrImpl.addDetector(externalDetector);				
		}			
	}
	

	/**
	 * Remove an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeDetector(String detectorLabel) {
		dfAppRootFullImpl.detectorMgrImpl.removeDetector(detectorLabel);
	}

	/**
	 * Add protected object. The protection profile referred in the protected object is assumed to be set in the system earlier.
	 * If OFC monitoring has been specified then DF will add the object to the OFC monitored objects.
	 * If DP based monitoring (then DP should be specified) DF will accept attack detections against the protected object 
	 * from registered DPs. In any case DF will mitigate attacks against this protected object. 
	 * For NEC vlan and array of vExtNames can be passed in the otherProperties of the ProtectedNetwork object rather than consumed from topology.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws ExceptionProtectionProfileNotFound If the protection profile specified in the protected object is not found in the system
	 */
	public void addPN(PN pn) throws Exception {
		
		Hashtable<String,Object> pnRow = dfAppRootFullImpl.pNsRepo.getRow(pn.label);
		if(pnRow != null) {
			String msg = "Protected network " + pn.label + " is already defined. Need to delete it first";
			throw new InvalidParameterException(msg);
		}
		dfAppRootFullImpl.pNsRepo.setRow(pn.label, pn.toRow());			
		addPN(pn.label);
	}

	/**
	 * Add protected object. The protection profile referred in the protected object is assumed to be set in the system earlier.
	 * If OFC monitoring has been specified then DF will add the object to the OFC monitored objects.
	 * If DP based monitoring (then DP should be specified) DF will accept attack detections against the protected object 
	 * from registered DPs. In any case DF will mitigate attacks against this protected object. 
	 * For NEC vlan and array of vExtNames can be passed in the otherProperties of the ProtectedNetwork object rather than consumed from topology.
	 * @param param_name param description
	 * @return return description
	 * @throws Exception 
	 * @throws ExceptionProtectionProfileNotFound If the protection profile specified in the protected object is not found in the system
	 */
	protected void addPN(String pnKey) throws Exception {

		dfAppRootFullImpl.mitigationMgrImpl.addPN(pnKey);		
		String detectorLabel = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DETECTOR_LABEL);
		Detector detector = dfAppRootFullImpl.detectorMgrImpl.getDetector(detectorLabel);
		if ( detector != null) {
			boolean ofBasedDetector = detector.getDetectorInfo().ofBasedDetector;
			dfAppRoot.pNsRepo.setCell(pnKey, PN.OF_BASED_DETECTION, ofBasedDetector); // Base on detector, not client param
			detector.addPN(pnKey);
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void changePN(PN pn) {
		// No Op at this time.
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removePN(String pnLabel) throws InvalidParameterException {

		String pnKey = pnLabel;
		
		if(dfAppRootFullImpl.pNsRepo.getRow(pnKey) == null)
			throw new InvalidParameterException("Protected network " + pnLabel + " is unknown.");
		
		String detectorLabel = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DETECTOR_LABEL);
		Detector detector = dfAppRootFullImpl.detectorMgrImpl.getDetector(detectorLabel);  // TODO: Konsta
		if ( detector != null)
			detector.removePN(pnKey);

		dfAppRootFullImpl.mitigationMgrImpl.removePN(pnKey);
		
		dfAppRoot.pNsRepo.setCell(pnKey, PN.PN_CANCELED, true); 
		// Cannot remove the PN yet because the above methods are asynchronous
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {}
	
	public void test() {
	}
}
