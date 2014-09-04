/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.core.interactionstructures;

import java.util.List;

import org.opendaylight.defense4all.core.Detection;

public class EndDetectionNotification {
	
	public Detection detection;
	public List<String> trafficFloorKeys;
	
	public EndDetectionNotification() {detection = null; trafficFloorKeys = null;}
}
