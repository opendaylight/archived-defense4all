/**
 * BasicFilterUser.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.SignatureProtection;


/**
 * This structure describes the parameters of a BasicFilterUser
 */
public class BasicFilterUser  implements java.io.Serializable {
    /* The name of the filter object. */
    private java.lang.String name;

    /* The protocol for the filter. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_Protocol protocol;

    /* The offset of the OMPC data. */
    private java.lang.Long OMPCOffset;

    /* The mask for the OMPC object. */
    private java.lang.String OMPCMask;

    /* The pattern for the OMPC data. */
    private java.lang.String OMPCPattern;

    /* The condition for the OMPC data check. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCCondition OMPCCondition;

    /* The length of the OMPC. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCLength OMPCLength;

    /* The offset for the content. */
    private java.lang.Long contentOffset;

    /* The content to be checked. */
    private java.lang.String content;

    /* The type of content. In the case of URL only the url is checked.
     * (No offset is used). In the case of text then the entire packet is
     * checked. (will make use of the offset). */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentType contentType;

    /* The type of object. This can be regular (user defined), static
     * (defined by the application), ids (defined by the user for ids) or
     * idsStatic(defined by the application for the ids). */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_AttackType attackType;

    /* End of search data. */
    private java.lang.Long contentMaxLength;

    /* The content data to be checked. */
    private java.lang.String contentData;

    /* The type of content coding that is used. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentEncoding contentEncoding;

    /* The type of content coding that is used. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataEncoding contentDataEncoding;

    /* The base of filter OMPC offset. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCOffsetRelativeto OMPCOffsetRelativeto;

    /* Source port group name. */
    private java.lang.String sourceAppPort;

    /* Destination port group name. */
    private java.lang.String destinationApp_Port;

    /* Distance. */
    private java.lang.String distance;

    /* Flag that shows whether content is a regular expression */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentRegularExpression contentRegularExpression;

    /* Flag that shows whether content data is a regular expression */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataRegExpression contentDataRegExpression;

    /* The packet size type. */
    private com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_PacketSizeType packetSizeType;

    /* Packet Size Range. */
    private java.lang.String packetSizeRange;

    public BasicFilterUser() {
    }

    public BasicFilterUser(
           java.lang.String name,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_Protocol protocol,
           java.lang.Long OMPCOffset,
           java.lang.String OMPCMask,
           java.lang.String OMPCPattern,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCCondition OMPCCondition,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCLength OMPCLength,
           java.lang.Long contentOffset,
           java.lang.String content,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentType contentType,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_AttackType attackType,
           java.lang.Long contentMaxLength,
           java.lang.String contentData,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentEncoding contentEncoding,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataEncoding contentDataEncoding,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCOffsetRelativeto OMPCOffsetRelativeto,
           java.lang.String sourceAppPort,
           java.lang.String destinationApp_Port,
           java.lang.String distance,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentRegularExpression contentRegularExpression,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataRegExpression contentDataRegExpression,
           com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_PacketSizeType packetSizeType,
           java.lang.String packetSizeRange) {
           this.name = name;
           this.protocol = protocol;
           this.OMPCOffset = OMPCOffset;
           this.OMPCMask = OMPCMask;
           this.OMPCPattern = OMPCPattern;
           this.OMPCCondition = OMPCCondition;
           this.OMPCLength = OMPCLength;
           this.contentOffset = contentOffset;
           this.content = content;
           this.contentType = contentType;
           this.attackType = attackType;
           this.contentMaxLength = contentMaxLength;
           this.contentData = contentData;
           this.contentEncoding = contentEncoding;
           this.contentDataEncoding = contentDataEncoding;
           this.OMPCOffsetRelativeto = OMPCOffsetRelativeto;
           this.sourceAppPort = sourceAppPort;
           this.destinationApp_Port = destinationApp_Port;
           this.distance = distance;
           this.contentRegularExpression = contentRegularExpression;
           this.contentDataRegExpression = contentDataRegExpression;
           this.packetSizeType = packetSizeType;
           this.packetSizeRange = packetSizeRange;
    }


