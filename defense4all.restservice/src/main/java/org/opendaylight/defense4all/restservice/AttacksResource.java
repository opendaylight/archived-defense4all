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
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.Repo;

public class AttacksResource {
	
	private static Log log = LogFactory.getLog(AttacksResource.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String attackLabel;
	
	public AttacksResource(UriInfo uriInfo, Request request, String attacLabel) {
		this.uriInfo = uriInfo;	this.request = request;	this.attackLabel = attacLabel;	
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Attack getAttack() {

		try {
			log.debug("In getnetNode. attac label is " + attackLabel);
			Repo<String> attacksRepo = DFHolder.get().attacksRepo;
			Hashtable<String,Object> attackRow = attacksRepo.getRow(attackLabel);
			return new Attack(attackRow);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve pn " + attackLabel, e);
			return null;
		}
	}

}
