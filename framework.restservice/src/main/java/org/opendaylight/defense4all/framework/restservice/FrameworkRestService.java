/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.restservice;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

@Path("/")
public class FrameworkRestService {

	private static Logger log = LoggerFactory.getLogger("org.opendaylight.defense4all.restservice");

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("hostaddress")
	@Produces(MediaType.APPLICATION_JSON)
	public String getHostAddress(@Context HttpServletResponse servletResponse) {

		log.debug("getHostaddress: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}

		String hostaddress = FMHolder.get().getHostAddr();
		return hostaddress;
	}

	@POST
	@Path("hostaddress")
	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces(MediaType.APPLICATION_JSON)
	public void setHostAddress(String hostaddress, @Context HttpServletResponse servletResponse) throws Exception {

		log.debug("setHostAddress: invoked");

		if ( ! FMHolder.get().isOpenForBusiness() ) {
			servletResponse.sendError(503, "Service is unavailable" ); 
			return ;
		}

		if (hostaddress == null || hostaddress.isEmpty()) {
			log.debug("setHostaddress: hostaddress is null or empty.");
			servletResponse.sendError(400);
		}
		try {
			FMHolder.get().getFrameworkMgmtPoint().setHostAddr(hostaddress);
		} catch ( Throwable e) {  
			log.error("Failed to set hostaddress - " + e.getLocalizedMessage());
			servletResponse.sendError(400);
		}
	}

	@POST
	@Path("terminate")
	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces(MediaType.APPLICATION_JSON)
	public void terminate(String scope, @Context HttpServletResponse servletResponse) throws Exception {

		log.debug("terminate: invoked");
		if (scope == null || scope.isEmpty())
			scope = "this";
		try {
			FMHolder.get().getFrameworkMgmtPoint().requestTerminate(scope);
		} catch ( Throwable e) {  
			log.error("Failed to initiate termination - " + e.getLocalizedMessage());
			servletResponse.sendError(400);
		}
	}

	@POST
	@Path("reset")
	@Consumes(MediaType.APPLICATION_JSON)
	//	@Produces(MediaType.APPLICATION_JSON)
	public void reset(String levelStr, @Context HttpServletResponse servletResponse) throws Exception {

		log.debug("reset: invoked");
		ResetLevel level = ResetLevel.valueOf(levelStr, ResetLevel.soft); // If invalid or none - 'soft' assumed
		try {
			FMHolder.get().getFrameworkMgmtPoint().requestReset(level);
		} catch ( Throwable e) {  
			log.error("Failed to initiate reset - " + e.getLocalizedMessage());
			servletResponse.sendError(400);
		}
	}
}
