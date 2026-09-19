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

package com.nokia.s60tools.hticonnection.services.keyeventservice;

import com.nokia.HTI.common.Util;
import com.nokia.s60tools.hticonnection.core.AbstractRequest;

/**
 * Common abstract class for key event requests. 
 */
public abstract class AbstractKeyRequest extends AbstractRequest {

	/**
	 * Timeout for the requested operation
	 */
	protected final long timeout;

	/**
	 * Helper method for converting the key to scan code
	 * @param scanCode Scan code in integer format.
	 * @return Scan code as byte array.
	 */
	protected static byte[] convertScanCodeToByteArr(int scanCode) {
	    byte[] scanCodeByteArr = new byte[2]; //Wide character set used
	    Util.intToLittleEndianBytes(scanCode, scanCodeByteArr, 0, 2);
	    return scanCodeByteArr;
	}

	/**
	 * Constructor.
	 * @param name Name of the request.
	 * @param timeout Time in milliseconds that is waited for the operation to complete. Use 0 for infinite.
	 */
	protected AbstractKeyRequest(String name, long timeout){
		super(name);
		this.timeout = timeout;
	}

}
