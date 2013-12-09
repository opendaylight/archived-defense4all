/**
 * ManagementSyslogServersPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Management.SyslogServers;

public interface ManagementSyslogServersPortType extends java.rmi.Remote {

    /**
     * Retrieves all the parameters of a SyslogServersTable. Edit
     * syslog servers parameters
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable get_SyslogServersTable(java.lang.String syslogServerAddress) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first SyslogServersTable. Edit
     * syslog servers parameters
     */
    public void getFirst_SyslogServersTable(javax.xml.rpc.holders.StringHolder syslogServerAddress, com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next SyslogServersTable. Edit syslog
     * servers parameters
     */
    public void getNext_SyslogServersTable(java.lang.String syslogServerAddress, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the SyslogServersTable. Edit syslog servers parameters
     */
    public void getAll_SyslogServersTable(com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a SyslogServersTable. Edit syslog servers parameters
     */
    public void create_SyslogServersTable(com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a SyslogServersTable. Edit syslog servers parameters
     */
    public void delete_SyslogServersTable(java.lang.String syslogServerAddress) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a SyslogServersTable. Edit
     * syslog servers parameters
     */
    public void update_SyslogServersTable(com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder entry) throws java.rmi.RemoteException;
}
