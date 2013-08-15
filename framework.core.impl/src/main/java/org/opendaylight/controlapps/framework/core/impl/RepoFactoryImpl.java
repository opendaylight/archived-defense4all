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
import java.util.Map;

import org.opendaylight.controlapps.framework.core.Asserter;
import org.opendaylight.controlapps.framework.core.EM;
import org.opendaylight.controlapps.framework.core.ExceptionEntityExists;
import org.opendaylight.controlapps.framework.core.ExceptionRepoFactoryInternalError;
import org.opendaylight.controlapps.framework.core.RepoCD;
import org.opendaylight.controlapps.framework.core.RepoFactory;
import org.opendaylight.controlapps.framework.core.SerializersSerializer;
import org.opendaylight.controlapps.framework.core.FrameworkMain.ResetLevel;


import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.cassandra.service.ThriftKsDef;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.ComparatorType;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.exceptions.HInvalidRequestException;
import me.prettyprint.hector.api.factory.HFactory;


public class RepoFactoryImpl extends FrameworkModule implements RepoFactory {
	
	// Local Cassandra server connectivity
	protected static final String LOCAL_HOST = "localhost";
	protected short cassandraServerPort; // Port number on which local Cassandra server listens for client requests
	
	// Cassandra cluster
	protected Cluster mCtrlAppsCluster; // Hector object representing the Cassandra cluster
	protected String clusterName;
	
	// Hector-Cassandra DB (keyspace)
	protected String dbName;  // Cassandra DB (keyspace) name for this app. Multiple DBs (for multiple control apps) can coexist in a cassandra cluster
	protected KeyspaceDefinition mCtrlAppsKSDefinition;
	protected Keyspace mCtrlAppsKS;	// Cassandra DB
	protected int ctrlAppsKSReplLevel;
	
	/**
	 * Name space allocation of Repo factory REPO minor IDs (Repo factory itself places its state in these REPOs)
	 */
	public enum RepoMinor {
	
		INVALID("RFactoryInvalid"),
		REPO_DESCRIPTIONS("RepoDescriptions"),
		EM_DESCRIPTIONS("EMDescriptions");

		private String value;
		private RepoMinor(String value) {this.value = value;}		
		public String val() {return value;}
	}
	
	// All allocated Repo and EM objects to ensure singleton allocation for each Repo or EM description
	protected  Hashtable<String,RepoImpl<?>> mRepos = null;
	protected Hashtable<String, EM> mEMs = null;
	
	/* Constructor for Spring */
	public RepoFactoryImpl() {
		super();
		mEMs = new Hashtable<String, EM>();
		mRepos = new Hashtable<String, RepoImpl<?>>();
	}

	// Setters for Spring
	public void setClusterName(String clusterName) {this.clusterName = clusterName;}
	public void setDbName(String dbName) {this.dbName = dbName;}
	public void setCassandraServerPort(short port) {cassandraServerPort = port;}
	public void setCtrlAppsKSReplLevel(int ctrlAppsKSReplLevel) {this.ctrlAppsKSReplLevel = ctrlAppsKSReplLevel;}

	protected static String aggregateRepoName(String repoMajor, String repoMinor) {
		return (repoMajor + "_" + repoMinor);
	}
	
	public void init() {
    	
    	// Connect to the cassandra cluster. We assume the local machine runs a Cassandra cluster member. 
		// We connect to it and ask it to discover all other Cassandra instances in this cluster
    	CassandraHostConfigurator hostConfigurator = new CassandraHostConfigurator(LOCAL_HOST + ":" + cassandraServerPort);        
    	// comment out for single host installation
    	//hostConfigurator.setAutoDiscoverHosts(true);
    	mCtrlAppsCluster = HFactory.getOrCreateCluster(clusterName, hostConfigurator);
    	
    	// get or create the DB (keyspace), with default (simple) replication strategy
    	mCtrlAppsKSDefinition = mCtrlAppsCluster.describeKeyspace(dbName);
    	if (mCtrlAppsKSDefinition == null) { // not created yet
    		ArrayList<ColumnFamilyDefinition> emptyArr = new ArrayList<ColumnFamilyDefinition>();
			mCtrlAppsKSDefinition = HFactory.createKeyspaceDefinition(dbName, ThriftKsDef.DEF_STRATEGY_CLASS, ctrlAppsKSReplLevel, emptyArr);
			mCtrlAppsCluster.addKeyspace(mCtrlAppsKSDefinition);
    	}
    	
    	// get a Hector object that represents the DB (keyspace). This method does NOT create a new keyspace in Cassandra!
    	mCtrlAppsKS = HFactory.createKeyspace(dbName, mCtrlAppsCluster);    	
	}
	
