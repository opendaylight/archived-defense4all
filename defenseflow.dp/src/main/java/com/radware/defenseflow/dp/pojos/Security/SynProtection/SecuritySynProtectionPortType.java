/**
 * SecuritySynProtectionPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;

public interface SecuritySynProtectionPortType extends java.rmi.Remote {

    /**
     * Retrieves the value of SSLMitigationAlteonStatus. Update SSL
     * Mitigation status
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.SSLMitigationAlteonStatus get_SSLMitigationAlteonStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of SSLMitigationAlteonStatus. Update SSL
     * Mitigation status (activated with update-policies)
     */
    public void set_SSLMitigationAlteonStatus(com.radware.defenseflow.dp.pojos.Security.SynProtection.SSLMitigationAlteonStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of TrackingTime. Sets Tracking time (sec)
     * period before attack deactivation
     */
    public long get_TrackingTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of TrackingTime. Sets Tracking time (sec)
     * period before attack deactivation
     */
    public void set_TrackingTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of Status. Sets the SYN Protection status
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.Status get_Status() throws java.rmi.RemoteException;

    /**
     * Updates the value of Status. Sets the SYN Protection status
     * (reset-activated)
     */
    public void set_Status(com.radware.defenseflow.dp.pojos.Security.SynProtection.Status value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a rsIDSynSSLMitigationEntry.
     * Update SSL Mitigation policies configuration
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSynSSLMitigationEntry get_rsIDSynSSLMitigationEntry(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsIDSynSSLMitigationEntry.
     * Update SSL Mitigation policies configuration
     */
    public void getFirst_rsIDSynSSLMitigationEntry(javax.xml.rpc.holders.StringHolder name, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSynSSLMitigationEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsIDSynSSLMitigationEntry.
     * Update SSL Mitigation policies configuration
     */
    public void getNext_rsIDSynSSLMitigationEntry(java.lang.String name, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSynSSLMitigationEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsIDSynSSLMitigationEntry. Update SSL Mitigation
     * policies configuration
     */
    public void getAll_rsIDSynSSLMitigationEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSynSSLMitigationEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a rsIDSynSSLMitigationEntry. Update SSL Mitigation policies
     * configuration (activated with update-policies)
     */
    public void create_rsIDSynSSLMitigationEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSynSSLMitigationEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a rsIDSynSSLMitigationEntry. Update SSL Mitigation
     * policies configuration (activated with update-policies)
     */
    public void delete_rsIDSynSSLMitigationEntry(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a rsIDSynSSLMitigationEntry.
     * Update SSL Mitigation policies configuration (activated with update-policies)
     */
    public void update_rsIDSynSSLMitigationEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSynSSLMitigationEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a SSLMitigationAlteonPortsEntry.
     * Update SSL device assigned ports configuration
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.SSLMitigationAlteonPortsEntry get_SSLMitigationAlteonPortsEntry(long index) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first SSLMitigationAlteonPortsEntry.
     * Update SSL device assigned ports configuration
     */
    public void getFirst_SSLMitigationAlteonPortsEntry(javax.xml.rpc.holders.LongHolder index, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.SSLMitigationAlteonPortsEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next SSLMitigationAlteonPortsEntry.
     * Update SSL device assigned ports configuration
     */
    public void getNext_SSLMitigationAlteonPortsEntry(long index, javax.xml.rpc.holders.LongHolder next_key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.SSLMitigationAlteonPortsEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the SSLMitigationAlteonPortsEntry. Update SSL
     * device assigned ports configuration
     */
    public void getAll_SSLMitigationAlteonPortsEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.SSLMitigationAlteonPortsEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a SSLMitigationAlteonPortsEntry.
     * Update SSL device assigned ports configuration (activated with update-policies)
     */
    public void update_SSLMitigationAlteonPortsEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.SSLMitigationAlteonPortsEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a rsIDSSynProfilesEntry. Manage
     * user defined Profiles
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry get_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsIDSSynProfilesEntry. Manage
     * user defined Profiles
     */
    public void getFirst_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsIDSSynProfilesEntry. Manage
     * user defined Profiles
     */
    public void getNext_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntryKey key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsIDSSynProfilesEntry. Manage user defined
     * Profiles
     */
    public void getAll_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a rsIDSSynProfilesEntry. Manage user defined Profiles
     */
    public void create_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a rsIDSSynProfilesEntry. Manage user defined Profiles
     */
    public void delete_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntryKey key) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a rsIDSSynProfilesEntry. Manage
     * user defined Profiles
     */
    public void update_rsIDSSynProfilesEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of SSLMitigationAlteonMngIP. Update SSL
     * Alteon management IP configuration
     */
    public java.lang.String get_SSLMitigationAlteonMngIP() throws java.rmi.RemoteException;

    /**
     * Updates the value of SSLMitigationAlteonMngIP. Update SSL Alteon
     * management IP configuration (activated with update-policies)
     */
    public void set_SSLMitigationAlteonMngIP(java.lang.String value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of RetransmitMaxTime. Sets a Maximum Allowed
     * SYN Retransmission Time(sec) between reset sending and successful
     * authentication by SYN retransmit
     */
    public long get_RetransmitMaxTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of RetransmitMaxTime. Sets a Maximum Allowed
     * SYN Retransmission Time(sec) between reset sending and successful
     * authentication by SYN retransmit
     */
    public void set_RetransmitMaxTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a AttacksUser. SYN Protection
     * User Attack Configuration
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser get_AttacksUser(long ID) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first AttacksUser. SYN Protection
     * User Attack Configuration
     */
    public void getFirst_AttacksUser(javax.xml.rpc.holders.LongHolder ID, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.AttacksUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next AttacksUser. SYN Protection
     * User Attack Configuration
     */
    public void getNext_AttacksUser(long ID, javax.xml.rpc.holders.LongHolder next_key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.AttacksUserHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the AttacksUser. SYN Protection User Attack Configuration
     */
    public void getAll_AttacksUser(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.AttacksUserArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a AttacksUser. SYN Protection User Attack Configuration
     */
    public void create_AttacksUser(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.AttacksUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a AttacksUser. SYN Protection User Attack Configuration
     */
    public void delete_AttacksUser(long ID) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a AttacksUser. SYN Protection
     * User Attack Configuration
     */
    public void update_AttacksUser(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.AttacksUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of RetransmitMinTime. Sets a Minimum Allowed
     * SYN Retransmission Time(in seconds) between reset sending and successful
     * authentication by SYN retransmit
     */
    public long get_RetransmitMinTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of RetransmitMinTime. Sets a Minimum Allowed
     * SYN Retransmission Time(in seconds) between reset sending and successful
     * authentication by SYN retransmit
     */
    public void set_RetransmitMinTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a rsIDSSynProfilesParamsEntry.
     * Update the parameters of a syn profile
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry get_rsIDSSynProfilesParamsEntry(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsIDSSynProfilesParamsEntry.
     * Update the parameters of a syn profile
     */
    public void getFirst_rsIDSSynProfilesParamsEntry(javax.xml.rpc.holders.StringHolder name, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesParamsEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsIDSSynProfilesParamsEntry.
     * Update the parameters of a syn profile
     */
    public void getNext_rsIDSSynProfilesParamsEntry(java.lang.String name, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesParamsEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsIDSSynProfilesParamsEntry. Update the parameters
     * of a syn profile
     */
    public void getAll_rsIDSSynProfilesParamsEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesParamsEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a rsIDSSynProfilesParamsEntry.
     * Update the parameters of a syn profile
     */
    public void update_rsIDSSynProfilesParamsEntry(com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesParamsEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of SSLMitigationAlteonSnmpAlertPort. Update
     * SSL Health-check port (Alteon SNMP port) configuration
     */
    public long get_SSLMitigationAlteonSnmpAlertPort() throws java.rmi.RemoteException;

    /**
     * Updates the value of SSLMitigationAlteonSnmpAlertPort. Update
     * SSL Health-check port (Alteon SNMP port) configuration (activated
     * with update-policies)
     */
    public void set_SSLMitigationAlteonSnmpAlertPort(long value) throws java.rmi.RemoteException;
}
