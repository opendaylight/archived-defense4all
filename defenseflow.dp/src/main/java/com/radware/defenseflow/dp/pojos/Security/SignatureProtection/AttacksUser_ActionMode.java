/**
 * AttacksUser_ActionMode.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class AttacksUser_ActionMode implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AttacksUser_ActionMode(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "Report Only";
    public static final java.lang.String _value2 = "Drop";
    public static final java.lang.String _value3 = "Reset Source";
    public static final java.lang.String _value4 = "Reset Destination";
    public static final java.lang.String _value5 = "Reset BiDirectional";
    public static final java.lang.String _value6 = "MM7 Error";
    public static final AttacksUser_ActionMode value1 = new AttacksUser_ActionMode(_value1);
    public static final AttacksUser_ActionMode value2 = new AttacksUser_ActionMode(_value2);
    public static final AttacksUser_ActionMode value3 = new AttacksUser_ActionMode(_value3);
    public static final AttacksUser_ActionMode value4 = new AttacksUser_ActionMode(_value4);
    public static final AttacksUser_ActionMode value5 = new AttacksUser_ActionMode(_value5);
    public static final AttacksUser_ActionMode value6 = new AttacksUser_ActionMode(_value6);
    public java.lang.String getValue() { return _value_;}
    public static AttacksUser_ActionMode fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AttacksUser_ActionMode enumeration = (AttacksUser_ActionMode)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AttacksUser_ActionMode fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AttacksUser_ActionMode.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_ActionMode"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
