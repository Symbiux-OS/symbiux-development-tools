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


package com.nokia.s60tools.imaker.internal.preferences;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.ImageFlasherHelpContextIDs;
import com.nokia.s60tools.imaker.Messages;

/**
 * This class represents a preference page that is contributed to the
 * Preferences dialog. By subclassing <samp>FieldEditorPreferencePage</samp>,
 * we can use the field support built into JFace that allows us to create a page
 * that is small and knows how to save, restore and apply itself.
 * <p>
 * This page is used to modify preferences only. They are stored in the
 * preference store that belongs to the main plug-in class. That way,
 * preferences can be accessed directly via the preference store.
 */
public class ImageFlasherPreferencePage extends PreferencePage implements
IWorkbenchPreferencePage {
	/** UI widgets */
	private Composite parent;
	private Button buttonBrowseDir;
	private Text textPreferencesDirectory;
	private String preferencesDirectory;
	private Button checkConfml;

	/**
	 * Constructor
	 */
	public ImageFlasherPreferencePage() {
		super();
	}

	public void init(IWorkbench workbench) {}

	protected IPreferenceStore doGetPreferenceStore() {
		return IMakerPlugin.getDefault().getPreferenceStore();
	}

	private void storeValues() {
		IPreferenceStore store = doGetPreferenceStore();
		store.setValue(PreferenceConstants.PREFERENCES_DIRECTORY, textPreferencesDirectory.getText());
		store.setValue(PreferenceConstants.CONFML_SELECTION_DIALOG, checkConfml.getSelection());
	}

	private void initializeDefaults() {
		IPreferenceStore store = doGetPreferenceStore();
		store.setDefault(PreferenceConstants.PREFERENCES_DIRECTORY, ""); //$NON-NLS-1$
		store.setDefault(PreferenceConstants.CONFML_SELECTION_DIALOG, false);
	}


	/*
	 * The user has pressed "Restore defaults". Restore all default preferences.
	 */
	@Override
	protected void performDefaults() {
		initializeDefaults();
		super.performDefaults();
	}

	/*
	 * The user has pressed Ok. Store/apply this page's values appropriately.
	 */
	@Override
	public boolean performOk() {
		storeValues();
		return super.performOk();
	}

	/*
	 * The user has pressed Apply. Store/apply this page's values appropriately.
	 */
	@Override
	public void performApply() {
		storeValues();
	}

	@Override
	protected Control createContents(Composite composite) {
		parent = composite;

		// Create Top composite in top of the parent composite
		Composite container = new Composite(parent, SWT.LEFT);

		GridData topCompositeGridData = new GridData(SWT.FILL, SWT.FILL, true,false);
		container.setLayoutData(topCompositeGridData);
		GridLayout topCompositeGridLayout = createLayout();
		container.setLayout(topCompositeGridLayout);

		setHelpForControl(container,ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP);

		addPreferencesControls(container);
		initializeValues();
		setHelpForControl(textPreferencesDirectory, ImageFlasherHelpContextIDs.IMAKERPLUGIN_HELP_PREFERENCES_MANAGEMENT);

		return container;
	}

	private void initializeValues() {
		IPreferenceStore store = doGetPreferenceStore();
		String str = store.getString(PreferenceConstants.PREFERENCES_DIRECTORY);
		textPreferencesDirectory.setText(str);
		boolean show = store.getBoolean(PreferenceConstants.CONFML_SELECTION_DIALOG);
		checkConfml.setSelection(show);
	}

	/**
	 * @param container
	 */
	private void addPreferencesControls(Composite container) {
		//Group groupPreferences = new Group(container, SWT.NONE);
		Composite comp = new Composite(container,SWT.NONE);
		GridLayout layoutPreferences = new GridLayout(4, true);
		comp.setLayout(layoutPreferences);
		comp.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				4, 1));
		//comp.setText(Messages.getString("ImageFlasherPreferencePage.0")); //$NON-NLS-1$

		// Preferences save directory
		Label labelPreferencesDirectory = new Label(comp, SWT.NONE);
		labelPreferencesDirectory.setText(Messages.getString("ImageFlasherPreferencePage.1")); //$NON-NLS-1$

		textPreferencesDirectory = new Text(comp, SWT.SINGLE | SWT.BORDER);
		textPreferencesDirectory.setLayoutData(new GridData(SWT.FILL,
				SWT.NONE, true, false, 2, 1));

		// Browse directory button
		buttonBrowseDir = new Button(comp, SWT.PUSH);
		buttonBrowseDir.setText(Messages.getString("ImageFlasherPreferencePage.2")); //$NON-NLS-1$
		buttonBrowseDir.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false, 1, 1));
		buttonBrowseDir.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}

			public void widgetSelected(SelectionEvent e) {
				browsePreferencesDirectory();
			}

		});
		Composite compCheck = new Composite(container,SWT.NONE);
		compCheck.setLayout(new GridLayout(1, true));
		compCheck.setLayoutData(new GridData(SWT.FILL, SWT.NONE, true, false,
				1, 1));
		checkConfml = new Button(compCheck,SWT.CHECK);
		checkConfml.setText(Messages.getString("ImageFlasherPreferencePage.3"));

	}

	private void setHelpForControl(Control container, String id) {
		PlatformUI.getWorkbench().getHelpSystem().setHelp(container, id);
	}

	private GridLayout createLayout() {
		GridLayout topCompositeGridLayout = new GridLayout(4, false);
		topCompositeGridLayout.horizontalSpacing = 5; // CodForChk_Dis_Magic
		topCompositeGridLayout.verticalSpacing = 5; // CodForChk_Dis_Magic
		topCompositeGridLayout.marginWidth = 0;
		topCompositeGridLayout.marginHeight = 0;
		return topCompositeGridLayout;
	}

	private void browsePreferencesDirectory() {

		DirectoryDialog dd = new DirectoryDialog(Display.getCurrent().getActiveShell(), SWT.OPEN);
		dd.open();

		preferencesDirectory = dd.getFilterPath();
		textPreferencesDirectory.setText(preferencesDirectory);

	}
}
