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

 
 
package com.nokia.s60tools.metadataeditor;

import java.util.Properties;
import java.util.Vector;

import com.nokia.s60tools.metadataeditor.core.MetadataEditorSettings;
import com.nokia.s60tools.metadataeditor.util.MetadataEditorConsole;
import com.nokia.s60tools.metadataeditor.xml.MetadataNotValidException;
import com.nokia.s60tools.metadataeditor.xml.MetadataXML;

/**
 * Helper class to set values from metadataeditor.properties to MetadataEditorSettings
 *
 */
public class MetadataEditorPropertiesSetter {
	
	private static final String ERROR_MSG_EMPTY_VALUE_SUFFIX = " values from metadataeditor.properties, values was empty, using defaults instead.";
	
	public static final String CATEGORY_KEY_1_0 ="category_values_for_1_0";
	public static final String CATEGORY_KEY_2_0 ="category_values_for_2_0";
	public static final String TYPE_KEY ="type_values";
	public static final String DEFAULT_TYPE_KEY ="default_type";
	public static final String SDK_VERSION_KEY ="sdk_version_values";
	
	private boolean settingWasOK = true;
	private StringBuffer errors = new StringBuffer();
	
	/***
	 * Set values from metadataeditor.properties to MetadataEditorSettings
	 * 
	 * Setting category_values, type_values, default_type and sdk_version_values
	 * 
	 * @param props metadataeditor.properties Properties read
	 * @return true if all settings was ok, false if errors occur. 
	 * 	Use this.getErrors() to see errors if occur.
	 */
	public boolean setProperties(Properties props){
	
		setCategorys(props);
		setTypes(props);
		setSDKs(props);
	
		return settingWasOK;
	}
	
	private void setCategorys(Properties props){
		try {
			if(props.containsKey(CATEGORY_KEY_1_0) && props.containsKey(CATEGORY_KEY_2_0)){
				String cats10 = props.getProperty(CATEGORY_KEY_1_0);
				String [] values10 = getValues(cats10);
				String cats20 = props.getProperty(CATEGORY_KEY_2_0);
				String [] values20 = getValues(cats20);
				
				if(values10.length > 0 && values20.length > 0){
					MetadataEditorSettings.getInstance().setCategoryValues(values10, values20);
				}
				else{
					MetadataEditorConsole.getInstance().println(
							"Could not set SDK Category" +ERROR_MSG_EMPTY_VALUE_SUFFIX);
				}
			}
		} catch (Exception e) {
			settingWasOK = false;
			errors.append("Could not set SDK Category values, using default values instead.\n");
			MetadataEditorConsole.getInstance().println(
					"Could not set SDK Category values. Error was: " +e.getMessage());						
			e.printStackTrace();
		}
	}


	
	private void setTypes(Properties props){
		try {
			if(props.containsKey(TYPE_KEY)){
				String cats = props.getProperty(TYPE_KEY);
				String [] values = getValues(cats);
				if(values.length > 0){
					MetadataEditorSettings.getInstance().setTypeValues(values);
					if(props.containsKey(DEFAULT_TYPE_KEY)){
						MetadataEditorSettings.getInstance().
							setDefaultType(props.getProperty(DEFAULT_TYPE_KEY).trim());
					}
				}else{
					MetadataEditorConsole.getInstance().println(
						"Could not set SDK Type " +ERROR_MSG_EMPTY_VALUE_SUFFIX);
				}
			}
		} catch (Exception e) {
			settingWasOK = false;
			errors.append("Could not set SDK Type values, using default values instead.\n");
			MetadataEditorConsole.getInstance().println(
					"Could not set SDK Type values. Error was: " +e.getMessage());			
			
			e.printStackTrace();
		}		
	}
	private void setSDKs(Properties props){
		try {
			if(props.containsKey(SDK_VERSION_KEY)){
				String cats = props.getProperty(SDK_VERSION_KEY);
				String [] values = getValues(cats);
				if(values.length > 0){
					for (int i = 0; i < values.length; i++) {
						if(!MetadataXML.isVersionNumberValid(values[i]) ){
							throw new MetadataNotValidException("Value: '" +values[i] +"', from: '" +SDK_VERSION_KEY + "' was not valid.");
						}
					}
					//in SDK lists first value is blanc
					String [] valuesWithBlanc = new String [values.length +1 ];
					valuesWithBlanc[0] = "";
					for (int i = 1; i < valuesWithBlanc.length; i++) {
						valuesWithBlanc[i] = values[i-1];
					}
					MetadataEditorSettings.getInstance().setSDKVersionValues(valuesWithBlanc);
				}else{
					MetadataEditorConsole.getInstance().println(
						"Could not set SDK Version" +ERROR_MSG_EMPTY_VALUE_SUFFIX);
				}
			}
		} catch (MetadataNotValidException e) {
			settingWasOK = false;
			errors.append("Could not set SDK Version values, using default values instead.\n");
			MetadataEditorConsole.getInstance().println(
					"Could not set SDK Version values. Error was: " +e.getMessage());
		}
		catch (Exception e) {
			settingWasOK = false;
			errors.append("Could not set SDK Version values, using default values instead.\n");
			MetadataEditorConsole.getInstance().println(
					"Could not set SDK Version values. Error was: " +e.getMessage());			
			e.printStackTrace();
		}		
	}
	
	private String [] getValues(String values) {
		Vector<String> v = new Vector<String>();
		int beginIndex = 0;
		int endIndex = values.indexOf(",");
		String value;
		
		//If there is more than one value
		if(endIndex != -1){
			while( endIndex != -1 ){
				value = new String( values.substring(beginIndex, endIndex) );
				//Making sure that there is no <,> or & chars. Editor must not crash for illeagal values.
				value = MetadataXML.removeForbiddenChars(value).trim();
				v.add(value);
				beginIndex = endIndex + 1;
				endIndex = values.indexOf(",", endIndex+1);
				//Adding last value after last ',' 
				if(beginIndex < values.length() && endIndex == -1){
					value = new String( values.substring(beginIndex, values.length()) );
					value = MetadataXML.removeForbiddenChars(value).trim();
					v.add(value);
				}
			}
		}
		//If only one value exist
		else{
			if(values.trim().length() > 0){
				v.add(MetadataXML.removeForbiddenChars(values.trim()));
			}
		}
		return v.toArray(new String[0]);
	}

	/**
	 * Errors.
	 * @return erros occurded while setting values to MetadataEditorSettings. 
	 * 	Empty if no errors occurded. 
	 */
	public String getErrors() {
		return errors.toString();
	}
	

}

