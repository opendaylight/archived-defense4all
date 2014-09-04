package org.opendaylight.defense4all.odl.controller;

import java.io.IOException;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

//TODO: does it use the credentials and authScope at all???



public class RestTemplateFactory {
	
//	INSTANCE;//Singleton
	public RestTemplateFactory(){}	
	
	public static RestTemplateFactory INSTANCE = new RestTemplateFactory();//TODO:to be removed
	
//	public static RestTemplateFactory getInstance(){
//		return INSTANCE;
//	}
	
	private static Logger log = LoggerFactory.getLogger(RestTemplateFactory.class);

	private boolean isInsecureSsl = false; //false = supports SSL by accept all (insecure), true = ssl.
	private String trustStore = null;// "/home/sagii/workspace/tmp/cacerts.my3";
	private boolean isWriteAcceptCharset = false;//from some reason RestTemplate tend to add "Accept" header that causes problem. I disable it by default.  
	
	public RestTemplate createRestTemplate(AuthScope authScope, UsernamePasswordCredentials credentials) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate;
		if(isInsecureSsl){
			restTemplate = createInsecureSSLRestTemplate(authScope, credentials);			 
		} else {
			restTemplate = createSecureSSLRestTemplate(authScope, credentials);
		}
		return  restTemplate; 
	}



	/*
	 * factory with hardcoded predefined host,port,user,password. for tests only. 
	 */
	@Deprecated
	public RestTemplate createInsecureSSLRestTemplate() throws NoSuchAlgorithmException, KeyManagementException{
		String host = "10.206.102.49";
		int port = 8443;		
		AuthScope authScope = new AuthScope(host, port, AuthScope.ANY_REALM);
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sdn","skyline");//odlOFC.username,odlOFC.password);
//		return createInsecureSSLRestTemplate(authScope, credentials);
		return createInsecureSSLRestTemplate(authScope,credentials);
	}

	/*
	 * factory with hardcoded predefined host,port,user,password. for tests only. 
	 */
	@Deprecated
	public RestTemplate createRestTemplate() throws NoSuchAlgorithmException, KeyManagementException, KeyStoreException{
		String host = "10.206.102.49";
		int port = 8443;		
		AuthScope authScope = new AuthScope(host, port, AuthScope.ANY_REALM);
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("sdn","skyline");//odlOFC.username,odlOFC.password);
//		return createInsecureSSLRestTemplate(authScope, credentials);
		return createRestTemplate(authScope,credentials);
	}
	
	
	public static class EnhancedHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
		public EnhancedHttpComponentsClientHttpRequestFactory(HttpClient httpClient) { super(httpClient);}

		@Override
		protected HttpUriRequest createHttpUriRequest(
				HttpMethod httpMethod, URI uri) {
			if (HttpMethod.DELETE == httpMethod) {
				return new HttpEntityEnclosingDeleteRequest(uri);
			}
			return super.createHttpUriRequest(httpMethod, uri);
		}
	}
	
	//to add delete body support for restTemplate
	public static class HttpEntityEnclosingDeleteRequest extends HttpEntityEnclosingRequestBase{
	     public HttpEntityEnclosingDeleteRequest(final URI uri) {
	    	 super();
	    	 setURI(uri);
	     }
	     @Override
	     public String getMethod() {
	    	 return "DELETE";
	     }
	}

	
	public RestTemplate createSecureSSLRestTemplate(AuthScope authScope, UsernamePasswordCredentials credentials) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		if(trustStore != null){
			System.setProperty("javax.net.ssl.trustStore", trustStore);//"/home/sagii/workspace/tmp/cacerts.my3");
		} else {
			log.debug("trustStore is not configured in the "+ this.getClass() + " bean");
		}
		//This requires at installation to add certificate to the truststore TODO: add instructions.
		TrustManager[] trustManagers = getDefaultTrustManagers();
		RestTemplate restTemplate = createRestTemplate(authScope, credentials,
				trustManagers, null);
		return restTemplate;		
	}
			
	private TrustManager[] getDefaultTrustManagers() throws NoSuchAlgorithmException, KeyStoreException{
		TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());		  
		trustManagerFactory.init((KeyStore)null);
		return trustManagerFactory.getTrustManagers();
	}
	
	
	
	
	
	// insecure RestTemplate that supports SSL
	public RestTemplate createInsecureSSLRestTemplate(AuthScope authScope, UsernamePasswordCredentials credentials) throws NoSuchAlgorithmException, KeyManagementException {
		 
	    X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
    	public boolean verify(String arg0, SSLSession arg1) {
    		return true;
    	}
		public void verify(String host, SSLSocket ssl) throws IOException {}
		public void verify(String host, X509Certificate cert) throws SSLException {}
		public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {}
	    };
	    
	    TrustManager[] trustAllCerts = { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {	return null;}
			public void checkClientTrusted(X509Certificate[] certs, String authType) {}
			public void checkServerTrusted(X509Certificate[] certs,	String authType) {}
	    } };
		
	    //TODO: does this overrides credentials??? 
	    HostnameVerifier hv = new HostnameVerifier() {
	    	public boolean verify(String arg0, SSLSession arg1) {
	    		return true;
	    		}
	    };	    
	    HttpsURLConnection.setDefaultHostnameVerifier(hv);
	    
		RestTemplate restTemplate = createRestTemplate(authScope, credentials,
				trustAllCerts, hostnameVerifier);
		return restTemplate;
	}
	
	public RestTemplate createRestTemplate(AuthScope authScope, UsernamePasswordCredentials credentials,
			TrustManager[] trustManagers, X509HostnameVerifier hostnameVerifier) throws NoSuchAlgorithmException, KeyManagementException {
		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
		credsProvider.setCredentials(
		        		authScope,// new AuthScope(null, -1),
		        		credentials);
		    
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, trustManagers, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
				
		HttpClientBuilder httpClientBuilder = HttpClients.custom();
		
		if (hostnameVerifier != null) {
			httpClientBuilder.setHostnameVerifier(hostnameVerifier);
		}
//		httpClientBuilder.setDefaultCredentialsProvider(credsProvider)		    		 
		httpClientBuilder.setSslcontext(sc);		    		 
		HttpClient httpClient = httpClientBuilder.build();
		
		HttpComponentsClientHttpRequestFactory fac = new EnhancedHttpComponentsClientHttpRequestFactory(httpClient);
		
		RestTemplate restTemplate = new RestTemplate(fac);

		if(!isWriteAcceptCharset){
			disableWriteAcceptCharset(restTemplate);
		}
		return restTemplate;
	}
	
	private void disableWriteAcceptCharset(RestTemplate restTemplate){
		 List<HttpMessageConverter<?>> c = restTemplate.getMessageConverters();
	     for(HttpMessageConverter<?> mc :c){
	    	 if (mc instanceof StringHttpMessageConverter) {
	    		 StringHttpMessageConverter mcc = (StringHttpMessageConverter) mc;
	    		 mcc.setWriteAcceptCharset(false);
	    	 }
	     }
	}	
	
	

	public boolean isInsecureSsl() {
		return isInsecureSsl;
	}

	public void setInsecureSsl(boolean isInsecureSsl) {
		this.isInsecureSsl = isInsecureSsl;
	}

	public String getTrustStore() {
		return trustStore;
	}

	public void setTrustStore(String trustStore) {
		this.trustStore = trustStore;
	}

	public boolean isWriteAcceptCharset() {
		return isWriteAcceptCharset;
	}

	public void setWriteAcceptCharset(boolean isWriteAcceptCharset) {
		this.isWriteAcceptCharset = isWriteAcceptCharset;
	}

	@Override
	public String toString() {
		return "RestTemplateFactory.INSTANCE [isSsDisabled=" + isInsecureSsl
				+ ", trustStore=" + trustStore + ", isWriteAcceptCharset="
				+ isWriteAcceptCharset + "]";
	}

	
