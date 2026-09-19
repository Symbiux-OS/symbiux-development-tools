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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.s60tools.imaker.internal.iqrf.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(IMakerTest.class);
		suite.addTestSuite(TargetTest.class);
		suite.addTestSuite(ResultTest.class);
		suite.addTestSuite(SettingTest.class);
		suite.addTestSuite(InterfaceTest.class);
		suite.addTestSuite(ConfigurationElementTest.class);
		suite.addTestSuite(ConfigurationTest.class);
		//$JUnit-END$
		return suite;
	}

}
