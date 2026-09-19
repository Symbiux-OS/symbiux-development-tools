/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.creator.util;

import org.eclipse.jface.resource.ImageDescriptor;

import com.nokia.s60tools.creator.CreatorActivator;
import com.nokia.s60tools.creator.common.ProductInfoRegistry;
import com.nokia.s60tools.util.console.AbstractProductSpecificConsole;

/**
 * Singleton class that offers console printing
 * services for the AppDep product.
 */
public class CreatorEditorConsole extends AbstractProductSpecificConsole {
	
	
	/**
	 * Singleton instance of the class.
	 */
	static private CreatorEditorConsole instance = null;
	
	/**
	 * Public accessor method.
	 * @return Singleton instance of the class.
	 */
	static public CreatorEditorConsole getInstance(){
		if(instance == null ){
			instance = new CreatorEditorConsole();
		}
		return instance;
	}
	
	/**
	 * Private constructor forcing Singleton usage of the class.
	 */
	private CreatorEditorConsole(){		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.AbstractProductSpecificConsole#getProductConsoleName()
	 */
	protected String getProductConsoleName() {
		return ProductInfoRegistry.getConsoleWindowName();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.AbstractProductSpecificConsole#getProductConsoleImageDescriptor()
	 */
	protected ImageDescriptor getProductConsoleImageDescriptor() {
		return CreatorActivator.getImageDescriptorForKey(CreatorActivator.CREATOR_SCRIPT_EDITOR_ICON);
	}	
}
