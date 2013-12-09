/**
 * PoliciesResourcesKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.Policy;


/**
 * Identifies and entry in the PoliciesResources table
 */
public class PoliciesResourcesKey  implements java.io.Serializable {
    private java.lang.String name;

    private com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResources_Direction direction;

    public PoliciesResourcesKey() {
    }

    public PoliciesResourcesKey(
           java.lang.String name,
           com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResources_Direction direction) {
           this.name = name;
           this.direction = direction;
    }


    /**
     * Gets the name value for this PoliciesResourcesKey.
     * 
     * @return name
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this PoliciesResourcesKey.
     * 
     * @param name
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the direction value for this PoliciesResourcesKey.
     * 
     * @return direction
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResources_Direction getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this PoliciesResourcesKey.
     * 
     * @param direction
     */
    public void setDirection(com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResources_Direction direction) {
        this.direction = direction;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof PoliciesResourcesKey)) return false;
        PoliciesResourcesKey other = (PoliciesResourcesKey) obj;
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
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection())));
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
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PoliciesResourcesKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "PoliciesResourcesKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.Policy", "PoliciesResources_Direction"));
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
