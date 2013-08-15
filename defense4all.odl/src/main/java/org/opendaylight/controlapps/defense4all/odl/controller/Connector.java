/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Kobi Samoray 
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.controlapps.defense4all.odl.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.opendaylight.controlapps.defense4all.odl.OdlOFC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class Connector {

	private static Log log = LogFactory.getLog(Connector.class);

	public OdlOFC odlOFC;
	protected String restPrefix;;
	protected ObjectMapper objMapper;
	protected RestTemplate restTemplate;

	public Connector(OdlOFC odlOFC) {
		objMapper  = new ObjectMapper();
		this.odlOFC = odlOFC;
	}

	public void init() {

		restPrefix = "http://" + odlOFC.hostname + ":" + Integer.toString(odlOFC.port);

		// set authentication for rest template
		AuthScope authScope = new AuthScope(odlOFC.hostname, odlOFC.port, AuthScope.ANY_REALM);
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(odlOFC.username,odlOFC.password);
		DefaultHttpClient client = new DefaultHttpClient();
		client.getCredentialsProvider().setCredentials(authScope, credentials);

		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
		restTemplate = new RestTemplate(factory);		
	}

	protected synchronized <T> T getFromController(String urlPrefix, TypeReference<?> typeRef) 
			throws RestClientException, ExceptionInvalidState {

		String url = mkUrl(urlPrefix);
		assertRestTemplateNotNull();
		String result = restTemplate.getForObject(url, String.class);
		log.debug("Caller: " + getMethodName(2) + " Class:" + typeRef.getType().toString() + " URL: " + url + " JSON: " + result);
		T t = null;
		
		try {
			t = objMapper.readValue(result, typeRef);
		} catch (JsonParseException e) {throw new RestClientException("Error getting from controller - " + e);
		} catch (JsonMappingException e) {throw new RestClientException("Error getting from controller - " + e);
		} catch (IOException e) {throw new RestClientException("Error getting from controller - " + e);}
		
		return t;
	}

	protected synchronized void addToController(String urlPrefix, Object object) throws RestClientException, ExceptionInvalidState {

		String url = mkUrl(urlPrefix);
		assertRestTemplateNotNull();
		String jsonStr;
		
		try {
			jsonStr = objMapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {throw new RestClientException("Error adding to controller - " + e);
		} catch (JsonMappingException e) {throw new RestClientException("Error adding to controller - " + e);
		} catch (IOException e) {throw new RestClientException("Error adding to controller - " + e);}
		
		log.debug("Caller: " + getMethodName(2) + " URL: " + url + " JSON: " + jsonStr);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jsonStr,headers);
		restTemplate.postForLocation(url, entity);
	}

	protected synchronized void putToController(String urlPrefix) throws RestClientException, ExceptionInvalidState {

		String url = mkUrl(urlPrefix);
		assertRestTemplateNotNull();
		
		log.debug("Caller: " + getMethodName(2) + " URL: " + url + " JSON: ");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		restTemplate.put(url, null);			
	}

	protected synchronized void delFromController(String urlPrefix) throws ExceptionInvalidState {
		
		String url = mkUrl(urlPrefix);
		assertRestTemplateNotNull();
		
		log.debug("Caller: " + getMethodName(2) + " URL: " + url);
		
		restTemplate.delete(url);
	}

	protected String mkUrl(String path) {
		return restPrefix + path;
	}

	private static String getMethodName(final int depth) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[ste.length - 1 - depth].getMethodName();
	}

	protected void assertRestTemplateNotNull() throws ExceptionInvalidState {
		if(restTemplate == null)
			throw new ExceptionInvalidState("No controllers are defined in Defense4all.");
	}
}
