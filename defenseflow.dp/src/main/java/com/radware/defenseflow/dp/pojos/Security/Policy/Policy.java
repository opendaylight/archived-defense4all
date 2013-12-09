/**
 * Policy.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.Policy;


/**
 * This structure describes the parameters of a Policy
 */
public class Policy  implements java.io.Serializable {
    /* The Name for the Rule. */
    private java.lang.String policyName;

    /* Obsolete PolicyProfile. */
    private java.lang.String policyProfile;

    /* The source address / range of address for the packet, the name
     * is defined in the Objects table or can be an IP address. */
    private java.lang.String policySourceAddress;

    /* The destination address / range of address for the packet,
     * the name is defined in the Objects table or can be an IP address. */
    private java.lang.String policyDestinationAddress;

    /* This variable indicates the direction of the incoming packet. */
    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Direction direction;

    /* The Port Group */
    private java.lang.String inboundPhysicalPortGroup;

    /* This variable indicates the state of the rule. */
    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_State state;

    /* The VLAN tag Group */
    private java.lang.String vlanTagGroup;

    /* This variable indicates the policy action */
    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Action action;

    /* This variable indicates the packet reporting status for the
     * policy rule */
    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReport packetReport;

    /* Name for the signatures profile. */
    private java.lang.String signaturesProfile;

    /* Name for the connection limiting profile. */
    private java.lang.String connectionLimitingProfile;

    /* Name for the stateful inspection profile. */
    private java.lang.String outOfStateProfile;

    /* Obsolete . */
    private java.lang.String statefulInspectionProfile;

    /* Name for the anti scanning profile. */
    private java.lang.String antiScanningProfile;

    /* Name for the behavioral dos profile. */
    private java.lang.String behavioralDosProfile;

    /* Name for the syn protection profile. */
    private java.lang.String synProtectionProfile;

    /* Obsolete field ServProtectionProfile. */
    private java.lang.String servProtectionProfile;

    /* Name for the PPS limiting profile. */
    private java.lang.String PPSProfile;

    /* MPLS RD Group. */
    private java.lang.String MPLSRDGroup;

    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTrace packetTrace;

    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTraceEnforcement packetTraceEnforcement;

    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReportEnforcement packetReportEnforcement;

    private java.lang.String DNSProtectionProfile;

    private com.radware.defenseflow.dp.pojos.Security.Policy.Policy_QuarantineStatusInPolicy quarantineStatusInPolicy;

    private java.lang.String serviceDiscoveryProfile;

    public Policy() {
    }

    public Policy(
           java.lang.String policyName,
           java.lang.String policyProfile,
           java.lang.String policySourceAddress,
           java.lang.String policyDestinationAddress,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Direction direction,
           java.lang.String inboundPhysicalPortGroup,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_State state,
           java.lang.String vlanTagGroup,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Action action,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReport packetReport,
           java.lang.String signaturesProfile,
           java.lang.String connectionLimitingProfile,
           java.lang.String outOfStateProfile,
           java.lang.String statefulInspectionProfile,
           java.lang.String antiScanningProfile,
           java.lang.String behavioralDosProfile,
           java.lang.String synProtectionProfile,
           java.lang.String servProtectionProfile,
           java.lang.String PPSProfile,
           java.lang.String MPLSRDGroup,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTrace packetTrace,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTraceEnforcement packetTraceEnforcement,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReportEnforcement packetReportEnforcement,
           java.lang.String DNSProtectionProfile,
           com.radware.defenseflow.dp.pojos.Security.Policy.Policy_QuarantineStatusInPolicy quarantineStatusInPolicy,
           java.lang.String serviceDiscoveryProfile) {
           this.policyName = policyName;
           this.policyProfile = policyProfile;
           this.policySourceAddress = policySourceAddress;
           this.policyDestinationAddress = policyDestinationAddress;
           this.direction = direction;
           this.inboundPhysicalPortGroup = inboundPhysicalPortGroup;
           this.state = state;
           this.vlanTagGroup = vlanTagGroup;
           this.action = action;
           this.packetReport = packetReport;
           this.signaturesProfile = signaturesProfile;
           this.connectionLimitingProfile = connectionLimitingProfile;
           this.outOfStateProfile = outOfStateProfile;
           this.statefulInspectionProfile = statefulInspectionProfile;
           this.antiScanningProfile = antiScanningProfile;
           this.behavioralDosProfile = behavioralDosProfile;
           this.synProtectionProfile = synProtectionProfile;
           this.servProtectionProfile = servProtectionProfile;
           this.PPSProfile = PPSProfile;
           this.MPLSRDGroup = MPLSRDGroup;
           this.packetTrace = packetTrace;
           this.packetTraceEnforcement = packetTraceEnforcement;
           this.packetReportEnforcement = packetReportEnforcement;
           this.DNSProtectionProfile = DNSProtectionProfile;
           this.quarantineStatusInPolicy = quarantineStatusInPolicy;
           this.serviceDiscoveryProfile = serviceDiscoveryProfile;
    }


