/**
 * RsNetFloodDynamicStateFpEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;


/**
 * This structure describes the parameters of a rsNetFloodDynamicStateFpEntry
 */
public class RsNetFloodDynamicStateFpEntry  implements java.io.Serializable {
    /* The controller */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_ProtectionType protectionType;

    /* The dynamic footprint type */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_FootprintType footprintType;

    /* The dynamic state */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_DetectionCondition detectionCondition;

    public RsNetFloodDynamicStateFpEntry() {
    }

    public RsNetFloodDynamicStateFpEntry(
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_ProtectionType protectionType,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_FootprintType footprintType,
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_DetectionCondition detectionCondition) {
           this.protectionType = protectionType;
           this.footprintType = footprintType;
           this.detectionCondition = detectionCondition;
    }


    /**
     * Gets the protectionType value for this RsNetFloodDynamicStateFpEntry.
     * 
     * @return protectionType   * The controller
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_ProtectionType getProtectionType() {
        return protectionType;
    }


    /**
     * Sets the protectionType value for this RsNetFloodDynamicStateFpEntry.
     * 
     * @param protectionType   * The controller
     */
    public void setProtectionType(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_ProtectionType protectionType) {
        this.protectionType = protectionType;
    }


    /**
     * Gets the footprintType value for this RsNetFloodDynamicStateFpEntry.
     * 
     * @return footprintType   * The dynamic footprint type
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_FootprintType getFootprintType() {
        return footprintType;
    }


    /**
     * Sets the footprintType value for this RsNetFloodDynamicStateFpEntry.
     * 
     * @param footprintType   * The dynamic footprint type
     */
    public void setFootprintType(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_FootprintType footprintType) {
        this.footprintType = footprintType;
    }


    /**
     * Gets the detectionCondition value for this RsNetFloodDynamicStateFpEntry.
     * 
     * @return detectionCondition   * The dynamic state
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_DetectionCondition getDetectionCondition() {
        return detectionCondition;
    }


    /**
     * Sets the detectionCondition value for this RsNetFloodDynamicStateFpEntry.
     * 
     * @param detectionCondition   * The dynamic state
     */
    public void setDetectionCondition(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.RsNetFloodDynamicStateFpEntry_DetectionCondition detectionCondition) {
        this.detectionCondition = detectionCondition;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsNetFloodDynamicStateFpEntry)) return false;
        RsNetFloodDynamicStateFpEntry other = (RsNetFloodDynamicStateFpEntry) obj;
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
            ((this.footprintType==null && other.getFootprintType()==null) || 
             (this.footprintType!=null &&
              this.footprintType.equals(other.getFootprintType()))) &&
            ((this.detectionCondition==null && other.getDetectionCondition()==null) || 
             (this.detectionCondition!=null &&
              this.detectionCondition.equals(other.getDetectionCondition())));
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
        if (getFootprintType() != null) {
            _hashCode += getFootprintType().hashCode();
        }
        if (getDetectionCondition() != null) {
            _hashCode += getDetectionCondition().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsNetFloodDynamicStateFpEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protectionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ProtectionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_ProtectionType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("footprintType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FootprintType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_FootprintType"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("detectionCondition");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DetectionCondition"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_DetectionCondition"));
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
