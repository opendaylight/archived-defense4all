/**
 * AttacksUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a AttacksUser
 */
public class AttacksUser  implements java.io.Serializable {
    /* This variable indicates the object type- whether it is a filter
     * or group */
    private long ID;

    /* The Name for the attack must be unique. */
    private java.lang.String attackName;

    /* The Name for the filter must be unique. */
    private java.lang.String filterName;

    /* This variable indicates the object type- whether it is a filter
     * or group */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_FilterType filterType;

    /* This variable indicates the time interval, in miliseconds,
     * to track a detection */
    private java.lang.Long trackingTime;

    /* This variable indicates the threshold */
    private java.lang.Long activeThreshold;

    /* This variable indicates how to count */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_TrackingType trackingType;

    /* The Name for the filter must be unique. */
    private java.lang.String attackMessage;

    /* This variable indicates the attack report mode */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_ActionMode actionMode;

    /* Denotes the type of the filter polciy. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_AttackType attackType;

    /* This variable indicates the per-attack packet report */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketReport packetReport;

    /* This variable indicates the risk of the attack */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Risk risk;

    /* This variable indicates whether the attack should be classified
     * or not */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_State state;

    /* This variable indicates the direction of the attack */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Direction direction;

    /* This variable indicates the suspend action type of the attack */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_SuspendAction suspendAction;

    /* This variable indicates the drop threshold */
    private java.lang.Long dropThreshold;

    /* This variable indicates the term threshold */
    private java.lang.Long termThreshold;

    /* This variable indicates exclude source network */
    private java.lang.String excludeSrc;

    /* This variable indicates exclude destination network */
    private java.lang.String excludeDest;

    /* This variable indicates the per-attack packet trace */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketTrace packetTrace;

    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_rsIDSAsAttackQuarantine rsIDSAsAttackQuarantine;

    public AttacksUser() {
    }

    public AttacksUser(
           long ID,
           java.lang.String attackName,
           java.lang.String filterName,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_FilterType filterType,
           java.lang.Long trackingTime,
           java.lang.Long activeThreshold,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_TrackingType trackingType,
           java.lang.String attackMessage,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_ActionMode actionMode,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_AttackType attackType,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketReport packetReport,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Risk risk,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_State state,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Direction direction,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_SuspendAction suspendAction,
           java.lang.Long dropThreshold,
           java.lang.Long termThreshold,
           java.lang.String excludeSrc,
           java.lang.String excludeDest,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketTrace packetTrace,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_rsIDSAsAttackQuarantine rsIDSAsAttackQuarantine) {
           this.ID = ID;
           this.attackName = attackName;
           this.filterName = filterName;
           this.filterType = filterType;
           this.trackingTime = trackingTime;
           this.activeThreshold = activeThreshold;
           this.trackingType = trackingType;
           this.attackMessage = attackMessage;
           this.actionMode = actionMode;
           this.attackType = attackType;
           this.packetReport = packetReport;
           this.risk = risk;
           this.state = state;
           this.direction = direction;
           this.suspendAction = suspendAction;
           this.dropThreshold = dropThreshold;
           this.termThreshold = termThreshold;
           this.excludeSrc = excludeSrc;
           this.excludeDest = excludeDest;
           this.packetTrace = packetTrace;
           this.rsIDSAsAttackQuarantine = rsIDSAsAttackQuarantine;
    }


    /**
     * Gets the ID value for this AttacksUser.
     * 
     * @return ID   * This variable indicates the object type- whether it is a filter
     * or group
     */
    public long getID() {
        return ID;
    }


    /**
     * Sets the ID value for this AttacksUser.
     * 
     * @param ID   * This variable indicates the object type- whether it is a filter
     * or group
     */
    public void setID(long ID) {
        this.ID = ID;
    }


    /**
     * Gets the attackName value for this AttacksUser.
     * 
     * @return attackName   * The Name for the attack must be unique.
     */
    public java.lang.String getAttackName() {
        return attackName;
    }


