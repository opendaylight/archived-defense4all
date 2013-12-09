/**
 * AttributesValues.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a AttributesValues
 */
public class AttributesValues  implements java.io.Serializable {
    /* The type of the attribute. */
    private java.lang.String attributeType;

    /* The Name for the Attribute. */
    private java.lang.String attributeName;

    /* Denotes the source of the attribute. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValues_AttributeSource attributeSource;

    /* The type id of the attribute. */
    private java.lang.Long attributeTypeId;

    /* The value of the attribute. */
    private java.lang.Long attributeValue;

    public AttributesValues() {
    }

    public AttributesValues(
           java.lang.String attributeType,
           java.lang.String attributeName,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValues_AttributeSource attributeSource,
           java.lang.Long attributeTypeId,
           java.lang.Long attributeValue) {
           this.attributeType = attributeType;
           this.attributeName = attributeName;
           this.attributeSource = attributeSource;
           this.attributeTypeId = attributeTypeId;
           this.attributeValue = attributeValue;
    }


    /**
     * Gets the attributeType value for this AttributesValues.
     * 
     * @return attributeType   * The type of the attribute.
     */
    public java.lang.String getAttributeType() {
        return attributeType;
    }


    /**
     * Sets the attributeType value for this AttributesValues.
     * 
     * @param attributeType   * The type of the attribute.
     */
    public void setAttributeType(java.lang.String attributeType) {
        this.attributeType = attributeType;
    }


    /**
     * Gets the attributeName value for this AttributesValues.
     * 
     * @return attributeName   * The Name for the Attribute.
     */
    public java.lang.String getAttributeName() {
        return attributeName;
    }


    /**
     * Sets the attributeName value for this AttributesValues.
     * 
     * @param attributeName   * The Name for the Attribute.
     */
    public void setAttributeName(java.lang.String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * Gets the attributeSource value for this AttributesValues.
     * 
     * @return attributeSource   * Denotes the source of the attribute.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValues_AttributeSource getAttributeSource() {
        return attributeSource;
    }


    /**
     * Sets the attributeSource value for this AttributesValues.
     * 
     * @param attributeSource   * Denotes the source of the attribute.
     */
    public void setAttributeSource(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributesValues_AttributeSource attributeSource) {
        this.attributeSource = attributeSource;
    }


    /**
     * Gets the attributeTypeId value for this AttributesValues.
     * 
     * @return attributeTypeId   * The type id of the attribute.
     */
    public java.lang.Long getAttributeTypeId() {
        return attributeTypeId;
    }


    /**
     * Sets the attributeTypeId value for this AttributesValues.
     * 
     * @param attributeTypeId   * The type id of the attribute.
     */
    public void setAttributeTypeId(java.lang.Long attributeTypeId) {
        this.attributeTypeId = attributeTypeId;
    }


    /**
     * Gets the attributeValue value for this AttributesValues.
     * 
     * @return attributeValue   * The value of the attribute.
     */
    public java.lang.Long getAttributeValue() {
        return attributeValue;
    }


    /**
     * Sets the attributeValue value for this AttributesValues.
     * 
     * @param attributeValue   * The value of the attribute.
     */
    public void setAttributeValue(java.lang.Long attributeValue) {
        this.attributeValue = attributeValue;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttributesValues)) return false;
        AttributesValues other = (AttributesValues) obj;
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
            ((this.attributeSource==null && other.getAttributeSource()==null) || 
             (this.attributeSource!=null &&
              this.attributeSource.equals(other.getAttributeSource()))) &&
            ((this.attributeTypeId==null && other.getAttributeTypeId()==null) || 
             (this.attributeTypeId!=null &&
              this.attributeTypeId.equals(other.getAttributeTypeId()))) &&
            ((this.attributeValue==null && other.getAttributeValue()==null) || 
             (this.attributeValue!=null &&
              this.attributeValue.equals(other.getAttributeValue())));
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
        if (getAttributeSource() != null) {
            _hashCode += getAttributeSource().hashCode();
        }
        if (getAttributeTypeId() != null) {
            _hashCode += getAttributeTypeId().hashCode();
        }
        if (getAttributeValue() != null) {
            _hashCode += getAttributeValue().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttributesValues.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributesValues"));
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
        elemField.setFieldName("attributeSource");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttributeSource"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributesValues_AttributeSource"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeTypeId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttributeTypeId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attributeValue");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttributeValue"));
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
