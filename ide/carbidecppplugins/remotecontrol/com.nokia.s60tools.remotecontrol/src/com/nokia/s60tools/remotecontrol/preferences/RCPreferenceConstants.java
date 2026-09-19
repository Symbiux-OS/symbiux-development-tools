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


/**
 * Class for storing keys to preferences.
 */
public class RCPreferenceConstants {

	//
	// CONSTANTS FOR SCREENCAPTURE
	//
	
	/**
	 * Color modes. Enum ordinals are mapped to indexes of colorCodes table 
	 */
	public enum ColorMode {
		GRAYSCALE, 
		COLOR16, 
		COLOR256, 
		COLORS64K, 
		COLORS16M
		}

	/**
	 * Color codes                                                  
	 */									         	 
	public static final byte[] colorCodes = new byte[]{(byte)0x04, (byte)0x05, (byte)0x06,  (byte)0x07,  (byte)0x08};
	                                                 //"EGray256", "EColor16", "EColor256", "EColor64K", "EColor16M"
	
	/**
	 * Default refresh delay is 1 second
	 */
	public static final String defaultRefreshDelay = "1000"; //$NON-NLS-1$

	/**
	 * Default color mode is 0x07 64k colors. Its index is 3
	 */
	public static final int defaultColorMode = 3;
	
	/**
	 * Default ask confirmation when saving screenshot.
	 */
	public static final boolean DEFAULT_ASK_LOCATION_ALWAYS = true;
	
	/**
	 * Default name for saving screenshot.
	 */
	public static final String DEFAULT_FILE_NAME = "screenshot"; //$NON-NLS-1$
	
	/**
	 * Constant for capture delay
	 */
	public static final String REFRESH_DELAY = "captureDelay"; //$NON-NLS-1$

	/**
	 * Constant for color mode
	 */
	public static final String COLOR_MODE = "colorMode"; //$NON-NLS-1$
	
	/**
	 * Constant for asking location always when taking screenshot.
	 */
	public static final String SCREENSHOT_ASK_LOCATION_ALWAYS = "askLocationAlways"; //$NON-NLS-1$
	
	/**
	 * Constant for default file name
	 */
	public static final String SAVE_FILE_NAME = "saveFileName"; //$NON-NLS-1$

	/**
	 * Constant for default location
	 */
	public static final String SAVE_LOCATION = "saveLocation"; //$NON-NLS-1$
	
	//
	// CONSTANTS FOR FTP
	//
	
	/**
	 * Default ask confirmation on download is true
	 */
	public static final boolean defaultDownloadConfirmation = true;
	
	/**
	 * Default ask confirmation on upload is true
	 */
	public static final boolean defaultUploadConfirmation = true;
	
	/**
	 * Default ask confirmation on paste is true
	 */
	public static final boolean defaultPasteConfirmation = true;
	
	/**
	 * Default ask confirmation on delete is true
	 */
	public static final boolean defaultDeleteConfirmation = true;
	
	/**
	 * Default get drive list is true
	 */
	public static final boolean defaultGetDriveList = true;
	
	/**
	 * Constant for file replace confirmation on file download
	 */
	public static final String DOWNLOAD_FILE_ASK_REPLACE_CONFIRMATION = "downloadConfirm"; //$NON-NLS-1$

	/**
	 * Constant for file replace confirmation on file upload
	 */
	public static final String UPLOAD_FILE_ASK_REPLACE_CONFIRMATION = "uploadConfirm"; //$NON-NLS-1$
	
	/**
	 * Constant for replace confirmation on paste operation
	 */
	public static final String PASTE_ASK_REPLACE_CONFIRMATION = "pasteConfirm"; //$NON-NLS-1$	
	
	/**
	 * Constant for delete file/folder confirmation on file/folder delete
	 */
	public static final String DELETE_FILE_ASK_CONFIRMATION = "deleteConfirm"; //$NON-NLS-1$
	
	/**
	 * Constant for getting drive list from the device
	 */
	public static final String GET_DRIVE_LIST_VALUE = "getDriveListValue"; //$NON-NLS-1$
	
	/**
	 * Separator used when shown drives is saved.
	 */
	public static final String SHOWN_DRIVES_SEPARATOR = "@"; //$NON-NLS-1$

	/**
	 * Drives that are shown as default.
	 */
	public static final String defaultShownDrives = "c:" + SHOWN_DRIVES_SEPARATOR + //$NON-NLS-1$
													"d:" + SHOWN_DRIVES_SEPARATOR +  //$NON-NLS-1$
													"e:" + SHOWN_DRIVES_SEPARATOR +  //$NON-NLS-1$
													"z:"; //$NON-NLS-1$

	/**
	 * Array of drives that is shown as default.
	 */
	public static final String[] defaultShownDrivesArray = { "c:", "d:", "e:", "z:" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
	
	/**
	 * Constant for shown drives.
	 */
	public static final String SHOWN_DRIVES = "shownDrives"; //$NON-NLS-1$
	
	/**
	 * Constant for upload folder on file download
	 */
	public static final String DOWNLOAD_FOLDER = "downloadFolder"; //$NON-NLS-1$
	
	/**
	 * Constant for upload folder on file upload
	 */
	public static final String UPLOAD_FOLDER = "uploadFolder"; //$NON-NLS-1$

	//
	// Internal and non-user visible preferences
	//
	
	/**
	 * Non-visible preference for screen shot save location
	 */
	public static final String SCREEN_SHOT_SAVE_LOCATION = "RcScreenShotSaveLocation"; //$NON-NLS-1$

	
	// Keyboard
	
	/**
	 * Keyboard layouts
	 */
	public enum KeyboardLayout {
		SIMPLE, 
		QWERTY
	}
	
	/**
	 * Constant for showing Navi pane
	 */
	public static final String SHOW_NAVIPANE = "showNaviPane"; //$NON-NLS-1$
	
	/**
	 * Constant for keyboard layout
	 */
	public static final String KEYBORD_LAYOUT = "KeyboardLayout"; //$NON-NLS-1$
}
