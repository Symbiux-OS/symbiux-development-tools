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

import java.util.List;

import org.eclipse.core.runtime.jobs.Job;


/**
 * The interface to be implemented by 
 * each command executors.
 */
public interface ICmdLineCommandExecutor {

	/**
	 * Runs the given command. Uses default stdout and
	 * stderr readers to handle program output.
	 *
	 * In order to get information about command processing 
	 * the client has to implement <code>ICmdLineCommandExecutorObserver</code> interface.
	 * 
	 * @param cmdLineArray Command line in an array form.
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver
	 */
	public void runCommand(String[] cmdLineArray);
	
	/**
	 * Runs the given command. Uses default stdout and
	 * stderr readers to handle program output.
	 *
	 * In order to get information about command processing 
	 * the client has to implement <code>ICmdLineCommandExecutorObserver</code> interface.
	 * 
	 * @param cmdLineArray Command line in an array form.
	 * @param path Path to directory where to run this command
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver
	 */
	public void runCommand(String[] cmdLineArray, String path);
	
	/**
	 * Runs the given command. Uses custom stdout and
	 * stderr readers given as parameters for handling
	 * the program output.
	 *
	 * In order to get information about command processing 
	 * the client has to implement <code>ICmdLineCommandExecutorObserver</code> interface.
	 * 
	 * @param cmdLineArrayList List of command lines in a string array form.
	 * @param stdOutReader Reference to custom stdout reader, or null if the usage 
	 * 					   of the default reader is what is wanted.
	 * @param stdErrReader Reference to custom stderr reader, or null if the usage 
	 * 					   of the default reader is what is wanted.
	 * @param jobContext   Job object under which the command will be executed.
	 *                     If there is no job context existing, the parameter
	 *                     can be set to <code>null</code>.
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver
	 */
	public void runCommand(String[] cmdLineArray, 
			 		  ICustomLineReader stdOutReader,
			 		  ICustomLineReader stdErrReader,
			 		  Job jobContext
			 		  );
	
	/**
	 * Runs the given list of commands. Uses default stdout and
	 * stderr readers to handle program output.
	 *
	 * In order to get information about command processing 
	 * the client has to implement <code>ICmdLineCommandExecutorObserver2</code> interface.
	 * 
	 * @param cmdLineArrayList List of command lines in a string array form.
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver2
	 */
	public void runCommand(List<String[]> cmdLineArrayList);
	
	/**
	 * Runs the given command. Uses custom stdout and
	 * stderr readers given as parameters for handling
	 * the program output.
	 *
	 * In order to get information about command processing 
	 * the client has to implement <code>ICmdLineCommandExecutorObserver2</code> interface.
	 * 
	 * @param cmdLineArrayList List of command lines in a string array form.
	 * @param stdOutReader Reference to custom stdout reader, or null if the usage 
	 * 					   of the default reader is what is wanted.
	 * @param stdErrReader Reference to custom stderr reader, or null if the usage 
	 * 					   of the default reader is what is wanted.
	 * @param jobContext   Job object under which the command will be executed.
	 *                     If there is no job context existing, the parameter
	 *                     can be set to <code>null</code>.
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver2
	 */
	public void runCommand(List<String[]> cmdLineArrayList, 
			 		  ICustomLineReader stdOutReader,
			 		  ICustomLineReader stdErrReader,
			 		  Job jobContext
			 		  );

	/**
	 * For a group of commands stops the execution
	 * of commands coming right after the currently executing 
	 * command has finished, if this method has been called.
	 * There is no way to reverse the cancellation after this
	 * this method has been called.
	 */
	public void cancelCommandExecutions();
	
	/**
	 * Runs a single command and wait for it's completion.
	 * @param cmdLineArray Command line in an array form.
	 * @param stdOutReader Reference to custom stdout reader, or null if the usage 
	 * 					   of the default reader is what is wanted.
	 * @param stdErrReader Reference to custom stderr reader, or null if the usage 
	 * 					   of the default reader is what is wanted.
	 * @return exit value of the command, 0 indicates normal termination.
	 * @throws InterruptedException if the current thread is interrupted by another thread while it is waiting.
	 */

	public int runSyncCommand(String[] cmdLineArray, ICustomLineReader stdOutReader, ICustomLineReader stdErrReader) throws InterruptedException;
	
	/**
	 * Runs a single command and returns process that is created.
	 * After returning process it waits until process is finished and informs to callback.
	 * Uses default stdout and stderr readers to handle program output.
	 *
	 * In order to get information about command processing 
	 * the client has to implement <code>ICmdLineCommandExecutorObserver</code> interface.
	 * 
	 * @param cmdLineArray Command line in an array form.
	 * @param path Path to directory where to run this command
	 * @return Process that was created or null if starting the process failed.
	 */
	public Process runAsyncCommand(String[] cmdLineArray, String path);
}
