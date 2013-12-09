/**
 * BasicFilterUser_OMPCLength.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class BasicFilterUser_OMPCLength implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BasicFilterUser_OMPCLength(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "One Byte";
    public static final java.lang.String _value2 = "Two Bytes";
    public static final java.lang.String _value3 = "Three Bytes";
    public static final java.lang.String _value4 = "Four Bytes";
    public static final java.lang.String _value5 = "None";
    public static final BasicFilterUser_OMPCLength value1 = new BasicFilterUser_OMPCLength(_value1);
    public static final BasicFilterUser_OMPCLength value2 = new BasicFilterUser_OMPCLength(_value2);
    public static final BasicFilterUser_OMPCLength value3 = new BasicFilterUser_OMPCLength(_value3);
    public static final BasicFilterUser_OMPCLength value4 = new BasicFilterUser_OMPCLength(_value4);
    public static final BasicFilterUser_OMPCLength value5 = new BasicFilterUser_OMPCLength(_value5);
    public java.lang.String getValue() { return _value_;}
    public static BasicFilterUser_OMPCLength fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        BasicFilterUser_OMPCLength enumeration = (BasicFilterUser_OMPCLength)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static BasicFilterUser_OMPCLength fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(BasicFilterUser_OMPCLength.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_OMPCLength"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
