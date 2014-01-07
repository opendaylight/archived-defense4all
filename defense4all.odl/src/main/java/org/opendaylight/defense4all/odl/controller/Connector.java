/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Kobi Samoray 
 * @author Gera Goft 
 * @version 0.1
 */
package org.opendaylight.defense4all.odl.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.DefaultHttpClient;
import com.fasterxml.jackson.core.type.TypeReference;	
import com.fasterxml.jackson.databind.DeserializationFeature;	
import com.fasterxml.jackson.databind.ObjectMapper;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.odl.OdlOFC;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class Connector {

	public interface JsonPreprocessor {
		public String preProcess(String jsonStr);
	}

	private static Log log = LogFactory.getLog(Connector.class);

	public OdlOFC odlOFC;
	protected String restPrefix;
	protected com.fasterxml.jackson.databind.ObjectMapper fasterxmlObjMapper;
	protected RestTemplate restTemplate;

	public Connector(OdlOFC odlOFC) {

		if(odlOFC == null) {
			log.error("Failed to create connector - null odlOFC passed");
			throw new IllegalArgumentException("Null odlOFC");
		}
		fasterxmlObjMapper  = new ObjectMapper();
		fasterxmlObjMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Ignore unknownfields 
		this.odlOFC = odlOFC;
	}

	public void init() throws ExceptionControlApp {

		try {
			restPrefix = "http://" + odlOFC.hostname + ":" + Integer.toString(odlOFC.port);

			// set authentication for rest template
			AuthScope authScope = new AuthScope(odlOFC.hostname, odlOFC.port, AuthScope.ANY_REALM);
			UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(odlOFC.username,odlOFC.password);
			DefaultHttpClient client = new DefaultHttpClient();
			client.getCredentialsProvider().setCredentials(authScope, credentials);

			HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(client);
			restTemplate = new RestTemplate(factory);
			if(restTemplate == null) throw new Exception("Failed to create restTemplate");
		} catch (Throwable e) {
			log.error("Failed to init connector to " + odlOFC.hostname, e);
			FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MINOR_HEALTH_ISSUE);
			throw new ExceptionControlApp ("Failed to init connector to " + odlOFC.hostname, e);
		}
	}

	protected synchronized <T> T getFromController(String urlPrefix, TypeReference<?> typeRef, JsonPreprocessor preProcessor) 
			throws RestClientException {

		T t;
		try {
			String url = mkUrl(urlPrefix);
			log.debug("Caller: "+getMethodName(2)+" Class:"+typeRef.getType().toString()+" Invoking restTemplate.getForObject"
					+ "Calling - URL: "+url+" JSON: ");
			String result = restTemplate.getForObject(url, String.class);
			if(result == null) return null;
			// Don't print it to log - it may contain non-printable chars
			// log.debug("Caller: "+getMethodName(2)+" Class:"+typeRef.getType().toString()+" URL: "+url+" JSON: "+result.toString());
			if(preProcessor != null) 
				result = preProcessor.preProcess(result);

			t = fasterxmlObjMapper.readValue(result, typeRef);
		} catch (Throwable e) { 
			log.error("Failed to get from controller " + odlOFC.hostname, e);
			throw new RestClientException("Failed to get from controller " + odlOFC.hostname, e);
		}

		return t;
	}

	protected synchronized void postToController(String urlPrefix, Object object) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);
			log.debug("Caller: URL: " + url );
			HttpEntity<String> entity = buildHttpEntityFromObject( object );
			restTemplate.postForLocation(url, entity);
		} catch (Throwable e) {
			log.error("Failed to post to controller " + odlOFC.hostname, e);
			throw new RestClientException("Failed to post to controller " + odlOFC.hostname, e);
		}
	}

	protected synchronized void putToController(String urlPrefix, Object object) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);
			log.debug("Caller: URL: " + url );
			HttpEntity<String> entity = buildHttpEntityFromObject( object );
			restTemplate.put(url, entity);
		} catch (Throwable e) {
			log.error("Failed to put to controller " + object + " to " + odlOFC.hostname, e);
			throw new RestClientException("Failed to put to controller " + object + " to " + odlOFC.hostname, e);
		}
	}

	private HttpEntity<String> buildHttpEntityFromObject( Object object) throws RestClientException {

		String jsonStr;

		try {
			jsonStr = fasterxmlObjMapper.writeValueAsString(object);
		} catch (Throwable e) {
			String msg = "Failed to writeValueAsString " + object.toString() + " for controller " + odlOFC.hostname;
			log.error(msg, e);
			throw new RestClientException(msg, e);
		}

		log.debug("Caller: " + getMethodName(2) + " JSON: " + jsonStr);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(jsonStr,headers);
		return entity;
	}

	protected synchronized void putToController(String urlPrefix) throws RestClientException {

		try {
			String url = mkUrl(urlPrefix);
			log.debug("Caller: " + getMethodName(2) + " URL: " + url + " JSON: ");
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			restTemplate.put(url, null);		
		} catch (Throwable e) {
			log.error("Failed to put to controller " + odlOFC.hostname, e);
			throw new RestClientException("Failed to put to controller " + odlOFC.hostname, e);
		}	
	}

	protected synchronized void delFromController(String urlPrefix) throws ExceptionInvalidState {

		try {
			String url = mkUrl(urlPrefix);
			log.debug("Caller: " + getMethodName(2) + " URL: " + url);
			restTemplate.delete(url);		
		} catch (Throwable e) {
			log.error("Failed to put to controller " + odlOFC.hostname, e);
			throw new RestClientException("Failed to put to controller " + odlOFC.hostname, e);
		}	
	}

	protected String mkUrl(String path) {
		return restPrefix + path;
	}

	private static String getMethodName(final int depth) {
		final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
		return ste[ste.length - 1 - depth].getMethodName();
	}
}
