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
package com.verticon.treatment.poi.handlers;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.verticon.treatment.Program;

public interface ExecutableProcreator extends Procreator {
	
	/**
	 * 
	 * @param fair
	 * @param row
	 * @param newParent
	 * @param editingDomain
	 * @throws MissingCriticalDataException
	 */
	void prepare(Program fair, HSSFRow row, EditingDomain editingDomain)
			throws MissingCriticalDataException;

    /**
     * Execute commands prepared by the ExecutableProcreator
     */
	void execute(EditingDomain ed);
    
    Exception getError();
    
    
}