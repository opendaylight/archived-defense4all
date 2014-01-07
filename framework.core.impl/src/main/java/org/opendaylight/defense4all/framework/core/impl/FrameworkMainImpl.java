/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @author Konstantin Pozdeev
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import java.nio.ByteBuffer;
import java.security.ProtectionDomain;
import java.util.Properties;

import javax.persistence.EntityManager;

import me.prettyprint.cassandra.serializers.StringSerializer;

import org.opendaylight.defense4all.framework.core.AppRoot;
import org.opendaylight.defense4all.framework.core.ClusterInfo;
import org.opendaylight.defense4all.framework.core.ClusterMgr;
import org.opendaylight.defense4all.framework.core.CoreState;
import org.opendaylight.defense4all.framework.core.ExceptionControlApp;
import org.opendaylight.defense4all.framework.core.FMHolder;
import org.opendaylight.defense4all.framework.core.FR;
import org.opendaylight.defense4all.framework.core.FrameworkMain;
import org.opendaylight.defense4all.framework.core.HealthTracker;
import org.opendaylight.defense4all.framework.core.PropertiesSerializer;
import org.opendaylight.defense4all.framework.core.Repo;
import org.opendaylight.defense4all.framework.core.RepoFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	public Repo<String> coreStateRepo = null;
	protected String stateClassPaths;
	protected String restWarPath; // Path to the WAR containing the service
	protected String restPath; 	// REST Path (in HTTP URL) to service requests
	protected int port;
	protected Server restServer;
	protected FRImpl frImpl;
	protected HealthTrackerImpl healthTrackerImpl;
	protected boolean openForBusiness = false;
	public boolean debugRun = false;
	public String hostAddr;

	static Logger log = LoggerFactory.getLogger(FrameworkMainImpl.class);

	/**
	 * Framework core entity manager id
	 */
	public static final String FRAMEWORK_CORE_EM_ID = "framework.core";

	private static final String SPRING_CONTEXT_FILENAME = "context.xml";

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
	public void setFlightRecorderImpl(FRImpl flightRecorderImpl) { this.frImpl = flightRecorderImpl; }
	public void setHealthTrackerImpl(HealthTrackerImpl healthTrackerImpl) {this.healthTrackerImpl = healthTrackerImpl;}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public static void main(String[] args) throws ExceptionControlApp {

		FrameworkMainImpl frameworkMain = null;
		try {
			context = new ClassPathXmlApplicationContext(SPRING_CONTEXT_FILENAME); // Start the Spring container
			frameworkMain = (FrameworkMainImpl) context.getBean("frameworkMain");
		} catch (Throwable e1) {
			log.error("Failed to initialize the Spring framework." + e1.getMessage());
			System.exit(1);
		}
		FMHolder.set(frameworkMain);

		/* Check if debug run */
		for(int i=0;i<args.length;i++) {
			if(args[i].contains("debug")) {
				frameworkMain.debugRun = true;
				break;
			}
		}
		
		try {
			frameworkMain.init();
		} catch (Throwable e2) {
			System.exit(2);
		}
		
		if(args.length > 0) {
			if(args[0].equals("reset")) {
				ResetLevel level = args.length > 1 ? ResetLevel.valueOf(args[1], ResetLevel.soft) : ResetLevel.soft;
				try {
					frameworkMain.reset(level);
				} catch (Throwable e3) {
					System.exit(3);
				}
				log.info("Reset is done");
				System.exit(0);
			} else if(args[0].equals("test")) {
				try {
					frameworkMain.appRoot.test(null);
					frameworkMain.test();
				} catch (Exception e4) {
					log.error("The framework/application test failed." + e4.getLocalizedMessage());
					System.exit(4);
				}
				System.exit(0);
			}
		}

		/* Bring-up of the jetty web server to serve REST requests. */
		try {
			frameworkMain.frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Rest server is starting");
			frameworkMain.restServer.start();
		} catch (Throwable e) {
			frameworkMain.stopRestServer();
			frameworkMain.finit();
			System.exit(9);
		}

		try {
			frameworkMain.restServer.join();
			frameworkMain.stopRestServer();
			frameworkMain.finit();
		} catch (InterruptedException e) {
			System.exit(10);
		}
	}
	
	protected void stopRestServer() {
		try {
			/* Stop rest server */
			if(restServer != null) {
				frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Rest server is stopping");
				restServer.stop();
			}
		} catch (Throwable e) {/* Ignore interrupt exceptions. */}
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
		log.info("Framework is starting");
		setupRESTServer();

		/* RepoFactoryImpl initialization */
		repoFactoryImpl.init();

		/* This part of frameworkMain init can only be done after RepoFactoryImpl init. */
		frameworkEM = repoFactoryImpl.createFrameworkMainEM(FRAMEWORK_CORE_EM_ID, stateClassPaths);
		try {
			coreStateRepo = (Repo<String>) repoFactoryImpl.getOrCreateRepo(RepoMajor.FWORK_GLOBAL.name(), 
					RepoMinor.CORE_STATE.name(), StringSerializer.get(), true, CoreState.getRCDs());
			hostAddr = (String)coreStateRepo.getCellValue(CoreState.FWORK_CORE_STATE_ROW_KEY, CoreState.HOST_ADDRESS);
		} catch (Throwable e1) {
			String msg = "Excepted trying to retrieve/construct the framework coreStateRepo";
			log.error(msg, e1);
			throw new ExceptionControlApp(msg);
		}

		/* init framework FlightLogger */
		frImpl.init();
		frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Is starting");

		/* Other modules initialization */
		peerCommunicatorImpl.init();
		clusterMgrImpl.init();
		frameworkMgmtPointImpl.init();

		frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Is starting " + appRoot.name);
		appRoot.init();

		/* Mark completion of all initializations allowing all periodic operations to start working and using
		 * repository and other services. */
		log.info("Is openForBusiness "+ appRoot.name );
		openForBusiness = true;
	}

	/**
	 * Cleans up all modules before shutdown - top-down, app first, org.opendaylight.ctlapps.framework next.
	 * @throws exception_type circumstances description 
	 */
	public void finit() {

		frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Is stopping");

		openForBusiness = false;

		frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Is stopping " + appRoot.name);
		try {
			appRoot.finit();
		} catch (Exception e1) {
			String appName = (appRoot.name != null) ? appRoot.name : "No appName";
			log.error("The application " + appName + " did not properly stop." + e1.toString());
		}

		frameworkMgmtPointImpl.finit();
		clusterMgrImpl.finit();
		peerCommunicatorImpl.finit();

		frImpl.finit();
		try {
			frameworkEM.flush(); // In current Hector implementation this is a no op, but still...
		} catch (Throwable e) {
			log.error("Could not flush the framework EM." + e.getMessage());
		}
		repoFactoryImpl.finit();
	}

	/**
	 * Performs factory reset on all modules - first the application, then all org.opendaylight.ctlapps.framework modules top down.
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {

		frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Is "+ resetLevel + " resetting");
		openForBusiness = false;
		StringBuilder msg = new StringBuilder();
		Boolean success = true;
		frImpl.logRecord(FR_FRAMEWORK_OPERATIONAL, "Is " + resetLevel + " resetting " + appRoot.name);
		try {
			appRoot.reset(resetLevel);
		} catch (Throwable e5) {
			log.error("Could not reset the application " + appRoot.name + "." + e5.getMessage());
			frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, appRoot.name + " failed to reset");			
			success = false;
			msg.append("Failed to reset application " + appRoot.name);
			// TODO: continue anyway or return - depending on resetLevel
		}

		try {
			frameworkMgmtPointImpl.reset(resetLevel);
		} catch (Throwable e1) {
			log.error("Could not reset framework mgmt point impl." + e1.getMessage());
			frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Management point failed to reset");
			success = false;
			msg.append("Failed to reset some framework modules");
			// TODO: continue anyway or return - depending on resetLevel
		}
		try {
			clusterMgrImpl.reset(resetLevel);
		} catch (Throwable e2) {
			log.error("Could not reset framework mgmt cluster mgr impl." + e2.getMessage());
			frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Cluster Manager failed to reset");
			success = false;
			msg.append("Failed to reset some framework modules");
			// TODO: continue anyway or return - depending on resetLevel
		}
		try {
			peerCommunicatorImpl.reset(resetLevel);
		} catch (Throwable e3) {
			log.error("Could not reset framework peer communicator impl." + e3.getMessage());
			frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Peer Communicator failed to reset");
			success = false;
			msg.append("Failed to reset some framework modules");
			// TODO: continue anyway or return - depending on resetLevel
		}
		try {
			frImpl.reset(resetLevel);
		} catch (Throwable e4) {
			log.error("Flight recorder failed to reset." + e4.getMessage());
			frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Flight Recorder failed to reset");
			success = false;
			msg.append("Failed to reset some framework modules");
			// TODO: continue anyway or return - depending on resetLevel
		}
		try {	// Delay repos clear to allow pending threads complete their operations
			Thread.sleep(5000);
		} catch (InterruptedException e) {}
		try {
			repoFactoryImpl.reset(resetLevel);
		} catch (Throwable e4) {
			log.error("Could not reset framework repo factory impl." + e4.getMessage());
			frImpl.logRecord(FrameworkMain.FR_FRAMEWORK_FAILURE, "Repo Factory failed to reset");
			success = false;
			msg.append("Failed to reset some framework modules");
			// TODO: continue anyway or return - depending on resetLevel
		}
		
		if(!success)
			throw new ExceptionControlApp(msg.toString());
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
	public FR getFR() {return frImpl;}

	/* Set the framework logic web parts */
	protected void setupRESTServer() throws ExceptionControlApp {

		try {
			restServer = new Server(port);	

			WebAppContext fworkWebAppContext = new WebAppContext();
			fworkWebAppContext.setContextPath(restPath);
			WebAppClassLoader classLoader = new WebAppClassLoader(this.getClass().getClassLoader(), fworkWebAppContext);
			fworkWebAppContext.setClassLoader(classLoader);
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

			restServer.setHandler(fworkWebAppContext);
		} catch (Throwable e) {
			log.error("Failed to initialize Rest server." + e.getMessage());
			healthTrackerImpl.reportHealthIssue(HealthTracker.SIGNIFICANT_HEALTH_ISSUE);
			frImpl.logRecord(FR_FRAMEWORK_FAILURE, "Failed to initialize Rest server");
			throw new ExceptionControlApp("Failed to initialize Rest server.", e);
		}
	}

	@Override
	public void requestShutdown(boolean graceful) {
		if(graceful) finit();
		System.exit(0);
	}

	public void requestReset(ResetLevel resetLevel) throws ExceptionControlApp {
		reset(resetLevel);
		init();		
	}

	@Override
	public HealthTracker getHealthTracker() {return healthTrackerImpl;}

	@Override
	public boolean isOpenForBusiness() {return openForBusiness;}

	@Override
	public boolean isDebugRun() {return debugRun;}

	@Override
	public String getHostAddr() {return hostAddr;}
}
