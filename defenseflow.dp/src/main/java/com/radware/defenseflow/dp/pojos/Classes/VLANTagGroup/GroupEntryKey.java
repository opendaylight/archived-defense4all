/**
 * GroupEntryKey.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup;


/**
 * Identifies and entry in the GroupEntry table
 */
public class GroupEntryKey  implements java.io.Serializable {
    /* The name of the VLAN tag group. */
    private java.lang.String groupName;

    /* The value of VLAN tag in discrete mode. */
    private long VLANTag;

    /* The apos;fromapos; part of the VLAN tag range. */
    private long VLANTagRangeFrom;

    public GroupEntryKey() {
    }

    public GroupEntryKey(
           java.lang.String groupName,
           long VLANTag,
           long VLANTagRangeFrom) {
           this.groupName = groupName;
           this.VLANTag = VLANTag;
           this.VLANTagRangeFrom = VLANTagRangeFrom;
    }


    /**
     * Gets the groupName value for this GroupEntryKey.
     * 
     * @return groupName   * The name of the VLAN tag group.
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this GroupEntryKey.
     * 
     * @param groupName   * The name of the VLAN tag group.
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }


    /**
     * Gets the VLANTag value for this GroupEntryKey.
     * 
     * @return VLANTag   * The value of VLAN tag in discrete mode.
     */
    public long getVLANTag() {
        return VLANTag;
    }


    /**
     * Sets the VLANTag value for this GroupEntryKey.
     * 
     * @param VLANTag   * The value of VLAN tag in discrete mode.
     */
    public void setVLANTag(long VLANTag) {
        this.VLANTag = VLANTag;
    }


    /**
     * Gets the VLANTagRangeFrom value for this GroupEntryKey.
     * 
     * @return VLANTagRangeFrom   * The apos;fromapos; part of the VLAN tag range.
     */
    public long getVLANTagRangeFrom() {
        return VLANTagRangeFrom;
    }


    /**
     * Sets the VLANTagRangeFrom value for this GroupEntryKey.
     * 
     * @param VLANTagRangeFrom   * The apos;fromapos; part of the VLAN tag range.
     */
    public void setVLANTagRangeFrom(long VLANTagRangeFrom) {
        this.VLANTagRangeFrom = VLANTagRangeFrom;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GroupEntryKey)) return false;
        GroupEntryKey other = (GroupEntryKey) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.groupName==null && other.getGroupName()==null) || 
             (this.groupName!=null &&
              this.groupName.equals(other.getGroupName()))) &&
            this.VLANTag == other.getVLANTag() &&
            this.VLANTagRangeFrom == other.getVLANTagRangeFrom();
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
        if (getGroupName() != null) {
            _hashCode += getGroupName().hashCode();
        }
        _hashCode += new Long(getVLANTag()).hashCode();
        _hashCode += new Long(getVLANTagRangeFrom()).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GroupEntryKey.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Classes.VLANTagGroup", "GroupEntryKey"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GroupName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VLANTag");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VLANTag"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VLANTagRangeFrom");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VLANTagRangeFrom"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
