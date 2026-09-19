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

package com.nokia.s60tools.hticonnection.services.ftpservice;

import com.nokia.HTI.HTIMessage;
import com.nokia.s60tools.hticonnection.core.RequestQueueManager;
import com.nokia.s60tools.hticonnection.core.RequestResult;
import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIErrorDetails;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.services.DriveInfo;
import com.nokia.s60tools.hticonnection.services.FileInfo;
import com.nokia.s60tools.hticonnection.services.IFTPListener;
import com.nokia.s60tools.hticonnection.services.IFTPService;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Service that contains interface for performing file operations on device.
 */
public class FTPService implements IFTPService{
	
	/**
	 * Print utility used to report errors, warnings, and info messages.
	 */
	private final IConsolePrintUtility printUtility;

	/**
	 * Constructor.
	 * @param printUtility Used for printing messages.
	 */
	public FTPService(IConsolePrintUtility printUtility){
		this.printUtility = printUtility;
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#listDirs(java.lang.String, long)
	 */
	public String[] listDirs(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		ListDirsRequest request = new ListDirsRequest(remoteDir, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getStringArrayData();	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#listFiles(java.lang.String, long)
	 */
	public String[] listFiles(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		ListFilesRequest request = new ListFilesRequest(remoteDir, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getStringArrayData();	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#listFilesAndDetails(java.lang.String, long)
	 */
	public FileInfo[] listFilesAndDetails(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		ListFilesAndDetailsRequest request = new ListFilesAndDetailsRequest(remoteDir, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getFileInfoArrayData();	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#listDirs(long)
	 */
	public DriveInfo[] listDrives(long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		ListDrivesRequest request = new ListDrivesRequest(timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getDriveInfoArrayData();
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#uploadFile(byte[], java.lang.String, com.nokia.s60tools.hticonnection.services.ftpservice.IFTPListener, long)
	 */
	public void uploadFile(byte[] fileData, String remoteFile, IFTPListener listener, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		UploadFileRequest request = new UploadFileRequest(fileData, remoteFile, listener, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("FTPService.FailedToUploadFile_ExceptionMsg") + remoteFile; //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#downloadFile(java.lang.String, com.nokia.s60tools.hticonnection.services.ftpservice.IFTPListener, long)
	 */
	public byte[] downloadFile(String remoteDir, IFTPListener listener, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		DownloadFileRequest request = new DownloadFileRequest(remoteDir, listener, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		return result.getByteData();	
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#deleteFile(java.lang.String, long)
	 */
	public void deleteFile(String remoteFile, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		DeleteFileRequest request = new DeleteFileRequest(remoteFile, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = (Messages.getString("FTPService.FailedToDeleteFile_ExceptionMsg") + remoteFile); //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}
		
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#makeDir(java.lang.String, long)
	 */
	public void makeDir(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		MakeDirRequest request = new MakeDirRequest(remoteDir, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = (Messages.getString("FTPService.FailedToMakeDir_ExceptionMsg") + remoteDir); //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#deleteDir(java.lang.String, long)
	 */
	public void deleteDir(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException {
		DeleteDirRequest request = new DeleteDirRequest(remoteDir, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = (Messages.getString("FTPService.FailedToDeleteDir_ExceptionMsg") + remoteDir); //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#copyFileDir(java.lang.String, java.lang.String, long)
	 */
	public void copyFileDir(String originalPath, String copyPath, long timeout)
			throws ServiceShutdownException, HTIException, ConnectionException {
		CopyFileDirRequest request = new CopyFileDirRequest(originalPath, copyPath, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("FTPService.FailedToCopyFile_ExceptionMsg1")  //$NON-NLS-1$
					+ originalPath + Messages.getString("FTPService.FailedToCopyFile_ExceptionMsg2") + copyPath; //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#moveFileDir(java.lang.String, java.lang.String, long)
	 */
	public void moveFileDir(String oldPath, String newLocation, long timeout)
			throws ServiceShutdownException, HTIException, ConnectionException {
		MoveFileDirRequest request = new MoveFileDirRequest(oldPath, newLocation, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("FTPService.FailedToMoveFile_ExceptionMsg1")  //$NON-NLS-1$
					+ oldPath + Messages.getString("FTPService.FailedToMoveFile_ExceptionMsg2") + newLocation; //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.hticonnection.services.IFTPService#renameFileDir(java.lang.String, java.lang.String, long)
	 */
	public void renameFileDir(String oldName, String newName, long timeout)
			throws ServiceShutdownException, HTIException, ConnectionException {
		RenameFileDirRequest request = new RenameFileDirRequest(oldName, newName, timeout);
		RequestResult result = RequestQueueManager.getInstance().submit(request, printUtility);
		
		// Checking if there has been error.
		HTIMessage msg = result.getHTIMessage();
		if(msg.isErrorResponse()){
			// Reporting error and throwing appropriate error message.
			String errorMsg = Messages.getString("FTPService.FailedToRename_ExceptionMsg1")  //$NON-NLS-1$
					+ oldName + Messages.getString("FTPService.FailedToRename_ExceptionMsg2") + newName; //$NON-NLS-1$
			printUtility.println(errorMsg, IConsolePrintUtility.MSG_ERROR);
			HTIErrorDetails details = new HTIErrorDetails(msg.getHTIErrorCode(),
					msg.getServiceErrorCode(), msg.getErrorDescription());
			throw new HTIException(errorMsg, details);
		}
	}
}