	@Override
	public void finit() {
		
		// Flush all Entity Managers 
		Iterator<Map.Entry<String,EM>> emIter = mEMs.entrySet().iterator();
		EM em;
		while(emIter.hasNext()) {
			em = emIter.next().getValue();
			em.flush(); // Today HOM entity manager does nothing, but as a preparation for future...
		}
		
		// Flush all repos
		Iterator<Map.Entry<String, RepoImpl<?>>> repoIter = mRepos.entrySet().iterator();
		RepoImpl<?> repo;
		while(repoIter.hasNext()) {
			repo = repoIter.next().getValue();
			repo.finit();
		}
	}
	
	@Override
	public void reset(ResetLevel resetLevel) {
		if(resetLevel == ResetLevel.factory)
			mCtrlAppsCluster.dropKeyspace(dbName); // Remove all tables in this DB
	}

	/**
	 * Get EM instance to persist objects of classes in the provided class paths. 
	 * The same entity manager is returned per all requests of an entity manager for a given stateClassPaths
	 * RepoFactoryImpl will scan the classpath looking for all classes annotated with "@Entity" and "@Table(name=repoName)". 
	 * Then a request to persist an object to or load an object from the repo "repoName" will allow RepoFactoryImpl to map 
	 * the object into the appropriate repo (according to its class). Provided class paths are package names beneath 
	 * /src/main/java - e.g., a class path can be "mypackagewithstate".
	 * There are two primary requests to be invoked against the entity manager object
	 * Persist request: "entityManager.persist(stateObject);" The object will be persisted in a repo named as the object class,
	 * in a row whose key is the key field value in the stateObject.
	 * Find request: "stateObject = mEntityManager.find(StateObjectClass.class, rowKey);" The row number "rowKey" will be loaded
	 * from the repo named as StateObjectClass.class, and populated into stateObject.
	 * @param emId Id (name) of the requested entity manager
	 * @return return Entity manager through through which state objects can be persisted and loaded from corresponding repo
	 */
	@Override
	public EM getEM(String emId)  throws IllegalArgumentException {		
		
		Asserter.assertNonEmptyStringParam(emId, "emId");		
		
		EM em = mEMs.get(emId);
		if(em != null)
			return em;
		
		EMDescription emDescription;
		try {
			emDescription = frameworkMainImpl.frameworkEM.find(EMDescription.class, emId);
		} catch (HInvalidRequestException e) {
			return null;
		}
		if (emDescription == null)
			return null; // The user has not yet introduced the EM description
		
		em = new EMImpl(frameworkMainImpl, this, mCtrlAppsKS, emDescription); // Create the EM instance using its emDescription
		mEMs.put(emId, em);
		return em;
	}	

	/**
	 * Get or create EM instance to persist objects of classes in the provided class paths. 
	 * Once instantiated, the same entity manager instance is returned per all requests of an entity manager for a given 
	 * stateClassPaths. If not yet instantiated, check if its definition has already been provided in a previous lifecycle.
	 * If so use it. If not, record the definition passed in as the parameter, and use it to instantiate the EntityManager
	 * for this lifecycle.
	 * @param emId Id (name) of the requested entity manager
	 * @param stateClassPaths Class paths containing classes of objects to be persisted, delimited by colon (":"), 
	 * for example: class.path1:classpath2:also.class.path3
	 * @return return Entity manager through through which state objects can be persisted and loaded from corresponding repo
	 * @throws ExceptionEntityExists 
	 */
	@Override
	public EM getOrCreateEM(String emId, String stateClassPaths)  throws IllegalArgumentException {		

		Asserter.assertNonEmptyStringParam(emId, "emId");
		Asserter.assertNonEmptyStringParam(stateClassPaths, "stateClassPaths");
		
		EM em = mEMs.get(emId);
		if (em != null)
			return em; // It has already been instantiated in this lifecycle.
		
		// Need to instantiate the requested EntityManager. If its description has already been defined in a previous lifecycle
		// use it, otherwise create the EMDescription, record it and use the created one to create an EntityManager instance.		
		EMDescription emDescription;
		try {
			emDescription = frameworkMainImpl.frameworkEM.find(EMDescription.class, emId);
		} catch (HInvalidRequestException e) {
			emDescription = null;
		}
		
		if (emDescription == null) { // Need to create and record the description		
			emDescription = new EMDescription(emId, stateClassPaths); // Create the EMDescription
			frameworkMainImpl.frameworkEM.persist(emDescription);
		}
		
		em = new EMImpl(frameworkMainImpl, this, mCtrlAppsKS, emDescription); // Create the EM instance using its emDescription
		mEMs.put(emId, em);
		
		return em;
	}

