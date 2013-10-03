/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.defense4all.framework.core.impl;

import me.prettyprint.hom.annotations.Column;
import me.prettyprint.hom.annotations.Id;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ASTable")
public class AnnotatedState {

	@Id
	public String rowKey;
	
	@Column(name = "a")
	public String a;
	
	@Column(name = "b")
	public boolean b;
	
	@Column(name = "c")
	public Object c;
	
	public String getRowKey() {return rowKey;}
	public void setRowKey(String rowKey) {this.rowKey = rowKey;}
	public String getA() {return a;}
	public void setA(String a) {this.a = a;}
	public boolean getB() {return b;}
	public void setB(boolean b) {this.b = b;}
	public String getC() {return c.toString();}
	public void setC(String c) {this.c = c;}

	public AnnotatedState() {
		rowKey = "zero";
		a = "default_a";
		b = false;
		c = new Object(); c = "default c object";
	}

	public AnnotatedState(String rowKey, String a, boolean b, Object c) {
		this.rowKey = rowKey;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	public void printObject() {

        System.out.print("rowKey = " + rowKey + "; ");
        System.out.print("a = " + a + "; ");
        System.out.print("b = " + b + "; ");
        System.out.println(getC());
	}
}

