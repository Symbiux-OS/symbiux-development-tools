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

package com.nokia.s60tools.imaker;

/**
 * IDs for context sensitive help.
 * @see contexts.xml -file IDs links to <code> <context id="<ID>"> </code>
 */
public class ImageFlasherHelpContextIDs {

	/**
     * The plug-in ID. Copy from ImageFlasherHelpActivator.PLUGIN_ID
     * to here to avoid runtime dependency to help project 
     */	 
    private static final String IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID = 
                                        "com.nokia.s60tools.imaker.doc.user";
	
    public static final String IMAKERDIALOG_CONFIGURATION = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERDIALOG_CONFIGURATION";

    public static final String IMAKERPLUGIN_HELP_PRODUCT = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_PRODUCT";
    

    public static final String IMAKERPLUGIN_HELP_TARGETS = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_TARGETS";
    
    public static final String IMAKERPLUGIN_HELP_SELECTED_TARGETS = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_SELECTED_TARGETS";
    
    public static final String IMAKERPLUGIN_HELP_IMAGE_TYPE = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_IMAGE_TYPE";
    
    public static final String IMAKERPLUGIN_HELP_FLAGS = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_FLAGS";
    
    public static final String IMAKERPLUGIN_HELP_ADDITIONAL_PARAMS = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_ADDITIONAL_PARAMS";
    
    public static final String IMAKERPLUGIN_HELP_PREFERENCES_MANAGEMENT = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP_PREFERENCES_MANAGEMENT";
    
    public static final String IMAKERPLUGIN_HELP = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID +".IMAKERPLUGIN_HELP";

    public static final String IMAKER_DEBUGTAB_TABLE = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID + ".IMAKER_DEBUGTAB_TABLE";

    public static final String IMAKERDIALOG_CONTENTTAB = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID + ".IMAKERDIALOG_CONTENTTAB";
    
    public static final String IMAKERDIALOG_SETTINGSTAB = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID + ".IMAKERDIALOG_SETTINGSTAB";
    
    public static final String IMAKERDIALOG_PLATSIMTAB = 
    	IMAKERPLUGIN_HELP_PROJECT_PLUGIN_ID + ".IMAKERDIALOG_PLATSIMTAB";
    
}
