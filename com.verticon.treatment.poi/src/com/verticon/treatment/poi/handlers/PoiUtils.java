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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EStructuralFeature;

import com.verticon.treatment.Event;
import com.verticon.treatment.TreatmentPackage;

public class PoiUtils {

	public static final String[] TESTLOG_HEADER = { "Account", "DateTime",
			"Test", "Description", "Comments", "Result", };

	public static final String[] TREATMENTLOG_HEADER = { "Account", "DateTime",
			"Treatment", "Description", "Comments" };

	public static final int ACCOUNT_COL = 0;
	public static final int DATETIME_COL = 1;
	public static final int TEST_COL = 2;
	public static final int DESCRIPTION_COL = 3;
	public static final int COMMENTS_COL = 4;
	public static final int RESULT_COL = 5;

	public static final int TREATMENT_COL = 2;

	public static final String TEST_PBT = "PBT";
	public static final String TEST_UA = "UA";

	public static final String TREATMENT_INDV = "INDIVIDUAL";
	public static final String TREATMENT_GRP = "GROUP";
	public static final String TREATMENT_SELF = "SELF HELP";

	private PoiUtils() {
		// prevent creation
	}

	static HSSFSheet getWorkSheet(File f) {
		FileInputStream fin = null;
		HSSFSheet s = null;
		try {
			// create a new file input stream with the input file specified
			// at the command line
	
			fin = new FileInputStream(f);
			HSSFWorkbook w = new HSSFWorkbook(fin);
			s = w.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
			// logger.error(bundleMarker,"Failed to process the spreadsheet",e);
			// Create the required Status object
			// Status status = new Status(IStatus.ERROR,
			// "com.verticon.tracker.fair.editor", 0,
			// "Failed to open the Fair Editor", e);
			// // Display the dialog
			// ErrorDialog
			// .openError(
			// workbench.getShell(),
			// "Fair Data Import Error",
			// "Please insure the data file contains valid column names and row values.",
			// status);
	
		} finally {
			// once all the events are processed close our file input stream
			if (fin != null) {
				try {
					fin.close();
				} catch (IOException e) {
	
				}
			}
		}
		return s;
	
	}

	static File convert(Object o) throws CoreException {
		IFile file = (IFile) o;
		// gets URI for EFS.
		URI uri = file.getLocationURI();
	
		// what if file is a link, resolve it.
		if (file.isLinked()) {
			uri = file.getRawLocationURI();
		}
		// Gets native File using EFS
		return EFS.getStore(uri).toLocalFile(0, new NullProgressMonitor());
	}

	static String getCriticalStringValue(HSSFRow row,
			EStructuralFeature feature, int index)
			throws MissingCriticalDataException {
		String result = getStringValue(row, feature, index);
		if (result == null || result.trim().length() < 1) {
			throw new MissingCriticalDataException(
					"The data value in a critical spreadsheet cell is empty. Please remove all blank rows and fill in values for all critical cells.",
					index, feature, row.getRowNum());
		}
		return result;
	}

	static String getStringValue(HSSFRow row,
			EStructuralFeature feature, int index)
			throws MissingCriticalDataException {
		String result = null;
	
		    if (index !=-1) {
				try {
					HSSFCell cellContents = row.getCell(index);
					if (cellContents != null) {
						switch (cellContents.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							result = cellContents.getStringCellValue();
							break;
						
						default:
						throw new MissingCriticalDataException(
								"The string value in a critical spreadsheet cell has the wrong data type. Please make sure your spreadsheet column number "
										+ index
										+ " is set to the string datatype.",
									index,feature, row.getRowNum());
							
						}
	
					}
				} catch (RuntimeException e) {
					//just fall through and return a null
				}
			}
		return result;
	}

	static String getValue(HSSFRow row, EStructuralFeature feature, int index) {
		String result = null;
	
		    if (index !=-1) {
				try {
					HSSFCell cellContents = row.getCell(index);
					if (cellContents != null) {
						switch (cellContents.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING:
							result = cellContents.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC:
							double num = cellContents.getNumericCellValue();
							long l = (long)num;
							result = Long.toString(l);
							break;
						default:
							break;
						}
	
					}
				} catch (RuntimeException e) {
					//just fall through and return a null
				}
			}
		return result;
	}

