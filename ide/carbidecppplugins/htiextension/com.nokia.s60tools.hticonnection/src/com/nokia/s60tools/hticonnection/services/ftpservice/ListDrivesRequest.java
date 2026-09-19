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

import java.util.Vector;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.services.DriveInfo;

/**
 * Callable object that can be used to wait for request to complete.
 */
public class ListDrivesRequest extends AbstractRequest{
	
	/**
	 * Name used for request.
	 */
	private static final String REQUEST_NAME = "List drives"; //$NON-NLS-1$
	
	// Settings for list drive request
	private final long timeout;
	
	/**
	 * List drives on device
	 * @param timeout Timeout for request
	 */
	public ListDrivesRequest(long timeout){
		super(REQUEST_NAME);
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
		com.nokia.HTI.FTPService.DriveInfo[] result = ((com.nokia.HTI.FTPService.FTPService)service)
						.listDrives(timeout);
		
		Vector<DriveInfo> driveInfos = new Vector<DriveInfo>();
		
		for (int i = 0; i < result.length; i++) {
			// Copy data from com.nokia.HTI.FTPService.DriveInfo
			// to com.nokia.s60tools.hticonnection.services.DriveInfo
			DriveInfo info = new DriveInfo(
					result[i].getRootPath(),
					result[i].getName(),
					DriveInfo.MediaType.valueOf(result[i].getType().toString()),
					result[i].getUid(),
					result[i].getSize(),
					result[i].getFreeSpace());
			
			driveInfos.add(info);
		}
		
		return new RequestResult(driveInfos.toArray(new DriveInfo[0]));
    }
}
