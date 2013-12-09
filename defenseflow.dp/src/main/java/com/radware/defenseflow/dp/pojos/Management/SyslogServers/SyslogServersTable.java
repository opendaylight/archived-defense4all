/**
 * SyslogServersTable.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Management.SyslogServers;


/**
 * This structure describes the parameters of a SyslogServersTable
 */
public class SyslogServersTable  implements java.io.Serializable {
    private java.lang.String syslogServerAddress;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogServerOperationalStatus;

    private java.lang.Long syslogServerSourcePort;

    private java.lang.Long syslogServerDestinationPort;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerFacility syslogServerFacility;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerProtocol syslogServerProtocol;

    private java.lang.String syslogCACertificate;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerRowStatus syslogServerRowStatus;

    private java.lang.Long syslogServerRowStatus2;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogSecuritySending;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogHealthSending;

    private com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogUserAuditSending;

    public SyslogServersTable() {
    }

    public SyslogServersTable(
           java.lang.String syslogServerAddress,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogServerOperationalStatus,
           java.lang.Long syslogServerSourcePort,
           java.lang.Long syslogServerDestinationPort,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerFacility syslogServerFacility,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerProtocol syslogServerProtocol,
           java.lang.String syslogCACertificate,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerRowStatus syslogServerRowStatus,
           java.lang.Long syslogServerRowStatus2,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogSecuritySending,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogHealthSending,
           com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogUserAuditSending) {
           this.syslogServerAddress = syslogServerAddress;
           this.syslogServerOperationalStatus = syslogServerOperationalStatus;
           this.syslogServerSourcePort = syslogServerSourcePort;
           this.syslogServerDestinationPort = syslogServerDestinationPort;
           this.syslogServerFacility = syslogServerFacility;
           this.syslogServerProtocol = syslogServerProtocol;
           this.syslogCACertificate = syslogCACertificate;
           this.syslogServerRowStatus = syslogServerRowStatus;
           this.syslogServerRowStatus2 = syslogServerRowStatus2;
           this.syslogSecuritySending = syslogSecuritySending;
           this.syslogHealthSending = syslogHealthSending;
           this.syslogUserAuditSending = syslogUserAuditSending;
    }


    /**
     * Gets the syslogServerAddress value for this SyslogServersTable.
     * 
     * @return syslogServerAddress
     */
    public java.lang.String getSyslogServerAddress() {
        return syslogServerAddress;
    }


    /**
     * Sets the syslogServerAddress value for this SyslogServersTable.
     * 
     * @param syslogServerAddress
     */
    public void setSyslogServerAddress(java.lang.String syslogServerAddress) {
        this.syslogServerAddress = syslogServerAddress;
    }


    /**
     * Gets the syslogServerOperationalStatus value for this SyslogServersTable.
     * 
     * @return syslogServerOperationalStatus
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus getSyslogServerOperationalStatus() {
        return syslogServerOperationalStatus;
    }


    /**
     * Sets the syslogServerOperationalStatus value for this SyslogServersTable.
     * 
     * @param syslogServerOperationalStatus
     */
    public void setSyslogServerOperationalStatus(com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogServerOperationalStatus) {
        this.syslogServerOperationalStatus = syslogServerOperationalStatus;
    }


    /**
     * Gets the syslogServerSourcePort value for this SyslogServersTable.
     * 
     * @return syslogServerSourcePort
     */
    public java.lang.Long getSyslogServerSourcePort() {
        return syslogServerSourcePort;
    }


    /**
     * Sets the syslogServerSourcePort value for this SyslogServersTable.
     * 
     * @param syslogServerSourcePort
     */
    public void setSyslogServerSourcePort(java.lang.Long syslogServerSourcePort) {
        this.syslogServerSourcePort = syslogServerSourcePort;
    }


    /**
     * Gets the syslogServerDestinationPort value for this SyslogServersTable.
     * 
     * @return syslogServerDestinationPort
     */
    public java.lang.Long getSyslogServerDestinationPort() {
        return syslogServerDestinationPort;
    }


    /**
     * Sets the syslogServerDestinationPort value for this SyslogServersTable.
     * 
     * @param syslogServerDestinationPort
     */
    public void setSyslogServerDestinationPort(java.lang.Long syslogServerDestinationPort) {
        this.syslogServerDestinationPort = syslogServerDestinationPort;
    }


