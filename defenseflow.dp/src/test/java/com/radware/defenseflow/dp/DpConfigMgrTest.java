package com.radware.defenseflow.dp;


import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FR;
import org.opendaylight.defense4all.framework.core.FrameworkMain;

import com.radware.defenseflow.dp.SoapConnector;
import com.radware.defenseflow.dp.pojos.Classes.Networks.Network;

//@RunWith(MockitoJUnitRunner.class)
public class DpConfigMgrTest {
	
//	private Connector	connector;
//	private String	zoneName = "JUTest15";
//	private long	custDnsTrafficEstimate;
//	private String	dpIpAddr = "10.206.167.52";
//	private String	dpUsername = "radware";
//	private String	dpPassword = "radware";
//	private String	custZoneName;
//	private long	bandwidth = 500;
//	private String dpName = "dp1";
////http://radware:radware@10.206.167.50/soap
////	@Test
//	public void test() {
//		try {
//			System.out.println("-1-");
//			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
//			//new DPConfigMgr(connector, zoneName, custDnsTrafficEstimate, bandwidth);
//		} catch (Exception e) {
//			fail("Create connector failed: " + e.getMessage());
//		}
//		
//	}
//	
//	//@Test
//	public void addGlobalProfilesTest() {				
//		try {
//			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
//			connector.init();
//			DPConfigMgr	manager = new DPConfigMgr();
//			manager.addGlobalProfilesIfNeeded(connector);
//			
//		} catch (Exception e) {	
//			e.printStackTrace();
//			fail("Configure failed: " + e.getMessage());
//		}
//		
//	}
//		
//	//@Test
//	public void addPNTest() {				
//		try {
//			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
//			connector.init();
//			
//			DPConfigMgr	manager = new DPConfigMgr();			
//			Network network = manager.createDPNetworkObject(dpIpAddr, "30", zoneName);
//			connector.createClassesNetworks(network);
//		} catch (Exception e) {	
//			e.printStackTrace();
//			fail("Configure failed: " + e.getMessage());
//		}
//		
//	}
//	
////	@Test
//	public void addSecurityConfigurationTest() {				
//		try {
//			System.out.println("-3-");
//			connector = new Connector(dpName, dpIpAddr, dpUsername, dpPassword);
//			connector.init();
//			DPConfigMgr	manager = new DPConfigMgr();
//			Network network = manager.createDPNetworkObject(dpIpAddr, "30", zoneName);
//			connector.createClassesNetworks(network);
//			manager.addSecurityConfigInDP(zoneName, connector, "400000:1");
//			connector.getPolicy(zoneName);
//		} catch (Exception e) {	
//			e.printStackTrace();
//			fail("Configure failed: " + e.getMessage());
//		}
//		
//	}
	
	

			
	@Test
	public void testRemovePolicy() throws Exception {
//		FMHolder.get().getFR().logRecord(
		FMHolder fmHolderMock = Mockito.mock(FMHolder.class);
		FrameworkMain frameworkMainMock = Mockito.mock(FrameworkMain.class);
		FR frMock = Mockito.mock(FR.class);
		
//		fmHolderMock.when(fmHolderMock.get()).thenReturn(frameworkMainMock));
//		frameworkMainMock.when(getFR().thenReturn());
//		frMock.when(logRecord().then());
//		 
		SoapConnector connector = new SoapConnector("dp", "10.206.167.98", "radware2", "radware24");
//		connector = new SoapConnector("dp", ams.mgmtAddr.getHostAddress(), ams.username, ams.password);
		try{ connector.init();
		}catch(Exception e){}//instead of mock, since init should work... and this is a temp test for bug-fix only.
		
		DPConfigMgr	manager = new DPConfigMgr();
		String networkName = "NAP2561";
		try{
			manager.removeSecurityConfigInDP(connector, networkName);
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
