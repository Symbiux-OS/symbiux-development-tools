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

import java.io.File;
import java.io.IOException;
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
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelFactory;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideConfiguration;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;
import com.nokia.s60tools.imaker.internal.impmodel.util.ImpResourceFactoryImpl;

/**
 * <!-- begin-user-doc --> A test suite for the '<em><b>impmodel</b></em>'
 * package. <!-- end-user-doc -->
 * 
 * @generated
 */
public class ImpResourceSaveTests extends TestCase {

	private File testFile;
	private ImpmodelFactory factory;
	private ImpDocument document;

	@Override
	protected void setUp() throws Exception {
		factory = ImpmodelFactory.eINSTANCE;
		document = factory.createImpDocument();
		testFile = getTestFile();
	}

	@Override
	protected void tearDown() throws Exception {
		testFile.delete();
	}


	public void testSavingEmptyDocument() throws Exception {
		saveModel(document);
		EObject doc = loadContents();
		assertNotNull(doc);
	}

	public void testSavingDocumentWithComments() throws Exception {
		Comment comment = factory.createComment();
		comment.setComment("This is a test comment");
		document.getComments().add(comment);
		saveModel(document);
		ImpDocument doc = (ImpDocument) loadContents();
		assertNotNull(doc);
		assertTrue("Expected comment not found!",doc.getComments().size()==1);
		Comment cm = doc.getComments().get(0);
		assertEquals(comment.getComment(), cm.getComment());
	}

	public void testSavingDocumentWithVariables() throws Exception {
		String name = "foo";
		String value = "bar";
		
		Variable var = factory.createVariable();
		var.setName(name);
		var.setValue(value);
		document.getVariables().add(var);
		saveModel(document);
		ImpDocument doc = (ImpDocument) loadContents();
		assertNotNull(doc);
		assertTrue("Expected variable not found!",doc.getVariables().size()==1);
		Variable vr = doc.getVariables().get(0);
		assertEquals(name, vr.getName());
		assertEquals(value, vr.getValue());
	}

	public void testSavingDocumentWithOrideFiles() throws Exception {
		String source = "foo";
		String target = "bar";
		
		OverrideFiles orid = factory.createOverrideFiles();
		FileListEntry entry = factory.createFileListEntry();
		entry.setSource(source);
		entry.setTarget(target);
		orid.getEntries().add(entry);
		document.getOrideFiles().add(orid);		
		saveModel(document);
		ImpDocument doc = (ImpDocument) loadContents();
		assertNotNull(doc);
		assertTrue("Expected override files not found!",doc.getOrideFiles().size()==1);
		OverrideFiles or = doc.getOrideFiles().get(0);
		assertTrue(or.getEntries().size()==1);
		entry = or.getEntries().get(0);
		assertEquals(source, entry.getSource());
		assertEquals(target, entry.getTarget());
	}

	public void testSavingDocumentWithOrideConfs() throws Exception {
		String file = "foo";
		String action = "udeb";
		String location = "core";
		
		OverrideConfiguration orid = factory.createOverrideConfiguration();
		ConfigEntry entry = factory.createConfigEntry();
		entry.setTarget(file);
		entry.setAction(action);
		entry.setLocation(location);
		orid.getEntries().add(entry);
		document.getOrideConfs().add(orid);		
		saveModel(document);
		ImpDocument doc = (ImpDocument) loadContents();
		assertNotNull(doc);
		assertTrue("Expected override files not found!",doc.getOrideConfs().size()==1);
		OverrideConfiguration or = doc.getOrideConfs().get(0);
		assertTrue(or.getEntries().size()==1);
		entry = or.getEntries().get(0);
		assertEquals(file, entry.getTarget());
		assertEquals(action, entry.getAction());
		assertEquals(location, entry.getLocation());
	}

	public void testSavingDocumentWithLineNumbers() throws Exception {
		Comment comment = factory.createComment();
		comment.setComment("Example file");
		comment.setLineNumber(0);
		document.getComments().add(comment);
		
		Variable var = factory.createVariable();
		var.setName("foo");
		var.setValue("bar");
		var.setLineNumber(4);
		document.getVariables().add(var);
		
		
		ConfigEntry entry = factory.createConfigEntry();
		entry.setTarget("foo");
		entry.setAction("udeb");
		entry.setLocation("core");
		OverrideConfiguration orid = factory.createOverrideConfiguration();
		orid.getEntries().add(entry);
		orid.setLineNumber(9);
		document.getOrideConfs().add(orid);		

		saveModel(document);
		
		ImpDocument doc = (ImpDocument) loadContents();
		assertEquals(1, doc.getComments().get(0).getLineNumber());
		assertEquals(5, doc.getVariables().get(0).getLineNumber());
		assertEquals(10, doc.getOrideConfs().get(0).getLineNumber());	}
	
	private File getTestFile() throws IOException {
		File file = File.createTempFile("test", ".imp");
		return file;
	}

	private void saveModel(EObject model) throws IOException {
		Resource res = getResource();
		res.getContents().add(model);
		res.save(null);
	}

	private EObject loadContents() throws IOException {
		Resource resource = getResource();
		resource.load(null);
		EList<EObject> contents = resource.getContents();
		assertFalse("resource is empty", contents.isEmpty());
		return contents.get(0);
	}

	private Resource getResource() {
		ResourceSetImpl rs = new ResourceSetImpl();
		Map<String, Object> factoryMap = rs.getResourceFactoryRegistry()
				.getExtensionToFactoryMap();
		factoryMap.put("imp", new ImpResourceFactoryImpl());

		URI uri = URI.createFileURI(testFile.getAbsolutePath());
		Resource resource = rs.createResource(uri);
		return resource;
	}

} // ImpResourceLoadTests
