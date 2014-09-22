package org.opendaylight.defense4all.odl.controller;

import static org.junit.Assert.*;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.springframework.web.client.RestTemplate;
//
//        import com.radware.defenseflow.hp.SpringTestContext;
//        import com.radware.defenseflow.hp.TestIntegrationWithController;
//        import com.radware.defenseflow.hp.pojos.ApplyAction;
//        import com.radware.defenseflow.hp.pojos.Flow;
//        import com.radware.defenseflow.hp.pojos.FlowContent;
//        import com.radware.defenseflow.hp.pojos.Flows;
//        import com.radware.defenseflow.hp.pojos.FlowsCounts;
//        import com.radware.defenseflow.hp.pojos.HpcProps;
//        import com.radware.defenseflow.hp.pojos.Instruction;

//TODO: add pre/post conditions (verify the add/delete worked).

//@Category(TestIntegrationWithController.class)
public class FlowEntryMgrTest {

    private FlowEntryMgr flowEntryMgr = new FlowEntryMgr();
    private Connector connector;
    private String testNodeId;

    @Before
    public void init() throws ExceptionControlApp, Exception{
//        HpcProps hpcProps = SpringTestContext.INSTANCE.getHpcProps();
//        connector = new Connector(hpcProps );
//        flowEntryMgr.setConnector(connector);
//        testNodeId = SpringTestContext.INSTANCE.getOpenFlowSwitchId(); //"00:00:00:50:56:a3:1c:0b";
    }
//
//    @Test
//    public void testAddFlow() throws Exception {
//        RestTemplate restTemplate = RestTemplateFactory.INSTANCE.createInsecureSSLRestTemplate();
//        restTemplate
//    }

