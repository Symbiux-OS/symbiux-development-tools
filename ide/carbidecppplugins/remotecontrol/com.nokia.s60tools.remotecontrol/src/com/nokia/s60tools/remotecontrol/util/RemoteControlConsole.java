/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.remotecontrol.util;

import org.eclipse.jface.resource.ImageDescriptor;

import com.nokia.s60tools.remotecontrol.common.ProductInfoRegistry;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.util.console.AbstractProductSpecificConsole;

/**
 * Singleton class that offers console printing
 * services for the AppDep product.
 */
public class RemoteControlConsole extends AbstractProductSpecificConsole {
	
	/**
	 * Singleton instance of the class.
	 */
	static private RemoteControlConsole instance = null;
	
	/**
	 * Public accessor method.
	 * @return Singleton instance of the class.
	 */
	static public RemoteControlConsole getInstance(){
		if(instance == null ){
			instance = new RemoteControlConsole();
		}
		return instance;
	}
	
	/**
	 * Private constructor forcing Singleton usage of the class.
	 */
	private RemoteControlConsole(){		
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
		return ImageResourceManager.getImageDescriptor(ImageKeys.IMG_APP_ICON);
	}
}
