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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;

import junit.framework.TestCase;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;

public class ProjectBuilder extends TestCase {
	public static final String TESTDATA_DIR   = "testdata";
	public static final String[] imp_file_names = new String[] {"test1.imp","test2.imp"};
//	public static final String[] imp_file_names = new String[] {"test1.imp","test2.imp"};
	public static final String[] imp_file_contents = new String[] {
		"define IMAGE_ORIDECONF\n" +
		"\\sys\\bin\\HelloConsole.exe replace-add core\n"+
		"endef\n" + 
		"define IMAGE_ORIDEFILES\n" +
		"\\epoc32\\release\\ARMV5\\UDEB\\HelloConsole.exe \\sys\\bin\\HelloConsole.exe\n"+
		"endef\n"+
		"WORKDIR = \\temp\n"+
		"VERBOSE = 31\n" +
		"USE_SYMGEN = 1\n" +
		"TYPE = rnd\n" +
		"DEFAULT_GOALS = core rofs3\n" +
		"TARGET_PRODUCT = test_ui"
		,
		"#iMaker properties\n" +
		"#Fri Sep 18 09:31:17 EEST 2009\n" +
		"PRODUCT=image_conf_dilbert_ui.mk\n" +
		"TARGET=core\n" +
		"HWID=0100\n" +
		"NAME=FIELD_VALUE\n" +
		"MAKEFILE=\\\\epoc32\\\\rom\\\\config\\\\ncp70\\\\dilbert\\\\image_conf_dilbert_ui.mk\n" +
		"TYPE=rnd\n" +
		"TARGET_LIST=core\n" +
		"DEBUG_FILES=true;false;\\\\epoc32\\\\data\\\\welcome.gif;\\\\welcome.gif;ROFS3"};
	private final String PROJECT_NAME = "test";
	protected IProject project = null;
	
	protected void createDefaultProject() throws CoreException {
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		IWorkspaceRoot root = workspace.getRoot();
		
		project = root.getProject(PROJECT_NAME);
		if(!project.exists()) {
			project.create(null);
		}
		if(!project.isOpen()) {
			project.open(null);
		}
	}
	
	protected void closeAndDeleteDefaultProject() throws CoreException {
		if(project!=null&&project.exists()) {
			project.close(null);
			project.delete(true, true, null);
		}
	}

	protected IFile addImakerImpFiles(String content, String name) {
		IFolder folder = project.getFolder(TESTDATA_DIR);
		try {
			if(!folder.exists()) {
				folder.create(true, true, null);	
			}
			ByteArrayInputStream input = new ByteArrayInputStream(content.getBytes());
			IFile file = folder.getFile(name);
			if(!file.exists()) {
				file.create(input, true, null);
			} else {
				file.setContents(input, true, false, null);			
			}
			return file;
		} catch (CoreException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static File[] getTestFiles() {
		File f = new File(TESTDATA_DIR);
		File[] files = f.listFiles(new FilenameFilter() {
			
//			@Override
			public boolean accept(File dir, String name) {
				return name.endsWith(".imp");
			}
		});		
		return files;
	}
}
