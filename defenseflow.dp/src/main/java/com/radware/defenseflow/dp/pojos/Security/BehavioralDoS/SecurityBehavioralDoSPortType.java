/**
 * SecurityBehavioralDoSPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public interface SecurityBehavioralDoSPortType extends java.rmi.Remote {

    /**
     * Retrieves all the parameters of a rsNetFloodDynamicStateTwoEntry.
     * Early blocking configuration of mitigation
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry get_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsNetFloodDynamicStateTwoEntry.
     * Early blocking configuration of mitigation
     */
    public void getFirst_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntry_ProtectionTypeHolder protectionType, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsNetFloodDynamicStateTwoEntry.
     * Early blocking configuration of mitigation
     */
    public void getNext_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntry_ProtectionTypeHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsNetFloodDynamicStateTwoEntry. Early blocking
     * configuration of mitigation
     */
    public void getAll_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a rsNetFloodDynamicStateTwoEntry.
     * Early blocking configuration of mitigation
     */
    public void update_rsNetFloodDynamicStateTwoEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateTwoEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of rsNetFloodDynamicTermSC37. Sets stability
     * counter for state 3 and 7 of the Behavioral DoS
     */
    public long get_rsNetFloodDynamicTermSC37() throws java.rmi.RemoteException;

    /**
     * Updates the value of rsNetFloodDynamicTermSC37. Sets stability
     * counter for state 3 and 7 of the Behavioral DoS
     */
    public void set_rsNetFloodDynamicTermSC37(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a rsNetFloodDynamicStateFpEntry.
     * mitigation configuration for header filed selection
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry get_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsNetFloodDynamicStateFpEntry.
     * mitigation configuration for header filed selection
     */
    public void getFirst_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsNetFloodDynamicStateFpEntry.
     * mitigation configuration for header filed selection
     */
    public void getNext_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntryKey key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsNetFloodDynamicStateFpEntry. mitigation
     * configuration for header filed selection
     */
    public void getAll_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a rsNetFloodDynamicStateFpEntry.
     * mitigation configuration for header filed selection
     */
    public void update_rsNetFloodDynamicStateFpEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodDynamicStateFpEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Updates the value of LearningResetAll. Reset baseline for all
     * policies, The baseline will be re-established
     */
    public void set_LearningResetAll(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a rsNetFloodBypassEntry. Configuration
     * of the behavioral-DoS bypass for the footprint creation.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry get_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsNetFloodBypassEntry. Configuration
     * of the behavioral-DoS bypass for the footprint creation.
     */
    public void getFirst_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsNetFloodBypassEntry. Configuration
     * of the behavioral-DoS bypass for the footprint creation.
     */
    public void getNext_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntryKey key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsNetFloodBypassEntry. Configuration of the
     * behavioral-DoS bypass for the footprint creation.
     */
    public void getAll_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a rsNetFloodBypassEntry. Configuration
     * of the behavioral-DoS bypass for the footprint creation.
     */
    public void update_rsNetFloodBypassEntry(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.RsNetFloodBypassEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Updates the value of LearningResetPolicy. Reset baseline specific
     * policies. The baseline is re-established
     */
    public void set_LearningResetPolicy(java.lang.String value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of BandwidthIn. Configuration of the inbound
     * traffic in [Kbit/Sec]
     */
    public long get_BandwidthIn() throws java.rmi.RemoteException;

    /**
     * Updates the value of BandwidthIn. Configuration of the inbound
     * traffic in [Kbit/Sec]
     */
    public void set_BandwidthIn(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of rsNetFloodDynamicTermSC6. Sets stability
     * counter for state 6 of the Behavioral DoS
     */
    public long get_rsNetFloodDynamicTermSC6() throws java.rmi.RemoteException;

    /**
     * Updates the value of rsNetFloodDynamicTermSC6. Sets stability
     * counter for state 6 of the Behavioral DoS
     */
    public void set_rsNetFloodDynamicTermSC6(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of BandwidthOut. Configuration of the outbound
     * traffic in [Kbit/Sec]
     */
    public long get_BandwidthOut() throws java.rmi.RemoteException;

    /**
     * Updates the value of BandwidthOut. Configuration of the outbound
     * traffic in [Kbit/Sec]
     */
    public void set_BandwidthOut(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of LearningResponsePeriod. Configuration
     * of the baseline learning response time
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod get_LearningResponsePeriod() throws java.rmi.RemoteException;

    /**
     * Updates the value of LearningResponsePeriod. Configuration
     * of the baseline learning response time
     */
    public void set_LearningResponsePeriod(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.LearningResponsePeriod value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of FootprintStrickness. Sets the level
     * of strictness of the footprint output of the Behavioral DoS
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness get_FootprintStrickness() throws java.rmi.RemoteException;

    /**
     * Updates the value of FootprintStrickness. Sets the level of
     * strictness of the footprint output of the Behavioral DoS
     */
    public void set_FootprintStrickness(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.FootprintStrickness value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of Status. Sets the Behavioral DoS Protection
     * operation status
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status get_Status() throws java.rmi.RemoteException;

    /**
     * Updates the value of Status. Sets the Behavioral DoS Protection
     * operation status (reset-activated)
     */
    public void set_Status(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Status value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a Profiles. Configures advanced
     * parameters of Behavioral DoS Security profiles
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles get_Profiles(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first Profiles. Configures advanced
     * parameters of Behavioral DoS Security profiles
     */
    public void getFirst_Profiles(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next Profiles. Configures advanced
     * parameters of Behavioral DoS Security profiles
     */
    public void getNext_Profiles(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the Profiles. Configures advanced parameters
     * of Behavioral DoS Security profiles
     */
    public void getAll_Profiles(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a Profiles. Configures advanced parameters of Behavioral
     * DoS Security profiles (activated with update-policies)
     */
    public void create_Profiles(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a Profiles. Configures advanced parameters of Behavioral
     * DoS Security profiles (activated with update-policies)
     */
    public void delete_Profiles(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a Profiles. Configures advanced
     * parameters of Behavioral DoS Security profiles (activated with update-policies)
     */
    public void update_Profiles(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder entry) throws java.rmi.RemoteException;
    public java.lang.String get_rsIdsHwaccWarning() throws java.rmi.RemoteException;

    /**
     * Retrieves the value of rsNetFloodDynamicTermSC2. Sets stability
     * counter for mitigation configuration of the Behavioral DoS
     */
    public long get_rsNetFloodDynamicTermSC2() throws java.rmi.RemoteException;

    /**
     * Updates the value of rsNetFloodDynamicTermSC2. Sets stability
     * counter for mitigation configuration of the Behavioral DoS
     */
    public void set_rsNetFloodDynamicTermSC2(long value) throws java.rmi.RemoteException;

    /**
     * Updates the value of SetQuotasDefaults. Set defaults quotas
     * according the configured bandwidth
     */
    public void set_SetQuotasDefaults(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a Quotas. Configuration of
     * the behavioral-DoS protocol quotas in percents
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas get_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first Quotas. Configuration of
     * the behavioral-DoS protocol quotas in percents
     */
    public void getFirst_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.Quotas_DirectionHolder direction, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next Quotas. Configuration of the
     * behavioral-DoS protocol quotas in percents
     */
    public void getNext_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.Quotas_DirectionHolder next_key, com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the Quotas. Configuration of the behavioral-DoS
     * protocol quotas in percents
     */
    public void getAll_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a Quotas. Configuration of
     * the behavioral-DoS protocol quotas in percents
     */
    public void update_Quotas(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.QuotasHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of SamplingStatus. Activate / Deactivate
     * sampling in the Behavioral DoS Protection mechanism
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus get_SamplingStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of SamplingStatus. Activate / Deactivate
     * sampling in the Behavioral DoS Protection mechanism
     */
    public void set_SamplingStatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SamplingStatus value) throws java.rmi.RemoteException;
}
