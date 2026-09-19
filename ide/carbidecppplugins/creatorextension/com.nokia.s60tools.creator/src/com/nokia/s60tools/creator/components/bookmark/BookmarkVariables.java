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
 
package com.nokia.s60tools.creator.components.bookmark;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

public class BookmarkVariables extends AbstractVariables {
	
	
	private static BookmarkVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static BookmarkVariables getInstance() {
		
		if(instance == null){
			instance = new BookmarkVariables();
		}
		
		return instance;
	}	
	
	private BookmarkVariables(){
		init();
	}

	//
	// Items (rows) for component 
	//
	public static final String NAME = "Name";
	public static final String URL = "URL";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	
	private void init() {

		items = new LinkedHashMap<String, String>(4);
		  items.put("name",NAME);
		  items.put("url",URL);
		  items.put("username",USERNAME);
		  items.put("password",PASSWORD);
		 
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractVariables#getInstanceImpl()
	 */
	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
