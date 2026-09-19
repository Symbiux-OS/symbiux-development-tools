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

package com.nokia.s60tools.remotecontrol.ui.dialogs;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Dialog for adding new drives to be shown in File Transfer view.
 */
public class DriveNameDialog extends Dialog implements SelectionListener {

	/**
	 * Drive name that was selected.
	 */
	private String selectedDriveName = null;
	/**
	 * Combo box for selecting drive.
	 */
	private Combo driveCombo;
	/**
	 * List of shown drives.
	 */
	private final String[] drives;

	/**
	 * Constructor.
	 * @param shell Parent shell.
	 * @param drives List of drives to be shown.
	 */
	public DriveNameDialog(Shell shell, String[] drives) {
		super(shell);
		this.drives = drives;
	}

	/* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);
        getButton(IDialogConstants.OK_ID).setFocus();
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.getString("DriveNameDialog.ShowDrive_DlgTitle")); //$NON-NLS-1$
    } 
    
    /* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		
		Composite dialogAreaComposite = (Composite) super.createDialogArea(parent);		
		
		//
		//Adding custom controls
		//
		
		final int cols = 1;	  
		GridLayout gdl = new GridLayout(cols, false);
		GridData gData = new GridData(GridData.FILL_BOTH);

		dialogAreaComposite.setLayout(gdl);
		dialogAreaComposite.setLayoutData(gData);
		
		// Label
		Label label = new Label(dialogAreaComposite,SWT.HORIZONTAL);
		label.setLayoutData(gData);
		label.setText(Messages.getString("DriveNameDialog.SelectDrive_DialogText")); //$NON-NLS-1$
		
		// Drive selection combo box.
		driveCombo = new Combo(dialogAreaComposite, SWT.DROP_DOWN | SWT.READ_ONLY);
		driveCombo.addSelectionListener(this);
		driveCombo.setLayoutData(gData);
		driveCombo.setVisibleItemCount(drives.length);
		driveCombo.setItems(drives);
		// Setting first item active.
		driveCombo.select(0);
		selectedDriveName = driveCombo.getItem(0);
		
		return dialogAreaComposite;
	}
	
	/**
	 * Returns drive name entered by user
	 * @return drive name or null if nothing was selected.
	 */
	public String getDriveName(){
		return selectedDriveName;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetDefaultSelected(SelectionEvent e) {
		// Not implemented.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	public void widgetSelected(SelectionEvent e) {
		selectedDriveName = driveCombo.getItem(driveCombo.getSelectionIndex());
	}
}
