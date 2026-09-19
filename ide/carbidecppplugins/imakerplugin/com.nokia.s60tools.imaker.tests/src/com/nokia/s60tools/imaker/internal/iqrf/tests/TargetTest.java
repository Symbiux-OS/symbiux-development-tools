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

import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Target;


public class TargetTest extends TestCase {
	protected Target fixture = null;

	protected void setFixture(Target fixture) {
		this.fixture = fixture;
	}

	private Target getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createTarget());
	}

	public void testName() {
		String name = "targetName";
		Target target = getFixture();
		target.setName(name);
		assertEquals(target.getName(),name);
	}
	
	public void testDescription() {
		String description = "This is test description";
		Target target = this.getFixture();
		target.setDescription(description);
		assertEquals(target.getDescription(),description);
	}
	
	public void tearDown() throws Exception {
		setFixture(null);
	}

} //TargetTest