	static BigDecimal getCriticalDecimalValue(HSSFRow row,
			EAttribute feature, int index) throws MissingCriticalDataException {
		BigDecimal result = getDecimalValue(row, feature, index);
		if (result == null) {
			throw new MissingCriticalDataException(
					"The decimal value in a critical spreadsheet cell is empty. Please remove all blank rows and fill in values for all critical cells.",
					index, feature, row.getRowNum());
		}
		return result;
	}

	private static BigDecimal getDecimalValue(HSSFRow row, EAttribute feature,
			int index) {
		BigDecimal result = null;

		if (index != -1) {
			try {
				HSSFCell cellContents = row.getCell(index);
				if (cellContents != null) {
					switch (cellContents.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						result = new BigDecimal(
								cellContents.getNumericCellValue());
						break;
					default:
						System.err.printf("Wrong type for decimal %s%n",
								cellContents.getCellType());
						break;
					}

				}
			} catch (RuntimeException e) {
				// just fall through and return a null
			}
		}
		return result;
	}

	public static Date getCriticalDateValue(HSSFRow row, EAttribute feature,
			int index) throws MissingCriticalDataException {
		Date result = getDateValue(row, feature, index);
		if (result == null) {
			throw new MissingCriticalDataException(
					"The date value in a critical spreadsheet cell is empty. Please remove all blank rows and fill in values for all critical cells.",
					index, feature, row.getRowNum());
		}
		return result;
	}

	private static Date getDateValue(HSSFRow row, EAttribute feature, int index) {
		Date result = null;

		if (index != -1) {
			try {
				HSSFCell cellContents = row.getCell(index);
				if (cellContents != null) {
					switch (cellContents.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC:
						result = cellContents.getDateCellValue();
						break;
					default:
						System.err.printf("Wrong type for date %s%n",
								cellContents.getCellType());
						break;
					}

				}
			} catch (RuntimeException e) {
				// just fall through and return a null
			}
		}
		return result;
	}

	static void addDate(Event event, HSSFRow row)
			throws MissingCriticalDataException {
		Date value = getCriticalDateValue(row,
				TreatmentPackage.Literals.EVENT__DATE, DATETIME_COL);
		event.setDate(value);
	}

	static void addComments(Event event, HSSFRow row) {
	
		try {
			String value = getStringValue(row,
					TreatmentPackage.Literals.EVENT__COMMENTS,
					COMMENTS_COL);
			if (value != null && value.trim().length() > 0) {
				event.setComments(value);
			}
		} catch (MissingCriticalDataException e) {
			// ignore it
		}
	
	
	}

	static boolean isWorkSheetMatch(String[] header, HSSFSheet workSheet) {
		int rowNum = workSheet.getFirstRowNum();
		Row row = workSheet.getRow(rowNum);
		for (int i = 0; i < header.length; i++) {
			Cell cell = row.getCell(i);
			// Must be a String
			if (cell.getCellType() != Cell.CELL_TYPE_STRING) {
				System.out.println("Header Column Type Error: "
						+ cell.getCellType() + " != " + Cell.CELL_TYPE_STRING);
				return false;
			}
			if (!header[i].equalsIgnoreCase(cell.getRichStringCellValue()
					.getString())) {
				// System.out.println("Header Column Name Error: " + header[i]
				// + " != " + cell.getRichStringCellValue().getString());
				return false;
			}
	
		}
		return true;
	}

	static void addDescription(Event event, HSSFRow row) {
		try {
			String value = getStringValue(row,
					TreatmentPackage.Literals.EVENT__DESCRIPTION,
					DESCRIPTION_COL);
			if (value != null && value.trim().length() > 0) {
				event.setDescription(value);
			}
		} catch (MissingCriticalDataException e) {
			// ignore it
		}
	}




}
