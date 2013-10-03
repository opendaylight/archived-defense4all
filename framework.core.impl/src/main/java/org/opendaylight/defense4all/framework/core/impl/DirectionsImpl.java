/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */package org.opendaylight.defense4all.framework.core.impl;

import java.util.Properties;

public class DirectionsImpl {
	
	private Properties directions = null;
	private static DirectionsImpl instance = new DirectionsImpl();
	
	private DirectionsImpl() {
		directions = new Properties();
		directions.put("north", "north direction");
		directions.put("south", "south direction");
		directions.put("east", "east direction");
		directions.put("west", "west direction");
	}
	
	public static DirectionsImpl getInstance() {
		return instance;
	}
	
	public Properties getDirections() {
		return directions;
	}
}
