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
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.opendaylight.controlapps.framework.core.RepoCD;
import org.opendaylight.controlapps.framework.core.SerializersSerializer;


import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

//Repo name corresponds to FrameworkMain.RepoMajor.REPO_FACTORY + "_" + RepoFactoryImpl.RepoMinor.REPO_DESCRIPTIONS

/**
 * 
 * @author gerag
 *
 * @param <K> - The type of the Repo key column
 */
@Entity
@Table(name="FWORK_REPO_FACTORY_RepoDescriptions")
public class RepoDescription {
	
	@Id
	public String repoName;

	@Column(name="KeySerializerClassName")
	public String keySerializerClassName;

	@Column(name="ImmediateFlush")
	public boolean immediateFlush = false;
	
	@Column(name="DescribedColumns")
	public List<RepoCD> describedColumnSet = null;

	/** Empty constructor to be used by Hector EntityManager when inflating and populating fields from Cassandra.
	 * Need to then invoke init() to complete common initialization.
	 */
	public RepoDescription() {}
	
	/** Constructor - Corresponds to inflation by Hector EntityManager
	 * @param mRepoFactoryImpl 
	 * @param param_name param description
	 * @throws exception_type circumstances description 
	 */
	public RepoDescription(String repoName, String keySerializerClassName, boolean immediateFlush, List<RepoCD> initialColumnSet) {		
		this.repoName = repoName;
		this.keySerializerClassName = keySerializerClassName;
		this.immediateFlush = immediateFlush;
		if (initialColumnSet != null)
			this.describedColumnSet = initialColumnSet;
		else
			this.describedColumnSet = new ArrayList<RepoCD>();
	}

	public void setRepoName(String repoName) {this.repoName = repoName;}
	public void setKeySerializerClassName(String keySerializerClassName) {this.keySerializerClassName = keySerializerClassName;}
	public void setImmediateFlush(boolean immediateFlush) {this.immediateFlush = immediateFlush;}
	public void setDescribedColumnSet(List<RepoCD> describedColumnSet) {this.describedColumnSet = describedColumnSet;}

	public String getRepoName() {return repoName;}
	public String getKeySerializerClassName() {return keySerializerClassName;}
	public boolean getImmediateFlush() {return immediateFlush;}
	public List<RepoCD> getDescribedColumnSet() {return describedColumnSet;}

	public void printObject() {
		SerializersSerializer sSerializer = SerializersSerializer.getInstance();
        System.out.print("rowKey = " + repoName + "; ");
        System.out.println("KeySerializerClassName = " + keySerializerClassName + "; ");
        System.out.println("ImmediateFlush = " + immediateFlush + "; ");;
        System.out.println("DescribedColumns are: ");
        for (RepoCD colDesc : describedColumnSet) {
        	System.out.print("Column name = " + colDesc.columnName + "; ");
        	System.out.print("Value serializer class name = " + sSerializer.toString(colDesc.columnValueSerializer) + "; ");
        	System.out.print("Column properties = " + colDesc.columnProperties + "; ");
        }
        System.out.println();
	}
}