//	
	public RestTemplate createInsecureSSLRestTemplateOld(AuthScope authScope, UsernamePasswordCredentials credentials) throws NoSuchAlgorithmException, KeyManagementException{ 		
//		try {			
			CredentialsProvider credsProvider = new BasicCredentialsProvider();
		    credsProvider.setCredentials(
		        		authScope,// new AuthScope(null, -1),
		        		credentials);
		    
		    X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
		    	public boolean verify(String arg0, SSLSession arg1) {
		    		return true;
		    	}
				public void verify(String host, SSLSocket ssl) throws IOException {}
				public void verify(String host, X509Certificate cert) throws SSLException {}
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {}
		    };
		     
		     TrustManager[] trustAllCerts = { new X509TrustManager() {
				public X509Certificate[] getAcceptedIssuers() {	return null;}
				public void checkClientTrusted(X509Certificate[] certs, String authType) {}
				public void checkServerTrusted(X509Certificate[] certs,	String authType) {}
		     } };
		     
		     SSLContext sc = SSLContext.getInstance("SSL");
		     HostnameVerifier hv = new HostnameVerifier() {
		    	 public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}
		     };
		     sc.init(null, trustAllCerts, new SecureRandom());
		     HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		     HttpsURLConnection.setDefaultHostnameVerifier(hv);			
				
		     HttpClient httpClient = HttpClients.custom()
//		    		 .setDefaultCredentialsProvider(credsProvider)
		    		 .setHostnameVerifier(hostnameVerifier)
		    		 .setSslcontext(sc)
		    		 .build();
		     
		     HttpComponentsClientHttpRequestFactory fac = new HttpComponentsClientHttpRequestFactory(httpClient){
		    	 @Override
	             protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
	                 if (HttpMethod.DELETE == httpMethod) {
	                     return new HttpEntityEnclosingDeleteRequest(uri);
	                 }
	                 return super.createHttpUriRequest(httpMethod, uri);
	             }
	         };
		     
		     RestTemplate restTemplate = new RestTemplate(fac);
		     
		     List<HttpMessageConverter<?>> c = restTemplate.getMessageConverters();
		     for(HttpMessageConverter<?> mc :c){
		    	 if (mc instanceof StringHttpMessageConverter) {
		    		 StringHttpMessageConverter mcc = (StringHttpMessageConverter) mc;
		    		 mcc.setWriteAcceptCharset(false);
		    	 }
		     }
			return restTemplate; 
	}
}
