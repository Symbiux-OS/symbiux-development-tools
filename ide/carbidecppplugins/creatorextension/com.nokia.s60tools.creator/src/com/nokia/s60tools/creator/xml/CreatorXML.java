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
 
 
package com.nokia.s60tools.creator.xml;

import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.xml.transform.TransformerException;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.IComponentServices;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.calendar.Calendar;
import com.nokia.s60tools.creator.components.calendar.CalendarValue;
import com.nokia.s60tools.creator.components.calendar.CalendarVariables;
import com.nokia.s60tools.creator.components.contact.Contact;
import com.nokia.s60tools.creator.components.contact.ContactGroup;
import com.nokia.s60tools.creator.components.contact.ContactGroupValue;
import com.nokia.s60tools.creator.components.contact.ContactSet;
import com.nokia.s60tools.creator.components.filetype.FileType;
import com.nokia.s60tools.creator.components.filetype.FileTypeVariables;
import com.nokia.s60tools.creator.components.messaging.Message;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.editors.CreatorScriptEditor;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;
import com.nokia.s60tools.util.resource.FileUtils;
import com.nokia.s60tools.util.xml.XMLIndentor;

/**
 * Creator class representing metadata in Editor area as well as Creator XML file in
 * hard drive. Use {@link CreatorXML#toString()} to get XML String representing this metadata file. 
 * 
 * This class is quite opposite for Creator XML parsing class {@link CreatorXMLParser}.
 * 
 * When user is updating values in UI and using 
 * {@link CreatorScriptEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)} 
 * or {@link CreatorScriptEditor#doSaveAs()} functionality, 
 * update corresponding values from UI to {@link CreatorXML} and get XML data to save
 * by using {@link CreatorXML#toString()}.
 *
 */
public class CreatorXML {

	/**
	 * New line "\r\n"
	 */
	public static final String NEW_LINE = "\r\n";

	/**
	 * Header for XML file
	 */
	private static final String XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\" ?>" +NEW_LINE;
	
	/**
	 * Value in version attributes when not set
	 */
	public static final String NOT_SET = "";
	
	/**
	 * Current XML data version. Only change when XML format is changing.
	 * NOTE: When XML format is changed, also backward compatibility issues
	 * should be taken care of!
	 */
	public static final String DATA_VERSION = "1.0";
	
	/**
	 * Unique ID for new with wizard created metadata xml file.
	 * When file content is just this, UI will know that it's a new file. 
	 */
	public static final String NEW_API_CREATOR_FILE_UID = "com.nokia.s60tools.creator.new_file";


	
	
	/**
	 * Name of this XML file in file system
	 */
	private String fileName;


	/**
	 * Component services provider
	 */
	private IComponentServices componentServices;
	
	/**
	 * Public construction
	 * @param componentServices
	 */
	public CreatorXML(IComponentServices componentServices) {		
		this.componentServices = componentServices;
		this.fileName = NOT_SET;
	}	
		

	/**
	 * Get name of this XML file
	 * @return
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * Set name of this XML file
	 * @param fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	
	/**
	 * Returns indented XML string representing components given through <{@link IComponentServices}.
	 */
	public String toString() {

		String notIntendedXML = toXMLString();
		
		try {
			String intendedXML;
			intendedXML = XMLIndentor.indentXML(notIntendedXML, FileUtils.ENCODING_TYPE_UTF_8, XMLIndentor.DEFAULT_INDENT_NUMBER);
			return intendedXML;
		} catch (UnsupportedEncodingException e) {
			logErrorMessage(e);			
		} catch (TransformerException e) {
			logErrorMessage(e);			
		}
		return notIntendedXML;
	}

	private void logErrorMessage(Exception e) {
		e.printStackTrace();		
		CreatorEditorConsole.getInstance().println(
				"Error when indenting XML, error was: " +e
				+". File: '"  +getFileName() +"' was not intended.",
				CreatorEditorConsole.MSG_ERROR);
	}
	
