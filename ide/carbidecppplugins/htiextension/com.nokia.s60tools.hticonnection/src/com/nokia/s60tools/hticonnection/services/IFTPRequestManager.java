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
 * Interface for canceling file transfer operation;
 */
public interface IFTPRequestManager {
	
	/**
	 * Returns file name which is handled by this operation.
	 * @return File name that is transfered.
	 */
	public String getFileName();
	
	/**
	 * Cancels this request.
	 */
	public void cancel();
}
