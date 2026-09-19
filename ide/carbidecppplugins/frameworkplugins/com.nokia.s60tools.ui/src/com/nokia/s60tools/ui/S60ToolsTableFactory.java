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

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;


/**
 * Factory class that creates custom <code>S60ToolsTable</code> component
 * that is automatically enhanced with sorting functionality.
 * Columns selections are listened by an instance of 
 * <code>S60TableColumnSelectionListener</code> and sorting is done
 * by a concrete sorter class that inherits from <code>S60ToolsViewerSorter</code>
 * class.
 * 
 * An example for creating TableViewer by help of this factory class:
 * 
 * <code>
 * <pre>
 * ArrayList columnDataArr = new ArrayList();		
 * //
 * // NOTE: Column indices must start from zero (0) and
 * // the columns must be added in ascending numeric
 * // order.
 * //
 *	
 * // Defining column data for the custom table
 *    
 * columnDataArr.add(new S60ToolsTableColumnData("Property", // Column name
 *                                                80, // Column width
 *                                                // Column index
 *                                                ComponentPropertiesData.PROPERTY_COLUMN_INDEX,
 *                                                // Sorting criteria for the column
 *                                                PropertyDataSorter.CRITERIA_PROPERTY));
 *
 * columnDataArr.add(new S60ToolsTableColumnData("Value",
 *                                                380,
 *                                                ComponentPropertiesData.VALUE_COLUMN_INDEX,
 *                                                PropertyDataSorter.CRITERIA_VALUE));
 *		
 * S60ToolsTableColumnData[] arr 
 *                  = (S60ToolsTableColumnData[]) columnDataArr.toArray(
 *                                                     new S60ToolsTableColumnData[0]);
 *		
 * // Creating custom table with pre-defined column data
 * S60ToolsTable tbl = S60ToolsTableFactory.create(parent, arr);
 *		
 * // Creating table viewer from existing Table widget
 * TableViewer tblViewer = new TableViewer(tbl.getTableInstance());
 *
 * // It is mandatory to set hosting viewer in order to enable automatic sorting!
 * tbl.setHostingViewer(tblViewer);
 *
 * </pre>
 * </code>
 * 
 * @see com.nokia.s60tools.ui.S60ToolsTable
 * @see com.nokia.s60tools.ui.S60TableColumnSelectionListener
 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
 * @see org.eclipse.jface.viewers.TableViewer
 */
public class S60ToolsTableFactory{

	/**
	 * Style for the table.
	 */
	private static final int DEFAULT_TABLE_STYLE = SWT.MULTI | SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL | 
	SWT.FULL_SELECTION | SWT.HIDE_SELECTION;
	
	/**
	 * Vertical grabbing mode for the table.
	 */
	private static final boolean GRAB_EXCESS_VERTICAL_SPACE = true;
	
	/**
	 * Horizontal grabbing for the table.
	 */
	private static final boolean GRAB_EXCESS_HORIZONAL_SPACE = true;

	/**
	 * Constant for not having column count feature.
	 */
	private static final int NO_COLUMN_COUNTING = -1;	

	/**
	 * Creates custom table type supporting automatic sorting.
	 * @param parent Parent composite for the table.
	 * @param columnDataArr Column data for the table.
	 * @return Custom table instance.
	 * @see com.nokia.s60tools.ui.S60ToolsTable
	 * @see com.nokia.s60tools.ui.S60TableColumnSelectionListener
	 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 * @see org.eclipse.jface.viewers.TableViewer
	 */
	public static S60ToolsTable create(Composite parent, S60ToolsTableColumnData[] columnDataArr) {
		return create(parent, columnDataArr, NO_COLUMN_COUNTING);
	}
	
	/**
	 * Creates custom table type supporting automatic sorting 
	 * 	and updateable column header.
	 * @param parent Parent composite for the table.
	 * @param columnDataArr Column data for the table.
	 * @param showItemCountInColumn Column index used for showing item count. 
	 * 			Must reference to <code>columnDataArr</code>[<code>showItemCountInColumn</code>].
	 * @return Custom table instance.
	 * @see com.nokia.s60tools.ui.S60ToolsTable
	 * @see com.nokia.s60tools.ui.S60TableColumnSelectionListener
	 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 * @see org.eclipse.jface.viewers.TableViewer
	 */
	public static S60ToolsTable create(Composite parent, S60ToolsTableColumnData[] columnDataArr,
            						   int showItemCountInColumn) {
		return create(parent, columnDataArr,
				     showItemCountInColumn, DEFAULT_TABLE_STYLE);
	}
		
	/**
	 * Creates custom table type supporting automatic sorting
	 * that has possibility for checkbox selection of rows.
	 * @param parent Parent composite for the table.
	 * @param columnDataArr Column data for the table.
	 * @return Custom table instance.
	 * @see com.nokia.s60tools.ui.S60ToolsTable
	 * @see com.nokia.s60tools.ui.S60TableColumnSelectionListener
	 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 * @see org.eclipse.jface.viewers.TableViewer
	 */
	public static S60ToolsTable createCheckboxTable(Composite parent, S60ToolsTableColumnData[] columnDataArr) {
		return createCheckboxTable(parent, columnDataArr, NO_COLUMN_COUNTING);
	}
	
