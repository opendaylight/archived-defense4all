/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev 
 * @version 0.1
 */
package org.opendaylight.defense4all.restservice;

import java.util.Hashtable;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.Repo;

public class AmsResource {

	public enum AMSResourceStatus {
		OK,
		CONFLICT,
		SERVER_ERROR
	}
	
	private static Log log = LogFactory.getLog(AmsResource.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String amsLabel;
	
	public AmsResource(UriInfo uriInfo, Request request, String amsLabel) {
		this.uriInfo = uriInfo;	this.request = request;	this.amsLabel = amsLabel;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public AMS getAms() throws ExceptionControlApp {

		try {
			log.debug("In getAms. ams label is " + amsLabel);
			Repo<String> amsRepo = DFHolder.get().amsRepo;
			log.debug("In getAms. amsRepo is " + amsRepo);
			Hashtable<String,Object> amsRow = amsRepo.getRow(amsLabel);
			if(amsRow == null) return null;
			return new AMS(amsRow);
		} catch (Throwable e) {
			log.error("Failed to retrieve pn " + amsLabel, e);			
			return null;
		}
	}

	@DELETE
	public AMSResourceStatus deleteAms() {

		try {
			log.debug("DeleteAms: invoked");
			boolean success = DFHolder.get().getMgmtPoint().removeAMS(amsLabel);
			return success ? AMSResourceStatus.OK : AMSResourceStatus.CONFLICT;
		} catch (ExceptionControlApp e) {
			log.error("Failed to delete AMS " + amsLabel, e);
			return AMSResourceStatus.SERVER_ERROR;		
		}
	}	
}
