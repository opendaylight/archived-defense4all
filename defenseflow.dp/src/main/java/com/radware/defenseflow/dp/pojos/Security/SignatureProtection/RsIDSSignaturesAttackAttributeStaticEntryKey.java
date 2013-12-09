/**
 * RsIDSSignaturesAttackAttributeStaticEntryKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * Identifies and entry in the rsIDSSignaturesAttackAttributeStaticEntry
 * table
 */
public class RsIDSSignaturesAttackAttributeStaticEntryKey  implements java.io.Serializable {
    /* The id of the attack. */
    private long attackId;

    /* The name for the Attribute. */
    private java.lang.String attributeName;

    public RsIDSSignaturesAttackAttributeStaticEntryKey() {
    }

    public RsIDSSignaturesAttackAttributeStaticEntryKey(
           long attackId,
           java.lang.String attributeName) {
           this.attackId = attackId;
           this.attributeName = attributeName;
    }


    /**
     * Gets the attackId value for this RsIDSSignaturesAttackAttributeStaticEntryKey.
     * 
     * @return attackId   * The id of the attack.
     */
    public long getAttackId() {
        return attackId;
    }


    /**
     * Sets the attackId value for this RsIDSSignaturesAttackAttributeStaticEntryKey.
     * 
     * @param attackId   * The id of the attack.
     */
    public void setAttackId(long attackId) {
        this.attackId = attackId;
    }


    /**
     * Gets the attributeName value for this RsIDSSignaturesAttackAttributeStaticEntryKey.
     * 
     * @return attributeName   * The name for the Attribute.
     */
    public java.lang.String getAttributeName() {
        return attributeName;
    }


    /**
     * Sets the attributeName value for this RsIDSSignaturesAttackAttributeStaticEntryKey.
     * 
     * @param attributeName   * The name for the Attribute.
     */
    public void setAttributeName(java.lang.String attributeName) {
        this.attributeName = attributeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsIDSSignaturesAttackAttributeStaticEntryKey)) return false;
        RsIDSSignaturesAttackAttributeStaticEntryKey other = (RsIDSSignaturesAttackAttributeStaticEntryKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.attackId == other.getAttackId() &&
            ((this.attributeName==null && other.getAttributeName()==null) || 
             (this.attributeName!=null &&
              this.attributeName.equals(other.getAttributeName())));
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
        _hashCode += new Long(getAttackId()).hashCode();
        if (getAttributeName() != null) {
            _hashCode += getAttributeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsIDSSignaturesAttackAttributeStaticEntryKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "rsIDSSignaturesAttackAttributeStaticEntryKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttributeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
