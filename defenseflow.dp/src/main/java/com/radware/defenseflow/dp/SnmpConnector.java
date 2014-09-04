/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Gera Goft
 * @version 0.1
 */

package com.radware.defenseflow.dp;

import java.io.IOException;

import org.opendaylight.defense4all.core.DFAppRoot.HealthStatus;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class SnmpConnector {
	
	protected static final String RDWR_SYS_OBJECT_ID = "1.3.6.1.4.1.89.1.1.62.16";

	public enum DPOID {
		
		SYSTEM_OBJECT_ID (".1.3.6.1.2.1.1.2"),
		RSWSD_RESOURCE_UTILIZATION (".1.3.6.1.4.1.89.35.1.53"),
		SYSTEM_FANS_STATUS(".1.3.6.1.4.1.89.35.1.162.1.2", "0"),
		SYSTEM_DESCRIPTION(".1.3.6.1.2.1.1.1"),
		SYSTEM_NAME(".1.3.6.1.2.1.1.1"),	
		RDWR_TEMPERTURE_CPU_1(".1.3.6.1.4.1.89.35.1.150"),
		RDWR_TEMPERTURE_SHUTDOWN_THRESHOLD(".1.3.6.1.4.1.89.35.1.153"),
		RDWR_TEMPERTURE_WARNING_THRESHOLD(".1.3.6.1.4.1.89.35.1.152");

		private String oidStr;	
		private String endIndex = null;	

		private DPOID(String oidValue){this.oidStr = oidValue;}
		private DPOID(String oidValue, String endIndex){this.oidStr = oidValue;	this.endIndex = endIndex;}

		public String getEndIndex(){return this.endIndex;}

		public OID[] getOIDs(){
			OID oid = new OID(oidStr);
			return new OID[] { oid };
		}

		public OID[] getNextOID(int index){
			OID oid = new OID(oidStr+ "." + index);
			return new OID[] { oid };
		}
	}

	protected static Logger log = LoggerFactory.getLogger(SoapConnector.class);
	protected Snmp snmp = null;
	protected String amsKey;
	protected String dpAddr;
	protected PDU pdu;
	protected TransportMapping transport;
	protected CommunityTarget target;

	public SnmpConnector(String amsKey, String dpAddr) {
		
		this.amsKey = amsKey; this.dpAddr = dpAddr;
		String dpUdpAddrSlashPort = "udp:" + dpAddr + "/161"; // E.g., "udp:10.206.167.51/161". Port 161 - for read ops.
		pdu = new PDU();
		
		target = new CommunityTarget();
		target.setCommunity(new OctetString("public"));
		Address targetAddress = GenericAddress.parse(dpUdpAddrSlashPort); 
		target.setAddress(targetAddress);
		target.setRetries(2);
		target.setTimeout(1500);
		target.setVersion(SnmpConstants.version2c);
	}

	/* Setters for Spring */

	/**
	 * Starts the SNMP client session
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {

		/* If you forget the listen() method you will not get any answers because the communication is asynchronous 
		 * and the listen() method listens for answers. */
		try {
			transport = new DefaultUdpTransportMapping();
			snmp = new Snmp(transport);		
			transport.listen();			
		} catch (IOException e) {
			String msg = "Excepted trying to initialize SNMP client";
			log.error(msg, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);	
		}
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
	}

	/* Takes a single OID and returns the response from the agent as a String. */
	protected String getAsString(DPOID dpoid) throws ExceptionControlApp, IllegalArgumentException {

		if(dpoid == null) throw new IllegalArgumentException("Null dpoid passed to getAsString");
		ResponseEvent responseEvent;
		pdu.clear();
		OID[] oids = dpoid.getOIDs();
		VariableBinding variableBinding = new VariableBinding(oids[0]);
		pdu.add(variableBinding);
		pdu.setType(PDU.GETNEXT); // Konsta - GET ?

		try {			
			responseEvent = snmp.getNext(pdu, target);
		} catch (Throwable e) {
			String msg = "Excepted trying to get SNMP event from " + dpAddr;
			log.error(msg, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);	
		}

		if(responseEvent == null) return null;
		PDU pdu2 = responseEvent.getResponse(); if(pdu2 == null) return null;
		VariableBinding variableBinding2 = pdu2.get(0); if(variableBinding2 == null) return null;
		Variable variable = variableBinding2.getVariable(); if(variable == null) return null;
		return variable.toString();
	}
	
	public HealthStatus getStatus() {

		try {
			String livenessStr = getAsString(DPOID.SYSTEM_OBJECT_ID);
			if(livenessStr == null) return HealthStatus.DOWN;
			if(livenessStr.equals(RDWR_SYS_OBJECT_ID)) // For now consider response for root mib of the device as live
				return HealthStatus.UP; 
			else
				return HealthStatus.DOWN;
		} catch (Throwable e) {return HealthStatus.DOWN;}
	}
}
