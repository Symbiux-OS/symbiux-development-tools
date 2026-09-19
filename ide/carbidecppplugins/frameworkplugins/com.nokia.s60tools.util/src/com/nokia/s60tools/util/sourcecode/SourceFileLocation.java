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

/**
 * Class for holding source file information.
 */
public class SourceFileLocation {
	
	/**
	 * Folder name constant for release directory
	 */
	public static final String RELEASE_FOLDER_NAME = "RELEASE"; //$NON-NLS-1$
	
	/**
	 * Folder name constant for epoc32
	 */
	public static final String EPOC32_FOLDER_NAME = "epoc32"; //$NON-NLS-1$
	
	/**
	 * Value for method offset in source file if offset was not found or is not set.
	 */
	public static final int OFFSET_NOT_FOUND = -1;

	/**
	 * Ordinal
	 */
	private String ordinal = null;
	
	/**
	 * Name of the method
	 */
	private String methodName = null;
	
	/**
	 * Source file location
	 */
	private String sourceFileLocation = null;
	
	/**
	 * Name of the DLL where method is exported
	 */
	private String dllName = null;
	
	/**
	 * Name of the object file, where method is exported
	 */
	private String objectName = null;
	
	/**
	 * Method address
	 */
	private String methodAddress = null;
	
	/**
	 * Method line offset. 
	 */
	private int methodLineOffset = OFFSET_NOT_FOUND;


	/**
	 * Gets method ordinal
	 * @return the ordinal
	 */
	public String getOrdinal() {
		return ordinal;
	}

	/**
	 * Sets method ordinal
	 * @param ordinal the ordinal to set
	 */
	public void setOrdinal(String ordinal) {
		this.ordinal = ordinal;
	}

	/**
	 * Gets method name
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}

	/**
	 * Sets method name
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	/**
	 * Gets source file location
	 * @return the sourceFileLocation in absolute path.
	 */
	public String getSourceFileLocation() {
		return sourceFileLocation;
	}

	/**
	 * Sets source file location
	 * @param sourceFileLocation in absolute format.
	 */
	public void setSourceFileLocation(String sourceFileLocation) {
		this.sourceFileLocation = sourceFileLocation;
	}

	/**
	 * Gets DLL name
	 * @return the dllName
	 */
	public String getDllName() {
		return dllName;
	}

	/**
	 * Sets DLL name
	 * @param dllName the dllName to set
	 */
	public void setDllName(String dllName) {
		this.dllName = dllName;
	}

	/**
	 * Gets object name
	 * @return the objectName
	 */
	public String getObjectName() {
		return objectName;
	}

	/**
	 * Sets object name
	 * @param objectName the objectName to set
	 */
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	/**
	 * Sets the address of the method
	 * @param methodAddress
	 */
	public void setMethodAddress(String methodAddress) {
		this.methodAddress  = methodAddress;
	}
	/**
	 * Get method address
	 * @return the address of the method
	 */
	public String getMethodAddress() {
		return methodAddress;
	}

	/**
	 * Gets method offset
	 * @return the methodLineOffset or 0 if not known or set.
	 */
	public int getMethodOffset() {
		return methodLineOffset;
	}

	/**
	 * Sets method offset
	 * @param methodLineOffset the methodLineOffset to set
	 */
	public void setMethodOffset(int methodLineOffset) {
		this.methodLineOffset = methodLineOffset;
	}
}
