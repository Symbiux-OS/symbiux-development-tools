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
 
 
package com.nokia.s60tools.sdk;

/**
 * Stores information about single RVCT
 * (=RealView Compiler Tools) toolchain 
 * installation
 */
public class RVCTToolChainInfo {
	
	/**
	 * Toolchain installation directory location.
	 */
	private String rvctToolBinariesDirectory = null;
	
	/**
	 * Toolchain version.
	 */
	private String rvctToolsVersion = null;
	
	/**
	 * Default constructor.
	 * Cannot be constructed outside of this package.
	 */
	RVCTToolChainInfo(){
		rvctToolBinariesDirectory = new String(""); //$NON-NLS-1$
		rvctToolsVersion = new String("");		 //$NON-NLS-1$
	}

	/**
	 * Gets RVCT toolchain installation directory.
	 * @return Returns the rvctToolBinariesDirectory.
	 */
	public String getRvctToolBinariesDirectory() {
		return rvctToolBinariesDirectory;
	}

	/**
	 * Sets RVCT toolchain installation directory. 
	 * Setters cannot be used outside this package.
	 * @param rvctToolBinariesDirectory The rvctToolBinariesDirectory to set.
	 */
	void setRvctToolBinariesDirectory(String rvctToolBinariesDirectory) {
		this.rvctToolBinariesDirectory = rvctToolBinariesDirectory;
	}

	/**
	 * Gets RVCT toolchain version.
	 * @return Returns the rvctToolsVersion.
	 */
	public String getRvctToolsVersion() {
		return rvctToolsVersion;
	}

	/**
	 * Sets RVCT toolchain version.
	 * Setters cannot be used outside this package.
	 * @param rvctToolsVersion The rvctToolsVersion to set.
	 */
	void setRvctToolsVersion(String rvctToolsVersion) {
		this.rvctToolsVersion = rvctToolsVersion;
	}
}
