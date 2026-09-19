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
package com.nokia.s60tools.symbianfoundationtemplates.actions;

import org.eclipse.jface.dialogs.IDialogSettings;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;

/**
 * This class is used to save/remember the last used user-include and source-include paths. 
 *
 */
public class MMPSourceUserIncPaths {

	private static final String SOURCE_PATHS = "Source paths";
	private static final String USERINCLUDE_PATHS = "User include paths";
	
	private static final String SOURCE_PATH_SECTION = "Source path section";
	private static final String USERINCLUDE_SECTION = "UserInc section";
	
	/**
	 * Path types, i.e, SOURCE or INCLUDE path 
	 *
	 */
	public static enum ValueTypes {SRC_DIRS, USERINC_DIRS}
	
	/**
	 * Save the given path values. 
	 * @param pathType
	 * @param value
	 */
	public void saveValues(ValueTypes pathType, String[] value) {
		try {
			switch (pathType) {
				case SRC_DIRS: {
					savePaths(value, SOURCE_PATHS, getSection(SOURCE_PATH_SECTION));
					break;
				}	
				case USERINC_DIRS:{
					savePaths(value, USERINCLUDE_PATHS, getSection(USERINCLUDE_SECTION));
					break;
				}				
			}
		} catch (Exception E) {
			E.printStackTrace();
		}
	}
	
	/**
	 * Returns values user has previously entered to wizard pages.
	 * @param valueType type of the values 
	 * @return user's previous values
	 */
	public String[] getPreviousValues(ValueTypes valueType) {
		try {
			String[] retVal = null;
			
			switch (valueType) {
				case SRC_DIRS: {
					retVal = getPreviousPaths(SOURCE_PATH_SECTION,SOURCE_PATHS);
					break;
				}
				case USERINC_DIRS:{
					retVal = getPreviousPaths(USERINCLUDE_SECTION, USERINCLUDE_PATHS);
					break;
				}
				
			}			
			return retVal;
			
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * Saves given path to correct section in dialog_settings.xml
	 * @param path path to save
	 * @param array name of the array which contains correct values
	 * @param section section which has array 
	 */
	protected void savePaths(String[] path, String listLabel, IDialogSettings section) {
		if (section != null) {
			String[] previousValues = section.getArray(listLabel);
			
			// No previous values exist
			if (previousValues == null) {
				previousValues = path;
			// Previous values exists
			} else {
				previousValues = path;
			}
			section.put(listLabel, previousValues);
		}
	}
	
	/**
	 * Returns previously entered values of wanted context (i.e. wizard page).
	 * @param section section which contains array
	 * @param array name of the array whose values are needed
	 * @return previously entered paths of given section
	 */
	protected String[] getPreviousPaths(String section, String listLabel) {
		String[] retVal = null;
		IDialogSettings sect = getSection(section);
		if (sect != null) {
			retVal = sect.getArray(listLabel);
		}
		else
		{
			System.out.println("Section is null"); 
		}
		
		return retVal;
	}
	
	/**
	 * Get the section by the given name.
	 * @param section name of the IDialogSettings section
	 * @return the IDialogSettings
	 */
	protected IDialogSettings getSection(String section) {
		IDialogSettings retVal = null;
		if (SymbianFoundationTemplates.getDefault().getDialogSettings() != null) {
			 retVal = SymbianFoundationTemplates.getDefault().getDialogSettings().getSection(section);
			if (retVal == null) {
			 retVal = SymbianFoundationTemplates.getDefault().getDialogSettings().addNewSection(section);
			}
		}
		return retVal;
	}	
	
}
