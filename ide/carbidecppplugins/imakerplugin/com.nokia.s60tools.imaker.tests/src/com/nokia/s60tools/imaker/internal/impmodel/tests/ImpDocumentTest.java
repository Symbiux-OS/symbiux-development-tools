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

import com.nokia.s60tools.imaker.internal.impmodel.ImpDocument;
import com.nokia.s60tools.imaker.internal.impmodel.ImpmodelFactory;
import com.nokia.s60tools.imaker.internal.impmodel.Variable;

import junit.framework.TestCase;

import junit.textui.TestRunner;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Imp Document</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getVariable(java.lang.String) <em>Get Variable</em>}</li>
 * </ul>
 * </p>
 * @generated
 */
public class ImpDocumentTest extends TestCase {

	/**
	 * The fixture for this Imp Document test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImpDocument fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ImpDocumentTest.class);
	}

	/**
	 * Constructs a new Imp Document test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImpDocumentTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Imp Document test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(ImpDocument fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Imp Document test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ImpDocument getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ImpmodelFactory.eINSTANCE.createImpDocument());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the '{@link com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getVariable(java.lang.String) <em>Get Variable</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.nokia.s60tools.imaker.internal.impmodel.ImpDocument#getVariable(java.lang.String)
	 * @generated NOT
	 */
	public void testGetVariable__String() {
		ImpmodelFactory factory = ImpmodelFactory.eINSTANCE;
		Variable v1 = factory.createVariable();
		Variable v2 = factory.createVariable();
		
		String v1_name = "v1";
		String v2_name = "v2";
		v1.setName(v1_name);
		v2.setName(v2_name);
		
		getFixture().getVariables().add(v1);
		getFixture().getVariables().add(v2);
		
		assertSame(v1, getFixture().getVariable(v1_name));
		assertSame(v2, getFixture().getVariable(v2_name));
		assertSame(null, getFixture().getVariable("test"));		
	}

} //ImpDocumentTest
