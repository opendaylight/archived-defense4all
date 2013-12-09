/**
 * Network.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.Networks;


/**
 * This structure describes the parameters of a Network
 */
public class Network  implements java.io.Serializable {
    /* The Name for the IP Object Rule, must be unique. */
    private java.lang.String name;

    /* SubIndex. */
    private long index;

    /* IP address */
    private java.lang.String address;

    /* IP address mask. */
    private java.lang.String mask;

    /* The from IP Address of the range. */
    private java.lang.String fromIp;

    /* The to IP Address of the range. */
    private java.lang.String toIp;

    /* This variable indicates the mode of the entry. This is either
     * a range or an IP address and mask. */
    private com.radware.defenseflow.dp.pojos.Classes.Networks.Network_Mode mode;

    public Network() {
    }

    public Network(
           java.lang.String name,
           long index,
           java.lang.String address,
           java.lang.String mask,
           java.lang.String fromIp,
           java.lang.String toIp,
           com.radware.defenseflow.dp.pojos.Classes.Networks.Network_Mode mode) {
           this.name = name;
           this.index = index;
           this.address = address;
           this.mask = mask;
           this.fromIp = fromIp;
           this.toIp = toIp;
           this.mode = mode;
    }


    /**
     * Gets the name value for this Network.
     * 
     * @return name   * The Name for the IP Object Rule, must be unique.
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this Network.
     * 
     * @param name   * The Name for the IP Object Rule, must be unique.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the index value for this Network.
     * 
     * @return index   * SubIndex.
     */
    public long getIndex() {
        return index;
    }


    /**
     * Sets the index value for this Network.
     * 
     * @param index   * SubIndex.
     */
    public void setIndex(long index) {
        this.index = index;
    }


    /**
     * Gets the address value for this Network.
     * 
     * @return address   * IP address
     */
    public java.lang.String getAddress() {
        return address;
    }


    /**
     * Sets the address value for this Network.
     * 
     * @param address   * IP address
     */
    public void setAddress(java.lang.String address) {
        this.address = address;
    }


    /**
     * Gets the mask value for this Network.
     * 
     * @return mask   * IP address mask.
     */
    public java.lang.String getMask() {
        return mask;
    }


    /**
     * Sets the mask value for this Network.
     * 
     * @param mask   * IP address mask.
     */
    public void setMask(java.lang.String mask) {
        this.mask = mask;
    }


    /**
     * Gets the fromIp value for this Network.
     * 
     * @return fromIp   * The from IP Address of the range.
     */
    public java.lang.String getFromIp() {
        return fromIp;
    }


    /**
     * Sets the fromIp value for this Network.
     * 
     * @param fromIp   * The from IP Address of the range.
     */
    public void setFromIp(java.lang.String fromIp) {
        this.fromIp = fromIp;
    }


    /**
     * Gets the toIp value for this Network.
     * 
     * @return toIp   * The to IP Address of the range.
     */
    public java.lang.String getToIp() {
        return toIp;
    }


    /**
     * Sets the toIp value for this Network.
     * 
     * @param toIp   * The to IP Address of the range.
     */
    public void setToIp(java.lang.String toIp) {
        this.toIp = toIp;
    }


    /**
     * Gets the mode value for this Network.
     * 
     * @return mode   * This variable indicates the mode of the entry. This is either
     * a range or an IP address and mask.
     */
    public com.radware.defenseflow.dp.pojos.Classes.Networks.Network_Mode getMode() {
        return mode;
    }


    /**
     * Sets the mode value for this Network.
     * 
     * @param mode   * This variable indicates the mode of the entry. This is either
     * a range or an IP address and mask.
     */
    public void setMode(com.radware.defenseflow.dp.pojos.Classes.Networks.Network_Mode mode) {
        this.mode = mode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Network)) return false;
        Network other = (Network) obj;
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
            this.index == other.getIndex() &&
            ((this.address==null && other.getAddress()==null) || 
             (this.address!=null &&
              this.address.equals(other.getAddress()))) &&
            ((this.mask==null && other.getMask()==null) || 
             (this.mask!=null &&
              this.mask.equals(other.getMask()))) &&
            ((this.fromIp==null && other.getFromIp()==null) || 
             (this.fromIp!=null &&
              this.fromIp.equals(other.getFromIp()))) &&
            ((this.toIp==null && other.getToIp()==null) || 
             (this.toIp!=null &&
              this.toIp.equals(other.getToIp()))) &&
            ((this.mode==null && other.getMode()==null) || 
             (this.mode!=null &&
              this.mode.equals(other.getMode())));
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
        _hashCode += new Long(getIndex()).hashCode();
        if (getAddress() != null) {
            _hashCode += getAddress().hashCode();
        }
        if (getMask() != null) {
            _hashCode += getMask().hashCode();
        }
        if (getFromIp() != null) {
            _hashCode += getFromIp().hashCode();
        }
        if (getToIp() != null) {
            _hashCode += getToIp().hashCode();
        }
        if (getMode() != null) {
            _hashCode += getMode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Network.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Classes.Networks", "Network"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("index");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Index"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("address");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Address"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mask");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Mask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("fromIp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FromIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("toIp");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ToIp"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("mode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Mode"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Classes.Networks", "Network_Mode"));
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
