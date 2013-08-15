/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.controlapps.framework.core.impl;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import java.util.Properties;
import javax.persistence.EntityManager;

import org.opendaylight.controlapps.framework.core.AppRoot;
import org.opendaylight.controlapps.framework.core.ClusterInfo;
import org.opendaylight.controlapps.framework.core.ClusterMgr;
import org.opendaylight.controlapps.framework.core.ExceptionControlApp;
import org.opendaylight.controlapps.framework.core.ExceptionEntityExists;
import org.opendaylight.controlapps.framework.core.FMHolder;
import org.opendaylight.controlapps.framework.core.FrameworkMain;
import org.opendaylight.controlapps.framework.core.MyLogger;
import org.opendaylight.controlapps.framework.core.PropertiesSerializer;
import org.opendaylight.controlapps.framework.core.RepoFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppClassLoader;
import org.eclipse.jetty.webapp.WebAppContext;

public class FrameworkMainImpl implements FrameworkMain {
	
	public static ApplicationContext context;
	protected Properties configProperties;
	protected AppRoot appRoot;

	protected RepoFactoryImpl repoFactoryImpl;
	protected ClusterMgrImpl clusterMgrImpl;
	protected PeerCommunicatorImpl peerCommunicatorImpl;
	protected FrameworkMgmtPointImpl frameworkMgmtPointImpl;
	
	protected EntityManager frameworkEM;
	protected String stateClassPaths;
	protected String restWarPath; // Path to the WAR containing the service
	protected String restPath; 	// REST Path (in HTTP URL) to service requests
	protected int port;
	protected Server restServer;
	
	/**
	 * Name space allocation of Framework REPO Major IDs
	 */
	
	public enum RepoMajor {	
		FWORK_INVALID,
		FWORK_GLOBAL,
		FWORK_REPO_FACTORY,
		FWORK_COMMUNICATOR,
		FWORK_CLUSTER_MGR,
		FWORK_MGMT_POINT
	}
	
	/**
	 * Name space allocation of Framework REPO global minor IDs
	 */

	public enum RepoMinor {	
		INVALID
	}
	
	/**
	 * Framework core entity manager id
	 */
	public static final String FRAMEWORK_CORE_EM_ID = "framework.core";

	private static final String SPRING_CONTEXT_FILENAME = "context.xml";
	
	public MyLogger myLogger;
	public String logFileName;
	
	/** ### Description ###
	 * @param param_name 
	 */
	public FrameworkMainImpl(Properties configProperties) {
		this.configProperties = configProperties;
	}

	/** Setters for Spring */
	public void setRepoFactoryImpl(RepoFactoryImpl repoFactoryImpl) {this.repoFactoryImpl = repoFactoryImpl;}
	public void setClusterMgrImpl(ClusterMgrImpl clusterMgr) {this.clusterMgrImpl = clusterMgr;}
	public void setPeerCommunicatorImpl(PeerCommunicatorImpl peerCommunicator) {this.peerCommunicatorImpl = peerCommunicator;}
	public void setFrameworkMgmtPointImpl(FrameworkMgmtPointImpl frameworkMgmtPoint) {this.frameworkMgmtPointImpl = frameworkMgmtPoint;}
	public void setStateClassPaths(String stateClassPaths) {this.stateClassPaths = stateClassPaths;}
	public void setAppRoot(AppRoot appRoot) {this.appRoot = appRoot;}
	public void setPort(int port) { this.port = port; }
	public void setRestWarPath(String restWarPath) { this.restWarPath = restWarPath; }
	public void setRestPath(String restPath) { this.restPath = restPath; }
	public void setLogFileName(String logFileName) { this.logFileName = logFileName; }
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public static void main(String[] args) throws ExceptionControlApp {
		
	    context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILENAME); // Start the Spring container
	    FrameworkMainImpl frameworkMain = (FrameworkMainImpl) context.getBean("frameworkMain");
	    FMHolder.set(frameworkMain);
	    frameworkMain.init();
	    	 
		if(args.length > 0) {
			if(args[0].equals("reset")) {
				ResetLevel level = args.length > 1 ? ResetLevel.valueOf(args[1], ResetLevel.soft) : ResetLevel.soft;
				frameworkMain.reset(level);
				frameworkMain.myLogger.logRow("Defense4all reset is complete");
				System.exit(0);
			} else if(args[0].equals("test")) {
			    frameworkMain.appRoot.test(null);
			    frameworkMain.test();
				System.exit(0);
			}
		}
		
