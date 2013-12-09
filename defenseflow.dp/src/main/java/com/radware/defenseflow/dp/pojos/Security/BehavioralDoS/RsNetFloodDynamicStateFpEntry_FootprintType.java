/**
 * RsNetFloodDynamicStateFpEntry_FootprintType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class RsNetFloodDynamicStateFpEntry_FootprintType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RsNetFloodDynamicStateFpEntry_FootprintType(java.lang.String value) {
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
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value1 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value1);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value2 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value2);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value3 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value3);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value4 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value4);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value5 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value5);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value6 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value6);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value7 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value7);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value8 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value8);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value9 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value9);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value10 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value10);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value11 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value11);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value12 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value12);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value13 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value13);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value14 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value14);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value15 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value15);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value16 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value16);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value17 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value17);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value18 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value18);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value19 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value19);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value20 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value20);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value21 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value21);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value22 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value22);
    public static final RsNetFloodDynamicStateFpEntry_FootprintType value23 = new RsNetFloodDynamicStateFpEntry_FootprintType(_value23);
    public java.lang.String getValue() { return _value_;}
    public static RsNetFloodDynamicStateFpEntry_FootprintType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RsNetFloodDynamicStateFpEntry_FootprintType enumeration = (RsNetFloodDynamicStateFpEntry_FootprintType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RsNetFloodDynamicStateFpEntry_FootprintType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RsNetFloodDynamicStateFpEntry_FootprintType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_FootprintType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
