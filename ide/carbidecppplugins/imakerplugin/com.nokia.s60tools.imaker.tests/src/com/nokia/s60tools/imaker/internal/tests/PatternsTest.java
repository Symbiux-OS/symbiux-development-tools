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

import java.util.regex.Matcher;

import junit.framework.TestCase;

import com.nokia.s60tools.imaker.internal.console.IMakerJob;

public class PatternsTest extends TestCase {

	public void testImakerJobPattern1() throws Exception {
		String[] data = new String[]{"sfdasdf = '/epoc32/rombuild'",
				"sfdasdf = '\\workdir\\work'",
		"sfdasdf = '\\workdir/work'"};
		
		for (int i = 0; i < data.length; i++) {
			String str = data[i];
			Matcher matcher = IMakerJob.VARIABLE_PATTERN1.matcher(str);
			assertTrue("Match failled on string: "+str,matcher.find());
		}
	}
	
	public void testImakerJobPattern2() throws Exception {
		String[] data2 = new String[]{"sfdasdf = 's:/epoc32/rombuild'",
				"sfdasdf = 's:\\workdir\\work'",
		"sfdasdf = 's:\\workdir/work'"};

		for (int i = 0; i < data2.length; i++) {
			String str = data2[i];
			Matcher matcher = IMakerJob.VARIABLE_PATTERN2.matcher(str);
			assertTrue("Match failled on string: "+str,matcher.find());
		}
	}	
}
