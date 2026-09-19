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

package com.nokia.s60tools.hticonnection.services.keyeventservice;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.HTIMessage;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.RequestResult;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class TypeTextRequest extends AbstractKeyRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Type text"; //$NON-NLS-1$
	/**
	 * Text to be typed.
	 */
	private final String typeStr;
	
	/**
	 * Constructor.
	 * @param typeStr Text to be typed.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 */
	public TypeTextRequest(String typeStr, long timeout) {
		super(REQUEST_NAME, timeout);
		this.typeStr = typeStr;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#createService()
	 */
	public BaseService createService(){
		return new com.nokia.HTI.KeyEventService.KeyEventService();
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#invokeService(com.nokia.HTI.IService)
	 */
	public RequestResult invokeService(IService service) throws Exception{
		HTIMessage result = ((com.nokia.HTI.KeyEventService.KeyEventService)service)
				.typeText(typeStr, timeout);		
		return new RequestResult(result);
    }
}
