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

 
 
package com.nokia.s60tools.metadataeditor.core;

import com.nokia.s60tools.metadataeditor.xml.MetadataXML;

/**
 * MetadataEditorSettings. Values for drop down menu on Metadata Editor.
 * 
 * Use MetadataEditorPropertiesSetter to set Values on startup
 */
public class MetadataEditorSettings {
	
	private static MetadataEditorSettings instance;
	
	//Default values used if not set, or something wrong with parameter file
	public static final String [] CATEGORY_VALUES_FOR_1_0 = {"sdk" ,"domain"};
	public static final String [] CATEGORY_VALUES_FOR_2_0 = {"public" ,"platform"};
	private String [] categoryValuesFor_1_0;
	private String [] categoryValuesFor_2_0;
	private String [] typeValues = {"c++"};
	private String defaultType = "c++";
	private int defaultTypeIndex = 0;
	private String [] SDKVersionValues = {"", "0.9", "1.0", "1.1", "1.2", "2.0", "2.1", "2.6", "2.8", 
			"3.0", "3.1", "3.2", "5.0", "5.1", "5.2"};

	
	/**
	 * Private construction
	 *
	 */
	private MetadataEditorSettings(){
		init();
	}	
	
	/**
	 * Set default values
	 */
	private void init() {
		categoryValuesFor_1_0 = CATEGORY_VALUES_FOR_1_0;
		categoryValuesFor_2_0 = CATEGORY_VALUES_FOR_2_0;
	}

	/**
	 * Only instance of Settings
	 * @return MetadataEditorSettings instance
	 */
	public static MetadataEditorSettings getInstance(){
		if(instance == null){
			instance = new MetadataEditorSettings();
		}
		return instance;
	}
	
	/**
	 * Get category values by data version 
	 * @param SDKVersion use {@link MetadataXML#DATA_VERSION_1_0} for 1.0 and
	 * {@link MetadataXML#DATA_VERSION_2_0} for 2.0.
	 * @return values for category
	 */
	public String[] getCategoryValues(String SDKVersion) {
		if(SDKVersion.equals(MetadataXML.DATA_VERSION_1_0)){
			return categoryValuesFor_1_0;
		}
		else{
			return categoryValuesFor_2_0; 
		}
	}

	public void setCategoryValues(String[] categoryValuesFor1_0, String[] categoryValuesFor2_0) {
		this.categoryValuesFor_1_0 = categoryValuesFor1_0;
		this.categoryValuesFor_2_0 = categoryValuesFor2_0;
	}

	public String[] getSDKVersionValues() {
		return SDKVersionValues;
	}

	public void setSDKVersionValues(String[] versionValues) {
		this.SDKVersionValues = versionValues;
	}

	public String[] getTypeValues() {
		return typeValues;
	}

	public void setTypeValues(String[] typeValues) {
		this.typeValues = typeValues;
	}

	public String getDefaultType() {
		return defaultType;
	}

	/**
	 * Setting default value. If given defaultType does not found in
	 * this.typeValues not setting it, but first item on this.typeValues
	 * @param defaultType
	 */
	public void setDefaultType(String defaultType) {
		
		boolean found = false;
		if(defaultType != null && typeValues.length > 0){
			for(int i=0; i<typeValues.length; i++){
				if(defaultType.equals(typeValues[i])){
					this.defaultType = defaultType;
					this.defaultTypeIndex = i;
					found = true;
					break;
				}
			}			
		}
		//If there is only one possible item, setting it as default
		else if(!found ){
			this.defaultType = typeValues[0];
			this.defaultTypeIndex = 0;
		}
	}

	public int getDefaultTypeIndex() {
		return defaultTypeIndex;
	}
	

}
