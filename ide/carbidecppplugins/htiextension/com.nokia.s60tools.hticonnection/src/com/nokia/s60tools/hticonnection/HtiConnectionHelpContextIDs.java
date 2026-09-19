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

package com.nokia.s60tools.hticonnection;

/**
 * IDs for context sensitive help.
 * @see contexts.xml -file IDs links to <code> <context id="<ID>"> </code>
 */
public class HtiConnectionHelpContextIDs {
	/**
     * The plug-in ID.
     */	 
    private static final String HTI_CONNECTION_HELP_PROJECT_PLUGIN_ID = 
                                        "com.nokia.s60tools.hticonnection.help"; //$NON-NLS-1$
	/**
	 * Main view help ID.
	 */
    public static final String HTI_CONNECTION_MAIN_VIEW = 
    	HTI_CONNECTION_HELP_PROJECT_PLUGIN_ID +".HTI_CONNECTION_MAIN_VIEW"; //$NON-NLS-1$
    /**
     * Preferences page help ID.
     */
    public static final String HTI_CONNECTION_PREFERENCES = 
    	HTI_CONNECTION_HELP_PROJECT_PLUGIN_ID +".HTI_CONNECTION_PREFERENCES"; //$NON-NLS-1$
    /**
     * Error message page help ID.
     */
    public static final String HTI_CONNECTION_ERROR_MESSAGE = 
    	HTI_CONNECTION_HELP_PROJECT_PLUGIN_ID +".HTI_CONNECTION_ERROR_MESSAGE"; //$NON-NLS-1$
    
}
