/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */


package org.opendaylight.defense4all.framework.core;

import java.util.Properties;








import org.opendaylight.defense4all.framework.core.FrameworkMain.ResetLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.prettyprint.cassandra.serializers.BooleanSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.hector.api.Serializer;


public class AppRoot {

	public FrameworkMain fMain;
	public Serializer<String> sSer;
	public Serializer<Integer> iSerializer;
	public Serializer<Boolean> bSerializer;
	public PropertiesSerializer pSerializer;
	public String name;
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	/** Constructor for Spring
	 * @param param_name 
	 */
	public AppRoot() {
		sSer = StringSerializer.get();
		iSerializer = IntegerSerializer.get();
		bSerializer = BooleanSerializer.get();
		pSerializer = PropertiesSerializer.get();	
		name = "";
	}
	
	/* Setters for Spring */
	public void setFrameworkMain(FrameworkMain frameworkMain) {this.fMain = frameworkMain;}

	/**
	 * Initializes all modules after construction. App specific - should be overridden.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void init() throws ExceptionControlApp {
		// init all modules
	}

	/**
	 * Cleans up all modules before shutdown. App specific - should be overridden.
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void finit() {
		// cleanup all modules
	}

	/**
	 * Resets the application according to reset level.
	 * @param param_name param description
	 * @return return description
	 * @throws ExceptionControlApp 
	 * @throws exception_type circumstances description 
	 */
	public void reset(ResetLevel resetLevel) throws ExceptionControlApp {
		// perform factory reset in all modules
	}

	/**
	 * Test
	 * @param param_name param description
	 * @return return description
	 * @throws exception_type circumstances description 
	 */
	public void test(Properties props) {
		// perform test of choice
	}

	public void setHostAddr(String hostAddr) {}
}
