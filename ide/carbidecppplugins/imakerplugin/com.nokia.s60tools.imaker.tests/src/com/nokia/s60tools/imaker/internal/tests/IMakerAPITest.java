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

import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.IMakerPlugin;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreAlreadyRunningException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreExecutionException;
import com.nokia.s60tools.imaker.exceptions.IMakerCoreNotFoundException;

public class IMakerAPITest extends IMakerCoreTest {

	public void testImakerAPIVersion() throws Exception {
		String stubPath = iMakerStubPath + "iMakerStub_093701.pl";

		IIMakerWrapper wrapper = getWrapper(stubPath);
		String version = wrapper.getIMakerCoreVersion();
		assertTrue("iMaker API don't work as expected!", version.startsWith("iMaker 10.03.01"));
	}

	private IIMakerWrapper getWrapper(String stubPath) {
		IIMakerWrapper wrapper = IMakerPlugin.getImakerWrapper(stubPath,true);

		List<String> tool = wrapper.getTool();
		tool.clear();
		tool.add(PERL);
		tool.add(stubPath);
		return wrapper;
	}

	public void testImakerAPINotExistingImpFile() throws Exception {
		String stubPath = iMakerStubPath + "iMakerStub_imp.pl";

		IIMakerWrapper wrapper = getWrapper(stubPath);
		File f = null;
		try {
			wrapper.buildImage(f, System.out);
			fail("wrapper should't accept illigal arguments!");
		} catch (Exception e) {
			// expected
			assertTrue(e instanceof IMakerCoreExecutionException);
			String message = e.getMessage();
			assertTrue(message.startsWith("Invalid"));
		}
	}

	public void testImakerAPIEmptyImpFile() throws Exception {
		String stubPath = iMakerStubPath + "iMaker_imp.pl";

		File f = File.createTempFile("test", ".imp");
		runAndVerifyResult(stubPath, f);
	}


	public void testImakerAPIImpFileWithSpaces() throws Exception {
		String stubPath = iMakerStubPath + "iMaker_imp.pl";

		File f = File.createTempFile("test 2", ".imp");
		System.out.println(f.getAbsolutePath());
		runAndVerifyResult(stubPath, f);
	}

	private void runAndVerifyResult(String stubPath, File f)
	throws IMakerCoreNotFoundException, IMakerCoreExecutionException,
	IMakerCoreAlreadyRunningException {
		IIMakerWrapper wrapper = getWrapper(stubPath);
		boolean result = wrapper.buildImage(f, System.out);		
		assertEquals(true, result);
	}

}
