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
 
package com.nokia.s60tools.creator.components.contact;

import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;

/**
 *  One row of data of contact group.
 * 
 *  Id and value are same in contact groups values. 
 *  Thats because of parsing expect that value is random if there is no value, and 
 *  group has only attributes, no value in XML element.
 */
public class ContactGroupValue extends AbstractValue {

	/**
	 * Creates new Value.
	 * @param value
	 * @param randomType
	 * @param amount
	 */
	public ContactGroupValue(String value, ModeTypes randomType, int amount) {
		this();
		setValue(value);
		setModeType(randomType);
		setAmount(amount);
	}
	
	/**
	 * Creates new Value.
	 * @param value
	 * @param amount
	 */
	public ContactGroupValue(String value, int amount) {
		this();
		setValue(value);
		setModeType(ModeTypes.RandomTypeNotRandom);
		setAmount(amount);
	}

	
	/** 
	 * Returning {@link #getId()} as String, because id and value are same in contact groups. 
	 * Thats because of parsing expect that value is random if there is no value, and 
	 * group has only attributes, no value in xml element.
	 * 
	 * @return {@link #getId()}
	 * @see com.nokia.s60tools.creator.components.AbstractValue#getValue()
	 */

	public String getValue() {
		return ""+getId();
	}

	/**
	 * Is this value random or not
	 * @return true if it is a random value
	 */
	public boolean isRandom() {
		return false;
	}	
	

	/* Contact Group value cannot be random. So always returning RandomTypes.RandomTypeNotRandom
	 * (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractValue#getRandomType()
	 */
	public ModeTypes getModeType() {
		return ModeTypes.RandomTypeNotRandom;
	}	

	/**
	/**
	 * Creates new Value.
	 * 
	 * @param value
	 * @param isRandom if false, random type is set to {@link ModeTypes#RandomTypeNotRandom}
	 * if true random type is set to {@link ModeTypes#RandomTypeDefaultLength}
	 * @param amount
	 */
	public ContactGroupValue(String value, boolean isRandom, int amount) {
		this();
		if(!isRandom){
			setModeType(ModeTypes.RandomTypeNotRandom);			
		}
		else{
			setModeType(ModeTypes.RandomTypeDefaultLength);
		}
		setValue(value);
		setAmount(amount);
	}
	
	/**
	 * Creates new Value with no random and no amount.
	 * random value is set to {@link ModeTypes#RandomTypeNotRandom} and
	 * amount is set to 0.
	 * @param value
	 */
	public ContactGroupValue(String value) {
		this();
		setValue(value);
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
	public ContactGroupValue() {
		super(CreatorEditorSettings.TYPE_CONTACT_GROUP);
	}	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractValue#setMaxAmount(int)
	 */
	public void setMaxAmount(int maxAmount) {
		super.setMaxAmount(maxAmount);
		super.setAmount(maxAmount);
	}
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractValue#setAmount(int)
	 */
	public void setAmount(int amount) {
		this.setMaxAmount(amount);
	}

	
	/* Overwriting because of value and id are same in contact groups
	 * (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractValue#setValue(java.lang.String)
	 */
	public void setValue(String value) {
		super.setValue(value);
		
		int intValue=0;
		try {
			intValue = Integer.parseInt(value);
		} catch (Exception e) {
			intValue = 0;
		}
		super.setId(intValue);
	}	
	/* Overwriting because of value and id are same in contact groups
	 * (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractValue#setId(int)
	 */
	public void setId(int id) {
		super.setId(id);
		super.setValue("" +id);
	}	
	
}
