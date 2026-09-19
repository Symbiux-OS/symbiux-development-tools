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

package com.nokia.s60tools.imaker;

import java.io.File;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.s60tools.imaker.exceptions.IMakerCoreAlreadyRunningException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
/**
 * General image generation interface
 *
 */
public interface IIMakerWrapper {

	/**
	 * Function to get iMaker version information
	 * @return
	 * @throws IMakerCoreNotFoundException
	 * @throws IMakerCoreExecutionException
	 */
	public abstract String getIMakerCoreVersion() throws IMakerCoreNotFoundException,
	IMakerCoreExecutionException;

	/**
	 * Method is for querying configurations from the iMaker.
	 * 
	 * 
	 * @return A list containing the data as UIConfiguration objects.
	 *         Return is null in case of an error
	 * @throws IMakerCoreExecutionException 
	 * @throws IMakerCoreNotFoundException 
	 * @throws InterruptedException 
	 */
	public abstract List<UIConfiguration> getConfigurations(IProgressMonitor monitor, String makefile) throws IMakerCoreNotFoundException,
	IMakerCoreExecutionException;


	/**
	 * Method is for the settings of a specific makefile.
	 * 
	 * 
	 * @return A list containing the data as UIConfiguration objects.
	 *         Return is null in case of an error
	 * @throws IMakerCoreExecutionException 
	 * @throws IMakerCoreNotFoundException 
	 */
	public abstract UIConfiguration getConfiguration(List<String> params, IProgressMonitor monitor) 
	throws IMakerCoreExecutionException, 
	IMakerCoreNotFoundException;

	/**
	 * Flashes an image. Currently does nothing
	 * 
	 * @param cmdParams Command line parameters for iMaker.
	 * @return true if flashing succeeded, otherwise false.
	 */
	public abstract boolean flashImage(List<String> cmdParams);

	/**
	 * Runs iMaker with the specified command line parameters. 
	 * @param cmdParams command line parameters to iMaker. Contains product make
	 * @return if build was successful, otherwise
	 * 	                 false
	 * @throws IMakerCoreNotFoundException
	 * @throws IMakerCoreExecutionException
	 * @throws IMakerCoreAlreadyRunningException 
	 */
	public abstract boolean buildImage(List<String> cmdParams, OutputStream out) throws IMakerCoreNotFoundException,
	IMakerCoreExecutionException, IMakerCoreAlreadyRunningException;


	/**
	 * Runs iMaker core with the specified imp file. 
	 * @param impFile absolute file URI to impFile
	 * @return if build was successful, otherwise
	 * 	                 false
	 * @throws IMakerCoreNotFoundException
	 * @throws IMakerCoreExecutionException
	 * @throws IMakerCoreAlreadyRunningException 
	 */
	public abstract boolean buildImage(File impFile, OutputStream out) throws IMakerCoreNotFoundException,
	IMakerCoreExecutionException, IMakerCoreAlreadyRunningException;
	
	/**
	 * Determine whether iMaker is running or not
	 * @return
	 */
	public abstract boolean isRunning();

	/**
	 * Construct the full iMaker command that will be executed and return it as a String for convenience
	 * @param params, command line parameters for iMaker
	 * @return
	 */
	public abstract String getBuildCommand(List<String> params);

	/**
	 * Construct the full iMaker command that will be executed and return it as a String for convenience
	 * @param params, command line parameters for iMaker
	 * @return
	 */
	public abstract String getBuildCommand(File impFile);
	
	/**
	 * Get full path to iMaker tool that will be run
	 * @return
	 */
	public abstract List<String> getTool();
	
	/**
	 * Get iMaker steps for specific target
	 * @param target, iMaker target
	 * @param makefile, iMaker product makefile
	 * @param monitor, monitor object
	 * @return
	 */
	public abstract String getTargetSteps(String target,String makefile, IProgressMonitor monitor) throws IMakerCoreExecutionException;
}