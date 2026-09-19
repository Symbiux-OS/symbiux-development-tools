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

import java.util.ArrayList;
import java.util.Arrays;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.ui.dialogs.DriveNameDialog;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RemoteControlMessageBox;

/**
 * Listener for add button events.
 */
public class AddButtonSelectionAdapter extends SelectionAdapter {
	
	/**
	 * List of all possible drives that can be shown.
	 */
	private static final String[] ALL_DRIVES = { "a:", "b:", "c:", "d:", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
			"e:", "f:", "g:", "h:", "i:", "j:",	"k:", "l:", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
			"m:", "n:", "o:", "p:", "q:", "r:", "s:", "t:", //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$
			"u:", "v:", "w", "x:", "y:", "z:" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	
	/**
	 * Control containing list of drives.
	 */
	private List drivesList;

	/**
	 * Constructor.
	 * @param drivesList List of drives that is handled.
	 */
	public AddButtonSelectionAdapter(List drivesList) {
		this.drivesList = drivesList;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent event) {
		
		String[] possibleDrives = getPossibleDrives();
		if(possibleDrives.length == 0) {
			// No new drives available. Showing error message.
			RemoteControlMessageBox msgBox =
				new RemoteControlMessageBox(Messages.getString("AddButtonSelectionAdapter.AllDrivesShown_MsgText"), SWT.ICON_INFORMATION); //$NON-NLS-1$
			msgBox.open();
		}
		
		// Showing select drive dialog.
		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		DriveNameDialog driveNameDialog = new DriveNameDialog(sh, possibleDrives);
		int result = driveNameDialog.open();
		
		if (result == IDialogConstants.OK_ID && driveNameDialog.getDriveName() != null) {
			// Adding drive that was selected to drivesList.
			String newName = driveNameDialog.getDriveName();
			
			for(int i = 0;i < drivesList.getItemCount();i++) {
				if(newName.compareTo(drivesList.getItem(i)) < 0) {
					// Found drive that should be later in the list. Adding new drive before it.
					drivesList.add(newName, i);
					return;
				}
			}
			
			// Adding drive to the last position in list.
			drivesList.add(driveNameDialog.getDriveName());
		}	
	}
	
	/**
	 * Gets list of drives that aren't already set in preferences.
	 * @return List of drives.
	 */
	private String[] getPossibleDrives() {
		
		String[] currentDrives = drivesList.getItems();
		// Creating new array, because array generated with .asList() doesn't support remove operation.
		ArrayList<String> possibleDrives = new ArrayList<String>(Arrays.asList(ALL_DRIVES));

		// Removing currently set drives from possible drives list.
		for(String drive : currentDrives) {
			if(possibleDrives.contains(drive)) {
				possibleDrives.remove(drive);
			}
		}
		
		return possibleDrives.toArray(new String[0]);
	}
}
