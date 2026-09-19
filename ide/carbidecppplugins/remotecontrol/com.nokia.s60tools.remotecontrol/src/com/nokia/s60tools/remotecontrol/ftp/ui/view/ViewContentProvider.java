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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;
import com.nokia.s60tools.hticonnection.services.DriveInfo;
import com.nokia.s60tools.hticonnection.services.FileInfo;
import com.nokia.s60tools.hticonnection.services.HTIServiceFactory;
import com.nokia.s60tools.hticonnection.services.HTIVersion;
import com.nokia.s60tools.hticonnection.services.IFTPService;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RemoteControlMessageBox;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.Messages;

/**
 * Handles elements elements in tableviewer.
 */
public class ViewContentProvider implements IStructuredContentProvider {
	
	/**
	 * Stores currently shown folder. Null if showing root.
	 */
	private String directoryPath = null;
	
	/**
	 * File separator which is used in Symbian.
	 */
	private String symbianFileSeparator = "\\"; //$NON-NLS-1$
	
	/**
	 * String that is used at the beginning of path to mean upper level.
	 */
	private final String UPLEVEL = "..\\"; //$NON-NLS-1$
	
	/**
	 * Stores list of directories and files in current directory.
	 */
	private Object[] currentDirListing;
	
	/**
	 * Timeout for FTP service requests
	 */
	private static int timeoutTime = 2000;
	
	/**
	 * Connection status provider.
	 */
	private final IConnectionStatusProvider connectionStatusProvider;

	/**
	 * Contains files that are copied or cutted and are waiting for paste operation.
	 */
	private List<IFtpObject> selectedFiles;
	/**
	 * Path where selected files are located.
	 */
	private String selectedPath;
	
	/**
	 * Possible operations that can be done for selected files.
	 */
	public enum OPERATION {
		NONE,
		COPY,
		CUT
	};

	/**
	 * Operation which is going to be done for selected files.
	 */
	private OPERATION fileOperation;
	
	/**
	 * Constructor.
	 * @param connectionStatusProvider Connection status provider.
	 */
	public ViewContentProvider(IConnectionStatusProvider connectionStatusProvider) {
		this.connectionStatusProvider = connectionStatusProvider;
		currentDirListing = getDefaultRoot();
		fileOperation = OPERATION.NONE;
	}
	
	/**
	 * Gets current path.
	 * @return the directoryPath
	 */
	public synchronized String getDirectoryPath() {
		return directoryPath;
	}

	/**
	 * Sets current path.
	 * @param directoryPath the directoryPath to set
	 */
	public synchronized void setDirectoryPath(String directoryPath) {
		this.directoryPath = directoryPath;
	}

	/**
	 * Updates directory list with given directoryPath.
	 * @param newPath Path of directory which contents are updated.
	 * @return Path that is set after update.
	 */
	public String updatePath(String newPath) {
		// Getting directories and files.
		
		if(newPath == null || newPath.trim().equals("")){ //$NON-NLS-1$
			// Using default root only if directory path is null or empty string.
			currentDirListing = getRoot();
			setDirectoryPath(null);
			return null;
		} else {
			final String convertedPath = convertPath(newPath, getDirectoryPath());
			if(convertedPath == null) {
				// Failed to convert path. Showing message from UI thread.
				String message = Messages.getString("ViewContentProvider.ViewContentProvider.InvalidPath_ErrMsg1")  //$NON-NLS-1$
						+ newPath + Messages.getString("ViewContentProvider.ViewContentProvider.InvalidPath_ErrMsg2"); //$NON-NLS-1$
				MessageBoxRunnable runnable = new MessageBoxRunnable(message);
				// Creating message box from UI thread.
				Display.getDefault().syncExec(runnable);
				
				return getDirectoryPath();
			}
			
			IFTPService ftpService = HTIServiceFactory.createFTPService(RemoteControlConsole.getInstance());
			List<IFtpObject> ftpObjects = new ArrayList<IFtpObject>(); 
			try {
				// Adding link to upper folder.
				ftpObjects.add(new FtpUplinkObject());
				
				// Getting directories.
				String[] dirs = ftpService.listDirs(convertedPath, timeoutTime);
				for(String dir : dirs){
					ftpObjects.add(new FtpFolderObject(dir));
				}

				// Getting files.
				
				FileInfo[] files = ftpService.listFilesAndDetails(convertedPath, 3000);
				for(FileInfo file : files){
					ftpObjects.add(new FtpFileObject(file.getName(), file.getSize()));
				}

				// Listing succeeded. Updating settings.
				Object[] objects = ftpObjects.toArray();
				currentDirListing = objects;
				setDirectoryPath(convertedPath);
				return convertedPath;
			} catch (ServiceShutdownException e){
				// Service is not online. No errors in transfer.
			} catch (Exception e) {
				// Failed to download contents. Showing message from UI thread.
				Runnable updateFailed = new Runnable() {
					public void run() {
						RemoteControlMessageBox message = new RemoteControlMessageBox(
								Messages.getString("ViewContentProvider.Failed_List_Content_ConsoleMsg") //$NON-NLS-1$
								+ convertedPath + "'.", SWT.ICON_ERROR); //$NON-NLS-1$
						message.open();
					}
				};
				// Creating message box from UI thread.
				Display.getDefault().syncExec(updateFailed);
				
				e.printStackTrace();
			}
		}
		// Setting path failed. Returning current path.
		return getDirectoryPath();
	}

