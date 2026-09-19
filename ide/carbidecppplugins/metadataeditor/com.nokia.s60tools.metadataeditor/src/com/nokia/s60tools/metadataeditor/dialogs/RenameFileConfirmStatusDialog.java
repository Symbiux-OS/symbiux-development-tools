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

 
 
package com.nokia.s60tools.metadataeditor.dialogs;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class RenameFileConfirmStatusDialog extends StatusDialog {

	
	private IPath oldFile = null;
	private IPath newFile = null;

	public RenameFileConfirmStatusDialog(Shell parent) {
		super(parent);
	}
	public RenameFileConfirmStatusDialog(Shell parent, IPath oldFile, IPath newFile) {
		super(parent);
		this.oldFile = oldFile;
		this.newFile = newFile;
		setTitle("Delete old file");
		
		
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.dialogs.Dialog#createDialogArea(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);		
	
		final int cols = 1;	  
		GridLayout gdl = new GridLayout(cols, false);
		GridData gd = new GridData(GridData.FILL_BOTH);

		composite.setLayout(gdl);
		composite.setLayoutData(gd);
		
		Label label = new Label(composite,SWT.HORIZONTAL);
		label.setText("New file was created with name: " +newFile.lastSegment()
				+"\nDelete " +oldFile.lastSegment() +"?");
				
			
		return composite;
	}    	

}
