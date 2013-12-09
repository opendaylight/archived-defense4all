/**
 * SecurityBehavioralDoSServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class SecurityBehavioralDoSServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSService {

    public SecurityBehavioralDoSServiceLocator() {
    }


    public SecurityBehavioralDoSServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecurityBehavioralDoSServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecurityBehavioralDoSPort
    private java.lang.String SecurityBehavioralDoSPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecurityBehavioralDoSPortAddress() {
        return SecurityBehavioralDoSPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecurityBehavioralDoSPortWSDDServiceName = "SecurityBehavioralDoSPort";

    public java.lang.String getSecurityBehavioralDoSPortWSDDServiceName() {
        return SecurityBehavioralDoSPortWSDDServiceName;
    }

    public void setSecurityBehavioralDoSPortWSDDServiceName(java.lang.String name) {
        SecurityBehavioralDoSPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSPortType getSecurityBehavioralDoSPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecurityBehavioralDoSPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecurityBehavioralDoSPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSPortType getSecurityBehavioralDoSPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSBindingStub(portAddress, this);
            _stub.setPortName(getSecurityBehavioralDoSPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecurityBehavioralDoSPortEndpointAddress(java.lang.String address) {
        SecurityBehavioralDoSPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSBindingStub(new java.net.URL(SecurityBehavioralDoSPort_address), this);
                _stub.setPortName(getSecurityBehavioralDoSPortWSDDServiceName());
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
        if ("SecurityBehavioralDoSPort".equals(inputPortName)) {
            return getSecurityBehavioralDoSPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "SecurityBehavioralDoSService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "SecurityBehavioralDoSPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecurityBehavioralDoSPort".equals(portName)) {
            setSecurityBehavioralDoSPortEndpointAddress(address);
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
