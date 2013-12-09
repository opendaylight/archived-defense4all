/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.odl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author gerag
 *
 */
public class OdlOFC extends OFC {

	static Logger log = LoggerFactory.getLogger(OdlOFC.class);
	
	/* Additional OFC repo columns */
	public static final String RESTPATH_CONTROLLER_NAME = "restpath_controller_name"; 

	public String restpathControllerName = null;

	public OdlOFC() {
		super();
		restpathControllerName = Odl.DEFAULT_RESTPATH_CONTROLLER_NAME;
	}

	/* (non-Javadoc)
	 * @see org.opendaylight.defense4all.core.OFC#toRow()
	 */
	public OdlOFC(Hashtable<String, Object> ofcRow) throws ExceptionControlApp {

		super(ofcRow);
		try {
			restpathControllerName = (String) ofcRow.get(RESTPATH_CONTROLLER_NAME);
			if(restpathControllerName != null && ! restpathControllerName.isEmpty()) return;

			/* Not in row. Check if it is set in props. */
			Properties props = (Properties) ofcRow.get(OFC.PROPS);
			if(props != null) restpathControllerName = props.getProperty(RESTPATH_CONTROLLER_NAME);
			if(restpathControllerName != null && ! restpathControllerName.isEmpty()) return;

			/* Not in props. Use default */
			restpathControllerName = Odl.DEFAULT_RESTPATH_CONTROLLER_NAME;
		} catch(Throwable e) {
			log.error("Excepted trying to inflate OdlFlowConfigInfo from row. " + e.getLocalizedMessage());
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Excepted trying to inflate OdlFlowConfigInfo from row. ", e);
		}
	}

	/* (non-Javadoc)
	 * @see org.opendaylight.defense4all.core.OFC#toRow()
	 */
	@Override
	public Hashtable<String, Object> toRow() {

		Hashtable<String, Object> row = super.toRow();
		if(restpathControllerName == null) restpathControllerName = "";
		row.put(RESTPATH_CONTROLLER_NAME, restpathControllerName);
		return row;
	}
	
	public List<String> getExtraCells() {

		List<String> cells = new ArrayList<String>();
		cells.add(RESTPATH_CONTROLLER_NAME);
		return cells;
	}	
}
