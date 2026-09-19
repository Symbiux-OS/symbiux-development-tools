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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

/**
 * This interfaces provides connection status of current HTI Connection.
 */
public interface IConnectionStatusProvider {
	
	/**
	 * Gets if HTI Connection is connected to the device.
	 * @return Is HTI Connection connected to the device.
	 */
	public boolean isConnected();
}
