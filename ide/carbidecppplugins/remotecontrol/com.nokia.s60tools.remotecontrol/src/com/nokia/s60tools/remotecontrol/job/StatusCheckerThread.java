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

package com.nokia.s60tools.remotecontrol.job;

import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * Thread that checks if job has been canceled and then cancels it.
 * This is needed in long running file transfer operation where original thread
 * can not check status of the operation.
 */
public class StatusCheckerThread extends Thread {
	
	/**
	 * True while there is need to monitor current status.
	 */
	private boolean monitoring = true;
	/**
	 * Monitor that contains current status.
	 */
	private final IProgressMonitor monitor;
	/**
	 * Job that needs to be canceled upon cancel status.
	 */
	private final IManageableJob manageableJob;
	/**
	 * Constructor.
	 * @param monitor Status of this monitor is checked.
	 * @param manageableJob This job needs to be informed if cancel has been requested.
	 */
	public StatusCheckerThread(IProgressMonitor monitor, IManageableJob manageableJob) {
		this.monitor = monitor;
		this.manageableJob = manageableJob;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		
		while(monitoring){
			try {
				if(monitor.isCanceled()) {
					manageableJob.cancelJob();
					// Stopping this thread as it is necessary to cancel each operation only once.
					monitoring = false;
				}
				
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
				RemoteControlConsole.getInstance().println(Messages.getString("StatusCheckerThread.CancelThreadInterrupted_ConsoleMsg") //$NON-NLS-1$
						, RemoteControlConsole.MSG_ERROR);
			}
		}
	}
	
	/**
	 * Stops this thread from monitoring the job.
	 */
	public void stopThread() {
		monitoring = false;
	}
}