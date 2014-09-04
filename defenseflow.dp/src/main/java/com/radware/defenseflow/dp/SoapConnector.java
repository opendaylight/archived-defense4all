/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Snir Cohen
 * @author Gera Goft
 * @version 0.1
 */

package com.radware.defenseflow.dp;

import javax.xml.rpc.holders.BooleanHolder;

import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksBindingStub;
import com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksPortType;
import com.radware.defenseflow.dp.pojos.Classes.Networks.ClassesNetworksServiceLocator;
import com.radware.defenseflow.dp.pojos.Classes.Networks.Network;
import com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey;
import com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkArrayHolder;
import com.radware.defenseflow.dp.pojos.Classes.Networks.holders.NetworkHolder;
import com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.ClassesVLANTagGroupPortType;
import com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.ClassesVLANTagGroupServiceLocator;
import com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntry;
import com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.GroupEntryKey;
import com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.holders.GroupEntryHolder;
import com.radware.defenseflow.dp.pojos.Classes.VLANTagGroup.ClassesVLANTagGroupBindingStub;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.ManagementSyslogServersBindingStub;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.ManagementSyslogServersPortType;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.ManagementSyslogServersServiceLocator;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.holders.SyslogServersTableHolder;
import com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationBindingStub;
import com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationPortType;
import com.radware.defenseflow.dp.pojos.Security.ActivateConfiguration.SecurityActivateConfigurationServiceLocator;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSBindingStub;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSPortType;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.SecurityBehavioralDoSServiceLocator;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesArrayHolder;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.holders.ProfilesHolder;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionGlobalStatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionBindingStub;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionPortType;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.SecurityDnsProtectionServiceLocator;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileArrayHolder;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.holders.DnsProtectionProfileHolder;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateBindingStub;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStatePortType;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.SecurityOutOfStateServiceLocator;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileArrayHolder;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.holders.ProfileHolder;
import com.radware.defenseflow.dp.pojos.Security.Policy.Policy;
import com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyBindingStub;
import com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyPortType;
import com.radware.defenseflow.dp.pojos.Security.Policy.SecurityPolicyServiceLocator;
import com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyArrayHolder;
import com.radware.defenseflow.dp.pojos.Security.Policy.holders.PolicyHolder;
import com.radware.defenseflow.dp.pojos.Security.SignatureProtection.SecuritySignatureProtectionPortType;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionBindingStub;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionPortType;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.SecuritySynProtectionServiceLocator;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.holders.RsIDSSynProfilesEntryHolder;

/**
 * The class Connector is hold a connectors to the web services
 * 
 * @author snirc
 * @author Gera Goft
 * 
 */
public class SoapConnector {

	protected static Logger log = LoggerFactory.getLogger(SoapConnector.class);

	//	private static Logger log = LoggerFactory.getLogger(DPEvent.class);
	//	log.error("Failed to construct the syslogPfxRegex from " + SYSLOG_PREFIX_REGEX, e1);
	//	FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);

	public String								amsKey;
	public String								dpUsername;
	public String								dpPassword;
	public String								dpMgmtAddr;
	public static final String					dpProtocol	= "http";
	public SecurityPolicyPortType				policyPort;
	public SecurityBehavioralDoSPortType		bdosProfilePort;
	public SecurityDnsProtectionPortType		dnsProfilePort;
	public ClassesNetworksPortType				networkPort;
	public SecurityOutOfStatePortType			oosProfilePort;
	public SecuritySignatureProtectionPortType	signaturesProfilePort;
	public SecuritySynProtectionPortType		securitySynProtectionPort;
	public ManagementSyslogServersPortType		syslogTargetPort;
	public ClassesVLANTagGroupPortType			vlanTagGroupPort;
	public SecurityActivateConfigurationPortType activateConfigurationPort;

	/**
	 * The constructor is initiate the connectors
	 * 
	 * @param dpIpAddr
	 * @throws Exception
	 */
	public SoapConnector(String amsKey, String dpMgmtAddr, String dpUsername, String dpPassword) throws IllegalArgumentException {

		String exceptionMessage = "";
		if (amsKey == null) 	exceptionMessage += "amsKey is null, ";
		if (dpUsername == null) exceptionMessage += "dpUsername is null, ";
		if (dpPassword == null) exceptionMessage += "dpPassword is null, ";
		if (dpMgmtAddr == null) exceptionMessage += "dpIpAddr is null, ";
		if(!exceptionMessage.isEmpty()) {
			log.error("Could not create connector - " + exceptionMessage);
			throw new IllegalArgumentException("Could not create connector - " + exceptionMessage);
		}

		this.amsKey = amsKey;
		this.dpUsername = dpUsername;
		this.dpPassword = dpPassword;
		this.dpMgmtAddr = dpMgmtAddr;
	}

