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

package com.nokia.s60tools.hticonnection.services;

import com.nokia.s60tools.hticonnection.exceptions.ConnectionException;
import com.nokia.s60tools.hticonnection.exceptions.HTIException;
import com.nokia.s60tools.hticonnection.exceptions.ServiceShutdownException;

/**
 * Service that contains interface for sending key events to device.
 */
public interface IKeyEventService {

	// Navigation related

	/** EStdKeyUpArrow=0x10 */
    public final static int SCANCODE_NAVI_NORTH = 0x10;
    /** EStdKeyRightArrow=0x0f */
    public final static int SCANCODE_NAVI_EAST = 0x0f;
    /** EStdKeyDownArrow=0x11 */
    public final static int SCANCODE_NAVI_SOUTH = 0x11;
    /** EStdKeyDownArrow=0x11 */
    public final static int SCANCODE_NAVI_WEST = 0x0e;
    /** EStdKeyDevice3=0xa7 */
    public final static int SCANCODE_NAVI_CENTERPUSH = 0xa7;
    
    // Numeric keypad keys in simple keyboard layout
    
    /** EStdKeyNkp0=0x92, Scan code for the 0 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_0 = 0x30;
    /** EStdKeyNkp1=0x89, Scan code for the 1 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_1 = 0x31;
    /** EStdKeyNkp2=0x8a, Scan code for the 2 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_2 = 0x32;
    /** EStdKeyNkp3=0x8b, Scan code for the 3 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_3 = 0x33;
    /** EStdKeyNkp4=0x8c, Scan code for the 4 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_4 = 0x34;
    /** EStdKeyNkp5=0x8d, Scan code for the 5 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_5 = 0x35;
    /** EStdKeyNkp6=0x8e, Scan code for the 6 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_6 = 0x36;
    /** EStdKeyNkp7=0x8f, Scan code for the 7 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_7 = 0x37;
    /** EStdKeyNkp8=0x90, Scan code for the 8 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_8 = 0x38;
    /** EStdKeyNkp9=0x91, Scan code for the 9 key on the Numeric keypad. */
	public final static int SCANCODE_NUMPAD_9 = 0x39;
    /** EStdKeyHash=0x7f, Scan code for Hash key (#) key. */
	public final static int SCANCODE_NUMPAD_HASH = 0x7f;
    /** EStdKeyNkpAsterisk=0x85, Scan code for Asterisk (*) key on the Numeric keypad. */
    public final static int SCANCODE_NUMPAD_ASTERISK = 0x85;
    /** EStdKeyNkpEnter=0x88, Scan code for Enter key on the Numeric keypad. */
    public final static int SCANCODE_NUMPAD_ENTER = 0x88;

    // Softkeys
    
    /** EStdKeyDevice0=0xa4 */
    public final static int SCANCODE_SOFTLEFT = 0xa4;
    /** EStdKeyDevice1=0xa5 */
    public final static int SCANCODE_SOFTRIGHT = 0xa5;

    // Other
    
    /** EStdKeyApplication0=0xb4 */
    public final static int SCANCODE_APP = 0xb4;
    /** EStdKeyNo=0xc5 */
    public final static int SCANCODE_END = 0xc5;
    /** EStdKeyYes=0xc4 */
    public final static int SCANCODE_SEND = 0xc4;
    /** EStdKeyRightShift=0x13 */
    public final static int SCANCODE_EDIT = 0x13;
    /** EStdKeyBackspace=0x01 */
    public final static int SCANCODE_CLEAR = 0x01;
    /** EStdKeyEnter=0x03 */
    public final static int SCANCODE_ENTER = 0x03;
    /** EStdKeyRightCtrl=0x17 */
    public final static int SCANCODE_RIGHT_CTRL = 0x17;
    /** EStdKeySpace=0x05 */
    public final static int SCANCODE_SPACE = 0x05;
    /** EStdKeyDevice2=0xa6 */
    public final static int SCANCODE_POWER =0xa6;
    /** EStdKeyRightShift=0x13 */
    public final static int SCANCODE_NAVI_EDIT = 0x13;
    /** EStdKeyDevice6=0xaa */
    public final static int SCANCODE_NAVI_VOICE = 0xaa;
    /** EStdKeyDevice6=0xab */
    public final static int SCANCODE_NAVI_CAMERA = 0xab;
    /** EStdKeyLeftFunc=0x18 */
    public final static int SCANCODE_CHR = 0x18;
    /** EStdKeyDevice4=0xa8 */
    public final static int SCANCODE_GRIP_OPEN = 0xa8;
    /** EStdKeyDevice5=0xa9 */
    public final static int SCANCODE_GRIP_CLOSED = 0xa9;
    
    
	/**
	 * Sends tap screen event to the device.
	 * Total time used to tap should be shorted than timeout, or request will fail on timeout.
	 * @param x Horizontal screen location of tap screen event in pixels.
	 * @param y Vertical screen location of tap screen event in pixels.
	 * @param tapCount Count of taps within this event.
	 * @param timeToHold How long one tap lasts in milliseconds.
	 * @param pauseBetweenTaps How much time there is between taps in milliseconds.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void tapScreen(int x, int y, int tapCount, int timeToHold, int pauseBetweenTaps, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Sends tap and drag event to the device.
	 * Total time used to tap should be shorted than timeout, or request will fail on timeout.
	 * @param startX Horizontal screen location from where dragging starts.
	 * @param startY Vertical screen location from where dragging starts.
	 * @param endX Horizontal screen location where dragging ends.
	 * @param endY Vertical screen location where dragging ends.
	 * @param dragTime Time that it takes from starting point to the end point.
	 * This value should be shorted than timeout, or request will fail because timeout.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * and new requests are not accepted.
	 */
	public void tapAndDrag(int startX, int startY, int endX, int endY, int dragTime, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Sends press key event. 
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * Pure ASCII text is sent using <code>typeText</code>.
	 * @param scanCode Scan code for the key.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * 			and new requests are not accepted.
	 * @see IKeyEventService#typeText
	 */
	public void pressKey(int scanCode, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;

	/**
	 * Sends press key event where key stays down for the holdTime.
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * Pure ASCII text is sent using <code>typeText</code>.
	 * @param scanCode Scan code for the key.
	 * @param holdTime Time that key is kept down in milliseconds. This should be shorter
	 * time than timeout, or this request will fail because timeout.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * 			and new requests are not accepted.
	 * @see IKeyEventService#typeText
	 */
	public void pressKeyLong(int scanCode, int holdTime, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Sends release key event.
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * Pure ASCII text is sent using <code>typeText</code>.
	 * @param scanCode Scan code for the key.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * 			and new requests are not accepted.
	 * @see IKeyEventService#typeText
	 */
	public void releaseKey(int scanCode, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;

	/**
	 * Sends a sequence of ASCII text.
	 * @param typeStr ASCII text string.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * 			and new requests are not accepted.
	 */
	public void typeText(String typeStr, long timeout)
						throws ServiceShutdownException, HTIException, ConnectionException;
	
	/**
	 * Sends hold key event.
	 * Used only for special keys with scan code defined in <code>e32keys.h</code> header file. 
	 * Pure ASCII text is sent using <code>typeText</code>.
	 * @param scanCode Scan code for the key.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 * @throws ConnectionException Connection failed
	 * @throws HTIException Thrown when there are problems with HTI
	 * @throws ServiceShutdownException Thrown after services have been shut down
	 * 			and new requests are not accepted.
	 * @see IKeyEventService#typeText
	 */
	public void holdKey(int scanCode, long timeout)
					throws ServiceShutdownException, HTIException, ConnectionException;
	

}
