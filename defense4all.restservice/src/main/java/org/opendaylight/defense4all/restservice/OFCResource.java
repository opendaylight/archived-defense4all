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

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.framework.core.Repo;


public class OFCResource {

	private static Log log = LogFactory.getLog(OFCResource.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String ofcLabel;

	public OFCResource(UriInfo uriInfo, Request request, String ofcLabel) {
		this.uriInfo = uriInfo;	this.request = request;	this.ofcLabel = ofcLabel;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public OFC getOFC() {		
		log.debug("In getOFC. OFC label is " + ofcLabel);

		Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
		Hashtable<String,Object> ofcRow = oFCsRepo.getRow(ofcLabel);
		return new OFC(ofcRow);
	}

	@DELETE
	public void deleteOFC() {		
		log.debug("DeleteOFC: invoked");
		DFHolder.get().getMgmtPoint().removeOFC(ofcLabel);
	}
}