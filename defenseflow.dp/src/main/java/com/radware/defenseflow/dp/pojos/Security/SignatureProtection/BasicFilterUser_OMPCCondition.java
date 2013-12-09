/**
 * BasicFilterUser_OMPCCondition.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;

public class BasicFilterUser_OMPCCondition implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected BasicFilterUser_OMPCCondition(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "None";
    public static final java.lang.String _value2 = "Equal";
    public static final java.lang.String _value3 = "Not equal";
    public static final java.lang.String _value4 = "Greater Than";
    public static final java.lang.String _value5 = "Less Than";
    public static final BasicFilterUser_OMPCCondition value1 = new BasicFilterUser_OMPCCondition(_value1);
    public static final BasicFilterUser_OMPCCondition value2 = new BasicFilterUser_OMPCCondition(_value2);
    public static final BasicFilterUser_OMPCCondition value3 = new BasicFilterUser_OMPCCondition(_value3);
    public static final BasicFilterUser_OMPCCondition value4 = new BasicFilterUser_OMPCCondition(_value4);
    public static final BasicFilterUser_OMPCCondition value5 = new BasicFilterUser_OMPCCondition(_value5);
    public java.lang.String getValue() { return _value_;}
    public static BasicFilterUser_OMPCCondition fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        BasicFilterUser_OMPCCondition enumeration = (BasicFilterUser_OMPCCondition)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static BasicFilterUser_OMPCCondition fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(BasicFilterUser_OMPCCondition.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_OMPCCondition"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
