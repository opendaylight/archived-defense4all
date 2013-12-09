/**
 * ManagementSyslogServersBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Management.SyslogServers;

public class ManagementSyslogServersBindingStub extends org.apache.axis.client.Stub implements com.radware.defenseflow.dp.pojos.Management.SyslogServers.ManagementSyslogServersPortType {
    private java.util.Vector cachedSerClasses = new java.util.Vector();
    private java.util.Vector cachedSerQNames = new java.util.Vector();
    private java.util.Vector cachedSerFactories = new java.util.Vector();
    private java.util.Vector cachedDeserFactories = new java.util.Vector();

    static org.apache.axis.description.OperationDesc [] _operations;

    static {
        _operations = new org.apache.axis.description.OperationDesc[7];
        _initOperationDesc1();
    }

    private static void _initOperationDesc1(){
        org.apache.axis.description.OperationDesc oper;
        org.apache.axis.description.ParameterDesc param;
        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("get_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "syslogServerAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "syslogServerAddress"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable"), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "syslogServerAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable"), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTableArray"), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable"), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "syslogServerAddress"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_SyslogServersTable");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable"), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

    }

    public ManagementSyslogServersBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public ManagementSyslogServersBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public ManagementSyslogServersBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "FeatureStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerFacility");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerFacility.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerProtocol");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerProtocol.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerRowStatus");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerRowStatus.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTableArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

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

    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable get_SyslogServersTable(java.lang.String syslogServerAddress) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "get_SyslogServersTable"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {syslogServerAddress});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_SyslogServersTable(javax.xml.rpc.holders.StringHolder syslogServerAddress, com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "getFirst_SyslogServersTable"));

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
                syslogServerAddress.value = (java.lang.String) _output.get(new javax.xml.namespace.QName("", "syslogServerAddress"));
            } catch (java.lang.Exception _exception) {
                syslogServerAddress.value = (java.lang.String) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "syslogServerAddress")), java.lang.String.class);
            }
            try {
                entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_SyslogServersTable(java.lang.String syslogServerAddress, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "getNext_SyslogServersTable"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {syslogServerAddress});

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
                next_entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_SyslogServersTable(com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "getAll_SyslogServersTable"));

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
                table.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable[].class);
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

    public void create_SyslogServersTable(com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "create_SyslogServersTable"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void delete_SyslogServersTable(java.lang.String syslogServerAddress) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "delete_SyslogServersTable"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {syslogServerAddress});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        extractAttachments(_call);
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void update_SyslogServersTable(com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("ManagementSyslogServersAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Management.SyslogServers", "update_SyslogServersTable"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
