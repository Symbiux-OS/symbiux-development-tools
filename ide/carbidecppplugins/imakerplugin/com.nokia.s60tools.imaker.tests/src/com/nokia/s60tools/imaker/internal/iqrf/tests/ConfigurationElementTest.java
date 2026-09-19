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


public class ConfigurationElementTest extends TestCase {
	protected ConfigurationElement fixture = null;

	protected void setFixture(ConfigurationElement fixture) {
		this.fixture = fixture;
	}

	private ConfigurationElement getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createConfigurationElement());
	}

	public void testName() {
		String name = "configurationElementName";
		ConfigurationElement conel = getFixture();
		conel.setName(name);
		assertEquals(conel.getName(),name);
	}
	
	public void testDescription() {
		String description = "This is test description";
		ConfigurationElement conel = this.getFixture();
		conel.setDescription(description);
		assertEquals(conel.getDescription(),description);
	}
	
	public void testValues() {
		String values = "example value";
		ConfigurationElement conel = this.getFixture();
		conel.setValues(values);
		assertEquals(conel.getValues(),values);
	}

	public void tearDown() throws Exception {
		setFixture(null);
	}

} //ConfigurationElementTest
