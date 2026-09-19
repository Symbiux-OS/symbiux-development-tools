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

import java.util.ArrayList;
import java.util.List;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.HTIMessage;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class TapAndDragRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Tap and drag"; //$NON-NLS-1$
	
	// Settings for tap screen event.
	private final long timeout;
	private final int startX;
	private final int startY;
	private final int endX;
	private final int endY;
	private final int dragTime;

	/**
	 * Tap and drag constructor.
	 * @param startX Horizontal screen location from where dragging starts.
	 * @param startY Vertical screen location from where dragging starts.
	 * @param endX Horizontal screen location where dragging ends.
	 * @param endY Vertical screen location where dragging ends.
	 * @param dragTime Time that it takes from starting point to the end point.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 */
	public TapAndDragRequest(int startX, int startY, int endX, int endY,
			int dragTime, long timeout) {
		super(REQUEST_NAME);
		this.startX = startX;
		this.startY = startY;
		this.endX = endX;
		this.endY = endY;
		this.dragTime = dragTime;
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
		// Creating parameters.
		List<Integer> coordinateList = new ArrayList<Integer>();
		coordinateList.add(startX);
		coordinateList.add(startY);
		coordinateList.add(endX);
		coordinateList.add(endY);
		
		// Using multi point tap and drag because tap and drag didn't work.
		// Dragtime needs to be divided to half because multipoint drag seems to wait
		// that time at the start and at the end.
		HTIMessage result = ((com.nokia.HTI.KeyEventService.KeyEventService)service)
				.tapAndDragMultipoint(dragTime / 2, 0, coordinateList, timeout);
		
		return new RequestResult(result);
    }
}
