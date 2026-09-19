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


package com.nokia.s60tools.imaker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedInputStream;

import org.eclipse.ui.console.MessageConsoleStream;

import com.nokia.s60tools.imaker.internal.console.IMakerConsole;

/**
 * This class is solely for reading data from an input stream,
 * interpreting and converting it to human readable form and
 * printing it to a window.
 * 
 */
public class IMakerConsoleLogger extends Thread {

	/**
	 * Number of lines already printed in the document.
	 * Usually some static text of notification.
	 */
	public static final int PRE_PRINTED_LINE_COUNT = 2;

	public boolean done = false;
	/**
	 * A window where to print data.
	 */
	public IMakerBuildLogEditor editor = null;
	/**
	 * A stream where to read data from.
	 */
	public PipedInputStream pis = null;

	/**
	 * Default constructor
	 * 
	 * @param bw A reference to a window where to print the data
	 * @param pis a reference to input stream to read.
	 */
	public IMakerConsoleLogger(PipedInputStream pis) {
		this.pis = pis;
	}

	/**
	 * Runs the thread.
	 */
	public void run() {
		// Read messages from the wrapper until forced to stop
		BufferedReader br = new BufferedReader(new InputStreamReader(pis));
		String line;
		IMakerConsole console = IMakerConsole.getDefault();
		MessageConsoleStream infoStream = console.getNewMessageConsoleStream(IMakerConsole.MSG_INFORMATION);
		MessageConsoleStream errorStream = console.getNewMessageConsoleStream(IMakerConsole.MSG_ERROR);
		MessageConsoleStream warningStream = console.getNewMessageConsoleStream(IMakerConsole.MSG_WARNING);
		while (!done) {
			try {
				// Read bytes from stream and convert them to String
				//if ((bytesAvailable = pis.available()) > 0)
				while ((line = br.readLine()) != null) {
					/*
					 * Check for errors and create markers to visualize them in the editor.
					 */					
					String lc = line.toLowerCase();
					if ((lc.indexOf("error ") != -1) 
							|| (lc.indexOf("no rule to make target") != -1 )
							|| (lc.indexOf("error:") != -1)) {
						errorStream.println(line);
					} else if (lc.indexOf("warning:") != -1) {
						warningStream.println(line);
					} else {
						infoStream.println(line);
					}
				}
				Thread.sleep(IMakerKeyConstants.DEFAULT_THREAD_SLEEP_TIME);
				infoStream.flush();
				infoStream.close();
				errorStream.flush();
				errorStream.close();
				warningStream.flush();
				warningStream.close();
			} catch (IOException e) {
				done=true;
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		try {
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
