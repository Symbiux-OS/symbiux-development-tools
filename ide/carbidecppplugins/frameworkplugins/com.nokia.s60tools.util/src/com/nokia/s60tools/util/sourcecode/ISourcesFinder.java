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

package com.nokia.s60tools.util.sourcecode;

import java.util.Collection;

/**
 * Interface for finding source file locations by ordinal and/or method name. 
 */
public interface ISourcesFinder {
	
	/**
	 * Finds all source files belonging to selected DLL.
	 * @param dllName DLL name.
	 * @param variant Build variant.
	 * @param build Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @throws CannotFoundFileException if something unexpected occurs
	 * @return source file names with full paths found belonging to DLL.
	 */
	public String [] findSourceFiles(String dllName,
			String variant, String build, String epocRootPath) throws CannotFoundFileException;
		
	/**
	 * Finds all source files belonging to selected DLL.
	 * @param dllName DLL name. 
	 * @param epocRootPath EPOCROOT path.
	 * @param pathsToSeekMapFile Collection of path names to seek map file from
	 * @return source file names along with paths belonging to given DLL.
	 * @throws CannotFoundFileException if something unexpected occurs.
	 */
	public String[] findSourceFiles(String dllName,
			 String epocRootPath, Collection<String> pathsToSeekMapFile
			) throws CannotFoundFileException;
}
