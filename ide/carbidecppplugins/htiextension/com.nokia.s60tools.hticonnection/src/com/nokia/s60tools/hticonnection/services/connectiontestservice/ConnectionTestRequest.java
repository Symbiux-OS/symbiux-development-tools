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

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.RequestFailedException;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class ConnectionTestRequest extends AbstractRequest{

	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Connection test"; //$NON-NLS-1$

	// Values that are used to test service.
	private final int WAIT_TIME = 1000;
	private final String ECHO_STR = "Test"; //$NON-NLS-1$
	
	/**
	 * Capture a full screen.
	 */
	public ConnectionTestRequest(){
		super(REQUEST_NAME);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#createService()
	 */
	public BaseService createService(){
		return new com.nokia.HTI.BaseService();
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#invokeService(com.nokia.HTI.IService)
	 */
	public RequestResult invokeService(IService service) throws Exception{
		String echo = ((com.nokia.HTI.BaseService)service)
						.echo(ECHO_STR, WAIT_TIME);
		
		// Testing that correct string is returned.
		if(!echo.equals(ECHO_STR)){
			throw new RequestFailedException(REQUEST_NAME + 
					Messages.getString("ConnectionTestRequest.WrongResultReturned_Exception_Msg")); //$NON-NLS-1$
		}
		
		// Returning result that request was successful.
		RequestResult requestResult = new RequestResult(true);
		return requestResult;
    }
}
