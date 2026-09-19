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
 
package com.nokia.s60tools.util.sourcecode;

/**
 * Exception is thrown when another Exception occurs while seeking a source file location. 
 * Original Exception can be stored as member, if needed.
 */
public class CannotFoundFileException extends Exception {

	/**
	 * Serialization ID.
	 */
	private static final long serialVersionUID = 2936572400459479759L;
	
	/**
	 * Storing original exception that occurred initially.
	 */
	private Exception exception = null;
	
	/**
	 * Default construction
	 */
	public CannotFoundFileException(){
		super();		
	}

	/**
	 * Exception with message 
	 * @param message
	 */
	public CannotFoundFileException(String message){
		super(message);
	}
	/**
	 * Exception with message and original exception.
	 * @param message Error message.
	 * @param exception Original exception mapped to this exception type.
	 */
	public CannotFoundFileException(String message, Exception exception){
		super(message);
		this.exception = exception;
	}

	/**
	 * Gets original exception mapped into this exception type.
	 * @return the original exception
	 */
	public Exception getException() {
		return exception;
	}	
	
}
