/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.opendaylight.defense4all.framework.core.Asserter;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.ExceptionKey;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.ColumnSliceIterator;
import me.prettyprint.cassandra.service.template.ColumnFamilyTemplate;
import me.prettyprint.cassandra.service.template.ThriftColumnFamilyTemplate;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.OrderedRows;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.MutationResult;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hector.api.query.ColumnQuery;
import me.prettyprint.hector.api.query.QueryResult;
import me.prettyprint.hector.api.query.RangeSlicesQuery;
import me.prettyprint.hector.api.query.SliceQuery;

//Repo name corresponds to FrameworkMain.RepoMajor.REPO_FACTORY + "_" + RepoFactoryImpl.RepoMinor.Repos

public class RepoImpl<K> implements Repo<K> {

	private static final int MAX_COLUMNS_TO_RETURN = 1000;
	private static final int MAX_ROWS_CHUNK_TO_RETURN = 10000; // Cassandra may start timing out at 100000 rows at a time

	Logger log = LoggerFactory.getLogger(this.getClass());

	protected RepoDescription repoDesc;	
	protected RepoFactoryImpl rFactory = null;
	protected Serializer<K> keySerializer;	
	protected ColumnFamilyTemplate<K, String> cfTemplate = null;
	protected Mutator<K> mutator = null;
	protected Mutator<K> opMutator = null;
	protected Serializer<String> stringSerializer = StringSerializer.get();
	protected Serializer<byte[]> bytesArraySerializer = BytesArraySerializer.get();
	protected Serializer<Object> objSerializer = ObjectSerializer.get();
	RangeSlicesQuery<K, String, byte[]> tableQuery = null;
	RangeSlicesQuery<K, String, byte[]> keysQuery = null;
	SliceQuery<K,String, byte[]> rowQuery = null;
	ColumnQuery<K, String, byte[]> cellQuery = null;

	/** Constructor - Corresponds to inflation by Hector EntityManager
	 * @param mRepoFactoryImpl 
	 * @param param_name param description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public RepoImpl(FrameworkMainImpl frameworkMain, RepoDescription repoDesc) throws ExceptionControlApp {
		this(frameworkMain.repoFactoryImpl, repoDesc);
	}

	@SuppressWarnings("unchecked")
	protected RepoImpl(RepoFactoryImpl rf, RepoDescription rdesc) throws ExceptionControlApp {

		this.repoDesc = rdesc;

		@SuppressWarnings("rawtypes")
		Class<? extends Serializer> c;
		String keySerializerClassName = rdesc.keySerializerClassName;
		String invalidClassMsg = "Invalid key serializer class name provided - \"" + keySerializerClassName + "\". ";
		try {
			c = Class.forName(keySerializerClassName).asSubclass(Serializer.class);	
			keySerializer = c.newInstance();
		} catch (Throwable e) {
			throw new IllegalArgumentException(invalidClassMsg, e);
		}

		this.rFactory = rf;
		try {
			cfTemplate = new ThriftColumnFamilyTemplate<K, String>(rFactory.ctrlAppsKS, repoDesc.repoName, keySerializer, stringSerializer);
			mutator = HFactory.createMutator(rFactory.ctrlAppsKS, keySerializer);
			opMutator = HFactory.createMutator(rFactory.ctrlAppsKS, keySerializer);
			tableQuery = HFactory.createRangeSlicesQuery(rFactory.ctrlAppsKS, keySerializer, stringSerializer, bytesArraySerializer);
			tableQuery.setColumnFamily(repoDesc.repoName).setRange(null, null, false, MAX_COLUMNS_TO_RETURN).setRowCount(MAX_ROWS_CHUNK_TO_RETURN);

			keysQuery = HFactory.createRangeSlicesQuery(rFactory.ctrlAppsKS, keySerializer, stringSerializer, bytesArraySerializer);
			keysQuery.setColumnFamily(repoDesc.repoName).setRange(null, null, false, 1).setRowCount(MAX_ROWS_CHUNK_TO_RETURN);
//			keysQuery = HFactory.createRangeSlicesQuery(rFactory.ctrlAppsKS, keySerializer, stringSerializer, bytesArraySerializer);
//			keysQuery.setColumnFamily(repoDesc.repoName).setKeys(null, null).setReturnKeysOnly();

			rowQuery = HFactory.createSliceQuery(rFactory.ctrlAppsKS, keySerializer, stringSerializer, bytesArraySerializer);
			rowQuery.setColumnFamily(repoDesc.repoName);
			cellQuery = HFactory.createColumnQuery(rFactory.ctrlAppsKS, keySerializer, stringSerializer, bytesArraySerializer);
			cellQuery.setColumnFamily(repoDesc.repoName);
		} catch (Throwable e) {
			log.error("Failed to initialize Repo." + e.getLocalizedMessage());
			rf.fMainImpl.frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Internal Database Failure");
			throw new ExceptionControlApp("Failed to initialize Repo.", e);
		}
	}

	/** Pre-shutdown cleanup
	 */
	public void finit() {
		try {
			applyUpdateBatch();
		} catch (Throwable e) {
			log.error("Failed to apply update batch." + e.getLocalizedMessage());
			rFactory.fMainImpl.frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Internal Database Failure");
		}
	}

