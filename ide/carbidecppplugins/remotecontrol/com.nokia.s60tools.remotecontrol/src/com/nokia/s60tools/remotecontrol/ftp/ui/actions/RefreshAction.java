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

import org.eclipse.jface.action.IAction;

import com.nokia.s60tools.remotecontrol.ftp.ui.view.FtpView;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;

/**
 * Action for refreshing FTP view (files and folders)
 */
public class RefreshAction extends S60ToolsBaseAction {

	/**
	 * Instance of FtpView. Used for refreshing view.
	 */
	private FtpView ftpView;

	/**
	 * Constructor
	 * 
	 * @param ftpView Instance of FtpView
	 */
	public RefreshAction(FtpView ftpView) {
		super(Messages.getString("RefreshAction.Action_Name"),  //$NON-NLS-1$
				Messages.getString("RefreshAction.Tooltip_Name"),  //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON, 
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_REFRESH));
		this.ftpView = ftpView;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		ftpView.refreshFolderContent();
	}
}
