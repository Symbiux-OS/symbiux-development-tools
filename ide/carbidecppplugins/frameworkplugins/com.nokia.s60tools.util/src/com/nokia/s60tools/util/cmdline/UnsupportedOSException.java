/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 * Exception thrown when an unsupported operation
 * system is detected.
 */
public class UnsupportedOSException extends Exception {
		
	static final long serialVersionUID = -4533538487088112779L;

	/**
	 * Default constructor. 
	 */
	public UnsupportedOSException(){
		super();
	}

	/**
	 * Constructor with attached message.
	 * @param message Exception's message.
	 */
	public UnsupportedOSException( String message ){
		super(message);
	}

}
