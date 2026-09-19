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
import com.nokia.s60tools.remotecontrol.preferences.RCPreferences;
import com.nokia.s60tools.remotecontrol.preferences.RCPreferenceConstants.KeyboardLayout;
import com.nokia.s60tools.remotecontrol.resources.ImageKeys;
import com.nokia.s60tools.remotecontrol.resources.ImageResourceManager;
import com.nokia.s60tools.remotecontrol.resources.Messages;
import com.nokia.s60tools.ui.actions.S60ToolsBaseAction;

/**
 * Action for switching keyboard layout
 */
public class SwitchKeyboardAction extends S60ToolsBaseAction {
	
	/**
	 * Text used for switch to qwerty keyboard action.
	 */
	private final String QWERTY_ACTION_TEXT = Messages.getString("SwitchKeyboardAction.SwitchToQwerty_Text"); //$NON-NLS-1$
	/**
	 * Text used for switch to simple keyboard action.
	 */
	private final String SIMPLE_ACTION_TEXT = Messages.getString("SwitchKeyboardAction.SwitchToSimple_Text"); //$NON-NLS-1$
	
	/**
	 * Instance of KeyboardView. Used for switching keyboard layout
	 */
	private KeyboardView keyboardView;
	
	/**
	 * Constructor
	 * @param keyboardView Instance of KeyboardView
	 */
	public SwitchKeyboardAction(KeyboardView keyboardView) {
		// Initializing action with empty strings.
		super("", "", IAction.AS_PUSH_BUTTON, //$NON-NLS-1$ //$NON-NLS-2$
				ImageResourceManager.getImageDescriptor(ImageKeys.IMG_SWITCH_KEYBOARD_MODE));
		this.keyboardView = keyboardView;
		
		// Adding correct text.
		if(RCPreferences.getKeyboardLayout() == KeyboardLayout.SIMPLE) {
			this.setText(QWERTY_ACTION_TEXT);
			this.setToolTipText(QWERTY_ACTION_TEXT);
		} else {
			this.setText(SIMPLE_ACTION_TEXT);
			this.setToolTipText(SIMPLE_ACTION_TEXT);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.action.Action#run()
	 */
	public void run() {
		//Switch keyboard layout when button is selected
		if(RCPreferences.getKeyboardLayout() == KeyboardLayout.QWERTY) {
			this.setText(QWERTY_ACTION_TEXT);
			this.setToolTipText(QWERTY_ACTION_TEXT);
			keyboardView.switchKeyboardLayout(KeyboardLayout.SIMPLE);
		} else {
			this.setText(SIMPLE_ACTION_TEXT);
			this.setToolTipText(SIMPLE_ACTION_TEXT);
			keyboardView.switchKeyboardLayout(KeyboardLayout.QWERTY);
		}
	}	
}

