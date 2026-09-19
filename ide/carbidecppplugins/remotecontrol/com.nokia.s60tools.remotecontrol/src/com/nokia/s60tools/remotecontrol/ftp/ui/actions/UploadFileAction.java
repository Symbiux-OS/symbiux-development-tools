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

package com.nokia.s60tools.remotecontrol.ftp.ui.actions;

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpUtils;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.ViewContentProvider;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;

/**
 * Action for uploading file from device
 */
public class UploadFileAction extends S60ToolsBaseAction {

	/**
	 * Viewer that is target for action.
	 */
	private TableViewer viewer;

	/**
	 * Content provider
	 */
	private ViewContentProvider contentProvider;

	/**
	 * Constructor
	 * @param viewer TableViewer.
	 * @param contentProvider Content provider.
	 */
	public UploadFileAction(TableViewer viewer, ViewContentProvider contentProvider) {
		super(Messages.getString("UploadFileAction.Action_Name"),  //$NON-NLS-1$
				Messages.getString("UploadFileAction.Tooltip_Name"),  //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON,
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_UPLOAD));
		this.viewer = viewer;
		this.contentProvider = contentProvider;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		
		// Show select files dialog to user

		Shell sh = RemoteControlActivator.getCurrentlyActiveWbWindowShell();
		FileDialog fileDialog = new FileDialog(sh, SWT.MULTI);
		fileDialog.setText(Messages.getString("UploadFileAction.SelectUploadFiles_DlgText")); //$NON-NLS-1$
		
		String uploadFilterPath = RCPreferences.getUploadLocation();
		if (uploadFilterPath == "") { //$NON-NLS-1$
			// Workspace root is used as default folder
			IPath location = ResourcesPlugin.getWorkspace().getRoot().getLocation();
			fileDialog.setFilterPath(location.toString());
		} else {
			// use last used folder
			fileDialog.setFilterPath(uploadFilterPath);
		}
		
		if (fileDialog.open() == null) {
			// User is canceled dialog
			return;
		}	
		
		String[] uploadFiles = fileDialog.getFileNames();
		String srcDir = fileDialog.getFilterPath();
		// Remember last used folder
		RCPreferences.setUploadLocation(srcDir);
		
		// Get upload path
		String uploadDir = FtpUtils.getUploadDirectory(viewer, contentProvider);
		
		String[] sourceFiles = getSourceFiles(srcDir, uploadFiles);
		if(uploadDir != null) {
			// Uploading files only when not in root.
			FtpUtils.uploadFiles(uploadDir, sourceFiles);
		}
	}

	/**
	 * Returns list of file names concatenated with path.
	 * @param srcDir Directory to be concatenated with file names.
	 * @param uploadFiles List of file names to be concatenated with directory.
	 * @return List of files with path.
	 */
	private String[] getSourceFiles(String srcDir, String[] uploadFiles) {
		String[] srcFiles = new String[uploadFiles.length];
		for(int i = 0;i < uploadFiles.length;i++) {
			srcFiles[i] = FtpUtils.addFileSepatorToEnd(srcDir) + uploadFiles[i];
		}
		return srcFiles;
	}
}
