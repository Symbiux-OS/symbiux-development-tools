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
package com.nokia.s60tools.symbianfoundationtemplates.ui.wizards;

import java.io.ByteArrayInputStream;
import java.util.AbstractList;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;

import com.nokia.s60tools.symbianfoundationtemplates.SymbianFoundationTemplates;
import com.nokia.s60tools.symbianfoundationtemplates.engine.TransformedTemplate;
import com.nokia.s60tools.symbianfoundationtemplates.resources.Messages;

/**
 * Base template wizard. Extend this to create new template wizards
 * based on different template engines.
 *
 */
public abstract class BaseTemplateWizard extends Wizard implements INewWizard {
	public boolean canFinish() {
		for(BaseTemplateWizardPage page : getTemplatePages())
			if(page.getStatus().matches(IStatus.ERROR))
				return false;
		
		return true;
	}
	
	@Override
	public boolean performFinish() {
		AbstractList<IFile> files = new ArrayList<IFile>();
		
		// Write the transformed templates into real files
		for(TransformedTemplate transformed : getTransformedTemplates()) {
			IFile file = transformed.getFile();

			ByteArrayInputStream in = new ByteArrayInputStream(transformed.getTransformed().getBytes());

			try {	
				// If file already exists, delete it.
				// This is ok because the wizards that don't want to overwrite
				// files will check this themselves...
				if(file.exists())
					file.delete(true, true, new NullProgressMonitor());
				
				file.create(in, true, new NullProgressMonitor());
			} catch(CoreException ce) {
				SymbianFoundationTemplates.getDefault().showErrorDialog(
						Messages.getString("WizardErrorGeneratingTransformed") + file.getName(), //$NON-NLS-1$
						ce.getMessage());
				file = null;
			} finally {
				if(file != null)
					files.add(file);
				else
					return false;
			}
		}
		
		// Open the real files in different editors
		for(IFile file : files) {
			IWorkbenchWindow window = PlatformUI.getWorkbench().getActiveWorkbenchWindow();
			
			if(window == null) {
				SymbianFoundationTemplates.getDefault().showErrorDialog(
						Messages.getString("WizardErrorOpeningTransformed") + file.getName(), //$NON-NLS-1$
						Messages.getString("WizardErrorReasonWorkbenchWindowNull")); //$NON-NLS-1$
				
				return false;
			}
		
			IWorkbenchPage page = window.getActivePage();
			
			if(page == null) {
				SymbianFoundationTemplates.getDefault().showErrorDialog(
						Messages.getString("WizardErrorOpeningTransformed") + file.getName(), //$NON-NLS-1$
						Messages.getString("WizardErrorReasonWorkbenchPageNull")); //$NON-NLS-1$
				
				return false;
			}
			
			IEditorDescriptor desc = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
			
			if(desc == null) {
				SymbianFoundationTemplates.getDefault().showErrorDialog("Cannot open file '" + file + "' in editor!", "Default editor for file type could not be found");
				return true;
			}
			
			try {
				page.openEditor(new FileEditorInput(file), desc.getId());
			} catch(PartInitException pie) {
				SymbianFoundationTemplates.getDefault().showErrorDialog(
						Messages.getString("WizardErrorOpeningTransformed") + file.getName(), //$NON-NLS-1$
						pie.getMessage());
			
				return false;
			}
		}
		
		savePreferencesToStore();
		
		return true;
	}
	
	/**
	 * Get template wizard pages in this wizard.
	 * 
	 * @return the template pages
	 */
	protected AbstractList<BaseTemplateWizardPage> getTemplatePages() {
		AbstractList<BaseTemplateWizardPage> templatePages = new ArrayList<BaseTemplateWizardPage>();
		
		IWizardPage[] wizardPages = getPages();
		
		for(int i = 0; i < wizardPages.length; i++)
			templatePages.add((BaseTemplateWizardPage)wizardPages[i]);

		return templatePages;
	}

	/**
	 * Save preferences to the preference store.
	 *
	 */
	private void savePreferencesToStore() {
		for(BaseTemplateWizardPage page : getTemplatePages())
			page.savePreferencesToStore();
	}
	
	public abstract void init(IWorkbench workbench, IStructuredSelection selection);

	public abstract void addPages();
	
	/**
	 * Get transformed templates from different template pages.
	 * 
	 * @return a list of transformed templates
	 */
	public abstract AbstractList<TransformedTemplate> getTransformedTemplates();
}
