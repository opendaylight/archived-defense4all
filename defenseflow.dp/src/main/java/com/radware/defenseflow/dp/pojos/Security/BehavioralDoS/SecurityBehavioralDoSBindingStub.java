/**
 * SecurityBehavioralDoSBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class SecurityBehavioralDoSBindingStub extends org.apache.axis.client.Stub implements com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[49];
        _initOperationDesc1();
        _initOperationDesc2();
        _initOperationDesc3();
        _initOperationDesc4();
        _initOperationDesc5();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsNetFloodDynamicStateTwoEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProtectionType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_rsNetFloodDynamicStateTwoEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProtectionType"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_rsNetFloodDynamicStateTwoEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProtectionType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_rsNetFloodDynamicStateTwoEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntryArray"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_rsNetFloodDynamicStateTwoEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsNetFloodDynamicTermSC37");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_300"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_rsNetFloodDynamicTermSC37");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_300"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsNetFloodDynamicStateFpEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_rsNetFloodDynamicStateFpEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_rsNetFloodDynamicStateFpEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[9] = oper;

    }

    private static void _initOperationDesc2(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_rsNetFloodDynamicStateFpEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryArray"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_rsNetFloodDynamicStateFpEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_LearningResetAll");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsNetFloodBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_rsNetFloodBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_rsNetFloodBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_rsNetFloodBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryArray"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_rsNetFloodBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_LearningResetPolicy");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_30"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_BandwidthIn");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_2147483647"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_BandwidthIn");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_2147483647"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsNetFloodDynamicTermSC6");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_300"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_rsNetFloodDynamicTermSC6");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_300"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_BandwidthOut");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_2147483647"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_BandwidthOut");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_2147483647"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_LearningResponsePeriod");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "LearningResponsePeriod"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_LearningResponsePeriod");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "LearningResponsePeriod"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_FootprintStrickness");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "FootprintStrickness"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_FootprintStrickness");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "FootprintStrickness"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Status");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Status"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_Status");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Status"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "ProfilesArray"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_Profiles");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsIdsHwaccWarning");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_255"));
        oper.setReturnClass(java.lang.String.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_rsNetFloodDynamicTermSC2");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_30"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[39] = oper;

    }

    private static void _initOperationDesc5(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_rsNetFloodDynamicTermSC2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_30"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[40] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_SetQuotasDefaults");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[41] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_Quotas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Direction"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas_Direction"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[42] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_Quotas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Direction"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas_Direction"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[43] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_Quotas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "Direction"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas_Direction"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas_Direction"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[44] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_Quotas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "QuotasArray"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[45] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_Quotas");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[46] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_SamplingStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "SamplingStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[47] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_SamplingStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "SamplingStatus"), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[48] = oper;

    }

    public SecurityBehavioralDoSBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SecurityBehavioralDoSBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SecurityBehavioralDoSBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
        if (service == null) {
            super.service = new org.apache.axis.client.Service();
        } else {
            super.service = service;
        }
        ((org.apache.axis.client.Service)super.service).setTypeMappingVersion("1.1");
            java.lang.Class cls;
            javax.xml.namespace.QName qName;
            javax.xml.namespace.QName qName2;
            java.lang.Class beansf = org.apache.axis.encoding.ser.BeanSerializerFactory.class;
            java.lang.Class beandf = org.apache.axis.encoding.ser.BeanDeserializerFactory.class;
            java.lang.Class enumsf = org.apache.axis.encoding.ser.EnumSerializerFactory.class;
            java.lang.Class enumdf = org.apache.axis.encoding.ser.EnumDeserializerFactory.class;
            java.lang.Class arraysf = org.apache.axis.encoding.ser.ArraySerializerFactory.class;
            java.lang.Class arraydf = org.apache.axis.encoding.ser.ArrayDeserializerFactory.class;
            java.lang.Class simplesf = org.apache.axis.encoding.ser.SimpleSerializerFactory.class;
            java.lang.Class simpledf = org.apache.axis.encoding.ser.SimpleDeserializerFactory.class;
            java.lang.Class simplelistsf = org.apache.axis.encoding.ser.SimpleListSerializerFactory.class;
            java.lang.Class simplelistdf = org.apache.axis.encoding.ser.SimpleListDeserializerFactory.class;
            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "FootprintStrickness");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "LearningResponsePeriod");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_100");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_100");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_1000");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_300");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_1_60");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_2147483647");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_30");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "long_5_60");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_FINACKFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FINACKFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_FRAGFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FRAGFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_ICMPFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_ICMPFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_IGMPFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_IGMPFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_jointDist");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_jointDist.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_packetReport");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_packetTrace");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetTrace.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_profileAction");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_profileAction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_RSTFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_RSTFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_startSimulation");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_startSimulation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_stopSimulation");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_stopSimulation.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_SYNACKFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNACKFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_SYNFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_TransparentOptimization");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_TransparentOptimization.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_UDPFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_UDPFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "ProfilesArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas_Direction");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "QuotasArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassController");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassController.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassField");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassField.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryKey");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_DetectionCondition");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_DetectionCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_FootprintType");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_FootprintType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_ProtectionType");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_ProtectionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntryKey");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_AnyTypeFlag");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_AnyTypeFlag.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_ProtectionType");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntryArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "SamplingStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Status");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_20");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_255");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "string_30");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

    }

    protected org.apache.axis.client.Call createCall() throws java.rmi.RemoteException {
        try {
            org.apache.axis.client.Call _call = super._createCall();
            if (super.maintainSessionSet) {
                _call.setMaintainSession(super.maintainSession);
            }
            if (super.cachedUsername != null) {
                _call.setUsername(super.cachedUsername);
            }
            if (super.cachedPassword != null) {
                _call.setPassword(super.cachedPassword);
            }
            if (super.cachedEndpoint != null) {
                _call.setTargetEndpointAddress(super.cachedEndpoint);
            }
            if (super.cachedTimeout != null) {
                _call.setTimeout(super.cachedTimeout);
            }
            if (super.cachedPortName != null) {
                _call.setPortName(super.cachedPortName);
            }
            java.util.Enumeration keys = super.cachedProperties.keys();
            while (keys.hasMoreElements()) {
                java.lang.String key = (java.lang.String) keys.nextElement();
                _call.setProperty(key, super.cachedProperties.get(key));
            }
            // All the type mapping information is registered
            // when the first call is made.
            // The type mapping information is actually registered in
            // the TypeMappingRegistry of the service, which
            // is the reason why registration is only needed for the first call.
            synchronized (this) {
                if (firstCall()) {
                    // must set encoding style before registering serializers
                    _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
                    _call.setEncodingStyle(org.apache.axis.Constants.URI_SOAP11_ENC);
                    for (int i = 0; i < cachedSerFactories.size(); ++i) {
                        java.lang.Class cls = (java.lang.Class) cachedSerClasses.get(i);
                        javax.xml.namespace.QName qName =
                                (javax.xml.namespace.QName) cachedSerQNames.get(i);
                        java.lang.Object x = cachedSerFactories.get(i);
                        if (x instanceof Class) {
                            java.lang.Class sf = (java.lang.Class)
                                 cachedSerFactories.get(i);
                            java.lang.Class df = (java.lang.Class)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                        else if (x instanceof javax.xml.rpc.encoding.SerializerFactory) {
                            org.apache.axis.encoding.SerializerFactory sf = (org.apache.axis.encoding.SerializerFactory)
                                 cachedSerFactories.get(i);
                            org.apache.axis.encoding.DeserializerFactory df = (org.apache.axis.encoding.DeserializerFactory)
                                 cachedDeserFactories.get(i);
                            _call.registerTypeMapping(cls, qName, sf, df, false);
                        }
                    }
                }
            }
            return _call;
        }
        catch (java.lang.Throwable _t) {
            throw new org.apache.axis.AxisFault("Failure trying to get the Call object", _t);
        }
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry get_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsNetFloodDynamicStateTwoEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {protectionType});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntry_ProtectionTypeHolder protectionType, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getFirst_rsNetFloodDynamicStateTwoEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                protectionType.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType) _output.get(new javax.xml.namespace.QName("", "ProtectionType"));
            } catch (java.lang.Exception _exception) {
                protectionType.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "ProtectionType")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntry_ProtectionTypeHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getNext_rsNetFloodDynamicStateTwoEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {protectionType});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getAll_rsNetFloodDynamicStateTwoEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry[].class);
            }
            try {
                status.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("", "status"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                status.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "status")), boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void update_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "update_rsNetFloodDynamicStateTwoEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entry.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public long get_rsNetFloodDynamicTermSC37() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsNetFloodDynamicTermSC37"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Long) _resp).longValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, long.class)).longValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_rsNetFloodDynamicTermSC37(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_rsNetFloodDynamicTermSC37"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry get_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsNetFloodDynamicStateFpEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getFirst_rsNetFloodDynamicStateFpEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey) _output.get(new javax.xml.namespace.QName("", "key"));
            } catch (java.lang.Exception _exception) {
                key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "key")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getNext_rsNetFloodDynamicStateFpEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getAll_rsNetFloodDynamicStateFpEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry[].class);
            }
            try {
                status.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("", "status"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                status.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "status")), boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void update_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "update_rsNetFloodDynamicStateFpEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entry.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_LearningResetAll(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_LearningResetAll"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry get_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsNetFloodBypassEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getFirst_rsNetFloodBypassEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey) _output.get(new javax.xml.namespace.QName("", "key"));
            } catch (java.lang.Exception _exception) {
                key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "key")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getNext_rsNetFloodBypassEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getAll_rsNetFloodBypassEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry[].class);
            }
            try {
                status.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("", "status"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                status.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "status")), boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void update_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "update_rsNetFloodBypassEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entry.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_LearningResetPolicy(java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_LearningResetPolicy"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public long get_BandwidthIn() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_BandwidthIn"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Long) _resp).longValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, long.class)).longValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_BandwidthIn(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_BandwidthIn"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public long get_rsNetFloodDynamicTermSC6() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsNetFloodDynamicTermSC6"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Long) _resp).longValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, long.class)).longValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_rsNetFloodDynamicTermSC6(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_rsNetFloodDynamicTermSC6"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public long get_BandwidthOut() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_BandwidthOut"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Long) _resp).longValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, long.class)).longValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_BandwidthOut(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_BandwidthOut"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod get_LearningResponsePeriod() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_LearningResponsePeriod"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_LearningResponsePeriod(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_LearningResponsePeriod"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness get_FootprintStrickness() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_FootprintStrickness"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_FootprintStrickness(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_FootprintStrickness"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status get_Status() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_Status"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_Status(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_Status"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles get_Profiles(java.lang.String profileName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {profileName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_Profiles(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getFirst_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                profileName.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "ProfileName"));
            } catch (java.lang.Exception _exception) {
                profileName.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "ProfileName")), java.lang.String.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_Profiles(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getNext_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {profileName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                next_key.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), java.lang.String.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_Profiles(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getAll_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles[].class);
            }
            try {
                status.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("", "status"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                status.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "status")), boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void create_Profiles(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "create_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entry.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void delete_Profiles(java.lang.String profileName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "delete_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {profileName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void update_Profiles(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "update_Profiles"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entry.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public java.lang.String get_rsIdsHwaccWarning() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsIdsHwaccWarning"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (java.lang.String) _resp;
            } catch (java.lang.Exception _exception) {
                return (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_resp, java.lang.String.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public long get_rsNetFloodDynamicTermSC2() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_rsNetFloodDynamicTermSC2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return ((java.lang.Long) _resp).longValue();
            } catch (java.lang.Exception _exception) {
                return ((java.lang.Long) org.apache.axis.utils.JavaUtils.convert(_resp, long.class)).longValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_rsNetFloodDynamicTermSC2(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[40]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_rsNetFloodDynamicTermSC2"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_SetQuotasDefaults(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[41]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_SetQuotasDefaults"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {new java.lang.Long(value)});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas get_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[42]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_Quotas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {direction});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.Quotas_DirectionHolder direction, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[43]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getFirst_Quotas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                direction.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction) _output.get(new javax.xml.namespace.QName("", "Direction"));
            } catch (java.lang.Exception _exception) {
                direction.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "Direction")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.Quotas_DirectionHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[44]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getNext_Quotas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {direction});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[45]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "getAll_Quotas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas[].class);
            }
            try {
                status.value = ((java.lang.Boolean) _output.get(new javax.xml.namespace.QName("", "status"))).booleanValue();
            } catch (java.lang.Exception _exception) {
                status.value = ((java.lang.Boolean) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "status")), boolean.class)).booleanValue();
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void update_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[46]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "update_Quotas"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {entry.value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            java.util.Map _output;
            _output = _call.getOutputParams();
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus get_SamplingStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[47]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "get_SamplingStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_SamplingStatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[48]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityBehavioralDoSAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "set_SamplingStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {value});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
