/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import me.prettyprint.cassandra.serializers.DateSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import org.opendaylight.defense4all.framework.core.EventRecordData;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FR;
import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * @author konstantinp
 *
 */
public class FRImpl implements FR {

	// Time sliced repo
	public Repo<String> timeSliceRepo = null;
	private static String SLICES_KEYS = "SLICE_KEYS";
	private static String FILTERS_KEY = "FILTER_KEYS";
	private static String FILTERS_CELL = "FILTER_CELL";
	private static String FILTER_DELIMETER = ",";

	// repos attributes
	public enum RepoMinor {
		INVALID,
		EVENTS,
		TIME_SLICES
	}

	public static class EventRecordImpl  extends EventRecordData implements EventRecord {
		public static final String TIME_COUNTER = "timeCounter";
		public static final String EVENT_TIME = "eventTime";
		public static final String EVENT_TYPE = "eventType";
		public static final String EVENT_DATA = "eventData";

		public String			timeCounter;

		public  EventRecordImpl( String eventType, String eventData) {
			this.eventTime = new Date();
			this.eventType = eventType;
			this.eventData = eventData;
		}

		private void generateKey ()  {
			this.timeCounter = String.valueOf(eventTime.getTime())+"_"+String.valueOf(counter);
			counter++;
		}

		public String getKey() {
			return timeCounter;
		}

		private static List<RepoCD> getEventRecordRCDs() {
			if(mEventsArchiveCDs == null) {
				RepoCD rcd;
				mEventsArchiveCDs = new ArrayList<RepoCD>();
				rcd = new RepoCD(TIME_COUNTER,  StringSerializer.get(), null);	mEventsArchiveCDs.add(rcd);
				rcd = new RepoCD(EVENT_TIME,  DateSerializer.get(), null);		mEventsArchiveCDs.add(rcd);
				rcd = new RepoCD(EVENT_TYPE,  StringSerializer.get(), null);	mEventsArchiveCDs.add(rcd);
				rcd = new RepoCD(EVENT_DATA,  StringSerializer.get(), null);	mEventsArchiveCDs.add(rcd);
			}
			return mEventsArchiveCDs;
		}

		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append(" Key: ");   sb.append(timeCounter);
			sb.append(" Event: "); sb.append(eventType);sb.append(":");
			sb.append(eventTime); sb.append(":");
			sb.append(eventData);
			return sb.toString();
		}

		public Hashtable<String, Object> toRow() {
			Hashtable<String, Object> row = new Hashtable<String, Object>();
			row.put(TIME_COUNTER,timeCounter );
			row.put(EVENT_TIME,eventTime );
			row.put(EVENT_TYPE,eventType );
			row.put(EVENT_DATA,eventData );
			return row;
		}

		public EventRecordImpl(Hashtable<String, Object> row) {
			this.timeCounter =  (String) row.get(TIME_COUNTER);
			this.eventTime = (Date)row.get(EVENT_TIME);
			this.eventType = (String) row.get(EVENT_TYPE);
			this.eventData = (String) row.get(EVENT_DATA);
		}

