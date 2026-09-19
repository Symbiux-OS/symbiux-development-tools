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

package com.nokia.s60tools.remotecontrol.common;

/**
 * This class stores product information such as product name, 
 * version, console view name etc.  
 * The idea is to have the product information in one place.
 */
public class ProductInfoRegistry {

	/**
	 * Name of the product
	 */
	private static final String PRODUCT_NAME = Product.getString("ProductInfoRegistry.ProductName"); //$NON-NLS-1$
	
	/**
	 * Name of the console of this product
	 */
	private static final String CONSOLE_WINDOW_NAME = PRODUCT_NAME + " " + Product.getString("ProductInfoRegistry.Console");	 //$NON-NLS-1$ //$NON-NLS-2$
	
	/**
	 * Images directory
	 */
	private static final String IMAGES_DIRECTORY = Product.getString("ProductInfoRegistry.IconsDirectory");	 //$NON-NLS-1$
	
	/**
	 * Get console window name
	 * @return Returns the CONSOLE_WINDOW_NAME.
	 */
	public static String getConsoleWindowName() {
		return CONSOLE_WINDOW_NAME;
	}
	/**
	 * Get product name
	 * @return Returns the PRODUCT_NAME.
	 */
	public static String getProductName() {
		return PRODUCT_NAME;
	}
	/**
	 * Get images directory
	 * @return Returns the IMAGES_DIRECTORY.
	 */
	public static String getImagesDirectoryName() {
		return IMAGES_DIRECTORY;
	}
}
