/**
 * RsNetFloodDynamicStateFpEntry_ProtectionType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class RsNetFloodDynamicStateFpEntry_ProtectionType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RsNetFloodDynamicStateFpEntry_ProtectionType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "udp";
    public static final java.lang.String _value2 = "udp-out";
    public static final java.lang.String _value3 = "icmp";
    public static final java.lang.String _value4 = "icmp-out";
    public static final java.lang.String _value5 = "igmp";
    public static final java.lang.String _value6 = "igmp-out";
    public static final java.lang.String _value7 = "tcp-syn";
    public static final java.lang.String _value8 = "tcp-syn-out";
    public static final java.lang.String _value9 = "tcp-rst";
    public static final java.lang.String _value10 = "tcp-rst-out";
    public static final java.lang.String _value11 = "tcp-ack-fin";
    public static final java.lang.String _value12 = "tcp-ack-fin-out";
    public static final java.lang.String _value13 = "tcp-syn-ack";
    public static final java.lang.String _value14 = "tcp-syn-ack-out";
    public static final java.lang.String _value15 = "tcp-frag";
    public static final java.lang.String _value16 = "tcp-frag-out";
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value1 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value1);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value2 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value2);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value3 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value3);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value4 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value4);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value5 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value5);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value6 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value6);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value7 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value7);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value8 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value8);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value9 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value9);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value10 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value10);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value11 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value11);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value12 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value12);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value13 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value13);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value14 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value14);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value15 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value15);
    public static final RsNetFloodDynamicStateFpEntry_ProtectionType value16 = new RsNetFloodDynamicStateFpEntry_ProtectionType(_value16);
    public java.lang.String getValue() { return _value_;}
    public static RsNetFloodDynamicStateFpEntry_ProtectionType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RsNetFloodDynamicStateFpEntry_ProtectionType enumeration = (RsNetFloodDynamicStateFpEntry_ProtectionType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RsNetFloodDynamicStateFpEntry_ProtectionType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RsNetFloodDynamicStateFpEntry_ProtectionType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_ProtectionType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
