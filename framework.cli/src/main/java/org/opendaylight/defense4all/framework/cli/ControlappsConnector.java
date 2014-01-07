/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Kobi Samoray 
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.framework.cli;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class ControlappsConnector {
	
	/* Example post request: http://localhost:8086/rest/general/hostaddress -d '10.206.167.31' */	
	public static final int RESTSERVICE_PORT = 8086;
	public static final String RESTSERVICE_HOSTNAME = "localhost";

	public String username;
	public String password;
	protected ObjectMapper objMapper;
	protected RestTemplate restTemplate;
	protected String restPrefix;

	public ControlappsConnector(String username, String password, String restSubPath) throws Exception {
		
		this.username = username; 
		this.password = password;
		restPrefix = "http://" + RESTSERVICE_HOSTNAME + ":" + RESTSERVICE_PORT + restSubPath;

		try {			
			objMapper  = new ObjectMapper();
			objMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknown fields
			
			// set authentication for rest template
			AuthScope authScope = new AuthScope(RESTSERVICE_HOSTNAME, RESTSERVICE_PORT, AuthScope.ANY_REALM);
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(username, password);
			DefaultHttpClient client = new DefaultHttpClient();
			client.getCredentialsProvider().setCredentials(authScope, credentials);

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
			restTemplate = new RestTemplate(factory);
			if(restTemplate == null) throw new Exception("");
		} catch (Throwable e) {
			throw new Exception("Failed to initialize connection with controlapps. Is it up?");
		}
	}

	public synchronized <T> T getFromControlApps(String urlPrefix, TypeReference<?> typeRef) throws Exception {

		String result;
		try {
			String url = mkUrl(urlPrefix);
			result = restTemplate.getForObject(url, String.class);
			if(result == null) return null;
		} catch (Throwable e) {
			throw new Exception("Failed to communicate with controlapps. Is it up?");
		}

		try {
			T t = objMapper.readValue(result, typeRef);
			return t;
		} catch (Throwable e) {
			throw new RestClientException("Internal error parsing the result. Please try again later or restart controlapps.");
		}
	}

	public synchronized String getStringFromControlApps(String urlPrefix) throws Exception {

		String result;
		try {
			String url = mkUrl(urlPrefix);
			result = restTemplate.getForObject(url, String.class);
			if(result == null) return null;
		} catch (Throwable e) {
			throw new Exception("Failed to communicate with controlapps. Is it up?");
		}

		return result;
	}

	public synchronized void postToControlApps(String urlPrefix, Object object) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);			
			HttpEntity<String> entity = buildHttpEntityFromObject( object );
			restTemplate.postForLocation(url, entity);
		} catch (Throwable e) {
			throw new RestClientException("Failed to post to controller " + RESTSERVICE_HOSTNAME, e);
		}
	}

	public synchronized void putToControlApps(String urlPrefix, Object object) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);
			HttpEntity<String> entity = buildHttpEntityFromObject( object );
			restTemplate.put(url, entity);
		} catch (Throwable e) {
			throw new RestClientException("Failed to put to controller " + object + " to " + RESTSERVICE_HOSTNAME, e);
		}
	}

	private HttpEntity<String> buildHttpEntityFromObject( Object object) throws RestClientException {

		String jsonStr;

		try {
			if(String.class.isInstance(object))
				jsonStr = (String) object;
			else
				jsonStr = objMapper.writeValueAsString(object);
		} catch (Throwable e) {
			String msg = "Failed to writeValueAsString ";
			throw new RestClientException(msg, e);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jsonStr,headers);
		return entity;
	}

	public synchronized void putToControlApps(String urlPrefix) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			restTemplate.put(url, null);		
		} catch (Throwable e) {
			throw new RestClientException("Failed to put to controller " + RESTSERVICE_HOSTNAME, e);
		}	
	}

	public synchronized void delFromControlApps(String urlPrefix) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);
			restTemplate.delete(url);		
		} catch (Throwable e) {
			throw new RestClientException("Failed to put to controller " + RESTSERVICE_HOSTNAME, e);
		}	
	}

	protected String mkUrl(String path) {return restPrefix + path;}
}
