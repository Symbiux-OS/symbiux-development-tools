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
public class ReleaseKeyRequest extends AbstractKeyRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Release key"; //$NON-NLS-1$
	/**
	 * Scan code for the key.
	 */
	private final int scanCode;
	
	/**
	 * Constructor.
	 * @param scanCode Scan code for the key.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 */
	public ReleaseKeyRequest(int scanCode, long timeout) {
		super(REQUEST_NAME, timeout);
		this.scanCode = scanCode;
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
		// Making byte array from integer scan code		
		byte[] scanCodeToByteArr = convertScanCodeToByteArr(scanCode);
		// Sending request
		HTIMessage result = ((com.nokia.HTI.KeyEventService.KeyEventService)service)
																.releaseKey(scanCodeToByteArr, timeout);		
		return new RequestResult(result);
    }
	
}
