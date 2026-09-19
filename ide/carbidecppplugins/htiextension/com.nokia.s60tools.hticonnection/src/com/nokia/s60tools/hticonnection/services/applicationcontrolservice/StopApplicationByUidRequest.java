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
public class StopApplicationByUidRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Stop application with UID"; //$NON-NLS-1$
	
	// Settings for stop application request
	private final int uid;
	private final long timeout;
	
	/**
	 * Stop application
	 * @param uid Application UID
	 * @param timeout Timeout for request
	 */
	public StopApplicationByUidRequest(int uid, long timeout){
		super(REQUEST_NAME);
		this.uid = uid;
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
		AppStatus status = ((com.nokia.HTI.ApplicationControlService.ApplicationControlService)service)
						.stopApplicationByUID(uid, timeout);
		return new RequestResult(status);
    }
}
