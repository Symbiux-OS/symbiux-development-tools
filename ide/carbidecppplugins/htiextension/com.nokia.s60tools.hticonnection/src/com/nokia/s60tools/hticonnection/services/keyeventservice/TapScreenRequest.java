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
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class TapScreenRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Tap screen"; //$NON-NLS-1$
	
	// Settings for tap screen event.
	private final long timeout;
	private final int x;
	private final int y;
	private final int tapCount;
	private final int timeToHold;
	private final int pauseBetweenTaps;

	/**
	 * Tap screen constructor.
	 * @param x Horizontal screen location of tap screen event in pixels.
	 * @param y Vertical screen location of tap screen event in pixels.
	 * @param tapCount Count of taps within this event.
	 * @param timeToHold How long one tap lasts in milliseconds.
	 * @param pauseBetweenTaps How much time there is between taps in milliseconds.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 */
	public TapScreenRequest(int x, int y, int tapCount, int timeToHold,
			int pauseBetweenTaps, long timeout) {
		super(REQUEST_NAME);
		this.x = x;
		this.y = y;
		this.tapCount = tapCount;
		this.timeToHold = timeToHold;
		this.pauseBetweenTaps = pauseBetweenTaps;
		this.timeout = timeout;
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
						.tapScreen(x, y, tapCount, timeToHold, pauseBetweenTaps, timeout);
		return new RequestResult(result);
    }
}
