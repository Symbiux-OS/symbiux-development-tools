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

package com.nokia.s60tools.remotecontrol.ftp.ui.view;

import java.io.File;
import java.util.ArrayList;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.FileTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Widget;

import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.remotecontrol.ui.dialogs.RemoteControlMessageBox;

/**
 * Listener for dropping files to File Transfer view.
 */
public class FileDropTargetListener implements DropTargetListener {

	/**
	 * Content provider for the viewer.
	 */
	private final ViewContentProvider contentProvider;
	/**
	 * Viewer that listens drop events.
	 */
	private final TableViewer viewer;

	/**
	 * Constructor.
	 * @param viewer Viewer that listens drop events.
	 * @param contentProvider Content provider for the viewer.
	 */
	public FileDropTargetListener(TableViewer viewer, ViewContentProvider contentProvider) {
		this.viewer = viewer;
		this.contentProvider = contentProvider;
	}

	//
	// Methods from DropTargetListener
	//
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragEnter(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragEnter(DropTargetEvent event) {
		setEventDetail(event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragLeave(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragLeave(DropTargetEvent event) {
		// Not implemented.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOperationChanged(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOperationChanged(DropTargetEvent event) {
		// Changing back to correct operation, if user tries to change operation.
		setEventDetail(event);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dragOver(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dragOver(DropTargetEvent event) {
		// Not implemented.
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#drop(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void drop(DropTargetEvent event) {
		if(FileTransfer.getInstance().isSupportedType(event.currentDataType)) {
			String[] paths = (String[])event.data;
			
			// Getting target directory.
			String uploadDir = FtpUtils.getUploadDirectory(viewer, contentProvider);
			Widget item = event.item;
			if(item instanceof TableItem) {
				TableItem tableItem = (TableItem)item;
				if(tableItem.getData() instanceof FtpFolderObject) {
					// Current directory name is added to allow uploading files to selected directory.
					uploadDir = uploadDir + tableItem.getText();
					uploadDir = FtpUtils.addFileSepatorToEnd(uploadDir);
				}
			}
			
			if(uploadDir != null) {
				// Dropping files only when not in root.
				String[] filesOnly = getFiles(paths);
				
				if(filesOnly.length > 0) {
					// Uploading files.
					FtpUtils.uploadFiles(uploadDir, getFiles(filesOnly));
				} else {
					// No files found. Showing message to user.
					String msg = Messages.getString("FileDropTargetListener.TransferFilesOnly_MsgBoxText"); //$NON-NLS-1$
					RemoteControlMessageBox msgBox = new RemoteControlMessageBox(msg, SWT.OK | SWT.ICON_INFORMATION);
					msgBox.open();
					
					// Operation was not completed successfully. Refusing from drop.
					event.detail = DND.DROP_NONE;
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.dnd.DropTargetListener#dropAccept(org.eclipse.swt.dnd.DropTargetEvent)
	 */
	public void dropAccept(DropTargetEvent event) {
		// Checking if current path has changed.
		setEventDetail(event);
	}

	//
	// Utility methods.
	//
	
	/**
	 * Returns supported TransferTypes.
	 * @return Supported transfer types.
	 */
	public Transfer[] getTransferTypes() {
		Transfer[] transferTypes = { FileTransfer.getInstance() };
		return transferTypes;
	}

	/**
	 * Returns all files from given paths. Other paths are discarded.
	 * @param paths Paths to be checked.
	 * @return All files from given paths.
	 */
	private String[] getFiles(String[] paths) {
		// Getting only files from given paths.
		ArrayList<String> files = new ArrayList<String>();
		for(String path : paths) {
			File file = new File(path);
			if(file.isFile()) {
				files.add(path);
			}
		}
		
		return files.toArray(new String[0]);
	}
	
	/**
	 * Sets correct detail value for given event.
	 * @param event Event to be checked.
	 */
	private void setEventDetail(DropTargetEvent event) {
		if(contentProvider.getDirectoryPath() == null) {
			// Can't drag into root.
			event.detail = DND.DROP_NONE;
		}
		else if((event.operations & DND.DROP_COPY) != 0) {
			// Accepting drags only with copy operations.
			event.detail = DND.DROP_COPY;
		}
	}
}
