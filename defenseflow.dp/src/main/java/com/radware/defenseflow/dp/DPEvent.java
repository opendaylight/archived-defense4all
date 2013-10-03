/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * EventProcessor reads events from syslog named pipe, and generates security event queue messages.
 *
 * @author Kobi Samoray
 * @author Gera Goft
 * @version 0.1
 */

package com.radware.defenseflow.dp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ####
 */
public class DPEvent {

	private static Pattern syslogPfxRegex = null;
	public String msg;
	public InetAddress dpAddr;
	
	/* syslog DP message example:
	 * Jun 17 11:40:43 10.210.49.41 DefensePro: security_msg_from_here
	 * "^(\\w\\w\\w)\\s+(\\d+)\\s+(\\d+:\\d+:\\d+)\\s+(.+?)\\s+(\\w+?):\\s+?";
	 */
	public final static String MONTH = "^(\\w\\w\\w)";		// Jun 				- regex group 1
	public final static String DATE = "(\\d+)";				// 17				- regex group 2
	public final static String TIME = "(\\d+:\\d+:\\d+)";	// 11:40:43			- regex group 3
	public final static String DP_IP = "(.+?)";				// 10.210.49.41		- regex group 4
	public final static String DP_SRC_ID = "DefensePro:";	// DefensePro:
	public final static String DP_MSG = "(.+)";				// --See DPSecurityEvent message example-- - regex group 5
	public static String SYSLOG_PREFIX_REGEX = MONTH+"\\s+"+DATE+"\\s+"+TIME+"\\s+"+DP_IP+"\\s+"+DP_SRC_ID+"\\s+"+DP_MSG;
	private final static int SYSLOG_PREFIX_GROUPS = 5;

	public DPEvent() {
		super();
	}
	
	public static DPEvent fromString(String s) {
		
		if(syslogPfxRegex == null) // First time usage
			syslogPfxRegex = Pattern.compile(SYSLOG_PREFIX_REGEX);
		
		Matcher matcher = syslogPfxRegex.matcher(s);
		if(!matcher.find() || matcher.groupCount() != SYSLOG_PREFIX_GROUPS) return null;

		try {
			DPEvent dpMsg = new DPEvent();
			dpMsg.dpAddr = InetAddress.getByName(matcher.group(4));
			dpMsg.msg = matcher.group(5);
			return dpMsg;	
		} catch (UnknownHostException e) {return null;}
	}
	
	@Override
	public String toString() {
		String s = "DPEvent [msg=" + msg + ", dpAddr=" + dpAddr + "]";
		return s;
	}
}
