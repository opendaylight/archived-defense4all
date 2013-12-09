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
import java.util.Properties;

import me.prettyprint.cassandra.serializers.AbstractSerializer;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.converters.Converter;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesSerializer extends AbstractSerializer<Properties> implements Converter<Properties> {
	
	private static PropertiesSerializer instance = new PropertiesSerializer();
	private static ObjectMapper objMapper = new ObjectMapper();
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	public static PropertiesSerializer get() {
		return instance;
	}

	/* For AbstractSerializer */
	
	@Override
	public ByteBuffer toByteBuffer(Properties props) {
		
		String propsJson;
		try {
			propsJson = objMapper.writeValueAsString(props);
		} catch (Throwable e) {
			log.error("Failed to writeValueAsString." + e.getLocalizedMessage());
			return null;
		}		
		ByteBuffer byteBuffer = ByteBuffer.wrap(propsJson.getBytes());
		return byteBuffer;
	}
	
	@Override
	public Properties fromByteBuffer(ByteBuffer byteBuffer) {
		
		Properties props;
		try {
			props = objMapper.readValue(new String(byteBuffer.array()), Properties.class);
		} catch (Throwable e) {
			log.error("Failed to read by objectMapper." + e.getLocalizedMessage());
			return null;
		}
	    return props;
	}

	/* For Converter */

	public byte[] convertObjTypeToCassType(Properties props) {
		
		String propsJson;
		try {
			propsJson = objMapper.writeValueAsString(props);
		} catch (Throwable e) {
			log.error("Failed to writeValueAsString." + e.getLocalizedMessage());
			return null;
		}		
		byte[] bytes = propsJson.getBytes();
		return bytes;
	}
	
	public Properties convertCassTypeToObjType(PropertyMappingDefinition md, byte[] bytes) {
		
		Properties props;
		try {
			props = objMapper.readValue(new String(bytes), Properties.class);
		} catch (Throwable e) {
			log.error("Failed to read by objectMapper." + e.getLocalizedMessage());
			return null;
		}
	    return props;
	}

	/* For serialization to/from UTF strings */

	public String toString(Properties props) {
		
		String propsJson;
		try {
			propsJson = objMapper.writeValueAsString(props);
		} catch (Throwable e) {
			log.error("Failed to writeValueAsString." + e.getLocalizedMessage());
			return null;
		}
		return propsJson;
	}

	public Properties fromString(String propsJson) {	
		
		Properties props;
		try {
			props = objMapper.readValue(propsJson, Properties.class);
		} catch (Throwable e) {
			log.error("Failed to read by objectMapper." + e.getLocalizedMessage());
			return null;
		}
	    return props;
	}
}
