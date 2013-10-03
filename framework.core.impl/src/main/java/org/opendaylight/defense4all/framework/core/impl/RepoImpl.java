/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;





import org.opendaylight.defense4all.framework.core.Asserter;
import org.opendaylight.defense4all.framework.core.ExceptionKey;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoCD;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.cassandra.serializers.ObjectSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
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

/**
 * 
 * @author gerag
 *
 * @param <K> - The type of the Repo key column
 */
public class RepoImpl<K> implements Repo<K> {
	
	private static final int MAX_COLUMNS_TO_RETURN = 1000;
	private static final int MAX_ROWS_CHUNK_TO_RETURN = 10000; // Cassandra may start timing out at 100000 rows at a time

	protected RepoDescription mRepoDescription;	
	protected RepoFactoryImpl mRepoFactory = null;
	protected Serializer<K> mKeySerializer;	
	protected ColumnFamilyTemplate<K, String> mCFTemplate = null;
	protected Mutator<K> mMutator = null;
	protected Serializer<String> mStringSerializer = StringSerializer.get();
	protected Serializer<byte[]> mBytesArraySerializer = BytesArraySerializer.get();
	protected Serializer<Object> mObjectSerializer = ObjectSerializer.get();
	RangeSlicesQuery<K, String, byte[]> mTableQuery = null;
	RangeSlicesQuery<K, String, byte[]> mKeysQuery = null;
	SliceQuery<K,String, byte[]> mRowQuery = null;
	ColumnQuery<K, String, byte[]> mCellQuery = null;
	
	/** Constructor - Corresponds to inflation by Hector EntityManager
	 * @param mRepoFactoryImpl 
	 * @param param_name param description
	 * @throws exception_type circumstances description 
	 */
	@SuppressWarnings("unchecked")
	public RepoImpl(FrameworkMainImpl frameworkMain, RepoDescription repoDescription) {
		
		this.mRepoDescription = repoDescription;
		
		@SuppressWarnings("rawtypes")
		Class<? extends Serializer> c;
		String keySerializerClassName = repoDescription.keySerializerClassName;
		String invalidClassMsg = "Invalid key serializer class name provided - \"" + keySerializerClassName + "\". ";
		try {
			c = Class.forName(keySerializerClassName).asSubclass(Serializer.class);	
			mKeySerializer = c.newInstance();
		} catch (ClassNotFoundException e) {
			throw new IllegalArgumentException(invalidClassMsg + " Encapsulated exception:" + e.getLocalizedMessage());
		} catch (InstantiationException e) {
			throw new IllegalArgumentException(invalidClassMsg + " Encapsulated exception:" + e.getLocalizedMessage());
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException(invalidClassMsg + " Encapsulated exception:" + e.getLocalizedMessage());
		}
		
		mRepoFactory = frameworkMain.repoFactoryImpl;
		mCFTemplate = new ThriftColumnFamilyTemplate<K, String>(mRepoFactory.mCtrlAppsKS, mRepoDescription.repoName, mKeySerializer, mStringSerializer);
		mMutator = HFactory.createMutator(mRepoFactory.mCtrlAppsKS, mKeySerializer);
		mTableQuery = HFactory.createRangeSlicesQuery(mRepoFactory.mCtrlAppsKS, mKeySerializer, mStringSerializer, mBytesArraySerializer);
		mTableQuery.setColumnFamily(mRepoDescription.repoName).setRange(null, null, false, MAX_COLUMNS_TO_RETURN).setRowCount(MAX_ROWS_CHUNK_TO_RETURN);

		mKeysQuery = HFactory.createRangeSlicesQuery(mRepoFactory.mCtrlAppsKS, mKeySerializer, mStringSerializer, mBytesArraySerializer);
		mKeysQuery.setColumnFamily(mRepoDescription.repoName).setRange(null, null, false, 1).setRowCount(MAX_ROWS_CHUNK_TO_RETURN);

//		mKeysQuery = HFactory.createRangeSlicesQuery(mRepoFactory.mCtrlAppsKS, mKeySerializer, mStringSerializer, mBytesArraySerializer);
//		mKeysQuery.setColumnFamily(mRepoDescription.repoName).setKeys(null, null).setReturnKeysOnly();
		
		mRowQuery = HFactory.createSliceQuery(mRepoFactory.mCtrlAppsKS, mKeySerializer, mStringSerializer, mBytesArraySerializer);
		mRowQuery.setColumnFamily(mRepoDescription.repoName);
		mCellQuery = HFactory.createColumnQuery(mRepoFactory.mCtrlAppsKS, mKeySerializer, mStringSerializer, mBytesArraySerializer);
		mCellQuery.setColumnFamily(mRepoDescription.repoName);
	}

