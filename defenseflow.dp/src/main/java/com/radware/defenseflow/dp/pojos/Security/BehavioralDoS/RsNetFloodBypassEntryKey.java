/**
 * RsNetFloodBypassEntryKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;


/**
 * Identifies and entry in the rsNetFloodBypassEntry table
 */
public class RsNetFloodBypassEntryKey  implements java.io.Serializable {
    /* The bypass controller */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassController bypassController;

    /* The bypass type */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassField bypassField;

    public RsNetFloodBypassEntryKey() {
    }

    public RsNetFloodBypassEntryKey(
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassController bypassController,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassField bypassField) {
           this.bypassController = bypassController;
           this.bypassField = bypassField;
    }


    /**
     * Gets the bypassController value for this RsNetFloodBypassEntryKey.
     * 
     * @return bypassController   * The bypass controller
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassController getBypassController() {
        return bypassController;
    }


    /**
     * Sets the bypassController value for this RsNetFloodBypassEntryKey.
     * 
     * @param bypassController   * The bypass controller
     */
    public void setBypassController(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassController bypassController) {
        this.bypassController = bypassController;
    }


    /**
     * Gets the bypassField value for this RsNetFloodBypassEntryKey.
     * 
     * @return bypassField   * The bypass type
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassField getBypassField() {
        return bypassField;
    }


    /**
     * Sets the bypassField value for this RsNetFloodBypassEntryKey.
     * 
     * @param bypassField   * The bypass type
     */
    public void setBypassField(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodBypassEntry_BypassField bypassField) {
        this.bypassField = bypassField;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsNetFloodBypassEntryKey)) return false;
        RsNetFloodBypassEntryKey other = (RsNetFloodBypassEntryKey) obj;
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
              this.bypassField.equals(other.getBypassField())));
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
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsNetFloodBypassEntryKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntryKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bypassController");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BypassController"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassController"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("bypassField");
        elemField.setXmlName(new javax.xml.namespace.QName("", "BypassField"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassField"));
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
