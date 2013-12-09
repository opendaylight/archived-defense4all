/**
 * SecurityOutOfStateServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.OutOfState;

public class SecurityOutOfStateServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateService {

    public SecurityOutOfStateServiceLocator() {
    }


    public SecurityOutOfStateServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecurityOutOfStateServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecurityOutOfStatePort
    private java.lang.String SecurityOutOfStatePort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecurityOutOfStatePortAddress() {
        return SecurityOutOfStatePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecurityOutOfStatePortWSDDServiceName = "SecurityOutOfStatePort";

    public java.lang.String getSecurityOutOfStatePortWSDDServiceName() {
        return SecurityOutOfStatePortWSDDServiceName;
    }

    public void setSecurityOutOfStatePortWSDDServiceName(java.lang.String name) {
        SecurityOutOfStatePortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStatePortType getSecurityOutOfStatePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecurityOutOfStatePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecurityOutOfStatePort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStatePortType getSecurityOutOfStatePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateBindingStub(portAddress, this);
            _stub.setPortName(getSecurityOutOfStatePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecurityOutOfStatePortEndpointAddress(java.lang.String address) {
        SecurityOutOfStatePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStatePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateBindingStub(new java.net.URL(SecurityOutOfStatePort_address), this);
                _stub.setPortName(getSecurityOutOfStatePortWSDDServiceName());
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
        if ("SecurityOutOfStatePort".equals(inputPortName)) {
            return getSecurityOutOfStatePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.OutOfState", "SecurityOutOfStateService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.OutOfState", "SecurityOutOfStatePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecurityOutOfStatePort".equals(portName)) {
            setSecurityOutOfStatePortEndpointAddress(address);
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
