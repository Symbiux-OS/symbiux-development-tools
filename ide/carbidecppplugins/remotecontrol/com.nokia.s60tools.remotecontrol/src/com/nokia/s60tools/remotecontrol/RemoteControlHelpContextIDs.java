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

package com.nokia.s60tools.remotecontrol;

/**
 * IDs for context sensitive help.
 * @see contexts.xml -file IDs links to <code> <context id="<ID>"> </code>
 */
public class RemoteControlHelpContextIDs {
	/**
     * The plug-in ID.
     */	 
    private static final String REMOTE_CONTROL_HELP_PROJECT_PLUGIN_ID = 
                                        "com.nokia.s60tools.remotecontrol.help"; //$NON-NLS-1$
	
    /**
     * The Screen view ID.
     */	
    public static final String REMOTE_CONTROL_SCREEN_VIEW = 
		  REMOTE_CONTROL_HELP_PROJECT_PLUGIN_ID +".REMOTE_CONTROL_SCREEN_VIEW"; //$NON-NLS-1$

    /**
     * The Preferences ID.
     */	
    public static final String REMOTE_CONTROL_PREFERENCES = 
		  REMOTE_CONTROL_HELP_PROJECT_PLUGIN_ID +".REMOTE_CONTROL_PREFERENCES"; //$NON-NLS-1$
    
    /**
     * The File transfer ID.
     */	
    public static final String REMOTE_CONTROL_FTP_VIEW = 
		  REMOTE_CONTROL_HELP_PROJECT_PLUGIN_ID +".REMOTE_CONTROL_FTP_VIEW"; //$NON-NLS-1$
    
    /**
     * The Keyboard ID.
     */	
    public static final String REMOTE_CONTROL_KEYBOARD = 
		  REMOTE_CONTROL_HELP_PROJECT_PLUGIN_ID +".REMOTE_CONTROL_KEYBOARD"; //$NON-NLS-1$
    
}
