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
import java.util.List;


public interface IEnvironmentManager {

	/**
	 * Sets the environment with the specified epocroot
	 * as the the active one
	 * @param epocRoot
	 */
	public abstract void setActiveEnvironment(String epocRoot);

	/**
	 * Returns the currently active environment
	 * @return
	 */
	public abstract IEnvironment getActiveEnvironment();

	/**
	 * Returns an environment by its epocRoot 
	 * @param epocRoot
	 * @return
	 */
	public abstract IEnvironment getEnvironmentByDrive(String epocRoot);

	/**
	 * Returns the list of available environments that contain iMaker tool
	 * @return
	 */
	public abstract List<IEnvironment> getEnviroments();

	/**
	 * Last run file
	 * @param file
	 */
	public abstract void setLastRun(File file);
	public abstract File getLastRun();

}