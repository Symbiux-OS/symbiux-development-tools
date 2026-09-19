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


import junit.framework.TestCase;

import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IMaker;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;
import com.nokia.s60tools.imaker.internal.iqrf.wrapper.IQRFWrapper;

public class IQRFWrapperTest extends TestCase{

	private IQRFWrapper wrapper = null;
	private IMaker imaker = null;
	private String xmi = null;

	public void setUp() throws Exception {
		wrapper = new IQRFWrapper();
		// Create an object to test the wrapper.
		imaker = IQRFFactory.eINSTANCE.createIMaker();
		imaker.setQuery("empty query");
		Target target1 = IQRFFactory.eINSTANCE.createTarget();
		target1.setName("Target 1");
		target1.setDescription("This is a target");
		Target target2 = IQRFFactory.eINSTANCE.createTarget();
		target2.setName("Target 2");
		target2.setDescription("This is another target");
		Result result = IQRFFactory.eINSTANCE.createResult();
		Interface intf = IQRFFactory.eINSTANCE.createInterface();
		intf.setName("A new interface");
		ConfigurationElement element1 = IQRFFactory.eINSTANCE.createConfigurationElement();
		element1.setName("ProductName");
		element1.setDescription("Product name");
		element1.setValues("char[255]");
		ConfigurationElement element2 = IQRFFactory.eINSTANCE.createConfigurationElement();
		element2.setName("CorePlatformName");
		element2.setDescription("Core Platform Name");
		element2.setValues("char[255]");
		ConfigurationElement element3 = IQRFFactory.eINSTANCE.createConfigurationElement();
		element3.setName("HWID");
		element3.setDescription("Hardware Id");
		element3.setValues("char[255]");
		intf.addConfigurationElement(element1);
		intf.addConfigurationElement(element2);
		intf.addConfigurationElement(element3);
		Configuration config = IQRFFactory.eINSTANCE.createConfiguration();
		config.setName("MAKEFILE.MK");
		config.setFilePath("c:\\temp\\something");
		config.addTargetRef(target1);
		config.addTargetRef(target2);
		Setting setting1 = IQRFFactory.eINSTANCE.createSetting();
		setting1.setName("productname");
		setting1.setValue("*");
		setting1.setConfigurationElement(element1);
		Setting setting2 = IQRFFactory.eINSTANCE.createSetting();
		setting2.setName("coreplatformname");
		setting2.setValue("*");
		setting2.setConfigurationElement(element2);
		Setting setting3 = IQRFFactory.eINSTANCE.createSetting();
		setting3.setName("hwid");
		setting3.setValue("HWID_5101, HWID_510");
		setting3.setConfigurationElement(element3);
		config.addSetting(setting1);
		config.addSetting(setting2);
		config.addSetting(setting3);
		result.addConfiguration(config);
		result.addInterface(intf);
		result.addTarget(target1);
		result.addTarget(target2);
		imaker.setResult(result);
		
		// Create an XMI document to test the wrapper.
		StringBuffer tmp = new StringBuffer();
		
		tmp.append("<?xml version=\"1.0\" encoding=\"ASCII\"?>\r\n");
		tmp.append("<IMaker xmi:version=\"2.0\" xmlns:xmi=\"http://www.omg.org/XMI\" xmlns=\"iqrf\" query=\"empty query\">\r\n");
		tmp.append("  <result>\r\n");
		tmp.append("    <interfaces name=\"A new interface\">\r\n");
		tmp.append("      <configurationElements name=\"ProductName\" description=\"Product name\" values=\"char[255]\"/>\r\n");
		tmp.append("      <configurationElements name=\"CorePlatformName\" description=\"Core Platform Name\" values=\"char[255]\"/>\r\n");
		tmp.append("      <configurationElements name=\"HWID\" description=\"Hardware Id\" values=\"char[255]\"/>\r\n");
		tmp.append("    </interfaces>\r\n");
		tmp.append("    <configurations name=\"MAKEFILE.MK\" filePath=\"c:\\temp\\something\" targetrefs=\"//@result/@targets.0 //@result/@targets.1\">\r\n");
		tmp.append("      <settings name=\"productname\" value=\"devlon51\" ref=\"//@result/@interfaces.0/@configurationElements.0\"/>\r\n");
		tmp.append("      <settings name=\"coreplatformname\" value=\"*\" ref=\"//@result/@interfaces.0/@configurationElements.1\"/>\r\n");
		tmp.append("      <settings name=\"hwid\" value=\"HWID_5101, HWID_5102\" ref=\"//@result/@interfaces.0/@configurationElements.2\"/>\r\n");
		tmp.append("    </configurations>\r\n");
		tmp.append("    <targets name=\"Target 1\" description=\"This is a target\"/>\r\n");
		tmp.append("    <targets name=\"Target 2\" description=\"This is another target\"/>\r\n");
		tmp.append("  </result>\r\n");
		tmp.append("</IMaker>\r\n");
			
		xmi = tmp.toString();
	}


	/**
	 * Tests that the imaker object first serialized to XMI
	 * and then deserialized back to model again contains
	 * the exact same data.
	 */
	public void testGetResult() {
		assertTrue(imaker != null);
		String doc = wrapper.getXMLDocument(imaker);
		assertTrue(doc != null);
		IMaker model = wrapper.getModel(doc);
		assertTrue(model != null);
	}

	/**
	 * Tests that the XMI document first deserialized to a model
	 * and then serialized back to XMI is the same document.
	 */
	public void testGetXMLDocument() {
		assertTrue(xmi != null);
		IMaker imaker2 = wrapper.getModel(xmi);
		assertTrue(imaker2 != null);
		String xmi2 = wrapper.getXMLDocument(imaker2);
		assertTrue(xmi2 != null);
		assertTrue(xmi.equals(xmi2));
	}
	
	public void testErrorConditions() {
		IMaker model1 = wrapper.getModel(xmi);
		IMaker model2 = wrapper.getModel(xmi);
		model2.setQuery("sadfasfdas");
		assertFalse("model1 should be equal to model2!",model1.equals(model2));
		
		Result r = model2.getResult();
		Target t = IQRFFactory.eINSTANCE.createTarget();
		t.setName("T");
		r.addTarget(t);
		
		assertTrue("model1 should be equal to model2!",!model1.equals(model2));
		
		assertFalse("Null should be taken into account",model1.equals(null));
	}
}
