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

import com.nokia.s60tools.creator.xml.CreatorXML;

/**
 * Exception thrown when XML is not valid
 */
public class CreatorScriptNotValidException extends Exception {

	/**
	 * Member to store unvalid XML file
	 */
	CreatorXML xml = null;
	
	
	private static final long serialVersionUID = 1L;

	/**
	 * Exception must have description, no default constructor
	 */
	@SuppressWarnings("unused")
	private CreatorScriptNotValidException() {
	}

	/* (non-Javadoc)
	 * @see Exception#Exception(String) 
	 */
	public CreatorScriptNotValidException(String arg0) {
		super(arg0);
	}

	/* (non-Javadoc)
	 * @see Exception#Exception(String, Throwable) 
	 */
	public CreatorScriptNotValidException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * Construction with error descriptions and xml containing errors
	 * @param errors
	 * @param xml
	 */
	public CreatorScriptNotValidException(String errors, CreatorXML xml) {
		super(errors);
		this.xml = xml;
	}

	/**
	 * Get so far parsed XML. Not necessarily contain all information what 
	 * is in xml file. Contains only that information what was found before
	 * parsing fails, rest of the elements was not parsed.
	 * 
	 * @return a XML that could not be parsed. 
	 */
	public CreatorXML getCreatorXML() {
		return this.xml;
	}

}
