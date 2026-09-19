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

package com.nokia.s60tools.hticonnection.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.IPreferencePage;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.preference.PreferenceNode;

import com.nokia.s60tools.hticonnection.HtiApiActivator;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferencePage;
import com.nokia.s60tools.hticonnection.resources.ImageKeys;
import com.nokia.s60tools.hticonnection.resources.Messages;


/**
 * Action for opening HTI API  preferences page
 */
public class OpenPreferencePageAction extends S60ToolsBaseAction {
	
	/**
	 * Constructor
	 */
	public OpenPreferencePageAction() {
		super(Messages.getString("OpenPreferencePageAction.OpenPreferences_Action_Text"), //$NON-NLS-1$
				Messages.getString("OpenPreferencePageAction.OpenPreferences_Action_Tooltip"), //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON,
				ImageKeys.IMG_PREFERENCES);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		openPreferencePage();
	}

	/**
	 * Open HTI API preferences page preference page
	 */
	private void openPreferencePage(){
		IPreferencePage page = new HtiApiPreferencePage();
		PreferenceManager mgr = new PreferenceManager();
		IPreferenceNode node = new PreferenceNode("1", page);//$NON-NLS-1$
		mgr.addToRoot(node);
		PreferenceDialog dialog = new PreferenceDialog(HtiApiActivator.getCurrentlyActiveWbWindowShell(), mgr);
		dialog.create();
		dialog.setMessage(page.getTitle());
		dialog.open();	
	}	
}

