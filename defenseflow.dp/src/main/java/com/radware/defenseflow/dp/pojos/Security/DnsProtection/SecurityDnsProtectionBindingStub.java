/**
 * SecurityDnsProtectionBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class SecurityDnsProtectionBindingStub extends org.apache.axis.client.Stub implements com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[47];
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
        oper.setName("get_DnsProtectionCollectiveRateLimitStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionCollectiveRateLimitStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionGlobalStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionGlobalStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionGlobalStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionGlobalStatus"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionDynamicStateFp");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_DnsProtectionDynamicStateFp");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_DnsProtectionDynamicStateFp");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_DnsProtectionDynamicStateFp");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpArray"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_DnsProtectionDynamicStateFp");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[7] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionLearningResponsePeriod");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionLearningResponsePeriod"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[8] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionLearningResponsePeriod");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionLearningResponsePeriod"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod.class, false, false);
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
        oper.setName("get_DnsProtectionFootprintStrictness");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionFootprintStrictness"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[10] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionFootprintStrictness");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionFootprintStrictness"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[11] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_SamplingStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "SamplingStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[12] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_SamplingStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "SamplingStatus"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[13] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[14] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_DnsProtectionBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[15] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_DnsProtectionBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "key"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryKey"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[16] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_DnsProtectionBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryArray"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[17] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_DnsProtectionBypassEntry");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[18] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionDynamicStateTwo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProtectionType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[19] = oper;

    }

    private static void _initOperationDesc3(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_DnsProtectionDynamicStateTwo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProtectionType"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[20] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_DnsProtectionDynamicStateTwo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProtectionType"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_ProtectionType"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[21] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_DnsProtectionDynamicStateTwo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwoArray"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[22] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_DnsProtectionDynamicStateTwo");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[23] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionResetPolicyLearning");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_30"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[24] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionDynamicTermSC2");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_1_30"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[25] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionDynamicTermSC2");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_1_30"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[26] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[27] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[28] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[29] = oper;

    }

    private static void _initOperationDesc4(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfileArray"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[30] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[31] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[32] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_DnsProtectionProfile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[33] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionCollectiveChallengeStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionCollectiveChallengeStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[34] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionCollectiveChallengeStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionCollectiveChallengeStatus"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[35] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionSignatureChallengeStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSignatureChallengeStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[36] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionSignatureChallengeStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSignatureChallengeStatus"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[37] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionDynamicTermSC3");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_6_300"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[38] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionDynamicTermSC3");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_6_300"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[39] = oper;

    }

    private static void _initOperationDesc5(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionResetAllLearning");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[40] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionSignatureRateLimitStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSignatureRateLimitStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[41] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionSignatureRateLimitStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSignatureRateLimitStatus"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[42] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionSdmProtComplianceStatus");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSdmProtComplianceStatus"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[43] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionSdmProtComplianceStatus");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSdmProtComplianceStatus"), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[44] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_DnsProtectionDynamicTermSC6");
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_6_300"));
        oper.setReturnClass(long.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "value"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[45] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("set_DnsProtectionDynamicTermSC6");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "value"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_6_300"), long.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[46] = oper;

    }

    public SecurityDnsProtectionBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SecurityDnsProtectionBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SecurityDnsProtectionBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassController");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassController.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassField");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassField.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntryKey");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionCollectiveChallengeStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionCollectiveRateLimitStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp_DetectionCondition");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp_DetectionCondition.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp_FootprintType");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp_FootprintType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp_ProtectionType");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp_ProtectionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFpKey");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_AnyTypeFlag");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_AnyTypeFlag.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_ProtectionType");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwoArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionFootprintStrictness");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionGlobalStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionLearningResponsePeriod");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_Action");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_Action.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsAaaaFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAaaaFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsAFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsMxFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsMxFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsNaptrFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsNaptrFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsOtherFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsOtherFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsPtrFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsPtrFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsSoaFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSoaFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsSrvFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSrvFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsTextFloodstatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsTextFloodstatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_ManTrigStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_ManTrigStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_packetReport");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_packetTrace");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetTrace.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfileArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSdmProtComplianceStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSignatureChallengeStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionSignatureRateLimitStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_100");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_1_1000");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_1_30");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_30");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_4000000");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "long_6_300");
            cachedSerQNames.add(qName);
            cls = long.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "SamplingStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_20");
            cachedSerQNames.add(qName);
            cls = java.lang.String.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(org.apache.axis.encoding.ser.BaseSerializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleSerializerFactory.class, cls, qName));
            cachedDeserFactories.add(org.apache.axis.encoding.ser.BaseDeserializerFactory.createFactory(org.apache.axis.encoding.ser.SimpleDeserializerFactory.class, cls, qName));

            qName = new javax.xml.namespace.QName("radware.Security.DnsProtection", "string_30");
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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus get_DnsProtectionCollectiveRateLimitStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionCollectiveRateLimitStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus get_DnsProtectionGlobalStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionGlobalStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionGlobalStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionGlobalStatus"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp get_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionDynamicStateFp"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpKeyHolder key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getFirst_DnsProtectionDynamicStateFp"));

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
                key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey) _output.get(new javax.xml.namespace.QName("", "key"));
            } catch (java.lang.Exception _exception) {
                key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "key")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getNext_DnsProtectionDynamicStateFp"));

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
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getAll_DnsProtectionDynamicStateFp"));

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
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp[].class);
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

    public void update_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[7]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "update_DnsProtectionDynamicStateFp"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod get_DnsProtectionLearningResponsePeriod() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[8]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionLearningResponsePeriod"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionLearningResponsePeriod(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[9]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionLearningResponsePeriod"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness get_DnsProtectionFootprintStrictness() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[10]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionFootprintStrictness"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionFootprintStrictness(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[11]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionFootprintStrictness"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus get_SamplingStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[12]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_SamplingStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_SamplingStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[13]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_SamplingStatus"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry get_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey key) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[14]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionBypassEntry"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {key});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[15]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getFirst_DnsProtectionBypassEntry"));

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
                key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey) _output.get(new javax.xml.namespace.QName("", "key"));
            } catch (java.lang.Exception _exception) {
                key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "key")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[16]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getNext_DnsProtectionBypassEntry"));

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
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[17]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getAll_DnsProtectionBypassEntry"));

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
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry[].class);
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

    public void update_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[18]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "update_DnsProtectionBypassEntry"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo get_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType protectionType) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[19]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionDynamicStateTwo"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {protectionType});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwo_ProtectionTypeHolder protectionType, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[20]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getFirst_DnsProtectionDynamicStateTwo"));

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
                protectionType.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType) _output.get(new javax.xml.namespace.QName("", "ProtectionType"));
            } catch (java.lang.Exception _exception) {
                protectionType.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "ProtectionType")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType protectionType, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwo_ProtectionTypeHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[21]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getNext_DnsProtectionDynamicStateTwo"));

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
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType) _output.get(new javax.xml.namespace.QName("", "next_key"));
            } catch (java.lang.Exception _exception) {
                next_key.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_key")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType.class);
            }
            try {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[22]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getAll_DnsProtectionDynamicStateTwo"));

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
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo[].class);
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

    public void update_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[23]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "update_DnsProtectionDynamicStateTwo"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionResetPolicyLearning(java.lang.String value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[24]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionResetPolicyLearning"));

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

    public long get_DnsProtectionDynamicTermSC2() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[25]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionDynamicTermSC2"));

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

    public void set_DnsProtectionDynamicTermSC2(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[26]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionDynamicTermSC2"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile get_DnsProtectionProfile(java.lang.String profileName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[27]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionProfile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {profileName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_DnsProtectionProfile(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[28]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getFirst_DnsProtectionProfile"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_DnsProtectionProfile(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[29]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getNext_DnsProtectionProfile"));

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
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_DnsProtectionProfile(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[30]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "getAll_DnsProtectionProfile"));

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
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile[].class);
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

    public void create_DnsProtectionProfile(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[31]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "create_DnsProtectionProfile"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void delete_DnsProtectionProfile(java.lang.String profileName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[32]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "delete_DnsProtectionProfile"));

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

    public void update_DnsProtectionProfile(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[33]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "update_DnsProtectionProfile"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus get_DnsProtectionCollectiveChallengeStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[34]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionCollectiveChallengeStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionCollectiveChallengeStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[35]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionCollectiveChallengeStatus"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus get_DnsProtectionSignatureChallengeStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[36]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionSignatureChallengeStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionSignatureChallengeStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[37]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionSignatureChallengeStatus"));

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

    public long get_DnsProtectionDynamicTermSC3() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[38]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionDynamicTermSC3"));

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

    public void set_DnsProtectionDynamicTermSC3(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[39]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionDynamicTermSC3"));

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

    public void set_DnsProtectionResetAllLearning(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[40]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionResetAllLearning"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus get_DnsProtectionSignatureRateLimitStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[41]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionSignatureRateLimitStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionSignatureRateLimitStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[42]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionSignatureRateLimitStatus"));

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

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus get_DnsProtectionSdmProtComplianceStatus() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[43]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionSdmProtComplianceStatus"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void set_DnsProtectionSdmProtComplianceStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[44]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionSdmProtComplianceStatus"));

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

    public long get_DnsProtectionDynamicTermSC6() throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[45]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "get_DnsProtectionDynamicTermSC6"));

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

    public void set_DnsProtectionDynamicTermSC6(long value) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[46]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityDnsProtectionAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.DnsProtection", "set_DnsProtectionDynamicTermSC6"));

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

}
