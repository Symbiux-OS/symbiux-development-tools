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

import java.io.File;
import java.util.ArrayList;


/**
 * Stores SDK/Platform related information.
 */
public class SdkInformation {
	
	
	/**
	 * SDK/Platform identifier,
	 */
	private String sdkId = null;
	
	/**
	 * Name of the SDK/Platform.
	 */
	private String sdkName = null;
	
	/**
	 * Epoc root directory i.e. SDK/Platform installation directory
	 * location (absolute path).
	 */
	private String epocRootDir = null;
	
	/**
	 * Tools directory location under epoc32 directory (absolute path).
	 */
	private String epoc32ToolsDir = null;
	
	/**
	 * Release directory location under epoc32 directory (absolute path).
	 */
	private String releaseRootDir = null;
	
	/**
	 * Default construtor.
	 * Cannot be constructed outside of this package.
	 */
	SdkInformation(){
		sdkId = new String(""); //$NON-NLS-1$
		sdkName = new String(""); //$NON-NLS-1$
		epocRootDir = new String(""); //$NON-NLS-1$
		epoc32ToolsDir = new String(""); //$NON-NLS-1$
		releaseRootDir = new String(""); //$NON-NLS-1$
	}

	/**
	 * Gets epoc root directory. 
	 * @return Returns the epocRootDir.
	 */
	public String getEpocRootDir() {
		return epocRootDir;
	}

	/**
	 * Sets epoc root directory.
	 * Setters cannot be used outside this package.
	 * @param epocRoot The epocRootDir to set.
	 */
	void setEpocRootDir(String epocRoot) {
		this.epocRootDir = epocRoot;
	}

	/**
	 * Gets the name of the SDK/Platform.
	 * @return Returns the sdkName.
	 */
	public String getSdkName() {
		return sdkName;
	}

	/**
	 * Sets the name of the SDK/Platform.
	 * Setters cannot be used outside this package.
	 * @param sdkName The sdkName to set.
	 */
	void setSdkName(String sdkName) {
		this.sdkName = sdkName;
	}

	/**
	 * Gets SDK/Platform identifier.
	 * @return Returns the sdkId.
	 */
	public String getSdkId() {
		return sdkId;
	}

	/**
	 * Sets SDK/Platform identifier.
	 * Setters cannot be used outside this package.
	 * @param sdkId The sdkId to set.
	 */
	void setSdkId(String sdkId) {
		this.sdkId = sdkId;
	}

	/**
	 * Gets epoc32 tools directory.
	 * @return Returns the epoc32ToolsDir.
	 */
	public String getEpoc32ToolsDir() {
		return epoc32ToolsDir;
	}

	/**
	 * Sets epoc32 tools directory.
	 * Setters cannot be used outside this package.
	 * @param epoc32ToolsDir The epoc32ToolsDir to set.
	 */
	void setEpoc32ToolsDir(String epoc32ToolsDir) {
		this.epoc32ToolsDir = epoc32ToolsDir;
	}

	/**
	 * Gets release directory residing under epoc32 directory.
	 * @return Returns the releaseRootDir.
	 */
	public String getReleaseRootDir() {
		return releaseRootDir;
	}

	/**
	 * Sets release root directory.
	 * Setters cannot be used outside this package.
	 * @param releaseRootDir The releaseRootDir to set.
	 */
	void setReleaseRootDir(String releaseRootDir) {
		this.releaseRootDir = releaseRootDir;
	}
	
	/**
	 * Return target platform names that are found under 
	 * release root directory (e.g. armi, thumb, armv5 etc.) for 
	 * this SDK/Platform.
	 * @return String array of target platform names.
	 */
	public String[] getPlatforms(){
		ArrayList<String> platforms = new ArrayList<String>();
		File releaseDir = new File(releaseRootDir);
		if(releaseDir.isDirectory()){
			File[] fileListArr = releaseDir.listFiles();
			for (int i = 0; i < fileListArr.length; i++) {
				File f = fileListArr[i];
				if(f.isDirectory()){
					platforms.add(f.getName());					
				}
				
			}
		}
		return (String[]) platforms.toArray(new String[0]);
	}
	
	/**
	 * Returns build types found for the given target platform. 
	 * @param platformName Name of the platform.
	 * @return String array of build type names.
	 */
	public String[] getBuildTypesForPlatform(String platformName){
		ArrayList<String> builtTypes = new ArrayList<String>();
		File platformDir = new File(releaseRootDir + File.separatorChar + platformName);
		if(platformDir.isDirectory()){
			File[] fileListArr = platformDir.listFiles();
			for (int i = 0; i < fileListArr.length; i++) {
				File f = fileListArr[i];
				if(f.isDirectory()){
					builtTypes.add(f.getName());					
				}
				
			}
		}
		return (String[]) builtTypes.toArray(new String[0]);
	}
	
	/**
	 * Checks if the EPOCROOT directory exists for the SDK.
	 * @return <code>true</code> if EPOCROOT directory exists, otherwise <code>false</code>.
	 */
	public boolean epocRootExists() {
		File epocrootDir = new File(getEpoc32ToolsDir());
		return epocrootDir.exists();
	}
}
