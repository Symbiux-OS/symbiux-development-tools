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
 
 
package com.nokia.s60tools.creator.core;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.calendar.CalendarVariables;

/**
 * CreatorScriptEditorSettings. Values for drop down menu on CreatorScript Editor.
 * 
 * Use CreatorScriptEditorPropertiesSetter to set Values on startup
 */
public class CreatorEditorSettings {
	
	/**
	 * One instance of settings
	 */
	private static CreatorEditorSettings instance;
	
	/**
	 * Holds forbidden characters and entity numbers 
	 */
	private Hashtable<String, String> entityNumbersAndChars;
	
	/**
	 * Holds forbidden characters and entity numbers 
	 */
	private Hashtable<String, String> entityNamesAndChars;



	/**
	 * Type for general "Unkown" component type
	 */
	public static final String TYPE_UNKNOWN = "Unknown component";
			
	//
	// Variables for contacts
	//
	public static final String TYPE_CONTACT = "Contact";
	public static final String TYPE_CONTACT_XML_ELEMENT = "contact";		
	public static final String TYPE_CONTACT_SET = "Contact-set";
	public static final String TYPE_CONTACT_SET_XML_ELEMENT = "contact-set";
	public static final String TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT = "contact-set-reference";
	public static final String TYPE_CONTACT_SET_REFERENCE_FROM = "From (Contact Set ID)";
	public static final String TYPE_CONTACT_SET_REFERENCE_TO = "To (Contact Set ID)";
	public static final String TYPE_CONTACT_SET_REFERENCE = "Contact Set ID";
	public static final String TYPE_CONTACT_GROUP = "Contact Group";
	public static final String TYPE_CONTACT_GROUP_XML_ELEMENT = "contactgroup";
	
	//
	// Variables for note
	//
	public static final String TYPE_NOTE = "Note";
	public static final String TYPE_NOTE_XML_ELEMENT = "note";	
	
	//
	// Variables for bookmark
	//
	public static final String TYPE_BOOKMARK = "Bookmark";
	public static final String TYPE_BOOKMARK_XML_ELEMENT = "bookmark";
	public static final String TYPE_BOOKMARK_FOLDER = "Bookmark folder";
	public static final String TYPE_BOOKMARK_FOLDER_XML_ELEMENT = "bookmarkfolder";

	//
	// Variables for saved page
	//
	public static final String TYPE_SAVED_PAGE = "Saved page";
	public static final String TYPE_SAVED_PAGE_XML_ELEMENT = "savedpage";
	public static final String TYPE_SAVED_PAGE_FOLDER = "Saved page folder";
	public static final String TYPE_SAVED_PAGE_FOLDER_XML_ELEMENT = "savedpagefolder";

	//
	// Variables for log
	//	
	public static final String TYPE_LOG = "Log";
	public static final String TYPE_LOG_XML_ELEMENT = "log";

	//
	// Variables for IMPS Server
	//
	public static final String TYPE_IMPS_SERVER = "IMPS Server";
	public static final String TYPE_IMPS_SERVER_XML_ELEMENT = "impsserver";

	//
	// Variables for Connection method
	//	
	public static final String TYPE_CONNECTION_METHOD_XML_ELEMENT = "connectionmethod";
	public static final String TYPE_CONNECTION_METHOD = "Connection method";

	//
	// Variables for calendar
	//	
	public static final String TYPE_CALENDAR_XML_ELEMENT = "calendar";
	public static final String TYPE_CALENDAR = "Calendar";
	public static final String TYPE_TODO = "To-do";
	public static final String TYPE_TODO_XML_ELEMENT = "todo";	
	public static final String TYPE_APPOINTMENT = "Appointment";
	public static final String TYPE_APPOINTMENT_XML_ELEMENT = "appointment";
	public static final String TYPE_EVENT = "Event";
	public static final String TYPE_EVENT_XML_ELEMENT = "event";
	public static final String TYPE_REMINDER = "Reminder";
	public static final String TYPE_REMINDER_XML_ELEMENT = "reminder";
	public static final String TYPE_ANNIVERSARY = "Anniversary";	
	public static final String TYPE_ANNIVERSARY_XML_ELEMENT = "anniversary";	

