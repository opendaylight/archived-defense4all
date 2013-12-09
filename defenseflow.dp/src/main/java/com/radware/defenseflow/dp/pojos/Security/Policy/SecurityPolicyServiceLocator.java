/**
 * SecurityPolicyServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.Policy;

public class SecurityPolicyServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyService {

    public SecurityPolicyServiceLocator() {
    }


    public SecurityPolicyServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecurityPolicyServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecurityPolicyPort
    private java.lang.String SecurityPolicyPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecurityPolicyPortAddress() {
        return SecurityPolicyPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecurityPolicyPortWSDDServiceName = "SecurityPolicyPort";

    public java.lang.String getSecurityPolicyPortWSDDServiceName() {
        return SecurityPolicyPortWSDDServiceName;
    }

    public void setSecurityPolicyPortWSDDServiceName(java.lang.String name) {
        SecurityPolicyPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyPortType getSecurityPolicyPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecurityPolicyPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecurityPolicyPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyPortType getSecurityPolicyPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyBindingStub(portAddress, this);
            _stub.setPortName(getSecurityPolicyPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecurityPolicyPortEndpointAddress(java.lang.String address) {
        SecurityPolicyPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyBindingStub(new java.net.URL(SecurityPolicyPort_address), this);
                _stub.setPortName(getSecurityPolicyPortWSDDServiceName());
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
        if ("SecurityPolicyPort".equals(inputPortName)) {
            return getSecurityPolicyPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.Policy", "SecurityPolicyService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.Policy", "SecurityPolicyPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecurityPolicyPort".equals(portName)) {
            setSecurityPolicyPortEndpointAddress(address);
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
