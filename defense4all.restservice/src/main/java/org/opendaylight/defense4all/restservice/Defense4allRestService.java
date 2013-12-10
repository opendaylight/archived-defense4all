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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.Repo;


@Path("/df")
public class Defense4allRestService {

	private static Log log = LogFactory.getLog(Defense4allRestService.class);
	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	
	@GET
	@Path("pns")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PN> getPNs() {
		ArrayList<PN> pnList = new ArrayList<PN>();
		try {
			log.debug("getPNs: Invoked");
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			Hashtable<String,Hashtable<String,Object>> res = pNsRepo.getTable();
			Collection<Hashtable<String, Object>> pnResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = pnResList.iterator(); i.hasNext();)
				pnList.add(new PN(i.next()));
						
			return pnList;
		} catch (Exception e) {
			log.error("Failed to retrieve pns. ", e);
			return pnList;
		}
	}
	
	@GET
	@Path("pns/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPNsCount() {
		
		try {
			log.debug("getPNsCount: invoked");
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			int count = pNsRepo.getKeys().size();
			return String.valueOf(count);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve PNs count");
			return null;
		}
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pns/{pn}")
	public PN getPN(@PathParam("pn") String pnLabel) {
		PNResource pnResource = new PNResource(uriInfo, request, pnLabel);
		return pnResource.getPN();
	}
	
	@POST
	@Path("pns")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addPN(PN pn, @Context HttpServletResponse servletResponse) throws Exception {	
		try {
			log.debug("addPN: invoked");			
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			if (pNsRepo.getRow(pn.label) != null) {
				log.debug("addPN: already contains " + pn.label);
				servletResponse.sendError(400, "addPN: already contains " + pn.label);
			} else {
				log.debug("addPN: adding " + pn.label);
				DFHolder.get().getMgmtPoint().addPN(pn);
			}
		} catch (Throwable e) {
			log.error("Failed to add PN " + pn.label);
			try {
				servletResponse.sendError(500, "Failed to add PN " + pn.label + ", " + e.getMessage());
			} catch (IOException e1) {
				log.error(e1);
			}
		}
	}

	@DELETE
	@Path("pns/{pn}")
	public void deletePn(@PathParam("pn") String pnLabel) {
		PNResource pnResource = new PNResource(uriInfo, request, pnLabel);
		pnResource.deletePN();
	}
	
	@GET
	@Path("amss")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AMS> getAMSs() {
		ArrayList<AMS> amsList = new ArrayList<AMS>();
		try {
			log.debug("getDPs: Invoked");
			Repo<String> amsRepo = DFHolder.get().amsRepo;
			Hashtable<String,Hashtable<String,Object>> res = amsRepo.getTable();
			Collection<Hashtable<String, Object>> amsResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = amsResList.iterator(); i.hasNext();)
				amsList.add(new AMS(i.next()));			
		} catch (Exception e) {
			log.error("Failed to retrieve amss. ", e);
		}
		return amsList;
	}
	
	@GET
	@Path("amss/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDPsCount() {
		
		try {
			log.debug("getDPsCount: invoked");
			Repo<String> dPsRepo = DFHolder.get().amsRepo;
			int count = dPsRepo.getKeys().size();
			return String.valueOf(count);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve AMSs count");
			return null;
		}
	}

	@GET
	@Path("amss/{ams}")
	@Produces(MediaType.APPLICATION_JSON)
	public AMS getAMS(@PathParam("ams") String amsLabel) {
		log.debug("GgetAMS : " + amsLabel);
		try {
			AmsResource amsResource = new AmsResource(uriInfo, request, amsLabel);			
			return amsResource.getAms();			
		} catch (Throwable e) {
			return new AMS();
		}
	}
	
	@POST
	@Path("amss")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAMS(AMS ams, @Context HttpServletResponse servletResponse) {
		try {
			log.debug("addAMS: invoked 123");
			Repo<String> amsRepo = DFHolder.get().amsRepo;
			if (amsRepo.getRow(ams.getLabel()) != null){
				log.debug("addAMS: already contains " + ams.getLabel());
				servletResponse.sendError(400, "addAMS: already contains " + ams.getLabel());
			} else {
				log.debug("addAms: adding " + ams.label);
				DFHolder.get().getMgmtPoint().addAMS(ams);
				
			}
		} catch (Throwable e) {
			log.error("Failed to add AMS " );
			try {
				servletResponse.sendError(500, e.getMessage());
			} catch (IOException e1) {
				log.error(e);
			}
		}
		
	}
	
	@DELETE
	@Path("amss/{ams}")
	public void deleteAms(@PathParam("ams") String amsLabel) {
		AmsResource amsResource = new AmsResource(uriInfo, request, amsLabel);
		amsResource.deleteAms();
	}

	@GET
	@Path("ofcs")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<OFC> getOFCs() {
		ArrayList<OFC> ofcList = new ArrayList<OFC>();
		try {
			Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
			Hashtable<String,Hashtable<String,Object>> res = oFCsRepo.getTable();
			Collection<Hashtable<String, Object>> ofcResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = ofcResList.iterator(); i.hasNext();)
				ofcList.add(new OFC(i.next()));				
		} catch (Exception e) {
			log.error("Failed to retrieve ofcs. ", e);
			return null;
		}
		return ofcList;
	}
	
	@GET
	@Path("ofcs/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOFCsCount() {
		
		try {
			log.debug("getOFCsCount: invoked");
			Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
			int count = oFCsRepo.getKeys().size();
			return String.valueOf(count);
		} catch (ExceptionControlApp e) {
			log.error("Failed to retrieve OFCs count");
			return null;
		}
	}
	
	@GET
	@Path("ofcs/{ofc}")
	@Produces(MediaType.APPLICATION_JSON)
	public OFC getOFC(@PathParam("ofc") String ofcLabel) {
		OFCResource ofcResource = new OFCResource(uriInfo, request, ofcLabel);
		return ofcResource.getOFC();			

	}
	
	@SuppressWarnings("unchecked")
	@POST
	@Path("ofcs")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addOFC(OFC ofc, @Context HttpServletResponse servletResponse) {
		try {
			log.debug("addOFC: invoked");
			Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;			
			if (oFCsRepo.getRow(ofc.hostname) != null) {
				log.debug("addOFC: already contains " + ofc.hostname);
				servletResponse.sendError(400, "addOFC: already contains " + ofc.hostname);
			} else {
				log.debug("addOFC: adding " + ofc.hostname);
				DFHolder.get().getMgmtPoint().addOFC(ofc);
			}
		} catch (Throwable e) {
			log.error("Failed to add OFC " + ofc.hostname);
			try {
				servletResponse.sendError(500, "Failed to add OFC " + ofc.hostname);
			} catch (IOException e1) {
				log.error(e1);
			}
		}
		
	}

	@DELETE
	@Path("ofcs/{ofc}")
	public void deleteOfc(@PathParam("ofc") String ofcLabel) {
		OFCResource ofcResource = new OFCResource(uriInfo, request, ofcLabel);
		ofcResource.deleteOFC();
	}
	
	
	@GET
	@Path("netnodes")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<NetNode> getNetnode() {
		ArrayList<NetNode> netNodeList = new ArrayList<NetNode>();
		try {
			log.debug("getNetnode: Invoked");
			Repo<String> netNodesRepo = DFHolder.get().netNodesRepo;
			Hashtable<String,Hashtable<String,Object>> res = netNodesRepo.getTable();
			Collection<Hashtable<String, Object>> netNodeResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = netNodeResList.iterator(); i.hasNext();)
				netNodeList.add(new NetNode(i.next()));		
			return netNodeList;
		} catch (Exception e) {
			log.error("Failed to retrieve netnode. ", e);
			return null;
		}
	}
	
	@GET
	@Path("netnodes/{netnode}")
	public NetNode getNetNode(@PathParam("netnode") String netNodeLabel) {
		NetNodeResource netNodeResource = new NetNodeResource(uriInfo, request, netNodeLabel);
		return netNodeResource.getNetNode();
	}
	
	@POST
	@Path("netnodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNetNode(NetNode netNode, @Context HttpServletResponse servletResponse) throws Exception {	
		try {
			log.debug("addNetNode: invoked");			
			Repo<String> netNodeRepo = DFHolder.get().netNodesRepo;
			if (netNodeRepo.getRow(netNode.label) != null) {
				log.debug("addNetNode: already contains " + netNode.label);
				servletResponse.sendError(400, "addNetNode: already contains " + netNode.label );
			} else {
				log.debug("addNetNode: adding " + netNode.label);
				DFHolder.get().getMgmtPoint().addNetNode(netNode);
			}
		} catch (Throwable e) {
			log.error("Failed to add NetNode " + netNode.label);
			try {
				servletResponse.sendError(500, "Failed to add NetNode " + netNode.label + ", " + e.getMessage());
			} catch (IOException e1) {
				log.error(e1);
			}
		}
	}
	
	@DELETE
	@Path("netnodes/{netnode}")
	public void deleteNetNode(@PathParam("netnode") String netNodeLabel) {
		NetNodeResource netNodeResource = new NetNodeResource(uriInfo, request, netNodeLabel);
		netNodeResource.deleteNetNode();
	}
		
	@GET
	@Path("mitigations/{mitigation}")
	public Mitigation getMitigation(@PathParam("mitigation") String mitigationLabel) {
		MitigationResource mitigationResource =  new MitigationResource(uriInfo, request, mitigationLabel);
		return mitigationResource.getMitigation();
	}
	
	@GET
	@Path("mitigations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Mitigation> getMitigations() {
		ArrayList<Mitigation> mitigationsList = new ArrayList<Mitigation>();
		try {
			Repo<String> mitigationsRepo = DFHolder.get().mitigationsRepo;
			Hashtable<String,Hashtable<String,Object>> res = mitigationsRepo.getTable();
			Collection<Hashtable<String, Object>> mitigationsResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = mitigationsResList.iterator(); i.hasNext();)
				mitigationsList.add(new Mitigation(i.next()));				
		} catch (Exception e) {
			log.error("Failed to retrieve ofcs. ", e);
			return null;
		}
		return mitigationsList;
	}
		
	@GET
	@Path("attacks")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Attack> getAttacs() {
		ArrayList<Attack> attackList = new ArrayList<Attack>();
		try {
			log.debug("getAttacs: Invoked");
			Repo<String> attacksRepo = DFHolder.get().attacksRepo;
			Hashtable<String,Hashtable<String,Object>> res = attacksRepo.getTable();
			Collection<Hashtable<String, Object>> attackResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = attackResList.iterator(); i.hasNext();)
				attackList.add(new Attack(i.next()));			
		} catch (Exception e) {
			log.error("Failed to retrieve attacs. ", e);
			return null;
		}
		return attackList;
	}
	
	@GET
	@Path("attacks/{attack}")
	public AttacksResource getAttacs(@PathParam("attac") String attacLabel) {
		return new AttacksResource(uriInfo, request, attacLabel);
	}
}
