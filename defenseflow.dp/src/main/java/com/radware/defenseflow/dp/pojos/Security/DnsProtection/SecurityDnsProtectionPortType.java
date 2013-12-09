/**
 * SecurityDnsProtectionPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public interface SecurityDnsProtectionPortType extends java.rmi.Remote {

    /**
     * Retrieves the value of DnsProtectionCollectiveRateLimitStatus.
     * Get the Status of the collective rate-limit mitigation
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveRateLimitStatus get_DnsProtectionCollectiveRateLimitStatus() throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionGlobalStatus. Sets the
     * DNS Protection operation status
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus get_DnsProtectionGlobalStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionGlobalStatus. Sets the DNS
     * Protection operation status (reset-activated)
     */
    public void set_DnsProtectionGlobalStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a DnsProtectionDynamicStateFp.
     * mitigation configuration for header filed selection
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFp get_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first DnsProtectionDynamicStateFp.
     * mitigation configuration for header filed selection
     */
    public void getFirst_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpKeyHolder key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next DnsProtectionDynamicStateFp.
     * mitigation configuration for header filed selection
     */
    public void getNext_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateFpKey key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the DnsProtectionDynamicStateFp. mitigation configuration
     * for header filed selection
     */
    public void getAll_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a DnsProtectionDynamicStateFp.
     * mitigation configuration for header filed selection
     */
    public void update_DnsProtectionDynamicStateFp(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateFpHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionLearningResponsePeriod.
     * Configuration of the baseline learning response time
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod get_DnsProtectionLearningResponsePeriod() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionLearningResponsePeriod. Configuration
     * of the baseline learning response time
     */
    public void set_DnsProtectionLearningResponsePeriod(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionLearningResponsePeriod value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionFootprintStrictness. Sets
     * the level of strictness of the footprint output of the DNS Protection
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness get_DnsProtectionFootprintStrictness() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionFootprintStrictness. Sets
     * the level of strictness of the footprint output of the DNS Protection
     */
    public void set_DnsProtectionFootprintStrictness(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionFootprintStrictness value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of SamplingStatus. Activate / Deactivate
     * sampling in the DNS Protection mechanism
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus get_SamplingStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of SamplingStatus. Activate / Deactivate
     * sampling in the DNS Protection mechanism
     */
    public void set_SamplingStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.SamplingStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a DnsProtectionBypassEntry.
     * Configuration of the DNS protection bypass for the footprint creation.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry get_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first DnsProtectionBypassEntry.
     * Configuration of the DNS protection bypass for the footprint creation.
     */
    public void getFirst_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next DnsProtectionBypassEntry.
     * Configuration of the DNS protection bypass for the footprint creation.
     */
    public void getNext_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntryKey key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the DnsProtectionBypassEntry. Configuration of
     * the DNS protection bypass for the footprint creation.
     */
    public void getAll_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a DnsProtectionBypassEntry.
     * Configuration of the DNS protection bypass for the footprint creation.
     */
    public void update_DnsProtectionBypassEntry(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionBypassEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a DnsProtectionDynamicStateTwo.
     * Early blocking configuration of mitigation
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo get_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType protectionType) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first DnsProtectionDynamicStateTwo.
     * Early blocking configuration of mitigation
     */
    public void getFirst_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwo_ProtectionTypeHolder protectionType, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next DnsProtectionDynamicStateTwo.
     * Early blocking configuration of mitigation
     */
    public void getNext_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionDynamicStateTwo_ProtectionType protectionType, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwo_ProtectionTypeHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the DnsProtectionDynamicStateTwo. Early blocking
     * configuration of mitigation
     */
    public void getAll_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a DnsProtectionDynamicStateTwo.
     * Early blocking configuration of mitigation
     */
    public void update_DnsProtectionDynamicStateTwo(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionDynamicStateTwoHolder entry) throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionResetPolicyLearning. Reset
     * baseline specific policies. The baseline is re-established
     */
    public void set_DnsProtectionResetPolicyLearning(java.lang.String value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionDynamicTermSC2. Sets stability
     * counter for mitigation configuration of the DNS Protection
     */
    public long get_DnsProtectionDynamicTermSC2() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionDynamicTermSC2. Sets stability
     * counter for mitigation configuration of the DNS Protection
     */
    public void set_DnsProtectionDynamicTermSC2(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a DnsProtectionProfile. Configures
     * the DNS protection profiles
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile get_DnsProtectionProfile(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first DnsProtectionProfile. Configures
     * the DNS protection profiles
     */
    public void getFirst_DnsProtectionProfile(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next DnsProtectionProfile. Configures
     * the DNS protection profiles
     */
    public void getNext_DnsProtectionProfile(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the DnsProtectionProfile. Configures the DNS
     * protection profiles
     */
    public void getAll_DnsProtectionProfile(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a DnsProtectionProfile. Configures the DNS protection
     * profiles (activated with update-policies)
     */
    public void create_DnsProtectionProfile(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a DnsProtectionProfile. Configures the DNS protection
     * profiles (activated with update-policies)
     */
    public void delete_DnsProtectionProfile(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a DnsProtectionProfile. Configures
     * the DNS protection profiles (activated with update-policies)
     */
    public void update_DnsProtectionProfile(com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionCollectiveChallengeStatus.
     * Set the Status of the collective challenge mitigation
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus get_DnsProtectionCollectiveChallengeStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionCollectiveChallengeStatus.
     * Set the Status of the collective challenge mitigation
     */
    public void set_DnsProtectionCollectiveChallengeStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionCollectiveChallengeStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionSignatureChallengeStatus.
     * Set the Status of the signature challenge mitigation
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus get_DnsProtectionSignatureChallengeStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionSignatureChallengeStatus.
     * Set the Status of the signature challenge mitigation
     */
    public void set_DnsProtectionSignatureChallengeStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureChallengeStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionDynamicTermSC3. Sets stability
     * counter for state 3 of the DNS Protection
     */
    public long get_DnsProtectionDynamicTermSC3() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionDynamicTermSC3. Sets stability
     * counter for state 3 of the DNS Protection
     */
    public void set_DnsProtectionDynamicTermSC3(long value) throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionResetAllLearning. Reset baseline
     * for all policies, The baseline will be re-established
     */
    public void set_DnsProtectionResetAllLearning(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionSignatureRateLimitStatus.
     * Set the Status of the signature rate-limit mitigation
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus get_DnsProtectionSignatureRateLimitStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionSignatureRateLimitStatus.
     * Set the Status of the signature rate-limit mitigation
     */
    public void set_DnsProtectionSignatureRateLimitStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSignatureRateLimitStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionSdmProtComplianceStatus.
     * Enable or Disable SDM Protocol Compliance Checks
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus get_DnsProtectionSdmProtComplianceStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionSdmProtComplianceStatus.
     * Enable or Disable SDM Protocol Compliance Checks
     */
    public void set_DnsProtectionSdmProtComplianceStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionSdmProtComplianceStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DnsProtectionDynamicTermSC6. Sets stability
     * counter for state 6 of the DNS Protection
     */
    public long get_DnsProtectionDynamicTermSC6() throws java.rmi.RemoteException;

    /**
     * Updates the value of DnsProtectionDynamicTermSC6. Sets stability
     * counter for state 6 of the DNS Protection
     */
    public void set_DnsProtectionDynamicTermSC6(long value) throws java.rmi.RemoteException;
}
