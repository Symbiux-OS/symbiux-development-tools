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
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import org.eclipse.cdt.utils.WindowsRegistry;
import org.eclipse.core.runtime.Plugin;

import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.s60tools.sdk.internal.Messages;


/**
 * This class acts as a facade to the services that are 
 * used to query the information related to installed SDKs.
 * It is not possible to create instance of this class
 * because all the SDK information query methods can 
 * be accessed in static fashion as shown in the following 
 * usage example:
 *  
 * <code>
 * <pre>
 * 
 * RVCTToolChainInfo[] rvctTools = SdkManager.getInstalledRVCTTools();
 * 
 * if(rvctTools.length > 0){
 *     for (int i = 0; i < rvctTools.length; i++) {
 *       // Doing the wanted operations on RVCT toolchain information...
 *     }
 * }
 * else{
 *    // No RVCT tools found error handling...
 * }
 * 
 * </pre>
 * </code> 
 * @see SdkInformation
 * @see RVCTToolChainInfo
 */
public class SdkManager extends Plugin {
		
	/**
	 * Search string used to find Sourcery G++ toolchain installation from path variable. 
	 */
	private static final String SOURCERY_TOOLCHAIN_SEARCH_STR = "sourcery"; //$NON-NLS-1$

	/**
	 * Binaries for CSL Arm toolchain and Sourcery G++ are stores in bin sub directory.
	 */
	private static final String BIN_DIR_NAME_FOR_GCC_TOOLS = "bin"; //$NON-NLS-1$

	/**
	 * Separator character in Win32 to separated directory entries in path environement variable.
	 */
	private static final String WIN32_PATH_ENTRY_SEPARATOR_STRING = ";"; //$NON-NLS-1$

	/**
	 * Environment variable name in Win32 to store currently used path. 
	 */
	private static final String WIN32_PATH_ENV_VAR_NAME = "PATH"; //$NON-NLS-1$

	/**
	 * RVCT compiler executable name.
	 */
	private static final String ARMCC_EXE_FILE_NAME = "armcc.exe"; //$NON-NLS-1$

	/**
	 * Registry key location under HKEY_LOCAL_MACHINE for checking CSL Arm Toolchain installation directory. 
	 */
	private static final String CSL_ARM_TOOLCHAIN_REG_PATH="SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\CSL Arm Toolchain (arm-symbianelf)_is1"; //$NON-NLS-1$

	/**
	 * Registry key location under HKEY_LOCAL_MACHINE for checking Sourcery G++ Lite installation directory. 
	 */
	private static final String SOURCERY_GPP_LITE_TOOLCHAIN_REG_PATH="SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Uninstall\\Sourcery G++ Lite for ARM SymbianOS"; //$NON-NLS-1$
	
	/**
	 * Registry key for checking installation directory for a toolchain. 
	 */
	private static final String TOOLCHAIN_INSTALL_LOC_REG_KEY="InstallLocation"; //$NON-NLS-1$
	
	/**
	 * This environment variable points to the RVCT tools
	 * that are used by default in the user's workstation.
	 * List contains all known RVCT tool version environment
	 * variable names. If new variables found, they will be added 
	 * to this list. First item on list is oldest (smallest version number)
	 * and last item on list is the newest one (greatest version number).
	 * 
	 * Currently first one is RVCT22BIN and last RVCT31BIN.
	 * 
	 * Remember to update also RVCT_BIN_ENV_VARIABLE_VERSION_NUMBERS when updated.
	 * 
	 */
	private static final String [] RVCT_BIN_ENV_VARIABLES = {
		"RVCT22BIN",//$NON-NLS-1$
		"RVCT30BIN", //$NON-NLS-1$
		"RVCT31BIN", //$NON-NLS-1$
		"RVCT40BIN"}; //$NON-NLS-1$
	
	/**
	 * Version numbers for RVCT_BIN_ENV_VARIABLES, order and count must mach ones in {@link SdkManager.RVCT_BIN_ENV_VARIABLES}}
	 * 
	 * Remember to update also RVCT_BIN_ENV_VARIABLES when updated.
	 */
	private static final String [] RVCT_BIN_ENV_VARIABLE_VERSION_NUMBERS = {
		"2.2",//$NON-NLS-1$
		"3.0", //$NON-NLS-1$
		"3.1", //$NON-NLS-1$
		"4.0"}; //$NON-NLS-1$
		
	
	/**
	 * The constructor is private for prohibiting
	 * the creation of the class because all the
	 * service method are static ones.
	 */
	private SdkManager() {
	}

