/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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
 
package com.nokia.s60tools.util.sourcecode.internal;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver;
import com.nokia.s60tools.util.cmdline.ICustomLineReader;

/**
 * Custom line reader for <code>petran.exe</code> command line executions.
 */
public class PetranDumpLineReader implements ICustomLineReader {

	private final ArrayList<String> resultLinesArrayList;

	public PetranDumpLineReader(ArrayList<String> resultLinesArrayList) {
		this.resultLinesArrayList = resultLinesArrayList;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICustomLineReader#readLine(java.io.BufferedReader, com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver)
	 */
	public String readLine(BufferedReader br, 
			   ICmdLineCommandExecutorObserver observer) throws IOException {		
		String line = br.readLine();
		if(line != null){
			resultLinesArrayList.add(line);			
		}
		return line;
	}

	/**
	 * Gets result line array.
	 * @return the resultLinesArrayList
	 */
	public ArrayList<String> getResultLinesArrayList() {
		return resultLinesArrayList;
	}

}