    /**
     * Gets the name value for this BasicFilterUser.
     * 
     * @return name   * The name of the filter object.
     */
    public java.lang.String getName() {
        return name;
    }


    /**
     * Sets the name value for this BasicFilterUser.
     * 
     * @param name   * The name of the filter object.
     */
    public void setName(java.lang.String name) {
        this.name = name;
    }


    /**
     * Gets the protocol value for this BasicFilterUser.
     * 
     * @return protocol   * The protocol for the filter.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_Protocol getProtocol() {
        return protocol;
    }


    /**
     * Sets the protocol value for this BasicFilterUser.
     * 
     * @param protocol   * The protocol for the filter.
     */
    public void setProtocol(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_Protocol protocol) {
        this.protocol = protocol;
    }


    /**
     * Gets the OMPCOffset value for this BasicFilterUser.
     * 
     * @return OMPCOffset   * The offset of the OMPC data.
     */
    public java.lang.Long getOMPCOffset() {
        return OMPCOffset;
    }


    /**
     * Sets the OMPCOffset value for this BasicFilterUser.
     * 
     * @param OMPCOffset   * The offset of the OMPC data.
     */
    public void setOMPCOffset(java.lang.Long OMPCOffset) {
        this.OMPCOffset = OMPCOffset;
    }


    /**
     * Gets the OMPCMask value for this BasicFilterUser.
     * 
     * @return OMPCMask   * The mask for the OMPC object.
     */
    public java.lang.String getOMPCMask() {
        return OMPCMask;
    }


    /**
     * Sets the OMPCMask value for this BasicFilterUser.
     * 
     * @param OMPCMask   * The mask for the OMPC object.
     */
    public void setOMPCMask(java.lang.String OMPCMask) {
        this.OMPCMask = OMPCMask;
    }


    /**
     * Gets the OMPCPattern value for this BasicFilterUser.
     * 
     * @return OMPCPattern   * The pattern for the OMPC data.
     */
    public java.lang.String getOMPCPattern() {
        return OMPCPattern;
    }


    /**
     * Sets the OMPCPattern value for this BasicFilterUser.
     * 
     * @param OMPCPattern   * The pattern for the OMPC data.
     */
    public void setOMPCPattern(java.lang.String OMPCPattern) {
        this.OMPCPattern = OMPCPattern;
    }


    /**
     * Gets the OMPCCondition value for this BasicFilterUser.
     * 
     * @return OMPCCondition   * The condition for the OMPC data check.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCCondition getOMPCCondition() {
        return OMPCCondition;
    }


    /**
     * Sets the OMPCCondition value for this BasicFilterUser.
     * 
     * @param OMPCCondition   * The condition for the OMPC data check.
     */
    public void setOMPCCondition(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCCondition OMPCCondition) {
        this.OMPCCondition = OMPCCondition;
    }


    /**
     * Gets the OMPCLength value for this BasicFilterUser.
     * 
     * @return OMPCLength   * The length of the OMPC.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCLength getOMPCLength() {
        return OMPCLength;
    }


    /**
     * Sets the OMPCLength value for this BasicFilterUser.
     * 
     * @param OMPCLength   * The length of the OMPC.
     */
    public void setOMPCLength(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCLength OMPCLength) {
        this.OMPCLength = OMPCLength;
    }


    /**
     * Gets the contentOffset value for this BasicFilterUser.
     * 
     * @return contentOffset   * The offset for the content.
     */
    public java.lang.Long getContentOffset() {
        return contentOffset;
    }


    /**
     * Sets the contentOffset value for this BasicFilterUser.
     * 
     * @param contentOffset   * The offset for the content.
     */
    public void setContentOffset(java.lang.Long contentOffset) {
        this.contentOffset = contentOffset;
    }


    /**
     * Gets the content value for this BasicFilterUser.
     * 
     * @return content   * The content to be checked.
     */
    public java.lang.String getContent() {
        return content;
    }


