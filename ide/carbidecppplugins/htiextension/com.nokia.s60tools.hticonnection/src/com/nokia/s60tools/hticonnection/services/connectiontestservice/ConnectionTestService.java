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

package com.nokia.s60tools.hticonnection.services.connectiontestservice;

import java.util.List;

import com.nokia.s60tools.hticonnection.core.RequestQueueManager;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.services.HTIVersion;
import com.nokia.s60tools.hticonnection.services.IConnectionTestService;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Service that contains interface for testing if connection is ready for use.
 */
public class ConnectionTestService implements IConnectionTestService{
	
	/**
	 * Print utility used to report errors, warnings, and info messages.
	 */
	private final IConsolePrintUtility printUtility;

	/**
	 * Constructor. 
	 * @param printUtility Used for printing messages.
	 */
	public ConnectionTestService(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IConnectionTestService#isReady()
	 */
	public boolean isReady(){
		ConnectionTestRequest request = new ConnectionTestRequest();
		
		try {
			// Getting result for connection test.
			RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
			return result.getBooleanData();
		} catch (Exception e) {
			// Request failed.
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IConnectionTestService#listServices()
	 */
	public List<String> listServices() throws HTIException, ConnectionException, ServiceShutdownException{
		ListServiceRequest request = new ListServiceRequest();
		
		// Getting result for connection test.
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getListData();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IConnectionTestService#getVersion()
	 */
	public HTIVersion getVersion() throws HTIException, ConnectionException, ServiceShutdownException{
		GetVersionRequest request = new GetVersionRequest();
		
		// Getting result for connection test.
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getHTIVersionData();
	}
}
