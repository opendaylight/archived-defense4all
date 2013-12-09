/**
 * SecurityOutOfStatePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.OutOfState;

public interface SecurityOutOfStatePortType extends java.rmi.Remote {

    /**
     * Retrieves all the parameters of a Profile. Configures the Out-of-State
     * Profiles
     */
    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile get_Profile(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first Profile. Configures the Out-of-State
     * Profiles
     */
    public void getFirst_Profile(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next Profile. Configures the Out-of-State
     * Profiles
     */
    public void getNext_Profile(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the Profile. Configures the Out-of-State Profiles
     */
    public void getAll_Profile(com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a Profile. Configures the Out-of-State Profiles (activated
     * with update-policies)
     */
    public void create_Profile(com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a Profile. Configures the Out-of-State Profiles (activated
     * with update-policies)
     */
    public void delete_Profile(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a Profile. Configures the
     * Out-of-State Profiles (activated with update-policies)
     */
    public void update_Profile(com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder entry) throws java.rmi.RemoteException;
}
