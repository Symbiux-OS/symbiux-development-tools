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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferencePage;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.HTIVersion;
import com.nokia.s60tools.hticonnection.services.connectiontestservice.GetVersionRequest;
import com.nokia.s60tools.hticonnection.services.ftpservice.AbstractFileTransferRequest;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Singleton class that handles messages one at the time.
 */
public class RequestQueueManager {

	/**
	 * Service that holds requests in line and handles them.
	 */
	private ExecutorService executorService;
	/**
	 * Instance of this singleton class.
	 */
	private static RequestQueueManager instance = null;

	/**
	 * List of requests that are currently in queue or executing.
	 */
	private static List<AbstractRequest> requests;
	
	/**
	 * Private constructor to prevent creating new instances.
	 */
	private RequestQueueManager(){
		executorService = Executors.newSingleThreadExecutor();
	}
	
	/**
	 * Only one instance can exist at one time.
	 * @return Current instance.
	 */
	public static synchronized RequestQueueManager getInstance(){
		if( instance == null ){
			instance = new RequestQueueManager();
			requests = Collections.synchronizedList(new ArrayList<AbstractRequest>());
		}
		return instance;
	}
	
	/**
	 * Resets manager so that it can be used again after stop if needed.
	 */
	public void reset(){
		instance = null;
	}
	
