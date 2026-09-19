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

import com.nokia.s60tools.hticonnection.core.RequestQueueManager;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.services.HTIScreenMode;
import com.nokia.s60tools.hticonnection.services.IScreenCaptureService;
import com.nokia.s60tools.hticonnection.services.ScreenCaptureData;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Service that contains interface for capturing images from device.
 */
public class ScreenCaptureService implements IScreenCaptureService{
	
	/**
	 * Print utility used to report errors, warnings, and info messages.
	 */
	private final IConsolePrintUtility printUtility;

	/**
	 * Constructor.
	 * @param printUtility Used for printing messages.
	 */
	public ScreenCaptureService(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IScreenCaptureService#captureFullScreen(java.lang.String, int, long)
	 */
	public byte[] captureFullScreen(String imgMimeType, int colorDepth, long timeout) 
								throws ServiceShutdownException, HTIException, ConnectionException{
		ScreenCaptureRequest request = new ScreenCaptureRequest(imgMimeType, colorDepth, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getByteData();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IScreenCaptureService#captureFullScreenDelta(java.lang.String, int, long)
	 */
	public ScreenCaptureData captureFullScreenDelta(String imgMimeType, int colorDepth, long timeout) 
								throws ServiceShutdownException, HTIException, ConnectionException{
		ScreenCaptureDeltaRequest request = new ScreenCaptureDeltaRequest(imgMimeType, colorDepth, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return new ScreenCaptureData(result.getListData());
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IScreenCaptureService#resetScreenDelta(java.lang.String, int, long)
	 */
	public void resetScreenDelta(long timeout) 
								throws ServiceShutdownException, HTIException, ConnectionException{
		ResetScreenDeltaRequest request = new ResetScreenDeltaRequest(timeout);
		RequestQueueManager.getInstance().submit(request, printUtility);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IScreenCaptureService#getScreenMode(long)
	 */
	public HTIScreenMode getScreenMode(long timeout) 
								throws ServiceShutdownException, HTIException, ConnectionException{
		GetScreenModeRequest request = new GetScreenModeRequest(timeout);	
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);	
		return result.getScreenMode();
	}
}
