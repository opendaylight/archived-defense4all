/**
 * SecuritySignatureProtectionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class SecuritySignatureProtectionServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionService {

    public SecuritySignatureProtectionServiceLocator() {
    }


    public SecuritySignatureProtectionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecuritySignatureProtectionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecuritySignatureProtectionPort
    private java.lang.String SecuritySignatureProtectionPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecuritySignatureProtectionPortAddress() {
        return SecuritySignatureProtectionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecuritySignatureProtectionPortWSDDServiceName = "SecuritySignatureProtectionPort";

    public java.lang.String getSecuritySignatureProtectionPortWSDDServiceName() {
        return SecuritySignatureProtectionPortWSDDServiceName;
    }

    public void setSecuritySignatureProtectionPortWSDDServiceName(java.lang.String name) {
        SecuritySignatureProtectionPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionPortType getSecuritySignatureProtectionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecuritySignatureProtectionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecuritySignatureProtectionPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionPortType getSecuritySignatureProtectionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionBindingStub(portAddress, this);
            _stub.setPortName(getSecuritySignatureProtectionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecuritySignatureProtectionPortEndpointAddress(java.lang.String address) {
        SecuritySignatureProtectionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionBindingStub(new java.net.URL(SecuritySignatureProtectionPort_address), this);
                _stub.setPortName(getSecuritySignatureProtectionPortWSDDServiceName());
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
        if ("SecuritySignatureProtectionPort".equals(inputPortName)) {
            return getSecuritySignatureProtectionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.SignatureProtection", "SecuritySignatureProtectionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "SecuritySignatureProtectionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecuritySignatureProtectionPort".equals(portName)) {
            setSecuritySignatureProtectionPortEndpointAddress(address);
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
