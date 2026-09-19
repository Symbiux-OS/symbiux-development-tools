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
 
package com.nokia.s60tools.util.resource;

import java.io.FileFilter;

/**
 * File filter interface for file finder functionality.
 */
public abstract interface IFileFinderFileFilter extends FileFilter {

	/**
	 * Get search item, it can be file type, file name...
	 * Use with messages.
	 * @return search item
	 */
	public abstract String getSearchItem();

}
