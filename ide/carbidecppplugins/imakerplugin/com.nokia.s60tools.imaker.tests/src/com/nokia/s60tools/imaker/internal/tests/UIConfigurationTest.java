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

import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.UIVariable;
import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;

public class UIConfigurationTest extends TestCase{
	private Configuration conf = null;
	private UIConfiguration uiConfig = null;	
	
	public void setUp() throws Exception {
		Target target1          = IQRFFactory.eINSTANCE.createTarget();
		target1.setName("Target 1");
		target1.setDescription("This is a target");
		Target target2          = IQRFFactory.eINSTANCE.createTarget();
		target2.setName("Target 2");
		target2.setDescription("This is another target");
		Result result           = IQRFFactory.eINSTANCE.createResult();
		Interface intf          = IQRFFactory.eINSTANCE.createInterface();
		intf.setName("A new interface");
		ConfigurationElement element1 = IQRFFactory.eINSTANCE.createConfigurationElement();
		element1.setName(com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapperPreferences.PRODUCT_NAME);
		element1.setDescription("Product name");
		element1.setValues("char[255]");
		ConfigurationElement element2 = IQRFFactory.eINSTANCE.createConfigurationElement();
		element2.setName(com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapperPreferences.COREPLATFORM_NAME);
		element2.setDescription("Core Platform Name");
		element2.setValues("char[255]");
		ConfigurationElement element3 = IQRFFactory.eINSTANCE.createConfigurationElement();
		element3.setName("HWID");
		element3.setDescription("Hardware Id");
		element3.setValues("char[255]");
		intf.addConfigurationElement(element1);
		intf.addConfigurationElement(element2);
		intf.addConfigurationElement(element3);
		conf                    = IQRFFactory.eINSTANCE.createConfiguration();
		conf.setName("MAKEFILE.MK");
		conf.setFilePath("c:\\temp\\something");
		conf.addTargetRef(target1);
		conf.addTargetRef(target2);
		Setting setting1        = IQRFFactory.eINSTANCE.createSetting();
		setting1.setName("Product Name");
		setting1.setValue("devlon51");
		setting1.setConfigurationElement(element1);
		Setting setting2        = IQRFFactory.eINSTANCE.createSetting();
		setting2.setName("core platform name");
		setting2.setValue("ncp51");
		setting2.setConfigurationElement(element2);
		Setting setting3        = IQRFFactory.eINSTANCE.createSetting();
		setting3.setName("hwid");
		setting3.setValue("HWID_5101 HWID_5102");
		setting3.setConfigurationElement(element3);
		conf.addSetting(setting1);
		conf.addSetting(setting2);
		conf.addSetting(setting3);
		result.addConfiguration(conf);
		result.addInterface(intf);
		result.addTarget(target1);
		result.addTarget(target2);
		
		uiConfig = new UIConfiguration(conf,null);
		
	}

	public void testUIConfigurationProductName() {
		assertEquals("devlon51", uiConfig.getProductName());
	}
	
	public void testUIConfigurationMakeFileName() {
		assertEquals("MAKEFILE.MK", uiConfig.getMakeFileName());
	}
	
	public void testUIConfigurationMakeFilePath() {
		assertEquals("c:\\temp\\something", uiConfig.getFilePath());
	}
	
	public void testUIConfigurationDefaultHWID() {
		assertEquals("HWID_5101", uiConfig.getDefaultHWID());
	}
	
	public void testUIConfigurationTargets() {
		assertTrue(uiConfig.getAllTargets() != null);
		assertEquals(2,uiConfig.getAllTargets().size());
	}
	
	public void testUIConfigurationVariables() {
		assertEquals(3,uiConfig.getVariables().size());
		UIVariable variable = (UIVariable)uiConfig.getVariables().get(0);
		assertEquals("Product Name",variable.getName());
		assertEquals("char[255]",variable.getValueFormat());
	}
	
	public void testUIConfigurationHWID() {
		assertTrue(uiConfig.getHWIDs() != null);
		assertEquals(2,uiConfig.getHWIDs().size());
	}
	
	public void testUIConfigurationPlatformName() {
		assertEquals("ncp51", uiConfig.getPlatformName());
	}
}
