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
 * This class holds required information about key events.
 */
public class BufKeyEvent {
	
	/**
	 * Character content for this event.
	 */
	private char charKey;
	/**
	 * Scan code for this event.
	 */
	private int scanCode;
	/**
	 * How long key is hold down. Only with scan codes.
	 */
	private int holdTime;
	/**
	 * Type of this event.
	 */
	private EventType eventType;

	/**
	 * Key event types that can be sent to the device.
	 * It is recommended to use charKeyPressed, scanCodeKeyPressed, and
	 * scanCodeKeyPressAndHold because it is impossible to predict how long
	 * it will be between hold and release key events that are sent to the
	 * device. This because there can be multiple other long events between them.
	 */
	public enum EventType {
		charKeyPressed,
		scanCodeKeyPressed,
		scanCodeKeyPressAndHold,
		scanCodeKeyHold,
		scanCodeKeyReleased
	}
	
	/**
	 * Constructor for single character events.
	 * @param charKey Character for this event.
	 */
	public BufKeyEvent(char charKey) {
		this.charKey = charKey;
		this.eventType = EventType.charKeyPressed;
	}
	
	/**
	 * Constructor for press, hold, and release key event.
	 * @param scanCode Scan code for this event.
	 * @param eventType Type of event. This should be
	 * scanCodeKeyPressed, scanCodeKeyHold, or scanCodeKeyReleased.
	 */
	public BufKeyEvent(int scanCode, EventType eventType) {
		this.scanCode = scanCode;
		this.eventType = eventType;
	}

	/**
	 * Constructor for pressing and holding key for certain time.
	 * @param scanCode Scan code for this event.
	 * @param holdTime Key is pressed this long in ms.
	 */
	public BufKeyEvent(int scanCode, int holdTime) {
		this.scanCode = scanCode;
		this.holdTime = holdTime;
		this.eventType = EventType.scanCodeKeyPressAndHold;
	}

	/**
	 * Getter for charKey.
	 * @return the charKey
	 */
	public char getCharKey() {
		return charKey;
	}

	/**
	 * Getter for scanCode.
	 * @return the scanCode
	 */
	public int getScanCode() {
		return scanCode;
	}

	/**
	 * Getter for holdTime.
	 * @return the holdTime in ms.
	 */
	public int getHoldTime() {
		return holdTime;
	}

	/**
	 * Getter for eventType.
	 * @return the eventType
	 */
	public EventType getEventType() {
		return eventType;
	}
}
