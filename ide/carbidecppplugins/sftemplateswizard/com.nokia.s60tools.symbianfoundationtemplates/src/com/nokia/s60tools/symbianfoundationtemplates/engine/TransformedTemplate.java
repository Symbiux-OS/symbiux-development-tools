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
package com.nokia.s60tools.symbianfoundationtemplates.engine;

import org.eclipse.core.resources.IFile;

/**
 * Wrapper class which contains the transformed template as a string
 * as well as the file name of the transformed template.
 *
 */
public class TransformedTemplate {
	private IFile file;
	
	private String transformed;
	
	/**
	 * Default constructor.
	 * 
	 * @param file the file
	 * @param transformed the transformed string
	 */
	public TransformedTemplate(IFile file, String transformed) {
		this.file = file;
		this.transformed = transformed;
	}
	
	/**
	 * Get the file.
	 * 
	 * @return the file
	 */
	public IFile getFile() {
		return file;
	}
	
	/**
	 * Get the transformed string.
	 * 
	 * @return the transformed string
	 */
	public String getTransformed() {
		return transformed;
	}
}
