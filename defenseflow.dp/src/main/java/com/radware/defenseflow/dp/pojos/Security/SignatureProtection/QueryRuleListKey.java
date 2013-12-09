/**
 * QueryRuleListKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * Identifies and entry in the QueryRuleList table
 */
public class QueryRuleListKey  implements java.io.Serializable {
    /* The Name for the Profile. */
    private java.lang.String profileName;

    /* The name of the rule. */
    private java.lang.String rule;

    /* The unique ID of the attack. */
    private long attackId;

    public QueryRuleListKey() {
    }

    public QueryRuleListKey(
           java.lang.String profileName,
           java.lang.String rule,
           long attackId) {
           this.profileName = profileName;
           this.rule = rule;
           this.attackId = attackId;
    }


    /**
     * Gets the profileName value for this QueryRuleListKey.
     * 
     * @return profileName   * The Name for the Profile.
     */
    public java.lang.String getProfileName() {
        return profileName;
    }


    /**
     * Sets the profileName value for this QueryRuleListKey.
     * 
     * @param profileName   * The Name for the Profile.
     */
    public void setProfileName(java.lang.String profileName) {
        this.profileName = profileName;
    }


    /**
     * Gets the rule value for this QueryRuleListKey.
     * 
     * @return rule   * The name of the rule.
     */
    public java.lang.String getRule() {
        return rule;
    }


    /**
     * Sets the rule value for this QueryRuleListKey.
     * 
     * @param rule   * The name of the rule.
     */
    public void setRule(java.lang.String rule) {
        this.rule = rule;
    }


    /**
     * Gets the attackId value for this QueryRuleListKey.
     * 
     * @return attackId   * The unique ID of the attack.
     */
    public long getAttackId() {
        return attackId;
    }


    /**
     * Sets the attackId value for this QueryRuleListKey.
     * 
     * @param attackId   * The unique ID of the attack.
     */
    public void setAttackId(long attackId) {
        this.attackId = attackId;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QueryRuleListKey)) return false;
        QueryRuleListKey other = (QueryRuleListKey) obj;
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
            ((this.rule==null && other.getRule()==null) || 
             (this.rule!=null &&
              this.rule.equals(other.getRule()))) &&
            this.attackId == other.getAttackId();
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
        if (getRule() != null) {
            _hashCode += getRule().hashCode();
        }
        _hashCode += new Long(getAttackId()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QueryRuleListKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QueryRuleListKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("profileName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProfileName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rule");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Rule"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackId");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackId"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
