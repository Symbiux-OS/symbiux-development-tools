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
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class TextEditingSupport extends IbyEntryEditingSupport {
	private final static int FILE_COLUMN_ID = 2;
	public TextEditingSupport(ColumnViewer viewer, int column) {
		super(viewer, column);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		TableViewer tv = (TableViewer) getViewer();
		TextCellEditor cellEditor = new TextCellEditor(tv.getTable());
		return cellEditor;
	}

	@Override
	protected Object getValue(Object element) {
		String value = null;
		
		if(column==FILE_COLUMN_ID) {
			value = getEntry(element).getFile();
		} else {
			value = getEntry(element).getTarget();
		}
		return (value==null)?"":value;
	}

	@Override
	protected void setValue(Object element, Object value) {
		String oldValue;
		String newValue;
		if(column==FILE_COLUMN_ID) {
			oldValue = getEntry(element).getFile();
			newValue = (String) value;
			if(oldValue!=null&&!oldValue.equals(newValue)) {
				getEntry(element).setFile(newValue);
				super.updateWarning(element);
				getViewer().update(element, null);
			} else if(oldValue==null) {
				getEntry(element).setFile(newValue);
				super.updateWarning(element);
				getViewer().update(element, null);				
			} else {}
		} else {
			oldValue = getEntry(element).getTarget();
			newValue = (String) value;
			if(oldValue!=null&&!oldValue.equals(newValue)) {
				getEntry(element).setTarget(newValue);
				super.updateWarning(element);
				getViewer().update(element, null);
			} else if(oldValue==null) {
				getEntry(element).setTarget(newValue);
				super.updateWarning(element);
				getViewer().update(element, null);				
			} else {}
		}
	}
}
