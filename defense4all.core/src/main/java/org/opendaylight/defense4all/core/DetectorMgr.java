/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.core;

public interface DetectorMgr {
	/**
	 * Add an Detector to repository
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void addDetector(Detector detector);
		
	/**
	 * Return an Detector from repository 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public Detector getDetector(String label);
	
	
	/**
	 * Delete an  Detector from repository 
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void removeDetector(String label);
	
}
