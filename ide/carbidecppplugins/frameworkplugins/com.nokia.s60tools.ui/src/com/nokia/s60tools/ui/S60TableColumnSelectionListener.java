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

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

/**
 * The purpose of this class is to be instantiated as anonymous class 
 * that can access parent table's properties. The inheriting class can 
 * override the the default implementation that does not invoke any actions. 
 * For example,
 * 
 * <code>
 * <pre>
 * 
 * TableColumn tblColumn;
 * // Table column related initialization ...
 * tblColumn.addSelectionListener(new S60TableColumnSelectionListener(tbl){
 *             public void widgetDefaultSelected(SelectionEvent e){
 *                  // Overridden implementation if needed...						
 *                  }
 *
 *             public void widgetSelected(SelectionEvent e){
 *                  // Overridden implementation if needed...						
 *                  }
 * });
 *
 * </pre>
 * </code>
 * 
 * @see com.nokia.s60tools.ui.S60ToolsTable
 */
class S60TableColumnSelectionListener implements SelectionListener{
	
	/**
	 * Reference to table that owns the column to be listened.
	 */
	private final S60ToolsTable parentTable;
	
	/**
	 * Constructor.
	 * @param parentTable Parent table to be listened for.
	 */
	public S60TableColumnSelectionListener(S60ToolsTable parentTable){
		this.parentTable = parentTable;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
	}

	/**
	 * Gets parent table.
	 * @return Returns the parentTable.
	 */
	public S60ToolsTable getParentTable() {
		return parentTable;
	}
	
}