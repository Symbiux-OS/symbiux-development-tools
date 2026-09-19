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
 
package com.nokia.s60tools.creator.components;

import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * Component value representing unknown component value in {@link UnknownCompoment} at XML Script.
 * E.g. In XML, there was component, added by user manually (without Creator Script Editor)
 * that is not supported with Creator Script Editor. 
 * 
 * This component type value exist, because of error messages can be then full filled with detailed information. 
 */
public class UnknownValue extends AbstractValue {
	
	/**
	 * Default construction
	 */
	public UnknownValue(){
		super(CreatorEditorSettings.TYPE_UNKNOWN);
	}

}
