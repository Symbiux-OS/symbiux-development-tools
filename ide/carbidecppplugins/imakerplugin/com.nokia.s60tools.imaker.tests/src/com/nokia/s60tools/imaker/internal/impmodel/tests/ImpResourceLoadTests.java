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

package com.nokia.s60tools.imaker.internal.impmodel.tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import junit.framework.TestCase;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.nokia.s60tools.imaker.internal.impmodel.Comment;
import com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry;
import com.nokia.s60tools.imaker.internal.impmodel.FileListEntry;
import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;
import com.nokia.s60tools.imaker.internal.impmodel.util.ImpResourceFactoryImpl;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>impmodel</b></em>' package.
 * <!-- end-user-doc -->
 * @generated
 */
public class ImpResourceLoadTests extends TestCase {

	
	
	private File testFile;

	@Override
	protected void setUp() throws Exception {
		testFile = getTestFile();
	}
	
	@Override
	protected void tearDown() throws Exception {
		testFile.delete();
	}

	private File getTestFile() throws IOException {
		File file = File.createTempFile("test", ".imp");
		return file;
	}

	public void testLoadingEmptyFile() throws Exception {
		EObject contents =  loadContents();
		assertNotNull(contents);
		assertTrue(contents instanceof ImpDocument);
	}


	public void testLoadingComments() throws Exception {
		String comments = "#comment1\n#comment2\nhuuhaa";
		populateTestFile(comments);
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue("Not desired amount of comments found!",doc.getComments().size()==2);
		Comment cm = doc.getComments().get(0);
		assertEquals("comment1", cm.getComment());
	}

	public void testLoadingVariables() throws Exception {
		String content = "#comment1\nvar1 = foo\nvar1 = bar";
		populateTestFile(content);
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue("Not desired amount of variables found!",doc.getVariables().size()==2);
		Variable var = doc.getVariables().get(0);
		assertEquals("var1", var.getName());
		assertEquals("foo", var.getValue());
	}

	public void testLoadingOverrideFiles() throws Exception {
		String content = "#comment1\nvar1 = foo\n" +
				"define IMAGE_ORIDEFILES\n" +
				"\ttarget1 source1\n" +
				"\ttarget2 source2\n" +
				"endef";

		populateTestFile(content);
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue("Not desired amount of override files found!",doc.getOrideFiles().size()==1);
		
		OverrideFiles of = doc.getOrideFiles().get(0);
		assertTrue("Not desired amount of entries in the list!",of.getEntries().size()==2);
	}

	public void testLoadingOverrideFilesWithSpace() throws Exception {
		String content = "#comment1\nvar1 = foo\n" +
		"define IMAGE_ORIDEFILES\n" +
		"\t\"source file1\" target1\n" +
		"\tsource2 \"target file2\"\n" +
		"endef";
		
		populateTestFile(content);
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue("Not desired amount of override files found!",doc.getOrideFiles().size()==1);
		
		OverrideFiles of = doc.getOrideFiles().get(0);
		assertTrue("Not desired amount of entries in the list!",of.getEntries().size()==2);
		FileListEntry entry = of.getEntries().get(0);
		assertEquals("\"source file1\"",	entry.getSource());
		entry = of.getEntries().get(1);
		assertEquals("\"target file2\"",	entry.getTarget());
		
	}

	public void testLoadingOverrideConfs() throws Exception {
		String content = "#comment1\nvar1 = foo\n" +
		"define IMAGE_ORIDECONF\n" +
		"\tekern.exe udeb core\n" +
		"\tAbout.r01 hide rofs2\n" +
		"endef";
		
		populateTestFile(content);
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue("Not desired amount of override confs found!",doc.getOrideConfs().size()==1);
		
		OverrideConfiguration oc = doc.getOrideConfs().get(0);
		assertTrue("Not desired amount of entries in the list!",oc.getEntries().size()==2);
		ConfigEntry entry = oc.getEntries().get(0);
		assertEquals("ekern.exe",	entry.getTarget());
		assertEquals("udeb",	entry.getAction());
		assertEquals("core",	entry.getLocation());
	}
	
	public void testLoadingOverrideFilesAndConfsTogether() throws Exception {
		String content = "#comment1\n#variables\nvar1 = foo\n" +
				"#trace files\n" +
				"define IMAGE_ORIDEFILES\n" +
				"\tsource1 ekern.exe\n" +
				"\tsource2 About.r01\n" +
				"endef\n" +
				"#trace configurations\n" +
				"define IMAGE_ORIDECONF\n" +
				"\tekern.exe udeb core\n" +
				"\tAbout.r01 hide rofs2\n" +
				"endef";

		populateTestFile(content);
		
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue("Not desired amount of override files found!",doc.getOrideFiles().size()==1);
		assertTrue("Not desired amount of override confs found!",doc.getOrideConfs().size()==1);
		
		OverrideFiles files = doc.getOrideFiles().get(0);
		FileListEntry entry = files.getEntries().get(0);
		assertFalse("File Entry has no associated action",entry.getActions().isEmpty());
		ConfigEntry action = entry.getActions().get(0);
		assertEquals("udeb", action.getAction());
		assertEquals("core", action.getLocation());
	}

	public void testLoadingLineBreaks() throws Exception {
		String comment = "This is a longggggg comment continued and continued";
		String content = "#This is a longggggg \\\ncomment continued \\\nand continued\n";
		
		populateTestFile(content);
		ImpDocument doc = (ImpDocument) loadContents();
		assertTrue(doc.getComments().size()==1);
		assertEquals(comment, doc.getComments().get(0).getComment());
	}

	public void testLoadingLineNumbers() throws Exception {
		String content = "#comment1\n" +
				"var1 = foo\n" +
		"#trace files\n" +
		"define IMAGE_ORIDEFILES\n" +
		"\ttarget1 source1\n" +
		"\ttarget2 source2\n" +
		"endef\n" +
		"#trace configurations\n" +
		"define IMAGE_ORIDECONF\n" +
		"\tekern.exe udeb core\n" +
		"\tAbout.r01 hide rofs2\n" +
		"endef";
		
		populateTestFile(content);
		ImpDocument doc = (ImpDocument) loadContents();
		assertEquals(1, doc.getComments().get(0).getLineNumber());
		assertEquals(3, doc.getComments().get(1).getLineNumber());
		assertEquals(8, doc.getComments().get(2).getLineNumber());
		assertEquals(2, doc.getVariables().get(0).getLineNumber());
		assertEquals(4, doc.getOrideFiles().get(0).getLineNumber());
		assertEquals(9, doc.getOrideConfs().get(0).getLineNumber());
	}
	
	private void populateTestFile(String content) throws IOException {
	    Writer output = new BufferedWriter(new FileWriter(testFile));
	    try {
	      output.write( content );
	    }
	    finally {
	      output.close();
	    }
	}

	private EObject loadContents() throws IOException {
		ResourceSetImpl rs = new ResourceSetImpl();
		Map<String, Object> factoryMap = rs.getResourceFactoryRegistry().getExtensionToFactoryMap();
		factoryMap.put("imp", new ImpResourceFactoryImpl());
		
		URI uri = URI.createFileURI(testFile.getAbsolutePath());
		Resource resource = rs.createResource(uri);
		assertNotNull(resource);
		resource.load(null);
		EList<EObject> contents = resource.getContents();
		assertFalse("resource is empty",contents.isEmpty());
		return contents.get(0);
	}

} //ImpResourceLoadTests
