/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */package org.opendaylight.defense4all.framework.restservice;

import java.util.Properties;

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
import org.opendaylight.defense4all.framework.core.Direction;
import org.opendaylight.defense4all.framework.core.FMHolder;

public class DirectionResource {

	private static Log log = LogFactory.getLog(DirectionResource.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String key;

	public DirectionResource(UriInfo uriInfo, Request request, String key) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.key = key;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getDirection() {		
		log.debug("In getDirection. Direction key is " + key);
		
		return FMHolder.get().getFrameworkMgmtPoint().getDirections().getProperty(key);
	}

	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response putDirection(JAXBElement<Direction> jaxbDirection) {
		Response res;
		Direction direction = jaxbDirection.getValue();
		Properties directions = FMHolder.get().getFrameworkMgmtPoint().getDirections();
		String value = directions.getProperty(key);
		if (value != null) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
			directions.put(direction.getKey(), direction.getValue());
		}
		return res;
	}

	@DELETE
	public void deleteDirection() {		
		log.debug("DeleteDirection: invoked");
		FMHolder.get().getFrameworkMgmtPoint().getDirections().remove(key);
	}
}