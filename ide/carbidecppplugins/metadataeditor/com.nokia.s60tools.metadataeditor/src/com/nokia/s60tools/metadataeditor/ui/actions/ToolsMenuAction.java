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

 
 
package com.nokia.s60tools.metadataeditor.ui.actions;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import com.nokia.s60tools.metadataeditor.MetadataEditorActivator;
import com.nokia.s60tools.metadataeditor.common.ProductInfoRegistry;
import com.nokia.s60tools.metadataeditor.util.MetadataEditorConsole;
import com.nokia.s60tools.metadataeditor.wizards.APIMetadataFileNewWizard;
import com.nokia.s60tools.util.console.IConsolePrintUtility;

/**
 * Action for menu action "API Metadata file". Launches {@link APIMetadataFileNewWizard} wizard.
 */
public class ToolsMenuAction implements IWorkbenchWindowActionDelegate  {
	
	/**
	 * Reference to workbench window
	 */
	private IWorkbenchWindow window;
	
	/**
	 * Constructor.
	 */
	public ToolsMenuAction(){			
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.window = window;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		try {
			
			IProject projects [] = ResourcesPlugin.getWorkspace().getRoot().getProjects();
			if(projects.length < 1){
				showErrorDialog( ProductInfoRegistry.getProductName() + " Error", 
						"No existing projects in workspace! Please create a project before creating files!");
				return;
			}

			
    		APIMetadataFileNewWizard wiz = new APIMetadataFileNewWizard();
    		IWorkbench wp = window.getWorkbench();
    		
    		ISelectionService selser = window.getSelectionService();
    		ISelection sel = selser.getSelection(); 
    		IStructuredSelection structSel = null;
    		//pre-select selection if made in resource tree
    		if(sel instanceof IStructuredSelection){
    			structSel = (IStructuredSelection)sel;
    		}

    		
    		wiz.init(wp, structSel);
    		//Opening new Creator script file dialog.
  	      	WizardDialog dialog = new WizardDialog
	         (wp.getActiveWorkbenchWindow().getShell(),wiz);
    		dialog.open();

			
		} catch (Exception e) {
			e.printStackTrace();
			MetadataEditorConsole.getInstance().println(e.getMessage(), 
					 IConsolePrintUtility.MSG_ERROR);
		}
	}
	
	/**
	 * Show an error dialog
	 * @param title
	 * @param message
	 * @param errors
	 */
	private void showErrorDialog(String title, String message) {
		Shell sh = MetadataEditorActivator.getCurrentlyActiveWbWindowShell();
		MessageDialog.openError(sh, title, message);
		
	}	

	/* (non-Javadoc)
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	public void selectionChanged(IAction action, ISelection selection) {
	}

}
