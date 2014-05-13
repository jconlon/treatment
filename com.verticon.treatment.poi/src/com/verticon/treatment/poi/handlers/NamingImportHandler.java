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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.ui.viewer.IViewerProvider;
import org.eclipse.emf.common.util.Monitor;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.handlers.HandlerUtil;

import com.verticon.treatment.poi.Activator;

/**
 * @author jconlon
 * 
 */
public class NamingImportHandler extends AbstractHandler {

	StringBuffer message = new StringBuffer("Imported: ");
	volatile Exception ex = null;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.
	 * ExecutionEvent)
	 */
	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {

		IEditorPart editorPart = HandlerUtil.getActiveEditorChecked(event);

		IRunnableWithProgress op = new WorkspaceModifyDelegatingOperation(
				getRunnable(
						(IStructuredSelection) HandlerUtil
						.getActiveMenuSelectionChecked(event), Activator
						.getDefault(), editorPart));

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				HandlerUtil.getActiveShell(event));
		try {
			dialog.run(false, true, op);

			if (ex == null) {
				MessageDialog.openInformation(
						HandlerUtil.getActiveShell(event), "Name Data Import",
						message.toString());
			} else {
				MessageDialog.openError(HandlerUtil.getActiveShell(event),
						"Name Data Import Failed", ex.getMessage());
			}


		} catch (InvocationTargetException e) {
			MessageDialog.openError(HandlerUtil.getActiveShell(event),
					"Name Data Import Failed", e.getMessage());
		} catch (InterruptedException e) {
			// Restore the interrupted status
			Thread.currentThread().interrupt();
		}
		return null;
	}

	private IRunnableWithProgress getRunnable(
			final IStructuredSelection selection, final Activator a,
			final IEditorPart editorPart) {
		return new IRunnableWithProgress() {

			@Override
			public void run(IProgressMonitor monitor)
					throws InvocationTargetException, InterruptedException {

				monitor.beginTask("Processing " + selection.size()
						+ " worksheets...", Monitor.UNKNOWN);
				Iterator<?> it = selection.iterator();
				Map<String, String> nameMap = new HashMap<String, String>();
				while (it.hasNext()) {
					try {
						processWorkSheet(it.next(), nameMap, monitor);

					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}
				}
				if (!nameMap.isEmpty()) {
					message.append(nameMap.size()).append(" names");
					System.out.printf(message.toString());
					a.loadNames(nameMap);
					if (editorPart instanceof IViewerProvider) {
						Viewer viewer = ((IViewerProvider) editorPart)
								.getViewer();
						if (viewer != null) {
							viewer.refresh();
						}
					}
				}
			}
		};
	}

	private int processWorkSheet(Object o, Map<String, String> nameMap,
			IProgressMonitor monitor) throws Exception {
		File f = convert(o);
		HSSFSheet ws = getWorkSheet(f);

		// Exception ex = null;
		int count = 0;

		try {
			for (int i = 1; i < ws.getLastRowNum() + 1; i++) {
				System.out.printf("Processing row %s%n", i);

				monitor.worked(1);

				String account = getStringValue(ws.getRow(i),
						PoiUtils.ACCOUNT_COL);
				
				if (account == null || account.length() == 0) {
					break;
				} else {
					String fName = getStringValue(
							ws.getRow(i), PoiUtils.FNAME_COL);
					if(fName == null || fName.length() == 0) {
						throw new Exception("Failed to find first Name in Row "+i);
					}
					String lName = getStringValue(
							ws.getRow(i), PoiUtils.LNAME_COL);
					if(lName == null || lName.length() == 0) {
						throw new Exception("Failed to find last Name in Row "+i);
					}
					StringBuilder builder = new StringBuilder(fName)
							.append(' ').append(lName);
					System.out.printf("Adding row %s account=%s name=%s %n", i,
							account,
							builder.toString());
					nameMap.put(account, builder.toString());
				}
				

				count++;
			}

		} catch (Exception e) {
			e.printStackTrace();
			ex = e;
		}
		if (ex != null) {
			throw ex;
		}

		return count;
	}

	static String getStringValue(HSSFRow row, int index) throws Exception {
		String result = null;

		if (index != -1) {

			HSSFCell cellContents = row.getCell(index);
			if (cellContents != null) {
				switch (cellContents.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					result = cellContents.getStringCellValue();
					break;

				default:
					throw new Exception(
							"The string value in a critical spreadsheet cell has the wrong data type. Please make sure your spreadsheet column number "
									+ index
									+ " is set to the string datatype. Row: "
									+ row.getRowNum()
									+ " Column Index: "
									+ index);

				}

			}

		}
		return result;
	}

}
