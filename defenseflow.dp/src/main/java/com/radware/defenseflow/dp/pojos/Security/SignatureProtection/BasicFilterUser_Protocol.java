/**
 * BasicFilterUser_Protocol.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class BasicFilterUser_Protocol implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BasicFilterUser_Protocol(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _ip = "ip";
    public static final java.lang.String _tcp = "tcp";
    public static final java.lang.String _udp = "udp";
    public static final java.lang.String _icmp = "icmp";
    public static final java.lang.String _nonIp = "nonIp";
    public static final java.lang.String _icmpV6 = "icmpV6";
    public static final BasicFilterUser_Protocol ip = new BasicFilterUser_Protocol(_ip);
    public static final BasicFilterUser_Protocol tcp = new BasicFilterUser_Protocol(_tcp);
    public static final BasicFilterUser_Protocol udp = new BasicFilterUser_Protocol(_udp);
    public static final BasicFilterUser_Protocol icmp = new BasicFilterUser_Protocol(_icmp);
    public static final BasicFilterUser_Protocol nonIp = new BasicFilterUser_Protocol(_nonIp);
    public static final BasicFilterUser_Protocol icmpV6 = new BasicFilterUser_Protocol(_icmpV6);
    public java.lang.String getValue() { return _value_;}
    public static BasicFilterUser_Protocol fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        BasicFilterUser_Protocol enumeration = (BasicFilterUser_Protocol)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static BasicFilterUser_Protocol fromString(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        return fromValue(value);
    }
    public boolean equals(java.lang.Object obj) {return (obj == this);}
    public int hashCode() { return toString().hashCode();}
    public java.lang.String toString() { return _value_;}
    public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumSerializer(
            _javaType, _xmlType);
    }
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new org.apache.axis.encoding.ser.EnumDeserializer(
            _javaType, _xmlType);
    }
    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BasicFilterUser_Protocol.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_Protocol"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
