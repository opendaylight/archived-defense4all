/**
 * AttacksUser_Direction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class AttacksUser_Direction implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AttacksUser_Direction(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "In-Out Bound";
    public static final java.lang.String _value2 = "In Bound";
    public static final java.lang.String _value3 = "Out Bound";
    public static final AttacksUser_Direction value1 = new AttacksUser_Direction(_value1);
    public static final AttacksUser_Direction value2 = new AttacksUser_Direction(_value2);
    public static final AttacksUser_Direction value3 = new AttacksUser_Direction(_value3);
    public java.lang.String getValue() { return _value_;}
    public static AttacksUser_Direction fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AttacksUser_Direction enumeration = (AttacksUser_Direction)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AttacksUser_Direction fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AttacksUser_Direction.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_Direction"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
