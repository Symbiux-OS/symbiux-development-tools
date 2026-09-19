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

package com.nokia.s60tools.hticonnection.preferences;

import com.nokia.carbide.remoteconnections.interfaces.IConnection;

/**
 * Interface for handling HTI Connection preferences.
 */
public interface IHtiApiPreferences {

	/**
	 * Initializes default values for preferences if not initialized before.
	 */
	public void initDefaultValues();

	/**
	 * Sets connection ID.
	 * 
	 * @param connectionID Unique identification for connection.
	 */
	public void setConnectionID(String connectionID);

	/**
	 * Gets connection ID.
	 * 
	 * @return Unique identification for connection.
	 */
	public String getConnectionID();

	/**
	 * Gets connection that matches the connection ID in preferences,
	 * or if there is only one HTI connection, then it is returned,
	 * or <code>null</code> if there is no matching connection.
	 * 
	 * @return connection that matches the connection ID in preferences, or null
	 * if connection was not found.
	 */
	public IConnection getCurrentConnection();
}