	/** Factory reset
	 */
	public void factoryReset() {
		// delete repo and remove any externally visible effects, like external notifications
	}

	/** Adds column description to the table (no need to add the key column).
	 * @param param_name param description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void addColumnDescription(RepoCD columnDescription) throws ExceptionControlApp {
		if (columnDescription == null)
			return;
		try {
			cfTemplate.addColumn(columnDescription.columnName, columnDescription.columnValueSerializer);
		} catch (Exception e) {
			log.error("Failed to add column to CFTemplate." + e.getLocalizedMessage());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to add column to CFTemplate.", e);
		}
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void applyUpdateBatch() throws ExceptionControlApp {
		applyUpdateBatch(mutator);
	}

	protected void applyUpdateBatch(Mutator<K> mutator) throws ExceptionControlApp {

		/* Cassandra may be chocked by too many concurrent requests so throttle it and retry a few times. */
		MutationResult mutationResult = null;
		for(int i=0;i<3;i++) {
			try {
				mutationResult = mutator.execute(); // Has no affect in case of immediate updates
				break;
			} catch (Throwable e) {}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		if(mutationResult == null) {
			log.error("Failed to flush to Cassandra mutator cached repo updates.");
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to flush to Cassandra mutator cached repo updates.");
		}

		log.trace("mutator execution time in usecs:"+mutationResult.getExecutionTimeMicro()+", host:"+mutationResult.getHostUsed());
	}

	/** Get Keys of all non empty (deleted) rows.
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public List<K> getKeys() throws ExceptionControlApp {
		/* We do not use keys-only as it may return keys for empty (deleted) rows. Instead we retrieve at least
		 * one column, just to make sure it is not a deleted row.
		 */
		ArrayList<K> resultKeys = new ArrayList<K>();
		K key;

		/* Cassandra may be chocked by too many concurrent requests so throttle it and retry a few times. */
		QueryResult<OrderedRows<K, String, byte[]>> queryResult = retryExecute(keysQuery, 3);
		if(queryResult == null)	return null;

		OrderedRows<K, String, byte[]> rows = queryResult.get();
		if(rows == null) return resultKeys;
		Iterator<Row<K, String, byte[]>> rowsIter = rows.iterator();		
		if(rowsIter == null) return resultKeys; // Return empty list. Could also return null.

		Row<K, String, byte[]> row;
		while (rowsIter.hasNext()) {        	
			row = rowsIter.next();  	
			key = row.getKey();
			if(!(row.getColumnSlice().getColumns().isEmpty())) 
				resultKeys.add(key);
		}

		return resultKeys;		
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public Hashtable<K, Hashtable<String, Object>> getTable() {

		K lastKey = null;

		Hashtable<K, Hashtable<String, Object>> resultTable = new Hashtable<K, Hashtable<String, Object>>();
		Hashtable<String, Object> resultRow; Object resultCellValue; String resultColumnName;

		QueryResult<OrderedRows<K, String, byte[]>> chunkQueryResult; OrderedRows<K, String, byte[]> rows; 
		Iterator<Row<K, String, byte[]>> rowsIter; Row<K, String, byte[]> row; 
		Iterator<HColumn<String, byte[]>> columnIter; HColumn<String, byte[]> hColumn; 
		Serializer<?> valueSerializer; byte [] bytesValue; 

		while (true) { // Iterate overall chunks

			tableQuery.setKeys(lastKey, null);

			/* Cassandra may be chocked by too many concurrent requests so throttle it and retry a few times. */
			chunkQueryResult = retryExecute(tableQuery, 3);
			if(chunkQueryResult == null) return null; 

			rows = chunkQueryResult.get();
			if(rows == null) break;
			rowsIter = rows.iterator();
			if(rowsIter == null) break;

			if (lastKey != null && rowsIter.hasNext())
				rowsIter.next(); // skip this first one, since it is the same as the last one from previous time we executed

			while (rowsIter.hasNext()) { // Iterate over a single row chunk

				row = rowsIter.next();
				if(row == null) {
					log.error("Received null row. Skipping.");
					rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;
				}
				lastKey = row.getKey();              
				columnIter = row.getColumnSlice().getColumns().iterator();
				if(columnIter == null) continue;
				resultRow = new Hashtable<String, Object>();

				try {
					while(columnIter.hasNext()) { // Iterate over a single row in a row chunk
						hColumn = columnIter.next();
						resultColumnName = hColumn.getName();
						bytesValue = hColumn.getValue();
						valueSerializer = getValueSerializer(resultColumnName, objSerializer);
						resultCellValue = valueSerializer.fromBytes(bytesValue);
						resultRow.put(resultColumnName, resultCellValue);
					}
				} catch (Throwable e) {
					log.error("Failed to retrieve row " + lastKey + ". " + e.getLocalizedMessage());
					rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
					continue;
				}
				if(resultRow.size() > 0) // Not a cassandra tombestone row
					resultTable.put(lastKey, resultRow);
			}

			if (rows.getCount() < MAX_ROWS_CHUNK_TO_RETURN) break; // Got the last chunk of rows				
		}

		return resultTable;
	}

