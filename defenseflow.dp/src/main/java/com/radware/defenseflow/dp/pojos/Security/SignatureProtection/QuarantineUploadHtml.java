/**
 * QuarantineUploadHtml.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a QuarantineUploadHtml
 */
public class QuarantineUploadHtml  implements java.io.Serializable {
    private java.lang.String quarantineDownloadPolicy;

    private java.lang.String quarantineDownloadAddress;

    private java.lang.String quarantineDownloadfile;

    public QuarantineUploadHtml() {
    }

    public QuarantineUploadHtml(
           java.lang.String quarantineDownloadPolicy,
           java.lang.String quarantineDownloadAddress,
           java.lang.String quarantineDownloadfile) {
           this.quarantineDownloadPolicy = quarantineDownloadPolicy;
           this.quarantineDownloadAddress = quarantineDownloadAddress;
           this.quarantineDownloadfile = quarantineDownloadfile;
    }


    /**
     * Gets the quarantineDownloadPolicy value for this QuarantineUploadHtml.
     * 
     * @return quarantineDownloadPolicy
     */
    public java.lang.String getQuarantineDownloadPolicy() {
        return quarantineDownloadPolicy;
    }


    /**
     * Sets the quarantineDownloadPolicy value for this QuarantineUploadHtml.
     * 
     * @param quarantineDownloadPolicy
     */
    public void setQuarantineDownloadPolicy(java.lang.String quarantineDownloadPolicy) {
        this.quarantineDownloadPolicy = quarantineDownloadPolicy;
    }


    /**
     * Gets the quarantineDownloadAddress value for this QuarantineUploadHtml.
     * 
     * @return quarantineDownloadAddress
     */
    public java.lang.String getQuarantineDownloadAddress() {
        return quarantineDownloadAddress;
    }


    /**
     * Sets the quarantineDownloadAddress value for this QuarantineUploadHtml.
     * 
     * @param quarantineDownloadAddress
     */
    public void setQuarantineDownloadAddress(java.lang.String quarantineDownloadAddress) {
        this.quarantineDownloadAddress = quarantineDownloadAddress;
    }


    /**
     * Gets the quarantineDownloadfile value for this QuarantineUploadHtml.
     * 
     * @return quarantineDownloadfile
     */
    public java.lang.String getQuarantineDownloadfile() {
        return quarantineDownloadfile;
    }


    /**
     * Sets the quarantineDownloadfile value for this QuarantineUploadHtml.
     * 
     * @param quarantineDownloadfile
     */
    public void setQuarantineDownloadfile(java.lang.String quarantineDownloadfile) {
        this.quarantineDownloadfile = quarantineDownloadfile;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof QuarantineUploadHtml)) return false;
        QuarantineUploadHtml other = (QuarantineUploadHtml) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.quarantineDownloadPolicy==null && other.getQuarantineDownloadPolicy()==null) || 
             (this.quarantineDownloadPolicy!=null &&
              this.quarantineDownloadPolicy.equals(other.getQuarantineDownloadPolicy()))) &&
            ((this.quarantineDownloadAddress==null && other.getQuarantineDownloadAddress()==null) || 
             (this.quarantineDownloadAddress!=null &&
              this.quarantineDownloadAddress.equals(other.getQuarantineDownloadAddress()))) &&
            ((this.quarantineDownloadfile==null && other.getQuarantineDownloadfile()==null) || 
             (this.quarantineDownloadfile!=null &&
              this.quarantineDownloadfile.equals(other.getQuarantineDownloadfile())));
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
        if (getQuarantineDownloadPolicy() != null) {
            _hashCode += getQuarantineDownloadPolicy().hashCode();
        }
        if (getQuarantineDownloadAddress() != null) {
            _hashCode += getQuarantineDownloadAddress().hashCode();
        }
        if (getQuarantineDownloadfile() != null) {
            _hashCode += getQuarantineDownloadfile().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(QuarantineUploadHtml.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QuarantineUploadHtml"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineDownloadPolicy");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineDownloadPolicy"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineDownloadAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineDownloadAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("quarantineDownloadfile");
        elemField.setXmlName(new javax.xml.namespace.QName("", "QuarantineDownloadfile"));
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