	/**
	 * Gets the list of all configured SDKs with the
	 * all the information required by the application.
	 * @return Collection of SdkInformation objects.
	 * @throws SdkEnvInfomationResolveFailureException 
	 */
	public static SdkInformation[] getSdkInformation() throws SdkEnvInfomationResolveFailureException{
		
		Vector<SdkInformation> configuredSdks = new Vector<SdkInformation>();		
				    	
		List<ISymbianSDK> sdkList = null;

		ISDKManager spPlugin = SDKCorePlugin.getSDKManager();
		sdkList = spPlugin.getSDKList();
					
		extractSdkInformation(configuredSdks, sdkList);		
		
		return configuredSdks.toArray(new SdkInformation[0]);
	}

	/**
	 * Extracts per SDK information.
	 * @param configuredSdks Vector to store found SDKs into. 
	 * @param sdkListMap SDK list map to browse for necessary information.
	 */
	private static void extractSdkInformation(Vector<SdkInformation> configuredSdks, List<ISymbianSDK> sdkList) {
		
		SdkInformation sdkInfoReturned = null;	
		//Collection sdkCollection = sdkListMap.values();
		
		for (Iterator<ISymbianSDK> iter = sdkList.iterator(); iter.hasNext();) {			
			// Creating new SDK information object instance
			sdkInfoReturned = new SdkInformation();
			
			// Getting SDK properties
			ISymbianSDK sdk = (ISymbianSDK) iter.next();
			
			// NOTE: ID given in SDK Preferences/devices.xml 
			//       is somehow stored in Label field instead 
			//       of actual ID field, which may contain following
			//       kind of strings based on the source where it is 
			//       got from: 
			//
			//          (none), unsupportedSDK, S60_3rd, ...
			//
			//       i.e. call to sdk.getId() does not return 
			//       valid SDK ID, but sdk.getLabel() does.
			sdkInfoReturned.setSdkId(sdk.getUniqueId());
			
			sdkInfoReturned.setEpocRootDir(sdk.getEPOCROOT());
			sdkInfoReturned.setReleaseRootDir(sdk.getReleaseRoot().toOSString());//Returns IPath
			sdkInfoReturned.setEpoc32ToolsDir(sdk.getToolsPath().toOSString());
						
			// When all required information is successfully gathered,
			// we'll add the information into the vector
			configuredSdks.add(sdkInfoReturned);			
		}
		
	}

	
	/**
	 * Returns toolchain info for all detected RVCT tools.
	 * @return Array of toolchain information objects.
	 */
	public static RVCTToolChainInfo[] getInstalledRVCTTools() {
		
		ArrayList<RVCTToolChainInfo> tools = new ArrayList<RVCTToolChainInfo>();
		
		RVCTToolChainInfo rvctInfo = null;
			//Seek all predefined RVCT tool environment variable names, and if predefined variable was found
			//setting it as default RVCT tool.
			for(int j=0; j<SdkManager.RVCT_BIN_ENV_VARIABLES.length; j++){
				String rvctBinDir = System.getenv(SdkManager.RVCT_BIN_ENV_VARIABLES[j]);
				if(rvctBinExist(rvctBinDir)){
					rvctInfo = new RVCTToolChainInfo();
					rvctInfo.setRvctToolsVersion(RVCT_BIN_ENV_VARIABLE_VERSION_NUMBERS[j]);			
					rvctInfo.setRvctToolBinariesDirectory(rvctBinDir);			
					tools.add(rvctInfo);
				}
			}
		return (RVCTToolChainInfo[])tools.toArray(new RVCTToolChainInfo[0]);
		
	}
	
	
	/**
	 * Check if <code>armcc.exe</code> file exist in directory found
	 * @param rvctBinDir
	 * @return true if <code>armcc.exe</code> exist, false otherwise.
	 */
	private static boolean rvctBinExist(String rvctBinDir) {
		String executablePath = rvctBinDir + File.separatorChar + ARMCC_EXE_FILE_NAME;
		File file = new File(executablePath);
		return file.exists();
	}


