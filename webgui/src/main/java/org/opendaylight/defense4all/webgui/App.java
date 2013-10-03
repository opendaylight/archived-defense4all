/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */



package org.opendaylight.defense4all.webgui;

import org.opendaylight.defense4all.framework.core.Direction;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class App {

	public static void main(String[] args) {

		Client client = Client.create();
		
		try {
			WebResource webResource = client.resource("http://localhost:8083/rest/directions");
			Direction direction = new Direction("northeast","northeast posted direction");
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, direction);
			if (response.getStatus() == 201)
				System.out.println("Output from Server .... \n" + response.getEntity(String.class)); 
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		try {
			WebResource webResource = client.resource("http://localhost:8083/rest/directions");
			ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
			if (response.getStatus() == 200) 
				System.out.println("Output from Server .... \n" + response.getEntity(String.class));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