    /**
     * Gets the policyName value for this Policy.
     * 
     * @return policyName   * The Name for the Rule.
     */
    public java.lang.String getPolicyName() {
        return policyName;
    }


    /**
     * Sets the policyName value for this Policy.
     * 
     * @param policyName   * The Name for the Rule.
     */
    public void setPolicyName(java.lang.String policyName) {
        this.policyName = policyName;
    }


    /**
     * Gets the policyProfile value for this Policy.
     * 
     * @return policyProfile   * Obsolete PolicyProfile.
     */
    public java.lang.String getPolicyProfile() {
        return policyProfile;
    }


    /**
     * Sets the policyProfile value for this Policy.
     * 
     * @param policyProfile   * Obsolete PolicyProfile.
     */
    public void setPolicyProfile(java.lang.String policyProfile) {
        this.policyProfile = policyProfile;
    }


    /**
     * Gets the policySourceAddress value for this Policy.
     * 
     * @return policySourceAddress   * The source address / range of address for the packet, the name
     * is defined in the Objects table or can be an IP address.
     */
    public java.lang.String getPolicySourceAddress() {
        return policySourceAddress;
    }


    /**
     * Sets the policySourceAddress value for this Policy.
     * 
     * @param policySourceAddress   * The source address / range of address for the packet, the name
     * is defined in the Objects table or can be an IP address.
     */
    public void setPolicySourceAddress(java.lang.String policySourceAddress) {
        this.policySourceAddress = policySourceAddress;
    }


    /**
     * Gets the policyDestinationAddress value for this Policy.
     * 
     * @return policyDestinationAddress   * The destination address / range of address for the packet,
     * the name is defined in the Objects table or can be an IP address.
     */
    public java.lang.String getPolicyDestinationAddress() {
        return policyDestinationAddress;
    }


    /**
     * Sets the policyDestinationAddress value for this Policy.
     * 
     * @param policyDestinationAddress   * The destination address / range of address for the packet,
     * the name is defined in the Objects table or can be an IP address.
     */
    public void setPolicyDestinationAddress(java.lang.String policyDestinationAddress) {
        this.policyDestinationAddress = policyDestinationAddress;
    }


    /**
     * Gets the direction value for this Policy.
     * 
     * @return direction   * This variable indicates the direction of the incoming packet.
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Direction getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this Policy.
     * 
     * @param direction   * This variable indicates the direction of the incoming packet.
     */
    public void setDirection(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Direction direction) {
        this.direction = direction;
    }


    /**
     * Gets the inboundPhysicalPortGroup value for this Policy.
     * 
     * @return inboundPhysicalPortGroup   * The Port Group
     */
    public java.lang.String getInboundPhysicalPortGroup() {
        return inboundPhysicalPortGroup;
    }


    /**
     * Sets the inboundPhysicalPortGroup value for this Policy.
     * 
     * @param inboundPhysicalPortGroup   * The Port Group
     */
    public void setInboundPhysicalPortGroup(java.lang.String inboundPhysicalPortGroup) {
        this.inboundPhysicalPortGroup = inboundPhysicalPortGroup;
    }


    /**
     * Gets the state value for this Policy.
     * 
     * @return state   * This variable indicates the state of the rule.
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_State getState() {
        return state;
    }


    /**
     * Sets the state value for this Policy.
     * 
     * @param state   * This variable indicates the state of the rule.
     */
    public void setState(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_State state) {
        this.state = state;
    }


    /**
     * Gets the vlanTagGroup value for this Policy.
     * 
     * @return vlanTagGroup   * The VLAN tag Group
     */
    public java.lang.String getVlanTagGroup() {
        return vlanTagGroup;
    }


    /**
     * Sets the vlanTagGroup value for this Policy.
     * 
     * @param vlanTagGroup   * The VLAN tag Group
     */
    public void setVlanTagGroup(java.lang.String vlanTagGroup) {
        this.vlanTagGroup = vlanTagGroup;
    }


