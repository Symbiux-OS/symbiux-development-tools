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
 
package com.nokia.s60tools.creator.components.landmark;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;

/**
 * Variables for landmark
 */
public class LandmarkVariables extends AbstractVariables {
	
	//
	// Variables to show items in UI
	//
	public static final String POSTAL_ZIP = "Postal code/ZIP";
	public static final String CITY = "City";
	public static final String STATE_PROVINCE = "State/Province";
	public static final String COUNTRY_REGION = "Country/Region";
	public static final String PHONE_NUMBER = "Phone number";
	public static final String URL = "URL";
	public static final String LATITUDE = "Latitude";
	public static final String LONGITUDE = "Longitude";
	public static final String POSITION_ACCURACY = "Position accuracy";
	public static final String STREET = "Street";
	public static final String DESCRIPTION = "Description";
	public static final String CATEGORY = "Category";
	public static final String NAME = "Name";
	public static final String ALTITUDE = "Altitude";
	public static final String ALTITUDE_ACCURACY = "Altitude accuracy";
	
	private static LandmarkVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static LandmarkVariables getInstance() {
		
		if(instance == null){
			instance = new LandmarkVariables();
		}
		
		return instance;
	}	
	
	private LandmarkVariables(){
		init();
		initMaxOccurValues();
		initTipTexts();
	}
	

	
	
	private void init() {

		items = new LinkedHashMap<String, String>(4);
		  items.put("name",NAME);
		  items.put("category",CATEGORY);
		  items.put("description",DESCRIPTION);
		  items.put("street",STREET);
		  items.put("postalcode",POSTAL_ZIP);
		  items.put("city",CITY);
		  items.put("state",STATE_PROVINCE);
		  items.put("country",COUNTRY_REGION);
		  items.put("phonenbr",PHONE_NUMBER);
		  items.put("url",URL);
		  items.put("latitude",LATITUDE);
		  items.put("longitude",LONGITUDE);
		  items.put("positionaccuracy",POSITION_ACCURACY);
		  items.put("altitude",ALTITUDE);
		  items.put("altitudeaccuracy",ALTITUDE_ACCURACY);
		 
	}
	
	/**
	 * Inits Max Occur valus for items.
	 * Only {@link LandmarkVariables#CATEGORY} can occur more than once in on script.
	 */
	private void initMaxOccurValues() {
		maxOccur = new LinkedHashMap<String, Integer>(4);

		Integer integerOne = new Integer(1);

		// On landmarks, only CATEGORY can occur more than once in on script

		maxOccur.put(NAME, integerOne);		
		maxOccur.put(DESCRIPTION, integerOne);
		maxOccur.put(STREET, integerOne);
		maxOccur.put(POSTAL_ZIP, integerOne);
		maxOccur.put(CITY, integerOne);
		maxOccur.put(STATE_PROVINCE, integerOne);
		maxOccur.put(COUNTRY_REGION, integerOne);
		maxOccur.put(PHONE_NUMBER, integerOne);
		maxOccur.put(URL, integerOne);
		maxOccur.put(LATITUDE, integerOne);
		maxOccur.put(LONGITUDE, integerOne);
		maxOccur.put(POSITION_ACCURACY, integerOne);
		maxOccur.put(ALTITUDE, integerOne);
		maxOccur.put(ALTITUDE_ACCURACY, integerOne);
	}	

	private void initTipTexts(){
		tipTexts = new HashMap<String, String>();		
		
		String altitudeTipText = "Altitude in meters";
		
		String accuracyTipText = "Accuracy in meters";
		tipTexts.put(POSITION_ACCURACY, accuracyTipText);
		tipTexts.put(ALTITUDE, altitudeTipText);
		tipTexts.put(ALTITUDE_ACCURACY, accuracyTipText);		
	}
	
	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

}
