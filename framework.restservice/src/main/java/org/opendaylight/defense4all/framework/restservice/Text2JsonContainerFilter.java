/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.restservice;

import java.util.List;

import javax.ws.rs.core.MultivaluedMap;

import com.sun.jersey.core.header.InBoundHeaders;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

public class Text2JsonContainerFilter implements ContainerRequestFilter {

	@Override
	public ContainerRequest filter(ContainerRequest request) {

		MultivaluedMap<String, String> requestHeaders = request.getRequestHeaders();
		List<String> requestHeader = requestHeaders.get("Content-Type");

		if (requestHeader != null) {
			String contentType = requestHeader.get(0);
			if (contentType.startsWith("text/plain")) {
				String newContentType = "application/json" + contentType.substring(10);
				requestHeaders.remove("Content-Type");
				requestHeaders.add("Content-Type", newContentType);
				request.setHeaders((InBoundHeaders) requestHeaders);
			}
		}
		return request;
	}
}
