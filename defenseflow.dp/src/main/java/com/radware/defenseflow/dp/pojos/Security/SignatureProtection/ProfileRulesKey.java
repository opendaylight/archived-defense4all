/**
 * ProfileRulesKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * Identifies and entry in the ProfileRules table
 */
public class ProfileRulesKey  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* The Name for the Rule. */
    private java.lang.String ruleName;

    /* The Type for the Attribute. */
    private java.lang.String ruleAttributeType;

    /* The Name for the Attribute. */
    private java.lang.String ruleAttributeName;

    public ProfileRulesKey() {
    }

    public ProfileRulesKey(
           java.lang.String profileName,
           java.lang.String ruleName,
           java.lang.String ruleAttributeType,
           java.lang.String ruleAttributeName) {
           this.profileName = profileName;
           this.ruleName = ruleName;
           this.ruleAttributeType = ruleAttributeType;
           this.ruleAttributeName = ruleAttributeName;
    }


    /**
     * Gets the profileName value for this ProfileRulesKey.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this ProfileRulesKey.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the ruleName value for this ProfileRulesKey.
     * 
     * @return ruleName   * The Name for the Rule.
     */
    public java.lang.String getRuleName() {
        return ruleName;
    }


    /**
     * Sets the ruleName value for this ProfileRulesKey.
     * 
     * @param ruleName   * The Name for the Rule.
     */
    public void setRuleName(java.lang.String ruleName) {
        this.ruleName = ruleName;
    }


    /**
     * Gets the ruleAttributeType value for this ProfileRulesKey.
     * 
     * @return ruleAttributeType   * The Type for the Attribute.
     */
    public java.lang.String getRuleAttributeType() {
        return ruleAttributeType;
    }


    /**
     * Sets the ruleAttributeType value for this ProfileRulesKey.
     * 
     * @param ruleAttributeType   * The Type for the Attribute.
     */
    public void setRuleAttributeType(java.lang.String ruleAttributeType) {
        this.ruleAttributeType = ruleAttributeType;
    }


    /**
     * Gets the ruleAttributeName value for this ProfileRulesKey.
     * 
     * @return ruleAttributeName   * The Name for the Attribute.
     */
    public java.lang.String getRuleAttributeName() {
        return ruleAttributeName;
    }


    /**
     * Sets the ruleAttributeName value for this ProfileRulesKey.
     * 
     * @param ruleAttributeName   * The Name for the Attribute.
     */
    public void setRuleAttributeName(java.lang.String ruleAttributeName) {
        this.ruleAttributeName = ruleAttributeName;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ProfileRulesKey)) return false;
        ProfileRulesKey other = (ProfileRulesKey) obj;
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
            ((this.ruleName==null && other.getRuleName()==null) || 
             (this.ruleName!=null &&
              this.ruleName.equals(other.getRuleName()))) &&
            ((this.ruleAttributeType==null && other.getRuleAttributeType()==null) || 
             (this.ruleAttributeType!=null &&
              this.ruleAttributeType.equals(other.getRuleAttributeType()))) &&
            ((this.ruleAttributeName==null && other.getRuleAttributeName()==null) || 
             (this.ruleAttributeName!=null &&
              this.ruleAttributeName.equals(other.getRuleAttributeName())));
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
        if (getRuleName() != null) {
            _hashCode += getRuleName().hashCode();
        }
        if (getRuleAttributeType() != null) {
            _hashCode += getRuleAttributeType().hashCode();
        }
        if (getRuleAttributeName() != null) {
            _hashCode += getRuleAttributeName().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ProfileRulesKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "ProfileRulesKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RuleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleAttributeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RuleAttributeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ruleAttributeName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "RuleAttributeName"));
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
