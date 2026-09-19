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

import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.nokia.s60tools.creator.components.AbstractComponent;
import com.nokia.s60tools.creator.components.AbstractValue;
import com.nokia.s60tools.creator.components.AbstractVariables;
import com.nokia.s60tools.creator.components.ComponentServices;
import com.nokia.s60tools.creator.components.Components;
import com.nokia.s60tools.creator.components.AbstractValue.ModeTypes;
import com.nokia.s60tools.creator.components.calendar.CalendarValue;
import com.nokia.s60tools.creator.components.filetype.FileTypeValue;
import com.nokia.s60tools.creator.components.filetype.FileTypeVariables;
import com.nokia.s60tools.creator.components.messaging.MessageValue;
import com.nokia.s60tools.creator.components.messaging.MessageVariables;
import com.nokia.s60tools.creator.core.CreatorEditorSettings;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;

/**
 * Class for parsing Creator XML Script file to Object model (Components)
 */
public class CreatorXMLParser {

	public CreatorXMLParser() {
	}

	CreatorXMLHandler handler = null;

	/**
	 * Was there errors when parsing (e.g. component type that is not supported)
	 * 
	 * @return true if there was some errors, false otherwise
	 */
	public boolean wasErrors() {
		return handler.getErrors().trim().length() == 0 ? false : true;
	}

	/**
	 * Get errors
	 * 
	 * @return description for errors or empty string if not any
	 */
	public String getErrors() {
		return handler.getErrors();
	}

	/**
	 * 
	 * @param fileName
	 *            file to be parsed
	 * @return CreatorXML object with all elements and attributes set
	 * @throws SAXException
	 * @throws IOException
	 * @throws CreatorScriptNotValidException
	 *             if parsed data was not valid
	 */
	public Components parse(InputStream inStreamAsUTF8)
			throws CreatorScriptNotValidException {
		XMLReader reader;
		Components components = null;
		try {
			handler = new CreatorXMLHandler();
			reader = XMLReaderFactory.createXMLReader();

			reader.setContentHandler(handler);
			CreatorXMLErrorHandler errorHandler = new CreatorXMLErrorHandler();
			reader.setErrorHandler(errorHandler);

			InputSource in = new InputSource();			
			in.setByteStream(inStreamAsUTF8);

			reader.parse(in);

			components = handler.getComponents();

			if (components != null) {
				return components;
			} else {
				throw new CreatorScriptNotValidException(handler.getErrors());
			}

		} catch (SAXException e) {
			e.printStackTrace();
			CreatorEditorConsole.getInstance().println(
					"SAXException on parsing: " + e.toString());
			throw new CreatorScriptNotValidException(e.getMessage());
		} catch (FileNotFoundException e) {
			CreatorEditorConsole.getInstance().println(
					"FileNotFoundException on parsing: " + e.toString());
			e.printStackTrace();
			throw new CreatorScriptNotValidException(e.getMessage());
		} catch (IOException e) {
			CreatorEditorConsole.getInstance().println(
					"IOException on parsing: " + e.toString());
			e.printStackTrace();
			throw new CreatorScriptNotValidException(e.getMessage());
		}

	}

	/**
	 * Error handler implementation
	 */
	public class CreatorXMLErrorHandler implements ErrorHandler {

		public void error(SAXParseException e) throws SAXException {
			CreatorEditorConsole.getInstance().println(
					"SAXParseException (error) on parsing: " + e.toString());

		}

		public void fatalError(SAXParseException e) throws SAXException {
			CreatorEditorConsole.getInstance().println(
					"SAXParseException (fatal) on parsing: " + e.toString());
		}

		public void warning(SAXParseException e) throws SAXException {
			CreatorEditorConsole.getInstance().println(
					"SAXParseException (warning) on parsing: " + e.toString());
		}

	}

	// //////////////////////////////////////////////////////////////////
	// Event handlers.
	// //////////////////////////////////////////////////////////////////

	/**
	 * Handler implementaion
	 */
	public class CreatorXMLHandler extends DefaultHandler {



		private static final String AMOUNT = "amount";

		private static final String CREATORSCRIPT = "creatorscript";

		private static final String MEMBERS = "members";

		// Local variables and flags

		private static final String FIELDS = "fields";

		// flags to know depth
		private int depth = 0;

