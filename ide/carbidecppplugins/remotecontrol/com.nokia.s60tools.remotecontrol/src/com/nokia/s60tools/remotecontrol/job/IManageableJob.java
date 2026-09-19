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

package com.nokia.s60tools.remotecontrol.job;

/**
 * Jobs that implement this interface can be asked to 
 * shutdown themselves during their execution. For example,
 * when a plugin is disposed it is a good idea to request
 * for all the managed jobs to also interrupt their
 * execution.
 */
public interface IManageableJob {
	
	/**
	 * Called when job needs to be shutdown
	 */
	public void forcedShutdown();
	
	/**
	 * Called when job needs to be canceled.
	 */
	public void cancelJob();
}
