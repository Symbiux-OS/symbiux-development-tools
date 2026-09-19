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

package com.nokia.s60tools.remotecontrol.screen.ui.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.ui.PartInitException;

import com.nokia.s60tools.remotecontrol.keyboard.ui.view.KeyboardView;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.util.RemoteControlConsole;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;
import com.nokia.s60tools.util.console.IConsolePrintUtility;


/**
 * Action for opening Keyboard view
 */
public class OpenKeyboardAction extends S60ToolsBaseAction {
	
	/**
	 * Constructor
	 * @param text Action's visible text.
	 * @param tooltip Action's tooltip text.
	 */
	public OpenKeyboardAction(String text, String tooltip) {
		super(text,	tooltip, IAction.AS_PUSH_BUTTON,
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_KEYBOARD_VIEW));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		try {
			KeyboardView.getViewInstance();
		} catch (PartInitException e) {
			e.printStackTrace();
			RemoteControlConsole.getInstance().println(e.getMessage(), 
					 IConsolePrintUtility.MSG_ERROR);
		}
	}
}

