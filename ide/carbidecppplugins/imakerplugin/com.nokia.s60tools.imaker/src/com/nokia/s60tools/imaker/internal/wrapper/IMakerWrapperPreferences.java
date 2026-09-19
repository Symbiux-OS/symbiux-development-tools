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


package com.nokia.s60tools.imaker.internal.wrapper;

/**
 * A simple preferences class for iMaker wrapper.
 *
 */
public class IMakerWrapperPreferences {
	/** Preferences */
	public static final String CMD_FETCH_VERSION                = "";
	public static final String CMD_FETCH_CONFIGURATIONS         = "help-config";  //"help-config CONFIGROOT=";
	public static final String ARG_CONFIGURATIONS_PATH          = "/epoc32/rom/config";
	public static final String CMD_FETCH_TARGETS                = "help-target";
	public static final String CMD_FETCH_CONFIGURATION          = "help-variable-*-all";
	public static final String CMD_FETCH_HWIDS                  = "help-variable-HWID_LIST-value";
	public static final String ARG_FETCH_CONFIGURATION          = "-f";
	public static final String DEFAULT_COMMAND                  = "epoc32\\tools\\imaker.cmd";
	public static final String PRODUCT_NAME                     = "product_name";
	public static final String COREPLATFORM_NAME                = "coreplat_name";
	public static final String UIPLATFORM_NAME                  = "uiplat_name";
	public static final String MAKEFILE_LIST_PATTERN            = "/epoc32/rom/config/.*?/image_conf.*?\\.mk";
	public static final String HWID                             = "hwid";
	public static final String HWID_LIST_PATTERN                = "HWID_LIST = `.*";
	public static final String HWID_DELIMITER                   = " ";
	public static final char   HWID_DEFAULT_MARKER              = '*';
	public static final char   HWID_FIELD_DELIMITER             = ' ';
	public static final char   IMAKER_PARAM_DELIMITER           = ' ';
	public static final char   TARGET_FIELD_DELIMITER           = ';';
	public static final char   TARGET_VALUE_DELIMITER           = '=';
	public static final String TARGET_FIELD_NAME                = "name";
	public static final String TARGET_FIELD_DESCRIPTION         = "description";
	public static final char   CONFIGURATION_FIELD_DELIMITER    = ';';
	public static final char   CONFIGURATION_REF_DELIMITER      = ',';
	public static final char   CONFIGURATION_VALUE_DELIMITER    = '=';
	public static final String CONFIGURATION_FIELD_NAME         = "name";
	public static final String CONFIGURATION_FIELD_VALUE        = "value";
	public static final String CONFIGURATION_FIELD_DESCRIPTION  = "description";
	public static final String CONFIGURATION_FIELD_VALUEFORMAT  = "values";
	public static final String INTERFACE_DEFAULT_NAME           = "An interface";
	public static final char   MAKEFILE_PATH_DELIMITER          = '/';
	public static final char   MAKEFILE_DRIVE_DESIGNATOR        = ':';
	public static final String IMAKER_EXITSHELL 				= "IMAKER_EXITSHELL";
	public static final String DEFAULT_DATA     				= "print-IMAKER_CONFMK,TARGET_DEFAULT";	
	public static final String TYPE     				        = "TYPE";	
	public static final String PLATSIM_STEP 			        = "step-PLATBLD";
	public static final String USE_PLATSIM 			        	= "USE_PLATSIM";
	public static final String TARGET_STEPS 			      	= "print-IMAKER_STEPS";
	public static final String TARGET_STEPS_RESTARTS 	    	= "IMAKER_MKRESTARTS=1";
}
