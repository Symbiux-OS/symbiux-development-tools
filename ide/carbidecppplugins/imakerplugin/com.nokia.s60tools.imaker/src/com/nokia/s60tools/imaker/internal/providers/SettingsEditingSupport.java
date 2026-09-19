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
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.internal.viewers.PreferencesTab;
import com.nokia.s60tools.imaker.internal.viewers.SettingsViewer;

public class SettingsEditingSupport extends EditingSupport {

	private int column;
	private TextCellEditor cellEditor;
	private String oldValue;
	private SettingsViewer settingsViewer;

	public SettingsEditingSupport(ColumnViewer viewer, int column, SettingsViewer settingsViewer) {
		super(viewer);
		this.settingsViewer = settingsViewer;
		switch (column) {
		case 1:
			cellEditor = new TextCellEditor(((TableViewer)viewer).getTable());
			break;
		default:
		}
		this.column = column;
	}

	@Override
	protected boolean canEdit(Object element) {
		if(column==1) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return cellEditor;
	}

	@Override
	protected Object getValue(Object element) {
		UIVariable variable = (UIVariable)element;
		switch (column) {
		case 1:
			oldValue = variable.getValue();
			return oldValue;
		default:
			break;
		}
		return null;
	}

	@Override
	protected void setValue(Object element, Object value) {
		final UIVariable variable = (UIVariable)element;

		switch (this.column) {
		case 1:
			if(!oldValue.equals(value)) {
				variable.setValue(String.valueOf(value));
				variable.setModified(true);
				settingsViewer.setDirty(true);
				Display display = ((TableViewer)getViewer()).getTable().getDisplay();
				display.asyncExec(new Runnable() {

					public void run() {
						if(PreferencesTab.currentPreferencesTab!=null) {
							PreferencesTab.currentPreferencesTab.refreshSettingsTab(variable);
						}
					}
					
				});
			}
			break;
		default:
			break;
		}
		getViewer().update(element, null);
	}
}
