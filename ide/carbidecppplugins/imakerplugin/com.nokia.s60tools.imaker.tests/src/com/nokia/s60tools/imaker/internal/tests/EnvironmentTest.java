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

import static org.easymock.EasyMock.*;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.eclipse.core.runtime.IProgressMonitor;

import com.nokia.s60tools.imaker.IEnvironment;
import com.nokia.s60tools.imaker.IIMakerWrapper;
import com.nokia.s60tools.imaker.UIConfiguration;
import com.nokia.s60tools.imaker.internal.iqrf.IQRFFactory;
import com.nokia.s60tools.imaker.internal.model.Environment;

public class EnvironmentTest extends TestCase {
	private IEnvironment environment = null;
	private IIMakerWrapper wrapperMock = null;
	private static final String DRIVE = "x:\\";
	
	public void setUp() throws Exception {
		wrapperMock = createMock(IIMakerWrapper.class);
		environment = new Environment(DRIVE);
		environment.setImakerWrapper(wrapperMock);
	}
	
	public void tearDown() throws Exception {
		wrapperMock = null;
		environment = null;
	}
	
	
	public void testCreate() throws Exception {
		assertNotNull(environment);
		assertEquals(DRIVE, environment.getDrive());
	}
	
	public void testLoad() throws Exception {
		assertFalse(environment.isLoaded());
		List<UIConfiguration> confs = new ArrayList<UIConfiguration>();
		confs.add(new UIConfiguration(IQRFFactory.eINSTANCE.createConfiguration(),null));
		int numberOfconfs = confs.size();
		wrapperMock.getConfigurations(isA(IProgressMonitor.class),null);
		expectLastCall().andReturn(confs).once();
		
		replay(wrapperMock);
		List<UIConfiguration> configurations = environment.load();
		assertTrue(environment.isLoaded());
		assertNotNull(configurations);
		assertTrue(configurations.size()>=numberOfconfs);
		assertTrue(environment.getConfigurations().size()>=numberOfconfs);
		assertSame(configurations, environment.getConfigurations());
		verify(wrapperMock);
	}
}
