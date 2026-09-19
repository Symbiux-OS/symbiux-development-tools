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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * Offers service for writing of messages to the
 * to the name console window in Console view.
 * Tree message types with distinct fonts that can 
 * be printed to the console output are provided:
 * - normal message (default console font)
 * - warning message (default console font + italics)
 * - error message  (default console font + italics + bold)
 */
public class ConsoleWindowUtility {

	/**
	 * Gets message console stream for given console window name. Creates a new console
	 * if there is not already console window existing with that name.
	 * @param consoleWindowName Name of the console window to print into.
	 * @param consoleWindowImageDescriptor Image descriptor for the image that is shown in drop-down
	 *                                     menu of console selection in case there are several open
	 *                                     console windows inside Console view.
	 * @return Message console stream to print.
	 */
	private static MessageConsoleStream getConsoleStream(String consoleWindowName,
														 ImageDescriptor consoleWindowImageDescriptor
			                                             ) {		
	      ConsolePlugin plugin = ConsolePlugin.getDefault();
	      IConsoleManager conMan = plugin.getConsoleManager();	      
	      IConsole[] consoleArray = conMan.getConsoles();
	      
	      for (int i = 0; i < consoleArray.length; i++){
			 if (consoleWindowName.equals(consoleArray[i].getName())){
				 MessageConsole console = (MessageConsole) consoleArray[i];
				 return console.newMessageStream();		        	 
			 }	    	  
	      }

	      MessageConsole msgConsole = new MessageConsole( 
	    		  	                consoleWindowName,
	    		  	                consoleWindowImageDescriptor,
					    			false
										    			);
	      conMan.addConsoles(new IConsole[]{msgConsole});
	      return msgConsole.newMessageStream();
	   }
	
	/**
	 * Gets font style bits for the given message type.
	 * @param messageType Message type.
	 * @return Font style bits.
	 * @see IConsolePrintUtility#MSG_NORMAL
	 * @see IConsolePrintUtility#MSG_WARNING
	 * @see IConsolePrintUtility#MSG_ERROR
	 */
	private static int getFontStyleBits(int messageType){
		
		int fontStyleBits;
		
		switch (messageType) {
		
			case IConsolePrintUtility.MSG_WARNING:
				fontStyleBits = SWT.ITALIC;			
				break;
	
			case IConsolePrintUtility.MSG_ERROR:
				fontStyleBits = SWT.ITALIC | SWT.BOLD;			
				break;
	
			default:
				fontStyleBits = SWT.NORMAL;
				break;
		}
		
		return fontStyleBits;
	}
	
	/**
	 * Prints message to the given console window with default message type.
	 * @param consoleWindowName Name of the console window to print into.
	 * @param consoleWindowImageDescriptor Image descriptor for the image that is shown in drop-down
	 *                                     menu of console selection in case there are several open
	 *                                     console windows inside Console view.
	 * @param message					   Message to be printed.
	 * @see IConsolePrintUtility#MSG_NORMAL
	 */
	public static void println(String consoleWindowName, ImageDescriptor consoleWindowImageDescriptor, String message){
		println(consoleWindowName, consoleWindowImageDescriptor, message, IConsolePrintUtility.MSG_NORMAL);
	}

	/**
	 * Prints message to the given console window.
	 * @param consoleWindowName Name of the console window to print into.
	 * @param consoleWindowImageDescriptor Image descriptor for the image that is shown in drop-down
	 *                                     menu of console selection in case there are several open
	 *                                     console windows inside Console view.
	 * @param message					   Message to be printed.
	 * @param messageType				   The type of the message.
	 * @see IConsolePrintUtility#MSG_NORMAL
	 * @see IConsolePrintUtility#MSG_WARNING
	 * @see IConsolePrintUtility#MSG_ERROR
	 */
	public static void println(String consoleWindowName, ImageDescriptor consoleWindowImageDescriptor, String message, int messageType){
		final MessageConsoleStream consoleStream = getConsoleStream(consoleWindowName, consoleWindowImageDescriptor);
		final int fontStyleBits = getFontStyleBits(messageType);		
		final String msg = message;
		
		// Runnable implementing the actual printing to console
		Runnable printToConsoleRunnable = new Runnable(){
			public void run(){
				printlnImpl(msg, consoleStream, fontStyleBits);
			}
		};
		
		// Queuing the runnable for the execution in UI thread
		Display.getDefault().asyncExec(printToConsoleRunnable);        		
	}

	/**
	 * This method is used to do println in away that allows also access 
	 * to console from non-UI thread.
	 * @param message		 Message to be printed.
	 * @param consoleStream	 Console stream to print into		
	 * @param fontStyleBits  Style bits determining the used font style.
	 */
	private static void printlnImpl(String message, MessageConsoleStream consoleStream, int fontStyleBits) {
		consoleStream.setFontStyle(fontStyleBits);
		consoleStream.println(message);
	}
	
}
