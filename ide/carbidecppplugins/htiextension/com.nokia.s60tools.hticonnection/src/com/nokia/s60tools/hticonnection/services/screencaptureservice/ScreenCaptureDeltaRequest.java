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

import java.io.ByteArrayInputStream;
import java.util.List;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Callable object that can be used to wait for request to complete.
 * The target of this request is to perform screen capture delta on device side.
 */
public class ScreenCaptureDeltaRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Screen capture delta"; //$NON-NLS-1$
	
	// Settings for capture.
	private final String imgMimeType;
	private final int colorDepth;
	private final long timeout;

	/**
	 * Capture a screen delta.
	 * @param imgMimeType Image MIME type, e.g. "image/png", "image/gif", "image/jpeg"
	 * @param colorDepth Color depth e.g. ConnectionTestService.COLOR_DEPTH_ECOLOR64K.
	 * @param timeout Time that is waited for operation to complete. Use 0 for infinite wait.
	 */
	public ScreenCaptureDeltaRequest(String imgMimeType, int colorDepth, long timeout){
		super(REQUEST_NAME);
		this.imgMimeType = imgMimeType;
		this.colorDepth = colorDepth;
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

		List changes = scService.captureFullScreenDelta(imgMimeType, (byte)colorDepth, timeout);
		
		// Testing image to prevent sending invalid data.
		testImageData(changes);
		
		return new RequestResult(changes);
    }
	
	/**
	 * Test data by creating image from data.
	 * @param imageChanges Image in binary format and its coordinates in a list.
	 * @throws Exception Thrown if creating the image fails.
	 */
	private void testImageData(List imageChanges) throws Exception {
		// Check imageData validity.
		if (imageChanges.size() != 5) {
			// Incorrect amount of items in a list.
			throw new Exception(Messages.getString("ScreenCaptureDeltaRequest.InvalidImageData_Exception_Msg")); //$NON-NLS-1$
		}
		// If image is empty, no need to test it.
		if (((byte[])imageChanges.get(4)).length == 0) return;
		try {		
			// Converting bytes to image to test that image is valid.
			ByteArrayInputStream is = new ByteArrayInputStream((byte[])imageChanges.get(4));
			Image image = new Image(Display.getDefault(), is);
			image.dispose();
		} 
		catch(Exception e) {
			// Couldn't create an image from data. Throwing an error.
			// Connection will be reseted in abstractRequest after error.
			throw new Exception(Messages.getString("ScreenCaptureDeltaRequest.InvalidImage_Exception_Msg")); //$NON-NLS-1$
		}
	}
}
