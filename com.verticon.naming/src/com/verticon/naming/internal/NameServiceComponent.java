/*******************************************************************************
 * Copyright (c) 2014 Verticon, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Verticon, Inc. - initial API and implementation
 *******************************************************************************/
package com.verticon.naming.internal;

import java.util.HashMap;
import java.util.Map;

import com.verticon.naming.INameService;


public class NameServiceComponent implements INameService {

	private final Map<String, String> nameMap = new HashMap<String, String>();
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NameServiceComponent []";
	}



	@Override
	public String getName(String account) {
		return nameMap.get(account);
	}



	public void activate() {

	}

	public void deactivate() {
		nameMap.clear();
	}



	@Override
	public void load(Map<String, String> accountNames) {
		nameMap.clear();
		nameMap.putAll(accountNames);
	}


}
