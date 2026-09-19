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
package com.nokia.s60tools.util.cmdline;

/**
 * Extends <code>ICmdLineCommandExecutorObserver</code>
 * interface for allowing to know when a whole group of commands
 * to be executed has been executed.
 */
public interface ICmdLineCommandExecutorObserver2 extends ICmdLineCommandExecutorObserver {	

	/**
	 * Informs about the completion of the execution
	 * of a single command.
	 * This will be called for <code>ICmdLineCommandExecutorObserver2</code>
	 * observer instances instead of <code>complete(int exitVal)</code>.
	 * @param proc Process object that has been completed.
	 * @see com.nokia.s60tools.util.cmdline.ICmdLineCommandExecutorObserver#completed
	 */
	public void completed(Process proc);
	
	/**
	 * Informs about the completion of the execution
	 * of all commands given to the executor.
	 */
	public void allCommandsExecuted();
}
