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

package com.nokia.s60tools.creator.job;

import java.io.File;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.creator.CreatorActivator;
import com.nokia.s60tools.creator.common.ProductInfoRegistry;
import com.nokia.s60tools.creator.preferences.CreatorPreferences;
import com.nokia.s60tools.creator.resources.Messages;
import com.nokia.s60tools.creator.util.CreatorEditorConsole;
import com.nokia.s60tools.hticonnection.services.AppStatus;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.IApplicationControlService;
import com.nokia.s60tools.hticonnection.services.IConnectionTestService;
import com.nokia.s60tools.hticonnection.services.IFTPListener;
import com.nokia.s60tools.hticonnection.services.IFTPRequestManager;
import com.nokia.s60tools.hticonnection.services.IFTPService;
import com.nokia.s60tools.util.exceptions.JobCancelledByUserException;
import com.nokia.s60tools.util.resource.FileUtils;

/**
 * Job for uploading files
 */
public class RunInDeviceJob extends Job implements IManageableJob, IFTPListener {
	
	private static final String CREATOR_EXE_NAME = ProductInfoRegistry.getCreatorSymbianExcecutableName();
	private String srcFilePath;
	private String destFilePath;
	private static int timeout = 30*1000; // Default timeout 30 s.
	private IProgressMonitor monitor;
	private String destFileName;
	private int confirmDialogSelection = -1;
	private IFTPRequestManager FTPReguestManager;
	

	/**
	 * Constructor
	 * @param name Name for job
	 * @param srcFilePath Source file path
	 * @param destFilePath Destination file path
	 */
	public RunInDeviceJob(String name, String srcFilePath, String destFilePath, String destFileName ) {
		super(name);
		this.srcFilePath = srcFilePath;
		this.destFilePath = destFilePath;
		this.destFileName = destFileName;
		setUser(true);
		setTimeOut();
	}
	
	/**
	 * Setting the timeout for operation.
	 */
	private void setTimeOut() {
		File srcFile = new File(srcFilePath);
		Long bytes = new Long( srcFile.length());
		//Setting time out to be 10 ms for byte, e.g script with few element is 300 bytes long, then timeout will be 30s
		int countTimeOut = bytes.intValue() * 10;
		//
		int maxTimeOut = 2*60*1000;
		//If we count longer than default time out, and it does not reach maximum time out, we set new timeout
		if(countTimeOut > timeout && countTimeOut < maxTimeOut ){
			timeout = countTimeOut;
		}
		//if we count longer than max timeout, using max timeout
		else if(countTimeOut > maxTimeOut){
			timeout = maxTimeOut;
		}
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.jobs.Job#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	protected IStatus run(IProgressMonitor monitor) {
		
		this.monitor = monitor;
		try{
		
			CreatorJobManager.getInstance().registerJob(this);			
			
			//First check if HTI is running
			IFTPService service = HTIServiceFactory
					.createFTPService(CreatorEditorConsole.getInstance());			
			IConnectionTestService connService = HTIServiceFactory
				.createConnectionTestService(CreatorEditorConsole.getInstance());
			checkCancel();			
			//If HTI is not running, returning error
			if(!connService.isReady()){
				String msg = Messages.getString("RunInDeviceJob.HTINotRunning_ErrMsg");
				CreatorEditorConsole.getInstance().println(
						msg, CreatorEditorConsole.MSG_ERROR); //$NON-NLS-1$
				
				return new Status(
						Status.ERROR,
						CreatorActivator.PLUGIN_ID,
						msg); //$NON-NLS-1$				
			}
			
			checkCancel();
			
			//Then upload file
			try {
				monitor.beginTask(getName(), IProgressMonitor.UNKNOWN); //$NON-NLS-1$
				
				boolean replace = CreatorPreferences.getDontAskFileReplaceInDevice();
				if(!replace){
					monitor.subTask(Messages.getString("RunInDeviceJob.CheckFileExistence_TaskName")); //$NON-NLS-1$
					replace = true;
					//Before load, check if there is file allready
					checkCancel();					
					String deviceSaveFolded = CreatorPreferences.getDeviceSaveFolded();
					String[] files = service.listFiles(deviceSaveFolded, 0);
					if(files != null && files.length > 0){
						for (int i = 0; i < files.length; i++) {
							if(destFileName.equalsIgnoreCase(files[i])){
								//File allready exist
								replace = showReplaceFileDialog();
								break;
							}
						}
					}
				}
				
				
				//If replacing (or file does not exist) uploading file to device, 
				//if user does not upload file, existing file will be executed in device.
				if(replace){
										
					checkCancel();
					monitor.subTask(Messages.getString("RunInDeviceJob.Upload_File_SubTaskName")); //$NON-NLS-1$
					
					StringBuffer buf = FileUtils.loadDataFromFile(srcFilePath);
					if (buf != null) {
						String fileString = buf.toString();
		
						checkCancel();	
						service.uploadFile(fileString.getBytes(), destFilePath, this, timeout);
						
		
					}					
				}
				
			}
			catch(JobCancelledByUserException e){
				return Status.CANCEL_STATUS;
			}			
			catch (Exception e) {			
				String msg = Messages.getString("RunInDeviceJob.Upload_Failed_ConsoleErrorMsg");
				return returnErrorStatus(e, msg);
			} 
			
			checkCancel();
			
			try {
				
				IApplicationControlService appService = HTIServiceFactory.createApplicationControlService(CreatorEditorConsole.getInstance());
				String programName = CREATOR_EXE_NAME;
				String parameters = destFilePath;							
				
				//Checking Creator application status in device
				checkCancel();
				monitor.subTask(Messages.getString("RunInDeviceJob.CheckIfRunning_SubTaskName")); //$NON-NLS-1$							
				AppStatus status = appService.getApplicationStatusByName(programName, timeout);
				
				//If creator is running, stopping it
				checkCancel();				
				if(status.getStatus() == AppStatus.RUNNING){
					
					//Showing dialog to as if user wants to shutdown creator
					//If user selects Cancel, JobCancelledByUserException is thrown
					showShutdownDialog();
					monitor.subTask(Messages.getString("RunInDeviceJob.StopCreator_SubTaskName")); //$NON-NLS-1$					
					appService.stopApplicationByName(programName, timeout);
				}
				
				//Showing user that he/she should follow progress in device/emulator
				showInformation();																													
				
				//Start to run script
				checkCancel();				
				monitor.subTask(Messages.getString("RunInDeviceJob.Run_File_SubTaskName")); //$NON-NLS-1$							
				appService.startProcess(programName, parameters, timeout);
			}
			catch(JobCancelledByUserException e){
				return Status.CANCEL_STATUS;
			}
			catch (Exception e){
				String msg = Messages.getString("RunInDeviceJob.Run_Failed_ConsoleErrorMsg");
				return returnErrorStatus(e, msg);
				
			}		
		
		}
		catch(JobCancelledByUserException e){
			return Status.CANCEL_STATUS;
		}
		finally {
			finished();
		}
		
		return Status.OK_STATUS;
	}

