/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Properties;

import me.prettyprint.hector.api.Serializer;

public class RepoCD implements Serializable {
	
	private static final long serialVersionUID = 1L;
	public String columnName;
	public transient Serializer<?> columnValueSerializer;
	public Properties columnProperties;
	
	public RepoCD(String columnName, Serializer<?> columnValueSerializer, Properties columnProperties) {
		this.columnName = columnName;
		this.columnValueSerializer = columnValueSerializer;
		this.columnProperties = columnProperties;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException {	     
	    oos.defaultWriteObject(); // default serialization	    
	    oos.writeUTF(SerializersSerializer.getInstance().toString(columnValueSerializer));
	}

	private void readObject(ObjectInputStream ois)	throws ClassNotFoundException, IOException, InstantiationException, IllegalAccessException {	    
	    ois.defaultReadObject(); // default deserialization  
	    columnValueSerializer = SerializersSerializer.getInstance().fromString(ois.readUTF());
	}
}
