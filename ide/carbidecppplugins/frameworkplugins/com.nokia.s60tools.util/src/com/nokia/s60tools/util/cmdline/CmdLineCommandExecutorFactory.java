/*
* Copyright (c) 2006 Nokia Corporation and/or its subsidiary(-ies). 
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
 
 
package com.nokia.s60tools.util.cmdline;

import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Creates command line executor based on the underlying
 * operating system.
 */
public class CmdLineCommandExecutorFactory {
	
	/**
	 * Creates command line executor based on the underlying
     * operating system.
	 * @param observer Command line executor observer instance.
	 * @param consolePrintUtility Instance of concrete console print utility.
	 * @return New command line executor instance.
	 * @throws UnsupportedOSException
	 */
	public static ICmdLineCommandExecutor CreateOsDependentCommandLineExecutor(ICmdLineCommandExecutorObserver observer,
			                                                                   IConsolePrintUtility consolePrintUtility) throws UnsupportedOSException{
		
        String osName = System.getProperty("os.name" ); //$NON-NLS-1$

        if( osName.toUpperCase().startsWith("WINDOWS")){ //$NON-NLS-1$
       	return new Win32CmdLineCommandExecutor(observer, consolePrintUtility);
        }
        
        throw new UnsupportedOSException(); // non-windows OS:s are not supported
	}
}
