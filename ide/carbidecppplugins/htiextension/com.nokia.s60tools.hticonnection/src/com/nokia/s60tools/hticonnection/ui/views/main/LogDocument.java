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

package com.nokia.s60tools.hticonnection.ui.views.main;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;

/**
 * Log Document object that contains output of HTI API plug-in.
 */
public class LogDocument extends Document{
	
	/**
	 * Singleton instance of the class.
	 */
	private static LogDocument instance = null;
	/**
	 * String that contains latest changes.
	 */
	private static String lastChanges = ""; //$NON-NLS-1$

	/**
	 * Public accessor method.
	 * @return Singleton instance of the class.
	 */
	static public LogDocument getInstance(){
		if(instance == null ){
			instance = new LogDocument();
		}
		return instance;
	}

	/**
	 * Adding text to wait for next update.
	 * @param text Text to be added to the end of document.
	 */
	public synchronized void addText(String text) {
		lastChanges += text + "\r\n"; //$NON-NLS-1$
	}

	/**
	 * Method for updating document.
	 */
	public synchronized void updateDocument(){
		try {
			// Appending changes to the end of the document.
			replace(getLength(), 0, lastChanges);
		} catch (BadLocationException e) {
			// Appending text to the end of document. No problems should arise.
		} finally {
			// Resetting latest changes.
			lastChanges = ""; //$NON-NLS-1$
		}
	}

}
