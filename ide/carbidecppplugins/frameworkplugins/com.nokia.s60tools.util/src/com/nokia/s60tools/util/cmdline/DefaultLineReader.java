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

import java.io.BufferedReader;
import java.io.IOException;


/**
 * An example instance of ICustomLineReader interface.
 * This is used by default if no user-defined custom
 * parser has not been passed for command executor
 * instance.
 *
 *@see com.nokia.s60tools.util.cmdline.ICustomLineReader
 */
public class DefaultLineReader implements ICustomLineReader {

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICustomLineReader#readLine(java.io.BufferedReader, com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver)
	 */
	public String readLine(BufferedReader br, 
						   ICmdLineCommandExecutorObserver observer) throws IOException {		
		return br.readLine();
	}

}
