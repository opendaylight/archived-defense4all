/**
 * DnsProtectionBypassEntry_BypassController.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class DnsProtectionBypassEntry_BypassController implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DnsProtectionBypassEntry_BypassController(java.lang.String value) {
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
    public static final DnsProtectionBypassEntry_BypassController value1 = new DnsProtectionBypassEntry_BypassController(_value1);
    public static final DnsProtectionBypassEntry_BypassController value2 = new DnsProtectionBypassEntry_BypassController(_value2);
    public static final DnsProtectionBypassEntry_BypassController value3 = new DnsProtectionBypassEntry_BypassController(_value3);
    public static final DnsProtectionBypassEntry_BypassController value4 = new DnsProtectionBypassEntry_BypassController(_value4);
    public static final DnsProtectionBypassEntry_BypassController value5 = new DnsProtectionBypassEntry_BypassController(_value5);
    public static final DnsProtectionBypassEntry_BypassController value6 = new DnsProtectionBypassEntry_BypassController(_value6);
    public static final DnsProtectionBypassEntry_BypassController value7 = new DnsProtectionBypassEntry_BypassController(_value7);
    public static final DnsProtectionBypassEntry_BypassController value8 = new DnsProtectionBypassEntry_BypassController(_value8);
    public static final DnsProtectionBypassEntry_BypassController value9 = new DnsProtectionBypassEntry_BypassController(_value9);
    public java.lang.String getValue() { return _value_;}
    public static DnsProtectionBypassEntry_BypassController fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DnsProtectionBypassEntry_BypassController enumeration = (DnsProtectionBypassEntry_BypassController)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DnsProtectionBypassEntry_BypassController fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DnsProtectionBypassEntry_BypassController.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionBypassEntry_BypassController"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
