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

package com.nokia.s60tools.creator.components.calendar;

import java.util.LinkedHashMap;

import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * Variables for calendar
 */
public class CalendarVariables extends AbstractVariables {

	public static final String STATUS_XML_ELEMENT = "status";

	public static final String RECURRENTFREQUENCY_XML_ELEMENT = "recurrentfrequency";

	public static final String ORGANIZEREMAIL_XML_ELEMENT = "organizeremail";

	private static CalendarVariables instance;

	/**
	 * Get Singleton instance of variables
	 * @return 
	 */
	public static CalendarVariables getInstance() {

		if (instance == null) {
			instance = new CalendarVariables();
		}

		return instance;
	}

	private CalendarVariables() {
		init();
		initFixedValues();	
		initMaxOccurValues();
	}
	

	
	//
	// Variables
	//
	public static final String SUMMARY = "Summary";
	public static final String LOCATION = "Location";
	public static final String DESCRIPTION = "Description";
	public static final String STARTTIME = "Start time";
	public static final String ENDTIME = "End time";
	
	public static final String CREATION_PERIOD_START_DATE = "Creation period start date";
	public static final String CREATION_PERIOD_END_DATE = "Creation period end date";
	

	public static final String ALARMTIME = "Alarm time";
	public static final String DATE = "Date";	

	public static final String RECURRENTFREQUENCY = "Recurrent frequency";
	public static final String RECURRENTINTERVAL = "Recurrent interval";
	public static final String RECURRENTFROM = "Recurrent from";
	public static final String RECURRENTTO = "Recurrent to";

	public static final String SYNCHRONIZATION = "Synchronization";

	public static final String ORGANIZERNAME = "Organizer name";
	public static final String ORGANIZEREMAIL = "Organizer email";

	public static final String ATTENDEES = "Attendees";
	public static final String ATTENDEE = "Attendee";
	public static final String ATTENDEE_XML_ELEMENT = "attendee";
	public static final String ATTENDEECOMMONNAME = "Commonname";
	public static final String ATTENDEEEMAIL = "Email";

	public static final String ATTENDEECOMMONNAME_XML_ELEMENT = "commonname";
	public static final String ATTENDEEEMAIL_XML_ELEMENT = "email";	
	
	public static final String SYNCHRONIZATION_XML_ELEMENT = "Synchronization";

	public static final String STATUS = "Status";

	public static final String PRIORITY = "Priority";	
	
	public static final String ATTENDEE_CONTACT_SET_REFERENCE  = "Attendee Contact Set ID";

	public static final String RECURRENTFREQUENCY_POSSIBLE_VALUES_AS_COMMA_SEPARATED [] = {"not-repeated", "daily", "weekly", "monthly", "yearly"} ;	
	public static final String RECURRENTFREQUENCY_POSSIBLE_VALUES_HELP_TEXT = "To do every second week occurrence calendar event, set '"
		+RECURRENTFREQUENCY+"' to \"weekly\" and set '" +RECURRENTINTERVAL +"' to 2";
	public static final String STATUS_POSSIBLE_VALUES_AS_COMMA_SEPARATED [] = {"tentative", "confirmed", "cancelled", "todoneedsaction", "todocompleted", "todoinprocess"};
	public static final String PRIORITY_POSSIBLE_VALUES_HELP_TEXT = "high, normal, low, or integer values between 0 and 255. Integer values: high = 1, normal = 2, low = 3";

	private void init() {

		items = new LinkedHashMap<String, String>(13);
		items.put("summary", SUMMARY);
		items.put("description", DESCRIPTION);
		items.put("location", LOCATION);
		items.put("starttime", STARTTIME);
		items.put("endtime", ENDTIME);
		
		items.put("creationperiodstartdate", CREATION_PERIOD_START_DATE);
		items.put("creationperiodenddate", CREATION_PERIOD_END_DATE);

		items.put("alarmtime", ALARMTIME);

		items.put(RECURRENTFREQUENCY_XML_ELEMENT, RECURRENTFREQUENCY);
		items.put("recurrentinterval", RECURRENTINTERVAL);		
		items.put("recurrentfrom", RECURRENTFROM);
		items.put("recurrentto", RECURRENTTO);

		items.put("synchronization", SYNCHRONIZATION);

		items.put("organizername", ORGANIZERNAME);		
		items.put(ORGANIZEREMAIL_XML_ELEMENT, ORGANIZEREMAIL);

		items.put(ATTENDEE_XML_ELEMENT, ATTENDEE);
		
		items.put(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT, ATTENDEE_CONTACT_SET_REFERENCE);
		

		//Sub items (xml elements) is collected to own table, so they are not shown in UI 

		additionalItems = new LinkedHashMap<String, String>(13);
		additionalItems.put("attendees", ATTENDEES);
		additionalItems.put(ATTENDEECOMMONNAME_XML_ELEMENT, ATTENDEECOMMONNAME);
		additionalItems.put(ATTENDEEEMAIL_XML_ELEMENT, ATTENDEEEMAIL);	
		
		additionalItems.put("starttime", DATE);
		additionalItems.put(STATUS_XML_ELEMENT, STATUS);
		additionalItems.put("priority", PRIORITY);
		
		additionalItems.put(CreatorEditorSettings.TYPE_APPOINTMENT_XML_ELEMENT, CreatorEditorSettings.TYPE_APPOINTMENT);
		additionalItems.put(CreatorEditorSettings.TYPE_EVENT_XML_ELEMENT, CreatorEditorSettings.TYPE_EVENT);
		additionalItems.put(CreatorEditorSettings.TYPE_REMINDER_XML_ELEMENT, CreatorEditorSettings.TYPE_REMINDER);
		additionalItems.put(CreatorEditorSettings.TYPE_TODO_XML_ELEMENT, CreatorEditorSettings.TYPE_TODO);
		additionalItems.put(CreatorEditorSettings.TYPE_ANNIVERSARY_XML_ELEMENT, CreatorEditorSettings.TYPE_ANNIVERSARY);
				
		

	}
	
