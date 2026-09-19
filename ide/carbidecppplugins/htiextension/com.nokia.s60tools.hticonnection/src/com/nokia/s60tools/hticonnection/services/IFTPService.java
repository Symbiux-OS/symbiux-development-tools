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

package com.nokia.s60tools.hticonnection.services;

import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;

/**
 * Service that contains interface for performing file operations on device.
 */
public interface IFTPService {

	/**
	 * Returns the list of directories located inside a specified directory.
     * @param remoteDir Directory
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return List of directories in given directory
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public String[] listDirs(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Returns the list of files located inside a specified directory.
     * @param remoteDir Directory
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return List of files in given directory
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public String[] listFiles(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;

	/**
	 * Returns the list of files and details located inside a specified directory.
     * @param remoteDir Directory
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return List of file information in given directory
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public FileInfo[] listFilesAndDetails(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Returns the list of drives on device.
	 * Supported in HTI 1.88.0 -> and 2.1.0 ->
     * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @return DriveInfo array containing information of drives on device
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public DriveInfo[] listDrives(long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Uploads given file to a specified directory.
     * @param fileData File data in byte array
	 * @param remoteFile File in target where to write data
	 * @param listener Listener for upload status information. Or null if information is not needed.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void uploadFile(byte[] fileData, String remoteFile, IFTPListener listener, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Downloads given file.
     * @param remoteDir File in target to download
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @param listener Listener for download status information. Or null if information is not needed.
	 * @return File data in byte array
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public byte[] downloadFile(String remoteDir, IFTPListener listener, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Deletes given file.
	 * @param remoteFile File in target where to write data
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void deleteFile(String remoteFile, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Creates specified directory.
	 * @param remoteDir Directory to be created to target
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void makeDir(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Deletes specified directory.
	 * @param remoteDir Directory to be deleted from target
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void deleteDir(String remoteDir, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Renames the specified file or folder. The command operates recursively.
	 * Note that the names must be absolute paths and if the new path is different
	 * than the old, this command can also move the file to that new path.
	 * The old and new path must be on the same drive.
	 * Supported in HTI 1.91.0 -> and 2.5.0 ->
	 * @param oldName Full path to the file or folder to be renamed.
	 * @param newName Full path indicating the new name (and possibly new location) of the file or folder. 
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void renameFileDir(String oldName, String newName, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Makes a copy of the specified file or folder to selected location.
	 * The command operates recursively. If the OriginalPath is a folder the contents
	 * of that folder will be copied to CopyPath. For example if OriginalPath is
	 * c:\temp and CopyPath is e:\copy_of_temp then the files and folders under
	 * c:\temp will be copied under e:\copy_of_temp 
	 * Supported in HTI 1.91.0 -> and 2.5.0 ->
	 * @param originalPath Full path to file or folder.
	 * @param copyPath Full path for copy file or folder.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void copyFileDir(String originalPath, String copyPath, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Moves the specified file or folder to selected location. The command operates recursively.
	 * Supported in HTI 1.91.0 -> and 2.5.0 ->
	 * @param oldPath Full path to the file or folder to be moved. 
	 * @param newLocation Target folder where file or folder is to be moved.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void moveFileDir(String oldPath, String newLocation, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
}

