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
package com.verticon.naming;

import java.util.Map;

public interface INameService {
	
	/**
	 * 
	 * @param account
	 * @return the name of the account or a null
	 */
	public String getName(String account);

	public void load(Map<String, String> accountNames);


}
