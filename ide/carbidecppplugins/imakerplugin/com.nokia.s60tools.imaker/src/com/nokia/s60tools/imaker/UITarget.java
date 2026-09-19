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
 * This class is a simple container class for target
 * information
 * 
 * @version 0.2
 */
public class UITarget {
	/** Private instance variables */
	private String name = null;
	private String description = null;
	private String steps = null;
	
	/**
	 * Default constructor
	 * 
	 * @param name Name of the target
	 * @param description Description of the target
	 */
	public UITarget(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	/**
	 * Get method for name
	 * 
	 * @return Name of the target
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Get method for description
	 * 
	 * @return Description of the target
	 */
	public String getDescription() {
		return description;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getSteps() {
		return steps;
	}
	
}
