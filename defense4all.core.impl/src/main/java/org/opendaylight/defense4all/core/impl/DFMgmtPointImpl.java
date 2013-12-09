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
import java.lang.IllegalArgumentException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFMgmtPoint;
import org.opendaylight.defense4all.core.Detector;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.NetNode.AMSConnection;
import org.opendaylight.defense4all.core.NetNode.PortLocation;
import org.opendaylight.defense4all.core.NetNode.ProtectedLink;
import org.opendaylight.defense4all.core.NetNode.TrafficPort;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.NetNode.SDNNodeMode;
import org.opendaylight.defense4all.core.PN.StatsCollectionStatus;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.HealthTracker;
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
	protected String amsUsername; 
	protected String amsPassword; 
	protected String amsMgmtAddrStr;
	protected List<String> amsSecurityConfigKeys= new ArrayList<String>();
	protected String amsSecurityConfigKeysComb;


	/* OFS from Spring */
	protected String ofsLabel;	// Record key.
	protected String ofsId;		// For OpenFlow NetNodes this is the DPID
	protected String ofsType;	// Covers ODL Node types and beyond 
	protected String ofsMgmtAddr;
	protected int 	 ofsMgmtPort;
	protected SDNNodeMode ofsSdnNodeMode;
	protected int 	 ofsHealthFrequency; // When in-path in secs. When out of path - decrease frequency by X 10	
	protected String ofsAmsConnAmsLabel;
	protected int 	 ofsAmsConnNetNodeNorthPort; // port in the node - closer to client
	protected int 	 ofsAmsConnNetNodeSouthPort; // port in the node - closer to server
	protected int 	 ofsAmsConnAmsNorthPort; 	 // port in the AMS device - connected to ofsAmsConnNetNodeNorthPort
	protected int 	 ofsAmsConnAmsSouthPort;	 // port in the AMS device - connected to ofsAmsConnNetNodeSouthPort
	protected String ofsTrafficNorthPortLabel;
	protected int 	 ofsTrafficNorthPort;
	protected int 	 ofsTrafficNorthPortVlan;
	protected PortLocation ofsTrafficNorthPortLocation;
	protected String ofsTrafficSouthPortLabel;
	protected int 	 ofsTrafficSouthPort;
	protected int 	 ofsTrafficSouthPortVlan;
	protected PortLocation ofsTrafficSouthPortLocation;
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
	public void setOfsAmsConnNetNodeNorthPort(int port) {this.ofsAmsConnNetNodeNorthPort = port;}
	public void setOfsAmsConnNetNodeSouthPort(int port) {this.ofsAmsConnNetNodeSouthPort = port;}
	public void setOfsAmsConnAmsNorthPort(int port) {this.ofsAmsConnAmsNorthPort = port;}
	public void setOfsAmsConnAmsSouthPort(int port) {this.ofsAmsConnAmsSouthPort = port;}
	public void setOfsTrafficNorthPortLabel(String label) {this.ofsTrafficNorthPortLabel = label;}
	public void setOfsTrafficNorthPort(int ofsTrafficPort) {this.ofsTrafficNorthPort = ofsTrafficPort;}
	public void setOfsTrafficNorthPortVlan(int vlan) {this.ofsTrafficNorthPortVlan = vlan;}
	public void setOfsTrafficNorthPortLocation(PortLocation portLocation) {this.ofsTrafficNorthPortLocation = portLocation;}
	public void setOfsTrafficSouthPortLabel(String label) {this.ofsTrafficSouthPortLabel = label;}
	public void setOfsTrafficSouthPort(int ofsTrafficPort) {this.ofsTrafficSouthPort = ofsTrafficPort;}
	public void setOfsTrafficSouthPortVlan(int vlan) {this.ofsTrafficSouthPortVlan = vlan;}
	public void setOfsTrafficSouthPortLocation(PortLocation portLocation) {this.ofsTrafficSouthPortLocation = portLocation;}
	public void setOfsProtectedLinkLabel(String label) {this.ofsProtectedLinkLabel = label;}
	public void setOfsProtectedLinkInPort(int port) {this.ofsProtectedLinkInPort = port;}
	public void setOfsProtectedLinkOutPort(int port) {this.ofsProtectedLinkOutPort = port;}	
	public void setOfsHealthFrequency(int ofsHealthFrequency) {this.ofsHealthFrequency = ofsHealthFrequency;}
	public void setOfsProps(Properties ofsProps) {this.ofsProps = ofsProps;}
	public void setOfsSetInSpring(boolean ofsSetInSpring) {this.ofsSetInSpring = ofsSetInSpring;}

	public void setAmsLabel(String amsLabel) {this.amsLabel = amsLabel;}
	public void setAmsBrand(String amsBrand) {this.amsBrand = amsBrand;}
	public void setAmsVersion(String amsVersion) {this.amsVersion = amsVersion;}
	public void setAmsForStatsCollection(boolean forStatsCollection) {this.amsForStatsCollection = forStatsCollection;}
	public void setAmsForDvsn(boolean forDvsn) {this.amsForDvsn = forDvsn;}
	public void setAmsHealthCheckFrequency(int frequency) {this.amsHealthCheckFrequency = frequency;}

	public void setAmsUsername( String amsUsername ) { this.amsUsername = amsUsername;}
	public void setAmsPassword( String amsPassword ) { this.amsPassword = amsPassword;}
	public void setAmsMgmtAddr( String amsMgmtAddrStr ) { this.amsMgmtAddrStr = amsMgmtAddrStr;}
	public void setAmsSecurityConfigKeys( String amsSecurityConfigKeys) {this.amsSecurityConfigKeysComb = amsSecurityConfigKeys; }
	public void setAmsSetInSpring(boolean amsSetInSpring) {this.amsSetInSpring = amsSetInSpring;}

	public DFMgmtPointImpl() {
		super();
	}

	/**
	 * Initialize after construction. Look for an OFC to add - first check in repo if one has already been set in 
	 * previous life cycle. If not, check if one has been set through Spring.
	 * @throws Exception 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {
		
		super.init();
		
		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DFMgmtPoint is starting.");

		/* OFC related initializations. */
		if (ofcHostname == null) {
			if(ofcIpAddrString == null)
				ofcSetInSpring = false; // At least one of hostname or ip must be not null, otherwise consider them unset by Spring.
			else
				ofcHostname = ofcIpAddrString;
		}

		List<String> ofcKeys = null;
		try {
			ofcKeys = dfAppRoot.oFCsRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to get keys from oFCsRepo ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from oFCsRepo ", e );
		}
		if ( ofcKeys == null) {
			log.error("Invalid null oFCsRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null oFCsRepo");
		}

		if(ofcKeys.size() > 0) {
			addOFC(ofcKeys.get(0));
		} else if(ofcSetInSpring) { // Add OFC if one set through Spring. After first invocation ofc will be in repo.
			OFC ofc = new OFC(ofcHostname, ofcIpAddrString, ofcPort, ofcUsername, ofcPassword, 
					ofcForStatsCollection, ofcForDvsn, null);			
			addOFC(ofc);
		}

		/* AMS related initializations. */
		List<String> amsKeys;
		try {
			amsKeys = dfAppRoot.amsRepo.getKeys();
		} catch (Exception e1) {
			log.error("Failed to get keys from amsRepo ", e1 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from amsRepo ", e1 );
		}
		if ( amsKeys == null) {
			log.debug ("Invalid null amsRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null oFCsRepo");
		}

		for(int i=0; i<amsKeys.size();i++) // Re-register all AMSs if not there yet
			addAMS(amsKeys.get(i));

		AMS ams;
		if(amsKeys.size() == 0 && amsSetInSpring) { // Add AMS if one set through Spring. Next time AMS will be in repo.
			try {
				if ( amsSecurityConfigKeysComb != null && ! amsSecurityConfigKeysComb.isEmpty()) {
					String[] split = amsSecurityConfigKeysComb.split(":");
					for ( String key:split)
						amsSecurityConfigKeys.add(key);
				}
				//public InetAddress dstAddr;
				InetAddress amsMgmtAddr = InetAddress.getByName(amsMgmtAddrStr); // Throws exception if address is not valid.
				ams = new AMS(amsLabel, amsBrand, amsVersion, amsMgmtAddr, 0, amsForStatsCollection, amsForDvsn, 
						amsHealthCheckFrequency, amsUsername, amsPassword, amsSecurityConfigKeys, null);
			} catch (Throwable e3) { 
				log.error("Failed to create ams from default parameters ", e3 );
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp("Failed to create ams from default parameters ", e3 );
			}
			addAMS(ams);
		}

		/* NetNode related initializations. */
		List<String> netNodeKeys;
		try {
			netNodeKeys = dfAppRoot.netNodesRepo.getKeys();
		} catch (Throwable e3) {
			log.error("Failed to get keys from amsRepo ", e3 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from netNodesRepo ", e3);
		}
		if ( netNodeKeys == null) {
			log.debug ("Invalid null netNodesRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null netNodesRepo");
		}

		for(int i=0; i<netNodeKeys.size();i++) // Re-register all NetNodes if not there yet
			addNetNode(netNodeKeys.get(i));
		if(netNodeKeys.size() == 0 && ofsSetInSpring) { // Add OFS if one set through Spring. Next time OFS will be in repo.
			NetNode ofs;
			try {
				ofs = new NetNode(ofsLabel,ofsId,ofsType,ofsMgmtAddr,ofsMgmtPort,ofsSdnNodeMode,ofsHealthFrequency);				
				AMSConnection amsConnection = ofs.new AMSConnection(ofsAmsConnAmsLabel, ofsAmsConnNetNodeNorthPort,
						ofsAmsConnNetNodeSouthPort, ofsAmsConnAmsNorthPort, ofsAmsConnAmsSouthPort);
				ofs.amsConnections.put(ofsAmsConnAmsLabel, amsConnection);			
				TrafficPort trafficNorthPort = ofs.new TrafficPort(ofsTrafficNorthPortLabel, (short) ofsTrafficNorthPort,
						ofsTrafficNorthPortVlan, ofsTrafficNorthPortLocation);
				ofs.trafficPorts.put(ofsTrafficNorthPortLabel, trafficNorthPort);		
				TrafficPort trafficSouthPort = ofs.new TrafficPort(ofsTrafficSouthPortLabel, (short) ofsTrafficSouthPort,
						ofsTrafficSouthPortVlan, ofsTrafficSouthPortLocation);
				ofs.trafficPorts.put(ofsTrafficSouthPortLabel, trafficSouthPort);				
				ProtectedLink protectedLink = ofs.new ProtectedLink(ofsProtectedLinkLabel,
						(short) ofsProtectedLinkInPort, (short) ofsProtectedLinkOutPort, null);
				ofs.protectedLinks.put(ofsProtectedLinkLabel, protectedLink);	
			} catch (Throwable e4) { 
				log.error("Failed to create NetNode from default parameters ", e4);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				throw new ExceptionControlApp("Failed to create NetNode from default parameters ", e4 );
			}
			addNetNode(ofs);
		} 				

		/* PN related initializations. */	
		List<String> pnKeys;
		try {
			pnKeys = dfAppRoot.pNsRepo.getKeys();
		} catch (Throwable e5) {
			log.error("Failed to get keys from pNsRepo ", e5 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from pNsRepo ", e5 );
		}
		if ( pnKeys == null) {
			log.debug ("Invalid null pNsRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null oFCsRepo");
		}

		for(int i=0; i<pnKeys.size();i++) // Re-register all protected networks in controller if not there yet
			addPN(pnKeys.get(i));

		try {
			if(pnKeys.size() == 0) { // Add PN if one set through properties file. Next time PN will be in repo.
				loadAndSetPNsFromPropsFile();
			}
		} catch (Throwable e6) { 
			log.error("Failed to create PN from default parameters ", e6);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DFMgmtPoint failed to start.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to create PN from default parameters ", e6 );
		}
	}

	/**
	 * Cleanup before shutdown
	 * @throws exception_type circumstances description 
	 */
	public void finit() {
		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DFMgmtPoint is stopping.");		
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DFMgmtPoint is resetting to level " + resetLevel);	
		super.reset(resetLevel);
		resetPns(resetLevel);
	}

	protected void loadAndSetPNsFromPropsFile() throws Exception {

		// Read properties file with default PNs attributes
		Properties propsFromFile = new Properties();
		Properties pnFields = new Properties();
		InputStream is=DFMgmtPointImpl.class.getClassLoader().getResourceAsStream("pns.properties");
		try {
			propsFromFile.load(is);
		} catch(IOException ioExc) {
			log.warn(ioExc);
			fr.logRecord(DFAppRoot.FR_DF_OPERATIONAL, "DF failed to instantiate PN from properties file.");
			return;
		}

		String pnLabels = propsFromFile.getProperty("PN.pns");
		if(pnLabels == null || pnLabels.isEmpty()) return;
		
		PN defaultPN = new PN(); 
		Set<String> pnKeySet = defaultPN.toRow().keySet();
		Properties pnProps; Properties pnAmsProps; Set<Object> propsFromFileKeySet;
		String propFromFileKey; String pnAttributeKey; String[] split; String propFromFile;
		int amsPropertyPrefixLen = PN.AMS_PROPERTY_PREFIX.length();
		List<String> netNodes = new ArrayList<String>();

		/* Set each property for relevant pn in the props file - either into its attribute or
		 * into the props attribute (can be in case properties of PN sub-classes are set) */
		String[] pnLabelsSplit;
		try {
			pnLabelsSplit =  pnLabels.split(",");
		} catch (Exception e) {
			log.error("Invalid lead line(PN.pns) in pns.properties file", e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to instantiate PN from properties file.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid lead line(PN.pns) in pns.properties file", e);
		}

		for( String pnLabel : pnLabelsSplit ) {

			try {
				pnProps = new Properties();
				pnAmsProps = new Properties();
				propsFromFileKeySet = propsFromFile.keySet();
				for ( Object keyObject : propsFromFileKeySet ) {
					propFromFileKey = (String) keyObject;
					split = propFromFileKey.split("\\.");
					if(split.length < 3) continue;
					if(!split[1].equals(pnLabel)) continue;
					pnAttributeKey = split[2];
					propFromFile = propsFromFile.getProperty(propFromFileKey);
					if (pnKeySet.contains(pnAttributeKey))
						pnFields.setProperty(pnAttributeKey, propFromFile);
					else if(pnAttributeKey.startsWith(PN.AMS_PROPERTY_PREFIX))
						pnAmsProps.setProperty(pnAttributeKey.substring(amsPropertyPrefixLen), propFromFile);
					else if(pnAttributeKey.startsWith(PN.NETNODE_PREFIX))
						netNodes.add(propFromFile);
					else
						pnProps.setProperty(pnAttributeKey, propFromFile);
				}

				// construct new PN from hash of attributes
				PN pn = new PN(pnFields, pnProps, pnAmsProps, netNodes);
				// Set type of detection from registered detector
				Detector pnDetector = dfAppRootFullImpl.detectorMgrImpl.getDetector(pn.getDetectorLabel());
				if ( pnDetector  != null ) {
					pn.setOfBasedDetection ( pnDetector.getDetectorInfo().getOfBasedDetector() );   
				}
				addPN(pn);
			} catch (Exception e) {
				log.error("Failed to create PN from pns.properties file", e);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to instantiate PN from properties file.");
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;	
			}
		}	
	}

	protected void resetPns(ResetLevel resetLevel) throws ExceptionControlApp {

		Hashtable<String, Hashtable<String, Object>> pnTable;
		try {
			pnTable = dfAppRootFullImpl.pNsRepo.getTable();
		} catch (Throwable e) {
			log.error("Failed to get pNsRepo table", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pNsRepo table", e);
		}
		Set<Entry<String, Hashtable<String, Object>>> pnTableEntrySet = pnTable.entrySet();
		if (pnTableEntrySet == null ) return;

		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = pnTableEntrySet.iterator();
		Map.Entry<String,Hashtable<String,Object>> entry; String pnKey;	PN pn;

		while(iter.hasNext()) {

			try {
				entry = iter.next();
				pnKey = entry.getKey();
			} catch (Exception e1) {
				log.error("Invalid entry in pNsRepo table", e1);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}  
			try {
				pn = new PN(entry.getValue());
			} catch (ExceptionControlApp e) {continue; /* Ignore */}

			pn.averageStr = ""; pn.latestRateStr = ""; pn.latestRateTime = 0; pn.attackSuspicions = "";
			pn.statsCollectionStatus = StatsCollectionStatus.INVALID;
			if(resetLevel == ResetLevel.dynamic) {
				pn.baselineStr = ""; pn.baselinesTime = 0;
			}

			try {
				dfAppRootFullImpl.pNsRepo.setRow(pnKey, pn.toRow());
			} catch (Throwable e) {
				log.error("Failed to set pnRow in pNsRepo table "+pnKey, e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
		}
	}

	protected void removeCanceledPns() throws ExceptionControlApp {
		Hashtable<String, Hashtable<String, Object>> pnTable;
		try {
			pnTable = dfAppRootFullImpl.pNsRepo.getTable();
		} catch (Throwable e) {
			log.error("Failed to get pNsRepo table", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pNsRepo table", e);
		}
		Set<Entry<String, Hashtable<String, Object>>> pnTableEntrySet = pnTable.entrySet();
		if (pnTableEntrySet == null ) return;

		Map.Entry<String,Hashtable<String,Object>> entry; Hashtable<String,Object> pnRow; boolean canceled;
		Iterator<Map.Entry<String,Hashtable<String,Object>>> iter = pnTableEntrySet.iterator();

		while(iter.hasNext()) {

			try {
				entry = iter.next();
				pnRow = entry.getValue();
			} catch (Exception e1) {
				log.error("Invalid entry in pNsRepo table", e1);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}  

			canceled = (Boolean) pnRow.get(PN.PN_CANCELED);
			try {
				if(canceled) dfAppRootFullImpl.pNsRepo.deleteRow(entry.getKey());
			} catch (Throwable e) {
				log.error("Failed to delete pnRow in pNsRepo table "+entry.getKey(), e);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
				continue;
			}
		}
	}

	/**
	 * Add OFC according to its purpose - collection or dvsn (the same OFC can be set for both). Ignore if
	 * already set (currently we accept only one).
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addOFC(OFC ofc) throws ExceptionControlApp {		

		List<String> keys;
		
		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is adding OFC " + ofc.toString());
		try {
			keys = dfAppRootFullImpl.oFCsRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to get keys from oFCsRepo.",e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of OFC " + ofc.hostname);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from oFCsRepo." , e );
		}
		if(keys.size() != 0) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF currently supports one OFC. OFC " + ofc.hostname + " is not added.");
			return;	// Already have a set OFC. Currently there can be only one OFC in the system.
		}	
		try {
			dfAppRootFullImpl.oFCsRepo.setRow(ofc.hostname, ofc.toRow()); // Record ofc in ofcs repo.		
		} catch  (Throwable e) {
			log.error("Failed to set row to oFCsRepo. "+ ofc.hostname,e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of OFC " + ofc.hostname);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to set row to oFCsRepo. "+ ofc.hostname,e);
		}
		try {
			addOFC(ofc.hostname); // Notify relevant DF modules. 
		} catch (Throwable e) {
			log.error("Failed to add OFC "+ofc.hostname, e);
			throw new ExceptionControlApp("Failed to add OFC "+ofc.hostname, e);
		}
	}

	/**
	 * Add OFC that has already been recorded in ofcs repo. Add according to its purpose - collection or dvsn 
	 * (the same OFC can be set for both). Ignore if already set (currently we accept only one).
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addOFC(String ofcKey) throws ExceptionControlApp {

		Boolean forStatsCollection = false;	 Boolean forDvsn= false;  boolean isError = false;
		ExceptionControlApp concatException = new ExceptionControlApp("");		

		try {
			forStatsCollection = (Boolean) dfAppRootFullImpl.oFCsRepo.getCellValue(ofcKey, OFC.FOR_STATS_COLLECTION);
			forDvsn = (Boolean) dfAppRootFullImpl.oFCsRepo.getCellValue(ofcKey, OFC.FOR_DIVERSION);
		} catch (Throwable e) {
			log.error("Failed to get cells from oFCsRepo. ofcKey : "+ ofcKey,e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get cells from oFCsRepo. ofcKey : " +ofcKey, e );
		}

		if(forStatsCollection) {
			try {
				dfAppRootFullImpl.getStatsCollectionRep().addOFC(ofcKey);
			} catch (Throwable e1) {
				log.error("Failed to add OFC to StatsCollectionRep. ofcKey : "+ ofcKey,e1);
				concatException = new ExceptionControlApp ("Failed to add OFC to StatsCollectionRep. ofcKey : "+ ofcKey,concatException);
				isError = true;
			}
		}
		if(forDvsn) {
			try {
				dfAppRootFullImpl.getDvsnRep().addOFC(ofcKey);
			} catch (Throwable e2) {
				log.error("Failed to add OFC to DvsnRep. ofcKey : "+ ofcKey,e2);
				concatException = new ExceptionControlApp ("Failed to add OFC to DvsnRep. ofcKey : "+ ofcKey,concatException);	
			}
		}
		if (isError ) throw concatException;
	}

	/**
	 * Remove OFC. The application will attempt to contact the previously set OFC in order to remove all monitors set through it.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeOFC(String ofcLabel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is removing OFC " + ofcLabel);
		
		String ofcKey=ofcLabel; // get from repo
		boolean forStatsCollection = false; // retrieve from repo
		boolean forDvsn = false; // retrieve from repo
		ExceptionControlApp concatException = new ExceptionControlApp("");
		boolean isError = false;

		try {
			forStatsCollection = (Boolean) dfAppRootFullImpl.oFCsRepo.getCellValue(ofcKey, OFC.FOR_STATS_COLLECTION);
			forDvsn = (Boolean) dfAppRootFullImpl.oFCsRepo.getCellValue(ofcKey, OFC.FOR_DIVERSION);
		} catch (Throwable e) {
			log.error("Failed to get cells from oFCsRepo. ofcKey : "+ ofcKey,e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process removal of OFC " + ofcLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get cells from oFCsRepo. ofcKey : " +ofcKey, e );
		}

		if(forStatsCollection) {
			try {
				dfAppRootFullImpl.getStatsCollectionRep().removeOFC(ofcKey);
			} catch (Throwable e1) {
				log.error("Failed to remove OFC from StatsCollectionRep. ofcKey : "+ ofcKey,e1);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove OFC " + ofcLabel);
				concatException = new ExceptionControlApp ("Failed to remove OFC from StatsCollectionRep. ofcKey : "+ ofcKey,concatException);
			}
		}
		if(forDvsn) {
			try {
				dfAppRootFullImpl.getDvsnRep().removeOFC(ofcKey);
			} catch (Throwable e2) {
				log.error("Failed to remove OFC from DvsnRep. ofcKey : "+ ofcKey,e2);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove OFC " + ofcLabel);
				concatException = new ExceptionControlApp ("Failed to remove OFC from DvsnRep. ofcKey : "+ ofcKey,concatException);		
			}
		}
		if (isError ) throw concatException;
	}

	/**
	 * Add netNode. Notify statsCollectionRep and DvsnRep.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(NetNode netNode) throws ExceptionControlApp {
		
		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is adding NetNode " + netNode.toString());
		
		try {
			dfAppRootFullImpl.netNodesRepo.setRow(netNode.label, netNode.toRow()); // Record netNode in netNodes repo.		
		} catch (Exception e) {
			log.error("Failed to setRow in netNodesRepo "+netNode.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of NetNode " + netNode.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to setRow in netNodesRepo "+netNode.label, e );
		}
		addNetNode(netNode.label); // Notify relevant DF modules. 
	}

	/**
	 * Add netNode that has already been recorded in netNodes repo. Notify statsCollectionRep and DvsnRep.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addNetNode(String netNodeKey) throws ExceptionControlApp{		
		boolean isError = false;
		ExceptionControlApp concatException = new ExceptionControlApp("");

		try {
			dfAppRootFullImpl.getStatsCollectionRep().addNetNode(netNodeKey);
		} catch (Throwable e) {
			log.error("Failed to update StatsCollectionRep", e);
			concatException = new ExceptionControlApp ("Failed to update StatsCollectionRep", concatException );
			isError = true;
		}		
		try {
			dfAppRootFullImpl.getDvsnRep().addNetNode(netNodeKey);
		} catch (Throwable e) {
			log.error("Failed to update DvsnRep", e);
			concatException = new ExceptionControlApp ("Failed to update DvsnRep", concatException );
			isError = true;
		}
		try {
			dfAppRoot.netNodesRepo.setCell(netNodeKey, NetNode.STATUS, NetNode.Status.ACTIVE.name());
		} catch (Throwable e) {
			log.error("Failed to update netNodesRepo", e);
			concatException = new ExceptionControlApp ("Failed to update netNodesRepo", concatException );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			isError = true;
		}
		try {
			dfAppRootFullImpl.mitigationMgrImpl.addNetNode(netNodeKey);
		} catch (Throwable e) {
			log.error("Failed to update mitigationMgr", e);
			concatException = new ExceptionControlApp ("Failed to update mitigationMgrImpl", concatException );
			isError = true;
		}

		if (isError) throw concatException;
	}

	/**
	 * Remove netNode. The application will attempt to remove all traffic counting/diversion elements set in it.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeNetNode(String netNodeLabel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is removing NetNode " + netNodeLabel);
		
		boolean isError = false;
		ExceptionControlApp concatException = new ExceptionControlApp("");
		try {
			dfAppRoot.netNodesRepo.setCell(netNodeLabel, NetNode.STATUS, NetNode.Status.REMOVED);
		} catch (Throwable e) {
			log.error("Failed to update netNodesRepo", e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process removal of NetNode " + netNodeLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw  new ExceptionControlApp ("Failed to update netNodesRepo", e );		
		}	
		try {
			dfAppRootFullImpl.getDvsnRep().removeNetNode(netNodeLabel);
		} catch (Throwable e) {
			log.error("Failed to update DvsnRep", e);
			concatException = new ExceptionControlApp ("Failed to update DvsnRep", concatException );
			isError = true;
		}
		try {
			dfAppRootFullImpl.getStatsCollectionRep().removeNetNode(netNodeLabel);
		} catch (Throwable e) {
			log.error("Failed to update StatsCollectionRep", e);
			concatException = new ExceptionControlApp ("Failed to update StatsCollectionRep", concatException );
			isError = true;
		}	
		if (isError) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process removal of NetNode " + netNodeLabel);
			throw concatException;
		}
	}

	/**
	 * Add DP. If it is for stats collection need to notify the DP-based detector. If it is for dvsn,
	 * notify both the dvsn rep and the DP rep.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addAMS(AMS ams) throws ExceptionControlApp, IllegalArgumentException {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is adding AMS " + ams.toString());
		
		Hashtable<String,Object> amsRow = dfAppRootFullImpl.amsRepo.getRow(ams.label);
		if(amsRow != null) {
			String msg = "AMS " + ams.label + " is already defined. Need to delete it first";
			fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF rejected adding AMS "+ams.label+". AMS with by label already exists.");
			throw new IllegalArgumentException(msg);
		}
		try {
			dfAppRootFullImpl.amsRepo.setRow(ams.label, ams.toRow());
		} catch (ExceptionControlApp e) {
			log.error("Failed to row to amsRepo "+ams.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of AMS " + ams.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to add AMS "+ams.label, e );
		}			
		addAMS(ams.label);
	}

	/**
	 * #### description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addAMS(String amsKey)  throws ExceptionControlApp  {
		try {
			dfAppRootFullImpl.amsRep.addAMS(amsKey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to add AMS "+amsKey, e );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to add AMS "+amsKey, e );
		}
	}

	/**
	 * #### description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeAMS(String amsLabel) throws ExceptionControlApp  {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is removing AMS " + amsLabel);
		try {
			dfAppRootFullImpl.amsRep.removeAMS(amsLabel);
		} catch (ExceptionControlApp e) {
			log.error("Failed to remove AMS "+amsLabel, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove AMS " + amsLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to remove AMS "+amsLabel, e );
		}
	}

	/**
	 * Add an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */	
	public void addDetector(DetectorInfo detectorInfo) throws ExceptionControlApp {
		
		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is adding detector " + detectorInfo.toString());
		
		try {
			if ( detectorInfo.getExternalDetector() == true) {
				ExternalDetector externalDetector = new ExternalDetector(detectorInfo);
				dfAppRootFullImpl.detectorMgrImpl.addDetector(externalDetector);				
			}
		} catch (Throwable e) {
			log.error("Failed to add detector "+detectorInfo.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of Detector " + detectorInfo.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to add detector "+detectorInfo.label, e );
		}			
	}


	/**
	 * Remove an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeDetector(String detectorLabel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is removing detector " + detectorLabel);
		try {
			dfAppRootFullImpl.detectorMgrImpl.removeDetector(detectorLabel);
		} catch (Throwable e) {
			log.error("Failed to remove detector "+detectorLabel, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove Detector " + detectorLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to remove detector "+detectorLabel, e );
		}
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
	public void addPN(PN pn) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is adding PN " + pn.toString());
		
		Hashtable<String, Object> pnRow;
		try {
			pnRow = dfAppRootFullImpl.pNsRepo.getRow(pn.label);
		} catch (Throwable e) {
			log.error("Failed to get pnRow from pNsRepo ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of PN " + pn.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pnRow from pNsRepo ", e );
		}

		if(pnRow != null) {
			String msg = "Protected network " + pn.label + " is already defined. Need to delete it first";
			fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF rejected addition of PN " + pn.label + ". Illegal argument(s).");
			throw new IllegalArgumentException(msg);
		}
		try {
			dfAppRootFullImpl.pNsRepo.setRow(pn.label, pn.toRow());
		} catch (Throwable e) {
			log.error("Failed to create pnRow from pNsRepo. pn label "+pn.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to process addition of PN " + pn.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to create pnRow from pNsRepo. pn label "+pn.label, e );
		}			
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
	protected void addPN(String pnKey) throws ExceptionControlApp {

		String detectorLabel;
		try {
			detectorLabel = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DETECTOR_LABEL);
		} catch (Throwable e) {
			log.error("Failed to get detectorLabel from pNsRepo for pnKey "+pnKey, e );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to update pNsRepo for pnKey "+pnKey, e );
		}

		try {
			dfAppRootFullImpl.mitigationMgrImpl.addPN(pnKey);		
			Detector detector = dfAppRootFullImpl.detectorMgrImpl.getDetector(detectorLabel);
			if ( detector != null) {
				boolean ofBasedDetector = detector.getDetectorInfo().ofBasedDetector;
				dfAppRoot.pNsRepo.setCell(pnKey, PN.OF_BASED_DETECTION, ofBasedDetector); // Base on detector, not client param
				detector.addPN(pnKey);
			}
		} catch (Throwable e) {
			log.error("Failed to add PN to detector and/or mitigation manager", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to add PN to detector and/or mitigation manager", e);
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
	public void removePN(String pnLabel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is removing PN " + pnLabel);
		
		String pnKey = pnLabel;
		boolean isError = false;
		ExceptionControlApp concatException = new ExceptionControlApp("");

		Hashtable<String, Object> pnRow;
		try {
			pnRow = dfAppRootFullImpl.pNsRepo.getRow(pnKey);
		} catch (ExceptionControlApp e1) {
			log.error("Failed to get pnRow from pNsRepo for pnKey "+pnKey, e1 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove PN " + pnLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pnRow from pNsRepo for pnKey "+pnKey, e1 );
		}

		if (pnRow == null ) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove PN " + pnLabel);
			throw new ExceptionControlApp("Protected network " + pnLabel + " is unknown.");
		}

		try {
			String detectorLabel = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DETECTOR_LABEL);
			Detector detector = dfAppRootFullImpl.detectorMgrImpl.getDetector(detectorLabel);  
			if ( detector != null)
				detector.removePN(pnKey);
		} catch (Throwable e1) {
			log.error("Failed to remove PN from detector. PN key "+pnKey, e1);
			concatException = new ExceptionControlApp ("Failed to remove PN from detector. PN key "+pnKey, concatException);
			isError = true;
		}

		try {
			dfAppRootFullImpl.attackDecisionPointImpl.removePN(pnKey);
		} catch (Exception e1) {
			String msg = "Failed to remove PN from attackDecisionPointImpl. PN key "+pnKey;
			log.error(msg, e1);
			concatException = new ExceptionControlApp (msg, concatException);
			isError = true;
		}

		try {
			dfAppRootFullImpl.mitigationMgrImpl.removePN(pnKey);
		} catch (Exception e1) {
			log.error("Failed to remove PN from mitigationMgr. PN key "+pnKey, e1);
			concatException = new ExceptionControlApp ("Failed to remove PN from mitigationMgr. PN key "+pnKey, concatException);
			isError = true;
		}

		try {
			dfAppRoot.pNsRepo.setCell(pnKey, PN.PN_CANCELED, true);
		} catch (Throwable e) {
			log.error("Failed to update pNsRepo for pnKey "+pnKey, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove PN " + pnLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to update pNsRepo for pnKey "+pnKey, e );
		} 

		if (isError) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove PN " + pnLabel);
			throw concatException;
		}
		// Cannot remove the PN yet because the above methods are asynchronous
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {}

	public void test() {
	}
}
