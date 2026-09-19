/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
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


package com.nokia.s60tools.imaker.internal.providers;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;

import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;

public class CheckBoxEditingSupport extends IbyEntryEditingSupport {
	private final static int ENABLE_COLUMN_ID = 1;
	public CheckBoxEditingSupport(ColumnViewer viewer, int column) {
		super(viewer, column);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		TableViewer tv = (TableViewer) getViewer();
		CheckboxCellEditor cellEditor = new CheckboxCellEditor(tv.getTable());
		return cellEditor;
	}

	@Override
	protected Object getValue(Object element) {
		return new Boolean(getEntry(element).isEnabled());
	}

	@Override
	protected void setValue(Object element, Object value) {
		Boolean newValue = (Boolean)value;
		IbyEntry entry = getEntry(element);
		entry.setEnabled(newValue);
		getViewer().update(element, null);
	}
}
