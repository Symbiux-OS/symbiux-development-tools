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

/**
 * AppStatus
 */
public class AppStatus
{
	/**
	 * Application was not found.
	 * Original HTI return code is unsigned byte 0xf1.
	 */
    public static final byte NOT_FOUND = -15;
    /**
     * Application was found and it is currently running.
     * Original HTI return code is unsigned byte 0xf4.
     */
    public static final byte RUNNING = -12;

    /**
     * Status of application.
     */
    private final byte status;

    /**
     * Constructor.
     * @param status Status of application.
     */
    public AppStatus(byte status)
    {
        this.status = status;
    }

    /**
     * This method returns status of application.
     * @return Status of application.
     */
    public byte getStatus()
    {
        return status;
    }

}

