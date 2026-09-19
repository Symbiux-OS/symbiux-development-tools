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
package com.nokia.s60tools.imaker.internal.dialogs;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import com.nokia.s60tools.imaker.IEnvironment;
import com.nokia.s60tools.imaker.UIConfiguration;

public class ProductSelectionDialog extends MessageDialog {
	private UIConfiguration selectedConfiguration = null;
	private IEnvironment activeEnvironment = null;
	protected boolean dontShowAgain;
	private final int style = SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
	
	public ProductSelectionDialog(Shell parentShell, IEnvironment env) {
		super(parentShell, "Select product", null, // accept
        // the
        // default
        // window
        // icon
		"The following products are available\n", QUESTION,
		new String[] { IDialogConstants.OK_LABEL, IDialogConstants.CANCEL_LABEL },
		0);
		this.activeEnvironment = env;
	}

	@Override
	protected Control createCustomArea(Composite parent) {
		parent.setLayout(new GridLayout(1,true));
		
		//table 
		Composite tableComp = new Composite(parent,SWT.FILL);
		tableComp.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		tableComp.setLayout(new GridLayout(1,true));
		GridData gridData = new GridData(GridData.FILL,GridData.FILL,true,true);
		gridData.heightHint = 180;
		Table table = new Table(tableComp,style);
		table.setLayoutData(gridData);
		
		TableViewer viewer = new TableViewer(table);

		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);

		setContentProvider(viewer);
		createColumns(viewer);
		
		viewer.addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
				IStructuredSelection sc = (IStructuredSelection) event.getSelection();
				selectedConfiguration = (UIConfiguration) sc.getFirstElement();
			}
		});
		
		viewer.setInput(this);
		return parent;
	}
	
	private void createColumns(TableViewer viewer) {
		int columnSizes[] = {110,320};
		
		TableViewerColumn column = new TableViewerColumn(viewer,SWT.NONE);
		column.getColumn().setText("Name");
		column.getColumn().setWidth(columnSizes[0]);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				UIConfiguration c = (UIConfiguration) element;
				return c.getConfigurationName();
			}		
		});

		column = new TableViewerColumn(viewer,SWT.NONE);
		column.getColumn().setText("Makefile");
		column.getColumn().setWidth(columnSizes[1]);
		column.setLabelProvider(new ColumnLabelProvider() {
			@Override
			public String getText(Object element) {
				UIConfiguration c = (UIConfiguration) element;
				return c.getFilePath();
			}		
		});
		
	}

	private void setContentProvider(TableViewer viewer) {
		viewer.setContentProvider(new ConfMLFilesContentProvider());
	}

	public UIConfiguration getSelectedConfiguration() {
		return selectedConfiguration;
	}

	public boolean displayDialog() {
		return open() == 0;
	}

	public boolean isDontShowAgain() {
		return dontShowAgain;
	}
	
	/**
	 * provide content for the table
	 */
	private class ConfMLFilesContentProvider implements IStructuredContentProvider {

		public Object[] getElements(Object inputElement) {
			List<UIConfiguration> configurations;
			try {
				configurations = activeEnvironment.getConfigurations();
				return configurations.toArray();
			} catch (InvocationTargetException e) {
				return new UIConfiguration[]{};
			}
		}

		public void dispose() {}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}		
	}
}
