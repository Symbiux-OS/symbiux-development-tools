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

package com.nokia.s60tools.remotecontrol.screen.ui.view;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.preference.PreferenceDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.dialogs.PreferencesUtil;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferencePage;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RemoteControlMessageBox;

/**
 * This class is used for saving screen shots based on preferences.
 * Stores images to queue and saves them sequentially with correct file name.
 */
public class ScreenSaverThread extends Thread {
	
	/**
	 * Queue keeping images track on images that need to be saved later.
	 */
	private LinkedBlockingQueue<byte[]> imageQueue;

	/**
	 * Boolean if this thread should be running.
	 */
	private boolean running = true;

	/**
	 * Screen view.
	 */
	private final ScreenView screenView;
	
	/**
	 * Amount if images that will be saved before asking if user wants to continue
	 * saving more images.
	 */
	private final int IMAGES_SAVED_BEFORE_NOTE = 1000;
	
	/**
	 * Constructor.
	 * @param screenView Screen view.
	 */
	public ScreenSaverThread(ScreenView screenView) {
		this.screenView = screenView;
		imageQueue = new LinkedBlockingQueue<byte[]>();
	}
	
	/**
	 * Adds image to the queue from which it will be saved to file system later.
	 * @param image to be saved.
	 */
	public synchronized void addImageToQueue(byte[] image) {
		if(running) {
			imageQueue.add(image);
		}
	}
	
	/**
	 * Stops the thread.
	 */
	public void stopThread() {
		running = false;
		
		imageQueue.clear();
	}

	/**
	 * Saves image to file system using values that are in preferences.
	 * @param image Image to be saved.
	 * @return True if save succeeded. False otherwise.
	 */
	private boolean saveImageDefaultValues(byte[] image) throws IOException {
		
		// Checking the path in preferences.
		File filePath = new File(RCPreferences.getScreenShotSaveLocation());
		if(filePath.isDirectory()) {
			// Image can be saved, because folder is ok.
			File fileNameWithPath = getNextFileName(filePath);
			saveImage(fileNameWithPath, image);
			return true;
		} else {
			// Image cannot be saved, because folder was not found.
			String msg = Messages.getString("ScreenSaverThread.InvalidPath_ErrorMsg"); //$NON-NLS-1$
			
			errorMsgBoxRunnable msgBoxRunnable = new errorMsgBoxRunnable(msg);
			
			Display.getDefault().asyncExec(msgBoxRunnable);
		}
		return false;
	}
	
	/**
	 * Open Remote control preferences page
	 */
	private void openPreferencePage() {
		
		PreferenceDialog dialog = PreferencesUtil.createPreferenceDialogOn(
				RemoteControlActivator.getCurrentlyActiveWbWindowShell(),
				RCPreferencePage.PAGE_ID,
				null,
				null);

		dialog.open();
	}	
	
	/**
	 * Gets next valid file name that can be used to save image.
	 * Checks given directory if it already contains files with same name
	 * and then creates new file name that doesn't exist.
	 * @param directory Directory to be checked.
	 * @return File name that can be used to save image.
	 */
	private File getNextFileName(File directory) {
		String fileName = RCPreferences.getScreenshotFileName();
		
		// Getting files that have matching file name.
		FileFilter filter = new FileFilter(directory, fileName);
		String[] files = directory.list(filter);
		
		// Checking if there is already file with bigger index.
		int index = 0;
		for(String tmpFileName : files) {
			// Index can be extracted because only files which contain index get through filter.
			String fileIndex = tmpFileName.substring(tmpFileName.lastIndexOf('(') + 1, tmpFileName.lastIndexOf(')'));
			int tmpIndex = Integer.parseInt(fileIndex);
			if(tmpIndex > index) {
				index = tmpIndex;
			}
		}
		
		// Making sure that file does not exist already.
		File nextFile;
		do {
			// Updating index to prevent using existing index. 
			index++;
			nextFile = new File(directory.getPath() + File.separator + fileName + "(" + index  + ").png"); //$NON-NLS-1$ //$NON-NLS-2$
		} while(nextFile.exists());
		
		return nextFile;
	}
	
