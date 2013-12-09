/**
 * SyslogServersTable_syslogServerRowStatus.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Management.SyslogServers;

public class SyslogServersTable_syslogServerRowStatus implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SyslogServersTable_syslogServerRowStatus(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _nr = "nr";
    public static final java.lang.String _unreachable = "unreachable";
    public static final java.lang.String _reachable = "reachable";
    public static final SyslogServersTable_syslogServerRowStatus nr = new SyslogServersTable_syslogServerRowStatus(_nr);
    public static final SyslogServersTable_syslogServerRowStatus unreachable = new SyslogServersTable_syslogServerRowStatus(_unreachable);
    public static final SyslogServersTable_syslogServerRowStatus reachable = new SyslogServersTable_syslogServerRowStatus(_reachable);
    public java.lang.String getValue() { return _value_;}
    public static SyslogServersTable_syslogServerRowStatus fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SyslogServersTable_syslogServerRowStatus enumeration = (SyslogServersTable_syslogServerRowStatus)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SyslogServersTable_syslogServerRowStatus fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SyslogServersTable_syslogServerRowStatus.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerRowStatus"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
