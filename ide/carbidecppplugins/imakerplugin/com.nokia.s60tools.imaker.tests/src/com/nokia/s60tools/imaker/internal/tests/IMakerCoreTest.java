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
package com.nokia.s60tools.imaker.internal.tests;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.FileLocator;
import org.osgi.framework.Bundle;

import junit.framework.TestCase;

public class IMakerCoreTest extends TestCase {
	protected List<String> command = null;
	protected final String PERL = "C:\\apps\\actperl\\bin\\perl.exe";
	protected String iMakerStubPath = null;

	/**
	 * Setup fixture.
	 */
	public void setUp() throws Exception {
		command = new ArrayList<String>();
		Bundle bundle = Activator.getDefault().getBundle();
		URL relativeURL = bundle.getEntry("/");
		
		URL localURL = FileLocator.toFileURL(relativeURL);
		File f = new File(localURL.getPath());
		String pluginInstallLocation = f.getAbsolutePath();
		iMakerStubPath = pluginInstallLocation + "\\tools\\";
	}

	/**
	 * Tear down fixture.
	 */
	public void tearDown() throws Exception {
		command.clear();
	}	
}