/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies). 
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
package com.nokia.s60tools.ui.preferences;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.preference.IPreferenceNode;
import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.jface.preference.PreferenceManager;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;

public class PreferenceUtils {

	/**
	 * Open a preference page by ID introduced in <code>plugin.xml</code>
	 * 
	 * @param preferencePageID ID for preference page
	 * @param shell {@link Shell}
	 */
	public static void openPreferencePage(String preferencePageID, Shell shell) {

		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				shell, preferencePageID, null, null);

		dialog.open();
	}		
	
	/**
	 * Open a preference page tab by ID introduced in <code>plugin.xml</code>
	 * 
	 * If the preference page contains tabs, and one tab is wanted to open, use this. 
	 * 
	 * @param preferencePageID ID for preference page
	 * @param preferencePageTabID ID for preference page tab under preference page with ID <preferencePageID>
	 * @param shell {@link Shell}
	 */
	// Warning comes from org.eclipse.jface.preference.PreferenceManager.getElements(int) because it uses raw List type as return value	
	@SuppressWarnings("unchecked")
	public static void openPreferencePage(String preferencePageID,
			String preferencePageTabID, Shell shell) {

		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				shell, preferencePageID, null, null);

		List<IPreferenceNode> elemns = dialog.getPreferenceManager()
				.getElements(PreferenceManager.PRE_ORDER);
		IPreferenceNode preferenceNode = null;
		//check all preference pages, if required is found
		for (Iterator<IPreferenceNode> iterator = elemns.iterator(); iterator
				.hasNext();) {
			preferenceNode = iterator.next();
			if (preferenceNode.getId().equals(preferencePageTabID)) {
				break;
			}
		}
		
		//If required preference page tab was found, set that as selection
		if (preferenceNode != null) {
			dialog.getTreeViewer().setSelection(
					new StructuredSelection(preferenceNode));
		}

		dialog.open();

	}		
	
}
