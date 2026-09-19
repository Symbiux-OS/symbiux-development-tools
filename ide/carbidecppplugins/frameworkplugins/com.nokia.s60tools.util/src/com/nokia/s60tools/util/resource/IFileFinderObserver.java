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
 
 
package com.nokia.s60tools.util.resource;

import java.io.File;
import java.util.Collection;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;

/**
 * The implementation of this interface can observe the
 * completion and progress of the file finder tool
 * execution.
 */
public interface IFileFinderObserver {
	
	
	/**
	 * Notifies that the main task is beginning.  This must only be called once
	 * on a given progress monitor instance.
	 * 
	 * This call is meant to pass on to {@link IProgressMonitor#beginTask(String, int)}.
	 * 
	 * <p>Tip: If you want to do something else within same {@link Job} you
	 * can increase the totalWork amount before giving it to {@link IProgressMonitor#beginTask(String, int)}.
	 * 
	 * @param name the name (or description) of the main task
	 * @param steps the total number of work units into which
	 *                  the main task is been subdivided. Allways returning {@link IFileFinderObserver#STEPS}.
	 *  @see {@link IProgressMonitor#beginTask(String, int)}
	 */	
	public void beginTask(String name, int steps); 
	
	/**
	 * Reports progress of a batch process like
	 * execution. It depends on the invoked command
	 * if any progress information can be gained. 
	 * @param stepsCompleted how many of steps has been completed
	 * @param taskName task name in progress.
	 */
	public void progress(int stepsCompleted, String taskName);
	

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
	 * @param filePaths Collection of file objects found.
	 */
	public void completed(int exitValue, Collection<File> files);
	
	/**
	 * Checks if the job is canceled.
	 * This call is meant to pass on to {@link IProgressMonitor#isCanceled()}.
	 * @return <code>true</code> if job is canceled, otherwise <code>false</code>.
	 */
	public boolean isCanceled();

}
