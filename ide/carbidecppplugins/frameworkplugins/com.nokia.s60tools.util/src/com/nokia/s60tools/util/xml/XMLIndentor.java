/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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
 
package com.nokia.s60tools.util.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.nokia.s60tools.util.resource.FileUtils;

/**
 * Class for adding indents to XML file.  
 */
public class XMLIndentor {
	
	
	/**
	 * Default construction. Can be instantiated
	 * only by using public static methods.
	 */
	private XMLIndentor(){
		
	}
	
	/**
	 * How deep the indents will be. Current value 2.
	 */
	public static final int DEFAULT_INDENT_NUMBER = 2;

	/**
	 * Adding indents to XML String.
	 * 
     * <p>
     * 
     * NOTE: There must not be newlines (\n or \r\n) in String between elements, if so, that line is not going to be indented. 
	 * 
	 * @param in XML String to be indent
	 * @param encodingType valid encoding type to character set, e.g. 
	 * {@link FileUtils#ENCODING_TYPE_UTF_8}
	 * @param indentNumber how many white spaces will one indent be, 
	 *                     must be at least 1, if not  {@link XMLIndentor#DEFAULT_INDENT_NUMBER} is used.
	 * @return Indented result. 
	 * @throws UnsupportedEncodingException 
	 * @throws TransformerException 
	 */
	public static String indentXML(String in, String encodingType, int indentNumber) throws UnsupportedEncodingException, TransformerException {
		
		/*
		 * e.g. XML:
		 * <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
		 * <atom><type>helium</type></atom>
		 * 
		 * is converted to:
		 * 
		 * <?xml version="1.0" encoding="UTF-8"?>
		 * <atom>
	     *   <type>helium</type>
	     * </atom>
	     * 
		 */
		
		//Check that 0 or -n is not used
		if(indentNumber < 1){
			indentNumber =  DEFAULT_INDENT_NUMBER;
		}
		
		TransformerFactory factory = TransformerFactory.newInstance();
		//Setting number of indents
		factory.setAttribute("indent-number", new Integer(indentNumber));
		Transformer transformer = factory.newTransformer();
		//Setting property, that indents will be made when transform is done
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		String result;		
		if(encodingType != null){
			//Setting encoding			
			transformer.setOutputProperty(OutputKeys.ENCODING, encodingType);
			result = transform(in, encodingType, transformer);
		}else{
			result = transform(in, transformer);
		}

		return result;
	}

	/**
	 * Transform with given encoding type.
	 * @param in 			XML String to be indent
	 * @param encodingType Valid encoding type to character set, e.g. 
	 *                     {@link FileUtils#ENCODING_TYPE_UTF_8}
	 * @param transformer  Transformer object.
	 * @return Indented result. 
	 * @throws UnsupportedEncodingException
	 * @throws TransformerException
	 */
	private static String transform(String in, String encodingType,
			Transformer transformer) throws UnsupportedEncodingException,
			TransformerException {
		InputStream inSource = new ByteArrayInputStream(in
				.getBytes(encodingType));
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult res = new StreamResult(new OutputStreamWriter(out,
				encodingType));
		// Making indents to XML by doing transform for it
		transformer.transform(new StreamSource(inSource), res);
		String result = out.toString(encodingType);
		return result;
	}	
	/**
	 * Transform without encoding type
	 * @param in 			XML String to be indent
	 * @param transformer  Transformer object.
	 * @return Indented result. 
	 * @throws UnsupportedEncodingException
	 * @throws TransformerException
	 */
	private static String transform(String in,
			Transformer transformer) throws UnsupportedEncodingException,
			TransformerException {
		InputStream inSource = new ByteArrayInputStream(in
				.getBytes());
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		StreamResult res = new StreamResult(new OutputStreamWriter(out));
		// Making indents to XML by doing transform for it
		transformer.transform(new StreamSource(inSource), res);
		String result = out.toString();
		return result;
	}	
	
	/**
	 * Adding indents to XML String. No specific encoding type is used,
	 * indent number is {@link XMLIndentor#DEFAULT_INDENT_NUMBER}
	 * <p> 
     * NOTE: There must not be newlines (\n or \r\n) in String between elements, 
     * if so, that line is not going to be indented. 
	 * 
	 * @param in 	XML String to be indent
	 * @return Indented result. 
	 * @throws UnsupportedEncodingException 
	 * @throws TransformerException 
	 */
	public static String indentXML(String in) throws UnsupportedEncodingException, TransformerException {
		/*
		 * e.g. XML:
		 * <?xml version="1.0" encoding="UTF-8" standalone="yes" ?>
		 * <atom><type>helium</type></atom>
		 * 
		 * is converted to:
		 * 
		 * <?xml version="1.0" encoding="UTF-8"?>
		 * <atom>
	     *   <type>helium</type>
	     * </atom>
	     * 
		 */		
		return indentXML(in, null, DEFAULT_INDENT_NUMBER);
	}	
		
}
