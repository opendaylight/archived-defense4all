/**
 * QueryAttributesList.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a QueryAttributesList
 */
public class QueryAttributesList  implements java.io.Serializable {
    /* The type of the attribute. */
    private java.lang.String attributeType;

    /* The Name for the Attribute. */
    private java.lang.String attributeName;

    /* The unique ID of the attack. */
    private long attackId;

    /* The Name for the attack. */
    private java.lang.String attackName;

    /* The type of attack. This can be user defined or static. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesList_SourceType sourceType;

    public QueryAttributesList() {
    }

    public QueryAttributesList(
           java.lang.String attributeType,
           java.lang.String attributeName,
           long attackId,
           java.lang.String attackName,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesList_SourceType sourceType) {
           this.attributeType = attributeType;
           this.attributeName = attributeName;
           this.attackId = attackId;
           this.attackName = attackName;
           this.sourceType = sourceType;
    }


    /**
     * Gets the attributeType value for this QueryAttributesList.
     * 
     * @return attributeType   * The type of the attribute.
     */
    public java.lang.String getAttributeType() {
        return attributeType;
    }


    /**
     * Sets the attributeType value for this QueryAttributesList.
     * 
     * @param attributeType   * The type of the attribute.
     */
    public void setAttributeType(java.lang.String attributeType) {
        this.attributeType = attributeType;
    }


    /**
     * Gets the attributeName value for this QueryAttributesList.
     * 
     * @return attributeName   * The Name for the Attribute.
     */
    public java.lang.String getAttributeName() {
        return attributeName;
    }


    /**
     * Sets the attributeName value for this QueryAttributesList.
     * 
     * @param attributeName   * The Name for the Attribute.
     */
    public void setAttributeName(java.lang.String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * Gets the attackId value for this QueryAttributesList.
     * 
     * @return attackId   * The unique ID of the attack.
     */
    public long getAttackId() {
        return attackId;
    }


    /**
     * Sets the attackId value for this QueryAttributesList.
     * 
     * @param attackId   * The unique ID of the attack.
     */
    public void setAttackId(long attackId) {
        this.attackId = attackId;
    }


    /**
     * Gets the attackName value for this QueryAttributesList.
     * 
     * @return attackName   * The Name for the attack.
     */
    public java.lang.String getAttackName() {
        return attackName;
    }


    /**
     * Sets the attackName value for this QueryAttributesList.
     * 
     * @param attackName   * The Name for the attack.
     */
    public void setAttackName(java.lang.String attackName) {
        this.attackName = attackName;
    }


    /**
     * Gets the sourceType value for this QueryAttributesList.
     * 
     * @return sourceType   * The type of attack. This can be user defined or static.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesList_SourceType getSourceType() {
        return sourceType;
    }


    /**
     * Sets the sourceType value for this QueryAttributesList.
     * 
     * @param sourceType   * The type of attack. This can be user defined or static.
     */
    public void setSourceType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QueryAttributesList_SourceType sourceType) {
        this.sourceType = sourceType;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAttributesList)) return false;
        QueryAttributesList other = (QueryAttributesList) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.attributeType==null && other.getAttributeType()==null) || 
             (this.attributeType!=null &&
              this.attributeType.equals(other.getAttributeType()))) &&
            ((this.attributeName==null && other.getAttributeName()==null) || 
             (this.attributeName!=null &&
              this.attributeName.equals(other.getAttributeName()))) &&
            this.attackId == other.getAttackId() &&
            ((this.attackName==null && other.getAttackName()==null) || 
             (this.attackName!=null &&
              this.attackName.equals(other.getAttackName()))) &&
            ((this.sourceType==null && other.getSourceType()==null) || 
             (this.sourceType!=null &&
              this.sourceType.equals(other.getSourceType())));
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
        if (getAttributeType() != null) {
            _hashCode += getAttributeType().hashCode();
        }
        if (getAttributeName() != null) {
            _hashCode += getAttributeName().hashCode();
        }
        _hashCode += new Long(getAttackId()).hashCode();
        if (getAttackName() != null) {
            _hashCode += getAttackName().hashCode();
        }
        if (getSourceType() != null) {
            _hashCode += getSourceType().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryAttributesList.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QueryAttributesList"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttributeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttributeName"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SourceType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QueryAttributesList_SourceType"));
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
