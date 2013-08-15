/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl;

import java.util.Hashtable;
import java.util.Properties;

import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Serializer;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.DFHolder;
import org.opendaylight.controlapps.defense4all.core.DFAppRoot.RepoMajor;
import org.opendaylight.controlapps.defense4all.core.TrafficFloor;
import org.opendaylight.controlapps.defense4all.odl.controller.Connector;
import org.opendaylight.controlapps.defense4all.odl.controller.FlowEntryMgr;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.ExceptionEntityExists;
import org.opendaylight.controlapps.framework.core.ExceptionRepoFactoryInternalError;
import org.opendaylight.controlapps.framework.core.FrameworkMain;
import org.opendaylight.controlapps.framework.core.Repo;
import org.opendaylight.controlapps.framework.core.RepoFactory;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;

public class Odl {

	/**
	 * Name space allocation of DF NEC Repo minor IDs
	 */
	public enum RepoMinor {	
		INVALID,
		FLOW_CONFIG_INFO,
		TRAFFIC_FLOOR	
	}
	
	public static final String DEFAULT_CONTROLLER_NAME = "default";
	
	protected boolean initialized = false;
	protected boolean cleanedUp = false;
	public FrameworkMain fm = null;	
	public String constFlowUrlPrefix;
	public String constStatsUrlPrefix;
	protected Logger log = Logger.getLogger("odl");	
	public String controllerName = DEFAULT_CONTROLLER_NAME;	
	protected static Hashtable<String,Connector> connectors = new Hashtable<String,Connector>();
	protected FlowEntryMgr flowEntryMgr;
	
	protected Repo<String> odlFlowConfigInfoRepo = null;
	protected Repo<String> odlTrafficFloorRepo = null;
	
	protected Odl() {
	}
	
	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {fm = frameworkMain;}
	public void setConstFlowUrlPrefix(String constFlowUrlPrefix) {this.constFlowUrlPrefix = constFlowUrlPrefix;}
	public void setConstStatsUrlPrefix(String constStatsUrlPrefix) {this.constStatsUrlPrefix = constStatsUrlPrefix;}

	public synchronized void init() throws ExceptionControlApp {
		
		if(initialized) return;

		/* Init all ODL repos. */
		String dfODLRepo = RepoMajor.DF_STATS_COLLECTION_REP.name();
		RepoFactory rf = fm.getRepoFactory();
		Serializer<String> sSer = StringSerializer.get();
		try {
			odlFlowConfigInfoRepo = (Repo<String>) rf.getOrCreateRepo(dfODLRepo, RepoMinor.FLOW_CONFIG_INFO.name(), 
					sSer, true, OdlFlowConfigInfo.getRCDs());
			odlTrafficFloorRepo = (Repo<String>) rf.getOrCreateRepo(dfODLRepo, RepoMinor.TRAFFIC_FLOOR.name(), 
					sSer, true, TrafficFloor.getRCDs());
		} catch (ExceptionRepoFactoryInternalError e) {throw new ExceptionControlApp("Internal framework error. ", e);
		} catch (IllegalArgumentException e) {throw new ExceptionControlApp("Internal framework error. ", e);
		} catch (ExceptionEntityExists e) {throw new ExceptionControlApp("Internal framework error. ", e);}
		
		flowEntryMgr = new FlowEntryMgr();
		initialized = true;
	}

	public synchronized void finit() {
		if(cleanedUp) return;
		cleanedUp = true;
		// Clean up
	}

	public synchronized void reset(ResetLevel resetLevel) {
		
		/* Clear all repos */
		odlFlowConfigInfoRepo.truncate();
		odlTrafficFloorRepo.truncate();
		
		/* Clear all caches */
		flowEntryMgr.reset();
	}

	/**
	 * 
	 * @param ofcKey
	 */
	public synchronized void initConnectionToOFC(String ofcKey) {
		
		Hashtable<String,Object> ofcRow = DFHolder.get().oFCsRepo.getRow(ofcKey);
		OdlOFC odlOfc = new OdlOFC(ofcRow);
		Connector connector = connectors.get(odlOfc.ofcName);
		if(connector == null) {
			connector = new Connector(odlOfc);
			connector.init();
			connectors.put(odlOfc.ofcName, connector);
			if(flowEntryMgr.connector == null) 
				flowEntryMgr.connector = connector; // TODO: in the future need to maintain flowEntryMgr per connector/OFC
		}
	}

	/**
	 * Retrieve the topology from the controller and populate it into the working objects (WOs).
	 * @param ofcKey
	 */
	public synchronized void retrieveTopology(String ofcKey) {
	}

	public void test(Properties props) {
	}
}
