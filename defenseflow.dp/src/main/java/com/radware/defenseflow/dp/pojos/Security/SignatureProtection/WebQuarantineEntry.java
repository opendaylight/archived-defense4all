/**
 * WebQuarantineEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a WebQuarantineEntry
 */
public class WebQuarantineEntry  implements java.io.Serializable {
    private java.lang.String webQuarantineAddress;

    private java.lang.String webQuarantinePolicy;

    private java.lang.Long webQuarantineTimeHours;

    private java.lang.Long webQuarantineTimeMin;

    public WebQuarantineEntry() {
    }

    public WebQuarantineEntry(
           java.lang.String webQuarantineAddress,
           java.lang.String webQuarantinePolicy,
           java.lang.Long webQuarantineTimeHours,
           java.lang.Long webQuarantineTimeMin) {
           this.webQuarantineAddress = webQuarantineAddress;
           this.webQuarantinePolicy = webQuarantinePolicy;
           this.webQuarantineTimeHours = webQuarantineTimeHours;
           this.webQuarantineTimeMin = webQuarantineTimeMin;
    }


    /**
     * Gets the webQuarantineAddress value for this WebQuarantineEntry.
     * 
     * @return webQuarantineAddress
     */
    public java.lang.String getWebQuarantineAddress() {
        return webQuarantineAddress;
    }


    /**
     * Sets the webQuarantineAddress value for this WebQuarantineEntry.
     * 
     * @param webQuarantineAddress
     */
    public void setWebQuarantineAddress(java.lang.String webQuarantineAddress) {
        this.webQuarantineAddress = webQuarantineAddress;
    }


    /**
     * Gets the webQuarantinePolicy value for this WebQuarantineEntry.
     * 
     * @return webQuarantinePolicy
     */
    public java.lang.String getWebQuarantinePolicy() {
        return webQuarantinePolicy;
    }


    /**
     * Sets the webQuarantinePolicy value for this WebQuarantineEntry.
     * 
     * @param webQuarantinePolicy
     */
    public void setWebQuarantinePolicy(java.lang.String webQuarantinePolicy) {
        this.webQuarantinePolicy = webQuarantinePolicy;
    }


    /**
     * Gets the webQuarantineTimeHours value for this WebQuarantineEntry.
     * 
     * @return webQuarantineTimeHours
     */
    public java.lang.Long getWebQuarantineTimeHours() {
        return webQuarantineTimeHours;
    }


    /**
     * Sets the webQuarantineTimeHours value for this WebQuarantineEntry.
     * 
     * @param webQuarantineTimeHours
     */
    public void setWebQuarantineTimeHours(java.lang.Long webQuarantineTimeHours) {
        this.webQuarantineTimeHours = webQuarantineTimeHours;
    }


    /**
     * Gets the webQuarantineTimeMin value for this WebQuarantineEntry.
     * 
     * @return webQuarantineTimeMin
     */
    public java.lang.Long getWebQuarantineTimeMin() {
        return webQuarantineTimeMin;
    }


    /**
     * Sets the webQuarantineTimeMin value for this WebQuarantineEntry.
     * 
     * @param webQuarantineTimeMin
     */
    public void setWebQuarantineTimeMin(java.lang.Long webQuarantineTimeMin) {
        this.webQuarantineTimeMin = webQuarantineTimeMin;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof WebQuarantineEntry)) return false;
        WebQuarantineEntry other = (WebQuarantineEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.webQuarantineAddress==null && other.getWebQuarantineAddress()==null) || 
             (this.webQuarantineAddress!=null &&
              this.webQuarantineAddress.equals(other.getWebQuarantineAddress()))) &&
            ((this.webQuarantinePolicy==null && other.getWebQuarantinePolicy()==null) || 
             (this.webQuarantinePolicy!=null &&
              this.webQuarantinePolicy.equals(other.getWebQuarantinePolicy()))) &&
            ((this.webQuarantineTimeHours==null && other.getWebQuarantineTimeHours()==null) || 
             (this.webQuarantineTimeHours!=null &&
              this.webQuarantineTimeHours.equals(other.getWebQuarantineTimeHours()))) &&
            ((this.webQuarantineTimeMin==null && other.getWebQuarantineTimeMin()==null) || 
             (this.webQuarantineTimeMin!=null &&
              this.webQuarantineTimeMin.equals(other.getWebQuarantineTimeMin())));
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
        if (getWebQuarantineAddress() != null) {
            _hashCode += getWebQuarantineAddress().hashCode();
        }
        if (getWebQuarantinePolicy() != null) {
            _hashCode += getWebQuarantinePolicy().hashCode();
        }
        if (getWebQuarantineTimeHours() != null) {
            _hashCode += getWebQuarantineTimeHours().hashCode();
        }
        if (getWebQuarantineTimeMin() != null) {
            _hashCode += getWebQuarantineTimeMin().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(WebQuarantineEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "WebQuarantineEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webQuarantineAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "webQuarantineAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webQuarantinePolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "webQuarantinePolicy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webQuarantineTimeHours");
        elemField.setXmlName(new javax.xml.namespace.QName("", "webQuarantineTimeHours"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("webQuarantineTimeMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "webQuarantineTimeMin"));
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
