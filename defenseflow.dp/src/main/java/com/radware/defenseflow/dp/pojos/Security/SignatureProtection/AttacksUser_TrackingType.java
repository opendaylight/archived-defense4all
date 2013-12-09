/**
 * AttacksUser_TrackingType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class AttacksUser_TrackingType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected AttacksUser_TrackingType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "Drop All";
    public static final java.lang.String _value2 = "Source Count";
    public static final java.lang.String _value3 = "Target Count";
    public static final java.lang.String _value4 = "Source and Target Count";
    public static final java.lang.String _value5 = "landattack";
    public static final java.lang.String _value6 = "fragments";
    public static final java.lang.String _value7 = "ncpsdscan";
    public static final java.lang.String _value8 = "dhcp";
    public static final java.lang.String _value9 = "ftpbounce";
    public static final java.lang.String _value10 = "bobo2k";
    public static final java.lang.String _value11 = "Sampling";
    public static final AttacksUser_TrackingType value1 = new AttacksUser_TrackingType(_value1);
    public static final AttacksUser_TrackingType value2 = new AttacksUser_TrackingType(_value2);
    public static final AttacksUser_TrackingType value3 = new AttacksUser_TrackingType(_value3);
    public static final AttacksUser_TrackingType value4 = new AttacksUser_TrackingType(_value4);
    public static final AttacksUser_TrackingType value5 = new AttacksUser_TrackingType(_value5);
    public static final AttacksUser_TrackingType value6 = new AttacksUser_TrackingType(_value6);
    public static final AttacksUser_TrackingType value7 = new AttacksUser_TrackingType(_value7);
    public static final AttacksUser_TrackingType value8 = new AttacksUser_TrackingType(_value8);
    public static final AttacksUser_TrackingType value9 = new AttacksUser_TrackingType(_value9);
    public static final AttacksUser_TrackingType value10 = new AttacksUser_TrackingType(_value10);
    public static final AttacksUser_TrackingType value11 = new AttacksUser_TrackingType(_value11);
    public java.lang.String getValue() { return _value_;}
    public static AttacksUser_TrackingType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        AttacksUser_TrackingType enumeration = (AttacksUser_TrackingType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static AttacksUser_TrackingType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(AttacksUser_TrackingType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_TrackingType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
