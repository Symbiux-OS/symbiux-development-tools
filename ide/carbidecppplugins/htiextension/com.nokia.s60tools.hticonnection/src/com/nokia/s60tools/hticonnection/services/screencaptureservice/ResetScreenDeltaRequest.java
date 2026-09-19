/*
* Copyright (c) 2010 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.hticonnection.services.screencaptureservice;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.HTIMessage;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;

/**
 * Callable object that can be used to wait for request to complete.
 * The target of this request is to perform reset screen delta on device side.
 */
public class ResetScreenDeltaRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Reset screen delta"; //$NON-NLS-1$
	
	// Settings for capture.
	private final long timeout;

	/**
	 * Reset screen delta.
	 * @param timeout Time that is waited for operation to complete. Use 0 for infinite wait.
	 */
	public ResetScreenDeltaRequest(long timeout){
		super(REQUEST_NAME);
		this.timeout = timeout;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#createService()
	 */
	public BaseService createService(){
		return new com.nokia.HTI.ScreenCapturingService.ScreenCapturingService();
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#invokeService(com.nokia.HTI.IService)
	 */
	public RequestResult invokeService(IService service) throws Exception{
		
		com.nokia.HTI.ScreenCapturingService.ScreenCapturingService scService = 
			(com.nokia.HTI.ScreenCapturingService.ScreenCapturingService)service;

		HTIMessage result = scService.resetScreenDelta(timeout);
			
		return new RequestResult(result);
    }
}
