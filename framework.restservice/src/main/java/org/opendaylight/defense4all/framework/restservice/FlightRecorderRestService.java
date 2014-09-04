/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.restservice;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.defense4all.framework.core.EventRecordData;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FR.FilterRecord;

@Path("/fr")
public class FlightRecorderRestService {

	static Logger log = LoggerFactory.getLogger("org.opendaylight.defense4all.restservice");

	static final String MAX_NUM_PARAM = "maxNum";
	static final String FROM_DATE_PARAM = "fromDate";
	static final String TO_DATE_PARAM = "toDate";
	static final String TO_FILE_PARAM = "toFile";
	static final String OLDER_DAYS_PARAM = "olderDays";
	static final String FILTER_PARAM = "filter";
	static final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy_hh:mm:ss");  

	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	@GET
	@Path("events")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventRecordData> getEvents(@QueryParam(MAX_NUM_PARAM) int maxNum,
			@QueryParam(FROM_DATE_PARAM) String fromDateStr, @QueryParam(TO_DATE_PARAM) String toDateStr,
			@QueryParam(FILTER_PARAM) String filterStr,
			@Context HttpServletResponse servletResponse) throws IOException {

		log.debug("FlightRecorderRestService latest : invoked");
		log.debug("Params :"+maxNum+":"+fromDateStr+":"+toDateStr);

		if ( ! FMHolder.get().isOpenForBusiness() ) {
			servletResponse.sendError(503, "Service is unavailable" ); 
			return null;
		}

		// verifying the format of the sent date   
		Date fromDate = null;   Date toDate = null;
		try {   
			if (fromDateStr != null )
				fromDate = sdf.parse(fromDateStr);  
			if ( toDateStr != null )
				toDate = sdf.parse( toDateStr );
		} catch ( Throwable pe) {  
			log.error("Failed to parse input parameters " + pe.getLocalizedMessage());
			servletResponse.sendError(400);
			return null;
		}  
		if ( fromDate == null || toDate == null ) {
			log.error("Bad input parameters");
			servletResponse.sendError(400);
			return null;
		}			

		FilterRecord filterRec = null;
		if ( filterStr != null ) {
			filterRec = FMHolder.get().getFR().createFilter(filterStr);
		}
		List<EventRecordData> events;
		try {
			events = FMHolder.get().getFR().getTimeRangeEvents(fromDate, toDate, maxNum, filterRec);
		} catch (Throwable e) {
			log.error("Failed to retrieve time range events. " + e.getLocalizedMessage());
			servletResponse.sendError(500);
			return null;
		}	
		return events;
	}

	@GET
	@Path("latest")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EventRecordData> getLatest(@QueryParam(MAX_NUM_PARAM) int maxNum, @QueryParam(FILTER_PARAM) String filterStr,
			@Context HttpServletResponse servletResponse) throws IOException {

		log.debug("FlightRecorderRestService events : invoked");

		if ( ! FMHolder.get().isOpenForBusiness() ) {
			servletResponse.sendError(503, "Service is unavailable" ); 
			return null;
		}

		FilterRecord filterRec = null;
		if ( filterStr != null ) {
			filterRec = FMHolder.get().getFR().createFilter(filterStr);
		}
		List<EventRecordData> events = null;
		try {
			events = FMHolder.get().getFR().getLatestEvents(maxNum, filterRec);
		} catch (Throwable e) {
			log.error("Failed to retrieve latest events. " + e.getLocalizedMessage());
			servletResponse.sendError(500);
			return null;
		}	
		return events;
	}	

	@POST
	@Path("dump")
	@Consumes(MediaType.APPLICATION_JSON)
	public String dump(Map<String,String> input , @Context HttpServletResponse servletResponse) throws Exception {

		log.debug("FlightRecorderRestService dump");

		if ( ! FMHolder.get().isOpenForBusiness() ) {
			servletResponse.sendError(503, "Service is unavailable" ); 
			return null;
		}


		String fileName = input.get(TO_FILE_PARAM);
		if ( fileName == null ) {
			log.error("Empty file name parameter" );
			servletResponse.sendError(400);
			return null; 
		}

		// verifying the format of the sent date 
		String fromDateStr = input.get(FROM_DATE_PARAM);
		String toDateStr = input.get(TO_DATE_PARAM);
		String maxNumStr = input.get(MAX_NUM_PARAM );
		String filterStr = input.get(FILTER_PARAM );
		Date fromDate = null;   Date toDate = null; 
		Integer maxNum = 0;   FilterRecord filterRec = null;
		try {   
			if (fromDateStr != null )
				fromDate = sdf.parse(fromDateStr);  
			if ( toDateStr != null )
				toDate = sdf.parse( toDateStr );
			if ( maxNumStr != null )
				maxNum = Integer.valueOf(maxNumStr);
			if (filterStr != null )
				filterRec = FMHolder.get().getFR().createFilter(filterStr);
		} catch ( Throwable pe) {  
			log.error("Failed to parse input parameters " + pe.getLocalizedMessage());
			servletResponse.sendError(400);
			return null;
		}   

		try {
			if ( fromDate != null && toDate != null )
				FMHolder.get().getFR().dump(fileName, fromDate, toDate, maxNum, filterRec);
			else
				FMHolder.get().getFR().dump(fileName, filterRec);
		} catch (Throwable ex ) {
			StringBuilder sb = new StringBuilder();
			sb.append("Failed to dump to "); sb.append(fileName);
			sb.append(". filter = "); sb.append(filterStr);
			if(fromDate != null) {
				sb.append(". from date = "); sb.append(fromDate);
			}
			if(toDate != null) {
				sb.append(". to date = "); sb.append(toDate);
			}
			log.error(sb.toString());
			log.error(ex.getLocalizedMessage() );
			servletResponse.sendError(500);
		}
		return fileName;
	}

	@POST
	@Path("cleanup")
	@Consumes(MediaType.APPLICATION_JSON)
	public String cleanup(Map<String,String> input , @Context HttpServletResponse servletResponse) throws Exception {

		log.debug("FlightRecorderRestService cleanup");
		if ( ! FMHolder.get().isOpenForBusiness() ) {
			servletResponse.sendError(503, "Service is unavailable" ); 
			return null;
		}

		String olderDaysStr = null; int olderDays = 0;

		olderDaysStr = input.get(OLDER_DAYS_PARAM);
		if ( olderDaysStr == null ) {
			log.error("Empty cleanup days parameter." );
			servletResponse.sendError(400);
			return null; 
		}

		try { 
			olderDays = Integer.valueOf(olderDaysStr);
		} catch ( Throwable pe) {  
			log.error("Failed to parse olderDaysStr. " + pe.getLocalizedMessage());
			servletResponse.sendError(400);
			return null;
		}

		try {
			FMHolder.get().getFR().reset(olderDays);
		} catch (Throwable ex ) {
			log.error("Failed to reset flight recorder.");
			log.error(ex.getLocalizedMessage() );
			servletResponse.sendError(500);
		}
		return olderDaysStr;
	}
}
