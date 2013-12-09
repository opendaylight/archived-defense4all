/**
 * RsNetFloodDynamicStateTwoEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;


/**
 * This structure describes the parameters of a rsNetFloodDynamicStateTwoEntry
 */
public class RsNetFloodDynamicStateTwoEntry  implements java.io.Serializable {
    /* The controller */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType;

    /* The dynamic state */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_AnyTypeFlag anyTypeFlag;

    /* The dynamic footprint type */
    private java.lang.Long typeThreshold;

    /* The dynamic footprint type */
    private java.lang.Long valueThreshold;

    public RsNetFloodDynamicStateTwoEntry() {
    }

    public RsNetFloodDynamicStateTwoEntry(
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_AnyTypeFlag anyTypeFlag,
           java.lang.Long typeThreshold,
           java.lang.Long valueThreshold) {
           this.protectionType = protectionType;
           this.anyTypeFlag = anyTypeFlag;
           this.typeThreshold = typeThreshold;
           this.valueThreshold = valueThreshold;
    }


    /**
     * Gets the protectionType value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @return protectionType   * The controller
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType getProtectionType() {
        return protectionType;
    }


    /**
     * Sets the protectionType value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @param protectionType   * The controller
     */
    public void setProtectionType(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_ProtectionType protectionType) {
        this.protectionType = protectionType;
    }


    /**
     * Gets the anyTypeFlag value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @return anyTypeFlag   * The dynamic state
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_AnyTypeFlag getAnyTypeFlag() {
        return anyTypeFlag;
    }


    /**
     * Sets the anyTypeFlag value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @param anyTypeFlag   * The dynamic state
     */
    public void setAnyTypeFlag(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateTwoEntry_AnyTypeFlag anyTypeFlag) {
        this.anyTypeFlag = anyTypeFlag;
    }


    /**
     * Gets the typeThreshold value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @return typeThreshold   * The dynamic footprint type
     */
    public java.lang.Long getTypeThreshold() {
        return typeThreshold;
    }


    /**
     * Sets the typeThreshold value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @param typeThreshold   * The dynamic footprint type
     */
    public void setTypeThreshold(java.lang.Long typeThreshold) {
        this.typeThreshold = typeThreshold;
    }


    /**
     * Gets the valueThreshold value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @return valueThreshold   * The dynamic footprint type
     */
    public java.lang.Long getValueThreshold() {
        return valueThreshold;
    }


    /**
     * Sets the valueThreshold value for this RsNetFloodDynamicStateTwoEntry.
     * 
     * @param valueThreshold   * The dynamic footprint type
     */
    public void setValueThreshold(java.lang.Long valueThreshold) {
        this.valueThreshold = valueThreshold;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsNetFloodDynamicStateTwoEntry)) return false;
        RsNetFloodDynamicStateTwoEntry other = (RsNetFloodDynamicStateTwoEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.protectionType==null && other.getProtectionType()==null) || 
             (this.protectionType!=null &&
              this.protectionType.equals(other.getProtectionType()))) &&
            ((this.anyTypeFlag==null && other.getAnyTypeFlag()==null) || 
             (this.anyTypeFlag!=null &&
              this.anyTypeFlag.equals(other.getAnyTypeFlag()))) &&
            ((this.typeThreshold==null && other.getTypeThreshold()==null) || 
             (this.typeThreshold!=null &&
              this.typeThreshold.equals(other.getTypeThreshold()))) &&
            ((this.valueThreshold==null && other.getValueThreshold()==null) || 
             (this.valueThreshold!=null &&
              this.valueThreshold.equals(other.getValueThreshold())));
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
        if (getProtectionType() != null) {
            _hashCode += getProtectionType().hashCode();
        }
        if (getAnyTypeFlag() != null) {
            _hashCode += getAnyTypeFlag().hashCode();
        }
        if (getTypeThreshold() != null) {
            _hashCode += getTypeThreshold().hashCode();
        }
        if (getValueThreshold() != null) {
            _hashCode += getValueThreshold().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsNetFloodDynamicStateTwoEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protectionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProtectionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_ProtectionType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("anyTypeFlag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AnyTypeFlag"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateTwoEntry_AnyTypeFlag"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("typeThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TypeThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("valueThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ValueThreshold"));
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