    /**
     * Gets the action value for this Policy.
     * 
     * @return action   * This variable indicates the policy action
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Action getAction() {
        return action;
    }


    /**
     * Sets the action value for this Policy.
     * 
     * @param action   * This variable indicates the policy action
     */
    public void setAction(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Action action) {
        this.action = action;
    }


    /**
     * Gets the packetReport value for this Policy.
     * 
     * @return packetReport   * This variable indicates the packet reporting status for the
     * policy rule
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReport getPacketReport() {
        return packetReport;
    }


    /**
     * Sets the packetReport value for this Policy.
     * 
     * @param packetReport   * This variable indicates the packet reporting status for the
     * policy rule
     */
    public void setPacketReport(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReport packetReport) {
        this.packetReport = packetReport;
    }


    /**
     * Gets the signaturesProfile value for this Policy.
     * 
     * @return signaturesProfile   * Name for the signatures profile.
     */
    public java.lang.String getSignaturesProfile() {
        return signaturesProfile;
    }


    /**
     * Sets the signaturesProfile value for this Policy.
     * 
     * @param signaturesProfile   * Name for the signatures profile.
     */
    public void setSignaturesProfile(java.lang.String signaturesProfile) {
        this.signaturesProfile = signaturesProfile;
    }


    /**
     * Gets the connectionLimitingProfile value for this Policy.
     * 
     * @return connectionLimitingProfile   * Name for the connection limiting profile.
     */
    public java.lang.String getConnectionLimitingProfile() {
        return connectionLimitingProfile;
    }


    /**
     * Sets the connectionLimitingProfile value for this Policy.
     * 
     * @param connectionLimitingProfile   * Name for the connection limiting profile.
     */
    public void setConnectionLimitingProfile(java.lang.String connectionLimitingProfile) {
        this.connectionLimitingProfile = connectionLimitingProfile;
    }


    /**
     * Gets the outOfStateProfile value for this Policy.
     * 
     * @return outOfStateProfile   * Name for the stateful inspection profile.
     */
    public java.lang.String getOutOfStateProfile() {
        return outOfStateProfile;
    }


    /**
     * Sets the outOfStateProfile value for this Policy.
     * 
     * @param outOfStateProfile   * Name for the stateful inspection profile.
     */
    public void setOutOfStateProfile(java.lang.String outOfStateProfile) {
        this.outOfStateProfile = outOfStateProfile;
    }


    /**
     * Gets the statefulInspectionProfile value for this Policy.
     * 
     * @return statefulInspectionProfile   * Obsolete .
     */
    public java.lang.String getStatefulInspectionProfile() {
        return statefulInspectionProfile;
    }


    /**
     * Sets the statefulInspectionProfile value for this Policy.
     * 
     * @param statefulInspectionProfile   * Obsolete .
     */
    public void setStatefulInspectionProfile(java.lang.String statefulInspectionProfile) {
        this.statefulInspectionProfile = statefulInspectionProfile;
    }


    /**
     * Gets the antiScanningProfile value for this Policy.
     * 
     * @return antiScanningProfile   * Name for the anti scanning profile.
     */
    public java.lang.String getAntiScanningProfile() {
        return antiScanningProfile;
    }


    /**
     * Sets the antiScanningProfile value for this Policy.
     * 
     * @param antiScanningProfile   * Name for the anti scanning profile.
     */
    public void setAntiScanningProfile(java.lang.String antiScanningProfile) {
        this.antiScanningProfile = antiScanningProfile;
    }


    /**
     * Gets the behavioralDosProfile value for this Policy.
     * 
     * @return behavioralDosProfile   * Name for the behavioral dos profile.
     */
    public java.lang.String getBehavioralDosProfile() {
        return behavioralDosProfile;
    }


    /**
     * Sets the behavioralDosProfile value for this Policy.
     * 
     * @param behavioralDosProfile   * Name for the behavioral dos profile.
     */
    public void setBehavioralDosProfile(java.lang.String behavioralDosProfile) {
        this.behavioralDosProfile = behavioralDosProfile;
    }


    /**
     * Gets the synProtectionProfile value for this Policy.
     * 
     * @return synProtectionProfile   * Name for the syn protection profile.
     */
    public java.lang.String getSynProtectionProfile() {
        return synProtectionProfile;
    }


    /**
     * Sets the synProtectionProfile value for this Policy.
     * 
     * @param synProtectionProfile   * Name for the syn protection profile.
     */
    public void setSynProtectionProfile(java.lang.String synProtectionProfile) {
        this.synProtectionProfile = synProtectionProfile;
    }


