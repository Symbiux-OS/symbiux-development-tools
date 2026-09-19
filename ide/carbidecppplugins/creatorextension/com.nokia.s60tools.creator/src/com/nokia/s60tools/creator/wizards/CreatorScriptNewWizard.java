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
package com.nokia.s60tools.creator.wizards;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWizard;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;

import com.nokia.s60tools.creator.CreatorActivator;
import com.nokia.s60tools.creator.xml.CreatorXML;
import com.nokia.s60tools.util.resource.FileUtils;


/**
 * This is a new wizard for Creator Script. Its role is to create a new file 
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace 
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "creatorxml". 
 */

public class CreatorScriptNewWizard extends Wizard implements INewWizard {
	private CreatorScriptNewWizardPage page;
	private ISelection selection;

	/**
	 * Constructor for CreatorScriptNewWizard.
	 * @throws Exception 
	 */
	public CreatorScriptNewWizard() throws Exception {
		super();
		
		IProject projects [] = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		if(projects.length < 1){
			String title = "Creator Script Editor Error" ; 
			String msg = "No existing projects in workspace! Please create a project before creating scripts!";
			Shell sh = CreatorActivator.getCurrentlyActiveWbWindowShell();
			MessageDialog.openError(sh, title, msg);
			//This is need to be done because wizard can be launched also from File -> New -> Other...
			throw new Exception(msg);
		}
				
		
		setNeedsProgressMonitor(true);
	}
	
	/**
	 * Adding the page to the wizard.
	 */

	public void addPages() {
		page = new CreatorScriptNewWizardPage(selection);
		addPage(page);
	}

	/**
	 * This method is called when 'Finish' button is pressed in
	 * the wizard. We will create an operation and run it
	 * using wizard as execution context.
	 */
	public boolean performFinish() {
		final String containerName = page.getContainerName();
		final String fileName = page.getFileName();
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException {
				try {
					doFinish(containerName, fileName, monitor);
				} catch (CoreException e) {
					throw new InvocationTargetException(e);
				} finally {
					monitor.done();
				}
			}
		};
		try {
			getContainer().run(true, false, op);
		} catch (InterruptedException e) {
			return false;
		} catch (InvocationTargetException e) {
			Throwable realException = e.getTargetException();
			MessageDialog.openError(getShell(), "Error", realException.getMessage());
			return false;
		}
		return true;
	}
	
	/**
	 * The worker method. It will find the container, create the
	 * file if missing or just replace its contents, and open
	 * the editor on the newly created file.
	 */

	private void doFinish(
		String containerName,
		String fileName,
		IProgressMonitor monitor)
		throws CoreException {
		// create a sample file
		monitor.beginTask("Creating " + fileName, 2);
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		if (!resource.exists() || !(resource instanceof IContainer)) {
			throwCoreException("Container \"" + containerName + "\" does not exist.");
		}
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		//File should not be possible to exist, because wizard page is chekking that
		//Not creating file really in here, save file from editor will create file		
		try {
			InputStream stream = openContentStream(fileName);
			file.create(stream, true, monitor);
			file.setCharset(FileUtils.ENCODING_TYPE_UTF_8, monitor);
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		monitor.worked(1);
		monitor.setTaskName("Opening file for editing...");
		getShell().getDisplay().asyncExec(new Runnable() {
			public void run() {
				IWorkbenchPage page =
					PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
				try {
					IDE.openEditor(page, file, true);
				} catch (PartInitException e) {
				}
			}
		});
		monitor.worked(1);
	}
	
	/**
	 * We will initialize file contents with a sample text.
	 * @throws UnsupportedEncodingException 
	 */
	private InputStream openContentStream(String fileName) throws UnsupportedEncodingException {
		String contents =  CreatorXML.NEW_API_CREATOR_FILE_UID + fileName ;
		return new ByteArrayInputStream(contents.getBytes(FileUtils.ENCODING_TYPE_UTF_8));
	}


	/**
	 * set image to wizard
	 */
	private void setImage() {
		setDefaultPageImageDescriptor(CreatorActivator.getWizardImage());
	}
	
	/**
	 * Throws exception with error message
	 * @param message
	 * @throws CoreException
	 */
	private void throwCoreException(String message) throws CoreException {
		IStatus status =
			new Status(IStatus.ERROR, CreatorActivator.PLUGIN_ID, IStatus.OK, message, null);
		throw new CoreException(status);
	}

	/**
	 * We will accept the selection in the workbench to see if
	 * we can initialize from it.
	 * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		
		this.selection = selection;
		setImage();
		setWindowTitle("New Creator Script File");
	}
}