	/**
	 * Showing user a dialog to shutdown Creator in device or not,
	 * @throws JobCancelledByUserException if user selects Cancel
	 */
	private void showShutdownDialog() throws JobCancelledByUserException {		
		
		if(!CreatorPreferences.getDontAskShutdownCreator()){
		
			Runnable runDlg = new Runnable(){
	
				public void run() {
					//
					ShutdownCreatorInDeviceDialog dlg = new ShutdownCreatorInDeviceDialog(CreatorActivator.getCurrentlyActiveWbWindowShell());
		
					dlg.open();
					confirmDialogSelection = dlg.getSelection();
				}
			};
			
			Display.getDefault().syncExec(runDlg);
			
			if(confirmDialogSelection == IDialogConstants.CANCEL_ID){
				throwJobCancelledException();
			}
		}
	}

	/**
	 * Shows user an information dialog to follow execution in device.
	 */
	private void showInformation() {
		
		if(!CreatorPreferences.getDontAskShowInformation()){
		Runnable showInfo = new Runnable(){
					
			public void run() {
				WatchDeviceInformationDialog dlg = new WatchDeviceInformationDialog(
						CreatorActivator.getCurrentlyActiveWbWindowShell(),
						destFileName);
				dlg.open();
				
			}
		};
		
		Display.getDefault().asyncExec(showInfo);
		}
	}

	/**
	 * Check if job is canceled, and throws an exception if it's.
	 * @throws JobCancelledByUserException
	 */
	private void checkCancel() throws JobCancelledByUserException {

		if(monitor.isCanceled()){
			if(FTPReguestManager != null){
				FTPReguestManager.cancel();
			}
			throwJobCancelledException();
		}
		
	}

	/**
	 * Throw cancel exception
	 * @throws JobCancelledByUserException
	 */
	private void throwJobCancelledException() throws JobCancelledByUserException {
		throw new JobCancelledByUserException(Messages.getString("RunInDeviceJob.JobCanceledByUser_Msg"));
	}

	/**
	 * Check if user wants to replace existing file
	 * @return <code>true</code> if replace, <code>false</code> otherwise.
	 * @throws JobCancelledByUserException 
	 */
	private boolean showReplaceFileDialog() throws JobCancelledByUserException {

		Runnable runDlg = new Runnable(){

			public void run() {
				//
				Shell shell = CreatorActivator.getCurrentlyActiveWbWindowShell();
				ConfirmFileReplaceDialog dlg = new ConfirmFileReplaceDialog(
						shell, destFilePath);
	
				dlg.open();
				confirmDialogSelection = dlg.getSelection();
			}
		};
		
		Display.getDefault().syncExec(runDlg);
		
		if(confirmDialogSelection == IDialogConstants.CANCEL_ID){
			throwJobCancelledException();
		}
		boolean replace = confirmDialogSelection == IDialogConstants.YES_ID ? true : false;		
		
		return replace;
	}

	/**
	 * Returns Error status
	 * @param e
	 * @param msg
	 * @return {@link Status} with {@link Status#ERROR}
	 */
	private IStatus returnErrorStatus(Exception e, String msg) {
		CreatorEditorConsole.getInstance().println(
				msg, CreatorEditorConsole.MSG_ERROR); //$NON-NLS-1$
		e.printStackTrace();			
		return new Status(
				Status.ERROR,
				CreatorActivator.PLUGIN_ID,
				msg, e); //$NON-NLS-1$
	}

	/**
	 * Done and unregister job
	 */
	private void finished() {
		monitor.done();
		CreatorJobManager.getInstance().unregisterJob(this);
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
		// not needed		
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestInQueue(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestInQueue(IFTPRequestManager manager) {
		// not needed
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPListener#requestStarted(com.nokia.s60tools.hticonnection.services.IFTPRequestManager)
	 */
	public void requestStarted(IFTPRequestManager manager) {
		FTPReguestManager = manager;
	}

}
