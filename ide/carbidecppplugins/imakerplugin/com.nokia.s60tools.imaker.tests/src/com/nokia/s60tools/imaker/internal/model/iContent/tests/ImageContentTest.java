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

package com.nokia.s60tools.imaker.internal.model.iContent.tests;

import junit.framework.TestCase;

import com.nokia.s60tools.imaker.internal.model.iContent.ACTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IContentFactory;
import com.nokia.s60tools.imaker.internal.model.iContent.IMAGESECTION;
import com.nokia.s60tools.imaker.internal.model.iContent.IbyEntry;
import com.nokia.s60tools.imaker.internal.model.iContent.ImageContent;

public class ImageContentTest extends TestCase {
	protected ImageContent fixture = null;

	protected void setFixture(ImageContent fixture) {
		this.fixture = fixture;
	}

	private ImageContent getFixture() {
		return fixture;
	}

	public void testImageContent() throws Exception {
		ImageContent content = this.getFixture();
		assertNotNull(content);
		assertNotNull(content.getEntries());
		assertTrue(content.getEntries().size()==0);
		IbyEntry ie = IContentFactory.eINSTANCE.createIbyEntry();

		ie.setAction(ACTION.HIDE);
		ie.setEnabled(false);
		String file = "huuhaa.exe";
		ie.setFile(file);
		ie.setLocation(IMAGESECTION.CORE);
		
		assertEquals(file, ie.getFile());
		content.getEntries().add(ie);
		assertTrue(content.getEntries().size()==1);
	}

	public void setUp() throws Exception {
		setFixture(IContentFactory.eINSTANCE.createImageContent());
	}

	public void tearDown() throws Exception {
		setFixture(null);
	}

} //ImageContentTest
