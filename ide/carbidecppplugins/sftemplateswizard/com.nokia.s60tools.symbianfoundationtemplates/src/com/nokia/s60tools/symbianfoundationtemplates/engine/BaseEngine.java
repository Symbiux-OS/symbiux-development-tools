/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies). 
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
package com.nokia.s60tools.symbianfoundationtemplates.engine;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Base engine class provides support for file opening and such.
 * 
 */
public abstract class BaseEngine {
	private StringBuilder templateString;
	
	/**
	 * Default constructor.
	 * 
	 * @param templateFile the template file to open
	 */
	public BaseEngine(String templateFile) throws IOException {
		BufferedReader in = null;
		FileReader reader = null;
		
		if(templateFile == null)
			throw new IOException("Could not determine the correct template file!");
		
		reader = new FileReader(templateFile);
		in = new BufferedReader(reader);
			
		String line;
			
		templateString = new StringBuilder();
			
		while((line = in.readLine()) != null)
			templateString.append(line).append("\n");

		if(in != null)
			in.close();
		if(reader != null)
			reader.close();
	}
	
	/**
	 * Get the opened template file as a string.
	 * 
	 * @return template file as a string
	 */
	protected String getTemplateString() {
		if(templateString == null)
			return null;
		else
			return templateString.toString();
	}

	/**
	 * Get the transformed string.
	 * 
	 * @return the transformed string
	 */
	public abstract String getTransformedString();
}