    /**
     * Gets the servProtectionProfile value for this Policy.
     * 
     * @return servProtectionProfile   * Obsolete field ServProtectionProfile.
     */
    public java.lang.String getServProtectionProfile() {
        return servProtectionProfile;
    }


    /**
     * Sets the servProtectionProfile value for this Policy.
     * 
     * @param servProtectionProfile   * Obsolete field ServProtectionProfile.
     */
    public void setServProtectionProfile(java.lang.String servProtectionProfile) {
        this.servProtectionProfile = servProtectionProfile;
    }


    /**
     * Gets the PPSProfile value for this Policy.
     * 
     * @return PPSProfile   * Name for the PPS limiting profile.
     */
    public java.lang.String getPPSProfile() {
        return PPSProfile;
    }


    /**
     * Sets the PPSProfile value for this Policy.
     * 
     * @param PPSProfile   * Name for the PPS limiting profile.
     */
    public void setPPSProfile(java.lang.String PPSProfile) {
        this.PPSProfile = PPSProfile;
    }


    /**
     * Gets the MPLSRDGroup value for this Policy.
     * 
     * @return MPLSRDGroup   * MPLS RD Group.
     */
    public java.lang.String getMPLSRDGroup() {
        return MPLSRDGroup;
    }


    /**
     * Sets the MPLSRDGroup value for this Policy.
     * 
     * @param MPLSRDGroup   * MPLS RD Group.
     */
    public void setMPLSRDGroup(java.lang.String MPLSRDGroup) {
        this.MPLSRDGroup = MPLSRDGroup;
    }


    /**
     * Gets the packetTrace value for this Policy.
     * 
     * @return packetTrace
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTrace getPacketTrace() {
        return packetTrace;
    }


    /**
     * Sets the packetTrace value for this Policy.
     * 
     * @param packetTrace
     */
    public void setPacketTrace(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTrace packetTrace) {
        this.packetTrace = packetTrace;
    }


    /**
     * Gets the packetTraceEnforcement value for this Policy.
     * 
     * @return packetTraceEnforcement
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTraceEnforcement getPacketTraceEnforcement() {
        return packetTraceEnforcement;
    }


    /**
     * Sets the packetTraceEnforcement value for this Policy.
     * 
     * @param packetTraceEnforcement
     */
    public void setPacketTraceEnforcement(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketTraceEnforcement packetTraceEnforcement) {
        this.packetTraceEnforcement = packetTraceEnforcement;
    }


    /**
     * Gets the packetReportEnforcement value for this Policy.
     * 
     * @return packetReportEnforcement
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReportEnforcement getPacketReportEnforcement() {
        return packetReportEnforcement;
    }


    /**
     * Sets the packetReportEnforcement value for this Policy.
     * 
     * @param packetReportEnforcement
     */
    public void setPacketReportEnforcement(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReportEnforcement packetReportEnforcement) {
        this.packetReportEnforcement = packetReportEnforcement;
    }


    /**
     * Gets the DNSProtectionProfile value for this Policy.
     * 
     * @return DNSProtectionProfile
     */
    public java.lang.String getDNSProtectionProfile() {
        return DNSProtectionProfile;
    }


    /**
     * Sets the DNSProtectionProfile value for this Policy.
     * 
     * @param DNSProtectionProfile
     */
    public void setDNSProtectionProfile(java.lang.String DNSProtectionProfile) {
        this.DNSProtectionProfile = DNSProtectionProfile;
    }


    /**
     * Gets the quarantineStatusInPolicy value for this Policy.
     * 
     * @return quarantineStatusInPolicy
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy_QuarantineStatusInPolicy getQuarantineStatusInPolicy() {
        return quarantineStatusInPolicy;
    }


    /**
     * Sets the quarantineStatusInPolicy value for this Policy.
     * 
     * @param quarantineStatusInPolicy
     */
    public void setQuarantineStatusInPolicy(com.radware.defenseflow.dp.pojos.Security.Policy.Policy_QuarantineStatusInPolicy quarantineStatusInPolicy) {
        this.quarantineStatusInPolicy = quarantineStatusInPolicy;
    }


    /**
     * Gets the serviceDiscoveryProfile value for this Policy.
     * 
     * @return serviceDiscoveryProfile
     */
    public java.lang.String getServiceDiscoveryProfile() {
        return serviceDiscoveryProfile;
    }


