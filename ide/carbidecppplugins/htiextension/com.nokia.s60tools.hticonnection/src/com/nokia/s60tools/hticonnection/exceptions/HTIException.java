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
 * Thrown when there are problems with HTI agent.
 */
public class HTIException extends Exception{

	/**
	 * Serial version ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * More details about error.
	 */
	private HTIErrorDetails errorDetails = null;
	/**
	 * Boolean about is HTI initialized successfully. False means problems with HTI.
	 */
	private boolean isHtiInitialized = true;
	/**
	 * Boolean about was HTI request canceled. True if request was canceled. False otherwise.
	 */
	private boolean isHtiRequestCanceled = false;
	
	/**
	 * Constructor.
	 * @param message Informative message about the error causing the exception.
	 * @param isHtiInitialized False if error arises because HTI is not initialized.
	 * True otherwise.
	 */
	public HTIException(String message, boolean isHtiInitialized){
		super(message);
		this.isHtiInitialized = isHtiInitialized;
	}
	
	/**
	 * Constructor.
	 * @param message Informative message about the error causing the exception.
	 * @param isHtiInitialized False if error arises because HTI is not initialized.
	 * True otherwise.
	 * @param isHtiRequestCanceled True if request was canceled. false otherwise.
	 */
	public HTIException(String message, boolean isHtiInitialized, boolean isHtiRequestCanceled){
		super(message);
		this.isHtiInitialized = isHtiInitialized;
		this.isHtiRequestCanceled = isHtiRequestCanceled;
	}
	
	/**
	 * Constructor.
	 * @param message Informative message about the error causing the exception.
	 * @param errorDetails Contains more details about error.
	 */
	public HTIException(String message, HTIErrorDetails errorDetails){
		super(message);
		this.errorDetails = errorDetails;
	}
	
	/**
	 * Gets if exception contains more detailed information about error.
	 * @return True if exception contains more details about error.
	 */
	public boolean hasErrorDetails(){
		return (errorDetails != null);
	}
	
	/**
	 * Gets more details about error.
	 * @return More details about error.
	 */
	public HTIErrorDetails getErrorDetails(){
		return errorDetails;
	}
	
	/**
	 * Gets if HTI has been initialized successfully. 
	 * @return True if HTI has been initialized successfully. False if couldn't connect to HTI agent.
	 */
	public boolean isHtiInitialized(){
		return isHtiInitialized;
	}

	/**
	 * Gets if HTI request was canceled.
	 * @return True if request was canceled. False otherwise.
	 */
	public boolean isHtiRequestCanceled() {
		return isHtiRequestCanceled;
	}
	
}
