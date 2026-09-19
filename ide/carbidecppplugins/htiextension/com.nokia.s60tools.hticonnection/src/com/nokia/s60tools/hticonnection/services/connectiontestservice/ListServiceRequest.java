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

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class ListServiceRequest extends AbstractRequest{

	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "List services"; //$NON-NLS-1$

	// Values that are used to test service.
	private final int WAIT_TIME = 1000;
	
	/**
	 * Constructor.
	 */
	public ListServiceRequest(){
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
	// Supressing warning from unchecked cast from listServices operation.
	@SuppressWarnings("unchecked")
	public RequestResult invokeService(IService service) throws Exception{
		List<String> services = (List<String>)((com.nokia.HTI.BaseService)service)
						.listServices(WAIT_TIME);
		return new RequestResult(services);
    }
}
