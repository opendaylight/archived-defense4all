/**
 * AttributeTypes.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a AttributeTypes
 */
public class AttributeTypes  implements java.io.Serializable {
    /* The Name for the attribute type. */
    private java.lang.String typeName;

    /* Does attribute type allow configuration of multiple values
     * in attack. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinAttack multipleValuesinAttack;

    /* Does attribute type allow configuration of multiple values
     * in rule. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinRule multipleValuesinRule;

    /* Does attribute type support configuration in static attacks. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_ConfigurableinStatic configurableinStatic;

    /* How attribute of this type are matched. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MatchMethod matchMethod;

    public AttributeTypes() {
    }

    public AttributeTypes(
           java.lang.String typeName,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinAttack multipleValuesinAttack,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinRule multipleValuesinRule,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_ConfigurableinStatic configurableinStatic,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MatchMethod matchMethod) {
           this.typeName = typeName;
           this.multipleValuesinAttack = multipleValuesinAttack;
           this.multipleValuesinRule = multipleValuesinRule;
           this.configurableinStatic = configurableinStatic;
           this.matchMethod = matchMethod;
    }


    /**
     * Gets the typeName value for this AttributeTypes.
     * 
     * @return typeName   * The Name for the attribute type.
     */
    public java.lang.String getTypeName() {
        return typeName;
    }


    /**
     * Sets the typeName value for this AttributeTypes.
     * 
     * @param typeName   * The Name for the attribute type.
     */
    public void setTypeName(java.lang.String typeName) {
        this.typeName = typeName;
    }


    /**
     * Gets the multipleValuesinAttack value for this AttributeTypes.
     * 
     * @return multipleValuesinAttack   * Does attribute type allow configuration of multiple values
     * in attack.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinAttack getMultipleValuesinAttack() {
        return multipleValuesinAttack;
    }


    /**
     * Sets the multipleValuesinAttack value for this AttributeTypes.
     * 
     * @param multipleValuesinAttack   * Does attribute type allow configuration of multiple values
     * in attack.
     */
    public void setMultipleValuesinAttack(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinAttack multipleValuesinAttack) {
        this.multipleValuesinAttack = multipleValuesinAttack;
    }


    /**
     * Gets the multipleValuesinRule value for this AttributeTypes.
     * 
     * @return multipleValuesinRule   * Does attribute type allow configuration of multiple values
     * in rule.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinRule getMultipleValuesinRule() {
        return multipleValuesinRule;
    }


    /**
     * Sets the multipleValuesinRule value for this AttributeTypes.
     * 
     * @param multipleValuesinRule   * Does attribute type allow configuration of multiple values
     * in rule.
     */
    public void setMultipleValuesinRule(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MultipleValuesinRule multipleValuesinRule) {
        this.multipleValuesinRule = multipleValuesinRule;
    }


    /**
     * Gets the configurableinStatic value for this AttributeTypes.
     * 
     * @return configurableinStatic   * Does attribute type support configuration in static attacks.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_ConfigurableinStatic getConfigurableinStatic() {
        return configurableinStatic;
    }


    /**
     * Sets the configurableinStatic value for this AttributeTypes.
     * 
     * @param configurableinStatic   * Does attribute type support configuration in static attacks.
     */
    public void setConfigurableinStatic(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_ConfigurableinStatic configurableinStatic) {
        this.configurableinStatic = configurableinStatic;
    }


    /**
     * Gets the matchMethod value for this AttributeTypes.
     * 
     * @return matchMethod   * How attribute of this type are matched.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MatchMethod getMatchMethod() {
        return matchMethod;
    }


    /**
     * Sets the matchMethod value for this AttributeTypes.
     * 
     * @param matchMethod   * How attribute of this type are matched.
     */
    public void setMatchMethod(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttributeTypes_MatchMethod matchMethod) {
        this.matchMethod = matchMethod;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttributeTypes)) return false;
        AttributeTypes other = (AttributeTypes) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.typeName==null && other.getTypeName()==null) || 
             (this.typeName!=null &&
              this.typeName.equals(other.getTypeName()))) &&
            ((this.multipleValuesinAttack==null && other.getMultipleValuesinAttack()==null) || 
             (this.multipleValuesinAttack!=null &&
              this.multipleValuesinAttack.equals(other.getMultipleValuesinAttack()))) &&
            ((this.multipleValuesinRule==null && other.getMultipleValuesinRule()==null) || 
             (this.multipleValuesinRule!=null &&
              this.multipleValuesinRule.equals(other.getMultipleValuesinRule()))) &&
            ((this.configurableinStatic==null && other.getConfigurableinStatic()==null) || 
             (this.configurableinStatic!=null &&
              this.configurableinStatic.equals(other.getConfigurableinStatic()))) &&
            ((this.matchMethod==null && other.getMatchMethod()==null) || 
             (this.matchMethod!=null &&
              this.matchMethod.equals(other.getMatchMethod())));
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
        if (getTypeName() != null) {
            _hashCode += getTypeName().hashCode();
        }
        if (getMultipleValuesinAttack() != null) {
            _hashCode += getMultipleValuesinAttack().hashCode();
        }
        if (getMultipleValuesinRule() != null) {
            _hashCode += getMultipleValuesinRule().hashCode();
        }
        if (getConfigurableinStatic() != null) {
            _hashCode += getConfigurableinStatic().hashCode();
        }
        if (getMatchMethod() != null) {
            _hashCode += getMatchMethod().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttributeTypes.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributeTypes"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TypeName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multipleValuesinAttack");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MultipleValuesinAttack"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributeTypes_MultipleValuesinAttack"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("multipleValuesinRule");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MultipleValuesinRule"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributeTypes_MultipleValuesinRule"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("configurableinStatic");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ConfigurableinStatic"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributeTypes_ConfigurableinStatic"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("matchMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "MatchMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttributeTypes_MatchMethod"));
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
