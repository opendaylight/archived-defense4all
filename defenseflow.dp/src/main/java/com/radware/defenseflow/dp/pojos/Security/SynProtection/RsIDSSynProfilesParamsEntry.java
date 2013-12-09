/**
 * RsIDSSynProfilesParamsEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;


/**
 * This structure describes the parameters of a rsIDSSynProfilesParamsEntry
 */
public class RsIDSSynProfilesParamsEntry  implements java.io.Serializable {
    private java.lang.String name;

    private com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_authenticationMethod authenticationMethod;

    private com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationStatus httpAuthenticationStatus;

    private com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationMethod httpAuthenticationMethod;

    private com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_TCPResetStatus TCPResetStatus;

    public RsIDSSynProfilesParamsEntry() {
    }

    public RsIDSSynProfilesParamsEntry(
           java.lang.String name,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_authenticationMethod authenticationMethod,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationStatus httpAuthenticationStatus,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationMethod httpAuthenticationMethod,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_TCPResetStatus TCPResetStatus) {
           this.name = name;
           this.authenticationMethod = authenticationMethod;
           this.httpAuthenticationStatus = httpAuthenticationStatus;
           this.httpAuthenticationMethod = httpAuthenticationMethod;
           this.TCPResetStatus = TCPResetStatus;
    }


    /**
     * Gets the name value for this RsIDSSynProfilesParamsEntry.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this RsIDSSynProfilesParamsEntry.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the authenticationMethod value for this RsIDSSynProfilesParamsEntry.
     * 
     * @return authenticationMethod
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_authenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }


    /**
     * Sets the authenticationMethod value for this RsIDSSynProfilesParamsEntry.
     * 
     * @param authenticationMethod
     */
    public void setAuthenticationMethod(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_authenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }


    /**
     * Gets the httpAuthenticationStatus value for this RsIDSSynProfilesParamsEntry.
     * 
     * @return httpAuthenticationStatus
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationStatus getHttpAuthenticationStatus() {
        return httpAuthenticationStatus;
    }


    /**
     * Sets the httpAuthenticationStatus value for this RsIDSSynProfilesParamsEntry.
     * 
     * @param httpAuthenticationStatus
     */
    public void setHttpAuthenticationStatus(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationStatus httpAuthenticationStatus) {
        this.httpAuthenticationStatus = httpAuthenticationStatus;
    }


    /**
     * Gets the httpAuthenticationMethod value for this RsIDSSynProfilesParamsEntry.
     * 
     * @return httpAuthenticationMethod
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationMethod getHttpAuthenticationMethod() {
        return httpAuthenticationMethod;
    }


    /**
     * Sets the httpAuthenticationMethod value for this RsIDSSynProfilesParamsEntry.
     * 
     * @param httpAuthenticationMethod
     */
    public void setHttpAuthenticationMethod(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_HttpAuthenticationMethod httpAuthenticationMethod) {
        this.httpAuthenticationMethod = httpAuthenticationMethod;
    }


    /**
     * Gets the TCPResetStatus value for this RsIDSSynProfilesParamsEntry.
     * 
     * @return TCPResetStatus
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_TCPResetStatus getTCPResetStatus() {
        return TCPResetStatus;
    }


    /**
     * Sets the TCPResetStatus value for this RsIDSSynProfilesParamsEntry.
     * 
     * @param TCPResetStatus
     */
    public void setTCPResetStatus(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesParamsEntry_TCPResetStatus TCPResetStatus) {
        this.TCPResetStatus = TCPResetStatus;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsIDSSynProfilesParamsEntry)) return false;
        RsIDSSynProfilesParamsEntry other = (RsIDSSynProfilesParamsEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.authenticationMethod==null && other.getAuthenticationMethod()==null) || 
             (this.authenticationMethod!=null &&
              this.authenticationMethod.equals(other.getAuthenticationMethod()))) &&
            ((this.httpAuthenticationStatus==null && other.getHttpAuthenticationStatus()==null) || 
             (this.httpAuthenticationStatus!=null &&
              this.httpAuthenticationStatus.equals(other.getHttpAuthenticationStatus()))) &&
            ((this.httpAuthenticationMethod==null && other.getHttpAuthenticationMethod()==null) || 
             (this.httpAuthenticationMethod!=null &&
              this.httpAuthenticationMethod.equals(other.getHttpAuthenticationMethod()))) &&
            ((this.TCPResetStatus==null && other.getTCPResetStatus()==null) || 
             (this.TCPResetStatus!=null &&
              this.TCPResetStatus.equals(other.getTCPResetStatus())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getAuthenticationMethod() != null) {
            _hashCode += getAuthenticationMethod().hashCode();
        }
        if (getHttpAuthenticationStatus() != null) {
            _hashCode += getHttpAuthenticationStatus().hashCode();
        }
        if (getHttpAuthenticationMethod() != null) {
            _hashCode += getHttpAuthenticationMethod().hashCode();
        }
        if (getTCPResetStatus() != null) {
            _hashCode += getTCPResetStatus().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsIDSSynProfilesParamsEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesParamsEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("authenticationMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "authenticationMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesParamsEntry_authenticationMethod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpAuthenticationStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "HttpAuthenticationStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesParamsEntry_HttpAuthenticationStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("httpAuthenticationMethod");
        elemField.setXmlName(new javax.xml.namespace.QName("", "HttpAuthenticationMethod"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesParamsEntry_HttpAuthenticationMethod"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TCPResetStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TCPResetStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSSynProfilesParamsEntry_TCPResetStatus"));
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
