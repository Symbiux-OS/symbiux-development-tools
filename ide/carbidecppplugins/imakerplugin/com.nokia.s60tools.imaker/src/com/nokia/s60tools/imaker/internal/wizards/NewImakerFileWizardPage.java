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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;

import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;

public class NewImakerFileWizardPage extends WizardNewFileCreationPage {

	private ImakerProperties values;
	private UIConfiguration config;

	public NewImakerFileWizardPage(IStructuredSelection selection, ImakerProperties values, UIConfiguration config) {
        super("NewImakerFileWizardPage", selection);
        setTitle("iMaker File");
        setDescription("Creates a new iMaker File");
        setFileExtension("imp");
        this.values = values;
        this.config = config;
	}

	@Override
	protected InputStream getInitialContents() {
		try {
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			values.setVariables(config.getVariables());
			values.store(bao, "iMaker properties");
			ByteArrayInputStream bs = new ByteArrayInputStream(bao.toString().getBytes());
			return bs;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
