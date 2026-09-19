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


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;

import com.nokia.s60tools.imaker.internal.managers.ProjectManager;

public class ProjectManagerTest extends ProjectBuilder  {
	private final String CONTENT  = "sample content";
	private final String LOCATION = "rofs3";
	private final String FILENAME = ProjectManager.IBY_FILENAME_PREFIX+LOCATION;
	private ProjectManager projectManager;

	public void setUp() throws Exception {
		createDefaultProject();
		assertTrue(project.isAccessible());
		projectManager = new ProjectManager(project);
	}
	
    public void tearDown() throws Exception {
//        closeAndDeleteDefaultProject();
    }

    public void testLocation() {
    	assertTrue(!projectManager.getRoot().equals(""));
    }

    public void testImplFiles() throws Exception {
    	addImakerImpFiles("sample content", "test.imp");
		assertTrue("No impl files found. There shoud be some in the testdata folder!",!projectManager.getImakerFiles().isEmpty());		
	}

	public void testIbyFiles() {
		IPath path = project.getLocation(); 
		IPath filePath = projectManager.createIbyFile(FILENAME, CONTENT, path);
		File file = filePath.toFile();
		assertTrue("Coudn't create iby file under test projec!", file.exists());
		try {
			FileInputStream fs = new FileInputStream(file);
			String line = getContents(fs);
			assertEquals(CONTENT, line);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getContents(InputStream contents) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(contents));
		String line = br.readLine();
		return line;
	}

	public void testMakefile() throws Exception {
		List<IPath> ifiles = new ArrayList<IPath>();		
		IPath path = project.getLocation(); 
		IPath filePath = projectManager.createIbyFile(FILENAME, CONTENT, path);
		ifiles.add(filePath);

		IPath mk = projectManager.createAdditionsMakefile(ifiles,path);
		
		assertNotNull("Makefile creation failled!",mk);
		assertTrue("Makefile creation failled!", mk.toFile().exists());
		FileInputStream fs = new FileInputStream(mk.toFile());
		String cons = getContents(fs);
		assertNotNull(cons);
		assertEquals(LOCATION.toUpperCase()+"_OBY += "+ filePath.toFile().getAbsolutePath(),cons);
	}
}
