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

import static com.verticon.treatment.poi.handlers.PoiUtils.TESTLOG_HEADER;
import static com.verticon.treatment.poi.handlers.PoiUtils.TREATMENTLOG_HEADER;
import static com.verticon.treatment.poi.handlers.PoiUtils.convert;
import static com.verticon.treatment.poi.handlers.PoiUtils.getWorkSheet;
import static com.verticon.treatment.poi.handlers.PoiUtils.isWorkSheetMatch;

import java.io.File;
import java.util.Collection;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.eclipse.core.expressions.PropertyTester;
import org.eclipse.core.runtime.CoreException;

public class XlsEventTypeTester extends PropertyTester {

	enum Tests {
		isTestLog, isOtherLog
	};

	@Override
	public boolean test(Object receiver, String property, Object[] args,
			Object expectedValue) {
		Collection<?> selection = (Collection<?>) receiver;

		switch (Tests.valueOf(property)) {
		case isTestLog:
			return isMatch(TESTLOG_HEADER, selection);
		case isOtherLog:
			return isMatch(TREATMENTLOG_HEADER, selection);
		default:
			return false;
		}

	}

	/**
	 * Examine all the files in the selection to see if they match the header
	 * 
	 * @param file
	 * @return
	 */
	private static boolean isMatch(String[] header, Collection<?> selection) {
		for (Object o : selection) {
			try {
				File f = convert(o);
				if (!isMatch(header, f)) {
					return false;
				}
			} catch (CoreException e) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Does the file match the header
	 * 
	 * @param f
	 * @param header
	 * @return
	 */
	private static boolean isMatch(String[] header, File f) {
		if (!f.isFile()) {
			System.out.println("Not a file: " + f.toString());
			return false;
		}
		if (!f.toString().endsWith(".xls")) {
			// System.out.println("Not an xls file: " + f.toString());
			return false;
		}
		HSSFSheet workSheet = getWorkSheet(f);
		if (workSheet == null) {
			System.out.println("Not an xls file: " + f.toString());
			return false;
		}

		return isWorkSheetMatch(header, workSheet);

		//
		// System.out.println("Row number: " + rowNum);
		// // for (Row row : workSheet) {
		// for (Cell cell : row) {
		// CellReference cellRef = new CellReference(row.getRowNum(),
		// cell.getColumnIndex());
		// System.out.print(cellRef.formatAsString());
		// System.out.print(" - ");
		//
		// switch (cell.getCellType()) {
		// case Cell.CELL_TYPE_STRING:
		// System.out.println(cell.getRichStringCellValue()
		// .getString());
		// break;
		// case Cell.CELL_TYPE_NUMERIC:
		// if (DateUtil.isCellDateFormatted(cell)) {
		// System.out.println(cell.getDateCellValue());
		// } else {
		// System.out.println(cell.getNumericCellValue());
		// }
		// break;
		// case Cell.CELL_TYPE_BOOLEAN:
		// System.out.println(cell.getBooleanCellValue());
		// break;
		// case Cell.CELL_TYPE_FORMULA:
		// System.out.println(cell.getCellFormula());
		// break;
		// default:
		// System.out.println();
		// }
		// }
		// // }

	}

}
