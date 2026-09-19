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
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.internal.wrapper.IMakerWrapper;


public class IMakerWrapperTest extends IMakerCoreTest {
	
	public void testImakerWrapperAgainsGivenStubs() throws Exception {
		List<String> script = new ArrayList<String>();
		File f = new File(iMakerStubPath);
		String[] files = f.list(new FilenameFilter() {
//			@Override
			public boolean accept(File dir, String name) {
				return name.startsWith("iMakerStub");
			}
		});
		for (int i = 0; i < files.length; i++) {
			script.add(PERL);
			String stub = files[i];
			String stubPath = iMakerStubPath + stub;
			script.add(stubPath);
			IIMakerWrapper wrapper = new IMakerWrapper(script);
			String version = wrapper.getIMakerCoreVersion();
			assertTrue("iMakerWrapper failed with stub " + stub, version!=null);
			assertTrue("iMakerWrapper failed with stub " + stub, version.length()>1);
			assertTrue("iMakerWrapper failed with stub " + stub, version.startsWith("iMaker"));

			List<UIConfiguration> configs = wrapper.getConfigurations(null,null);
			assertTrue("iMakerWrapper failed with stub " + stub, !configs.isEmpty());

			ArrayList<String> params = new ArrayList<String>();
			params.add("test");
			String command = wrapper.getBuildCommand(params);
			assertNotNull("iMakerWrapper failed with stub " + stub, command);
			params.clear();
			params.add("-f");
			params.add("mk");
			params.add("flash");
			boolean success = wrapper.buildImage(params, null);
			assertTrue("iMakerWrapper failed with stub " + stub, success);
			script.clear();
		}
	}
}
