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


import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;


/**
 * Class representing contact group
 */
public class ContactGroup extends AbstractComponent {


	public int getId() {
		int id_ = NULL_ID;
		String numberValue = getAdditionalParameter(AbstractComponent.ID_PARAMETER_ID);
		if( numberValue != null ){
			try {
				id_ = Integer.parseInt(numberValue);
			} catch (Exception e) {
				id_ = NULL_ID;
			}
		}
		return id_;
	}



	public ContactGroup(int id) {
		super(id);
	}	
	


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.config.AbstractComponent#getType()
	 */
	public String getType() {
		return CreatorEditorSettings.TYPE_CONTACT_GROUP;
	}

	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#isValid()
	 */
	public boolean isValid() {
		return true;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getIdByValue(java.lang.String)
	 */
	public String getIdByValue(String value) {		
		return ContactGroupVariables.getInstance().getIdByValue(value);
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getValueById(java.lang.String)
	 */
	public String getValueById(String id) {		
		return ContactGroupVariables.getInstance().getValueById(id);
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getXMLElementName()
	 */
	public String getXMLElementName() {
		return CreatorEditorSettings.TYPE_CONTACT_GROUP_XML_ELEMENT;
	}




	/**
	 * Get name parameter
	 * @return
	 */
	public String getName() {
		return getAdditionalParameter(AbstractComponent.NAME_PARAMETER_ID); 
	}
	
	/**
	 * Component type must be separated from other data with COMPONENT_TYPE_SEPARATOR, and before
	 * COMPONENT_TYPE_SEPARATOR there must not be any other information than component type.
	 * @return component String. Format:
	 * <code><Component type> COMPONENT_TYPE_SEPARATOR <Item label>=<Item value>, <Item label>=<Item value>... </code> 
	 */	
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
		allFieldsB.append(getType());			
		allFieldsB.append( COMPONENT_TYPE_SEPARATOR);
		
		//reference to another component
		if(hasReferenceToAnotherComponent()){
			allFieldsB.append( getReferenceToAnotherComponent().getType() );
			allFieldsB.append( WHITE_SPACE );
			allFieldsB.append( ID_PARAMETER_ID );			
			allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR );
			allFieldsB.append( getReferenceToAnotherComponent().getId() );
			allFieldsB.append( COMPONENT_TYPE_SEPARATOR);			
		}
		
		//Amount (how many of this component will be added)
		if(getAmount() > 0){
			allFieldsB.append(" maxmount=");
			allFieldsB.append(getAmount());
			allFieldsB.append(COMPONENT_ITEM_SEPARATOR);
		}
		

		if(getName() != null && getName().trim().length() > 0){
			allFieldsB.append(ContactGroupVariables.NAME);
			allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR);
			allFieldsB.append(getName() );
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
				ContactGroupValue val = (ContactGroupValue) iterator.next();
	
				allFieldsB.append(itemlabel);
				allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR);//=
				//If value is random value, showing in UI only short description (<RND>)
				allFieldsB.append(val.getValue());
				allFieldsB.append(COMPONENT_ITEM_SEPARATOR);//, 						
			}
			
		}
		//deleting last ", " from list
		if(allFieldsB.toString().endsWith(COMPONENT_ITEM_SEPARATOR)){
			allFieldsB.delete(allFieldsB.length()-COMPONENT_ITEM_SEPARATOR.length(), allFieldsB.length());
		}
		return  CreatorEditorSettings.getInstance().replaceEntitiesWithChars(allFieldsB.toString());
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
		return ContactGroupVariables.getInstance().getValuesForItemType(idByValue);

	}
	

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getVariables()
	 */
	public AbstractVariables getVariables(){
		return ContactGroupVariables.getInstance();
	}		
}
