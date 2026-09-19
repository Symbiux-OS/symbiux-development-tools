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
package com.nokia.s60tools.symbianfoundationtemplates.ui.wizards.s60;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

/**
 * File selection control.
 * 
 * Provides mechanism for file selection for template
 * wizard pages.
 * 
 */
public class FileSelectionControl {
	private Text fileText;
	
	/**
	 * Default constructor.
	 * 
	 * @param page the template wizard page this control belongs to.
	 * @param parent the parent composite to attach this control.
	 */
	public FileSelectionControl(Composite topLevel, Listener listener, String label) {
		// File selection
		new Label(topLevel, SWT.NONE).setText(label);
		fileText = new Text(topLevel, SWT.BORDER);
		GridData fileGridData = new GridData(GridData.FILL_HORIZONTAL);
		fileGridData.horizontalSpan = 2;
		fileText.setLayoutData(fileGridData);
		fileText.addListener(SWT.Modify, listener);
	}
	
	/**
	 * Get selected file.
	 * 
	 * @return the selected file
	 */
	public String getSelectedFile() {
		return fileText.getText();
	}
	
	/**
	 * Get selected file without extension.
	 * 
	 * @return the selected file without extension
	 */
	public String getSelectedFileWithoutExtension() {
		if(fileText.getText().contains("."))
			return fileText.getText().substring(0, fileText.getText().lastIndexOf('.'));
		else
			return fileText.getText();
	}
	/**
	 * Set file name
	 * 
	 * @return
	 */
	public void setFileName(String filename)
	{
		if(filename!=null)
		fileText.setText(filename);
	}
	/**
	 * Get file name
	 * @return
	 */
	public Text getTextControl()
	{
		return fileText;
	}

}