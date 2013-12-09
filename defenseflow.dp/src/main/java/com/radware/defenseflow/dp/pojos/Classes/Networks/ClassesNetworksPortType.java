/**
 * ClassesNetworksPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Classes.Networks;

public interface ClassesNetworksPortType extends java.rmi.Remote {

    /**
     * Retrieves all the parameters of a Network. The temporary network
     * database allows users to modify and create new network classes. A
     * network can be an IP range or an IP subnet/mask, or a combination
     * of IP ranges and subnet masks. Use the same network name, with a different
     * Sub-Index, for multiple entries in the table to create such combination.
     * Temporary classes can be altered and configured without affecting
     * the current operation of the device. As these classes are adjusted,
     * the changes do not affect the flow of packets unless the inactive
     * database is activated.
     */
    public com.radware.defenseflow.dp.pojos.Classes.Networks.Network get_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first Network. The temporary network
     * database allows users to modify and create new network classes. A
     * network can be an IP range or an IP subnet/mask, or a combination
     * of IP ranges and subnet masks. Use the same network name, with a different
     * Sub-Index, for multiple entries in the table to create such combination.
     * Temporary classes can be altered and configured without affecting
     * the current operation of the device. As these classes are adjusted,
     * the changes do not affect the flow of packets unless the inactive
     * database is activated.
     */
    public void getFirst_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkKeyHolder key, com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next Network. The temporary network
     * database allows users to modify and create new network classes. A
     * network can be an IP range or an IP subnet/mask, or a combination
     * of IP ranges and subnet masks. Use the same network name, with a different
     * Sub-Index, for multiple entries in the table to create such combination.
     * Temporary classes can be altered and configured without affecting
     * the current operation of the device. As these classes are adjusted,
     * the changes do not affect the flow of packets unless the inactive
     * database is activated.
     */
    public void getNext_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey key, com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkKeyHolder next_key, com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the Network. The temporary network database allows
     * users to modify and create new network classes. A network can be an
     * IP range or an IP subnet/mask, or a combination of IP ranges and subnet
     * masks. Use the same network name, with a different Sub-Index, for
     * multiple entries in the table to create such combination. Temporary
     * classes can be altered and configured without affecting the current
     * operation of the device. As these classes are adjusted, the changes
     * do not affect the flow of packets unless the inactive database is
     * activated.
     */
    public void getAll_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a Network. The temporary network database allows users
     * to modify and create new network classes. A network can be an IP range
     * or an IP subnet/mask, or a combination of IP ranges and subnet masks.
     * Use the same network name, with a different Sub-Index, for multiple
     * entries in the table to create such combination. Temporary classes
     * can be altered and configured without affecting the current operation
     * of the device. As these classes are adjusted, the changes do not affect
     * the flow of packets unless the inactive database is activated. 		
     * 	 (activated with update-policies)
     */
    public void create_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a Network. The temporary network database allows users
     * to modify and create new network classes. A network can be an IP range
     * or an IP subnet/mask, or a combination of IP ranges and subnet masks.
     * Use the same network name, with a different Sub-Index, for multiple
     * entries in the table to create such combination. Temporary classes
     * can be altered and configured without affecting the current operation
     * of the device. As these classes are adjusted, the changes do not affect
     * the flow of packets unless the inactive database is activated. 		
     * 	 (activated with update-policies)
     */
    public void delete_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey key) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a Network. The temporary network
     * database allows users to modify and create new network classes. A
     * network can be an IP range or an IP subnet/mask, or a combination
     * of IP ranges and subnet masks. Use the same network name, with a different
     * Sub-Index, for multiple entries in the table to create such combination.
     * Temporary classes can be altered and configured without affecting
     * the current operation of the device. As these classes are adjusted,
     * the changes do not affect the flow of packets unless the inactive
     * database is activated. 			 (activated with update-policies)
     */
    public void update_Network(com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a ActiveNetwork. An active
     * network entry defines an IP range or an IP subnet/mask that is part
     * of a network class that can be currently used in classification policies.
     * Several entries can exist with the same network name, creating a combination
     * of ranges and/or subnets.
     */
    public com.radware.defenseflow.dp.pojos.Classes.Networks.Network get_ActiveNetwork(com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first ActiveNetwork. An active
     * network entry defines an IP range or an IP subnet/mask that is part
     * of a network class that can be currently used in classification policies.
     * Several entries can exist with the same network name, creating a combination
     * of ranges and/or subnets.
     */
    public void getFirst_ActiveNetwork(com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkKeyHolder key, com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next ActiveNetwork. An active network
     * entry defines an IP range or an IP subnet/mask that is part of a network
     * class that can be currently used in classification policies. Several
     * entries can exist with the same network name, creating a combination
     * of ranges and/or subnets.
     */
    public void getNext_ActiveNetwork(com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey key, com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkKeyHolder next_key, com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the ActiveNetwork. An active network entry defines
     * an IP range or an IP subnet/mask that is part of a network class that
     * can be currently used in classification policies. Several entries
     * can exist with the same network name, creating a combination of ranges
     * and/or subnets.
     */
    public void getAll_ActiveNetwork(com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;
}
