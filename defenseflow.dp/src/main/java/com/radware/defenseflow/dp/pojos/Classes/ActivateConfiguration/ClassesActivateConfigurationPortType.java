/**
 * ClassesActivateConfigurationPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.ActivateConfiguration;

public interface ClassesActivateConfigurationPortType extends java.rmi.Remote {

    /**
     * Updates active classes with all the changes, additions or deletions
     * made via the temporary classes database.
     */
    public void update_ActiveClasses() throws java.rmi.RemoteException;
}