	//
	// Variables for mailbox
	//
	public static final String TYPE_MAIL_BOX_XML_ELEMENT = "mailbox";
	public static final String TYPE_MAIL_BOX = "Mail box";

	//
	// Variables for messages
	//
	public static final String TYPE_MESSAGE_XML_ELEMENT = "message";
	public static final String TYPE_MESSAGE_SMS = "SMS";
	public static final String TYPE_MESSAGE_MMS = "MMS";
	public static final String TYPE_MESSAGE_AMS = "AMS";	
	public static final String TYPE_MESSAGE_EMAIL = "Email";
	public static final String TYPE_MESSAGE_SMART = "Smart message";
	public static final String TYPE_MESSAGE_IR = "IR message";
	public static final String TYPE_MESSAGE_BT = "BT message";
	public static final String TYPE_MESSAGE = "Message";

	//
	// Variables for landmarks
	//
	public static final String TYPE_LANDMARK_XML_ELEMENT = "landmark";	
	public static final String TYPE_LANDMARK = "Landmark";

	//
	// Variables for file
	//	
	public static final String TYPE_FILE = "File";
	public static final String TYPE_FILE_XML_ELEMENT = "file";


	
	

	/**
	 * List of components able to add to script. This components is shown as list in combo in UI.
	 * Contact set does not belong to list, because it's handled as separate component.
	 */
	private String [] components = {
			TYPE_CONTACT,
			TYPE_CONTACT_GROUP,
			TYPE_CONNECTION_METHOD,
			TYPE_TODO,
			TYPE_APPOINTMENT,
			TYPE_EVENT,
			TYPE_REMINDER,
			TYPE_ANNIVERSARY,
			TYPE_MAIL_BOX,
			TYPE_MESSAGE_SMS,
			TYPE_MESSAGE_MMS,
			TYPE_MESSAGE_AMS,
			TYPE_MESSAGE_EMAIL,
			TYPE_MESSAGE_SMART,
			TYPE_MESSAGE_IR,
			TYPE_MESSAGE_BT,
			TYPE_NOTE,
			TYPE_BOOKMARK,
			TYPE_BOOKMARK_FOLDER,
			TYPE_SAVED_PAGE,
			TYPE_SAVED_PAGE_FOLDER,
			TYPE_LOG,
			TYPE_IMPS_SERVER,
			TYPE_LANDMARK,
			TYPE_FILE
			};	
	
	
	/**
	 * Private construction
	 */
	private CreatorEditorSettings(){
		
		init();
		
	}
	
	/**
	 * Initialize entities for forbidden characters
	 */
	private void init(){
		entityNumbersAndChars = new Hashtable<String, String>();
		entityNumbersAndChars.put("&#38;", "&" );
		entityNumbersAndChars.put("&#60;", "<" );
		entityNumbersAndChars.put("&#62;", ">" );
		entityNumbersAndChars.put("&#39;", "\'");
		entityNumbersAndChars.put("&#34;", "\"");
		
		entityNamesAndChars = new Hashtable<String, String>();
		entityNamesAndChars.put("&amp;", "&" );
		entityNamesAndChars.put("&lt;", "<" );
		entityNamesAndChars.put("&gt;", ">" );
		entityNamesAndChars.put("&apos;", "\'");
		entityNamesAndChars.put("&quot;", "\"");
		
	}

	
	
	/**
	 * Only instance of Settings
	 * @return CreatorScriptEditorSettings instance
	 */
	public static CreatorEditorSettings getInstance(){
		if(instance == null){
			instance = new CreatorEditorSettings();
		}
		return instance;
	}
	
	/**
	 * Get Components
	 * @return components possible to create to script
	 */
	public String[] getComponents() {
		return components;
	}

	/**
	 * Set Components
	 * @param components possible to create to script
	 */
	public void setComponents(String[] components) {
		this.components = components;
	}


	/**
	 * Is selected component supported or not.
	 * @param componentName
	 * @return true if it's a supported component name false otherwise
	 */
	public boolean isSupportedComponent(String componentName){
		
		boolean isSupported = false;
		for (int i = 0; i < components.length; i++) {
			if(components[i].equals(componentName)){
				isSupported = true;
				break;
			}
		}
		
		return isSupported ;
	}


