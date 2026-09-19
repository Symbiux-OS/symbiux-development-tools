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

package com.nokia.s60tools.hticonnection.exceptions;

/**
 * Thrown when request fails.
 */
public class RequestFailedException extends Exception{

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor.
	 * @param message Informative message about the error causing the exception.
	 */
	public RequestFailedException(String message){
		super(message);
	}
	
	/**
	 * Constructor.
	 * @param cause Original exception.
	 */
	public RequestFailedException(Exception cause){
		super(cause);
	}
}
