/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core;

import java.nio.ByteBuffer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.AbstractSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.converters.Converter;

public class SerializersSerializer extends AbstractSerializer<Serializer<?>> implements Converter<Serializer<?>> {
	
	private static SerializersSerializer instance = new SerializersSerializer();
	public static SerializersSerializer getInstance() {return instance;}

	Logger log = LoggerFactory.getLogger(this.getClass());

	/* For AbstractSerializer */
	
	@Override
	public ByteBuffer toByteBuffer(Serializer<?> serializer) {
		try {
			String serializerName = serializer.getClass().getCanonicalName();
			return StringSerializer.get().toByteBuffer(serializerName);
		} catch (Throwable e) {
			log.error("Failed to convert." + e.getLocalizedMessage());
			return null;
		}
	}
	
	@Override
	public Serializer<?> fromByteBuffer(ByteBuffer byteBuffer) {		
	    try {
	    	String serializerName = StringSerializer.get().fromByteBuffer(byteBuffer);
			return (Serializer<?>) Class.forName(serializerName).newInstance();
		} catch (Throwable e) {
			log.error("Failed to convert." + e.getLocalizedMessage());
			return null; 
		}
	}

	/* For Converter */
	
	public Serializer<?> convertCassTypeToObjType(PropertyMappingDefinition md, byte[] serializedSerializer) {	
	    try {
	    	String serializerName = StringSerializer.get().fromBytes(serializedSerializer);
			return (Serializer<?>) Class.forName(serializerName).newInstance();
		} catch (Throwable e) {	
			log.error("Failed to convert." + e.getLocalizedMessage());
			return null; 
		}
	}

	public byte[] convertObjTypeToCassType(Serializer<?> serializer) {
		try {
			String serializerName = serializer.getClass().getCanonicalName();
			return StringSerializer.get().toBytes(serializerName);
		} catch (Throwable e) {
			log.error("Failed to convert." + e.getLocalizedMessage());
			return null;
		}
	}

	/* For serialization to/from UTF strings */

	public String toString(Serializer<?> serializer) {
		return serializer.getClass().getCanonicalName();
	}

	public Serializer<?> fromString(String serializerName) {		
	    try {
			return (Serializer<?>) Class.forName(serializerName).newInstance();
		} catch (Throwable e) {
			log.error("Failed to convert." + e.getLocalizedMessage());
			return null; 
		}
	}
}
