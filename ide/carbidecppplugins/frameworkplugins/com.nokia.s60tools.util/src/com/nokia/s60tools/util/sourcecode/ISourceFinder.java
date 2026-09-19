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
public interface ISourceFinder {
	
	/**
	 * Finds source file location by ordinal and method name
	 * @param ordinal Function ordinal.
	 * @param methodName Method name.
	 * @param componentName Component name.
	 * @param variant Build variant.
	 * @param build	Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @throws CannotFoundFileException if something unexpected occurs
	 * @return Information about wanted source file
	 */
	public SourceFileLocation findSourceFile(String ordinal, String methodName,
			String componentName, String variant, String build, String epocRootPath) throws CannotFoundFileException; 
	/**
	 * Finds source file location by ordinal
	 * @param ordinal Function ordinal.
	 * @param componentName Component name.
	 * @param variant Build variant.
	 * @param build	Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @throws CannotFoundFileException if something unexpected occurs
	 * @return Information about wanted source file
	 */
	public SourceFileLocation findSourceFile(String ordinal, String componentName,
			String variant, String build, String epocRootPath) throws CannotFoundFileException;
	
	/**
	 * Finds source file location by ordinal
	 * @param ordinal Function ordinal.
	 * @param methodName Method name.
	 * @param componentName Component name.
	 * @param epocRootPath EPOCROOT path.
	 * @param pathsToSeekMapFile Collection of path names to seek map files from.
	 * @throws CannotFoundFileException if something unexpected occurs
	 * @return Information about wanted source file
	 */
	public SourceFileLocation findSourceFile(String ordinal, String methodName,
			String componentName, String epocRootPath, Collection<String> pathsToSeekMapFile) throws CannotFoundFileException; 
	/**
	 * Finds source file location under SDK by method name.
	 * @param methodName Method name.
	 * @param componentName Component name.
	 * @param variant Build variant.
	 * @param build	Build type (urel/udeb).
	 * @param epocRootPath EPOCROOT path.
	 * @throws CannotFoundFileException if something unexpected occurs
	 * @return Information about wanted source file
	 */
	public SourceFileLocation findSourceFileByMethodName(String methodName,
			String componentName, String variant, String build, String epocRootPath) throws CannotFoundFileException; 	

	
	/**
	 * Found source file location by method name. To seek <code>.map</code> file
	 * given paths is used to find correct one. <code>.map</code> file
	 * named <code>[componentName].map</code> is seeked.  
	 * @param methodName Method name.
	 * @param componentName Component name.
	 * @param epocRootPath EPOCROOT path.
	 * @param pathsToSeekMapFile Collection of path names to seek map files from.
	 * @return Information about wanted source file
	 * @throws CannotFoundFileException
	 */
	public SourceFileLocation findSourceFileByMethodName(String methodName,
			String componentName, String epocRootPath, Collection<String> pathsToSeekMapFile) throws CannotFoundFileException; 	
	
}
