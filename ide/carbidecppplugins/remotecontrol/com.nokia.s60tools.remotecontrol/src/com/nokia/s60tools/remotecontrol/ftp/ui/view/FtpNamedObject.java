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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

/**
 * Class for storing general information. 
 */
public class FtpNamedObject{
	
	private String name;

	/**
	 * Constructor.
	 *  @param name Name of object
	 */
	public FtpNamedObject(String name) {
		this.name = name;
	}
	
	/**
	 * Getter for name.
	 * @return name
	 */
	public String getName() {
		return name;
	}
}