/**
 * ClassesActivateConfigurationServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration;

@SuppressWarnings("serial")
public class ClassesActivateConfigurationServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationService {

    public ClassesActivateConfigurationServiceLocator() {
    }


    public ClassesActivateConfigurationServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ClassesActivateConfigurationServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ClassesActivateConfigurationPort
    private java.lang.String ClassesActivateConfigurationPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getClassesActivateConfigurationPortAddress() {
        return ClassesActivateConfigurationPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ClassesActivateConfigurationPortWSDDServiceName = "ClassesActivateConfigurationPort";

    public java.lang.String getClassesActivateConfigurationPortWSDDServiceName() {
        return ClassesActivateConfigurationPortWSDDServiceName;
    }

    public void setClassesActivateConfigurationPortWSDDServiceName(java.lang.String name) {
        ClassesActivateConfigurationPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationPortType getClassesActivateConfigurationPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ClassesActivateConfigurationPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getClassesActivateConfigurationPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationPortType getClassesActivateConfigurationPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationBindingStub _stub = new com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationBindingStub(portAddress, this);
            _stub.setPortName(getClassesActivateConfigurationPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setClassesActivateConfigurationPortEndpointAddress(java.lang.String address) {
        ClassesActivateConfigurationPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(@SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationBindingStub _stub = new com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration.ClassesActivateConfigurationBindingStub(new java.net.URL(ClassesActivateConfigurationPort_address), this);
                _stub.setPortName(getClassesActivateConfigurationPortWSDDServiceName());
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
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, @SuppressWarnings("rawtypes") Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("ClassesActivateConfigurationPort".equals(inputPortName)) {
            return getClassesActivateConfigurationPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Classes.ActivateConfiguration", "ClassesActivateConfigurationService");
    }

    @SuppressWarnings("rawtypes")
	private java.util.HashSet ports = null;

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Classes.ActivateConfiguration", "ClassesActivateConfigurationPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ClassesActivateConfigurationPort".equals(portName)) {
            setClassesActivateConfigurationPortEndpointAddress(address);
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
