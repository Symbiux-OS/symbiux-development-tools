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
package com.nokia.s60tools.remotecontrol.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Listener for Browse button events.
 */
public class BrowseButtonSelectionAdapter extends SelectionAdapter {

	/**
	 * Text field that contains directory.
	 */
	private final Text locationText;

	/**
	 * Constructor.
	 * @param locationText 
	 */
	public BrowseButtonSelectionAdapter(Text locationText) {
		this.locationText = locationText;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent event) {
		// Show select folder dialog to user
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		
		DirectoryDialog dirDialog = new DirectoryDialog(sh, SWT.OPEN);
		dirDialog.setText(Messages.getString("BrowseButtonSelectionAdapter.DirectoryDialog_Title")); //$NON-NLS-1$
		dirDialog.setMessage(Messages.getString("BrowseButtonSelectionAdapter.DirectoryDialog_Message")); //$NON-NLS-1$
		
		// Using last used folder as default path.
		String saveLocationPath = RCPreferences.getScreenShotSaveLocation();
		dirDialog.setFilterPath(saveLocationPath);
		
		// Get user selected folder
		String destPath = dirDialog.open();
		
		if (destPath == null) {
			// User has canceled dialog
			return;
		}
		
		RCPreferences.setScreenShotSaveLocation(destPath);
		locationText.setText(destPath);
	}
}