	/**
	 * ExecutorService needs to be shutdown, so that it won't stay active.
	 * This should be run after there won't be new tasks coming.
	 */
	@SuppressWarnings("unchecked")
	public void stop(){
		// Shutting down all
		List<Runnable> requests = executorService.shutdownNow();
		
		for(Runnable runnable : requests) {
			if(runnable instanceof FutureTask) {
				// Warning from this unchecked cast is suppressed as it doesn't
				// matter what type of FutureTask it is. Just canceling it.
				FutureTask request = (FutureTask)runnable;
				request.cancel(false);
			}
		}
		
		try {
			// Waiting for commands to complete for one second and then shutting down..
			executorService.awaitTermination(100, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			// Service is being shutdown. Exception doesn't matter anymore.
		}
	}
	
	/**
	 * Submits request to ExecutorService and returns result for request.
	 * @param requestCallable Request that will be added to queue.
	 * @param printUtility Used for printing messages.
	 * @return RequestResult contains request.
	 * @throws ConnectionException Connection failed.
	 * @throws HTIException Thrown when connection HTI agent could not be initialized.
	 * @throws ServiceShutdownException Thrown if services have been shut down
	 */
	public RequestResult submit(AbstractRequest requestCallable, IConsolePrintUtility printUtility)
						throws HTIException, ConnectionException, ServiceShutdownException {
		// Checking if services are shutdown.
		ConnectionStatus status = HtiConnection.getInstance().getConnectionStatus();
		if(executorService.isShutdown() || status != ConnectionStatus.CONNECTED){
			throw new ServiceShutdownException(Messages.getString("RequestQueueManager.ServiceShutdown_Exception_Msg")); //$NON-NLS-1$
		}
		
		// Creating future task that holds request.
		FutureTask<RequestResult> resultFutureTask = new FutureTask<RequestResult>(requestCallable);
		requestCallable.setFutureTask(resultFutureTask);
		
		// Handling FTP Request before putting them in to queue.
		if(requestCallable instanceof AbstractFileTransferRequest) {
			AbstractFileTransferRequest ftpRequest = (AbstractFileTransferRequest) requestCallable;
			ftpRequest.informInQueue();
		}
			
		// Submitting the task to executor service.
		executorService.submit(resultFutureTask);
		return getResult(requestCallable, resultFutureTask, printUtility);
		
	}
	
	/**
	 * Returns result from resultFutureTasks.
	 * @param requestCallable Request that will be added to queue.
	 * @param resultFutureTask Future task that is created from requestCallable.
	 * @param printUtility printUtility Used for printing messages.
	 * @return RequestResult contains request.
	 * @throws ConnectionException Connection failed.
	 * @throws HTIException Thrown when connection HTI agent could not be initialized.
	 * @throws ServiceShutdownException Thrown if services have been shut down
	 */
	private RequestResult getResult(AbstractRequest requestCallable,
									FutureTask<RequestResult> resultFutureTask,
									IConsolePrintUtility printUtility)
											throws HTIException, ConnectionException, ServiceShutdownException {

		// Keeping requests in list, so that they can be canceled if necessary.
		synchronized(requests) {
			requests.add(requestCallable);
		}
		
		RequestResult result = null;
		try {
			// Get method blocks the call, until computation of the result is finished or exception has arisen.
			result = resultFutureTask.get();
		} catch (CancellationException e) {
			if(HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.CONNECTED) {
				// Not connected to service.
				throw new ServiceShutdownException(Messages.getString("RequestQueueManager.Datagateway_Shutdown_Exception_Msg")); //$NON-NLS-1$
			}
			
			// Request was canceled. Informing caller.
			throw new HTIException(requestCallable.getRequestName()
					 + Messages.getString("RequestQueueManager.RequestCanceled_Exception_Msg0"), true, true); //$NON-NLS-1$
		} catch (InterruptedException e) {
			handleInterrupedException(requestCallable, e, printUtility);
		} catch (ExecutionException e) {
			handleExecutionException(requestCallable, e, printUtility);
		} finally {
			// Removing requests that have been already executed.
			synchronized(requests) {
				requests.remove(requestCallable);
			}
			
			if(requestCallable instanceof AbstractFileTransferRequest) {
				// Handling informing about the end of the FTP Request.
				((AbstractFileTransferRequest) requestCallable).informEnded();
			}
		} 
		
		return result;
	}
	
	/**
	 * Tests connection by sending get version request to the device.
	 * This method is used to test connection when it is in testing state.
	 * Throws exceptions which have occurred when getting the response. 
	 * @return True if connection is up and running. False if request failed to
	 * get data or connection is down.
	 * @throws InterruptedException if the request was interrupted while waiting. 
	 * @throws ExecutionException if the request threw an exception.
	 */
	public boolean testConnection() throws InterruptedException, ExecutionException {
		
		ConnectionStatus status = HtiConnection.getInstance().getConnectionStatus();
		
		// Checking if service is shutdown.
		if(status == ConnectionStatus.SHUTDOWN || executorService.isShutdown() ){
			return false;
		}
		
		GetVersionRequest request = new GetVersionRequest();
		
		// Creating future task that holds request.
		FutureTask<RequestResult> resultFutureTask = new FutureTask<RequestResult>(request);
		
		// Submitting the task to executor service.
		executorService.submit(resultFutureTask);

		// Get method blocks the call, until computation of the result is finished or exception has arisen.
		RequestResult result = resultFutureTask.get();
		HTIVersion version = result.getHTIVersionData();
		
		if (version != null) {
			// Request succeeded
			HtiConnection.getInstance().setHTIVersion(version);
			return true;
		}
		
		// Request didn't return value.
		return false;
	}
	
	/**
	 * Cancels all requests that are currently in queue.
	 */
	public void cancelRequestsInQueue() {
		synchronized(requests) {
			Iterator<AbstractRequest> iter = requests.iterator();
			while(iter.hasNext()) {
				iter.next().cancel();
			}
		}
	}
	
	/**
	 * Handling executionExceptions. Logging errors and throwing correct exception.
	 * @param requestCallable Request that caused the exception.
	 * @param exception Exception to be handled.
	 * @param printUtility Used for printing messages.
	 * @throws ConnectionException Thrown if connection failed.
	 * @throws HTIException Thrown if couldn't connect to HTI agent.
	 * @throws ServiceShutdownException Thrown if services have been shut down.
	 */
	private void handleExecutionException(AbstractRequest requestCallable, ExecutionException exception,
			IConsolePrintUtility printUtility)
				throws ConnectionException, HTIException, ServiceShutdownException {
		// Testing if service has been shutdown.
		if(HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.CONNECTED){
			throw new ServiceShutdownException(Messages.getString("RequestQueueManager.Datagateway_Shutdown_Exception_Msg")); //$NON-NLS-1$
		}
		
		// Handling exceptions arisen when running call.
		printUtility.println(requestCallable.getRequestName() +
				Messages.getString("RequestQueueManager.RequestFailed_Exception_Msg") + //$NON-NLS-1$
				exception.getCause().getMessage(),
				IConsolePrintUtility.MSG_ERROR);
		
		handleConnectionFailure(requestCallable, exception, printUtility);

		// Checking if problem was that HTI agent couldn't be initialized.
		if (exception.getMessage().equals("com.nokia.HTI.HTIException: HTI NOT INITIALIZED")) { //$NON-NLS-1$
			throw new HTIException(exception.getMessage(), false);
		}
		
		throw new ConnectionException(exception.getMessage());
	}

	/**
	 * Handling executionExceptions. Logging errors and throwing correct exception.
	 * @param requestCallable Request that caused the exception.
	 * @param exception exception Exception to be handled.
	 * @param printUtility Used for printing messages.
	 * @throws ConnectionException Thrown if connection failed.
	 * @throws ServiceShutdownException Thrown if services have been shut down.
	 */
	private void handleInterrupedException(AbstractRequest requestCallable,
			InterruptedException exception, IConsolePrintUtility printUtility)
					throws ConnectionException, ServiceShutdownException {
		// Testing if service has been shutdown.
		if(HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.CONNECTED){
			throw new ServiceShutdownException(Messages.getString("RequestQueueManager.Datagateway_Shutdown_Exception_Msg")); //$NON-NLS-1$
		}
		
		// Handling interrupts in thread handling.
		printUtility.println(requestCallable.getRequestName() +
				Messages.getString("RequestQueueManager.RequestInterrupted_Exception_Msg") + //$NON-NLS-1$
				exception.getCause().getMessage(),
				IConsolePrintUtility.MSG_ERROR);
		throw new ConnectionException(exception.getMessage());
	}
	
	/**
	 * Doing necessary actions when connection failure happens.
	 * Informs user when connection/request fails because of connection problems
	 * and offers possibility to change/test connection settings.
	 * @param requestCallable Request that caused the exception.
	 * @param exception Exception to be handled.
	 * @param printUtility Used for printing messages.
	 */
	private synchronized void handleConnectionFailure(final AbstractRequest requestCallable,
			ExecutionException exception, IConsolePrintUtility printUtility)
				throws ServiceShutdownException{
		// Showing error message only if connection is up and preferences page is not open.
		if(HtiConnection.getInstance().getConnectionStatus() != ConnectionStatus.CONNECTED
				&& HtiApiPreferencePage.isCreated()){
			return;
		}
		
		boolean isReported = HtiConnection.getInstance().reportConnectionError(exception);
		
		if(isReported) {
			// Error reported and service is now in CONNECTING state because of connection problem.
			throw new ServiceShutdownException(Messages.getString("RequestQueueManager.Datagateway_Shutdown_Exception_Msg")); //$NON-NLS-1$
		}

		// It is now checked that HTI was initialized correctly and connection was established.
		// Leaving handling of other problems to the client.
	}
}