	/** Pre-shutdown cleanup
	 */
	public void finit() {
		applyUpdateBatch();
	}

	/** Factory reset
	 */
	public void factoryReset() {
		// delete repo and remove any externally visible effects, like external notifications
	}

	/** Adds column description to the table (no need to add the key column).
	 * @param param_name param description
	 * @throws exception_type circumstances description 
	 */
	public void addColumnDescription(RepoCD columnDescription) {
		if (columnDescription == null)
			return;
		mCFTemplate.addColumn(columnDescription.columnName, columnDescription.columnValueSerializer);
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void applyUpdateBatch() {
		@SuppressWarnings("unused")
		MutationResult mutationResult = mMutator.execute(); // Has no affect in case of immediate updates
		// TODO: add to detailed trace log: execution time and cassandra host on which the operation succeeded.
	}

	/** Get Keys of all non empty (deleted) rows.
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public List<K> getKeys() {
		/* We do not use keys-only as it may return keys for empty (deleted) rows. Instead we retrieve at least
		 * one column, just to make sure it is not a deleted row.
		 */
		ArrayList<K> resultKeys = new ArrayList<K>();
		K key;
		
		QueryResult<OrderedRows<K, String, byte[]>> queryResult = mKeysQuery.execute();		
		Iterator<Row<K, String, byte[]>> rowsIter = queryResult.get().iterator();		
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
			
			mTableQuery.setKeys(lastKey, null);
            chunkQueryResult = mTableQuery.execute();
            
            rows = chunkQueryResult.get();
            rowsIter = rows.iterator();
            if(rowsIter == null)
            	break;
            
            if (lastKey != null) 
            	rowsIter.next(); // skip this first one, since it is the same as the last one from previous time we executed
            
            while (rowsIter.hasNext()) { // Iterate over a single row chunk
            	
              row = rowsIter.next();
              lastKey = row.getKey();              
              columnIter = row.getColumnSlice().getColumns().iterator();
              if(columnIter == null)
            	  continue;
              resultRow = new Hashtable<String, Object>();
              
              while(columnIter.hasNext()) { // Iterate over a single row in a row chunk
              	hColumn = columnIter.next();
              	resultColumnName = hColumn.getName();
              	bytesValue = hColumn.getValue();
              	valueSerializer = getValueSerializer(resultColumnName, mObjectSerializer);
              	resultCellValue = valueSerializer.fromBytes(bytesValue);
              	resultRow.put(resultColumnName, resultCellValue);
              }
              if(resultRow.size() > 0) // Not a cassandra tombestone row
            	  resultTable.put(lastKey, resultRow);
            }

            if (rows.getCount() < MAX_ROWS_CHUNK_TO_RETURN) // Got the last chunk of rows
                break;
        }
		
