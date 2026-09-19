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

import java.io.ByteArrayInputStream;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.resources.Messages;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class ScreenCaptureRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Screen capture"; //$NON-NLS-1$
	
	// Settings for capture.
	private final String imgMimeType;
	private final int colorDepth;
	private final long timeout;

	/**
	 * Capture a full screen.
	 * @param imgMimeType Image MIME type, e.g. "image/png", "image/gif", "image/jpeg"
	 * @param colorDepth Color depth e.g. ConnectionTestService.COLOR_DEPTH_ECOLOR64K.
	 * @param timeout Time that is waited for operation to complete. Use 0 for infinite wait.
	 */
	public ScreenCaptureRequest(String imgMimeType, int colorDepth, long timeout){
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
		
		byte[] result = scService.captureFullScreen(imgMimeType, (byte)colorDepth, timeout);
		
		// Testing image to prevent sending invalid data.
		testImageData(result);
		
		return new RequestResult(result);
    }
	
	/**
	 * Test data by creating image from data.
	 * @param imageData Image in binary format.
	 * @throws Exception Thrown if creating the image fails.
	 */
	private void testImageData(byte[] imageData) throws Exception {
		try {
			// Converting bytes to image to test that image is valid.
			ByteArrayInputStream is = new ByteArrayInputStream(imageData);
			Image image = new Image(Display.getDefault(), is);
			image.dispose();
		} 
		catch(Exception e) {
			// Couldn't create an image from data. Throwing an error.
			// Connection will be reseted in abstractRequest after error.
			throw new Exception(Messages.getString("ScreenCaptureRequest.InvalidImage_Exception_Msg")); //$NON-NLS-1$
		}
	}
}
