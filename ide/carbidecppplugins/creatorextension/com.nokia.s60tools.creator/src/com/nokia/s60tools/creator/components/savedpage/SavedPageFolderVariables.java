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
 
package com.nokia.s60tools.creator.components.savedpage;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for saved page folder
 */
public class SavedPageFolderVariables extends AbstractVariables {
	
	
	private static SavedPageFolderVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static SavedPageFolderVariables getInstance() {
		
		if(instance == null){
			instance = new SavedPageFolderVariables();
		}
		
		return instance;
	}	
	
	private SavedPageFolderVariables(){
		init();
	}


	/**
	 * UI text "Name"
	 */
	public static final String NAME = "Name";
	
	private void init() {

		items = new LinkedHashMap<String, String>(1);
		items.put("name",NAME);
		 
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