	    try {
	    	frameworkMain.restServer.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
		}
	}
	
	protected void test() {
	}
	
	protected void testPropertiesSerializer() {

		PropertiesSerializer pSerializer = PropertiesSerializer.get();
		Properties props1 = new Properties();
		props1.put("key1", "value1");
		props1.put("key2", "value2");
		props1.put("key3", "value3");
		System.out.println("1" + props1);
		
		ByteBuffer byteBuffer = pSerializer.toByteBuffer(props1);
		Properties props2 = pSerializer.fromByteBuffer(byteBuffer);
		System.out.println("2" + props2);
		
		byte[] bytes = pSerializer.convertObjTypeToCassType(props1);
		Properties props3 = pSerializer.convertCassTypeToObjType(null, bytes);
		System.out.println("3" + props3);
		
		String s = pSerializer.toString(props1);
		Properties props4 = pSerializer.fromString(s);
		System.out.println("4" + props4);		
	}
	

	/**
	 * Initializes all modules after construction bottom-up. First, registers the provided ShutdownHookThread object 
	 * to be called for pre-shutdown cleanup. Next, invokes org.opendaylight.ctlapps.framework initialization, followed by application initialization.
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {
		
		/* This class initialization */
		Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(this, appRoot));
		myLogger = new MyLogger(logFileName);
		myLogger.logRow("-------------------------------------------------");
		myLogger.logRow("Defense4all is starting...");
	    setupRESTServer();
		
		/* RepoFactoryImpl initialization */
		repoFactoryImpl.init();
		
		/* This part of frameworkMain init can only be done after RepoFactoryImpl init. */
		try { 
			frameworkEM = repoFactoryImpl.createFrameworkMainEM(FRAMEWORK_CORE_EM_ID, stateClassPaths);
		} catch (ExceptionEntityExists e) {
			// TODO Auto-generated catch block
			return;
		}
		
		/* Other modules initialization */
		peerCommunicatorImpl.init();
		clusterMgrImpl.init();
		frameworkMgmtPointImpl.init();		
	    appRoot.init();

	    /* Bring-up of the jetty web server to serve REST requests. */
	    try {
	    	restServer.start();
		} catch (Exception e) {
			throw new ExceptionControlApp("Failed to start the Jetty WEB server");
		}
	}

	/**
	 * Cleans up all modules before shutdown - top-down, app first, org.opendaylight.ctlapps.framework next.
	 * @throws exception_type circumstances description 
	 */
	public void finit() {

		myLogger.logRow("Defense4all is stopping...");
		myLogger.logRow("-------------------------------------------------");
		
		try {
			restServer.stop();
			restServer.join();
		} catch (Exception e) {/* Ignore */}
		
		appRoot.finit();
		frameworkMgmtPointImpl.finit();
		clusterMgrImpl.finit();
		peerCommunicatorImpl.finit();
				
		frameworkEM.flush(); // In current Hector implementation this is a no op, but still...
		repoFactoryImpl.finit();
		
		/* This class cleanup */
		myLogger.finit(); 
	}

	/**
	 * Performs factory reset on all modules - first the application, then all org.opendaylight.ctlapps.framework modules top down.
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) {
		
		appRoot.reset(resetLevel);	
		frameworkMgmtPointImpl.reset(resetLevel);
		clusterMgrImpl.reset(resetLevel);
		peerCommunicatorImpl.reset(resetLevel);
		repoFactoryImpl.reset(resetLevel);
		; 	// This class factory reset
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void setClusterInfo(ClusterInfo clusterInfo) {
		// pass to cluster manager
	}

	@Override
	public Properties getConfigProperties() {return configProperties;}

	@Override
	public RepoFactory getRepoFactory() {return repoFactoryImpl;}

	@Override
	public ClusterMgr getClusterMgr() {return clusterMgrImpl;}

	@Override
	public PeerCommunicatorImpl getPeerCommunicator() {return peerCommunicatorImpl;}

	@Override
	public FrameworkMgmtPointImpl getFrameworkMgmtPoint() {return frameworkMgmtPointImpl;}

	@Override
	public AppRoot getAppRoot() {return appRoot;}

	@Override
	public MyLogger getMyLogger() {return myLogger;}

	/* Set the framework logic web parts */
	protected void setupRESTServer() throws ExceptionControlApp {

		restServer = new Server(port);		

	    WebAppContext fworkWebAppContext = new WebAppContext();
	    fworkWebAppContext.setContextPath(restPath);  
        try {
        	WebAppClassLoader classLoader = new WebAppClassLoader(this.getClass().getClassLoader(), fworkWebAppContext);
        	fworkWebAppContext.setClassLoader(classLoader);
		} catch (IOException e) {
			throw new ExceptionControlApp("Failed to set class loader " + this.getClass().getClassLoader().toString());
		}
        fworkWebAppContext.setParentLoaderPriority(true);
        
        // check if path to restservice war is defined in command line params
        String restServiceWarPath = System.getProperty("restWarPath" );
       
        // if it's not defined use default from context file
        if (restServiceWarPath == null  ) {
        	// append current class location with relative path to restservice war from context file
        	ProtectionDomain protectionDomain = this.getClass().getProtectionDomain();    	
        	restServiceWarPath = protectionDomain.getCodeSource().getLocation().getPath() +  restWarPath ;
        }
        fworkWebAppContext.setWar(restServiceWarPath); 

      
//        System.out.println("in getWebAppContext. cl = " + this.getClass().getClassLoader().toString());		
		restServer.setHandler(fworkWebAppContext);
	}
}
