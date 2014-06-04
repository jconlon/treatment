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

import static com.verticon.treatment.poi.handlers.PoiUtils.convert;
import static com.verticon.treatment.poi.handlers.PoiUtils.getWorkSheet;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.handlers.HandlerUtil;

import com.verticon.treatment.Program;

/**
 * @author jconlon
 * 
 */
public class EventImportHandler extends AbstractHandler {
	
	private StringBuffer message;
	private volatile Exception ex = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ex = null;
		message = new StringBuffer("Imported: ");
		IEditorPart editorPart = HandlerUtil.getActiveEditorChecked(event);

		EditingDomain editingDomain = ((IEditingDomainProvider) editorPart
				.getAdapter(IEditingDomainProvider.class)).getEditingDomain();

		Resource r = editingDomain.getResourceSet().getResources().get(0);
		EObject o = r.getContents().get(0);
		if (!(o instanceof Program)) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					"Event Import Failed",
					"Treatment Document must contain a Program Element");
			return false;
		}

		Program program = (Program) o;

		IRunnableWithProgress op = new WorkspaceModifyDelegatingOperation(
				getRunnable(program,
						(IStructuredSelection) HandlerUtil
								.getActiveMenuSelectionChecked(event),
						editingDomain));

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				HandlerUtil.getActiveShell(event));
		try {
			dialog.run(false, true, op);

			if (ex == null) {
				MessageDialog.openInformation(
						HandlerUtil.getActiveShell(event), "Event Data Import",
						message.toString());
			} else {
				MessageDialog.openError(HandlerUtil.getActiveShell(event),
						"Event Data Import Failed", ex.getMessage());
			}


		} catch (InvocationTargetException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					"Event Data Import Failed", e.getMessage());
		} catch (InterruptedException e) {
			// Restore the interrupted status
			Thread.currentThread().interrupt();
		}
		return null;
	}

	private IRunnableWithProgress getRunnable(final Program program,
			final IStructuredSelection selection, final EditingDomain ed) {
		return new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {

				monitor.beginTask("Processing " + selection.size()
						+ " worksheets...", Monitor.UNKNOWN);
				Iterator<?> it = selection.iterator();
				while (it.hasNext()) {
					try {
						processWorkSheet(it.next(), program, ed, monitor);

					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}
				}

			}
		};
	}

	private int processWorkSheet(Object o, Program program, EditingDomain ed,
			IProgressMonitor monitor) throws Exception {
		File f = convert(o);
		HSSFSheet ws = getWorkSheet(f);
		ExecutableProcreator procreator;
		if (PoiUtils.isWorkSheetMatch(PoiUtils.TESTLOG_HEADER, ws)) {
			procreator = ExecutableProcreatorFactory.newTestEventProcreator();
			System.out.printf("Processing Test Event Log with %s rows%n",
					ws.getLastRowNum());

		} else {
			procreator = ExecutableProcreatorFactory.newTreatmentEventProcreator();
			System.out.printf("Processing Treatment Event Log with %s rows%n",
				ws.getLastRowNum());
		}
		// Exception ex = null;
		int count = 0;

		try {
			int rowsInSheet = ws.getPhysicalNumberOfRows();
			for (int i = 1; i < rowsInSheet; i++) {
				System.out.printf(
					"Processing row %s in spreadsheet with %s rows.%n",
					 i + 1,
					rowsInSheet);

				monitor.worked(1);
				procreator.prepare(program, ws.getRow(i), ed);

				count++;
			}
			procreator.execute(ed);
			message.append(procreator.getStatus());
		} catch (Exception e) {
			ex = e;
		} finally {
			procreator.dispose();
		}
		if (ex != null) {
			throw ex;
		}

		return count;
	}

}
