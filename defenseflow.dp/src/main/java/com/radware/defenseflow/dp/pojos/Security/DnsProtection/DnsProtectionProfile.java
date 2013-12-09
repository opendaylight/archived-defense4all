/**
 * DnsProtectionProfile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;


/**
 * This structure describes the parameters of a DnsProtectionProfile
 */
public class DnsProtectionProfile  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* This variable indicates whether to check dns a protocol in
     * this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAFloodstatus dnsAFloodstatus;

    /* This variable indicates whether to check dns mx protocol in
     * this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsMxFloodstatus dnsMxFloodstatus;

    /* This variable indicates whether to check dns ptr protocol in
     * this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsPtrFloodstatus dnsPtrFloodstatus;

    /* This variable indicates whether to check dns aaaa protocol
     * in this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAaaaFloodstatus dnsAaaaFloodstatus;

    /* This variable indicates whether to check dns text protocol
     * in this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsTextFloodstatus dnsTextFloodstatus;

    /* This variable indicates whether to check dns soa in this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSoaFloodstatus dnsSoaFloodstatus;

    /* This variable indicates whether to check dns naptr in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsNaptrFloodstatus dnsNaptrFloodstatus;

    /* This variable indicates whether to check dns srv in this policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSrvFloodstatus dnsSrvFloodstatus;

    /* This variable indicates whether to check dns other in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsOtherFloodstatus dnsOtherFloodstatus;

    /* Expected number of queries per second for this policy. */
    private java.lang.Long expectedQps;

    /* dns a quota value for this policy. */
    private java.lang.Long dnsAQuota;

    /* dns mx quota value for this policy. */
    private java.lang.Long dnsMxQuota;

    /* dns ptr quota value for this policy. */
    private java.lang.Long dnsPtrQuota;

    /* dns aaaa quota value for this policy. */
    private java.lang.Long dnsAaaaQuota;

    /* dns text quota value for this policy. */
    private java.lang.Long dnsTextQuota;

    /* dns soa quota value for this policy. */
    private java.lang.Long dnsSoaQuota;

    /* dns naptr quota value for this policy. */
    private java.lang.Long dnsNaptrQuota;

    /* dns srv quota value for this policy. */
    private java.lang.Long dnsSrvQuota;

    /* dns other types quota value for this policy. */
    private java.lang.Long dnsOtherQuota;

    /* This variable indicates the policy action */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_Action action;

    /* The maximum number of queries per sec per dns type that this
     * profile will forward in collective mode mitigation */
    private java.lang.Long maxAllowedQPS;

    /* The Signature rate limit mitigation target, as a percentage
     * of the learned traffic. */
    private java.lang.Long sigRateLimit;

    /* Whether to activate manual triggers mode for this profile. */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_ManTrigStatus manTrigStatus;

    /* Number of QPS for starting the manual triggers mitigation. */
    private java.lang.Long manTrigActivationThreshold;

    /* Number of QPS for terminating the manual triggers mitigation. */
    private java.lang.Long manTrigTerminationThreshold;

    /* The number of QPS that will be forwarded in manual triggers
     * mode. */
    private java.lang.Long manTrigMaxQpsTarget;

    /* Number of seconds above the manual triggers activation threshold
     * before starting the mitigation */
    private java.lang.Long manTrigActivationPeriod;

    /* Number of seconds below the manual triggers termination threshold
     * before stopping the mitigation */
    private java.lang.Long manTrigTerminationPeriod;

    /* Number of seconds with bad feedback before changing the mitigation
     * type in manual triggers mode */
    private java.lang.Long manTrigEscalationPeriod;

    /* Packet Report Status */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetReport packetReport;

    /* Packet Trace Status */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetTrace packetTrace;

    public DnsProtectionProfile() {
    }

    public DnsProtectionProfile(
           java.lang.String profileName,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAFloodstatus dnsAFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsMxFloodstatus dnsMxFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsPtrFloodstatus dnsPtrFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAaaaFloodstatus dnsAaaaFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsTextFloodstatus dnsTextFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSoaFloodstatus dnsSoaFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsNaptrFloodstatus dnsNaptrFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSrvFloodstatus dnsSrvFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsOtherFloodstatus dnsOtherFloodstatus,
           java.lang.Long expectedQps,
           java.lang.Long dnsAQuota,
           java.lang.Long dnsMxQuota,
           java.lang.Long dnsPtrQuota,
           java.lang.Long dnsAaaaQuota,
           java.lang.Long dnsTextQuota,
           java.lang.Long dnsSoaQuota,
           java.lang.Long dnsNaptrQuota,
           java.lang.Long dnsSrvQuota,
           java.lang.Long dnsOtherQuota,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_Action action,
           java.lang.Long maxAllowedQPS,
           java.lang.Long sigRateLimit,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_ManTrigStatus manTrigStatus,
           java.lang.Long manTrigActivationThreshold,
           java.lang.Long manTrigTerminationThreshold,
           java.lang.Long manTrigMaxQpsTarget,
           java.lang.Long manTrigActivationPeriod,
           java.lang.Long manTrigTerminationPeriod,
           java.lang.Long manTrigEscalationPeriod,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetReport packetReport,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetTrace packetTrace) {
           this.profileName = profileName;
           this.dnsAFloodstatus = dnsAFloodstatus;
           this.dnsMxFloodstatus = dnsMxFloodstatus;
           this.dnsPtrFloodstatus = dnsPtrFloodstatus;
           this.dnsAaaaFloodstatus = dnsAaaaFloodstatus;
           this.dnsTextFloodstatus = dnsTextFloodstatus;
           this.dnsSoaFloodstatus = dnsSoaFloodstatus;
           this.dnsNaptrFloodstatus = dnsNaptrFloodstatus;
           this.dnsSrvFloodstatus = dnsSrvFloodstatus;
           this.dnsOtherFloodstatus = dnsOtherFloodstatus;
           this.expectedQps = expectedQps;
           this.dnsAQuota = dnsAQuota;
           this.dnsMxQuota = dnsMxQuota;
           this.dnsPtrQuota = dnsPtrQuota;
           this.dnsAaaaQuota = dnsAaaaQuota;
           this.dnsTextQuota = dnsTextQuota;
           this.dnsSoaQuota = dnsSoaQuota;
           this.dnsNaptrQuota = dnsNaptrQuota;
           this.dnsSrvQuota = dnsSrvQuota;
           this.dnsOtherQuota = dnsOtherQuota;
           this.action = action;
           this.maxAllowedQPS = maxAllowedQPS;
           this.sigRateLimit = sigRateLimit;
           this.manTrigStatus = manTrigStatus;
           this.manTrigActivationThreshold = manTrigActivationThreshold;
           this.manTrigTerminationThreshold = manTrigTerminationThreshold;
           this.manTrigMaxQpsTarget = manTrigMaxQpsTarget;
           this.manTrigActivationPeriod = manTrigActivationPeriod;
           this.manTrigTerminationPeriod = manTrigTerminationPeriod;
           this.manTrigEscalationPeriod = manTrigEscalationPeriod;
           this.packetReport = packetReport;
           this.packetTrace = packetTrace;
    }


    /**
     * Gets the profileName value for this DnsProtectionProfile.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this DnsProtectionProfile.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the dnsAFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsAFloodstatus   * This variable indicates whether to check dns a protocol in
     * this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAFloodstatus getDnsAFloodstatus() {
        return dnsAFloodstatus;
    }


    /**
     * Sets the dnsAFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsAFloodstatus   * This variable indicates whether to check dns a protocol in
     * this policy.
     */
    public void setDnsAFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAFloodstatus dnsAFloodstatus) {
        this.dnsAFloodstatus = dnsAFloodstatus;
    }


    /**
     * Gets the dnsMxFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsMxFloodstatus   * This variable indicates whether to check dns mx protocol in
     * this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsMxFloodstatus getDnsMxFloodstatus() {
        return dnsMxFloodstatus;
    }


    /**
     * Sets the dnsMxFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsMxFloodstatus   * This variable indicates whether to check dns mx protocol in
     * this policy.
     */
    public void setDnsMxFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsMxFloodstatus dnsMxFloodstatus) {
        this.dnsMxFloodstatus = dnsMxFloodstatus;
    }


    /**
     * Gets the dnsPtrFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsPtrFloodstatus   * This variable indicates whether to check dns ptr protocol in
     * this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsPtrFloodstatus getDnsPtrFloodstatus() {
        return dnsPtrFloodstatus;
    }


    /**
     * Sets the dnsPtrFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsPtrFloodstatus   * This variable indicates whether to check dns ptr protocol in
     * this policy.
     */
    public void setDnsPtrFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsPtrFloodstatus dnsPtrFloodstatus) {
        this.dnsPtrFloodstatus = dnsPtrFloodstatus;
    }


    /**
     * Gets the dnsAaaaFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsAaaaFloodstatus   * This variable indicates whether to check dns aaaa protocol
     * in this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAaaaFloodstatus getDnsAaaaFloodstatus() {
        return dnsAaaaFloodstatus;
    }


    /**
     * Sets the dnsAaaaFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsAaaaFloodstatus   * This variable indicates whether to check dns aaaa protocol
     * in this policy.
     */
    public void setDnsAaaaFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAaaaFloodstatus dnsAaaaFloodstatus) {
        this.dnsAaaaFloodstatus = dnsAaaaFloodstatus;
    }


    /**
     * Gets the dnsTextFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsTextFloodstatus   * This variable indicates whether to check dns text protocol
     * in this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsTextFloodstatus getDnsTextFloodstatus() {
        return dnsTextFloodstatus;
    }


    /**
     * Sets the dnsTextFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsTextFloodstatus   * This variable indicates whether to check dns text protocol
     * in this policy.
     */
    public void setDnsTextFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsTextFloodstatus dnsTextFloodstatus) {
        this.dnsTextFloodstatus = dnsTextFloodstatus;
    }


    /**
     * Gets the dnsSoaFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsSoaFloodstatus   * This variable indicates whether to check dns soa in this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSoaFloodstatus getDnsSoaFloodstatus() {
        return dnsSoaFloodstatus;
    }


    /**
     * Sets the dnsSoaFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsSoaFloodstatus   * This variable indicates whether to check dns soa in this policy.
     */
    public void setDnsSoaFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSoaFloodstatus dnsSoaFloodstatus) {
        this.dnsSoaFloodstatus = dnsSoaFloodstatus;
    }


    /**
     * Gets the dnsNaptrFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsNaptrFloodstatus   * This variable indicates whether to check dns naptr in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsNaptrFloodstatus getDnsNaptrFloodstatus() {
        return dnsNaptrFloodstatus;
    }


    /**
     * Sets the dnsNaptrFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsNaptrFloodstatus   * This variable indicates whether to check dns naptr in this
     * policy.
     */
    public void setDnsNaptrFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsNaptrFloodstatus dnsNaptrFloodstatus) {
        this.dnsNaptrFloodstatus = dnsNaptrFloodstatus;
    }


    /**
     * Gets the dnsSrvFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsSrvFloodstatus   * This variable indicates whether to check dns srv in this policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSrvFloodstatus getDnsSrvFloodstatus() {
        return dnsSrvFloodstatus;
    }


    /**
     * Sets the dnsSrvFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsSrvFloodstatus   * This variable indicates whether to check dns srv in this policy.
     */
    public void setDnsSrvFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSrvFloodstatus dnsSrvFloodstatus) {
        this.dnsSrvFloodstatus = dnsSrvFloodstatus;
    }


    /**
     * Gets the dnsOtherFloodstatus value for this DnsProtectionProfile.
     * 
     * @return dnsOtherFloodstatus   * This variable indicates whether to check dns other in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsOtherFloodstatus getDnsOtherFloodstatus() {
        return dnsOtherFloodstatus;
    }


    /**
     * Sets the dnsOtherFloodstatus value for this DnsProtectionProfile.
     * 
     * @param dnsOtherFloodstatus   * This variable indicates whether to check dns other in this
     * policy.
     */
    public void setDnsOtherFloodstatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsOtherFloodstatus dnsOtherFloodstatus) {
        this.dnsOtherFloodstatus = dnsOtherFloodstatus;
    }


    /**
     * Gets the expectedQps value for this DnsProtectionProfile.
     * 
     * @return expectedQps   * Expected number of queries per second for this policy.
     */
    public java.lang.Long getExpectedQps() {
        return expectedQps;
    }


    /**
     * Sets the expectedQps value for this DnsProtectionProfile.
     * 
     * @param expectedQps   * Expected number of queries per second for this policy.
     */
    public void setExpectedQps(java.lang.Long expectedQps) {
        this.expectedQps = expectedQps;
    }


    /**
     * Gets the dnsAQuota value for this DnsProtectionProfile.
     * 
     * @return dnsAQuota   * dns a quota value for this policy.
     */
    public java.lang.Long getDnsAQuota() {
        return dnsAQuota;
    }


    /**
     * Sets the dnsAQuota value for this DnsProtectionProfile.
     * 
     * @param dnsAQuota   * dns a quota value for this policy.
     */
    public void setDnsAQuota(java.lang.Long dnsAQuota) {
        this.dnsAQuota = dnsAQuota;
    }


    /**
     * Gets the dnsMxQuota value for this DnsProtectionProfile.
     * 
     * @return dnsMxQuota   * dns mx quota value for this policy.
     */
    public java.lang.Long getDnsMxQuota() {
        return dnsMxQuota;
    }


    /**
     * Sets the dnsMxQuota value for this DnsProtectionProfile.
     * 
     * @param dnsMxQuota   * dns mx quota value for this policy.
     */
    public void setDnsMxQuota(java.lang.Long dnsMxQuota) {
        this.dnsMxQuota = dnsMxQuota;
    }


    /**
     * Gets the dnsPtrQuota value for this DnsProtectionProfile.
     * 
     * @return dnsPtrQuota   * dns ptr quota value for this policy.
     */
    public java.lang.Long getDnsPtrQuota() {
        return dnsPtrQuota;
    }


    /**
     * Sets the dnsPtrQuota value for this DnsProtectionProfile.
     * 
     * @param dnsPtrQuota   * dns ptr quota value for this policy.
     */
    public void setDnsPtrQuota(java.lang.Long dnsPtrQuota) {
        this.dnsPtrQuota = dnsPtrQuota;
    }


    /**
     * Gets the dnsAaaaQuota value for this DnsProtectionProfile.
     * 
     * @return dnsAaaaQuota   * dns aaaa quota value for this policy.
     */
    public java.lang.Long getDnsAaaaQuota() {
        return dnsAaaaQuota;
    }


    /**
     * Sets the dnsAaaaQuota value for this DnsProtectionProfile.
     * 
     * @param dnsAaaaQuota   * dns aaaa quota value for this policy.
     */
    public void setDnsAaaaQuota(java.lang.Long dnsAaaaQuota) {
        this.dnsAaaaQuota = dnsAaaaQuota;
    }


    /**
     * Gets the dnsTextQuota value for this DnsProtectionProfile.
     * 
     * @return dnsTextQuota   * dns text quota value for this policy.
     */
    public java.lang.Long getDnsTextQuota() {
        return dnsTextQuota;
    }


    /**
     * Sets the dnsTextQuota value for this DnsProtectionProfile.
     * 
     * @param dnsTextQuota   * dns text quota value for this policy.
     */
    public void setDnsTextQuota(java.lang.Long dnsTextQuota) {
        this.dnsTextQuota = dnsTextQuota;
    }


    /**
     * Gets the dnsSoaQuota value for this DnsProtectionProfile.
     * 
     * @return dnsSoaQuota   * dns soa quota value for this policy.
     */
    public java.lang.Long getDnsSoaQuota() {
        return dnsSoaQuota;
    }


    /**
     * Sets the dnsSoaQuota value for this DnsProtectionProfile.
     * 
     * @param dnsSoaQuota   * dns soa quota value for this policy.
     */
    public void setDnsSoaQuota(java.lang.Long dnsSoaQuota) {
        this.dnsSoaQuota = dnsSoaQuota;
    }


    /**
     * Gets the dnsNaptrQuota value for this DnsProtectionProfile.
     * 
     * @return dnsNaptrQuota   * dns naptr quota value for this policy.
     */
    public java.lang.Long getDnsNaptrQuota() {
        return dnsNaptrQuota;
    }


    /**
     * Sets the dnsNaptrQuota value for this DnsProtectionProfile.
     * 
     * @param dnsNaptrQuota   * dns naptr quota value for this policy.
     */
    public void setDnsNaptrQuota(java.lang.Long dnsNaptrQuota) {
        this.dnsNaptrQuota = dnsNaptrQuota;
    }


    /**
     * Gets the dnsSrvQuota value for this DnsProtectionProfile.
     * 
     * @return dnsSrvQuota   * dns srv quota value for this policy.
     */
    public java.lang.Long getDnsSrvQuota() {
        return dnsSrvQuota;
    }


    /**
     * Sets the dnsSrvQuota value for this DnsProtectionProfile.
     * 
     * @param dnsSrvQuota   * dns srv quota value for this policy.
     */
    public void setDnsSrvQuota(java.lang.Long dnsSrvQuota) {
        this.dnsSrvQuota = dnsSrvQuota;
    }


    /**
     * Gets the dnsOtherQuota value for this DnsProtectionProfile.
     * 
     * @return dnsOtherQuota   * dns other types quota value for this policy.
     */
    public java.lang.Long getDnsOtherQuota() {
        return dnsOtherQuota;
    }


    /**
     * Sets the dnsOtherQuota value for this DnsProtectionProfile.
     * 
     * @param dnsOtherQuota   * dns other types quota value for this policy.
     */
    public void setDnsOtherQuota(java.lang.Long dnsOtherQuota) {
        this.dnsOtherQuota = dnsOtherQuota;
    }


    /**
     * Gets the action value for this DnsProtectionProfile.
     * 
     * @return action   * This variable indicates the policy action
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_Action getAction() {
        return action;
    }


    /**
     * Sets the action value for this DnsProtectionProfile.
     * 
     * @param action   * This variable indicates the policy action
     */
    public void setAction(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_Action action) {
        this.action = action;
    }


    /**
     * Gets the maxAllowedQPS value for this DnsProtectionProfile.
     * 
     * @return maxAllowedQPS   * The maximum number of queries per sec per dns type that this
     * profile will forward in collective mode mitigation
     */
    public java.lang.Long getMaxAllowedQPS() {
        return maxAllowedQPS;
    }


    /**
     * Sets the maxAllowedQPS value for this DnsProtectionProfile.
     * 
     * @param maxAllowedQPS   * The maximum number of queries per sec per dns type that this
     * profile will forward in collective mode mitigation
     */
    public void setMaxAllowedQPS(java.lang.Long maxAllowedQPS) {
        this.maxAllowedQPS = maxAllowedQPS;
    }


    /**
     * Gets the sigRateLimit value for this DnsProtectionProfile.
     * 
     * @return sigRateLimit   * The Signature rate limit mitigation target, as a percentage
     * of the learned traffic.
     */
    public java.lang.Long getSigRateLimit() {
        return sigRateLimit;
    }


    /**
     * Sets the sigRateLimit value for this DnsProtectionProfile.
     * 
     * @param sigRateLimit   * The Signature rate limit mitigation target, as a percentage
     * of the learned traffic.
     */
    public void setSigRateLimit(java.lang.Long sigRateLimit) {
        this.sigRateLimit = sigRateLimit;
    }


    /**
     * Gets the manTrigStatus value for this DnsProtectionProfile.
     * 
     * @return manTrigStatus   * Whether to activate manual triggers mode for this profile.
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_ManTrigStatus getManTrigStatus() {
        return manTrigStatus;
    }


    /**
     * Sets the manTrigStatus value for this DnsProtectionProfile.
     * 
     * @param manTrigStatus   * Whether to activate manual triggers mode for this profile.
     */
    public void setManTrigStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_ManTrigStatus manTrigStatus) {
        this.manTrigStatus = manTrigStatus;
    }


    /**
     * Gets the manTrigActivationThreshold value for this DnsProtectionProfile.
     * 
     * @return manTrigActivationThreshold   * Number of QPS for starting the manual triggers mitigation.
     */
    public java.lang.Long getManTrigActivationThreshold() {
        return manTrigActivationThreshold;
    }


    /**
     * Sets the manTrigActivationThreshold value for this DnsProtectionProfile.
     * 
     * @param manTrigActivationThreshold   * Number of QPS for starting the manual triggers mitigation.
     */
    public void setManTrigActivationThreshold(java.lang.Long manTrigActivationThreshold) {
        this.manTrigActivationThreshold = manTrigActivationThreshold;
    }


    /**
     * Gets the manTrigTerminationThreshold value for this DnsProtectionProfile.
     * 
     * @return manTrigTerminationThreshold   * Number of QPS for terminating the manual triggers mitigation.
     */
    public java.lang.Long getManTrigTerminationThreshold() {
        return manTrigTerminationThreshold;
    }


    /**
     * Sets the manTrigTerminationThreshold value for this DnsProtectionProfile.
     * 
     * @param manTrigTerminationThreshold   * Number of QPS for terminating the manual triggers mitigation.
     */
    public void setManTrigTerminationThreshold(java.lang.Long manTrigTerminationThreshold) {
        this.manTrigTerminationThreshold = manTrigTerminationThreshold;
    }


    /**
     * Gets the manTrigMaxQpsTarget value for this DnsProtectionProfile.
     * 
     * @return manTrigMaxQpsTarget   * The number of QPS that will be forwarded in manual triggers
     * mode.
     */
    public java.lang.Long getManTrigMaxQpsTarget() {
        return manTrigMaxQpsTarget;
    }


    /**
     * Sets the manTrigMaxQpsTarget value for this DnsProtectionProfile.
     * 
     * @param manTrigMaxQpsTarget   * The number of QPS that will be forwarded in manual triggers
     * mode.
     */
    public void setManTrigMaxQpsTarget(java.lang.Long manTrigMaxQpsTarget) {
        this.manTrigMaxQpsTarget = manTrigMaxQpsTarget;
    }


    /**
     * Gets the manTrigActivationPeriod value for this DnsProtectionProfile.
     * 
     * @return manTrigActivationPeriod   * Number of seconds above the manual triggers activation threshold
     * before starting the mitigation
     */
    public java.lang.Long getManTrigActivationPeriod() {
        return manTrigActivationPeriod;
    }


    /**
     * Sets the manTrigActivationPeriod value for this DnsProtectionProfile.
     * 
     * @param manTrigActivationPeriod   * Number of seconds above the manual triggers activation threshold
     * before starting the mitigation
     */
    public void setManTrigActivationPeriod(java.lang.Long manTrigActivationPeriod) {
        this.manTrigActivationPeriod = manTrigActivationPeriod;
    }


    /**
     * Gets the manTrigTerminationPeriod value for this DnsProtectionProfile.
     * 
     * @return manTrigTerminationPeriod   * Number of seconds below the manual triggers termination threshold
     * before stopping the mitigation
     */
    public java.lang.Long getManTrigTerminationPeriod() {
        return manTrigTerminationPeriod;
    }


    /**
     * Sets the manTrigTerminationPeriod value for this DnsProtectionProfile.
     * 
     * @param manTrigTerminationPeriod   * Number of seconds below the manual triggers termination threshold
     * before stopping the mitigation
     */
    public void setManTrigTerminationPeriod(java.lang.Long manTrigTerminationPeriod) {
        this.manTrigTerminationPeriod = manTrigTerminationPeriod;
    }


    /**
     * Gets the manTrigEscalationPeriod value for this DnsProtectionProfile.
     * 
     * @return manTrigEscalationPeriod   * Number of seconds with bad feedback before changing the mitigation
     * type in manual triggers mode
     */
    public java.lang.Long getManTrigEscalationPeriod() {
        return manTrigEscalationPeriod;
    }


    /**
     * Sets the manTrigEscalationPeriod value for this DnsProtectionProfile.
     * 
     * @param manTrigEscalationPeriod   * Number of seconds with bad feedback before changing the mitigation
     * type in manual triggers mode
     */
    public void setManTrigEscalationPeriod(java.lang.Long manTrigEscalationPeriod) {
        this.manTrigEscalationPeriod = manTrigEscalationPeriod;
    }


    /**
     * Gets the packetReport value for this DnsProtectionProfile.
     * 
     * @return packetReport   * Packet Report Status
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetReport getPacketReport() {
        return packetReport;
    }


    /**
     * Sets the packetReport value for this DnsProtectionProfile.
     * 
     * @param packetReport   * Packet Report Status
     */
    public void setPacketReport(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetReport packetReport) {
        this.packetReport = packetReport;
    }


    /**
     * Gets the packetTrace value for this DnsProtectionProfile.
     * 
     * @return packetTrace   * Packet Trace Status
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetTrace getPacketTrace() {
        return packetTrace;
    }


    /**
     * Sets the packetTrace value for this DnsProtectionProfile.
     * 
     * @param packetTrace   * Packet Trace Status
     */
    public void setPacketTrace(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetTrace packetTrace) {
        this.packetTrace = packetTrace;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DnsProtectionProfile)) return false;
        DnsProtectionProfile other = (DnsProtectionProfile) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.profileName==null && other.getProfileName()==null) || 
             (this.profileName!=null &&
              this.profileName.equals(other.getProfileName()))) &&
            ((this.dnsAFloodstatus==null && other.getDnsAFloodstatus()==null) || 
             (this.dnsAFloodstatus!=null &&
              this.dnsAFloodstatus.equals(other.getDnsAFloodstatus()))) &&
            ((this.dnsMxFloodstatus==null && other.getDnsMxFloodstatus()==null) || 
             (this.dnsMxFloodstatus!=null &&
              this.dnsMxFloodstatus.equals(other.getDnsMxFloodstatus()))) &&
            ((this.dnsPtrFloodstatus==null && other.getDnsPtrFloodstatus()==null) || 
             (this.dnsPtrFloodstatus!=null &&
              this.dnsPtrFloodstatus.equals(other.getDnsPtrFloodstatus()))) &&
            ((this.dnsAaaaFloodstatus==null && other.getDnsAaaaFloodstatus()==null) || 
             (this.dnsAaaaFloodstatus!=null &&
              this.dnsAaaaFloodstatus.equals(other.getDnsAaaaFloodstatus()))) &&
            ((this.dnsTextFloodstatus==null && other.getDnsTextFloodstatus()==null) || 
             (this.dnsTextFloodstatus!=null &&
              this.dnsTextFloodstatus.equals(other.getDnsTextFloodstatus()))) &&
            ((this.dnsSoaFloodstatus==null && other.getDnsSoaFloodstatus()==null) || 
             (this.dnsSoaFloodstatus!=null &&
              this.dnsSoaFloodstatus.equals(other.getDnsSoaFloodstatus()))) &&
            ((this.dnsNaptrFloodstatus==null && other.getDnsNaptrFloodstatus()==null) || 
             (this.dnsNaptrFloodstatus!=null &&
              this.dnsNaptrFloodstatus.equals(other.getDnsNaptrFloodstatus()))) &&
            ((this.dnsSrvFloodstatus==null && other.getDnsSrvFloodstatus()==null) || 
             (this.dnsSrvFloodstatus!=null &&
              this.dnsSrvFloodstatus.equals(other.getDnsSrvFloodstatus()))) &&
            ((this.dnsOtherFloodstatus==null && other.getDnsOtherFloodstatus()==null) || 
             (this.dnsOtherFloodstatus!=null &&
              this.dnsOtherFloodstatus.equals(other.getDnsOtherFloodstatus()))) &&
            ((this.expectedQps==null && other.getExpectedQps()==null) || 
             (this.expectedQps!=null &&
              this.expectedQps.equals(other.getExpectedQps()))) &&
            ((this.dnsAQuota==null && other.getDnsAQuota()==null) || 
             (this.dnsAQuota!=null &&
              this.dnsAQuota.equals(other.getDnsAQuota()))) &&
            ((this.dnsMxQuota==null && other.getDnsMxQuota()==null) || 
             (this.dnsMxQuota!=null &&
              this.dnsMxQuota.equals(other.getDnsMxQuota()))) &&
            ((this.dnsPtrQuota==null && other.getDnsPtrQuota()==null) || 
             (this.dnsPtrQuota!=null &&
              this.dnsPtrQuota.equals(other.getDnsPtrQuota()))) &&
            ((this.dnsAaaaQuota==null && other.getDnsAaaaQuota()==null) || 
             (this.dnsAaaaQuota!=null &&
              this.dnsAaaaQuota.equals(other.getDnsAaaaQuota()))) &&
            ((this.dnsTextQuota==null && other.getDnsTextQuota()==null) || 
             (this.dnsTextQuota!=null &&
              this.dnsTextQuota.equals(other.getDnsTextQuota()))) &&
            ((this.dnsSoaQuota==null && other.getDnsSoaQuota()==null) || 
             (this.dnsSoaQuota!=null &&
              this.dnsSoaQuota.equals(other.getDnsSoaQuota()))) &&
            ((this.dnsNaptrQuota==null && other.getDnsNaptrQuota()==null) || 
             (this.dnsNaptrQuota!=null &&
              this.dnsNaptrQuota.equals(other.getDnsNaptrQuota()))) &&
            ((this.dnsSrvQuota==null && other.getDnsSrvQuota()==null) || 
             (this.dnsSrvQuota!=null &&
              this.dnsSrvQuota.equals(other.getDnsSrvQuota()))) &&
            ((this.dnsOtherQuota==null && other.getDnsOtherQuota()==null) || 
             (this.dnsOtherQuota!=null &&
              this.dnsOtherQuota.equals(other.getDnsOtherQuota()))) &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.maxAllowedQPS==null && other.getMaxAllowedQPS()==null) || 
             (this.maxAllowedQPS!=null &&
              this.maxAllowedQPS.equals(other.getMaxAllowedQPS()))) &&
            ((this.sigRateLimit==null && other.getSigRateLimit()==null) || 
             (this.sigRateLimit!=null &&
              this.sigRateLimit.equals(other.getSigRateLimit()))) &&
            ((this.manTrigStatus==null && other.getManTrigStatus()==null) || 
             (this.manTrigStatus!=null &&
              this.manTrigStatus.equals(other.getManTrigStatus()))) &&
            ((this.manTrigActivationThreshold==null && other.getManTrigActivationThreshold()==null) || 
             (this.manTrigActivationThreshold!=null &&
              this.manTrigActivationThreshold.equals(other.getManTrigActivationThreshold()))) &&
            ((this.manTrigTerminationThreshold==null && other.getManTrigTerminationThreshold()==null) || 
             (this.manTrigTerminationThreshold!=null &&
              this.manTrigTerminationThreshold.equals(other.getManTrigTerminationThreshold()))) &&
            ((this.manTrigMaxQpsTarget==null && other.getManTrigMaxQpsTarget()==null) || 
             (this.manTrigMaxQpsTarget!=null &&
              this.manTrigMaxQpsTarget.equals(other.getManTrigMaxQpsTarget()))) &&
            ((this.manTrigActivationPeriod==null && other.getManTrigActivationPeriod()==null) || 
             (this.manTrigActivationPeriod!=null &&
              this.manTrigActivationPeriod.equals(other.getManTrigActivationPeriod()))) &&
            ((this.manTrigTerminationPeriod==null && other.getManTrigTerminationPeriod()==null) || 
             (this.manTrigTerminationPeriod!=null &&
              this.manTrigTerminationPeriod.equals(other.getManTrigTerminationPeriod()))) &&
            ((this.manTrigEscalationPeriod==null && other.getManTrigEscalationPeriod()==null) || 
             (this.manTrigEscalationPeriod!=null &&
              this.manTrigEscalationPeriod.equals(other.getManTrigEscalationPeriod()))) &&
            ((this.packetReport==null && other.getPacketReport()==null) || 
             (this.packetReport!=null &&
              this.packetReport.equals(other.getPacketReport()))) &&
            ((this.packetTrace==null && other.getPacketTrace()==null) || 
             (this.packetTrace!=null &&
              this.packetTrace.equals(other.getPacketTrace())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getProfileName() != null) {
            _hashCode += getProfileName().hashCode();
        }
        if (getDnsAFloodstatus() != null) {
            _hashCode += getDnsAFloodstatus().hashCode();
        }
        if (getDnsMxFloodstatus() != null) {
            _hashCode += getDnsMxFloodstatus().hashCode();
        }
        if (getDnsPtrFloodstatus() != null) {
            _hashCode += getDnsPtrFloodstatus().hashCode();
        }
        if (getDnsAaaaFloodstatus() != null) {
            _hashCode += getDnsAaaaFloodstatus().hashCode();
        }
        if (getDnsTextFloodstatus() != null) {
            _hashCode += getDnsTextFloodstatus().hashCode();
        }
        if (getDnsSoaFloodstatus() != null) {
            _hashCode += getDnsSoaFloodstatus().hashCode();
        }
        if (getDnsNaptrFloodstatus() != null) {
            _hashCode += getDnsNaptrFloodstatus().hashCode();
        }
        if (getDnsSrvFloodstatus() != null) {
            _hashCode += getDnsSrvFloodstatus().hashCode();
        }
        if (getDnsOtherFloodstatus() != null) {
            _hashCode += getDnsOtherFloodstatus().hashCode();
        }
        if (getExpectedQps() != null) {
            _hashCode += getExpectedQps().hashCode();
        }
        if (getDnsAQuota() != null) {
            _hashCode += getDnsAQuota().hashCode();
        }
        if (getDnsMxQuota() != null) {
            _hashCode += getDnsMxQuota().hashCode();
        }
        if (getDnsPtrQuota() != null) {
            _hashCode += getDnsPtrQuota().hashCode();
        }
        if (getDnsAaaaQuota() != null) {
            _hashCode += getDnsAaaaQuota().hashCode();
        }
        if (getDnsTextQuota() != null) {
            _hashCode += getDnsTextQuota().hashCode();
        }
        if (getDnsSoaQuota() != null) {
            _hashCode += getDnsSoaQuota().hashCode();
        }
        if (getDnsNaptrQuota() != null) {
            _hashCode += getDnsNaptrQuota().hashCode();
        }
        if (getDnsSrvQuota() != null) {
            _hashCode += getDnsSrvQuota().hashCode();
        }
        if (getDnsOtherQuota() != null) {
            _hashCode += getDnsOtherQuota().hashCode();
        }
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getMaxAllowedQPS() != null) {
            _hashCode += getMaxAllowedQPS().hashCode();
        }
        if (getSigRateLimit() != null) {
            _hashCode += getSigRateLimit().hashCode();
        }
        if (getManTrigStatus() != null) {
            _hashCode += getManTrigStatus().hashCode();
        }
        if (getManTrigActivationThreshold() != null) {
            _hashCode += getManTrigActivationThreshold().hashCode();
        }
        if (getManTrigTerminationThreshold() != null) {
            _hashCode += getManTrigTerminationThreshold().hashCode();
        }
        if (getManTrigMaxQpsTarget() != null) {
            _hashCode += getManTrigMaxQpsTarget().hashCode();
        }
        if (getManTrigActivationPeriod() != null) {
            _hashCode += getManTrigActivationPeriod().hashCode();
        }
        if (getManTrigTerminationPeriod() != null) {
            _hashCode += getManTrigTerminationPeriod().hashCode();
        }
        if (getManTrigEscalationPeriod() != null) {
            _hashCode += getManTrigEscalationPeriod().hashCode();
        }
        if (getPacketReport() != null) {
            _hashCode += getPacketReport().hashCode();
        }
        if (getPacketTrace() != null) {
            _hashCode += getPacketTrace().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DnsProtectionProfile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsAFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsAFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsAFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsMxFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsMxFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsMxFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsPtrFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsPtrFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsPtrFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsAaaaFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsAaaaFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsAaaaFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsTextFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsTextFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsTextFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsSoaFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsSoaFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsSoaFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsNaptrFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsNaptrFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsNaptrFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsSrvFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsSrvFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsSrvFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsOtherFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsOtherFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_DnsOtherFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("expectedQps");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExpectedQps"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsAQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsAQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsMxQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsMxQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsPtrQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsPtrQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsAaaaQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsAaaaQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsTextQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsTextQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsSoaQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsSoaQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsNaptrQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsNaptrQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsSrvQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsSrvQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dnsOtherQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DnsOtherQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Action"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_Action"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("maxAllowedQPS");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MaxAllowedQPS"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sigRateLimit");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SigRateLimit"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_ManTrigStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigActivationThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigActivationThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigTerminationThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigTerminationThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigMaxQpsTarget");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigMaxQpsTarget"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigActivationPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigActivationPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigTerminationPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigTerminationPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("manTrigEscalationPeriod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ManTrigEscalationPeriod"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packetReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_packetReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetTrace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packetTrace"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionProfile_packetTrace"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
