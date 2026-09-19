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
 * Job for renaming file
 */
public class RenameJob extends Job implements IManageableJob {
	
	/**
	 * Remote file
	 */
	private final String remoteFile;
	/**
	 * New file name.
	 */
	private final String targetFile;
	
	/**
	 * Timeout for request
	 */
	private static int timeout = 0; //Infinite
	
	/**
	 * Progress monitor
	 */
	private IProgressMonitor monitor;
	
	/**
	 * Constructor
	 * @param name Name for job
	 * @param remoteFile File to be renamed.
	 * @param targetFile New file name.
	 */
	public RenameJob(String name, String remoteFile, String targetFile) {
		super(name);
		this.remoteFile = remoteFile;
		this.targetFile = targetFile;
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
			monitor.beginTask(Messages.getString("RenameJob.Task_Name"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
			service.renameFileDir(remoteFile, targetFile, timeout);
		} catch (Exception e) {
			String errMsg = Messages.getString("RenameJob.FailedToRename_ErrMsg") + remoteFile + //$NON-NLS-1$
					Messages.getString("RenameJob.FailedToRename_ErrMsg2") + targetFile;  //$NON-NLS-1$
			RemoteControlConsole.getInstance().println(
					errMsg,
					RemoteControlConsole.MSG_ERROR); 
			e.printStackTrace();
			return new Status(Status.ERROR,
					RemoteControlActivator.getPluginID(),
					errMsg,
					e);
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