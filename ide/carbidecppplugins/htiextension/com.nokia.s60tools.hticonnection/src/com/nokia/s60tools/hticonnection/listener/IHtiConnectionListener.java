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

package com.nokia.s60tools.hticonnection.listener;

/**
 * Interface for informing HTI connection status changes
 */
public interface IHtiConnectionListener {
	
	/**
	 * Called when connection to terminal is terminated
	 */
	public void connectionTerminated();

	/**
	 * Called when connection to terminal is tested as operational and started.
	 */
	public void connectionStarted();
}
