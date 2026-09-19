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

import java.util.List;

import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;

/**
 * Service that contains interface for testing if connection is ready for use.
 */
public interface IConnectionTestService {
	
	/**
	 * Tests if connection is ready for use.
	 * @return <code>True</code> if connection can be used. 
	 *         <code>False</code> if connection doesn't work as expected.
	 */
	public boolean isReady();
	
	/**
	 * List available services
	 * @return List of available services
	 * @throws ServiceShutdownException 
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI.
	 */
	public List<String> listServices() throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Get HTI version
	 * @return HTIVersion instance containing version information
	 * @throws ServiceShutdownException 
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI.
	 */
	public HTIVersion getVersion() throws HTIException, ConnectionException, ServiceShutdownException;
}
