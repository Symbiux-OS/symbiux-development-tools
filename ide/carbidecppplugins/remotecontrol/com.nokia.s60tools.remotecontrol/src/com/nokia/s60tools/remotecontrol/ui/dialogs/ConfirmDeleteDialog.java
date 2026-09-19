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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Dialog for asking confirmation for deleting file or folder from user
 */
public class ConfirmDeleteDialog extends Dialog {

	/**
	 * Selected button
	 */
	private int selection = 0;
	
	/**
	 * UI components
	 */
	private Button confirmDeleteRadioCB = null;
	
	/**
	 * Constructor
	 * @param parentShell Shell
	 */
	public ConfirmDeleteDialog(Shell parentShell) {
		super(parentShell);
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.getString("ConfirmDeleteDialog.ConfirmDelete_Title"));  //$NON-NLS-1$
    } 	
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
    	GridLayout gdl = new GridLayout(1, false);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		parent.setLayout(gdl);
		parent.setLayoutData(gd);
		
    	createButton(parent, IDialogConstants.YES_ID, IDialogConstants.YES_LABEL, true);
    	createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, false);
    }
    
    /* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite dialogAreaComposite = (Composite) super.createDialogArea(parent);		
		Label label = new Label(dialogAreaComposite,SWT.NONE);
		label.setText(Messages.getString("ConfirmDeleteDialog.ConfirmDelete_Text"));  //$NON-NLS-1$
		 
		// "Don't ask again" check box
		confirmDeleteRadioCB  = new Button(dialogAreaComposite, SWT.CHECK);
		confirmDeleteRadioCB.setText(Messages.getString("ConfirmDeleteDialog.ConfirmAlwaysCheck_Text")); //$NON-NLS-1$
		confirmDeleteRadioCB.setSelection(!RCPreferences.getDeleteConfirm());

		// Disable "No" button when check box is selected
		confirmDeleteRadioCB.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				getButton(IDialogConstants.NO_ID).setEnabled(!confirmDeleteRadioCB.getSelection());
			}
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// Not implemented
			}
		});
		
		return dialogAreaComposite;
	}    
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {
		// Save pressed button id
		selection = buttonId;
		
		if (buttonId != IDialogConstants.NO_ID) {
			// Save "Don't ask again" state
			RCPreferences.setDeleteConfirm(!confirmDeleteRadioCB.getSelection());
		}
		
		super.close();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#open()
	 */
	public int open(){
		return super.open();
	}
	
	/**
	 * Returns id of selected button
	 * @return Id of selected button
	 */
	public int getSelection() {
		return selection;
	}
}