	/**
	 * Create an EM instance to persist objects of classes in the provided class paths. Once instantiated, 
	 * the same entity manager instance is returned per all requests of an entity manager for a given stateClassPaths. 
	 * If not yet instantiated, create its EMDescription and use it to instantiate the EntityManager for this lifecycle.
	 * This method does not record the EMDescription in Cassandra (cannot use self to instantiate self).
	 * @param emId Id (name) of the requested entity manager
	 * @param stateClassPaths Class paths containing classes of objects to be persisted, delimited by colon (":"), 
	 * for example: class.path1:classpath2:also.class.path3
	 * @return return Entity manager through through which state objects can be persisted and loaded from corresponding repo
	 * @throws ExceptionEntityExists 
	 */
	@Override
	public EM createFrameworkMainEM(String emId, String stateClassPaths) 
			throws IllegalArgumentException, ExceptionEntityExists {		

		Asserter.assertNonEmptyStringParam(emId, "emId");
		Asserter.assertNonEmptyStringParam(stateClassPaths, "stateClassPaths");

		/* If corresponding column family has not yet been created in Cassandra - do it now. Column names are strings (UTF8TYPE).
		 * Need to check for EM_DESCRIPTIONS repo and REPO_DESCRIPTIONS repo.
		 */
		ColumnFamilyDefinition columnFamilyDefinition;
		String fEMDsRepoName = FrameworkMainImpl.RepoMajor.FWORK_REPO_FACTORY.name() + "_" + RepoFactoryImpl.RepoMinor.EM_DESCRIPTIONS.val();
		if(!columnFamilyExists(fEMDsRepoName)) {
	    	columnFamilyDefinition = HFactory.createColumnFamilyDefinition(dbName, fEMDsRepoName, ComparatorType.UTF8TYPE);
	    	mCtrlAppsCluster.addColumnFamily(columnFamilyDefinition);
		}
		String fREPODsRepoName = FrameworkMainImpl.RepoMajor.FWORK_REPO_FACTORY.name() + "_" + RepoFactoryImpl.RepoMinor.REPO_DESCRIPTIONS.val();
		if(!columnFamilyExists(fREPODsRepoName)) {
	    	columnFamilyDefinition = HFactory.createColumnFamilyDefinition(dbName, fREPODsRepoName, ComparatorType.UTF8TYPE);
	    	mCtrlAppsCluster.addColumnFamily(columnFamilyDefinition);
		}
		
		EMDescription emDescription = new EMDescription(emId, stateClassPaths); // Create the EMDescription		
		EM em = new EMImpl(frameworkMainImpl, this, mCtrlAppsKS, emDescription); // Create the EM instance using its emDescription
		mEMs.put(emId, em);		
		return em;
	}

	/**
	 * Get an object representing an already created repo
	 * @param RepoNameMajor a name uniquely identifying the component using this repo. 
	 * For framework or application global repos the component is the framework or the application respectively.
	 * @param RepoNameMinor a name uniquely identifying the repo among other repos the component uses. 
	 * @return requested repo object, or null if this repo has not yet been created
	 * @throws ExceptionRepoFactoryInternalError If there is an inconsistency between internal RepoFactoryImpl data structures
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public RepoImpl<?> getRepo(String RepoNameMajor, String RepoNameMinor) throws IllegalArgumentException {		

		Asserter.assertNonEmptyStringParam(RepoNameMajor, "RepoNameMajor");
		Asserter.assertNonEmptyStringParam(RepoNameMinor, "RepoNameMinor");
		
		String repoName = aggregateRepoName(RepoNameMajor, RepoNameMinor);

		RepoImpl<?> repo = mRepos.get(repoName);		
		if (repo != null)  // Java object for this repo has already been instantiated in this lifecycle. Reuse it as singleton.
			return repo;
		
		if (!columnFamilyExists(repoName))
			return null; // The repo has not been created yet
    	
		/* Java repo object has not been instantiated yet in this lifecycle	*/
		
		RepoDescription repoDescription = frameworkMainImpl.frameworkEM.find(RepoDescription.class, repoName);			
		if (repoDescription == null) 
			return null;
		
