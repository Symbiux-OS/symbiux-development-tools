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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Dialog for entering new folder name 
 */
public class FolderNameDialog extends Dialog {
	
	/**
	 * Folder name text filed
	 */
	private Text folderNameText;
	
	/**
	 * Folder name
	 */
	private String folderName;
	
	/**
	 * Constructor.
	 * @param parentShell Parent shell
	 */
	public FolderNameDialog(Shell parentShell) {
		super(parentShell);
	}
	
	/* (non-Javadoc)
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    protected void createButtonsForButtonBar(Composite parent) {
        createButton(parent, IDialogConstants.OK_ID, IDialogConstants.OK_LABEL, true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);     
		Button ok = getButton(IDialogConstants.OK_ID);
		ok.setEnabled(false);
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    protected void configureShell(Shell shell) {
        super.configureShell(shell);
        shell.setText(Messages.getString("FolderNameDialog.Dialog_Label")); //$NON-NLS-1$ 
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
	
		Label label = new Label(dialogAreaComposite,SWT.HORIZONTAL);
		label.setText(Messages.getString("FolderNameDialog.Folder_Name_Label")); //$NON-NLS-1$
		folderNameText = new Text(dialogAreaComposite, SWT.LEFT | SWT.BORDER | SWT.BORDER);	
		folderNameText.setLayoutData(gd);
		folderNameText.setEnabled(true);
		
		// Enables OK button when one or more characters are entered
		folderNameText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				folderName = folderNameText.getText();
				if(folderName != null  && folderName.trim().length() > 0){
					Button ok = getButton(IDialogConstants.OK_ID);
					ok.setEnabled(true);
				}else{
					Button ok = getButton(IDialogConstants.OK_ID);
					ok.setEnabled(false);
				}
			}
		});	 				
		return dialogAreaComposite;
	}    
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.window.Window#open()
	 */
	public int open(){
		folderName = ""; //$NON-NLS-1$
		return super.open();
	}
	
	/**
	 * Returns folder name entered by user
	 * @return Folder name
	 */
	public String getFolderName(){
		return folderName;
	}
}
