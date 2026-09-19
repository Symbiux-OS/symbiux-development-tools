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
package com.nokia.s60tools.symbianfoundationtemplates.engine.s60;

import java.util.ResourceBundle;

/**
 * S60 transform keys provider.
 *
 */
public class S60TransformKeys {
	private static final String BUNDLE_NAME = "com.nokia.s60tools.symbianfoundationtemplates.engine.s60.s60transformkeys";
	
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	
	private S60TransformKeys() {
	}
	
	public static String getString(String key) {
		return RESOURCE_BUNDLE.getString(key);
	}
}
