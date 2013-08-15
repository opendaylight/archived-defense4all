/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.restservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opendaylight.controlapps.framework.core.Direction;
import org.opendaylight.controlapps.framework.core.FMHolder;


@Path("/directions")
public class FrameworkRestService {

	private static Log log = LogFactory.getLog(FrameworkRestService.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Direction> getDirections() {
		log.debug("getDirections: Invoked");
		Properties directions = FMHolder.get().getFrameworkMgmtPoint().getDirections();
		Iterator<Entry<Object, Object>> iter = directions.entrySet().iterator(); 
		List<Direction> res =  new ArrayList<Direction>();
		Entry<Object,Object> entry;
		while(iter.hasNext()) {
			entry = iter.next();
			res.add(new Direction((String) (entry.getKey()), (String) (entry.getValue())));
		}
		return res;
	}

	@GET
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getCount() {
		log.debug("getCount: invoked");
		int count = FMHolder.get().getFrameworkMgmtPoint().getDirections().size();
		return String.valueOf(count);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
	public void newDirection(Direction direction, @Context HttpServletResponse servletResponse) throws Exception {
		log.debug("newDirection: invoked");
		Properties directions = FMHolder.get().getFrameworkMgmtPoint().getDirections();
		if (directions.containsKey(direction.key)) {
			log.debug("getCount: already contains " + direction);
			servletResponse.sendError(400);
		} else {
			log.debug("getCount: putting " + direction);
			directions.put(direction.key, direction.value);
		}
	}

	@Path("{direction}")
	public DirectionResource getDirection(@PathParam("direction") String direction) {
		return new DirectionResource(uriInfo, request, direction);
	}
}
