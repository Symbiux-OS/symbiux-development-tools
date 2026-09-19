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
 
package com.nokia.s60tools.creator.components.log;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for log
 */
public class LogVariables extends AbstractVariables {
	

	private static final String DIRECTION_XML = "direction";
	private static LogVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static LogVariables getInstance() {
		
		if(instance == null){
			instance = new LogVariables();
		}
		
		return instance;
	}	
	
	private LogVariables(){
		init();
		initFixedValues();
	}
	
	//
	// Variables to show items in UI
	//
	public static final String DIRECTION = "Direction";
	public static final String DURATION = "Duration";
	public static final String PHONENUMBER = "Phone number";
	public static final String DATETIME = "Datetime";
	
	/**
	 * Direction fixed values
	 */
	public static final String [] ALL_DIRECTION_TYPES_AS_COMMA_SEPARATED_STRING = {"missed", "in", "out"};
	
	private void init() {

		items = new LinkedHashMap<String, String>(4);
		  items.put(DIRECTION_XML,DIRECTION);
		  items.put("duration",DURATION);
		  items.put("phonenumber",PHONENUMBER);
		  items.put("datetime",DATETIME);
		 
	}
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);

		itemsValues.put(DIRECTION_XML, ALL_DIRECTION_TYPES_AS_COMMA_SEPARATED_STRING);
				

	}		
	
	protected AbstractVariables getInstanceImpl() {
		return instance;
	}
	
	/**
	 * Check if <code>incvalueforeachcopy</code> is supported for type.
	 * @param type as in UI, not as in XML.
	 * @return <code>true</code> if <code>incvalueforeachcopy</code> is supported.
	 */
	public boolean isTypeSupportingIncValueForEachCopy(String type) {
		
		if(type == null){
			return false;
		}
		
		return type.equals(PHONENUMBER);
	}	

}
