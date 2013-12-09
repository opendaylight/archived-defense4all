/**
 * RsNetFloodBypassEntry_BypassField.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class RsNetFloodBypassEntry_BypassField implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RsNetFloodBypassEntry_BypassField(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "checksum";
    public static final java.lang.String _value2 = "sequence-num";
    public static final java.lang.String _value3 = "id-num";
    public static final java.lang.String _value4 = "dns-id-num";
    public static final java.lang.String _value5 = "dns-qname";
    public static final java.lang.String _value6 = "dns-qcount";
    public static final java.lang.String _value7 = "source-port";
    public static final java.lang.String _value8 = "source-ip";
    public static final java.lang.String _value9 = "frag-offset";
    public static final java.lang.String _value10 = "flow-label-ipv6";
    public static final java.lang.String _value11 = "tos";
    public static final java.lang.String _value12 = "packet-size";
    public static final java.lang.String _value13 = "destination-port";
    public static final java.lang.String _value14 = "destination-ip";
    public static final java.lang.String _value15 = "fragment";
    public static final java.lang.String _value16 = "icmp-igmp-message-type";
    public static final java.lang.String _value17 = "ttl";
    public static final java.lang.String _value18 = "frag-header-id-ipv6";
    public static final java.lang.String _value19 = "frag-offset-ipv6";
    public static final java.lang.String _value20 = "packet-size-ipv6";
    public static final java.lang.String _value21 = "source-ip-ipv6";
    public static final java.lang.String _value22 = "destination-ip-ipv6";
    public static final java.lang.String _value23 = "icmp-message-type-ipv6";
    public static final RsNetFloodBypassEntry_BypassField value1 = new RsNetFloodBypassEntry_BypassField(_value1);
    public static final RsNetFloodBypassEntry_BypassField value2 = new RsNetFloodBypassEntry_BypassField(_value2);
    public static final RsNetFloodBypassEntry_BypassField value3 = new RsNetFloodBypassEntry_BypassField(_value3);
    public static final RsNetFloodBypassEntry_BypassField value4 = new RsNetFloodBypassEntry_BypassField(_value4);
    public static final RsNetFloodBypassEntry_BypassField value5 = new RsNetFloodBypassEntry_BypassField(_value5);
    public static final RsNetFloodBypassEntry_BypassField value6 = new RsNetFloodBypassEntry_BypassField(_value6);
    public static final RsNetFloodBypassEntry_BypassField value7 = new RsNetFloodBypassEntry_BypassField(_value7);
    public static final RsNetFloodBypassEntry_BypassField value8 = new RsNetFloodBypassEntry_BypassField(_value8);
    public static final RsNetFloodBypassEntry_BypassField value9 = new RsNetFloodBypassEntry_BypassField(_value9);
    public static final RsNetFloodBypassEntry_BypassField value10 = new RsNetFloodBypassEntry_BypassField(_value10);
    public static final RsNetFloodBypassEntry_BypassField value11 = new RsNetFloodBypassEntry_BypassField(_value11);
    public static final RsNetFloodBypassEntry_BypassField value12 = new RsNetFloodBypassEntry_BypassField(_value12);
    public static final RsNetFloodBypassEntry_BypassField value13 = new RsNetFloodBypassEntry_BypassField(_value13);
    public static final RsNetFloodBypassEntry_BypassField value14 = new RsNetFloodBypassEntry_BypassField(_value14);
    public static final RsNetFloodBypassEntry_BypassField value15 = new RsNetFloodBypassEntry_BypassField(_value15);
    public static final RsNetFloodBypassEntry_BypassField value16 = new RsNetFloodBypassEntry_BypassField(_value16);
    public static final RsNetFloodBypassEntry_BypassField value17 = new RsNetFloodBypassEntry_BypassField(_value17);
    public static final RsNetFloodBypassEntry_BypassField value18 = new RsNetFloodBypassEntry_BypassField(_value18);
    public static final RsNetFloodBypassEntry_BypassField value19 = new RsNetFloodBypassEntry_BypassField(_value19);
    public static final RsNetFloodBypassEntry_BypassField value20 = new RsNetFloodBypassEntry_BypassField(_value20);
    public static final RsNetFloodBypassEntry_BypassField value21 = new RsNetFloodBypassEntry_BypassField(_value21);
    public static final RsNetFloodBypassEntry_BypassField value22 = new RsNetFloodBypassEntry_BypassField(_value22);
    public static final RsNetFloodBypassEntry_BypassField value23 = new RsNetFloodBypassEntry_BypassField(_value23);
    public java.lang.String getValue() { return _value_;}
    public static RsNetFloodBypassEntry_BypassField fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RsNetFloodBypassEntry_BypassField enumeration = (RsNetFloodBypassEntry_BypassField)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RsNetFloodBypassEntry_BypassField fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RsNetFloodBypassEntry_BypassField.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassField"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
