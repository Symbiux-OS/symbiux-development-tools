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
package com.nokia.s60tools.util.sourcecode;

import java.io.IOException;

/**
 * Interface for finding Symbian project files.
 */
public interface IProjectFinder {
	
	/**
	 * Finds MMP file where source file belongs to.
	 * @param sourceFilePath Path for source file.
	 * @return path for MMP file or null if search was canceled.
	 * @throws CannotFoundFileException if an error occurs when searching file.
	 */
	public String findMMPFile(String sourceFilePath) throws CannotFoundFileException;
	
	/**
	 * Finds bld.inf file where .mmp file belongs to.
	 * @param MMPFilePath Path for mmp file.
	 * @return path for bld.inf file or null if search was canceled.
	 * @throws CannotFoundFileException if an error occurs when searching file.
	 */
	public String findBLDINFFile(String MMPFilePath) throws CannotFoundFileException;

	/**
	 * Parses bld.inf file and return how much difference is with root directory
	 * and directory that contains bld.inf file.
	 * @param bldInfFilePath Path to bld.inf file.
	 * @return Count on where root level is from bld.inf file.
	 * @throws IOException if exception occurs when handling the bld.inf file.
	 */
	public int getRootLevelFromBldInf(String bldInfFilePath) throws IOException;
	
}
