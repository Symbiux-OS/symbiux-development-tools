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
 * Variables for saved page
 */
public class SavedPageVariables extends AbstractVariables {
	
	
	private static SavedPageVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static SavedPageVariables getInstance() {
		
		if(instance == null){
			instance = new SavedPageVariables();
		}
		
		return instance;
	}	
	
	private SavedPageVariables(){
		init();
	}


	/**
	 * UI text "Name"
	 */
	public static final String NAME = "Name";
	/**
	 * UI text "Path"
	 */
	public static final String PATH = "Path";

	
	private void init() {

		items = new LinkedHashMap<String, String>(2);
		items.put("name",NAME);
		items.put("path",PATH);
		 
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
