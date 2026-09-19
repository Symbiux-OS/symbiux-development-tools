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


package com.nokia.s60tools.remotecontrol.keyboard.ui.actions;

import org.eclipse.jface.action.IAction;

import com.nokia.s60tools.remotecontrol.keyboard.ui.view.KeyboardView;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;


/**
 * Action for show/hide Navi pane
 */
public class NaviPaneAction extends S60ToolsBaseAction {
	
	/**
	 * Instance of KeyboardView. Used for showing/hiding navi pane 
	 */
	private KeyboardView keyboardView;
	
	/**
	 * Constructor
	  */
	public NaviPaneAction(KeyboardView keyboardView) {
		super(Messages.getString("NaviPaneAction.Navikeys_Action_Text"), Messages.getString("NaviPaneAction.Navikeys_Action_Tooltip"), //$NON-NLS-1$ //$NON-NLS-2$
				IAction.AS_CHECK_BOX,
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_NAVI_KEYS));
		
		this.keyboardView = keyboardView;
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		//Show pane when button is selected. Hide when unselected.
		keyboardView.showNaviPane(isChecked());
	}	
}

