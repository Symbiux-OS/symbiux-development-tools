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

package com.nokia.s60tools.imaker;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IPath;

import com.nokia.carbide.cpp.sdk.core.ISDKManager;
import com.nokia.carbide.cpp.sdk.core.ISymbianSDK;
import com.nokia.carbide.cpp.sdk.core.SDKCorePlugin;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapperPreferences;

public class IMakerUtils {
	public static final int NORMAL_MSG = 1;
	public static final int WARNING_MSG = 2;
	public static final int ERROR_MSG = 3;
	
	public static String getLocationWithLeadingSlash(String location) {
		if (location.length()>0) {
			if(location.charAt(0)=='/') {				
				return location.substring(1);
			}
			return location;
		}
		return "";
	}
	
	/**
	 * Returns the drive of the location where is project is located
	 * @param resource
	 * @return
	 */
	public static String getProjectRootLocation(IResource resource) {
		IProject project = resource.getProject();
		String location = project.getLocation().toString();
		return getDrive(location);
	}

	private static String getDrive(String location) {
		if(location==null||location.length()<2) {
			return null;
		}
		return location.substring(0,2);
	}

	public static String getLocationDrive(String location) {	
		return getDrive(location);
	}
	
	public static int getMessageType(String msg) {
		String message = msg.toLowerCase();
		if((message.indexOf("error ") != -1) 
				|| (message.indexOf("error:") != -1)
				|| (message.indexOf("no rule to make target") != -1)) {
			return ERROR_MSG;
		} else if (message.indexOf("warning")!=-1) {
			return WARNING_MSG;
		} else {
			return NORMAL_MSG;
		}		
	}

	/**
	 * @return
	 */
	public static List<String> getAvailableSDKs() {
		List<String> sdks = new ArrayList<String>();
		ISDKManager sdkManager = SDKCorePlugin.getSDKManager();
		List<ISymbianSDK> sdkList = sdkManager.getSDKList();
		for (ISymbianSDK sdk : sdkList) {
			sdks.add(sdk.getEPOCROOT());
		}
		return sdks;
	}

	/**
	 * @param file
	 * @return
	 */
	public static String getFileName(String file) {
		int index = file.lastIndexOf('/');
		if(index != -1) {
			return file.substring(index+1);
		}
		return "";
	}

	/**
	 * Parses the version of imaker from the specified string
	 * @param versionString
	 * @return
	 */
	public static String parseIMakerVersion(String versionString) {
		if(versionString==null||versionString.equals("")) {
			return null;
		}
		String verpattern = "\\d{2}\\.\\d{2}\\.\\d{2}";
		Matcher matcher = Pattern.compile(verpattern).matcher(versionString);
		if(matcher.find()) {
			String version = versionString.substring(matcher.start(), matcher.end());
			return version;
		} else {
			return null;			
		}
	}
	
	/**
	 * determines weather imaker core exists
	 * @return
	 * @throws IMakerCoreNotFoundException
	 */
	public static boolean iMakerCoreExists(String tool) {
		boolean ret = true;
		try {
			File f = new File(tool);
			if(!f.exists()) {
				ret = false;
			}
		} catch(Exception e) {
			return false;
		}
		return ret;
	}
	/**
	 * Converts a list of Variables to a list of Strings 
	 * @param config
	 * @return
	 */
	public static List<String> convertVariablesToStrings(List<UIVariable> variables) {
		if(variables==null) {
			return null;
		}
		List<String> parameters = new ArrayList<String>();
		for (UIVariable variable : variables) {
			if(variable.isModified() || variable.getInclude()) {
				parameters.add(variable.getName()+"="+variable.getValue());
			}
		}
		return parameters;
	}
	
	/**
	 * Converts a list of Strings to a list of Variables 
	 * @param config
	 * @return
	 */
	public static List<UIVariable> convertStringsToVariables(List<String> variables) {
		if(variables==null) {
			return null;
		}
		List<UIVariable> retVars = new ArrayList<UIVariable>();
		for (String variable : variables) {
			String[] parts = variable.split("=");
			retVars.add(new UIVariable(parts[0],parts[1],"","",""));
		}
		return retVars;
	}
	
