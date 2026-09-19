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


import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;


/**
 * Class representing contact set
 */
public class ContactSet extends AbstractComponent {



	public ContactSet(int id) {
		super(id);
	}	
	


	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.config.AbstractComponent#getType()
	 */
	public String getType() {
		return CreatorEditorSettings.TYPE_CONTACT_SET;
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
		return ContactSetVariables.getInstance().getIdByValue(value);
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getValueById(java.lang.String)
	 */
	public String getValueById(String id) {		
		return ContactSetVariables.getInstance().getValueById(id);
	}



	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getXMLElementName()
	 */
	public String getXMLElementName() {
		return CreatorEditorSettings.TYPE_CONTACT_SET_XML_ELEMENT;
	}
	


	/**
	 * Get Number of existing parameters
	 * @return 0 if not any
	 */
	public int getNumberOfExistingContacts() {
		int numberOfExistingParams = NULL_ID;
		String numberValue = getAdditionalParameter(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID);
		if( numberValue != null ){
			try {
				numberOfExistingParams = Integer.parseInt(numberValue);
			} catch (Exception e) {
				numberOfExistingParams = NULL_ID;
			}
		}
		return numberOfExistingParams;
	}
	
	
	/**
	 * Component type must be separated from other data with COMPONENT_TYPE_SEPARATOR, and before
	 * COMPONENT_TYPE_SEPARATOR there must not be any other information than component type.
	 * @return component String. Format:
	 * <code><Component type> COMPONENT_TYPE_SEPARATOR <Item label>=<Item value>, <Item label>=<Item value>... </code> 
	 */	
	public String toString() {
	
		StringBuffer allFieldsB = new StringBuffer();
		allFieldsB.append(getType());			
		allFieldsB.append( COMPONENT_TYPE_SEPARATOR);

		allFieldsB.append(getValueById(ID_PARAMETER_ID));
		allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR);
		allFieldsB.append(getId());

		//Number of existing contacts
		if(getNumberOfExistingContacts() != 0){
			allFieldsB.append(COMPONENT_ITEM_SEPARATOR);
			allFieldsB.append(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS);
			allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR);
			allFieldsB.append(getNumberOfExistingContacts() );
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
		return ContactSetVariables.getInstance().getValuesForItemType(idByValue);

	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.creator.components.AbstractComponent#getVariables()
	 */
	public AbstractVariables getVariables(){
		return ContactSetVariables.getInstance();
	}		
}
