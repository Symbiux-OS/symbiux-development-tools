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

package com.nokia.s60tools.remotecontrol.keyboard.ui.view;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Button;

import com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator;

/**
 * Mouse listener for keyboard buttons. Listens left mouse button.
 */
class KeyboardMouseListener implements MouseListener {
	
	/**
	 * Timestamp when left mouse button is pressed over the button
	 */
	private int mouseDownTimestamp = 0;
	
	/**
	 * Constant for left mouse button
	 */
	private final int LEFT_MOUSE_BUTTON = 1;
	
	/**
	 * Key data
	 */
	private KbKey key;
	
	/**
	 * Instance of the keyboard which is registered the keyboard mediator
	 */
	private Object keyboard;
	
	/**
	 * Keyboard mediator to delegate events to.
	 */
	private IKeyboardMediator keyboardMediator;
	
	/**
	 * Keeping  button down more than 500 ms is handled as long press
	 */
	private static final long LONG_PRESS_TIME = 500;

	/**
	 * Constructor
	 * @param key Key data
	 * @param keyboard Instance of the keyboard which is registered the keyboard mediator
	 * @param keyboardMediator Keyboard mediator to delegate events to
	 */
	public KeyboardMouseListener(KbKey key, Object keyboard, IKeyboardMediator keyboardMediator) {
		super();
		this.key = key;
		this.keyboard = keyboard;
		this.keyboardMediator = keyboardMediator;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDoubleClick(MouseEvent e) {
		// NOT USED
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseDown(MouseEvent e) {
		if (key.getScancode() >= 0) {
			// Scancode is mapped to button. Scancodes are sent after button is released.
			// Wait for button release.
			if (e.button == LEFT_MOUSE_BUTTON) {
				//Left mouse button pressed
				mouseDownTimestamp = e.time;
			}
		} else {
			// Shift key(on QWERTY keyboard) press is not send to target. 
			// It only changes button labels to shift labels.
			if (key.getKeyFunc() != KbKey.KbKeyFunc.SHIFT) {
				// Character is mapped to button. Send character event.
				keyboardMediator.characterKeyPressed(keyboard, ((Button)e.widget).getText().charAt(0));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	public void mouseUp(MouseEvent e) {
		if (key.getScancode() >= 0) {
			if (e.button == LEFT_MOUSE_BUTTON) {
				// Left button released
				// Calculate time button is kept down
				int holdTime = e.time - mouseDownTimestamp;
				if (holdTime > LONG_PRESS_TIME) {
					// Button is hold. Send press and hold key event.
					keyboardMediator.scanCodeKeyPressedAndHold(keyboard, key.getScancode(), holdTime);
				} else {
					// Button is pressed. Send press key event.
					keyboardMediator.scanCodeKeyPressed(keyboard, key.getScancode());
				}
			}
		}
	}
}
