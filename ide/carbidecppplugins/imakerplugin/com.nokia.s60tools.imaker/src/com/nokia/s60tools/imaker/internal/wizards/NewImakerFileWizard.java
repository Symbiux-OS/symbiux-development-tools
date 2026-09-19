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

package com.nokia.s60tools.imaker.internal.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.viewers.PreferencesTab;

public class NewImakerFileWizard extends Wizard implements INewWizard {
	private IStructuredSelection selection;
	private NewImakerFileWizardPage newFileWizardPage;
	

	private ImakerProperties values;
	private UIConfiguration configuration;
	private boolean saved = false;
	private IFile file = null;

	public NewImakerFileWizard(ImakerProperties values, UIConfiguration current, PreferencesTab tab) {
		setWindowTitle("New iMaker File");
		this.values = values;
		this.configuration = current;
	} 

	public boolean isSaved() {
		return saved;
	}

	public NewImakerFileWizardPage getNewFileWizardPage() {
		return newFileWizardPage;
	}

	@Override
	public void addPages() {
		newFileWizardPage = new NewImakerFileWizardPage(selection,values,configuration);
		addPage(newFileWizardPage);
	}

	@Override
	public boolean performFinish() {
		file = newFileWizardPage.createNewFile();
		this.saved = true;
		return true;
	}
	
	public IFile getFile() {
		return file;
	}

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
	}
}
