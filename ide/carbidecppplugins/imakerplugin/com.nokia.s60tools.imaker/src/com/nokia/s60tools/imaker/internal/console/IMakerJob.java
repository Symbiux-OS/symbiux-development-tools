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

import java.io.File;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.swt.program.Program;
import org.eclipse.ui.console.IHyperlink;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.IMakerConsoleLogger;
import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.StatusHandler;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreAlreadyRunningException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;

public class IMakerJob extends Job {
	private File file;
	public volatile boolean done = false;
	private IIMakerWrapper wrapper;
	private String epocroot;

	public static final int DEFAULT_THREAD_SLEEP_TIME = 20;
	public static final String BUILD_START_MESSAGE    = Messages.getString("ImageBuilder.0"); //$NON-NLS-1$
	public static final String BUILD_CMD			  = Messages.getString("ImageBuilder.1"); //$NON-NLS-1$
	public static final String BUILD_SUCCESS_MESSAGE  = Messages.getString("ImageBuilder.2"); //$NON-NLS-1$
	public static final String BUILD_FAILURE_MESSAGE  = Messages.getString("ImageBuilder.3"); //$NON-NLS-1$

	public static final Pattern VARIABLE_PATTERN1 = Pattern.compile(".*\\s*=\\s*.([\\\\/].*).");
	public static final Pattern VARIABLE_PATTERN2 = Pattern.compile(".*\\s*=\\s*.(.:.*).");
	
	public IMakerJob(String name, File file, IIMakerWrapper wrapper) {
		super(name);
		this.file = file;
		this.wrapper = wrapper;
		this.epocroot = IMakerUtils.getLocationDrive(wrapper.getTool().get(0));
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		monitor.beginTask(Messages.getString("ImageBuilder.4"), IProgressMonitor.UNKNOWN);
		IMakerConsole console = IMakerConsole.getDefault();
		console.clear();
		MessageConsoleStream infoStream = console.getNewMessageConsoleStream(IMakerConsole.MSG_INFORMATION);
		MessageConsoleStream errorStream = console.getNewMessageConsoleStream(IMakerConsole.MSG_ERROR);
		
		//print command
		infoStream.println(BUILD_START_MESSAGE);
		infoStream.print(BUILD_CMD);
		infoStream.println(wrapper.getBuildCommand(file));

		try {
			PipedInputStream pin = new PipedInputStream();
			PipedOutputStream pout = new PipedOutputStream(pin);
			
			IMakerConsoleLogger logger = new IMakerConsoleLogger(pin);
			logger.start();
						
			boolean success = false;
			success = wrapper.buildImage(file,pout);

			// Stop the thread.
			logger.done = true;
			if (success) {
				infoStream.println(BUILD_SUCCESS_MESSAGE);
			} else {
				errorStream.println(BUILD_FAILURE_MESSAGE);
			}

			pout.close();
			pin.close();
			infoStream.close();
			errorStream.close();
			Thread.sleep(IMakerKeyConstants.DEFAULT_THREAD_SLEEP_TIME);
			highlightSummary(console.getMessageConsole());
			monitor.done();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (IMakerCoreNotFoundException e) {
			StatusHandler.handle(IStatus.ERROR,Messages.getString("Error.2"),e);
		} catch (IMakerCoreExecutionException e) {
			StatusHandler.handle(IStatus.ERROR,Messages.getString("Error.1"),e);
			e.printStackTrace();
		} catch (IMakerCoreAlreadyRunningException e) {
			StatusHandler.handle(IStatus.WARNING,Messages.getString("Flashmenu.13"),e);
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return Status.OK_STATUS;
	}
	
	private void highlightSummary(MessageConsole console) {
		IDocument doc = console.getDocument();
		int numberOfLines = doc.getNumberOfLines();
		boolean begin = true;
		int startLine = 0;
		int endLine   = 0;
		for (int i = numberOfLines-1; i >= 0; i--) {
			try {
				String line = doc.get(doc.getLineOffset(i), doc.getLineLength(i));
				if(isSummaryDelimeter(line)) {
					if(!begin) {
						endLine = i;
						break;
					} else {
						startLine = i;
						begin = false;
					}
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = startLine-1; i > endLine; i--) {
			highlightLine(doc,i,console);			
		}
	}


	private void highlightLine(IDocument doc, int line, MessageConsole console) {
		try {
			int lineOffset = doc.getLineOffset(line);
			String content = doc.get(lineOffset, doc.getLineLength(line));
			Matcher matcher1 = VARIABLE_PATTERN1.matcher(content);
			Matcher matcher2 = VARIABLE_PATTERN2.matcher(content);
			if(matcher1.find()) {
				int start = matcher1.start(1);
				int end   = matcher1.end(1);
				makeLink(epocroot,doc, console, lineOffset, start, end);
			} else if(matcher2.find()) {
				int start = matcher2.start(1);
				int end   = matcher2.end(1);
				makeLink("",doc, console, lineOffset, start, end);				
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private void makeLink(String root, IDocument doc, MessageConsole console,
			int lineOffset, int start, int end) throws BadLocationException {
		int length = end-start;
		String link = doc.get(lineOffset+start, length);
		console.addHyperlink(new Link(root,link), lineOffset+start, length);
	}

	private boolean isSummaryDelimeter(String line) {
		return line.startsWith(IMakerKeyConstants.SUMMARY_DELIMETER);
	}
	
	private class Link implements IHyperlink {

		private String link;
		private String eroot;

		public Link(String eroot, String link) {
			this.link = link;
			this.eroot = eroot;
		}
//		@Override
		public void linkActivated() {
			String path = eroot+link;
			File f = new File(path);
			while(f!=null && !f.exists()) {
				f=f.getParentFile();				
			}
			if(f!=null) {
				if(f.isFile()) {
					f = f.getParentFile();
				}
				Program.launch(f.getPath());				
			}
		}

//		@Override
		public void linkEntered() {}

//		@Override
		public void linkExited() {}
		
	}	
}
