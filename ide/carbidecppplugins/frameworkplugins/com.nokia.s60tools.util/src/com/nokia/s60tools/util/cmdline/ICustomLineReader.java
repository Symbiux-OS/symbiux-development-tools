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
 * Specifies interface that each custom line reader used 
 * with command line executor must implement. Custom line
 * readers enable parsing of output of a command line
 * tool. This makes possible to invoke custom actions
 * based on the output. The parsed and returned string
 * goes then trough default handling.
 * 
 * DefaultLineReader implements a non-parsing instance
 * of this interface.
 * @see com.nokia.s60tools.util.cmdline.DefaultLineReader
 */
public interface ICustomLineReader {
	
	/**
	 * Reads and parses from buffered reader input
	 * and return the result to the caller.
	 * @param br Buffered reader to read from.
	 * @param observer Observer to notify about progress if the progress can 
	 *                 be estimated based on the command line tool's output.
	 * @return Read and parsed command line tool output string.
	 * @throws IOException
	 */
	public String readLine(BufferedReader br, 
					ICmdLineCommandExecutorObserver observer) throws IOException;
}
