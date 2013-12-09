/**
 * DnsProtectionDynamicStateFp_FootprintType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class DnsProtectionDynamicStateFp_FootprintType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DnsProtectionDynamicStateFp_FootprintType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "packet-size";
    public static final java.lang.String _value2 = "frag-offset-ipv6";
    public static final java.lang.String _value3 = "source-ip";
    public static final java.lang.String _value4 = "id-num";
    public static final java.lang.String _value5 = "checksum";
    public static final java.lang.String _value6 = "dns-flags";
    public static final java.lang.String _value7 = "packet-size-ipv6";
    public static final java.lang.String _value8 = "destination-ip";
    public static final java.lang.String _value9 = "dns-ancount";
    public static final java.lang.String _value10 = "vlan-tag";
    public static final java.lang.String _value11 = "destination-ip-ipv6";
    public static final java.lang.String _value12 = "source-port";
    public static final java.lang.String _value13 = "dns-id-num";
    public static final java.lang.String _value14 = "flow-label-ipv6";
    public static final java.lang.String _value15 = "fragment";
    public static final java.lang.String _value16 = "frag-header-id-ipv6";
    public static final java.lang.String _value17 = "frag-offset";
    public static final java.lang.String _value18 = "dns-qname";
    public static final java.lang.String _value19 = "source-ip-ipv6";
    public static final java.lang.String _value20 = "tos";
    public static final java.lang.String _value21 = "ttl";
    public static final java.lang.String _value22 = "dns-qcount";
    public static final DnsProtectionDynamicStateFp_FootprintType value1 = new DnsProtectionDynamicStateFp_FootprintType(_value1);
    public static final DnsProtectionDynamicStateFp_FootprintType value2 = new DnsProtectionDynamicStateFp_FootprintType(_value2);
    public static final DnsProtectionDynamicStateFp_FootprintType value3 = new DnsProtectionDynamicStateFp_FootprintType(_value3);
    public static final DnsProtectionDynamicStateFp_FootprintType value4 = new DnsProtectionDynamicStateFp_FootprintType(_value4);
    public static final DnsProtectionDynamicStateFp_FootprintType value5 = new DnsProtectionDynamicStateFp_FootprintType(_value5);
    public static final DnsProtectionDynamicStateFp_FootprintType value6 = new DnsProtectionDynamicStateFp_FootprintType(_value6);
    public static final DnsProtectionDynamicStateFp_FootprintType value7 = new DnsProtectionDynamicStateFp_FootprintType(_value7);
    public static final DnsProtectionDynamicStateFp_FootprintType value8 = new DnsProtectionDynamicStateFp_FootprintType(_value8);
    public static final DnsProtectionDynamicStateFp_FootprintType value9 = new DnsProtectionDynamicStateFp_FootprintType(_value9);
    public static final DnsProtectionDynamicStateFp_FootprintType value10 = new DnsProtectionDynamicStateFp_FootprintType(_value10);
    public static final DnsProtectionDynamicStateFp_FootprintType value11 = new DnsProtectionDynamicStateFp_FootprintType(_value11);
    public static final DnsProtectionDynamicStateFp_FootprintType value12 = new DnsProtectionDynamicStateFp_FootprintType(_value12);
    public static final DnsProtectionDynamicStateFp_FootprintType value13 = new DnsProtectionDynamicStateFp_FootprintType(_value13);
    public static final DnsProtectionDynamicStateFp_FootprintType value14 = new DnsProtectionDynamicStateFp_FootprintType(_value14);
    public static final DnsProtectionDynamicStateFp_FootprintType value15 = new DnsProtectionDynamicStateFp_FootprintType(_value15);
    public static final DnsProtectionDynamicStateFp_FootprintType value16 = new DnsProtectionDynamicStateFp_FootprintType(_value16);
    public static final DnsProtectionDynamicStateFp_FootprintType value17 = new DnsProtectionDynamicStateFp_FootprintType(_value17);
    public static final DnsProtectionDynamicStateFp_FootprintType value18 = new DnsProtectionDynamicStateFp_FootprintType(_value18);
    public static final DnsProtectionDynamicStateFp_FootprintType value19 = new DnsProtectionDynamicStateFp_FootprintType(_value19);
    public static final DnsProtectionDynamicStateFp_FootprintType value20 = new DnsProtectionDynamicStateFp_FootprintType(_value20);
    public static final DnsProtectionDynamicStateFp_FootprintType value21 = new DnsProtectionDynamicStateFp_FootprintType(_value21);
    public static final DnsProtectionDynamicStateFp_FootprintType value22 = new DnsProtectionDynamicStateFp_FootprintType(_value22);
    public java.lang.String getValue() { return _value_;}
    public static DnsProtectionDynamicStateFp_FootprintType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DnsProtectionDynamicStateFp_FootprintType enumeration = (DnsProtectionDynamicStateFp_FootprintType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DnsProtectionDynamicStateFp_FootprintType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DnsProtectionDynamicStateFp_FootprintType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateFp_FootprintType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
