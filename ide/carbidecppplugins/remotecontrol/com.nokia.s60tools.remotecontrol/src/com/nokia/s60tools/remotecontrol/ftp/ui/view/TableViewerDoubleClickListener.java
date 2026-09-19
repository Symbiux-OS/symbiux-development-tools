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

import java.io.File;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TableViewer;

/**
 * Handles double click events in ftp table viewer.
 */
public class TableViewerDoubleClickListener implements IDoubleClickListener {

	/**
	 * Content provider
	 */
	private final ViewContentProvider contentProvider;
	
	/**
	 * Table viewer
	 */
	private final TableViewer viewer;
	
	/**
	 * FTP view composite
	 */
	private final FtpView ftpView;
	
	/**
	 * Constructor.
	 * @param viewer TableViever
	 * @param contentProvider Content provider for viewer.
	 * @param ftpView Owner of viewer and contentProvider.
	 */
	public TableViewerDoubleClickListener(TableViewer viewer,
			ViewContentProvider contentProvider, FtpView ftpView) {
				this.viewer = viewer;
				this.contentProvider = contentProvider;
				this.ftpView = ftpView;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.viewers.IDoubleClickListener#doubleClick(org.eclipse.jface.viewers.DoubleClickEvent)
	 */
	public void doubleClick(DoubleClickEvent event) {
		IStructuredSelection selection = (IStructuredSelection) viewer.getSelection();
		Object selectedObject = selection.getFirstElement();
		
		// Nothing selected.
		if(selectedObject == null){
			return;
		}
		
		IFtpObject ftpObject = (IFtpObject) selectedObject;

		// Handling if object is a folder or a drive.
		if(FtpFolderObject.class.isInstance(selectedObject) || FtpDriveObject.class.isInstance(selectedObject)){
			String currentDirectory = contentProvider.getDirectoryPath();
			String targetDirectory;
			if(currentDirectory == null){
				// If current directory doesn't exist, then using plain name.
				targetDirectory = ftpObject.getName();
			}
			else if(currentDirectory.substring(currentDirectory.length() - 1).equals(File.separator)){
				// Current directory contains already separator at the end and second separator causes problems.
				targetDirectory = currentDirectory.concat(ftpObject.getName());
			}
			else{
				// Adding directory name with file separator.
				targetDirectory = currentDirectory + File.separator + ftpObject.getName();
			}
			ftpView.updatePathThenRefresh(targetDirectory);
		}
		
		// Handling if object is file. Files are opened when double clicked.
		if(FtpFileObject.class.isInstance(selectedObject)) {
			FtpUtils.downloadFileAndOpen(viewer, contentProvider);
		}
		
		// Handling if object is a uplink.
		if(FtpUplinkObject.class.isInstance(selectedObject)){
			String currentDirectory = contentProvider.getDirectoryPath();
			File currentDir = new File(currentDirectory);
			String targetDirectory = currentDir.getParent();
			ftpView.updatePathThenRefresh(targetDirectory);
		}
	}
}
