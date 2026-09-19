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

package com.nokia.s60tools.hticonnection.util;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.hticonnection.common.ProductInfoRegistry;
import com.nokia.s60tools.hticonnection.ui.views.main.LogDocument;
import com.nokia.s60tools.util.console.AbstractProductSpecificConsole;

/**
 * Singleton class that offers console printing
 * services for the HTI API product. All text is printed
 * first to document and then transferred to console.
 */
public class HtiApiConsole extends AbstractProductSpecificConsole {
	
	/**
	 * Singleton instance of the class.
	 */
	static private HtiApiConsole instance = null;
	/**
	 * Storing log document instance here.
	 */
	private static LogDocument log = null;
	
	/**
	 * Public accessor method.
	 * @return Singleton instance of the class.
	 */
	static public HtiApiConsole getInstance(){
		if(instance == null ){
			instance = new HtiApiConsole();
			log = LogDocument.getInstance();
		}
		return instance;
	}
	
	/**
	 * Private constructor forcing Singleton usage of the class.
	 */
	private HtiApiConsole(){		
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.IConsolePrintUtility#println(java.lang.String)
	 */
	public void println(String message){
		appendText(message);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.IConsolePrintUtility#println(java.lang.String, int)
	 */
	public void println(String message, int messageType){
		appendText(message);
	}
	
	/**
	 * Appends text to the end of log.
	 * @param text Appended to the end of log.
	 */
	private void appendText(String text){

		// Adding new text to log.
		log.addText(text);
		
		// Updating the changes.
		Runnable updateView = new Runnable(){
			public void run() {
				log.updateDocument();
			}							
		};
		// UI updates from background threads has to be queued
		// into UI thread in order not to cause invalid thread access
		Display.getDefault().asyncExec(updateView);
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.AbstractProductSpecificConsole#getProductConsoleName()
	 */
	protected String getProductConsoleName() {
		return ProductInfoRegistry.getConsoleWindowName();
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.AbstractProductSpecificConsole#getProductConsoleImageDescriptor()
	 */
	protected ImageDescriptor getProductConsoleImageDescriptor() {
		return null;
	}
}
