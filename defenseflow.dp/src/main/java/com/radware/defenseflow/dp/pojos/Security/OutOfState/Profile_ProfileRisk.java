/**
 * Profile_ProfileRisk.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.OutOfState;

public class Profile_ProfileRisk implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected Profile_ProfileRisk(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _info = "info";
    public static final java.lang.String _low = "low";
    public static final java.lang.String _medium = "medium";
    public static final java.lang.String _high = "high";
    public static final Profile_ProfileRisk info = new Profile_ProfileRisk(_info);
    public static final Profile_ProfileRisk low = new Profile_ProfileRisk(_low);
    public static final Profile_ProfileRisk medium = new Profile_ProfileRisk(_medium);
    public static final Profile_ProfileRisk high = new Profile_ProfileRisk(_high);
    public java.lang.String getValue() { return _value_;}
    public static Profile_ProfileRisk fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        Profile_ProfileRisk enumeration = (Profile_ProfileRisk)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static Profile_ProfileRisk fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(Profile_ProfileRisk.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.OutOfState", "Profile_ProfileRisk"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
