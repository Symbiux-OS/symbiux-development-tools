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

package com.nokia.s60tools.imaker.internal.iqrf;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>IMaker</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker#getResult <em>Result</em>}</li>
 *   <li>{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker#getQuery <em>Query</em>}</li>
 * </ul>
 * </p>
 *
 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getIMaker()
 * @model
 * @generated
 */
public interface IMaker extends EObject {
	/**
	 * Returns the value of the '<em><b>Result</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Result</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Result</em>' containment reference.
	 * @see #setResult(Result)
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getIMaker_Result()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Result getResult();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker#getResult <em>Result</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Result</em>' containment reference.
	 * @see #getResult()
	 * @generated
	 */
	void setResult(Result value);

	/**
	 * Returns the value of the '<em><b>Query</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Query</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Query</em>' attribute.
	 * @see #setQuery(String)
	 * @see com.nokia.s60tools.imaker.internal.iqrf.IQRFPackage#getIMaker_Query()
	 * @model default=""
	 * @generated
	 */
	String getQuery();

	/**
	 * Sets the value of the '{@link com.nokia.s60tools.imaker.internal.iqrf.IMaker#getQuery <em>Query</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Query</em>' attribute.
	 * @see #getQuery()
	 * @generated
	 */
	void setQuery(String value);
	
} // IMaker