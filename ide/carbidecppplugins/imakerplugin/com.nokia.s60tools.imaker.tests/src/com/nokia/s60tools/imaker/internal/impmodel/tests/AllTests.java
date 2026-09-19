/*
* Copyright (c) 2009 Nokia Corporation and/or its subsidiary(-ies).
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

package com.nokia.s60tools.imaker.internal.impmodel.tests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <!-- begin-user-doc -->
 * A test suite for the '<em><b>Imp</b></em>' model.
 * <!-- end-user-doc -->
 * @generated
 */
public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(
		"Test for com.nokia.s60tools.imaker.internal.impmodel.tests");
//		suite.addTestSuite(CommentContainerTest.class);
//		suite.addTestSuite(CommentTest.class);
//		suite.addTestSuite(ConfigEntryTest.class);
//		suite.addTestSuite(FileListEntryTest.class);
//		suite.addTestSuite(ImpDocumentTest.class);
//		suite.addTestSuite(LineNumberContainerTest.class);
//		suite.addTestSuite(OverrideConfigurationTest.class);
//		suite.addTestSuite(OverrideFilesTest.class);
//		suite.addTestSuite(VariableTest.class);
		suite.addTestSuite(ImpResourceLoadTests.class);
		suite.addTestSuite(ImpResourceSaveTests.class);
		return suite;
	}
} //ImpAllTests
