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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import com.nokia.s60tools.imaker.IMakerKeyConstants;
import com.nokia.s60tools.imaker.internal.impmodel.ConfigEntry;
import com.nokia.s60tools.imaker.internal.impmodel.FileListEntry;
import com.nokia.s60tools.imaker.internal.impmodel.ImpConstants;
import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.OverrideFiles;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;
import com.nokia.s60tools.imaker.internal.impmodel.util.ImpResourceFactoryImpl;
import com.nokia.s60tools.imaker.internal.model.ImakerProperties;
import com.nokia.s60tools.imaker.internal.model.iContent.ACTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;



public class ImakerPropertiesTest extends ProjectBuilder {

//	private String FIELD_NAME = "NAME";
//	private String FIELD_VALUE = "FIELD_VALUE";
	private ImakerProperties iProperties;
	private IFile file;

	@Override
	protected void setUp() throws Exception {
		createDefaultProject();
		assertTrue(project.isAccessible());
		iProperties = new ImakerProperties();
		file = project.getFile("sample.imp");
	}
	
	@Override
    public void tearDown() throws Exception {
        closeAndDeleteDefaultProject();
    }
	
	
	public void testImakerPropertiesLoading() throws Exception {
		String content = "define IMAGE_ORIDECONF\n"
				+ "\\sys\\bin\\HelloConsole.exe replace-add core\n"
				+ "endef\n"
				+ "define IMAGE_ORIDEFILES\n"
				+ "\\epoc32\\release\\ARMV5\\UDEB\\HelloConsole.exe \\sys\\bin\\HelloConsole.exe\n"
				+ "endef\n" + "WORKDIR = \\temp\n" + "VERBOSE = 31\n"
				+ "USE_SYMGEN = 1\n" + "TYPE = rnd\n"
				+ "DEFAULT_GOALS = core rofs3\n" + "TARGET_PRODUCT = test_ui\n"
				+ "RUN_PLATSIM = 1\n" + "PLATSIM_INSTANCE = 4";

		IFile ifile = addImakerImpFiles(content, "test1.imp");
		
		//create properties from the file
		ImakerProperties ip = ImakerProperties.createFromFile(ifile);
		assertNotNull(ip);
		
		//verify
		assertEquals("test_ui",ip.get(IMakerKeyConstants.PRODUCT));
		assertEquals("core rofs3",ip.get(IMakerKeyConstants.TARGET_LIST));
		assertEquals("rnd",ip.get(IMakerKeyConstants.TYPE));
		assertEquals("1",ip.get(IMakerKeyConstants.SYMBOLFILES));
		assertEquals("31",ip.get(IMakerKeyConstants.VERBOSE));
		
		String adds = (String) ip.get(IMakerKeyConstants.ADDITIONAL_PARAMETERS);
		assertTrue(adds.contains("WORKDIR"));
		assertFalse(adds.contains("RUN_PLATSIM"));

		assertEquals("4",ip.get(IMakerKeyConstants.PLATSIM_INSTANCE));
		assertEquals("1",ip.get(IMakerKeyConstants.PLATSIM_RUN));		
	}

	public void testImakerPropertiesLoadingFiles() throws Exception {
		String content = "define IMAGE_ORIDECONF\n"
			+ "\\sys\\bin\\HelloConsole1.exe replace-add core\n"
			+ "HelloConsole2.exe replace-add rofs3\n"
			+ "endef\n"
			+ "define IMAGE_ORIDEFILES\n"
			+ "\\epoc32\\release\\ARMV5\\UDEB\\HelloConsole1.exe \\sys\\bin\\HelloConsole1.exe\n"
			+ "\\epoc32\\release\\ARMV5\\UDEB\\HelloConsole2.exe \\sys\\test\\HelloConsole2.exe\n"
			+ "endef\n";
		
		IFile ifile = addImakerImpFiles(content, "test1.imp");
		
		//create properties from the file
		ImakerProperties ip = ImakerProperties.createFromFile(ifile);
		assertNotNull(ip);
		
		//verify
		Object files = ip.get(IMakerKeyConstants.DEBUGFILES);
		assertNotNull(files);
		List<IbyEntry> oFiles = (List<IbyEntry>) files;
		assertEquals(2, oFiles.size());
		IbyEntry iby = oFiles.get(0);
		
		assertEquals(true, iby.isEnabled());
		assertEquals("\\epoc32\\release\\ARMV5\\UDEB\\HelloConsole1.exe", iby.getFile());
		assertEquals("\\sys\\bin\\HelloConsole1.exe", iby.getTarget());
		assertEquals("core", iby.getLocation().getLiteral());
	}
    
