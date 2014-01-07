/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Snir Cohen (6:14)
 * @author Gera Goft
 * @version 0.1
 */
package com.radware.defenseflow.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.Bandwidth;
import org.opendaylight.defense4all.core.DFAppModule;
import org.opendaylight.defense4all.core.DFAppRoot;
import org.opendaylight.defense4all.core.DFAppRoot.RepoMajor;
import org.opendaylight.defense4all.core.DvsnInfo;
import org.opendaylight.defense4all.core.DvsnInfo.AMSDvsnInfo;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.radware.defenseflow.dp.DPRep.RepoMinor;
import com.radware.defenseflow.dp.pojos.Classes.Networks.Network;
import com.radware.defenseflow.dp.pojos.Classes.Networks.NetworkKey;
import com.radware.defenseflow.dp.pojos.Classes.Networks.Network_Mode;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.FeatureStatus;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerFacility;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerProtocol;
import com.radware.defenseflow.dp.pojos.Management.SyslogServers.SyslogServersTable_syslogServerRowStatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FINACKFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_FRAGFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_ICMPFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_IGMPFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_RSTFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNACKFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_SYNFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.BehavioralDoS.Profiles_UDPFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsAaaaFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsMxFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsNaptrFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsOtherFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsPtrFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSoaFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsSrvFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_DnsTextFloodstatus;
import com.radware.defenseflow.dp.pojos.Security.DnsProtection.DnsProtectionProfile_packetReport;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_PacketReport;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileAction;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_ProfileRisk;
import com.radware.defenseflow.dp.pojos.Security.OutOfState.Profile_SYNACKAllow;
import com.radware.defenseflow.dp.pojos.Security.Policy.Policy;
import com.radware.defenseflow.dp.pojos.Security.Policy.Policy_Action;
import com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReport;
import com.radware.defenseflow.dp.pojos.Security.Policy.Policy_PacketReportEnforcement;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry;
import com.radware.defenseflow.dp.pojos.Security.SynProtection.RsIDSSynProfilesEntry_ProfileType;

/**
 * 
 * @author snirc
 * 
 */
public class DPConfigMgr extends DFAppModule {

	public static final String SYN_PROTECTION_PROFILE_NAME = "SYNProt_Global";
	public static final String SIGNATURES_PROFILE_NAME = "Dos-All";

	/* Profile name value suffixes */
	public static final String BDOS_PROFILE_NAME_SUFFIX = "_BDOS";
	public static final String DNS_PROFILE_NAME_SUFFIX = "_DNS";
	public static final String OOS_PROFILE_NAME_SUFFIX = "_OoS";

	/* Percentage of tolerable failures to configure networks in DP */
	public static final int  DP_NETWORKS_FAILURE_THRESHOLD = 20;

	private Logger log = LoggerFactory.getLogger(this.getClass());

	public DPRep amsRep;
	protected HashMap<String, Connector> connectors;

	/*
	 * Syn profile and its name, signatures default profile name (pre-existing
	 * in DP)
	 */
	protected Profile synProtectionProfile; 

	protected long dnsAQuota = 90;
	protected long dnsMxQuota = 45;
	protected long dnsPtrQuota = 45;
	protected long dnsAaaaQuota = 15;
	protected long dnsTextQuota = 8;
	protected long dnsSoaQuota = 2;
	protected long dnsNaptrQuota = 2;
	protected long dnsSrvQuota = 2;
	protected long dnsOtherQuota = 2;
	protected long actThreshold = 5000;
	protected long termThreshold = 4000;

	/** Repos for configured networks and security configurations. */
	public Repo<String> configuredNetworkRepo = null;
	public Repo<String> securityConfigRepo = null;
	

	/**
	 * Create DPCfgMgr object
	 * 
	 * @param connector
	 * @param custDnsTrafficEstimate
	 */
	public DPConfigMgr() {

		super();
		connectors = new HashMap<String, Connector>();

		/* Init globally defined profiles/names */
		synProtectionProfile = new Profile(); 
		synProtectionProfile.setProfileName(SYN_PROTECTION_PROFILE_NAME);
	}