	/**
	 * Saves given image with given file name/path.
	 * @param fileName Name and path of file where image is saved.
	 * @param imageData Image to be saved.
	 */
	private void saveImage(File fileName, byte[] imageData) throws IOException {
		// Write file to disk
		
		OutputStream out = new FileOutputStream(fileName);
		out.write(imageData);
		out.close();
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {

		byte[] image = null;
		while(running) {
			try {
				image = imageQueue.poll(50, TimeUnit.MILLISECONDS);

				if(image != null) {
					// New image was found from queue, saving it.
					if(saveImageDefaultValues(image)) {
						screenView.getScreenSettings().imageSaved();
						if(screenView.getScreenSettings().getImagesSaved() == IMAGES_SAVED_BEFORE_NOTE) {
							// Pre-defined amount of images saved since starting saving.
							// Showing error message from UI thread.
							Runnable showErrorMessage = new Runnable(){
								public void run() {
									String msg = IMAGES_SAVED_BEFORE_NOTE + Messages.getString("ScreenSaverThread.SaveAllScreenshotsConfirmation_MessageText"); //$NON-NLS-1$
									RemoteControlMessageBox msgBox = new RemoteControlMessageBox(
											msg, SWT.YES | SWT.NO | SWT.ICON_QUESTION);

									int result = msgBox.open();
									if(result == SWT.NO) {
										stopSavingScreenshots();
									}
								}
							};
							Display.getDefault().asyncExec(showErrorMessage);
						}
					}
				}

				image = null;

			} catch (final Exception e) {
				
				// Showing error message from UI thread.
				Runnable showErrorMessage = new Runnable(){
					public void run() {
						// Preventing more images to cause same error message.
						stopSavingScreenshots();
						
						String msg = Messages.getString("ScreenSaverThread.FailedToSaveImage_ErrorMsg1") //$NON-NLS-1$
							+ RCPreferences.getScreenShotSaveLocation() + 
							Messages.getString("ScreenSaverThread.FailedToSaveImage_ErrorMsg2") + e.getMessage(); //$NON-NLS-1$
						RemoteControlMessageBox msgBox = new RemoteControlMessageBox(
								msg, SWT.ICON_ERROR);

						msgBox.open();
					}
				};
				Display.getDefault().asyncExec(showErrorMessage);

			}
		}
	}

	/**
	 * Stops the save all screenshots action.
	 */
	private void stopSavingScreenshots() {
		// Preventing more images to cause same error message.
		screenView.getScreenSettings().setSavingAllScreenshots(false);
		screenView.updateActionButtonStates();
		imageQueue.clear();
	}
	
	/**
	 * Runnable for showing error message with option to show preferences page.
	 */
	private class errorMsgBoxRunnable implements Runnable {

		/**
		 * Message to be shown.
		 */
		private final String msg;

		/**
		 * Constructor.
		 * @param msg Message that is shown to user.
		 */
		public errorMsgBoxRunnable(String msg) {
			this.msg = msg;
		}

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// Preventing more images to cause same error message.
			stopSavingScreenshots();
			
			RemoteControlMessageBox msgBox = new RemoteControlMessageBox(
					msg, SWT.YES | SWT.NO | SWT.ICON_ERROR);
			
			int result = msgBox.open();

			// Opening preferences if Yes is selected.
			if(result == SWT.YES){
				openPreferencePage();
			}
		}
	}
	
	/**
	 * Filter that can be used to for getting indexed image files with
	 * given name prefix.
	 */
	private class FileFilter implements FilenameFilter {

		/**
		 * Directory that is used to compare.
		 */
		private final File defaultDir;
		/**
		 * File name that is used to compare.
		 */
		private final String defaultName;

		/**
		 * Constructor.
		 * @param defaultDir Directory that is used to compare.
		 * @param defaultName File name that is used to compare.
		 */
		public FileFilter(File defaultDir, String defaultName) {
			this.defaultDir = defaultDir;
			this.defaultName = defaultName;
		}

		/* (non-Javadoc)
		 * @see java.io.FilenameFilter#accept(java.io.File, java.lang.String)
		 */
		public boolean accept(File dir, String name) {
			if(dir != defaultDir) {
				return false;
			}
			
			// Matches to e.g. defaultName(123).png
			String regex = "\\A" + defaultName + "\\([0-9]+\\)\\.png\\z"; //$NON-NLS-1$ //$NON-NLS-2$
			Pattern pattern = Pattern.compile(regex);
			Matcher matcher = pattern.matcher(name);
			if(matcher.find()) {
				return true;
			}
			return false;
		}
	}
}
