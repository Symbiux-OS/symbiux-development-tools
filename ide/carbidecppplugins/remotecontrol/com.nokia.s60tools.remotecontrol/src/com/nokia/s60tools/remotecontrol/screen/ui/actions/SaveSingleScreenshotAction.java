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

import java.io.File;

import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;
import org.eclipse.swt.widgets.FileDialog;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.common.ProductInfoRegistry;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.screen.ui.view.ScreenSettings;
import com.nokia.s60tools.remotecontrol.screen.ui.view.ScreenView;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RemoteControlMessageBox;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;


/**
 * Action for saving current screen.
 */
public class SaveSingleScreenshotAction extends S60ToolsBaseAction {
	
	/**
	 * Owner of this action.
	 */
	private final ScreenView screenView;

	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 * @param screenView Owner of this action.
	 */
	public SaveSingleScreenshotAction(String text, String tooltip, ScreenView screenView) {
		super(text,	tooltip, IAction.AS_PUSH_BUTTON,
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_SAVE_SCREENSHOT));
		this.screenView = screenView;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		Image screenShot = null;
		try {
			screenShot = ScreenSettings.getScreenShotCopy();
		} catch (Exception e) {
			showScreenNotAvailableError();
			return;
		}
		if(screenShot == null){
			showScreenNotAvailableError();
			return;
		}
		// We have valid image => saving it
		if(RCPreferences.getAskLocationAlways()) {
			saveScreenShot(screenShot);
		} else {
			screenView.saveCurrentScreenDefaultValues();
		}
		
		screenShot.dispose();
	}


	/**
	 * Ask save location from user and stores the image.
	 * @param screenShot Screen shot to be saved.
	 */
	private void saveScreenShot(Image screenShot) {
		//
		// Creating save dialog
		//
		FileDialog saveDialog = new FileDialog(RemoteControlActivator.getCurrentlyActiveWbWindowShell(), SWT.SAVE);
		
		//
		// Setting save dialog parameters
		//
		try {
			// Setting default save location
			String screenShotSaveLocation = RCPreferences.getInternalScreenShotSaveLocation();
			File saveLocFile = new File(screenShotSaveLocation);
			if(saveLocFile.exists() && saveLocFile.isDirectory()){
				// Just making sure that we really have a valid absolute path at hand
				screenShotSaveLocation = saveLocFile.getAbsolutePath(); 
			}
			else{
				// Otherwise using workspace root location as default
				IWorkspaceRoot wsRoot = ResourcesPlugin.getWorkspace().getRoot();
				screenShotSaveLocation = wsRoot.getLocation().toString();				
			}			
			saveDialog.setFilterPath(screenShotSaveLocation);
		} catch (Exception e) {
			// Could not resolve/set default save location then not setting one, and continuing
		}
		// Setting supported extensions
		saveDialog.setFilterExtensions(new String[]{"*.png", "*.*"});//$NON-NLS-1$ //$NON-NLS-2$
		// Setting default file name
		saveDialog.setFileName(ProductInfoRegistry.getProductName() + Messages.getString("SaveSingleScreenshotAction.DefaultScreenShotFileName_Postfix"));  //$NON-NLS-1$
		// Setting overwrite mode
		saveDialog.setOverwrite(true); // Confirming possible overwrite from user
		
		//
		// Running save dialog
		//
		String destinationFileAbsolutePath = saveDialog.open();
		if(destinationFileAbsolutePath != null){
			// Doing actual save if got valid path as result (not pressed Cancel)
			ImageLoader imgLoader = new ImageLoader();
			imgLoader.data = new ImageData[]{screenShot.getImageData()};
			imgLoader.save(destinationFileAbsolutePath, SWT.IMAGE_PNG);
			// Storing parent directory path for future use in next usage/sessions
			RCPreferences.setInternalScreenShotSaveLocation(new File(destinationFileAbsolutePath).getParent());
		}
	}

	/**
	 * Shows error message to user in case there was no image available. 
	 */
	private void showScreenNotAvailableError() {
		RemoteControlMessageBox infoMsg = new RemoteControlMessageBox(Messages.getString("SaveSingleScreenshotAction.ScreenImageNotAvailable_InfoMsg"), SWT.OK | SWT.ICON_INFORMATION); //$NON-NLS-1$
		infoMsg.open();
	}
	
}

