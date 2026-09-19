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
package com.nokia.s60tools.symbianfoundationtemplates.util;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;

/**
 * Utility class.
 *
 */
public class Util {
	/**
	 * Get the status of the given file name (empty, exists already, discouraged extension...)
	 * 
	 * @param fileName the file name
	 * @param templates template files used for checking extensions
	 * @param folder folder where file resides
	 * @return status
	 */
	public static IStatus getFileNameStatus(String fileName, String templates, String folder) {
		IStatus fileStatus = new Status(IStatus.OK, SymbianFoundationTemplates.PLUGIN_ID, 0, "", null);
		
		if(fileName.length() == 0)
			fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, "File name is empty.", null);
		else {
			AbstractList<String> approvedExtensions = getFileExtensions(templates);
		
			boolean endsWithCorrect = false;

			for(String extension : approvedExtensions)
				if(fileName.endsWith(extension))
					endsWithCorrect = true;
			
			if(!endsWithCorrect)
				fileStatus = new Status(IStatus.WARNING, SymbianFoundationTemplates.PLUGIN_ID, 1, "File name is discouraged. File extension does not correspond to file types " + approvedExtensions + ".", null);
			
			if(folder!="" && getSelectedFile(fileName, folder).exists())
				fileStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, "File already exists.", null);
		}
		
		return fileStatus;
	}

	/**
	 * Get the status of the given folder name (empty, exists already...)
	 * 
	 * @param folderName folder name
	 * @param folderType folder type used for creating a correct status message	 
	 * @return status
	 */
	public static IStatus getFolderNameStatus(String folderName, String folderType) {
		IStatus folderStatus = new Status(IStatus.OK, SymbianFoundationTemplates.PLUGIN_ID, 0, "", null);
		
		if(folderName.equals(""))
			folderStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, folderType + " is empty.", null);
		else if(!selectedFolderExists(folderName))
			folderStatus = new Status(IStatus.ERROR, SymbianFoundationTemplates.PLUGIN_ID, -1, folderType + " does not exist.", null);
		
		return folderStatus;
	}
	
	/**
	 * Helper method for getting the selected file
	 * 
	 * @param file file name
	 * @param folder folder name
	 * @return IFile representing the selected file
	 */
	public static IFile getSelectedFile(String file, String folder) {
		return ResourcesPlugin.getWorkspace().getRoot().getFile(
			new Path(folder 
					+ File.separator 
					+ file));
	}
	
	/**
	 * Get the module name.
	 * 
	 * @param folder folder to determine the module from
	 * @return the module name
	 */
	public static String getModuleName(String folder) {
		String[] folderParts = folder.split("/");
		
		if(folderParts.length > 0)
			return folderParts[0];
		else
			return "";
	}
	
	/**
	 * Get the subsystem name.
	 * 
	 * @param folder the folder to determine the subsystem from
	 * @return the subsystem name
	 */
	public static String getSubSystemName(String folder) {
		String[] folderParts = folder.split("/");
		
		if(folderParts.length > 1)
			return folderParts[1];
		else
			return "";
	}
	
	public static int getCopyrightYear() {
		return new GregorianCalendar().get(Calendar.YEAR);
	}
	
	/**
	 * Helper method for getting template file extensions
	 * 
	 * @param templates templates
	 * @return extensions
	 */
	public static AbstractList<String> getFileExtensions(String templates) {
		String[] templateFiles = templates.split(";");
		
		AbstractList<String> extensions = new ArrayList<String>();
		
		for(int i = 0; i < templateFiles.length; i++)
			if(templateFiles[i].contains(".")) {
				String extension = templateFiles[i].substring(
						templateFiles[i].lastIndexOf('.'),
						templateFiles[i].length());
				
				if(!extensions.contains(extension))
					extensions.add(extension);
			}
	
		return extensions;
	}
	
	/**
	 * Helper method for checking whether the selected directory already exists.
	 * 
	 * @return true if exists, false otherwise
	 */
	private static boolean selectedFolderExists(String folderName) {
		String[] pathParts = folderName.split("/");
		IProject project=null;
		if(pathParts!=null && folderName.indexOf("\\")==-1 && !folderName.startsWith(".") && !folderName.startsWith("/"))
		{
			project = ResourcesPlugin.getWorkspace().getRoot().getProject(pathParts[0]);
		}
		else
			return false;
		
		String directory = pathParts[0] + File.separator;
		
		if(pathParts.length > 1)
			for(int i = 1; i < pathParts.length; i++)
				directory += pathParts[i] + File.separator;
		
		IFolder folder = null;
		
		if(pathParts.length > 1)
			folder = ResourcesPlugin.getWorkspace().getRoot().getFolder(new Path(directory));
		
		if(folder == null)
			return project.exists();
		else
			return folder.exists();
	}
	/**
	 * Helper method for checking whether the given file name is valid or not.
	 * @param filename filename
	 * @return true if it is valid.
	 */
	public static boolean checkFileName(String filename)
	{
		String[] restricted_chars={"&", "^", "+", "-", "@", "$", "%", "*", "(", ")", "|", "\\", "/", "[", "]", "{", "}", "<", ">", "?", ";", ":", ",", "'"};
		
		for (int i = 0; i < restricted_chars.length; i++) {
			if(filename.indexOf(restricted_chars[i])!=-1)
				return false;
		}
		if(filename.indexOf(".") != filename.lastIndexOf("."))
			return false;
			
		return true;
	}

	/**
	 * Helper method for getting default license.
	 * @return License string
	 */
	public static String[] getDefaultLicence() {
		return new String[] {"Symbian Foundation License v1.0","Eclipse Public License v1.0"};
	}

	/**
	 * Helper method for getting the default company name.
	 * @return Company name string.
	 */
	public static String getDefaultCompanyName() {
		return "Nokia Corporation";
	}

	/**
	 * Helper method for getting the default company copytight.
	 * @return Company copyright string.
	 */
	public static String getDefaultCompanyCopyright() {
		return "Nokia Corporation and/or its subsidiary(-ies)";
	}
}