    @Test
    public void testAddOpenFlowEntry() throws Exception {
//        flowEntryMgr.addOpenFlowEntry(testNodeId, getFlow2());
    }
//
//    @Test
//    public void testAddOpenFlowEntryWithMask() throws Exception {
//        flowEntryMgr.addOpenFlowEntry(testNodeId, getFlow3());
//
//    }
//
//    @Test
//    public void testDeleteOpenFlowEntry() throws Exception {
//        flowEntryMgr.deleteOpenFlowEntry(testNodeId, getFlow1());
//    }
//
//    @Test
//    public void testGetOpenFlowStats() throws Exception {
//        FlowsCounts flowsCounts = flowEntryMgr.getOpenFlowStats(testNodeId);
//        System.out.println(flowsCounts);
//    }
//
//
//    @Test
//    public void testGetOpenFlows() throws Exception {
//        Flows flows = flowEntryMgr.getOpenFlows(testNodeId);
//        System.out.println(flows);
//    }
//
//
//    @Test
//    public void testAddGetAndDeleteFlow() throws Exception{
//        Flow flow = getFlow1();
//        String cookie = flow.getFlowContent().getCookie();
//        Flow retrievedFlow = flowEntryMgr.getOpenFlowEntry(testNodeId, cookie );//"0xfffb0000faded001");
//        assertNull("Flow with (cookie = " +cookie+") should not exist before test.",retrievedFlow);
//        flowEntryMgr.addOpenFlowEntry(testNodeId, flow);
//        retrievedFlow = flowEntryMgr.getOpenFlowEntry(testNodeId, flow.getFlowContent().getCookie());//"0xfffb0000faded001");
//        assertEquals("fail to add/get flow with (cookie = " +cookie + ")", retrievedFlow.getFlowContent().getCookie(), cookie);
//        flowEntryMgr.deleteOpenFlowEntry(testNodeId, retrievedFlow);
//        retrievedFlow = flowEntryMgr.getOpenFlowEntry(testNodeId, flow.getFlowContent().getCookie());//"0xfffb0000faded001");
//        assertNull("Flow with (cookie = " +cookie+") should have been deleted .",retrievedFlow);
//    }
//
////	@Test //example how to use decimal
////	public void testGetOpenFlowEntry() throws Exception {
////		Flow flow = flowEntryMgr.getOpenFlowEntry(nodeId, new BigInteger("13372").toString(16));
////		System.out.println(flow);
////	}
//
//
//
//    private Flow getFlow1(){
//        Flow flow1 = new Flow();
//        FlowContent flowContent = new FlowContent();
//        flowContent.setCookie("0xfffb0000faded001");
//        flowContent.setTableId(0);
//        flowContent.setPriority(5);
//
//        //set action
//        Instruction instruction = new Instruction();
//        List<ApplyAction> applyActions = new ArrayList<ApplyAction>();
//        ApplyAction applyAction = new ApplyAction();
//        applyAction.setOutput("NORMAL");
//        applyActions.add(applyAction);
//        instruction.setApplyActions(applyActions );
//        List<Instruction> instructions = new ArrayList<Instruction>();
//        instructions.add(instruction);
//        flowContent.setInstructions(instructions);
//        //set match
//        List<Properties> match = new ArrayList<Properties>();
//        Properties matchProperties1 = new Properties();
//        matchProperties1.put("eth_type", "ipv4");
//        Properties matchProperties2 = new Properties();
//        matchProperties2.put("ip_proto", "tcp");
//        match.add(matchProperties1);
//        match.add(matchProperties2);
//        flowContent.setMatch(match);
//
//        flow1.setFlowContent(flowContent);
//        return flow1;
//    }
//
//    private Flow getFlow2(){
//        Flow flow2 = new Flow();
//        FlowContent flowContent = new FlowContent();
//        flowContent.setCookie("0xfffb0000faded002");
//        flowContent.setTableId(0);
//        flowContent.setPriority(1);
//
//        //set action
//        Instruction instruction = new Instruction();
//        List<ApplyAction> applyActions = new ArrayList<ApplyAction>();
//        ApplyAction applyAction = new ApplyAction();
//        applyAction.setOutput("NORMAL");
//        applyActions.add(applyAction);
//        instruction.setApplyActions(applyActions );
//        List<Instruction> instructions = new ArrayList<Instruction>();
//        instructions.add(instruction);
//        flowContent.setInstructions(instructions);
//        //set match
//        List<Properties> match = new ArrayList<Properties>();
//        Properties matchProperties1 = new Properties();
//        matchProperties1.put("eth_type", "ipv4");
////		Properties matchProperties2 = new Properties();
////		matchProperties2.put("ip_proto", "tcp");
//        match.add(matchProperties1);
////		match.add(matchProperties2);
//
//        Properties matchProperties4 = new Properties();
//        matchProperties4.put("ipv4_dst", "100.1.1.100");
//        match.add(matchProperties4);
//
//        flowContent.setMatch(match);
//
//        flow2.setFlowContent(flowContent);
//        return flow2;
//    }
//
//    private Flow getFlow3(){
//        boolean found = false;
//        Flow flow = getFlow2();
//        List<Properties> match = flow.getFlowContent().getMatch();
//        for (Properties properties : match) {
//            if(properties.containsKey("ipv4_dst")){
//                properties.put("mask", "255.255.0.0");
//                found = true;
//            }
//        }
//        System.out.println("found="+found);
//        return flow;
//    }
//    /**
//     * Be careful! this test should probably not be enabled/run normally!! It deletes the flows!
//     * @throws Exception
//     */
//    public void testDeleteAllFlowsFromHpc() throws Exception{
//        testDeleteAllFlowsFromHpc(testNodeId);
//    }
//
//    public void testDeleteAllFlowsFromHpc(String nodeId) throws Exception{
//        boolean isHpSwitch = true;
//        Flows flows = flowEntryMgr.getOpenFlows(nodeId);
//        List<Flow> flowsList = flows.toFlowList();
//        int numberOfFlowsFound = flowsList.size();
//        for ( Flow flow :  flowsList){
//            if(!isHpSwitch || !isBasicFlow(flow)){// if it's not hp switch delete all flows. if it's hp switch don't delete basic flows.
//                flowEntryMgr.deleteOpenFlowEntry(nodeId, flow);
//                //System.out.println("delele sim");
//            }
//        }
//        flows = flowEntryMgr.getOpenFlows(nodeId);
//        flowsList = flows.toFlowList();
//        int numberOfFlowsLeft = flowsList.size();
//        assertEquals("Expected to delete all flows except basic flows, but left "+ numberOfFlowsLeft +" flows out of " + numberOfFlowsFound ,
//                isHpSwitch?2:0,  numberOfFlowsLeft);
//    }
//
//    private boolean isBasicFlow(Flow flow) {
//        FlowContent content = flow.getFlowContent();
//        if (content != null){
//            List<Properties> matches = content.getMatch();
//            if (matches != null && matches.size() == 1) {
//                Properties maybeEthTypeProp = matches.get(0);
//                if (maybeEthTypeProp != null) {
//                    String ethType = maybeEthTypeProp.getProperty("eth_type");
//                    if ("bddp".equals(ethType)){
//                        if (content.getPriority() == 60000) {
//                            System.out.println("found basic 1");
//                            return true;
//                        }
//                    }
//                }
//            }
//            if (content.getPriority() == 0) {
//                if ("ffff000000000000".equals(content.getCookie())) {
//                    System.out.println("found basic 2");
//                    return true;
//                }
//            }
//        }
//        return false;
//    }


}
