/**
 * ClassesVLANTagGroupPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup;

public interface ClassesVLANTagGroupPortType extends java.rmi.Remote {

    /**
     * Retrieves all the parameters of a GroupEntry. The temporary
     * VLAN Tag groups database allows users to modify and create new Layer2
     * data class. A VLAN Tag group entry defines the membership of a VLAN
     * tag range or a discrete VLAN tag to a group. Use the same group name
     * for multiple entries to create a group that is a combination of a
     * few ranges and discrete tags. Temporary classes can be altered and
     * configured without affecting the current operation of the device.
     * As these classes are adjusted, the changes do not affect the flow
     * of packets unless the inactive database is activated.
     */
    public com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry get_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first GroupEntry. The temporary
     * VLAN Tag groups database allows users to modify and create new Layer2
     * data class. A VLAN Tag group entry defines the membership of a VLAN
     * tag range or a discrete VLAN tag to a group. Use the same group name
     * for multiple entries to create a group that is a combination of a
     * few ranges and discrete tags. Temporary classes can be altered and
     * configured without affecting the current operation of the device.
     * As these classes are adjusted, the changes do not affect the flow
     * of packets unless the inactive database is activated.
     */
    public void getFirst_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next GroupEntry. The temporary
     * VLAN Tag groups database allows users to modify and create new Layer2
     * data class. A VLAN Tag group entry defines the membership of a VLAN
     * tag range or a discrete VLAN tag to a group. Use the same group name
     * for multiple entries to create a group that is a combination of a
     * few ranges and discrete tags. Temporary classes can be altered and
     * configured without affecting the current operation of the device.
     * As these classes are adjusted, the changes do not affect the flow
     * of packets unless the inactive database is activated.
     */
    public void getNext_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntryKey key, com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the GroupEntry. The temporary VLAN Tag groups
     * database allows users to modify and create new Layer2 data class.
     * A VLAN Tag group entry defines the membership of a VLAN tag range
     * or a discrete VLAN tag to a group. Use the same group name for multiple
     * entries to create a group that is a combination of a few ranges and
     * discrete tags. Temporary classes can be altered and configured without
     * affecting the current operation of the device. As these classes are
     * adjusted, the changes do not affect the flow of packets unless the
     * inactive database is activated.
     */
    public void getAll_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a GroupEntry. The temporary VLAN Tag groups database allows
     * users to modify and create new Layer2 data class. A VLAN Tag group
     * entry defines the membership of a VLAN tag range or a discrete VLAN
     * tag to a group. Use the same group name for multiple entries to create
     * a group that is a combination of a few ranges and discrete tags. Temporary
     * classes can be altered and configured without affecting the current
     * operation of the device. As these classes are adjusted, the changes
     * do not affect the flow of packets unless the inactive database is
     * activated. 	 (activated with update-policies)
     */
    public void create_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a GroupEntry. The temporary VLAN Tag groups database
     * allows users to modify and create new Layer2 data class. A VLAN Tag
     * group entry defines the membership of a VLAN tag range or a discrete
     * VLAN tag to a group. Use the same group name for multiple entries
     * to create a group that is a combination of a few ranges and discrete
     * tags. Temporary classes can be altered and configured without affecting
     * the current operation of the device. As these classes are adjusted,
     * the changes do not affect the flow of packets unless the inactive
     * database is activated. 	 (activated with update-policies)
     */
    public void delete_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntryKey key) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a GroupEntry. The temporary
     * VLAN Tag groups database allows users to modify and create new Layer2
     * data class. A VLAN Tag group entry defines the membership of a VLAN
     * tag range or a discrete VLAN tag to a group. Use the same group name
     * for multiple entries to create a group that is a combination of a
     * few ranges and discrete tags. Temporary classes can be altered and
     * configured without affecting the current operation of the device.
     * As these classes are adjusted, the changes do not affect the flow
     * of packets unless the inactive database is activated. 	 (activated
     * with update-policies)
     */
    public void update_GroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a ActiveGroupEntry. Active
     * VLAN Tag groups that can be currently used in classification policies.
     * A VLAN Tag group entry defines a VLAN tag range or a discrete VLAN
     * tag that can be currently used to classify traffic. Several entries
     * can exist with the same group name, creating a group that is a combination
     * of multiple ranges and discrete tags.
     */
    public com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry get_ActiveGroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntryKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first ActiveGroupEntry. Active
     * VLAN Tag groups that can be currently used in classification policies.
     * A VLAN Tag group entry defines a VLAN tag range or a discrete VLAN
     * tag that can be currently used to classify traffic. Several entries
     * can exist with the same group name, creating a group that is a combination
     * of multiple ranges and discrete tags.
     */
    public void getFirst_ActiveGroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryKeyHolder key, com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next ActiveGroupEntry. Active VLAN
     * Tag groups that can be currently used in classification policies.
     * A VLAN Tag group entry defines a VLAN tag range or a discrete VLAN
     * tag that can be currently used to classify traffic. Several entries
     * can exist with the same group name, creating a group that is a combination
     * of multiple ranges and discrete tags.
     */
    public void getNext_ActiveGroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntryKey key, com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryKeyHolder next_key, com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the ActiveGroupEntry. Active VLAN Tag groups
     * that can be currently used in classification policies. A VLAN Tag
     * group entry defines a VLAN tag range or a discrete VLAN tag that can
     * be currently used to classify traffic. Several entries can exist with
     * the same group name, creating a group that is a combination of multiple
     * ranges and discrete tags.
     */
    public void getAll_ActiveGroupEntry(com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;
}
