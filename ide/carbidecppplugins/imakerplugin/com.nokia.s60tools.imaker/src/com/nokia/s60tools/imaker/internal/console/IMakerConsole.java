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

package com.nokia.s60tools.imaker.internal.console;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleConstants;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.nokia.s60tools.imaker.IMakerPlugin;

public class IMakerConsole {
	
	private static final String IMAKER_CONSOLE_NAME = "iMaker";
	private static IMakerConsole fDefault = null;
	private MessageConsole fMessageConsole = null;
	
	public static final int MSG_INFORMATION = 1;
	public static final int MSG_ERROR = 2;
	public static final int MSG_WARNING = 3;
		
	private IMakerConsole() {}
	
	public static IMakerConsole getDefault() {
		if(fDefault==null) {
			fDefault = new IMakerConsole();
		}
		return fDefault;
	}
		
	public void println(String msg, int msgKind) {
		if( msg == null ) return;
		
		/* if console-view in Java-perspective is not active, then show it and
		 * then display the message in the console attached to it */		
		if( !displayConsoleView() )
		{
			/*If an exception occurs while displaying in the console, then just diplay atleast the same in a message-box */
			MessageDialog.openError(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error", msg);
			return;
		}
		
		/* display message on console */	
		getNewMessageConsoleStream(msgKind).println(msg);				
	}
	
	public void clear() {		
		final IDocument document = getMessageConsole().getDocument();
		if (document != null) {
			PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
				
//				@Override
				public void run() {
					document.set("");
				}
			});
		}			
	}	
		
	private boolean displayConsoleView() {
		try
		{
			IWorkbenchWindow activeWorkbenchWindow = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			if( activeWorkbenchWindow != null )
			{
				IWorkbenchPage activePage = activeWorkbenchWindow.getActivePage();
				if( activePage != null )
					activePage.showView(IConsoleConstants.ID_CONSOLE_VIEW, null, IWorkbenchPage.VIEW_VISIBLE);
			}
			
		} catch (PartInitException partEx) {			
			return false;
		}
		
		return true;
	}
	
	public MessageConsoleStream getNewMessageConsoleStream(final int kind) {		
		int swtColorId = SWT.COLOR_DARK_GREEN;
		
		switch (kind) {
			case MSG_INFORMATION:
				swtColorId = SWT.COLOR_DARK_GREEN;				
				break;
			case MSG_ERROR:
				swtColorId = SWT.COLOR_DARK_MAGENTA;
				break;
			case MSG_WARNING:
				swtColorId = SWT.COLOR_DARK_BLUE;
				break;
			default:				
		}	
		final int colorID = swtColorId;
		final MessageConsoleStream msgConsoleStream = getMessageConsole().newMessageStream();
		final Display display = PlatformUI.getWorkbench().getDisplay();
		display.syncExec(new Runnable() {
			
//			@Override
			public void run() {
				msgConsoleStream.setColor(display.getSystemColor(colorID));
			}
		});
		return msgConsoleStream;
	}
	
	public MessageConsole getMessageConsole() {
		if( fMessageConsole == null )
			createMessageConsoleStream();		
		return fMessageConsole;
	}
		
	private void createMessageConsoleStream() {
		fMessageConsole = new MessageConsole(getProductConsoleName(), getProductConsoleImageDescriptor()); 
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ fMessageConsole });
	}	
	
	private ImageDescriptor getProductConsoleImageDescriptor() {
		return IMakerPlugin.getImageDescriptor("icons/imakerplugin_icon.png");
	}

	private String getProductConsoleName() {
		return IMAKER_CONSOLE_NAME;
	}
}
