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

import junit.framework.TestCase;

import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Setting;

public class SettingTest extends TestCase {
	protected Setting fixture = null;

	protected void setFixture(Setting fixture) {
		this.fixture = fixture;
	}

	private Setting getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createSetting());
	}

	public void tearDown() throws Exception {
		setFixture(null);
	}

	public void testSetConfigurationElement__ConfigurationElement() {
		// Ensure that you remove @generated or mark it @generated NOT
		ConfigurationElement cl = IQRFFactory.eINSTANCE.createConfigurationElement();
		Setting st = this.getFixture();
		st.setConfigurationElement(cl);
		assertEquals(st.getRef(),cl);
	}

	public void testName() {
		String name = "settingName";
		Setting st = getFixture();
		st.setName(name);
		assertEquals(st.getName(),name);
	}
	
	public void testValue() {
		String value = "example value";
		Setting st = getFixture();
		st.setValue(value);
		assertEquals(st.getValue(),value);
	}
} //SettingTest
