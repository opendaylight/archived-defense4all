/**
 * ExcludeAttack.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a ExcludeAttack
 */
public class ExcludeAttack  implements java.io.Serializable {
    /* The attack id must exsist in appsec signature attacks db */
    private long ID;

    /* This variable indicates the exclude attack name */
    private java.lang.String attackName;

    /* Excluded source network. */
    private java.lang.String excludeSrc;

    /* Excluded destination network */
    private java.lang.String excludeDst;

    public ExcludeAttack() {
    }

    public ExcludeAttack(
           long ID,
           java.lang.String attackName,
           java.lang.String excludeSrc,
           java.lang.String excludeDst) {
           this.ID = ID;
           this.attackName = attackName;
           this.excludeSrc = excludeSrc;
           this.excludeDst = excludeDst;
    }


    /**
     * Gets the ID value for this ExcludeAttack.
     * 
     * @return ID   * The attack id must exsist in appsec signature attacks db
     */
    public long getID() {
        return ID;
    }


    /**
     * Sets the ID value for this ExcludeAttack.
     * 
     * @param ID   * The attack id must exsist in appsec signature attacks db
     */
    public void setID(long ID) {
        this.ID = ID;
    }


    /**
     * Gets the attackName value for this ExcludeAttack.
     * 
     * @return attackName   * This variable indicates the exclude attack name
     */
    public java.lang.String getAttackName() {
        return attackName;
    }


    /**
     * Sets the attackName value for this ExcludeAttack.
     * 
     * @param attackName   * This variable indicates the exclude attack name
     */
    public void setAttackName(java.lang.String attackName) {
        this.attackName = attackName;
    }


    /**
     * Gets the excludeSrc value for this ExcludeAttack.
     * 
     * @return excludeSrc   * Excluded source network.
     */
    public java.lang.String getExcludeSrc() {
        return excludeSrc;
    }


    /**
     * Sets the excludeSrc value for this ExcludeAttack.
     * 
     * @param excludeSrc   * Excluded source network.
     */
    public void setExcludeSrc(java.lang.String excludeSrc) {
        this.excludeSrc = excludeSrc;
    }


    /**
     * Gets the excludeDst value for this ExcludeAttack.
     * 
     * @return excludeDst   * Excluded destination network
     */
    public java.lang.String getExcludeDst() {
        return excludeDst;
    }


    /**
     * Sets the excludeDst value for this ExcludeAttack.
     * 
     * @param excludeDst   * Excluded destination network
     */
    public void setExcludeDst(java.lang.String excludeDst) {
        this.excludeDst = excludeDst;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof ExcludeAttack)) return false;
        ExcludeAttack other = (ExcludeAttack) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            this.ID == other.getID() &&
            ((this.attackName==null && other.getAttackName()==null) || 
             (this.attackName!=null &&
              this.attackName.equals(other.getAttackName()))) &&
            ((this.excludeSrc==null && other.getExcludeSrc()==null) || 
             (this.excludeSrc!=null &&
              this.excludeSrc.equals(other.getExcludeSrc()))) &&
            ((this.excludeDst==null && other.getExcludeDst()==null) || 
             (this.excludeDst!=null &&
              this.excludeDst.equals(other.getExcludeDst())));
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
        _hashCode += new Long(getID()).hashCode();
        if (getAttackName() != null) {
            _hashCode += getAttackName().hashCode();
        }
        if (getExcludeSrc() != null) {
            _hashCode += getExcludeSrc().hashCode();
        }
        if (getExcludeDst() != null) {
            _hashCode += getExcludeDst().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(ExcludeAttack.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "ExcludeAttack"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ID");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ID"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludeSrc");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExcludeSrc"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("excludeDst");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExcludeDst"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
