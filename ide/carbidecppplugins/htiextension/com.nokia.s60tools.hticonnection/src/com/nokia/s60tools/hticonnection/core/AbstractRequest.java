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

package com.nokia.s60tools.hticonnection.core;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.nokia.HTI.BaseService;
import com.nokia.HTI.IService;
import com.nokia.s60tools.hticonnection.gateway.DataGatewayConstants;

/**
 * Abstract request class which is used as base for HTI requests.
 */
public abstract class AbstractRequest implements Callable<RequestResult>{

	/**
	 * Name of the request.
	 */
	protected final String name;
	/**
	 * Future task that has been created from this request. It is needed so that this request can
	 * be canceled properly.
	 */
	protected FutureTask<RequestResult> resultFutureTask = null;
	/**
	 * This holds information whether request has been canceled.
	 */
	protected boolean requestCanceled = false;
	
	/**
	 * Constructor.
	 * @param name Name of the request.
	 */
	protected AbstractRequest(String name){
		this.name = name;
	}

	/**
	 * Getter for name.
	 * @return Name of the request.
	 */
	public String getRequestName(){
		return name;
	}
	
	/**
	 * Sets future task so that file transfer can be canceled by using it.
	 * @param resultFutureTask Task created from this request.
	 */
	public void setFutureTask(FutureTask<RequestResult> resultFutureTask) {
		this.resultFutureTask = resultFutureTask;
		if(requestCanceled) {
			// Cancel has already been ordered.
			resultFutureTask.cancel(false);
		}
	}
	
	/**
	 * Cancels this request by canceling future task.
	 */
	public void cancel() {
		requestCanceled = true;
		// Checking that future task has been created and then canceling it.
		if(resultFutureTask != null) {
			resultFutureTask.cancel(false);
		}
	}
	
	/**
	 * Actual method that contains code to send the request and return the response.
	 * @return Result for call inside a RequesResult class.
	 * @throws Exception Thrown if screen capture fails.
	 */
	public RequestResult call() throws Exception {
		// Initializing service.
		BaseService service = createService();
		// Getting settings and connecting.
		String host = DataGatewayConstants.HOST;
		int port = DataGatewayConstants.PORT;
		service.connect(host, port);
		
		// Sending upload request to HTI API.
		try{
			// Invoking request
			RequestResult requestResult = invokeService(service);
			return requestResult;
			
		} catch(Exception e){
			// Connection needs to be restarted in case of communication errors
			// to make sure that HTI works correctly. 
			service.detach();
			throw e;
		}
	}
	
	//
	// Abstract methods to be implemented by sub classes.	
	//
	
	/**
	 * Create service that is used for this request.
	 * @return Service that has been inherited from BaseService.
	 */
	public abstract BaseService createService(); 	
	
	/**
	 * Invoke necessary actions for getting result and return result.
	 * @param service Service that has been created in createService method.
	 * @return Result from request.
	 * @throws Exception Exception 
	 */
	public abstract RequestResult invokeService(IService service) throws Exception;
}
