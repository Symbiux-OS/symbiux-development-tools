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

package com.nokia.s60tools.hticonnection.services.ftpservice;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.HTIMessage;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.IFTPListener;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class UploadFileRequest extends AbstractFileTransferRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "Upload file"; //$NON-NLS-1$
	
	// Settings for upload file request
	private final byte[] fileData;
	private final long timeout;
	
	/**
	 * Uploads file to device
	 * @param fileData  Bytes read from local file
	 * @param remoteFile File in target where to write data
	 * @param listener Listener for this request. Or null if information is not needed.
	 * @param timeout Timeout for request
	 */
	public UploadFileRequest(byte[] fileData, String remoteFile, IFTPListener listener, long timeout){
		super(remoteFile, listener, REQUEST_NAME);
		this.fileData = fileData;
		this.timeout = timeout;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#createService()
	 */
	public BaseService createService(){
		return new com.nokia.HTI.FTPService.FTPService();
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.core.AbstractRequest#invokeService(com.nokia.HTI.IService)
	 */
	public RequestResult invokeService(IService service) throws Exception{
		informStarted();
		// Checking if request has been canceled.
		if(requestCanceled) {
			throw new HTIException(REQUEST_NAME + Messages.getString("UploadFileRequest.RequestCanceled.Exception.Msg0"), true, true); //$NON-NLS-1$
		}
		setStartedService((com.nokia.HTI.FTPService.FTPService)service);

		// Sending upload file request.
		HTIMessage result;
		try {
			result = ((com.nokia.HTI.FTPService.FTPService)service)
					.uploadFile(fileData, remoteFile, timeout);
		} finally {
			setStartedService(null);
			// Checking if request was canceled while uploading file.
			if(requestCanceled) {
				resultFutureTask.cancel(false);
			}
		}
		return new RequestResult(result);
    }
}
