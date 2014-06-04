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

import static com.verticon.treatment.poi.handlers.PoiUtils.ACCOUNT_COL;

import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.verticon.treatment.Person;
import com.verticon.treatment.Program;
import com.verticon.treatment.TreatmentFactory;
import com.verticon.treatment.TreatmentPackage;

/**
 * @author jconlon
 * 
 */
class PersonProcreator implements ExecutableProcreator {

	/**
	 * Reference to the Set of {@link Department}s keyed by the parent
	 * {@link Division} . These are new Departments that will be created when
	 * the {@link #execute(EditingDomain)} is called.
	 */
	private final Set<Person> personsAddedToProgram = new HashSet<Person>();

	private int totalChildrenAdded = 0;

	private final Procreator child;

	private Exception exception = null;

	private CompoundCommand localCompoundCommand = null;

	protected PersonProcreator(Procreator child) {
		super();
		this.child = child;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verticon.treatment.poi.importWizards.Procreator#process(com.verticon
	 * .treatment.Program, org.apache.poi.hssf.usermodel.HSSFRow,
	 * org.eclipse.emf.ecore.EObject, boolean,
	 * org.eclipse.emf.edit.domain.EditingDomain,
	 * org.eclipse.emf.common.command.CompoundCommand)
	 */
	public void process(Program program, HSSFRow row, EObject parent,
			boolean parentWasCreated, EditingDomain editingDomain,
			CompoundCommand compoundCommand)
			throws MissingCriticalDataException {

		String account = getAccount(row);

		Person person = cachedInstance(account);

		boolean personWasCreated = false;
		// Program is the container for the Person.
		if (person == null) {
			person = getPersonInProgram(program, account);

			if (person != null) {// person is in the program
				System.out.printf("Row=%s person %s is already in Program.%n",
						row.getRowNum(), account);
				// logger.info(bundleMarker,"Row={} person {} is already in Fair.",
				// row
				// .getRowNum(), personName);

			} else {// Person is not in the program, Create it.
				person = newInstance(account, program,
						editingDomain, compoundCommand);
				personWasCreated = true;

			}
		} else {
			System.out.printf("Row=%s person %s is already created.%n",
					row.getRowNum(), account);
			// logger.info(bundleMarker,"Row={} found previously created Person {}.",
			// row
			// .getRowNum(), personName);
		}

		if (child != null) {
			child.process(program, row, person,
					personWasCreated, editingDomain, compoundCommand);
		}
	}

	@Override
	public String getStatus() {
		StringBuilder sbuilderBuilder = new StringBuilder();
		sbuilderBuilder.append(totalChildrenAdded).append(" person(s), ")
				.append(child == null ? "" : child.getStatus());
		return sbuilderBuilder.toString();
	}

	@Override
	public void dispose() {
		// logger.debug(bundleMarker,"Disposing");
		personsAddedToProgram.clear();
		child.dispose();
	}


	private Person getPersonInProgram(Program program, String accountName) {
		Person result = null;
		for (Person personInProgram : program.getPersons()) {
			if (personInProgram.getAccount().equals(accountName)) {
				result = personInProgram;
			}
		}
		return result;
	}


	private Person cachedInstance(String account) {
		Person results = null;

		for (Person addedElement : personsAddedToProgram) {
			if (addedElement.getAccount().equals(account)) {
				results = addedElement;
				break;
			}
		}

		return results;
	}


	private Person newInstance(String account, Program owner,
			EditingDomain editingDomain, CompoundCommand compoundCommand)
			throws MissingCriticalDataException {
		Person person = TreatmentFactory.eINSTANCE.createPerson();
		person.setAccount(account);
		totalChildrenAdded++;

		Command command = AddCommand.create(editingDomain, // domain
				owner,// owner
				TreatmentPackage.Literals.PROGRAM__PERSONS,// feature
				person// value
				);
		compoundCommand.append(command);

		if (!personsAddedToProgram.contains(person)) {
			personsAddedToProgram.add(person);
		}
		 System.out.printf("Person %s added create command.%n", account);
		// logger.info(bundleMarker,"Row={} added a command to create Person {}.",
		// row
		// .getRowNum(), person.getName());

		return person;
	}


	private String getAccount(HSSFRow row) throws MissingCriticalDataException {
		String account = PoiUtils
				.getCriticalStringValue(row,
						TreatmentPackage.Literals.PERSON__ACCOUNT,
 ACCOUNT_COL);
		return account;
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.verticon.treatment.poi.importWizards.ExecutableProcreator#prepare
	 * (com.verticon.treatment.Program, org.apache.poi.hssf.usermodel.HSSFRow,
	 * org.eclipse.emf.edit.domain.EditingDomain)
	 */
	public void prepare(Program program, HSSFRow row,
			EditingDomain editingDomain)
			throws MissingCriticalDataException {

		if (localCompoundCommand == null) {
			localCompoundCommand = new CompoundCommand();
		}

		try {
			process(program, row, null, false, editingDomain,
					localCompoundCommand);
		} catch (MissingCriticalDataException e) {
			this.exception = e;
			/**
			 * Prepare the ExecutableProcreator for execution.
			 * 
			 * @param fair
			 * @param row
			 * @param listColumnMapper
			 * @param parent
			 * @param newParent
			 * @param editingDomain
			 * @throws MissingCriticalDataException
			 *             when critical data in spreadsheets is missing.
			 */
			throw e;
		}
	}

	@Override
	public void execute(EditingDomain ed) {
		if (localCompoundCommand == null) {
			throw new IllegalStateException(
					"process method must be called first.");
		}
		if (localCompoundCommand.canUndo()) {
			System.out.println("Can undo");
		}
		ed.getCommandStack().execute(localCompoundCommand);

	}

	@Override
	public Exception getError() {
		return exception;
	}


}
