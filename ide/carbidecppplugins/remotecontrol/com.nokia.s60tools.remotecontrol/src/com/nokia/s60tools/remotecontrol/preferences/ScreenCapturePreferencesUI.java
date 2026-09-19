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

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Text;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferenceConstants.ColorMode;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Class for Screen capture preferences UI
 */
public class ScreenCapturePreferencesUI extends Composite {

	/**
	 * Screen capture delay values
	 */
	private static final String DELAYS[] = 
		{"10", "20", "30", "40", "50", "100", "200", "500", "1000"}; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$ //$NON-NLS-7$ //$NON-NLS-8$ //$NON-NLS-9$
	
	/**
	 *  Color mode radio buttons
	 */
	private Button grayscaleRB;
	private Button color16RB;
	private Button color256RB;
	private Button color64kRB;
	private Button color16MRB;

	/**
	 * Refresh delay combobox
	 */
	private Combo delayCombo;

	/**
	 * Text field containing file name prefix for screen shot.
	 */
	private Text fileNameText;

	/**
	 * Text field containing path where screen shots are saved.
	 */
	private Text pathText;

	/**
	 * Checkbox if single screen shot filename and path should be asked always.
	 */
	private Button askLocationAlways;

	/**
	 * Constructor
	 * 
	 * @param parent Parent widget
	 * @param style Style parameters
	 */
	public ScreenCapturePreferencesUI(Composite parent, int style) {
		super(parent, style);

		// Create capture options group
		Group screenCaptureGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
		screenCaptureGroup.setText(Messages.getString("ScreenCapturePreferencesUI.CaptureOptionGroup_Title")); //$NON-NLS-1$
		screenCaptureGroup.setLayout(new GridLayout(1, false));
		screenCaptureGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createCaptureOptions(screenCaptureGroup);
		
		// Create save screenshot group
		Group saveScreenshotGroup = new Group(this, SWT.SHADOW_ETCHED_IN);
		saveScreenshotGroup.setText(Messages.getString("ScreenCapturePreferencesUI.SaveScreenshotGroup_Title")); //$NON-NLS-1$
		saveScreenshotGroup.setLayout(new GridLayout(1, false));
		saveScreenshotGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		createSaveScreenshot(saveScreenshotGroup);
		
		// Create link to Carbide's Keys preferences page.
		Link configureKeysLink = new Link(this, SWT.NULL);
		configureKeysLink.setText(Messages.getString("ScreenCapturePreferencesUI.CarbideKeysLink_Text")); //$NON-NLS-1$
		configureKeysLink.setToolTipText(Messages.getString("ScreenCapturePreferencesUI.CarbideKeysLink_Tooltip")); //$NON-NLS-1$
		
		//listener for configure keys link
		configureKeysLink.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent event) {
				// Opens Carbide's Keys preferences page when selected.
				PreferenceDialog dialog = org.eclipse.ui.dialogs.PreferencesUtil.createPreferenceDialogOn(
						RemoteControlActivator.getCurrentlyActiveWbWindowShell(),
						"org.eclipse.ui.preferencePages.Keys", //$NON-NLS-1$
						null,
						null);
				dialog.open();
			}
		});
		
		getPrefsStoreValues();
	}

	/**
	 * Creates contents for capture options group.
	 * @param parent Parent widget.
	 */
	private void createCaptureOptions(Composite parent) {
		
		// Create delay label
		Label delayLabel = new Label(parent, SWT.SIMPLE);
		delayLabel.setText(Messages.getString("ScreenCapturePreferencesUI.Delay_Label")); //$NON-NLS-1$

		// Create two column layout for delay combobox and label
		Composite delayContainer = new Composite(parent, SWT.SIMPLE);
		delayContainer.setLayout(new GridLayout(2, false));
		delayContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		// Create delay combobox
		delayCombo = new Combo(delayContainer, SWT.DROP_DOWN | SWT.READ_ONLY);
		// Add values
		for (int i = 0; i < DELAYS.length; i++) {
			delayCombo.add(DELAYS[i]);
		}	
		delayCombo.setVisibleItemCount(20);

		// Create milliseconds label for delay combobox
		Label msLabel = new Label(delayContainer, SWT.SIMPLE);
		msLabel.setText(Messages.getString("ScreenCapturePreferencesUI.Delay_Label_msPostfix")); //$NON-NLS-1$

		// Create color mode label
		Label colorModeLabel = new Label(parent, SWT.SIMPLE);
		colorModeLabel.setText(Messages.getString("ScreenCapturePreferencesUI.Color_Mode_Label")); //$NON-NLS-1$

		// Create color mode radio buttons
		grayscaleRB = new Button(parent, SWT.RADIO);
		grayscaleRB.setText(Messages.getString("ScreenCapturePreferencesUI.Grayscale")); //$NON-NLS-1$
		color16RB = new Button(parent, SWT.RADIO);
		color16RB.setText(Messages.getString("ScreenCapturePreferencesUI.16_Colors")); //$NON-NLS-1$
		color256RB = new Button(parent, SWT.RADIO);
		color256RB.setText(Messages.getString("ScreenCapturePreferencesUI.256_Colors")); //$NON-NLS-1$
		color64kRB = new Button(parent, SWT.RADIO);
		color64kRB.setText(Messages.getString("ScreenCapturePreferencesUI.64k_Colors")); //$NON-NLS-1$
		color16MRB = new Button(parent, SWT.RADIO);
		color16MRB.setText(Messages.getString("ScreenCapturePreferencesUI.16M_Colors")); //$NON-NLS-1$
	}

	/**
	 * Creates contents for save screenshot options group.
	 * @param parent Parent widget.
	 */
	private void createSaveScreenshot(Composite parent) {
		
		// Ask always
		askLocationAlways = new Button(parent, SWT.CHECK);
		askLocationAlways.setText(Messages.getString("ScreenCapturePreferencesUI.AskLocationAlvays_CheckBox_Text")); //$NON-NLS-1$
		
		// Separator.
		Label separatorLine = new Label(parent, SWT.HORIZONTAL | SWT.SEPARATOR);
		separatorLine.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL));
		
		// File name and location subgroup.
		
		Label infoLabel = new Label(parent, SWT.NONE);
		infoLabel.setText(Messages.getString("ScreenCapturePreferencesUI.FileNamePrefix_Label")); //$NON-NLS-1$
		
		Composite locationComposite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, false);
		locationComposite.setLayout(layout);
		locationComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		
		// File name row.
		Label fileNameLabel = new Label(locationComposite, SWT.NONE);
		fileNameLabel.setText(Messages.getString("ScreenCapturePreferencesUI.NamePrefix_Label")); //$NON-NLS-1$
		fileNameText = new Text(locationComposite, SWT.BORDER | SWT.FILL | SWT.CLIP_CHILDREN);
		GridData data = new GridData(SWT.FILL);
		data.horizontalSpan = 2;
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		fileNameText.setLayoutData(data);
		
		// Path row.
		Label pathLabel = new Label(locationComposite, SWT.NONE);
		pathLabel.setText(Messages.getString("ScreenCapturePreferencesUI.PathLabel_Label")); //$NON-NLS-1$
		pathText = new Text(locationComposite, SWT.BORDER | SWT.FILL);
		data = new GridData(SWT.FILL);
		data.horizontalAlignment = GridData.FILL;
		data.grabExcessHorizontalSpace = true;
		pathText.setLayoutData(data);
		Button browseButton = new Button(locationComposite, SWT.NONE);
		browseButton.setText(Messages.getString("ScreenCapturePreferencesUI.BrowseButton_Text")); //$NON-NLS-1$
		
		//listener for Browse button
		BrowseButtonSelectionAdapter browseButtonListener = new BrowseButtonSelectionAdapter(pathText);
		browseButton.addSelectionListener(browseButtonListener);
	}
	
	/**
	 * Sets default values to UI components
	 */
	public void setDefaults() {
		setDefaultColorMode();
		setDefaultRefreshDelay();
		setDefaultScreenshotLocation();
	}

	/**
	 * Sets default color mode to radio buttons
	 */
	private void setDefaultColorMode() {
		grayscaleRB.setSelection(false);
		color16RB.setSelection(false);
		color256RB.setSelection(false);
		color64kRB.setSelection(true);
		color16MRB.setSelection(false);
	}

	/**
	 * Sets default refresh delay to combobox
	 */
	private void setDefaultRefreshDelay() {
		delayCombo.select(delayCombo.indexOf(RCPreferenceConstants.defaultRefreshDelay));
	}

	/**
	 * Sets default screenshot location.
	 */
	private void setDefaultScreenshotLocation() {
		askLocationAlways.setSelection(RCPreferenceConstants.DEFAULT_ASK_LOCATION_ALWAYS);
		fileNameText.setText(RCPreferenceConstants.DEFAULT_FILE_NAME);
		// Path is empty as default.
		pathText.setText(""); //$NON-NLS-1$
	}
	
	/**
	 * Saves values to prefstore
	 */
	public void savePrefStoreValues() {
		// Save refresh delay value
		int index = delayCombo.getSelectionIndex();
		if (index >= 0) {
			String delay = delayCombo.getItem(index);
			if (delay != null) {
				RCPreferences.setRefreshDelay(delay);
			}
		}

		// Save color mode value
		if (grayscaleRB.getSelection()) {
			RCPreferences.setColorMode(ColorMode.GRAYSCALE.ordinal());
		} else if (color16RB.getSelection()) {
			RCPreferences.setColorMode(ColorMode.COLOR16.ordinal());
		} else if (color256RB.getSelection()) {
			RCPreferences.setColorMode(ColorMode.COLOR256.ordinal());
		} else if (color64kRB.getSelection()) {
			RCPreferences.setColorMode(ColorMode.COLORS64K.ordinal());
		} else if (color16MRB.getSelection()) {
			RCPreferences.setColorMode(ColorMode.COLORS16M.ordinal());
		}
		
		// Save screenshot location preferences.
		RCPreferences.setAskLocationAlways(askLocationAlways.getSelection());
		RCPreferences.setScreenshotFileName(fileNameText.getText());
		RCPreferences.setScreenShotSaveLocation(pathText.getText());
	}

	/**
	 * Sets old values to components
	 */
	private void getPrefsStoreValues() {

		// Get refresh delay and set value selected on the combobox
		String delay = RCPreferences.getRefreshDelay();
		int index = delayCombo.indexOf(delay);
		if (index >= 0) {
			delayCombo.select(index);
		} else {
			// Use default
			setDefaultRefreshDelay();
		}
				
		// Get color mode value and set current radio button selected
		int colorMode = RCPreferences.getColorModeInt();
		switch (colorMode) {
			case 0:
				grayscaleRB.setSelection(true);
				break;
			case 1:
				color16RB.setSelection(true);
				break;
			case 2:
				color256RB.setSelection(true);
				break;
			case 3:
				color64kRB.setSelection(true);
				break;
			case 4:
				color16MRB.setSelection(true);
				break;

			default:
				// Use default
				setDefaultColorMode();
				break;
		}

		// Get save screenshot location preferences.
		askLocationAlways.setSelection(RCPreferences.getAskLocationAlways());
		fileNameText.setText(RCPreferences.getScreenshotFileName());
		pathText.setText(RCPreferences.getScreenShotSaveLocation());
	}
}
