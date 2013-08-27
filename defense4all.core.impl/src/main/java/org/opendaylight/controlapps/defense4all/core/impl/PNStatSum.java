/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */


package org.opendaylight.controlapps.defense4all.core.impl;

import org.opendaylight.controlapps.defense4all.core.TrafficTuple;
import org.opendaylight.controlapps.defense4all.core.impl.CounterStat;
import org.opendaylight.controlapps.defense4all.core.impl.CounterStat.Status;


public class PNStatSum {

	public TrafficTuple average;
	public TrafficTuple latestRate;
	public boolean activePeriod;
	public boolean warmupPeriod;
	public boolean attacked;

	PNStatSum() {
		average = new TrafficTuple(); latestRate = new TrafficTuple(); activePeriod = true; 
		warmupPeriod = false; attacked = false;
	}

	void reset() {
		average.zero(); latestRate.zero(); activePeriod = true; warmupPeriod = false; attacked = false;
	}

	void add(CounterStat counterStat) {

		if(counterStat.status == Status.WARMUP_PERIOD) {
			warmupPeriod = true;
			average.zero();
		}
		if(warmupPeriod) return;			

		latestRate.add(counterStat.latestRate);
		// Konsta if(counterStat.dvsnStat) return; // No adding of diversion stat averages or consideration for active
		if(counterStat.attacked) return;

		activePeriod = activePeriod && counterStat.status == Status.ACTIVE;
		average.add(counterStat.movingAverage);
		attacked = attacked || counterStat.attacked;
	}
}
