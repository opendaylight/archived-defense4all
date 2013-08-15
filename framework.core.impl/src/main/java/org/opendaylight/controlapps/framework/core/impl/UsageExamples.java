/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.core.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.opendaylight.controlapps.framework.core.AppRoot;
import org.opendaylight.controlapps.framework.core.EM;
import org.opendaylight.controlapps.framework.core.ExceptionEntityExists;
import org.opendaylight.controlapps.framework.core.ExceptionRepoFactoryInternalError;
import org.opendaylight.controlapps.framework.core.FrameworkMain;
import org.opendaylight.controlapps.framework.core.Repo;
import org.opendaylight.controlapps.framework.core.RepoCD;

/**
 * @author Gera Goft
 *
 */
public class UsageExamples {


	public void annotationsTest1(FrameworkMain fMain) {

		String DF_CORE_EM_ID = "df.core";
		String stateClassPaths = "org.opendaylight.controlapps.framework.core.impl";
		EM dfEM = fMain.getRepoFactory().getOrCreateEM(DF_CORE_EM_ID, stateClassPaths);
		
		AnnotatedState as0;
		AnnotatedState as1; 
		AnnotatedState as2;
		AnnotatedState as3;
		
		fMain.getRepoFactory().declareAnnotationTable("ASTable");
		
		// To see what is actually stored from last time.
		as3 = dfEM.find(AnnotatedState.class, "three"); as3.printObject();
		as2 = dfEM.find(AnnotatedState.class, "two"); as2.printObject();
		as1 = dfEM.find(AnnotatedState.class, "one"); as1.printObject();
		as0 = dfEM.find(AnnotatedState.class, "zero"); as0.printObject();
		
		as0 = new AnnotatedState("zero", "a0", true, "c0"); dfEM.persist(as0);
		as1 = new AnnotatedState("one", "a1", false, "c1"); dfEM.persist(as1);
		as2 = new AnnotatedState("two", "a2", true, "c2"); dfEM.persist(as2);
		as3 = new AnnotatedState("three", "a3", false, "c3"); dfEM.persist(as3);
		as0 = as1 = as2 = as3 = null;
		
		as3 = dfEM.find(AnnotatedState.class, "three"); as3.printObject();
		as2 = dfEM.find(AnnotatedState.class, "two"); as2.printObject();
		as1 = dfEM.find(AnnotatedState.class, "one"); as1.printObject();
		as0 = dfEM.find(AnnotatedState.class, "zero"); as0.printObject();

		as0 = new AnnotatedState("zero", null, false, null); dfEM.remove(as0, "zero");
		as1 = new AnnotatedState("one", null, false, null); dfEM.remove(as1, "one");
		as2 = new AnnotatedState("two", null, false, null); dfEM.remove(as2, "two");

		as3 = dfEM.find(AnnotatedState.class, "three"); if (as3 == null) System.out.println("as3 is null."); else as3.printObject();
		as2 = dfEM.find(AnnotatedState.class, "two"); if (as2 == null) System.out.println("as2 is null."); else as2.printObject();
		as1 = dfEM.find(AnnotatedState.class, "one"); if (as1 == null) System.out.println("as1 is null."); else as1.printObject();
		as0 = dfEM.find(AnnotatedState.class, "zero"); if (as0 == null) System.out.println("as0 is null."); else as0.printObject();

		as0 = new AnnotatedState("zero", "a00", true, "c00"); dfEM.persist(as0);
		as1 = new AnnotatedState("one", "a10", false, "c10"); dfEM.persist(as1);
		as2 = new AnnotatedState("two", "a20", true, "c20"); dfEM.persist(as2);
		as3 = new AnnotatedState("three", "a30", false, "c30"); dfEM.persist(as3);
		as0 = as1 = as2 = as3 = null;
		
		as3 = dfEM.find(AnnotatedState.class, "three"); as3.printObject();
		as2 = dfEM.find(AnnotatedState.class, "two"); as2.printObject();
		as1 = dfEM.find(AnnotatedState.class, "one"); as1.printObject();
		as0 = dfEM.find(AnnotatedState.class, "zero"); as0.printObject();
	}
	
	public void repoTest1(FrameworkMain fMain) {
		
		AppRoot appRoot = fMain.getAppRoot();
		
		List<RepoCD> cds = new ArrayList<RepoCD>();
		RepoCD cd;
		cd = new RepoCD("column0", appRoot.iSerializer, null); cds.add(cd);
		cd = new RepoCD("column1", appRoot.bSerializer, null); cds.add(cd);
		cd = new RepoCD("column2", appRoot.sSer, null); cds.add(cd);
		Repo<Integer> repo = null;
		try {
			repo =  fMain.getRepoFactory().getOrCreateRepo("MajorRepo", "MinorRepo", appRoot.iSerializer, true, cds);
		} catch (IllegalArgumentException e) {
			System.out.println(e); return;
		} catch (ExceptionEntityExists e) {
			System.out.println(e); return;
		} catch (ExceptionRepoFactoryInternalError e) {
			System.out.println(e); return;
		}

		cd = new RepoCD("cFly1", appRoot.sSer, null);
		repo.addColumnDescription(cd);
		
		Hashtable<String, Object> cells;
		cells = new Hashtable<String, Object>(); 
		cells.put("column0", 0); cells.put("column1", true); cells.put("column2", "col20"); cells.put("cFly1", "fly10");
		repo.setRow(0, cells);
		cells = new Hashtable<String, Object>(); 
		cells.put("column0", 1); cells.put("column1", false); cells.put("adhoc1", "ah10");
		repo.setRow(1, cells);
		cells = new Hashtable<String, Object>(); 
		cells.put("column2", "col22"); cells.put("adhoc2", "ah22");
		repo.setRow(2, cells);
		
		repo.applyUpdateBatch();
		
		System.out.println("Keys should be 0, 1, 2:");
		List<Integer> keys = repo.getKeys();
		for(Integer key: keys) System.out.print(key + ", "); 
		System.out.println();

		Hashtable<String,Object> row;
		for(Integer key: keys) {
			row = repo.getRow(key);
			printRow(key, row);
		}
		
		repo.deleteCell(0, "column1");
		row = repo.getRow(0);
		printRow(0, row);
		
		repo.deleteRow(2);
		Hashtable<Integer,Hashtable<String,Object>> table = repo.getTable();
		printTable(table);
	}
	
	public void printCell(Entry<String,Object> cell) {
		System.out.print(cell.getKey() + "," + cell.getValue() + "; ");
	}

	public void printRow(Integer key, Hashtable<String,Object> row) {
		System.out.print("key=" + key + ";");
		Entry<String,Object> cell;
		Iterator<Entry<String,Object>> iter = row.entrySet().iterator();
		while(iter.hasNext()) {
			cell = iter.next();
			printCell(cell);
		}
		System.out.println();
	}

	public void printTable(Hashtable<Integer,Hashtable<String,Object>> table) {
		Iterator<Entry<Integer,Hashtable<String,Object>>> iter = table.entrySet().iterator();
		Entry<Integer,Hashtable<String,Object>> rowAndKey; Integer key; Hashtable<String,Object> row;
		while(iter.hasNext()) {
			rowAndKey = iter.next();
			key = rowAndKey.getKey();
			row = rowAndKey.getValue();
			printRow(key, row);
		}
	}
}
