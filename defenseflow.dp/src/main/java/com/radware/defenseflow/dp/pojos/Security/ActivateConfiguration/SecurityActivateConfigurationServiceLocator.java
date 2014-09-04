/**
 * SecurityActivateConfigurationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration;

public class SecurityActivateConfigurationServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationService {

    public SecurityActivateConfigurationServiceLocator() {
    }


    public SecurityActivateConfigurationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public SecurityActivateConfigurationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for SecurityActivateConfigurationPort
    private java.lang.String SecurityActivateConfigurationPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getSecurityActivateConfigurationPortAddress() {
        return SecurityActivateConfigurationPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String SecurityActivateConfigurationPortWSDDServiceName = "SecurityActivateConfigurationPort";

    public java.lang.String getSecurityActivateConfigurationPortWSDDServiceName() {
        return SecurityActivateConfigurationPortWSDDServiceName;
    }

    public void setSecurityActivateConfigurationPortWSDDServiceName(java.lang.String name) {
        SecurityActivateConfigurationPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationPortType getSecurityActivateConfigurationPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(SecurityActivateConfigurationPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getSecurityActivateConfigurationPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationPortType getSecurityActivateConfigurationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationBindingStub(portAddress, this);
            _stub.setPortName(getSecurityActivateConfigurationPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setSecurityActivateConfigurationPortEndpointAddress(java.lang.String address) {
        SecurityActivateConfigurationPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationBindingStub _stub = new com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationBindingStub(new java.net.URL(SecurityActivateConfigurationPort_address), this);
                _stub.setPortName(getSecurityActivateConfigurationPortWSDDServiceName());
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
        if ("SecurityActivateConfigurationPort".equals(inputPortName)) {
            return getSecurityActivateConfigurationPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Security.ActivateConfiguration", "SecurityActivateConfigurationService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Security.ActivateConfiguration", "SecurityActivateConfigurationPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("SecurityActivateConfigurationPort".equals(portName)) {
            setSecurityActivateConfigurationPortEndpointAddress(address);
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