	/**
	 * Converts path to be used in device.
	 * This method also checks if there is one or multiple '..\' in
	 * the beginning of path and merges path with current path. Shows
	 * error message if merge fails and returns null.
	 * @param newPath to be converted.
	 * @param oldPath Old path that might be needed during conversion. Can be null.
	 * @return Converted path or null if path couldn't be converted.
	 */
	public String convertPath(final String newPath, final String oldPath) {

		String newConvertedPath = newPath.replace("/", symbianFileSeparator); //$NON-NLS-1$
		int dirCount = 0;

		// Checking how many ..\ there are in new path and modifies path.
		while(newConvertedPath.startsWith(UPLEVEL)) {
			newConvertedPath = newConvertedPath.substring(3, newConvertedPath.length());
			dirCount++;
		}

		// Paths need to be merged, because there was ..\ in input path.
		if(dirCount > 0) {
			if(oldPath == null) {
				// Cannot get upper directory from root.
				return null;
			}

			String oldConvertedPath = oldPath;
			
			if(oldPath.endsWith(symbianFileSeparator)) {
				oldConvertedPath = oldPath.substring(0, oldPath.length() - 1);
			}

			// Converts current path matching the amount of found ..\ in input path.
			for(int i = 0;i < dirCount;i++) {
				if(oldConvertedPath.lastIndexOf(symbianFileSeparator) >= 0) {
					oldConvertedPath = oldConvertedPath.substring(0, oldConvertedPath.lastIndexOf(symbianFileSeparator));
				} else {
					// More up directories than in old path.
					return null;
				}
			}
			
			return oldConvertedPath + symbianFileSeparator + newConvertedPath;
		}

		return newConvertedPath;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface.viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	public void inputChanged(Viewer v, Object oldInput, Object newInput) {
	//	Not needed
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	public void dispose() {
	}
	
	/**
	 * Returns input object.
	 * @return Input object.
	 */
	public Object getInput(){
		return new Object();
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java.lang.Object)
	 */
	public Object[] getElements(Object parent) {
		return currentDirListing;
	}
	
	/**
	 * Returns list of drives that are used in root.
	 * @return List of drives that are used in root.
	 */
	public Object[] getRoot() {
		
		// Getting root from the device if possible.
		if(RCPreferences.getGetDriveList()
				&& connectionStatusProvider.isConnected()
				&& supportsDriveListing()) {
			Object[] root = getRootFromDevice();
			if(root != null) {
				return root;
			}
		}
		
		// Couldn't get root from the device. Returning default root.
		return getDefaultRoot();
	}
	
	/**
	 * Checks if drive listing is supported in current HTI agent.
	 * @return True if drive listing is supported. False otherwise.
	 */
	private boolean supportsDriveListing() {
		// Drive listing is supported in 1.88-> and 2.1->
		if(isSupportedVersion(new HTIVersion(1, 88)) ||
				isSupportedVersion(new HTIVersion(2, 1))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if copy, move, and rename operations are supported in current HTI agent.
	 * @return True if drive listing is supported. False otherwise.
	 */
	public boolean supportsCopyMoveRename() {
		// Copy, move, and rename are supported in 1.91-> and 2.5-> versions.
		// 1.91-> versions are used for 3.x devices and 2.5-> versions for 5.x devices.
		if(isSupportedVersion(new HTIVersion(1, 91)) ||
				isSupportedVersion(new HTIVersion(2, 5))) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Checks if current version is same or newer than given version.
	 * @return True if current version is newer or same as given version. False otherwise.
	 */
	private boolean isSupportedVersion(HTIVersion comparedVersion) {
		HTIVersion currentVersion = HTIServiceFactory.getCurrentHTIVersion();
		
		if(currentVersion != null &&
				currentVersion.getMajor() == comparedVersion.getMajor() &&
				currentVersion.getMinor() >= comparedVersion.getMinor()) {
			
			return true;
		}
		
		return false;
	}

	/**
	 * Returns list of drives that are used in root.
	 * @return List of directories that are used in root. Or null if couldn't get list
	 * from the device.
	 */
	private Object[] getRootFromDevice() {
		try {
			// Creating needed variables.
			IFTPService ftpService = HTIServiceFactory.createFTPService(RemoteControlConsole.getInstance());
			List<IFtpObject> ftpObjects = new ArrayList<IFtpObject>();
			
			// Getting drive list from the device.
			DriveInfo[] drives = ftpService.listDrives(timeoutTime);
			for(DriveInfo info : drives) {
				ftpObjects.add(new FtpDriveObject(info.getRootPath(), info, connectionStatusProvider));
			}
			
			return ftpObjects.toArray();
		} catch (Exception e) {
			// No need to report. Default drives are shown instead.
		}
		
		return null;
	}
	
	/**
	 * Returns list of drives that are used in root.
	 * @return List of drives that are used in root.
	 */
	private Object[] getDefaultRoot() {
		// Getting drives from preferences.
		String[] drives = RCPreferences.getShownDrives();
		IFtpObject[] objects = new IFtpObject[drives.length];
		
		// Creating drive objects.
		for(int i = 0;i < drives.length;i++) {
			objects[i] = new FtpDriveObject(drives[i], null, connectionStatusProvider);
		}
		
		return objects;
	}
	
	/**
	 * Sets files that are waiting for paste operation.
	 * @param selectedFiles Files that are waiting for paste.
	 * @param selectedPath Path where files are located.
	 * @param fileOperation Copy or cut operation.
	 */
	public void setClipboardFiles(List<IFtpObject> selectedFiles, String selectedPath, OPERATION fileOperation) {
		this.selectedFiles = selectedFiles;
		this.selectedPath = selectedPath;
		this.fileOperation = fileOperation;
	}

	/**
	 * Returns list of files which are waiting for paste.
	 * @return selectedFiles List of files which are waiting for paste.
	 */
	public List<IFtpObject> getSelectedFiles() {
		return selectedFiles;
	}

	/**
	 * Returns current operation for selected files.
	 * @return Current operation for selected files.
	 */
	public OPERATION getFileOperation() {
		return fileOperation;
	}

	/**
	 * Returns path where files were selected.
	 * @return Path where files were selected.
	 */
	public String getSelectedPath() {
		return selectedPath;
	}

	/**
	 * Runnable that can be used to show message to user.
	 */
	private class MessageBoxRunnable implements Runnable {

		/**
		 * Message to be shown to user.
		 */
		private final String message;

		/**
		 * Constructor.
		 * @param message to be shown to user in message box.
		 */
		public MessageBoxRunnable(final String message) {
			this.message = message;
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			RemoteControlMessageBox msgBox = new RemoteControlMessageBox(
					message, SWT.ICON_ERROR);
			msgBox.open();
		}
	}
}