		public boolean match( FilterRecord filterRecord ) {
			if (filterRecord == null)
				return true;
			return filterRecord.match(this);
		}
	}

	public static class  FilterRecordImpl implements FilterRecord {

		// store list of Ids to match to filter
		private List<Integer> filterMap = new ArrayList<Integer>();

		private FilterRecordImpl() {};

		// Match event record type with the id from repo filters list
		public boolean match( EventRecord eventRecord ) {
			if ( eventRecord == null )
				return false;
			EventRecordImpl eventImpl = (EventRecordImpl)eventRecord;
			int matchId = filtersList.indexOf(eventImpl.eventType);
			if ( -1 == matchId)
				return false; // no such events in the repo
			return filterMap.contains(matchId);
		}

		// token string - find each of the strings in the Repo metadata filters string and store id in the filter
		// match list
		public static FilterRecord create ( String filterStr) {

			FilterRecordImpl filter = new FilterRecordImpl();
			try {
				String[] filterTokens = filterStr.split(FILTER_DELIMETER);
				for(String filterToken : filterTokens) {
					int pos = filtersList.indexOf(filterToken);
					if ( -1 == pos) {
						filtersList.add(filterToken);
						pos = filtersList.size() - 1;
					}
					filter.filterMap.add(pos);
				}
			} catch ( Exception ex) {
				log.error("Bad argument for EventLog filtering "+filterStr);
				return null;
			}
			return filter;
		}
	}

	/* period of time slices */
	static long slicePeriod = 0;
	static long counter = 0;

	protected int flightRecorderSliceDays;
	protected RepoFactoryImpl repoFactoryImpl;
	protected String outputFilePrefix;
	protected String outputFileSuffix;

	static Logger log = LoggerFactory.getLogger(FRImpl.class);

	/* Event archive repo */
	public Repo<String> eventsArchiveRepo = null;
	protected static ArrayList<RepoCD> mEventsArchiveCDs = null;

	/* List of all possible strings to be used for filtering events in the repo for search optimization position
	 * of the event string in the filtersList is used as data in the event slice repo */
	private static List<String> filtersList = new ArrayList<String>();

	/** Setters for Spring */
	public void setRepoFactoryImpl(RepoFactoryImpl repoFactoryImpl) {this.repoFactoryImpl = repoFactoryImpl;}
	public void setFlightRecorderSliceDays  ( int slice ) { this.flightRecorderSliceDays = slice;}
    public void setOutputFilePrefix ( String prefix) { this.outputFilePrefix = prefix;}
    public void setOutputFileSuffix  ( String suffix) { this.outputFileSuffix= suffix;}

	public FRImpl() {}

	public void init() throws ExceptionControlApp {

		if(flightRecorderSliceDays == 0) flightRecorderSliceDays = 1;
		slicePeriod = flightRecorderSliceDays * 24 * 60 * 60 * 1000;

		/* Init flight recorder repos */
		String repoGlobal = FrameworkMainImpl.RepoMajor.FWORK_FLIGHT_RECORDER.name();
		try {
			eventsArchiveRepo = repoFactoryImpl.getOrCreateRepo(repoGlobal, RepoMinor.EVENTS.name(),
					StringSerializer.get() , true, EventRecordImpl.getEventRecordRCDs());
			timeSliceRepo =	repoFactoryImpl.getOrCreateRepo(repoGlobal, RepoMinor.TIME_SLICES.name(),
					StringSerializer.get() , true,null);
		} catch ( Exception e) {
			log.error("Failed to initialize Flight Recorder " + e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to initialize Flight Recorder ", e);
		}

		/* Init filtering list */
		loadFiltersList();
	}

	public void finit() {
		try {
			eventsArchiveRepo.applyUpdateBatch();
		} catch (ExceptionControlApp e) { /* Ignore in order to at least try flush the next repo. */}
		try {
			timeSliceRepo.applyUpdateBatch();
		} catch (ExceptionControlApp e) {/* Ignore - no recovery is feasible. */}
	}

	// load possible filters from  metadata DB cell
	private void loadFiltersList() throws ExceptionControlApp {

		boolean filterKeyCellExists = timeSliceRepo.hasCell(FILTERS_KEY, FILTERS_CELL);
		if(!filterKeyCellExists) {
			resetFiltersList();
			return;
		}

		/* Populate filters */
		try {
			String filtersString = (String) timeSliceRepo.getCellValue(FILTERS_KEY, FILTERS_CELL);
			if (filtersString != null &&  ! filtersString.isEmpty()) {
				String[] filterTokens = filtersString.split(FILTER_DELIMETER);
				for(String filterToken : filterTokens) {
					filtersList.add(filterToken);
				}
			}
		} catch (ExceptionControlApp e) {
			log.error("Badly formatted filters cell.");
			resetFiltersList();// reset invalid string
		}
		// TODO: Periodic cleanup - recreate filtersList in repo.
	}

	private void resetFiltersList() throws ExceptionControlApp {
		try {
			filtersList.clear();
			timeSliceRepo.setCell(FILTERS_KEY, FILTERS_CELL, "" ); // reset invalid string
		} catch (Throwable e) {
			String msg = "Excepted trying to set empty filters cell after catching badly formatted one";
			log.error(msg);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp(msg, e);
		}
	}

	// Concatenate all filters string and store in the metadata DB cell
	private void saveFiltersList() {

		if (  filtersList == null || filtersList.isEmpty()) return;

		StringBuilder sb = new StringBuilder();
		for ( String filter:filtersList) {
			sb.append(filter); sb.append(FILTER_DELIMETER);
		}
		sb.setLength(sb.length() - FILTER_DELIMETER.length());
		try {
			timeSliceRepo.setCell(FILTERS_KEY, FILTERS_CELL, sb.toString() );
		} catch (ExceptionControlApp e) {}
	}

	// Find/add filter string in list of possible filters
	// update repo when required
	private int generateMatchId(String eventType) {
		int matchId = filtersList.indexOf(eventType);
		if ( -1 == matchId) {
			filtersList.add(eventType);
			matchId = filtersList.size() - 1;
			saveFiltersList();
		}
		return matchId;
	}

	/**
	 * create filter for search in the repository
	 * @param filterStr
	 * @return
	 */
	public FilterRecord createFilter( String filterStr ) {
		return FilterRecordImpl.create(filterStr);
	}

	/**
	 * add event record to repository
	 * @param eventType
	 * @param eventData
	 */
	@Override
	public synchronized void logRecord (String eventType, String eventData) {
		EventRecordImpl event = new EventRecordImpl(eventType, eventData );
		try {
			event.generateKey();
			eventsArchiveRepo.setRow(event.getKey(), event.toRow());
			addToSlice ( event);
		} catch (ExceptionControlApp e) {
			log.error("Failed to log record: " + eventType + eventData + ". " + e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}
	@Override
	public synchronized void logRecord (EventRecord iEvent) {
		EventRecordImpl event = (EventRecordImpl) iEvent;
		try {
			if ( event.getKey() == null)
				event.generateKey();
			eventsArchiveRepo.setRow(event.getKey(), event.toRow());
			addToSlice ( event);
		} catch (ExceptionControlApp e) {
			log.error("Failed to log record: " + iEvent.toString() + ". " + e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
		}
	}

	private void addToSlice (EventRecordImpl event ) throws ExceptionControlApp {

		long sliceNumber = event.eventTime.getTime() / slicePeriod;
		String sliceKey = String.valueOf(sliceNumber); 		// check if slice exists already
		timeSliceRepo.setCell(SLICES_KEYS, sliceKey, "" );  // add slice to metadata if not exist
		int filterId = generateMatchId(event.eventType);
		timeSliceRepo.setCell(sliceKey, event.getKey(), filterId);	// add event to slice record if not exist
	}

	@Override
	public String getOutputFileSuffix() {
		return this.outputFileSuffix;
	}

	@Override
	public String getOutputFilePrefix() {
		return this.outputFilePrefix;
	}

	private String getFileFullPath(String infixName) throws ExceptionControlApp {
		try {
			if (!infixName.matches("^[a-zA-Z0-9_-]*$"))
			{
				throw new Exception("invalid file name " + infixName);
			}
			String fullPath = this.outputFilePrefix + infixName + this.outputFileSuffix;
			return fullPath;
		}
		catch (Exception e) {
			log.error("Failed to dump Flight Recorder events " +e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to dump to file  "+infixName+" ", e);
		}
	}

	/**
	 * @param fileName
	 * dump content of records to file
	 * @throws ExceptionControlApp
	 */
	@Override
	public void dump(String fileName) throws ExceptionControlApp {
		dump (fileName, null );
	}

	@Override
	public void dump(String fileName, FilterRecord filter) throws ExceptionControlApp {

		BufferedWriter bw = null;
		try {
			fileName = getFileFullPath(fileName);
			File file = new File(fileName);
			if(!file.exists())
				file.createNewFile(); 	// if file doesnt exists, create it
			File absPath = file.getAbsoluteFile();
			FileWriter fwriter = new FileWriter(absPath);
			bw = new BufferedWriter(fwriter);

			List<String> eventSlicesKeys = timeSliceRepo.getOrderedColumns(SLICES_KEYS, 0, false);
			if (eventSlicesKeys == null )
				return;
			for ( String eventSliceKey:eventSlicesKeys) {
				List<String> eventKeys = timeSliceRepo.getOrderedColumns(eventSliceKey, 0, false);
				if (eventKeys == null )
					continue;
				for ( String eventKey:eventKeys ) {
					EventRecordImpl event  = new EventRecordImpl(eventsArchiveRepo.getRow(eventKey));
					if ( ! event.match(filter))
						continue;
					event.dump(bw);
					bw.newLine();
				}
			}
		}
		catch (IOException e) {
			log.error("Failed to dump to file  "+fileName+" "+e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to dump to file  "+fileName+" ", e);
		}
		catch (Exception e) {
			log.error("Failed to dump Flight Recorder events " +e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to dump to file  "+fileName+" ", e);
		}
		finally {
			try {
				if (bw != null) bw.close();
			} catch (IOException e) {}
		}
	}

	/**
	 * dump content of records to file
	 * @param fileName file name to dump to
	 * @param fromDate filter records from date
	 * @param toDate filter records from date
	 * @param maxNum max records to dump
	 * @throws ExceptionControlApp
	 */
	@Override
	public void dump(String fileName, Date fromDate, Date toDate, int maxNum) throws ExceptionControlApp {
		dump( fileName,  fromDate,  toDate,  maxNum, null );
	}

	@Override
	public void dump(String fileName, Date fromDate, Date toDate, int maxNum, FilterRecord filter) throws ExceptionControlApp {
		BufferedWriter bw = null;
		try {
			fileName = getFileFullPath(fileName);
			File file = new File(fileName);
			if(!file.exists())
				file.createNewFile(); 	// if file doesnt exists, create it
			File absPath = file.getAbsoluteFile();
			FileWriter fwriter = new FileWriter(absPath);
			bw = new BufferedWriter(fwriter);

			List<EventRecordData> result = getTimeRangeEvents (  fromDate,  toDate,  maxNum, filter );
			if(result != null) {
				for ( EventRecordData event:result) {
					event.dump(bw);
					bw.newLine();
				}
			}
		}
		catch (IOException e) {
			log.error("Failed to dump to file  "+fileName+" "+e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to dump to file  "+fileName+" ", e);
		}
		catch (Exception e) {
			log.error("Failed to dump Flight Recorder events " +e.getLocalizedMessage());
			repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to dump to file  "+fileName+" ", e);
		}
		finally {
			try {
				if (bw != null) bw.close();
			} catch (IOException e) {}
		}
	}

	/**
	 * @param number  max records to return
	 * @return list of latest records in the repo
	 * @throws ExceptionControlApp
	 */
	@Override
	public List<EventRecordData> getLatestEvents(int number ) throws ExceptionControlApp {
		return getLatestEvents(number, null );
	}

	@Override
	public List<EventRecordData> getLatestEvents(int maxNum, FilterRecord filter) throws ExceptionControlApp {

		List<EventRecordData> result = new ArrayList<EventRecordData>();
		List<String> eventSlicesKeys = timeSliceRepo.getOrderedColumns(SLICES_KEYS,0,false);
		if(eventSlicesKeys == null) return null;

		if ( maxNum == 0 )
			maxNum = Integer.MAX_VALUE;

		ListIterator<String> eventSlicesKeysIter = eventSlicesKeys.listIterator(eventSlicesKeys.size());
		while ( eventSlicesKeysIter.hasPrevious()) {

			String eventSliceKey = eventSlicesKeysIter.previous(); // get lates event slice
			if ( eventSliceKey.equals(SLICES_KEYS)) continue; // if iterator points to metadata string - skip it

			List<Integer> filters = (filter != null ) ? ((FilterRecordImpl)filter).filterMap : null;
			List<String> eventKeys = timeSliceRepo.getOrderedColumns(eventSliceKey,maxNum,true, filters);
			if(eventKeys == null) continue;

			for ( String key:eventKeys) {
				Hashtable<String, Object> row;
				try {
					row = eventsArchiveRepo.getRow(key);
				} catch (Throwable e) {
					log.error("Failed to retrieve row" + key + e.getLocalizedMessage());
					repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;
				}
				if(row == null) continue;

				EventRecordImpl event  = new EventRecordImpl(row);
				if ( ! event.match(filter)) continue;
				result.add(event);
			}
			if ( result.size() >= maxNum )	break;
		}
		return result;
	}

	/**
	 * @param fromDate filter records from date
	 * @param toDate filter records to date
	 * @param maxNum - max records to return
	 * @return - list of  records in the repo
	 */
	@Override
	public List<EventRecordData> getTimeRangeEvents ( Date fromDate, Date toDate, int maxNum ) {
		return getTimeRangeEvents (  fromDate,  toDate,  maxNum, null );
	}

	@Override
	public List<EventRecordData> getTimeRangeEvents ( Date fromDate, Date toDate, int maxNum, FilterRecord filter) {

		List<EventRecordData> result = new ArrayList<EventRecordData>();
		//	List<Object> filterObjects = (List<Object>)((FilterRecordImpl)filter).filterMap;
		List<String> allSlicesKeys = timeSliceRepo.getOrderedColumns(SLICES_KEYS, 0, false);
		if(allSlicesKeys == null) return null;

		long  sliceFrom = fromDate.getTime() / slicePeriod ;
		long    sliceTo = toDate.getTime() / slicePeriod ;

		Iterator<String> eventSlicesKeysIter = allSlicesKeys.iterator();
		String stringFrom = String.valueOf(fromDate.getTime());
		boolean onInterval = false; boolean afterInterval = false;
		if ( maxNum == 0 )
			maxNum = Integer.MAX_VALUE;
		while ( eventSlicesKeysIter.hasNext()) {

			// Go over slices to find from and to slice
			String eventSliceKey = eventSlicesKeysIter.next();
			if ( !onInterval &&   Long.valueOf(eventSliceKey)  < sliceFrom  )
				continue;
			if ( Long.valueOf(eventSliceKey) > sliceTo )
				break;

			List<String> eventKeys = timeSliceRepo.getOrderedColumns(eventSliceKey,0,false, (filter !=null )?((FilterRecordImpl)filter).filterMap:null  );
			if(eventKeys == null) continue;

			for ( String eventKey:eventKeys ) {

				if ( !onInterval && eventKey.compareTo(stringFrom) >= 0 ) // check if we are on interval point already
					onInterval = true;
				if ( onInterval != true ) // continue to next if we are not on interval still
					continue;

				Hashtable<String, Object> row; EventRecordImpl event = null;
				try {
					row = eventsArchiveRepo.getRow(eventKey);
					event  = new EventRecordImpl(row);
				} catch (ExceptionControlApp e) {
					log.error("Failed to retrieve row" + eventKey + " or construct event object. " + e.getLocalizedMessage());
					repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;
				}
				// check if event is not past the interval already
				if ( event.eventTime.getTime() <= toDate.getTime() && result.size() < maxNum ) {
					if ( ! event.match(filter))
						continue;
					result.add(event);
				} else {
					afterInterval = true;
					break;
				}
			}
			// stop if we pass end of interval
			if ( afterInterval == true)
				break;
		}
		return result;
	}

	/**
	 * @param days cleanup older records
	 */
	@Override
	public void reset ( int days ) {

		if(days < 0) return;
		long deletePeriod = days * 24 * 60 * 60 * 1000;
		long latestSlice  = ( new Date().getTime() - deletePeriod ) / slicePeriod;

		List<String> allSlicesKeys = timeSliceRepo.getOrderedColumns(SLICES_KEYS,0,false);
		if(allSlicesKeys == null) return;

		Iterator<String> eventSlicesKeysIter = allSlicesKeys.iterator();
		while ( eventSlicesKeysIter.hasNext()) { // Go over slices to find latest slice

			String eventSliceKey = eventSlicesKeysIter.next();
			if ( Long.valueOf(eventSliceKey) > latestSlice )
				break;

			List<String> eventKeys = timeSliceRepo.getOrderedColumns(eventSliceKey,0,false);
			if ( eventKeys == null )
				continue;
			boolean failedToDeleteAnEvent = false;
			for ( String eventKey:eventKeys ) {
				try {
					eventsArchiveRepo.deleteRow(eventKey);
				} catch (ExceptionControlApp e) {
					log.error("Failed to delete event " + eventKey + e.getLocalizedMessage());
					failedToDeleteAnEvent = true;
					continue;
				} // delete all events in current slice and slice itself
			}
			if(failedToDeleteAnEvent) // Record only one health issue for all events failed to be deleted
				repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			try {
				timeSliceRepo.deleteRow(eventSliceKey);
				timeSliceRepo.deleteCell(SLICES_KEYS, eventSliceKey);
			} catch (ExceptionControlApp e) {
				log.error("Failed to delete eventSliceKey " + eventSliceKey + e.getLocalizedMessage());
				repoFactoryImpl.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
		}
	}

	@Override
	public synchronized void reset (ResetLevel resetLevel) {
		timeSliceRepo.truncate();
		eventsArchiveRepo.truncate();
	}

	public void testRepo() {
		try {

			int i = 5;

			System.out.println("******************");
			while ( i-- > 0 ) {
				EventRecordImpl ev1 = new EventRecordImpl ("COMMON", "first alarm");
				logRecord(ev1);
				logRecord("EVENT", "first event");
				logRecord("ALARM", "common record" );
				if ( i % 10000 == 0 ) {
					System.out.print("."+i+".");
				}
				Thread.sleep(500);
			}
			System.out.println("******************");

			long bt = System.currentTimeMillis();
			List<EventRecordData> latest = getLatestEvents(20, createFilter("COMMON"));
			long at = System.currentTimeMillis();
			if(latest == null)
				System.out.println("No events");
			else {
				for (EventRecordData ev:latest) {
					System.out.println("Latest: "+ev.toString());
				}
			}

			System.out.println("Spend "+(at-bt));

			DateFormat df = new SimpleDateFormat("MM/dd/yy hh:mm:ss");
			Date from = df.parse("10/10/13 11:25:32");
			Date to = df.parse("10/20/13 14:53:56");

			dump("/tmp/event_dump");
			dump("/tmp/event_dump_1", from, to, 20, createFilter("ALARM,EVENT") );

		} catch ( Exception e) {
			log.error("Fail to test Flight Recorder " +e.getLocalizedMessage());
		}
	}
}
