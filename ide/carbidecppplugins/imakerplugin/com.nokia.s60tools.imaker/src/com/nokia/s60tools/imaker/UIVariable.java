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


package com.nokia.s60tools.imaker;

/**
 * This class is a container for variable information from iMaker.
 * 
 * @version 0.2
 */
public class UIVariable {
	/** Private instance variables */
	private String defaultValue = null;

	private boolean modified;

	private String name = null;
	private String value = null;
	private String type = null;
	private String description = null;
	private String valueFormat = null;
	private boolean include = false;
	/**
	 * Default constructor
	 * 
	 * @param name Name of the variable
	 * @param value Value of the variable
	 * @param type Type of the variable
	 * @param description Description of the variable
	 * @param valueFormat Value format (values) of the variable
	 */
	public UIVariable(String name, String value, String type,
			          String description, String valueFormat) {
		this.name = name;
		this.value = defaultValue = value;
		this.type = type;
		this.description = description;
		this.valueFormat = valueFormat;
	}

	public void performDefaults() {
		this.value = defaultValue;
		this.modified = false;
	}

	public boolean isModified() {
		return modified;
	}

	public void setModified(boolean modified) {
		this.modified = modified;
	}


	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public String getDescription() {
		return description;
	}

	public String getValueFormat() {
		return valueFormat;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(getName()+ "\t\t");
		sb.append(getValue()+ "\t\t");
		sb.append(getValueFormat() + "\t\t");
		sb.append(getDescription());
		return sb.toString();
	}

	public boolean getInclude() {
		return include;
	}

	public void setInclude(boolean inc) {
		include=inc;
	}

	@Override
	public boolean equals(Object that) {
		if(that==null||!(that instanceof UIVariable)) {
			return false;
		}
		UIVariable v = (UIVariable) that;
		return this.getName().equals(v.getName()) && this.getValue().equals(v.getValue());
	}
	
}
