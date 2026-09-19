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

package com.nokia.s60tools.creator.preferences;

import org.eclipse.jface.preference.IPreferenceStore;

import com.nokia.s60tools.creator.CreatorActivator;

/**
 * Helper class to use Dependency Explorer preferences. Use this class for accessing DE preferences 
 * instead of accessing directly through  {@link org.eclipse.jface.util.IPropertyChangeListener.IPreferenceStore}.
 */
public class CreatorPreferences {
	
	/**
	 * Get folder where to save files in target device
	 * @return save folder 
	 */
	public static String getDeviceSaveFolded(){
		String value = CreatorActivator.getPrefsStore().getString(CreatorPreferenceConstants.DEFAULT_DEVICE_SAVE_FOLDER);
		if(value == null || value.trim().length()==0){
			return CreatorPreferenceConstants.DEFAULT_SAVE_FOLDER_IN_DEVICE;
		}
		return value;
	}
	

	/**
	 * Get "Don't ask again" value for replace files in device
	 * @return <code>true</code> if preference "don't ask again" is cheked, <code>false</code> otherwise.
	 */
	public static boolean getDontAskFileReplaceInDevice(){
		
		String value = CreatorActivator.getPrefsStore().getString(CreatorPreferenceConstants.CREATOR_DONT_ASK_REPLACE);
		if(value != null && value.equalsIgnoreCase(CreatorPreferenceConstants.TRUE)){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Set "Don't ask again" value for replace files in device
	 * @param isDontAskAgainChecked <code>true</code> if don't ask again is selected, <code>false</code> otherwise.
	 */
	public static void setDontAskFileReplaceInDevice(boolean isDontAskAgainChecked){
		
		IPreferenceStore store = CreatorActivator.getPrefsStore();

		if(isDontAskAgainChecked){
			store.setValue(CreatorPreferenceConstants.CREATOR_DONT_ASK_REPLACE, CreatorPreferenceConstants.TRUE);
		}else{
			store.setValue(CreatorPreferenceConstants.CREATOR_DONT_ASK_REPLACE, CreatorPreferenceConstants.FALSE);
		}
	}
	
	/**
	 * Get "Don't ask again" value for show information to follow execution in device
	 * @return <code>true</code> if preference "don't ask again" is cheked, <code>false</code> otherwise.
	 */
	public static boolean getDontAskShowInformation(){
		
		String value = CreatorActivator.getPrefsStore().getString(CreatorPreferenceConstants.DONT_ASK_SHOW_INFORMATION);
		if(value != null && value.equalsIgnoreCase(CreatorPreferenceConstants.TRUE)){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Set "Don't ask again" value for show information to follow execution in device
	 * @param isDontAskAgainChecked <code>true</code> if don't ask again is selected, <code>false</code> otherwise.
	 */
	public static void setDontAskShowInformation(boolean isDontAskAgainChecked){
		
		IPreferenceStore store = CreatorActivator.getPrefsStore();

		if(isDontAskAgainChecked){
			store.setValue(CreatorPreferenceConstants.DONT_ASK_SHOW_INFORMATION, CreatorPreferenceConstants.TRUE);
		}else{
			store.setValue(CreatorPreferenceConstants.DONT_ASK_SHOW_INFORMATION, CreatorPreferenceConstants.FALSE);
		}
	}


	
	/**
	 * Get "Don't ask again" value for replace files in device
	 * @return <code>true</code> if preference "don't ask again" is cheked, <code>false</code> otherwise.
	 */
	public static boolean getDontAskShutdownCreator(){
		
		String value = CreatorActivator.getPrefsStore().getString(CreatorPreferenceConstants.CREATOR_DONT_ASK_SHUTDOWN);
		if(value != null && value.equalsIgnoreCase(CreatorPreferenceConstants.TRUE)){
			return true;
		}else{
			return false;
		}
		
	}
	
	/**
	 * Set "Don't ask again" value for replace files in device
	 * @param isDontAskAgainChecked <code>true</code> if don't ask again is selected, <code>false</code> otherwise.
	 */
	public static void setDontAskShutdownCreator(boolean isDontAskAgainChecked){
		
		IPreferenceStore store = CreatorActivator.getPrefsStore();

		if(isDontAskAgainChecked){
			store.setValue(CreatorPreferenceConstants.CREATOR_DONT_ASK_SHUTDOWN, CreatorPreferenceConstants.TRUE);
		}else{
			store.setValue(CreatorPreferenceConstants.CREATOR_DONT_ASK_SHUTDOWN, CreatorPreferenceConstants.FALSE);
		}
	}	

	
}
