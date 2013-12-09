/**
 * SecuritySynProtectionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;

public class SecuritySynProtectionServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionService {

    public SecuritySynProtectionServiceLocator() {
    }


    public SecuritySynProtectionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecuritySynProtectionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecuritySynProtectionPort
    private java.lang.String SecuritySynProtectionPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecuritySynProtectionPortAddress() {
        return SecuritySynProtectionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecuritySynProtectionPortWSDDServiceName = "SecuritySynProtectionPort";

    public java.lang.String getSecuritySynProtectionPortWSDDServiceName() {
        return SecuritySynProtectionPortWSDDServiceName;
    }

    public void setSecuritySynProtectionPortWSDDServiceName(java.lang.String name) {
        SecuritySynProtectionPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionPortType getSecuritySynProtectionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecuritySynProtectionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecuritySynProtectionPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionPortType getSecuritySynProtectionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionBindingStub(portAddress, this);
            _stub.setPortName(getSecuritySynProtectionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecuritySynProtectionPortEndpointAddress(java.lang.String address) {
        SecuritySynProtectionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionBindingStub(new java.net.URL(SecuritySynProtectionPort_address), this);
                _stub.setPortName(getSecuritySynProtectionPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("SecuritySynProtectionPort".equals(inputPortName)) {
            return getSecuritySynProtectionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.SynProtection", "SecuritySynProtectionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.SynProtection", "SecuritySynProtectionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecuritySynProtectionPort".equals(portName)) {
            setSecuritySynProtectionPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
