/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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


package com.nokia.s60tools.remotecontrol.actions;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.ui.dialogs.PreferencesUtil;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferencePage;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferencePage.Tabs;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;


/**
 * Action for opening Remote control preferences page
 */
public class OpenPreferencePageAction extends S60ToolsBaseAction {
	
	/**
	 * Tab in preferences page that will be opened.
	 */
	private final Tabs selection;

	//Tabs selection;
	
	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 * @param buttonStyle Style supported
	 * @param imageKey Image of the image used for the action. 
	 *                 Can be set to <code>null</code>, if no image is needed.
	 * @param selection Preference tab that should be be opened.
	 */
	public OpenPreferencePageAction(String text, String tooltip,
			int buttonStyle, String imageKey, Tabs selection) {
		super(text, tooltip, buttonStyle,
				ImageResourceManager.getImageDescriptor(imageKey));
		this.selection = selection;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		openPreferencePage();
	}
	
	/**
	 * Open Remote control preferences page
	 */
	private void openPreferencePage() {
		
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				RemoteControlActivator.getCurrentlyActiveWbWindowShell(),
				RCPreferencePage.PAGE_ID,
				null,
				null);
		
		Object object = dialog.getSelectedPage();
		if(object instanceof RCPreferencePage) {
			// Selecting correct tab.
			((RCPreferencePage)object).openTab(selection);
		}
		
		dialog.open();
	}	
}

