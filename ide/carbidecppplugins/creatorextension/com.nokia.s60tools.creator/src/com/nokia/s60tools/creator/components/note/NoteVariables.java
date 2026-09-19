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
 
package com.nokia.s60tools.creator.components.note;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for note
 */
public class NoteVariables extends AbstractVariables {
	

	private static NoteVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static NoteVariables getInstance() {
		
		if(instance == null){
			instance = new NoteVariables();
		}
		
		return instance;
	}	
	
	private NoteVariables(){
		init();
	}
	

	/**
	 * UI text "Text".
	 */
	public static final String TEXT = "Text";
	
	private void init() {

		items = new LinkedHashMap<String, String>(1);
		items.put("text",TEXT);
		 
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
