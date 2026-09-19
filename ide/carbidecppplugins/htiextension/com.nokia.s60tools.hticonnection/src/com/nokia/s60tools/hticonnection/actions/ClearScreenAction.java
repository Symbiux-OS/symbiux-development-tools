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
 * Action for opening clearing console
 */
public class ClearScreenAction extends S60ToolsBaseAction {
	
	/**
	 * Viewer that is target for action.
	 */
	private final MainView mainView;

	/**
	 * Constructor
	 * @param mainView Viewer that is target for action.
	 */
	public ClearScreenAction(MainView mainView) {
		super(Messages.getString("ClearScreenAction.ClearScreen_Action_Text"), //$NON-NLS-1$
				Messages.getString("ClearScreenAction.ClearScreen_Action_Tooltip"), //$NON-NLS-1$
				IAction.AS_PUSH_BUTTON,
				ImageKeys.IMG_CLEAR_SCREEN);
		this.mainView = mainView;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		mainView.getMainTextViewer().clearScreen();
	}
}