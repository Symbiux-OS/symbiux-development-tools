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
 
package com.nokia.s60tools.creator.components.messaging;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.filetype.FileTypeVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * Variables for message
 */
public class MessageVariables extends AbstractVariables {


	//
	// UI texts for xml elements
	//
	public static final String STATUS = "Status";
	public static final String TO = "To";
	public static final String FROM = "From";
	public static final String FOLDER = "Folder";
	public static final String SUBJECT = "Subject";
	public static final String TEXT = "Text";
	public static final String ATTACHMENTPATH = "Attachment path";
	public static final String ATTACHMENTID = "Attachment file ID";
	public static final String SMART_MSG_TYPE = "Type";
	
	/**
	 * Possible fixed values for "Folder" 
	 */
	public static final String [] FOLDER_POSSIBLE_VALUES = {"Sent", "Inbox", "Draft", "Outbox"};
	/**
	 * Possible fixed values for "Status"
	 */
	public static final String [] STATUS_POSSIBLE_VALUES = {"Read", "New"};
	/**
	 * Possible fixed values for Smart message "Type"
	 */
	public static final String [] SMART_MSG_TYPE_POSSIBLE_VALUES = {
		"internetsettings", "emailnotification", "businesscard", "wapsettings", "vcalendar", 
		"vcard", "ringtone", "operatorlogo", "wapprovisioning", "clilogo"};

	
	
	//
	// XML element names
	//
	private static final String ATTACHEMENT_ID_XML_ELEMENT = "attachmentid";
	private static final String FOLDER_XML_ELEMENT = "folder";
	private static final String SMARTMESSAGETYPE_XML_ELEMENT = "smartmessagetype";
	private static final String BT_XML_ELEMENT = "bt";
	private static final String IR_XML_ELEMENT = "ir";
	private static final String SMART_XML_ELEMENT = "smart";
	private static final String SMS_XML_ELEMENT = "sms";
	private static final String MMS_XML_ELEMENT = "mms";
	private static final String AMS_XML_ELEMENT = "ams";
	private static final String EMAIL_XML_ELEMENT = "email";
	private static final String FROM_XML_ELEMENT = "from";
	private static final String TO_XML_ELEMENT = "to";
	private static final String STATUS_XML_ELEMENT = "status";
	
	private static MessageVariables instance;
	
	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static MessageVariables getInstance() {
		
		if(instance == null){
			instance = new MessageVariables();
		}
		
		return instance;
	}	
	
