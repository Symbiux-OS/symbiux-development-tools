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
 
 
package com.nokia.s60tools.util.console;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * Declares abstract product-specific console
 * that can be subclassed and configured with
 * product name and product-specific image
 * descriptor for the console.
 * 
 * Product specific console logging utility class
 * could be implemented, for instance, as a singleton
 * class that that extends this class and provides 
 * implementations for the abstract methods.<br><br>
 * 
 * In the following is given an example of such implementation:
 * 
 * <code>
 * <pre>
 * 
 * public class MyProductConsole extends AbstractProductSpecificConsole {
 * 	
 *     static private MyProductConsole instance = null;
 *     // Product information may be stored in some common place and just
 *     // initializing information in here order to use it in the methods.
 *     static final private String PRODUCT_CONSOLE_NAME = "MyProduct Console";
 *     static final private String PRODUCT_IMAGE_KEY = "MY_PROD_CONSOLE_IMG"
 * 	
 * 	static public MyProductConsole getInstance(){
 * 	    if(instance == null ){
 * 		instance = new MyProductConsole();
 * 	    }
 * 	    return instance;
 * 	}
 * 	
 *     private MyProductConsole(){		
 *     }
 * 			
 *     protected String getProductConsoleName() {
 * 	return PRODUCT_CONSOLE_NAME;
 *     }
 * 
 *     protected ImageDescriptor getProductConsoleImageDescriptor() {
 * 	// Product-specific images are probably stored into plugin-specific image registry
 * 	return MyProductPlugin.getDefault().getImageRegistry().getDescriptor(PRODUCT_IMAGE_KEY);
 *     }
 * 
 * }
 * 
  * </pre>
 * </code>
 *  
 */
public abstract class AbstractProductSpecificConsole implements IConsolePrintUtility{
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.IConsolePrintUtility#println(java.lang.String)
	 */
	public void println(String message){
		ConsoleWindowUtility.println(getProductConsoleName(), getProductConsoleImageDescriptor(), message);
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.console.IConsolePrintUtility#println(java.lang.String, int)
	 */
	public void println(String message, int messageType){
		ConsoleWindowUtility.println(getProductConsoleName(), getProductConsoleImageDescriptor(), message, messageType);
	}
	
	/**
	 * Prints given exception's stack trace element array contents to console as error message.
	 * @param e exception for which stack trace is to be printed into the console
	 */
	public void printStackTrace(Exception e) {
		
		if(e == null){
			return;
		}
		StackTraceElement[] stackTrace = e.getStackTrace();
		
		for (int i = 0; i < stackTrace.length; i++) {
			StackTraceElement stackTraceElement = stackTrace[i];
			String traceString = stackTraceElement.toString();
			println(traceString, IConsolePrintUtility.MSG_ERROR);
		}
	}		

	/**
	 * Returns the name of the console used for a concrete product.
	 * @return Console name string.
	 */
	protected abstract String getProductConsoleName(); 
	
	/**
	 * Returns the image descriptor of the console used for a concrete product.
	 * @return Console's image descriptor.
	 */
	protected abstract ImageDescriptor getProductConsoleImageDescriptor(); 
}
