/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.core;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.opendaylight.defense4all.core.CounterStat.Status;

@JsonIgnoreProperties({"lastReading","latestRate","average"})
public class PNStatReport {

	public String pnKey;	public Status status;
	public String lastReadingStr;		public TrafficTuple lastReading;	// bytes/packets counter reading
	public String latestRateStr;		public TrafficTuple latestRate;		// bytes/packets per second
	public String averageStr; 			public TrafficTuple average;		// bytes/packets per second

	public PNStatReport() {

		this.pnKey = ""; status = Status.INVALID;		
		lastReadingStr = ""; lastReading = new TrafficTuple();
		latestRateStr = "";  latestRate = new TrafficTuple();
		averageStr = "";  average = new TrafficTuple();
	}	

	public PNStatReport(CounterStat pnStatSum) {

		this();
		if(pnStatSum == null) return;

		this.pnKey = pnStatSum.pnKey; this.status = pnStatSum.status;		
		this.lastReading = pnStatSum.lastReading;
		this.latestRate = pnStatSum.latestRate;
		this.average = pnStatSum.average;
		toJacksonFriendly();
	}
	
	public String getPnKey() {return pnKey;}
	public void setPnKey(String pnKey) {this.pnKey = pnKey;}

	public Status getStatus() {return status;}
	public void setStatus(Status status) {this.status = status;}
	
	public String getLastReadingStr() {return lastReadingStr;}
	public void setLastReadingStr(String lastReadingStr) {
		this.lastReadingStr = lastReadingStr;
		this.lastReading = new TrafficTuple(lastReadingStr);
	}

	public TrafficTuple getLastReading() {return lastReading;}
	public void setLastReading(TrafficTuple lastReading) {this.lastReading = lastReading;}

	public String getLatestRateStr() {return latestRateStr;}
	public void setLatestRateStr(String latestRateStr) {
		this.latestRateStr = latestRateStr;
		this.latestRate = new TrafficTuple(latestRateStr);
	}

	public TrafficTuple getLatestRate() {return latestRate;}
	public void setLatestRate(TrafficTuple latestRate) {this.latestRate = latestRate;}

	public String getAverageStr() {return averageStr;}
	public void setAverageStr(String averageStr) {
		this.averageStr = averageStr;
		this.average = new TrafficTuple(averageStr);
	}

	public TrafficTuple getAverage() {return average;}
	public void setAverage(TrafficTuple average) {this.average = average;}

	public void toJacksonFriendly() {		
		lastReadingStr = (lastReading == null ? "" : lastReading.toString());
		latestRateStr = (latestRate == null ? "" : latestRate.toString());
		averageStr = (average == null ? "" : average.toString());
	}
	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append("pnKey:"); sb.append(pnKey); sb.append("; ");
		sb.append("lastReading:"); if (lastReading!= null ) sb.append(lastReading.toString()); sb.append("; ");
		sb.append("latestRate:"); if (latestRate!= null ) sb.append(latestRate.toString()); sb.append("; ");
		sb.append("average:"); if (average!= null ) sb.append(average.toString()); sb.append("; ");
		sb.append("status:"); sb.append(status.name()); sb.append("; ");
		sb.append("]");
		return sb.toString();
	}

	public String toString(int protocol) {

		StringBuilder sb = new StringBuilder();
		sb.append("latestRate="); if (latestRate!= null ) sb.append(latestRate.toString(protocol)); sb.append("; ");
		sb.append("average="); if (average!= null ) sb.append(average.toString(protocol)); sb.append("; ");
		return sb.toString();
	}
}
