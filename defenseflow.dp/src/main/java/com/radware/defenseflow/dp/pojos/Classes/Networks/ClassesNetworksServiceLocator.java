/**
 * ClassesNetworksServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.Networks;

public class ClassesNetworksServiceLocator extends org.apache.axis.client.Service implements com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksService {

    public ClassesNetworksServiceLocator() {
    }


    public ClassesNetworksServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public ClassesNetworksServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for ClassesNetworksPort
    private java.lang.String ClassesNetworksPort_address = "http://device_hostname_or_ip/soap";

    public java.lang.String getClassesNetworksPortAddress() {
        return ClassesNetworksPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String ClassesNetworksPortWSDDServiceName = "ClassesNetworksPort";

    public java.lang.String getClassesNetworksPortWSDDServiceName() {
        return ClassesNetworksPortWSDDServiceName;
    }

    public void setClassesNetworksPortWSDDServiceName(java.lang.String name) {
        ClassesNetworksPortWSDDServiceName = name;
    }

    public com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksPortType getClassesNetworksPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(ClassesNetworksPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getClassesNetworksPort(endpoint);
    }

    public com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksPortType getClassesNetworksPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksBindingStub _stub = new com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksBindingStub(portAddress, this);
            _stub.setPortName(getClassesNetworksPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setClassesNetworksPortEndpointAddress(java.lang.String address) {
        ClassesNetworksPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksBindingStub _stub = new com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksBindingStub(new java.net.URL(ClassesNetworksPort_address), this);
                _stub.setPortName(getClassesNetworksPortWSDDServiceName());
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
        if ("ClassesNetworksPort".equals(inputPortName)) {
            return getClassesNetworksPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("radware.Classes.Networks", "ClassesNetworksService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("radware.Classes.Networks", "ClassesNetworksPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("ClassesNetworksPort".equals(portName)) {
            setClassesNetworksPortEndpointAddress(address);
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
