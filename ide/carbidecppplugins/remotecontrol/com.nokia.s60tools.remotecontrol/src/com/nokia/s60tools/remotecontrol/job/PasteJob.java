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
import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpUtils;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.ViewContentProvider.OPERATION;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;

/**
 * Job for pasting files
 */
public class PasteJob extends Job implements IManageableJob {
	
	/**
	 * Path where selected files are located.
	 */
	private final String selectedPath;
	/**
	 * Path where files should be pasted.
	 */
	private final String targetPath;
	/**
	 * File that was selected.
	 */
	private final String selectedFile;
	/**
	 * File/directory name where paste will occur.
	 */
	private final String targetFile;
	
	/**
	 * Operation to be done for selected files.
	 */
	private final OPERATION fileOperation;
	
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
	 * @param jobName Name for job.
	 * @param selectedFile Files that were selected.
	 * @param selectedPath Path where selected files are located.
	 * @param targetPath Path where files are to be pasted.
	 * @param fileOperation Operation to be done for selected files.
	 */
	public PasteJob(String jobName, String selectedFile,
			String selectedPath, String targetFile, String targetPath, OPERATION fileOperation) {
		super(jobName);
		this.selectedPath = selectedPath;
		this.selectedFile = selectedFile;
		this.targetFile = targetFile;
		this.targetPath = targetPath;
		this.fileOperation = fileOperation;
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
			monitor.beginTask(Messages.getString("PasteJob.Task_Name") + selectedFile, //$NON-NLS-1$
					IProgressMonitor.UNKNOWN);
			
			String sourceFile = FtpUtils.addFileSepatorToEnd(selectedPath) + selectedFile;
			String destFile = FtpUtils.addFileSepatorToEnd(targetPath) + targetFile;
			
			// Running correct HTI operation.
			switch(fileOperation) {
			case COPY:
				service.copyFileDir(sourceFile, destFile, timeout);
				break;
			case CUT:
				if(selectedFile.equals(targetFile)) {
					// File take directory as parameter when it is moved.
					service.moveFileDir(sourceFile, targetPath, timeout);	
				} else if(selectedPath.equals(targetPath)){
					// File name changed, but folder is same. Just renaming it, as
					// file can not be moved to be different name.
					service.renameFileDir(sourceFile, destFile, timeout);
				} else {				
					// File name changed. Has to copy file and then delete old file, as
					// file can not be moved to be different name.
										
					// Check if source file is a file or a directory.
					String[] sourcePathDirs = service.listDirs(selectedPath, timeout);
					
					boolean sourceIsDir = false;
					for (String directory : sourcePathDirs) {
						if (directory.equals(selectedFile)) sourceIsDir = true;
					}
						
					// Do we need to copy anything.
					boolean doCopy = true;
						
					// If source is directory...
					if (sourceIsDir) {
						// Are there any files in source directory.
						String[] sourceDirs = service.listDirs(sourceFile, timeout);
						String[] sourceFiles = service.listFiles(sourceFile, timeout);
						if ((sourceDirs.length == 0) && (sourceFiles.length == 0)) {
							doCopy = false;
						}
					}
					
					// Copy source file or source directory if needed.
					if (doCopy) {
						// If there are files or folders inside source folder.
						service.copyFileDir(sourceFile, destFile, timeout);
					} else {
						// If pasting an empty folder.
						service.makeDir(destFile, timeout);
					}

					// If source is directory...
					if (sourceIsDir) {
						// ...delete it.
						service.deleteDir(sourceFile, timeout);
					} else { // If source is file...
						// ...delete it.
						service.deleteFile(sourceFile, timeout);
					}
				}
				
				break;
			case NONE:
				// Nothing to do.
				break;
			default:
				// Nothing to do.
				break;
			}
			
		} catch (Exception e) {
			String errMsg = Messages.getString("PasteJob.FailedPaste_ErrMsg") //$NON-NLS-1$
					+ FtpUtils.addFileSepatorToEnd(selectedPath) + selectedFile + Messages.getString("PasteJob.FailedPaste_ErrMsg2") + //$NON-NLS-1$
					FtpUtils.addFileSepatorToEnd(targetPath) + targetFile;
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