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

import com.nokia.s60tools.imaker.internal.iqrf.ConfigurationElement;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Interface;

public class InterfaceTest extends TestCase {
	protected Interface fixture = null;

	protected void setFixture(Interface fixture) {
		this.fixture = fixture;
	}

	private Interface getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createInterface());
	}

	public void tearDown() throws Exception {
		setFixture(null);
	}

	public void testAddConfigurationElement__ConfigurationElement() {
		// Ensure that you remove @generated or mark it @generated NOT
		ConfigurationElement c1 = IQRFFactory.eINSTANCE.createConfigurationElement();
		ConfigurationElement c2 = IQRFFactory.eINSTANCE.createConfigurationElement();
		Interface it = getFixture();
		it.addConfigurationElement(c1);
		it.addConfigurationElement(c2);
		List conelements = (List)it.getConfigurationElements();
		assertTrue(conelements.contains(c1));
		assertTrue(conelements.contains(c2));
		assertEquals(conelements.get(0),c1);
	}
	
	public void testName() {
		String name = "interfaceName";
		Interface it = getFixture();
		it.setName(name);
		assertEquals(it.getName(),name);
	}
} //InterfaceTest
