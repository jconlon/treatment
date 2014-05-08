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

import static com.verticon.treatment.poi.handlers.PoiUtils.TREATMENT_COL;
import static com.verticon.treatment.poi.handlers.PoiUtils.TREATMENT_GRP;
import static com.verticon.treatment.poi.handlers.PoiUtils.TREATMENT_INDV;
import static com.verticon.treatment.poi.handlers.PoiUtils.TREATMENT_SELF;
import static com.verticon.treatment.poi.handlers.PoiUtils.addComments;
import static com.verticon.treatment.poi.handlers.PoiUtils.addDate;
import static com.verticon.treatment.poi.handlers.PoiUtils.addDescription;
import static com.verticon.treatment.poi.handlers.PoiUtils.getCriticalStringValue;

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
import com.verticon.treatment.Treatment;
import com.verticon.treatment.TreatmentFactory;
import com.verticon.treatment.TreatmentPackage;

/**
 * Build a TreatmentEvent from the row: "DateTime","Treatment", "Description",
 * "Comments"
 * 
 * @author jconlon
 * 
 */
public class TreatmentEventProcreator implements Procreator {
	private int totalChildrenAdded = 0;
	private final Set<Treatment> eventsAddedToProgram = new HashSet<Treatment>();

	@Override
	public void process(Program program, HSSFRow row, EObject parent,
			boolean newParent, EditingDomain editingDomain,
			CompoundCommand compoundCommand)
			throws MissingCriticalDataException {

		Treatment proposedEvent = buildProposedTreatmentEvent(row);

		Treatment eventInCache = cachedInstance(proposedEvent);

		if (eventInCache == null) {
			Treatment eventInPerson = (Treatment) getEventInPerson(
					(Person) parent,
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

	private Treatment buildProposedTreatmentEvent(HSSFRow row)
			throws MissingCriticalDataException {
		Treatment proposedEvent;
		// Two types of test events
		String type = getEventType(row);
		if (TREATMENT_GRP.equals(type)) {
			proposedEvent = TreatmentFactory.eINSTANCE.createGroupTreatment();
		} else if (TREATMENT_INDV.equals(type)) {
			proposedEvent = TreatmentFactory.eINSTANCE
					.createIndivdualTreatment();
		} else if (TREATMENT_SELF.equals(type)) {
			proposedEvent = TreatmentFactory.eINSTANCE.createSelfHelp();
		} else {
			throw new IllegalStateException(
					"Failed to determine treatment type.");
		}
		addDate(proposedEvent, row);
		addComments(proposedEvent, row);
		addDescription(proposedEvent, row);
		return proposedEvent;
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


	private String getEventType(HSSFRow row) throws MissingCriticalDataException {
		String value = getCriticalStringValue(row,
				TreatmentPackage.Literals.PERSON__EVENTS, TREATMENT_COL);
		if (TREATMENT_INDV.equals(value) || TREATMENT_GRP.equals(value)
				|| TREATMENT_SELF.equals(value)) {
			return value;
		}
		throw new MissingCriticalDataException(
"Unknown value =" + value
				+ " Should be " + TREATMENT_INDV + " or " + TREATMENT_GRP
				+ " or " + TREATMENT_SELF, TREATMENT_COL,
				TreatmentPackage.Literals.PERSON__EVENTS, row.getRowNum());
	}

	private Treatment cachedInstance(Event event) {
		Treatment results = null;

		for (Treatment addedElement : eventsAddedToProgram) {
			if (addedElement.equals(event)) {
				results = addedElement;
				break;
			}
		}

		return results;
	}

	private Event getEventInPerson(Person person, Treatment event) {
		Event result = null;
		for (Event eventInPerson : person.getEvents()) {
			if (eventInPerson.equals(event)) {
				result = eventInPerson;
			}
		}
		return result;
	}

	private void addCommand(EObject owner, Treatment event,
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
