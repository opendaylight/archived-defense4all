/**
 * QuarantineActions_QuarantineActionMetadata.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class QuarantineActions_QuarantineActionMetadata implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected QuarantineActions_QuarantineActionMetadata(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _Enable = "Enable";
    public static final java.lang.String _Disable = "Disable";
    public static final QuarantineActions_QuarantineActionMetadata Enable = new QuarantineActions_QuarantineActionMetadata(_Enable);
    public static final QuarantineActions_QuarantineActionMetadata Disable = new QuarantineActions_QuarantineActionMetadata(_Disable);
    public java.lang.String getValue() { return _value_;}
    public static QuarantineActions_QuarantineActionMetadata fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        QuarantineActions_QuarantineActionMetadata enumeration = (QuarantineActions_QuarantineActionMetadata)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static QuarantineActions_QuarantineActionMetadata fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(QuarantineActions_QuarantineActionMetadata.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "QuarantineActions_QuarantineActionMetadata"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
