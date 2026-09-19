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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.contact.ContactValue;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;




/**
 * Abstract entry base class that contains only id for the
 * entry and method that are common for all entry types.
 * 
 * To be subclassed in order to create concrete entry types.
 */
public abstract class AbstractComponent {
	
	/**
	 * White space
	 */
	public static final String WHITE_SPACE = " ";
	/**
	 * Separator for components ("|")
	 */
	public static final String COMPONENT_TYPE_SEPARATOR = " | ";
	/**
	 * Separator for items (comma ",")
	 */
	public static final String COMPONENT_ITEM_SEPARATOR = ", ";
	/**
	 * Separator for between component label and value ("=")
	 */
	public static final String COMPONENT_LABEL_VALUE_SEPARATOR = "=";
	
	/**
	 * ID what is not set
	 */
	public static final int NULL_ID = 0;
	/**
	 * If Max Occur for item is not defined, it's unbounded (infinite).
	 * Value for  is <code>-1</code>.
	 */
	public static final int MAX_OCCURS_UNBOUNDED = -1;
	/**
	 * Id for type
	 */
	public static final String TYPE_PARAMETER_ID = "type";
	/**
	 * ID for id
	 */
	public static final String ID_PARAMETER_ID = "id";
	/**
	 * ID for name
	 */
	public static final String NAME_PARAMETER_ID = "name";
	/**
	 * incvalueforeachcopy value in XML. 
	 * @see AbstractValue#RANDOM_TEXT_INC_FOR_EACH_COPY
	 */
	public static final String INCVALUEFOREACHCOPY_PARAMETER_ID = "incvalueforeachcopy";
	/**
	 * String value for <code>true</code>
	 */
	public static final String TRUE = "true";
	/**
	 * ID for number of existing contacts 
	 */
	public static final String NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID ="numberofexistingcontacts";
	/**
	 * UI text for Contacts (amount)
	 */
	public static final String NUMBER_OF_EXISTING_CONTACTS = "Contacts (amount)";
	
	/**
	 * UI text for adding existing contacts to contact-set 
	 */
	public static final String NUMBER_OF_EXISTING_CONTACTS_DIALOG_TEXT = "Add existing contacts in device to this Contact-set (amount):";	
	/**
	 * User configurable id for the entry. The entries
	 * are identified by there unique id per search method 
	 * configuration storage.
	 */
	private int id;
	
	/**
	 * Storage for attributes
	 */
	private Map<String, Vector<AbstractValue>> attributes;

	
	/**
	 * Amount
	 */
	private int amount;
	

	/**
	 * If component has additional parameters, like type
	 */
	private Map<String, String>additionalParameters = null;
	
	/**
	 * If component have a reference to another component
	 */
	private AbstractComponent referenceToAnotherComponent = null;
	
	/**
	 * Constructor
	 * @param id Entry id.that is unique per search method. 
	 * @param isSelected Is the entry used for queries by default.
	 */
	protected AbstractComponent(int id){
		this.id = id;
		this.amount = 0;
	}
	

	/**
	 * @return the id for the entry.
	 */
	public int getId() {
		return id;
	}


	
	/**
	 * Notifies storage object that this entry has been modified.
	 * @param eventType Event type of the modification.
	 */
	protected void notifyModification(int eventType){
	}
	

	/**
	 * Get Component type
	 * @return type
	 */
	public abstract String getType();
	
	/**
	 * Get Component type used in XML 
	 * @return type <xs:complexType>
	 */
	public abstract String getXMLElementName();	


	/**
	 * Get all attributes
	 * @return attributes
	 */
	public Map<String, Vector<AbstractValue>> getAttributes() {
		if(attributes == null){
			attributes = new LinkedHashMap<String, Vector<AbstractValue>>();
		}
		return attributes;
	}

	/**
	 * Set all attributes
	 * @param attributes
	 */
	public void setAttributes(Map<String, Vector<AbstractValue>> attributes) {
		this.attributes = attributes;
	}
	
	/**
	 * Set one attribute to attributes
	 * @param key
	 * @param value
	 */
	public void setAttribute(String key, Vector<AbstractValue> value){
		getAttributes().put(key, value);
	}
	
	/**
	 * Get one attribute
	 * @param key
	 * @return value, null if not found or value is empty
	 */
	public Vector<AbstractValue> getAttribute(String key) {		
		return getAttributes().get(key);
	}


	/**
	 * Get component keys (e.g. entry item labels) 
	 * @return a key (label)
	 */
	public Set<String> getKeys() {
		return getAttributes().keySet();
	}


	/**
	 * Check if component is valid or not. 
	 * @return trie od
	 */
	public abstract boolean isValid();
	


	/**
	 * Get amount attribute in component.
	 * <xs:attribute =”amount” use=”optional” type=”xs:positiveInteger”/>
	 * @return amount
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * Set amount attribute in component.
	 * <xs:attribute =”amount” use=”optional” type=”xs:positiveInteger”/>
	 * @param amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	/**
	 * Get number of attributes (items) added to this component
	 * @return count
	 */
	public int getAttributeCount(){
		return getAttributes().size();
	}
	
	/**
	 * Get showable UI value by XML element name
	 * @param id -element name in XML
	 * @return value -element name in UI
	 */
	public abstract String getValueById(String id);	
	