	/**
	 * Extracts modified variables from a configuration
	 * @param config
	 * @return
	 */
	public static List<UIVariable> getCommandlineIncludeVariables(List<UIVariable> variables) {
		if(variables==null) {
			return null;
		}
		List<UIVariable> parameters = new ArrayList<UIVariable>();

		for (UIVariable variable : variables) {
			if(variable.isModified() || variable.getInclude()) {
				parameters.add(variable);
			}
		}
		return parameters;
	}
	
	public static List<String> getImakerTool(String epocroot) {
		List<String> tool = new ArrayList<String>();
		tool.add(epocroot + "\\" + IMakerWrapperPreferences.DEFAULT_COMMAND);
		return tool;
	}
	
	/**
	 * This method creates ibyfiles and makefile under the given project
	 * @param entries
	 * @param path The location where to put the created files
	 * @return The absolute path of the makefile created, null if nothing gets created otherwise
	 */
	public static IPath createIbyFiles(List<IbyEntry> entries, ProjectManager manager, IPath path) { 
		List<IPath> ibyFiles = new ArrayList<IPath>();
		for (Iterator<IbyEntry> iter = entries.iterator(); iter.hasNext();) {
			StringBuffer sb = new StringBuffer();
			IbyEntry ibyEntry =  iter.next();
			iter.remove();
			append(ibyEntry,sb);
			for (Iterator<IbyEntry> iter2 = entries.iterator(); iter2.hasNext();) {
				IbyEntry entry = (IbyEntry) iter2.next();
				if(entry.getLocation().equals(ibyEntry.getLocation())) {
					iter2.remove();
					append(entry,sb);
				}
			}
			String basename = ProjectManager.IBY_FILENAME_PREFIX
					+ ibyEntry.getLocation().toString().toLowerCase();
			// save iby file content
			String content = sb.toString();
			if(!content.equals("")) {
				appendHeaderAndFooter(basename,sb);
				ibyFiles.add(manager.createIbyFile(basename, sb.toString(), path));				
			}
		}
		if(!ibyFiles.isEmpty()) {
			return manager.createAdditionsMakefile(ibyFiles, path);
		} else {
			return null;
		}
	}
	
	private static void appendHeaderAndFooter(String basename, StringBuffer sb) {
		sb.insert(0,"OVERRIDE_REPLACE/ADD\n");
		sb.append("OVERRIDE_END");
	}

	private static void append(IbyEntry ibyEntry, StringBuffer sb) {
		String str = ibyEntry.toString();
		if(ibyEntry.isEnabled() && !str.equals("")) {
			sb.append(str);
			sb.append("\n");
		}
	}
	
	
	/**
	 * @param hostPath
	 * @return
	 */
	public static String getFixedTargetPath(IPath hostPath) {
		String tp = "\\"; //$NON-NLS-1$
		
		if (hostPath.getFileExtension().compareToIgnoreCase("app") == 0) { //$NON-NLS-1$
			// this is an EKA1 app
			tp += "system\\apps\\" + hostPath.removeFirstSegments(hostPath.segmentCount()-2).setDevice(null).toOSString(); //$NON-NLS-1$
		} else if (hostPath.toOSString().toLowerCase().indexOf("\\epoc32\\release\\") >= 0) { //$NON-NLS-1$
			// this is a binary
			tp += "sys\\bin\\" + hostPath.lastSegment(); //$NON-NLS-1$
		} else {
			// see if this is a resource
			final String dataZDir = "\\epoc32\\data\\z\\"; //$NON-NLS-1$
			int index = hostPath.toOSString().toLowerCase().indexOf(dataZDir);
			if (index >= 0) {
				tp += hostPath.toOSString().substring(index + dataZDir.length());
			} else {
				// fallback - just add filename
				tp += hostPath.lastSegment();
			}
		}
		return tp;
	}	
}
