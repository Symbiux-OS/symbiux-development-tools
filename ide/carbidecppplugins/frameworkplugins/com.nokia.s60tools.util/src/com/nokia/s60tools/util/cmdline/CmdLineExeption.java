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
 
package com.nokia.s60tools.util.cmdline;

/**
 * Exception is thrown, when errors occurred in command line execution.
 */
public class CmdLineExeption extends Exception {

	/**
	 * Serialization id.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public CmdLineExeption(){
		super();
	}

	/**
	 * Constructor with attached message.
	 * @param message Exception's message.
	 */
	public CmdLineExeption( String message ){
		super(message);
	}
	
}