	/**
	 * Check if a String contains forbidden characters to XML element and attribute content
	 * @param in
	 * @return true if contain, false otherwise
	 */
	public boolean containForbiddenChars(String in){
		
		Collection<String> forbiddenChars = entityNumbersAndChars.values();
		for (Iterator<String> iterator = forbiddenChars.iterator(); iterator.hasNext();) {
			String forbiddenChar = (String) iterator.next();
			if(in.contains(forbiddenChar)){
				return true;
			}
		}
		return false;		
	
	}
	
	/**
	 * Check if a String contains entities matching forbidden chars (e.g. &amp; or &#38;)
	 * @param in
	 * @return
	 */
	public boolean containEntities(String in){
		
		Set<String> numKeys = entityNumbersAndChars.keySet();
		for (Iterator<String> iterator = numKeys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(in.contains(key)){
				return true;
			}
		}
		
		Set<String> nameKeys = entityNamesAndChars.keySet();
		for (Iterator<String> iterator = nameKeys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			if(in.contains(key)){
				return true;
			}
		}				
		return false;	
	
	}	

	/**
	 * Replaces entities:
	 * &#38; and &amp; with &
	 * &#60; and &lt; with <
	 * &#62; and &gt; with >
	 * &#39; and &apos; with '
	 * &#34; and &quot; with "
	 * @param in String with entities
	 * @return out String with entities as characters
	 */
	public String replaceEntitiesWithChars(String in){
		
		if(in == null){
			return in;
		}
		
		String out = new String(in);
		Set<String> numKeys = entityNumbersAndChars.keySet();
		for (Iterator<String> iterator = numKeys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			out = out.replace(key, entityNumbersAndChars.get(key));
		}
		
		Set<String> nameKeys = entityNamesAndChars.keySet();
		for (Iterator<String> iterator = nameKeys.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			out = out.replace(key, entityNamesAndChars.get(key));
		}
		
		return out;
		
	}

	/**
	 * Checks that there are no forbidden characters and replaces with valid
	 * ones if needed. Chars that will be replaced: &, <, >, ", '
	 * 
	 * @param in
	 *            String to be checked.
	 * @return Returns checked and corrected string.
	 */
	public String replaceForbiddenChars(String in) {
		
		if(in == null){
			return in;
		}

		StringBuffer b = new StringBuffer();
		char[] arr = in.toCharArray();
		for (int i = 0; i < arr.length; i++) {

			if (arr[i] == '<') {
				b.append("&lt;");
			} else if (arr[i] == '>') {
				b.append("&gt;");
			} else if (arr[i] == '&') {
				b.append("&amp;");
			} else if (arr[i] == '\"') {
				b.append("&quot;");
			} else if (arr[i] == '\'') {
				b.append("&apos;");
			} else {
				b.append(arr[i]);
			}

		}
		return b.toString();

	}

	/**
	 * 
	 * @param key
	 *            (type in UI format or in XML element name)
	 * @return true if key is one of contact set reference types
	 *         (TYPE_CONTACT_SET_REFERENCE, TYPE_CONTACT_SET_REFERENCE_FROM or
	 *         TYPE_CONTACT_SET_REFERENCE_TO) false otherwise.
	 */
	public static boolean isContactSetReference(String key) {
		if (key != null && ( key.equals(TYPE_CONTACT_SET_REFERENCE)
			|| key.equals(TYPE_CONTACT_SET_REFERENCE_FROM)
			|| key.equals(TYPE_CONTACT_SET_REFERENCE_TO)
			|| key.equals(TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT)
			|| key.equals(CalendarVariables.ATTENDEE_CONTACT_SET_REFERENCE)
			)){
			return true;
		}			
		else{
			return false;
		}
	}

	/**
	 * Check if value is randomText
	 * @param randomTxt
	 * @return <code>true</code> if its random text, <code>false</code> otherwise.
	 */
	public static boolean isRandomText(String randomTxt) {
		
		if(randomTxt == null){
			return false;
		}
		else{
			return randomTxt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_DEFAULT_LENGTH_LONG)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_MAX_LENGTH_LONG)						
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT_USER_DEFINED_LENGTH_LONG)
				|| randomTxt.equals(AbstractValue.RANDOM_TEXT)
				|| randomTxt.equals("&lt;RND&gt;");
		}
	}
	

}
