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

import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IEnvironment;
import com.nokia.s60tools.imaker.IEnvironmentManager;
import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.ImageFlasherHelpContextIDs;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.internal.managers.EnvironmentManager;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.wrapper.PlatsimManager;

public class PlatsimTab extends CTabItem implements IPropertyViewer {

	private Group platsimGroup;
	private IEnvironmentManager environmentManager;
	private PlatsimManager platsimManager;
	private Label locationLabel;
	private Text locationField;
	private Label instancesLabel;
	private Combo instancesCombo;
	private Button run;
	private boolean activated;
	private boolean isRun = false;

	public PlatsimTab(CTabFolder parent, int style) {
		super(parent, style);
		environmentManager = EnvironmentManager.getInstance();
		IEnvironment active = environmentManager
				.getActiveEnvironment();
		platsimManager = new PlatsimManager(active.getDrive());
		setControl(createControl(parent));
	}
	
	private Control createControl(CTabFolder parent) {
		Composite top = new Composite(parent,SWT.FLAT);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ImageFlasherHelpContextIDs.IMAKERDIALOG_PLATSIMTAB);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		top.setLayoutData(layoutData);
		top.setLayout(new GridLayout(1,false));

		platsimGroup = new Group(top, SWT.NONE);
		platsimGroup.setText("PlatSim");
		platsimGroup.setLayout(new GridLayout(2,false));
		layoutData = new GridData(SWT.FILL, SWT.FILL, true, false);
		platsimGroup.setLayoutData(layoutData);
		
		locationLabel = new Label(platsimGroup, SWT.NONE);
		locationLabel.setText("Location");
		locationField = new Text(platsimGroup, SWT.SINGLE|SWT.BORDER);
		layoutData = new GridData();
		layoutData.widthHint = 200;
		locationField.setLayoutData(layoutData);
		
		instancesLabel = new Label(platsimGroup, SWT.NONE);
		instancesLabel.setText("Instance");
		instancesCombo = new Combo(platsimGroup, SWT.NONE);
		run = new Button(top,SWT.CHECK);
		run.setText("Run PlatSim after image creation");
		populate();
		instancesCombo.setEnabled(false);
		run.setEnabled(isRun);
		run.addSelectionListener(new SelectionListener() {
			
//			@Override
			public void widgetSelected(SelectionEvent e) {
				isRun = run.isEnabled();
			}
			
//			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
		return top;
	}

	private void populate() {
		locationField.setText(platsimManager.getDefaulfLocation());
		locationField.setEditable(false);
		List<String> ins = platsimManager.getInstances();
		for (String in : ins) {
			instancesCombo.add(in);
		}
		instancesCombo.add(Messages.getString("PlatsimTab.0"));
		instancesCombo.select(0);
	}
	
	public void activate(boolean activate) {
		instancesCombo.setEnabled(activate);
		run.setEnabled(activate);
		this.activated = activate;
	}
	
	public boolean isActivated() {
		return this.activated;
	}
	
	private String getInstance() {
		int index = instancesCombo.getSelectionIndex();
		if(index!=-1) {
			return instancesCombo.getItem(index);			
		} else {
			String text = instancesCombo.getText();
			if(text!=null) {
				return text;
			}
		}
		return null;
	}

	public void addToProperties(ImakerProperties prop) {
		if(isActivated()) {
			String instance = getInstance();
			if (instance!=null) {
				prop.put(IMakerKeyConstants.PLATSIM_INSTANCE, instance);				
			}
			if(run.getSelection()) {
				prop.put(IMakerKeyConstants.PLATSIM_RUN, "1");				
			} else {
				prop.put(IMakerKeyConstants.PLATSIM_RUN, "0");				
			}
		}
	}

//	@Override
	public void restoreFromProperties(ImakerProperties prop) {
		String instance = (String) prop.get(IMakerKeyConstants.PLATSIM_INSTANCE);
		if(instance!=null) {
			String[] items = instancesCombo.getItems();
			for (int i = 0; i < items.length; i++) {
				String item = items[i];
				if(item.equals(instance)) {
					instancesCombo.select(i);
					break;
				}
			}
		}
		String str = (String) prop.get(IMakerKeyConstants.PLATSIM_RUN);
		if(str!=null&&str.equals("true")) {
			this.run.setSelection(true);
		} else {
			this.run.setSelection(false);			
		}
	}

//	@Override
	public void clear() {
//		instancesCombo.removeAll();
		run.setSelection(false);
	}
}
