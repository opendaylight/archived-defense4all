/**
 * QueryProfileList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a QueryProfileList
 */
public class QueryProfileList  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* The unique ID of the attack. */
    private long attackId;

    /* The Name for the attack. */
    private java.lang.String attackName;

    public QueryProfileList() {
    }

    public QueryProfileList(
           java.lang.String profileName,
           long attackId,
           java.lang.String attackName) {
           this.profileName = profileName;
           this.attackId = attackId;
           this.attackName = attackName;
    }


    /**
     * Gets the profileName value for this QueryProfileList.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this QueryProfileList.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the attackId value for this QueryProfileList.
     * 
     * @return attackId   * The unique ID of the attack.
     */
    public long getAttackId() {
        return attackId;
    }


    /**
     * Sets the attackId value for this QueryProfileList.
     * 
     * @param attackId   * The unique ID of the attack.
     */
    public void setAttackId(long attackId) {
        this.attackId = attackId;
    }


    /**
     * Gets the attackName value for this QueryProfileList.
     * 
     * @return attackName   * The Name for the attack.
     */
    public java.lang.String getAttackName() {
        return attackName;
    }


    /**
     * Sets the attackName value for this QueryProfileList.
     * 
     * @param attackName   * The Name for the attack.
     */
    public void setAttackName(java.lang.String attackName) {
        this.attackName = attackName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryProfileList)) return false;
        QueryProfileList other = (QueryProfileList) obj;
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
            this.attackId == other.getAttackId() &&
            ((this.attackName==null && other.getAttackName()==null) || 
             (this.attackName!=null &&
              this.attackName.equals(other.getAttackName())));
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
        _hashCode += new Long(getAttackId()).hashCode();
        if (getAttackName() != null) {
            _hashCode += getAttackName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryProfileList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QueryProfileList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackName"));
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
