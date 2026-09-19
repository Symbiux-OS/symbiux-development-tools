/*
* Copyright (c) 2007 Nokia Corporation and/or its subsidiary(-ies). 
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
 
package com.nokia.s60tools.creator.components.contact;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * Variables for contact set
 */
public class ContactSetVariables extends AbstractVariables {
	
	
	public static final String ADD_CONTACT_SET_TXT = "Add new " +CreatorEditorSettings.TYPE_CONTACT_SET;

	private static ContactSetVariables instance;

	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static ContactSetVariables getInstance() {
		
		if(instance == null){
			instance = new ContactSetVariables();
		}
		
		return instance;
	}	
	
	private ContactSetVariables(){
		init();
	}
	
	/**
	 * UI text "ID"
	 */
	public static final String ID = "ID";
	
	private void init() {

		items = new LinkedHashMap<String, String>(1);
		items.put(AbstractComponent.ID_PARAMETER_ID,ID);
		
		additionalItems = new LinkedHashMap<String, String>(13);
		additionalItems.put(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID, AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_DIALOG_TEXT);

		 
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractVariables#isModeEnabledForKey(java.lang.String)
	 */
	public boolean isModeEnabledForKey(String key) {
		return false;
	}		
	
}
