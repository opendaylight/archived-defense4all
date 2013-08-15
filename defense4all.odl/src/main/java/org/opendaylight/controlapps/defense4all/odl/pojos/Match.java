/**
 * Copyright (c) <2013> <Radware Ltd.> and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 * @author Gera Goft 
 * @version 0.1
 */

package org.opendaylight.controlapps.defense4all.odl.pojos;

import java.util.List;

public class Match {
	
	public List<MatchField> matchField;
	
    public Match() {matchField = null;}

	public List<MatchField> getMatchField() {return matchField;}
	public void setMatchField(List<MatchField> matchField) {this.matchField = matchField;}

	@Override
    public String toString() {return "Match [ " +  matchField.toString() + "]";}
}