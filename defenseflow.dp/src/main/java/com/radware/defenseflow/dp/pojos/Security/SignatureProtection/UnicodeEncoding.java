/**
 * UnicodeEncoding.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class UnicodeEncoding implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected UnicodeEncoding(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "None    (full name is given in brackets)";
    public static final java.lang.String _value2 = "e1250   (ANSI - Central Europe)";
    public static final java.lang.String _value3 = "e1251   (ANSI - Cyrillic)";
    public static final java.lang.String _value4 = "e1252   (ANSI - Latin I)";
    public static final java.lang.String _value5 = "e1253   (ANSI - Greek)";
    public static final java.lang.String _value6 = "e1254   (ANSI - Turkish)";
    public static final java.lang.String _value7 = "e1255   (ANSI - Hebrew)";
    public static final java.lang.String _value8 = "e1256   (ANSI - Arabic)";
    public static final java.lang.String _value9 = "e1257   (ANSI - Baltic)";
    public static final java.lang.String _value10 = "e1258   (ANSI-OEM - Viet Nam)";
    public static final java.lang.String _value11 = "e20127 (US-ASCII)";
    public static final java.lang.String _value12 = "e20261 (T.61)";
    public static final java.lang.String _value13 = "e20866 (Russian - KOI8)";
    public static final java.lang.String _value14 = "e28591 (ISO 8859-1 Latin I)";
    public static final java.lang.String _value15 = "e28592 (ISO 8859-2 Central Europe)";
    public static final java.lang.String _value16 = "e28605 (ISO 8859-15 Latin 9)";
    public static final java.lang.String _value17 = "e37    (IBM EBCDIC - U.S.-Canada)";
    public static final java.lang.String _value18 = "e437   (OEM - United States)";
    public static final java.lang.String _value19 = "e500   (IBM EBCDIC - International)";
    public static final java.lang.String _value20 = "e850   (OEM - Multilingual Latin I)";
    public static final java.lang.String _value21 = "e860   (OEM - Portuguese)";
    public static final java.lang.String _value22 = "e861   (OEM - Icelandic)";
    public static final java.lang.String _value23 = "e863   (OEM - Canadian French)";
    public static final java.lang.String _value24 = "e865   (OEM - Nordic)";
    public static final java.lang.String _value25 = "e874   (ANSI-OEM - Thai)";
    public static final java.lang.String _value26 = "e932   (ANSI-OEM - Japanese Shift-JIS)";
    public static final java.lang.String _value27 = "e936   (ANSI-OEM - Simplified Chinese GBK)";
    public static final java.lang.String _value28 = "e949   (ANSI-OEM - Korean)";
    public static final java.lang.String _value29 = "e950   (ANSI-OEM - Traditional Chinese Big5)";
    public static final UnicodeEncoding value1 = new UnicodeEncoding(_value1);
    public static final UnicodeEncoding value2 = new UnicodeEncoding(_value2);
    public static final UnicodeEncoding value3 = new UnicodeEncoding(_value3);
    public static final UnicodeEncoding value4 = new UnicodeEncoding(_value4);
    public static final UnicodeEncoding value5 = new UnicodeEncoding(_value5);
    public static final UnicodeEncoding value6 = new UnicodeEncoding(_value6);
    public static final UnicodeEncoding value7 = new UnicodeEncoding(_value7);
    public static final UnicodeEncoding value8 = new UnicodeEncoding(_value8);
    public static final UnicodeEncoding value9 = new UnicodeEncoding(_value9);
    public static final UnicodeEncoding value10 = new UnicodeEncoding(_value10);
    public static final UnicodeEncoding value11 = new UnicodeEncoding(_value11);
    public static final UnicodeEncoding value12 = new UnicodeEncoding(_value12);
    public static final UnicodeEncoding value13 = new UnicodeEncoding(_value13);
    public static final UnicodeEncoding value14 = new UnicodeEncoding(_value14);
    public static final UnicodeEncoding value15 = new UnicodeEncoding(_value15);
    public static final UnicodeEncoding value16 = new UnicodeEncoding(_value16);
    public static final UnicodeEncoding value17 = new UnicodeEncoding(_value17);
    public static final UnicodeEncoding value18 = new UnicodeEncoding(_value18);
    public static final UnicodeEncoding value19 = new UnicodeEncoding(_value19);
    public static final UnicodeEncoding value20 = new UnicodeEncoding(_value20);
    public static final UnicodeEncoding value21 = new UnicodeEncoding(_value21);
    public static final UnicodeEncoding value22 = new UnicodeEncoding(_value22);
    public static final UnicodeEncoding value23 = new UnicodeEncoding(_value23);
    public static final UnicodeEncoding value24 = new UnicodeEncoding(_value24);
    public static final UnicodeEncoding value25 = new UnicodeEncoding(_value25);
    public static final UnicodeEncoding value26 = new UnicodeEncoding(_value26);
    public static final UnicodeEncoding value27 = new UnicodeEncoding(_value27);
    public static final UnicodeEncoding value28 = new UnicodeEncoding(_value28);
    public static final UnicodeEncoding value29 = new UnicodeEncoding(_value29);
    public java.lang.String getValue() { return _value_;}
    public static UnicodeEncoding fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        UnicodeEncoding enumeration = (UnicodeEncoding)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static UnicodeEncoding fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(UnicodeEncoding.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "UnicodeEncoding"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
