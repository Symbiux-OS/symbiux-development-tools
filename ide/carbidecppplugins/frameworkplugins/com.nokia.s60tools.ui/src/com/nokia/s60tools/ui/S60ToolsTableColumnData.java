/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
* All rights reserved.
* This component and the accompanying materials are made available
* under the terms of "Eclipse Public License v1.0"
* which accompanies this distribution, and is available
* at the URL "http://www.eclipse.org/legal/epl-v10.html".
*
* Initial Contributors:
* Nokia Corporation - initial contribution.
*
* Contributors:
*
* Description:
*
*/
 
 
package com.nokia.s60tools.ui;

import org.eclipse.swt.SWT;

/**
 * Defines necessary information for a single 
 * column. This class is used for constructing
 * <b>S60ToolsTable</b> instance.
 * @see com.nokia.s60tools.ui.S60ToolsTable
 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
 */
public class S60ToolsTableColumnData {
   
	/**
	 * Column's header text.
	 */
	private final String columnHeader;
	
	/**
	 * Column width.
	 */
	private final int columnWidth;
	
	/**
	 * Column index (from zero onwards).
	 */
	private final int columnIndex;
	
	/**
	 * Sorting criteria for the column.
	 */
	private final int columnSortCriteria;

	/**
	 * <code>true</code> if we want to show item count for this column,
	 * and <code>false</code> if not (default is <code>false</code>).
	 */
	private final boolean showItemCount;
	
	/**
	 * Default column style unless otherwise defined during construction.
	 */
	private static final int DEFAULT_COLUMN_STYLE = SWT.LEFT;

	/**
	 * Column style bits.
	 */
	private int columnsStyle = DEFAULT_COLUMN_STYLE;	

	/**
	 * Constructor using default sorting criteria (no sorting).
	 * @param columnHeader Column's header text.
	 * @param columnWidth Column width.
	 * @param columnIndex Column index (from zero onwards).
     * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 */
	public S60ToolsTableColumnData(String columnHeader, int columnWidth, int columnIndex) {
		this.columnHeader = columnHeader;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.showItemCount = false;
		this.columnSortCriteria = S60ToolsViewerSorter.CRITERIA_NO_SORT;
	}

	
	/**
	 * Constructor using default sorting criteria (no sorting).
	 * @param columnHeader Column's header text.
	 * @param columnWidth Column width.
	 * @param columnIndex Column index (from zero onwards).
	 * @param showItemCount <code>true</code> if we want to show item count for this column,
	 *                     and <code>false</code> if not.
     * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 */
	public S60ToolsTableColumnData(String columnHeader, int columnWidth, int columnIndex,
			                       boolean showItemCount) {
		this.columnHeader = columnHeader;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.showItemCount = showItemCount;
		this.columnSortCriteria = S60ToolsViewerSorter.CRITERIA_NO_SORT;
	}
	
	/**
	 * Constructor using given sorting criteria.
	 * @param columnHeader Column's header text.
	 * @param columnWidth Column width.
	 * @param columnIndex Column index (from zero onwards).
	 * @param columnSortCriteria Sorting criteria for the column.
	 */
	public S60ToolsTableColumnData(String columnHeader, int columnWidth, 
			                       int columnIndex, int columnSortCriteria) {
		this.columnHeader = columnHeader;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.showItemCount = false;
		this.columnSortCriteria = columnSortCriteria;
	}

	/**
	 * Constructor using given sorting criteria and item count showing status.
	 * @param columnHeader Column's header text.
	 * @param columnWidth Column width.
	 * @param columnIndex Column index (from zero onwards).
	 * @param columnSortCriteria Sorting criteria for the column.
	 * @param showItemCount <code>true</code> if we want to show item count for this column,
	 *                     and <code>false</code> if not.
	 */
	public S60ToolsTableColumnData(String columnHeader, int columnWidth, 
			                       int columnIndex, int columnSortCriteria,
			                       boolean showItemCount) {
		this.columnHeader = columnHeader;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.columnSortCriteria = columnSortCriteria;
		this.showItemCount = showItemCount;
	}	
	
	/**
	 * Constructor using given sorting criteria and style bits.
	 * @param columnHeader Column's header text.
	 * @param columnWidth Column width.
	 * @param columnIndex Column index (from zero onwards).
	 * @param columnSortCriteria Sorting criteria for the column.
	 * @param columnsStyle Column style bits, if other than default value.
	 */
	public S60ToolsTableColumnData(String columnHeader, int columnWidth, 
			                       int columnIndex, int columnSortCriteria,
			                       int columnsStyle) {
		this.columnHeader = columnHeader;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.columnSortCriteria = columnSortCriteria;
		this.showItemCount = false;
		this.columnsStyle = columnsStyle;
	}	
	
	/**
	 * Constructor using given sorting criteria, item count showing status, and style bits.
	 * @param columnHeader Column's header text.
	 * @param columnWidth Column width.
	 * @param columnIndex Column index (from zero onwards).
	 * @param columnSortCriteria Sorting criteria for the column.
	 * @param showItemCount <code>true</code> if we want to show item count for this column,
	 *                     and <code>false</code> if not.
	 * @param columnsStyle Column style bits, if other than default value.
	 */
	public S60ToolsTableColumnData(String columnHeader, int columnWidth, 
			                       int columnIndex, int columnSortCriteria,
			                       boolean showItemCount,
			                       int columnsStyle) {
		this.columnHeader = columnHeader;
		this.columnWidth = columnWidth;
		this.columnIndex = columnIndex;
		this.columnSortCriteria = columnSortCriteria;
		this.showItemCount = showItemCount;
		this.columnsStyle = columnsStyle;
	}	
	
	/**
	 * Gets column header.
	 * @return Returns the columnHeader.
	 */
	public String getColumnHeader() {
		return columnHeader;
	}


	/**
	 * Gets column width. 
	 * @return Returns the columnWidth.
	 */
	public int getColumnWidth() {
		return columnWidth;
	}

	/**
	 * Gets column index (from zero onwards).
	 * @return Returns the columnIndex.
	 */
	public int getColumnIndex() {
		return columnIndex;
	}

	/**
	 * Gets sorting criteria for the column.
	 * @return Returns the columnSortCriteria.
	 */
	public int getColumnSortCriteria() {
		return columnSortCriteria;
	}


	/**
	 * Returns item count showing status.
	 * @return <code>true</code> if we want to show item count for this column,
	 *                     and <code>false</code> if not.
	 */
	public boolean isShowItemCount() {
		return showItemCount;
	}
	
	/**
	 * Gets style bits for the column.
	 * @return style bits for the column.
	 */
	public int getColumnsStyle() {
		return columnsStyle;
	}
}
