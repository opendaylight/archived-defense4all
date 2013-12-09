/**
 * Profiles.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;


/**
 * This structure describes the parameters of a Profiles
 */
public class Profiles  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* This variable indicates whether to check tcp protocol in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNFloodstatus SYNFloodstatus;

    /* This variable indicates whether to check udp protocol in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_UDPFloodstatus UDPFloodstatus;

    /* This variable indicates whether to check igmp protocol in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_IGMPFloodstatus IGMPFloodstatus;

    /* This variable indicates whether to check icmp protocol in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_ICMPFloodstatus ICMPFloodstatus;

    /* This variable indicates whether to check tcp fin + ack in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FINACKFloodstatus FINACKFloodstatus;

    /* This variable indicates whether to check tcp reset in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_RSTFloodstatus RSTFloodstatus;

    /* This variable indicates whether to check tcp syn + ack in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNACKFloodstatus SYNACKFloodstatus;

    /* This variable indicates whether to check tcp fragment in this
     * policy. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FRAGFloodstatus FRAGFloodstatus;

    /* network flood bandwidth inbound initial value (in Mgbit) for
     * this policy. */
    private java.lang.Long bandwidthIn;

    /* network flood bandwidth outbound initial value (in Mgbit) for
     * this policy. */
    private java.lang.Long bandwidthOut;

    /* Tcp quota inbound value for this policy. */
    private java.lang.Long tcpInQuota;

    /* Udp quota inbound value for this policy. */
    private java.lang.Long udpInQuota;

    /* Icmp quota inbound value for this policy. */
    private java.lang.Long icmpInQuota;

    /* Igmp quota inbound value for this policy. */
    private java.lang.Long igmpInQuota;

    /* Tcp quota outbound value for this policy. */
    private java.lang.Long tcpOutQuota;

    /* Udp quota outbound value for this policy. */
    private java.lang.Long udpOutQuota;

    /* Icmp quota outbound value for this policy. */
    private java.lang.Long icmpOutQuota;

    /* Igmp quota outbound value for this policy. */
    private java.lang.Long igmpOutQuota;

    /* Transparent Closed feedback Optimization. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_TransparentOptimization transparentOptimization;

    /* Packet Report Status */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetReport packetReport;

    /* Packet Trace Status */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetTrace packetTrace;

    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_profileAction profileAction;

    private java.lang.Long simulationDuration;

    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_stopSimulation stopSimulation;

    private java.lang.Long stabilizationTime;

    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_startSimulation startSimulation;

    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_jointDist jointDist;

    public Profiles() {
    }

    public Profiles(
           java.lang.String profileName,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNFloodstatus SYNFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_UDPFloodstatus UDPFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_IGMPFloodstatus IGMPFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_ICMPFloodstatus ICMPFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FINACKFloodstatus FINACKFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_RSTFloodstatus RSTFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNACKFloodstatus SYNACKFloodstatus,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FRAGFloodstatus FRAGFloodstatus,
           java.lang.Long bandwidthIn,
           java.lang.Long bandwidthOut,
           java.lang.Long tcpInQuota,
           java.lang.Long udpInQuota,
           java.lang.Long icmpInQuota,
           java.lang.Long igmpInQuota,
           java.lang.Long tcpOutQuota,
           java.lang.Long udpOutQuota,
           java.lang.Long icmpOutQuota,
           java.lang.Long igmpOutQuota,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_TransparentOptimization transparentOptimization,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetReport packetReport,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetTrace packetTrace,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_profileAction profileAction,
           java.lang.Long simulationDuration,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_stopSimulation stopSimulation,
           java.lang.Long stabilizationTime,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_startSimulation startSimulation,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_jointDist jointDist) {
           this.profileName = profileName;
           this.SYNFloodstatus = SYNFloodstatus;
           this.UDPFloodstatus = UDPFloodstatus;
           this.IGMPFloodstatus = IGMPFloodstatus;
           this.ICMPFloodstatus = ICMPFloodstatus;
           this.FINACKFloodstatus = FINACKFloodstatus;
           this.RSTFloodstatus = RSTFloodstatus;
           this.SYNACKFloodstatus = SYNACKFloodstatus;
           this.FRAGFloodstatus = FRAGFloodstatus;
           this.bandwidthIn = bandwidthIn;
           this.bandwidthOut = bandwidthOut;
           this.tcpInQuota = tcpInQuota;
           this.udpInQuota = udpInQuota;
           this.icmpInQuota = icmpInQuota;
           this.igmpInQuota = igmpInQuota;
           this.tcpOutQuota = tcpOutQuota;
           this.udpOutQuota = udpOutQuota;
           this.icmpOutQuota = icmpOutQuota;
           this.igmpOutQuota = igmpOutQuota;
           this.transparentOptimization = transparentOptimization;
           this.packetReport = packetReport;
           this.packetTrace = packetTrace;
           this.profileAction = profileAction;
           this.simulationDuration = simulationDuration;
           this.stopSimulation = stopSimulation;
           this.stabilizationTime = stabilizationTime;
           this.startSimulation = startSimulation;
           this.jointDist = jointDist;
    }


    /**
     * Gets the profileName value for this Profiles.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this Profiles.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the SYNFloodstatus value for this Profiles.
     * 
     * @return SYNFloodstatus   * This variable indicates whether to check tcp protocol in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNFloodstatus getSYNFloodstatus() {
        return SYNFloodstatus;
    }


    /**
     * Sets the SYNFloodstatus value for this Profiles.
     * 
     * @param SYNFloodstatus   * This variable indicates whether to check tcp protocol in this
     * policy.
     */
    public void setSYNFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNFloodstatus SYNFloodstatus) {
        this.SYNFloodstatus = SYNFloodstatus;
    }


    /**
     * Gets the UDPFloodstatus value for this Profiles.
     * 
     * @return UDPFloodstatus   * This variable indicates whether to check udp protocol in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_UDPFloodstatus getUDPFloodstatus() {
        return UDPFloodstatus;
    }


    /**
     * Sets the UDPFloodstatus value for this Profiles.
     * 
     * @param UDPFloodstatus   * This variable indicates whether to check udp protocol in this
     * policy.
     */
    public void setUDPFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_UDPFloodstatus UDPFloodstatus) {
        this.UDPFloodstatus = UDPFloodstatus;
    }


    /**
     * Gets the IGMPFloodstatus value for this Profiles.
     * 
     * @return IGMPFloodstatus   * This variable indicates whether to check igmp protocol in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_IGMPFloodstatus getIGMPFloodstatus() {
        return IGMPFloodstatus;
    }


    /**
     * Sets the IGMPFloodstatus value for this Profiles.
     * 
     * @param IGMPFloodstatus   * This variable indicates whether to check igmp protocol in this
     * policy.
     */
    public void setIGMPFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_IGMPFloodstatus IGMPFloodstatus) {
        this.IGMPFloodstatus = IGMPFloodstatus;
    }


    /**
     * Gets the ICMPFloodstatus value for this Profiles.
     * 
     * @return ICMPFloodstatus   * This variable indicates whether to check icmp protocol in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_ICMPFloodstatus getICMPFloodstatus() {
        return ICMPFloodstatus;
    }


    /**
     * Sets the ICMPFloodstatus value for this Profiles.
     * 
     * @param ICMPFloodstatus   * This variable indicates whether to check icmp protocol in this
     * policy.
     */
    public void setICMPFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_ICMPFloodstatus ICMPFloodstatus) {
        this.ICMPFloodstatus = ICMPFloodstatus;
    }


    /**
     * Gets the FINACKFloodstatus value for this Profiles.
     * 
     * @return FINACKFloodstatus   * This variable indicates whether to check tcp fin + ack in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FINACKFloodstatus getFINACKFloodstatus() {
        return FINACKFloodstatus;
    }


    /**
     * Sets the FINACKFloodstatus value for this Profiles.
     * 
     * @param FINACKFloodstatus   * This variable indicates whether to check tcp fin + ack in this
     * policy.
     */
    public void setFINACKFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FINACKFloodstatus FINACKFloodstatus) {
        this.FINACKFloodstatus = FINACKFloodstatus;
    }


    /**
     * Gets the RSTFloodstatus value for this Profiles.
     * 
     * @return RSTFloodstatus   * This variable indicates whether to check tcp reset in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_RSTFloodstatus getRSTFloodstatus() {
        return RSTFloodstatus;
    }


    /**
     * Sets the RSTFloodstatus value for this Profiles.
     * 
     * @param RSTFloodstatus   * This variable indicates whether to check tcp reset in this
     * policy.
     */
    public void setRSTFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_RSTFloodstatus RSTFloodstatus) {
        this.RSTFloodstatus = RSTFloodstatus;
    }


    /**
     * Gets the SYNACKFloodstatus value for this Profiles.
     * 
     * @return SYNACKFloodstatus   * This variable indicates whether to check tcp syn + ack in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNACKFloodstatus getSYNACKFloodstatus() {
        return SYNACKFloodstatus;
    }


    /**
     * Sets the SYNACKFloodstatus value for this Profiles.
     * 
     * @param SYNACKFloodstatus   * This variable indicates whether to check tcp syn + ack in this
     * policy.
     */
    public void setSYNACKFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNACKFloodstatus SYNACKFloodstatus) {
        this.SYNACKFloodstatus = SYNACKFloodstatus;
    }


    /**
     * Gets the FRAGFloodstatus value for this Profiles.
     * 
     * @return FRAGFloodstatus   * This variable indicates whether to check tcp fragment in this
     * policy.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FRAGFloodstatus getFRAGFloodstatus() {
        return FRAGFloodstatus;
    }


    /**
     * Sets the FRAGFloodstatus value for this Profiles.
     * 
     * @param FRAGFloodstatus   * This variable indicates whether to check tcp fragment in this
     * policy.
     */
    public void setFRAGFloodstatus(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FRAGFloodstatus FRAGFloodstatus) {
        this.FRAGFloodstatus = FRAGFloodstatus;
    }


    /**
     * Gets the bandwidthIn value for this Profiles.
     * 
     * @return bandwidthIn   * network flood bandwidth inbound initial value (in Mgbit) for
     * this policy.
     */
    public java.lang.Long getBandwidthIn() {
        return bandwidthIn;
    }


    /**
     * Sets the bandwidthIn value for this Profiles.
     * 
     * @param bandwidthIn   * network flood bandwidth inbound initial value (in Mgbit) for
     * this policy.
     */
    public void setBandwidthIn(java.lang.Long bandwidthIn) {
        this.bandwidthIn = bandwidthIn;
    }


    /**
     * Gets the bandwidthOut value for this Profiles.
     * 
     * @return bandwidthOut   * network flood bandwidth outbound initial value (in Mgbit) for
     * this policy.
     */
    public java.lang.Long getBandwidthOut() {
        return bandwidthOut;
    }


    /**
     * Sets the bandwidthOut value for this Profiles.
     * 
     * @param bandwidthOut   * network flood bandwidth outbound initial value (in Mgbit) for
     * this policy.
     */
    public void setBandwidthOut(java.lang.Long bandwidthOut) {
        this.bandwidthOut = bandwidthOut;
    }


    /**
     * Gets the tcpInQuota value for this Profiles.
     * 
     * @return tcpInQuota   * Tcp quota inbound value for this policy.
     */
    public java.lang.Long getTcpInQuota() {
        return tcpInQuota;
    }


    /**
     * Sets the tcpInQuota value for this Profiles.
     * 
     * @param tcpInQuota   * Tcp quota inbound value for this policy.
     */
    public void setTcpInQuota(java.lang.Long tcpInQuota) {
        this.tcpInQuota = tcpInQuota;
    }


    /**
     * Gets the udpInQuota value for this Profiles.
     * 
     * @return udpInQuota   * Udp quota inbound value for this policy.
     */
    public java.lang.Long getUdpInQuota() {
        return udpInQuota;
    }


    /**
     * Sets the udpInQuota value for this Profiles.
     * 
     * @param udpInQuota   * Udp quota inbound value for this policy.
     */
    public void setUdpInQuota(java.lang.Long udpInQuota) {
        this.udpInQuota = udpInQuota;
    }


    /**
     * Gets the icmpInQuota value for this Profiles.
     * 
     * @return icmpInQuota   * Icmp quota inbound value for this policy.
     */
    public java.lang.Long getIcmpInQuota() {
        return icmpInQuota;
    }


    /**
     * Sets the icmpInQuota value for this Profiles.
     * 
     * @param icmpInQuota   * Icmp quota inbound value for this policy.
     */
    public void setIcmpInQuota(java.lang.Long icmpInQuota) {
        this.icmpInQuota = icmpInQuota;
    }


    /**
     * Gets the igmpInQuota value for this Profiles.
     * 
     * @return igmpInQuota   * Igmp quota inbound value for this policy.
     */
    public java.lang.Long getIgmpInQuota() {
        return igmpInQuota;
    }


    /**
     * Sets the igmpInQuota value for this Profiles.
     * 
     * @param igmpInQuota   * Igmp quota inbound value for this policy.
     */
    public void setIgmpInQuota(java.lang.Long igmpInQuota) {
        this.igmpInQuota = igmpInQuota;
    }


    /**
     * Gets the tcpOutQuota value for this Profiles.
     * 
     * @return tcpOutQuota   * Tcp quota outbound value for this policy.
     */
    public java.lang.Long getTcpOutQuota() {
        return tcpOutQuota;
    }


    /**
     * Sets the tcpOutQuota value for this Profiles.
     * 
     * @param tcpOutQuota   * Tcp quota outbound value for this policy.
     */
    public void setTcpOutQuota(java.lang.Long tcpOutQuota) {
        this.tcpOutQuota = tcpOutQuota;
    }


    /**
     * Gets the udpOutQuota value for this Profiles.
     * 
     * @return udpOutQuota   * Udp quota outbound value for this policy.
     */
    public java.lang.Long getUdpOutQuota() {
        return udpOutQuota;
    }


    /**
     * Sets the udpOutQuota value for this Profiles.
     * 
     * @param udpOutQuota   * Udp quota outbound value for this policy.
     */
    public void setUdpOutQuota(java.lang.Long udpOutQuota) {
        this.udpOutQuota = udpOutQuota;
    }


    /**
     * Gets the icmpOutQuota value for this Profiles.
     * 
     * @return icmpOutQuota   * Icmp quota outbound value for this policy.
     */
    public java.lang.Long getIcmpOutQuota() {
        return icmpOutQuota;
    }


    /**
     * Sets the icmpOutQuota value for this Profiles.
     * 
     * @param icmpOutQuota   * Icmp quota outbound value for this policy.
     */
    public void setIcmpOutQuota(java.lang.Long icmpOutQuota) {
        this.icmpOutQuota = icmpOutQuota;
    }


    /**
     * Gets the igmpOutQuota value for this Profiles.
     * 
     * @return igmpOutQuota   * Igmp quota outbound value for this policy.
     */
    public java.lang.Long getIgmpOutQuota() {
        return igmpOutQuota;
    }


    /**
     * Sets the igmpOutQuota value for this Profiles.
     * 
     * @param igmpOutQuota   * Igmp quota outbound value for this policy.
     */
    public void setIgmpOutQuota(java.lang.Long igmpOutQuota) {
        this.igmpOutQuota = igmpOutQuota;
    }


    /**
     * Gets the transparentOptimization value for this Profiles.
     * 
     * @return transparentOptimization   * Transparent Closed feedback Optimization.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_TransparentOptimization getTransparentOptimization() {
        return transparentOptimization;
    }


    /**
     * Sets the transparentOptimization value for this Profiles.
     * 
     * @param transparentOptimization   * Transparent Closed feedback Optimization.
     */
    public void setTransparentOptimization(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_TransparentOptimization transparentOptimization) {
        this.transparentOptimization = transparentOptimization;
    }


    /**
     * Gets the packetReport value for this Profiles.
     * 
     * @return packetReport   * Packet Report Status
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetReport getPacketReport() {
        return packetReport;
    }


    /**
     * Sets the packetReport value for this Profiles.
     * 
     * @param packetReport   * Packet Report Status
     */
    public void setPacketReport(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetReport packetReport) {
        this.packetReport = packetReport;
    }


    /**
     * Gets the packetTrace value for this Profiles.
     * 
     * @return packetTrace   * Packet Trace Status
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetTrace getPacketTrace() {
        return packetTrace;
    }


    /**
     * Sets the packetTrace value for this Profiles.
     * 
     * @param packetTrace   * Packet Trace Status
     */
    public void setPacketTrace(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_packetTrace packetTrace) {
        this.packetTrace = packetTrace;
    }


    /**
     * Gets the profileAction value for this Profiles.
     * 
     * @return profileAction
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_profileAction getProfileAction() {
        return profileAction;
    }


    /**
     * Sets the profileAction value for this Profiles.
     * 
     * @param profileAction
     */
    public void setProfileAction(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_profileAction profileAction) {
        this.profileAction = profileAction;
    }


    /**
     * Gets the simulationDuration value for this Profiles.
     * 
     * @return simulationDuration
     */
    public java.lang.Long getSimulationDuration() {
        return simulationDuration;
    }


    /**
     * Sets the simulationDuration value for this Profiles.
     * 
     * @param simulationDuration
     */
    public void setSimulationDuration(java.lang.Long simulationDuration) {
        this.simulationDuration = simulationDuration;
    }


    /**
     * Gets the stopSimulation value for this Profiles.
     * 
     * @return stopSimulation
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_stopSimulation getStopSimulation() {
        return stopSimulation;
    }


    /**
     * Sets the stopSimulation value for this Profiles.
     * 
     * @param stopSimulation
     */
    public void setStopSimulation(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_stopSimulation stopSimulation) {
        this.stopSimulation = stopSimulation;
    }


    /**
     * Gets the stabilizationTime value for this Profiles.
     * 
     * @return stabilizationTime
     */
    public java.lang.Long getStabilizationTime() {
        return stabilizationTime;
    }


    /**
     * Sets the stabilizationTime value for this Profiles.
     * 
     * @param stabilizationTime
     */
    public void setStabilizationTime(java.lang.Long stabilizationTime) {
        this.stabilizationTime = stabilizationTime;
    }


    /**
     * Gets the startSimulation value for this Profiles.
     * 
     * @return startSimulation
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_startSimulation getStartSimulation() {
        return startSimulation;
    }


    /**
     * Sets the startSimulation value for this Profiles.
     * 
     * @param startSimulation
     */
    public void setStartSimulation(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_startSimulation startSimulation) {
        this.startSimulation = startSimulation;
    }


    /**
     * Gets the jointDist value for this Profiles.
     * 
     * @return jointDist
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_jointDist getJointDist() {
        return jointDist;
    }


    /**
     * Sets the jointDist value for this Profiles.
     * 
     * @param jointDist
     */
    public void setJointDist(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_jointDist jointDist) {
        this.jointDist = jointDist;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Profiles)) return false;
        Profiles other = (Profiles) obj;
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
            ((this.SYNFloodstatus==null && other.getSYNFloodstatus()==null) || 
             (this.SYNFloodstatus!=null &&
              this.SYNFloodstatus.equals(other.getSYNFloodstatus()))) &&
            ((this.UDPFloodstatus==null && other.getUDPFloodstatus()==null) || 
             (this.UDPFloodstatus!=null &&
              this.UDPFloodstatus.equals(other.getUDPFloodstatus()))) &&
            ((this.IGMPFloodstatus==null && other.getIGMPFloodstatus()==null) || 
             (this.IGMPFloodstatus!=null &&
              this.IGMPFloodstatus.equals(other.getIGMPFloodstatus()))) &&
            ((this.ICMPFloodstatus==null && other.getICMPFloodstatus()==null) || 
             (this.ICMPFloodstatus!=null &&
              this.ICMPFloodstatus.equals(other.getICMPFloodstatus()))) &&
            ((this.FINACKFloodstatus==null && other.getFINACKFloodstatus()==null) || 
             (this.FINACKFloodstatus!=null &&
              this.FINACKFloodstatus.equals(other.getFINACKFloodstatus()))) &&
            ((this.RSTFloodstatus==null && other.getRSTFloodstatus()==null) || 
             (this.RSTFloodstatus!=null &&
              this.RSTFloodstatus.equals(other.getRSTFloodstatus()))) &&
            ((this.SYNACKFloodstatus==null && other.getSYNACKFloodstatus()==null) || 
             (this.SYNACKFloodstatus!=null &&
              this.SYNACKFloodstatus.equals(other.getSYNACKFloodstatus()))) &&
            ((this.FRAGFloodstatus==null && other.getFRAGFloodstatus()==null) || 
             (this.FRAGFloodstatus!=null &&
              this.FRAGFloodstatus.equals(other.getFRAGFloodstatus()))) &&
            ((this.bandwidthIn==null && other.getBandwidthIn()==null) || 
             (this.bandwidthIn!=null &&
              this.bandwidthIn.equals(other.getBandwidthIn()))) &&
            ((this.bandwidthOut==null && other.getBandwidthOut()==null) || 
             (this.bandwidthOut!=null &&
              this.bandwidthOut.equals(other.getBandwidthOut()))) &&
            ((this.tcpInQuota==null && other.getTcpInQuota()==null) || 
             (this.tcpInQuota!=null &&
              this.tcpInQuota.equals(other.getTcpInQuota()))) &&
            ((this.udpInQuota==null && other.getUdpInQuota()==null) || 
             (this.udpInQuota!=null &&
              this.udpInQuota.equals(other.getUdpInQuota()))) &&
            ((this.icmpInQuota==null && other.getIcmpInQuota()==null) || 
             (this.icmpInQuota!=null &&
              this.icmpInQuota.equals(other.getIcmpInQuota()))) &&
            ((this.igmpInQuota==null && other.getIgmpInQuota()==null) || 
             (this.igmpInQuota!=null &&
              this.igmpInQuota.equals(other.getIgmpInQuota()))) &&
            ((this.tcpOutQuota==null && other.getTcpOutQuota()==null) || 
             (this.tcpOutQuota!=null &&
              this.tcpOutQuota.equals(other.getTcpOutQuota()))) &&
            ((this.udpOutQuota==null && other.getUdpOutQuota()==null) || 
             (this.udpOutQuota!=null &&
              this.udpOutQuota.equals(other.getUdpOutQuota()))) &&
            ((this.icmpOutQuota==null && other.getIcmpOutQuota()==null) || 
             (this.icmpOutQuota!=null &&
              this.icmpOutQuota.equals(other.getIcmpOutQuota()))) &&
            ((this.igmpOutQuota==null && other.getIgmpOutQuota()==null) || 
             (this.igmpOutQuota!=null &&
              this.igmpOutQuota.equals(other.getIgmpOutQuota()))) &&
            ((this.transparentOptimization==null && other.getTransparentOptimization()==null) || 
             (this.transparentOptimization!=null &&
              this.transparentOptimization.equals(other.getTransparentOptimization()))) &&
            ((this.packetReport==null && other.getPacketReport()==null) || 
             (this.packetReport!=null &&
              this.packetReport.equals(other.getPacketReport()))) &&
            ((this.packetTrace==null && other.getPacketTrace()==null) || 
             (this.packetTrace!=null &&
              this.packetTrace.equals(other.getPacketTrace()))) &&
            ((this.profileAction==null && other.getProfileAction()==null) || 
             (this.profileAction!=null &&
              this.profileAction.equals(other.getProfileAction()))) &&
            ((this.simulationDuration==null && other.getSimulationDuration()==null) || 
             (this.simulationDuration!=null &&
              this.simulationDuration.equals(other.getSimulationDuration()))) &&
            ((this.stopSimulation==null && other.getStopSimulation()==null) || 
             (this.stopSimulation!=null &&
              this.stopSimulation.equals(other.getStopSimulation()))) &&
            ((this.stabilizationTime==null && other.getStabilizationTime()==null) || 
             (this.stabilizationTime!=null &&
              this.stabilizationTime.equals(other.getStabilizationTime()))) &&
            ((this.startSimulation==null && other.getStartSimulation()==null) || 
             (this.startSimulation!=null &&
              this.startSimulation.equals(other.getStartSimulation()))) &&
            ((this.jointDist==null && other.getJointDist()==null) || 
             (this.jointDist!=null &&
              this.jointDist.equals(other.getJointDist())));
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
        if (getSYNFloodstatus() != null) {
            _hashCode += getSYNFloodstatus().hashCode();
        }
        if (getUDPFloodstatus() != null) {
            _hashCode += getUDPFloodstatus().hashCode();
        }
        if (getIGMPFloodstatus() != null) {
            _hashCode += getIGMPFloodstatus().hashCode();
        }
        if (getICMPFloodstatus() != null) {
            _hashCode += getICMPFloodstatus().hashCode();
        }
        if (getFINACKFloodstatus() != null) {
            _hashCode += getFINACKFloodstatus().hashCode();
        }
        if (getRSTFloodstatus() != null) {
            _hashCode += getRSTFloodstatus().hashCode();
        }
        if (getSYNACKFloodstatus() != null) {
            _hashCode += getSYNACKFloodstatus().hashCode();
        }
        if (getFRAGFloodstatus() != null) {
            _hashCode += getFRAGFloodstatus().hashCode();
        }
        if (getBandwidthIn() != null) {
            _hashCode += getBandwidthIn().hashCode();
        }
        if (getBandwidthOut() != null) {
            _hashCode += getBandwidthOut().hashCode();
        }
        if (getTcpInQuota() != null) {
            _hashCode += getTcpInQuota().hashCode();
        }
        if (getUdpInQuota() != null) {
            _hashCode += getUdpInQuota().hashCode();
        }
        if (getIcmpInQuota() != null) {
            _hashCode += getIcmpInQuota().hashCode();
        }
        if (getIgmpInQuota() != null) {
            _hashCode += getIgmpInQuota().hashCode();
        }
        if (getTcpOutQuota() != null) {
            _hashCode += getTcpOutQuota().hashCode();
        }
        if (getUdpOutQuota() != null) {
            _hashCode += getUdpOutQuota().hashCode();
        }
        if (getIcmpOutQuota() != null) {
            _hashCode += getIcmpOutQuota().hashCode();
        }
        if (getIgmpOutQuota() != null) {
            _hashCode += getIgmpOutQuota().hashCode();
        }
        if (getTransparentOptimization() != null) {
            _hashCode += getTransparentOptimization().hashCode();
        }
        if (getPacketReport() != null) {
            _hashCode += getPacketReport().hashCode();
        }
        if (getPacketTrace() != null) {
            _hashCode += getPacketTrace().hashCode();
        }
        if (getProfileAction() != null) {
            _hashCode += getProfileAction().hashCode();
        }
        if (getSimulationDuration() != null) {
            _hashCode += getSimulationDuration().hashCode();
        }
        if (getStopSimulation() != null) {
            _hashCode += getStopSimulation().hashCode();
        }
        if (getStabilizationTime() != null) {
            _hashCode += getStabilizationTime().hashCode();
        }
        if (getStartSimulation() != null) {
            _hashCode += getStartSimulation().hashCode();
        }
        if (getJointDist() != null) {
            _hashCode += getJointDist().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Profiles.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SYNFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SYNFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_SYNFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UDPFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UDPFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_UDPFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IGMPFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IGMPFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_IGMPFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ICMPFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ICMPFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_ICMPFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FINACKFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FINACKFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_FINACKFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("RSTFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RSTFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_RSTFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SYNACKFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SYNACKFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_SYNACKFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("FRAGFloodstatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FRAGFloodstatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_FRAGFloodstatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bandwidthIn");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BandwidthIn"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bandwidthOut");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BandwidthOut"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcpInQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TcpInQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("udpInQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UdpInQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("icmpInQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IcmpInQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("igmpInQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IgmpInQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcpOutQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TcpOutQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("udpOutQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UdpOutQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("icmpOutQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IcmpOutQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("igmpOutQuota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IgmpOutQuota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("transparentOptimization");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TransparentOptimization"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_TransparentOptimization"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packetReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_packetReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetTrace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "packetTrace"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_packetTrace"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileAction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "profileAction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_profileAction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("simulationDuration");
        elemField.setXmlName(new javax.xml.namespace.QName("", "simulationDuration"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stopSimulation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stopSimulation"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_stopSimulation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("stabilizationTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "stabilizationTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("startSimulation");
        elemField.setXmlName(new javax.xml.namespace.QName("", "startSimulation"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_startSimulation"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("jointDist");
        elemField.setXmlName(new javax.xml.namespace.QName("", "jointDist"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Profiles_jointDist"));
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
