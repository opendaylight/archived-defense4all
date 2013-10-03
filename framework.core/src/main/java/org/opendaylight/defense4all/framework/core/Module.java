/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.springframework.jmx.access.InvalidInvocationException;


public abstract class Module {
	
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
			} catch (Exception e) {/* Ignore*/}
		}
	}

	protected FrameworkMain frameworkMain;
	protected ExecutorService decoupleExecutor;
	protected ScheduledExecutorService periodicExecutor;

	/* Constructor for Spring */
	public Module() {
		decoupleExecutor = null; periodicExecutor = null;
	}
	
	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {this.frameworkMain = frameworkMain;}
	
	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected abstract void actionSwitcher(int actionCode, Object param);

	/** Post-constructor initialization
	 * @throws ExceptionControlApp 
	 */
	public void init() throws ExceptionControlApp {
	}

	/** Pre-shutdown cleanup
	 */
	public void finit() {
	}

	/** Factory reset
	 */
	public void reset(ResetLevel level) {
	}	

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void invokeDecoupledSerially(int actionCode, Object param) {
		Execution execution = new Execution(actionCode, param);
		if(decoupleExecutor == null)
			decoupleExecutor = Executors.newSingleThreadExecutor();
		decoupleExecutor.execute(execution);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void invokeDecoupledSerially(Runnable runnable) {
		if(decoupleExecutor == null)
			decoupleExecutor = Executors.newSingleThreadExecutor();
		decoupleExecutor.execute(runnable);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addPeriodicExecution(int actionCode, Object param, Long intervalInSecs) throws InvalidInvocationException {
		Execution execution = new Execution(actionCode, param);
		if(periodicExecutor == null)
			periodicExecutor = Executors.newSingleThreadScheduledExecutor();
		periodicExecutor.scheduleAtFixedRate(execution, 0, intervalInSecs, TimeUnit.SECONDS); // start the first cycle immediately
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addPeriodicExecution(Runnable runnable, Long intervalInSecs) throws InvalidInvocationException {
		if(periodicExecutor == null)
			periodicExecutor = Executors.newSingleThreadScheduledExecutor();
		periodicExecutor.scheduleAtFixedRate(runnable, 0, intervalInSecs, TimeUnit.SECONDS); // start the first cycle immediately
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addBackgroundTask(int actionCode, Object param) {
		Execution execution = new Execution(actionCode, param);
		addBackgroundTask(execution);
	}

	/**
	 * #### method description ####
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	protected void addBackgroundTask(Runnable runnable) {
		Thread thread = new Thread(runnable);
		thread.start();	
	}
}
