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
			return new AMS(amsRow);
		} catch (Throwable e) {
			log.error("Failed to retrieve pn " + amsLabel, e);			
			return null;
		}
	}

	@DELETE
	public void deleteAms() {
		
		try {
			log.debug("DeleteAms: invoked");
			DFHolder.get().getMgmtPoint().removeAMS(amsLabel);
		} catch (ExceptionControlApp e) {{/* Ignore. Already logged in DFMgmtPoint. */}}
	}
	
}