	/* Setters for Spring */
	public void setAmsRep(DPRep amsRep) {this.amsRep = amsRep;}

	@Override
	public void init() throws ExceptionControlApp {

		super.init();

		/* create/get DPConfigMgr Repos */
		try {
			RepoFactory rf = fMain.getRepoFactory();
			String rMajor = RepoMajor.DF_AMS_REP.name();
			StringSerializer sSer = StringSerializer.get();
			configuredNetworkRepo = (Repo<String>) rf.getOrCreateRepo(rMajor, RepoMinor.CONFIGURED_NETWORKS.name(),	sSer, 
					true, ConfiguredNetwork.getRCDs());
			securityConfigRepo = (Repo<String>) rf.getOrCreateRepo(rMajor, RepoMinor.SECURITY_CONFIGURATIONS.name(), sSer, 
					true, SecurityConfig.getRCDs());
		} catch (Throwable e) {
			log.error("Failed to getOrCreateRepo for configured networks.", e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to getOrCreateRepo for configured networks.", e);
		}
	}

	@Override
	public void finit() {
		super.finit();
	}

	@Override
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		super.reset(resetLevel);
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws ExceptionControlApp
	 * @throws exception_type circumstances description
	 */
	public void addAMS(String amsKey) throws ExceptionControlApp, IllegalArgumentException {

		if(amsKey == null) throw new IllegalArgumentException("amsKey is null");

		Hashtable<String, Object> amsRow; AMS ams;	Connector connector;
		Iterator<Map.Entry<String, Hashtable<String, Object>>> configuredNetworkIter;
		Hashtable<String, Hashtable<String, Object>> table; int numOfDpNetworks;
		Set<Map.Entry<String, Hashtable<String, Object>>> entrySet;
		ConfiguredNetwork configuredNetwork; Hashtable<String, Object> configurationNetworkRow;
		Network dpNetwork = null; int failuresCount = 0; 

		try {

			amsRow = dfAppRoot.amsRepo.getRow(amsKey);
			ams = new AMS(amsRow);

			/* Create a connector object to this DP. */
			connector = new Connector(amsKey, ams.mgmtAddr.getHostAddress(), ams.username, ams.password);
			connector.init();			
			connectors.put(amsKey, connector);	 

			table = configuredNetworkRepo.getTable(); 
			entrySet = table.entrySet();
			configuredNetworkIter = entrySet.iterator();
			numOfDpNetworks = entrySet.size();

		} catch (Throwable e1) {
			connectors.remove(amsKey); // In case the exception occurred after we have added the connector to connectors
			String msg = "Excepted trying to retrieve or construct relevant information" + amsKey;
			log.error(msg, e1);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e1);	
		}	

		/* Add global profiles (applicable for all PNs). */
		try {
			addGlobalProfilesIfNeeded(connector);
		} catch (Throwable e1) {
			connectors.remove(amsKey); // In case the exception occurred after we have added the connector to connectors
			String msg = "Excepted trying to add global profiles to DP" + amsKey;
			log.error(msg, e1);
			throw new ExceptionControlApp(msg, e1);
		}

		/* Add all DF instances (as of now there is only one) as DP syslog targets. */
		try {
			addSyslogTarget(connector, fMain.getHostAddr());
		} catch (Throwable e1) {
			connectors.remove(amsKey);
		}

		/* Add network configurations for all PNs to the newly added DP. */
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Adding to DP " + amsKey 
				+ " classes networks representing all protected networks");
		while (configuredNetworkIter.hasNext()) {
			try {
				configurationNetworkRow = configuredNetworkIter.next().getValue(); 
				configuredNetwork = new ConfiguredNetwork(configurationNetworkRow); 
				dpNetwork = createDPNetworkObject(configuredNetwork); 
				connector.createClassesNetworks(dpNetwork);				
			} catch (Throwable e) {
				log.error("Failed to create network: " + dpNetwork, e);
				failuresCount++;
			}
		}