	public void init() throws ExceptionControlApp {

		Throwable e = null;
		log.info("Instantiating connectivity to DP " + amsKey);
		for(int i=0;i<3;i++) {
			try {
				if(policyPort == null) initSecurityPolicyPort();
				if(bdosProfilePort == null) initSecurityBehavioralDoSPort();
				if(oosProfilePort == null) initOoSProfilePort();
				if(dnsProfilePort == null) initSecurityDNSPort();
				if(networkPort == null) initSecurityNetworkPort();
				if(vlanTagGroupPort == null) initVLANPort();
				if(securitySynProtectionPort == null) initSecuritySynProtectionPort();			
				if(syslogTargetPort == null) initSyslogTargetSetupPort();
				if(activateConfigurationPort == null) initActivateConfigurationPort();	
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL, "Instantiated connectivity to AMS " + amsKey);
				return;
			} catch (Throwable e1) {
				log.error("Failed to init connector for " + dpMgmtAddr, e1);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to connect to AMS " + amsKey);
				e = e1;
				try {
					Thread.sleep(500);
				} catch (Throwable e2) { /* Ignore */}
			}
		}
		FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to properly initialize connectivity to AMS " + amsKey);
		throw new ExceptionControlApp("Failed to init connector for " + dpMgmtAddr, e);
	}

	private void initSyslogTargetSetupPort() throws ExceptionControlApp {

		ManagementSyslogServersServiceLocator service = new ManagementSyslogServersServiceLocator();
		service.setManagementSyslogServersPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			syslogTargetPort = service.getManagementSyslogServersPort();
			if(syslogTargetPort == null) 
				throw new ExceptionControlApp("Got null from getManagementSyslogServersPort.");
		} catch (Throwable e) {
			log.error("Failed to getManagementSyslogServersPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getManagementSyslogServersPort for " + dpMgmtAddr, e);
		}
		((ManagementSyslogServersBindingStub) syslogTargetPort).setUsername(dpUsername);
		((ManagementSyslogServersBindingStub) syslogTargetPort).setPassword(dpPassword);
	}	

	private void initSecuritySynProtectionPort() throws ExceptionControlApp {

		SecuritySynProtectionServiceLocator service = new SecuritySynProtectionServiceLocator();
		service.setSecuritySynProtectionPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			securitySynProtectionPort = service.getSecuritySynProtectionPort();
			if(securitySynProtectionPort == null) 
				throw new ExceptionControlApp("Got null from getSecuritySynProtectionPort.");
		} catch (Throwable e) {
			log.error("Failed to getSecuritySignatureProtectionPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getSecuritySignatureProtectionPort for " + dpMgmtAddr, e);
		}
		((SecuritySynProtectionBindingStub) securitySynProtectionPort).setUsername(dpUsername);
		((SecuritySynProtectionBindingStub) securitySynProtectionPort).setPassword(dpPassword);
	}

	private void initSecurityPolicyPort() throws ExceptionControlApp {

		SecurityPolicyServiceLocator service = new SecurityPolicyServiceLocator();
		service.setSecurityPolicyPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			policyPort = service.getSecurityPolicyPort();
			if(policyPort == null) 
				throw new ExceptionControlApp("Got null from getSecurityPolicyPort.");
		} catch (Throwable e) {
			log.error("Failed to getSecurityPolicyPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getSecurityPolicyPort for " + dpMgmtAddr, e);
		}
		((SecurityPolicyBindingStub) policyPort).setUsername(dpUsername);
		((SecurityPolicyBindingStub) policyPort).setPassword(dpPassword);
	}

	private void initSecurityBehavioralDoSPort() throws ExceptionControlApp {

		SecurityBehavioralDoSServiceLocator service = new SecurityBehavioralDoSServiceLocator();
		service.setSecurityBehavioralDoSPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			bdosProfilePort = service.getSecurityBehavioralDoSPort();
			if(bdosProfilePort == null) 
				throw new ExceptionControlApp("Got null from getSecurityBehavioralDoSPort.");
		} catch (Throwable e) {
			log.error("Failed to getSecurityBehavioralDoSPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getSecurityBehavioralDoSPort for " + dpMgmtAddr, e);
		}
		((SecurityBehavioralDoSBindingStub) bdosProfilePort).setUsername(dpUsername);
		((SecurityBehavioralDoSBindingStub) bdosProfilePort).setPassword(dpPassword);
	}

	private void initSecurityDNSPort() throws ExceptionControlApp {

		SecurityDnsProtectionServiceLocator service = new SecurityDnsProtectionServiceLocator();
		service.setSecurityDnsProtectionPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			dnsProfilePort = service.getSecurityDnsProtectionPort();
			if(dnsProfilePort == null) 
				throw new ExceptionControlApp("Got null from getSecurityDnsProtectionPort.");
		} catch (Throwable e) {
			log.error("Failed to getSecurityDnsProtectionPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getSecurityDnsProtectionPort for " + dpMgmtAddr, e);
		}
		((SecurityDnsProtectionBindingStub) dnsProfilePort).setUsername(dpUsername);
		((SecurityDnsProtectionBindingStub) dnsProfilePort).setPassword(dpPassword);
	}

	private void initSecurityNetworkPort() throws ExceptionControlApp {

		ClassesNetworksServiceLocator service = new ClassesNetworksServiceLocator();
		service.setClassesNetworksPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			networkPort = service.getClassesNetworksPort();
			if(networkPort == null) 
				throw new ExceptionControlApp("Got null from getClassesNetworksPort.");
		} catch (Throwable e) {
			log.error("Failed to getClassesNetworksPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getClassesNetworksPort for " + dpMgmtAddr, e);
		}
		((ClassesNetworksBindingStub) networkPort).setUsername(dpUsername);
		((ClassesNetworksBindingStub) networkPort).setPassword(dpPassword);		
	}

	private void initVLANPort() throws ExceptionControlApp {

		ClassesVLANTagGroupServiceLocator service = new ClassesVLANTagGroupServiceLocator();
		service.setClassesVLANTagGroupPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			vlanTagGroupPort = service.getClassesVLANTagGroupPort();
			if(vlanTagGroupPort == null) 
				throw new ExceptionControlApp("Got null from getClassesVLANTagGroupPort.");
		} catch (Throwable e) {
			log.error("Failed to getClassesVLANTagGroupPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getClassesVLANTagGroupPort for " + dpMgmtAddr, e);
		}
		((ClassesVLANTagGroupBindingStub) vlanTagGroupPort).setUsername(dpUsername);
		((ClassesVLANTagGroupBindingStub) vlanTagGroupPort).setPassword(dpPassword);		
	}

	private void initOoSProfilePort() throws ExceptionControlApp {

		SecurityOutOfStateServiceLocator service = new SecurityOutOfStateServiceLocator();
		service.setSecurityOutOfStatePortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			oosProfilePort = service.getSecurityOutOfStatePort();
			if(oosProfilePort == null) 
				throw new ExceptionControlApp("Got null from getSecurityOutOfStatePort.");
		} catch (Throwable e) {
			log.error("Failed to getSecurityOutOfStatePort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getSecurityOutOfStatePort for " + dpMgmtAddr, e);
		}
		((SecurityOutOfStateBindingStub) oosProfilePort).setUsername(dpUsername);
		((SecurityOutOfStateBindingStub) oosProfilePort).setPassword(dpPassword);
	}

	public void updateCreateBdosProfile(Profiles profile) throws ExceptionControlApp {

		Profiles existingProfile = null;
		try {
			existingProfile = bdosProfilePort.get_Profiles(profile.getProfileName());
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		if(existingProfile != null) {		
			updateBdosProfile(profile);
		} else {
			createBdosProfile(profile);
		}
	}

	//TODO: error path gera
	private void initActivateConfigurationPort() throws ExceptionControlApp {

		SecurityActivateConfigurationServiceLocator service = new SecurityActivateConfigurationServiceLocator();
		service.setSecurityActivateConfigurationPortEndpointAddress(dpProtocol + "://" + dpMgmtAddr + "/soap");
		try {
			activateConfigurationPort = service.getSecurityActivateConfigurationPort();
			if(activateConfigurationPort == null) 
				throw new ExceptionControlApp("Got null from getSecurityActivateConfigurationPort.");
		} catch (Throwable e) {
			log.error("Failed to getSecurityActivateConfigurationPort for " + dpMgmtAddr, e);
			throw new ExceptionControlApp("Failed to getSecurityActivateConfigurationPort for " + dpMgmtAddr, e);
		}
		((SecurityActivateConfigurationBindingStub) activateConfigurationPort).setUsername(dpUsername);
		((SecurityActivateConfigurationBindingStub) activateConfigurationPort).setPassword(dpPassword);
	}

	public void activatePolicy() throws Throwable {
		activateConfigurationPort.update_ActivePolicies();
	}

	public void createBdosProfile(Profiles profile) throws ExceptionControlApp {

		ProfilesHolder entry = new ProfilesHolder(profile);

		for(int i=0;i<3;i++) {
			try {
				bdosProfilePort.create_Profiles(entry);
				log.info( "Created BDoS profile " + profile.getProfileName() + " in DP " + amsKey);
				return;
			} catch (Throwable e) {
				log.error("Failed to create bdos profile in " + dpMgmtAddr + ": " + e.getMessage(), e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}
		}
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Exhausted trying to create BDoS profile " 
				+ profile.getProfileName() + " in DP " + amsKey);
		throw new ExceptionControlApp("Exhausted trying to create bdos profile in " + dpMgmtAddr);
	}

	public void updateBdosProfile(Profiles profile) throws ExceptionControlApp {

		ProfilesHolder entry = new ProfilesHolder(profile);
		for(int i=0;i<3;i++) {
			try {
				bdosProfilePort.update_Profiles(entry);
				FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Updated BDoS profile " 
						+ profile.getProfileName() + " in DP " + amsKey);
				return;
			} catch (Throwable e) {				
				log.error("Failed to update bdos profile in " + dpMgmtAddr + ": " + e.getMessage(), e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}
		}
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to update BDoS profile in AMS " + amsKey);
		throw new ExceptionControlApp("Exhausted trying to update bdos profile in " + dpMgmtAddr);
	}

	public void updateCreateOOSProfile(Profile profile) throws ExceptionControlApp {

		Profile existingProfile = null;
		try {
			existingProfile = oosProfilePort.get_Profile(profile.getProfileName());
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		if(existingProfile != null) {		
			updateOOSProfile(profile);
		} else {
			createOOSProfile(profile);
		}
	}

	public void createOOSProfile(Profile profile) throws ExceptionControlApp {

		ProfileHolder entry = new ProfileHolder(profile);

		for(int i=0;i<3;i++) {
			try {
				oosProfilePort.create_Profile(entry);
				log.info("Created out of state profile " + profile.getProfileName() + " in DP " + amsKey);
				return;
			} catch (Throwable e) {
				log.error("Failed to create out of state profile in " + dpMgmtAddr + ": " + e.getMessage(), e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}
		}
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to create out-of-state profile for AMS " + amsKey);
		throw new ExceptionControlApp("Exhausted trying to create out of state profile in " + dpMgmtAddr);
	}

	public void updateOOSProfile(Profile profile) throws ExceptionControlApp {

		ProfileHolder entry = new ProfileHolder(profile);
		try {
			oosProfilePort.update_Profile(entry);
			log.info("Updated Out-Of-State profile "+ profile.getProfileName() + " in DP " + amsKey);
		} catch (Throwable e) {
			log.warn("Failed to update out of state profile in " + dpMgmtAddr, e);
		}
	}


	public boolean updateCreateDnsProfile(DnsProtectionProfile profile) throws ExceptionControlApp {

		DnsProtectionProfile existingProfile = null;
		try {
			DnsProtectionGlobalStatus dnsProtectionGlobalStatus = dnsProfilePort.get_DnsProtectionGlobalStatus();
			if(dnsProtectionGlobalStatus.getValue().equals(DnsProtectionGlobalStatus._disable)) 
				return false;		// Dns is disabled - nothing to do.
		} catch (Throwable e) {return false;}
		try {
			existingProfile = dnsProfilePort.get_DnsProtectionProfile(profile.getProfileName());
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		if(existingProfile != null) {		
			updateDnsProfile(profile);
		} else {
			createDnsProfile(profile);
		}
		return true;
	}
	/**
	 * 
	 * @param profile
	 * @throws Exception
	 */
	public void createDnsProfile(DnsProtectionProfile profile) throws ExceptionControlApp {

		DnsProtectionProfileHolder entry = new DnsProtectionProfileHolder(profile);

		for(int i=0;i<3;i++) {
			try {
				dnsProfilePort.create_DnsProtectionProfile(entry);
				log.info("Created dns profile " + profile.getProfileName() + " in DP " + amsKey);
				return;
			} catch (Throwable e) {
				log.error("Failed to create dns profile in " + dpMgmtAddr + ": " + e.getMessage(), e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}
		}
		
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to create DNS profile for AMS " + amsKey);
		throw new ExceptionControlApp("Exhausted trying to create dns profile in " + dpMgmtAddr);
	}

	public void updateDnsProfile(DnsProtectionProfile profile) throws ExceptionControlApp {

		DnsProtectionProfileHolder entry = new DnsProtectionProfileHolder(profile);
		for(int i=0;i<3;i++) {
			try {
				dnsProfilePort.update_DnsProtectionProfile(entry);
				log.info( "Updated dns profile " + profile.getProfileName() + " in DP " + amsKey);
				return;
			} catch (Throwable e) {
				log.warn("Failed to update dns profile in " + dpMgmtAddr, e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}
		}
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to update DNS profile for AMS " + amsKey);
		throw new ExceptionControlApp("Exhausted trying to update dns profile in " + dpMgmtAddr);
	}

	public void createIfNonExistingSynProtectionProfile(RsIDSSynProfilesEntry profile)  throws ExceptionControlApp {		

		RsIDSSynProfilesEntryHolder entry = new RsIDSSynProfilesEntryHolder(profile);
		try {
			securitySynProtectionPort.create_rsIDSSynProfilesEntry(entry);
		} catch (Throwable e) {return; /* Already exists - no further action is required. */}
		log.info("Created SYN profile " + profile.getProfileName() + " in DP " + amsKey);
	}

	/**
	 * 
	 * @return array of DnsProfiles
	 * @throws Exception
	 */
	public DnsProtectionProfile[] getAllDnsProfiles() throws Exception {

		DnsProtectionProfileArrayHolder table = new DnsProtectionProfileArrayHolder();
		BooleanHolder status = new BooleanHolder();
		for(int i=0;i<3;i++) {
			try {
				dnsProfilePort.getAll_DnsProtectionProfile(table, status);
				return table.value;
			} catch (Throwable e) {
				log.error("Failed to get all dns profiles from " + dpMgmtAddr, e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}
		}
		throw new ExceptionControlApp("Exhausted trying to get all dns profiles from " + dpMgmtAddr);
	}

	/**
	 * return the DnsProfiles of the specified name
	 * @param profileName
	 * @return
	 */
	public DnsProtectionProfile getDnsProfile(String profileName) {
		try {
			return dnsProfilePort.get_DnsProtectionProfile(profileName);
		} catch (Throwable e) {
			return null;
		}
	}

	/**
	 * Delete the profile with the given name
	 * @param profileName
	 */
	public void deleteDnsProfile(String profileName){
		try {
			dnsProfilePort.delete_DnsProtectionProfile(profileName);
			log.info( "Deleted DNS profile " + profileName + " from DP " + amsKey);
		} catch (Throwable e) {
			log.warn("failed to delete Dns profile from profile name " + profileName + ". " + e.getMessage());
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to delete DNS profile from AMS " + amsKey);
		}
	}

	/**
	 * 
	 * @return all  Out Of State profiles
	 * @throws Exception
	 */
	public Profile[] getAllOosProfiles() throws Exception {

		ProfileArrayHolder table = new ProfileArrayHolder();
		BooleanHolder status = new BooleanHolder();
		oosProfilePort.getAll_Profile(table, status);
		return table.value;
	}

	/**
	 * 
	 * @param profileName
	 * @return Out Of State profiles of the specified profile name
	 */
	public Profile getOosProfile(String profileName){
		try {
			return oosProfilePort.get_Profile(profileName);
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		return null;
	}

	/**
	 * delete the Out Of State profile of the given name
	 * @param profileName
	 */         
	public void deleteOosProfile(String profileName){
		try {
			oosProfilePort.delete_Profile(profileName);
			log.info("Deleted Out-Of-State profile " + profileName + " from DP " + amsKey);
		} catch (Throwable e) {
			log.warn("failed to delete Out of statre profile from profile name " + profileName + ". " + e.getMessage());
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to delete Out-Of-State profile from AMS " + amsKey);
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Profiles[] getAllBDosProfiles() throws Exception {

		ProfilesArrayHolder table = new ProfilesArrayHolder();
		BooleanHolder status = new BooleanHolder();
		bdosProfilePort.getAll_Profiles(table, status);
		return table.value;
	}

	/**
	 * 
	 * @param profileName
	 * @return
	 * @throws Exception
	 */
	public Profiles getProfile(String profileName) throws Exception {		
		Profiles profile = null;
		try {
			profile = bdosProfilePort.get_Profiles(profileName);
		} catch (Exception e) {/* Ignore - exception means not exist */}		
		return profile;		
	}

	/**
	 * 
	 * @param profileName
	 */
	public void deleteBdosProfile(String profileName){		
		try {
			bdosProfilePort.delete_Profiles(profileName);
			log.info("Deleted BDoS profile " + profileName + " from DP " + amsKey);
		} catch (Throwable e) {
			log.warn("failed to delete Bdos profile from profile name " + profileName + ". " + e.getMessage());
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to delete BDoS profile from AMS " + amsKey);
		}		
	}

	/**
	 * 
	 * @param policy
	 * @throws Exception
	 */
	public void updateCreatePolicy(Policy policy) throws ExceptionControlApp {

		Policy existingPolicy = null;
		try {
			existingPolicy = policyPort.get_Policy(policy.getPolicyName());
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		if(existingPolicy != null) {
			updatePolicy(policy);
		} else {
			createPolicy(policy);
		}
		try {
			activatePolicy();
		} catch (Throwable e) {throw new ExceptionControlApp("Failed to activate policy!" + e.getMessage());}
	}

	/**
	 * 
	 * @param policy
	 * @throws Exception
	 */
	public void createPolicy(Policy policy) throws ExceptionControlApp {

		PolicyHolder policyHolder = new PolicyHolder(policy);
		for(int i=0;i<3;i++) {
			try {
				policyPort.create_Policy(policyHolder);
				log.info("Created policy " + policy.getPolicyName() + " in DP " + amsKey);
				return;
			} catch (Throwable e) {
				log.error("Failed to create policy for " + policyHolder + ": " + e.getMessage(), e);
				try {
					Thread.sleep(500);
				} catch (Throwable e1) { /* Ignore */}
			}		
		}
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to create policy for AMS " + amsKey);
		throw new ExceptionControlApp("Exhausted trying to create policy for " + policyHolder + " in DP " + amsKey);
	}

	public void updatePolicy(Policy policy) throws ExceptionControlApp {

		PolicyHolder policyHolder = new PolicyHolder(policy);
		try {
			policyPort.update_Policy(policyHolder);
			log.info( "Updated policy " + policy.getPolicyName() + " in DP " + amsKey);
		} catch (Throwable e) {			
			log.error("Failed to update policy for " + policy.getPolicyName(), e);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to update policy for AMS " + amsKey);
			throw new ExceptionControlApp("Failed to update policy for " + policy.getPolicyName()+ ": " + e.getMessage());
		}
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public Policy[] getAllPolicies() throws Exception {

		PolicyArrayHolder table = new PolicyArrayHolder();
		BooleanHolder status = new BooleanHolder();
		policyPort.getAll_Policy(table, status);
		return table.value;
	}

	/**
	 * 
	 * @param policyName
	 * @return
	 */
	public Policy getPolicy(String policyName){

		Policy policy = null;
		try {
			policy = policyPort.get_Policy(policyName);
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		return policy;
	}

	/**
	 *  Delete the policy with the specified policy name
	 * @param policyName
	 */
	public void deletePolicy(String policyName) {		
		try {
			policyPort.delete_Policy(policyName);
			log.info( "Deleted policy " + policyName + " from DP " + amsKey);
			activatePolicy();
		} catch (Throwable e) {
			log.warn("failed to delete policy from policy name " + policyName + ". " + e.getMessage());		
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to delete policy from AMS " + amsKey);
		}		
	}

	public void createClassesNetworks(Network network) throws Exception {
		try {
			NetworkHolder entry = new NetworkHolder(network);
			networkPort.create_Network(entry);
			log.info( "Created classes network " + network.getName() + " in DP " + amsKey);
		} catch (Exception e) {
			if(e.getMessage().contains("OperationFailedException"))	return;
		}
	}

	public void createClassesVlan(GroupEntry vlan) throws Exception {

		try {			
			GroupEntryHolder entry = new GroupEntryHolder(vlan);
			vlanTagGroupPort.create_GroupEntry(entry);
			log.info( "Created classes VLAN Group " + vlan.getGroupName() + " in DP " + amsKey);
		} catch (Exception e) {
			if(e.getMessage().contains("OperationFailedException" + ""))	{
				return;
			}
		}
	}

	public void updateClassesVlan(GroupEntry vlan) throws Exception {

		try {			
			GroupEntryHolder entry = new GroupEntryHolder(vlan);
			vlanTagGroupPort.update_GroupEntry(entry);
			log.info("Updated classes VLAN Group " + vlan.getGroupName() + " in DP " + amsKey);
		} catch (Exception e) {
			throw e;
		}
	}

	public Network[] getAllClassesNetworks() throws Exception {

		NetworkArrayHolder table = new NetworkArrayHolder();
		BooleanHolder status = new BooleanHolder();
		networkPort.getAll_Network(table, status);
		return table.value;		
	}

	/**
	 * delete a network with the given NetworkKey
	 * @param networkKey
	 */
	public void deleteClassesNetwork(NetworkKey networkKey) {		
		try {
			networkPort.delete_Network(networkKey);
			log.info( "Deleted classes network " + networkKey + " from DP " + amsKey);
		} catch (Throwable e) {
			log.warn("failed to delete Network from network name " + networkKey.getName() + ". " + e.getMessage());
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to delete classes network " 
					+ networkKey + " from DP " + amsKey);
		}		
	}

	/**
	 * delete a vlan with the given VlanKey
	 * @param vlanKey
	 */
	public void deleteClassesVlan(GroupEntryKey groupEntryKey) {		
		try {
			vlanTagGroupPort.delete_GroupEntry(groupEntryKey);
			log.info("Deleted classes vlan " + groupEntryKey + " from DP " + amsKey);
		} catch (Throwable e) {
			log.warn("failed to delete vlan " + groupEntryKey.getVLANTag() + ". " + e.getMessage());
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to delete the vlan from AMS " + amsKey);
		}		
	}

	/**
	 * 
	 * @param networkKey
	 * @return
	 */
	public Network getClassesNetwork(NetworkKey networkKey){

		Network network = null;
		try {
			network = networkPort.get_Network(networkKey);
		} catch (Throwable e) {/* Ignore - exception means not exist */}
		return network;		
	}

	public void addSyslogTarget(SyslogServersTable syslogTarget) throws ExceptionControlApp {

		SyslogServersTableHolder syslogHolder = new SyslogServersTableHolder(syslogTarget);
		try {
			syslogTargetPort.create_SyslogServersTable(syslogHolder);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL, "Setting DefenseFlow as the syslog server for AMS "+ amsKey );
		} catch (Throwable e) { // We ignore. Problem with DP. syslog entry is set anyways.
			//			if(e.getMessage().contains("OperationFailedException"))	
			//				return;	// This DF IP is already configured as syslog target in this DP
			//			log.error("Failed to create syslog target for " + syslogHolder, e);
			//			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE,"Failed to set syslog " 
			//					+ syslogTarget.getSyslogServerAddress());
			//			throw new ExceptionControlApp("Failed to create syslog target for" + syslogHolder + ": " + e.getMessage());
		}
	}

	public void removeSyslogTarget(String syslogTargetAddr) throws ExceptionControlApp {

		try {
			syslogTargetPort.delete_SyslogServersTable(syslogTargetAddr);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL,"Removing DefenseFlow as the syslog server from AMS "+ amsKey );
		} catch (Throwable e) {
			log.error("Failed to remove syslog target for " + syslogTargetAddr, e);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE,"Failed to remove DefenseFlow as the syslog server from AMS "+ amsKey );
			throw new ExceptionControlApp("Failed to remove syslog target for " + syslogTargetAddr + ": " + e.getMessage());
		}
	}
}
