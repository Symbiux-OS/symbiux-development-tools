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

package com.nokia.s60tools.hticonnection.services.applicationcontrolservice;

import com.nokia.HTI.HTIMessage;
import com.nokia.s60tools.hticonnection.core.RequestQueueManager;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIErrorDetails;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.AppStatus;
import com.nokia.s60tools.hticonnection.services.IApplicationControlService;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Service that contains interface for controlling applications on device.
 */
public class ApplicationControlService implements IApplicationControlService{
	
	/**
	 * Print utility used to report errors, warnings, and info messages.
	 */
	private final IConsolePrintUtility printUtility;

	/**
	 * Constructor.
	 * @param printUtility Used for printing messages.
	 */
	public ApplicationControlService(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#startApplicationByName(java.lang.String, java.lang.String, long)
	 */
	public void startApplicationByName(String programName, String docName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException {
		StartApplicationByNameRequest request = new StartApplicationByNameRequest(programName, docName, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("AppControlServiceService.FailedToStartApp_ExceptionMsg") + programName; //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#startProcess(java.lang.String, java.lang.String, long)
	 */
	public void startProcess(String programName, String parameters, long timeout)
			throws ServiceShutdownException, HTIException, ConnectionException {
		StartCommandByNameRequest request = new StartCommandByNameRequest(programName, parameters, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = 
				Messages.getString("AppControlServiceService.FailedToStartProgram_ExceptionMsg_Part1")//$NON-NLS-1$
					+ " "//$NON-NLS-1$
					+ programName 
					+ " "//$NON-NLS-1$
					+ Messages.getString("AppControlServiceService.FailedToStartProgram_ExceptionMsg_Part2")//$NON-NLS-1$
					+ " "//$NON-NLS-1$
					+parameters;
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}

		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#startApplicationByUid(int, java.lang.String, long)
	 */
	public void startApplicationByUid(int uid, String docName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException {
		StartApplicationByUidRequest request = new StartApplicationByUidRequest(uid, docName, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		com.nokia.HTI.ApplicationControlService.AppStatus status = result.getAppStatus();
		if (status.getStatus() == AppStatus.NOT_FOUND) {
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("ApplicationControlService.FailedToStartAppByUidExceptionMsg_Part1")  //$NON-NLS-1$
					+uid +Messages.getString("ApplicationControlService.FailedToStartAppByUid_AppNotFoundExceptionMsg_Suffix");  //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			throw new HTIException(errorMsg, null);
		}
		else if (status.getStatus() == com.nokia.HTI.ApplicationControlService.AppStatus.ALREADY_RUNNING) {
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("ApplicationControlService.FailedToStartAppByUidExceptionMsg_Part1")  //$NON-NLS-1$
					+uid +Messages.getString("ApplicationControlService.FailedToStartAppByUidAlreaydyRunningExceptionMsg_Suffix"); //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			throw new HTIException(errorMsg, null);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#getApplicationStatusByName(java.lang.String, long)
	 */
	public AppStatus getApplicationStatusByName(String programName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException {
		GetApplicationStatusByNameRequest request = new GetApplicationStatusByNameRequest(programName, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		return new AppStatus(result.getAppStatus().getStatus());
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#getApplicationStatusByUid(int, long)
	 */
	public AppStatus getApplicationStatusByUid(int uid, long timeout) throws ServiceShutdownException, HTIException, ConnectionException {
		GetApplicationStatusByUidRequest request = new GetApplicationStatusByUidRequest(uid, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		return new AppStatus(result.getAppStatus().getStatus());
		}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#stopApplicationByName(java.lang.String, long)
	 */
	public void stopApplicationByName(String programName, long timeout) throws ServiceShutdownException, HTIException, ConnectionException {
		StopApplicationByNameRequest request = new StopApplicationByNameRequest(programName, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("AppControlServiceService.FailedToStopApp_ExceptionMsg") + programName; //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IApplicationControlService#stopApplicationByUid(int, long)
	 */
	public void stopApplicationByUid(int uid, long timeout) throws ServiceShutdownException, HTIException, ConnectionException {
		StopApplicationByUidRequest request = new StopApplicationByUidRequest(uid, timeout);
		RequestQueueManager.getInstance().submit(request, printUtility);
		// No need to check result. Returned AppStatus is Ok or NotFound.
	}
}
