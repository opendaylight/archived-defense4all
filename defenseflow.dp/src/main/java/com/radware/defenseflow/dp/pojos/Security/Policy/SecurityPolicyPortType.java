/**
 * SecurityPolicyPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.radware.defenseflow.dp.pojos.Security.Policy;

public interface SecurityPolicyPortType extends java.rmi.Remote {

    /**
     * Retrieves all the parameters of a PoliciesResources. Policies
     * resources utilization.
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResources get_PoliciesResources(com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResourcesKey key) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first PoliciesResources. Policies
     * resources utilization.
     */
    public void getFirst_PoliciesResources(com.radware.defenseflow.dp.pojos.Security.Policy.holders.PoliciesResourcesKeyHolder key, com.radware.defenseflow.dp.pojos.Security.Policy.holders.PoliciesResourcesHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next PoliciesResources. Policies
     * resources utilization.
     */
    public void getNext_PoliciesResources(com.radware.defenseflow.dp.pojos.Security.Policy.PoliciesResourcesKey key, com.radware.defenseflow.dp.pojos.Security.Policy.holders.PoliciesResourcesKeyHolder next_key, com.radware.defenseflow.dp.pojos.Security.Policy.holders.PoliciesResourcesHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the PoliciesResources. Policies resources utilization.
     */
    public void getAll_PoliciesResources(com.radware.defenseflow.dp.pojos.Security.Policy.holders.PoliciesResourcesArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of HwEntriesUtil. View the total utilization
     * of HW entries, used in the HW-accelerator to represent network policies.
     */
    public long get_HwEntriesUtil() throws java.rmi.RemoteException;

    /**
     * Retrieves the value of SubPolicyUtil. View the total utilization
     * of sub policies, used in the HW-accelerator.
     */
    public long get_SubPolicyUtil() throws java.rmi.RemoteException;

    /**
     * Retrieves all the parameters of a Policy. Configures the IDS
     * policies
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.Policy get_Policy(java.lang.String policyName) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of first Policy. Configures the IDS
     * policies
     */
    public void getFirst_Policy(javax.xml.rpc.holders.StringHolder policyName, com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all parameters of next Policy. Configures the IDS
     * policies
     */
    public void getNext_Policy(java.lang.String policyName, javax.xml.rpc.holders.StringHolder next_key, com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyHolder next_entry) throws java.rmi.RemoteException;

    /**
     * Retrieves all the Policy. Configures the IDS policies
     */
    public void getAll_Policy(com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyArrayHolder table, javax.xml.rpc.holders.BooleanHolder status) throws java.rmi.RemoteException;

    /**
     * Adds a Policy. Configures the IDS policies (activated with
     * update-policies)
     */
    public void create_Policy(com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyHolder entry) throws java.rmi.RemoteException;

    /**
     * Deletes a Policy. Configures the IDS policies (activated with
     * update-policies)
     */
    public void delete_Policy(java.lang.String policyName) throws java.rmi.RemoteException;

    /**
     * Updates any or all parameters of a Policy. Configures the IDS
     * policies (activated with update-policies)
     */
    public void update_Policy(com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyHolder entry) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of TrafficExclusionStatus. Configures the
     * Traffic Exclusion Status
     */
    public com.radware.defenseflow.dp.pojos.Security.Policy.TrafficExclusionStatus get_TrafficExclusionStatus() throws java.rmi.RemoteException;

    /**
     * Updates the value of TrafficExclusionStatus. Configures the
     * Traffic Exclusion Status
     */
    public void set_TrafficExclusionStatus(com.radware.defenseflow.dp.pojos.Security.Policy.TrafficExclusionStatus value) throws java.rmi.RemoteException;

    /**
     * Retrieves the value of TotalPolicyNum. View the total number
     * of policies (2-way policies are counted twice)
     */
    public long get_TotalPolicyNum() throws java.rmi.RemoteException;
}
