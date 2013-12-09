/**
 * BasicFilterUser_ContentType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class BasicFilterUser_ContentType implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BasicFilterUser_ContentType(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "None";
    public static final java.lang.String _value2 = "URL";
    public static final java.lang.String _value3 = "Text";
    public static final java.lang.String _value4 = "Host Name";
    public static final java.lang.String _value5 = "Header Field";
    public static final java.lang.String _value6 = "Mail Domain";
    public static final java.lang.String _value7 = "Mail To";
    public static final java.lang.String _value8 = "Mail From";
    public static final java.lang.String _value9 = "Mail Subject";
    public static final java.lang.String _value10 = "File Type";
    public static final java.lang.String _value11 = "Cookie";
    public static final java.lang.String _value12 = "Normalized URL";
    public static final java.lang.String _value13 = "POP3 User";
    public static final java.lang.String _value14 = "urilength";
    public static final java.lang.String _value15 = "FTP Command";
    public static final java.lang.String _value16 = "FTP Content";
    public static final java.lang.String _value17 = "RPC";
    public static final java.lang.String _value18 = "DCERPC";
    public static final java.lang.String _value19 = "HTTP Reply Data";
    public static final java.lang.String _value20 = "HTTP Reply Header";
    public static final java.lang.String _value21 = "MM7 Request";
    public static final java.lang.String _value22 = "MM7 File Attachment";
    public static final BasicFilterUser_ContentType value1 = new BasicFilterUser_ContentType(_value1);
    public static final BasicFilterUser_ContentType value2 = new BasicFilterUser_ContentType(_value2);
    public static final BasicFilterUser_ContentType value3 = new BasicFilterUser_ContentType(_value3);
    public static final BasicFilterUser_ContentType value4 = new BasicFilterUser_ContentType(_value4);
    public static final BasicFilterUser_ContentType value5 = new BasicFilterUser_ContentType(_value5);
    public static final BasicFilterUser_ContentType value6 = new BasicFilterUser_ContentType(_value6);
    public static final BasicFilterUser_ContentType value7 = new BasicFilterUser_ContentType(_value7);
    public static final BasicFilterUser_ContentType value8 = new BasicFilterUser_ContentType(_value8);
    public static final BasicFilterUser_ContentType value9 = new BasicFilterUser_ContentType(_value9);
    public static final BasicFilterUser_ContentType value10 = new BasicFilterUser_ContentType(_value10);
    public static final BasicFilterUser_ContentType value11 = new BasicFilterUser_ContentType(_value11);
    public static final BasicFilterUser_ContentType value12 = new BasicFilterUser_ContentType(_value12);
    public static final BasicFilterUser_ContentType value13 = new BasicFilterUser_ContentType(_value13);
    public static final BasicFilterUser_ContentType value14 = new BasicFilterUser_ContentType(_value14);
    public static final BasicFilterUser_ContentType value15 = new BasicFilterUser_ContentType(_value15);
    public static final BasicFilterUser_ContentType value16 = new BasicFilterUser_ContentType(_value16);
    public static final BasicFilterUser_ContentType value17 = new BasicFilterUser_ContentType(_value17);
    public static final BasicFilterUser_ContentType value18 = new BasicFilterUser_ContentType(_value18);
    public static final BasicFilterUser_ContentType value19 = new BasicFilterUser_ContentType(_value19);
    public static final BasicFilterUser_ContentType value20 = new BasicFilterUser_ContentType(_value20);
    public static final BasicFilterUser_ContentType value21 = new BasicFilterUser_ContentType(_value21);
    public static final BasicFilterUser_ContentType value22 = new BasicFilterUser_ContentType(_value22);
    public java.lang.String getValue() { return _value_;}
    public static BasicFilterUser_ContentType fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        BasicFilterUser_ContentType enumeration = (BasicFilterUser_ContentType)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static BasicFilterUser_ContentType fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(BasicFilterUser_ContentType.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_ContentType"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
