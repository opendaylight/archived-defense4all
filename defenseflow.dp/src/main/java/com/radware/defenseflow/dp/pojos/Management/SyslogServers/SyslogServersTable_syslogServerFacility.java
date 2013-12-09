/**
 * SyslogServersTable_syslogServerFacility.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Management.SyslogServers;

public class SyslogServersTable_syslogServerFacility implements java.io.Serializable {
    private java.lang.String _value_;
    private static java.util.HashMap _table_ = new java.util.HashMap();

    // Constructor
    protected SyslogServersTable_syslogServerFacility(java.lang.String value) {
        _value_ = value;
        _table_.put(_value_,this);
    }

    public static final java.lang.String _value1 = "Kernel Messages";
    public static final java.lang.String _value2 = "User Level Messages";
    public static final java.lang.String _value3 = "Mail System";
    public static final java.lang.String _value4 = "System Daemons";
    public static final java.lang.String _value5 = "Authorization Messages";
    public static final java.lang.String _value6 = "Syslogd Messages";
    public static final java.lang.String _value7 = "Line Printer Subsystem";
    public static final java.lang.String _value8 = "Network News Subsystem";
    public static final java.lang.String _value9 = "UUCP";
    public static final java.lang.String _value10 = "Clock Daemon";
    public static final java.lang.String _value11 = "Security Messages";
    public static final java.lang.String _value12 = "FTP Daemon";
    public static final java.lang.String _value13 = "NTP Daemon";
    public static final java.lang.String _value14 = "Log Audit";
    public static final java.lang.String _value15 = "Log Alert";
    public static final java.lang.String _value16 = "Clock Daemon2";
    public static final java.lang.String _value17 = "Local Use 0";
    public static final java.lang.String _value18 = "Local Use 1";
    public static final java.lang.String _value19 = "Local Use 2";
    public static final java.lang.String _value20 = "Local Use 3";
    public static final java.lang.String _value21 = "Local Use 4";
    public static final java.lang.String _value22 = "Local Use 5";
    public static final java.lang.String _value23 = "Local Use 6";
    public static final java.lang.String _value24 = "Local Use 7";
    public static final SyslogServersTable_syslogServerFacility value1 = new SyslogServersTable_syslogServerFacility(_value1);
    public static final SyslogServersTable_syslogServerFacility value2 = new SyslogServersTable_syslogServerFacility(_value2);
    public static final SyslogServersTable_syslogServerFacility value3 = new SyslogServersTable_syslogServerFacility(_value3);
    public static final SyslogServersTable_syslogServerFacility value4 = new SyslogServersTable_syslogServerFacility(_value4);
    public static final SyslogServersTable_syslogServerFacility value5 = new SyslogServersTable_syslogServerFacility(_value5);
    public static final SyslogServersTable_syslogServerFacility value6 = new SyslogServersTable_syslogServerFacility(_value6);
    public static final SyslogServersTable_syslogServerFacility value7 = new SyslogServersTable_syslogServerFacility(_value7);
    public static final SyslogServersTable_syslogServerFacility value8 = new SyslogServersTable_syslogServerFacility(_value8);
    public static final SyslogServersTable_syslogServerFacility value9 = new SyslogServersTable_syslogServerFacility(_value9);
    public static final SyslogServersTable_syslogServerFacility value10 = new SyslogServersTable_syslogServerFacility(_value10);
    public static final SyslogServersTable_syslogServerFacility value11 = new SyslogServersTable_syslogServerFacility(_value11);
    public static final SyslogServersTable_syslogServerFacility value12 = new SyslogServersTable_syslogServerFacility(_value12);
    public static final SyslogServersTable_syslogServerFacility value13 = new SyslogServersTable_syslogServerFacility(_value13);
    public static final SyslogServersTable_syslogServerFacility value14 = new SyslogServersTable_syslogServerFacility(_value14);
    public static final SyslogServersTable_syslogServerFacility value15 = new SyslogServersTable_syslogServerFacility(_value15);
    public static final SyslogServersTable_syslogServerFacility value16 = new SyslogServersTable_syslogServerFacility(_value16);
    public static final SyslogServersTable_syslogServerFacility value17 = new SyslogServersTable_syslogServerFacility(_value17);
    public static final SyslogServersTable_syslogServerFacility value18 = new SyslogServersTable_syslogServerFacility(_value18);
    public static final SyslogServersTable_syslogServerFacility value19 = new SyslogServersTable_syslogServerFacility(_value19);
    public static final SyslogServersTable_syslogServerFacility value20 = new SyslogServersTable_syslogServerFacility(_value20);
    public static final SyslogServersTable_syslogServerFacility value21 = new SyslogServersTable_syslogServerFacility(_value21);
    public static final SyslogServersTable_syslogServerFacility value22 = new SyslogServersTable_syslogServerFacility(_value22);
    public static final SyslogServersTable_syslogServerFacility value23 = new SyslogServersTable_syslogServerFacility(_value23);
    public static final SyslogServersTable_syslogServerFacility value24 = new SyslogServersTable_syslogServerFacility(_value24);
    public java.lang.String getValue() { return _value_;}
    public static SyslogServersTable_syslogServerFacility fromValue(java.lang.String value)
          throws java.lang.IllegalArgumentException {
        SyslogServersTable_syslogServerFacility enumeration = (SyslogServersTable_syslogServerFacility)
            _table_.get(value);
        if (enumeration==null) throw new java.lang.IllegalArgumentException();
        return enumeration;
    }
    public static SyslogServersTable_syslogServerFacility fromString(java.lang.String value)
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
        new org.apache.axis.description.TypeDesc(SyslogServersTable_syslogServerFacility.class);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerFacility"));
    }
    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

}
