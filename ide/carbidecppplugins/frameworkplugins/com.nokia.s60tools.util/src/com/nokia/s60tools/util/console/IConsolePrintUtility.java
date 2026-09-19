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
 
 
package com.nokia.s60tools.util.console;


/**
 * Common interface for concrete console print utilities that are offered
 * in product level. Concrete implementors should provide support for
 * all message types defined as public class attributes.
 * 
 * This interface is implemented by AbstractProductSpecificConsole which
 * uses further ConsoleWindowUtility class for formatting messages
 * according the message type parameter and prints to message output. 
 * @see com.nokia.s60tools.util.console.AbstractProductSpecificConsole
 * @see com.nokia.s60tools.util.console.ConsoleWindowUtility
 */
public interface IConsolePrintUtility {
	
	/**
	 * Constant denoting normal message type (default formatting type).
	 */
	public static final int MSG_NORMAL = 1;
	/**
	 * Constant denoting warning message type.
	 */
	public static final int MSG_WARNING = 2;
	/**
	 * Constant denoting error message type.
	 */
	public static final int MSG_ERROR = 3;

	/**
	 * Prints given message to console with default formatting.
	 * @param message	Message to be printed.
	 * @see #MSG_NORMAL
	 */
	public void println(String message);

	/**
	 * Prints given message to console.
	 * @param message	  Message to be printed.
	 * @param messageType The type of the message.
	 * @see #MSG_NORMAL
	 * @see #MSG_WARNING
	 * @see #MSG_ERROR
	 */
	public void println(String message, int messageType);

}