    /**
     * Gets the syslogServerFacility value for this SyslogServersTable.
     * 
     * @return syslogServerFacility
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerFacility getSyslogServerFacility() {
        return syslogServerFacility;
    }


    /**
     * Sets the syslogServerFacility value for this SyslogServersTable.
     * 
     * @param syslogServerFacility
     */
    public void setSyslogServerFacility(com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerFacility syslogServerFacility) {
        this.syslogServerFacility = syslogServerFacility;
    }


    /**
     * Gets the syslogServerProtocol value for this SyslogServersTable.
     * 
     * @return syslogServerProtocol
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerProtocol getSyslogServerProtocol() {
        return syslogServerProtocol;
    }


    /**
     * Sets the syslogServerProtocol value for this SyslogServersTable.
     * 
     * @param syslogServerProtocol
     */
    public void setSyslogServerProtocol(com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerProtocol syslogServerProtocol) {
        this.syslogServerProtocol = syslogServerProtocol;
    }


    /**
     * Gets the syslogCACertificate value for this SyslogServersTable.
     * 
     * @return syslogCACertificate
     */
    public java.lang.String getSyslogCACertificate() {
        return syslogCACertificate;
    }


    /**
     * Sets the syslogCACertificate value for this SyslogServersTable.
     * 
     * @param syslogCACertificate
     */
    public void setSyslogCACertificate(java.lang.String syslogCACertificate) {
        this.syslogCACertificate = syslogCACertificate;
    }


    /**
     * Gets the syslogServerRowStatus value for this SyslogServersTable.
     * 
     * @return syslogServerRowStatus
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerRowStatus getSyslogServerRowStatus() {
        return syslogServerRowStatus;
    }


    /**
     * Sets the syslogServerRowStatus value for this SyslogServersTable.
     * 
     * @param syslogServerRowStatus
     */
    public void setSyslogServerRowStatus(com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerRowStatus syslogServerRowStatus) {
        this.syslogServerRowStatus = syslogServerRowStatus;
    }


    /**
     * Gets the syslogServerRowStatus2 value for this SyslogServersTable.
     * 
     * @return syslogServerRowStatus2
     */
    public java.lang.Long getSyslogServerRowStatus2() {
        return syslogServerRowStatus2;
    }


    /**
     * Sets the syslogServerRowStatus2 value for this SyslogServersTable.
     * 
     * @param syslogServerRowStatus2
     */
    public void setSyslogServerRowStatus2(java.lang.Long syslogServerRowStatus2) {
        this.syslogServerRowStatus2 = syslogServerRowStatus2;
    }


    /**
     * Gets the syslogSecuritySending value for this SyslogServersTable.
     * 
     * @return syslogSecuritySending
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus getSyslogSecuritySending() {
        return syslogSecuritySending;
    }


    /**
     * Sets the syslogSecuritySending value for this SyslogServersTable.
     * 
     * @param syslogSecuritySending
     */
    public void setSyslogSecuritySending(com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogSecuritySending) {
        this.syslogSecuritySending = syslogSecuritySending;
    }


    /**
     * Gets the syslogHealthSending value for this SyslogServersTable.
     * 
     * @return syslogHealthSending
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus getSyslogHealthSending() {
        return syslogHealthSending;
    }


    /**
     * Sets the syslogHealthSending value for this SyslogServersTable.
     * 
     * @param syslogHealthSending
     */
    public void setSyslogHealthSending(com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogHealthSending) {
        this.syslogHealthSending = syslogHealthSending;
    }


    /**
     * Gets the syslogUserAuditSending value for this SyslogServersTable.
     * 
     * @return syslogUserAuditSending
     */
    public com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus getSyslogUserAuditSending() {
        return syslogUserAuditSending;
    }


