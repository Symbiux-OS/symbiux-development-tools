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

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.remotecontrol.RemoteControlHelpContextIDs;
import com.nokia.s60tools.remotecontrol.common.ProductInfoRegistry;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Preference page for Remote control
 */
public class RCPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {

	/**
	 * ID for Remote Control preferences page.
	 */
	public static final String PAGE_ID = "com.nokia.s60tools.remotecontrol.RCPreferencePage"; //$NON-NLS-1$
	
	/**
	 * Enumeration of possible tabs in the page.
	 */
	public enum Tabs {
		SCREENCAPTURE,
		FTP
	}
	
	/**
	 *  Screen capture preference UI
	 */
	private ScreenCapturePreferencesUI scPrefUI;
	/**
	 *  Ftp preference UI
	 */
	private FtpPreferencesUI ftpPrefUI;
	/**
	 * Folder containing tabs in the page.
	 */
	private TabFolder tabFolder;
	
	/**
	 * General constructor for opening default page.
	 */
	public RCPreferencePage() {
		super(ProductInfoRegistry.getProductName());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.preference.PreferencePage#createContents(org.eclipse
	 * .swt.widgets.Composite)
	 */
	protected Control createContents(Composite parent) {

		Composite container = new Composite(parent, SWT.SIMPLE);
		container.setLayout(new GridLayout(1, false));
		container.setLayoutData(new GridData(GridData.FILL_BOTH));
				
		// Create tab folder
		tabFolder = new TabFolder(container, SWT.TOP | SWT.FILL);
		tabFolder.setLayoutData(new GridData(GridData.FILL_BOTH));

		// Create tabs
		createScreenCaptureTab(tabFolder);
		createFtpTab(tabFolder);
		
		// Setting context help IDs	
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, 
	    		RemoteControlHelpContextIDs.REMOTE_CONTROL_PREFERENCES);
		
		return container;
	}

	/**
	 * Opens specified tab in preferences page.
	 * @param tab to be opened.
	 */
	public void openTab(Tabs tab) {
		// Opening correct tab.
		if (tab == Tabs.SCREENCAPTURE) {
			tabFolder.setSelection(0);
		} else if (tab == Tabs.FTP) {
			tabFolder.setSelection(1);
		}
	}
	
	/**
	 * Creates Ftp tab
	 * @param tabFolder Tab folder where tab should be added
	 */
	private void createFtpTab(TabFolder tabFolder) {
		TabItem ftpTab = new TabItem(tabFolder, SWT.FILL);
		ftpTab.setText(Messages.getString("RCPreferencePage.Ftp_Tabname")); //$NON-NLS-1$

		// Create tab content
		ftpPrefUI = new FtpPreferencesUI(tabFolder, SWT.NONE);
		ftpPrefUI.setLayout(new GridLayout(1, false));
		ftpPrefUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		ftpTab.setControl(ftpPrefUI);
	}

	/**
	 * Creates Screen Capture tab
	 * @param tabFolder Tab folder where tab should be added
	 */
	private void createScreenCaptureTab(TabFolder tabFolder) {
		TabItem screenCaptureTab = new TabItem(tabFolder, SWT.FILL);
		screenCaptureTab.setText(Messages.getString("RCPreferencePage.Screencapture_Tabname")); //$NON-NLS-1$

		// Create screen capture tab content
		scPrefUI = new ScreenCapturePreferencesUI(tabFolder, SWT.NONE);
		scPrefUI.setLayout(new GridLayout(1, false));
		scPrefUI.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		screenCaptureTab.setControl(scPrefUI);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performDefaults()
	 */
	protected void performDefaults() {
		// Set default values
		scPrefUI.setDefaults();
		ftpPrefUI.setDefaults();

		super.performDefaults();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.preference.PreferencePage#performOk()
	 */
	public boolean performOk() {
		// Save preferences
		scPrefUI.savePrefStoreValues();
		ftpPrefUI.savePrefStoreValues();

		return super.performOk();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
	 */
	public void init(IWorkbench workbench) {
		// Not needed
	}
}
