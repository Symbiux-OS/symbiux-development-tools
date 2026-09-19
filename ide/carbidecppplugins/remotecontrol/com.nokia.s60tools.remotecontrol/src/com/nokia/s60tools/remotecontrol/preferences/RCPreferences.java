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

import org.eclipse.jface.util.IPropertyChangeListener;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;

/**
 * Helper class to use Remote control preferences. Use this class for accessing
 * RC preferences instead of accessing directly through
 * {@link org.eclipse.jface.util.IPropertyChangeListener.IPreferenceStore}.
 */
public class RCPreferences {

	
	/**
	 * Initializes default values for preferences if not initialized before.
	 */
	public static void initDefaultValues() {
		// Refresh delay
		if(!RemoteControlActivator.getPrefsStore().getDefaultString(RCPreferenceConstants.REFRESH_DELAY).
				equals(RCPreferenceConstants.defaultRefreshDelay)){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.REFRESH_DELAY, 
																RCPreferenceConstants.defaultRefreshDelay);
		}
		
		// Color mode
		if(RemoteControlActivator.getPrefsStore().getDefaultInt(RCPreferenceConstants.COLOR_MODE) 
				!= RCPreferenceConstants.defaultColorMode){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.COLOR_MODE, 
																RCPreferenceConstants.defaultColorMode);
		}
		
		// Save screenshot location confirmation
		if(RemoteControlActivator.getPrefsStore().getDefaultBoolean(RCPreferenceConstants.SCREENSHOT_ASK_LOCATION_ALWAYS) 
				!= RCPreferenceConstants.DEFAULT_ASK_LOCATION_ALWAYS){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.SCREENSHOT_ASK_LOCATION_ALWAYS, 
																RCPreferenceConstants.DEFAULT_ASK_LOCATION_ALWAYS);
		}
		
		// Save screenshot file name
		if(!RemoteControlActivator.getPrefsStore().getDefaultString(RCPreferenceConstants.SAVE_FILE_NAME).equals(
				RCPreferenceConstants.DEFAULT_FILE_NAME)){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.SAVE_FILE_NAME, 
																RCPreferenceConstants.DEFAULT_FILE_NAME);
		}
		
		// Download confirmation
		if(RemoteControlActivator.getPrefsStore().getDefaultBoolean(RCPreferenceConstants.DOWNLOAD_FILE_ASK_REPLACE_CONFIRMATION) 
				!= RCPreferenceConstants.defaultDownloadConfirmation){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.DOWNLOAD_FILE_ASK_REPLACE_CONFIRMATION, 
																RCPreferenceConstants.defaultDownloadConfirmation);
		}
		
		// Upload confirmation
		if(RemoteControlActivator.getPrefsStore().getDefaultBoolean(RCPreferenceConstants.UPLOAD_FILE_ASK_REPLACE_CONFIRMATION) 
				!= RCPreferenceConstants.defaultUploadConfirmation){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.UPLOAD_FILE_ASK_REPLACE_CONFIRMATION, 
																RCPreferenceConstants.defaultUploadConfirmation);
		}
		
		// Paste confirmation
		if(RemoteControlActivator.getPrefsStore().getDefaultBoolean(RCPreferenceConstants.PASTE_ASK_REPLACE_CONFIRMATION) 
				!= RCPreferenceConstants.defaultPasteConfirmation){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.PASTE_ASK_REPLACE_CONFIRMATION, 
																RCPreferenceConstants.defaultPasteConfirmation);
		}
		
		// Delete confirmation
		if(RemoteControlActivator.getPrefsStore().getDefaultBoolean(RCPreferenceConstants.DELETE_FILE_ASK_CONFIRMATION) 
				!= RCPreferenceConstants.defaultDeleteConfirmation){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.DELETE_FILE_ASK_CONFIRMATION, 
																RCPreferenceConstants.defaultDeleteConfirmation);
		}
		
		// Get drive list from the device value
		if(RemoteControlActivator.getPrefsStore().getDefaultBoolean(RCPreferenceConstants.GET_DRIVE_LIST_VALUE) 
				!= RCPreferenceConstants.defaultGetDriveList){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.GET_DRIVE_LIST_VALUE, 
																RCPreferenceConstants.defaultGetDriveList);
		}
		
		// Shown drives list
		if(RemoteControlActivator.getPrefsStore().getDefaultString(RCPreferenceConstants.SHOWN_DRIVES) 
				!= RCPreferenceConstants.defaultShownDrives){
			
			RemoteControlActivator.getPrefsStore().setDefault(RCPreferenceConstants.SHOWN_DRIVES, 
																RCPreferenceConstants.defaultShownDrives);
		}
	}
	
	
	/**
	 * Adds a property change listener to this preference store
	 * 
	 * @param listener A property change listener
	 */
	public static void addPropertyChangeListener(IPropertyChangeListener listener) {
		RemoteControlActivator.getPrefsStore().addPropertyChangeListener(listener);
	}
	
	/**
	 * Removes a property change listener from this preference store
	 * 
	 * @param listener A property change listener
	 */
	public static void removePropertyChangeListener(IPropertyChangeListener listener) {
		RemoteControlActivator.getPrefsStore().removePropertyChangeListener(listener);
	}
	
	/**
	 * Gets screen capture delay
	 * 
	 * @return Delay for refresh or empty string if not set
	 */
	public static String getRefreshDelay() {

		return RemoteControlActivator.getPrefsStore().getString(
				RCPreferenceConstants.REFRESH_DELAY);
	}
	
	/**
	 * Gets screen capture delay as int value
	 * 
	 * @return Delay for refresh or 0 if not set
	 */
	public static int getRefreshDelayInt() {
		int retval = 0;
		String delay = getRefreshDelay();
		
		if(delay != null && !delay.trim().equals("")) { //$NON-NLS-1$
			retval = new Integer(delay).intValue();
		}
		return retval;
	}

	/**
	 * Sets screen capture delay
	 * 
	 * @param refreshDelay
	 *            refresh delay value
	 */
	public static void setRefreshDelay(String refreshDelay) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.REFRESH_DELAY, refreshDelay);
	}
	
	/**
	 * Gets color mode byte value
	 * 
	 * @return Color mode byte value
	 */
	public static byte getColorModeByte() {
		return RCPreferenceConstants.colorCodes[getColorModeInt()];
	}
	
	/**
	 * Gets color mode int value
	 * 
	 * @return Color mode int value
	 */
	public static int getColorModeInt() {
		return RemoteControlActivator.getPrefsStore().getInt(
				RCPreferenceConstants.COLOR_MODE);
	}
	
	/**
	 * Sets color mode
	 * 
	 * @param colorMode
	 *            Color mode value
	 */
	public static void setColorMode(int colorMode) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.COLOR_MODE, colorMode);
	}
	
	/**
	 * Gets location is asked always when saving screenshot. 
	 * 
	 * @return Ask location boolean value
	 */
	public static boolean getAskLocationAlways() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.SCREENSHOT_ASK_LOCATION_ALWAYS);
	}
	
	/**
	 * Sets location is asked always when saving screenshot.
	 * 
	 * @param askLocationAlways Ask location boolean value
	 */
	public static void setAskLocationAlways(boolean askLocationAlways) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.SCREENSHOT_ASK_LOCATION_ALWAYS, askLocationAlways);
	}
	
	/**
	 * Gets file name that is used to save screen shots.
	 * Used for sequential and single screen shots.
	 * 
	 * @return File name for screen shots.
	 */
	public static String getScreenshotFileName() {
		return RemoteControlActivator.getPrefsStore().getString(
				RCPreferenceConstants.SAVE_FILE_NAME);
	}
	
	/**
	 * Sets file name that is used to save screen shots.
	 * Used for sequential and single screen shots.
	 * 
	 * @param File name for screen shots.
	 */
	public static void setScreenshotFileName(String FileName) {
		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.SAVE_FILE_NAME, FileName);
	}
	
	/**
	 * Gets path where screen shots are saved.
	 * Used for sequential and single screen shots.
	 * 
	 * @return Path for screen shots.
	 */
	public static String getScreenShotSaveLocation() {
		return RemoteControlActivator.getPrefsStore().getString(
				RCPreferenceConstants.SAVE_LOCATION);
	}
	
	/**
	 * Sets path where screen shots are saved.
	 * Used for sequential and single screen shots.
	 * 
	 * @param Path for screen shots.
	 */
	public static void setScreenShotSaveLocation(String path) {
		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.SAVE_LOCATION, path);
	}
	
	/**
	 * Gets ask replace confirmation on download boolean value
	 * 
	 * @return Ask confirmation boolean value
	 */
	public static boolean getDownloadConfirm() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.DOWNLOAD_FILE_ASK_REPLACE_CONFIRMATION);
	}
	
	/**
	 * Sets ask replace confirmation on download
	 * 
	 * @param askConfirmation
	 *            Ask confirmation boolean value
	 */
	public static void setDownloadConfirm(boolean askConfirmation) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.DOWNLOAD_FILE_ASK_REPLACE_CONFIRMATION, askConfirmation);
	}
	
	/**
	 * Gets ask replace confirmation on upload boolean value
	 * 
	 * @return Ask confirmation boolean value
	 */
	public static boolean getUploadConfirm() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.UPLOAD_FILE_ASK_REPLACE_CONFIRMATION);
	}
	
	/**
	 * Sets ask replace confirmation on upload
	 * 
	 * @param askConfirmation
	 *            Ask confirmation boolean value
	 */
	public static void setUploadConfirm(boolean askConfirmation) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.UPLOAD_FILE_ASK_REPLACE_CONFIRMATION, askConfirmation);
	}

	/**
	 * Gets ask replace confirmation on paste boolean value
	 * 
	 * @return Ask confirmation boolean value
	 */
	public static boolean getPasteConfirm() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.PASTE_ASK_REPLACE_CONFIRMATION);
	}

	/**
	 * Sets ask replace confirmation on paste
	 * 
	 * @param askConfirmation
	 *            Ask confirmation boolean value
	 */
	public static void setPasteConfirm(boolean askConfirmation) {
		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.PASTE_ASK_REPLACE_CONFIRMATION, askConfirmation);
	}
	
	/**
	 * Gets ask confirmation on delete file/folder boolean value
	 * 
	 * @return Ask confirmation boolean value
	 */
	public static boolean getDeleteConfirm() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.DELETE_FILE_ASK_CONFIRMATION);
	}
	
	/**
	 * Sets ask confirmation on delete file/folder
	 * 
	 * @param askConfirmation Ask confirmation boolean value
	 */
	public static void setDeleteConfirm(boolean askConfirmation) {
		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.DELETE_FILE_ASK_CONFIRMATION, askConfirmation);
	}

	/**
	 * Gets get drive list boolean value
	 * 
	 * @return Get drive list boolean value
	 */
	public static boolean getGetDriveList() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.GET_DRIVE_LIST_VALUE);
	}
	
	/**
	 * Sets get drive list
	 * 
	 * @param getDriveList GetDriveList boolean value
	 */
	public static void setGetDriveList(boolean getDriveList) {
		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.GET_DRIVE_LIST_VALUE, getDriveList);
	}
	
	/**
	 * Gets array of drives that are shown in File Transfer view.
	 *  
	 * @return Array of drives to be shown.
	 */
	public static String[] getShownDrives() {
	
		// Get shown drives
		String drives = RemoteControlActivator.getPrefsStore().getString(RCPreferenceConstants.SHOWN_DRIVES);
		if(drives != null && drives.trim().length() > 0){
			return drives.split(RCPreferenceConstants.SHOWN_DRIVES_SEPARATOR);
		}
		return new String[0];
	}
	
	/**
	 * Sets array of drives that are shown in File Transfer view.
	 * 
	 * @param drives Shown drives.
	 */
	public static void setShownDrives(String[] drives) {
		
		StringBuffer drivesList = new StringBuffer();
		
		// Separating drives with separator to prevent mixing them.
		for (int i = 0; i < drives.length; i++) {
			drivesList.append(drives[i]);
			if(i < (drives.length-1)){
				drivesList.append(RCPreferenceConstants.SHOWN_DRIVES_SEPARATOR); //Separator
			}
		}
		
		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.SHOWN_DRIVES, drivesList.toString());
	}
	
	/**
	 * Gets screen shot save location. Used when screen shot location is always asked.
	 * Gets internal, non-user visible preference.
	 * 
	 * @return Absolute path name to directory to save screen shots, or empty string ("") if not set previously
	 */
	public static String getInternalScreenShotSaveLocation() {		
		return RemoteControlActivator.getPrefsStore().getString(
				RCPreferenceConstants.SCREEN_SHOT_SAVE_LOCATION);
	}
	
	/**
	 * Sets screen shot save location. Used when screen shot location is always asked.
	 * Sets internal, non-user visible preference.
	 * 
	 * @param screenShotSaveLocationAbsPath Absolute path name to directory to save screenshots in next sessions.
	 */
	public static void setInternalScreenShotSaveLocation(String screenShotSaveLocationAbsPath) {
		// Storing internal preference => no need to invoke preference listeners => using putValue
		RemoteControlActivator.getPrefsStore().putValue(
				RCPreferenceConstants.SCREEN_SHOT_SAVE_LOCATION, screenShotSaveLocationAbsPath);
	}
	
	/**
	 * Gets location where to download files.
	 * Gets internal, non-user visible preference.
	 * 
	 * @return Absolute path name to directory to download files, or empty string ("") if not set previously
	 */
	public static String getDownloadLocation() {		
		return RemoteControlActivator.getPrefsStore().getString(
				RCPreferenceConstants.DOWNLOAD_FOLDER);
	}
	
	/**
	 * Sets location where to download files.
	 * Sets internal, non-user visible preference.
	 * 
	 * @param downloadLocation Absolute path name to directory to download files.
	 */
	public static void setDownloadLocation(String downloadLocation) {
		RemoteControlActivator.getPrefsStore().putValue(
				RCPreferenceConstants.DOWNLOAD_FOLDER, downloadLocation);
	}
	
	/**
	 * Gets location to upload files from.
	 * Gets internal, non-user visible preference.
	 * 
	 * @return Absolute path name to directory to upload files from, or empty string ("") if not set previously
	 */
	public static String getUploadLocation() {		
		return RemoteControlActivator.getPrefsStore().getString(
				RCPreferenceConstants.UPLOAD_FOLDER);
	}
	
	/**
	 * Sets location to upload files from.
	 * Sets internal, non-user visible preference.
	 * 
	 * @param uploadLocation Absolute path name to directory to upload files from.
	 */
	public static void setUploadLocation(String uploadLocation) {
		RemoteControlActivator.getPrefsStore().putValue(
				RCPreferenceConstants.UPLOAD_FOLDER, uploadLocation);
	}
	
	/**
	 * Gets show Navi pane boolean value
	 * 
	 * @return Show Navi pane boolean value
	 */
	public static boolean getShowNaviPane() {
		return RemoteControlActivator.getPrefsStore().getBoolean(
				RCPreferenceConstants.SHOW_NAVIPANE);
	}
	
	/**
	 * Sets show Navi pane
	 * 
	 * @param show
	 *            Show Navi pane boolean value
	 */
	public static void setShowNaviPane(boolean show) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.SHOW_NAVIPANE, show);
	}
	
	/**
	 * Gets keyboard layout
	 * 
	 * @return Keyboard layout
	 */
	public static RCPreferenceConstants.KeyboardLayout getKeyboardLayout() {
		int value = RemoteControlActivator.getPrefsStore().getInt(
				RCPreferenceConstants.KEYBORD_LAYOUT);
		if (value == 0) {
			return RCPreferenceConstants.KeyboardLayout.SIMPLE;
		} else {
			return RCPreferenceConstants.KeyboardLayout.QWERTY;
		}
	}
	
	/**
	 * Sets keyboard layout
	 * 
	 * @param layout Keyboard layout
	 */
	public static void setKeyboardLayout(RCPreferenceConstants.KeyboardLayout layout) {

		RemoteControlActivator.getPrefsStore().setValue(
				RCPreferenceConstants.KEYBORD_LAYOUT, layout.ordinal());
	}
}