    /**
     * Sets the attackName value for this AttacksUser.
     * 
     * @param attackName   * The Name for the attack must be unique.
     */
    public void setAttackName(java.lang.String attackName) {
        this.attackName = attackName;
    }


    /**
     * Gets the filterName value for this AttacksUser.
     * 
     * @return filterName   * The Name for the filter must be unique.
     */
    public java.lang.String getFilterName() {
        return filterName;
    }


    /**
     * Sets the filterName value for this AttacksUser.
     * 
     * @param filterName   * The Name for the filter must be unique.
     */
    public void setFilterName(java.lang.String filterName) {
        this.filterName = filterName;
    }


    /**
     * Gets the filterType value for this AttacksUser.
     * 
     * @return filterType   * This variable indicates the object type- whether it is a filter
     * or group
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_FilterType getFilterType() {
        return filterType;
    }


    /**
     * Sets the filterType value for this AttacksUser.
     * 
     * @param filterType   * This variable indicates the object type- whether it is a filter
     * or group
     */
    public void setFilterType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_FilterType filterType) {
        this.filterType = filterType;
    }


    /**
     * Gets the trackingTime value for this AttacksUser.
     * 
     * @return trackingTime   * This variable indicates the time interval, in miliseconds,
     * to track a detection
     */
    public java.lang.Long getTrackingTime() {
        return trackingTime;
    }


    /**
     * Sets the trackingTime value for this AttacksUser.
     * 
     * @param trackingTime   * This variable indicates the time interval, in miliseconds,
     * to track a detection
     */
    public void setTrackingTime(java.lang.Long trackingTime) {
        this.trackingTime = trackingTime;
    }


    /**
     * Gets the activeThreshold value for this AttacksUser.
     * 
     * @return activeThreshold   * This variable indicates the threshold
     */
    public java.lang.Long getActiveThreshold() {
        return activeThreshold;
    }


    /**
     * Sets the activeThreshold value for this AttacksUser.
     * 
     * @param activeThreshold   * This variable indicates the threshold
     */
    public void setActiveThreshold(java.lang.Long activeThreshold) {
        this.activeThreshold = activeThreshold;
    }


    /**
     * Gets the trackingType value for this AttacksUser.
     * 
     * @return trackingType   * This variable indicates how to count
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_TrackingType getTrackingType() {
        return trackingType;
    }


    /**
     * Sets the trackingType value for this AttacksUser.
     * 
     * @param trackingType   * This variable indicates how to count
     */
    public void setTrackingType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_TrackingType trackingType) {
        this.trackingType = trackingType;
    }


    /**
     * Gets the attackMessage value for this AttacksUser.
     * 
     * @return attackMessage   * The Name for the filter must be unique.
     */
    public java.lang.String getAttackMessage() {
        return attackMessage;
    }


    /**
     * Sets the attackMessage value for this AttacksUser.
     * 
     * @param attackMessage   * The Name for the filter must be unique.
     */
    public void setAttackMessage(java.lang.String attackMessage) {
        this.attackMessage = attackMessage;
    }


    /**
     * Gets the actionMode value for this AttacksUser.
     * 
     * @return actionMode   * This variable indicates the attack report mode
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_ActionMode getActionMode() {
        return actionMode;
    }


    /**
     * Sets the actionMode value for this AttacksUser.
     * 
     * @param actionMode   * This variable indicates the attack report mode
     */
    public void setActionMode(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_ActionMode actionMode) {
        this.actionMode = actionMode;
    }


    /**
     * Gets the attackType value for this AttacksUser.
     * 
     * @return attackType   * Denotes the type of the filter polciy.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_AttackType getAttackType() {
        return attackType;
    }


    /**
     * Sets the attackType value for this AttacksUser.
     * 
     * @param attackType   * Denotes the type of the filter polciy.
     */
    public void setAttackType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_AttackType attackType) {
        this.attackType = attackType;
    }


    /**
     * Gets the packetReport value for this AttacksUser.
     * 
     * @return packetReport   * This variable indicates the per-attack packet report
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketReport getPacketReport() {
        return packetReport;
    }


    /**
     * Sets the packetReport value for this AttacksUser.
     * 
     * @param packetReport   * This variable indicates the per-attack packet report
     */
    public void setPacketReport(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketReport packetReport) {
        this.packetReport = packetReport;
    }


    /**
     * Gets the risk value for this AttacksUser.
     * 
     * @return risk   * This variable indicates the risk of the attack
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Risk getRisk() {
        return risk;
    }


    /**
     * Sets the risk value for this AttacksUser.
     * 
     * @param risk   * This variable indicates the risk of the attack
     */
    public void setRisk(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Risk risk) {
        this.risk = risk;
    }


    /**
     * Gets the state value for this AttacksUser.
     * 
     * @return state   * This variable indicates whether the attack should be classified
     * or not
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_State getState() {
        return state;
    }


    /**
     * Sets the state value for this AttacksUser.
     * 
     * @param state   * This variable indicates whether the attack should be classified
     * or not
     */
    public void setState(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_State state) {
        this.state = state;
    }


    /**
     * Gets the direction value for this AttacksUser.
     * 
     * @return direction   * This variable indicates the direction of the attack
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Direction getDirection() {
        return direction;
    }


    /**
     * Sets the direction value for this AttacksUser.
     * 
     * @param direction   * This variable indicates the direction of the attack
     */
    public void setDirection(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_Direction direction) {
        this.direction = direction;
    }


    /**
     * Gets the suspendAction value for this AttacksUser.
     * 
     * @return suspendAction   * This variable indicates the suspend action type of the attack
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_SuspendAction getSuspendAction() {
        return suspendAction;
    }


    /**
     * Sets the suspendAction value for this AttacksUser.
     * 
     * @param suspendAction   * This variable indicates the suspend action type of the attack
     */
    public void setSuspendAction(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_SuspendAction suspendAction) {
        this.suspendAction = suspendAction;
    }


    /**
     * Gets the dropThreshold value for this AttacksUser.
     * 
     * @return dropThreshold   * This variable indicates the drop threshold
     */
    public java.lang.Long getDropThreshold() {
        return dropThreshold;
    }


    /**
     * Sets the dropThreshold value for this AttacksUser.
     * 
     * @param dropThreshold   * This variable indicates the drop threshold
     */
    public void setDropThreshold(java.lang.Long dropThreshold) {
        this.dropThreshold = dropThreshold;
    }


    /**
     * Gets the termThreshold value for this AttacksUser.
     * 
     * @return termThreshold   * This variable indicates the term threshold
     */
    public java.lang.Long getTermThreshold() {
        return termThreshold;
    }


    /**
     * Sets the termThreshold value for this AttacksUser.
     * 
     * @param termThreshold   * This variable indicates the term threshold
     */
    public void setTermThreshold(java.lang.Long termThreshold) {
        this.termThreshold = termThreshold;
    }


    /**
     * Gets the excludeSrc value for this AttacksUser.
     * 
     * @return excludeSrc   * This variable indicates exclude source network
     */
    public java.lang.String getExcludeSrc() {
        return excludeSrc;
    }


    /**
     * Sets the excludeSrc value for this AttacksUser.
     * 
     * @param excludeSrc   * This variable indicates exclude source network
     */
    public void setExcludeSrc(java.lang.String excludeSrc) {
        this.excludeSrc = excludeSrc;
    }


    /**
     * Gets the excludeDest value for this AttacksUser.
     * 
     * @return excludeDest   * This variable indicates exclude destination network
     */
    public java.lang.String getExcludeDest() {
        return excludeDest;
    }


    /**
     * Sets the excludeDest value for this AttacksUser.
     * 
     * @param excludeDest   * This variable indicates exclude destination network
     */
    public void setExcludeDest(java.lang.String excludeDest) {
        this.excludeDest = excludeDest;
    }


    /**
     * Gets the packetTrace value for this AttacksUser.
     * 
     * @return packetTrace   * This variable indicates the per-attack packet trace
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketTrace getPacketTrace() {
        return packetTrace;
    }


    /**
     * Sets the packetTrace value for this AttacksUser.
     * 
     * @param packetTrace   * This variable indicates the per-attack packet trace
     */
    public void setPacketTrace(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_PacketTrace packetTrace) {
        this.packetTrace = packetTrace;
    }


    /**
     * Gets the rsIDSAsAttackQuarantine value for this AttacksUser.
     * 
     * @return rsIDSAsAttackQuarantine
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_rsIDSAsAttackQuarantine getRsIDSAsAttackQuarantine() {
        return rsIDSAsAttackQuarantine;
    }


    /**
     * Sets the rsIDSAsAttackQuarantine value for this AttacksUser.
     * 
     * @param rsIDSAsAttackQuarantine
     */
    public void setRsIDSAsAttackQuarantine(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.AttacksUser_rsIDSAsAttackQuarantine rsIDSAsAttackQuarantine) {
        this.rsIDSAsAttackQuarantine = rsIDSAsAttackQuarantine;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AttacksUser)) return false;
        AttacksUser other = (AttacksUser) obj;
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
            ((this.filterName==null && other.getFilterName()==null) || 
             (this.filterName!=null &&
              this.filterName.equals(other.getFilterName()))) &&
            ((this.filterType==null && other.getFilterType()==null) || 
             (this.filterType!=null &&
              this.filterType.equals(other.getFilterType()))) &&
            ((this.trackingTime==null && other.getTrackingTime()==null) || 
             (this.trackingTime!=null &&
              this.trackingTime.equals(other.getTrackingTime()))) &&
            ((this.activeThreshold==null && other.getActiveThreshold()==null) || 
             (this.activeThreshold!=null &&
              this.activeThreshold.equals(other.getActiveThreshold()))) &&
            ((this.trackingType==null && other.getTrackingType()==null) || 
             (this.trackingType!=null &&
              this.trackingType.equals(other.getTrackingType()))) &&
            ((this.attackMessage==null && other.getAttackMessage()==null) || 
             (this.attackMessage!=null &&
              this.attackMessage.equals(other.getAttackMessage()))) &&
            ((this.actionMode==null && other.getActionMode()==null) || 
             (this.actionMode!=null &&
              this.actionMode.equals(other.getActionMode()))) &&
            ((this.attackType==null && other.getAttackType()==null) || 
             (this.attackType!=null &&
              this.attackType.equals(other.getAttackType()))) &&
            ((this.packetReport==null && other.getPacketReport()==null) || 
             (this.packetReport!=null &&
              this.packetReport.equals(other.getPacketReport()))) &&
            ((this.risk==null && other.getRisk()==null) || 
             (this.risk!=null &&
              this.risk.equals(other.getRisk()))) &&
            ((this.state==null && other.getState()==null) || 
             (this.state!=null &&
              this.state.equals(other.getState()))) &&
            ((this.direction==null && other.getDirection()==null) || 
             (this.direction!=null &&
              this.direction.equals(other.getDirection()))) &&
            ((this.suspendAction==null && other.getSuspendAction()==null) || 
             (this.suspendAction!=null &&
              this.suspendAction.equals(other.getSuspendAction()))) &&
            ((this.dropThreshold==null && other.getDropThreshold()==null) || 
             (this.dropThreshold!=null &&
              this.dropThreshold.equals(other.getDropThreshold()))) &&
            ((this.termThreshold==null && other.getTermThreshold()==null) || 
             (this.termThreshold!=null &&
              this.termThreshold.equals(other.getTermThreshold()))) &&
            ((this.excludeSrc==null && other.getExcludeSrc()==null) || 
             (this.excludeSrc!=null &&
              this.excludeSrc.equals(other.getExcludeSrc()))) &&
            ((this.excludeDest==null && other.getExcludeDest()==null) || 
             (this.excludeDest!=null &&
              this.excludeDest.equals(other.getExcludeDest()))) &&
            ((this.packetTrace==null && other.getPacketTrace()==null) || 
             (this.packetTrace!=null &&
              this.packetTrace.equals(other.getPacketTrace()))) &&
            ((this.rsIDSAsAttackQuarantine==null && other.getRsIDSAsAttackQuarantine()==null) || 
             (this.rsIDSAsAttackQuarantine!=null &&
              this.rsIDSAsAttackQuarantine.equals(other.getRsIDSAsAttackQuarantine())));
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
        if (getFilterName() != null) {
            _hashCode += getFilterName().hashCode();
        }
        if (getFilterType() != null) {
            _hashCode += getFilterType().hashCode();
        }
        if (getTrackingTime() != null) {
            _hashCode += getTrackingTime().hashCode();
        }
        if (getActiveThreshold() != null) {
            _hashCode += getActiveThreshold().hashCode();
        }
        if (getTrackingType() != null) {
            _hashCode += getTrackingType().hashCode();
        }
        if (getAttackMessage() != null) {
            _hashCode += getAttackMessage().hashCode();
        }
        if (getActionMode() != null) {
            _hashCode += getActionMode().hashCode();
        }
        if (getAttackType() != null) {
            _hashCode += getAttackType().hashCode();
        }
        if (getPacketReport() != null) {
            _hashCode += getPacketReport().hashCode();
        }
        if (getRisk() != null) {
            _hashCode += getRisk().hashCode();
        }
        if (getState() != null) {
            _hashCode += getState().hashCode();
        }
        if (getDirection() != null) {
            _hashCode += getDirection().hashCode();
        }
        if (getSuspendAction() != null) {
            _hashCode += getSuspendAction().hashCode();
        }
        if (getDropThreshold() != null) {
            _hashCode += getDropThreshold().hashCode();
        }
        if (getTermThreshold() != null) {
            _hashCode += getTermThreshold().hashCode();
        }
        if (getExcludeSrc() != null) {
            _hashCode += getExcludeSrc().hashCode();
        }
        if (getExcludeDest() != null) {
            _hashCode += getExcludeDest().hashCode();
        }
        if (getPacketTrace() != null) {
            _hashCode += getPacketTrace().hashCode();
        }
        if (getRsIDSAsAttackQuarantine() != null) {
            _hashCode += getRsIDSAsAttackQuarantine().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AttacksUser.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser"));
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
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filterName");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FilterName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("filterType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "FilterType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_FilterType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingTime");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TrackingTime"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("activeThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ActiveThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("trackingType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TrackingType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_TrackingType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackMessage");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackMessage"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("actionMode");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ActionMode"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_ActionMode"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_AttackType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetReport");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketReport"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_PacketReport"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("risk");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Risk"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_Risk"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("state");
        elemField.setXmlName(new javax.xml.namespace.QName("", "State"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_State"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("direction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Direction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_Direction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("suspendAction");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SuspendAction"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_SuspendAction"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("dropThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DropThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("termThreshold");
        elemField.setXmlName(new javax.xml.namespace.QName("", "TermThreshold"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
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
        elemField.setFieldName("excludeDest");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ExcludeDest"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetTrace");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketTrace"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_PacketTrace"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("rsIDSAsAttackQuarantine");
        elemField.setXmlName(new javax.xml.namespace.QName("", "rsIDSAsAttackQuarantine"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "AttacksUser_rsIDSAsAttackQuarantine"));
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
