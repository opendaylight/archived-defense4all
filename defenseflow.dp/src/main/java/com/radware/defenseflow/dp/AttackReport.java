/**
 * Copyright 2012 Radware and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * ### class description ###
 *
 * @author Gera Goft
 * @version 0.1
 */
package com.radware.defenseflow.dp;

/**
 * @author gerag
 *
 */
public class AttackReport {

	public long reportTime;		// In seconds
	public MonitoredTraffic monitoredTraffic;
	public DPSecEvent dpSecEvent;		// Contains extra details of the security event
	
	public AttackReport(long reportTime, MonitoredTraffic monitoredTraffic, DPSecEvent dpSecurityEvent) {
		this.reportTime = reportTime; this.monitoredTraffic = monitoredTraffic; this.dpSecEvent = dpSecurityEvent;
	}
	
	@Override
	public String toString() {
		String s = "AttackReport [timeInSecs:" + reportTime + ", " + monitoredTraffic.toString()
				+ ", dpSecurityEvent:" + dpSecEvent.toString() + "]";
		return s;
	}
}
