package org.opendaylight.defense4all.restservice;

import java.util.Hashtable;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.framework.core.Repo;

public class MitigationResource {

	private static Log log = LogFactory.getLog(MitigationResource.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String mitigationLabel;
	
	public MitigationResource(UriInfo uriInfo, Request request,	String mitigationLabel) {
		this.uriInfo = uriInfo;	this.request = request;	this.mitigationLabel = mitigationLabel;	
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Mitigation getMitigation() {

		try {
			log.debug("In getnetMitigation. Mitigation label is " + mitigationLabel);
			Repo<String> mitigationsRepo = DFHolder.get().mitigationsRepo;
			Hashtable<String,Object> mitigationsRow = mitigationsRepo.getRow(mitigationLabel);
			return new Mitigation(mitigationsRow);
		} catch (Exception e) {
			log.error("Failed to retrieve mitigation " + mitigationLabel, e);
			return null;
		}
	}

}
