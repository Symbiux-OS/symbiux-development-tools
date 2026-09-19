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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

public class CancelObserver extends Thread {
	private static final long WAIT_PERIOD = 100;
	public volatile boolean done = false;
	private IProgressMonitor monitor;
//	private Job job;
	public CancelObserver(Job builder, IProgressMonitor mon) {
//		this.job = builder;
		this.monitor = mon;
	}

	@Override
	public void run() {
		while(!done) {
			if(monitor.isCanceled()) {
//				IMakerWrapper.getInstance().cancel();
//				job.cancel();
				Display display = PlatformUI.getWorkbench().getDisplay();
				display.asyncExec(new Runnable() {

					public void run() {
						Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
						MessageDialog.openInformation(shell, "Canceling not supported",
						"Cancelling of image creation is not allowed!");
						
					}
					
				});
				done=true;
			}
			try {
				sleep(WAIT_PERIOD);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
