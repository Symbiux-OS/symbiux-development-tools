/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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

import java.io.File;
import java.io.FileInputStream;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IFTPListener;
import com.nokia.s60tools.hticonnection.services.IFTPRequestManager;
import com.nokia.s60tools.hticonnection.services.IFTPService;
import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * Job for uploading files
 */
public class FileUploadJob extends Job implements IManageableJob, IFTPListener {
	
	/**
	 * Source file
	 */
	private String srcFile;
	
	/**
	 * Destination file
	 */
	private String destFile;
	
	/**
	 * Timeout for request
	 */
	private static int timeout = 0; // Infinite
	
	/**
	 * Progress monitor
	 */
	private IProgressMonitor monitor;
	
	/**
	 * FTP request manager
	 */
	private IFTPRequestManager FTPRequestManager = null;
	
	/**
	 * Thread for checking status
	 */
	private StatusCheckerThread statusChecker = null;

	/**
	 * Constructor
	 * @param name Name for job
	 * @param srcFile Source file path
	 * @param destFile Destination file path
	 */
	public FileUploadJob(String name, String srcFile, String destFile ) {
		super(name);
		this.srcFile = srcFile;
		this.destFile = destFile;
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
			monitor.beginTask(Messages.getString("FileUploadJob.Upload_File_TaskName"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
			monitor.subTask(Messages.getString("FileUploadJob.Read_File_From_Disk_SubTaskName")); //$NON-NLS-1$

			// Loading data from file.
			File file = new File(srcFile);
			FileInputStream inputStream = new FileInputStream(file);
			byte[] contents = new byte[(int)file.length()];
			inputStream.read(contents);
			inputStream.close();

			// Check is canceled
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			// Starting thread that checks status of the monitor regularly.
			statusChecker = new StatusCheckerThread(monitor, this);
			statusChecker.start();
			
			// Sending long running upload request.
			monitor.subTask(Messages.getString("FileUploadJob.Upload_File_SubTaskName")); //$NON-NLS-1$
			service.uploadFile(contents, destFile, this, timeout);
		} catch (HTIException e) {
			if(e.isHtiRequestCanceled()) {
				// Uploading was canceled. No need to inform about it.
				return Status.CANCEL_STATUS;
			}
			
			RemoteControlConsole.getInstance().println(
						Messages.getString("FileUploadJob.Upload_Failed_ConsoleErrorMsg"), RemoteControlConsole.MSG_ERROR); //$NON-NLS-1$
				e.printStackTrace();
				return new Status(
						Status.ERROR,
						RemoteControlActivator.getPluginID(),
						Messages.getString("FileUploadJob.Upload_Failed_ConsoleErrorMsg"), e); //$NON-NLS-1$
		} catch (Exception e) {
			RemoteControlConsole.getInstance().println(
					Messages.getString("FileUploadJob.Upload_Failed_ConsoleErrorMsg"), RemoteControlConsole.MSG_ERROR); //$NON-NLS-1$
			e.printStackTrace();
			return new Status(
					Status.ERROR,
					RemoteControlActivator.getPluginID(),
					Messages.getString("FileUploadJob.Upload_Failed_ConsoleErrorMsg"), e); //$NON-NLS-1$
		} finally {
			if(statusChecker != null) {
				statusChecker.stopThread();
			}
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
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestEnded(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestEnded(IFTPRequestManager manager) {
		monitor.subTask(Messages.getString("FileUploadJob.UploadEnded_SubTaskName")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestInQueue(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestInQueue(IFTPRequestManager manager) {
		FTPRequestManager = manager;
		monitor.subTask(Messages.getString("FileUploadJob.UploadInQueue_SubTaskName")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestStarted(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestStarted(IFTPRequestManager manager) {
		monitor.subTask(Messages.getString("FileUploadJob.UploadStarted_SubTaskName")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.job.IManageableJob#cancelJob()
	 */
	public void cancelJob() {
		if(FTPRequestManager != null) {
			// Sending cancel request.
			FTPRequestManager.cancel();
			monitor.subTask(Messages.getString("FileUploadJob.CancelingUpload_SubTaskName")); //$NON-NLS-1$
		}
	}
}