    /**
     * Sets the syslogUserAuditSending value for this SyslogServersTable.
     * 
     * @param syslogUserAuditSending
     */
    public void setSyslogUserAuditSending(com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus syslogUserAuditSending) {
        this.syslogUserAuditSending = syslogUserAuditSending;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof SyslogServersTable)) return false;
        SyslogServersTable other = (SyslogServersTable) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.syslogServerAddress==null && other.getSyslogServerAddress()==null) || 
             (this.syslogServerAddress!=null &&
              this.syslogServerAddress.equals(other.getSyslogServerAddress()))) &&
            ((this.syslogServerOperationalStatus==null && other.getSyslogServerOperationalStatus()==null) || 
             (this.syslogServerOperationalStatus!=null &&
              this.syslogServerOperationalStatus.equals(other.getSyslogServerOperationalStatus()))) &&
            ((this.syslogServerSourcePort==null && other.getSyslogServerSourcePort()==null) || 
             (this.syslogServerSourcePort!=null &&
              this.syslogServerSourcePort.equals(other.getSyslogServerSourcePort()))) &&
            ((this.syslogServerDestinationPort==null && other.getSyslogServerDestinationPort()==null) || 
             (this.syslogServerDestinationPort!=null &&
              this.syslogServerDestinationPort.equals(other.getSyslogServerDestinationPort()))) &&
            ((this.syslogServerFacility==null && other.getSyslogServerFacility()==null) || 
             (this.syslogServerFacility!=null &&
              this.syslogServerFacility.equals(other.getSyslogServerFacility()))) &&
            ((this.syslogServerProtocol==null && other.getSyslogServerProtocol()==null) || 
             (this.syslogServerProtocol!=null &&
              this.syslogServerProtocol.equals(other.getSyslogServerProtocol()))) &&
            ((this.syslogCACertificate==null && other.getSyslogCACertificate()==null) || 
             (this.syslogCACertificate!=null &&
              this.syslogCACertificate.equals(other.getSyslogCACertificate()))) &&
            ((this.syslogServerRowStatus==null && other.getSyslogServerRowStatus()==null) || 
             (this.syslogServerRowStatus!=null &&
              this.syslogServerRowStatus.equals(other.getSyslogServerRowStatus()))) &&
            ((this.syslogServerRowStatus2==null && other.getSyslogServerRowStatus2()==null) || 
             (this.syslogServerRowStatus2!=null &&
              this.syslogServerRowStatus2.equals(other.getSyslogServerRowStatus2()))) &&
            ((this.syslogSecuritySending==null && other.getSyslogSecuritySending()==null) || 
             (this.syslogSecuritySending!=null &&
              this.syslogSecuritySending.equals(other.getSyslogSecuritySending()))) &&
            ((this.syslogHealthSending==null && other.getSyslogHealthSending()==null) || 
             (this.syslogHealthSending!=null &&
              this.syslogHealthSending.equals(other.getSyslogHealthSending()))) &&
            ((this.syslogUserAuditSending==null && other.getSyslogUserAuditSending()==null) || 
             (this.syslogUserAuditSending!=null &&
              this.syslogUserAuditSending.equals(other.getSyslogUserAuditSending())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSyslogServerAddress() != null) {
            _hashCode += getSyslogServerAddress().hashCode();
        }
        if (getSyslogServerOperationalStatus() != null) {
            _hashCode += getSyslogServerOperationalStatus().hashCode();
        }
        if (getSyslogServerSourcePort() != null) {
            _hashCode += getSyslogServerSourcePort().hashCode();
        }
        if (getSyslogServerDestinationPort() != null) {
            _hashCode += getSyslogServerDestinationPort().hashCode();
        }
        if (getSyslogServerFacility() != null) {
            _hashCode += getSyslogServerFacility().hashCode();
        }
        if (getSyslogServerProtocol() != null) {
            _hashCode += getSyslogServerProtocol().hashCode();
        }
        if (getSyslogCACertificate() != null) {
            _hashCode += getSyslogCACertificate().hashCode();
        }
        if (getSyslogServerRowStatus() != null) {
            _hashCode += getSyslogServerRowStatus().hashCode();
        }
        if (getSyslogServerRowStatus2() != null) {
            _hashCode += getSyslogServerRowStatus2().hashCode();
        }
        if (getSyslogSecuritySending() != null) {
            _hashCode += getSyslogSecuritySending().hashCode();
        }
        if (getSyslogHealthSending() != null) {
            _hashCode += getSyslogHealthSending().hashCode();
        }
        if (getSyslogUserAuditSending() != null) {
            _hashCode += getSyslogUserAuditSending().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(SyslogServersTable.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerAddress");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerAddress"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerOperationalStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerOperationalStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "FeatureStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerSourcePort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerSourcePort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerDestinationPort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerDestinationPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerFacility");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerFacility"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerFacility"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerProtocol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerProtocol"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerProtocol"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogCACertificate");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogCACertificate"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerRowStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerRowStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "SyslogServersTable_syslogServerRowStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogServerRowStatus");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogServerRowStatus"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogSecuritySending");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogSecuritySending"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "FeatureStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogHealthSending");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogHealthSending"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "FeatureStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("syslogUserAuditSending");
        elemField.setXmlName(new javax.xml.namespace.QName("", "syslogUserAuditSending"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Management.SyslogServers", "FeatureStatus"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
