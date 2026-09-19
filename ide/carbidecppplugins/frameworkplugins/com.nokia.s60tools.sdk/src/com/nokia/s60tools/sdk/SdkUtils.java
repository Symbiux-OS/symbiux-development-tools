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


package com.nokia.s60tools.sdk;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Utility class for getting SDK/Platform related information.
 */
public class SdkUtils {
	
	/**
	 * Platform folders that are ignored when map folders are searched.
	 * Win* folders are ignored if excludeWinFolders is true.
	 */
	private static final String[] IGNORED_PLATFORM_FOLDERS = {
		"tools",//$NON-NLS-1$
		"thumb"}; //$NON-NLS-1$
	
	/**
	 * Folders that are ignore when getting build types.
	 */
	private static final String[] IGNORED_BUILD_FOLDERS = {
		"lib"}; //$NON-NLS-1$
	
	/**
	 * This class is not meant to be instantiated.
	 */
	private SdkUtils() {
	}
	
	/**
	 * Method goes through all SDKs and searches for folders which can contain .map files. It will collect
	 * all folders in to a HashMap. Tools and thumb folders are ignored. Also win* folders
	 * are ignored if ecludeWinFolders is true.
	 * 
	 * HashMap contains e.g.:
	 * [S60_32 armv5 UREL][C:\Build_C\S60_3_2_200820\epoc32\release\armv5\UREL]
	 * [S60_32 arm9e udeb][C:\Build_C\S60_3_2_200820\epoc32\release\arm9e\UDEB]
	 *
	 * @param excludeWinFolders defines whether wins, winscw folders are excluded from the result
	 * @return HashMap containing sdk names and their map folders
	 */
	public static HashMap<String, String> getSdkMapFileFolders(boolean excludeWinFolders) {
		return getSdkMapFileFolders(excludeWinFolders, false);
	}
	
	/**
	 * Method goes through all SDKs and searches for folders which can contain .map files. It will collect
	 * all folders in to a HashMap. Tools and thumb folders are ignored. Also win* folders
	 * are ignored if ecludeWinFolders is true.
	 * 
	 * HashMap contains e.g.:
	 * [S60_32 armv5 UREL][C:\Build_C\S60_3_2_200820\epoc32\release\armv5\UREL]
	 * [S60_32 arm9e udeb][C:\Build_C\S60_3_2_200820\epoc32\release\arm9e\UDEB]
	 * 
	 * if epocrootPath parameter is true, then HashMap contains e.g.:
	 * [S60_32 armv5 UREL][Z:\]
	 * [S60_32 arm9e udeb][Z:\]
	 *
	 * @param excludeWinFolders defines whether wins, winscw folders are excluded from the result
	 * @param epocrootPath defines whether folders should be epocroot folders instead of map files folders
	 * @return HashMap containing sdk names and their map folders
	 */
	public static HashMap<String, String> getSdkMapFileFolders(boolean excludeWinFolders, boolean epocrootPath) {
		try {
			HashMap<String, String> folders = new HashMap<String, String>();
		
			// get all SDKs
			SdkInformation[] sdks = SdkManager.getSdkInformation();
			// if SDKs exists
			if (sdks != null && sdks.length > 0) {
				// go through all SDKs
				for (int i = 0; i < sdks.length; i++) {
					SdkInformation sdk = sdks[i];
					// if SDK's epocroot does not exist, ignore SDK
					if (!sdk.epocRootExists())
						continue;
					// get all SDK's platforms
					String[] platforms = sdk.getPlatforms();
					// if platforms exist
					if (platforms != null && platforms.length > 0) {
						// go through all platforms (armv5, gcce, winscw, ...)
						for (int j = 0; j < platforms.length; j++) {
							String platform = platforms[j];
							boolean isWinFolder = platform.toLowerCase().startsWith("win");
							
							// ignore emulator platforms
							if (isWinFolder) {
								if (excludeWinFolders) {
									continue;
								}
								// Win folder is accepted, not checking if it is in predefined platforms.
							}
							// ignoring platforms which shouldn't contain map files.
							else if (isIgnoredPlatformFolder(platform)) {
								continue;
							}
							
							// get platform's build types (urel, udeb)
							String[] buildTypes = sdk.getBuildTypesForPlatform(platform);
							// if build types exist
							if (buildTypes != null && buildTypes.length > 0) {
								// go through all build types
								for (int k = 0; k < buildTypes.length; k++) {
									String buildType = buildTypes[k];
									// Check if is not valid build type.
									if(isIgnoredBuildType(buildType)) {
										continue;
									}
									String mapFolder = sdk.getReleaseRootDir() + File.separator;
									mapFolder += platform + File.separator + buildType;
									
									String sdkFormat = sdk.getSdkId() + " " + platform + " " + buildType;
									if (epocrootPath)
										folders.put(sdkFormat, sdk.getEpocRootDir());
									else
										folders.put(sdkFormat, mapFolder);
								}
							}
						}
					}
				}
			}
			if (folders.size() > 0)
				return folders;
		} catch (Exception e) {
		}

		return null;
	}
	
	/**
	 * Checks whether given type is ignored build type
	 * @param buildType type to be checked
	 * @return true if build type is ignored, false if not
	 */
	private static boolean isIgnoredBuildType(String buildType) {
		for(String ignoredFolder : IGNORED_BUILD_FOLDERS) {
			if(ignoredFolder.equalsIgnoreCase(buildType)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * checks whether given platform should be ignored
	 * @param platform platform to be checked
	 * @return true if platform should be ignored files, false if not
	 */
	private static boolean isIgnoredPlatformFolder(String platform) {
		for(String mapFilePlatform : IGNORED_PLATFORM_FOLDERS) {
			if(platform.toLowerCase().startsWith(mapFilePlatform.toLowerCase())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether given folder contains any .map files.
	 * This method takes can time, if there are many files in the folder.
	 * @param folder folder to be checked for .map files
	 * @return true if folder contains .map files, false if not
	 */
	public static boolean folderContainsMapFiles(String folder) {
		File path = new File(folder);
		if (path.isDirectory() && path.exists()) {

			String[] files = path.list();
			if (files != null && files.length > 0) {
				for (int i = 0; i < files.length; i++) {
					if (files[i].endsWith(".map"))
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Checks whether given zip file contains any .map files
	 * @param zipFilePath zip file's path
	 * @return true if zip file contains .map files, false if not
	 */
	public static boolean zipContainsMapFiles(String zipFilePath) {
		try {
			FileInputStream fis = new FileInputStream(zipFilePath);
			ZipInputStream zis = new ZipInputStream(fis);
			ZipEntry ze;
			while((ze=zis.getNextEntry())!=null){
				if (ze.getName().toLowerCase().endsWith("map")) { //$NON-NLS-1$
					zis.closeEntry();
					zis.close();
					return true;
				}
				zis.closeEntry();
			}
			zis.close();
		} catch (Exception e) {
		}
		
		return false;
	}	
}
