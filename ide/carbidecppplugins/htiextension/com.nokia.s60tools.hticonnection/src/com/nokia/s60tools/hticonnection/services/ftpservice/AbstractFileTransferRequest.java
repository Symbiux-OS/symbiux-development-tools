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

import java.util.concurrent.FutureTask;

import com.nokia.HTI.FTPService.FTPService;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.services.IFTPListener;
import com.nokia.s60tools.hticonnection.services.IFTPRequestManager;

/**
 * Abstract file transfer class that hold implementation for canceling
 * file transfer operation.
 */
public abstract class AbstractFileTransferRequest extends AbstractRequest implements IFTPRequestManager {
	
	/**
	 * Listener for this operation.
	 */
	private final IFTPListener listener;
	/**
	 * File that is transfered.
	 */
	protected final String remoteFile;
	/**
	 * This service is set when file transfer has started.
	 * This instance can be canceled then if needed. 
	 */
	private FTPService startedService = null;
	
	/**
	 * Constructor.
	 * @param remoteFile File in target to be transfered.
	 * @param listener Listener for this request. Or null if information is not needed.
	 * @param requestName Name of this request.
	 */
	public AbstractFileTransferRequest(String remoteFile, IFTPListener listener, String requestName){
		super(requestName);
		this.remoteFile = remoteFile;
		this.listener = listener;
	}

	/**
	 * Getter for remote file name.
	 * @return Remote file name.
	 */
	public String getRemoteFileName() {
		return remoteFile;
	}

	/**
	 * Sets future task so that file transfer can be canceled by using it.
	 * @param resultFutureTask
	 */
	public void setFutureTask(FutureTask<RequestResult> resultFutureTask) {
		this.resultFutureTask = resultFutureTask;
		if(requestCanceled) {
			// Cancel has already been ordered.
			resultFutureTask.cancel(false);
		}
	}

	/**
	 * Informs listener that request has been put into the queue.
	 */
	public void informInQueue() {
		if(listener != null) {
			listener.requestInQueue(this);
		}
	}

	/**
	 * Informs listener that request has been started.
	 */
	public void informStarted() {
		if(listener != null) {
			listener.requestStarted(this);
		}
	}

	/**
	 * Informs listener that request has been ended
	 */
	public void informEnded() {
		if(listener != null) {
			listener.requestEnded(this);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPRequestManager#getFileName()
	 */
	public String getFileName() {
		return remoteFile;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPRequestManager#cancel()
	 */
	@Override
	public void cancel() {
		requestCanceled = true;
		FTPService service = getStartedService();
		
		if(service != null) {
			service.cancelCurrentTransfer();
		}
		// Checking that future task has been created and request is not transferring the data.
		// It future task is canceled when transfer is ongoing, then transfer would continue in background.
		if(resultFutureTask != null && service == null) {
			resultFutureTask.cancel(false);
		}
	}
	
	/**
	 * Getter for service that is used in this request.
	 * @param startedService Service that is used in this request.
	 */
	public synchronized void setStartedService(FTPService startedService) {
		this.startedService = startedService;
	}
	
	/**
	 * Setter for service that is used in this request.
	 * @return Service that is used in this request.
	 */
	public synchronized FTPService getStartedService() {
		return startedService;
	}
}
