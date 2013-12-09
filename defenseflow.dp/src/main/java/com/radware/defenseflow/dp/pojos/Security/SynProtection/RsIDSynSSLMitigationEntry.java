/**
 * RsIDSynSSLMitigationEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SynProtection;


/**
 * This structure describes the parameters of a rsIDSynSSLMitigationEntry
 */
public class RsIDSynSSLMitigationEntry  implements java.io.Serializable {
    /* The key of this table. */
    private java.lang.String name;

    /* The VIP IP address. */
    private java.lang.String SSLVIP;

    /* The ADC server IP address. */
    private java.lang.String SSLServerIP;

    /* ADC MAC */
    private java.lang.String VIPMAC;

    /* Related Policy ID */
    private java.lang.String networkPolicyName;

    /* This variable indicates the state of the entry. */
    private com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSynSSLMitigationEntry_State state;

    public RsIDSynSSLMitigationEntry() {
    }

    public RsIDSynSSLMitigationEntry(
           java.lang.String name,
           java.lang.String SSLVIP,
           java.lang.String SSLServerIP,
           java.lang.String VIPMAC,
           java.lang.String networkPolicyName,
           com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSynSSLMitigationEntry_State state) {
           this.name = name;
           this.SSLVIP = SSLVIP;
           this.SSLServerIP = SSLServerIP;
           this.VIPMAC = VIPMAC;
           this.networkPolicyName = networkPolicyName;
           this.state = state;
    }


    /**
     * Gets the name value for this RsIDSynSSLMitigationEntry.
     * 
     * @return name   * The key of this table.
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this RsIDSynSSLMitigationEntry.
     * 
     * @param name   * The key of this table.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the SSLVIP value for this RsIDSynSSLMitigationEntry.
     * 
     * @return SSLVIP   * The VIP IP address.
     */
    public java.lang.String getSSLVIP() {
        return SSLVIP;
    }


    /**
     * Sets the SSLVIP value for this RsIDSynSSLMitigationEntry.
     * 
     * @param SSLVIP   * The VIP IP address.
     */
    public void setSSLVIP(java.lang.String SSLVIP) {
        this.SSLVIP = SSLVIP;
    }


    /**
     * Gets the SSLServerIP value for this RsIDSynSSLMitigationEntry.
     * 
     * @return SSLServerIP   * The ADC server IP address.
     */
    public java.lang.String getSSLServerIP() {
        return SSLServerIP;
    }


    /**
     * Sets the SSLServerIP value for this RsIDSynSSLMitigationEntry.
     * 
     * @param SSLServerIP   * The ADC server IP address.
     */
    public void setSSLServerIP(java.lang.String SSLServerIP) {
        this.SSLServerIP = SSLServerIP;
    }


    /**
     * Gets the VIPMAC value for this RsIDSynSSLMitigationEntry.
     * 
     * @return VIPMAC   * ADC MAC
     */
    public java.lang.String getVIPMAC() {
        return VIPMAC;
    }


    /**
     * Sets the VIPMAC value for this RsIDSynSSLMitigationEntry.
     * 
     * @param VIPMAC   * ADC MAC
     */
    public void setVIPMAC(java.lang.String VIPMAC) {
        this.VIPMAC = VIPMAC;
    }


    /**
     * Gets the networkPolicyName value for this RsIDSynSSLMitigationEntry.
     * 
     * @return networkPolicyName   * Related Policy ID
     */
    public java.lang.String getNetworkPolicyName() {
        return networkPolicyName;
    }


    /**
     * Sets the networkPolicyName value for this RsIDSynSSLMitigationEntry.
     * 
     * @param networkPolicyName   * Related Policy ID
     */
    public void setNetworkPolicyName(java.lang.String networkPolicyName) {
        this.networkPolicyName = networkPolicyName;
    }


    /**
     * Gets the state value for this RsIDSynSSLMitigationEntry.
     * 
     * @return state   * This variable indicates the state of the entry.
     */
    public com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSynSSLMitigationEntry_State getState() {
        return state;
    }


    /**
     * Sets the state value for this RsIDSynSSLMitigationEntry.
     * 
     * @param state   * This variable indicates the state of the entry.
     */
    public void setState(com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSynSSLMitigationEntry_State state) {
        this.state = state;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof RsIDSynSSLMitigationEntry)) return false;
        RsIDSynSSLMitigationEntry other = (RsIDSynSSLMitigationEntry) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.name==null && other.getName()==null) || 
             (this.name!=null &&
              this.name.equals(other.getName()))) &&
            ((this.SSLVIP==null && other.getSSLVIP()==null) || 
             (this.SSLVIP!=null &&
              this.SSLVIP.equals(other.getSSLVIP()))) &&
            ((this.SSLServerIP==null && other.getSSLServerIP()==null) || 
             (this.SSLServerIP!=null &&
              this.SSLServerIP.equals(other.getSSLServerIP()))) &&
            ((this.VIPMAC==null && other.getVIPMAC()==null) || 
             (this.VIPMAC!=null &&
              this.VIPMAC.equals(other.getVIPMAC()))) &&
            ((this.networkPolicyName==null && other.getNetworkPolicyName()==null) || 
             (this.networkPolicyName!=null &&
              this.networkPolicyName.equals(other.getNetworkPolicyName()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState())));
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
        if (getName() != null) {
            _hashCode += getName().hashCode();
        }
        if (getSSLVIP() != null) {
            _hashCode += getSSLVIP().hashCode();
        }
        if (getSSLServerIP() != null) {
            _hashCode += getSSLServerIP().hashCode();
        }
        if (getVIPMAC() != null) {
            _hashCode += getVIPMAC().hashCode();
        }
        if (getNetworkPolicyName() != null) {
            _hashCode += getNetworkPolicyName().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(RsIDSynSSLMitigationEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSynSSLMitigationEntry"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSLVIP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SSLVIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("SSLServerIP");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SSLServerIP"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VIPMAC");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VIPMAC"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("networkPolicyName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "NetworkPolicyName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SynProtection", "rsIDSynSSLMitigationEntry_State"));
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