	/** 
	 * Get this object as XML String.
	 * @return a XML String representing this Creator file
	 */
	private String toXMLString(){
		
		
		StringBuffer b = new StringBuffer();
		
		b.append(XML_HEADER);
		b.append("<creatorscript version=\"1.0\">");
		
		b.append(getAllComponentsAsXML());
		
		b.append("</creatorscript>");
	
		return b.toString();
	}
	
	
	/**
	 * Looping through components and creating XML
	 * @return XML as StringBuffer
	 */
	private StringBuffer getAllComponentsAsXML() {

		StringBuffer b = new StringBuffer();

		Set<String> componentKeys = componentServices.getComponents().keySet();
				
		//looping through all component types
		for (Iterator<String> compKeysIt = componentKeys.iterator(); compKeysIt
				.hasNext();) {
			String componentTypeKey = (String) compKeysIt.next();

			Vector<AbstractComponent> componentsInOneType = componentServices.getComponents().get(componentTypeKey);
			
			//Some items needs to be handled differently because of special elements/attributes.
			//Messages and calendars will be handled differently later in private void addOneComponentToXML(StringBuffer b, AbstractComponent comp)
			if(componentTypeKey.equals(CreatorEditorSettings.TYPE_CONTACT)){				
				//All contacts to XML
				addContactsToXML(b, componentsInOneType);				
			}
			else if(componentTypeKey.equals(CreatorEditorSettings.TYPE_CONTACT_SET)){				
				//All contacts to XML
				addEmptyContactSetsToXML(b, componentsInOneType);
			}
			else if(componentTypeKey.equals(CreatorEditorSettings.TYPE_CONTACT_GROUP)){				
				//All contacts to XML
				addContactGroupsToXML(b, componentsInOneType);
			}		
			else{
				//All all components in type of "componentTypeKey" to XML
				addOneTypeOfComponentsToXML(b, componentsInOneType);
			}
		}// for componentKeys

		return b;
	}



	/**
	 * Add all components in one type to XML
	 * @param b
	 * @param componentsInOneType
	 */
	private void addOneTypeOfComponentsToXML(StringBuffer b,
			Vector<AbstractComponent> componentsInOneType) {
		//Looping through all components at one type
		
		for (Iterator<AbstractComponent> iterator = componentsInOneType.iterator(); iterator
				.hasNext();) {
			AbstractComponent comp = (AbstractComponent) iterator.next();
			
			addOneComponentToXML(b, comp);

		}//for componentsInOneType
	}
	
	
	/**
	 * Add contacts to XML
	 * @param b
	 * @param componentsInOneType
	 */
	private void addContactsToXML(StringBuffer b,
			Vector<AbstractComponent> componentsInOneType) {
		//Looping through all components at one type
		
		//Table to store contacts which belongs to set
		Hashtable<AbstractComponent, Vector<AbstractComponent>> contactSetsWithContacts = 
			new Hashtable<AbstractComponent, Vector<AbstractComponent>>();

		//looping through all contacts
		for (Iterator<AbstractComponent> iterator = componentsInOneType.iterator(); iterator
				.hasNext();) {
			AbstractComponent comp = (AbstractComponent) iterator.next();
			Contact con = (Contact)comp;
			
			//if that contact has a reference to contact set
			if(con.hasReferenceToAnotherComponent()){
				//get vector where is all contacts belongs to this set
				Vector<AbstractComponent> v = contactSetsWithContacts.get(con.getReferenceToAnotherComponent());
				if(v == null){
					//if this was first contact in that set, creating new vector
					v = new Vector<AbstractComponent>();
				}
				v.add(con);//adding this contact to that contact set
				contactSetsWithContacts.put(con.getReferenceToAnotherComponent(), v);
			}
			//If there is no reference to set, just adding this contact like any other component
			else{			
				addOneComponentToXML(b, con);
			}

		}//for componentsInOneType
		
		if(contactSetsWithContacts.size() > 0){
			Set<AbstractComponent> sets = contactSetsWithContacts.keySet();
			for (Iterator<AbstractComponent> iterator = sets.iterator(); iterator.hasNext();) {
				AbstractComponent comp = (AbstractComponent) iterator.next();
				ContactSet set = (ContactSet)comp;
				b.append("<contact-set id=\"");
				b.append(set.getId());
				b.append("\"" );
				//If there is added number of existing contacts
				if(set.getNumberOfExistingContacts() != 0){
					b.append(" ");
					b.append(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID);
					b.append("=\"");
					b.append(set.getNumberOfExistingContacts() );
					b.append("\"");
				}					
				
				b.append(">");
				
				Vector<AbstractComponent> contactsInSet = contactSetsWithContacts.get(comp);
				for (Iterator<AbstractComponent> iterator2 = contactsInSet.iterator(); iterator2
						.hasNext();) {
					AbstractComponent con = (AbstractComponent) iterator2
							.next();
					addOneComponentToXML(b, con);
					
				}
				b.append("</contact-set>");
			}
		}
	}
	
