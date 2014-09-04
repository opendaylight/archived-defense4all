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
import org.opendaylight.defense4all.core.PO;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.Repo;

public class POResource {

	private static Log log = LogFactory.getLog(POResource.class);

	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String poLabel;

	public POResource(UriInfo uriInfo, Request request, String poLabel) {
		this.uriInfo = uriInfo;	this.request = request;	this.poLabel = poLabel;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public PO getPO() {

		try {
			log.debug("In getPO. PO label is " + poLabel);
			Hashtable<String,Object> poRow = DFHolder.get().posRepo.getRow(poLabel);
			if(poRow == null) return null;
			return new PO(poRow);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve po " + poLabel, e);
			return null;
		}
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putPO(JAXBElement<PO> jaxbPO) {
		
		try {
			Response res;
			PO po = jaxbPO.getValue();
			Repo<String> posRepo = DFHolder.get().posRepo;
			if (posRepo.getRow(poLabel) != null) {
				res = Response.noContent().build();
			} else {
				res = Response.created(uriInfo.getAbsolutePath()).build();
				DFHolder.get().posRepo.setRow(po.label, po.toRow());
			}
			return res;
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve po " + poLabel, e);
			return Response.serverError().build();
		}
	}

	@DELETE
	public void deletePO() {		
		try {
			log.debug("DeletePO: invoked");
			DFHolder.get().posRepo.deleteRow(poLabel);
		} catch (ExceptionControlApp e) {
			log.error("Failed to delete po " + poLabel, e);
		}
	}
}