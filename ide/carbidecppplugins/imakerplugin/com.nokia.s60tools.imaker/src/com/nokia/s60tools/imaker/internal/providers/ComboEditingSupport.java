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
import org.eclipse.jface.viewers.ComboBoxCellEditor;
import org.eclipse.jface.viewers.TableViewer;

import com.nokia.s60tools.imaker.internal.model.iContent.ACTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;

public class ComboEditingSupport extends IbyEntryEditingSupport {
	public ComboEditingSupport(ColumnViewer viewer, int column) {
		super(viewer, column);
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		TableViewer tv = (TableViewer) getViewer();
		switch (column) {
		case 4:
			IMAGESECTION[] values = IMAGESECTION.values();
			String[] entries = new String[values.length];
			for (int i = 0; i < values.length; i++) {
				entries[i]=values[i].getLiteral();
			}
			ComboBoxCellEditor cellEditor = new ComboBoxCellEditor(tv.getTable(), entries);
			return cellEditor;
		case 5:
			ACTION[] actions = ACTION.values();
			entries = new String[actions.length];
			for (int i = 0; i < actions.length; i++) {
				entries[i]=actions[i].getLiteral();
			}
			cellEditor = new ComboBoxCellEditor(tv.getTable(), entries);
			return cellEditor;

		default:
			break;
		}
		return null;
	}

	@Override
	protected Object getValue(Object element) {
		IbyEntry ie = getEntry(element);
		switch (column) {
		case 4:
			Integer value = new Integer(ie.getLocation().getValue());
			return value;
		case 5:
			value = new Integer(ie.getAction().getValue());
			return value;
		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		IbyEntry ie = getEntry(element);
		switch (column) {
		case 4:
			int oldValue = ie.getLocation().getValue();
			int newValue = ((Integer)value).intValue();
			if(oldValue!=newValue) {
				ie.setLocation(IMAGESECTION.get(newValue));
			}			
			break;
		case 5:
			oldValue = ie.getAction().getValue();
			newValue = ((Integer)value).intValue();
			if(oldValue!=newValue) {
				ie.setAction(ACTION.get(newValue));
			}
			break;

		default:
			break;
		}
		getViewer().update(element, null);
	}
}
