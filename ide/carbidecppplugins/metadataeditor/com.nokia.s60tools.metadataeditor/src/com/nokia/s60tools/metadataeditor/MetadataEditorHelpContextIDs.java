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

 
 
package com.nokia.s60tools.metadataeditor;


/**
 * IDs for context sensitive help.
 * @see contexts.xml -file IDs links to <code> <context id="<ID>"> </code>
 */
public class MetadataEditorHelpContextIDs {

	/**
	 * The plug-in ID. Copy from MetadataEditorHelpActivator.PLUGIN_ID
	 * to here to avoid runtime dependency to help project 
	 */	 
	private static final String METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID = "com.nokia.s60tools.metadataeditor.help";
	
	
	/**
	 * ID to Metadata Editor Help TOC
	 */
    public static final String METADATA_EDITOR_HELP_TOC = 
  		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_TOC";
    
    /**
     * ID to Editing and saving metadata file
     */
    public static final String METADATA_EDITOR_HELP_EDIT = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_EDIT";

    /**
     * ID to Editing and saving metadata file # Edit Extended sdk
     */    
    public static final String METADATA_EDITOR_HELP_EDIT_EXTENDED_SDK = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_EDIT_EXTENDED_SDK";
    /**
     * ID to Editing and saving metadata file # Edit Attributes
     */    
    public static final String METADATA_EDITOR_HELP_EDIT_ATTRIBS = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_EDIT_ATTRIBS";

    /**
     * ID to Editing and saving metadata file # Edit Libs
     */        
    public static final String METADATA_EDITOR_HELP_EDIT_LIBS = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_EDIT_LIBS";

    /**
     * ID to Editing and saving metadata file # Edit Release
     */        
    public static final String METADATA_EDITOR_HELP_EDIT_RELEASE = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_EDIT_RELEASE";
    
    /**
     * ID to Creating a new metadata file
     */        
    public static final String METADATA_EDITOR_HELP_CREATE = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_CREATE";
    
    /**
     * ID to Creating a new project and link to a filesystem
     */        
    public static final String METADATA_EDITOR_HELP_NEW_PROJECT = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_NEW_PROJECT";
    
    /**
     * ID to Import the project
     */        
    public static final String METADATA_EDITOR_HELP_IMPORT_PROJECT = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_IMPORT_PROJECT";
        

    /**
     * ID to convert to v. 2.0
     */        
    public static final String METADATA_EDITOR_HELP_CONVERT_FILES = 
		  METADATA_EDITOR_HELP_PROJECT_PLUGIN_ID +".METADATA_EDITOR_HELP_CONVERT_FILES";
    
}
