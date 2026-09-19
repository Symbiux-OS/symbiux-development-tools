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

package com.nokia.s60tools.remotecontrol.screen.ui.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

import com.nokia.s60tools.hticonnection.services.IKeyEventService;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * Command handler for App key
 */
public class AppKeyCommandHandler extends BaseCommandHandler {

	/**
	 * Timestamp when first key press has occured.
	 * Adjusted to zero after key press event is sent to keyboard mediator.
	 */
	private long firstPressTime = 0;
	
	/**
	 * Timestamp when latest key press has occured
	 * Adjusted to zero after key press event is sent to keyboard mediator.
	 */
	private long latestPressTime = 0;
	
	/**
	 * Is handler capable to receive events.
	 */
	private boolean isEnabled = true;
	
	/**
	 * Keeping home button down more than 700 ms is handled as long press
	 */
	private static final int HOME_LONG_PRESS_TIME = 700;
	
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.screen.ui.commands.BaseCommandHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Take timestamp
		long time = System.currentTimeMillis();
		
		if (firstPressTime == 0) {
			// Save timestamp when key is first time pressed down. We need this
			// value for solving is this long key press or normal key press. 
			firstPressTime = time;
			latestPressTime = time;
			
			// Start thread for waiting more events
			Thread thread = new LongPressThread();
			thread.start();
			
		} else {
			// Save timestamp of latest event
			latestPressTime = time;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isEnabled()
	 */
	public boolean isEnabled() {
		return isEnabled;
	}

	/**
	 * Thread for sending key events. Solves is user is kept key down
	 * or just pressed it.
	 */
	private class LongPressThread extends Thread {
		
		/* (non-Javadoc)
		 * @see java.lang.Thread#run()
		 */
		public void run(){
			try {
				// Sleep time specified for long press. 
				sleep(HOME_LONG_PRESS_TIME);
				
				if (firstPressTime == latestPressTime ) {
					// There has not came second press event so button was not kept down
					// Send key pressed event
					scanCodeKeyPressed(IKeyEventService.SCANCODE_APP);
				}
				else {
					// Long press
					// Disable receiveing events while sending event
					isEnabled = false;
					// Send key pressed and hold event
					scanCodeKeyPressedAndHold(IKeyEventService.SCANCODE_APP, HOME_LONG_PRESS_TIME);
					// Wait a moment before registering new events. This is because user may keep
					// key down longer that 600ms and without this sleep we will be received some
					// extra key events.
					sleep(1000);
					isEnabled = true;
				}
				
				// Reset timestamps
				firstPressTime = 0;
				latestPressTime = 0;
				
			} catch (InterruptedException e) {
				RemoteControlConsole.getInstance().println(Messages.getString("KeyCommandHandler.FailedToSendEvent_ErrorMsg") //$NON-NLS-1$
						+ e.getMessage(), RemoteControlConsole.MSG_ERROR);
				e.printStackTrace();
			}
		}
	}
}