	/**
	 * Creates custom table type supporting automatic sorting
	 * that has possibility for checkbox selection of rows
	 * with updateable column header.
	 * @param parent Parent composite for the table.
	 * @param columnDataArr Column data for the table.
	 * @param showItemCountInColumn Column index used for showing item count. 
	 * 			Must reference to <code>columnDataArr</code>[<code>showItemCountInColumn</code>].
	 * @return Custom table instance.
	 * @see com.nokia.s60tools.ui.S60ToolsTable
	 * @see com.nokia.s60tools.ui.S60TableColumnSelectionListener
	 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 * @see org.eclipse.jface.viewers.TableViewer
	 */
	public static S60ToolsTable createCheckboxTable(Composite parent, S60ToolsTableColumnData[] columnDataArr,
            						   int showItemCountInColumn) {
		return create(parent, columnDataArr,
				     showItemCountInColumn, DEFAULT_TABLE_STYLE | SWT.CHECK);
	}
		
	/**
	 * Creates custom table type supporting automatic sorting 
	 * 	and updateable column header.
	 * @param parent Parent composite for the table.
	 * @param columnDataArr Column data for the table.
	 * @param showItemCountInColumn Column index used for showing item count. 
	 * 			Must reference to <code>columnDataArr</code>[<code>showItemCountInColumn</code>].
	 * @param tableStyle Style for the table to be created.
	 * @return Custom table instance.
	 * @see com.nokia.s60tools.ui.S60ToolsTable
	 * @see com.nokia.s60tools.ui.S60TableColumnSelectionListener
	 * @see com.nokia.s60tools.ui.S60ToolsViewerSorter
	 * @see org.eclipse.jface.viewers.TableViewer
	 */
	private static S60ToolsTable create(Composite parent, S60ToolsTableColumnData[] columnDataArr,
            						   int showItemCountInColumn, int tableStyle) {
		
		S60ToolsTable tbl;
		if(showItemCountInColumn != NO_COLUMN_COUNTING 
				&& showItemCountInColumn < columnDataArr.length){
			tbl = new S60ToolsTable(parent, tableStyle,
					columnDataArr[showItemCountInColumn].getColumnHeader(), 
					showItemCountInColumn);
		}
		else{
			tbl = new S60ToolsTable(parent, tableStyle);
		}
		
		GridData gd = new GridData(SWT.FILL, SWT.FILL, true, true);
		gd.grabExcessVerticalSpace = GRAB_EXCESS_VERTICAL_SPACE;
		gd.grabExcessHorizontalSpace = GRAB_EXCESS_HORIZONAL_SPACE;
		gd.horizontalSpan = columnDataArr.length;
		tbl.setLayoutData(gd);		
					
		tbl.setLinesVisible(true);
		tbl.setHeaderVisible(true);
		
		TableColumn tblColumn = null;
		for (int i = 0; i < columnDataArr.length; i++) {
			S60ToolsTableColumnData columnData = columnDataArr[i];
			tblColumn = new TableColumn(tbl.getTableInstance(), 
										columnData.getColumnsStyle(), 
									    columnData.getColumnIndex());
			
			//If items count in this column is shown, initializin header with value 0
			if(columnData.isShowItemCount()){
				tblColumn.setText(
						columnData.getColumnHeader() + " ("  //$NON-NLS-1$
						+ 0 +S60ToolsTable.TXT_ITEMS_IN_COLUMN_HEADER + ")"); //$NON-NLS-1$
			}
			else{
				tblColumn.setText(columnData.getColumnHeader());				
			}
			tblColumn.setWidth(columnData.getColumnWidth());
			
			// Settings sorting to happen in column selection
			// if sorting criteria has been set.
			final int sortCriteria = columnData.getColumnSortCriteria();
			if(sortCriteria != S60ToolsViewerSorter.CRITERIA_NO_SORT){
				tblColumn.addSelectionListener(new S60TableColumnSelectionListener(tbl){
					public void widgetSelected(SelectionEvent e){						
						S60ToolsTable parentTbl = getParentTable();
						if(parentTbl != null){							
							TableViewer viewer = parentTbl.getHostingViewer();
							if(viewer != null){								
								ViewerSorter sorter = viewer.getSorter();
								if(sorter != null){									
									if(sorter instanceof S60ToolsViewerSorter){										
										S60ToolsViewerSorter s60ToolsSorter = (S60ToolsViewerSorter)sorter;
										int currentSortCriteria = s60ToolsSorter.getSortCriteria();
										if(currentSortCriteria != sortCriteria){
											// If previously using different criteria
											// => updating the criteria.
											s60ToolsSorter.setSortCriteria(sortCriteria);																						
										}
										else{
											// If already using this criteria
											// => no sorting. Back to original ordering.
											s60ToolsSorter.setSortCriteria(S60ToolsViewerSorter.CRITERIA_NO_SORT);																						
										}
										// Refresh forces sort with the new criteria.
										viewer.refresh();
									}
								}
							}
						}
					}
				});
			}
			
		}
		
		return tbl;		
	}

}
