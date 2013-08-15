/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.core;

import java.nio.ByteBuffer;

import me.prettyprint.cassandra.serializers.AbstractSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.converters.Converter;

public class SerializersSerializer extends AbstractSerializer<Serializer<?>> implements Converter<Serializer<?>> {
	
	private static SerializersSerializer instance = new SerializersSerializer();

	public static SerializersSerializer getInstance() {
		return instance;
	}

	/* For AbstractSerializer */
	
	@Override
	public ByteBuffer toByteBuffer(Serializer<?> serializer) {
		String serializerName = serializer.getClass().getCanonicalName();
		return StringSerializer.get().toByteBuffer(serializerName);
	}
	
	@Override
	public Serializer<?> fromByteBuffer(ByteBuffer byteBuffer) {		
	    try {
	    	String serializerName = StringSerializer.get().fromByteBuffer(byteBuffer);
			return (Serializer<?>) Class.forName(serializerName).newInstance();
		} catch (Throwable e) {	return null; }
	}

	/* For Converter */
	
	public Serializer<?> convertCassTypeToObjType(PropertyMappingDefinition md, byte[] serializedSerializer) {	
	    try {
	    	String serializerName = StringSerializer.get().fromBytes(serializedSerializer);
			return (Serializer<?>) Class.forName(serializerName).newInstance();
		} catch (Throwable e) {	return null; }
	}

	public byte[] convertObjTypeToCassType(Serializer<?> serializer) {
		String serializerName = serializer.getClass().getCanonicalName();
		return StringSerializer.get().toBytes(serializerName);
	}

	/* For serialization to/from UTF strings */

	public String toString(Serializer<?> serializer) {
		return serializer.getClass().getCanonicalName();
	}

	public Serializer<?> fromString(String serializerName) {		
	    try {
			return (Serializer<?>) Class.forName(serializerName).newInstance();
		} catch (Throwable e) {	return null; }
	}
}
