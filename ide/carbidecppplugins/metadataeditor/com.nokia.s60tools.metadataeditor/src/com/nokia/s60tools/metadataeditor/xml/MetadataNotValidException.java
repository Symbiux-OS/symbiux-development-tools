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

public class MetadataNotValidException extends Exception {

	MetadataXML xml = null;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Exception must have description, no default constructor
	 */
	@SuppressWarnings("unused")
	private MetadataNotValidException() {
	}

	public MetadataNotValidException(String arg0) {
		super(arg0);
	}

	public MetadataNotValidException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public MetadataNotValidException(String errors, MetadataXML xml) {
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
	public MetadataXML getMetadataXML() {
		return this.xml;
	}

}
