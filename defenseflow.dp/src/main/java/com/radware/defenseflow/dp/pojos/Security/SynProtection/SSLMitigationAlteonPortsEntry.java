/**
 * SSLMitigationAlteonPortsEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;


/**
 * This structure describes the parameters of a SSLMitigationAlteonPortsEntry
 */
public class SSLMitigationAlteonPortsEntry  implements java.io.Serializable {
    /* The Index of entry. */
    private long index;

    /* The Inbound physical port of Alteon. */
    private java.lang.Long inboundPort;

    /* The Outbound physical port of Alteon. */
    private java.lang.Long outboundPort;

    public SSLMitigationAlteonPortsEntry() {
    }

    public SSLMitigationAlteonPortsEntry(
           long index,
           java.lang.Long inboundPort,
           java.lang.Long outboundPort) {
           this.index = index;
           this.inboundPort = inboundPort;
           this.outboundPort = outboundPort;
    }


    /**
     * Gets the index value for this SSLMitigationAlteonPortsEntry.
     * 
     * @return index   * The Index of entry.
     */
    public long getIndex() {
        return index;
    }


    /**
     * Sets the index value for this SSLMitigationAlteonPortsEntry.
     * 
     * @param index   * The Index of entry.
     */
    public void setIndex(long index) {
        this.index = index;
    }


    /**
     * Gets the inboundPort value for this SSLMitigationAlteonPortsEntry.
     * 
     * @return inboundPort   * The Inbound physical port of Alteon.
     */
    public java.lang.Long getInboundPort() {
        return inboundPort;
    }


    /**
     * Sets the inboundPort value for this SSLMitigationAlteonPortsEntry.
     * 
     * @param inboundPort   * The Inbound physical port of Alteon.
     */
    public void setInboundPort(java.lang.Long inboundPort) {
        this.inboundPort = inboundPort;
    }


    /**
     * Gets the outboundPort value for this SSLMitigationAlteonPortsEntry.
     * 
     * @return outboundPort   * The Outbound physical port of Alteon.
     */
    public java.lang.Long getOutboundPort() {
        return outboundPort;
    }


    /**
     * Sets the outboundPort value for this SSLMitigationAlteonPortsEntry.
     * 
     * @param outboundPort   * The Outbound physical port of Alteon.
     */
    public void setOutboundPort(java.lang.Long outboundPort) {
        this.outboundPort = outboundPort;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SSLMitigationAlteonPortsEntry)) return false;
        SSLMitigationAlteonPortsEntry other = (SSLMitigationAlteonPortsEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.index == other.getIndex() &&
            ((this.inboundPort==null && other.getInboundPort()==null) || 
             (this.inboundPort!=null &&
              this.inboundPort.equals(other.getInboundPort()))) &&
            ((this.outboundPort==null && other.getOutboundPort()==null) || 
             (this.outboundPort!=null &&
              this.outboundPort.equals(other.getOutboundPort())));
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
        _hashCode += new Long(getIndex()).hashCode();
        if (getInboundPort() != null) {
            _hashCode += getInboundPort().hashCode();
        }
        if (getOutboundPort() != null) {
            _hashCode += getOutboundPort().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SSLMitigationAlteonPortsEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "SSLMitigationAlteonPortsEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("index");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Index"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("inboundPort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "InboundPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("outboundPort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OutboundPort"));
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