	/**
	 * Gets bin-directory path for Sourcery G++/CSL Arm Toolchain.
	 * Sourcery G++ is preferred, and returned as result if found.
	 * Otherwise seeking CSL Arm Toolchain install location.
	 * The method also checks that the all required tools exist.
	 * @return Path to tool binaries under installation path directory, 
	 *         successful. Otherwise throws an exception.
	 * @throws SdkEnvInfomationResolveFailureException 
	 */
	public static String getCSLArmToolchainInstallPathAndCheckReqTools() throws SdkEnvInfomationResolveFailureException{
		
		String installPath = null;
		String gcceToolBinDir = null;
		
		//First checking existence Sourcery G++ toolchain from registry
		try {
			String toolchainRegPath = SOURCERY_GPP_LITE_TOOLCHAIN_REG_PATH;
			String toolchainRegKey = TOOLCHAIN_INSTALL_LOC_REG_KEY;
			installPath = getRegKeyValue(toolchainRegPath, toolchainRegKey);
			checkInstallPathValidity(toolchainRegPath, installPath);
			gcceToolBinDir = installToBinPath(installPath);
			if(checkRequiredBinaries(gcceToolBinDir)){
				return gcceToolBinDir;
			}
		} catch (SdkEnvInfomationResolveFailureException e) {
			// We can ignore this at this phase, because we can continue search 
		}
		
		// Then checking existence Sourcery G++ toolchain from PATH environment variable
		try {
			String searchString = SOURCERY_TOOLCHAIN_SEARCH_STR; 
			String searchRegExp = ".*" + searchString + ".*"; //$NON-NLS-1$ //$NON-NLS-2$
			Pattern searchPattern = Pattern.compile(searchRegExp, Pattern.CASE_INSENSITIVE);
			return searchPathEnvVarForGccBinariesPath(searchPattern);
		} catch (SdkEnvInfomationResolveFailureException e) {
			// We can ignore this at this phase, because we can continue search 
		}

		// Fallback 1: Trying to located older toolchain version
		try {
			gcceToolBinDir = getOlderCSLArmToolchainInstallPathAndCheckReqTools();
			return gcceToolBinDir;
		} catch (SdkEnvInfomationResolveFailureException e) {
			// We can ignore this at this phase, because we can continue search 
		}
		
		// Fallback 2: Going through all directories from PATH variable, and seek for binaries
		String searchRegExp = ".*"; //$NON-NLS-1$
		Pattern searchPattern = Pattern.compile(searchRegExp, Pattern.CASE_INSENSITIVE);
		return searchPathEnvVarForGccBinariesPath(searchPattern);
	}

	/**
	 * Search binaries path based on the given search string and current PATH environment variable.
	 * Throws an exception if something fails or no valid binaries path was found
	 * @param searchPattern Search pattern to be used for matching directories from PATH environment variable.
	 * @return Binaries path containing all required GCC binaries.
	 * @throws SdkEnvInfomationResolveFailureException
	 */
	private static String searchPathEnvVarForGccBinariesPath(Pattern searchPattern) throws SdkEnvInfomationResolveFailureException {
		
		String errMsg = Messages.getString("SdkManager.Toolchain_Not_Found_From_Path_ErrMsg")  //$NON-NLS-1$
						+ " " + searchPattern.pattern(); //$NON-NLS-1$
		try {
			// Parsing PATH environment variable - Win32 dependent environment variable 
			String pathEnvStr = System.getenv(WIN32_PATH_ENV_VAR_NAME); 
			String[] pathDirArr = pathEnvStr.split(Pattern.quote(WIN32_PATH_ENTRY_SEPARATOR_STRING)); 
			for (int i = 0; i < pathDirArr.length; i++) {
				String dir = pathDirArr[i];
				// Does directory name match with search pattern?
				if(searchPattern.matcher(dir).matches()){
					// If matches checking the required tools
					if(checkRequiredBinaries(dir)){
						return dir;
					}					
				}
			}
		} catch (Exception e) {
			// We can ignore exception => all exceptions are mapped into same exception 
			errMsg = errMsg + " (" + e.getMessage() + ")."; //$NON-NLS-1$ //$NON-NLS-2$
		}
		// If could not find anything ending up in here
		throw new SdkEnvInfomationResolveFailureException(errMsg);			
	}

	/**
	 * Gets bin-directory path for CSL Arm Toolchain.
	 * The method also checks that the all required tools exist.
	 * @return Path to tool binaries under installation path directory, 
	 *         successful. Otherwise throws an exception.
	 * @throws SdkEnvInfomationResolveFailureException
	 */
	private static String getOlderCSLArmToolchainInstallPathAndCheckReqTools()
			throws SdkEnvInfomationResolveFailureException {

		String toolchainRegPath = CSL_ARM_TOOLCHAIN_REG_PATH;
		String toolchainRegKey = TOOLCHAIN_INSTALL_LOC_REG_KEY;
		String installPath = getRegKeyValue(toolchainRegPath, toolchainRegKey);		
		checkInstallPathValidity(toolchainRegPath, installPath);
		String gcceToolBinDir = installToBinPath(installPath);
		if(checkRequiredBinaries(gcceToolBinDir)){
			return gcceToolBinDir;
		}
		// Required binaries were not existing
		String errMsg = Messages.getString("SdkManager.Required_Tool_For_CSL_Arm_Missing")  //$NON-NLS-1$
						+ gcceToolBinDir;
		throw new SdkEnvInfomationResolveFailureException(errMsg);
	}

