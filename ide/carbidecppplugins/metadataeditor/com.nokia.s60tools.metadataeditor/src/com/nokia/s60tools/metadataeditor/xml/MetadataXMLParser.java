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

 
 
package com.nokia.s60tools.metadataeditor.xml;

import java.io.CharArrayWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.DefaultHandler2;
import org.xml.sax.helpers.XMLReaderFactory;

import com.nokia.s60tools.metadataeditor.util.MetadataEditorConsole;

public class MetadataXMLParser {

	public MetadataXMLParser() {
	}

	/**
	 * 
	 * @param fileName file to be parsed
	 * @return MetadataXML object with all elements and attributes set
	 * @throws SAXException
	 * @throws IOException
	 * @throws MetadataNotValidException if parsed data was not valid
	 */
	public MetadataXML parse(String fileName) throws  MetadataNotValidException  {
		XMLReader reader;
		MetadataXML xml = null;
		MetadataXMLHandler handler = null;
		try {
			handler = new MetadataXMLHandler(fileName);
			reader = XMLReaderFactory.createXMLReader();
			//tel to parser that Lexical handler (@see http://java.sun.com/j2se/1.5.0/docs/api/org/xml/sax/ext/LexicalHandler.html) 
			//is in use, if not done, the @link{ MetadataXMLHandler.comment () } wont work.
			reader.setProperty ("http://xml.org/sax/properties/lexical-handler", handler);	
			reader.setContentHandler(handler);
			MetadataXMLErrorHandler errorHandler = new MetadataXMLErrorHandler();
			reader.setErrorHandler(errorHandler);
	
			FileReader r = new FileReader(fileName);
			reader.parse(new InputSource(r));
			
			xml = handler.getMetadataXML();
			if(xml.isValid()){
				return xml;
			}
			else{
				throw new MetadataNotValidException(handler.getErrors(), xml);
			}

		} catch (SAXException e) {
			e.printStackTrace();
			MetadataEditorConsole.getInstance().println("SAXException on parsing: " +e.toString() );
			throw new MetadataNotValidException(e.getMessage());
		} catch (FileNotFoundException e) {
			MetadataEditorConsole.getInstance().println("FileNotFoundException on parsing: " +e.toString() );			
			e.printStackTrace();
			throw new MetadataNotValidException(e.getMessage());
		} catch (IOException e) {			
			MetadataEditorConsole.getInstance().println("IOException on parsing: " +e.toString() );			
			e.printStackTrace();
			throw new MetadataNotValidException(e.getMessage());
		}
		
	}

	public class MetadataXMLErrorHandler implements ErrorHandler{

		public void error(SAXParseException e) throws SAXException {
			MetadataEditorConsole.getInstance().println("SAXParseException (error) on parsing: " +e.toString() );			
			
		}

		public void fatalError(SAXParseException e) throws SAXException {
			MetadataEditorConsole.getInstance().println("SAXParseException (fatal) on parsing: " +e.toString() );			
		}

		public void warning(SAXParseException e) throws SAXException {
			MetadataEditorConsole.getInstance().println("SAXParseException (warnig) on parsing: " +e.toString() );			
		}
		
	}
	
	
	////////////////////////////////////////////////////////////////////
	// Event handlers.
	////////////////////////////////////////////////////////////////////

	public class MetadataXMLHandler extends DefaultHandler2 {

		public MetadataXMLHandler(String filename) {
			xml = new MetadataXML(filename);
		}

		@SuppressWarnings("unused")
		private MetadataXMLHandler() {
		}

		//Local variables and flags
		private CharArrayWriter elementContent = new CharArrayWriter();
		//writer for comments
		private CharArrayWriter comments = new CharArrayWriter();	
		private Vector<String> commentsForOneElement = new Vector<String>();
		private MetadataXML xml ;
		private boolean libs;
		private boolean attributes;
		private boolean extendedsdk;
		private StringBuffer errors = new StringBuffer();

		public MetadataXML getMetadataXML() {
			return xml;
		}

		public void startDocument() {
		}

		public void endDocument() {
		}

