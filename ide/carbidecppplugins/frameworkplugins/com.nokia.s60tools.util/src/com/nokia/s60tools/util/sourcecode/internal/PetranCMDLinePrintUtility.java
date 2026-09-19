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
 
package com.nokia.s60tools.util.sourcecode.internal;

import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Custom CMD line print utility, that is not printing normal messages at all,
 * only prints warnings and errors by using given application specific print utility.
 */
public class PetranCMDLinePrintUtility implements IConsolePrintUtility {

	/**
	 * Application specific print utility.
	 */
	private IConsolePrintUtility printUtility = null;

	/**
	 * Creates a Petran CMD line runner specific print utility, what is not printing normal messages, 
	 * only warnings and errors using given print utility.
	 * @param printUtility Print utility used to report error, warnings, and info messages.
	 */
	public PetranCMDLinePrintUtility(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;		
	}


	/** Not printing anything
	 * @see com.nokia.s60tools.util.console.IConsolePrintUtility#println(java.lang.String)
	 */
	public void println(String message) {
		
	}

	/** 
	 * Prints only warnings and errors
	 * 
	 * @see com.nokia.s60tools.util.console.IConsolePrintUtility#println(java.lang.String, int)
	 */
	public void println(String message, int messageType) {

		if(messageType == MSG_NORMAL){
			//Not printing normal messages
		}
		else{
			printUtility.println(message, messageType);
		}
		
	}

}