		private boolean creatorscript = false;
		private boolean fields = false;
		private boolean component = false;
		private boolean subComponent = false;
		private boolean item = false;
		private boolean subitem = false;
		private String subItemBaseName = "";
		private Map<String, String> subItemMap;
		Stack<String> elementNames = new Stack<String>();

		private AbstractComponent aComponent;
		private AbstractComponent aSubComponent;
		private String componentType = "";
		private String subComponentType = "";
		// A name of element e.g. "<mobile>" on:
		// <creatorscript version="1.0"><contact><fields><mobile>
		private String itemName = "";
		private Map<String, Vector<AbstractValue>> componentItems;
		private Map<String, Vector<AbstractValue>> subComponentItems;
		private Components components;
		private CharArrayWriter elementContent = new CharArrayWriter();

		private StringBuffer errors = new StringBuffer();
		private int itemAmountParam;
		private ModeTypes randomType;

		private int itemIdParam;

		private int itemMaxAmountParam;
		
		private String rightType = null;
		
		private String incvalueforeachcopy = null;

		private int userDefinedRandomLen = -1;


		public CreatorXMLHandler() {
			components = new Components();

		}

		/**
		 * Get components
		 * @return components parsed
		 */
		public Components getComponents() {
			return components;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#startDocument()
		 */
		public void startDocument() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endDocument()
		 */
		public void endDocument() {
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#startElement(java.lang.String,
		 *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
		 */
		public void startElement(String uri, String name_, String qName,
				Attributes atts) {

			String name = name_.toLowerCase();//Element names is fixed always to be on lowercase
			
			elementNames.push(name);
			 depth++;

			 
			// <creatorscript>
			if (name.equalsIgnoreCase(CREATORSCRIPT)) {
				creatorscript = true;
			}
			// <fields>
			else if (name.equalsIgnoreCase(FIELDS) || name.equalsIgnoreCase(MEMBERS)) {
				fields = true;
			}			 
			// <component>
			else if (creatorscript && !component && !fields && !item) {
				aComponent = ComponentServices
						.getComponentByXMLElementName(name);
				if (aComponent != null) {
					component = true;
					componentType = name;
					componentItems = new LinkedHashMap<String, Vector<AbstractValue>>();
					
					handleComponentParameters(atts, aComponent);

				}// if(aComponent != null){
				else {
					String errMsg = "Could not found component type: " + name + CreatorXML.NEW_LINE;
					CreatorEditorConsole.getInstance().println("Error on parsing: " + errMsg);
					errors.append(errMsg);
				}
			}// if(creatorscript &&!component && !fields && !item){

			//sub component <component><component> e.g. <contact-set><contact>
			else if (creatorscript && component && !fields && !item) {
				//We found a sub component, if component is open, and field is not opened
				aSubComponent = ComponentServices
						.getComponentByXMLElementName(name);
				if (aSubComponent != null) {
					subComponent = true;
					subComponentType = name;
					subComponentItems = new LinkedHashMap<String, Vector<AbstractValue>>();
					handleComponentParameters(atts, aSubComponent);
					

				}// if(aComponent != null){
				else {
					String errMsg = "Could not found component type: " + name + CreatorXML.NEW_LINE;
					CreatorEditorConsole.getInstance().println("Error on parsing: " + errMsg);
					errors.append(errMsg);
				}
			}// if(creatorscript &&!component && !fields && !item){			
			
			// <item>
			else if (creatorscript && component && fields) {

				// subitem, e.g.
				// <creatorscript><calendar><items><attendees><attendee>
				if (item) {
					if (!subitem) {
						subitem = true;
						// subItemsStack = new Stack<AbstractValue>();
						subItemMap = new LinkedHashMap<String, String>();
						subItemBaseName = name;
					}

				}// else if(creatorscript && component && fields && item){
				itemName = name;//ItemName is used with item, newer with the subitem
				item = true;

				//handle params for item
				handleItemParams(atts, aComponent, name);
			}// else if(creatorscript && component && fields


			// If component was unknown (errors on XML) and we are inside of
			// field.
			else if (creatorscript && !component && fields) {
				// Skipping all attributes of this component.
			}
			else{
				errors.append("Unknown error when parsing component: '"+getComponent().getType() +"' with element: '" + uri +"', element local name: '" +name +"', name: '" + qName +"'." );
			}

			elementContent.reset();

		}

		/**
		 * handles item level parameters
		 * @param atts
		 */
		private void handleItemParams(Attributes atts,
				AbstractComponent com, String elementName) {


			boolean foundRandomLen = false;
			int len = atts.getLength();
			for (int i = 0; i < len; i++) {
				String key = atts.getQName(i);
				if(key.equalsIgnoreCase(AMOUNT)){
					//Set amount to component
					itemAmountParam = getIntParameter(key, atts.getValue(key));					
				}
				else if(key.equalsIgnoreCase("randomlength")){
				
					// randomlength parameter 
					handleRandomParam(atts.getValue(key), com);
					foundRandomLen = true;
				}
				else if(key.equalsIgnoreCase(AbstractComponent.ID_PARAMETER_ID)){
				
					itemIdParam = getIntParameter(key, atts.getValue(key));
				}				
				else if(key.equalsIgnoreCase("maxamount")){
					itemMaxAmountParam = getIntParameter(key, atts.getValue(key));
				}
				else if(key.equalsIgnoreCase(AbstractComponent.INCVALUEFOREACHCOPY_PARAMETER_ID)){
					incvalueforeachcopy = atts.getValue(key);
				}				
				
				//Ecryption right type on FileType <encryption type="DRM-CD"><right type="print">
				else if(key.equalsIgnoreCase(FileTypeVariables.TYPE_XML)){

					//Handling encryption type parameter for File
					if(elementName.equalsIgnoreCase(FileTypeVariables.ENCRYPTION_TYPE_XML)){
						handleFileTypeEncryptionTypeParam(atts, com, elementName, key);						
					}
					//It's encryption right type parameter
					else{
						rightType = atts.getValue(key);
					}
					
				}				
				
			}	
			//Always must handle randomlength, even if parameter was not found
			if(!foundRandomLen){
				handleRandomParam(null, com);
			}
		}

		/**
		 * Handle <encryption type=[type]> -parameter from FileType Component
		 * @param atts
		 * @param com
		 * @param elementName
		 * @param key
		 */
		private void handleFileTypeEncryptionTypeParam(Attributes atts,
				AbstractComponent com, String elementName, String key) {
			
			String encryptionType = atts.getValue(key);

			if(com.getType().equals(CreatorEditorSettings.TYPE_FILE)){
				AbstractValue val = ComponentServices
					.getValueByXMLElementName(com.getXMLElementName());							
			
				if(encryptionType != null && !CreatorEditorSettings.isRandomText(encryptionType)){
					val.setValue(encryptionType);								
					val.setRandom(false);
				}else{
					val.setModeType(AbstractValue.ModeTypes.RandomTypeDefaultLength);
				}
				String itemId = com.getValueById(elementName);
				addValueToComponent(val, itemId, componentItems);								
			}
		}

		/**
		 * Handles possible component level parameters
		 * @param atts
		 * @param comp
		 */
		private void handleComponentParameters(Attributes atts, AbstractComponent comp) {
			
			int len = atts.getLength();
			for (int i = 0; i < len; i++) {
				String key = atts.getQName(i);
			
				if(key.equalsIgnoreCase(AMOUNT)){
					//Set amount to component
					handleComponentAmountParameter(atts.getValue(key), comp);
				}
				else if(key.equalsIgnoreCase(AbstractComponent.TYPE_PARAMETER_ID)){
				
					//Required Type parameter for Calendar and Messages
					handleTypeParameter(atts.getValue(key), comp);
				}
				else if(key.equalsIgnoreCase(AbstractComponent.ID_PARAMETER_ID)){
				
					//If there is "id" parameter set that to component id
					handleIdParameter(atts.getValue(key), comp);
				}				
				else if(key.equalsIgnoreCase(AbstractComponent.NAME_PARAMETER_ID)){
					String name = atts.getValue(key);
					if(name != null && name.trim().length() > 0){
						comp.addAdditionalParameter(AbstractComponent.NAME_PARAMETER_ID,
								name.trim());
					}				
				}
				else if(key.equalsIgnoreCase(AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID)){
					//handle numberofexistingcontacts parameter
					handleNumberOfExistingContactsParameters(atts.getValue(key), comp);
				}
				
			}

		}

		/**
		 * Handles AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID and AbstractComponent.NAME_PARAMETER_ID parameters
		 * @param atts
		 * @param comp
		 */
		private void handleNumberOfExistingContactsParameters(String value,
				AbstractComponent comp) {

			int numberOfExistingParams = AbstractComponent.NULL_ID;
			if (value != null && value.trim().length() > 0) {
				try {
					numberOfExistingParams = Integer.parseInt(value);
					comp
							.addAdditionalParameter(
									AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID,
									"" + numberOfExistingParams);

				} catch (Exception e) {
					// Adding error message
					errors
							.append("Invalid '"
									+ "AbstractComponent.NUMBER_OF_EXISTING_CONTACTS_PARAMETER_ID"
									+ "' parameter in component: '"
									+ comp.getType() + "'.");
				}
			}

		}

		/**
		 * Sets id parameter to compoent if valid
		 * @param value
		 * @param com
		 */
		private void handleIdParameter(String value,
				AbstractComponent com) {

			if(value != null && value.trim().length() > 0){
				try {
					int id = Integer.parseInt(value);
					com.setId(id);
				} catch (Exception e) {
					errors.append("Invalid id parameter value: '" +value +"' in component: '" +com.getType() +"'.");
				}
			}
			
		}

		/**
		 * Handles type parameter to component
		 * @param typeParameter
		 * @param comp
		 */
		private void handleTypeParameter(String typeParameter, AbstractComponent comp) {
			if(typeParameter != null && typeParameter.trim().length() > 0){
				comp.addAdditionalParameter(AbstractComponent.TYPE_PARAMETER_ID, typeParameter.trim());
			}
		}

		/**
		 * Handles random parameter
		 * @param randomlength
		 */
		private void handleRandomParam(String randomlength,
				AbstractComponent com) {
			//if randomlenght parameter is set
			if (randomlength != null && randomlength.trim().length() > 0) {

				if (randomlength.equalsIgnoreCase(AbstractVariables.RANDOM_LEN_DEFAULT_XML_VALUE)) {
					randomType = ModeTypes.RandomTypeDefaultLength;
				} else if (randomlength.equalsIgnoreCase(AbstractVariables.RANDOM_LEN_MAX_XML_VALUE)) {
					randomType = ModeTypes.RandomTypeMaxLength;
				}
				else {
					// Set to user defined random length
					try {
						userDefinedRandomLen = Integer
									.parseInt(randomlength);
						randomType = ModeTypes.RandomTypeUserDefinedLength;
					} catch (Exception e) {
						// set to default length when error occurs
						randomType = ModeTypes.RandomTypeDefaultLength;
						errors.append("Invalid randomlength parameter value: '" +randomlength +"' in component: '" +com.getType() +"'.");
						userDefinedRandomLen = -1;
					}

				}
			}
			//randomlength parameter was not set 
			else {
				// NOTE: item might also be default len random type if not
				// set and there is no value
				// that will be taken care of in endElement. In here just
				// setting value so known at it was not set
				randomType = ModeTypes.RandomTypeNotRandom;
			}
		}


		
		
		/**
		 * Get int parameter
		 * @param value
		 * @return 0 if not found or invalid, value as int otherwise
		 */
		private int getIntParameter(String parameterName, String value) {
			int valueInt = 0;
			if (value != null && value.trim().length() > 0) {
				try {
					valueInt = Integer.parseInt(value);
				} catch (Exception e) {
					errors.append("Not supported '" +parameterName +"' value: '" + value
							+ "' attribute in item: '" + itemName
							+ "' in component: '" + componentType + "'");
					valueInt = 0;
				}
			} 
			return valueInt;
		}
		
		
		/**
		 * Handles component amount parameter
		 * @param amount
		 * @param comp
		 */
		private void handleComponentAmountParameter(String amount, AbstractComponent comp) {
			if (amount != null && amount.trim().length() > 0) {
				try {
					int am = Integer.parseInt(amount);
					comp.setAmount(am);
				} catch (Exception e) {
					errors.append("Not supported amout: '" + amount
							+ "' attribute in component: '"
							+ componentType + "'");
				}
			}// if(aComponent != null){
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.xml.sax.helpers.DefaultHandler#endElement(java.lang.String,
		 *      java.lang.String, java.lang.String)
		 */
		public void endElement(String uri, String name_, String qName) {

			String name = name_.toLowerCase();//Element names is fixed always to be on lowercase
			
			
			elementNames.pop();
			depth--;

			
			// </creatorscript>
			if (name.equalsIgnoreCase(CREATORSCRIPT)) {
				creatorscript = false;
			}

			// </fields>
			else if (name.equalsIgnoreCase(FIELDS) || name.equalsIgnoreCase(MEMBERS)) {
				fields = false;
			}			
			// </component>
			else if (creatorscript && component && !subComponent && !fields) {

				if (name.equals(componentType)) {
					aComponent.setAttributes(componentItems);
					// If component is known, e.g. is not type UnknownCompoment
					// (CreatorEditorSettings.TYPE_UNKNOWN)
					if (!aComponent.getType().equals(
							CreatorEditorSettings.TYPE_UNKNOWN)) {
						components.addComponent(aComponent);
					}
					// Else component is UnknownComponent and is added to errors
					else {
						String errMsg = "Component type: '" + componentType
							+ "' not supported. Component with following information was skipped: "
							+ aComponent.toString();
						errors.append(errMsg);
						CreatorEditorConsole.getInstance().println(
								"Error on parsing component, error was; " + errMsg);							

					}
					component = false;
					componentType = "";
					//Making sure that also sub component is closed when component is closed
					subComponent = false;
					subComponentType = "";
				}

			}
			// </component></component> == subcomponent closing
			else if (creatorscript && component && subComponent && !fields) {

				//Collecting subcomponent data, and adding it as reference to component
				if (name.equals(subComponentType)) {
					aSubComponent.setAttributes(subComponentItems);
					// If component is known, e.g. is not type UnknownCompoment
					// (CreatorEditorSettings.TYPE_UNKNOWN)
					if (!aSubComponent.getType().equals(
							CreatorEditorSettings.TYPE_UNKNOWN)) {
						//This might seems to be hazard code, but it goest that way, that 
						//sub component has a reference to component, not vice versa
						aSubComponent.setReferenceToAnotherComponent(aComponent);
						components.addComponent(aSubComponent);
					}
					// Else component is UnknownComponent and is added to errors
					else {
						String errMsg = "Component type: '" + subComponentType
										+ "' under component: '" +componentType 
										+ "' not supported. Component with following information was skipped: "
										+ aSubComponent.toString();
						errors.append(errMsg);
						CreatorEditorConsole.getInstance().println(
								"Error on parsing sub component, error was; " + errMsg);						
					}
					subComponent = false;
					subComponentType = "";
				}

			}			
			
			// </item>
			else if (creatorscript && component && fields && item && !subitem) {
				
				if (name.equals(itemName) ) {					

					// Get real value type by component type
					AbstractValue val = ComponentServices
							.getValueByXMLElementName(getComponent().getXMLElementName());								

					// If there was component type that is not supported
					if (val != null) {
						
						if (val instanceof FileTypeValue && name.equalsIgnoreCase(FileTypeVariables.ENCRYPTION_TYPE_XML)){
							
							//Because of file type encryption is handled in #handleFileTypeEncryptionTypeParam()
							//We skip handling it here. Thats because of encryption has no value, but only parameter
							//and default implementation is that empty element means random value, and there is no random 
							//encryption values. Element is added earlier because of component order in list occurs how they
							//added and it causes other issues to handle first possible elements and later on element parameters
							//(mainly element parameters is handled first, and so its done with encryption parameters also).
							
						}else{
						
							setValueParamsAndSetAsContactSetReference(val, itemName);
	
							// Get Vector where all same named values is stored,
							// e.g. if one contact has many "First names" all is
							// stored to same vector
							String itemId = getComponent().getValueById(itemName);
							if (itemId != null) {
								
								// When found closing point to subitem		
								if(subComponent){
									addValueToComponent(val, itemId, subComponentItems);
								}else{
									addValueToComponent(val, itemId, componentItems);
								}
								
							}					
							else {
								String errMsg = "Element: ' " + itemName
										+ "' in component: '"
										+ getComponent().getXMLElementName()
										+ "' is not supported." +CreatorXML.NEW_LINE;
								errors.append(errMsg);
							}
						}

					}// if(val != null){					
					// Else we found a component value that is not supported.
					else {
						String errMsg = "Could not found values for component: "
								+ getComponent().getType() + CreatorXML.NEW_LINE;
						CreatorEditorConsole.getInstance().println(
								"Error on parsing: " + errMsg);
						errors.append(errMsg);
					}
				}
					
				
				itemName = "";
				item = false;
				itemAmountParam = 0;						

			}// else if(creatorscript && component && fields && item &&  !subitem){
			//<subitem>
			else if (creatorscript && component && fields && item && subitem) {
				
				//E.g. <attendee> in calendar are subitems
				//<calendar type="appointment"><fields><summary>MEETING</summary><attendees><attendee><email>...
				
				// Get real value type by component type
				AbstractValue val = ComponentServices
						.getValueByXMLElementName(aComponent.getXMLElementName());

				// If there was component type that is not supported
				if (val != null) {

					setValueParamsAndSetAsContactSetReference(val, subItemBaseName);

					//If sub item is closing
					if (name.equals(subItemBaseName)) {
						
						subitem = false;

						// Get Vector where all same named values is stored,
						// e.g. if one contact has many "First names" all is
						// stored to same vector
						String itemId = aComponent.getValueById(subItemBaseName);
						
						if (itemId != null) {

							// If we are inside of element where is sub elements
							if (val instanceof CalendarValue) {

								handleCalendarSubValue(name, qName, val, itemId);


							}// if(subitem && val instanceof CalendarValue){
							//When its message value, and its under from or to, get item ID so it will be from / to							
							else if (val instanceof MessageValue) {
								
								handleMessageSubValue(name, qName, val, subItemBaseName, elementNames.lastElement());

							}// if(subitem && val instanceof CalendarValue){
							//When it's a FileType, there can be encryption 
							//<ecryption><type="DRM-CD"><right type="[value]"><count></count>...  
							else if (val instanceof FileTypeValue) {
								
								handleFileTypeSubValue(name);

							}// if(subitem && val instanceof CalendarValue){								
							// Error: 
							else {
								errors.append("Unknown subelement type: '" +itemId +"' found in: " +val.getType() +CreatorXML.NEW_LINE);
							}
						}// if(itemId != null){
						else {
							String errMsg = "Element: ' " + subItemBaseName + "' in component: '" 
								+ aComponent.getXMLElementName() + "' is not supported." +CreatorXML.NEW_LINE;
							errors.append(errMsg);
						}

						
						
					}// if(name.equals(subItemBaseName)){
					//else sub item does not closed yet but it was a element of sub element
					else {
						// Creating sub value values and put them a side for waiting until closing point of
						// sub element is found  e.g. email and common name in
						// <attendee><email/><commonname/></attendee> 
						
						//When type is file, there can be several count, starttime, endtime, 
						//interval and accumulated elemets, those must be possible to distinguish each other, 
						//thats why encryption Right type is used in parameter name inside of Creator editor  
						if(val instanceof FileTypeValue && rightType != null) {

							String uiId = FileTypeVariables.getEncryptionId(rightType, name);
							String uiCompName = aComponent.getValueById(uiId);
							if(uiCompName != null){
								addValueToComponent(val, uiCompName, componentItems);
							}else{
								String errMsg = "Element: ' " + name + "' under " +FileTypeVariables.RIGHT_XML +" " 
									+FileTypeVariables.TYPE_XML +": '" +rightType
									+  "' in component: '" + aComponent.getXMLElementName() 
									+ "' is not supported." +CreatorXML.NEW_LINE;
								errors.append(errMsg);
							}
						}else{
							subItemMap.put(name, val.getValue());
						}
					}
				}// if(val != null){

				// Else we found a sub component value that is not supported.
				else {
					String errMsg = "Could not found values for component: " + aComponent.getType() + CreatorXML.NEW_LINE;
					CreatorEditorConsole.getInstance().println( "Error on parsing: " + errMsg);
					errors.append(errMsg);
				}

			}//else if (creatorscript && component && fields && item && subitem) {

		}

		/**
		 * @param name
		 */
		private void handleFileTypeSubValue(String name) {
			
			if(name.equalsIgnoreCase(FileTypeVariables.RIGHT_XML)){
				rightType = null;
			}

		}

		/**
		 * @param name
		 * @param qName
		 * @param val
		 * @param currentElementName
		 * @param superElementType
		 */
		private void handleMessageSubValue(String name, String qName,
				AbstractValue val, String currentElementName, String superElementType) {


			MessageValue mesVal = (MessageValue) val; 

			//What value types was in under <item><subitem> -subitems
			Set<String> subItemKeys = subItemMap
						.keySet();
				// Looping through all sub elements and add
				// them to element (value)
				for (Iterator<String> iterator = subItemKeys
						.iterator(); iterator.hasNext();) {

					String key = (String) iterator.next();
					mesVal.setValue(subItemMap
							.get(key));
				}
				mesVal.setType(MessageVariables.getValueByIds(superElementType, name));

				String id = MessageVariables.getInstance().getIDByValueAndType(currentElementName, superElementType);
				
				// When found closing point to subitem									
				if(subComponent){
					addValueToComponent(val, id, subComponentItems);
				}else{
					addValueToComponent(val, id, componentItems);
				}									
		}		
		
		/**
		 * Handles sub value of calendar type
		 * @param name
		 * @param qName
		 * @param val
		 * @param itemId
		 */
		private void handleCalendarSubValue(String name, String qName,
				AbstractValue val, String itemId) {
			// If this is the closing point of element where
			// was sub elements
			// Collecting sub element datas and add them to
			// element
			// e.g. //e.g. attendee in
			// <calendar><attendee><email/><commonname/></attendee></calendar>


				CalendarValue calVal = (CalendarValue) val;

				Set<String> subItemKeys = subItemMap
						.keySet();
				// Looping through all sub elements and add
				// them to element (value)
				for (Iterator<String> iterator = subItemKeys
						.iterator(); iterator.hasNext();) {

					String key = (String) iterator.next();
					calVal.setValue(key, subItemMap
							.get(key));
				}

				// When found closing point to subitem									
				if(subComponent){
					addValueToComponent(val, itemId, subComponentItems);
				}else{
					addValueToComponent(val, itemId, componentItems);
				}									
		}

		/**
		 * Get component or sub component, depending on value of subComponent
		 * @return a aSubComponent if subComponent == true
		 */
		private AbstractComponent getComponent() {
			if(subComponent){
				return aSubComponent;
			}else{
				return aComponent;
			}
		}

		/**
		 * Set Parameters to value, resets Parameter values when used.
		 * @param value
		 * @param XML element name where parameters belongs to.
		 */
		private void setValueParamsAndSetAsContactSetReference(AbstractValue val, String xmlElementName) {
			
			
			if(CreatorEditorSettings.isContactSetReference(xmlElementName)){
				val.setContactSetReference(true);
			}
			
			// If there is some content, its not a random value,
			// otherwise it is a random value
			if (itemAmountParam != 0) {
				val.setAmount(itemAmountParam);
				itemAmountParam = 0;
			}
			
			if ( itemIdParam != 0) {
				val.setId(itemIdParam);
				itemIdParam = 0;
			}

			if ( itemMaxAmountParam != 0) {
				val.setMaxAmount(itemMaxAmountParam);
				itemMaxAmountParam = 0;
			}		
			
			
			//
			//Setting random values & modes
			//

			if (elementContent != null
					&& elementContent.toString().trim().length() > 0) {
				val.setValue(elementContent.toString());
				val.setRandom(false);
				
				if(incvalueforeachcopy != null && incvalueforeachcopy.equalsIgnoreCase(AbstractComponent.TRUE)){
					//Setting value to null when its used.
					val.setModeType(ModeTypes.ModeTypeIncValueForEachCopy);					
					incvalueforeachcopy = null;
				}					
				
			}else if(val.isContactSetReference()){
				val.setRandom(false);//Contact set reference cannot be random
			}
			//Otherwise its a random
			else {
				if (randomType != ModeTypes.RandomTypeNotRandom) {
					val.setModeType(randomType);
					if(randomType == ModeTypes.RandomTypeUserDefinedLength){
						val.setRandomValueLenght(userDefinedRandomLen);
					}
				} else {
					val.setModeType(ModeTypes.RandomTypeDefaultLength);
				}

			}
		}

		/**
		 * Add a value to component
		 * @param val
		 * @param itemId
		 * @param items
		 */
		private void addValueToComponent(AbstractValue val, String itemId, Map<String, Vector<AbstractValue>> items) {
			Vector<AbstractValue> itemValues = items.get(itemId);
			if (itemValues == null) {
				// If this was a first item with this name, creating a new
				// Vector to store values
				itemValues = new Vector<AbstractValue>();
			}
			itemValues.add(val);
			//putting items bact to table
			items.put(itemId, itemValues);
		}

		/* (non-Javadoc)
		 * @see org.xml.sax.helpers.DefaultHandler#characters(char[], int, int)
		 */
		public void characters(char ch[], int start, int length) {
			elementContent.write(ch, start, length);
		}

		/**
		 * @return Errors String
		 */
		public String getErrors() {
			return errors.toString();
		}

	}

}
