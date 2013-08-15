/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.opendaylight.controlapps.defense4all.core.Mitigation;
import org.opendaylight.controlapps.defense4all.core.DvsnRep;
import org.opendaylight.controlapps.defense4all.core.ProtocolPort;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;

public class OdlDvsnRep extends DvsnRep {

	public static String AMS_IN_POINT_COLUMN = "ams_in_point";
	public static String AMS_OUT_POINT_COLUMN = "ams_out_point";
	
	Odl odl = null;
	Logger log = Logger.getLogger("OdlDvsnRep");
	
	/* Constructor for Spring */
	public OdlDvsnRep() {
		super();
	}
	
	/* Setters for Spring */
	public void setOdl(Odl odl) {this.odl = odl;}
	
	/** Post-constructor initialization	 */
	@Override
	public void init() throws ExceptionControlApp {	
		super.init();
		odl.init();
	}

	/** Pre-shutdown cleanup */
	@Override
	public void finit() {
		super.finit();
		odl.finit();
	}

	/** Reset */
	@Override
	public void reset(ResetLevel resetLevel) {
		super.reset(resetLevel);
		odl.reset(resetLevel);
	}

	@Override
	protected void initConnectionToOFC(String ofcKey) {
		odl.initConnectionToOFC(ofcKey);
		odl.retrieveTopology(ofcKey);
		notifyTopologyChanged();
	}
	
	public void test(Properties props) {
	}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void removeOFC(String ofcKey) {
		// Reuse/extend super class implementation
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return Properties object containing diversion properties. If the traffic to the passed in protected object can not be diverted 
	 * to the passed in DPis (in terms of network capacity, addressing and other constraints) then null is returned.
	 * @throws exception_type circumstances description 
	 */
	@Override
	public Properties getDvsnProps(String pnKey, String amsKey) {
		// Can check diversion links capacity and current load with respect to the diverted traffic size, 
		// constraints with respect to other diverted traffics.
		return new Properties();
	}

	/**
	 * Just retrieve the entire topology.
 	 * This operation is synchronous.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	protected void retrieveTopologyChanges(String ofcKey) {
	}

	/**
	 * Set diversion in the OF network 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public List<String> divert(String mitigationKey) {

		Hashtable<String,Object> mitigationRow = dfAppRoot.mitigationsRepo.getRow(mitigationKey);
		List<String> trafficFloorKeys = new ArrayList<String>();
		
		/* Update diversion record */
		String amsInPoint = "place_holder_ams_in_point";	String amsOutPoint = "place_holder_ams_out_point";		
		mitigationRow.put(AMS_IN_POINT_COLUMN, amsInPoint);
		mitigationRow.put(AMS_OUT_POINT_COLUMN, amsOutPoint);

		try {
			
			String pnKey = (String) mitigationRow.get(Mitigation.PNKEY);
			
			ProtocolPort protocolPort = new ProtocolPort((String) mitigationRow.get(Mitigation.PROTOCOL_PORT));
			frameworkMain.getMyLogger().logRow("defense4all is diverting traffic for " + pnKey + " " + protocolPort.toString() + "!");
			
			; /* TODO: Create diversion back flow */
			; /* TODO: Create diversion flow */
		} catch (Exception e) {
			log.debug("Failed to create diversion" + e.getMessage());
			return null;
		}
		
		/* record the complemented diversion information in diversions repo */
		dfAppRoot.mitigationsRepo.setRow(mitigationKey, mitigationRow);
		return trafficFloorKeys;
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	@Override
	public List<String> endDvsn(String dvsnKey) {

		Hashtable<String,Object> dvsnRow = dfAppRoot.mitigationsRepo.getRow(dvsnKey);
		List<String> trafficFloorKeys = new ArrayList<String>();

		try {
			
			String pnKey = (String) dvsnRow.get(Mitigation.PNKEY);

			ProtocolPort protocolPort = new ProtocolPort((String) dvsnRow.get(Mitigation.PROTOCOL_PORT));
			frameworkMain.getMyLogger().logRow("defense4all is canceling traffic diversion for " + pnKey + " " + protocolPort.toString() + "!");

			; // TODO: Remove diversion path
			; // TODO: Remove diversion back path

		} catch (Exception e) {
			log.debug("Failed to end diversion" + e.getMessage());
			return trafficFloorKeys;
		}
		
		/* record the complemented diversion information in diversions repo */
		dfAppRoot.mitigationsRepo.setRow(dvsnKey, dvsnRow);
		return trafficFloorKeys;
	}
	
	@Override
	public void registerDP(String amsKey) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void unregisterDP(String amsKey) {
		// TODO Auto-generated method stub		
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		// Reuse/extend super class implementation
	}
}
