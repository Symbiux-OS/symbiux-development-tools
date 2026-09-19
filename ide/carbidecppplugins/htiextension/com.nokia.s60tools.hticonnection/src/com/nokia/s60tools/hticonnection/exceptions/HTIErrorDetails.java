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
 * More detailed information about error.
 */
public class HTIErrorDetails {
	
	/**
	 * Error code from HTI.
	 */
	private int HTIErrorCode;
	/**
	 * Error code from HTI service.
	 */
	private int HTIServiceErrorCode;
	/**
	 * Error description.
	 */
	private String errorDescription;

	/**
	 * Constructor.
	 * @param HTIErrorCode Error code from HTI.
	 * @param HTIServiceErrorCode Error code from HTI service.
	 * @param errorDescription Error description.
	 */
	public HTIErrorDetails(int HTIErrorCode, int HTIServiceErrorCode, String errorDescription){
		this.HTIErrorCode = HTIErrorCode;
		this.errorDescription = errorDescription;
		HTIErrorCode = HTIServiceErrorCode;
	}
	
	/**
	 * Getter for HTI error code.
	 * @return Error code from HTI.
	 */
	public int getHTIErrorCode() {
		return HTIErrorCode;
	}
	
	/**
	 * Getter for Error code from HTI service.
	 * @return Error code from HTI service.
	 */
	public int getHTIServiceErrorCode() {
		return HTIServiceErrorCode;
	}
	
	/**
	 * Getter for error description.
	 * @return Error description.
	 */
	public String getErrorDescription() {
		return errorDescription;
	}
}