	/**
	 * Adding contact sets what is not referenced by any other component
	 * @param b
	 * @param v
	 */
	private void addContactGroupsToXML(StringBuffer b, Vector<AbstractComponent> v) {		
		
		for (Iterator<AbstractComponent> iterator = v.iterator(); iterator.hasNext();) {

			ContactGroup group = (ContactGroup) iterator
					.next();
			
			b.append("<");
			b.append(group.getXMLElementName());
			
			if(group.getName() != null && group.getName().trim().length() > 0){
				b.append(" ");
				b.append(AbstractComponent.NAME_PARAMETER_ID);
				b.append("=\"");
				b.append(group.getName() );
				b.append("\"");
			}			
			
			b.append("><members>");

			//adding elements

			Set<String> keys = group.getKeys();
			//Looping through all items in one type of component
			for (Iterator<String> iterator2 = keys.iterator(); iterator2
					.hasNext();) {
				String key = (String) iterator2.next();
				String xmlKey = group.getIdByValue(key);

				Vector<AbstractValue> value = group.getAttribute(key);

				//Looping through all values of this type of item
				for (Iterator<AbstractValue> valueIt = value.iterator(); valueIt.hasNext();) {
					ContactGroupValue val = (ContactGroupValue) valueIt.next();
					//adding element data
					addContactGroupValueData(b, xmlKey, val);
				}			

			}

		
			b.append("</members>");

			b.append("</");
			b.append(group.getXMLElementName());
			b.append(">");
			
				
		}
	}	
	
	/**
	 * Adding element data to contact group
	 * @param b
	 * @param xmlKey
	 * @param val
	 */
	private void addContactGroupValueData(StringBuffer b, String xmlKey, ContactGroupValue val) {

		//If its not a random value, there is real value
		
		b.append("<");
		b.append(xmlKey);
		
		if(val.getAmount() > 0){
			b.append(" maxamount=\"");
			b.append(val.getMaxAmount());
			b.append("\"");
		}

		b.append(" id=\"");
		b.append(val.getId());//Value must be valid
		b.append("\"");
		b.append("/>");			

	}	
	
	
	
	/**
	 * Adding contact sets what is not referenced by any other component
	 * @param b
	 * @param v
	 */
	private void addEmptyContactSetsToXML(StringBuffer b, Vector<AbstractComponent> v) {
		
		
		for (Iterator<AbstractComponent> iterator = v.iterator(); iterator.hasNext();) {

			ContactSet set = (ContactSet) iterator
					.next();
			
			//If there is no references to this contact set by any other component
			//then an empty contact set will be created.
			//But if there is some components that references to this contact-set
			//when creating these components, also contact sets will be added.
			if(!componentServices.isReferencedByAnotherComponent(set)){

				int id = set.getId();
				
				if(id == AbstractComponent.NULL_ID){			
					b.append("<contact-set/>");
				}
				else{			
					b.append("<contact-set id=\"" );
					b.append(id);
					b.append("\"");
					
					//If there is added number of existing contacts
					if(set.getNumberOfExistingContacts() != 0){
						b.append(" ");
						b.append(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID);
						b.append("=\"");
						b.append(set.getNumberOfExistingContacts() );
						b.append("\"");
					}										
					
					b.append(">" );
					
					b.append("</contact-set>");
				}
				
			}			
		}
	}		

	/**
	 * Adding a component to xml
	 * @param b
	 * @param comp
	 */
	private void addOneComponentToXML(StringBuffer b, AbstractComponent comp) {
		int amount = comp.getAmount();
		//e.g. <calendar>
		if (amount == 0 || amount == 1) {
			b.append("<");
			b.append(comp.getXMLElementName());
			addAdditionalComponentParameters(comp, b);
			b.append(">");
		} else {
			b.append("<");
			b.append(comp.getXMLElementName());
			b.append(" amount=\"");
			b.append(amount);
			b.append("\"");
			addAdditionalComponentParameters(comp, b);
			b.append(">");
		}
		b.append("<fields>");

		//Calendar
		if (comp instanceof Calendar){
			addOneCalendarComponentData(b, (Calendar)comp );
		}
		//Messages
		else if (comp instanceof Message){
			addOneMessageComponentData(b, (Message)comp );
		}		
		//FileType handling varies about default handling because of DRM Support 
		else if (comp instanceof FileType){
			addOneFileTypeComponentData(b, (FileType)comp );
		}			
		else{				
			addOneAbstractComponentData(b, comp);
		}

		b.append("</fields>");

		b.append("</");
		b.append(comp.getXMLElementName());
		b.append(">");
	}

