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


import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.contact.ContactValue;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;


/**
 * Class representing message
 */
public class Message extends AbstractComponent {


	public Message(int id, String messageType) {
		this(id);
		setMessageType(messageType);
	}	
	public Message(int id) {
		super(id);
	}		
	
	/**
	 * Get type for this Message
	 * @return
	 */
	public String getMessageType() {
		return getAdditionalParameter(TYPE_PARAMETER_ID);
	}



	/**
	 * Set event type for this Message
	 * @param eventType
	 */
	public void setMessageType(String eventType) {
		addAdditionalParameter(TYPE_PARAMETER_ID, eventType);
	}	
	
	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.config.AbstractComponent#getType()
	 */
	public String getType() {
		return CreatorEditorSettings.TYPE_MESSAGE;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#isValid()
	 */
	public boolean isValid() {
		// Message must have type
		return getMessageType() != null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getIdByValue(java.lang.String)
	 */
	public String getIdByValue(String value) {		
		return MessageVariables.getInstance().getIdByValue(value);
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getValueById(java.lang.String)
	 */
	public String getValueById(String id) {		
		return MessageVariables.getInstance().getValueById(id);
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getXMLElementName()
	 */
	public String getXMLElementName() {
		return CreatorEditorSettings.TYPE_MESSAGE_XML_ELEMENT;
	}
	
	/**
	 * Component type must be separated from other data with COMPONENT_TYPE_SEPARATOR, and before
	 * COMPONENT_TYPE_SEPARATOR there must not be any other information than component type.
	 * @return component String. Format:
	 * <code><Component type> COMPONENT_TYPE_SEPARATOR <Item label>=<Item value>, <Item label>=<Item value>... </code> 
	 */	
	public String toString() {
	
		Set<String> componentKeys = getKeys();
		//Collection<String> values = comp.getAttributes().values();
		
		StringBuffer allFieldsB = new StringBuffer();
		allFieldsB.append( getValueById( getMessageType() ));			
		allFieldsB.append( COMPONENT_TYPE_SEPARATOR);
		
		//Amount (how many of this component will be added)
		if(getAmount() > 0){
			allFieldsB.append(" Amount=");
			allFieldsB.append(getAmount());
			allFieldsB.append(COMPONENT_ITEM_SEPARATOR);
		}
		
		String itemlabel = new String();
		Vector<AbstractValue> itemValue;
		//Looping through one component, founding all fields from that component
		for (Iterator<String> compValuesIt = componentKeys.iterator(); compValuesIt.hasNext();) {
			itemlabel = (String) compValuesIt.next();
			itemValue = getAttribute(itemlabel);
			//get all values from values vector
			for (Iterator<AbstractValue> iterator = itemValue.iterator(); iterator.hasNext();) {
				AbstractValue absVal = iterator.next();
				MessageValue calVal = (MessageValue)absVal;

				addOneItemToBuffer(allFieldsB, itemlabel, calVal);									
			}
			
		}
		
		//deleting last ", " from list
		if(allFieldsB.toString().endsWith(COMPONENT_ITEM_SEPARATOR)){
			allFieldsB.delete(allFieldsB.length()-COMPONENT_ITEM_SEPARATOR.length(), allFieldsB.length());
		}
		return  CreatorEditorSettings.getInstance().replaceEntitiesWithChars(allFieldsB.toString());
	}
	
	private void addOneItemToBuffer(StringBuffer allFieldsB, String itemlabel,
			MessageValue calVal) {
		allFieldsB.append(itemlabel);
		allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR);
		//If value is random value, showing in UI only short description (<RND>)
		if(calVal.isRandom()){
			allFieldsB.append(ContactValue.RANDOM_TEXT);
		}else{
			allFieldsB.append(calVal.getValue());
		}
		allFieldsB.append(COMPONENT_ITEM_SEPARATOR);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getValuesForItemType(java.lang.String)
	 */
	public String[] getValuesForItemType(String itemType) {
		
		//If there is not this type of item at all
		String idByValue = getIdByValue(itemType);
		if(idByValue == null){
			return null;
		}
		return MessageVariables.getInstance().getValuesForItemType(idByValue);
		
	}	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#isTypeSupportingIncValueForEachCopy(java.lang.String)
	 */
	public boolean isTypeSupportingIncValueForEachCopy(String type) {
		
		return MessageVariables.getInstance().isTypeSupportingIncValueForEachCopy(type);
		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#itemMaxOccur(java.lang.String)
	 */
	public int itemMaxOccur(String itemName) {		
		return MessageVariables.getInstance().itemMaxOccur(itemName);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getVariables()
	 */
	public AbstractVariables getVariables(){
		return MessageVariables.getInstance();
	}		
}
