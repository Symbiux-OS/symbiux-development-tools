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

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 * One row of data of calendar
 */
public class CalendarValue extends AbstractValue {
	
	private boolean isAttendee = false;
	private String attendeeEmail = null;
	private String attendeeCommonName = null;

	/**
	 * @param value
	 * @param randomType
	 * @param amount
	 */
	public CalendarValue(String type, String value, ModeTypes randomType, int amount) {
		this();
		setValue(type, value);
		setModeType(randomType);
		setAmount(amount);
	}
	

	/**
	 * Set amount and also maxamount as amount
	 * For now in calendar there is no any other possible amount fields in calendar than maxamount in contact-set reference
	 * So using always amount also as maxamount. If there is new requirement for amount in calendar, reimplement. 
	 *	
	 * @param amount -for amount and maxamount
	 */
	public void setAmount(int amount) {
		super.setAmount(amount);
		super.setMaxAmount(amount);
	}

	/**
	 * 
	 * @param value
	 * @param amount
	 */
	public CalendarValue(String type, String value, int amount) {
		this();
		setValue(type, value);
		setModeType(ModeTypes.RandomTypeNotRandom);
		setAmount(amount);
	}


	/**
	 * Creates new Value.
	 * 
	 * @param value
	 * @param isRandom if false, random type is set to {@link ModeTypes#RandomTypeNotRandom}
	 * if true random type is set to {@link ModeTypes#RandomTypeDefaultLength}
	 * @param amount
	 */
	public CalendarValue(String type, String value, boolean isRandom, int amount) {
		this();
		if(!isRandom){
			setModeType(ModeTypes.RandomTypeNotRandom);			
		}
		else{
			setModeType(ModeTypes.RandomTypeDefaultLength);
		}
		setValue(type, value);
		setAmount(amount);
	}
	
	/**
	 * Creates new Value with no random and no amount.
	 * random value is set to {@link ModeTypes#RandomTypeNotRandom} and
	 * amount is set to 0.
	 * @param value
	 */
	public CalendarValue(String type, String value) {
		this();
		setValue(type, value);
		setModeType(ModeTypes.RandomTypeNotRandom);
		setAmount(0);
	}	
	
	/**
	 * Creates new Value.
	 * Attributes is set by default;
	 *  - random value is set to {@link ModeTypes#RandomTypeDefaultLength}
	 *  - random is set to true
	 *  - value is set to EMPTY_STRING 
	 *  - amount is set to 0.
	 */
	public CalendarValue() {
		super(CreatorEditorSettings.TYPE_CALENDAR);
	}

	/**
	 * Is this value an attendee value
	 * @return <code>true</code> if this is attendee <code>false</code> otherwise
	 */
	public boolean isAttendee() {
		return isAttendee;
	}

	/**
	 * Set this value to be an attendee value
	 * @param isAttendee <code>true</code> if this is attendee, <code>false</code> otherwise
	 */
	public void setAttendee(boolean isAttendee) {
		this.isAttendee = isAttendee;
	}

	/**
	 * Get attendee email
	 * @return attendee email
	 */
	public String getAttendeeEmail() {
		return attendeeEmail;
	}

	/**
	 * Get attendee common nane
	 * @return attendeee common name
	 */
	public String getAttendeeCommonName() {
		return attendeeCommonName;
	}


	/**
	 * Set value by type. If type is "Attendee" using setAttendeeValue(value)
	 * otherwise using setValue(String)
	 * @param value
	 * @param type as in ZML 
	 */
	public void setValue(String type, String value) {
		
		if (type != null) {
			setType(type);
			if (type.equalsIgnoreCase(CalendarVariables.ATTENDEEEMAIL_XML_ELEMENT)
					|| type.equalsIgnoreCase(CalendarVariables.ATTENDEEEMAIL)) {
				this.attendeeEmail = value;
				setAttendee(true);
			} else if (type.equalsIgnoreCase(CalendarVariables.ATTENDEECOMMONNAME_XML_ELEMENT)
					|| type.equalsIgnoreCase(CalendarVariables.ATTENDEECOMMONNAME)) {
				this.attendeeCommonName = value;
				setAttendee(true);
			}else if (type.equalsIgnoreCase(CalendarVariables.ATTENDEE)
					|| type.equalsIgnoreCase(CalendarVariables.ATTENDEE_XML_ELEMENT)) {
				setAttendeeValue(value);
			}else {
				setValue(value);
			}

		} else {
			setValue(value);
		}
		
	}	
	
	/**
	 * Set attendee value, calling this will also set this object as attendee by using setAttendee(true) 
	 * @param attendeeEmail - <code>null</code> not allowed.
	 * @param attendeeCommonName or <code>null</code> if not set
	 */
	public void setAttendeeValue(String attendeeEmail, String attendeeCommonName) {
		setAttendee(true);		
		
		this.attendeeEmail = attendeeEmail;
		this.attendeeCommonName = attendeeCommonName;

	}
	
	/**
	 * Set attendee value, calling this will also set this object as attendee by using setAttendee(true) 
	 * @param attendee with email and optional common name separated with '|'
	 */
	public void setAttendeeValue(String attendee) {
		
		if(attendee != null){

			setAttendee(true);			

			int separatorIndex = attendee.indexOf("|") ; 
			if(separatorIndex != -1){
				this.attendeeEmail = attendee.substring(0,separatorIndex).trim();
				this.attendeeCommonName = attendee.substring(separatorIndex +1).trim();;
				
			}else{
				this.attendeeEmail = attendee;				
			}
		}
	}	

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractValue#getValue()
	 */
	public String getValue() {

		//In case of Attendee, there is two parameters in one value
		if(isAttendee()){
			if(getAttendeeCommonName() == null){
				return getAttendeeEmail();
			}else{
				return getAttendeeEmail() + AbstractComponent.COMPONENT_TYPE_SEPARATOR
					+getAttendeeCommonName();
			}
			
		}
		//In case of contact set reference, value is not set, but id is
		else if(isContactSetReference()){
			return ""+getId();
		}
		else{
			return super.getValue();
		}
	}	
	
	

}
