/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.framework.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import me.prettyprint.cassandra.serializers.AbstractSerializer;
import me.prettyprint.hom.PropertyMappingDefinition;
import me.prettyprint.hom.converters.Converter;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class PropertiesSerializer extends AbstractSerializer<Properties> implements Converter<Properties> {
	
	private static PropertiesSerializer instance = new PropertiesSerializer();
	private static ObjectMapper objMapper = new ObjectMapper();

	public static PropertiesSerializer get() {
		return instance;
	}

	/* For AbstractSerializer */
	
	@Override
	public ByteBuffer toByteBuffer(Properties props) {
		
		String propsJson;
		try {
			propsJson = objMapper.writeValueAsString(props);
		} catch (JsonGenerationException e) {
			return null;
		} catch (JsonMappingException e) {
			return null;
		} catch (IOException e) {
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
		} catch (JsonParseException e1) {
			return null;
		} catch (JsonMappingException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
	    return props;
	}

	/* For Converter */

	public byte[] convertObjTypeToCassType(Properties props) {
		
		String propsJson;
		try {
			propsJson = objMapper.writeValueAsString(props);
		} catch (JsonGenerationException e) {
			return null;
		} catch (JsonMappingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}		
		byte[] bytes = propsJson.getBytes();
		return bytes;
	}
	
	public Properties convertCassTypeToObjType(PropertyMappingDefinition md, byte[] bytes) {
		
		Properties props;
		try {
			props = objMapper.readValue(new String(bytes), Properties.class);
		} catch (JsonParseException e1) {
			return null;
		} catch (JsonMappingException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
	    return props;
	}

	/* For serialization to/from UTF strings */

	public String toString(Properties props) {
		
		String propsJson;
		try {
			propsJson = objMapper.writeValueAsString(props);
		} catch (JsonGenerationException e) {
			return null;
		} catch (JsonMappingException e) {
			return null;
		} catch (IOException e) {
			return null;
		}
		return propsJson;
	}

	public Properties fromString(String propsJson) {	
		
		Properties props;
		try {
			props = objMapper.readValue(propsJson, Properties.class);
		} catch (JsonParseException e1) {
			return null;
		} catch (JsonMappingException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}
	    return props;
	}
}
