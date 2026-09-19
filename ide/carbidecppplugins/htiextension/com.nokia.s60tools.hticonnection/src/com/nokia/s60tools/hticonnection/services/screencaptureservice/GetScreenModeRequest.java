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

package com.nokia.s60tools.hticonnection.services.screencaptureservice;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.services.HTIScreenMode;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class GetScreenModeRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Get screen mode"; //$NON-NLS-1$
	
	// Settings for capture.
	private final long timeout;

	/**
	 * Get screen mode constructor.
	 * @param timeout Time that is waited for operation to complete. Use 0 for infinite wait.
	 */
	public GetScreenModeRequest(long timeout) {
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
		com.nokia.HTI.ScreenCapturingService.HTIScreenMode screenMode = 
				((com.nokia.HTI.ScreenCapturingService.ScreenCapturingService)service)
						.getScreenMode(timeout);
		
		// Creating a new screen mode object, that can be sent to client.
		HTIScreenMode result = new HTIScreenMode(screenMode.getIndex(), screenMode.getWidth(),
												screenMode.getHeight(), screenMode.getRotation(),
												screenMode.getMode(), screenMode.getFocusIndex());
		
		return new RequestResult(result);
    }
}
