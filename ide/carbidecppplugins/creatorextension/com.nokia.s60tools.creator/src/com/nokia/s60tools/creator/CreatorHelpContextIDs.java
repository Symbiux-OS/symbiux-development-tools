/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.creator;


/**
 * IDs for context sensitive help.
 * @see contexts.xml -file IDs links to <code> <context id="<ID>"> </code>
 */
public class CreatorHelpContextIDs {

	/**
	 * The plug-in ID. Copy from CreatorActivator.PLUGIN_ID
	 * to here to avoid runtime dependency to help project 
	 */	 
	private static final String CREATOR_HELP_PROJECT_PLUGIN_ID = "com.nokia.s60tools.creator.help";
	
	
	/**
	 * ID to Metadata Editor Help TOC
	 */
    public static final String CREATOR_HELP_TOC = 
  		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_TOC";
    
    /**
     * ID to Editing a component
     */
    public static final String CREATOR_HELP_MODIFY_COMPONENT = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_MODIFY_COMPONENT";

    /**
     * ID to Adding and component
     */
    public static final String CREATOR_HELP_ADD_COMPONENT = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_ADD_COMPONENT";

    
    /**
     * ID to Contacts
     */
    public static final String CREATOR_HELP_CONTACTS = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_CONTACTS";

    
    /**
     * ID to Contact set
     */
    public static final String CREATOR_HELP_CONTACT_SET = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_CONTACT_SET";
    
    /**
     * ID to Calendar
     */
    public static final String CREATOR_HELP_CALENDAR = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_CALENDAR";
    
    /**
     * ID to Messages
     */
    public static final String CREATOR_HELP_MESSAGES = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_MESSAGES";
    
    /**
     * ID to generic component
     */
    public static final String CREATOR_HELP_GENERIC_COMPONENT = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_GENERIC_COMPONENT";    
    
    /**
     * ID to create new script (With wizard)
     */
    public static final String CREATOR_HELP_CREATE_SCRIPT = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_CREATE_SCRIPT";      

    /**
     * ID to random values
     */
    public static final String CREATOR_HELP_RANDOM_VALUES = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_RANDOM_VALUES";     
    
    /**
     * ID to components
     */
    public static final String CREATOR_HELP_COMPONENTS = 
		  CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_COMPONENTS";


    /**
     * ID to preference page
     */
	public static final String PREF_PAGE = CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_PREFERENCES";    

    /**
     * ID to run in device
     */
	public static final String RUN_IN_DEVICE_PAGE = CREATOR_HELP_PROJECT_PLUGIN_ID +".CREATOR_HELP_RUN_IN_DEVICE";    
	
}
