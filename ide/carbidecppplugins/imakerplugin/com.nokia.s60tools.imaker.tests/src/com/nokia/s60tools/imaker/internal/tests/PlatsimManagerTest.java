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


import java.io.File;
import java.util.List;

import junit.framework.TestCase;


import com.nokia.s60tools.imaker.internal.wrapper.PlatsimManager;

public class PlatsimManagerTest extends TestCase{

	public void setUp() throws Exception {
	}

	public void tearDown() throws Exception {
	}

	public void testGetInstances() {
		String drive = "z:\\";
		PlatsimManager manager = new PlatsimManager(drive);
		String loc = manager.getDefaulfLocation();
		File f = new File(loc);
		List<String> ins = manager.getInstances();
		assertNotNull(ins);
		if(f.exists()) {
			assertTrue("No platsim instances exist!",ins.size()>=1);			
		} else {
			assertTrue(ins.size()==0);						
		}
	}

}
