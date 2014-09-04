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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.transaction.NotSupportedException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFDetector;
import org.opendaylight.defense4all.core.DFMgmtPoint;
import org.opendaylight.defense4all.core.Detector;
import org.opendaylight.defense4all.core.DetectorInfo;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PN.OperationalStatus;
import org.opendaylight.defense4all.core.PN.StatsCollectionStatus;
import org.opendaylight.defense4all.core.interactionstructures.PNStatReport;
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

	private static Log log = LogFactory.getLog(DFMgmtPointImpl.class);

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

		log.info("DefenseFlow management failed in initialize.");

		/* OFC related initializations. */
		List<String> ofcKeys = null;
		try {
			ofcKeys = dfAppRoot.oFCsRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to get keys from oFCsRepo ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from oFCsRepo ", e );
		}
		if ( ofcKeys == null) {
			log.error("Invalid null oFCsRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null oFCsRepo");
		}

		if(ofcKeys.size() > 0) {
			try {
				addOFC(ofcKeys.get(0));
			} catch (Throwable e) {
				// TODO Currently Ignore, but need to account for regular init vs. reset init
				log.error("Failed to re-add OFC " + ofcKeys.get(0), e);
			}
		}

		/* AMS related initializations. */
		List<String> amsKeys;
		try {
			amsKeys = dfAppRoot.amsRepo.getKeys();
		} catch (Exception e1) {
			log.error("Failed to get keys from amsRepo ", e1 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from amsRepo ", e1 );
		}
		if ( amsKeys == null) {
			log.debug ("Invalid null amsRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null oFCsRepo");
		}

		for(int i=0; i<amsKeys.size();i++) // Re-register all AMSs if not there yet
			addAMS(amsKeys.get(i));

		/* NetNode related initializations. */
		List<String> netNodeKeys;
		try {
			netNodeKeys = dfAppRoot.netNodesRepo.getKeys();
		} catch (Throwable e3) {
			log.error("Failed to get keys from amsRepo ", e3 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from netNodesRepo ", e3);
		}
		if ( netNodeKeys == null) {
			log.debug ("Invalid null netNodesRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null netNodesRepo");
		}


		/* Re-register all NetNodes if not there yet, except those marked as removed. */
		for(String netNodeLabel : netNodeKeys) {
			try {
				if(NetNode.isRemoved(netNodeLabel))
					dfAppRoot.netNodesRepo.deleteRow(netNodeLabel);
				else
					addNetNode(netNodeLabel);	
			} catch (Throwable e) {continue; /* Ignore */}
		}

		/* PN related initializations. */	
		List<String> pnKeys;
		try {
			pnKeys = dfAppRoot.pNsRepo.getKeys();
		} catch (Throwable e5) {
			log.error("Failed to get keys from pNsRepo ", e5 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from pNsRepo ", e5 );
		}
		if ( pnKeys == null) {
			log.debug ("Invalid null pNsRepo");
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Invalid null oFCsRepo");
		}

		for(int i=0; i<pnKeys.size();i++) { // Re-register all protected networks in controller if not there yet
			try {
				addPN(pnKeys.get(i));
			} catch (Throwable e) {
				log.error("Failed to re-add PN " + pnKeys.get(i), e);
			}
		}

		try {
			if(pnKeys.size() == 0) { // Add PN if one set through properties file. Next time PN will be in repo.
				loadAndSetPNsFromPropsFile();
			}
		} catch (Throwable e6) { 
			log.error("Failed to create PN from default parameters ", e6);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow management failed in initialize.");
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to create PN from default parameters ", e6 );
		}
	}

	/**
	 * Cleanup before shutdown
	 * @throws exception_type circumstances description 
	 */
	public void finit() {
		log.info("DFMgmtPoint is stopping.");		
		super.finit();
	}

	/** Reset 
	 * @throws ExceptionControlApp */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		log.info("DFMgmtPoint is resetting to level " + resetLevel);	
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
			log.warn("DF failed to instantiate PN from properties file.", ioExc);
			try {
				is.close();
			} catch (IOException e) {/* Ignore */}
			return;
		}
		try {
			is.close();
		} catch (IOException e) {/* Ignore */}

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
			//fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to instantiate PN from properties file.");
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
				//fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to instantiate PN from properties file.");
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

		List<String> pnkeys;
		try {
			pnkeys = dfAppRootFullImpl.pNsRepo.getKeys();
			if(pnkeys == null) return;
		} catch (Throwable e) {
			log.error("Failed to get pNsRepo keys", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pNsRepo keys", e);
		}

		for(String pnkey : pnkeys) {
			try {
				removePnIfCanceled(pnkey);
			} catch (Throwable e1) {continue;}  
		}
	}

	protected void removePnIfCanceled (String pnkey) throws ExceptionControlApp {

		try {
			Object obj = dfAppRootFullImpl.pNsRepo.getCellValue(pnkey, PN.OPERATIONAL_STATUS);
			OperationalStatus operationalStatus = OperationalStatus.INVALID;
			if(obj != null)	operationalStatus = OperationalStatus.valueOf((String) obj);
			if(operationalStatus == OperationalStatus.CANCELED) dfAppRootFullImpl.pNsRepo.deleteRow(pnkey);
		} catch (Throwable e) {
			log.error("Failed to delete pnRow in pNsRepo table "+pnkey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to remove canceled PN " + pnkey, e);
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
	public void addOFC(OFC ofc) throws ExceptionControlApp, IllegalArgumentException {	

		try {
			ofc.validate();
		} catch (Exception e1) {throw new IllegalArgumentException(e1);}	

		List<String> keys;

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow adding a controller. ");
		try {
			keys = dfAppRootFullImpl.oFCsRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to get keys from oFCsRepo.",e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add controller " + ofc.hostname);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get keys from oFCsRepo." , e );
		}
		if(keys.size() != 0) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow currently supports only one controller. Controller "
					+ ofc.hostname + " was not added.");
			return;	// Already have a set OFC. Currently there can be only one OFC in the system.
		}	
		try {
			dfAppRootFullImpl.oFCsRepo.setRow(ofc.hostname, ofc.toRow()); // Record ofc in ofcs repo.		
		} catch  (Throwable e) {
			log.error("Failed to set row to oFCsRepo. "+ ofc.hostname,e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add controller " + ofc.hostname);
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
				int interval = dfAppRootFullImpl.getStatsCollectionRep().getStatsCollectionInterval();
				dfAppRootFullImpl.statsCollectorImpl.startCollection(interval);
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

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow removing controller " + ofcLabel);

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
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove controller " + ofcLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get cells from oFCsRepo. ofcKey : " +ofcKey, e );
		}

		if(forStatsCollection) {
			try {
				dfAppRootFullImpl.getStatsCollectionRep().removeOFC(ofcKey);
			} catch (Throwable e1) {
				log.error("Failed to remove OFC from StatsCollectionRep. ofcKey : "+ ofcKey,e1);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove controller " + ofcLabel);
				concatException = new ExceptionControlApp ("Failed to remove OFC from StatsCollectionRep. ofcKey : "+ ofcKey,concatException);
			}
		}
		if(forDvsn) {
			try {
				dfAppRootFullImpl.getDvsnRep().removeOFC(ofcKey);
			} catch (Throwable e2) {
				log.error("Failed to remove OFC from DvsnRep. ofcKey : "+ ofcKey,e2);
				fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove controller " + ofcLabel);
				concatException = new ExceptionControlApp ("Failed to remove OFC from DvsnRep. ofcKey : "+ ofcKey,concatException);		
			}
		}
		if (isError ) throw concatException;
	}

	/**
	 * Add netNode. Notify statsCollectionRep and DvsnRep.
	 * @param param_name param description
	 * @return return description
	 * @throws NotSupportedException 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void addNetNode(NetNode netNode) throws ExceptionControlApp, IllegalArgumentException, IllegalStateException,
	NotSupportedException { 

		try {
			netNode.validate();
		} catch (Exception e1) {throw new IllegalArgumentException(e1);}

		Hashtable<String, Object> netNodeRow;
		try {
			netNodeRow = dfAppRootFullImpl.pNsRepo.getRow(netNode.label);
		} catch (Throwable e) {
			log.error("Failed to get netNodeRow from netNodesRepo ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add NetNode " + netNode.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("DF Failed to process addition of netNode " + netNode.label, e );
		}

		if(netNodeRow != null) {
			NetNode.Status netNodeStatus = NetNode.Status.valueOf((String) netNodeRow.get(NetNode.STATUS));
			if(netNodeStatus == NetNode.Status.REMOVED) {
				try {
					dfAppRootFullImpl.netNodesRepo.deleteRow(netNode.label);
				} catch(Throwable e1) {
					log.error("Failed to delete netNodeRow from netNodesRepo ", e1 );
					fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add NetNode " + netNode.label);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					throw new ExceptionControlApp("Failed to delete netNodeRow from netNodesRepo ", e1 );
				}
			} else {
				String msg = "NetNode " + netNode.label + " is already defined. Need to delete it first";
				fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow failed to add NetNode "+netNode.label+". Illegal argument(s).");
				throw new IllegalStateException(msg);
			}
		}

		fr.logRecord(DFAppRoot.FR_DF_CONFIG,  "DefenseFlow adding NetNode " + netNode.label);

		try {
			dfAppRootFullImpl.netNodesRepo.setRow(netNode.label, netNode.toRow()); // Record netNode in netNodes repo.		
		} catch (Exception e) {
			log.error("Failed to setRow in netNodesRepo "+netNode.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add NetNode " + netNode.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to setRow in netNodesRepo "+netNode.label, e );
		}
		addNetNode(netNode.label); // Notify relevant DF modules. 
	}

	/**
	 * Add netNode that has already been recorded in netNodes repo. Notify statsCollectionRep and DvsnRep.
	 * @param param_name param description
	 * @return return description
	 * @throws NotSupportedException 
	 * @throws exception_type circumstances description 
	 */
	protected void addNetNode(String netNodeKey) throws ExceptionControlApp, NotSupportedException {		
		boolean isError = false;
		ExceptionControlApp concatException = new ExceptionControlApp("");

		try {
			dfAppRootFullImpl.getStatsCollectionRep().addNetNode(netNodeKey);
		} catch (NotSupportedException e) {
			throw e; // Not supported so abort addNetNode processing and throw this exception
		} catch (Throwable e) {
			log.error("Failed to update StatsCollectionRep", e);
			concatException = new ExceptionControlApp ("Failed to update StatsCollectionRep", concatException );
			isError = true;
		}		
		try {
			dfAppRootFullImpl.getDvsnRep().addNetNode(netNodeKey);
		} catch (NotSupportedException e) {
			try {
				dfAppRootFullImpl.getStatsCollectionRep().removeNetNode(netNodeKey);
			} catch (Throwable e1) {/* Ignore */}
			throw e; // Not supported so abort addNetNode processing and throw this exception
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

		if (isError) throw concatException; // TODO: Need to undo completed actions
	}

	/**
	 * Remove netNode. The application will attempt to remove all traffic counting/diversion elements set in it.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeNetNode(String netNodeLabel) throws ExceptionControlApp, NotSupportedException {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow removing NetNode " + netNodeLabel);

		boolean isError = false;

		if(NetNode.isRemoved(netNodeLabel))	return; // Check if is already marked as removed.

		ExceptionControlApp concatException = new ExceptionControlApp("");
		try {
			dfAppRootFullImpl.getDvsnRep().removeNetNode(netNodeLabel);
		} catch (NotSupportedException e) {
			throw e; // Not supported so abort removeNetNode processing and throw this exception
		} catch (IllegalStateException e1) {
			throw e1; // NetNode cannot be removed at this time/state - abort removeNetNode processing and throw this exception
		} catch (Throwable e2) {
			log.error("Failed to update DvsnRep", e2);
			concatException = new ExceptionControlApp ("Failed to update DvsnRep", concatException );
			isError = true;
		}
		try {
			dfAppRootFullImpl.getStatsCollectionRep().removeNetNode(netNodeLabel);
		} catch (NotSupportedException e) {
			throw e; // Not supported so abort removeNetNode processing and throw this exception
		} catch (Throwable e) {
			log.error("Failed to update StatsCollectionRep", e);
			concatException = new ExceptionControlApp ("Failed to update StatsCollectionRep", concatException );
			isError = true;
		}	
		if (isError) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove NetNode " + netNodeLabel);
			throw concatException;
		}

		try {
			dfAppRoot.netNodesRepo.setCell(netNodeLabel, NetNode.STATUS, NetNode.Status.REMOVED.name());
		} catch (Throwable e) {
			log.error("Failed to update netNodesRepo", e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove NetNode " + netNodeLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw  new ExceptionControlApp ("Failed to update netNodesRepo", e );		
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

		try {
			ams.validate();
		} catch (Exception e1) {throw new IllegalArgumentException(e1);}

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow adding AMS " + ams.label);

		Hashtable<String,Object> amsRow = dfAppRootFullImpl.amsRepo.getRow(ams.label);
		if(amsRow != null) {
			AMS.Status status = AMS.Status.valueOf((String) amsRow.get(AMS.STATUS));
			if(status == AMS.Status.REMOVED) {
				try {
					dfAppRootFullImpl.amsRepo.deleteRow(ams.label);
				} catch (Throwable e) {
					log.error("Failed to delete amsRow from amsRepo ", e );
					fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add AMS " + ams.label);
					fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					throw new ExceptionControlApp("Failed to delete pnRow from pNsRepo ", e );
				}
			} else {
				String msg = "AMS " + ams.label + " is already defined. Need to delete it first";
				fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow failed to add AMS "+ams.label+". An AMS with the same label already exists.");
				throw new IllegalArgumentException(msg);
			}
		}
		try {
			dfAppRootFullImpl.amsRepo.setRow(ams.label, ams.toRow());
		} catch (ExceptionControlApp e) {
			log.error("Failed to row to amsRepo "+ams.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add AMS " + ams.label);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to add AMS "+ams.label, e );
		}
        if("Other".equalsIgnoreCase(ams.getBrand())){//Value from GUI
            dfAppRootFullImpl.setAmsRepByType("DefaultAms");//default from Spring xml
        } else {
            dfAppRootFullImpl.setAmsRepByType("DP");//DP configuration from Spring xml
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
	public boolean removeAMS(String amsLabel) throws ExceptionControlApp  {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DF is removing AMS " + amsLabel);

		if(AMS.isRemoved(amsLabel))	return true; // Check if is already marked as removed.

		boolean success = dfAppRootFullImpl.mitigationMgrImpl.removeAMS(amsLabel);
		if(!success) return false;

		try {
			dfAppRootFullImpl.amsRep.removeAMS(amsLabel);
		} catch (ExceptionControlApp e) {
			log.error("Failed to remove AMS "+amsLabel, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DF failed to properly remove AMS " + amsLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("DefenseFlow failed to remove AMS "+amsLabel, e );
		}

		try {
			dfAppRoot.amsRepo.setCell(amsLabel, AMS.STATUS, AMS.Status.REMOVED.name());
		} catch (Throwable e) {
			log.error("Failed to update netNodesRepo", e);
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove AMS " + amsLabel);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp ("Failed to update netNodesRepo", e );		
		}
		return true;
	}

	/**through
	 * Add an external detector.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */	
	public void addDetector(DetectorInfo detectorInfo) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, " DefenseFlow adding "+detectorInfo.label+" detector ");

		try {
			if ( detectorInfo.getExternalDetector() == true) {
				ExternalDetector externalDetector = new ExternalDetector(detectorInfo);
				dfAppRootFullImpl.detectorMgrImpl.addDetector(externalDetector);				
			}
		} catch (Throwable e) {
			log.error("Failed to add detector "+detectorInfo.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add "+detectorInfo.label+" detector ");
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
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove "+detectorLabel+" detector "); 
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
	public void addPN(PN pn) throws Exception, IllegalArgumentException, IllegalStateException {

		try {
			pn.validate();
		} catch (Exception e1) {throw new IllegalArgumentException(e1);}

		Hashtable<String, Object> pnRow;
		try {
			pnRow = dfAppRootFullImpl.pNsRepo.getRow(pn.label);
		} catch (Throwable e) {
			log.error("Failed to get pnRow from pNsRepo ", e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add PO " + PN.getPrintableKey(pn.label));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pnRow from pNsRepo ", e );
		}

		if(pnRow != null) {
			PN existingPn = new PN(pnRow);
			if(existingPn.operationalStatus == OperationalStatus.CANCELED) {
				try {
					removePnIfCanceled(existingPn.label);
				} catch(ExceptionControlApp e1) {throw e1;}
			} else {
				String msg = "Protected network " + pn.label + " is already defined. Need to delete it first";
				fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow failed to add PO " + PN.getPrintableKey(pn.label));
				throw new IllegalStateException(msg);
			}
		}

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow adding PO " + PN.getPrintableKey(pn.label));

		try {
			dfAppRootFullImpl.pNsRepo.setRow(pn.label, pn.toRow());
		} catch (Throwable e) {
			log.error("Failed to create pnRow from pNsRepo. pn label "+pn.label, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to add PO " + PN.getPrintableKey(pn.label));
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

		/* Don't try to add cancelled PN */
		try {
			Object obj = dfAppRootFullImpl.pNsRepo.getCellValue(pnKey, PN.OPERATIONAL_STATUS);
			OperationalStatus operationalStatus = OperationalStatus.INVALID;
			if(obj != null)	operationalStatus = OperationalStatus.valueOf((String) obj);
			if(operationalStatus == OperationalStatus.CANCELED ) return;
		} catch (Throwable e) {
			log.error("Failed to retrieve operational status value for " + pnKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			return;
		}

		/* Reset the PN status to INVALID - to re-attempt its installation after restarts. */
		try {
			dfAppRootFullImpl.pNsRepo.setCell(pnKey, PN.OPERATIONAL_STATUS, PN.OperationalStatus.INVALID.name());
		} catch (Throwable e) {
			log.error("Failed to set PN operational status for "+pnKey, e );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to set PN operational status for "+pnKey, e);
		}

		String detectorLabel;
		try {
			detectorLabel = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DETECTOR_LABEL);
		} catch (Throwable e) {
			log.error("Failed to get detectorLabel from pNsRepo for pnKey "+pnKey, e );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get detectorLabel from pNsRepo for pnKey "+pnKey, e);
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
	@Override
	public Collection<PNStatReport> getLatestPNStatReports() throws ExceptionControlApp {

		DFDetector dfDetector;
		Collection<PNStatReport> pnStatReports = new ArrayList<PNStatReport>();
		Collection<PNStatReport> detectorPnStatReports = new ArrayList<PNStatReport>();

		/* Add pnStatReports from all OF detectors. */
		try {		
			List<Detector> detectors = dfAppRootFullImpl.detectorMgrImpl.getDetectors();
			for(Detector detector : detectors) {
				if (!detector.getDetectorInfo().ofBasedDetector) continue;
				dfDetector = (DFDetector) detector;
				detectorPnStatReports = dfDetector.getLatestPNStatReports();
				pnStatReports.addAll(detectorPnStatReports);
			}
		} catch (Throwable e) {
			log.error("Failed to getLatestPNStatReports ", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to getLatestPNStatReports ", e);
		}

		return pnStatReports;
	}

	/** 
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description
	 */
	@Override
	public PNStatReport getLatestPNStatReport(String pnKey) throws ExceptionControlApp, IllegalArgumentException {

		if(pnKey == null || pnKey.isEmpty()) throw new IllegalArgumentException("Invalid pnkey " + pnKey);

		String detectorLabel; Detector detector; DFDetector dfDetector;
		PNStatReport pnStatReport = new PNStatReport(); pnStatReport.pnKey = pnKey;

		/* Get the label of the detector processing stats for this PN. */
		try {
			detectorLabel = (String) dfAppRoot.pNsRepo.getCellValue(pnKey, PN.DETECTOR_LABEL);
			if (detectorLabel == null) return pnStatReport;
		} catch (Throwable e) {
			log.error("Failed to get detectorLabel from pNsRepo for pnKey " + pnKey, e );
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get detectorLabel from pNsRepo for pnKey " + pnKey, e);
		}

		/* Out of the detector label get the detector processing stats for this PN. */
		try {		
			detector = dfAppRootFullImpl.detectorMgrImpl.getDetector(detectorLabel);
			if (detector == null) return pnStatReport;
			boolean ofBasedDetector = detector.getDetectorInfo().ofBasedDetector;
			if(!ofBasedDetector) return pnStatReport;
			dfDetector = (DFDetector) detector;
		} catch (Throwable e) {
			log.error("Failed to get detector " + detectorLabel, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get detector " + detectorLabel, e);
		}

		/* Retrieve and return the PN stats from that detector. */
		pnStatReport = dfDetector.getLatestPNStatReport(pnKey);		
		return pnStatReport;
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
	public boolean removePN(String pnLabel) throws ExceptionControlApp {

		fr.logRecord(DFAppRoot.FR_DF_CONFIG, "DefenseFlow removing PO " + PN.getPrintableKey(pnLabel));

		String pnKey = pnLabel;
		boolean isError = false;
		ExceptionControlApp concatException = new ExceptionControlApp("");

		Hashtable<String, Object> pnRow;
		try {
			pnRow = dfAppRootFullImpl.pNsRepo.getRow(pnKey);
		} catch (ExceptionControlApp e1) {
			log.error("Failed to get pnRow from pNsRepo for pnKey "+pnKey, e1 );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove PO " + PN.getPrintableKey(pnLabel));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to get pnRow from pNsRepo for pnKey "+pnKey, e1 );
		}

		if (pnRow == null ) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove PO " + PN.getPrintableKey(pnLabel));
			throw new ExceptionControlApp("Protected network " + pnLabel + " is unknown.");
		}

		Object obj = pnRow.get(PN.OPERATIONAL_STATUS);
		OperationalStatus operationalStatus = OperationalStatus.INVALID; 
		if(obj != null)	operationalStatus = OperationalStatus.valueOf((String) obj);
		if(operationalStatus == OperationalStatus.CANCELED) return true;

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
		} catch (Throwable e1) {
			String msg = "Failed to remove PN from attackDecisionPointImpl. PN key "+pnKey;
			log.error(msg, e1);
			concatException = new ExceptionControlApp (msg, concatException);
			isError = true;
		}

		try {
			dfAppRootFullImpl.mitigationMgrImpl.removePN(pnKey);
		} catch (Throwable e1) {
			log.error("Failed to remove PN from mitigationMgr. PN key "+pnKey, e1);
			concatException = new ExceptionControlApp ("Failed to remove PN from mitigationMgr. PN key "+pnKey, concatException);
			isError = true;
		}

		try {
			dfAppRoot.pNsRepo.setCell(pnKey, PN.OPERATIONAL_STATUS, PN.OperationalStatus.CANCELED.name());
		} catch (Throwable e) {
			log.error("Failed to update pNsRepo for pnKey "+pnKey, e );
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove PO " + PN.getPrintableKey(pnLabel));
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to update pNsRepo for pnKey "+pnKey, e );
		} 

		if (isError) {
			fr.logRecord(DFAppRoot.FR_DF_FAILURE, "DefenseFlow failed to remove PO " + PN.getPrintableKey(pnLabel));
			throw concatException;
		}
		// Cannot remove the PN yet because the above methods are asynchronous
		return true;
	}

	protected boolean hasActiveAttacks(String pnKey) {

		List<String> attackKeys = null;
		try {
			attackKeys = dfAppRootFullImpl.attacksRepo.getKeys();
		} catch (ExceptionControlApp e) {
			log.error("Failed to get keys from attacksRepo", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
		if ( attackKeys == null ) return false;

		String attackPnKey;
		for(String attackKey : attackKeys) {
			try {
				attackPnKey = (String) dfAppRootFullImpl.attacksRepo.getCellValue(attackKey, Attack.PNKEY);
				if(attackPnKey.equals(pnKey)) return true; // Found attack on this PN
			} catch (ExceptionControlApp e) {
				log.error("Failed in hasActiveAttacks. Attack key: "+attackKey);
				fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}
		return false;
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {}

	public void test() {
	}
}
