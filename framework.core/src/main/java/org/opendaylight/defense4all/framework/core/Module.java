/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core; 

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.access.InvalidInvocationException;

public abstract class Module {

	protected Logger log = LoggerFactory.getLogger(this.getClass());

	protected class Execution implements Runnable {

		protected static final int ACTION_INVALID = -1;
		protected static final int ACTION_RESERVED = 0;

		protected int actionCode = ACTION_INVALID;
		protected Object param = null;

		public Execution(int actionCode, Object param) {		
			this.actionCode = actionCode;
			this.param = param;
		}

		public void run() {
			try {
				actionSwitcher(actionCode, param);
			} catch (Throwable e) {
				String paramStr = param == null ? "null" : param.toString();
				log.error("Excepted in Execution run. actionCode = " + actionCode + ", param = " + paramStr);
				FMHolder.get().getHealthTracker().reportHealthIssue(HealthTracker.MODERATE_HEALTH_ISSUE);
			}
		}
	}

	protected FrameworkMain fMain;
	protected ExecutorService decoupleExecutor;
	protected ScheduledExecutorService periodicExecutor;
	protected List<Thread> backgroundExecutionThreads;

	// Synchronized common logging
	protected FR fr = null;

	/* Constructor for Spring */
	public Module() {decoupleExecutor = null; periodicExecutor = null; backgroundExecutionThreads = null;}

	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {this.fMain = frameworkMain;}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected abstract void actionSwitcher(int actionCode, Object param);

	/** Post-constructor initialization
	 * @throws Exception 
	 * @throws ExceptionRepoFactoryInternalError 
	 */
	public void init() throws ExceptionControlApp {
		fr = fMain.getFR();
	}

	/** Pre-shutdown cleanup
	 */
	public void finit() {
	}

