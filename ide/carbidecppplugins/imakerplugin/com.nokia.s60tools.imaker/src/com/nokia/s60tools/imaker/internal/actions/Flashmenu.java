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
package com.nokia.s60tools.imaker.internal.actions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate;

import com.nokia.s60tools.imaker.IEnvironmentManager;
import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.IMakerUtils;
import com.nokia.s60tools.imaker.Messages;
import com.nokia.s60tools.imaker.StatusHandler;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreCancelledException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;
import com.nokia.s60tools.imaker.internal.console.IMakerJob;
import com.nokia.s60tools.imaker.internal.dialogs.LaunchIMakerDialog;
import com.nokia.s60tools.imaker.internal.managers.EnvironmentManager;
import com.nokia.s60tools.imaker.internal.managers.ProjectManager;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapper;

/**
 * @version 0.1
 */
public class Flashmenu implements IWorkbenchWindowPulldownDelegate {
	/** menus items */
	private Menu menu;
	private final String MENU_ITEM_SETTINGS = Messages.getString("Flashmenu.17");

	/** private data members */
	private MenuItem openDialogItem = null;
	private Shell shell;
	private IResource selectedResource;
	
	/* (non-Javadoc)
	 * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate#getMenu(org.eclipse.swt.widgets.Control)
	 */
	public Menu getMenu(Control parent) {
		/*dispose the previously used menu*/
		if(menu!=null) {
			menu.dispose();
		}
		/* Create new menu and listeners if first call */
		MenuItem item = null;
		MenuSelectionListener msl = new MenuSelectionListener();
		menu = new Menu(parent);

		ImageDescriptor descriptor = IMakerPlugin.getImageDescriptor("icons/imakermenu16.png");
		ProjectManager pm = new ProjectManager(selectedResource.getProject());
		List<IResource> files = pm.getImakerFiles();
		for(IResource file: files) {
			IFile f = (IFile) file;
			item = new MenuItem(menu, SWT.RADIO);
			item.setText(f.getName());
			item.setData(file);
			item.setImage(descriptor.createImage());
			item.addSelectionListener(msl);
		}

		item = new MenuItem(menu, SWT.SEPARATOR);

		descriptor = IMakerPlugin.getImageDescriptor("icons/imakerdialog.png");
		openDialogItem = new MenuItem(menu, SWT.NULL);
		openDialogItem.setText(MENU_ITEM_SETTINGS); //$NON-NLS-1$
		openDialogItem.addSelectionListener(msl);
		openDialogItem.setImage(descriptor.createImage());
		return menu;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	public void dispose() {
		if(menu!=null) {
			menu.dispose();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	public void init(IWorkbenchWindow window) {
		this.shell = window.getShell();
		window.getWorkbench();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	public void run(IAction action) {
		IEnvironmentManager manager = EnvironmentManager.getInstance();
		if(selectedResource instanceof IFile) {
			IFile file = (IFile) selectedResource;
			if(file.getFileExtension().endsWith("imp")) {
				runImpFile(file.getLocation().toFile());
			}
		} else {
			if (manager.getLastRun()!=null) {
				runImpFile(manager.getLastRun());				
			} else {
				MessageDialog.openInformation(shell, "Unable To Launch iMaker", "The selection cannot be launched, please select an iMaker file (.imp) " +
				"or\n create new lauch using Open iMaker Dialog action... from the pulldown menu.");
			}
		}
	}

	private void runImpFile(File file) {
		IEnvironmentManager manager = EnvironmentManager.getInstance();
		manager.setLastRun(file);
		List<String> imaker = IMakerUtils.getImakerTool(getSelectionRoot());		
		IIMakerWrapper wrapper = new IMakerWrapper(imaker);
		IMakerJob job = new IMakerJob("Creating image", file ,wrapper);
		job.setPriority(Job.LONG);
		job.setRule(IMakerPlugin.getDefault().getImakerRule());
		job.schedule();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object elem = ss.getFirstElement();
			if(elem instanceof IResource) {
				selectedResource = ((IResource)elem).getProject();
			}
		} else {
			selectedResource = null;
		}
	}

	private String getSelectionRoot() {
		return IMakerUtils.getProjectRootLocation(selectedResource);
	}

	/**
	 * internal selectionlistener for menu items. 
	 */
	private class MenuSelectionListener implements SelectionListener {

		public void widgetDefaultSelected(SelectionEvent e) {
			widgetSelected(e);
		}

		public void widgetSelected(SelectionEvent e) {
			MenuItem selection = (MenuItem)e.widget;
			if(selection==openDialogItem) {
				try {
					ProjectManager projectManager = new ProjectManager(selectedResource.getProject());
					IEnvironmentManager manager = IMakerPlugin.getEnvironmentManager();
					manager.setActiveEnvironment(getSelectionRoot());

					LaunchIMakerDialog dialog = new LaunchIMakerDialog(shell,
							projectManager,manager);
					try {
						manager.getActiveEnvironment().load();
					} catch (InvocationTargetException ite) {
						Throwable cause = ite.getCause();
						if(cause instanceof IMakerCoreCancelledException) {
							return;
						} else if(cause instanceof IMakerCoreExecutionException) {
							StatusHandler.handle(IStatus.ERROR,Messages.getString("Error.1"),cause);
						} else if(cause instanceof IMakerCoreNotFoundException) {
							StatusHandler.handle(IStatus.ERROR,Messages.getString("Error.2"),cause);
						} else {
							StatusHandler.handle(IStatus.ERROR,cause.getMessage(),cause);							
						}
						return;
					}
					int returnValue = dialog.open();
					if(returnValue == IDialogConstants.OK_ID) {
						String path = dialog.getFilePath();
						ImakerProperties run = manager.getActiveEnvironment().getRunProperties();
						if(!path.equals(ProjectManager.NEW_ITEM)) {
							IFile file = (IFile)projectManager.getImakerFile(new Path(path));
							runImpFile(file.getLocation().toFile());
						} else {
							try {
								File file = File.createTempFile("temp", ".imp");
								run.saveToFile(file);
								runImpFile(file);
							} catch (IOException e1) {
								e1.printStackTrace();
							}
						}
					}
				} catch(NullPointerException ne) {
					ne.printStackTrace();
				}
			} else {
				IFile file = (IFile) selection.getData();
				runImpFile(file.getLocation().toFile());
			}
		}
	}
}
