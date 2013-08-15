/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

/**
 * Represents the flow that is installed on the network node
 * along with the table location, hit counters and timers
 */
public class FlowStat {

    public Flow flow; // Flow descriptor
    public byte tableId;
    public int  durationSeconds;
    public int  durationNanoseconds;
    public long packetCount;
    public long byteCount;

    public FlowStat () {}

    public Flow getFlow() {return flow;}
    public void setFlow(Flow flow) {this.flow = flow;}

    public byte getTableId() {return tableId;}
    public void setTableId(byte tableId) {this.tableId = tableId;}

    public int getDurationSeconds() {return durationSeconds;}
    public void setDurationSeconds(int durationSeconds) {this.durationSeconds = durationSeconds;}

    public int getDurationNanoseconds() {return durationNanoseconds;}
    public void setDurationNanoseconds(int durationNanoseconds) {this.durationNanoseconds = durationNanoseconds;}

    public long getPacketCount() {return packetCount;}
    public void setPacketCount(long count) {packetCount = count;}

    public long getByteCount() {return byteCount;}
    public void setByteCount(long count) {byteCount = count;}

    @Override
    public String toString() {
        return "FlowStat [" + flow.toString() + ", tableId = " + tableId 
        		+ ", sec = " + durationSeconds + ", durationNanoseconds = " + durationNanoseconds
        		+ ", pkt = " + packetCount + ", byte = " + byteCount + "]";
    }
}
