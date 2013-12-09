/**
 * QuarantineActions.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a QuarantineActions
 */
public class QuarantineActions  implements java.io.Serializable {
    private java.lang.String quarantinePolicyName;

    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionType quarantineActionType;

    private java.lang.String quarantineRedirectIp;

    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionMetadata quarantineActionMetadata;

    private java.lang.Long quarantineAgingHour;

    private java.lang.Long quarantineAgingMin;

    public QuarantineActions() {
    }

    public QuarantineActions(
           java.lang.String quarantinePolicyName,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionType quarantineActionType,
           java.lang.String quarantineRedirectIp,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionMetadata quarantineActionMetadata,
           java.lang.Long quarantineAgingHour,
           java.lang.Long quarantineAgingMin) {
           this.quarantinePolicyName = quarantinePolicyName;
           this.quarantineActionType = quarantineActionType;
           this.quarantineRedirectIp = quarantineRedirectIp;
           this.quarantineActionMetadata = quarantineActionMetadata;
           this.quarantineAgingHour = quarantineAgingHour;
           this.quarantineAgingMin = quarantineAgingMin;
    }


    /**
     * Gets the quarantinePolicyName value for this QuarantineActions.
     * 
     * @return quarantinePolicyName
     */
    public java.lang.String getQuarantinePolicyName() {
        return quarantinePolicyName;
    }


    /**
     * Sets the quarantinePolicyName value for this QuarantineActions.
     * 
     * @param quarantinePolicyName
     */
    public void setQuarantinePolicyName(java.lang.String quarantinePolicyName) {
        this.quarantinePolicyName = quarantinePolicyName;
    }


    /**
     * Gets the quarantineActionType value for this QuarantineActions.
     * 
     * @return quarantineActionType
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionType getQuarantineActionType() {
        return quarantineActionType;
    }


    /**
     * Sets the quarantineActionType value for this QuarantineActions.
     * 
     * @param quarantineActionType
     */
    public void setQuarantineActionType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionType quarantineActionType) {
        this.quarantineActionType = quarantineActionType;
    }


    /**
     * Gets the quarantineRedirectIp value for this QuarantineActions.
     * 
     * @return quarantineRedirectIp
     */
    public java.lang.String getQuarantineRedirectIp() {
        return quarantineRedirectIp;
    }


    /**
     * Sets the quarantineRedirectIp value for this QuarantineActions.
     * 
     * @param quarantineRedirectIp
     */
    public void setQuarantineRedirectIp(java.lang.String quarantineRedirectIp) {
        this.quarantineRedirectIp = quarantineRedirectIp;
    }


    /**
     * Gets the quarantineActionMetadata value for this QuarantineActions.
     * 
     * @return quarantineActionMetadata
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionMetadata getQuarantineActionMetadata() {
        return quarantineActionMetadata;
    }


    /**
     * Sets the quarantineActionMetadata value for this QuarantineActions.
     * 
     * @param quarantineActionMetadata
     */
    public void setQuarantineActionMetadata(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.QuarantineActions_QuarantineActionMetadata quarantineActionMetadata) {
        this.quarantineActionMetadata = quarantineActionMetadata;
    }


    /**
     * Gets the quarantineAgingHour value for this QuarantineActions.
     * 
     * @return quarantineAgingHour
     */
    public java.lang.Long getQuarantineAgingHour() {
        return quarantineAgingHour;
    }


    /**
     * Sets the quarantineAgingHour value for this QuarantineActions.
     * 
     * @param quarantineAgingHour
     */
    public void setQuarantineAgingHour(java.lang.Long quarantineAgingHour) {
        this.quarantineAgingHour = quarantineAgingHour;
    }


    /**
     * Gets the quarantineAgingMin value for this QuarantineActions.
     * 
     * @return quarantineAgingMin
     */
    public java.lang.Long getQuarantineAgingMin() {
        return quarantineAgingMin;
    }


    /**
     * Sets the quarantineAgingMin value for this QuarantineActions.
     * 
     * @param quarantineAgingMin
     */
    public void setQuarantineAgingMin(java.lang.Long quarantineAgingMin) {
        this.quarantineAgingMin = quarantineAgingMin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QuarantineActions)) return false;
        QuarantineActions other = (QuarantineActions) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.quarantinePolicyName==null && other.getQuarantinePolicyName()==null) || 
             (this.quarantinePolicyName!=null &&
              this.quarantinePolicyName.equals(other.getQuarantinePolicyName()))) &&
            ((this.quarantineActionType==null && other.getQuarantineActionType()==null) || 
             (this.quarantineActionType!=null &&
              this.quarantineActionType.equals(other.getQuarantineActionType()))) &&
            ((this.quarantineRedirectIp==null && other.getQuarantineRedirectIp()==null) || 
             (this.quarantineRedirectIp!=null &&
              this.quarantineRedirectIp.equals(other.getQuarantineRedirectIp()))) &&
            ((this.quarantineActionMetadata==null && other.getQuarantineActionMetadata()==null) || 
             (this.quarantineActionMetadata!=null &&
              this.quarantineActionMetadata.equals(other.getQuarantineActionMetadata()))) &&
            ((this.quarantineAgingHour==null && other.getQuarantineAgingHour()==null) || 
             (this.quarantineAgingHour!=null &&
              this.quarantineAgingHour.equals(other.getQuarantineAgingHour()))) &&
            ((this.quarantineAgingMin==null && other.getQuarantineAgingMin()==null) || 
             (this.quarantineAgingMin!=null &&
              this.quarantineAgingMin.equals(other.getQuarantineAgingMin())));
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
        if (getQuarantinePolicyName() != null) {
            _hashCode += getQuarantinePolicyName().hashCode();
        }
        if (getQuarantineActionType() != null) {
            _hashCode += getQuarantineActionType().hashCode();
        }
        if (getQuarantineRedirectIp() != null) {
            _hashCode += getQuarantineRedirectIp().hashCode();
        }
        if (getQuarantineActionMetadata() != null) {
            _hashCode += getQuarantineActionMetadata().hashCode();
        }
        if (getQuarantineAgingHour() != null) {
            _hashCode += getQuarantineAgingHour().hashCode();
        }
        if (getQuarantineAgingMin() != null) {
            _hashCode += getQuarantineAgingMin().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QuarantineActions.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QuarantineActions"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantinePolicyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantinePolicyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineActionType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineActionType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QuarantineActions_QuarantineActionType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineRedirectIp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineRedirectIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineActionMetadata");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineActionMetadata"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QuarantineActions_QuarantineActionMetadata"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineAgingHour");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineAgingHour"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineAgingMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineAgingMin"));
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