    /**
     * Sets the serviceDiscoveryProfile value for this Policy.
     * 
     * @param serviceDiscoveryProfile
     */
    public void setServiceDiscoveryProfile(java.lang.String serviceDiscoveryProfile) {
        this.serviceDiscoveryProfile = serviceDiscoveryProfile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Policy)) return false;
        Policy other = (Policy) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.policyName==null && other.getPolicyName()==null) || 
             (this.policyName!=null &&
              this.policyName.equals(other.getPolicyName()))) &&
            ((this.policyProfile==null && other.getPolicyProfile()==null) || 
             (this.policyProfile!=null &&
              this.policyProfile.equals(other.getPolicyProfile()))) &&
            ((this.policySourceAddress==null && other.getPolicySourceAddress()==null) || 
             (this.policySourceAddress!=null &&
              this.policySourceAddress.equals(other.getPolicySourceAddress()))) &&
            ((this.policyDestinationAddress==null && other.getPolicyDestinationAddress()==null) || 
             (this.policyDestinationAddress!=null &&
              this.policyDestinationAddress.equals(other.getPolicyDestinationAddress()))) &&
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection()))) &&
            ((this.inboundPhysicalPortGroup==null && other.getInboundPhysicalPortGroup()==null) || 
             (this.inboundPhysicalPortGroup!=null &&
              this.inboundPhysicalPortGroup.equals(other.getInboundPhysicalPortGroup()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.vlanTagGroup==null && other.getVlanTagGroup()==null) || 
             (this.vlanTagGroup!=null &&
              this.vlanTagGroup.equals(other.getVlanTagGroup()))) &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.packetReport==null && other.getPacketReport()==null) || 
             (this.packetReport!=null &&
              this.packetReport.equals(other.getPacketReport()))) &&
            ((this.signaturesProfile==null && other.getSignaturesProfile()==null) || 
             (this.signaturesProfile!=null &&
              this.signaturesProfile.equals(other.getSignaturesProfile()))) &&
            ((this.connectionLimitingProfile==null && other.getConnectionLimitingProfile()==null) || 
             (this.connectionLimitingProfile!=null &&
              this.connectionLimitingProfile.equals(other.getConnectionLimitingProfile()))) &&
            ((this.outOfStateProfile==null && other.getOutOfStateProfile()==null) || 
             (this.outOfStateProfile!=null &&
              this.outOfStateProfile.equals(other.getOutOfStateProfile()))) &&
            ((this.statefulInspectionProfile==null && other.getStatefulInspectionProfile()==null) || 
             (this.statefulInspectionProfile!=null &&
              this.statefulInspectionProfile.equals(other.getStatefulInspectionProfile()))) &&
            ((this.antiScanningProfile==null && other.getAntiScanningProfile()==null) || 
             (this.antiScanningProfile!=null &&
              this.antiScanningProfile.equals(other.getAntiScanningProfile()))) &&
            ((this.behavioralDosProfile==null && other.getBehavioralDosProfile()==null) || 
             (this.behavioralDosProfile!=null &&
              this.behavioralDosProfile.equals(other.getBehavioralDosProfile()))) &&
            ((this.synProtectionProfile==null && other.getSynProtectionProfile()==null) || 
             (this.synProtectionProfile!=null &&
              this.synProtectionProfile.equals(other.getSynProtectionProfile()))) &&
            ((this.servProtectionProfile==null && other.getServProtectionProfile()==null) || 
             (this.servProtectionProfile!=null &&
              this.servProtectionProfile.equals(other.getServProtectionProfile()))) &&
            ((this.PPSProfile==null && other.getPPSProfile()==null) || 
             (this.PPSProfile!=null &&
              this.PPSProfile.equals(other.getPPSProfile()))) &&
            ((this.MPLSRDGroup==null && other.getMPLSRDGroup()==null) || 
             (this.MPLSRDGroup!=null &&
              this.MPLSRDGroup.equals(other.getMPLSRDGroup()))) &&
            ((this.packetTrace==null && other.getPacketTrace()==null) || 
             (this.packetTrace!=null &&
              this.packetTrace.equals(other.getPacketTrace()))) &&
            ((this.packetTraceEnforcement==null && other.getPacketTraceEnforcement()==null) || 
             (this.packetTraceEnforcement!=null &&
              this.packetTraceEnforcement.equals(other.getPacketTraceEnforcement()))) &&
            ((this.packetReportEnforcement==null && other.getPacketReportEnforcement()==null) || 
             (this.packetReportEnforcement!=null &&
              this.packetReportEnforcement.equals(other.getPacketReportEnforcement()))) &&
            ((this.DNSProtectionProfile==null && other.getDNSProtectionProfile()==null) || 
             (this.DNSProtectionProfile!=null &&
              this.DNSProtectionProfile.equals(other.getDNSProtectionProfile()))) &&
            ((this.quarantineStatusInPolicy==null && other.getQuarantineStatusInPolicy()==null) || 
             (this.quarantineStatusInPolicy!=null &&
              this.quarantineStatusInPolicy.equals(other.getQuarantineStatusInPolicy()))) &&
            ((this.serviceDiscoveryProfile==null && other.getServiceDiscoveryProfile()==null) || 
             (this.serviceDiscoveryProfile!=null &&
              this.serviceDiscoveryProfile.equals(other.getServiceDiscoveryProfile())));
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
        if (getPolicyName() != null) {
            _hashCode += getPolicyName().hashCode();
        }
        if (getPolicyProfile() != null) {
            _hashCode += getPolicyProfile().hashCode();
        }
        if (getPolicySourceAddress() != null) {
            _hashCode += getPolicySourceAddress().hashCode();
        }
        if (getPolicyDestinationAddress() != null) {
            _hashCode += getPolicyDestinationAddress().hashCode();
        }
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        if (getInboundPhysicalPortGroup() != null) {
            _hashCode += getInboundPhysicalPortGroup().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getVlanTagGroup() != null) {
            _hashCode += getVlanTagGroup().hashCode();
        }
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getPacketReport() != null) {
            _hashCode += getPacketReport().hashCode();
        }
        if (getSignaturesProfile() != null) {
            _hashCode += getSignaturesProfile().hashCode();
        }
        if (getConnectionLimitingProfile() != null) {
            _hashCode += getConnectionLimitingProfile().hashCode();
        }
        if (getOutOfStateProfile() != null) {
            _hashCode += getOutOfStateProfile().hashCode();
        }
        if (getStatefulInspectionProfile() != null) {
            _hashCode += getStatefulInspectionProfile().hashCode();
        }
        if (getAntiScanningProfile() != null) {
            _hashCode += getAntiScanningProfile().hashCode();
        }
        if (getBehavioralDosProfile() != null) {
            _hashCode += getBehavioralDosProfile().hashCode();
        }
        if (getSynProtectionProfile() != null) {
            _hashCode += getSynProtectionProfile().hashCode();
        }
        if (getServProtectionProfile() != null) {
            _hashCode += getServProtectionProfile().hashCode();
        }
        if (getPPSProfile() != null) {
            _hashCode += getPPSProfile().hashCode();
        }
        if (getMPLSRDGroup() != null) {
            _hashCode += getMPLSRDGroup().hashCode();
        }
        if (getPacketTrace() != null) {
            _hashCode += getPacketTrace().hashCode();
        }
        if (getPacketTraceEnforcement() != null) {
            _hashCode += getPacketTraceEnforcement().hashCode();
        }
        if (getPacketReportEnforcement() != null) {
            _hashCode += getPacketReportEnforcement().hashCode();
        }
        if (getDNSProtectionProfile() != null) {
            _hashCode += getDNSProtectionProfile().hashCode();
        }
        if (getQuarantineStatusInPolicy() != null) {
            _hashCode += getQuarantineStatusInPolicy().hashCode();
        }
        if (getServiceDiscoveryProfile() != null) {
            _hashCode += getServiceDiscoveryProfile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Policy.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PolicyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PolicyProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policySourceAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PolicySourceAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("policyDestinationAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PolicyDestinationAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_Direction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inboundPhysicalPortGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "InboundPhysicalPortGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_State"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("vlanTagGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VlanTagGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Action"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_Action"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_PacketReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("signaturesProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SignaturesProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("connectionLimitingProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ConnectionLimitingProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outOfStateProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OutOfStateProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("statefulInspectionProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "StatefulInspectionProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("antiScanningProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AntiScanningProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("behavioralDosProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BehavioralDosProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("synProtectionProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SynProtectionProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("servProtectionProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ServProtectionProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("PPSProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PPSProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("MPLSRDGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MPLSRDGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetTrace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketTrace"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_PacketTrace"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetTraceEnforcement");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketTraceEnforcement"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_PacketTraceEnforcement"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReportEnforcement");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketReportEnforcement"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_PacketReportEnforcement"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("DNSProtectionProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DNSProtectionProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineStatusInPolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineStatusInPolicy"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "Policy_QuarantineStatusInPolicy"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("serviceDiscoveryProfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ServiceDiscoveryProfile"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
