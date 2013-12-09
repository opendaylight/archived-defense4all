/**
 * SecurityDnsProtectionServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class SecurityDnsProtectionServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionService {

    public SecurityDnsProtectionServiceLocator() {
    }


    public SecurityDnsProtectionServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecurityDnsProtectionServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecurityDnsProtectionPort
    private java.lang.String SecurityDnsProtectionPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecurityDnsProtectionPortAddress() {
        return SecurityDnsProtectionPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecurityDnsProtectionPortWSDDServiceName = "SecurityDnsProtectionPort";

    public java.lang.String getSecurityDnsProtectionPortWSDDServiceName() {
        return SecurityDnsProtectionPortWSDDServiceName;
    }

    public void setSecurityDnsProtectionPortWSDDServiceName(java.lang.String name) {
        SecurityDnsProtectionPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionPortType getSecurityDnsProtectionPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecurityDnsProtectionPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecurityDnsProtectionPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionPortType getSecurityDnsProtectionPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionBindingStub(portAddress, this);
            _stub.setPortName(getSecurityDnsProtectionPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecurityDnsProtectionPortEndpointAddress(java.lang.String address) {
        SecurityDnsProtectionPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionBindingStub(new java.net.URL(SecurityDnsProtectionPort_address), this);
                _stub.setPortName(getSecurityDnsProtectionPortWSDDServiceName());
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
        if ("SecurityDnsProtectionPort".equals(inputPortName)) {
            return getSecurityDnsProtectionPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.DnsProtection", "SecurityDnsProtectionService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.DnsProtection", "SecurityDnsProtectionPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecurityDnsProtectionPort".equals(portName)) {
            setSecurityDnsProtectionPortEndpointAddress(address);
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
