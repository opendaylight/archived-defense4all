/**
 * Quotas.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;


/**
 * This structure describes the parameters of a Quotas
 */
public class Quotas  implements java.io.Serializable {
    /* The protection way inbound / outbound. */
    private com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction;

    /* Tcp quota value. */
    private java.lang.Long TCPquota;

    /* Udp quota value. */
    private java.lang.Long UDPquota;

    /* Icmp quota value. */
    private java.lang.Long ICMPquota;

    /* Igmp quota value. */
    private java.lang.Long IGMPquota;

    public Quotas() {
    }

    public Quotas(
           com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction,
           java.lang.Long TCPquota,
           java.lang.Long UDPquota,
           java.lang.Long ICMPquota,
           java.lang.Long IGMPquota) {
           this.direction = direction;
           this.TCPquota = TCPquota;
           this.UDPquota = UDPquota;
           this.ICMPquota = ICMPquota;
           this.IGMPquota = IGMPquota;
    }


    /**
     * Gets the direction value for this Quotas.
     * 
     * @return direction   * The protection way inbound / outbound.
     */
    public com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this Quotas.
     * 
     * @param direction   * The protection way inbound / outbound.
     */
    public void setDirection(com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Quotas_Direction direction) {
        this.direction = direction;
    }


    /**
     * Gets the TCPquota value for this Quotas.
     * 
     * @return TCPquota   * Tcp quota value.
     */
    public java.lang.Long getTCPquota() {
        return TCPquota;
    }


    /**
     * Sets the TCPquota value for this Quotas.
     * 
     * @param TCPquota   * Tcp quota value.
     */
    public void setTCPquota(java.lang.Long TCPquota) {
        this.TCPquota = TCPquota;
    }


    /**
     * Gets the UDPquota value for this Quotas.
     * 
     * @return UDPquota   * Udp quota value.
     */
    public java.lang.Long getUDPquota() {
        return UDPquota;
    }


    /**
     * Sets the UDPquota value for this Quotas.
     * 
     * @param UDPquota   * Udp quota value.
     */
    public void setUDPquota(java.lang.Long UDPquota) {
        this.UDPquota = UDPquota;
    }


    /**
     * Gets the ICMPquota value for this Quotas.
     * 
     * @return ICMPquota   * Icmp quota value.
     */
    public java.lang.Long getICMPquota() {
        return ICMPquota;
    }


    /**
     * Sets the ICMPquota value for this Quotas.
     * 
     * @param ICMPquota   * Icmp quota value.
     */
    public void setICMPquota(java.lang.Long ICMPquota) {
        this.ICMPquota = ICMPquota;
    }


    /**
     * Gets the IGMPquota value for this Quotas.
     * 
     * @return IGMPquota   * Igmp quota value.
     */
    public java.lang.Long getIGMPquota() {
        return IGMPquota;
    }


    /**
     * Sets the IGMPquota value for this Quotas.
     * 
     * @param IGMPquota   * Igmp quota value.
     */
    public void setIGMPquota(java.lang.Long IGMPquota) {
        this.IGMPquota = IGMPquota;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Quotas)) return false;
        Quotas other = (Quotas) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection()))) &&
            ((this.TCPquota==null && other.getTCPquota()==null) || 
             (this.TCPquota!=null &&
              this.TCPquota.equals(other.getTCPquota()))) &&
            ((this.UDPquota==null && other.getUDPquota()==null) || 
             (this.UDPquota!=null &&
              this.UDPquota.equals(other.getUDPquota()))) &&
            ((this.ICMPquota==null && other.getICMPquota()==null) || 
             (this.ICMPquota!=null &&
              this.ICMPquota.equals(other.getICMPquota()))) &&
            ((this.IGMPquota==null && other.getIGMPquota()==null) || 
             (this.IGMPquota!=null &&
              this.IGMPquota.equals(other.getIGMPquota())));
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
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        if (getTCPquota() != null) {
            _hashCode += getTCPquota().hashCode();
        }
        if (getUDPquota() != null) {
            _hashCode += getUDPquota().hashCode();
        }
        if (getICMPquota() != null) {
            _hashCode += getICMPquota().hashCode();
        }
        if (getIGMPquota() != null) {
            _hashCode += getIGMPquota().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Quotas.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "Quotas_Direction"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("TCPquota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TCPquota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("UDPquota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "UDPquota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ICMPquota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ICMPquota"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("IGMPquota");
        elemField.setXmlName(new javax.xml.namespace.QName("", "IGMPquota"));
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
