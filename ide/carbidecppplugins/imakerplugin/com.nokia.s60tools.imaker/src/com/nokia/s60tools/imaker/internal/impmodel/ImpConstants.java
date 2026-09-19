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

package com.nokia.s60tools.imaker.internal.impmodel;

public class ImpConstants {
	public static final String FILE_ENCODING      = "UTF-8";
	public static final String DEFINE_END         = "endef";

	public static final String COMMENT_START      = "#";
	public static final String VARIABLE_SEPARATOR = "=";

	public static final String ENTRY_SEPARATOR    = " ";
	
	
	public static final String ORIDEFILES_START   = "define IMAGE_ORIDEFILES";
	public static final String ORIDECONF_START    = "define IMAGE_ORIDECONF";

	public static final String LINE_SPLITTER      = "\\";
	
	
	//basic variable names
	public static final String TARGET_PRODUCT      = "TARGET_PRODUCT";
	public static final String DEFAULT_GOALS       = "DEFAULT_GOALS";
	public static final String TYPE                = "TYPE";
	public static final String VERBOSE             = "VERBOSE";
	public static final String BLDROPT             = "BLDROPT";
	public static final String USE_SYMGEN          = "USE_SYMGEN";
	
	//platsim variables
	public static final String PLATSIM_RUN           = "RUN_PLATSIM";
	public static final String PLATSIM_INSTANCE      = "PLATSIM_INSTANCE";	
}
