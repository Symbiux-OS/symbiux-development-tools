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

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.ImageFlasherHelpContextIDs;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;

public class SettingsTab extends CTabItem {

	private SettingsViewer settingsViewer;
	
	public SettingsTab(CTabFolder parent, int style) {
		super(parent, style);
		setControl(createControl(parent));
	}

	private Control createControl(CTabFolder parent) {
		Composite top = new Composite(parent,SWT.NONE);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(top, ImageFlasherHelpContextIDs.IMAKERDIALOG_SETTINGSTAB);
		settingsViewer = new SettingsViewer(top);
		return top;
	}

	public void setInput(UIConfiguration uic) {
		settingsViewer.setUiConfiguration(uic);
	}

	public boolean isDirty() {
		return settingsViewer.isDirty();
	}

	public TableViewer getTableViewer() {
		return settingsViewer.getTableViewer();
	}
	public void setDirty(boolean dirty) {
		settingsViewer.setDirty(dirty);
	}

	public void addChangedSettings(ImakerProperties prop) {
		StringBuffer sb = new StringBuffer();
		List<UIVariable> variables = settingsViewer.getUiConfiguration().getVariables();
		List<UIVariable> vars = IMakerUtils.getCommandlineIncludeVariables(variables);
		List<String> vs = IMakerUtils.convertVariablesToStrings(vars);
		for (int i = 0; i < vs.size(); i++) {
			String str = vs.get(i);
			sb.append(str);
			if(i<vs.size()-1) {
				sb.append(ImakerProperties.SEPARATOR);
			}
		}
		String mods = sb.toString();
		if(mods!=null&&!mods.equals("")) {
			prop.put(IMakerKeyConstants.MODIFIED_SETTINGS, mods);			
		}
	}
	
}
