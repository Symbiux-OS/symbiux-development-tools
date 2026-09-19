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

import java.util.Map;

import org.eclipse.core.resources.IFile;

/**
 * Class represents an original template that has the template file
 * and the transformation rules.
 *
 */
public class OriginalTemplate {
	private String templateFile;
	
	private IFile toBeTransformedFile;
	
	private Map<String, String> transformRules;
	
	public OriginalTemplate(String templateFile, IFile toBeTransformedFile, Map<String, String> transformRules) {
		this.templateFile = templateFile;
		this.toBeTransformedFile = toBeTransformedFile;
		this.transformRules = transformRules;
	}
	
	/**
	 * @return the template file
	 */
	public String getTemplateFile() {
		return templateFile;
	}
	
	/**
	 * @return the to-be-transformed file
	 */
	public IFile getToBeTransformedFile() {
		return toBeTransformedFile;
	}
	
	/**
	 * @return transform rules
	 */
	public Map<String, String> getTransformRules() {
		return transformRules;
	}
}
