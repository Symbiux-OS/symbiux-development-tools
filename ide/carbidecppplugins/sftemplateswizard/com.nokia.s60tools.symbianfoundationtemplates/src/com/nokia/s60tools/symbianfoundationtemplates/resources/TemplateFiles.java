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
package com.nokia.s60tools.symbianfoundationtemplates.resources;

import java.util.ResourceBundle;

/**
 * Template files provider.
 *
 */
public class TemplateFiles {
	private static final String BUNDLE_NAME = "com.nokia.s60tools.symbianfoundationtemplates.resources.templatefiles";
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private TemplateFiles() {
	}
	
	public static String getString(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}
}
