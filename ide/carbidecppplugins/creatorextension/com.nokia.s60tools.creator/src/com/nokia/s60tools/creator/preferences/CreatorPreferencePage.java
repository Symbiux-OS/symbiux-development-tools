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


package com.nokia.s60tools.creator.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.creator.CreatorActivator;
import com.nokia.s60tools.creator.CreatorHelpContextIDs;
import com.nokia.s60tools.creator.resources.Messages;


/**
 * Preference page for DE plugin preferences
 *
 */
public class CreatorPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage{


	


	public static final int TEXT_WIDTH = 150;
	
	/**
	 * Create the preference page
	 */
	public CreatorPreferencePage() {
		super();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench arg0) {
	}


	private Text saveFolder;


	/**
	 * Button for dont ask again file replace in device
	 */
	private Button dontAskAgainFileReplaceBtn;


	/**
	 * Button for dont ask again show information 
	 */
	private Button dontAskAgainShowInformationBtn;
	
	/**
	 * Button for dont ask again shutdown Creator in device/emulator
	 */
	private Button dontAskAgainShutdownBtn;	


	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse.swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {
		Composite container = new Composite(parent, SWT.SIMPLE);
		final int cols = 1;	  
		GridLayout gdl = new GridLayout(cols, false);
		GridData gd = new GridData(GridData.FILL_BOTH);
		container.setLayout(gdl);
		container.setLayoutData(gd);		
		
		
		//Group for all components
		Group group = new Group (container, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 1;
		group.setLayout(gridLayout);
		gridLayout.makeColumnsEqualWidth = false;
		
		group.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
		group.setText(Messages.getString("CreatorPreferencePage.PreferencesGroup_Txt"));//$NON-NLS-1$
		group.setLayout (gridLayout);
		
		
		//Create preference composite for Prefix search order
		createPrefixSeachOrderComposite(group);			
		
		//Create group where is Dont Ask for file replace
		createDontAskAgainFileReplaceComposite(group);
		//Create group where is Dont ask for showing information dialog
		createDontAskAgainShowInformationComposite(group);
		
		createDontAskAgainShutdownComposite(group);
		
		getPrefsStoreValues();

		setHelps(parent);
		
		return container;
	}

	/**
	 * Creates Don't ask for Search dialogs set as new root component questionary.
	 * @param container
	 */
	private void createDontAskAgainFileReplaceComposite(
			Composite container) {


		//Group for all components
		Composite askGroup = new Composite (container, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		askGroup.setLayout(gridLayout);
//		gridLayout.makeColumnsEqualWidth = false;		
//		askGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
//		askGroup.setText(Messages.getString("CreatorPreferencePage.DontAskAgainFileReplace_Group_Txt"));  //$NON-NLS-1$
//		askGroup.setLayout (gridLayout);		
//		
		dontAskAgainFileReplaceBtn = new Button(askGroup, SWT.CHECK);
		dontAskAgainFileReplaceBtn.setToolTipText(Messages.getString("CreatorPreferencePage.DontAskAgainFileReplace_ToolTip_Txt"));//$NON-NLS-1$
		dontAskAgainFileReplaceBtn.setSelection(CreatorPreferences.getDontAskFileReplaceInDevice());

		Label label = new Label(askGroup, SWT.HORIZONTAL);
		label.setText(Messages.getString("CreatorPreferencePage.DontAskAgainFileReplace_Txt"));//$NON-NLS-1$ 
			
	}
	
	/**
	 * Creates Don't ask for Search dialogs set as new root component questionary.
	 * @param container
	 */
	private void createDontAskAgainShowInformationComposite(
			Composite container) {


		//Group for all components
		Composite askGroup = new Composite (container, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		askGroup.setLayout(gridLayout);
//		gridLayout.makeColumnsEqualWidth = false;		
//		askGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
//		askGroup.setText(Messages.getString("CreatorPreferencePage.DontAskAgainShowInformation_Group_Txt"));  //$NON-NLS-1$
//		askGroup.setLayout (gridLayout);		
//		
		dontAskAgainShowInformationBtn = new Button(askGroup, SWT.CHECK);
		dontAskAgainShowInformationBtn.setToolTipText(Messages.getString("CreatorPreferencePage.DontAskAgainShowInformation_ToolTip_Txt"));//$NON-NLS-1$
		dontAskAgainShowInformationBtn.setSelection(CreatorPreferences.getDontAskShowInformation());
	
		Label label = new Label(askGroup, SWT.HORIZONTAL);
		label.setText(Messages.getString("CreatorPreferencePage.DontAskAgainShowInformation_Txt"));//$NON-NLS-1$ 
			
	}	

	
	/**
	 * Creates Don't ask for Search dialogs set as new root component questionary.
	 * @param container
	 */
	private void createDontAskAgainShutdownComposite(
			Composite container) {


		//Group for all components
		Composite askGroup = new Composite (container, SWT.NONE);
		GridLayout gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		askGroup.setLayout(gridLayout);
//		gridLayout.makeColumnsEqualWidth = false;		
//		askGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
//		askGroup.setText(Messages.getString("CreatorPreferencePage.DontAskAgainShutdown_Group_Txt"));  //$NON-NLS-1$
//		askGroup.setLayout (gridLayout);		
		
		dontAskAgainShutdownBtn = new Button(askGroup, SWT.CHECK);
		dontAskAgainShutdownBtn.setToolTipText(Messages.getString("CreatorPreferencePage.DontAskAgainShutdown_ToolTip_Txt"));//$NON-NLS-1$
		dontAskAgainShutdownBtn.setSelection(CreatorPreferences.getDontAskShutdownCreator());
	
		Label label = new Label(askGroup, SWT.HORIZONTAL);
		label.setText(Messages.getString("CreatorPreferencePage.DontAskAgainShutdown_Txt"));//$NON-NLS-1$ 
			
	}		
	
	
	/**
	 * Create composite for prefix search order
	 * @param parent
	 * @param container
	 */
	private void createPrefixSeachOrderComposite(
			Composite container) {
				
		
		//Group for all components
//		Group prefixGroup = new Group (container, SWT.NONE);
//		GridLayout gridLayout = new GridLayout();
//		gridLayout.numColumns = 2;
//		prefixGroup.setLayout(gridLayout);
//		gridLayout.makeColumnsEqualWidth = false;
//		
//		prefixGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));		
//		prefixGroup.setText(Messages.getString("CreatorPreferencePage.Upload_Group_Txt"));  //$NON-NLS-1$
//		prefixGroup.setLayout (gridLayout);
		
		//composite for component list
		Composite listComp = new Composite(container,SWT.SIMPLE);
		GridLayout listCompLayout = new GridLayout();
		listCompLayout.numColumns = 2;
		listComp.setLayout(listCompLayout);

		Label label = new Label(listComp,  SWT.HORIZONTAL);
		label.setText(Messages.getString("CreatorPreferencePage.Upload_Group_Txt"));  //$NON-NLS-1$		
		
		saveFolder = new Text(listComp,SWT.LEFT | SWT.BORDER);
		GridData listData = new GridData(TEXT_WIDTH, SWT.DEFAULT);	
		saveFolder.setLayoutData(listData);		
		
		
//		Composite btnComp = new Composite(container,SWT.SIMPLE);
//		GridLayout btnCompLayout = new GridLayout();
//		btnCompLayout.numColumns = 1;
//		btnCompLayout.makeColumnsEqualWidth = true;
//		btnComp.setLayout(btnCompLayout);	
//		
//
//		GridData btnData = new GridData();
//		btnData.horizontalAlignment = SWT.FILL;
//		btnData.grabExcessVerticalSpace = true;
		

	}

	/**
	 * Set old values to components
	 */
	private void getPrefsStoreValues(){
		//Set Prefix search order values
		String value = CreatorPreferences.getDeviceSaveFolded();
		saveFolder.setText(value);		
		dontAskAgainFileReplaceBtn.setSelection(CreatorPreferences.getDontAskFileReplaceInDevice());
		dontAskAgainShowInformationBtn.setSelection(CreatorPreferences.getDontAskShowInformation());
		dontAskAgainShutdownBtn.setSelection(CreatorPreferences.getDontAskShutdownCreator());
	}
	

	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		// Resetting prefixes values.
		saveFolder.setText(CreatorPreferenceConstants.DEFAULT_SAVE_FOLDER_IN_DEVICE);
		// Resetting dontAskAgain value.
		dontAskAgainFileReplaceBtn.setSelection(false);
		dontAskAgainShowInformationBtn.setSelection(false);
		dontAskAgainShutdownBtn.setSelection(false);
		// Applies values to screen. Values are saved when Ok button is pressed.
		super.performDefaults();
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		IPreferenceStore store = CreatorActivator.getPrefsStore();
	
		saveDefaultFolderPreferences(store);
		saveDontAskAgainSearchConfirmation(store);
		
		return super.performOk();
	}

	/**
	 * Save prefix seach order preferences
	 * @param store
	 */
	private void saveDefaultFolderPreferences(IPreferenceStore store) {
		String value = saveFolder.getText();
		store.setValue(CreatorPreferenceConstants.DEFAULT_DEVICE_SAVE_FOLDER, value);
	}
	
	/**
	 * Save prefix seach order preferences
	 * @param store
	 */
	private void saveDontAskAgainSearchConfirmation(IPreferenceStore store) {
		
		CreatorPreferences.setDontAskFileReplaceInDevice(dontAskAgainFileReplaceBtn.getSelection());
		CreatorPreferences.setDontAskShowInformation(dontAskAgainShowInformationBtn.getSelection());
		CreatorPreferences.setDontAskShutdownCreator(dontAskAgainShutdownBtn.getSelection());
		
	}
	
	/**
	 * Sets this page's context sensitive helps
	 *
	 */
	private void setHelps(Composite helpContainer) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(helpContainer,
															CreatorHelpContextIDs.PREF_PAGE);
	}	
	
}
