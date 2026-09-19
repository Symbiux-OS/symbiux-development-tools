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
 
package com.nokia.s60tools.creator.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Abstract super class for variables of one type.
 */
public abstract class AbstractVariables {
	
	/**
	 * ID for max random length 
	 */
	public static final String RANDOM_LEN_MAX_XML_VALUE = "max";

	/**
	 * ID for default random length
	 */
	public static final String RANDOM_LEN_DEFAULT_XML_VALUE = "default";

	/**
	 * "yes" and "no" values
	 */
	public static final String YES_NO_TYPES_AS_COMMA_SEPARATED_STRING [] = {"yes", "no"};
	
	/**
	 * UI help text for date time format
	 */
	public static final String DATE_TIME_FORMAT = "YYYY-MM-DDTHH:MM:SS";
	
	/**
	 * UI help text for date format
	 */
	public static final String DATE_FORMAT = "YYYY-MM-DD";
	
	/**
	 * UI Help text for date and date time usage 
	 */
	public static final String DATE_TIME_AND_DATE_FORMAT_HELP_TEXT = "For date time fields use format: '" 
		+DATE_TIME_FORMAT +"' and for date fields use format: '" +DATE_FORMAT +"'.";
	
	/**
	 * UI Help text for date usage
	 */
	public static final String DATE_FORMAT_HELP_TEXT = "For date fields use format: '" 
		+DATE_FORMAT +"'.";

	/**
	 * UI help text for coordinate format
	 */
	public static final String COORDINATE_FORMAT = "-nn.nnnn – nn.nnnn";
	
	/**
	 * UI help text for coordinate format usage
	 */
	public static final String COORDINATE_FORMAT_HELP_TEXT = "For latitude, longitude and altitude use format: '" +COORDINATE_FORMAT +"'."
		+" Positive latitude means north and negative south, zero point is Equator. " +
				"Positive longitude means east and negative west, zero point is Prime Meridian. " +
				"For altitude zero point is sea level.";
		
	
	/**
	 * Storage for component items to be shown in UI.
	 * First String is key to XML element name and second String is shown in UI.
	 */
	protected Map<String, String> items = null;
	
	/**
	 * Storage for additional items, not to be shown in UI. But even when these items are not shown in UI
	 * they are allowed to add to script. Most likely those are XML structure elements when XML is hidden
	 * from user to. Or XML typing information what's hidden from user. 
	 */
	protected Map<String, String> additionalItems = null;
	
	/**
	 * Storage for fixed values of certain item type to be shown in UI.
	 * First String is key to XML element name and second String table is values able to set to element.
	 * Values are set as they appear in XML and UI.
	 */
	protected Map<String, String[]> itemsValues = null;
	
	/**
	 * Storage for max occurs of certain item type in one script.
	 */
	protected Map<String, Integer> maxOccur;
	
	/**
	 * Storage for Tip Texts of certain item type.
	 */
	protected Map<String, String> tipTexts;		
	
	
	/**
	 * Get Singleton instance of variables
	 * @return a component class implementing {@link AbstractVariables}
	 */
	protected abstract AbstractVariables getInstanceImpl();


	/**
	 * Get XML element name by value
	 * @param value
	 * @return key if found, null otherwise
	 */
	public String getIdByValue(String value) {
		
		if(value == null){
			return null;
		}
		
		Collection<String> keys = getItemIDs();
		for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			String _value = getInstanceImpl().items.get(key);
			if (_value.equals(value)) {
				return key;
			}
		}

		//If there is additional items added, checkin also it there is value
		if(getInstanceImpl().additionalItems != null){
			//If value is null, it might be sub value e.g. email in <attendees><attendee><email>...
			Collection<String> subkeys = getInstanceImpl().additionalItems.keySet();
			for (Iterator<String> iterator = subkeys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				String _value = getInstanceImpl().additionalItems.get(key);
				if (_value.equals(value)) {
					return key;
				}
			}		
		}
		//Or if id was not found at all, its not a supported value/id
		return null;

	}	

	/**
	 * Get item ID:s (match to element name in XML <contact><element name></element
	 * name></contact>)
	 * 
	 * @return item ID:s
	 */
	public Collection<String> getItemIDs() {
		
		return getInstanceImpl().items.keySet();
		
	}

	/**
	 * Get Item values (showable names)
	 * @return item names
	 */
	public Collection<String> getItemValues() {
		
		return getInstanceImpl().items.values();
	}

	/**
	 * Get item ID:s (match to element name in XML <contact><element name></element
	 * name></contact>)
	 * 
	 * @return item ID:s
	 */
	public String[] getItemIDsAsString() {
		return (String[]) getItemIDs().toArray(new String[0]);
	}

	/**
	 * Get Item values (showable names)
	 * @return item names
	 */
	public String[] getItemValuesAsString() {
		Collection<String> col = getItemValues();
		String[] arr = (String[]) col.toArray(new String[0]);
		return arr;
	}

	/**
	 * Get value by XML element name
	 * @param id
	 * @return value
	 */
	public String getValueById(String id) {
		if(id == null){
			return null;
		}
		String id_ = id.toLowerCase();
		String value = getInstanceImpl().items.get(id_);
		//If value is null, it might be sub value e.g. email in <attendees><attendee><email>...
		if (value == null && getInstanceImpl().additionalItems != null){
			value = getInstanceImpl().additionalItems.get(id_);
		}
		return value;
	}	
	
	/**
	 * Get fixed values for type. E.g. Message can have values "read" and "new" for item "status".
	 * @param id - ID of the item (element type in XML).
	 * @return possible values or <code>null</code> if item type has no 
	 * fixed values or item type was not found.
	 */
	public String[] getValuesForItemType(String itemType){
		if(getInstanceImpl().itemsValues == null || getValueById(itemType) == null){
			return null;
		}
		else{
			String [] values = getInstanceImpl().itemsValues.get(itemType);
			if(values == null || values.length < 1){
				return null;
			}else{
				return values;
			}
		}
	}

	/**
	 * Items max occurrence count in one script. Default implementation is
	 * unbounded and {@link AbstractComponent#MAX_OCCURS_UNBOUNDED} is returned. 
	 * Component can overwrite this implementation if has items that have limited number
	 * of items occurrences in one script.
	 * @param itemName
	 * @return item max occurrence if defined or {@link AbstractComponent#MAX_OCCURS_UNBOUNDED}.
	 */
	public int itemMaxOccur(String itemName) {
		
		if(maxOccur == null){		
			return AbstractComponent.MAX_OCCURS_UNBOUNDED;
		}else{
			Integer maxOcc = maxOccur.get(itemName);			
			if(maxOcc != null){
				int occ = maxOcc.intValue();
				if(occ > 0){
					return occ;
				}
			}
		}
		return AbstractComponent.MAX_OCCURS_UNBOUNDED;
	}


	/**
	 * Get Tip Text to item type.
	 * @param itemType
	 * @return TipText or <code>null</code> if not set.
	 */
	public String getTipText(String itemType) {

		if(tipTexts == null || itemType == null){
			return null;			
		}else{
			return tipTexts.get(itemType);
		}
		
	}


	/**
	 * Check if mode is enabled for key. Default implementation return <code>true</code>,
	 * component variables can overwrite this if special handling is needed.
	 * @param key
	 * @return <code>true</code> if mode is enabled.
	 */
	public boolean isModeEnabledForKey(String key) {
		return true;
	}	
	
}
