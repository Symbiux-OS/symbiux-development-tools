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
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IFTPService;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * Job for making directory
 */
public class MakeDirJob extends Job implements IManageableJob {
	
	/**
	 * Remote directory
	 */
	private String remoteDir;
	
	/**
	 * Timeout for request
	 */
	private static int timeout = 10000;
	
	/**
	 * Progress monitor
	 */
	private IProgressMonitor monitor;

	/**
	 * Constructor
	 * @param name Name for job
	 * @param remoteDir Directory to be created
	 */
	public MakeDirJob(String name, String remoteDir) {
		super(name);
		this.remoteDir = remoteDir;
		setUser(true);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor arg0) {
		this.monitor = arg0;

		IFTPService service = HTIServiceFactory
				.createFTPService(RemoteControlConsole.getInstance());

		try {
			RemoteControlJobManager.getInstance().registerJob(this);
			monitor.beginTask(Messages.getString("MakeDirJob.Make_Dir_Task_Name"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
			service.makeDir(remoteDir, timeout);
		} catch (Exception e) {
			RemoteControlConsole.getInstance().println(
					Messages.getString("MakeDirJob.MakeDir_Failed_ConsoleErrorMsg"), //$NON-NLS-1$
					RemoteControlConsole.MSG_ERROR); 
			e.printStackTrace();
			return new Status(Status.ERROR, RemoteControlActivator
					.getPluginID(), Messages
					.getString("MakeDirJob.MakeDir_Failed_ConsoleErrorMsg"), e); //$NON-NLS-1$
		} finally {
			monitor.done();
			RemoteControlJobManager.getInstance().unregisterJob(this);
		}
		return Status.OK_STATUS;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.job.IManageableJob#forcedShutdown()
	 */
	public void forcedShutdown() {
		cancel();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.job.IManageableJob#cancelJob()
	 */
	public void cancelJob() {
		// Short operation. No need to cancel specifically.
	}
}