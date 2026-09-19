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

package com.nokia.s60tools.remotecontrol.screen.ui.actions;

import org.eclipse.jface.action.IAction;

import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.screen.ui.view.ScreenView;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;

/**
 * Action for saving current image and all future images.
 */
public class SaveMultiScreenshotsAction extends S60ToolsBaseAction {
	/**
	 * Text for start action.
	 */
	private static final String startText = Messages.getString("SaveMultiScreenshotsAction.SaveScreenshots_Text"); //$NON-NLS-1$
	/**
	 * Text for stop action.
	 */
	private static final String stopText = Messages.getString("SaveMultiScreenshotsAction.StopSavingScreenshots_Text"); //$NON-NLS-1$
	/**
	 *  Tooltip for start action.
	 */
	private static final String startTooltip = Messages.getString("SaveMultiScreenshotsAction.SaveScreenshots_Tooltip"); //$NON-NLS-1$
	/**
	 *  Tooltip for stop action.
	 */
	private static final String stopTooltip = Messages.getString("SaveMultiScreenshotsAction.StopSavingScreenshots_Tooltip"); //$NON-NLS-1$
	
	/**
	 * Boolean if all screenshots are saved.
	 */
	private boolean saveScreenshots = false;
	
	/**
	 * Owner of this action.
	 */
	private final ScreenView screenView;

	/**
	 * Constructor
	 * @param screenView Owner of this action.
	 */
	public SaveMultiScreenshotsAction(ScreenView screenView) {
		super(startText, startTooltip,
				IAction.AS_PUSH_BUTTON,
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_START_MULTI_SCREENSHOT));
		this.screenView = screenView;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		saveScreenshots = !saveScreenshots;
		screenView.getScreenSettings().setSavingAllScreenshots(saveScreenshots);

		if(saveScreenshots) {
			showSavingMessage();
			screenView.saveCurrentScreenDefaultValues();
		} else {
			showSaveCount();
		}
		
		updateImage();
		screenView.updateActionButtonStates();
	}
	
	/**
	 * Set status for saving all screenshots. This can be used to change
	 * state from outside. Needed when there comes error when saving images.
	 * @param saveScreenshots True if all screenshots are saved. False otherwise.
	 */
	public void setSaving(boolean saveScreenshots) {
		if(this.saveScreenshots && !saveScreenshots) {
			showSaveCount();
		}
		else if(!this.saveScreenshots && saveScreenshots) {
			showSavingMessage();
		}
		
		this.saveScreenshots = saveScreenshots;
		screenView.getScreenSettings().setSavingAllScreenshots(saveScreenshots);
		// Updating image.
		updateImage();
	}
	
	/**
	 * Sets description to inform user that images are being saved.
	 */
	private void showSavingMessage() {
		String location = RCPreferences.getScreenShotSaveLocation();
		screenView.updateDescription(Messages.getString("SaveMultiScreenshotsAction.SavingImages_DescMsg") + location); //$NON-NLS-1$
	}

	/**
	 * Updates description to show how many images were saved and to where.
	 */
	private void showSaveCount() {
		// Showing information about saved images to the user in description field.
		int imagesSaved = screenView.getScreenSettings().getImagesSaved();
		String location = RCPreferences.getScreenShotSaveLocation();
		screenView.updateDescription(imagesSaved + Messages.getString("SaveMultiScreenshotsAction.ImagesSavedTo_DescMsg") + location); //$NON-NLS-1$
	}
	
	/**
	 * Updates correct image to action depending on status.
	 */
	private void updateImage(){
		if(saveScreenshots){
			setImageDescriptor(ImageResourceManager.
					getImageDescriptor(ImageKeys.IMG_STOP_MULTI_SCREENSHOT));
			setText(stopText);
			setToolTipText(stopTooltip);
		} else {
			setImageDescriptor(ImageResourceManager.
					getImageDescriptor(ImageKeys.IMG_START_MULTI_SCREENSHOT));
			setText(startText);
			setToolTipText(startTooltip);
		}
	}
}