/**
 * DnsProtectionBypassEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;


/**
 * This structure describes the parameters of a DnsProtectionBypassEntry
 */
public class DnsProtectionBypassEntry  implements java.io.Serializable {
    /* The bypass controller */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassController bypassController;

    /* The bypass type */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassField bypassField;

    /* The bypass status */
    private com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassStatus bypassStatus;

    /* The values of the bypass. */
    private java.lang.String bypassValues;

    public DnsProtectionBypassEntry() {
    }

    public DnsProtectionBypassEntry(
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassController bypassController,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassField bypassField,
           com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassStatus bypassStatus,
           java.lang.String bypassValues) {
           this.bypassController = bypassController;
           this.bypassField = bypassField;
           this.bypassStatus = bypassStatus;
           this.bypassValues = bypassValues;
    }


    /**
     * Gets the bypassController value for this DnsProtectionBypassEntry.
     * 
     * @return bypassController   * The bypass controller
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassController getBypassController() {
        return bypassController;
    }


    /**
     * Sets the bypassController value for this DnsProtectionBypassEntry.
     * 
     * @param bypassController   * The bypass controller
     */
    public void setBypassController(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassController bypassController) {
        this.bypassController = bypassController;
    }


    /**
     * Gets the bypassField value for this DnsProtectionBypassEntry.
     * 
     * @return bypassField   * The bypass type
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassField getBypassField() {
        return bypassField;
    }


    /**
     * Sets the bypassField value for this DnsProtectionBypassEntry.
     * 
     * @param bypassField   * The bypass type
     */
    public void setBypassField(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassField bypassField) {
        this.bypassField = bypassField;
    }


    /**
     * Gets the bypassStatus value for this DnsProtectionBypassEntry.
     * 
     * @return bypassStatus   * The bypass status
     */
    public com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassStatus getBypassStatus() {
        return bypassStatus;
    }


    /**
     * Sets the bypassStatus value for this DnsProtectionBypassEntry.
     * 
     * @param bypassStatus   * The bypass status
     */
    public void setBypassStatus(com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionBypassEntry_BypassStatus bypassStatus) {
        this.bypassStatus = bypassStatus;
    }


    /**
     * Gets the bypassValues value for this DnsProtectionBypassEntry.
     * 
     * @return bypassValues   * The values of the bypass.
     */
    public java.lang.String getBypassValues() {
        return bypassValues;
    }


    /**
     * Sets the bypassValues value for this DnsProtectionBypassEntry.
     * 
     * @param bypassValues   * The values of the bypass.
     */
    public void setBypassValues(java.lang.String bypassValues) {
        this.bypassValues = bypassValues;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof DnsProtectionBypassEntry)) return false;
        DnsProtectionBypassEntry other = (DnsProtectionBypassEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.bypassController==null && other.getBypassController()==null) || 
             (this.bypassController!=null &&
              this.bypassController.equals(other.getBypassController()))) &&
            ((this.bypassField==null && other.getBypassField()==null) || 
             (this.bypassField!=null &&
              this.bypassField.equals(other.getBypassField()))) &&
            ((this.bypassStatus==null && other.getBypassStatus()==null) || 
             (this.bypassStatus!=null &&
              this.bypassStatus.equals(other.getBypassStatus()))) &&
            ((this.bypassValues==null && other.getBypassValues()==null) || 
             (this.bypassValues!=null &&
              this.bypassValues.equals(other.getBypassValues())));
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
        if (getBypassController() != null) {
            _hashCode += getBypassController().hashCode();
        }
        if (getBypassField() != null) {
            _hashCode += getBypassField().hashCode();
        }
        if (getBypassStatus() != null) {
            _hashCode += getBypassStatus().hashCode();
        }
        if (getBypassValues() != null) {
            _hashCode += getBypassValues().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(DnsProtectionBypassEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bypassController");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BypassController"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassController"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bypassField");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BypassField"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassField"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bypassStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BypassStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bypassValues");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BypassValues"));
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