	private MessageVariables(){
		init();
		initFixedValues();
		initMaxOccurValues();
	}
	

	
	private void init() {

		items = new LinkedHashMap<String, String>(3);
		items.put(TO_XML_ELEMENT, TO);
		items.put(FROM_XML_ELEMENT, FROM);
		items.put(FOLDER_XML_ELEMENT, FOLDER);
		items.put("subject", SUBJECT);
		items.put("text", TEXT);
		items.put(STATUS_XML_ELEMENT, STATUS);		
		items.put("attachmentpath", ATTACHMENTPATH);
		items.put(ATTACHEMENT_ID_XML_ELEMENT, ATTACHMENTID);
		//For contact set references, (from and to) must create own handling, because of items keys are unique, cant have same key with two values
		items.put(/*FROM_XML_ELEMENT*/CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM);
		items.put(/*TO_XML_ELEMENT */CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO);

		additionalItems = new LinkedHashMap<String, String>(13);

		
		additionalItems.put(SMS_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_SMS);
		additionalItems.put(MMS_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_MMS);
		additionalItems.put(AMS_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_AMS);
		additionalItems.put(EMAIL_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_EMAIL);
		additionalItems.put(SMART_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_SMART);
		additionalItems.put(IR_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_IR);		 
		additionalItems.put(BT_XML_ELEMENT, CreatorEditorSettings.TYPE_MESSAGE_BT);
		
		
		additionalItems.put(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE);
		//For contact set references, (from and to) must create own handling, because of items keys are unique, cant have same key with two values
		//Contact set reference from and to xml elements will found here
		additionalItems.put(FROM_XML_ELEMENT, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM);		
		additionalItems.put(TO_XML_ELEMENT, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO);
		
		additionalItems.put(SMARTMESSAGETYPE_XML_ELEMENT, SMART_MSG_TYPE);		
		
	}
	
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);
		itemsValues.put(STATUS_XML_ELEMENT, STATUS_POSSIBLE_VALUES);
		itemsValues.put(SMARTMESSAGETYPE_XML_ELEMENT, SMART_MSG_TYPE_POSSIBLE_VALUES);
		itemsValues.put(FOLDER_XML_ELEMENT, FOLDER_POSSIBLE_VALUES);
		itemsValues.put(ATTACHEMENT_ID_XML_ELEMENT, FileTypeVariables.ALL_FILE_TYPES_AS_COMMA_SEPARATED_STRING);				
	}
	
	/**
	 * Inits Max Occur valus for items
	 */
	private void initMaxOccurValues(){
		maxOccur = new LinkedHashMap<String, Integer>(4);
		
		Integer integerOne = new Integer (1);
		
		maxOccur.put(FOLDER, integerOne );
		maxOccur.put(SUBJECT, integerOne );
		maxOccur.put(TEXT, integerOne );
		maxOccur.put(SMART_MSG_TYPE, integerOne );
		maxOccur.put(STATUS, integerOne );		
	}	
	
	/**
	 * Get XML element name by value
	 * @param value
	 * @return key if found, null otherwise
	 */
	public String getIdByValue(String value) {
		
		if(value != null && value.equals(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM)){
			return FROM_XML_ELEMENT;
		}
		else if(value != null && value.equals(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO)){
			return TO_XML_ELEMENT;
		}		
		else{
			return super.getIdByValue(value);
		}

	}
	
	
	/**
	 * Get value by two xml element name. Use with from/to and contact-set-reference to get proper contact set reference element UI name.
	 * @param superElement
	 * @param element
	 * @return String
	 */
	public static String getValueByIds(String superElement, String element){
		if(superElement != null && element != null){
			if(superElement.equalsIgnoreCase(FROM) && element.equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT)){
				return CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM;
			}
			else if(superElement.equalsIgnoreCase(TO) && element.equalsIgnoreCase(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT)){
				return CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO;
			}
			else{ 
				return null;			
			}
		}
		else{
			return null;
		}
	}
	
	/**
	 * Get Item values (showable names)
	 * 
	 * @return item names
	 */
	public String[] getItemValuesAsString(String type) {

		String _type = getValueById(type);
		
		String[] arr = null;

		//
		// Some of types has limited number of values, not all
		//
		
		if (_type.equals(CreatorEditorSettings.TYPE_MESSAGE_SMS)) {
			arr = new String[] {
			// No subject, no attachement in SMS
					TO, FROM, FOLDER, TEXT,STATUS, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO, 
					CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM };
			return arr;
		} else if (_type.equals(CreatorEditorSettings.TYPE_MESSAGE_SMART)) {
			arr = new String[] { TO, FROM, FOLDER ,STATUS, 
					CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO, 
					CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM,
					SMART_MSG_TYPE};
			return arr;
		} else if (_type.equals(CreatorEditorSettings.TYPE_MESSAGE_IR)
				|| _type.equals(CreatorEditorSettings.TYPE_MESSAGE_BT)) {
			arr = new String[] { TO, FROM, FOLDER, STATUS,SUBJECT, CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO, 
					CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM };
			return arr;
		}
		// Types:
		// CreatorEditorSettings.TYPE_MESSAGE_MMS,
		// CreatorEditorSettings.TYPE_MESSAGE_AMS,
		// CreatorEditorSettings.TYPE_MESSAGE_EMAIL
		// will have all elements
		else {
			return getItemValuesAsString();
		}
	}	

	protected AbstractVariables getInstanceImpl() {
		return instance;
	}

	/**
	 * Get ID by subelement name and superelement name
	 * @param itemId - sub element name
	 * @param superElementType - super element name
	 * @return
	 */
	public String getIDByValueAndType(String itemId, String superElementType) {
		
		if(itemId != null && superElementType != null && itemId.equals(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT)){
		
			if(superElementType.equals(FROM_XML_ELEMENT )){
				return CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_FROM;
			}
			else if(superElementType.equals(TO_XML_ELEMENT )){
				return CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_TO;
			}
			else{
				return getIdByValue(itemId);
			}
		}
		
		else{
			return getIdByValue(itemId);
		}
		
	}

	/**
	 * Check if <code>incvalueforeachcopy</code> is supported for type.
	 * @param type as in UI, not as in XML.
	 * @return <code>true</code> if <code>incvalueforeachcopy</code> is supported.
	 */
	public boolean isTypeSupportingIncValueForEachCopy(String type) {
		
		//phone number
		
		if(type == null){
			return false;
		}
		
		return type.equals(TO) || type.equals(FROM);
	}
}
