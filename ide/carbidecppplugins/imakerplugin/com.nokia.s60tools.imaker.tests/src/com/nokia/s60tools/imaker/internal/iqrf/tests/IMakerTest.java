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

import com.nokia.s60tools.imaker.internal.iqrf.IMaker;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.iqrf.Result;


public class IMakerTest extends TestCase {
	protected IMaker fixture = null;

	protected void setFixture(IMaker fixture) {
		this.fixture = fixture;
	}

	private IMaker getFixture() {
		return fixture;
	}

	public void setUp() throws Exception {
		setFixture(IQRFFactory.eINSTANCE.createIMaker());
	}

	public void testSetQuery() {
		IMaker imaker = getFixture();
		String query = "imaker -help";
		imaker.setQuery(query);
		assertEquals(imaker.getQuery(),query);
	}

	public void testSetResult() {
		IMaker imaker = getFixture();
		Result result = IQRFFactory.eINSTANCE.createResult();
		imaker.setResult(result);
		assertEquals(imaker.getResult(),result);
	}
	
	public void tearDown() throws Exception {
		setFixture(null);
	}

} //IMakerTest
