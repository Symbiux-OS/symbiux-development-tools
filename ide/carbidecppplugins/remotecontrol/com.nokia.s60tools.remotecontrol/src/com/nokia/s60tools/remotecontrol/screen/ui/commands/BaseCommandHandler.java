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

package com.nokia.s60tools.remotecontrol.screen.ui.commands;

import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.IHandler;
import org.eclipse.core.commands.IHandlerListener;

import com.nokia.s60tools.remotecontrol.RemoteControlActivator;
import com.nokia.s60tools.remotecontrol.keyboard.IKeyboardMediator;

/**
 * Base class for command handlers
 */
public class BaseCommandHandler implements IHandler{

	/**
	 * Keyboard mediator to delegate events to.
	 */
	private IKeyboardMediator keyboardMediator;
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#execute(org.eclipse.core.commands.ExecutionEvent)
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// Implemented in child classes
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#addHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	public void addHandlerListener(IHandlerListener handlerListener) {
		// Register keyboard mediator
		keyboardMediator = RemoteControlActivator.getKeyboardMediator();
		keyboardMediator.registerKeyboardMediatorClient(this);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#dispose()
	 */
	public void dispose() {
		//Not used
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isEnabled()
	 */
	public boolean isEnabled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#isHandled()
	 */
	public boolean isHandled() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.core.commands.IHandler#removeHandlerListener(org.eclipse.core.commands.IHandlerListener)
	 */
	public void removeHandlerListener(IHandlerListener handlerListener) {
		// Unregister keyboard mediator
		keyboardMediator.unregisterKeyboardMediatorClient(this);
	}
	
	/**
	 * Sends scancode
	 * @param scanCode Scan code
	 */
	protected void scanCodeKeyPressed(int scanCode) {
		keyboardMediator.scanCodeKeyPressed(this, scanCode);
	}
	
	protected void scanCodeKeyPressedAndHold(int scanCode, int delay) {
		keyboardMediator.scanCodeKeyPressedAndHold(this, scanCode, delay);
	}
}
