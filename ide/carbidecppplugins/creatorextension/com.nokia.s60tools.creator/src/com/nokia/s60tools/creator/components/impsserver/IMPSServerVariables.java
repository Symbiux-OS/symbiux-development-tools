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
 
package com.nokia.s60tools.creator.components.impsserver;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for IMPS server
 */
public class IMPSServerVariables extends AbstractVariables {
	
	
	private static IMPSServerVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static IMPSServerVariables getInstance() {
		
		if(instance == null){
			instance = new IMPSServerVariables();
		}
		
		return instance;
	}	
	
	private IMPSServerVariables(){
		init();
	}

	//
	// Variables to show items in UI
	//
	public static final String NAME = "Name";
	public static final String URL = "URL";
	public static final String USERNAME = "Username";
	public static final String PASSWORD = "Password";
	public static final String CONNECTION_METHOD_NAME = "Connection method name";
	
	private void init() {

		items = new LinkedHashMap<String, String>(5);
		  items.put("name",NAME);
		  items.put("url",URL);
		  items.put("username",USERNAME);
		  items.put("password",PASSWORD);
		  items.put("connectionmethodname",CONNECTION_METHOD_NAME);
	}

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
