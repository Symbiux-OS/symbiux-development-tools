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


package com.nokia.s60tools.hticonnection.actions;

import org.eclipse.jface.action.IAction;

import com.nokia.s60tools.hticonnection.resources.ImageKeys;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.ui.views.main.MainView;

/**
 * Action for setting scroll lock for console.
 */
public class ScrollLockAction extends S60ToolsBaseAction {
	
	/**
	 * Viewer that is target for action.
	 */
	private final MainView mainView;

	/**
	 * Constructor
	 * @param mainView Viewer that is target for action.
	 */
	public ScrollLockAction(MainView mainView) {
		super(Messages.getString("ScrollLockAction.ScrollLock_Action_Text"), //$NON-NLS-1$
				Messages.getString("ScrollLockAction.ScrollLock_Action_Tooltip"), //$NON-NLS-1$
				IAction.AS_CHECK_BOX,
				ImageKeys.IMG_SCROLL_LOCK);
		this.mainView = mainView;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		// Setting scroll lock on if action is checked.
		mainView.getMainTextViewer().setScrollLock(isChecked());
	}
}