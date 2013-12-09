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
import me.prettyprint.hom.annotations.AnnotationScanner;
import me.prettyprint.hom.annotations.DefaultAnnotationScanner;

public class EMImpl extends EntityManagerImpl implements EM {

	protected FrameworkMainImpl frameworkMain = null;
	protected RepoFactoryImpl repoFactory = null;
	protected EMDescription emDescription = null;
	protected static ClassCacheMgr cacheMgr = new ClassCacheMgr();
	protected static EMObjectMapper objMapper = new EMObjectMapper(cacheMgr);

	public static EMImpl getEM(FrameworkMainImpl fMain, RepoFactoryImpl rfImpl, Keyspace keyspace, EMDescription emDesc) {
		
		String[] classpathPrefix = emDesc.classPathsDelimitedByColon.split(":");
		AnnotationScanner scanner = new DefaultAnnotationScanner();
		EMImpl emImpl = new EMImpl(fMain, rfImpl, keyspace, emDesc, classpathPrefix, scanner);
		return emImpl;
	}
	
	public EMImpl(FrameworkMainImpl fMain, RepoFactoryImpl rfImpl, Keyspace keyspace, EMDescription emDesc,
			String[] classpathPrefix, AnnotationScanner scanner) {
		super(keyspace, classpathPrefix, cacheMgr, objMapper, scanner);
		this.frameworkMain = fMain;
		objMapper.setKeyspace(keyspace);
		this.repoFactory = rfImpl;
		this.emDescription = emDesc;		
	}
	
	public void remove(Object obj, Object key) {
		objMapper.remove(obj, key);
	}
}