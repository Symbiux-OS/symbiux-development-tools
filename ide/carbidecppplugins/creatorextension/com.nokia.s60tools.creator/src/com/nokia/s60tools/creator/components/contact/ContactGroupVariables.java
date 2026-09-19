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
 * Variables for contact group
 */
public class ContactGroupVariables extends AbstractVariables {
	

	private static ContactGroupVariables instance;
	
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static ContactGroupVariables getInstance() {
		
		if(instance == null){
			instance = new ContactGroupVariables();
		}
		
		return instance;
	}	
	
	private ContactGroupVariables(){
		init();
	}
	

	/**
	 * UI text "Name"
	 */
	public static final String NAME = "Name";
	
	private void init() {

		items = new LinkedHashMap<String, String>(1);
		items.put(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE);
		
		additionalItems = new LinkedHashMap<String, String>(13);

		additionalItems.put(AbstractComponent.NAME_PARAMETER_ID, NAME);
		
		 
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}
		

}
