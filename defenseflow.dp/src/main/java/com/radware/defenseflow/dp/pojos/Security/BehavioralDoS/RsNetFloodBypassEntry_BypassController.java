/**
 * RsNetFloodBypassEntry_BypassController.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class RsNetFloodBypassEntry_BypassController implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RsNetFloodBypassEntry_BypassController(java.lang.String value) {
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
    public static final RsNetFloodBypassEntry_BypassController value1 = new RsNetFloodBypassEntry_BypassController(_value1);
    public static final RsNetFloodBypassEntry_BypassController value2 = new RsNetFloodBypassEntry_BypassController(_value2);
    public static final RsNetFloodBypassEntry_BypassController value3 = new RsNetFloodBypassEntry_BypassController(_value3);
    public static final RsNetFloodBypassEntry_BypassController value4 = new RsNetFloodBypassEntry_BypassController(_value4);
    public static final RsNetFloodBypassEntry_BypassController value5 = new RsNetFloodBypassEntry_BypassController(_value5);
    public static final RsNetFloodBypassEntry_BypassController value6 = new RsNetFloodBypassEntry_BypassController(_value6);
    public static final RsNetFloodBypassEntry_BypassController value7 = new RsNetFloodBypassEntry_BypassController(_value7);
    public static final RsNetFloodBypassEntry_BypassController value8 = new RsNetFloodBypassEntry_BypassController(_value8);
    public static final RsNetFloodBypassEntry_BypassController value9 = new RsNetFloodBypassEntry_BypassController(_value9);
    public static final RsNetFloodBypassEntry_BypassController value10 = new RsNetFloodBypassEntry_BypassController(_value10);
    public static final RsNetFloodBypassEntry_BypassController value11 = new RsNetFloodBypassEntry_BypassController(_value11);
    public static final RsNetFloodBypassEntry_BypassController value12 = new RsNetFloodBypassEntry_BypassController(_value12);
    public static final RsNetFloodBypassEntry_BypassController value13 = new RsNetFloodBypassEntry_BypassController(_value13);
    public static final RsNetFloodBypassEntry_BypassController value14 = new RsNetFloodBypassEntry_BypassController(_value14);
    public static final RsNetFloodBypassEntry_BypassController value15 = new RsNetFloodBypassEntry_BypassController(_value15);
    public static final RsNetFloodBypassEntry_BypassController value16 = new RsNetFloodBypassEntry_BypassController(_value16);
    public java.lang.String getValue() { return _value_;}
    public static RsNetFloodBypassEntry_BypassController fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RsNetFloodBypassEntry_BypassController enumeration = (RsNetFloodBypassEntry_BypassController)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RsNetFloodBypassEntry_BypassController fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RsNetFloodBypassEntry_BypassController.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodBypassEntry_BypassController"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