		public void startElement(String uri, String name, String qName,
				Attributes atts) {

			elementContent.reset();
			//Writing comments for that element
			writeComments(name);
			
			//Setting flags
			if(name.equalsIgnoreCase("libs")){
				libs = true;
			}
			if(name.equalsIgnoreCase("attributes")){
				attributes = true;
			}			
			if(name.equalsIgnoreCase("extendedsdk")){
				extendedsdk = true;
			}			
			
			//Setting data from attributes
			if(name.equalsIgnoreCase("api")){
				xml.setAPIID(atts.getValue("id"));
				try {
					xml.setDataVersion(atts.getValue("dataversion"));
				} catch (MetadataNotValidException e) {
					e.printStackTrace();
					errors.append(e.getMessage());
					errors.append("\n");
				}
			}
			else if(name.equalsIgnoreCase("release")){
				xml.setReleaseCategory(atts.getValue("category"));
				boolean categorySDK = 
					(atts.getValue("category").equalsIgnoreCase("sdk") 
							|| atts.getValue("category").equalsIgnoreCase("public") ) 
							? true : false ;
				try {
					if(categorySDK || atts.getIndex("sinceversion") != -1){
						xml.setReleaseSince(atts.getValue("sinceversion"));
					}
				} catch (MetadataNotValidException e) {
					//If category is "sdk" since is mandatory, optional if "domain"
					if(categorySDK){
						e.printStackTrace();
						errors.append(e.getMessage());
						errors.append("\n");
					}
				}
				if(atts.getIndex("deprecatedsince") != -1){
					try {
						xml.setReleaseDeprecatedSince(atts.getValue("deprecatedsince"));
					} catch (MetadataNotValidException e) {
						e.printStackTrace();
						errors.append(e.getMessage());
						errors.append("\n");
					}
				}				

			}			
			else if(libs){			
				if(name.equalsIgnoreCase("lib")){
					xml.addLib(atts.getValue("name"));
				}
			}else if (attributes && extendedsdk){
				try {
					xml.setExtendedSDKSince(atts.getValue("sinceversion"));
				} catch (MetadataNotValidException e) {
					e.printStackTrace();
					errors.append(e.getMessage());
					errors.append("\n");
				}
				if(atts.getIndex("deprecatedsince") != -1){
					try {
						xml.setExtendedSDKDeprecatedSince(atts.getValue("deprecatedsince"));
					} catch (MetadataNotValidException e) {
						e.printStackTrace();
						errors.append(e.getMessage());
						errors.append("\n");
					}
				}				
			}
				
		}

		/**
		 * Writing comments to XML
		 */
		private void writeComments(String name) {
			if(!commentsForOneElement.isEmpty()){
				xml.addComments(name, commentsForOneElement);
				commentsForOneElement = new Vector<String>();
			}
		}

		public void endElement(String uri, String name, String qName) {
			
			//Writing comments for that element just in case also when element is closed
			//reason for doing that is that if theres comments inside element content
			writeComments(name);
			
			//When finding elements, setting data directly to xml
			if (name.equalsIgnoreCase("name")) {
				xml.setAPIName(elementContent.toString());
			}
			else if (name.equalsIgnoreCase("description")) {
				xml.setDescription(elementContent.toString());
			}
			else if (name.equalsIgnoreCase("type")) {
				xml.setType(elementContent.toString());
			}
			else if (name.equalsIgnoreCase("htmldocprovided")) {
				if(elementContent.toString().equalsIgnoreCase("yes")){
					xml.setHtmlDocProvided(true);
				}else{
					xml.setHtmlDocProvided(false);
				}
			}
			else if (name.equalsIgnoreCase("adaptation")) {
				if(elementContent.toString().equalsIgnoreCase("yes")){
					xml.setAdaptation(true);
				}else{
					xml.setAdaptation(false);
				}				
			}
			//when data is as parameters, using flags and setting data in startElement
			else if(name.equalsIgnoreCase("libs")){
				libs = false;
			}
			else if(name.equalsIgnoreCase("attributes")){
				attributes = false;
			}
			else if(name.equalsIgnoreCase("extendedsdk")){
				extendedsdk = false;
			}			
			else if (name.equalsIgnoreCase("subsystem") || (name.equalsIgnoreCase("collection"))) {
				//We are not checking the versions when parsing,
				//but UI will handle it (if there is needed elements missing with DATA versions).
				//With version 1.0 we must have subsystem, with newer version we must have "collection" as subsystem, 
				//anyway with xml -object, we are using the same element to store value for that.
				xml.setSubsystem(elementContent.toString());
			}			

		}
		

		/* (non-Javadoc)
		 * @see org.xml.sax.ext.DefaultHandler2#comment(char[], int, int)
		 */
		public void comment(char[] ch,
                int start,
                int length)
         throws SAXException{
			//Adding comments from XML file to XML object 
			comments.write(ch, start, length);
			commentsForOneElement.add(comments.toString());
			comments.reset();
		}
		
		public void characters(char ch[], int start, int length) {
			elementContent.write(ch, start, length);
		}

		public String getErrors() {
			return errors.toString();
		}

	}

}
