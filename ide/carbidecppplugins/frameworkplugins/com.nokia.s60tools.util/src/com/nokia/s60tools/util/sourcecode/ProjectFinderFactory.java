/*
* Copyright (c) 2008 Nokia Corporation and/or its subsidiary(-ies). 
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

package com.nokia.s60tools.util.sourcecode;

import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.s60tools.util.console.IConsolePrintUtility;
import com.nokia.s60tools.util.internal.Messages;
import com.nokia.s60tools.util.sourcecode.internal.ProjectFinder;

/**
 * Factory class for creating {@link IProjectFinder} class implementations.
 * Real implementations is not allowed to use directly, 
 * but only through {@link IProjectFinder} interface created with this Factory.
 */
public class ProjectFinderFactory {
	
	/**
	 * Creates an default implementation for {@link IProjectFinder}
	 * @return {@link IProjectFinder}
	 * @param printUtility Console utility used to report error, warnings, and info message.
	 *                     If set to <code>null</code> no reporting is done.
	 * @param monitor Progress monitor used to check cancel status of the calling job.
	 *                Cannot be <code>null</code>.
	 * @return New project finder utility instance.
	 */
	public static IProjectFinder createProjectFinder(IConsolePrintUtility printUtility, IProgressMonitor monitor){
		if(monitor == null){
			throw new IllegalArgumentException(Messages.getString("ProjectFinderFactory.Monitor_ObjectNull_ErrMsg")); //$NON-NLS-1$
		}
		return new ProjectFinder(printUtility, monitor);

	}	
	
}
