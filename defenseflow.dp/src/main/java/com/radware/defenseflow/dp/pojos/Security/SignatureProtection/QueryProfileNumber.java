/**
 * QueryProfileNumber.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a QueryProfileNumber
 */
public class QueryProfileNumber  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* The number of attacks included in the profile. */
    private java.lang.Long attacksNumber;

    public QueryProfileNumber() {
    }

    public QueryProfileNumber(
           java.lang.String profileName,
           java.lang.Long attacksNumber) {
           this.profileName = profileName;
           this.attacksNumber = attacksNumber;
    }


    /**
     * Gets the profileName value for this QueryProfileNumber.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this QueryProfileNumber.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the attacksNumber value for this QueryProfileNumber.
     * 
     * @return attacksNumber   * The number of attacks included in the profile.
     */
    public java.lang.Long getAttacksNumber() {
        return attacksNumber;
    }


    /**
     * Sets the attacksNumber value for this QueryProfileNumber.
     * 
     * @param attacksNumber   * The number of attacks included in the profile.
     */
    public void setAttacksNumber(java.lang.Long attacksNumber) {
        this.attacksNumber = attacksNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryProfileNumber)) return false;
        QueryProfileNumber other = (QueryProfileNumber) obj;
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
            ((this.attacksNumber==null && other.getAttacksNumber()==null) || 
             (this.attacksNumber!=null &&
              this.attacksNumber.equals(other.getAttacksNumber())));
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
        if (getAttacksNumber() != null) {
            _hashCode += getAttacksNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryProfileNumber.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QueryProfileNumber"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attacksNumber");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttacksNumber"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
