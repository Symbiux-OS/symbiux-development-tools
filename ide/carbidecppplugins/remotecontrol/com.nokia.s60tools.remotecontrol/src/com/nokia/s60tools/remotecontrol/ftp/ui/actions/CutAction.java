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

package com.nokia.s60tools.remotecontrol.ftp.ui.actions;

import java.util.List;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpUtils;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.IFtpObject;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.ViewContentProvider;
import com.nokia.s60tools.remotecontrol.ftp.ui.view.ViewContentProvider.OPERATION;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;

/**
 * Action for cutting file or folder for paste operation in the device
 */
public class CutAction extends S60ToolsBaseAction {

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
	 * 
	 * @param viewer Viewer that is target for action.
	 * @param contentProvider Content provider
	 */
	public CutAction(TableViewer viewer,
			ViewContentProvider contentProvider) {
		
		super(Messages.getString("CutAction.Action_Name"), //$NON-NLS-1$
				Messages.getString("CutAction.Tooltip_Name"), //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON, 
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(
						ISharedImages.IMG_TOOL_CUT));
		this.viewer = viewer;
		this.contentProvider = contentProvider;
		
		setDisabledImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_CUT_DISABLED));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		List<IFtpObject> selectedFiles = FtpUtils.getSelectedObjectNames(viewer, contentProvider);
		String path = FtpUtils.getCurrentPath(contentProvider);
		contentProvider.setClipboardFiles(selectedFiles, path, OPERATION.CUT);
	}
}
