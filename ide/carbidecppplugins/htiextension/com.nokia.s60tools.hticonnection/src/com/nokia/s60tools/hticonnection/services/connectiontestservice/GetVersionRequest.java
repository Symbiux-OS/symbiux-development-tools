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
import com.nokia.s60tools.hticonnection.services.HTIVersion;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class GetVersionRequest extends AbstractRequest{

	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Get version"; //$NON-NLS-1$

	// Values that are used to test service.
	private final int WAIT_TIME = 1500;
	
	/**
	 * Constructor.
	 */
	public GetVersionRequest(){
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
		// Copy data from com.nokia.HTI.HTIVersion 
		// to com.nokia.s60tools.hticonnection.services.HTIVersion 
		com.nokia.HTI.HTIVersion version = ((com.nokia.HTI.BaseService)service).getVersion(WAIT_TIME);
		return new RequestResult(new HTIVersion(version.getMajor(), version.getMinor()));
    }
}
