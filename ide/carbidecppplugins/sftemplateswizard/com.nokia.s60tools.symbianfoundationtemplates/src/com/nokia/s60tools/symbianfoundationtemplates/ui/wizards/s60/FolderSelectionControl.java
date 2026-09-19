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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.dialogs.ContainerSelectionDialog;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;
import com.nokia.s60tools.symbianfoundationtemplates.actions.MMPSourceUserIncPaths;
import com.nokia.s60tools.symbianfoundationtemplates.resources.Messages;
import com.nokia.s60tools.symbianfoundationtemplates.resources.PreferenceConstants;

/**
 * Folder selection control.
 *
 */
public class FolderSelectionControl implements Listener {
	private final Composite topLevel;
	
	private Combo folderText;
	
	private Button folderButton;

	public FolderSelectionControl(Composite topLevel, Listener listener, String label) {
		this.topLevel = topLevel;
		
		// Folder selection
		new Label(topLevel, SWT.NONE).setText(label);
		folderText = new Combo(topLevel, SWT.BORDER);
		folderText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		MMPSourceUserIncPaths pathsFromMMP=new MMPSourceUserIncPaths();
		String[] paths=null;
		IPreferenceStore preferenceStore = SymbianFoundationTemplates.getDefault().getPreferenceStore();
		if(label.equals(Messages.getString("WizardPageSourceFolderLabel")) && preferenceStore.getBoolean(PreferenceConstants.OPENED_FROM_VIEW))
		{
			paths=pathsFromMMP.getPreviousValues(MMPSourceUserIncPaths.ValueTypes.SRC_DIRS);
			if(paths!=null)
			{
			folderText.setItems(paths);
			folderText.select(0);
			}
		}
		else if(label.equals(Messages.getString("WizardPageHeaderFolderLabel")) && preferenceStore.getBoolean(PreferenceConstants.OPENED_FROM_VIEW))
		{
			paths=pathsFromMMP.getPreviousValues(MMPSourceUserIncPaths.ValueTypes.USERINC_DIRS);
			if(paths!=null)
			{
			folderText.setItems(paths);
			folderText.select(0);
			}
		}
		folderText.addListener(SWT.Modify, listener);
		folderButton = new Button(topLevel, SWT.NONE);
		folderButton.setText(Messages.getString("FolderSelectionBrowseLabel")); //$NON-NLS-1$
		folderButton.addListener(SWT.Selection, this);
	}
	
	public void handleEvent(Event event) {
		// Handle folder button
		if(event.widget == folderButton) {
			ContainerSelectionDialog dialog = new ContainerSelectionDialog(topLevel.getShell(),
					ResourcesPlugin.getWorkspace().getRoot(), false,
					Messages.getString("FolderSelectionDialogTitle")); //$NON-NLS-1$
			
			dialog.setBlockOnOpen(true);
			
			dialog.open();
			
			Object[] result = dialog.getResult();
			
			if(result != null) {
				String path = result[0].toString();
				folderText.setText(path.substring(1, path.length()));
			}	
		}
	}
	
	/**
	 * @return the selected folder
	 */
	public String getSelectedFolder() {
		return folderText.getText();
	}
	
	/**
	 * @param text the selected folder to be set
	 */
	public void setSelectedFolder(String text) {
		if(text != null)
			folderText.setText(text);
	}
}
