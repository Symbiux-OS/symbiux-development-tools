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

/**
 * The implementation of this interface can observe the
 * completion and progress of the command line tool
 * execution.
 */
public interface ICmdLineCommandExecutorObserver {
	
	/**
	 * Reports progress of a batch process like
	 * execution. It depends on the invoked command
	 * if any progress information can be gained. 
	 * @param percentage Progress percentage.
	 */
	public void progress(int percentage);
	
	/**
	 * Allows observer to get hands on the created process.
	 * @param proc Process object that has been created for running
	 * 			   the command currently under execution.
	 */
	public void processCreated(Process proc);

	/**
	 * Informs the observer that the execution of the currently
	 * executed command was interrupted due to some reason.
	 * @param reasonMsg Message describing the reason. 
	 */
	public void interrupted(String reasonMsg);
	
	/**
	 * Informs about the completion of the execution
	 * of a single command.
	 * @param exitValue Exit value of the executed command.
	 */
	public void completed(int exitValue);
}
