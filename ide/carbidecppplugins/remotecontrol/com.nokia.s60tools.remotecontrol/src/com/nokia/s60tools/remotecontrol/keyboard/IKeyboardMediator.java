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

package com.nokia.s60tools.remotecontrol.keyboard;

/**
 * Interface for mediator class implementations that act between keyboard requests from UI 
 * and services that send key requests further to key services. 
 */
public interface IKeyboardMediator{
	
	/**
	 * Register for keyboard mediator services.
	 * @param mediatorClient Client registering mediator services.
	 */
	public void registerKeyboardMediatorClient(Object mediatorClient);
	
	/**
	 * Unregister for keyboard mediator services.
	 * @param mediatorClient Client registering mediator services.
	 */
	public void unregisterKeyboardMediatorClient(Object mediatorClient);
	
	/**
	 * Called whenever a normal character key press event is received.
	 * @param mediatorClient Client registered to the mediator.
	 * @param charKey Character key pressed.
	 */
	public void characterKeyPressed(Object mediatorClient, char charKey);

	/**
	 * Called whenever client want to send a specific scan code key event.
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * @param mediatorClient Client registered to the mediator.
	 * @param scanCode Scan code for the special key used.
	 */
	public void scanCodeKeyPressed(Object mediatorClient, int scanCode);

	/**
	 * Called whenever client want to send a specific scan code key event, where key is hold down for defined time.
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * @param mediatorClient Client registered to the mediator.
	 * @param scanCode Scan code for the special key used.
	 * @param holdTime Time that key is hold down in ms.
	 */
	public void scanCodeKeyPressedAndHold(Object mediatorClient, int scanCode, int holdTime);

	/**
	 * Called whenever client want to hold a specific scan code key.
	 * Call should be always be matched with corresponding <code>scanCodeKeyReleased</code> call.
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * @param mediatorClient Client registered to the mediator.
	 * @param scanCode Scan code for the special key used.
	 */
	public void scanCodeKeyHold(Object mediatorClient, int scanCode);

	/**
	 * Called whenever client when a previously hold scan code key is released.
	 * Call should always have corresponding hold event executed beforehand by calling <code>scanCodeKeyHold</code> 
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * @param mediatorClient Client registered to the mediator.
	 * @param scanCode Scan code for the special key used.
	 */
	public void scanCodeKeyReleased(Object mediatorClient, int scanCode);

	/**
	 * Returns information about if mediator is currently idle and doing nothing.
	 * @param mediatorClient Client registered to the mediator.
	 * @return True if mediator is doing nothing, or false if there are events in the queue. 
	 */
	public boolean isMediatorIdle(Object mediatorClient);
	
}