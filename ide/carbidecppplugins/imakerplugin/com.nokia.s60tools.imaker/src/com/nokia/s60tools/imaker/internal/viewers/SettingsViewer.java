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


package com.nokia.s60tools.imaker.internal.viewers;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.internal.providers.SettingsColumnLabelProvider;

/**
 *
 */
public class SettingsViewer {

	private TableViewer tableViewer;
	private UIConfiguration uiConfiguration;
	private boolean dirty = false;
	
	public SettingsViewer(Composite parent) {
		addChildControls(parent);
	}

	private void addChildControls(Composite parent) {
		int style = GridData.HORIZONTAL_ALIGN_FILL | GridData.FILL_BOTH;
		GridData gridData = new GridData (style);
		parent.setLayoutData (gridData);
		parent.setLayout(new GridLayout());

		/* Create and setup the TableViewer */
		createTableViewer(parent);
	}

	private void createTableViewer(Composite parent) {
		int style = SWT.SINGLE | SWT.H_SCROLL
		| SWT.V_SCROLL | SWT.FULL_SELECTION;
		tableViewer = new TableViewer(parent,style);

		tableViewer.setContentProvider(new SettingsContentProvider());
		
		SettingsColumnLabelProvider settingsColumnLabelProvider = new SettingsColumnLabelProvider();
		settingsColumnLabelProvider.createColumns(tableViewer,this);
		
		tableViewer.getTable().setHeaderVisible(true);
		tableViewer.getTable().setLinesVisible(true);

		GridData gridData = new GridData(GridData.FILL_BOTH);
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalSpan = 3;
		tableViewer.getTable().setLayoutData(gridData);
	}

	public UIConfiguration getUiConfiguration() {
		return uiConfiguration;
	}

	public void setUiConfiguration(UIConfiguration uic) {
		this.uiConfiguration = uic;
		tableViewer.setInput(uiConfiguration);
	}

	/**
	 * InnerClass that acts as a proxy for the UIConfiguration 
	 * providing content for the Table. 
	 *
	 */
	class SettingsContentProvider implements IStructuredContentProvider,ISettingViewer {

		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
			if (newInput != null)
				((UIConfiguration) newInput).addChangeListener(this);
			if (oldInput != null)
				((UIConfiguration) oldInput).removeChangeListener(this);
		}

		public Object[] getElements(Object inputElement) {
			UIConfiguration uic = (UIConfiguration) inputElement;
			return uic.getVariables().toArray();
		}

		public void dispose() {
		}

		public void updateVariable(UIVariable variable) {
			tableViewer.update(variable, null);	
		}
	}

	public TableViewer getTableViewer() {
		return tableViewer;
	}

	public boolean isDirty() {
		return dirty;
	}

	public void setDirty(boolean dirty) {
		this.dirty = dirty;
	}
}
