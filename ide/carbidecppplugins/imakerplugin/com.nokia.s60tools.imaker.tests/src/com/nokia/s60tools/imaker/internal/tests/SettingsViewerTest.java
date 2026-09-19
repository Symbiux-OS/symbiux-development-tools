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

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;

import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;
import com.nokia.s60tools.imaker.internal.viewers.SettingsViewer;

public class SettingsViewerTest extends TestCase{
	private Shell shell;
	private SettingsViewer settingsViewer;
	private Composite composite;

	public void setUp() throws Exception {
		shell = new Shell();
		shell.setText("Sample shell");
		
		GridLayout layout = new GridLayout();
		shell.setLayout(layout);
		
		composite = new Composite(shell,SWT.NONE);		
	}

	public void tearDown() throws Exception {
		shell.dispose();
	}

	public void testThatControlsAreCreated() {
		settingsViewer = new SettingsViewer(composite);
		assertTrue(composite.getChildren()!=null);
		assertTrue(composite.getChildren().length!=0);
		displayShellWithSettingsViewer();
	}

	private void displayShellWithSettingsViewer() {
		shell.setSize(600, 500);
		shell.open();
//		Display display = shell.getDisplay();
//		while (!shell.isDisposed()) {
//			if (!display.readAndDispatch())
//				display.sleep();
//		}		
	}
	
	public void testViewerTable() {
		settingsViewer = new SettingsViewer(composite);
		Control[] children = composite.getChildren();
		assertTrue(children[0] instanceof Table);
		Table table = (Table) children[0];
		assertTrue(table.getHeaderVisible());
		assertTrue(table.getLinesVisible());
	}
	
	public void testTableColumns() throws Exception {
		settingsViewer = new SettingsViewer(composite);
		Control[] children = composite.getChildren();
		Table table = (Table) children[0];
		assertEquals(4, table.getColumnCount());
		TableColumn column = table.getColumn(1);
		assertEquals("Value", column.getText());
	}
	
	public void testWithUIConfiguration() throws Exception {
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
		element3.setName("HardwareID");
		element3.setDescription("Hardware Id");
		element3.setValues("char[255]");
		intf.addConfigurationElement(element1);
		intf.addConfigurationElement(element2);
		intf.addConfigurationElement(element3);
		Configuration conf = IQRFFactory.eINSTANCE.createConfiguration();
		conf.setName("MAKEFILE.MK");
		conf.setFilePath("c:\\temp\\something");
		conf.addTargetRef(target1);
		conf.addTargetRef(target2);
		Setting setting1        = IQRFFactory.eINSTANCE.createSetting();
		setting1.setName("Product Name");
		setting1.setValue("devlon51");
		setting1.setConfigurationElement(element1);
		Setting setting2        = IQRFFactory.eINSTANCE.createSetting();
		setting2.setName("Core Platform");
		setting2.setValue("*");
		setting2.setConfigurationElement(element2);
		Setting setting3        = IQRFFactory.eINSTANCE.createSetting();
		setting3.setName("Hardware Id");
		setting3.setValue("5101");
		setting3.setConfigurationElement(element3);
		conf.addSetting(setting1);
		conf.addSetting(setting2);
		conf.addSetting(setting3);
		result.addConfiguration(conf);
		result.addInterface(intf);
		result.addTarget(target1);
		result.addTarget(target2);
		
		UIConfiguration uiConfig = new UIConfiguration(conf,null);
		settingsViewer = new SettingsViewer(composite);
		settingsViewer.setUiConfiguration(uiConfig);

		displayShellWithSettingsViewer();
	}
}
