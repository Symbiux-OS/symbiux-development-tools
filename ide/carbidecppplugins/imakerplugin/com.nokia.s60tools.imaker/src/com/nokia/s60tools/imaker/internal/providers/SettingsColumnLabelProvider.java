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

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;

import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.internal.viewers.SettingsViewer;

public class SettingsColumnLabelProvider {
	public void createColumns(TableViewer viewer, SettingsViewer settingsViewer) {
		String[] titles = { 
				Messages.getString("SettingsTable.header0"), 
				Messages.getString("SettingsTable.header1"), 
				Messages.getString("SettingsTable.header2"),
				Messages.getString("SettingsTable.header3") 
				};
		int[] bounds = { 100, 180, 80, 250 };

		// Column 0: Parameter
		int i = 0;
		TableViewerColumn column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(bounds[i]);
		column.getColumn().setText(titles[i]);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return ((UIVariable) element).getName();
			}
		});
		
		// Column 1: Value
		i++;
		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(bounds[i]);
		column.getColumn().setText(titles[i]);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return ((UIVariable) element).getValue();
			}
		});
		column.setEditingSupport(new SettingsEditingSupport(viewer, i,settingsViewer));

		// Column 2: Value format
		i++;
		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(bounds[i]);
		column.getColumn().setText(titles[i]);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return ((UIVariable) element).getValueFormat();
			}
		});

		// Column 3: Description
		i++;
		column = new TableViewerColumn(viewer, SWT.NONE);
		column.getColumn().setWidth(bounds[i]);
		column.getColumn().setText(titles[i]);
		column.getColumn().setMoveable(true);
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return ((UIVariable) element).getDescription();
			}
		});
	}
}
