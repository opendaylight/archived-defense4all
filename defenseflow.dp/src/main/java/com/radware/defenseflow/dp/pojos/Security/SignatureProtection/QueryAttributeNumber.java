/**
 * QueryAttributeNumber.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a QueryAttributeNumber
 */
public class QueryAttributeNumber  implements java.io.Serializable {
    /* The type of the attribute. */
    private java.lang.String attributeType;

    /* The Name for the Attribute. */
    private java.lang.String attributeName;

    /* The number of attacks included in the attribute. */
    private java.lang.Long attacksNumber;

    public QueryAttributeNumber() {
    }

    public QueryAttributeNumber(
           java.lang.String attributeType,
           java.lang.String attributeName,
           java.lang.Long attacksNumber) {
           this.attributeType = attributeType;
           this.attributeName = attributeName;
           this.attacksNumber = attacksNumber;
    }


    /**
     * Gets the attributeType value for this QueryAttributeNumber.
     * 
     * @return attributeType   * The type of the attribute.
     */
    public java.lang.String getAttributeType() {
        return attributeType;
    }


    /**
     * Sets the attributeType value for this QueryAttributeNumber.
     * 
     * @param attributeType   * The type of the attribute.
     */
    public void setAttributeType(java.lang.String attributeType) {
        this.attributeType = attributeType;
    }


    /**
     * Gets the attributeName value for this QueryAttributeNumber.
     * 
     * @return attributeName   * The Name for the Attribute.
     */
    public java.lang.String getAttributeName() {
        return attributeName;
    }


    /**
     * Sets the attributeName value for this QueryAttributeNumber.
     * 
     * @param attributeName   * The Name for the Attribute.
     */
    public void setAttributeName(java.lang.String attributeName) {
        this.attributeName = attributeName;
    }


    /**
     * Gets the attacksNumber value for this QueryAttributeNumber.
     * 
     * @return attacksNumber   * The number of attacks included in the attribute.
     */
    public java.lang.Long getAttacksNumber() {
        return attacksNumber;
    }


    /**
     * Sets the attacksNumber value for this QueryAttributeNumber.
     * 
     * @param attacksNumber   * The number of attacks included in the attribute.
     */
    public void setAttacksNumber(java.lang.Long attacksNumber) {
        this.attacksNumber = attacksNumber;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryAttributeNumber)) return false;
        QueryAttributeNumber other = (QueryAttributeNumber) obj;
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
        if (getAttributeType() != null) {
            _hashCode += getAttributeType().hashCode();
        }
        if (getAttributeName() != null) {
            _hashCode += getAttributeName().hashCode();
        }
        if (getAttacksNumber() != null) {
            _hashCode += getAttacksNumber().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryAttributeNumber.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QueryAttributeNumber"));
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
