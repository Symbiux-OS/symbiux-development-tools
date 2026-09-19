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


package com.nokia.s60tools.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.ui.internal.Messages;
import com.nokia.s60tools.ui.internal.S60ToolsUiPlugin;
import com.nokia.s60tools.ui.preferences.PreferenceUtils;


/**
 * Action for opening a preferences page
 */
public class OpenPreferencePageAction extends S60ToolsBaseAction {
	
	/**
	 * Preference page icon
	 */
	public static final String IMG_PREFERENCE_PAGE = "IMG_PREFERENCE_PAGE";
	private String preferencePageID = null;
	private String preferencePageTabID = null;
	private Shell shell = null;

	
	
	/**
	 * Constructor for creating Action to open preference page tab
	 * @param preferencePageID
	 * @param preferencePageTabID
	 * @param shell
	 */
	public OpenPreferencePageAction(String preferencePageID,
			String preferencePageTabID, Shell shell) {
		super(Messages.getString("OpenPreferencePageAction.OpenPreferences_Action_Text"), //$NON-NLS-1$
				Messages.getString("OpenPreferencePageAction.OpenPreferences_Action_Tooltip"), //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON,
				S60ToolsUiPlugin.getImageDescriptorForKey(IMG_PREFERENCE_PAGE));
		this.preferencePageID = preferencePageID;
		this.preferencePageTabID = preferencePageTabID;
		this.shell = shell;
	}
	
	/**
	 * Constructor for creating Action to open preference page 
	 * @param preferencePageID
	 * @param shell
	 */
	public OpenPreferencePageAction(String preferencePageID,
			Shell shell) {
		this(preferencePageID, null, shell);
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
		
		if(preferencePageTabID == null){
			PreferenceUtils.openPreferencePage(preferencePageID, shell);
		}
		else{
			PreferenceUtils.openPreferencePage(preferencePageID, preferencePageTabID, shell);
		}						
	}	
}

