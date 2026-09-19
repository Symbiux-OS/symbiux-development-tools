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

package com.nokia.s60tools.hticonnection.common;


/**
 * This class stores product information such as product name, 
 * version, console view name etc.  
 * The idea is to have the product information in one place.
 */
public class ProductInfoRegistry {

	private static final String PRODUCT_NAME = Product.getString("ProductInfoRegistry.ProductName"); //$NON-NLS-1$
	private static final String CONSOLE_WINDOW_NAME = PRODUCT_NAME + " " + Product.getString("ProductInfoRegistry.Console");	 //$NON-NLS-1$ //$NON-NLS-2$
	private static final String IMAGES_DIRECTORY = Product.getString("ProductInfoRegistry.IconsDirectory");	 //$NON-NLS-1$
	private static final String BINARIES_RELATIVE_PATH = Product.getString("ProductInfoRegistry.BinariesRelativePath");	 //$NON-NLS-1$
	private static final String DATAGATEWAY_EXE_NAME = Product.getString("ProductInfoRegistry.DatagatewayExeName");	 //$NON-NLS-1$
	
	/**
	 * Returns name that is used in console window.
	 * @return Returns the CONSOLE_WINDOW_NAME.
	 */
	public static String getConsoleWindowName() {
		return CONSOLE_WINDOW_NAME;
	}
	/**
	 * Returns name of the product.
	 * @return Returns the PRODUCT_NAME.
	 */
	public static String getProductName() {
		return PRODUCT_NAME;
	}
	/**
	 * Returns image directory name.
	 * @return Returns the IMAGES_DIRECTORY.
	 */
	public static String getImagesDirectoryName() {
		return IMAGES_DIRECTORY;
	}
	
	/**
	 * Returns path to binaries.
	 * @return Returns the BINARIES_RELATIVE_PATH.
	 */
	public static String getWin32BinariesRelativePath() {
		return BINARIES_RELATIVE_PATH;
	}
	
	/**
	 * Returns name of datagateway executable.
	 * @return Returns the DATAGATEWAY_EXE_NAME.
	 */
	public static String getDatagatewayExeName() {
		return DATAGATEWAY_EXE_NAME;
	}
}
