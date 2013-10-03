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
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.framework.core.Repo;


@Path("/df")
public class Defense4allRestService {

	private static Log log = LogFactory.getLog(Defense4allRestService.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Path("ofcs")
	@Produces(MediaType.APPLICATION_JSON)
	public Hashtable<String,Hashtable<String,Object>> getOFCs() {
		log.debug("getOFCs: Invoked");
		Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
		Hashtable<String,Hashtable<String,Object>> res = oFCsRepo.getTable();
		return res;
	}
	
	@GET
	@Path("dps")
	@Produces(MediaType.APPLICATION_JSON)
	public Hashtable<String,Hashtable<String,Object>> getDPs() {
		log.debug("getDPs: Invoked");
		Repo<String> dPsRepo = DFHolder.get().amsRepo;
		Hashtable<String,Hashtable<String,Object>> res = dPsRepo.getTable();
		return res;
	}
	
	@GET
	@Path("pns")
	@Produces(MediaType.APPLICATION_JSON)
	public Hashtable<String,Hashtable<String,Object>> getPNs() {
		log.debug("getPNs: Invoked");
		Repo<String> pNsRepo = DFHolder.get().pNsRepo;
		Hashtable<String,Hashtable<String,Object>> res = pNsRepo.getTable();
		return res;
	}

	@GET
	@Path("ofcs/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOFCsCount() {
		log.debug("getOFCsCount: invoked");
		Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
		int count = oFCsRepo.getKeys().size();
		return String.valueOf(count);
	}

	@GET
	@Path("dps/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDPsCount() {
		log.debug("getDPsCount: invoked");
		Repo<String> dPsRepo = DFHolder.get().amsRepo;
		int count = dPsRepo.getKeys().size();
		return String.valueOf(count);
	}

	@GET
	@Path("pns/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPNsCount() {
		log.debug("getPNsCount: invoked");
		Repo<String> pNsRepo = DFHolder.get().pNsRepo;
		int count = pNsRepo.getKeys().size();
		return String.valueOf(count);
	}

	@POST
	@Path("ofcs")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addOFC(OFC ofc, @Context HttpServletResponse servletResponse) throws Exception {
		log.debug("addOFC: invoked");
		Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
		
		if (oFCsRepo.getRow(ofc.hostname) != null) {
			log.debug("addOFC: already contains " + ofc.hostname);
			servletResponse.sendError(400);
		} else {
			log.debug("addOFC: adding " + ofc.hostname);
			DFHolder.get().getMgmtPoint().addOFC(ofc);
		}
	}

	@POST
	@Path("pns")
	@Consumes(MediaType.APPLICATION_JSON)
	//public void addPN(PN pn, @Context HttpServletResponse servletResponse) throws Exception {
	public void addPN(Hashtable<String,Object> pnRow, @Context HttpServletResponse servletResponse) throws Exception {
		log.debug("addPN: invoked");
		Repo<String> pNsRepo = DFHolder.get().pNsRepo;
		PN pn = new PN (pnRow );
		if (pNsRepo.getRow(pn.label) != null) {
			log.debug("addPN: already contains " + pn.label);
			servletResponse.sendError(400);
		} else {
			log.debug("addPN: adding " + pn.label);
			DFHolder.get().getMgmtPoint().addPN(pn);
		}
	}

	@Path("ofcs/{ofc}")
	public OFCResource getOFC(@PathParam("ofc") String ofcLabel) {
		return new OFCResource(uriInfo, request, ofcLabel);
	}

	@Path("pns/{pn}")
	public PNResource getPN(@PathParam("pn") String pnLabel) {
		return new PNResource(uriInfo, request, pnLabel);
	}
}