	private void initFixedValues(){
		itemsValues = new LinkedHashMap<String, String[]>(4);
		itemsValues.put(RECURRENTFREQUENCY_XML_ELEMENT, RECURRENTFREQUENCY_POSSIBLE_VALUES_AS_COMMA_SEPARATED);		
		itemsValues.put(STATUS_XML_ELEMENT, STATUS_POSSIBLE_VALUES_AS_COMMA_SEPARATED);
	}	
	
	
	/**
	 * Inits Max Occur valus for items
	 */
	private void initMaxOccurValues(){
		maxOccur = new LinkedHashMap<String, Integer>(4);
		
		Integer integerOne = new Integer (1);
		
		maxOccur.put(SUMMARY, integerOne );
		maxOccur.put(DESCRIPTION, integerOne );
		maxOccur.put(LOCATION, integerOne );
		maxOccur.put(STARTTIME, integerOne );
		maxOccur.put(ENDTIME, integerOne );		
		maxOccur.put(CREATION_PERIOD_START_DATE, integerOne );
		maxOccur.put(CREATION_PERIOD_END_DATE, integerOne );
		maxOccur.put(ALARMTIME, integerOne );
		maxOccur.put(RECURRENTFREQUENCY, integerOne );
		maxOccur.put(RECURRENTINTERVAL, integerOne );		
		maxOccur.put(RECURRENTFROM, integerOne );
		maxOccur.put(RECURRENTTO, integerOne );
		maxOccur.put(SYNCHRONIZATION, integerOne );
		maxOccur.put(ORGANIZERNAME, integerOne );		
		maxOccur.put(ORGANIZEREMAIL, integerOne );
		
		maxOccur.put(STATUS, integerOne);
		maxOccur.put(PRIORITY, integerOne);		

		maxOccur.put(ATTENDEES, integerOne);		

	}		
	
	/**
	 * Get Item values (showable names)
	 * @return item names
	 */
	public String[] getItemValuesAsString(String type) {

		String[] arr = null;

		//TO-DO
		if(type.equals(CreatorEditorSettings.TYPE_TODO_XML_ELEMENT)){
			arr=new String[]{
					SUMMARY, DESCRIPTION, STARTTIME ,ENDTIME, CREATION_PERIOD_START_DATE, CREATION_PERIOD_END_DATE, ALARMTIME, SYNCHRONIZATION, PRIORITY, STATUS
					};
			return arr;
		}
		//Reminder
		else if(type.equals(CreatorEditorSettings.TYPE_REMINDER_XML_ELEMENT)){
			arr=new String[]{
					SUMMARY, DESCRIPTION, ALARMTIME, SYNCHRONIZATION, CREATION_PERIOD_START_DATE, CREATION_PERIOD_END_DATE
					};
			return arr;			
		}		
		//Anniversary
		else if(type.equals(CreatorEditorSettings.TYPE_ANNIVERSARY_XML_ELEMENT)){
			arr=new String[]{
					SUMMARY, DESCRIPTION, DATE, ALARMTIME, SYNCHRONIZATION, CREATION_PERIOD_START_DATE, CREATION_PERIOD_END_DATE
					};
			return arr;	
		}	
		//Event (Day note)
		else if(type.equals(CreatorEditorSettings.TYPE_EVENT_XML_ELEMENT)){
			arr=new String[]{
					SUMMARY, DESCRIPTION, STARTTIME, ENDTIME, CREATION_PERIOD_START_DATE, CREATION_PERIOD_END_DATE, ALARMTIME, SYNCHRONIZATION
					};
			return arr;			
		}
		//Appointment
		else if(type.equals(CreatorEditorSettings.TYPE_APPOINTMENT)){
			//return all others, but not priority and status
			arr=new String[]{
					SUMMARY, DESCRIPTION, LOCATION, STARTTIME, ENDTIME, CREATION_PERIOD_START_DATE, CREATION_PERIOD_END_DATE, ALARMTIME,
					RECURRENTFROM, RECURRENTTO, RECURRENTFREQUENCY, RECURRENTINTERVAL,
					SYNCHRONIZATION, ORGANIZERNAME, ORGANIZEREMAIL, ATTENDEE, 
					CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE
					};
			return arr;			
			
		}
		//Just in case else returning all
		else{
			return getItemValuesAsString();
		}		
	}	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractVariables#getInstanceImpl()
	 */
	protected AbstractVariables getInstanceImpl() {
		return instance;
	}



}
