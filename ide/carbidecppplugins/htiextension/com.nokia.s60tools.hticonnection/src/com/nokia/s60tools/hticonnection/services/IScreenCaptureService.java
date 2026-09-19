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

package com.nokia.s60tools.hticonnection.services;

import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;

/**
 * Service that contains interface for capturing images from device.
 */
public interface IScreenCaptureService {

	/**
	 * Captures a full screen with specified type and color amount.
	 * @param imgMimeType Image MIME type, e.g. "image/png", "image/gif", "image/jpeg"
	 * @param colorDepth Color depth e.g. ConnectionTestService.COLOR_DEPTH_ECOLOR64K.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return Full screen image with specified type as byte array.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public byte[] captureFullScreen(String imgMimeType, int colorDepth, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Captures a screen delta with specified type and color amount.
	 * Screen delta means changes in screen after last screen capture delta.
	 * @param imgMimeType Image MIME type, e.g. "image/png", "image/gif", "image/jpeg"
	 * @param colorDepth Color depth e.g. ConnectionTestService.COLOR_DEPTH_ECOLOR64K.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return Screen capture data including changed part of the screen as byte[] and the location of it.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public ScreenCaptureData captureFullScreenDelta(String imgMimeType, int colorDepth, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * 
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ConnectionException Connection failed
	 */
	public void resetScreenDelta(long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Gets information about the screen. This information includes width, height, rotation, and display mode
	 * of the screen. Also screen index and currently focused screen index is returned.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return HTIScreenMode object that contains information about the screen.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public HTIScreenMode getScreenMode(long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
}