	/** Factory reset
	 */
	public void reset(ResetLevel level) throws ExceptionControlApp {

		boolean terminated;
		boolean gotCurrentTermination = false;;
		if(decoupleExecutor != null) {
			try {
				decoupleExecutor.shutdown();
				log.info("Waiting for thread termination");
				terminated = decoupleExecutor.awaitTermination(10, TimeUnit.SECONDS);
				if(!terminated) {
					decoupleExecutor.shutdownNow();
					log.info("Waiting for thread termination now");
					terminated = decoupleExecutor.awaitTermination(5, TimeUnit.SECONDS);
					if(!terminated)
						log.error("Some threads did not shutdownNow.");
				}
			} 
			catch (InterruptedException ie) {
				try {					
					decoupleExecutor.shutdownNow(); // (Re-)Cancel if current thread also interrupted					
					gotCurrentTermination = true; // Preserve interrupt status
				} catch (Throwable e) {	
					log.error("Failed to shutdown decoupleExecutor." + e.getLocalizedMessage());
				}
			}
			catch (Throwable e) {
				log.error("Failed to shutdown decoupleExecutor." + e.getLocalizedMessage());
			}
		}

		if(periodicExecutor != null) {
			try {
				periodicExecutor.shutdown();
				log.info("Waiting for thread termination");
				terminated = periodicExecutor.awaitTermination(10, TimeUnit.SECONDS);
				if(!terminated) {
					periodicExecutor.shutdownNow();
					log.info("Waiting for thread termination now");
					terminated = periodicExecutor.awaitTermination(5, TimeUnit.SECONDS);
					if(!terminated)
						log.error("Some threads did not shutdownNow.");
				}
			} 
			catch (InterruptedException ie) {
				try {					
					periodicExecutor.shutdownNow(); // (Re-)Cancel if current thread also interrupted					
					gotCurrentTermination = true; // Preserve interrupt status
				} catch (Throwable e) {	
					log.error("Failed to shutdown periodicExecutor." + e.getLocalizedMessage());
				}
			}
			catch (Throwable e) {
				log.error("Failed to shutdown periodicExecutor." + e.getLocalizedMessage());
			}
		}

		if(backgroundExecutionThreads != null) {
			for(Thread backgroundExecutionThread : backgroundExecutionThreads ) {
				backgroundExecutionThread.interrupt(); 
				try {
					backgroundExecutionThread.join(10000);
				} catch (InterruptedException e) {
					gotCurrentTermination = true; // Preserve interrupt status
					continue;
				} catch (Throwable e) {	
					log.error("Failed to shutdown backgroundExecutionThread." + e.getLocalizedMessage());
					continue;
				}
			}
		}
		
		if (gotCurrentTermination == true) { // Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}	

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void invokeDecoupledSerially(int actionCode, Object param) throws ExceptionControlApp {

		Execution execution = new Execution(actionCode, param);
		if(decoupleExecutor == null) decoupleExecutor = Executors.newSingleThreadExecutor();

		for(int i=0;i<3;i++) { // TODO: instead of looping add to a framework service to re-try invoking after some time
			try {
				decoupleExecutor.execute(execution);
				return;
			} catch (Throwable e) {
				log.error("Failed to execute " + execution.actionCode + ":" + execution.param.toString(), e);
			}
		}

		throw new ExceptionControlApp("Failed to execute " + execution.actionCode + ":" + execution.param.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void invokeDecoupledSerially(Runnable runnable) throws ExceptionControlApp {

		if(decoupleExecutor == null) decoupleExecutor = Executors.newSingleThreadExecutor();

		for(int i=0;i<3;i++) { // TODO: instead of looping add to a framework service to re-try invoking after some time
			try {
				decoupleExecutor.execute(runnable);
				return;
			} catch (Throwable e) {
				log.error("Failed to execute." + e.getLocalizedMessage());
			}
		}
		throw new ExceptionControlApp("Failed to execute.");
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void addPeriodicExecution(int actionCode, Object param, Long intervalInSecs) throws ExceptionControlApp {

		Execution execution = new Execution(actionCode, param);
		if(periodicExecutor == null) periodicExecutor = Executors.newSingleThreadScheduledExecutor();

		for(int i=0;i<3;i++) { // TODO: instead of looping add to a framework service to re-try invoking after some time
			try {
				periodicExecutor.scheduleAtFixedRate(execution, 0, intervalInSecs, TimeUnit.SECONDS); // start the first cycle immediately
				return;
			} catch (Throwable e) {
				log.error("Failed to execute "+execution.actionCode + ":"+execution.param.toString()+e.getLocalizedMessage());
			}
		}
		throw new ExceptionControlApp("Failed to execute " + execution.actionCode + ":" + execution.param.toString());
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void addPeriodicExecution(Runnable runnable, Long intervalInSecs) throws InvalidInvocationException, ExceptionControlApp {

		if(periodicExecutor == null) periodicExecutor = Executors.newSingleThreadScheduledExecutor();

		for(int i=0;i<3;i++) { // TODO: instead of looping add to a framework service to re-try invoking after some time
			try {
				periodicExecutor.scheduleAtFixedRate(runnable, 0, intervalInSecs, TimeUnit.SECONDS); // start the first cycle immediately
				return;
			} catch (Throwable e) {
				log.error("Failed to execute." + e.getLocalizedMessage());
			}
		}
		throw new ExceptionControlApp("Failed to execute.");
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void addBackgroundTask(int actionCode, Object param) throws ExceptionControlApp {
		Execution backGroundExecution = new Execution(actionCode, param);
		addBackgroundTask(backGroundExecution);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	protected void addBackgroundTask(Runnable runnable) throws ExceptionControlApp {

		Thread backgroundExecutionThread = new Thread(runnable);
		if(backgroundExecutionThreads == null) backgroundExecutionThreads = new ArrayList<Thread>();
		backgroundExecutionThreads.add(backgroundExecutionThread);

		for(int i=0;i<3;i++) { // TODO: instead of looping add to a framework service to re-try invoking after some time
			try {
				backgroundExecutionThread.start();
				return;
			} catch (Throwable e) {
				log.error("Failed to start backgroundExecutionThread." + e.getLocalizedMessage());
			}
		}
		throw new ExceptionControlApp("Failed to start backgroundExecutionThread.");
	}
}