		return resultTable;
	}

	/** If the entry of the specified key already exists in Repo, then any cell in the passed in entry with value different 
	 * than the value of the latest recorded cell overrides the currently stored value.
	 * @param param_name param description. 
	 * @return return entry id (key column value)
	 * @throws ExceptionKey If null or empty non-integer key is passed
	 */
	@SuppressWarnings("unchecked")
	public void setRow(K key, Hashtable<String, Object> cells) {		

		Asserter.assertNonNullObjectParam(key, "key");
		Asserter.assertNonNullObjectParam(cells, "cells");
		
		Iterator<String> columnNamesIterator = cells.keySet().iterator();
		String columnName; Object cellValue; Serializer<Object> valueSerializer; HColumn<String, Object> hColumn;
		while(columnNamesIterator.hasNext()) {
			columnName = columnNamesIterator.next();
			cellValue = cells.get(columnName);
			valueSerializer = (Serializer<Object>) mCFTemplate.getValueSerializer(columnName);
        	if (valueSerializer == null) // Not a registered column. Will simply leave it as bytes array (byte[]).
        		valueSerializer = mObjectSerializer;
			hColumn = HFactory.createColumn(columnName, cellValue, mStringSerializer, valueSerializer);
			mMutator.addInsertion(key, mRepoDescription.repoName, hColumn);
		}
		if(mRepoDescription.immediateFlush)
			applyUpdateBatch();
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void deleteRow(K key) {

		Asserter.assertNonNullObjectParam(key, "key");
		
		mMutator.addDeletion(key, mRepoDescription.repoName);
		if(mRepoDescription.immediateFlush)
			applyUpdateBatch();
	}

	/** This method deletes all rows.
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	@Override
	public void truncate() {
		mRepoFactory.mCtrlAppsCluster.truncate(mRepoFactory.dbName, mRepoDescription.repoName);
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the all entry cells (including the empty ones).
	 * @throws exception_type circumstances description 
	 */
	public Hashtable<String, Object> getRow(K key) {
		
        mRowQuery.setKey(key).setRange(null, null, false, MAX_COLUMNS_TO_RETURN);
        QueryResult<ColumnSlice<String,byte[]>> result = mRowQuery.execute();
        
        List<HColumn<String, byte[]>> hColumns = result.get().getColumns();
        if(hColumns.size() == 0) return null; // Cassandra returns empty row (no columns) for non existing keys.
        
        Iterator<HColumn<String, byte[]>> iter = hColumns.iterator();
        Hashtable<String, Object> cells = new Hashtable<String, Object>();
        HColumn<String, byte[]> hColumn; String columnName; Serializer<?> valueSerializer; byte [] bytesValue; 
        Object value;
        while(iter.hasNext()) {
        	hColumn = iter.next();
        	columnName = hColumn.getName();
        	bytesValue = hColumn.getValue();
        	valueSerializer = getValueSerializer(columnName, mObjectSerializer);
        	value = valueSerializer.fromBytes(bytesValue);
        	cells.put(columnName, value);
        }

        return cells;
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public Object getCellValue(K key, String columnName) {
		
		Asserter.assertNonNullObjectParam(key, "key");
		Asserter.assertNonEmptyStringParam(columnName, "columnName");
		
    	mCellQuery.setKey(key).setName(columnName);    	
    	QueryResult<HColumn<String, byte[]>> queryResult = mCellQuery.execute();
    	
    	HColumn<String, byte[]> hColumn = queryResult.get();
    	byte[] bytesValue = hColumn.getValue();
        Serializer<?> valueSerializer = getValueSerializer(columnName, mObjectSerializer);
        Object value = valueSerializer.fromBytes(bytesValue);
        return value;
	}

	/** If the cell of the specified key and column name  has value different than the value of the 
	 * latest recorded cell then the passed in value overrides the currently stored value.
	 * @param param_name param description. 
	 * @return return entry id (key column value)
	 * @throws ExceptionKey If null or empty non-integer key is passed
	 */
	@SuppressWarnings("unchecked")
	public void setCell(K key, String columnName, Object cellValue) {		

		Asserter.assertNonNullObjectParam(key, "key");
		Asserter.assertNonNullObjectParam(columnName, "columnName");

		Serializer<Object> valueSerializer = (Serializer<Object>) mCFTemplate.getValueSerializer(columnName);
		if (valueSerializer == null) // Not a registered column. Will simply leave it as bytes array (byte[]).
			valueSerializer = mObjectSerializer;
		HColumn<String, Object> hColumn = HFactory.createColumn(columnName, cellValue, mStringSerializer, valueSerializer);
		mMutator.addInsertion(key, mRepoDescription.repoName, hColumn);
		if(mRepoDescription.immediateFlush)
			applyUpdateBatch();
	}
	
	protected Serializer<?> getValueSerializer(String columnName, Serializer<?> defaultSerializer) {

		Asserter.assertNonEmptyStringParam(columnName, "columnName");
		Asserter.assertNonNullObjectParam(defaultSerializer, "defaultSerializer");
		
        Serializer<?> valueSerializer = mCFTemplate.getValueSerializer(columnName);
        if (valueSerializer == null) // Not a registered column. use default serializer.
        	return defaultSerializer;
		return valueSerializer;
	}

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void deleteCell(K key, String columnName) {

		Asserter.assertNonNullObjectParam(key, "key");
		Asserter.assertNonEmptyStringParam(columnName, "columnName");
		
		mMutator.addDeletion(key, mRepoDescription.repoName, columnName, mStringSerializer);
		if(mRepoDescription.immediateFlush)
			applyUpdateBatch();
	}
}
