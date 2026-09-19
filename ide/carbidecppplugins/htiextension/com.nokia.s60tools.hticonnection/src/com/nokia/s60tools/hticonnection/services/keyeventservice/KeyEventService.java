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

package com.nokia.s60tools.hticonnection.services.keyeventservice;

import com.nokia.HTI.HTIMessage;
import com.nokia.s60tools.hticonnection.core.RequestQueueManager;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIErrorDetails;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.IKeyEventService;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Class contains implementation for sending key events to device.
 */
public class KeyEventService implements IKeyEventService {
	
	/**
	 * Print utility used to report errors, warnings, and info messages.
	 */
	private final IConsolePrintUtility printUtility;

	/**
	 * Constructor.
	 * @param printUtility Used for printing messages.
	 */
	public KeyEventService(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;
	}
	
	/**
	 * Checks result message for errors and reports them further via exception handling if needed.
	 * @param result Result to be checked.
	 * @param errorMsg Error message to be used if error is encountered.
	 * @throws HTIException
	 */
	private void checkResultForErrors(RequestResult result, String errorMsg)
			throws HTIException {
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#tapScreen(int, int, int, int, int, long)
	 */
	public void tapScreen(int x, int y, int tapCount, int timeToHold, int pauseBetweenTaps, long timeout)
								throws ServiceShutdownException, HTIException, ConnectionException {
		
		TapScreenRequest request = new TapScreenRequest(x, y, tapCount, timeToHold, pauseBetweenTaps, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedTapScreen_Exception_Msg"); //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#tapAndDrag(int, int, int, int, int, long)
	 */
	public void tapAndDrag(int startX, int startY, int endX, int endY, int dragTime, long timeout)
								throws ServiceShutdownException, HTIException, ConnectionException {
		
		TapAndDragRequest request = new TapAndDragRequest(startX, startY, endX, endY, dragTime, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedTapAndDrag_Exception_Msg"); //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#pressKey(int)
	 */
	public void pressKey(int scanCode, long timeout) throws HTIException, ConnectionException, ServiceShutdownException {
		
		PressKeyRequest request = new PressKeyRequest(scanCode, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedToSendPressKeyEvent_ExceptionMsg");  //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#pressKeyLong(int, int, long)
	 */
	public void pressKeyLong(int scanCode, int holdTime, long timeout) 
			throws ServiceShutdownException, HTIException, ConnectionException {
		
		PressKeyLongRequest request = new PressKeyLongRequest(scanCode, holdTime, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedToSendPressAndHoldKeyEvent_ExceptionMsg"); //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#releaseKey(int)
	 */
	public void releaseKey(int scanCode, long timeout) throws HTIException, ConnectionException, ServiceShutdownException {
		
		ReleaseKeyRequest request = new ReleaseKeyRequest(scanCode, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedToSendReleaseKeyEvent_ExceptionMsg");  //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#typeText(java.lang.String)
	 */
	public void typeText(String typeStr, long timeout) throws ServiceShutdownException,
			HTIException, ConnectionException {
		
		TypeTextRequest request = new TypeTextRequest(typeStr, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedToSendTypeTextEvent_ExceptionMsg");  //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IKeyEventService#holdKey(int)
	 */
	public void holdKey(int scanCode, long timeout) throws ServiceShutdownException,
			HTIException, ConnectionException {
		
		HoldKeyRequest request = new HoldKeyRequest(scanCode, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error and throwing appropriate error message if needed.
		String errorMsg = Messages.getString("KeyEventService.FailedToSendHoldKeyEvent_ExceptionMsg");  //$NON-NLS-1$
		checkResultForErrors(result, errorMsg);
	}
}
