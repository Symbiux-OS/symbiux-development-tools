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

package com.nokia.s60tools.remotecontrol.preferences;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.List;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Class for Ftp preferences UI
 */
public class FtpPreferencesUI extends Composite {

	/**
	 * Height hint for drive list
	 */
	private static final int DRIVES_LIST_ITEMS_HEIGHT_HINT = 6;
	
	/**
	 * Drive list widget
	 */
	private List drivesList;
	
	/**
	 * Add button
	 */
	private Button addButton;
	
	/**
	 * Remove button
	 */
	private Button removeButton;
	
	// Controls for file transfer options.
	private Button downloadConfirmCB = null;
	private Button uploadConfirmCB = null;
	private Button pasteConfirmCB = null;
	private Button deleteConfirmCB = null;
	private Button getDriveListCB = null;

	/**
	 * Constructor
	 * 
	 * @param parent
	 *            Parent widget
	 * @param style
	 *            Style parameters
	 */
	public FtpPreferencesUI(Composite parent, int style) {
		super(parent, style);

		// Create shown drives group
		Group shownDrivesGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
		shownDrivesGroup.setText(Messages.getString("FtpPreferencesUI.Ftp_Drives_Groupname")); //$NON-NLS-1$
		shownDrivesGroup.setLayout(new GridLayout(2, false));
		shownDrivesGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createShownDrives(shownDrivesGroup);
		
		// Create file transfer options group
		Group ftpGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
		ftpGroup.setText(Messages.getString("RCPreferencePage.Ftp_Options_Groupname")); //$NON-NLS-1$
		ftpGroup.setLayout(new GridLayout(1, false));
		ftpGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		downloadConfirmCB  = new Button(ftpGroup, SWT.CHECK);
		downloadConfirmCB.setText(Messages.getString("FtpPreferencesUI.Download_Confirm_CheckButton_Text")); //$NON-NLS-1$
		uploadConfirmCB  = new Button(ftpGroup, SWT.CHECK);
		uploadConfirmCB.setText(Messages.getString("FtpPreferencesUI.Upload_Confirm_CheckButton_Text")); //$NON-NLS-1$
		pasteConfirmCB  = new Button(ftpGroup, SWT.CHECK);
		pasteConfirmCB.setText(Messages.getString("FtpPreferencesUI.Paste_Confirm_CheckButton_Text")); //$NON-NLS-1$
		deleteConfirmCB  = new Button(ftpGroup, SWT.CHECK);
		deleteConfirmCB.setText(Messages.getString("FtpPreferencesUI.Delete_Confirm_CheckButton_Text")); //$NON-NLS-1$
		
		getPrefsStoreValues();
	}

	/**
	 * Creates contents for shown drives group.
	 * @param parent Parent control.
	 */
	private void createShownDrives(Composite parent) {
		// Creating drives list.
		final int listBoxStyleBits = SWT.MULTI | SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL;
		drivesList = new List(parent, listBoxStyleBits);
		drivesList.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		// Grid data for composite.
		int listHeight = drivesList.getItemHeight() * DRIVES_LIST_ITEMS_HEIGHT_HINT;
		Rectangle trim = drivesList.computeTrim(0, 0, 0, listHeight);
		GridData listData = new GridData(GridData.FILL_BOTH);
		listData.heightHint = trim.height;				
		drivesList.setLayoutData(listData);	
		
		// Creating composite for buttons
		Composite btnComp = new Composite(parent, SWT.NONE);
		GridLayout btnCompLayout = new GridLayout(1, true);
		btnComp.setLayout(btnCompLayout);	
		
		GridData btnData = new GridData();
		btnData.horizontalAlignment = SWT.FILL;
		btnData.grabExcessVerticalSpace = true;

		// Add button.
		addButton = new Button(btnComp, SWT.NONE);
		addButton.setText(Messages.getString("FtpPreferencesUI.AddDrive_Button_Text")); //$NON-NLS-1$
		addButton.setLayoutData(btnData);
		
		//listener for add button
		AddButtonSelectionAdapter addButtonListener = new AddButtonSelectionAdapter(drivesList);
		addButton.addSelectionListener(addButtonListener);
		
		// Remove button.
		removeButton = new Button(btnComp, SWT.NONE);
		removeButton.setText(Messages.getString("FtpPreferencesUI.RemoveDrive_Button_Text")); //$NON-NLS-1$
		removeButton.setLayoutData(btnData);
		
		//listener for remove button
		removeButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				drivesList.remove(drivesList.getSelectionIndices());
			}
		});
		
		// Get drives from the device check box
		getDriveListCB  = new Button(parent, SWT.CHECK);
		getDriveListCB.setText(Messages.getString("FtpPreferencesUI.GetDriveListFromDevice_CheckBoxText")); //$NON-NLS-1$
	}
	
	/**
	 * Sets default values to UI components
	 */
	public void setDefaults() {
		downloadConfirmCB.setSelection(RCPreferenceConstants.defaultDownloadConfirmation);
		uploadConfirmCB.setSelection(RCPreferenceConstants.defaultUploadConfirmation);
		pasteConfirmCB.setSelection(RCPreferenceConstants.defaultPasteConfirmation);
		deleteConfirmCB.setSelection(RCPreferenceConstants.defaultDeleteConfirmation);
		drivesList.setItems(RCPreferenceConstants.defaultShownDrivesArray);
		getDriveListCB.setSelection(RCPreferenceConstants.defaultGetDriveList);
	}

	/**
	 * Saves values to prefstore
	 */
	public void savePrefStoreValues() {
		RCPreferences.setDownloadConfirm(downloadConfirmCB.getSelection());
		RCPreferences.setUploadConfirm(uploadConfirmCB.getSelection());
		RCPreferences.setPasteConfirm(pasteConfirmCB.getSelection());
		RCPreferences.setDeleteConfirm(deleteConfirmCB.getSelection());
		RCPreferences.setShownDrives(drivesList.getItems());
		RCPreferences.setGetDriveList(getDriveListCB.getSelection());
	}

	/**
	 * Sets old values to components
	 */
	private void getPrefsStoreValues() {
		downloadConfirmCB.setSelection(RCPreferences.getDownloadConfirm());
		uploadConfirmCB.setSelection(RCPreferences.getUploadConfirm());
		pasteConfirmCB.setSelection(RCPreferences.getPasteConfirm());
		deleteConfirmCB.setSelection(RCPreferences.getDeleteConfirm());
		drivesList.setItems(RCPreferences.getShownDrives());
		getDriveListCB.setSelection(RCPreferences.getGetDriveList());
	}
}
