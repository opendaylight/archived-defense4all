/**
 * GroupEntry.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup;


/**
 * This structure describes the parameters of a GroupEntry
 */
public class GroupEntry  implements java.io.Serializable {
    /* The name of the VLAN tag group. */
    private java.lang.String groupName;

    /* The value of VLAN tag in discrete mode. */
    private long VLANTag;

    /* The apos;fromapos; part of the VLAN tag range. */
    private long VLANTagRangeFrom;

    /* The apos;toapos; part of the VLAN tag range. */
    private java.lang.Long VLANTagRangeTo;

    /* The mode of the VLAN tag group: discrete or range. */
    private com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry_GroupMode groupMode;

    public GroupEntry() {
    }

    public GroupEntry(
           java.lang.String groupName,
           long VLANTag,
           long VLANTagRangeFrom,
           java.lang.Long VLANTagRangeTo,
           com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry_GroupMode groupMode) {
           this.groupName = groupName;
           this.VLANTag = VLANTag;
           this.VLANTagRangeFrom = VLANTagRangeFrom;
           this.VLANTagRangeTo = VLANTagRangeTo;
           this.groupMode = groupMode;
    }


    /**
     * Gets the groupName value for this GroupEntry.
     * 
     * @return groupName   * The name of the VLAN tag group.
     */
    public java.lang.String getGroupName() {
        return groupName;
    }


    /**
     * Sets the groupName value for this GroupEntry.
     * 
     * @param groupName   * The name of the VLAN tag group.
     */
    public void setGroupName(java.lang.String groupName) {
        this.groupName = groupName;
    }


    /**
     * Gets the VLANTag value for this GroupEntry.
     * 
     * @return VLANTag   * The value of VLAN tag in discrete mode.
     */
    public long getVLANTag() {
        return VLANTag;
    }


    /**
     * Sets the VLANTag value for this GroupEntry.
     * 
     * @param VLANTag   * The value of VLAN tag in discrete mode.
     */
    public void setVLANTag(long VLANTag) {
        this.VLANTag = VLANTag;
    }


    /**
     * Gets the VLANTagRangeFrom value for this GroupEntry.
     * 
     * @return VLANTagRangeFrom   * The apos;fromapos; part of the VLAN tag range.
     */
    public long getVLANTagRangeFrom() {
        return VLANTagRangeFrom;
    }


    /**
     * Sets the VLANTagRangeFrom value for this GroupEntry.
     * 
     * @param VLANTagRangeFrom   * The apos;fromapos; part of the VLAN tag range.
     */
    public void setVLANTagRangeFrom(long VLANTagRangeFrom) {
        this.VLANTagRangeFrom = VLANTagRangeFrom;
    }


    /**
     * Gets the VLANTagRangeTo value for this GroupEntry.
     * 
     * @return VLANTagRangeTo   * The apos;toapos; part of the VLAN tag range.
     */
    public java.lang.Long getVLANTagRangeTo() {
        return VLANTagRangeTo;
    }


    /**
     * Sets the VLANTagRangeTo value for this GroupEntry.
     * 
     * @param VLANTagRangeTo   * The apos;toapos; part of the VLAN tag range.
     */
    public void setVLANTagRangeTo(java.lang.Long VLANTagRangeTo) {
        this.VLANTagRangeTo = VLANTagRangeTo;
    }


    /**
     * Gets the groupMode value for this GroupEntry.
     * 
     * @return groupMode   * The mode of the VLAN tag group: discrete or range.
     */
    public com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry_GroupMode getGroupMode() {
        return groupMode;
    }


    /**
     * Sets the groupMode value for this GroupEntry.
     * 
     * @param groupMode   * The mode of the VLAN tag group: discrete or range.
     */
    public void setGroupMode(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry_GroupMode groupMode) {
        this.groupMode = groupMode;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof GroupEntry)) return false;
        GroupEntry other = (GroupEntry) obj;
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
            this.VLANTagRangeFrom == other.getVLANTagRangeFrom() &&
            ((this.VLANTagRangeTo==null && other.getVLANTagRangeTo()==null) || 
             (this.VLANTagRangeTo!=null &&
              this.VLANTagRangeTo.equals(other.getVLANTagRangeTo()))) &&
            ((this.groupMode==null && other.getGroupMode()==null) || 
             (this.groupMode!=null &&
              this.groupMode.equals(other.getGroupMode())));
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
        if (getVLANTagRangeTo() != null) {
            _hashCode += getVLANTagRangeTo().hashCode();
        }
        if (getGroupMode() != null) {
            _hashCode += getGroupMode().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(GroupEntry.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Classes.VLANTagGroup", "GroupEntry"));
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
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("VLANTagRangeTo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "VLANTagRangeTo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("groupMode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "GroupMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Classes.VLANTagGroup", "GroupEntry_GroupMode"));
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
