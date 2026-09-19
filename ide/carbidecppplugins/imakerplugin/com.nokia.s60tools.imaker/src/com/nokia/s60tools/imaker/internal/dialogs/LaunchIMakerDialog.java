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
package com.nokia.s60tools.imaker.internal.dialogs;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.TitleAreaDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.imaker.IEnvironmentManager;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.viewers.IMakerTabsViewer;

public class LaunchIMakerDialog extends TitleAreaDialog {

	/** Keep track of the currently visible dialog instance */
	private static LaunchIMakerDialog fgCurrentlyVisibleLaunchIMakerDialog;

	/** Id for 'Launch' button.*/
	protected static final int ID_LAUNCH_BUTTON = IDialogConstants.CLIENT_ID + 1;

	/** Id for 'Close' button.*/
	protected static final int ID_CLOSE_BUTTON = IDialogConstants.CLIENT_ID + 2;

	/** Id for 'Cancel' button.*/
	protected static final int ID_CANCEL_BUTTON = IDialogConstants.CLIENT_ID + 3;

	/**
	 * Constant specifying how wide this dialog is allowed to get (as a percentage of
	 * total available screen width) as a result of tab labels in the edit area.
	 */
	protected static final float MAX_DIALOG_WIDTH_PERCENT = 0.75f;

	/**
	 * Constant specifying how tall this dialog is allowed to get (as a percentage of
	 * total available screen height) as a result of preferred tab size.
	 */
	protected static final float MAX_DIALOG_HEIGHT_PERCENT = 0.60f;

	/**
	 * Size of this dialog if there is no preference specifying a size.
	 */
	protected static final Point DEFAULT_INITIAL_DIALOG_SIZE = new Point(540, 650); //(620, 690)


	/**
	 * Returns the currently visible dialog
	 * @return the currently visible launch dialog
	 */
	public static LaunchIMakerDialog getCurrentlyVisibleLaunchConfigurationDialog() {
		return fgCurrentlyVisibleLaunchIMakerDialog;
	}

	/**
	 * Sets which launch dialog is currently the visible one
	 * @param dialog the dialog to set as the visible one
	 */
	public static void setCurrentlyVisibleLaunchConfigurationDialog(LaunchIMakerDialog dialog) {
		fgCurrentlyVisibleLaunchIMakerDialog = dialog;
	}

	/**
	 * widgets
	 */
	private IMakerTabsViewer fTabViewer;
	private Image fBannerImage;

	private Button runButton;

	private IMakerTabsViewer imakerTabsViewer;

	private ProjectManager projectManager;

	private String filePath;

	private IEnvironmentManager environmentManager;

	public LaunchIMakerDialog(Shell parentShell, ProjectManager projectManager, IEnvironmentManager manager) {
		super(parentShell);
		this.projectManager = projectManager;
		this.environmentManager = manager;
	}
	
	public ProjectManager getProjectManager() {
		return projectManager;
	}

	@Override
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText(getShellTitle());
	}
	private String getShellTitle() {
		return Messages.getString("Project.name");
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite dialogComp = (Composite)super.createDialogArea(parent);
		setTitle(Messages.getString("LaunchImakerDialog.title"));
		setMessage(Messages.getString("LaunchImakerDialog.message"));
		setImage();
		
		addContent(dialogComp);
		return dialogComp;
	}

	public void initialize() {
		getTabViewer().initialize();
	}
	/**
	 * Adds content to the dialog area
	 * 
	 * @param dialogComp
	 */
	protected void addContent(Composite dialogComp) {
		GridData gd;
		Composite topComp = new Composite(dialogComp, SWT.NONE);
		gd = new GridData(GridData.FILL_BOTH);
		topComp.setLayoutData(gd);
		GridLayout topLayout = new GridLayout(1, false);
		topComp.setLayout(topLayout);

		Composite editAreaComp = createPreferenceEditArea(topComp);
		gd = new GridData(GridData.FILL_BOTH);
		editAreaComp.setLayoutData(gd);

		dialogComp.layout(true);
		initialize();
		applyDialogFont(dialogComp);
	}

	private void setImage() {
		setTitleImage(getBannerImage());
	}

	private Image getBannerImage() {
		if (fBannerImage == null) {
			ImageDescriptor descriptor = IMakerPlugin.getImageDescriptor("icons/imaker_dialog.png");
			if (descriptor != null) {
				fBannerImage = descriptor.createImage();
			}
		}
		return fBannerImage;
	}
	
	private Composite createPreferenceEditArea(Composite parent) {
		imakerTabsViewer = new IMakerTabsViewer(parent,environmentManager, this);
		setTabViewer(imakerTabsViewer);
		getTabViewer().addSelectionChangedListener(new ISelectionChangedListener() {
			public void selectionChanged(SelectionChangedEvent event) {
//				handleTabSelectionChanged();
			}
		});
		return (Composite)getTabViewer().getControl();
	}


	/**
	 * A launch configuration dialog overrides this method
	 * to create a custom set of buttons in the button bar.
	 * This dialog has 'Launch' and 'Cancel'
	 * buttons.
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, IDialogConstants.OK_ID, Messages.getString("LaunchImakerDialog.run"), true);
		createButton(parent, ID_CLOSE_BUTTON, Messages.getString("LaunchImakerDialog.close"), false);  
	}
	
	public Button getRunButton() {
		return runButton;
	}
	/**
	 * Handle the 'close' & 'launch' buttons here, all others are handled
	 * in <code>Dialog</code>
	 * 
	 * @see org.eclipse.jface.dialogs.Dialog#buttonPressed(int)
	 */
	protected void buttonPressed(int buttonId) {
		if (buttonId == IDialogConstants.OK_ID) {
			setFilePath(imakerTabsViewer.handleRunPressed());
			okPressed();
		} else if (buttonId == ID_CLOSE_BUTTON) {
			cancelPressed();
		} else {
			super.buttonPressed(buttonId);
		}
	}
	
	@Override
	protected Point getInitialSize() {
		return DEFAULT_INITIAL_DIALOG_SIZE;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}

	
	/**
	 * Sets the viewer used to display the tabs for a launch configuration.
	 * 
	 * @param viewer the new view to set
	 */
	protected void setTabViewer(IMakerTabsViewer viewer) {
		fTabViewer = viewer;
	}

	/**
	 * Returns the viewer used to display the tabs for a launch configuration.
	 * 
	 * @return LaunchConfigurationTabGroupViewer
	 */
	protected IMakerTabsViewer getTabViewer() {
		return fTabViewer;
	}
	
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String path) {
		this.filePath = path;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}
	
	
}
