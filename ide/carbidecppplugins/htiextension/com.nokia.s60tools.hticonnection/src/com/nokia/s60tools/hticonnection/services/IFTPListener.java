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

package com.nokia.s60tools.hticonnection.services;

/**
 * Interface for getting status from file transfer operation.
 */
public interface IFTPListener {

	/**
	 * Called when request has been registered and is waiting for start.
	 * @param manager Can be used to cancel the request.
	 * and contains information of the request.
	 */
	public void requestInQueue(IFTPRequestManager manager);
	
	/**
	 * Called when request has been started.
	 * @param manager Can be used to cancel the request.
	 * and contains information of the request.
	 */
	public void requestStarted(IFTPRequestManager manager);
	
	/**
	 * Called when request has been completed or canceled.
	 * @param manager Can be used to cancel the request
	 * and contains information of the request.
	 */
	public void requestEnded(IFTPRequestManager manager);
}