    /**
     * Sets the content value for this BasicFilterUser.
     * 
     * @param content   * The content to be checked.
     */
    public void setContent(java.lang.String content) {
        this.content = content;
    }


    /**
     * Gets the contentType value for this BasicFilterUser.
     * 
     * @return contentType   * The type of content. In the case of URL only the url is checked.
     * (No offset is used). In the case of text then the entire packet is
     * checked. (will make use of the offset).
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentType getContentType() {
        return contentType;
    }


    /**
     * Sets the contentType value for this BasicFilterUser.
     * 
     * @param contentType   * The type of content. In the case of URL only the url is checked.
     * (No offset is used). In the case of text then the entire packet is
     * checked. (will make use of the offset).
     */
    public void setContentType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentType contentType) {
        this.contentType = contentType;
    }


    /**
     * Gets the attackType value for this BasicFilterUser.
     * 
     * @return attackType   * The type of object. This can be regular (user defined), static
     * (defined by the application), ids (defined by the user for ids) or
     * idsStatic(defined by the application for the ids).
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_AttackType getAttackType() {
        return attackType;
    }


    /**
     * Sets the attackType value for this BasicFilterUser.
     * 
     * @param attackType   * The type of object. This can be regular (user defined), static
     * (defined by the application), ids (defined by the user for ids) or
     * idsStatic(defined by the application for the ids).
     */
    public void setAttackType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_AttackType attackType) {
        this.attackType = attackType;
    }


    /**
     * Gets the contentMaxLength value for this BasicFilterUser.
     * 
     * @return contentMaxLength   * End of search data.
     */
    public java.lang.Long getContentMaxLength() {
        return contentMaxLength;
    }


    /**
     * Sets the contentMaxLength value for this BasicFilterUser.
     * 
     * @param contentMaxLength   * End of search data.
     */
    public void setContentMaxLength(java.lang.Long contentMaxLength) {
        this.contentMaxLength = contentMaxLength;
    }


    /**
     * Gets the contentData value for this BasicFilterUser.
     * 
     * @return contentData   * The content data to be checked.
     */
    public java.lang.String getContentData() {
        return contentData;
    }


    /**
     * Sets the contentData value for this BasicFilterUser.
     * 
     * @param contentData   * The content data to be checked.
     */
    public void setContentData(java.lang.String contentData) {
        this.contentData = contentData;
    }


    /**
     * Gets the contentEncoding value for this BasicFilterUser.
     * 
     * @return contentEncoding   * The type of content coding that is used.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentEncoding getContentEncoding() {
        return contentEncoding;
    }


    /**
     * Sets the contentEncoding value for this BasicFilterUser.
     * 
     * @param contentEncoding   * The type of content coding that is used.
     */
    public void setContentEncoding(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentEncoding contentEncoding) {
        this.contentEncoding = contentEncoding;
    }


    /**
     * Gets the contentDataEncoding value for this BasicFilterUser.
     * 
     * @return contentDataEncoding   * The type of content coding that is used.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataEncoding getContentDataEncoding() {
        return contentDataEncoding;
    }


    /**
     * Sets the contentDataEncoding value for this BasicFilterUser.
     * 
     * @param contentDataEncoding   * The type of content coding that is used.
     */
    public void setContentDataEncoding(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataEncoding contentDataEncoding) {
        this.contentDataEncoding = contentDataEncoding;
    }


    /**
     * Gets the OMPCOffsetRelativeto value for this BasicFilterUser.
     * 
     * @return OMPCOffsetRelativeto   * The base of filter OMPC offset.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCOffsetRelativeto getOMPCOffsetRelativeto() {
        return OMPCOffsetRelativeto;
    }


    /**
     * Sets the OMPCOffsetRelativeto value for this BasicFilterUser.
     * 
     * @param OMPCOffsetRelativeto   * The base of filter OMPC offset.
     */
    public void setOMPCOffsetRelativeto(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_OMPCOffsetRelativeto OMPCOffsetRelativeto) {
        this.OMPCOffsetRelativeto = OMPCOffsetRelativeto;
    }


    /**
     * Gets the sourceAppPort value for this BasicFilterUser.
     * 
     * @return sourceAppPort   * Source port group name.
     */
    public java.lang.String getSourceAppPort() {
        return sourceAppPort;
    }


    /**
     * Sets the sourceAppPort value for this BasicFilterUser.
     * 
     * @param sourceAppPort   * Source port group name.
     */
    public void setSourceAppPort(java.lang.String sourceAppPort) {
        this.sourceAppPort = sourceAppPort;
    }


    /**
     * Gets the destinationApp_Port value for this BasicFilterUser.
     * 
     * @return destinationApp_Port   * Destination port group name.
     */
    public java.lang.String getDestinationApp_Port() {
        return destinationApp_Port;
    }


    /**
     * Sets the destinationApp_Port value for this BasicFilterUser.
     * 
     * @param destinationApp_Port   * Destination port group name.
     */
    public void setDestinationApp_Port(java.lang.String destinationApp_Port) {
        this.destinationApp_Port = destinationApp_Port;
    }


    /**
     * Gets the distance value for this BasicFilterUser.
     * 
     * @return distance   * Distance.
     */
    public java.lang.String getDistance() {
        return distance;
    }


    /**
     * Sets the distance value for this BasicFilterUser.
     * 
     * @param distance   * Distance.
     */
    public void setDistance(java.lang.String distance) {
        this.distance = distance;
    }


    /**
     * Gets the contentRegularExpression value for this BasicFilterUser.
     * 
     * @return contentRegularExpression   * Flag that shows whether content is a regular expression
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentRegularExpression getContentRegularExpression() {
        return contentRegularExpression;
    }


    /**
     * Sets the contentRegularExpression value for this BasicFilterUser.
     * 
     * @param contentRegularExpression   * Flag that shows whether content is a regular expression
     */
    public void setContentRegularExpression(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentRegularExpression contentRegularExpression) {
        this.contentRegularExpression = contentRegularExpression;
    }


    /**
     * Gets the contentDataRegExpression value for this BasicFilterUser.
     * 
     * @return contentDataRegExpression   * Flag that shows whether content data is a regular expression
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataRegExpression getContentDataRegExpression() {
        return contentDataRegExpression;
    }


    /**
     * Sets the contentDataRegExpression value for this BasicFilterUser.
     * 
     * @param contentDataRegExpression   * Flag that shows whether content data is a regular expression
     */
    public void setContentDataRegExpression(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_ContentDataRegExpression contentDataRegExpression) {
        this.contentDataRegExpression = contentDataRegExpression;
    }


    /**
     * Gets the packetSizeType value for this BasicFilterUser.
     * 
     * @return packetSizeType   * The packet size type.
     */
    public com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_PacketSizeType getPacketSizeType() {
        return packetSizeType;
    }


    /**
     * Sets the packetSizeType value for this BasicFilterUser.
     * 
     * @param packetSizeType   * The packet size type.
     */
    public void setPacketSizeType(com.radware.defenseflow.dp.pojos.Security.SignatureProtection.BasicFilterUser_PacketSizeType packetSizeType) {
        this.packetSizeType = packetSizeType;
    }


    /**
     * Gets the packetSizeRange value for this BasicFilterUser.
     * 
     * @return packetSizeRange   * Packet Size Range.
     */
    public java.lang.String getPacketSizeRange() {
        return packetSizeRange;
    }


    /**
     * Sets the packetSizeRange value for this BasicFilterUser.
     * 
     * @param packetSizeRange   * Packet Size Range.
     */
    public void setPacketSizeRange(java.lang.String packetSizeRange) {
        this.packetSizeRange = packetSizeRange;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof BasicFilterUser)) return false;
        BasicFilterUser other = (BasicFilterUser) obj;
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
            ((this.protocol==null && other.getProtocol()==null) || 
             (this.protocol!=null &&
              this.protocol.equals(other.getProtocol()))) &&
            ((this.OMPCOffset==null && other.getOMPCOffset()==null) || 
             (this.OMPCOffset!=null &&
              this.OMPCOffset.equals(other.getOMPCOffset()))) &&
            ((this.OMPCMask==null && other.getOMPCMask()==null) || 
             (this.OMPCMask!=null &&
              this.OMPCMask.equals(other.getOMPCMask()))) &&
            ((this.OMPCPattern==null && other.getOMPCPattern()==null) || 
             (this.OMPCPattern!=null &&
              this.OMPCPattern.equals(other.getOMPCPattern()))) &&
            ((this.OMPCCondition==null && other.getOMPCCondition()==null) || 
             (this.OMPCCondition!=null &&
              this.OMPCCondition.equals(other.getOMPCCondition()))) &&
            ((this.OMPCLength==null && other.getOMPCLength()==null) || 
             (this.OMPCLength!=null &&
              this.OMPCLength.equals(other.getOMPCLength()))) &&
            ((this.contentOffset==null && other.getContentOffset()==null) || 
             (this.contentOffset!=null &&
              this.contentOffset.equals(other.getContentOffset()))) &&
            ((this.content==null && other.getContent()==null) || 
             (this.content!=null &&
              this.content.equals(other.getContent()))) &&
            ((this.contentType==null && other.getContentType()==null) || 
             (this.contentType!=null &&
              this.contentType.equals(other.getContentType()))) &&
            ((this.attackType==null && other.getAttackType()==null) || 
             (this.attackType!=null &&
              this.attackType.equals(other.getAttackType()))) &&
            ((this.contentMaxLength==null && other.getContentMaxLength()==null) || 
             (this.contentMaxLength!=null &&
              this.contentMaxLength.equals(other.getContentMaxLength()))) &&
            ((this.contentData==null && other.getContentData()==null) || 
             (this.contentData!=null &&
              this.contentData.equals(other.getContentData()))) &&
            ((this.contentEncoding==null && other.getContentEncoding()==null) || 
             (this.contentEncoding!=null &&
              this.contentEncoding.equals(other.getContentEncoding()))) &&
            ((this.contentDataEncoding==null && other.getContentDataEncoding()==null) || 
             (this.contentDataEncoding!=null &&
              this.contentDataEncoding.equals(other.getContentDataEncoding()))) &&
            ((this.OMPCOffsetRelativeto==null && other.getOMPCOffsetRelativeto()==null) || 
             (this.OMPCOffsetRelativeto!=null &&
              this.OMPCOffsetRelativeto.equals(other.getOMPCOffsetRelativeto()))) &&
            ((this.sourceAppPort==null && other.getSourceAppPort()==null) || 
             (this.sourceAppPort!=null &&
              this.sourceAppPort.equals(other.getSourceAppPort()))) &&
            ((this.destinationApp_Port==null && other.getDestinationApp_Port()==null) || 
             (this.destinationApp_Port!=null &&
              this.destinationApp_Port.equals(other.getDestinationApp_Port()))) &&
            ((this.distance==null && other.getDistance()==null) || 
             (this.distance!=null &&
              this.distance.equals(other.getDistance()))) &&
            ((this.contentRegularExpression==null && other.getContentRegularExpression()==null) || 
             (this.contentRegularExpression!=null &&
              this.contentRegularExpression.equals(other.getContentRegularExpression()))) &&
            ((this.contentDataRegExpression==null && other.getContentDataRegExpression()==null) || 
             (this.contentDataRegExpression!=null &&
              this.contentDataRegExpression.equals(other.getContentDataRegExpression()))) &&
            ((this.packetSizeType==null && other.getPacketSizeType()==null) || 
             (this.packetSizeType!=null &&
              this.packetSizeType.equals(other.getPacketSizeType()))) &&
            ((this.packetSizeRange==null && other.getPacketSizeRange()==null) || 
             (this.packetSizeRange!=null &&
              this.packetSizeRange.equals(other.getPacketSizeRange())));
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
        if (getProtocol() != null) {
            _hashCode += getProtocol().hashCode();
        }
        if (getOMPCOffset() != null) {
            _hashCode += getOMPCOffset().hashCode();
        }
        if (getOMPCMask() != null) {
            _hashCode += getOMPCMask().hashCode();
        }
        if (getOMPCPattern() != null) {
            _hashCode += getOMPCPattern().hashCode();
        }
        if (getOMPCCondition() != null) {
            _hashCode += getOMPCCondition().hashCode();
        }
        if (getOMPCLength() != null) {
            _hashCode += getOMPCLength().hashCode();
        }
        if (getContentOffset() != null) {
            _hashCode += getContentOffset().hashCode();
        }
        if (getContent() != null) {
            _hashCode += getContent().hashCode();
        }
        if (getContentType() != null) {
            _hashCode += getContentType().hashCode();
        }
        if (getAttackType() != null) {
            _hashCode += getAttackType().hashCode();
        }
        if (getContentMaxLength() != null) {
            _hashCode += getContentMaxLength().hashCode();
        }
        if (getContentData() != null) {
            _hashCode += getContentData().hashCode();
        }
        if (getContentEncoding() != null) {
            _hashCode += getContentEncoding().hashCode();
        }
        if (getContentDataEncoding() != null) {
            _hashCode += getContentDataEncoding().hashCode();
        }
        if (getOMPCOffsetRelativeto() != null) {
            _hashCode += getOMPCOffsetRelativeto().hashCode();
        }
        if (getSourceAppPort() != null) {
            _hashCode += getSourceAppPort().hashCode();
        }
        if (getDestinationApp_Port() != null) {
            _hashCode += getDestinationApp_Port().hashCode();
        }
        if (getDistance() != null) {
            _hashCode += getDistance().hashCode();
        }
        if (getContentRegularExpression() != null) {
            _hashCode += getContentRegularExpression().hashCode();
        }
        if (getContentDataRegExpression() != null) {
            _hashCode += getContentDataRegExpression().hashCode();
        }
        if (getPacketSizeType() != null) {
            _hashCode += getPacketSizeType().hashCode();
        }
        if (getPacketSizeRange() != null) {
            _hashCode += getPacketSizeRange().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(BasicFilterUser.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("name");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Name"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("protocol");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Protocol"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_Protocol"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OMPCOffset");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OMPCOffset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OMPCMask");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OMPCMask"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OMPCPattern");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OMPCPattern"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OMPCCondition");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OMPCCondition"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_OMPCCondition"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OMPCLength");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OMPCLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_OMPCLength"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentOffset");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentOffset"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("content");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Content"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_ContentType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("attackType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "AttackType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_AttackType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentMaxLength");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentMaxLength"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentData");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentData"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentEncoding");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentEncoding"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_ContentEncoding"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentDataEncoding");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentDataEncoding"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_ContentDataEncoding"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("OMPCOffsetRelativeto");
        elemField.setXmlName(new javax.xml.namespace.QName("", "OMPCOffsetRelativeto"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_OMPCOffsetRelativeto"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("sourceAppPort");
        elemField.setXmlName(new javax.xml.namespace.QName("", "SourceAppPort"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("destinationApp_Port");
        elemField.setXmlName(new javax.xml.namespace.QName("", "DestinationApp_Port"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("distance");
        elemField.setXmlName(new javax.xml.namespace.QName("", "Distance"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentRegularExpression");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentRegularExpression"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_ContentRegularExpression"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("contentDataRegExpression");
        elemField.setXmlName(new javax.xml.namespace.QName("", "ContentDataRegExpression"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_ContentDataRegExpression"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetSizeType");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketSizeType"));
        elemField.setXmlType(new javax.xml.namespace.QName("radware.Security.SignatureProtection", "BasicFilterUser_PacketSizeType"));
        elemField.setMinOccurs(0);
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("packetSizeRange");
        elemField.setXmlName(new javax.xml.namespace.QName("", "PacketSizeRange"));
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
