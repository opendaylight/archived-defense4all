/**
 * DnsProtectionDynamicStateTwo_AnyTypeFlag.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.DnsProtection;

public class DnsProtectionDynamicStateTwo_AnyTypeFlag implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected DnsProtectionDynamicStateTwo_AnyTypeFlag(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "false";
    public static final java.lang.String _value2 = "true";
    public static final DnsProtectionDynamicStateTwo_AnyTypeFlag value1 = new DnsProtectionDynamicStateTwo_AnyTypeFlag(_value1);
    public static final DnsProtectionDynamicStateTwo_AnyTypeFlag value2 = new DnsProtectionDynamicStateTwo_AnyTypeFlag(_value2);
    public java.lang.String getValue() { return _value_;}
    public static DnsProtectionDynamicStateTwo_AnyTypeFlag fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        DnsProtectionDynamicStateTwo_AnyTypeFlag enumeration = (DnsProtectionDynamicStateTwo_AnyTypeFlag)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static DnsProtectionDynamicStateTwo_AnyTypeFlag fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(DnsProtectionDynamicStateTwo_AnyTypeFlag.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.DnsProtection", "DnsProtectionDynamicStateTwo_AnyTypeFlag"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
