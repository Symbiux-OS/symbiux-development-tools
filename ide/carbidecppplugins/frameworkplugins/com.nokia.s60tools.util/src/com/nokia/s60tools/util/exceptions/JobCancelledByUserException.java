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

package com.nokia.s60tools.util.exceptions;

public class JobCancelledByUserException extends Exception {

	static final long serialVersionUID = -2283577166005521183L;

	/**
	 * Constructor with attached message.
	 * @param message Detailed message for the user.
	 */
	public JobCancelledByUserException(String string) {
		super(string);
	}
}
