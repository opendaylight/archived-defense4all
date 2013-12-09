/**
 * DnsProtectionBypassEntry_BypassField.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class DnsProtectionBypassEntry_BypassField implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DnsProtectionBypassEntry_BypassField(java.lang.String value) {
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
    public static final java.lang.String _value17 = "dns-qname";
    public static final java.lang.String _value18 = "source-ip-ipv6";
    public static final java.lang.String _value19 = "tos";
    public static final java.lang.String _value20 = "ttl";
    public static final java.lang.String _value21 = "dns-qcount";
    public static final DnsProtectionBypassEntry_BypassField value1 = new DnsProtectionBypassEntry_BypassField(_value1);
    public static final DnsProtectionBypassEntry_BypassField value2 = new DnsProtectionBypassEntry_BypassField(_value2);
    public static final DnsProtectionBypassEntry_BypassField value3 = new DnsProtectionBypassEntry_BypassField(_value3);
    public static final DnsProtectionBypassEntry_BypassField value4 = new DnsProtectionBypassEntry_BypassField(_value4);
    public static final DnsProtectionBypassEntry_BypassField value5 = new DnsProtectionBypassEntry_BypassField(_value5);
    public static final DnsProtectionBypassEntry_BypassField value6 = new DnsProtectionBypassEntry_BypassField(_value6);
    public static final DnsProtectionBypassEntry_BypassField value7 = new DnsProtectionBypassEntry_BypassField(_value7);
    public static final DnsProtectionBypassEntry_BypassField value8 = new DnsProtectionBypassEntry_BypassField(_value8);
    public static final DnsProtectionBypassEntry_BypassField value9 = new DnsProtectionBypassEntry_BypassField(_value9);
    public static final DnsProtectionBypassEntry_BypassField value10 = new DnsProtectionBypassEntry_BypassField(_value10);
    public static final DnsProtectionBypassEntry_BypassField value11 = new DnsProtectionBypassEntry_BypassField(_value11);
    public static final DnsProtectionBypassEntry_BypassField value12 = new DnsProtectionBypassEntry_BypassField(_value12);
    public static final DnsProtectionBypassEntry_BypassField value13 = new DnsProtectionBypassEntry_BypassField(_value13);
    public static final DnsProtectionBypassEntry_BypassField value14 = new DnsProtectionBypassEntry_BypassField(_value14);
    public static final DnsProtectionBypassEntry_BypassField value15 = new DnsProtectionBypassEntry_BypassField(_value15);
    public static final DnsProtectionBypassEntry_BypassField value16 = new DnsProtectionBypassEntry_BypassField(_value16);
    public static final DnsProtectionBypassEntry_BypassField value17 = new DnsProtectionBypassEntry_BypassField(_value17);
    public static final DnsProtectionBypassEntry_BypassField value18 = new DnsProtectionBypassEntry_BypassField(_value18);
    public static final DnsProtectionBypassEntry_BypassField value19 = new DnsProtectionBypassEntry_BypassField(_value19);
    public static final DnsProtectionBypassEntry_BypassField value20 = new DnsProtectionBypassEntry_BypassField(_value20);
    public static final DnsProtectionBypassEntry_BypassField value21 = new DnsProtectionBypassEntry_BypassField(_value21);
    public java.lang.String getValue() { return _value_;}
    public static DnsProtectionBypassEntry_BypassField fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DnsProtectionBypassEntry_BypassField enumeration = (DnsProtectionBypassEntry_BypassField)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DnsProtectionBypassEntry_BypassField fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DnsProtectionBypassEntry_BypassField.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassField"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
