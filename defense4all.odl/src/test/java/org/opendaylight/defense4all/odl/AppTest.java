/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.util.ArrayList;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.PatternLayout;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.odl.controller.Connector;
import org.opendaylight.defense4all.odl.controller.FlowEntryMgr;
import org.opendaylight.defense4all.odl.pojos.FlowStat;
import org.opendaylight.defense4all.odl.pojos.FlowStatistics;
import org.opendaylight.defense4all.odl.pojos.NodeProperties;
import org.opendaylight.defense4all.odl.pojos.Nodes;
import org.opendaylight.defense4all.odl.pojos.ReceivedFlowConfig;
import org.opendaylight.defense4all.odl.pojos.SentFlowConfig;
import org.opendaylight.defense4all.odl.pojos.SentNode;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	FlowEntryMgr flowEntryMgr;
	String nodeId;
	
	Logger rootLogger;	
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName ) {
    	
    	super( testName );
    	
		OdlOFC odlOFC = new OdlOFC();
		odlOFC.hostname = "127.0.0.1"; odlOFC.ipAddrString = "127.0.0.1"; 
//		odlOFC.hostname = "10.206.102.71"; odlOFC.ipAddrString = "10.206.102.71"; 
		odlOFC.port = 8080;
		odlOFC.username = "admin"; odlOFC.password = "admin";
		
		Connector connector = new Connector(odlOFC);
		try {
			connector.init();
		} catch (ExceptionControlApp e) {
			assertFalse(true);
		}		
		
		Odl odl = new Odl();
		odl.constFlowUrlPrefix =  "/controller/nb/v2/flowprogrammer";
		odl.constStatsUrlPrefix = "/controller/nb/v2/statistics";
		odl.constSwitchUrlPrefix = "/controller/nb/v2/switchmanager";
		
		flowEntryMgr = new FlowEntryMgr();
		flowEntryMgr.odl = odl; flowEntryMgr.connector = connector;
		
		nodeId = null;
    }
    
    @Override
    protected void setUp() throws Exception {   	
    	super.setUp();
    	/*
		Nodes nodes = flowEntryMgr.getNodes();
		NodeProperties nodeProps = nodes.nodeProperties.get(0);
		nodeId = (String) nodeProps.node.id;*/
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite(){return new TestSuite( AppTest.class ); }

    /**
     * Some test
     */
    public void testAppTemp() {
		
/*		assertFalse("AppTest failed to get nodes", nodeId == null);
    	
    	SentFlowConfig sfe10 = new SentFlowConfig();
		SentFlowConfig sfe11 = new SentFlowConfig();
		ReceivedFlowConfig rfe10 = new ReceivedFlowConfig();
		ReceivedFlowConfig rfe11 = new ReceivedFlowConfig();
		
		 Create fe10 
		try {
			sfe10 = new SentFlowConfig();
			sfe10.name = "fe10"; sfe10.cookie = "1010"; sfe10.node = new SentNode(nodeId, "OF"); sfe10.priority = 110; 
			sfe10.etherType = "0x800"; sfe10.protocol = 6; sfe10.nwDst = "10.10.10.10"; sfe10.ingressPort = 1;
			sfe10.actions = new ArrayList<String>(); sfe10.actions.add("OUTPUT=1");
			sfe10.installInHw = true;
			flowEntryMgr.addOpenFlowEntry(nodeId, "fe10", sfe10);
		} catch (Exception e) {
			assertFalse("exception adding flowEntry 10: " + e.getLocalizedMessage(), true);
		}
		
		 Create fe11 
		try {
			sfe11 = new SentFlowConfig();
			sfe11.name = "fe11"; sfe11.cookie = "1011"; sfe11.node = new SentNode(nodeId, "OF"); sfe11.priority = 111; 
			sfe11.etherType = "0x800"; sfe11.protocol = 17; sfe11.nwDst = "10.10.10.11"; sfe11.ingressPort = 1;
			sfe11.actions = new ArrayList<String>(); sfe11.actions.add("OUTPUT=1");
			sfe11.installInHw = true;
			flowEntryMgr.addOpenFlowEntry(nodeId, "fe11", sfe11);
		} catch (Exception e) {
			assertFalse("exception adding flowEntry 11: " + e.getLocalizedMessage(), true);
		}
		
		 get fe10
		try {
			rfe10 = flowEntryMgr.getOpenFlowEntry(nodeId, "fe10");
			if(rfe10 == null) throw new Exception("got null for fe10 flowEntry.");
		} catch (Exception e) {
			assertFalse("exception getting server flowEntry: " + e.getLocalizedMessage(), true);
		}		
		assertEquals(sfe10, rfe10);
		
	
		 Check receipt of statistics for set flow entries 
		FlowStatistics flowStats = null;
		short maxRetries = 3;
		while ( maxRetries != 0 ) {
			try {
				Thread.sleep(3000);
				flowStats = flowEntryMgr.getOpenFlowStats(nodeId);
				if(flowStats == null) throw new Exception("Got null flowstatistics");
			} catch (Exception e) {
				System.out.println("exception getting server flowStatistics: " + e.getLocalizedMessage());
				assertFalse(true);
			}
			if ( flowStats.flowStatistic != null &&  ! flowStats.flowStatistic.isEmpty() )
				break;
			maxRetries--;
		}
		
		
		assertFalse("null/empty flowStat in flowStats", flowStats.flowStatistic == null || flowStats.flowStatistic.isEmpty());
		int setFlowCount = 0;
		for(FlowStat flowStat : flowStats.flowStatistic) {
			if(flowStat.flow.id == 1010 || flowStat.flow.id == 1011)
				setFlowCount++;
		}
		assertFalse("Could not find stats for all set flow entries", setFlowCount != 2);
		
		 Delete fe10 
		try {
			flowEntryMgr.deleteOpenFlowEntry(nodeId, "fe10");
		} catch (Exception e) {
			assertFalse("exception deleting fe10 flowEntry: " + e.getLocalizedMessage(), true);
		}
		
		 get fe10
		try {
			rfe10 = flowEntryMgr.getOpenFlowEntry(nodeId, "fe10");
		} catch (Exception e) { Ignore - expected?}
		
		assertFalse("fe10 was not deleted: ", rfe10 != null);
		
		 Delete fe11 
		try {
			flowEntryMgr.deleteOpenFlowEntry(nodeId, "fe11");
		} catch (Exception e) {
			assertFalse("exception deleting fe11 flowEntry: " + e.getLocalizedMessage(), true);
		}
		
		 get fe11
		try {
			rfe11 = flowEntryMgr.getOpenFlowEntry(nodeId, "fe11");
		} catch (Exception e) { Ignore - expected?}
		
		assertFalse("fe11 was not deleted: ", rfe11 != null);	*/
    }
    
    @Override
    protected void tearDown() throws Exception {
    	super.tearDown();

		/*try {
			flowEntryMgr.deleteOpenFlowEntry(nodeId, "fe10"); // Delete fe10
		} catch (Exception e) {  }
		
		try {
			flowEntryMgr.deleteOpenFlowEntry(nodeId, "fe11"); // Delete fe11
		} catch (Exception e) { }*/
    }
}