		repo = new RepoImpl(frameworkMainImpl, repoDescription); // Create the repo java object representing the CF created in Cassandra
		mRepos.put(repoName, repo); // and add it to the list of repos    	
		return repo;
	}

	/**
	 * Create a repo and return an object representing it
	 * @param RepoNameMajor a name uniquely identifying the component using this repo. 
	 * For framework or application global repos the component is the framework or the application respectively.
	 * @param RepoNameMinor a name uniquely identifying the repo among other repos the component uses.
	 * @param repoColumnDescriptions a set of column descriptions containing column characteristics, such as type of serialization and validation. 
	 * @return requested repo object
	 * @throws ExceptionRepoFactoryInternalError If there is an inconsistency between internal RepoFactoryImpl data structures
	 * @throws ExceptionEntityExists If the repo already exists
	 */
	@Override
	public <K> RepoImpl<K> createRepo(String RepoNameMajor, String RepoNameMinor, Serializer<K> keySerializer, 
			boolean immediateFlush, List<RepoCD> columnDescriptions ) 
			throws IllegalArgumentException, ExceptionEntityExists, ExceptionRepoFactoryInternalError  {

		Asserter.assertNonEmptyStringParam(RepoNameMajor, "RepoNameMajor");
		Asserter.assertNonEmptyStringParam(RepoNameMinor, "RepoNameMinor");
		Asserter.assertNonNullObjectParam(keySerializer, "keySerializer");
		
		String repoName = aggregateRepoName(RepoNameMajor, RepoNameMinor);

		/* If the repo description already exists in cassandra - use it. Otherwise create and persist one to repoDescriptions repo. */
		RepoDescription repoDescription = frameworkMainImpl.frameworkEM.find(RepoDescription.class, repoName);
		if(repoDescription == null) {
			SerializersSerializer sSerializer = SerializersSerializer.getInstance();
	    	repoDescription = new RepoDescription(repoName, sSerializer.toString(keySerializer), immediateFlush, columnDescriptions);
	    	frameworkMainImpl.frameworkEM.persist(repoDescription);			
		}
		
    	// If not yet created, create the column family in Cassandra for repo "repoName", with column types string (UTF8TYPE).
		if (!columnFamilyExists(repoName)) {
	    	ColumnFamilyDefinition columnFamilyDefinition = HFactory.createColumnFamilyDefinition(dbName, repoName, ComparatorType.UTF8TYPE);
	    	mCtrlAppsCluster.addColumnFamily(columnFamilyDefinition);
    	}		
		
    	// Create the repo java object representing the column family created in cassandra, and add it to the list of repos
		RepoImpl<K> repo = new RepoImpl<K>(frameworkMainImpl, repoDescription);
		mRepos.put(repoName, repo);
    	
		return repo;
	}

	/**
	 * Get a Java object representing a repo. Create the repo if non-existent. Create the java object if has not been 
	 * instantiated yet in this life-cycle.
	 * @param RepoNameMajor a name uniquely identifying the component using this repo. 
	 * For framework or application global repos the component is the framework or the application respectively.
	 * @param RepoNameMinor a name uniquely identifying the repo among other repos the component uses.
	 * @param repoColumnDescriptions a set of column descriptions containing column characteristics, such as type of serialization and validation. 
	 * @return requested repo object
	 * @throws ExceptionRepoFactoryInternalError If there is an inconsistency between internal RepoFactoryImpl data structures
	 * @throws ExceptionEntityExists If the repo already exists
	 */
	@Override
	public <K> RepoImpl<K> getOrCreateRepo(String RepoNameMajor, String RepoNameMinor, Serializer<K> keySerializer, 
			boolean immediateFlush,	List<RepoCD> columnDescriptions ) 
			throws IllegalArgumentException, ExceptionEntityExists, ExceptionRepoFactoryInternalError  {

		Asserter.assertNonEmptyStringParam(RepoNameMajor, "RepoNameMajor");
		Asserter.assertNonEmptyStringParam(RepoNameMinor, "RepoNameMinor");
		Asserter.assertNonNullObjectParam(keySerializer, "keySerializer");
		
		@SuppressWarnings("unchecked")
		RepoImpl<K> repo = (RepoImpl<K>) getRepo(RepoNameMajor, RepoNameMinor);
		if (repo == null) // Has not yet been created
			repo = (RepoImpl<K>) createRepo(RepoNameMajor, RepoNameMinor, keySerializer, immediateFlush, columnDescriptions);
		return repo;
	}
	
	protected boolean columnFamilyExists(String cfName) {
		
		Iterator<ColumnFamilyDefinition> iter = mCtrlAppsKSDefinition.getCfDefs().iterator();
		ColumnFamilyDefinition cfDef;
		while(iter.hasNext()) {
			cfDef = iter.next();
			if(cfDef.getName().equals(cfName))
				return true;
		}
		return false;
	}

	/**
	 * The table needs to be declared prior to persisting or finding the annotated state class, because
	 * the corresponding Column Family needs to be created in Cassandra in advance. This method is idempotent.
	 * @param tableName
	 */
	public void declareAnnotationTable(String tableName) {

		// If corresponding column family has not been created yet in Cassandra - do it now. Column names are strings (UTF8TYPE).
		if(!columnFamilyExists(tableName)) {
	    	ColumnFamilyDefinition columnFamilyDefinition = HFactory.createColumnFamilyDefinition(dbName, tableName, ComparatorType.UTF8TYPE);
	    	mCtrlAppsCluster.addColumnFamily(columnFamilyDefinition);
		}
	}
	
	@Override
	protected void actionSwitcher(int actionCode, Object param) {
		// TODO: check if decoupled execution is needed		
	}
}
