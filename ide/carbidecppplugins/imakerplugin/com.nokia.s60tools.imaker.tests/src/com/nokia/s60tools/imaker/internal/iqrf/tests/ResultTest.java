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
import com.nokia.s60tools.imaker.internal.iqrf.Interface;
import com.nokia.s60tools.imaker.internal.iqrf.Result;
import com.nokia.s60tools.imaker.internal.iqrf.Target;

public class ResultTest extends TestCase {
	protected Result fixture = null;
	
	protected void setFixture(Result fixture) {
		this.fixture = fixture;
	}

	private Result getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createResult());
	}

	public void tearDown() throws Exception {
		setFixture(null);
	}

	public void testAddConfiguration__Configuration() {
		// Ensure that you remove @generated or mark it @generated NOT
		Configuration c1 = IQRFFactory.eINSTANCE.createConfiguration();
		Configuration c2 = IQRFFactory.eINSTANCE.createConfiguration();
		Result it = getFixture();
		it.addConfiguration(c1);
		it.addConfiguration(c2);
		List conelements = (List)it.getConfigurations();
		assertTrue(conelements.contains(c1));
		assertTrue(conelements.contains(c2));
		assertEquals(conelements.get(0),c1);
		assertEquals(conelements.size(),2);
	}

	public void testAddInterface__Interface() {
		// Ensure that you remove @generated or mark it @generated NOT
		Interface i1 = IQRFFactory.eINSTANCE.createInterface();
		Interface i2 = IQRFFactory.eINSTANCE.createInterface();
		Result it = getFixture();
		it.addInterface(i1);
		it.addInterface(i2);
		List ints = (List)it.getInterfaces();
		assertTrue(ints.contains(i1));
		assertTrue(ints.contains(i2));
		assertEquals(ints.get(0),i1);
		assertEquals(ints.size(),2);
	}

	public void testAddTarget__Target() {
		// Ensure that you remove @generated or mark it @generated NOT
		Target t1 = IQRFFactory.eINSTANCE.createTarget();
		Target t2 = IQRFFactory.eINSTANCE.createTarget();
		Result it = getFixture();
		it.addTarget(t1);
		it.addTarget(t2);
		List targets = (List)it.getTargets();
		assertTrue(targets.contains(t1));
		assertTrue(targets.contains(t2));
		assertEquals(targets.get(0),t1);
		assertEquals(targets.size(),2);
	}

} //ResultTest
