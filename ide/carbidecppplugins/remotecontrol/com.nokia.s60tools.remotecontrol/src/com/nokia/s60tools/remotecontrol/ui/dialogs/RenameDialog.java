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
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Dialog for renaming file
 */
public class RenameDialog extends Dialog implements ModifyListener {
	
	/**
	 * Name
	 */
	private String name;
	
	/**
	 * File name text field
	 */
	private Text nameField = null;

	/**
	 * Flag for keeping track if original name is possible return value.
	 */
	private final boolean allowOriginalName;
	
	/**
	 * Name for dialog.
	 */
	private final String dialogName;
	
	/**
	 * Constructor
	 * @param parentShell Shell
	 * @param name Current name of the file
	 * @param allowOriginalName True if it is possible to return original name. False otherwise.
	 */
	public RenameDialog(Shell parentShell, String fileName, boolean allowOriginalName, String dialogName) {
		super(parentShell);
		this.name = fileName;
		this.allowOriginalName = allowOriginalName;
		this.dialogName = dialogName;
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(dialogName);
    } 	
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
    	GridLayout gdl = new GridLayout(1, false);
		GridData gd = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		parent.setLayout(gdl);
		parent.setLayoutData(gd);
		
    	createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
    	createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

    	if(!allowOriginalName) {
    		// Not allowed to return same file name. User needs to modify name before enabling OK button.
			getButton(IDialogConstants.OK_ID).setEnabled(false);
    	}
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
		GridLayout gl = new GridLayout(cols, false);
		GridData gd = new GridData(GridData.FILL_BOTH);

		dialogAreaComposite.setLayout(gl);
		dialogAreaComposite.setLayoutData(gd);
	
		Label label = new Label(dialogAreaComposite, SWT.NONE);
		label.setText(Messages.getString("RenameDialog.label"));   //$NON-NLS-1$
		nameField = new Text(dialogAreaComposite, SWT.LEFT | SWT.SINGLE | SWT.BORDER);
		nameField.setLayoutData(gd);
		nameField.setText(name);
		
		nameField.addModifyListener(this);
		
		return dialogAreaComposite;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {	
		if (buttonId == IDialogConstants.OK_ID) {
			// Save file name that user entered
			name = nameField.getText();
		}	
		super.buttonPressed(buttonId);
	}
	
	
	/**
	 * Get file name
	 * @return File name that user entered. If user canceled dialog
	 * 			the original file name is returned.
	 */
	public String getFileName(){
		return name;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.ModifyListener#modifyText(org.eclipse.swt.events.ModifyEvent)
	 */
	public void modifyText(ModifyEvent e) {
		if(!allowOriginalName && nameField.getText().equalsIgnoreCase(name)) {
			// Not allowed to return same file name.
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		} else {
			getButton(IDialogConstants.OK_ID).setEnabled(true);
		}
		
		// Empty names are not allowed.
		if(nameField.getText().equals("")) { //$NON-NLS-1$
			getButton(IDialogConstants.OK_ID).setEnabled(false);
		}
	}

}
