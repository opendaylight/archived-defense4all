/**
 * DnsProtectionDynamicStateFp_ProtectionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class DnsProtectionDynamicStateFp_ProtectionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DnsProtectionDynamicStateFp_ProtectionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "dns-naptr";
    public static final java.lang.String _value2 = "dns-text";
    public static final java.lang.String _value3 = "dns-other";
    public static final java.lang.String _value4 = "dns-aaaa";
    public static final java.lang.String _value5 = "dns-a";
    public static final java.lang.String _value6 = "dns-mx";
    public static final java.lang.String _value7 = "dns-srv";
    public static final java.lang.String _value8 = "dns-ptr";
    public static final java.lang.String _value9 = "dns-soa";
    public static final DnsProtectionDynamicStateFp_ProtectionType value1 = new DnsProtectionDynamicStateFp_ProtectionType(_value1);
    public static final DnsProtectionDynamicStateFp_ProtectionType value2 = new DnsProtectionDynamicStateFp_ProtectionType(_value2);
    public static final DnsProtectionDynamicStateFp_ProtectionType value3 = new DnsProtectionDynamicStateFp_ProtectionType(_value3);
    public static final DnsProtectionDynamicStateFp_ProtectionType value4 = new DnsProtectionDynamicStateFp_ProtectionType(_value4);
    public static final DnsProtectionDynamicStateFp_ProtectionType value5 = new DnsProtectionDynamicStateFp_ProtectionType(_value5);
    public static final DnsProtectionDynamicStateFp_ProtectionType value6 = new DnsProtectionDynamicStateFp_ProtectionType(_value6);
    public static final DnsProtectionDynamicStateFp_ProtectionType value7 = new DnsProtectionDynamicStateFp_ProtectionType(_value7);
    public static final DnsProtectionDynamicStateFp_ProtectionType value8 = new DnsProtectionDynamicStateFp_ProtectionType(_value8);
    public static final DnsProtectionDynamicStateFp_ProtectionType value9 = new DnsProtectionDynamicStateFp_ProtectionType(_value9);
    public java.lang.String getValue() { return _value_;}
    public static DnsProtectionDynamicStateFp_ProtectionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DnsProtectionDynamicStateFp_ProtectionType enumeration = (DnsProtectionDynamicStateFp_ProtectionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DnsProtectionDynamicStateFp_ProtectionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DnsProtectionDynamicStateFp_ProtectionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp_ProtectionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
