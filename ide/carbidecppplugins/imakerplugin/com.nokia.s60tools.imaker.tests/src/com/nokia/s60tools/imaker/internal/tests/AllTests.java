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

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
				"Test for com.nokia.s60tools.imaker.internal.tests");
		//$JUnit-BEGIN$
		suite.addTestSuite(SettingsViewerTest.class);
		suite.addTestSuite(PlatsimManagerTest.class);
		suite.addTestSuite(IMakerWrapperTest.class);
		suite.addTestSuite(IMakerAPITest.class);
		suite.addTestSuite(ProjectManagerTest.class);
		suite.addTestSuite(UIConfigurationTest.class);
		suite.addTestSuite(EnvironmentTest.class);
		suite.addTestSuite(ImakerPropertiesTest.class);
		suite.addTestSuite(IQRFWrapperTest.class);
		suite.addTestSuite(PatternsTest.class);
		suite.addTest(com.nokia.s60tools.imaker.internal.iqrf.tests.AllTests.suite());
		suite.addTest(com.nokia.s60tools.imaker.internal.model.iContent.tests.AllTests.suite());
		suite.addTest(com.nokia.s60tools.imaker.internal.impmodel.tests.AllTests.suite());
		//$JUnit-END$
		return suite;
	}

}
