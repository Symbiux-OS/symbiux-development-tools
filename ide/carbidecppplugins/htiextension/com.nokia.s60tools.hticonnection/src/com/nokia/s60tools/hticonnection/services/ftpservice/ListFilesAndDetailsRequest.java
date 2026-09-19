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
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.services.FileInfo;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class ListFilesAndDetailsRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "List files"; //$NON-NLS-1$
	
	// Settings for list files request
	private final String remoteDir;
	private final long timeout;
	
	/**
	 * List files in given directory
	 * @param remoteDir Directory
	 * @param timeout Timeout for request
	 */
	public ListFilesAndDetailsRequest(String remoteDir, long timeout){
		super(REQUEST_NAME);
		this.remoteDir = remoteDir;
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
		com.nokia.HTI.FTPService.FileInfo[] result = ((com.nokia.HTI.FTPService.FTPService)service)
						.listFilesWithSize(remoteDir, timeout);
		
		FileInfo[] files = new FileInfo[result.length];
		// Copy data from com.nokia.HTI.FTPService.FileInfo
		// to com.nokia.s60tools.hticonnection.services.FileInfo
		for(int i = 0;i < result.length;i++) {
			files[i] = new FileInfo(result[i].getName(), result[i].getSize());
		}
		
		return new RequestResult(files);
    }
}