		/* Decide whether the operation is failed based on the percentage of failed dpNetwork creations in DP. 
		 * 1 is added to numOfDpNetworks to avoid division by zero in case there are no configured networks (no PNs). */
		if(failuresCount * 100 / (numOfDpNetworks + 1) < DP_NETWORKS_FAILURE_THRESHOLD) return;

		/* Operation is failed, so try to undo all side affects (no atomicity - best effort for each undo). 
		 * We do not remove global profiles if created (treat them as part of the basic DP setup). */

		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "The number of classes networks " 
				+ " failed to be set up in DP " + amsKey + "is " + failuresCount + ". Exceeded tolerable threshold.");

		/* Remove the newly added connector. */
		connectors.remove(amsKey);

		/* Remove all dpNetworks, ignoring failure to remove any one of them. */
		try {
			configuredNetworkIter = entrySet.iterator();
		} catch (Throwable e1) {
			log.debug("Failed to obtain second iterator: " + amsKey, e1);
		}	
		NetworkKey networkKey = null;
		while (configuredNetworkIter.hasNext()) {
			try {
				configurationNetworkRow = configuredNetworkIter.next().getValue(); 
				configuredNetwork = new ConfiguredNetwork(configurationNetworkRow); 
				dpNetwork = createDPNetworkObject(configuredNetwork); 
				networkKey = new NetworkKey(configuredNetwork.name, 0);
				connector.deleteClassesNetwork(networkKey);
			} catch (Throwable e) {
				log.error("Failed to remove Network: " + networkKey, e);
			}
		}

		/* Log and report error. */
		String msg = "Failed to add this DP, because failed to configure networks for too many PNs." + amsKey;
		log.error(msg);
		throw new ExceptionControlApp(msg);	
	}

	/*
	 * Add syn protection and signatures profiles if needed. If added - reboot
	 * DP to pick up changes.
	 */
	public void addGlobalProfilesIfNeeded(Connector connector) throws ExceptionControlApp {

		try {

			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Adding to DP " + connector.amsKey 
					+ " global SYN profiles for HTTP and HTTPS (if needed)");
			RsIDSSynProfilesEntry synProtectionProfileObject = createSynProtectionProfileObject((long)200000, "HTTP");
			connector.createSynProtectionProfile(synProtectionProfileObject);
			synProtectionProfileObject = createSynProtectionProfileObject((long)200001, "HTTPS");
			connector.createSynProtectionProfile(synProtectionProfileObject);
		} catch (ExceptionControlApp e) {
			String msg = "Failed to add Global Profiles network: " + e.getMessage();
			log.error(msg);
			throw new ExceptionControlApp(msg);
		}		
	}

	/** 
	 * Add specified DF address as syslog target for the specified DP 
		 * #### method description ####
		 * @param param_name param description
		 * @return return description
		 * @throws exception_type circumstances description
	 */
	public void addSyslogTarget(Connector connector, String syslogTargetAddr) throws ExceptionControlApp {

		try {
			long syslogServerSourcePort = 514; long syslogServerDestinationPort = 514;
			SyslogServersTable syslogTarget = new SyslogServersTable(syslogTargetAddr, FeatureStatus.Enabled, 
				syslogServerSourcePort,	syslogServerDestinationPort, SyslogServersTable_syslogServerFacility.value23,
				SyslogServersTable_syslogServerProtocol.value1, "", SyslogServersTable_syslogServerRowStatus.reachable,
				null, FeatureStatus.Enabled, FeatureStatus.Enabled, FeatureStatus.Enabled);
			connector.addSyslogTarget(syslogTarget);
		} catch (Throwable e) {
			String msg = "Failed to add syslog target "+syslogTargetAddr+" to DP "+connector.amsKey+" - "+e.getMessage();
			log.error(msg);
			throw new ExceptionControlApp(msg);
		}		
	}

	private RsIDSSynProfilesEntry createSynProtectionProfileObject(long attactId, String attactName) {

		RsIDSSynProfilesEntry rsIDSSynProfilesEntry = new RsIDSSynProfilesEntry();
		rsIDSSynProfilesEntry.setProfileName(SYN_PROTECTION_PROFILE_NAME);
		rsIDSSynProfilesEntry.setAttackID(attactId);
		rsIDSSynProfilesEntry.setAttackName(attactName);
		rsIDSSynProfilesEntry.setProfileType(RsIDSSynProfilesEntry_ProfileType.value2);
		return rsIDSSynProfilesEntry;
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws ExceptionControlApp
	 * @throws exception_type circumstances description
	 */
	public void removeAMS(String amsKey) throws ExceptionControlApp, IllegalArgumentException {

		if(amsKey == null) throw new IllegalArgumentException("amsKey is null");

		/* Create a connector object to this DP. */
		Connector connector = connectors.remove(amsKey);

		/* Remove network configurations for all PNs to the being removed DP. */
		List<String> configNetworkKeys = null;
		try {
			configNetworkKeys = configuredNetworkRepo.getKeys();
		} catch (Throwable e) {
			log.error("Failed to remove ams: " + amsKey, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to remove ams: " + amsKey, e);
		}
		NetworkKey networkKey = null;
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Deleting from DP " + connector.amsKey 
				+ " all classes networks that DF has installed for all protected networks");
		for (String configuredNetworkName : configNetworkKeys) {
			try {
				networkKey = new NetworkKey(configuredNetworkName, 0);
				connector.deleteClassesNetwork(networkKey); 
			} catch (Throwable e) {
				log.error("Failed to remove Network: " + networkKey, e);
			}
		}
		
		/* Remove all DF instances (as of now there is only one) as syslog targets. */
		try {
			connector.removeSyslogTarget(fMain.getHostAddr());
		} catch (Exception e) { /* Ignore */}
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws ExceptionControlApp
	 * @throws exception_type
	 *             circumstances description
	 */
	public void addPN(String pnkey) throws ExceptionControlApp {

		Network dpNetwork = null; Connector connector = null;
		Iterator<Map.Entry<String, Connector>> connectorsIter = connectors.entrySet().iterator();

		try {
			/* Create and populate the configuredNetwork object. */
			ConfiguredNetwork configuredNetwork = new ConfiguredNetwork();
			configuredNetwork.name = DPRep.generateNetworkName(pnkey);
			configuredNetwork.address = (String) dfAppRoot.pNsRepo.getCellValue(pnkey, PN.DST_ADDR); 
			configuredNetwork.addressPrefixLen = (Integer) dfAppRoot.pNsRepo.getCellValue(pnkey, PN.DST_ADDR_PREFIX_LEN); 

			/* Add the configured network to its repo. */
			configuredNetworkRepo.setRow(configuredNetwork.name,configuredNetwork.toRow()); 

			dpNetwork = createDPNetworkObject(configuredNetwork);

			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_OPERATIONAL, "Adding to all DPs " + configuredNetwork.toString());
		} catch (Exception e1) {
			String msg = "Excepted trying to add pn: " + pnkey;
			log.error(msg, e1);
			throw new ExceptionControlApp(msg, e1);
		}		

		/* Pre-register the PN network in all DPs. */
		while (connectorsIter.hasNext()) {
			try {
				connector = connectorsIter.next().getValue();
				connector.createClassesNetworks(dpNetwork);
			} catch (Exception e) {
				log.error("Failed to create network: " + dpNetwork + " at " + (connector==null ? "" : connector.amsKey), e);
			}
		}
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws exception_type
	 *             circumstances description
	 */
	public void removePN(String pnkey) throws IllegalArgumentException {

		if(pnkey == null) throw new IllegalArgumentException("Null pnkey.");

		/* Unregister the PN network in all DPs. */
		Iterator<Map.Entry<String, Connector>> iter = connectors.entrySet().iterator();
		String networkName = DPRep.generateNetworkName(pnkey);
		NetworkKey networkKey = new NetworkKey(networkName, 0);
		Connector connector;
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Removing from all DPs " + networkKey.getName());
		while (iter.hasNext()) {
			connector = iter.next().getValue();
			connector.deleteClassesNetwork(networkKey); 
		}

		/* Remove the configured network from repo */
		try {
			configuredNetworkRepo.deleteRow(pnkey);
		} catch (ExceptionControlApp e) {
			log.error("Failed to delete row: " + pnkey, e);
		}
	}

	public void addSecurityConfiguration(String dvsnInfoKey) throws ExceptionControlApp {

		/* Retrieve/generate networkName, connector, inboundBandwidthStr, dvsnInfo, pnkey. */
		Hashtable<String, Object> dvsnInfoRow = dfAppRoot.dvsnInfosRepo.getRow(dvsnInfoKey);
		DvsnInfo dvsnInfo = new DvsnInfo(dvsnInfoRow); 
		AMSDvsnInfo amsDvsnInfo = dvsnInfo.amsDvsnInfos.get(0);
		Connector connector = connectors.get(amsDvsnInfo.label);		
		String inboundBandwidthStr = dvsnInfo.configProps.getProperty(DvsnInfo.INBOUND_BANDWIDTH);		
		String pnkey = (String) dfAppRoot.mitigationsRepo.getCellValue(dvsnInfo.mitigationKey, Mitigation.PNKEY);			
		String networkName = DPRep.generateNetworkName(pnkey);

		/* Add the security configuration to the DP and record it in Repo. */
		FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Adding security configuration to DP " 
				+ connector.amsKey + "for diversion " + dvsnInfoKey);
		addSecurityConfigInDP(networkName, connector, inboundBandwidthStr);		
		try {
			addSecurityConfigInRepo(networkName, dvsnInfo, amsDvsnInfo, pnkey);
		} catch (Throwable e) { // Need to clean up from DP
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_FAILURE, "Failed to add security configuration to DP " 
					+ connector.amsKey + "for diversion " + dvsnInfoKey + "Removing any partially set configuration.");
			removeSecurityConfigInDP(connector, networkName);
			log.error("Failed to add security configuration in repo - cleaning out in DP.", e);
			throw new ExceptionControlApp(e);
		}
	}

	protected void removeSecurityConfigInDP(Connector connector, String networkName) {
		connector.deletePolicy(networkName);
		connector.deleteBdosProfile(networkName + BDOS_PROFILE_NAME_SUFFIX); 
		connector.deleteDnsProfile(networkName + DNS_PROFILE_NAME_SUFFIX); 
		connector.deleteOosProfile(networkName + OOS_PROFILE_NAME_SUFFIX); 
	}

	/**
	 * #### method description ####
	 * @param dnsProfileCreated 
	 * 
	 * @param param_name
	 *            param description
	 * @return return description
	 * @throws ExceptionControlApp
	 * @throws exception_type circumstances description
	 */
	public void addSecurityConfigInDP(String networkName, Connector connector, String inboundBandwidthStr)
			throws ExceptionControlApp {			

		long bandwidthKbps = 0;
		Bandwidth bandwidth;

		try {
			bandwidth = new Bandwidth(inboundBandwidthStr);
			bandwidthKbps = bandwidth.bytes * 8 / 1000; 
			bandwidthKbps = bandwidthKbps < 1 ? 1 : bandwidthKbps;

		} catch (Throwable e) { 
			log.error("Failed to calculate bandwidth, " + e.getMessage(), e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to calculate bandwidth, " + e.getMessage());
		}		

		/* Create all the security profiles and policy for this attacked PN. */
		String bdosProfileName = null;
		String dnsProfileName = null;
		String oosProfileName = null;
		boolean bdosProfileCreated = false;
		boolean dnsProfileCreated = false;
		boolean oosProfileCreated = false;

		try {

			long custDnsTrafficEstimate = 0; 

			bdosProfileName = networkName + BDOS_PROFILE_NAME_SUFFIX;
			dnsProfileName = networkName + DNS_PROFILE_NAME_SUFFIX;
			oosProfileName = networkName + OOS_PROFILE_NAME_SUFFIX;

			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY,"Calculated bandwidth for BDoS profile: "+bandwidthKbps);
			Profiles bdosProfile = createBdosProfile(bdosProfileName, bandwidthKbps);
			DnsProtectionProfile dnsProfile = createDnsProfile(dnsProfileName, custDnsTrafficEstimate);
			Profile oosProfile = createOutOfStateProfile(oosProfileName);
			Policy securityPolicy = createPolicy(networkName, bdosProfileName, dnsProfileName, oosProfileName);

			/* Set in DP all created profiles and policy for this attacked PN. */
			connector.createBdosProfile(bdosProfile); 
			bdosProfileCreated = true;
			connector.createDnsProfile(dnsProfile);
			dnsProfileCreated = true;
			connector.createOOSProfile(oosProfile);
			oosProfileCreated = true;
			connector.createPolicy(securityPolicy);

		} catch (Throwable e) {
			//Don't need to be removed it do update instead
//				if(bdosProfileCreated && bdosProfileName != null) connector.deleteBdosProfile(bdosProfileName);
//				if(dnsProfileCreated && bdosProfileName != null) connector.deleteDnsProfile(bdosProfileName);			
//				if(oosProfileCreated && oosProfileName != null) connector.deleteOosProfile(oosProfileName);
			log.error("Failed to add security configuration, " + e.getMessage(), e);
			throw new ExceptionControlApp("Failed to add security configuration: " + e.getMessage());
		}		
	}

	private void addSecurityConfigInRepo(String networkName, DvsnInfo dvsnInfo, AMSDvsnInfo amsDvsnInfo, String pnkey)
			throws ExceptionControlApp {

		/* Check if the security configuration already exists in repo (could be registered with another DP). If so - 
		 * update that record with this DP. Otherwise create the record in the repo with this DP. */
		boolean setInAmsRepo = false; String secConfigKey = "";
		try {
			String bdosProfileName = networkName + BDOS_PROFILE_NAME_SUFFIX;
			String dnsProfileName = networkName + DNS_PROFILE_NAME_SUFFIX;
			String oosProfileName = networkName + OOS_PROFILE_NAME_SUFFIX;

			secConfigKey = SecurityConfig.generateKey(networkName, dvsnInfo.mitigationKey);
			dfAppRoot.amsRepo.setCell(amsDvsnInfo.label, AMS.SECURITY_CONFIG_PREFIX	+ secConfigKey, secConfigKey);
			setInAmsRepo = true;

			if (securityConfigRepo.hasCell(secConfigKey,SecurityConfig.NETWORK_NAME)) 
				// Means this security config was already set for other DPs - so add this one
				securityConfigRepo.setCell(secConfigKey,SecurityConfig.DP_NAME_PREFIX + amsDvsnInfo.label, amsDvsnInfo.label);
			else { // Need to create new security record and add to repo
				SecurityConfig securityConfig = new SecurityConfig();
				securityConfig.mitigationKey = dvsnInfo.mitigationKey;
				securityConfig.pnkey = pnkey;
				securityConfig.configuredNetworkName = networkName;
				securityConfig.bdosProfileName = bdosProfileName;
				securityConfig.dnsProfileName = dnsProfileName;
				securityConfig.oosProfileName = oosProfileName;
				securityConfig.securityPolicyName = networkName;
				securityConfig.generateAndSetKey();
				securityConfig.configuredDPs = new ArrayList<String>();
				securityConfig.configuredDPs.add(amsDvsnInfo.label);
				securityConfigRepo.setRow(secConfigKey, securityConfig.toRow()); 
			}
		} catch (Throwable e) {
			if(setInAmsRepo) {
				try {
					dfAppRoot.amsRepo.deleteCell(amsDvsnInfo.label, AMS.SECURITY_CONFIG_PREFIX	+ secConfigKey);
				} catch (Throwable e1) {/* Ignore. */}
			}
			String msg = "Failed to add security configuration in database";
			log.error(msg, e);
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg + e.getMessage());
		}

		// We do not currently address the need to book-keep preparing for multiple diversions into for the same PN into
		// the same DP. This means that: 1. In case of dual attempt to set up profiles and policy for the same PN we 
		// ignore DP's rejection to setup. 2. We do not update the bandwidth to account for diversion from another netnode.
	}

	/**
	 * #### method description ####
	 * 
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp
	 * @throws exception_type circumstances description
	 */
	public void removeSecurityConfiguration(String dvsnInfoKey) {

		String amsKey = ".";
		try {
			Hashtable<String, Object> dvsnInfoRow = dfAppRoot.dvsnInfosRepo.getRow(dvsnInfoKey); 
			if(dvsnInfoRow == null) { // Not necessarily a bug - maybe because of partial cleanup.
				log.error("May not have fully removed security configuration - got null dvsnInfoRow for key " + dvsnInfoKey);
				return;
			}
			DvsnInfo dvsnInfo = new DvsnInfo(dvsnInfoRow); 
			AMSDvsnInfo amsDvsnInfo = dvsnInfo.amsDvsnInfos.get(0);
			Connector connector = connectors.get(amsDvsnInfo.label);
			String pnkey = (String) dfAppRoot.mitigationsRepo.getCellValue(dvsnInfo.mitigationKey, Mitigation.PNKEY); 
			String networkName = DPRep.generateNetworkName(pnkey);
			String securityConfigKey = SecurityConfig.generateKey(networkName, dvsnInfo.mitigationKey);

			amsKey = connector.amsKey;
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, "Removing security configuration from DP " 
					+ amsKey + "for diversion " + dvsnInfoKey);
			removeSecurityConfigInDP(connector, networkName);

			/* Remove the securityConfig from its repo. Also delete the securityConfigKey from AMS repo. */
			securityConfigRepo.deleteRow(securityConfigKey); 
			dfAppRoot.amsRepo.deleteCell(amsDvsnInfo.label,	AMS.SECURITY_CONFIG_PREFIX + securityConfigKey);
		} catch (Throwable e) {
			log.error("Failed to fully remove security configuration", e);
			StringBuilder sb = new StringBuilder(); 
			sb.append("Failed to fully remove security configuration from DP "); sb.append(amsKey);
			sb.append(", "); sb.append(dvsnInfoKey);
			FMHolder.get().getFR().logRecord(DFAppRoot.FR_AMS_SECURITY, sb.toString());
			fMain.getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}

		// We do not currently address the need to book-keep preparing for multiple diversions into for the same PN into
		// the same DP. This means that in case of dual attempt to remove profiles and policy at the end of an attack we
		// ignore DP rejection.
	}

	public Network createDPNetworkObject(ConfiguredNetwork configuredNetwork) {

		Network network = new Network();
		network.setName(configuredNetwork.name);
		network.setIndex(0); 
		network.setAddress(configuredNetwork.address);
		network.setMask(Integer.toString(configuredNetwork.addressPrefixLen));
		network.setMode(Network_Mode.value1); // value1 = "IP Mask"; value2 = "IP Range"

		return network;
	}

	public Network createDPNetworkObject(String address, String mask, String name) {

		Network network = new Network();
		network.setName(name);
		network.setIndex(1); 
		network.setAddress(address);
		network.setMask(mask);
		network.setMode(Network_Mode.value2);

		return network;
	}


	private Profiles createBdosProfile(String bdosProfileName, long bandwidth) {

		Profiles profile = new Profiles();
		profile.setProfileName(bdosProfileName);
		profile.setBandwidthIn(bandwidth);
		profile.setBandwidthOut(bandwidth); 
		profile.setSYNACKFloodstatus(Profiles_SYNACKFloodstatus.active);
		profile.setSYNFloodstatus(Profiles_SYNFloodstatus.active);
//		profile.setTransparentOptimization(Profiles_TransparentOptimization.yes);
		profile.setUDPFloodstatus(Profiles_UDPFloodstatus.active);
		profile.setICMPFloodstatus(Profiles_ICMPFloodstatus.active);
		profile.setIGMPFloodstatus(Profiles_IGMPFloodstatus.active);
		profile.setFINACKFloodstatus(Profiles_FINACKFloodstatus.active);
		profile.setFRAGFloodstatus(Profiles_FRAGFloodstatus.active);
		profile.setRSTFloodstatus(Profiles_RSTFloodstatus.active);

		return profile;
	}

	private DnsProtectionProfile createDnsProfile(String dnsProfileName, long custDnsTrafficEstimate) {

		DnsProtectionProfile profile = new DnsProtectionProfile();

		profile.setProfileName(dnsProfileName);
		profile.setDnsAFloodstatus(DnsProtectionProfile_DnsAFloodstatus.active);
		profile.setDnsMxFloodstatus(DnsProtectionProfile_DnsMxFloodstatus.active);
		profile.setDnsPtrFloodstatus(DnsProtectionProfile_DnsPtrFloodstatus.active);
		profile.setDnsAaaaFloodstatus(DnsProtectionProfile_DnsAaaaFloodstatus.active);
		profile.setDnsTextFloodstatus(DnsProtectionProfile_DnsTextFloodstatus.active);
		profile.setDnsSoaFloodstatus(DnsProtectionProfile_DnsSoaFloodstatus.active);
		profile.setDnsNaptrFloodstatus(DnsProtectionProfile_DnsNaptrFloodstatus.active);
		profile.setDnsSrvFloodstatus(DnsProtectionProfile_DnsSrvFloodstatus.active);
		profile.setDnsOtherFloodstatus(DnsProtectionProfile_DnsOtherFloodstatus.active);
		profile.setExpectedQps(custDnsTrafficEstimate);
		profile.setDnsAQuota(dnsAQuota);
		profile.setDnsMxQuota(dnsMxQuota);
		profile.setDnsPtrQuota(dnsPtrQuota);
		profile.setDnsAaaaQuota(dnsAaaaQuota);
		profile.setDnsTextQuota(dnsTextQuota);
		profile.setDnsSoaQuota(dnsSoaQuota);
		profile.setDnsNaptrQuota(dnsNaptrQuota);
		profile.setDnsSrvQuota(dnsSrvQuota);
		profile.setDnsOtherQuota(dnsOtherQuota);
		profile.setMaxAllowedQPS(custDnsTrafficEstimate * 2);
		profile.setPacketReport(DnsProtectionProfile_packetReport.enable);

		return profile;
	}

	private Profile createOutOfStateProfile(String oosProfileName) {

		Profile profile = new Profile();

		profile.setProfileName(oosProfileName);
		profile.setACTThreshold(actThreshold);
		profile.setTermThreshold(termThreshold);
		profile.setSYNACKAllow(Profile_SYNACKAllow.enable);
		profile.setProfileRisk(Profile_ProfileRisk.medium);
		profile.setProfileAction(Profile_ProfileAction.value2); 
		profile.setPacketReport(Profile_PacketReport.enable);

		return profile;
	}

	public Policy createPolicy(String networkName, String bdosProfileName,String dnsProfileName, String oosProfileName) {

		Policy policy = new Policy();
		policy.setPolicyName(networkName);
		policy.setPolicyDestinationAddress(networkName);
		policy.setPolicySourceAddress("any");
		policy.setBehavioralDosProfile(bdosProfileName);
		//	policy.setDNSProtectionProfile(dnsProfileName);
		//	policy.setSignaturesProfile(signaturesProfileName);
		policy.setSynProtectionProfile(synProtectionProfile.getProfileName());
		policy.setOutOfStateProfile(oosProfileName);
		policy.setAction(Policy_Action.value2);
		policy.setPacketReport(Policy_PacketReport.enable);
		policy.setPacketReportEnforcement(Policy_PacketReportEnforcement.enable);

		return policy;
	}

	@Override
	protected void actionSwitcher(int actionCode, Object param) {
	}
}
