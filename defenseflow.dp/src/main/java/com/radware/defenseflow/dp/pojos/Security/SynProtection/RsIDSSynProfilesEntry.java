/**
 * RsIDSSynProfilesEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;


/**
 * This structure describes the parameters of a rsIDSSynProfilesEntry
 */
public class RsIDSSynProfilesEntry  implements java.io.Serializable {
    /* The name of the profile. */
    private java.lang.String profileName;

    /* This name of the Attack */
    private java.lang.String attackName;

    /* This variable indicates tha attack id */
    private java.lang.Long attackID;

    /* Denotes the type of the profile. */
    private com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry_ProfileType profileType;

    public RsIDSSynProfilesEntry() {
    }

    public RsIDSSynProfilesEntry(
           java.lang.String profileName,
           java.lang.String attackName,
           java.lang.Long attackID,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry_ProfileType profileType) {
           this.profileName = profileName;
           this.attackName = attackName;
           this.attackID = attackID;
           this.profileType = profileType;
    }


    /**
     * Gets the profileName value for this RsIDSSynProfilesEntry.
     * 
     * @return profileName   * The name of the profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this RsIDSSynProfilesEntry.
     * 
     * @param profileName   * The name of the profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the attackName value for this RsIDSSynProfilesEntry.
     * 
     * @return attackName   * This name of the Attack
     */
    public java.lang.String getAttackName() {
        return attackName;
    }


    /**
     * Sets the attackName value for this RsIDSSynProfilesEntry.
     * 
     * @param attackName   * This name of the Attack
     */
    public void setAttackName(java.lang.String attackName) {
        this.attackName = attackName;
    }


    /**
     * Gets the attackID value for this RsIDSSynProfilesEntry.
     * 
     * @return attackID   * This variable indicates tha attack id
     */
    public java.lang.Long getAttackID() {
        return attackID;
    }


    /**
     * Sets the attackID value for this RsIDSSynProfilesEntry.
     * 
     * @param attackID   * This variable indicates tha attack id
     */
    public void setAttackID(java.lang.Long attackID) {
        this.attackID = attackID;
    }


    /**
     * Gets the profileType value for this RsIDSSynProfilesEntry.
     * 
     * @return profileType   * Denotes the type of the profile.
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry_ProfileType getProfileType() {
        return profileType;
    }


    /**
     * Sets the profileType value for this RsIDSSynProfilesEntry.
     * 
     * @param profileType   * Denotes the type of the profile.
     */
    public void setProfileType(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry_ProfileType profileType) {
        this.profileType = profileType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsIDSSynProfilesEntry)) return false;
        RsIDSSynProfilesEntry other = (RsIDSSynProfilesEntry) obj;
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
            ((this.attackName==null && other.getAttackName()==null) || 
             (this.attackName!=null &&
              this.attackName.equals(other.getAttackName()))) &&
            ((this.attackID==null && other.getAttackID()==null) || 
             (this.attackID!=null &&
              this.attackID.equals(other.getAttackID()))) &&
            ((this.profileType==null && other.getProfileType()==null) || 
             (this.profileType!=null &&
              this.profileType.equals(other.getProfileType())));
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
        if (getAttackName() != null) {
            _hashCode += getAttackName().hashCode();
        }
        if (getAttackID() != null) {
            _hashCode += getAttackID().hashCode();
        }
        if (getProfileType() != null) {
            _hashCode += getProfileType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsIDSSynProfilesEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesEntry_ProfileType"));
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
