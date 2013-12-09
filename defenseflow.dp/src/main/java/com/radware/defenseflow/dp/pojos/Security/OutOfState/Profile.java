/**
 * Profile.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.OutOfState;


/**
 * This structure describes the parameters of a Profile
 */
public class Profile  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table. */
    private java.lang.Long ACTThreshold;

    /* This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table. */
    private java.lang.Long termThreshold;

    /* This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table. */
    private com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_SYNACKAllow SYNACKAllow;

    /* This variable indicates Packet trace status. */
    private com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketTrace packetTrace;

    /* This variable indicates Packet report status. */
    private com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketReport packetReport;

    /* This variable indicates Packet report status. */
    private com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileRisk profileRisk;

    /* This variable indicates Packet report status. */
    private com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileAction profileAction;

    public Profile() {
    }

    public Profile(
           java.lang.String profileName,
           java.lang.Long ACTThreshold,
           java.lang.Long termThreshold,
           com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_SYNACKAllow SYNACKAllow,
           com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketTrace packetTrace,
           com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketReport packetReport,
           com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileRisk profileRisk,
           com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileAction profileAction) {
           this.profileName = profileName;
           this.ACTThreshold = ACTThreshold;
           this.termThreshold = termThreshold;
           this.SYNACKAllow = SYNACKAllow;
           this.packetTrace = packetTrace;
           this.packetReport = packetReport;
           this.profileRisk = profileRisk;
           this.profileAction = profileAction;
    }


    /**
     * Gets the profileName value for this Profile.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this Profile.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the ACTThreshold value for this Profile.
     * 
     * @return ACTThreshold   * This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table.
     */
    public java.lang.Long getACTThreshold() {
        return ACTThreshold;
    }


    /**
     * Sets the ACTThreshold value for this Profile.
     * 
     * @param ACTThreshold   * This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table.
     */
    public void setACTThreshold(java.lang.Long ACTThreshold) {
        this.ACTThreshold = ACTThreshold;
    }


    /**
     * Gets the termThreshold value for this Profile.
     * 
     * @return termThreshold   * This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table.
     */
    public java.lang.Long getTermThreshold() {
        return termThreshold;
    }


    /**
     * Sets the termThreshold value for this Profile.
     * 
     * @param termThreshold   * This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table.
     */
    public void setTermThreshold(java.lang.Long termThreshold) {
        this.termThreshold = termThreshold;
    }


    /**
     * Gets the SYNACKAllow value for this Profile.
     * 
     * @return SYNACKAllow   * This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table.
     */
    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_SYNACKAllow getSYNACKAllow() {
        return SYNACKAllow;
    }


    /**
     * Sets the SYNACKAllow value for this Profile.
     * 
     * @param SYNACKAllow   * This variable indicates the administrative status of this entry.
     * Used to delete an entry of this table.
     */
    public void setSYNACKAllow(com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_SYNACKAllow SYNACKAllow) {
        this.SYNACKAllow = SYNACKAllow;
    }


    /**
     * Gets the packetTrace value for this Profile.
     * 
     * @return packetTrace   * This variable indicates Packet trace status.
     */
    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketTrace getPacketTrace() {
        return packetTrace;
    }


    /**
     * Sets the packetTrace value for this Profile.
     * 
     * @param packetTrace   * This variable indicates Packet trace status.
     */
    public void setPacketTrace(com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketTrace packetTrace) {
        this.packetTrace = packetTrace;
    }


    /**
     * Gets the packetReport value for this Profile.
     * 
     * @return packetReport   * This variable indicates Packet report status.
     */
    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketReport getPacketReport() {
        return packetReport;
    }


    /**
     * Sets the packetReport value for this Profile.
     * 
     * @param packetReport   * This variable indicates Packet report status.
     */
    public void setPacketReport(com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketReport packetReport) {
        this.packetReport = packetReport;
    }


    /**
     * Gets the profileRisk value for this Profile.
     * 
     * @return profileRisk   * This variable indicates Packet report status.
     */
    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileRisk getProfileRisk() {
        return profileRisk;
    }


    /**
     * Sets the profileRisk value for this Profile.
     * 
     * @param profileRisk   * This variable indicates Packet report status.
     */
    public void setProfileRisk(com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileRisk profileRisk) {
        this.profileRisk = profileRisk;
    }


    /**
     * Gets the profileAction value for this Profile.
     * 
     * @return profileAction   * This variable indicates Packet report status.
     */
    public com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileAction getProfileAction() {
        return profileAction;
    }


    /**
     * Sets the profileAction value for this Profile.
     * 
     * @param profileAction   * This variable indicates Packet report status.
     */
    public void setProfileAction(com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileAction profileAction) {
        this.profileAction = profileAction;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Profile)) return false;
        Profile other = (Profile) obj;
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
            ((this.ACTThreshold==null && other.getACTThreshold()==null) || 
             (this.ACTThreshold!=null &&
              this.ACTThreshold.equals(other.getACTThreshold()))) &&
            ((this.termThreshold==null && other.getTermThreshold()==null) || 
             (this.termThreshold!=null &&
              this.termThreshold.equals(other.getTermThreshold()))) &&
            ((this.SYNACKAllow==null && other.getSYNACKAllow()==null) || 
             (this.SYNACKAllow!=null &&
              this.SYNACKAllow.equals(other.getSYNACKAllow()))) &&
            ((this.packetTrace==null && other.getPacketTrace()==null) || 
             (this.packetTrace!=null &&
              this.packetTrace.equals(other.getPacketTrace()))) &&
            ((this.packetReport==null && other.getPacketReport()==null) || 
             (this.packetReport!=null &&
              this.packetReport.equals(other.getPacketReport()))) &&
            ((this.profileRisk==null && other.getProfileRisk()==null) || 
             (this.profileRisk!=null &&
              this.profileRisk.equals(other.getProfileRisk()))) &&
            ((this.profileAction==null && other.getProfileAction()==null) || 
             (this.profileAction!=null &&
              this.profileAction.equals(other.getProfileAction())));
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
        if (getACTThreshold() != null) {
            _hashCode += getACTThreshold().hashCode();
        }
        if (getTermThreshold() != null) {
            _hashCode += getTermThreshold().hashCode();
        }
        if (getSYNACKAllow() != null) {
            _hashCode += getSYNACKAllow().hashCode();
        }
        if (getPacketTrace() != null) {
            _hashCode += getPacketTrace().hashCode();
        }
        if (getPacketReport() != null) {
            _hashCode += getPacketReport().hashCode();
        }
        if (getProfileRisk() != null) {
            _hashCode += getProfileRisk().hashCode();
        }
        if (getProfileAction() != null) {
            _hashCode += getProfileAction().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Profile.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ACTThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ACTThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TermThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SYNACKAllow");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SYNACKAllow"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_SYNACKAllow"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetTrace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketTrace"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_PacketTrace"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_PacketReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileRisk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileRisk"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_ProfileRisk"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileAction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileAction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_ProfileAction"));
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
