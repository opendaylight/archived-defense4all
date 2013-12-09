/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Kobi Samoray 
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.restservice;

import java.util.Hashtable;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.Repo;


public class PNResource {

	private static Log log = LogFactory.getLog(PNResource.class);

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String pnLabel;

	public PNResource(UriInfo uriInfo, Request request, String pnLabel) {
		this.uriInfo = uriInfo;	this.request = request;	this.pnLabel = pnLabel;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PN getPN() {

		try {
			log.debug("In getPN. PN label is " + pnLabel);
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			Hashtable<String,Object> pnRow = pNsRepo.getRow(pnLabel);
			return new PN(pnRow);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve pn " + pnLabel, e);
			return null;
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPN(JAXBElement<PN> jaxbPN) {
		
		try {
			Response res;
			PN pn = jaxbPN.getValue();
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			if (pNsRepo.getRow(pnLabel) != null) {
				res = Response.noContent().build();
			} else {
				res = Response.created(uriInfo.getAbsolutePath()).build();
				DFHolder.get().getMgmtPoint().changePN(pn);
			}
			return res;
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve pn " + pnLabel, e);
			return Response.serverError().build();
		}
	}

	@DELETE
	public void deletePN() {		
		try {
			log.debug("DeletePN: invoked");
			DFHolder.get().getMgmtPoint().removePN(pnLabel);
		} catch (ExceptionControlApp e) {/* Ignore. Already logged in DFMgmtPoint. */}
	}
}