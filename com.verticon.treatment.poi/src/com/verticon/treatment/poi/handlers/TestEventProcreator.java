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

import static com.verticon.treatment.poi.handlers.PoiUtils.RESULT_COL;
import static com.verticon.treatment.poi.handlers.PoiUtils.TEST_COL;
import static com.verticon.treatment.poi.handlers.PoiUtils.TEST_PBT;
import static com.verticon.treatment.poi.handlers.PoiUtils.TEST_UA;
import static com.verticon.treatment.poi.handlers.PoiUtils.addComments;
import static com.verticon.treatment.poi.handlers.PoiUtils.addDate;
import static com.verticon.treatment.poi.handlers.PoiUtils.addDescription;
import static com.verticon.treatment.poi.handlers.PoiUtils.getCriticalStringValue;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CompoundCommand;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.domain.EditingDomain;

import com.verticon.treatment.Event;
import com.verticon.treatment.Person;
import com.verticon.treatment.Program;
import com.verticon.treatment.Test;
import com.verticon.treatment.TreatmentFactory;
import com.verticon.treatment.TreatmentPackage;

/**
 * Build a TestEvent from the row: "DateTime","Test","Result", "Comments"
 * "DateTime","Test","Description","Result","Comments"
 * 
 * @author jconlon
 * 
 */
public class TestEventProcreator implements Procreator {
	private int totalChildrenAdded = 0;
	private final Set<Event> eventsAddedToProgram = new HashSet<Event>();

	@Override
	public void process(Program program, HSSFRow row, EObject parent,
			boolean newParent, EditingDomain editingDomain,
			CompoundCommand compoundCommand)
			throws MissingCriticalDataException {

		Event proposedEvent = buildProposedEvent(row);

		Event eventInCache = cachedInstance(proposedEvent);

		if (eventInCache == null) {
			Event eventInPerson = getEventInPerson((Person) parent,
					proposedEvent);

			if (eventInPerson != null) {// event is in the program
				System.out.printf(
						"Row=%s event %s is already in the Person %s.%n",
						row.getRowNum(), eventInPerson,
						eventInPerson.getPerson());
				// logger.info(bundleMarker,"Row={} person {} is already in Fair.",
				// row
				// .getRowNum(), personName);

			} else {// Event is not in the program, Create it.

				addCommand(parent, proposedEvent, editingDomain,
						compoundCommand);

			}
		} else {
			System.out.printf("Row=%s event %s is already created.%n",
					row.getRowNum(), eventInCache);

		}

	}

	/**
	 * "DateTime","Test","Description","Result","Comments"
	 * 
	 * @param row
	 * @return
	 * @throws MissingCriticalDataException
	 */
	private Event buildProposedEvent(HSSFRow row)
			throws MissingCriticalDataException {
		Test proposedEvent = null;
		// Two types of test events
		String type = getTestType(row);
		if (TEST_PBT.equals(type)) {
			proposedEvent = TreatmentFactory.eINSTANCE.createBreathalyzer();
		} else if (TEST_UA.equals(type)) {
			proposedEvent = TreatmentFactory.eINSTANCE.createUrinalysis();
		}
		addDate(proposedEvent, row);
		addDescription(proposedEvent, row);
		addResult(proposedEvent, row);
		addComments(proposedEvent, row);

		return proposedEvent;
	}


	private void addResult(Test event, HSSFRow row)
			throws MissingCriticalDataException {
		BigDecimal value = PoiUtils.getCriticalDecimalValue(row,
				TreatmentPackage.Literals.TEST__VALUE, RESULT_COL);
		event.setValue(value);
	}

	@Override
	public void dispose() {
		eventsAddedToProgram.clear();
	}

	@Override
	public String getStatus() {
		StringBuilder sbuilderBuilder = new StringBuilder();
		sbuilderBuilder.append(totalChildrenAdded).append(" events, ");
		return sbuilderBuilder.toString();
	}


	private String getTestType(HSSFRow row) throws MissingCriticalDataException {
		String value = getCriticalStringValue(row,
				TreatmentPackage.Literals.PERSON__EVENTS, TEST_COL);
		if ((TEST_PBT.equals(value)) || (TEST_UA.equals(value))) {
			return value;
		}
		throw new MissingCriticalDataException(
"Unknown value =" + value
				+ " Should be " + TEST_PBT + " or " + TEST_UA, TEST_COL,
				TreatmentPackage.Literals.PERSON__EVENTS, row.getRowNum());
	}

	private Event cachedInstance(Event event) {
		Event results = null;

		for (Event addedElement : eventsAddedToProgram) {
			if (addedElement.equals(event)) {
				results = addedElement;
				break;
			}
		}

		return results;
	}

	private Event getEventInPerson(Person person, Event event) {
		Event result = null;
		for (Event eventInPerson : person.getEvents()) {
			if (eventInPerson.equals(event)) {
				result = eventInPerson;
			}
		}
		return result;
	}

	private void addCommand(EObject owner, Event event,
			EditingDomain editingDomain, CompoundCommand compoundCommand)
			throws MissingCriticalDataException {

		totalChildrenAdded++;

		Command command = AddCommand.create(editingDomain, // domain
				owner,// owner
				TreatmentPackage.Literals.PERSON__EVENTS,// feature
				event// value
				);
		compoundCommand.append(command);

		if (!eventsAddedToProgram.contains(event)) {
			eventsAddedToProgram.add(event);
		}
		System.out.printf("Event %s added create command.%n", event);
		// logger.info(bundleMarker,"Row={} added a command to create Person {}.",
		// row
		// .getRowNum(), person.getName());
	}
}
