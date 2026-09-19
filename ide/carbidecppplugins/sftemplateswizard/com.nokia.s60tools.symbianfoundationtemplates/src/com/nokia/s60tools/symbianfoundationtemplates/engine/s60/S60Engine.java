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
package com.nokia.s60tools.symbianfoundationtemplates.engine.s60;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import com.nokia.s60tools.symbianfoundationtemplates.engine.BaseEngine;

/**
 * Symbian Foundation template transform engine.
 *
 */
public class S60Engine extends BaseEngine {
	private Map<String, String> rules;

	/**
	 * Default constructor.
	 * 
	 * @param templateFile the template file used in transforming
	 */
	public S60Engine(String templateFile) throws IOException {
		super(templateFile);
	}

	/**
	 * Set transform rules used in transforming
	 * 
	 * @param rules the transform rules
	 */
	public void setTransformRules(Map<String, String> rules) {
		this.rules = rules;
	}
	
	@Override
	public String getTransformedString() {
		String transformed = getTemplateString();
		
		if(transformed == null)
			return null;
		
		for(String key : rules.keySet()) {
			String transformedString;
			
			if(key.equals(S60TransformKeys.getString("key_description")))
				transformedString = createDescription(rules.get(key));
			else
				transformedString = rules.get(key);
			
			if(!transformedString.equals(""))
				transformed = transformed.replace(key, transformedString);
		}
		return transformed;
	}
	
	/**
	 * Helper method to create a "pretty" description field.
	 * 
	 * @param description the original description string
	 * @return the "pretty" description field
	 */
	private String createDescription(String description) {
		StringBuffer buff = new StringBuffer();
		
		BufferedReader reader = new BufferedReader(new StringReader(description));
		
		try {
			String line = reader.readLine();
			
			if(line == null)
				return "";
			
			buff.append("*  Description : ").append(line);
			
			while((line = reader.readLine()) != null)
				buff.append("\n").append("*                ").append(line);
		} catch(IOException ioe) {
			return "";
		}
		
		return buff.toString();
	}
}
