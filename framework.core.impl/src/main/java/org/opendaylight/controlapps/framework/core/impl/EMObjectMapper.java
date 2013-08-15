/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.core.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import me.prettyprint.cassandra.serializers.BytesArraySerializer;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.mutation.Mutator;
import me.prettyprint.hom.CFMappingDef;
import me.prettyprint.hom.ClassCacheMgr;
import me.prettyprint.hom.HectorObjectMapper;

public  class EMObjectMapper extends HectorObjectMapper {
	
	protected Keyspace mKeyspace = null;
	protected ClassCacheMgr mCacheMgr = null;

	public EMObjectMapper(ClassCacheMgr cacheMgr) {
		super(cacheMgr);
		mCacheMgr = cacheMgr;
	}
	
	public void setKeyspace(Keyspace keyspace) {
		mKeyspace = keyspace;
	}
	
	@SuppressWarnings("unchecked")
	public void remove(Object obj, Object key) {
	    
	    CFMappingDef<Object> cfMapDef = (CFMappingDef<Object>) mCacheMgr.getCfMapDef(obj.getClass(), true);
	    String colFamName = cfMapDef.getEffectiveColFamName();	    

	    Mutator<byte[]> mutator = HFactory.createMutator(mKeyspace, BytesArraySerializer.get());

	    /* The below is instead of simply calling:
	     * byte[] keyBytes = super.generateColumnFamilyKeyFromPkObj(cfMapDef, key);
	     * generateColumnFamilyKeyFromPkObj is unfortunately a private method of the superclass, so we use reflection.
	     */	    
	    Class<?>[] paramTypes = new Class<?>[2]; paramTypes[0] = CFMappingDef.class; paramTypes[1] = Object.class;
	    Object[] params = new Object[2]; params[0] = cfMapDef; params[1] = key;
	    Method method;
		try {
			method = HectorObjectMapper.class.getDeclaredMethod("generateColumnFamilyKeyFromPkObj", paramTypes);
		} catch (SecurityException e) {
			return;
		} catch (NoSuchMethodException e) {
			return;
		}
	    method.setAccessible(true);
	    byte[] keyBytes;
		try {
			keyBytes = (byte[]) method.invoke(this, params);
		} catch (IllegalArgumentException e) {
			return;
		} catch (IllegalAccessException e) {
			return;
		} catch (InvocationTargetException e) {
			return;
		}
	    
	    mutator.addDeletion(keyBytes, colFamName);
	    mutator.execute();
	}
}
