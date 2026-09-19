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
import java.io.FileOutputStream;
import java.io.IOException;

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
import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpUtils;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * Job for downloading files
 */
public class FileDownloadJob extends Job implements IManageableJob, IFTPListener {
	
	/**
	 * Remote file
	 */
	private String remoteFile;
	
	/**
	 * Destinaton directory
	 */
	private String destinationDir;
	
	/**
	 * Timeout for request
	 */
	private static int timeout = 0; // Infinite
	
	/**
	 * Output stream for file
	 */
	private FileOutputStream out;
	
	/**
	 * Progress monitor
	 */
	private IProgressMonitor monitor;
	
	/**
	 * FTP request manager
	 */
	private IFTPRequestManager FTPRequestManager;
	
	/**
	 * Thread for checking status
	 */
	private StatusCheckerThread statusChecker = null;

	/**
	 * Boolean if file should be opened after download is complete.
	 */
	private final boolean openFile;

	/**
	 * Constructor
	 * @param name Name for job
	 * @param remoteFile Remote file path
	 * @param destinationDir Destination dir
	 */
	public FileDownloadJob(String name, String remoteFile, String destinationDir ) {
		super(name);
		this.remoteFile = remoteFile;
		this.destinationDir = destinationDir;
		this.openFile = false;
		setUser(true);
	}
	
	/**
	 * Constructor
	 * @param name Name for job
	 * @param remoteFile Remote file path
	 * @param destinationDir Destination dir
	 * @param openFile Boolean if file should be opened after download is complete.
	 */
	public FileDownloadJob(String name, String remoteFile, String destinationDir, boolean openFile) {
		super(name);
		this.remoteFile = remoteFile;
		this.destinationDir = destinationDir;
		this.openFile = openFile;
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
			monitor.beginTask(Messages.getString("FileDownloadJob.Download_File_TaskName"), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
			monitor.subTask(Messages.getString("FileDownloadJob.Download_File_SubTaskName")); //$NON-NLS-1$
			
			// Starting thread that checks status of the monitor regularly.
			statusChecker = new StatusCheckerThread(monitor, this);
			statusChecker.start();

			// Starting long running download file operation.
			byte[] bytes = service.downloadFile(remoteFile, this, timeout);
			
			statusChecker.stopThread();
			
			// Check is canceled
			if (monitor.isCanceled()) {
				return Status.CANCEL_STATUS;
			}

			if (bytes.length > 0) {
				monitor.subTask(Messages.getString("FileDownloadJob.Write_File_To_Disk_SubTaskName")); //$NON-NLS-1$
				// Write file to disk
				File file = new File(destinationDir);
				out = new FileOutputStream(file);
				out.write(bytes);
				out.close();
			}
			
			if(openFile) {
				FtpUtils.openFile(destinationDir);
			}
		} catch (HTIException e) {
			if(e.isHtiRequestCanceled()) {
				// Uploading was canceled. No need to inform about it.
				return Status.CANCEL_STATUS;
			}
			RemoteControlConsole.getInstance().println(
						Messages.getString("FileDownloadJob.Download_Failed_ConsoleErrorMsg"), RemoteControlConsole.MSG_ERROR); //$NON-NLS-1$
				e.printStackTrace();
				return new Status(
						Status.ERROR,
						RemoteControlActivator.getPluginID(),
						Messages.getString("FileDownloadJob.Download_Failed_ConsoleErrorMsg"), e); //$NON-NLS-1$
		} catch (Exception e) {
			RemoteControlConsole.getInstance().println(
					Messages.getString("FileDownloadJob.Download_Failed_ConsoleErrorMsg"), RemoteControlConsole.MSG_ERROR); //$NON-NLS-1$
			e.printStackTrace();
			return new Status(Status.ERROR, RemoteControlActivator
					.getPluginID(), Messages.getString("FileDownloadJob.Download_Failed_ConsoleErrorMsg"), e); //$NON-NLS-1$
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
		try {
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		cancel();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestEnded(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestEnded(IFTPRequestManager manager) {
		monitor.subTask(Messages.getString("FileDownloadJob.DownloadEnded_SubTaskName")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestInQueue(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestInQueue(IFTPRequestManager manager) {
		FTPRequestManager = manager;
		monitor.subTask(Messages.getString("FileDownloadJob.DownloadInQueue_SubTaskName")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestStarted(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestStarted(IFTPRequestManager manager) {
		monitor.subTask(Messages.getString("FileDownloadJob.DownloadStarted_SubTaskName")); //$NON-NLS-1$
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.remotecontrol.job.IManageableJob#cancelJob()
	 */
	public void cancelJob() {
		if(FTPRequestManager != null) {
			// Sending cancel request.
			FTPRequestManager.cancel();
			monitor.subTask(Messages.getString("FileDownloadJob.CancelingDownload_SubTaskName")); //$NON-NLS-1$
		}
	}
}
