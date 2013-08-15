/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.framework.core;

import java.util.Hashtable;
import java.util.List;

//Repo name corresponds to FrameworkMain.RepoMajor.REPO_FACTORY + "." + RepoFactory.RepoMinor.Repos

/**
 * 
 * @author gerag
 *
 * @param <K> - The type of the Repo key column
 */
public interface Repo<K> {

	/** Adds column description to the table (no need to add the key column).
	 * @param param_name param description
	 * @throws exception_type circumstances description 
	 */
	public void addColumnDescription(RepoCD columnDescription);

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void applyUpdateBatch();

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public List<K> getKeys();

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public Hashtable<K, Hashtable<String, Object>> getTable();

	/** If the entry of the specified key already exists in Repo, then any cell in the passed in entry with value different 
	 * than the value of the latest recorded cell overrides the currently stored value.
	 * @param param_name param description. 
	 * @return return entry id (key column value)
	 * @throws ExceptionKey If null or empty non-integer key is passed
	 */
	public void setRow(K key, Hashtable<String, Object> cells);

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void deleteRow(K key);

	/** ##description ###
	 * @param param_name param description
	 * @return return the all entry cells (including the empty ones).
	 * @throws exception_type circumstances description 
	 */
	public Hashtable<String, Object> getRow(K key);

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void setCell(K key, String columnName, Object cellValue); 

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public Object getCellValue(K key, String columnName);

	/** ##description ###
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	public void deleteCell(K key, String columnName);

	/** Delete all rows in repo (truncate)
	 * @param param_name param description
	 * @return return the value of the specified entry cell
	 * @throws exception_type circumstances description 
	 */
	void truncate();
}
