/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.util.List;
import me.prettyprint.hector.api.Serializer;

public interface RepoFactory {

	/**
	 * Get EntityManager instance to persist objects of classes in the provided class paths. 
	 * The same entity manager is returned per all requests of an entity manager for a given stateClassPaths
	 * RepoFactory will scan the classpath looking for all classes annotated with "@Entity" and "@Table(name=repoName)". 
	 * Then a request to persist an object to or load an object from the repo "repoName" will allow RepoFactory to map 
	 * the object into the appropriate repo (according to its class). Provided class paths are package names beneath 
	 * /src/main/java - e.g., a class path can be "mypackagewithstate".
	 * There are two primary requests to be invoked against the entity manager object
	 * Persist request: "entityManager.persist(stateObject);" The object will be persisted in a repo named as the object class,
	 * in a row whose key is the key field value in the stateObject.
	 * Find request: "stateObject = mEntityManager.find(StateObjectClass.class, rowKey);" The row number "rowKey" will be loaded
	 * from the repo named as StateObjectClass.class, and populated into stateObject.
	 * @param emId Id (name) of the requested entity manager
	 * @return return Entity manager through through which state objects can be persisted and loaded from corresponding repo
	 * @throws IllegalArgumentException
	 * @throws ExceptionRepoFactoryInternalError 
	 */
	public EM getEM(String emId) throws IllegalArgumentException, IllegalArgumentException, ExceptionControlApp;

	/**
	 * Get or create EntityManager instance to persist objects of classes in the provided class paths. 
	 * Once instantiated, the same entity manager instance is returned per all requests of an entity manager for a given 
	 * stateClassPaths. If not yet instantiated, check if its definition has already been provided in a previous lifecycle.
	 * If so use it. If not, record the definition passed in as the parameter, and use it to instantiate the EntityManager
	 * for this lifecycle.
	 * @param emId Id (name) of the requested entity manager
	 * @param stateClassPaths Class paths containing classes of objects to be persisted, delimited by colon (":"), 
	 * for example: class.path1:classpath2:also.class.path3
	 * @return return Entity manager through through which state objects can be persisted and loaded from corresponding repo
	 * @throws ExceptionRepoFactoryInternalError 
	 * @throws ExceptionEntityExists 
	 */
	public EM getOrCreateEM(String emId, String stateClassPaths) throws IllegalArgumentException, ExceptionControlApp;

	/**
	 * Create an EntityManager instance to persist objects of classes in the provided class paths. Once instantiated, 
	 * the same entity manager instance is returned per all requests of an entity manager for a given stateClassPaths. 
	 * If not yet instantiated, create its EMDescription and use it to instantiate the EntityManager for this lifecycle.
	 * This method does not record the EMDescription in Cassandra (cannot use self to instantiate self).
	 * @param emId Id (name) of the requested entity manager
	 * @param stateClassPaths Class paths containing classes of objects to be persisted, delimited by colon (":"), 
	 * for example: class.path1:classpath2:also.class.path3
	 * @return return Entity manager through through which state objects can be persisted and loaded from corresponding repo
	 * @throws ExceptionEntityExists 
	 * @throws ExceptionRepoFactoryInternalError 
	 */
	public EM createFrameworkMainEM(String emId, String stateClassPaths) throws  IllegalArgumentException, 
		ExceptionEntityExists, ExceptionControlApp;

	/**
	 * Get an object representing an already created repo
	 * @param RepoNameMajor a name uniquely identifying the component using this repo. 
	 * For framework or application global repos the component is the framework or the application respectively.
	 * @param RepoNameMinor a name uniquely identifying the repo among other repos the component uses. 
	 * @return requested repo object, or null if this repo has not yet been created
	 * @throws ExceptionRepoFactoryInternalError If there is an inconsistency between internal RepoFactory data structures
	 */
	public Repo<?> getRepo(String RepoNameMajor, String RepoNameMinor) throws  IllegalArgumentException, 
	ExceptionControlApp;

	/**
	 * Create a repo and return an object representing it
	 * @param RepoNameMajor a name uniquely identifying the component using this repo. 
	 * For framework or application global repos the component is the framework or the application respectively.
	 * @param RepoNameMinor a name uniquely identifying the repo among other repos the component uses.
	 * @param repoColumnDescriptions a set of column descriptions containing column characteristics, such as type of serialization and validation. 
	 * @return requested repo object
	 * @throws ExceptionRepoFactoryInternalError If there is an inconsistency between internal RepoFactory data structures
	 * @throws ExceptionEntityExists If the repo already exists
	 */
	public <K> Repo<K> createRepo(String RepoNameMajor, String RepoNameMinor, Serializer<K> keySerializer, boolean immediateFlush,
			List<RepoCD> columnDescriptions) throws IllegalArgumentException, ExceptionEntityExists, ExceptionControlApp;

	/**
	 * Get a Java object representing a repo. Create the repo if non-existent. Create the java object if has not been 
	 * instantiated yet in this life-cycle.
	 * @param RepoNameMajor a name uniquely identifying the component using this repo. 
	 * For framework or application global repos the component is the framework or the application respectively.
	 * @param RepoNameMinor a name uniquely identifying the repo among other repos the component uses.
	 * @param repoColumnDescriptions a set of column descriptions containing column characteristics, such as type of serialization and validation. 
	 * @return requested repo object
	 * @throws ExceptionRepoFactoryInternalError If there is an inconsistency between internal RepoFactory data structures
	 * @throws ExceptionEntityExists If the repo already exists
	 */
	public <K> Repo<K> getOrCreateRepo(String RepoNameMajor, String RepoNameMinor, Serializer<K> keySerializer, 
			boolean immediateFlush,	List<RepoCD> columnDescriptions ) 
			throws IllegalArgumentException, ExceptionEntityExists, ExceptionControlApp;

	/**
	 * The table needs to be declared prior to persisting or finding the annotated state class, because
	 * the corresponding Column Family needs to be created in Cassandra in advance. This method is idempotent.
	 * @param tableName
	 * @throws ExceptionRepoFactoryInternalError 
	 */
	public void declareAnnotationTable(String tableName) throws ExceptionControlApp;
}