    public void testSavingToFileMainTab() throws Exception {
    	String product        = "test_product";
    	String targets        = "t1 t2 t3";
    	String type           = "rnd";
    	String symgen         = "1";
    	String verbose        = "31";

    	iProperties.put(IMakerKeyConstants.PRODUCT, product);
    	iProperties.put(IMakerKeyConstants.TARGET_LIST, targets);
    	iProperties.put(IMakerKeyConstants.TYPE, type);
    	iProperties.put(IMakerKeyConstants.SYMBOLFILES, symgen);
    	iProperties.put(IMakerKeyConstants.VERBOSE, verbose);
		
		createAndSaveTheFile();

		ImpDocument model = getImpModelAndVerify();

		verifyVariableIsPresent(ImpConstants.TARGET_PRODUCT, product, model);
		verifyVariableIsPresent(ImpConstants.DEFAULT_GOALS,  targets, model);
		verifyVariableIsPresent(ImpConstants.TYPE,type,model);
		verifyVariableIsPresent(ImpConstants.USE_SYMGEN,symgen,model);
		verifyVariableIsPresent(ImpConstants.VERBOSE,verbose,model);
	}
    
    public void testSavingToFileMainTabAdditionalParameters() throws Exception {
    	String var1_value   = "\\temp"; 
    	String var2_value   = "\"test value2\""; 
    	String addParameters  = "VAR1="+var1_value+" VAR2="+var2_value;

    	iProperties.put(IMakerKeyConstants.ADDITIONAL_PARAMETERS, addParameters);
    	
    	createAndSaveTheFile();
    	
    	ImpDocument model = getImpModelAndVerify();
    	
    	//verify additionals
    	verifyVariableIsPresent("VAR1",var1_value,model);
    	verifyVariableIsPresent("VAR2","test value2",model);
    }
    
    public void testSavingToFileContentTab() throws Exception {
    	List<IbyEntry> entries = new ArrayList<IbyEntry>();
    	
    	IbyEntry entry = IContentFactory.eINSTANCE.createIbyEntry();
    	entry.setEnabled(true);
    	entry.setAction(ACTION.HIDE);
		entry.setFile("file1");
    	entry.setLocation(IMAGESECTION.CORE);
		entry.setTarget("target1");
		entries.add(entry);

		entry = IContentFactory.eINSTANCE.createIbyEntry();
		entry.setEnabled(false);
		entry.setAction(ACTION.REMOVE);
		entry.setFile("file2");
		entry.setLocation(IMAGESECTION.ROFS3);
		entry.setTarget("target2");
		entries.add(entry);

		entry = IContentFactory.eINSTANCE.createIbyEntry();
		entry.setEnabled(true);
		entry.setAction(ACTION.REPLACE);
		entry.setFile("file3");
		entry.setLocation(IMAGESECTION.ROFS3);
		entry.setTarget("target3");
		entries.add(entry);

    	
    	iProperties.put(IMakerKeyConstants.DEBUGFILES, entries);
    	
    	createAndSaveTheFile();
    	
    	ImpDocument model = getImpModelAndVerify();
    	
    	assertEquals(1,model.getOrideFiles().size());
    	OverrideFiles files = model.getOrideFiles().get(0);
    	assertEquals(2, files.getEntries().size());
    	FileListEntry fEntry = files.getEntries().get(0);
    	assertEquals("file1", fEntry.getSource());
    	assertEquals("target1", fEntry.getTarget());
    	assertEquals("core", fEntry.getActions().get(0).getLocation());
    	
    	fEntry = files.getEntries().get(1);
    	assertEquals(1, fEntry.getActions().size());
    	ConfigEntry cEntry = fEntry.getActions().get(0);
    	assertEquals("rofs3", cEntry.getLocation());
    }

    public void testSavingToFilePlatsimTab() throws Exception {
    	String instance = "5";
    	String run = "1";
    	
    	iProperties.put(IMakerKeyConstants.PLATSIM_INSTANCE, instance);
    	iProperties.put(IMakerKeyConstants.PLATSIM_RUN, run);
    	
    	createAndSaveTheFile();
    	
    	ImpDocument model = getImpModelAndVerify();
    	
    	verifyVariableIsPresent(ImpConstants.PLATSIM_INSTANCE, instance, model);
    	verifyVariableIsPresent(ImpConstants.PLATSIM_RUN,  run, model);
    }
    
	private ImpDocument getImpModelAndVerify() throws IOException {
		//verify that correct file is produced
    	ImpDocument model = (ImpDocument) getFileContentAsImpModel(file.getLocation().toFile());
    	assertNotNull(model);
		return model;
	}

	private void createAndSaveTheFile() throws CoreException {
		//create file
    	file.create(null, true, null);
    	
    	//Save the properties to file
    	iProperties.saveToFile(file.getLocation().toFile());
    	file.refreshLocal(IResource.DEPTH_ZERO, null);
	}

	private void verifyVariableIsPresent(String name, String value, ImpDocument model) {
		Variable var = model.getVariable(name);
		assertNotNull(var);
		assertEquals(value, var.getValue());
	}

	private EObject getFileContentAsImpModel(File file) throws IOException {
		ResourceSetImpl rs = new ResourceSetImpl();
		Map<String, Object> factoryMap = rs.getResourceFactoryRegistry().getExtensionToFactoryMap();
		factoryMap.put("imp", new ImpResourceFactoryImpl());
		
		URI uri = URI.createFileURI(file.getAbsolutePath());
		Resource resource = rs.createResource(uri);
		assertNotNull(resource);
		resource.load(null);
		EList<EObject> contents = resource.getContents();
		assertFalse("resource is empty",contents.isEmpty());
		return contents.get(0);	
	}
}

