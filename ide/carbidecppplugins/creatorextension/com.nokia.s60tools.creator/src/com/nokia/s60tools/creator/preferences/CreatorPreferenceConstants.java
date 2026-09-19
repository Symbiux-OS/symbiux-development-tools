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

/**
 * Class for storing keys to preferences.
 */
public class CreatorPreferenceConstants {
	
	
	/**
	 * Default value for script save folder in device/emulator.
	 */
	public static final String DEFAULT_SAVE_FOLDER_IN_DEVICE = "c:\\data\\";//$NON-NLS-1$
	
	/**
	 * <code>true</code> value
	 */
	public static final String TRUE = "true"; //$NON-NLS-1$
	
	/**
	 * <code>false</code> value
	 */
	public static final String FALSE = "false"; //$NON-NLS-1$
	
	/**
	 * preference DB key for prefix search order.
	 */
	public final static String DEFAULT_DEVICE_SAVE_FOLDER = "creatorDefaultDeviceSaveFolder"; //$NON-NLS-1$
	
	/**
	 * preference DB key for "don't ask again" for replace file in device. 
	 */
	public final static String CREATOR_DONT_ASK_REPLACE = "creatorDontAskReplace";//$NON-NLS-1$

	
	/**
	 * preference DB key for "don't ask again" for show information to follow execution in device. 
	 */
	public final static String DONT_ASK_SHOW_INFORMATION = "creatorDontAskShowInformation";//$NON-NLS-1$

	/**
	 * preference DB key for "don't ask again" for shutdown Creator in device. 
	 */
	public static final String CREATOR_DONT_ASK_SHUTDOWN = "creatorDontAskShutdown";
	
}
