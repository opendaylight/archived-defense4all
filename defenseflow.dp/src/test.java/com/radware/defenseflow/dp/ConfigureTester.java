package com.radware.defenseflow.dp;

import static org.junit.Assert.*;

import org.junit.Test;

import com.radware.defenseflow.dp.pojos.Classes.Networks.Network;

public class ConfigureTester {
	
	private Connector	connector;
	private String	zoneName = "JUTest15";
	private long	custDnsTrafficEstimate;
	private String	dpIpAddr = "10.206.167.52";
	private String	dpUsername = "radware";
	private String	dpPassword = "radware";
	private String	custZoneName;
	private long	bandwidth = 500;
	private String dpName = "dp1";
//http://radware:radware@10.206.167.50/soap
//	@Test
	public void test() {
		try {
			System.out.println("-1-");
			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
			//new DPConfigMgr(connector, zoneName, custDnsTrafficEstimate, bandwidth);
		} catch (Exception e) {
			fail("Create connector failed: " + e.getMessage());
		}
		
	}
	
	//@Test
	public void addGlobalProfilesTest() {				
		try {
			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
			connector.init();
			DPConfigMgr	manager = new DPConfigMgr();
			manager.addGlobalProfilesIfNeeded(connector);
			
		} catch (Exception e) {	
			e.printStackTrace();
			fail("Configure failed: " + e.getMessage());
		}
		
	}
		
	//@Test
	public void addPNTest() {				
		try {
			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
			connector.init();
			
			DPConfigMgr	manager = new DPConfigMgr();			
			Network network = manager.createDPNetworkObject(dpIpAddr, "30", zoneName);
			connector.createClassesNetworks(network);
		} catch (Exception e) {	
			e.printStackTrace();
			fail("Configure failed: " + e.getMessage());
		}
		
	}
	
	@Test
	public void addSecurityConfigurationTest() {				
		try {
			System.out.println("-3-");
			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
			connector.init();
			DPConfigMgr	manager = new DPConfigMgr();
			Network network = manager.createDPNetworkObject(dpIpAddr, "30", zoneName);
			connector.createClassesNetworks(network);
			manager.addSecurityConfigInDP(zoneName, connector, "400000:1");
			connector.getPolicy(zoneName);
		} catch (Exception e) {	
			e.printStackTrace();
			fail("Configure failed: " + e.getMessage());
		}
		
	}
}
