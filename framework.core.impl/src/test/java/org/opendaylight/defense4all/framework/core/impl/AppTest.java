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
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.serializers.ShortSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoCD;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {

	Logger log = LoggerFactory.getLogger(this.getClass());


	public boolean isLocalTest(){
		return ( System.getProperty("localTest") != null );
	}

	protected class SomeClass {

		public static final String KEY = "key";
		public static final String PNKEY = "pnKey";
		public static final String NODE_ID = "node_id";
		public static final String FLOOR = "floor";
		public static final String FLOW_CONFIG_INFO_KEY_PREFIX = "flow_config_info_key_";

		protected ArrayList<RepoCD> rcds = null;

		public String key;
		public String pnKey;
		public String nodeId;
		public short  floor;
		public HashMap<String,Object> fciKeys;

		public SomeClass(String key, String pnKey, String nodeId, short floor, HashMap<String,Object> fciKeys) {		
			this.key = key;this.pnKey =pnKey; this.nodeId =nodeId; this.floor =floor; this.fciKeys =fciKeys;
		}

		public SomeClass() {		
			this.key = null;this.pnKey =null; this.nodeId =null; this.floor =(short) 0; this.fciKeys =null;
		}

		public SomeClass(Hashtable<String, Object> row) {

			key = (String) row.get(KEY);
			pnKey = (String) row.get(PNKEY);
			nodeId = (String) row.get(NODE_ID);
			floor = (Short) row.get(FLOOR);

			fciKeys = new HashMap<String,Object>();
			Iterator<Map.Entry<String,Object>> iter = row.entrySet().iterator();
			Map.Entry<String,Object> entry;
			while(iter.hasNext()) {
				entry = iter.next();
				if(entry.getKey().startsWith(FLOW_CONFIG_INFO_KEY_PREFIX))
					fciKeys.put((String) entry.getValue(), null);
			}
		}

		public Hashtable<String, Object> toRow() {

			Hashtable<String, Object> row = new Hashtable<String, Object>();		

			row.put(KEY, key);
			row.put(PNKEY, pnKey);
			row.put(NODE_ID, nodeId);
			row.put(FLOOR, floor);
			Iterator<Map.Entry<String,Object>> iter = fciKeys.entrySet().iterator();
			String flowConfigInfoKey;
			while(iter.hasNext()) {
				flowConfigInfoKey = iter.next().getKey();
				row.put(FLOW_CONFIG_INFO_KEY_PREFIX + flowConfigInfoKey, flowConfigInfoKey);
			}
			return row;
		}

		public List<RepoCD> getRCDs() {

			if(rcds == null) {
				RepoCD rcd;
				rcds = new ArrayList<RepoCD>();			
				rcd = new RepoCD(KEY, StringSerializer.get(), null);			rcds.add(rcd);
				rcd = new RepoCD(PNKEY, StringSerializer.get(), null);			rcds.add(rcd);
				rcd = new RepoCD(NODE_ID, StringSerializer.get(), null);		rcds.add(rcd);
				rcd = new RepoCD(FLOOR, ShortSerializer.get(), null);			rcds.add(rcd);
			}		
			return rcds;
		}
	}	

	RepoFactoryImpl rf = null;
	Repo<String> someRepo = null;

	/* Create the test case */
	public AppTest( String testName ) {

		super( testName );
		
		if ( ! isLocalTest()) return;

		/* Get/create the test "SomeRepo" */
		rf = new RepoFactoryImpl();
		rf.clusterName = "df_cluster"; rf.dbName = "DF"; rf.cassandraServerPort = 9160; rf.ctrlAppsKSReplLevel = 1;
		FrameworkMainImpl frameworkMainImpl = new FrameworkMainImpl(null);
		frameworkMainImpl.frImpl = new FRImpl();
		rf.setFrameworkMain(frameworkMainImpl);
		try {
			rf.init();
		} catch (Throwable e1) {
			assertFalse("AppTest failed to init" + e1.getLocalizedMessage(), true);
		}
		try {
			SomeClass sc = new SomeClass();
			someRepo = (Repo<String>) rf.getCreateTestRepo("SomeRepo", StringSerializer.get(), false, sc.rcds);
		} catch (Throwable e) {
			assertFalse("AppTest failed to construct" + e.getLocalizedMessage(), true);
		}
	}

	@Override
	protected void setUp() throws Exception {   	
		super.setUp();
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite(){return new TestSuite( AppTest.class ); }

	/**
	 * Some test
	 */
	public void testAppTemp() {
		
		if ( ! isLocalTest()) return;

		HashMap<String,Object> fciKeys;
		SomeClass sc;
		long timeStart;
		//		System.out.println("starting to add records to someRepo");
		log.info("starting to add records to someRepo");
		timeStart = System.currentTimeMillis();
		try {
			for(int i=0;i<50000;i++) {
				fciKeys = new HashMap<String,Object>(); 
				fciKeys.put("fci"+i+"1", "fci"+i+"1value"); fciKeys.put("fci"+i+"2", "fci"+i+"2value");  
				fciKeys.put("fci"+i+"3", "fci"+i+"3value"); fciKeys.put("fci"+i+"4", "fci"+i+"4value");
				sc = new SomeClass("key"+i, "pnKey"+i, "node"+i, (short) 10, fciKeys);
				someRepo.setRow(sc.key, sc.toRow());
				if(i%10000 == 0) someRepo.applyUpdateBatch();
			}
			someRepo.applyUpdateBatch();
		} catch (Exception e) {
			assertFalse("exception adding records: " + e.getLocalizedMessage(), true);
		}
		System.out.println("ended adding records, time in msecs: " + (System.currentTimeMillis()- timeStart));

		timeStart = System.currentTimeMillis();
		System.out.println("starting to randomly read records from someRepo");
		try {
			int k; int min=0; int max=9998; Hashtable<String,Object> row;
			for(int i=0;i<10000;i++) {
				k = (int) ((min + (Math.random() * (max-min)) + 1));
				row = someRepo.getRow("key"+k);
				if(row != null)	sc = new SomeClass(row);
			}
		} catch (Exception e) {
			log.error("exception reading records: ", e);
			System.out.println("exception reading records: " + e.getLocalizedMessage());
			assertFalse("exception reading records: " + e.getLocalizedMessage(), true);
		}
		System.out.println("ended reading records, time in msecs: " + (System.currentTimeMillis()- timeStart));

		//assertTrue(true);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		if ( ! isLocalTest()) return;
		someRepo.truncate();
	}
}

