/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.core.impl;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

//Repo name corresponds to FrameworkMain.RepoMajor.REPO_FACTORY + "_" + RepoFactoryImpl.RepoMinor.EM_DESCRIPTIONS

@Entity
@Table(name= "FWORK_REPO_FACTORY_EMDescriptions")
public class EMDescription {
	
	
	/** Empty constructor to be used by Hector EntityManager when inflating and populating fields from Cassandra. */
	public EMDescription() {}
	
	public EMDescription(String key, String classPathsDelimitedByColon) {
		this.key = key;
		this.classPathsDelimitedByColon = classPathsDelimitedByColon;
	}
	
	@Id
	public String key;
	
	@Column(name = "classPathsDelimitedByColon")
	public String classPathsDelimitedByColon;
	
	// For Entity Manager to populate fields at load
	public String getKey() {return key;}
	public void setKey(String rowKey) {this.key = rowKey;}
	public String getClassPathsDelimitedByColon() {return classPathsDelimitedByColon;}
	public void setClassPathsDelimitedByColon(String classPathsDelimitedByColon) {this.classPathsDelimitedByColon = classPathsDelimitedByColon;}
	
	public void printObject() {
        System.out.print("rowKey = " + key + "; ");
        System.out.println("classPathsDelimitedByColon = " + classPathsDelimitedByColon + "; ");   	
	}
}
