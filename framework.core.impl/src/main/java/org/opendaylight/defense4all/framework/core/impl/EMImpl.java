/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import org.opendaylight.defense4all.framework.core.EM;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.EntityManagerImpl;
import me.prettyprint.hom.annotations.DefaultAnnotationScanner;

public class EMImpl extends EntityManagerImpl implements EM {

	protected FrameworkMainImpl mFrameworkMain = null;
	protected RepoFactoryImpl mRepoFactory = null;
	protected EMDescription mEMDescription = null;
	protected static ClassCacheMgr mCacheMgr = new ClassCacheMgr();
	protected static EMObjectMapper mObjMapper = new EMObjectMapper(mCacheMgr);

	public EMImpl(FrameworkMainImpl frameworkMain, RepoFactoryImpl repoFactoryImpl, Keyspace keyspace, EMDescription emDescription) {
		super(keyspace, emDescription.classPathsDelimitedByColon.split(":"), mCacheMgr, mObjMapper, new DefaultAnnotationScanner());
		mFrameworkMain = frameworkMain;
		mObjMapper.setKeyspace(keyspace);
		mRepoFactory = repoFactoryImpl;
		mEMDescription = emDescription;		
	}
	
	public void remove(Object obj, Object key) {
		mObjMapper.remove(obj, key);
	}
}