	/**
	 * FileType handling, because of DRM in ui does not match right to XML needed special handling
	 * @param b
	 * @param comp
	 */
	private void addOneFileTypeComponentData(StringBuffer b, FileType comp) {

		Map<String, Vector<AbstractValue>> encValues = new LinkedHashMap<String, Vector<AbstractValue>>();
		Set<String> keys = comp.getKeys();
		String encType = null;
		//Looping through all items in one type of component
		for (Iterator<String> iterator2 = keys.iterator(); iterator2
				.hasNext();) {
			String key = (String) iterator2.next();
			String xmlKey = comp.getIdByValue(key);

			Vector<AbstractValue> value = comp.getAttribute(key);
			if(FileTypeVariables.isEncryptionValue(key)){
				encValues.put(xmlKey, value);
			}
			else if(xmlKey.equalsIgnoreCase(FileTypeVariables.ENCRYPTION_TYPE_XML)){
				//Looping through all values of this type of item
				for (Iterator<AbstractValue> valueIt = value.iterator(); valueIt.hasNext();) {
					AbstractValue val =  valueIt.next();
					encType = val.getValue();//If there is many values, first will remain
					break;///all other values will be skipped
				}				
			}
			else{
				//Looping through all values of this type of item
				for (Iterator<AbstractValue> valueIt = value.iterator(); valueIt.hasNext();) {
					AbstractValue val =  valueIt.next();
					//adding element data
					addValueData(b, xmlKey, val);
				}			
			}
			
		}
		try{
		//If encryption is set
		if(encType != null){
			b.append("<");
			b.append(FileTypeVariables.ENCRYPTION_TYPE_XML);
			b.append(" type=\"");
			b.append(encType);
			b.append("\">");
			
			
			//Checking DRM-CD attributes
			///Should we check that its CD type of encryption? Possibility to loss data then.
			if(!encValues.isEmpty()){
				StringBuffer encB = addOneEncryptionToFileType(FileTypeVariables.PRINT_TYPE , FileTypeVariables.PRINT_KEYS, encValues);
				if(encB != null){
					b.append(encB);
				}
				
				encB = addOneEncryptionToFileType(FileTypeVariables.DISPLAY_TYPE , FileTypeVariables.DISPLAY_KEYS, encValues);
				if(encB != null){
					b.append(encB);
				}
				
				encB = addOneEncryptionToFileType(FileTypeVariables.PLAY_TYPE , FileTypeVariables.PLAY_KEYS, encValues);
				if(encB != null){
					b.append(encB);
				}
				
				encB = addOneEncryptionToFileType(FileTypeVariables.EXECUTE_TYPE , FileTypeVariables.EXECUTE_KEYS, encValues);
				if(encB != null){
					b.append(encB);
				}								
			}
			
			b.append("</");
			b.append(FileTypeVariables.ENCRYPTION_TYPE_XML);
			b.append(">");			
			
		}		
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * Creates one type of encryption and returns it
	 * @param type
	 * @param keys
	 * @param encValues
	 * @return buffer where <right type=[given type]>... or <code>null</code> 
	 * if there was no type values 
	 * for that type in Map given
	 */
	private StringBuffer addOneEncryptionToFileType(String type, String [][]keys,
			Map<String, Vector<AbstractValue>> encValues) {
		
		StringBuffer encB = new StringBuffer();

		boolean wasItems = false;
		
		String rightType = "<right type=\"";
		String close = "\">";
		String rightTypeClose = "</right>";
		encB.append(rightType);
		encB.append(type);
		encB.append(close);
		
		for (int i = 0; i < keys[0].length; i++) {
			Vector<AbstractValue> values = encValues.get(keys[0][i]);
			if(values==null){
				continue;
			}
			for (Iterator<AbstractValue> iterator2 = values.iterator(); iterator2.hasNext();) {
				AbstractValue value = (AbstractValue) iterator2.next();
				addValueData(encB, keys[1][i], value);				
				wasItems = true;
			}					
		}
		encB.append(rightTypeClose);
		
		if(wasItems){
			return encB;
		}else{
			return null;
		}
	}


	/**
	 * Adds one message to XML
	 * @param b
	 * @param comp
	 */
	private void addOneMessageComponentData(StringBuffer b, Message comp) {
		Set<String> keys = comp.getKeys();
		//Looping through all items in one type of component
		for (Iterator<String> iterator2 = keys.iterator(); iterator2
				.hasNext();) {
			String key = (String) iterator2.next();
			String xmlKey = comp.getIdByValue(key);

			Vector<AbstractValue> value = comp.getAttribute(key);

			//Looping through all values of this type of item
			for (Iterator<AbstractValue> valueIt = value.iterator(); valueIt.hasNext();) {
				AbstractValue val =  valueIt.next();
				//adding element data when component is a contact set reference 
				if(CreatorEditorSettings.isContactSetReference(key))
				{
					b.append("<");
					String idByValue = comp.getIdByValue( val.getType() );
					b.append( idByValue );//<to> or <from>
					b.append(">");
					addContactSetReferenceToBuffer(b, val);
					b.append("</");
					b.append( idByValue );
					b.append(">");
					
				}
				//Add all other values than contact set references
				else {//if(key.equals(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE)){
					addValueData(b, xmlKey, val);
				}	
			}			
		}
	}

	/**
	 * Add additional parameters for component
	 * @param comp
	 * @param b
	 */
	private void addAdditionalComponentParameters(AbstractComponent comp,
			StringBuffer b) {

		if(comp.hasAdditionalParameters()){
			
			Set<String> keys = comp.getAdditionalParameters().keySet();
			for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
				String type = (String) iterator.next();
				String value = comp.getAdditionalParameter(type);

				b.append(" ");
				b.append(type);
				b.append("=\"");
				b.append(value);
				b.append("\"");
				
			}
		}		
	}

	/**
	 * Add XML data for one component inside of <fields></fields>
	 * @param b
	 * @param comp
	 */
	private void addOneAbstractComponentData(StringBuffer b, AbstractComponent comp) {
		
		
		Set<String> keys = comp.getKeys();
		//Looping through all items in one type of component
		for (Iterator<String> iterator2 = keys.iterator(); iterator2
				.hasNext();) {
			String key = (String) iterator2.next();
			String xmlKey = comp.getIdByValue(key);

			Vector<AbstractValue> value = comp.getAttribute(key);

			//Looping through all values of this type of item
			for (Iterator<AbstractValue> valueIt = value.iterator(); valueIt.hasNext();) {
				AbstractValue val =  valueIt.next();
				//adding element data
				addValueData(b, xmlKey, val);
			}			

		}
	}
	
	/**
	 * Add XML data for one Calendar component inside of <fields></fields>
	 * @param b
	 * @param comp
	 */
	private void addOneCalendarComponentData(StringBuffer b, Calendar comp) {

		//Store all attendees found to here
		Vector <CalendarValue> attendees = new Vector<CalendarValue>();
		Vector <AbstractValue> contactSetReferences = new Vector<AbstractValue>();
		String xmlKeyForAttendees = CalendarVariables.ATTENDEE_XML_ELEMENT;
		
		Set<String> keys = comp.getKeys();
		//Looping through all items in one type of component
		for (Iterator<String> iterator2 = keys.iterator(); iterator2
				.hasNext();) {
			String key = (String) iterator2.next();
			String xmlKey = comp.getIdByValue(key);

			Vector<AbstractValue> value = comp.getAttribute(key);
			
			//Looping through all values of this type of item
			for (Iterator<AbstractValue> valueIt = value.iterator(); valueIt.hasNext();) {
				AbstractValue val =  valueIt.next();
				CalendarValue calVal = (CalendarValue)val;

				
				//Adding contact set references to attendees
				
				//If its not a contact set reference or attendee
				if(!CreatorEditorSettings.isContactSetReference(key) 
						&& !calVal.isAttendee())
				{
					addValueData(b, xmlKey, calVal);
				}
				//if it is contact set reference
				else if(CreatorEditorSettings.isContactSetReference(key)){
					contactSetReferences.add(calVal);
				}	
				//otherwise it's attendee
				else{					
					attendees.add(calVal);
				}				
			}			
		}
		//Handle attendees and contact set references
		if(attendees.size() > 0 || contactSetReferences.size() > 0 ){
			addAttendeesToCalendar(b, xmlKeyForAttendees, attendees, contactSetReferences);
		}
	}	
	
	

	/**
	 * Add attendees to calendar
	 * @param b
	 * @param xmlKey
	 * @param attendees
	 * @param contactSetReferences
	 */
	private void addAttendeesToCalendar(StringBuffer b, String xmlKey,
			Vector<CalendarValue> attendees, Vector<AbstractValue> contactSetReferences) {
		
		b.append("<attendees>");
		
		addContactSetReferencesToBuffer(b, contactSetReferences);
		
		for (Iterator<CalendarValue> iterator = attendees.iterator(); iterator.hasNext();) {
			CalendarValue calVal = (CalendarValue) iterator.next();
			
			b.append("<");
			b.append(xmlKey);//attendee
			
			if(calVal.getAmount() > 0){
				b.append(" amount=\"");
				b.append(calVal.getAmount());
				b.append("\"");
			}
			
			if(!calVal.isRandom()){
				b.append(">");//close attendee element

				//Adding all attendee attributes as elements to XML
				if(calVal.getAttendeeEmail() != null){
					b.append("<");
					b.append(CalendarVariables.ATTENDEEEMAIL_XML_ELEMENT);
					b.append(">");
					b.append(calVal.getAttendeeEmail());
					b.append("</");
					b.append(CalendarVariables.ATTENDEEEMAIL_XML_ELEMENT);
					b.append(">");					
				}
				if(calVal.getAttendeeCommonName() != null){
					b.append("<");
					b.append(CalendarVariables.ATTENDEECOMMONNAME_XML_ELEMENT);
					b.append(">");
					b.append(calVal.getAttendeeCommonName());
					b.append("</");
					b.append(CalendarVariables.ATTENDEECOMMONNAME_XML_ELEMENT);
					b.append(">" );					
				}				
				
				
				b.append("</");
				b.append(xmlKey);//attendee
				b.append(">");
			}
			//Else its a random value, close element "<element/>" and set possible random type attribute
			else{
				if(calVal.getModeType() == ModeTypes.RandomTypeMaxLength){
					b.append(" randomlength=\"max\"");
				}
				else if(calVal.getModeType() == ModeTypes.RandomTypeUserDefinedLength){
					b.append(" randomlength=\"");
					b.append(calVal.getRandomValueLenght());
					b.append("\"");
				}				

				b.append("/>");				
			}			
		}
		b.append("</attendees>");
	}

	/**
	 * Adds contact set references to XML
	 * @param b
	 * @param contactSetReferences
	 */
	private void addContactSetReferencesToBuffer(StringBuffer b,
			Vector<AbstractValue> contactSetReferences) {
		//Contact set references
		for (Iterator<AbstractValue> iterator = contactSetReferences.iterator(); iterator
				.hasNext();) {
			AbstractValue set = (AbstractValue) iterator.next();
			addContactSetReferenceToBuffer(b, set);
		}
	}

	/**
	 * Adds contact set reference to XML
	 * @param b
	 * @param val
	 */
	private void addContactSetReferenceToBuffer(StringBuffer b,
			AbstractValue val) {
		b.append("<");
		b.append(CreatorEditorSettings.TYPE_CONTACT_SET_REFERENCE_XML_ELEMENT);
		b.append(" id=\"");
		b.append(val.getId());
		b.append("\"");
		
		if(val.getMaxAmount() > 0){
			b.append(" maxamount=\"");
			b.append(val.getMaxAmount());
			b.append("\"");
			 	
		}
		b.append("/>" );
	}

	/**
	 * Add one value data to XML
	 * @param b
	 * @param xmlKey
	 * @param val
	 */
	private void addValueData(StringBuffer b, String xmlKey, AbstractValue val) {

			//If its not a random value, there is real value
			
			b.append("<");
			b.append(xmlKey);
			
			if(val.getAmount() > 0){
				b.append(" amount=\"");
				b.append(val.getAmount());
				b.append("\"");
			}
			//adding incvalueforeachcopy parameter
			if(val.getModeType() == ModeTypes.ModeTypeIncValueForEachCopy){
				b.append(" ");
				b.append(AbstractComponent.INCVALUEFOREACHCOPY_PARAMETER_ID);
				b.append("=\"");					
				b.append(AbstractComponent.TRUE);
				b.append("\"");
			}				
			if(!val.isRandom()){
				b.append(">");
				b.append(val.getValue());
				b.append("</");
				b.append(xmlKey);
				b.append(">");	
			}
			//Else its a random value, close element "<element/>" and set possible random type attribute
			else{
				if(val.getModeType() == ModeTypes.RandomTypeMaxLength){
					b.append(" randomlength=\"max\"");
				}
				else if(val.getModeType() == ModeTypes.RandomTypeUserDefinedLength){
					b.append(" randomlength=\"");
					b.append(val.getRandomValueLenght());
					b.append("\"");
				}

				b.append("/>");				
			}

	}
	

}
