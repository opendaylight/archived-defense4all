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
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.NotSupportedException;
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

import org.opendaylight.defense4all.core.AMS;
import org.opendaylight.defense4all.core.Attack;
import org.opendaylight.defense4all.core.DFHolder;
import org.opendaylight.defense4all.core.Mitigation;
import org.opendaylight.defense4all.core.NetNode;
import org.opendaylight.defense4all.core.OFC;
import org.opendaylight.defense4all.core.PN;
import org.opendaylight.defense4all.core.PO;
import org.opendaylight.defense4all.core.interactionstructures.PNStatReport;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.restservice.AmsResource.AMSResourceStatus;
import org.opendaylight.defense4all.restservice.NetNodeResource.NetNodeResourceStatus;
import org.opendaylight.defense4all.restservice.PNResource.PNResourceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/df")
public class Defense4allRestService {

	private static Logger log = LoggerFactory.getLogger("org.opendaylight.defense4all.restservice");

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("pos")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PO> getPOs(@Context HttpServletResponse servletResponse) {
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}

		ArrayList<PO> poList = new ArrayList<PO>();
		try {
			log.debug("getPOs: Invoked");
			Repo<String> posRepo = DFHolder.get().posRepo;
			Hashtable<String,Hashtable<String,Object>> res = posRepo.getTable();
			if(res == null) return poList;
			Collection<Hashtable<String, Object>> poResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = poResList.iterator(); i.hasNext();)
				poList.add(new PO(i.next()));						
			return poList;
		} catch (Throwable e) {
			log.error("Failed to retrieve pos. ", e);
			return poList;
		}
	}

	@GET
	@Path("pos/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPOsCount(@Context HttpServletResponse servletResponse) {
		log.debug("getPOsCount: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {

			Repo<String> posRepo = DFHolder.get().posRepo;
			int count = posRepo.getKeys().size();
			return String.valueOf(count);
		} catch (Throwable e) {
			log.error("Failed to retrieve POs count");
			return null;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pos/{po}")
	public PO getPO(@PathParam("po") String poLabel, @Context HttpServletResponse servletResponse) {
		log.debug("getPO: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		POResource poResource = new POResource(uriInfo, request, poLabel);
		return poResource.getPO();
	}

	@POST
	@Path("pos")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addPO(PO po, @Context HttpServletResponse servletResponse) throws Exception {
		log.debug("addPO: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return;
		}
		try {
			if (DFHolder.get().posRepo.getRow(po.label) != null) {
				log.debug("addPO: already contains " + po.label);
				servletResponse.sendError(400, "addPO: already contains " + po.label);
			} else {
				log.debug("addPO: adding " + po.label);
				DFHolder.get().posRepo.setRow(po.label, po.toRow());
			}
		} catch (Throwable e) {
			log.error("Failed to add PO " + po.label);
			try {
				servletResponse.sendError(500, "Failed to add PO " + po.label);
			} catch (IOException e1) {
				log.error("Error in response "+e);
			}
		}
	}

	@DELETE
	@Path("pos/{po}")
	public void deletePO(@PathParam("po") String poLabel, @Context HttpServletResponse servletResponse) {
		log.debug("deletePO: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return;
		}
		POResource poResource = new POResource(uriInfo, request, poLabel);
		poResource.deletePO();
	}


	@GET
	@Path("pns")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<PN> getPNs(@Context HttpServletResponse servletResponse) {
		log.debug("getPNs: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		ArrayList<PN> pnList = new ArrayList<PN>();
		try {		
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			Hashtable<String,Hashtable<String,Object>> res = pNsRepo.getTable();
			if(res == null) return pnList;
			Collection<Hashtable<String, Object>> pnResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = pnResList.iterator(); i.hasNext();)
				pnList.add(new PN(i.next()));						
			return pnList;
		} catch (Throwable e) {
			log.error("Failed to retrieve pns. ", e);
			return pnList;
		}
	}

	@GET
	@Path("pns/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getPNsCount(@Context HttpServletResponse servletResponse) {
		log.debug("getPNsCount: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			Repo<String> pNsRepo = DFHolder.get().pNsRepo;
			int count = pNsRepo.getKeys().size();
			return String.valueOf(count);
		} catch (Throwable e) {
			log.error("Failed to retrieve PNs count");
			return null;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pns/{pn}")
	public PN getPN(@PathParam("pn") String pnLabel, @Context HttpServletResponse servletResponse) {
		log.debug("getPN: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		PNResource pnResource = new PNResource(uriInfo, request, pnLabel);
		return pnResource.getPN();
	}

	@POST
	@Path("pns")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addPN(PN pn, @Context HttpServletResponse servletResponse) throws Exception {
		log.debug("addPN: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		try {
			DFHolder.get().getMgmtPoint().addPN(pn);
		} catch (IllegalStateException e1) {
			try {
				servletResponse.sendError(409, "Failed to add PN " + pn.label);
			} catch (IOException e2) {
				log.error("Error in response "+e2);
			}
		} catch (Throwable e) {
			log.error("Failed to add PN " + pn.label);
			try {
				servletResponse.sendError(500, "Failed to add PN " + pn.label);
			} catch (IOException e3) {
				log.error("Error in response "+e3);
			}
		}
	}

	@DELETE
	@Path("pns/{pn}")
	public void deletePn(@PathParam("pn") String pnLabel, @Context HttpServletResponse servletResponse) {
		log.debug("deletePn: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return;
		}
		PNResource pnResource = new PNResource(uriInfo, request, pnLabel);
		PNResourceStatus status = pnResource.deletePN();
		int errorCode = 0;
		switch (status) {
		case OK:
			errorCode = 0;
			break;
		case CONFLICT:
			errorCode = 409;
			break;
		case SERVER_ERROR:
			errorCode = 500;
			break;
		}
		if(errorCode != 0) {
			try {
				servletResponse.sendError(errorCode, "Failed to remove PN " + pnLabel);
			} catch (IOException e) {/* Ignore */}
		}
	}	

	@GET
	@Path("amss")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<AMS> getAMSs(@Context HttpServletResponse servletResponse) {
		log.debug("getAMSs: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		ArrayList<AMS> amsList = new ArrayList<AMS>();
		try {
			Repo<String> amsRepo = DFHolder.get().amsRepo;
			Hashtable<String,Hashtable<String,Object>> res = amsRepo.getTable();
			if(res == null) return amsList;
			Collection<Hashtable<String, Object>> amsResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = amsResList.iterator(); i.hasNext();)
				amsList.add(new AMS(i.next()));			
		} catch (Throwable e) {
			log.error("Failed to retrieve amss. ", e);
		}
		return amsList;
	}

	@GET
	@Path("amss/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDPsCount(@Context HttpServletResponse servletResponse) {
		log.debug("getDPsCount: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			Repo<String> dPsRepo = DFHolder.get().amsRepo;
			int count = dPsRepo.getKeys().size();
			return String.valueOf(count);
		} catch (Throwable e) {
			log.error("Failed to retrieve AMSs count");
			return null;
		}
	}

	@GET
	@Path("amss/{ams}")
	@Produces(MediaType.APPLICATION_JSON)
	public AMS getAMS(@PathParam("ams") String amsLabel, @Context HttpServletResponse servletResponse) {
		log.debug("getAMS: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
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
		log.debug("addAMS: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		try {
			DFHolder.get().getMgmtPoint().addAMS(ams);
		} catch (IllegalStateException e1) {
			try {
				servletResponse.sendError(409, "Failed to add AMS " + ams.label);
			} catch (IOException e2) {
				log.error("Error in response "+e2);
			}
		} catch (Throwable e) {
			log.error("Failed to add AMS " + ams.label);
			try {
				servletResponse.sendError(500, "Failed to add AMS " + ams.label);
			} catch (IOException e3) {
				log.error("Error in response "+e3);
			}
		}
	}

	@DELETE
	@Path("amss/{ams}")
	public void deleteAms(@PathParam("ams") String amsLabel, @Context HttpServletResponse servletResponse) {
		log.debug("deleteAms: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		AmsResource amsResource = new AmsResource(uriInfo, request, amsLabel);
		AMSResourceStatus status = amsResource.deleteAms();
		int errorCode = 0;
		switch (status) {
		case OK:
			errorCode = 0;
			break;
		case CONFLICT:
			errorCode = 409;
			break;
		case SERVER_ERROR:
			errorCode = 500;
			break;
		}
		if(errorCode != 0) {
			try {
				servletResponse.sendError(errorCode, "Failed to remove AMS " + amsLabel);
			} catch (IOException e) {/* Ignore */}
		}
	}

	@GET
	@Path("ofcs")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<OFC> getOFCs(@Context HttpServletResponse servletResponse) {
		log.debug("getOFCs: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		ArrayList<OFC> ofcList = new ArrayList<OFC>();
		try {
			Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
			Hashtable<String,Hashtable<String,Object>> res = oFCsRepo.getTable();
			if(res == null) return ofcList;
			Collection<Hashtable<String, Object>> ofcResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = ofcResList.iterator(); i.hasNext();)
				ofcList.add(new OFC(i.next()));				
		} catch (Throwable e) {
			log.error("Failed to retrieve ofcs. ", e);
			return null;
		}
		return ofcList;
	}

	@GET
	@Path("ofcs/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getOFCsCount(@Context HttpServletResponse servletResponse) {
		log.debug("getOFCsCount: invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			log.debug("getOFCsCount: invoked");
			Repo<String> oFCsRepo = DFHolder.get().oFCsRepo;
			int count = oFCsRepo.getKeys().size();
			return String.valueOf(count);
		} catch (Throwable e) {
			log.error("Failed to retrieve OFCs count");
			return null;
		}
	}

	@GET
	@Path("ofcs/{ofc}")
	@Produces(MediaType.APPLICATION_JSON)
	public OFC getOFC(@PathParam("ofc") String ofcLabel, @Context HttpServletResponse servletResponse) {
		log.debug("getOFC : " + ofcLabel);
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			OFCResource ofcResource = new OFCResource(uriInfo, request, ofcLabel);
			return ofcResource.getOFC();			
		} catch (Throwable e) {
			return new OFC();
		}
	}

	@POST
	@Path("ofcs")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addOFC(OFC ofc, @Context HttpServletResponse servletResponse) {
		log.debug("addOFC : invoked " );
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		try {
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
				log.error("Error in response "+e1);
			}
		}
	}

	@DELETE
	@Path("ofcs/{ofc}")
	public void deleteOfc(@PathParam("ofc") String ofcLabel, @Context HttpServletResponse servletResponse) {
		log.debug("deleteOfc : invoked " );
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		try {
			if ( ! FMHolder.get().isOpenForBusiness() )
				servletResponse.sendError(503, "Service is unavailable" ); 
		} catch (Throwable e) {
			log.error("Error in response "+e);
			return;
		}
		OFCResource ofcResource = new OFCResource(uriInfo, request, ofcLabel);
		ofcResource.deleteOFC();
	}


	@GET
	@Path("netnodes")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<NetNode> getNetnodes(@Context HttpServletResponse servletResponse)  {
		log.debug("getNetnode: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}

		ArrayList<NetNode> netNodeList = new ArrayList<NetNode>();
		Collection<Hashtable<String, Object>> netNodeResList ;
		NetNode netNode; 
		try {
			Repo<String> netNodesRepo = DFHolder.get().netNodesRepo;
			Hashtable<String,Hashtable<String,Object>> res = netNodesRepo.getTable();
			if(res == null) return netNodeList;
			netNodeResList = res.values();
		}
		catch (Throwable e) {
			log.error("Failed to retrieve netnodes. ", e);
			return null;
		}
		Hashtable<String, Object> netNodeRow=null;
		for(Iterator<Hashtable<String, Object>> i = netNodeResList.iterator(); i.hasNext();) {
			try {
				netNodeRow = i.next();
				netNode = new NetNode(netNodeRow);

				netNode.toJacksonFriendly();
				netNodeList.add(netNode);	
				log.debug("NetNode retrieved "+netNodeRow.toString());
			}
			catch (Throwable e) {
				log.error("Failed to retrieve netnode. " + netNodeRow.toString());
				continue;
			}
		}
		return netNodeList;

	}

	@GET
	@Path("netnodes/count")
	@Produces(MediaType.APPLICATION_JSON)
	public String getNetnodesCount(@Context HttpServletResponse servletResponse) {
		log.debug("getNetnodesCount: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			Repo<String> netNodesRepo = DFHolder.get().netNodesRepo;
			int count = netNodesRepo.getKeys().size();
			return String.valueOf(count);
		} catch (Throwable e) {
			log.error("Failed to retrieve OFCs count");
			return null;
		}
	}

	@GET
	@Path("netnodes/{netnode}")
	@Produces(MediaType.APPLICATION_JSON)
	public NetNode getNetNode(@PathParam("netnode") String netNodeLabel, @Context HttpServletResponse servletResponse) {
		log.debug("getNetNode: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			NetNodeResource netNodeResource = new NetNodeResource(uriInfo, request, netNodeLabel);
			return netNodeResource.getNetNode();		
		} catch (Throwable e) {
			return new NetNode();
		}
	}

	@POST
	@Path("netnodes")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addNetNode(NetNode netNode, @Context HttpServletResponse servletResponse) throws Exception {
		log.debug("addNetNode: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		int errorCode = 0;
		try {
			DFHolder.get().getMgmtPoint().addNetNode(netNode);
			errorCode = 0;
		} catch (NotSupportedException e1) {
			errorCode = 405;
		} catch (Throwable e) {
			log.error("Failed to add NetNode " + netNode.label);
			errorCode = 500;
		}
		if(errorCode != 0) {
			try {
				servletResponse.sendError(errorCode, "Failed to add NetNode " + netNode.label);
			} catch (IOException e1) {
				log.error("Error in response "+e1);
			}
		}
	}

	@DELETE
	@Path("netnodes/{netnode}")
	public void deleteNetNode(@PathParam("netnode") String netNodeLabel, @Context HttpServletResponse servletResponse) {

		log.debug("deleteNetNode: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return ;
		}
		NetNodeResource netNodeResource = new NetNodeResource(uriInfo, request, netNodeLabel);		
		NetNodeResourceStatus status = netNodeResource.deleteNetNode();
		int errorCode = 0;
		switch (status) {
		case OK:
			errorCode = 0;
			break;
		case FORBIDDEN:
			errorCode = 405;
			break;
		case CONFLICT:
			errorCode = 409;
			break;
		case SERVER_ERROR:
			errorCode = 500;
			break;
		}
		if(errorCode != 0) {
			try {
				servletResponse.sendError(errorCode, "Failed to remove PN " + netNodeLabel);
			} catch (IOException e) {/* Ignore */}
		}
	}

	@GET
	@Path("mitigations")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Mitigation> getMitigations(@Context HttpServletResponse servletResponse) {
		log.debug("getMitigations: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		ArrayList<Mitigation> mitigationsList = new ArrayList<Mitigation>();
		try {
			Repo<String> mitigationsRepo = DFHolder.get().mitigationsRepo;
			Hashtable<String,Hashtable<String,Object>> res = mitigationsRepo.getTable();
			if(res == null) return mitigationsList;
			Collection<Hashtable<String, Object>> mitigationsResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = mitigationsResList.iterator(); i.hasNext();)
				mitigationsList.add(new Mitigation(i.next()));				
		} catch (Throwable e) {
			log.error("Failed to retrieve mitigations. ", e);
			return null;
		}
		return mitigationsList;
	}

	@GET
	@Path("mitigations/{mitigation}")
	@Produces(MediaType.APPLICATION_JSON)
	public Mitigation getMitigation(@PathParam("mitigation") String mitigationLabel, @Context HttpServletResponse servletResponse) {

		log.debug("getMitigation: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			Repo<String> mitigationsRepo = DFHolder.get().mitigationsRepo;
			Hashtable<String,Object> mitigationRow = mitigationsRepo.getRow(mitigationLabel);
			Mitigation mitigation = new Mitigation(mitigationRow);
			return mitigation;
		} catch (Throwable e) {
			log.error("Failed to retrieve mitigation " + mitigationLabel, e);
			return null;
		}
	}

	@GET
	@Path("attacks")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<Attack> getAttacks(@Context HttpServletResponse servletResponse) {
		log.debug("getAttacks: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		ArrayList<Attack> attackList = new ArrayList<Attack>();
		try {
			log.debug("getAttacks: Invoked");
			Repo<String> attacksRepo = DFHolder.get().attacksRepo;
			Hashtable<String,Hashtable<String,Object>> res = attacksRepo.getTable();
			if(res == null) return attackList;
			Collection<Hashtable<String, Object>> attackResList = res.values();			
			for(Iterator<Hashtable<String, Object>> i = attackResList.iterator(); i.hasNext();)
				attackList.add(new Attack(i.next()));			
		} catch (Throwable e) {
			log.error("Failed to retrieve attacks. ", e);
			return null;
		}
		return attackList;
	}

	@GET
	@Path("attacks/{attack}")
	@Produces(MediaType.APPLICATION_JSON)
	public Attack getAttack(@PathParam("attack") String attackLabel, @Context HttpServletResponse servletResponse) {

		log.debug("getAttack: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			Repo<String> attacksRepo = DFHolder.get().attacksRepo;
			Hashtable<String,Object> attackRow = attacksRepo.getRow(attackLabel);
			Attack attack = new Attack(attackRow);
			return attack;
		} catch (Throwable e) {
			log.error("Failed to retrieve attack " + attackLabel, e);
			return null;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pnstats")
	public Collection<PNStatReport> getPNStats(@Context HttpServletResponse servletResponse) {

		log.debug("getPNStats: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}
		try {
			Collection<PNStatReport> pnStatReports = DFHolder.get().getMgmtPoint().getLatestPNStatReports();
			return pnStatReports;			
		} catch (Throwable e) {
			log.error("Failed to retrieve pnStats. ", e);
			return null;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("pnstats/{pn}")
	public PNStatReport getPNStat(@PathParam("pn") String pnLabel, @Context HttpServletResponse servletResponse) {

		log.debug("getPNStat: Invoked");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return null;
		}

		try {
			PNStatReport pnStatReport = DFHolder.get().getMgmtPoint().getLatestPNStatReport(pnLabel);
			return pnStatReport;
		} catch (IllegalArgumentException e) {
			try {
				servletResponse.sendError(400, "get pnstat: Null or empty pnLabel.");
			} catch (IOException e1) {log.error("Error in response "+e1);}
		} catch (Throwable e1) {
			try {
				servletResponse.sendError(500, "Failed to get pnstat for pn " + pnLabel);
			} catch (IOException e2) {log.error("Error in response "+e2);}
		}
		return null;
	}
	
	@POST
	@Path("detectors/{label}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void changeDetector(Map<String,String> input, @PathParam("label") String detectorLabel, @Context HttpServletResponse servletResponse) {
		log.debug("detectors: Invoked");
		
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return;
		}
		
		if ( detectorLabel== null || DFHolder.get().getDetectorMgr().getDetector(detectorLabel) == null ) {
			try {
				servletResponse.sendError(503, "Service is unavailable" ); 
			} catch (Throwable e) {
				log.error("Error in response "+e);
			}
			return;
		}
		
		try {
		Iterator<Map.Entry<String,String>> iter = input.entrySet().iterator();
		Map.Entry<String,String> entry;
		
		while ( iter.hasNext() ) {
			entry = iter.next();
			String attrName = entry.getKey();
			String attrValue = entry.getValue();
			DFHolder.get().getDetectorMgr().setDetectorProperties ( detectorLabel, attrName,attrValue );
		} 
		} catch (Throwable e) {
			try {
				servletResponse.sendError(500, "Failed to update detector " + detectorLabel);
			} catch (IOException e2) {log.error("Error in response "+e2);}
		}
	}
}
