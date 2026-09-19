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


package com.nokia.s60tools.creator.job;

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

import com.nokia.s60tools.creator.preferences.CreatorPreferences;
import com.nokia.s60tools.creator.resources.Messages;

/**
 * Dialog for asking confirmation for file replace from user
 */
public class ConfirmFileReplaceDialog extends Dialog {
	
	
	// File name
	private String filePath;
	// Selected button
	private int selection = 0;
	
	// UI components
	private Button confirmReplaceRadioCB = null;
	
	/**
	 * Constructor
	 * @param parentShell Shell
	 * @param filePath Name of the file to be replaced
	 */
	public ConfirmFileReplaceDialog(Shell parentShell, String filePath) {
		super(parentShell);
		this.filePath = filePath;
	}
	
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.getString("ConfirmFileReplaceDialog.Confirm_Replace_DlgLabel")); //$NON-NLS-1$ 
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
    	createButton(parent, IDialogConstants.NO_ID, IDialogConstants.NO_LABEL, true);
    	createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, true); 
    }
    
    /* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite dialogAreaComposite = (Composite) super.createDialogArea(parent);		
		Label label = new Label(dialogAreaComposite,SWT.NONE);
		label.setText(Messages.getString("ConfirmFileReplaceDialog.Confirm_Replace_DlgMsg_Prefix") //$NON-NLS-1$
						+ "'" //$NON-NLS-1$
						+ filePath
						+ "'" //$NON-NLS-1$
						+ ". " //$NON-NLS-1$
						+ Messages.getString("ConfirmFileReplaceDialog.Confirm_Replace_DlgMsg_Postfix")); //$NON-NLS-1$); 
		 
		// "Don't ask again" check box
		confirmReplaceRadioCB  = new Button(dialogAreaComposite, SWT.CHECK);
		confirmReplaceRadioCB.setText(Messages.getString("ConfirmFileReplaceDialog.DontAskAgainFileReplace_Txt")); //$NON-NLS-1$
		confirmReplaceRadioCB.setSelection(CreatorPreferences.getDontAskFileReplaceInDevice());
		// Disable "No" button when check box is selected
		confirmReplaceRadioCB.addSelectionListener(new SelectionListener() {
			public void widgetSelected(SelectionEvent arg0) {
				getButton(IDialogConstants.NO_ID).setEnabled(!confirmReplaceRadioCB.getSelection());
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
		
		if (buttonId != IDialogConstants.CANCEL_ID) {
			// Save "Don't ask again" state
				CreatorPreferences.setDontAskFileReplaceInDevice(confirmReplaceRadioCB
						.getSelection());
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
