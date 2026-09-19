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

package com.nokia.s60tools.hticonnection.services.applicationcontrolservice;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.HTI.ApplicationControlService.AppStatus;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class GetApplicationStatusByNameRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Get application status with name"; //$NON-NLS-1$
	
	// Settings for request
	private final String appName;
	private final long timeout;
	
	/**
	 * Get application status
	 * @param appName Application name (with full path if necessary)
	 * @param timeout Timeout for request
	 */
	public GetApplicationStatusByNameRequest(String appName, long timeout){
		super(REQUEST_NAME);
		this.appName = appName;
		this.timeout = timeout;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#createService()
	 */
	public BaseService createService(){
		return new com.nokia.HTI.ApplicationControlService.ApplicationControlService();
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#invokeService(com.nokia.HTI.IService)
	 */
	public RequestResult invokeService(IService service) throws Exception{
		AppStatus result = ((com.nokia.HTI.ApplicationControlService.ApplicationControlService)service)
						.getApplicationStatus(appName, timeout);
		return new RequestResult(result);
    }
}
