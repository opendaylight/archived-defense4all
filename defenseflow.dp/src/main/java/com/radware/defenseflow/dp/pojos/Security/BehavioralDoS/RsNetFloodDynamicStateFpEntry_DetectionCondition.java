/**
 * RsNetFloodDynamicStateFpEntry_DetectionCondition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.BehavioralDoS;

public class RsNetFloodDynamicStateFpEntry_DetectionCondition implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected RsNetFloodDynamicStateFpEntry_DetectionCondition(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _no = "no";
    public static final java.lang.String _yes = "yes";
    public static final RsNetFloodDynamicStateFpEntry_DetectionCondition no = new RsNetFloodDynamicStateFpEntry_DetectionCondition(_no);
    public static final RsNetFloodDynamicStateFpEntry_DetectionCondition yes = new RsNetFloodDynamicStateFpEntry_DetectionCondition(_yes);
    public java.lang.String getValue() { return _value_;}
    public static RsNetFloodDynamicStateFpEntry_DetectionCondition fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        RsNetFloodDynamicStateFpEntry_DetectionCondition enumeration = (RsNetFloodDynamicStateFpEntry_DetectionCondition)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static RsNetFloodDynamicStateFpEntry_DetectionCondition fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(RsNetFloodDynamicStateFpEntry_DetectionCondition.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.BehavioralDoS", "rsNetFloodDynamicStateFpEntry_DetectionCondition"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