	/** If the entry of the specified key already exists in Repo, then any cell in the passed in entry with value different 
	 * than the value of the latest recorded cell overrides the currently stored value.
	 * @param param_name param description. 
	 * @return return entry id (key column value)
	 * @throws ExceptionControlApp 
	 * @throws ExceptionKey If null or empty non-integer key is passed
	 */
	@SuppressWarnings("unchecked")
	public void setRow(K key, Hashtable<String, Object> cells) throws ExceptionControlApp {		

		Asserter.assertNonNullObjectParam(key, "key", log);
		Asserter.assertNonNullObjectParam(cells, "cells", log);

		Iterator<String> columnNamesIterator = cells.keySet().iterator();
		String columnName = ""; Object cellValue = ""; Serializer<Object> valueSerializer; HColumn<String, Object> hColumn;
		Mutator<K> mutor = repoDesc.immediateFlush ? mutator : opMutator;

		try {
			while(columnNamesIterator.hasNext()) {			
				columnName = columnNamesIterator.next();
				cellValue = cells.get(columnName);
				valueSerializer = (Serializer<Object>) cfTemplate.getValueSerializer(columnName);
				if (valueSerializer == null) // Not a registered column. Will simply leave it as bytes array (byte[]).
					valueSerializer = objSerializer;
				hColumn = HFactory.createColumn(columnName, cellValue, stringSerializer, valueSerializer);
				mutor.addInsertion(key, repoDesc.repoName, hColumn);
			}
		} catch (Throwable e) {
			if(columnName == null) columnName = ""; if(cellValue == null) cellValue = "";
			log.error("Failed to populate a column to mutator "+columnName+":"+cellValue+". "+e.getLocalizedMessage());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			if(repoDesc.immediateFlush)	// Mutator only contains updates from this setRow, so we can safely cancel them
				mutor.discardPendingMutations();
			// Otherwise the mutator may contain updates from multiple methods, so we prefer inconsistency of this row
			// to discarding other updates.
		}

		if(repoDesc.immediateFlush)
			applyUpdateBatch(mutor);
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void deleteRow(K key) throws ExceptionControlApp {

		Asserter.assertNonNullObjectParam(key, "key", log);
		Mutator<K> mutor = repoDesc.immediateFlush ? mutator : opMutator;

		try {
			mutor.addDeletion(key, repoDesc.repoName);
		} catch (Throwable e) {
			log.error("Failed to delete row " + key + ". " + e.getLocalizedMessage());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to delete row " + key + ". ", e);
		}
		if(repoDesc.immediateFlush)
			applyUpdateBatch(mutor);
	}

	/** This method deletes all rows.
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void truncate() {

		for(int i=0;i<3;i++) {
			try {
				rFactory.ctrlAppsCluster.truncate(rFactory.dbName, repoDesc.repoName);
				break;
			} catch (Throwable e) {
				log.error("Failed to truncate repo"+repoDesc.repoName+". "+e.getLocalizedMessage());
				rFactory.fMainImpl.frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE,"Internal Database Failure");
				rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			}
		}
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the all entry cells (including the empty ones).
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public Hashtable<String, Object> getRow(K key) throws ExceptionControlApp {

		rowQuery.setKey(key).setRange(null, null, false, MAX_COLUMNS_TO_RETURN);

		/* Cassandra may be chocked by too many concurrent requests so throttle it and retry a few times. */
		QueryResult<ColumnSlice<String, byte[]>> result = null;
		for(int i=0;i<3;i++) {
			try {
				result = rowQuery.execute();
				break;
			} catch (Throwable e) {
				log.error("Failed to execute row query for " + key + ". " + e.getLocalizedMessage());
				rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		if(result == null) return null;

		List<HColumn<String, byte[]>> hColumns = result.get().getColumns();
		if(hColumns.size() == 0) return null; // Cassandra returns empty row (no columns) for non existing keys.

		Iterator<HColumn<String, byte[]>> iter = hColumns.iterator();
		Hashtable<String, Object> cells = new Hashtable<String, Object>();
		HColumn<String, byte[]> hColumn; String columnName; Serializer<?> valueSerializer; byte [] bytesValue; 
		Object value;

		try {
			while(iter.hasNext()) {
				hColumn = iter.next();
				columnName = hColumn.getName();
				bytesValue = hColumn.getValue();
				valueSerializer = getValueSerializer(columnName, objSerializer);
				value = valueSerializer.fromBytes(bytesValue);
				cells.put(columnName, value);
			}
		} catch (Throwable e) {
			log.error("Failed to execute row query for " + key + ". " + e.getLocalizedMessage());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to populate received row " + key + ". ", e);
		}

		return cells;
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return ordered list of record column names.
	 * @throws exception_type circumstances description 
	 */
	public List<String> getOrderedColumns(K key, int number, boolean reverse) {
		return getOrderedColumns ( key, number, reverse, null);
	}

	public List<String> getOrderedColumns(K key, int number, boolean reverse, List<?> filterList) {

		rowQuery.setKey(key);

		ColumnSliceIterator<K, String, byte[]> iterator;
		if (! reverse)
			iterator = new ColumnSliceIterator<K, String, byte[]>(rowQuery, null, "\uFFFF", false);
		else 
			iterator = new ColumnSliceIterator<K, String, byte[]>(rowQuery, "\uFFFF", "",  true); 
		List<String> listColumns = new ArrayList<String>();		

		while (iterator.hasNext()  ) {

			HColumn<String, byte[]> hColumn = null;
			try {				
				hColumn = iterator.next();				
				if (filterList != null ) {
					byte[] bytesValue = hColumn.getValue();
					Serializer<?> valueSerializer = getValueSerializer((String) hColumn.getName(), objSerializer);
					Object value = valueSerializer.fromBytes(bytesValue);

					if ( ! filterList.contains(value)) continue;
				}				
				listColumns.add(hColumn.getName());
				if (number != 0 && listColumns.size() >= number ) break;
			} catch (Throwable e) {
				String colStr = (hColumn == null) ? "" : hColumn.getName();
				if(colStr == null) colStr = "";
				log.error("Failed to retrieve cell " + colStr + ". " + e.getLocalizedMessage());
				rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			}
		}       

		return listColumns;
	}


	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public Object getCellValue(K key, String columnName) throws ExceptionControlApp {

		Asserter.assertNonNullObjectParam(key, "key", log);
		Asserter.assertNonEmptyStringParam(columnName, "columnName", log);

		cellQuery.setKey(key).setName(columnName);  

		/* Cassandra may be chocked by too many concurrent requests so throttle it and retry a few times. */
		QueryResult<HColumn<String, byte[]>> queryResult = null; boolean reported = false;
		for(int i=0;i<3;i++) {
			try {
				queryResult = cellQuery.execute();
				break;
			} catch (Throwable e) {
				if(!reported) {
					log.info("Failed to execute cell query for "+ key +": " + columnName + e.getLocalizedMessage() +
							"could be because the column has not yet been set.");
					reported = true;
				}
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}
		if(queryResult == null) return null;

		Object value; HColumn<String, byte[]> hColumn;
		try {
			hColumn = queryResult.get();
			if(hColumn == null) return null;
			byte[] bytesValue = hColumn.getValue();
			if(bytesValue == null) return null;
			Serializer<?> valueSerializer = getValueSerializer(columnName, objSerializer);
			value = valueSerializer.fromBytes(bytesValue);
		} catch (Throwable e) {
			log.error("Failed to inflate cell " + key +" : " + columnName + ". " + e.toString());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to inflate cell " + key +": " + columnName + ". ", e);
		}
		return value;
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description
	 */
	@Override
	public boolean hasCell(K key, String columnName) {

		if(key == null || columnName == null) return false; 

		QueryResult<HColumn<String, byte[]>> queryResult = null;
		try {
			cellQuery.setKey(key).setName(columnName); 
			queryResult = cellQuery.execute();
			HColumn<String, byte[]> hColumn = queryResult.get();
			if(hColumn == null) return false;
			byte[] bytesValue = hColumn.getValue();
			if(bytesValue == null) return false;
			return true;
		} catch (Throwable e) {
			return false; // Exception is expected when cell does not exists.
		}
	}

	/** If the cell of the specified key and column name  has value different than the value of the 
	 * latest recorded cell then the passed in value overrides the currently stored value.
	 * @param param_name param description. 
	 * @return return entry id (key column value)
	 * @throws ExceptionControlApp 
	 * @throws ExceptionKey If null or empty non-integer key is passed
	 */
	@SuppressWarnings("unchecked")
	public void setCell(K key, String columnName, Object cellValue) throws ExceptionControlApp {		

		Asserter.assertNonNullObjectParam(key, "key", log);
		Asserter.assertNonNullObjectParam(columnName, "columnName", log);

		Mutator<K> mutor = repoDesc.immediateFlush ? mutator : opMutator;

		Serializer<Object> valueSerializer;
		HColumn<String, Object> hColumn;
		try {
			valueSerializer = (Serializer<Object>) cfTemplate.getValueSerializer(columnName);
			if (valueSerializer == null) // Not a registered column. Will simply leave it as bytes array (byte[]).
				valueSerializer = objSerializer;
			hColumn = HFactory.createColumn(columnName, cellValue, stringSerializer, valueSerializer);
			mutor.addInsertion(key, repoDesc.repoName, hColumn);
		} catch (Exception e) {
			log.error("Failed to populate a column to mutator "+columnName +":"+cellValue+". "+e.getLocalizedMessage());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			if(repoDesc.immediateFlush)	// Mutator only contains updates from this setRow, so we can safely cancel them
				mutor.discardPendingMutations();
			// Otherwise the mutator may contain updates from multiple methods, so we prefer inconsistency of this row
			// to discarding other updates.
		}
		if(repoDesc.immediateFlush)
			applyUpdateBatch(mutor);
	}

	protected QueryResult<OrderedRows<K,String,byte[]>> retryExecute(RangeSlicesQuery<K,String,byte[]> query, int retries) {

		QueryResult<OrderedRows<K,String,byte[]>> queryResult = null;		
		for(int i=0;i<3;i++) {
			try {
				queryResult = query.execute(); // Has no affect in case of immediate updates
				break;
			} catch (Throwable e) {
				log.error("Failed to execute slice query. " + e.getLocalizedMessage());
				rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {}
		}		
		return queryResult;
	}

	protected Serializer<?> getValueSerializer(String columnName, Serializer<?> defaultSerializer) {

		Asserter.assertNonEmptyStringParam(columnName, "columnName", log);
		Asserter.assertNonNullObjectParam(defaultSerializer, "defaultSerializer", log);

		Serializer<?> valueSerializer = cfTemplate.getValueSerializer(columnName);
		if (valueSerializer == null) // Not a registered column. use default serializer.
			return defaultSerializer;
		return valueSerializer;
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void deleteCell(K key, String columnName) throws ExceptionControlApp {

		Asserter.assertNonNullObjectParam(key, "key", log);
		Asserter.assertNonEmptyStringParam(columnName, "columnName", log);

		Mutator<K> mutor = repoDesc.immediateFlush ? mutator : opMutator;

		try {
			mutor.addDeletion(key, repoDesc.repoName, columnName, stringSerializer);
		} catch (Throwable e) {
			log.error("Failed to delete row " + key + ". " + e.getLocalizedMessage());
			rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			throw new ExceptionControlApp("Failed to delete row " + key + ". ", e);
		}
		if(repoDesc.immediateFlush)
			applyUpdateBatch(mutor);		
	}

	/** Delete all fields from the record.
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void deleteCells(K key, List<String> cellKeys) throws ExceptionControlApp {

		Asserter.assertNonNullObjectParam(key, "key", log);
		Asserter.assertNonNullObjectParam(cellKeys, "cellKeys", log);

		Mutator<K> mutor = repoDesc.immediateFlush ? mutator : opMutator;
		for(String cellKey : cellKeys) {
			try {
				mutor.addDeletion(key, repoDesc.repoName, cellKey, stringSerializer);
			} catch (Throwable e) {
				log.error("Failed to delete cells in row " + key + ". " + e.getLocalizedMessage());
				rFactory.fMainImpl.healthTrackerImpl.reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
				throw new ExceptionControlApp("Failed to delete cells in row " + key + ". ", e);
			}
		}
		if(repoDesc.immediateFlush)
			applyUpdateBatch(mutor);		
	}
}
