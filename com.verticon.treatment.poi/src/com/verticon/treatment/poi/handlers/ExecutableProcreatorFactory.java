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
/**
 * 
 */
package com.verticon.treatment.poi.handlers;



/**
 * Factory to create ExecutableProcreators
 * 
 * @author jconlon
 * 
 */
public class ExecutableProcreatorFactory {

	private ExecutableProcreatorFactory() {
		// Prevents instantiation
	}

	/**
	 * 
	 * @return ExecutableProcreator to import Test Events
	 */
	public static final ExecutableProcreator newTestEventProcreator() {
		return new PersonProcreator(new TestEventProcreator());
	}
    
	/**
	 * 
	 * @return ExecutableProcreator to import Treatment Events
	 */
	public static final ExecutableProcreator newTreatmentEventProcreator() {
		return new PersonProcreator(new TreatmentEventProcreator());
	}
   
}
