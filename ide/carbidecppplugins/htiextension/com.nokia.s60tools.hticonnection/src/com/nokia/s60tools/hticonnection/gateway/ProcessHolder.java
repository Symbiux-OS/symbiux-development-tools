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

package com.nokia.s60tools.hticonnection.gateway;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import com.nokia.s60tools.hticonnection.actions.OpenPreferencePageAction;
import com.nokia.s60tools.hticonnection.core.HtiConnection;
import com.nokia.s60tools.hticonnection.core.HtiConnection.ConnectionStatus;
import com.nokia.s60tools.hticonnection.preferences.HtiApiPreferencePage;
import com.nokia.s60tools.hticonnection.resources.Messages;
import com.nokia.s60tools.hticonnection.ui.dialogs.ErrorDialogWithHelp;
import com.nokia.s60tools.hticonnection.ui.views.main.MainView;
import com.nokia.s60tools.hticonnection.util.HtiApiConsole;
import com.nokia.s60tools.util.cmdline.CmdLineCommandExecutorFactory;
import com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutor;
import com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver;
import com.nokia.s60tools.util.cmdline.UnsupportedOSException;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * contains and controls datagateway process.
 */
public class ProcessHolder implements ICmdLineCommandExecutorObserver {
	
	/**
	 * Datagateway.exe process that is running.
	 */
	private Process process;
	/**
	 * Executor that is used to launch external programs.
	 */
	private ICmdLineCommandExecutor cmdLineExecutor;
	/**
	 * True if datagateway is currently shutting down.
	 * Used to keep track of short period when process is alive,
	 * but can not get more commands.
	 */
	private boolean isShutdowning = false;

	/**
	 * Constructor.
	 * @throws UnsupportedOSException Thrown if Operating system is not supported.
	 */
	public ProcessHolder() throws UnsupportedOSException {
		cmdLineExecutor = CmdLineCommandExecutorFactory.CreateOsDependentCommandLineExecutor(
				this, HtiApiConsole.getInstance());
	}
	
	/**
	 * Class that can be used to notify user about unexpected datagateway shutdown.
	 */
	private class DataGatewayShutdownRunnable implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			// Creating a dialog to notify user about problem.
			ErrorDialogWithHelp msgBox = new ErrorDialogWithHelp(
					Messages.getString("ProcessHolder.Datagateway_Unexcept_Shutdown_Change_Settings_ConsoleMsg"),  //$NON-NLS-1$
					SWT.YES | SWT.NO);
			int result = msgBox.open();

			// Opening preferences if Yes is selected.
			if(result == SWT.YES){
				OpenPreferencePageAction openPreferencesAction = new OpenPreferencePageAction();
				openPreferencesAction.run();
			}
		}
	}

	/**
	 * Runs asynchronous command.
	 * @param cmd Command to be run.
	 * @param path Path where command is run.
	 * @return True if command was started successfully. False otherwise.
	 */
	public boolean runAsyncCommand(String[] cmd, String path) {
		process = cmdLineExecutor.runAsyncCommand(cmd, path);
		return (process == null) ? false : true;
	}

	/**
	 * Stops this process.
	 */
	public void stopProcess() {
		if(process != null){
			isShutdowning = true;
			process.destroy();
			try {
				process.waitFor();
				process = null;
			} catch (InterruptedException e) {
				HtiApiConsole.getInstance().println(
						Messages.getString("ProcessHolder.Stop_Datagateway_Failed_ConsoleMsg"), //$NON-NLS-1$
						HtiApiConsole.MSG_ERROR);
			}
		}
	}

	/**
	 * Checks if gateway is ready for use.
	 * @return Returns true if gateway is ready for use.
	 */
	public boolean isReady() {
		if (process != null && !isShutdowning) {
			try {
				// Throws IllegalThreadStateException if process is running
				process.exitValue();
				// Process is terminated
				return false;
			} catch (IllegalThreadStateException e) {
				// Process is running
				return true;
			}
		} else {
			// Process has not been created
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#progress(int)
	 */
	public void progress(int percentage) {
		// not implemented
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#interrupted(java.lang.String)
	 */
	public void interrupted(String reasonMsg) {
		this.process = null;
	}

	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#processCreated(java.lang.Process)
	 */
	public void processCreated(Process proc) {
		// No need to implement. Process is got from runAsyncCommand.
	}
	
	/* (non-Javadoc)
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#completed(int)
	 */
	public void completed(int exitValue) {
		if(!isShutdowning) {
			// Service wasn't shutdown intentionally from HTI Connection. Handling unexpected situation.
			
			// Process has been crashed and is not running anymore.
			this.process = null;
			
			// Setting service as shutdown, until gateway is started again.
			HtiConnection.getInstance().setConnectionStatus(ConnectionStatus.SHUTDOWN);
			// Opening the mainview instance.
			MainView.openMainViewAsync(true);

			// Creating and showing dialog for user.
			if(!HtiApiPreferencePage.isCreated()){
				Runnable dialogRunnable = new DataGatewayShutdownRunnable();
				Display.getDefault().asyncExec(dialogRunnable);
			}

			// Datagateway was shutdown.
			String msg = Messages.getString("ProcessHolder.Datagateway_Unexcept_Shutdown_ConsoleMsg"); //$NON-NLS-1$
			HtiApiConsole.getInstance().println(msg, IConsolePrintUtility.MSG_ERROR);
		}
	}
}
