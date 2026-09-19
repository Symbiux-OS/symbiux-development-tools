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
package com.nokia.s60tools.imaker.internal.iqrf.tests;

import java.util.List;

import junit.framework.TestCase;

import com.nokia.s60tools.imaker.internal.iqrf.Configuration;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;
import com.nokia.s60tools.imaker.internal.iqrf.Target;

public class ConfigurationTest  extends TestCase {
	protected Configuration fixture = null;

	protected void setFixture(Configuration fixture) {
		this.fixture = fixture;
	}

	private Configuration getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createConfiguration());
	}

	public void tearDown() throws Exception {
		setFixture(null);
	}

	public void testAddSetting__Setting() {
		// Ensure that you remove @generated or mark it @generated NOT
		Setting s1 = IQRFFactory.eINSTANCE.createSetting();
		Setting s2 = IQRFFactory.eINSTANCE.createSetting();
		Configuration ct = getFixture();
		ct.addSetting(s1);
		ct.addSetting(s2);
		List settings = (List)ct.getSettings();
		assertTrue(settings.contains(s1));
		assertTrue(settings.contains(s2));
		assertEquals(settings.get(0),s1);
	}

	public void testAddTargetRef__Target() {
		// Ensure that you remove @generated or mark it @generated NOT
		Target t1 = IQRFFactory.eINSTANCE.createTarget();
		Target t2 = IQRFFactory.eINSTANCE.createTarget();
		Configuration ct = getFixture();
		ct.addTargetRef(t1);
		ct.addTargetRef(t2);
		List targets = (List)ct.getTargetrefs();
		assertTrue(targets.contains(t1));
		assertTrue(targets.contains(t2));
		assertEquals(targets.get(0),t1);
	}
	
	public void testName() {
		String name = "configurationName";
		Configuration ct = getFixture();
		ct.setName(name);
		assertEquals(ct.getName(),name);
	}
	
	public void testFilePath() {
		String fp = "c:\\temp\\f1.xml";
		Configuration ct = getFixture();
		ct.setFilePath(fp);
		assertEquals(ct.getFilePath(),fp);
	}
} //ConfigurationTest
