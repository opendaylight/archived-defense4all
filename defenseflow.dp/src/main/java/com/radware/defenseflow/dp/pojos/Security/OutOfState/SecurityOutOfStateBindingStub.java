/**
 * SecurityOutOfStateBindingStub.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.OutOfState;

public class SecurityOutOfStateBindingStub extends org.apache.axis.client.Stub implements com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStatePortType {
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
        oper.setName("get_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.OutOfState", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile"));
        oper.setReturnClass(com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class);
        oper.setReturnQName(new javax.xml.namespace.QName("", "entry"));
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[0] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getFirst_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile"), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[1] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getNext_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.OutOfState", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_key"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "next_entry"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile"), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[2] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("getAll_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "table"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "ProfileArray"), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile[].class, false, false);
        oper.addParameter(param);
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "status"), org.apache.axis.description.ParameterDesc.OUT, new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"), boolean.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[3] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("create_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile"), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[4] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("delete_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "ProfileName"), org.apache.axis.description.ParameterDesc.IN, new javax.xml.namespace.QName("radware.Security.OutOfState", "string_20"), java.lang.String.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[5] = oper;

        oper = new org.apache.axis.description.OperationDesc();
        oper.setName("update_Profile");
        param = new org.apache.axis.description.ParameterDesc(new javax.xml.namespace.QName("", "entry"), org.apache.axis.description.ParameterDesc.INOUT, new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile"), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class, false, false);
        oper.addParameter(param);
        oper.setReturnType(org.apache.axis.encoding.XMLType.AXIS_VOID);
        oper.setStyle(org.apache.axis.constants.Style.RPC);
        oper.setUse(org.apache.axis.constants.Use.ENCODED);
        _operations[6] = oper;

    }

    public SecurityOutOfStateBindingStub() throws org.apache.axis.AxisFault {
         this(null);
    }

    public SecurityOutOfStateBindingStub(java.net.URL endpointURL, javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
         this(service);
         super.cachedEndpoint = endpointURL;
    }

    public SecurityOutOfStateBindingStub(javax.xml.rpc.Service service) throws org.apache.axis.AxisFault {
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
            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(beansf);
            cachedDeserFactories.add(beandf);

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_PacketReport");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketReport.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_PacketTrace");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketTrace.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_ProfileAction");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileAction.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_ProfileRisk");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileRisk.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_SYNACKAllow");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_SYNACKAllow.class;
            cachedSerClasses.add(cls);
            cachedSerFactories.add(enumsf);
            cachedDeserFactories.add(enumdf);

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "ProfileArray");
            cachedSerQNames.add(qName);
            cls = com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile[].class;
            cachedSerClasses.add(cls);
            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile");
            qName2 = null;
            cachedSerFactories.add(new org.apache.axis.encoding.ser.ArraySerializerFactory(qName, qName2));
            cachedDeserFactories.add(new org.apache.axis.encoding.ser.ArrayDeserializerFactory());

            qName = new javax.xml.namespace.QName("radware.Security.OutOfState", "string_20");
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

    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile get_Profile(java.lang.String profileName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[0]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "get_Profile"));

        setRequestHeaders(_call);
        setAttachments(_call);
 try {        java.lang.Object _resp = _call.invoke(new java.lang.Object[] {profileName});

        if (_resp instanceof java.rmi.RemoteException) {
            throw (java.rmi.RemoteException)_resp;
        }
        else {
            extractAttachments(_call);
            try {
                return (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) _resp;
            } catch (java.lang.Exception _exception) {
                return (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) org.apache.axis.utils.JavaUtils.convert(_resp, com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getFirst_Profile(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[1]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "getFirst_Profile"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getNext_Profile(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder next_entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[2]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "getNext_Profile"));

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
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) _output.get(new javax.xml.namespace.QName("", "next_entry"));
            } catch (java.lang.Exception _exception) {
                next_entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "next_entry")), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void getAll_Profile(com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[3]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "getAll_Profile"));

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
                table.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile[]) _output.get(new javax.xml.namespace.QName("", "table"));
            } catch (java.lang.Exception _exception) {
                table.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile[]) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "table")), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile[].class);
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

    public void create_Profile(com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[4]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "create_Profile"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

    public void delete_Profile(java.lang.String profileName) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[5]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "delete_Profile"));

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

    public void update_Profile(com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder entry) throws java.rmi.RemoteException {
        if (super.cachedEndpoint == null) {
            throw new org.apache.axis.NoEndPointException();
        }
        org.apache.axis.client.Call _call = createCall();
        _call.setOperation(_operations[6]);
        _call.setUseSOAPAction(true);
        _call.setSOAPActionURI("SecurityOutOfStateAction");
        _call.setSOAPVersion(org.apache.axis.soap.SOAPConstants.SOAP11_CONSTANTS);
        _call.setOperationName(new javax.xml.namespace.QName("radware.Security.OutOfState", "update_Profile"));

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
                entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) _output.get(new javax.xml.namespace.QName("", "entry"));
            } catch (java.lang.Exception _exception) {
                entry.value = (com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile) org.apache.axis.utils.JavaUtils.convert(_output.get(new javax.xml.namespace.QName("", "entry")), com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile.class);
            }
        }
  } catch (org.apache.axis.AxisFault axisFaultException) {
  throw axisFaultException;
}
    }

}
