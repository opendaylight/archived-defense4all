/**
 * SecuritySignatureProtectionPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public interface SecuritySignatureProtectionPortType extends java.rmi.Remote {

    /**
     * Retrieves the value of AntiFraudCheckForUpdatesFreq. Set how
     * frequent device will check for anti-fraud update. 		 if no update
     * is sent for that period a trap will be sent to the user.
     */
    public long get_AntiFraudCheckForUpdatesFreq() throws java.rmi.RemoteException;

    /**
     * Updates the value of AntiFraudCheckForUpdatesFreq. Set how
     * frequent device will check for anti-fraud update. 		 if no update
     * is sent for that period a trap will be sent to the user.
     */
    public void set_AntiFraudCheckForUpdatesFreq(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of IpFragmentsOverlapStatus. Sets the IP
     * Fragments overlapping enabled/disabled
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.IpFragmentsOverlapStatus get_IpFragmentsOverlapStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of IpFragmentsOverlapStatus. Sets the IP
     * Fragments overlapping enabled/disabled
     */
    public void set_IpFragmentsOverlapStatus(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.IpFragmentsOverlapStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of TcpReassemblyStatus. Set the status
     * of the tcp reassembly mechanism
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.TcpReassemblyStatus get_TcpReassemblyStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of TcpReassemblyStatus. Set the status of
     * the tcp reassembly mechanism
     */
    public void set_TcpReassemblyStatus(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.TcpReassemblyStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a ProfileRules. Retrieves the
     * rules contained in profile
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.ProfileRules get_ProfileRules(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.ProfileRulesKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first ProfileRules. Retrieves the
     * rules contained in profile
     */
    public void getFirst_ProfileRules(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ProfileRulesKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ProfileRulesHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next ProfileRules. Retrieves the
     * rules contained in profile
     */
    public void getNext_ProfileRules(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.ProfileRulesKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ProfileRulesKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ProfileRulesHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the ProfileRules. Retrieves the rules contained
     * in profile
     */
    public void getAll_ProfileRules(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ProfileRulesArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a ProfileRules. Retrieves the rules contained in profile
     * (activated with update-policies)
     */
    public void create_ProfileRules(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ProfileRulesHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a ProfileRules. Retrieves the rules contained in profile
     * (activated with update-policies)
     */
    public void delete_ProfileRules(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.ProfileRulesKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of MinUrlPkt. The minimum length of an
     * incomplete URL request
     */
    public long get_MinUrlPkt() throws java.rmi.RemoteException;

    /**
     * Updates the value of MinUrlPkt. The minimum length of an incomplete
     * URL request
     */
    public void set_MinUrlPkt(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QuarantineActions. Web quarantine
     * actions configuration. The action table defines the quarantine action
     * when it is enabled in the policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions get_QuarantineActions(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QuarantineActions. Web quarantine
     * actions configuration. The action table defines the quarantine action
     * when it is enabled in the policy.
     */
    public void getFirst_QuarantineActions(javax.xml.rpc.holders.StringHolder quarantinePolicyName, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineActionsHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QuarantineActions. Web quarantine
     * actions configuration. The action table defines the quarantine action
     * when it is enabled in the policy.
     */
    public void getNext_QuarantineActions(java.lang.String quarantinePolicyName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineActionsHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QuarantineActions. Web quarantine actions
     * configuration. The action table defines the quarantine action when
     * it is enabled in the policy.
     */
    public void getAll_QuarantineActions(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineActionsArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a QuarantineActions. Web quarantine actions configuration.
     * The action table defines the quarantine action when it is enabled
     * in the policy.
     */
    public void create_QuarantineActions(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineActionsHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a QuarantineActions. Web quarantine actions configuration.
     * The action table defines the quarantine action when it is enabled
     * in the policy.
     */
    public void delete_QuarantineActions(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a QuarantineActions. Web quarantine
     * actions configuration. The action table defines the quarantine action
     * when it is enabled in the policy.
     */
    public void update_QuarantineActions(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineActionsHolder entry) throws java.rmi.RemoteException;

    /**
     * Get QuarantinePolicyName parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public void get_QuarantineActions_QuarantinePolicyName(javax.xml.rpc.holders.StringHolder quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Set QuarantinePolicyName parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public void set_QuarantineActions_QuarantinePolicyName(java.lang.String quarantinePolicyName, java.lang.String quarantinePolicyName2) throws java.rmi.RemoteException;

    /**
     * Get QuarantineActionType parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionType get_QuarantineActions_QuarantineActionType(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Set QuarantineActionType parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public void set_QuarantineActions_QuarantineActionType(java.lang.String quarantinePolicyName, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionType quarantineActionType) throws java.rmi.RemoteException;

    /**
     * Get QuarantineRedirectIp parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public java.lang.String get_QuarantineActions_QuarantineRedirectIp(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Set QuarantineRedirectIp parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public void set_QuarantineActions_QuarantineRedirectIp(java.lang.String quarantinePolicyName, java.lang.String quarantineRedirectIp) throws java.rmi.RemoteException;

    /**
     * Get QuarantineActionMetadata parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionMetadata get_QuarantineActions_QuarantineActionMetadata(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Set QuarantineActionMetadata parameter of a QuarantineActions.
     * Web quarantine actions configuration. The action table defines the
     * quarantine action when it is enabled in the policy.
     */
    public void set_QuarantineActions_QuarantineActionMetadata(java.lang.String quarantinePolicyName, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionMetadata quarantineActionMetadata) throws java.rmi.RemoteException;

    /**
     * Get QuarantineAgingHour parameter of a QuarantineActions. Web
     * quarantine actions configuration. The action table defines the quarantine
     * action when it is enabled in the policy.
     */
    public long get_QuarantineActions_QuarantineAgingHour(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Set QuarantineAgingHour parameter of a QuarantineActions. Web
     * quarantine actions configuration. The action table defines the quarantine
     * action when it is enabled in the policy.
     */
    public void set_QuarantineActions_QuarantineAgingHour(java.lang.String quarantinePolicyName, long quarantineAgingHour) throws java.rmi.RemoteException;

    /**
     * Get QuarantineAgingMin parameter of a QuarantineActions. Web
     * quarantine actions configuration. The action table defines the quarantine
     * action when it is enabled in the policy.
     */
    public long get_QuarantineActions_QuarantineAgingMin(java.lang.String quarantinePolicyName) throws java.rmi.RemoteException;

    /**
     * Set QuarantineAgingMin parameter of a QuarantineActions. Web
     * quarantine actions configuration. The action table defines the quarantine
     * action when it is enabled in the policy.
     */
    public void set_QuarantineActions_QuarantineAgingMin(java.lang.String quarantinePolicyName, long quarantineAgingMin) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of AntiFraudDropPointsAgingTime. Set the
     * Drop-Point attack aging time
     */
    public long get_AntiFraudDropPointsAgingTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of AntiFraudDropPointsAgingTime. Set the
     * Drop-Point attack aging time
     */
    public void set_AntiFraudDropPointsAgingTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of AntiFraudPhishingAgingTime. Set the
     * phishing attack aging time
     */
    public long get_AntiFraudPhishingAgingTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of AntiFraudPhishingAgingTime. Set the phishing
     * attack aging time
     */
    public void set_AntiFraudPhishingAgingTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QueryProfileList. Retrieves
     * the attacks contained in profile
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryProfileList get_QueryProfileList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryProfileListKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QueryProfileList. Retrieves
     * the attacks contained in profile
     */
    public void getFirst_QueryProfileList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileListKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileListHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QueryProfileList. Retrieves
     * the attacks contained in profile
     */
    public void getNext_QueryProfileList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryProfileListKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileListKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileListHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QueryProfileList. Retrieves the attacks contained
     * in profile
     */
    public void getAll_QueryProfileList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileListArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of SessionDrop. Sets the status of the
     * session-drop mechanism
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SessionDrop get_SessionDrop() throws java.rmi.RemoteException;

    /**
     * Updates the value of SessionDrop. Sets the status of the session-drop
     * mechanism
     */
    public void set_SessionDrop(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SessionDrop value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QueryAttributeNumber. Retrieves
     * the number of attacks configured for attribute
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributeNumber get_QueryAttributeNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributeNumberKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QueryAttributeNumber. Retrieves
     * the number of attacks configured for attribute
     */
    public void getFirst_QueryAttributeNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributeNumberKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributeNumberHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QueryAttributeNumber. Retrieves
     * the number of attacks configured for attribute
     */
    public void getNext_QueryAttributeNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributeNumberKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributeNumberKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributeNumberHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QueryAttributeNumber. Retrieves the number
     * of attacks configured for attribute
     */
    public void getAll_QueryAttributeNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributeNumberArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QuarantineUploadHtml. Quarantine
     * HTML Upload via TFTP
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineUploadHtml get_QuarantineUploadHtml(java.lang.String quarantineDownloadPolicy) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QuarantineUploadHtml. Quarantine
     * HTML Upload via TFTP
     */
    public void getFirst_QuarantineUploadHtml(javax.xml.rpc.holders.StringHolder quarantineDownloadPolicy, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineUploadHtmlHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QuarantineUploadHtml. Quarantine
     * HTML Upload via TFTP
     */
    public void getNext_QuarantineUploadHtml(java.lang.String quarantineDownloadPolicy, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineUploadHtmlHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QuarantineUploadHtml. Quarantine HTML Upload
     * via TFTP
     */
    public void getAll_QuarantineUploadHtml(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineUploadHtmlArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a QuarantineUploadHtml. Quarantine HTML Upload via TFTP
     */
    public void create_QuarantineUploadHtml(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineUploadHtmlHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a QuarantineUploadHtml. Quarantine HTML Upload via
     * TFTP
     */
    public void delete_QuarantineUploadHtml(java.lang.String quarantineDownloadPolicy) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a QuarantineUploadHtml. Quarantine
     * HTML Upload via TFTP
     */
    public void update_QuarantineUploadHtml(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QuarantineUploadHtmlHolder entry) throws java.rmi.RemoteException;

    /**
     * Get QuarantineDownloadPolicy parameter of a QuarantineUploadHtml.
     * Quarantine HTML Upload via TFTP
     */
    public void get_QuarantineUploadHtml_QuarantineDownloadPolicy(javax.xml.rpc.holders.StringHolder quarantineDownloadPolicy) throws java.rmi.RemoteException;

    /**
     * Set QuarantineDownloadPolicy parameter of a QuarantineUploadHtml.
     * Quarantine HTML Upload via TFTP
     */
    public void set_QuarantineUploadHtml_QuarantineDownloadPolicy(java.lang.String quarantineDownloadPolicy, java.lang.String quarantineDownloadPolicy2) throws java.rmi.RemoteException;

    /**
     * Get QuarantineDownloadAddress parameter of a QuarantineUploadHtml.
     * Quarantine HTML Upload via TFTP
     */
    public java.lang.String get_QuarantineUploadHtml_QuarantineDownloadAddress(java.lang.String quarantineDownloadPolicy) throws java.rmi.RemoteException;

    /**
     * Set QuarantineDownloadAddress parameter of a QuarantineUploadHtml.
     * Quarantine HTML Upload via TFTP
     */
    public void set_QuarantineUploadHtml_QuarantineDownloadAddress(java.lang.String quarantineDownloadPolicy, java.lang.String quarantineDownloadAddress) throws java.rmi.RemoteException;

    /**
     * Get QuarantineDownloadfile parameter of a QuarantineUploadHtml.
     * Quarantine HTML Upload via TFTP
     */
    public java.lang.String get_QuarantineUploadHtml_QuarantineDownloadfile(java.lang.String quarantineDownloadPolicy) throws java.rmi.RemoteException;

    /**
     * Set QuarantineDownloadfile parameter of a QuarantineUploadHtml.
     * Quarantine HTML Upload via TFTP
     */
    public void set_QuarantineUploadHtml_QuarantineDownloadfile(java.lang.String quarantineDownloadPolicy, java.lang.String quarantineDownloadfile) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DosShieldAgingTime. Defines how often
     * thresholds are checked
     */
    public long get_DosShieldAgingTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of DosShieldAgingTime. Defines how often
     * thresholds are checked
     */
    public void set_DosShieldAgingTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of AntiFraudStatus. Set Anti Fraud Protection
     * status
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AntiFraudStatus get_AntiFraudStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of AntiFraudStatus. Set Anti Fraud Protection
     * status (reset-activated)
     */
    public void set_AntiFraudStatus(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AntiFraudStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a AttributesValues. Configures
     * the IDS attributes parameters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValues get_AttributesValues(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValuesKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first AttributesValues. Configures
     * the IDS attributes parameters
     */
    public void getFirst_AttributesValues(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributesValuesKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributesValuesHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next AttributesValues. Configures
     * the IDS attributes parameters
     */
    public void getNext_AttributesValues(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValuesKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributesValuesKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributesValuesHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the AttributesValues. Configures the IDS attributes
     * parameters
     */
    public void getAll_AttributesValues(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributesValuesArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a AttributesValues. Configures the IDS attributes parameters
     * (activated with update-policies)
     */
    public void create_AttributesValues(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributesValuesHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a AttributesValues. Configures the IDS attributes parameters
     * (activated with update-policies)
     */
    public void delete_AttributesValues(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValuesKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of UnicodeEncoding. Defines encoding for
     * unicode normalization
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.UnicodeEncoding get_UnicodeEncoding() throws java.rmi.RemoteException;

    /**
     * Updates the value of UnicodeEncoding. Defines encoding for
     * unicode normalization
     */
    public void set_UnicodeEncoding(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.UnicodeEncoding value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a rsIDSSignaturesAttackAttributeStaticEntry.
     * Retrieves static attacks changed for attribute
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.RsIDSSignaturesAttackAttributeStaticEntry get_rsIDSSignaturesAttackAttributeStaticEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.RsIDSSignaturesAttackAttributeStaticEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first rsIDSSignaturesAttackAttributeStaticEntry.
     * Retrieves static attacks changed for attribute
     */
    public void getFirst_rsIDSSignaturesAttackAttributeStaticEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.RsIDSSignaturesAttackAttributeStaticEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.RsIDSSignaturesAttackAttributeStaticEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next rsIDSSignaturesAttackAttributeStaticEntry.
     * Retrieves static attacks changed for attribute
     */
    public void getNext_rsIDSSignaturesAttackAttributeStaticEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.RsIDSSignaturesAttackAttributeStaticEntryKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.RsIDSSignaturesAttackAttributeStaticEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.RsIDSSignaturesAttackAttributeStaticEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the rsIDSSignaturesAttackAttributeStaticEntry.
     * Retrieves static attacks changed for attribute
     */
    public void getAll_rsIDSSignaturesAttackAttributeStaticEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.RsIDSSignaturesAttackAttributeStaticEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a BasicFilterUser. Configures
     * the IDS basic user filters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser get_BasicFilterUser(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first BasicFilterUser. Configures
     * the IDS basic user filters
     */
    public void getFirst_BasicFilterUser(javax.xml.rpc.holders.StringHolder name, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.BasicFilterUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next BasicFilterUser. Configures
     * the IDS basic user filters
     */
    public void getNext_BasicFilterUser(java.lang.String name, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.BasicFilterUserHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the BasicFilterUser. Configures the IDS basic
     * user filters
     */
    public void getAll_BasicFilterUser(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.BasicFilterUserArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a BasicFilterUser. Configures the IDS basic user filters
     * (activated with update-policies)
     */
    public void create_BasicFilterUser(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.BasicFilterUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a BasicFilterUser. Configures the IDS basic user filters
     * (activated with update-policies)
     */
    public void delete_BasicFilterUser(java.lang.String name) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a BasicFilterUser. Configures
     * the IDS basic user filters (activated with update-policies)
     */
    public void update_BasicFilterUser(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.BasicFilterUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a ExcludeAttack. Configures
     * the IDS Attacks Excluded Addresses parameters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.ExcludeAttack get_ExcludeAttack(long ID) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first ExcludeAttack. Configures
     * the IDS Attacks Excluded Addresses parameters
     */
    public void getFirst_ExcludeAttack(javax.xml.rpc.holders.LongHolder ID, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ExcludeAttackHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next ExcludeAttack. Configures
     * the IDS Attacks Excluded Addresses parameters
     */
    public void getNext_ExcludeAttack(long ID, javax.xml.rpc.holders.LongHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ExcludeAttackHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the ExcludeAttack. Configures the IDS Attacks
     * Excluded Addresses parameters
     */
    public void getAll_ExcludeAttack(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ExcludeAttackArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a ExcludeAttack. Configures the IDS Attacks Excluded Addresses
     * parameters (activated with update-policies)
     */
    public void create_ExcludeAttack(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ExcludeAttackHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a ExcludeAttack. Configures the IDS Attacks Excluded
     * Addresses parameters (activated with update-policies)
     */
    public void delete_ExcludeAttack(long ID) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a ExcludeAttack. Configures
     * the IDS Attacks Excluded Addresses parameters (activated with update-policies)
     */
    public void update_ExcludeAttack(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.ExcludeAttackHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of FreeUpFrequency. Defines how often security
     * tables are cleared
     */
    public long get_FreeUpFrequency() throws java.rmi.RemoteException;

    /**
     * Updates the value of FreeUpFrequency. Defines how often security
     * tables are cleared
     */
    public void set_FreeUpFrequency(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a WebQuarantineEntry. Add /
     * Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.WebQuarantineEntry get_WebQuarantineEntry(java.lang.String webQuarantineAddress) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first WebQuarantineEntry. Add /
     * Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void getFirst_WebQuarantineEntry(javax.xml.rpc.holders.StringHolder webQuarantineAddress, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.WebQuarantineEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next WebQuarantineEntry. Add /
     * Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void getNext_WebQuarantineEntry(java.lang.String webQuarantineAddress, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.WebQuarantineEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the WebQuarantineEntry. Add / Delete web quarantine
     * entries. The IP will be processed according to the policy. A valid
     * policy should be defined
     */
    public void getAll_WebQuarantineEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.WebQuarantineEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a WebQuarantineEntry. Add / Delete web quarantine entries.
     * The IP will be processed according to the policy. A valid policy should
     * be defined
     */
    public void create_WebQuarantineEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.WebQuarantineEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a WebQuarantineEntry. Add / Delete web quarantine entries.
     * The IP will be processed according to the policy. A valid policy should
     * be defined
     */
    public void delete_WebQuarantineEntry(java.lang.String webQuarantineAddress) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a WebQuarantineEntry. Add
     * / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void update_WebQuarantineEntry(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.WebQuarantineEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Get webQuarantineAddress parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void get_WebQuarantineEntry_webQuarantineAddress(javax.xml.rpc.holders.StringHolder webQuarantineAddress) throws java.rmi.RemoteException;

    /**
     * Set webQuarantineAddress parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void set_WebQuarantineEntry_webQuarantineAddress(java.lang.String webQuarantineAddress, java.lang.String webQuarantineAddress2) throws java.rmi.RemoteException;

    /**
     * Get webQuarantinePolicy parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public java.lang.String get_WebQuarantineEntry_webQuarantinePolicy(java.lang.String webQuarantineAddress) throws java.rmi.RemoteException;

    /**
     * Set webQuarantinePolicy parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void set_WebQuarantineEntry_webQuarantinePolicy(java.lang.String webQuarantineAddress, java.lang.String webQuarantinePolicy) throws java.rmi.RemoteException;

    /**
     * Get webQuarantineTimeHours parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public long get_WebQuarantineEntry_webQuarantineTimeHours(java.lang.String webQuarantineAddress) throws java.rmi.RemoteException;

    /**
     * Set webQuarantineTimeHours parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void set_WebQuarantineEntry_webQuarantineTimeHours(java.lang.String webQuarantineAddress, long webQuarantineTimeHours) throws java.rmi.RemoteException;

    /**
     * Get webQuarantineTimeMin parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public long get_WebQuarantineEntry_webQuarantineTimeMin(java.lang.String webQuarantineAddress) throws java.rmi.RemoteException;

    /**
     * Set webQuarantineTimeMin parameter of a WebQuarantineEntry.
     * Add / Delete web quarantine entries. The IP will be processed according
     * to the policy. A valid policy should be defined
     */
    public void set_WebQuarantineEntry_webQuarantineTimeMin(java.lang.String webQuarantineAddress, long webQuarantineTimeMin) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of Status. Sets the Application Security
     * operation status
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.Status get_Status() throws java.rmi.RemoteException;

    /**
     * Updates the value of Status. Sets the Application Security
     * operation status (reset-activated)
     */
    public void set_Status(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.Status value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of MinFragmentSize. Sets the minimum allowed
     * length of a fragmented packet
     */
    public long get_MinFragmentSize() throws java.rmi.RemoteException;

    /**
     * Updates the value of MinFragmentSize. Sets the minimum allowed
     * length of a fragmented packet
     */
    public void set_MinFragmentSize(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QueryRuleNumber. Retrieves
     * the number of attacks contained in rule
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryRuleNumber get_QueryRuleNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryRuleNumberKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QueryRuleNumber. Retrieves
     * the number of attacks contained in rule
     */
    public void getFirst_QueryRuleNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleNumberKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleNumberHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QueryRuleNumber. Retrieves
     * the number of attacks contained in rule
     */
    public void getNext_QueryRuleNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryRuleNumberKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleNumberKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleNumberHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QueryRuleNumber. Retrieves the number of
     * attacks contained in rule
     */
    public void getAll_QueryRuleNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleNumberArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DosShieldStatus. Sets the DoS Shield
     * operation status
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.DosShieldStatus get_DosShieldStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of DosShieldStatus. Sets the DoS Shield operation
     * status (reset-activated)
     */
    public void set_DosShieldStatus(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.DosShieldStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a AttributeTypes. Configures
     * the IDS attribute types parameters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes get_AttributeTypes(java.lang.String typeName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first AttributeTypes. Configures
     * the IDS attribute types parameters
     */
    public void getFirst_AttributeTypes(javax.xml.rpc.holders.StringHolder typeName, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributeTypesHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next AttributeTypes. Configures
     * the IDS attribute types parameters
     */
    public void getNext_AttributeTypes(java.lang.String typeName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributeTypesHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the AttributeTypes. Configures the IDS attribute
     * types parameters
     */
    public void getAll_AttributeTypes(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributeTypesArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a AttributeTypes. Configures the IDS attribute types parameters
     */
    public void create_AttributeTypes(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributeTypesHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a AttributeTypes. Configures the IDS attribute types
     * parameters
     */
    public void delete_AttributeTypes(java.lang.String typeName) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a AttributeTypes. Configures
     * the IDS attribute types parameters
     */
    public void update_AttributeTypes(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttributeTypesHolder entry) throws java.rmi.RemoteException;

    /**
     * Get MatchMethod parameter of a AttributeTypes. Configures the
     * IDS attribute types parameters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MatchMethod get_AttributeTypes_MatchMethod(java.lang.String typeName) throws java.rmi.RemoteException;

    /**
     * Set MatchMethod parameter of a AttributeTypes. Configures the
     * IDS attribute types parameters
     */
    public void set_AttributeTypes_MatchMethod(java.lang.String typeName, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MatchMethod matchMethod) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a AttacksUser. Configures the
     * IDS user attacks parameters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser get_AttacksUser(long ID) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first AttacksUser. Configures the
     * IDS user attacks parameters
     */
    public void getFirst_AttacksUser(javax.xml.rpc.holders.LongHolder ID, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttacksUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next AttacksUser. Configures the
     * IDS user attacks parameters
     */
    public void getNext_AttacksUser(long ID, javax.xml.rpc.holders.LongHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttacksUserHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the AttacksUser. Configures the IDS user attacks
     * parameters
     */
    public void getAll_AttacksUser(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttacksUserArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a AttacksUser. Configures the IDS user attacks parameters
     * (activated with update-policies)
     */
    public void create_AttacksUser(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttacksUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a AttacksUser. Configures the IDS user attacks parameters
     * (activated with update-policies)
     */
    public void delete_AttacksUser(long ID) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a AttacksUser. Configures
     * the IDS user attacks parameters (activated with update-policies)
     */
    public void update_AttacksUser(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.AttacksUserHolder entry) throws java.rmi.RemoteException;

    /**
     * Get rsIDSAsAttackQuarantine parameter of a AttacksUser. Configures
     * the IDS user attacks parameters
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_rsIDSAsAttackQuarantine get_AttacksUser_rsIDSAsAttackQuarantine(long ID) throws java.rmi.RemoteException;

    /**
     * Set rsIDSAsAttackQuarantine parameter of a AttacksUser. Configures
     * the IDS user attacks parameters (activated with update-policies)
     */
    public void set_AttacksUser_rsIDSAsAttackQuarantine(long ID, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_rsIDSAsAttackQuarantine rsIDSAsAttackQuarantine) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of MinFragmentStatus. Minimum allowed protection
     * feature enabled/disabled
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.MinFragmentStatus get_MinFragmentStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of MinFragmentStatus. Minimum allowed protection
     * feature enabled/disabled
     */
    public void set_MinFragmentStatus(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.MinFragmentStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QueryAttributesList. Retrieves
     * the attacks contained in attribute
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesList get_QueryAttributesList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesListKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QueryAttributesList. Retrieves
     * the attacks contained in attribute
     */
    public void getFirst_QueryAttributesList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributesListKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributesListHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QueryAttributesList. Retrieves
     * the attacks contained in attribute
     */
    public void getNext_QueryAttributesList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesListKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributesListKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributesListHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QueryAttributesList. Retrieves the attacks
     * contained in attribute
     */
    public void getAll_QueryAttributesList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryAttributesListArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of DosShieldSamplingRate. Configures the
     * rate (sec) that packets are sampled.
     */
    public long get_DosShieldSamplingRate() throws java.rmi.RemoteException;

    /**
     * Updates the value of DosShieldSamplingRate. Configures the
     * rate (sec) that packets are sampled.
     */
    public void set_DosShieldSamplingRate(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of AntiFraudMalDwnldAgingTime. Set the
     * mal-dwnld attack aging time
     */
    public long get_AntiFraudMalDwnldAgingTime() throws java.rmi.RemoteException;

    /**
     * Updates the value of AntiFraudMalDwnldAgingTime. Set the mal-dwnld
     * attack aging time
     */
    public void set_AntiFraudMalDwnldAgingTime(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of MaxUri. Configures the maximum allowed
     * length of URI
     */
    public long get_MaxUri() throws java.rmi.RemoteException;

    /**
     * Updates the value of MaxUri. Configures the maximum allowed
     * length of URI
     */
    public void set_MaxUri(long value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QueryRuleList. Retrieves
     * the attacks contained in rule
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryRuleList get_QueryRuleList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryRuleListKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QueryRuleList. Retrieves
     * the attacks contained in rule
     */
    public void getFirst_QueryRuleList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleListKeyHolder key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleListHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QueryRuleList. Retrieves the
     * attacks contained in rule
     */
    public void getNext_QueryRuleList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryRuleListKey key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleListKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleListHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QueryRuleList. Retrieves the attacks contained
     * in rule
     */
    public void getAll_QueryRuleList(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryRuleListArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of MinFragmentMode. Sets the action performed
     * when a too short packet arrived
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.MinFragmentMode get_MinFragmentMode() throws java.rmi.RemoteException;

    /**
     * Updates the value of MinFragmentMode. Sets the action performed
     * when a too short packet arrived
     */
    public void set_MinFragmentMode(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.MinFragmentMode value) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a QueryProfileNumber. Retrieves
     * the number of attacks contained in profile
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryProfileNumber get_QueryProfileNumber(java.lang.String profileName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first QueryProfileNumber. Retrieves
     * the number of attacks contained in profile
     */
    public void getFirst_QueryProfileNumber(javax.xml.rpc.holders.StringHolder profileName, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileNumberHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next QueryProfileNumber. Retrieves
     * the number of attacks contained in profile
     */
    public void getNext_QueryProfileNumber(java.lang.String profileName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileNumberHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the QueryProfileNumber. Retrieves the number
     * of attacks contained in profile
     */
    public void getAll_QueryProfileNumber(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.holders.QueryProfileNumberArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of IpFregmentsOverlapMode. Sets the action
     * for overlapped fragments(if overlapping disabled)
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.IpFregmentsOverlapMode get_IpFregmentsOverlapMode() throws java.rmi.RemoteException;

    /**
     * Updates the value of IpFregmentsOverlapMode. Sets the action
     * for overlapped fragments(if overlapping disabled)
     */
    public void set_IpFregmentsOverlapMode(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.IpFregmentsOverlapMode value) throws java.rmi.RemoteException;
}