	/**
	 * Checks that given install path pointed by given registry key is valid one.
	 * Throws an exception in case of invalid path.
	 * @param toolchainRegPath Registry path
	 * @param installPath Install path to check.
	 * @throws SdkEnvInfomationResolveFailureException
	 */
	private static void checkInstallPathValidity(String toolchainRegPath,
			String installPath) throws SdkEnvInfomationResolveFailureException {
		if(installPath == null){
			String errMsg = Messages.getString("SdkManager.Local_Machine_Key") +  toolchainRegPath  //$NON-NLS-1$
								+ Messages.getString("SdkManager.Does_Not_Exist_Ending"); //$NON-NLS-1$
			throw new SdkEnvInfomationResolveFailureException(errMsg);			
		}
		
		if (!new File(installPath).exists()){
			String errMsg = Messages.getString("SdkManager.CSL_Arm_Toolchain_InstallPath_Pointed_By_Registry")  //$NON-NLS-1$
							+  toolchainRegPath 
							+ Messages.getString("SdkManager.Does_Not_Exist_Ending"); //$NON-NLS-1$
			throw new SdkEnvInfomationResolveFailureException(errMsg);
		}
	}

	/**
	 * Gets value for given registry key from given registry path.
	 * @param toolchainRegPath Registry path
	 * @param toolchainRegKey  Registry key
	 * @return string value for given registry key
	 * @throws SdkEnvInfomationResolveFailureException
	 */
	private static String getRegKeyValue(String toolchainRegPath,
			String toolchainRegKey)
			throws SdkEnvInfomationResolveFailureException {
		String installPath = null;
		
		try {			
			WindowsRegistry wr = WindowsRegistry.getRegistry();
			installPath = wr.getLocalMachineValue(toolchainRegPath, 
													 toolchainRegKey);			
		} catch (Exception e) {					
			String errMsg = Messages.getString("SdkManager.Could_Not_Read_Registry") +  CSL_ARM_TOOLCHAIN_REG_PATH  //$NON-NLS-1$
								+ " (" + e.getMessage() +")."; //$NON-NLS-1$ //$NON-NLS-2$
			throw new SdkEnvInfomationResolveFailureException(errMsg);
		}
		return installPath;
	}

	/**
	 * Checks that binaries path contains required binaries.
	 * @param binariesPath Suggested installation path for "arm-none-symbianelf-*.exe" binaries.
	 * @return <code>true</code> if all required binaries were found, otherwise <code>false</code>.
	 * @throws SdkEnvInfomationResolveFailureException
	 */
	private static boolean checkRequiredBinaries(String binariesPath)
			throws SdkEnvInfomationResolveFailureException {
		
		String[] gccBinToolList = { "arm-none-symbianelf-nm.exe", //$NON-NLS-1$
									"arm-none-symbianelf-readelf.exe", //$NON-NLS-1$
									"arm-none-symbianelf-c++filt.exe" //$NON-NLS-1$
									};
		String toolName = null;
		String toolPathName = null;
		for (int i = 0; i < gccBinToolList.length; i++) {
			toolName = gccBinToolList[i];
			toolPathName = binariesPath + File.separator + toolName; //$NON-NLS-1$
			
			if (!new File(toolPathName).exists()){
				return false;
			}			
		}
		return true;
	}

	/**
	 * Converts installation path into binaries path.
	 * @param installPath Toolchain installation path
	 * @return Toolchain binaries path.
	 */
	private static String installToBinPath(String installPath) {
		return installPath + File.separator + BIN_DIR_NAME_FOR_GCC_TOOLS; 
	}
	
	/**
	 * Created SDK information object based on the given parameters.
	 * This method is needed only for enabling JUnit tests that simulate
	 * existence of an SDK instead of using installed SDKs.
	 * @param sdkId SDK identifier.
	 * @param sdkName SDK name.
	 * @param epocRootDir SDK's epoc32 directory path.
	 * @param epoc32ToolsDir SDK's epoc32\tools directory path.
	 * @param releaseRootDir SDK's epoc32\release directory path.
	 */
	public static SdkInformation createTestSdkInformationObject(String sdkId, String sdkName, String epocRootDir, String epoc32ToolsDir, String releaseRootDir) {
		
		SdkInformation sdkInfoReturned = new SdkInformation();	
		
		sdkInfoReturned.setSdkId(sdkId);
		sdkInfoReturned.setSdkName(sdkName);
		sdkInfoReturned.setEpocRootDir(epocRootDir);
		sdkInfoReturned.setEpoc32ToolsDir(epoc32ToolsDir);
		sdkInfoReturned.setReleaseRootDir(releaseRootDir);
		
		return sdkInfoReturned;
		
	}
}