	/**
	 * Get XML element name by showable UI value
	 * @param value -element name in UI
	 * @return id -element name in XML
	 */
	public abstract String getIdByValue (String value);


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
			allFieldsB.append( referenceToAnotherComponent.getType() );
			allFieldsB.append( WHITE_SPACE );
			allFieldsB.append( ID_PARAMETER_ID );			
			allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR );
			allFieldsB.append( referenceToAnotherComponent.getId() );
			allFieldsB.append( COMPONENT_TYPE_SEPARATOR);			
		}
		
		//Amount (how many of this component will be added)
		if(getAmount() > 0){
			allFieldsB.append(" amount=");
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
				AbstractValue val = iterator.next();
	
				allFieldsB.append(itemlabel);
				allFieldsB.append(COMPONENT_LABEL_VALUE_SEPARATOR);
				//If value is random value, showing in UI only short description (<RND>)
				if(val.isRandom()){
					allFieldsB.append(ContactValue.RANDOM_TEXT);
				}else{
										
					//Add value 
					allFieldsB.append(val.getValue());
					//adding incvalueforeachcopy parameter
					if(val.getModeType() == ModeTypes.ModeTypeIncValueForEachCopy){
						allFieldsB.append(" ");
						allFieldsB.append(AbstractValue.RANDOM_TEXT_INC_FOR_EACH_COPY_SHORT);
					}					
				}
				allFieldsB.append(COMPONENT_ITEM_SEPARATOR);									
			}
			
		}
		//deleting last ", " from list
		if(allFieldsB.toString().endsWith(COMPONENT_ITEM_SEPARATOR)){
			allFieldsB.delete(allFieldsB.length()-COMPONENT_ITEM_SEPARATOR.length(), allFieldsB.length());
		}
		return  CreatorEditorSettings.getInstance().replaceEntitiesWithChars(allFieldsB.toString());
	}


	/**
	 * Get additional parameters
	 * @return additionalParameters
	 */
	public Map<String, String> getAdditionalParameters() {
		if(additionalParameters == null){
			additionalParameters = new LinkedHashMap<String, String>();
		}		
		return additionalParameters;
	}


	/**
	 * Set additional parameters
	 * @param additionalParameters
	 */
	public void setAdditionalParameters(Map<String, String> additionalParameters) {
		this.additionalParameters = additionalParameters;
	}
	

	/**
	 * Add one additional parameter
	 * @param type
	 * @param value
	 */
	public void addAdditionalParameter(String type, String value) {
		getAdditionalParameters().put(type, CreatorEditorSettings.getInstance().replaceForbiddenChars(value));
	}
	
	/**
	 * Get additional parameter
	 * @param type
	 * @return value, or null if not exist
	 */
	public String getAdditionalParameter(String type) {
		return getAdditionalParameters().get(type);
	}	
	
	/**
	 * Does this component have some additional parameters
	 * @return true if this component has some additional parameter, false otherwise
	 */
	public boolean hasAdditionalParameters(){
		return additionalParameters != null && getAdditionalParameters().size() > 0 ? true : false;
	}

	/**
	 * Removes all additional parameters
	 * Note: attributes is not going to be removed. Use {@link AbstractComponent#removeAllAttributes()}
	 */
	public void removeAdditionalParameters() {
		additionalParameters = new LinkedHashMap<String, String>();
	}

	/**
	 * Set this contact belong to contact set
	 * @param reference
	 */
	public void setReferenceToAnotherComponent(AbstractComponent reference) {
		this.referenceToAnotherComponent = reference;
	}


	/**
	 * Get contact set which this contact belongs to
	 * @return
	 */
	public AbstractComponent getReferenceToAnotherComponent() {
		return referenceToAnotherComponent;
	}
	
	/**
	 * 
	 * @return true if component has a reference to another component
	 */
	public boolean hasReferenceToAnotherComponent(){
		return referenceToAnotherComponent != null;
	}


	/**
	 * Set component id
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}


	/**
	 * Removes all existing attributes.
	 * Note: Additional parameters is not going to be removed. Use {@link AbstractComponent#removeAdditionalParameters()}
	 */
	public void removeAllAttributes() {
		attributes = new LinkedHashMap<String, Vector<AbstractValue>>();		
	}


	/**
	 * Get fixed values for itemType
	 * @param itemType
	 * @return possible values for item or <code>null</code> if item has no fixed values
	 */
	public abstract String[] getValuesForItemType(String itemType);


	/**
	 * Check if type is supporting <code>incvalueforeachcopy</code> parameter.
	 * Default implementation return allways <code>false</code>, overwrite in component if 
	 * real implementation is needed, in other words, if in component there is some
	 * type that supports <code>incvalueforeachcopy</code> parameter. 
	 * @param type
	 * @return <code>true</code> if <code>incvalueforeachcopy</code> is supported <code>false</code> othewise.
	 */
	public boolean isTypeSupportingIncValueForEachCopy(String type) {
		
		return false;
		
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
		return MAX_OCCURS_UNBOUNDED;
	}


	/**
	 * Get variables for that component.
	 * @return variables of that component type.
	 */
	public abstract AbstractVariables getVariables();


	/**
	 * Check if this type and value has some limitations to other values.
	 * 
	 * @see AbstractComponent#isTypeDisabledByTypeAndValue(String, String, String)
	 * 
	 * @param type
	 * @param value
	 * @return <code>false</code> as default. Extending components may overwrite this if needed.
	 */
	public boolean hasTypeLimitationsForOtherValues(String type, String value) {		
		return false;
	}
	
	/**
	 * Check that is a type disabled by some other type and value.
	 * 
	 * @see AbstractComponent#hasTypeLimitationsForOtherValues(String, String)
	 * 
	 * @param selectedType type that may disable some other type
	 * @param selectedValue value of type that may disable some other type
	 * @param typeToDisable type to be disabled by selected type and value
	 * @return <code>false</code> as default. Extending components may overwrite this if needed.
	 */
	public boolean isTypeDisabledByTypeAndValue(String selectedType, String selectedValue, String typeToDisable) {
		return false;
	}	
	
	
}
