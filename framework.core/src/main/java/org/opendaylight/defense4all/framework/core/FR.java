/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;

public interface FR {
	
	public interface EventRecord {
		public void dump(BufferedWriter bw) throws IOException;		
		public boolean match(FilterRecord filter);
	}
	
	public interface FilterRecord {
		public boolean match( EventRecord eventRecord );	
	}
	
	/**
	 * create filter for search in the repository
	 * @param filterStr
	 * @return
	 */
	public FilterRecord createFilter( String filterStr );
	
	/**
	 * add event record to repository
	 * @param eventType
	 * @param eventData
	 */
	public void logRecord (String eventType, String eventData);
	public void logRecord (EventRecord event);	
	
	/**
	 * @param fileName
	 * dump content of records to file
	 * @throws ExceptionControlApp 
	 */
	public void dump(String fileName) throws ExceptionControlApp;
	public void dump(String fileName, FilterRecord filter) throws ExceptionControlApp;
	
	/**
	 * dump content of records to file
	 * @param fileName file name to dump to
	 * @param fromDate filter records from date
	 * @param toDate filter records from date
	 * @param maxNum max records to dump
	 * @throws ExceptionControlApp 
	 */
	public void dump(String fileName, Date fromDate, Date toDate, int maxNum) throws ExceptionControlApp;
	public void dump(String fileName, Date fromDate, Date toDate, int maxNum, FilterRecord filter) throws ExceptionControlApp;
	
	/**
	 * @param number  max records to return
	 * @return list of latest records in the repo
	 * @throws ExceptionControlApp 
	 */
	public List<EventRecord> getLatestEvents(int number) throws ExceptionControlApp ;
	public List<EventRecord> getLatestEvents(int number, FilterRecord filter) throws ExceptionControlApp ;
	
	/**
	 * @param fromDate filter records from date
	 * @param toDate filter records to date
	 * @param maxNum - max records to return
	 * @return - list of  records in the repo
	 */
	public List<EventRecord> getTimeRangeEvents ( Date fromDate, Date toDate, int maxNum);
	public List<EventRecord> getTimeRangeEvents ( Date fromDate, Date toDate, int maxNum, FilterRecord filter);
	
	/**
	 * @param days cleanup older records 
	 */
	public void reset ( int days );
	public void reset (ResetLevel resetLevel);

}
