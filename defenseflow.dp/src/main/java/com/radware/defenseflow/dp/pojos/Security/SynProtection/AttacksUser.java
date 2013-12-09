/**
 * AttacksUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;

import com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_AttackType;
import com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketReport;
import com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Risk;


/**
 * This structure describes the parameters of a AttacksUser
 */
public class AttacksUser  implements java.io.Serializable {
    /* This variable indicates the object type- whether it is a filter
     * or group */
    private long ID;

    /* The Name for the attack must be unique. */
    private java.lang.String attackName;

    /* The Destination Application Port Group. */
    private java.lang.String applicationPortGroup;

    /* This variable indicates the activating threshold */
    private java.lang.Long activationThreshold;

    /* This variable indicates the termination threshold */
    private java.lang.Long terminationThreshold;

    /* This variable indicates the per-attack packet report */
    private com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_PacketReport packetReport;

    /* Denotes the type of the attack . */
    private com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_AttackType attackType;

    /* This variable indicates the risk of the attack */
    private com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_Risk risk;

    public AttacksUser() {
    }

    public AttacksUser(
           long ID,
           java.lang.String attackName,
           java.lang.String applicationPortGroup,
           java.lang.Long activationThreshold,
           java.lang.Long terminationThreshold,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_PacketReport packetReport,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_AttackType attackType,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_Risk risk) {
           this.ID = ID;
           this.attackName = attackName;
           this.applicationPortGroup = applicationPortGroup;
           this.activationThreshold = activationThreshold;
           this.terminationThreshold = terminationThreshold;
           this.packetReport = packetReport;
           this.attackType = attackType;
           this.risk = risk;
    }


  

	/**
     * Gets the ID value for this AttacksUser.
     * 
     * @return ID   * This variable indicates the object type- whether it is a filter
     * or group
     */
    public long getID() {
        return ID;
    }


    /**
     * Sets the ID value for this AttacksUser.
     * 
     * @param ID   * This variable indicates the object type- whether it is a filter
     * or group
     */
    public void setID(long ID) {
        this.ID = ID;
    }


    /**
     * Gets the attackName value for this AttacksUser.
     * 
     * @return attackName   * The Name for the attack must be unique.
     */
    public java.lang.String getAttackName() {
        return attackName;
    }


    /**
     * Sets the attackName value for this AttacksUser.
     * 
     * @param attackName   * The Name for the attack must be unique.
     */
    public void setAttackName(java.lang.String attackName) {
        this.attackName = attackName;
    }


    /**
     * Gets the applicationPortGroup value for this AttacksUser.
     * 
     * @return applicationPortGroup   * The Destination Application Port Group.
     */
    public java.lang.String getApplicationPortGroup() {
        return applicationPortGroup;
    }


    /**
     * Sets the applicationPortGroup value for this AttacksUser.
     * 
     * @param applicationPortGroup   * The Destination Application Port Group.
     */
    public void setApplicationPortGroup(java.lang.String applicationPortGroup) {
        this.applicationPortGroup = applicationPortGroup;
    }


    /**
     * Gets the activationThreshold value for this AttacksUser.
     * 
     * @return activationThreshold   * This variable indicates the activating threshold
     */
    public java.lang.Long getActivationThreshold() {
        return activationThreshold;
    }


    /**
     * Sets the activationThreshold value for this AttacksUser.
     * 
     * @param activationThreshold   * This variable indicates the activating threshold
     */
    public void setActivationThreshold(java.lang.Long activationThreshold) {
        this.activationThreshold = activationThreshold;
    }


    /**
     * Gets the terminationThreshold value for this AttacksUser.
     * 
     * @return terminationThreshold   * This variable indicates the termination threshold
     */
    public java.lang.Long getTerminationThreshold() {
        return terminationThreshold;
    }


    /**
     * Sets the terminationThreshold value for this AttacksUser.
     * 
     * @param terminationThreshold   * This variable indicates the termination threshold
     */
    public void setTerminationThreshold(java.lang.Long terminationThreshold) {
        this.terminationThreshold = terminationThreshold;
    }


    /**
     * Gets the packetReport value for this AttacksUser.
     * 
     * @return packetReport   * This variable indicates the per-attack packet report
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_PacketReport getPacketReport() {
        return packetReport;
    }


    /**
     * Sets the packetReport value for this AttacksUser.
     * 
     * @param packetReport   * This variable indicates the per-attack packet report
     */
    public void setPacketReport(com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_PacketReport packetReport) {
        this.packetReport = packetReport;
    }


    /**
     * Gets the attackType value for this AttacksUser.
     * 
     * @return attackType   * Denotes the type of the attack .
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_AttackType getAttackType() {
        return attackType;
    }


    /**
     * Sets the attackType value for this AttacksUser.
     * 
     * @param attackType   * Denotes the type of the attack .
     */
    public void setAttackType(com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_AttackType attackType) {
        this.attackType = attackType;
    }


    /**
     * Gets the risk value for this AttacksUser.
     * 
     * @return risk   * This variable indicates the risk of the attack
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_Risk getRisk() {
        return risk;
    }


    /**
     * Sets the risk value for this AttacksUser.
     * 
     * @param risk   * This variable indicates the risk of the attack
     */
    public void setRisk(com.radware.defenseflow.dp.pojos.Security.SynProtection.AttacksUser_Risk risk) {
        this.risk = risk;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttacksUser)) return false;
        AttacksUser other = (AttacksUser) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.attackName==null && other.getAttackName()==null) || 
             (this.attackName!=null &&
              this.attackName.equals(other.getAttackName()))) &&
            ((this.applicationPortGroup==null && other.getApplicationPortGroup()==null) || 
             (this.applicationPortGroup!=null &&
              this.applicationPortGroup.equals(other.getApplicationPortGroup()))) &&
            ((this.activationThreshold==null && other.getActivationThreshold()==null) || 
             (this.activationThreshold!=null &&
              this.activationThreshold.equals(other.getActivationThreshold()))) &&
            ((this.terminationThreshold==null && other.getTerminationThreshold()==null) || 
             (this.terminationThreshold!=null &&
              this.terminationThreshold.equals(other.getTerminationThreshold()))) &&
            ((this.packetReport==null && other.getPacketReport()==null) || 
             (this.packetReport!=null &&
              this.packetReport.equals(other.getPacketReport()))) &&
            ((this.attackType==null && other.getAttackType()==null) || 
             (this.attackType!=null &&
              this.attackType.equals(other.getAttackType()))) &&
            ((this.risk==null && other.getRisk()==null) || 
             (this.risk!=null &&
              this.risk.equals(other.getRisk())));
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
        _hashCode += new Long(getID()).hashCode();
        if (getAttackName() != null) {
            _hashCode += getAttackName().hashCode();
        }
        if (getApplicationPortGroup() != null) {
            _hashCode += getApplicationPortGroup().hashCode();
        }
        if (getActivationThreshold() != null) {
            _hashCode += getActivationThreshold().hashCode();
        }
        if (getTerminationThreshold() != null) {
            _hashCode += getTerminationThreshold().hashCode();
        }
        if (getPacketReport() != null) {
            _hashCode += getPacketReport().hashCode();
        }
        if (getAttackType() != null) {
            _hashCode += getAttackType().hashCode();
        }
        if (getRisk() != null) {
            _hashCode += getRisk().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttacksUser.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "AttacksUser"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("applicationPortGroup");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ApplicationPortGroup"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activationThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ActivationThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("terminationThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TerminationThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "AttacksUser_PacketReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "AttacksUser_AttackType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("risk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Risk"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "AttacksUser_Risk"));
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
