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

package com.nokia.s60tools.hticonnection.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.nokia.s60tools.hticonnection.ui.views.main.MainView;
import com.nokia.s60tools.hticonnection.util.HtiApiConsole;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Activates/creates main View.
 */
public class MainViewOpenAction implements IWorkbenchWindowActionDelegate {
	
	/**
	 * Constructor.
	 */
	public MainViewOpenAction(){			
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		try {
			// We only want to activate/create the view and 
			// do not need to use the instance after it is returned.
			MainView.getViewInstance(true);

		} catch (Exception e) {
			e.printStackTrace();
			HtiApiConsole.getInstance().println(e.getMessage(), 
					 IConsolePrintUtility.MSG_ERROR);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		// Not needed now, but must be implemented anyway	
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		// Not needed now, but must be implemented anyway		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		// Not needed now, but must be implemented anyway
	}

}
